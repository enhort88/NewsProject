package by.htp.ex.service.impl;

import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.NewsDAOException;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.util.validation.ValidationException;
import by.htp.ex.util.validation.impl.NewsValidator;
import by.htp.ex.util.validation.impl.NewsValidator.NewsValidationBuilder;
import by.htp.ex.util.validation.impl.UserValidator;
import by.htp.ex.util.validation.impl.UserValidator.UserValidationBuilder;

public class NewsServiceImpl implements INewsService {

	private final INewsDAO newsDAO = DaoProvider.getInstance().getNewsDAO();

	@Override
	public void update(News news) throws ValidationException, ServiceException {

		NewsValidationBuilder newsValidationBuilder = new NewsValidator.NewsValidationBuilder();
		NewsValidator validator = newsValidationBuilder
				.checkTitle(news.getTitle())
				.checkBrief(news.getBriefNews())
				.checkContent(news.getContent())
				.checkDate(news.getNewsDate())
				.checkAll();

		if (!validator.getErrors().isEmpty()) {
			throw new ValidationException(validator.errorMessage());
		}
		try {

			newsDAO.updateNews(news);
		} catch (NewsDAOException e) {

			throw new ServiceException(e);
			
		}

	}

	@Override
	public List<News> latestList(int count) throws ServiceException {

		try {
			return newsDAO.getLatestsNews(count);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> list() throws ServiceException {
		try {
			return newsDAO.getList();
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public News findById(int id) throws ServiceException {
		try {
			return newsDAO.findNewsById(id);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteNews(int[] newsIds) throws ServiceException {
		try {
			newsDAO.deleteNews(newsIds);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
			
		}
	}

	@Override
	public void addNews(News news) throws ServiceException, ValidationException {
		NewsValidationBuilder newsValidationBuilder = new NewsValidator.NewsValidationBuilder();
		NewsValidator validator = newsValidationBuilder
				.checkTitle(news.getTitle())
				.checkBrief(news.getBriefNews())
				.checkContent(news.getContent())
				.checkDate(news.getNewsDate())
				.checkAll();
		if (!validator.getErrors().isEmpty()) {
			throw new ValidationException(validator.errorMessage());
		}
		try {
			newsDAO.addNews(news);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
			
		}

	}

}
