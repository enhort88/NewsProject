package by.htp.ex.service;

import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.util.validation.ValidationException;

public interface INewsService {

	void update(News news) throws ServiceException, ValidationException;			

	void deleteNews(int[] newsIds) throws ServiceException;

	void addNews(News news) throws ServiceException, ValidationException;			

	List<News> latestList(int count) throws ServiceException;

	List<News> list() throws ServiceException;

	News findById(int id) throws ServiceException;
}
