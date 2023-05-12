package by.htp.ex.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.NewsDAOException;
import by.htp.ex.dao.impl.connection_pool.ConnectionPool;
import by.htp.ex.dao.impl.connection_pool.ConnectionPoolException;
import by.htp.ex.dao.reentrantlock.CloseableReentrantLock;

public class NewsDAO implements INewsDAO {

	private static final CloseableReentrantLock CRLOCK = new CloseableReentrantLock();
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

	private static final String SQL_DELETE_NEWS = "DELETE FROM news WHERE id=?";
	private static final String SQL_UPDATE_NEWS = "UPDATE news SET title=?,brief=?,content=?,news_create_date=? WHERE id=?";
	private static final String SQL_ADD_NEWS = "INSERT INTO news(title, brief, content, news_create_date, users_id) VALUES(?,?,?,?,?)";
	private static final String SQL_FIND_NEWS = "SELECT * FROM news WHERE id=?";
	private static final String SQL_GET_LIST = "SELECT * FROM news ORDER BY news_create_date DESC";
	private static final String SQL_GET_LATEST = "SELECT * FROM news ORDER BY news_create_date DESC LIMIT ?";

	private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final String TABLE_FIELD_DATE = "news_create_date";
	private static final String TABLE_FIELD_ID = "id";
	private static final String TABLE_FIELD_TITLE = "title";
	private static final String TABLE_FIELD_BRIEF = "brief";
	private static final String TABLE_FIELD_CONTENT = "content";
	private static final String TABLE_FIELD_USERS_ID = "users_id";

	@Override
	public List<News> getList() throws NewsDAOException {

		List<News> result = new ArrayList<>();

		try (Connection connection = CONNECTION_POOL.takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_GET_LIST)) {

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				SimpleDateFormat formatter = new SimpleDateFormat(DATA_FORMAT);
				Date date = null;
				try {
					date = formatter.parse(resultSet.getString(TABLE_FIELD_DATE));
				} catch (ParseException e) {
					throw new NewsDAOException("Error! While parcing Date");
				}
				String latestNewsDate = formatter.format(date);
				result.add(new News(resultSet.getInt(TABLE_FIELD_ID), resultSet.getString(TABLE_FIELD_TITLE),
						resultSet.getString(TABLE_FIELD_BRIEF), resultSet.getString(TABLE_FIELD_CONTENT),
						latestNewsDate, resultSet.getInt(TABLE_FIELD_USERS_ID)));
			}
		} catch (SQLException e) {
			throw new NewsDAOException("Error! SQL request failed");
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Error! Troubles with connection!");
		}

		return result;
	}

	@Override
	public List<News> getLatestsNews(int count) throws NewsDAOException {
		List<News> result = new ArrayList<>();
		try (Connection connection = CONNECTION_POOL.takeConnection();
				PreparedStatement statementLatestNews = connection.prepareStatement(SQL_GET_LATEST)) {
			statementLatestNews.setInt(1, count);
			ResultSet resultSet = statementLatestNews.executeQuery();
			while (resultSet.next()) {
//				Date date = resultSet.getDate(TABLE_FIELD_DATE);
				SimpleDateFormat formatter = new SimpleDateFormat(DATA_FORMAT);
				Date date = null;
				try {
					date = formatter.parse(resultSet.getString(TABLE_FIELD_DATE));
				} catch (ParseException e) {
					throw new NewsDAOException("Error! While parcing Date", e);
				}
				String latestNewsDate = formatter.format(date);
				result.add(new News(resultSet.getInt(TABLE_FIELD_ID), resultSet.getString(TABLE_FIELD_TITLE),
						resultSet.getString(TABLE_FIELD_BRIEF), resultSet.getString(TABLE_FIELD_CONTENT),
						latestNewsDate, resultSet.getInt(TABLE_FIELD_USERS_ID)));
			}
		} catch (SQLException e) {
			throw new NewsDAOException("Error! SQL request failed");

		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Error! Troubles with connection!");
		}

		return result;
	}

	@Override
	public News findNewsById(int id) throws NewsDAOException {
		News news = null;
		try (Connection connection = CONNECTION_POOL.takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_NEWS)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
//				Date date = resultSet.getDate(TABLE_FIELD_DATE);
				SimpleDateFormat formatter = new SimpleDateFormat(DATA_FORMAT);
				Date date = null;
				try {
					date = formatter.parse(resultSet.getString(TABLE_FIELD_DATE));
				} catch (ParseException e) {
					throw new NewsDAOException("Error! While parcing Date", e);
				}
				String latestNewsDate = formatter.format(date);
				news = new News(resultSet.getInt(TABLE_FIELD_ID), resultSet.getString(TABLE_FIELD_TITLE),
						resultSet.getString(TABLE_FIELD_BRIEF), resultSet.getString(TABLE_FIELD_CONTENT),
						latestNewsDate, resultSet.getInt(TABLE_FIELD_USERS_ID));
			}
		} catch (SQLException e) {
			throw new NewsDAOException("Error! SQL request failed");
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Error! Troubles with connection!");
		}
		return news;
	}

	@Override
	public void deleteNews(int[] idNews) throws NewsDAOException {
		try (Connection connection = CONNECTION_POOL.takeConnection()) {
			connection.setAutoCommit(false);
			try (CloseableReentrantLock closeableLock = CRLOCK.open();
					PreparedStatement statement = connection.prepareStatement(SQL_DELETE_NEWS)) {
				for (int each : idNews) {
					try {
						int id = each;
						statement.setInt(1, id);
						statement.executeUpdate();
					} catch (NumberFormatException e) {
						throw new NewsDAOException("Error! Problems with parsing strings!");
					}
				}
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				throw new NewsDAOException("Error! SQL request failed! Rollback.");
			}
		} catch (SQLException e) {
			throw new NewsDAOException("Error! SQL request failed");
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Error! Troubles with connection!");
		}
	}

	@Override
	public void addNews(News news) throws NewsDAOException {
		try (Connection connection = CONNECTION_POOL.takeConnection()) {
			connection.setAutoCommit(false);
			try (CloseableReentrantLock closeableLock = CRLOCK.open()) {
				PreparedStatement statement = connection.prepareStatement(SQL_ADD_NEWS);
				SimpleDateFormat formatter = new SimpleDateFormat(DATA_FORMAT);
				Date date = new Date();
				String dateTime = formatter.format(date);
				statement.setString(1, news.getTitle());
				statement.setString(2, news.getBriefNews());
				statement.setString(3, news.getContent());
				statement.setString(4, dateTime);
				statement.setInt(5, news.getUserId());
				statement.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				throw new NewsDAOException("Error! SQL request failed! Rollback.");
			}
		} catch (SQLException e) {
			throw new NewsDAOException("Error! SQL request failed");
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Error! Troubles with connection!");
		}
	}

	@Override
	public void updateNews(News news) throws NewsDAOException {
		try (Connection connection = CONNECTION_POOL.takeConnection()) {
			connection.setAutoCommit(false);
			try (CloseableReentrantLock closeableLock = CRLOCK.open()) {
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_NEWS);
				SimpleDateFormat formatter = new SimpleDateFormat(DATA_FORMAT);
				Date date = new Date();
				String dateTime = formatter.format(date);
				statement.setString(1, news.getTitle());
				statement.setString(2, news.getBriefNews());
				statement.setString(3, news.getContent());
				statement.setString(4, dateTime);
				statement.setInt(5, news.getIdNews());
				statement.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				throw new NewsDAOException("Error! SQL request failed! Rollback.");
			}
		} catch (SQLException e) {
			throw new NewsDAOException("Error! SQL request failed");
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("Error! Troubles with connection!");
		}
	}

}
