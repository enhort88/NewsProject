package by.htp.ex.dao;

import java.util.List;

import by.htp.ex.bean.News;

public interface INewsDAO {
	List<News> getList() throws NewsDAOException;

	List<News> getLatestsNews(int count) throws NewsDAOException;

	News findNewsById(int id) throws NewsDAOException;

	void addNews(News news) throws NewsDAOException;

	void updateNews(News news) throws NewsDAOException;

	void deleteNews(int[] idNewses) throws NewsDAOException;
}
