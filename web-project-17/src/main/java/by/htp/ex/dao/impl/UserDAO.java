package by.htp.ex.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;

import by.htp.ex.bean.User;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.dao.impl.connection_pool.ConnectionPool;
import by.htp.ex.dao.impl.connection_pool.ConnectionPoolException;
import by.htp.ex.dao.reentrantlock.CloseableReentrantLock;

public class UserDAO implements IUserDAO {

	private static final CloseableReentrantLock CRLOCK = new CloseableReentrantLock();
	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat FORMATTER_BIRTHDAY = new SimpleDateFormat("yyyy-MM-dd");
	private static final String DATE_NOW = FORMATTER.format(new Date());

	private static final String SQL_GET_ID_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
	private static final String SQL_GET_USER_DETAIL_USERS_ID = "SELECT * FROM user_details WHERE users_id = ?";
	private static final String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
	private static final String SQL_GET_ROLE = "SELECT * FROM roles WHERE id = ?";
	private static final String SQL_REG_USER_DETAIL = "INSERT INTO user_details(users_id,name,surname,birthday,gender,adress,telnumber,email) VALUES (?,?,?,?,?,?,?,?)";
	private static final String SQL_REG_NEW_USER = "INSERT INTO users (login,password,registration_date,roles_id) VALUES (?,?,?,?)";

	private static final String TABLE_USER_ID = "id";
	private static final String TABLE_USER_LOGIN = "login";
	private static final String TABLE_USER_PASSWORD = "password";
	private static final String TABLE_USER_EMAIL = "email";

	private static final String TABLE_USER_DETAIL_NAME = "name";
	private static final String TABLE_USER_DETAIL_SURNAME = "surname";
	private static final String TABLE_USER_DETAIL_BIRTH = "birthday";
	private static final String TABLE_USER_DETAIL_GENDER = "gender";
	private static final String TABLE_USER_DETAIL_ADRESS = "adress";
	private static final String TABLE_USER_DETAIL_TEL = "telnumber";

	private static final String TABLE_ROLE_ID = "roles_id";
	private static final String TABLE_ROLE_TITLE = "title";

	@Override
	public boolean registration(User user) throws DaoException {

		int users_id = 0;
		try (Connection connection = CONNECTION_POOL.takeConnection();
				CloseableReentrantLock closeableLock = CRLOCK.open();) {
			connection.setAutoCommit(false);
			try {

				PreparedStatement psFindLogin = connection.prepareStatement(SQL_GET_ID_BY_LOGIN);
				PreparedStatement psAddUser = connection.prepareStatement(SQL_REG_NEW_USER);
				PreparedStatement psAddUserDetail = connection.prepareStatement(SQL_REG_USER_DETAIL);

				psFindLogin.setString(1, user.getLogin());
				ResultSet rsFindLogin = psFindLogin.executeQuery();
				while (rsFindLogin.next()) {
					return false;
				}
				psAddUser.setString(1, user.getLogin());
				String hashpassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
				psAddUser.setString(2, hashpassword);
				psAddUser.setString(3, DATE_NOW);
				psAddUser.setInt(4, 5);
				psAddUser.executeUpdate();

				psFindLogin.setString(1, user.getLogin());
				ResultSet rsFindId = psFindLogin.executeQuery();
				while (rsFindId.next()) {
					users_id = rsFindId.getInt(1);
				}
				psAddUserDetail.setInt(1, users_id);
				psAddUserDetail.setString(2, user.getName());
				psAddUserDetail.setString(3, user.getSurname());
				String BIRTHDAY = FORMATTER.format(user.getDateBirth());
				psAddUserDetail.setString(4, BIRTHDAY);
				psAddUserDetail.setString(5, user.getGender());
				psAddUserDetail.setString(6, user.getAdress());
				psAddUserDetail.setString(7, user.getTelnumber());
				psAddUserDetail.setString(8, user.getEmail());
				
				psAddUserDetail.executeUpdate();
				connection.commit();
				
			} catch (SQLException e) {
				connection.rollback();
				throw new DaoException("Error! SQL request failed! Rollback.");
			}
		} catch (SQLException e) {
			throw new DaoException("Error! SQL request failed");
		} catch (ConnectionPoolException e) {
			throw new DaoException("Error! Troubles with connection!");
		}
		return true;
	}

	@Override
	public boolean logination(String login, String password) throws DaoException {

		try (Connection connection = CONNECTION_POOL.takeConnection()) {
			PreparedStatement statement = connection.prepareStatement(SQL_GET_ID_BY_LOGIN);

			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String hashPassword = resultSet.getString(TABLE_USER_PASSWORD);
				return BCrypt.checkpw(password, hashPassword);
			}
			return false;
		} catch (IllegalArgumentException e) {
			throw new DaoException("Error! Unsupported symbols are used!");
		} catch (SQLException e) {
			throw new DaoException("Error! SQL request failed");
		} catch (ConnectionPoolException e) {
			throw new DaoException("Error! Troubles with connection!");
		}
	}

	@Override
	public String getRole(String login) throws DaoException {
		try (Connection connection = CONNECTION_POOL.takeConnection()) {
			PreparedStatement stUserRoleId = connection.prepareStatement(SQL_GET_ID_BY_LOGIN);
			PreparedStatement stRolesTitle = connection.prepareStatement(SQL_GET_ROLE);

			stUserRoleId.setString(1, login);
			ResultSet rsUserRoleId = stUserRoleId.executeQuery();
			while (rsUserRoleId.next()) {
				int rolesId = rsUserRoleId.getInt(TABLE_ROLE_ID);
				stRolesTitle.setInt(1, rolesId);
				ResultSet rsRoleTitle = stRolesTitle.executeQuery();
				while (rsRoleTitle.next()) {
					String result = rsRoleTitle.getString(TABLE_ROLE_TITLE);
					return result;
				}
			}
			throw new DaoException("Error! There is no such login: " + "\"" + login + "\"");
		} catch (SQLException e) {
			throw new DaoException("Error! SQL request failed");
		} catch (ConnectionPoolException e) {
			throw new DaoException("Error! Troubles with connection!");
		}
	}

	@Override
	public User getUserById(Integer id) throws DaoException {
		User user = new User();

		try (Connection connection = CONNECTION_POOL.takeConnection()) {
			String email= "";
			PreparedStatement stUserById = connection.prepareStatement(SQL_GET_USER_BY_ID);
			PreparedStatement stUserDetail = connection.prepareStatement(SQL_GET_USER_DETAIL_USERS_ID);
			stUserById.setInt(1, id);
			ResultSet rsUserById = stUserById.executeQuery();
			while (rsUserById.next()) {
				user.setLogin(rsUserById.getString(TABLE_USER_LOGIN));
				user.setPassword(rsUserById.getString(TABLE_USER_PASSWORD));
				email = rsUserById.getString(TABLE_USER_EMAIL);
			}
			stUserDetail.setInt(1, id);
			ResultSet rsUserDetail = stUserDetail.executeQuery();
			while (rsUserDetail.next()) {
				user.setName(rsUserDetail.getString(TABLE_USER_DETAIL_NAME));
				user.setSurname(rsUserDetail.getString(TABLE_USER_DETAIL_SURNAME));
				user.setGender(rsUserDetail.getString(TABLE_USER_DETAIL_GENDER));
				user.setAdress(rsUserDetail.getString(TABLE_USER_DETAIL_ADRESS));
				user.setTelnumber(rsUserDetail.getString(TABLE_USER_DETAIL_TEL));
				// user.setEmail(email);
				try {
					user.setDateBirth(FORMATTER_BIRTHDAY.parse(rsUserDetail.getString(TABLE_USER_DETAIL_BIRTH)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return user;

		} catch (SQLException e) {
			throw new DaoException("Error! SQL request failed!", e);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Error! Troubles with connection!");
		}
	}

	@Override
	public int getIdbyLogin(String login) throws DaoException {
		try (Connection connection = CONNECTION_POOL.takeConnection()) {
			PreparedStatement statement = connection.prepareStatement(SQL_GET_ID_BY_LOGIN);
			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int userId = resultSet.getInt(TABLE_USER_ID);
				return userId;
			}
			throw new DaoException("Error, user is missing");

		} catch (SQLException e) {
			throw new DaoException("Error! SQL request failed");
		} catch (ConnectionPoolException e) {
			throw new DaoException("Error! Troubles with connection!");
		}
	}
}
