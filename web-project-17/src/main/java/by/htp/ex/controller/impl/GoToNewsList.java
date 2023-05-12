package by.htp.ex.controller.impl;

import java.io.IOException;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToNewsList implements Command {

	private static final int NEWS_COUNT_ALL_ACTIVE = 1;
	private static final int DEFAULT_NEWS_PAGE = 1;
	private static final int NEWS_COUNT_ALL_DEFAULT = 1;
	private static final int NEWS_COUNT_ON_PAGE = 5;
	private static final String NEWS_COUNT_ALL = "newsCountAll";
	private static final String PAGE_NUMBER_VIEW = "pageNumberView";
	private static final String TOTAL_AMMOUNT_OF_PAGES = "totalAmmountPages";
	private static final String PAGE_NUMBER = "pageNumber";
	private static final String NEWS_COUNT = "newsCount";
	private static final int DEFAULT_NEWS_COUNT = 5;
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String ERROR_MESS = "Error, problems with listing news";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String BASE_LAYOUT_JSP = "WEB-INF/pages/layouts/baseLayout.jsp";
	private static final String PRESENTATION = "presentation";
	private static final String NEWS_LIST = "newsList";
	private static final String NEWS = "news";
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<News> newsList = null;
		int newsPage;
		int newsCountOnPage;
		int totalAmmountNews;
		int totalAmmountPages;
		int beginNumber;
		int endNumber;

		try {
			newsList = newsService.list();
			

		} catch (ServiceException e) {
			session.setAttribute(ERROR_MESSAGE, ERROR_MESS);
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		}
		
		String pageNumber = (String) request.getParameter(PAGE_NUMBER);

		if (pageNumber != null) {
			newsPage = Integer.parseInt(pageNumber);
		} else {
			newsPage = DEFAULT_NEWS_PAGE;
		}
		String newsCountStr = (String) request.getParameter(NEWS_COUNT);

		if (newsCountStr != null) {
			newsCountOnPage = Integer.parseInt(newsCountStr);

		} else {
			newsCountOnPage = DEFAULT_NEWS_COUNT;
		}

		totalAmmountNews = newsList.size();
		
		if (totalAmmountNews <= newsCountOnPage) {
			newsCountOnPage = NEWS_COUNT_ON_PAGE;
		}

		if (totalAmmountNews % newsCountOnPage != 0) {
			totalAmmountPages = totalAmmountNews / newsCountOnPage + 1;
		} else {
			totalAmmountPages = totalAmmountNews / newsCountOnPage;
		}
		
		beginNumber = (newsPage - 1) * newsCountOnPage;

		if ((beginNumber + (newsCountOnPage - 1)) < totalAmmountNews) {
			endNumber = beginNumber + (newsCountOnPage - 1);
		} else {
			endNumber = totalAmmountNews - 1;
		}

		List<News> list = newsList.subList(beginNumber, endNumber + 1);
		
		String newsCountAll = (String) request.getParameter(NEWS_COUNT_ALL);
		if (newsCountAll !=null && (Integer.parseInt(newsCountAll) == NEWS_COUNT_ALL_ACTIVE)) {
			
			session.setAttribute(newsCountAll, NEWS_COUNT_ALL_DEFAULT);
			request.setAttribute(PAGE_NUMBER, NEWS_COUNT_ALL_DEFAULT);
			request.setAttribute(PAGE_NUMBER_VIEW, "");
			request.setAttribute(NEWS_COUNT, newsList.size());
			request.setAttribute(TOTAL_AMMOUNT_OF_PAGES, NEWS_COUNT_ALL_DEFAULT);
			
			request.setAttribute(NEWS, newsList);
		}else {
			request.setAttribute(PAGE_NUMBER, newsPage);
			request.setAttribute(PAGE_NUMBER_VIEW, newsPage);
			request.setAttribute(NEWS_COUNT, newsCountOnPage);
			request.setAttribute(TOTAL_AMMOUNT_OF_PAGES, totalAmmountPages);
			request.setAttribute(NEWS, list);
		}
		
		request.setAttribute(PRESENTATION, NEWS_LIST);
		request.getRequestDispatcher(BASE_LAYOUT_JSP).forward(request, response);
	}
	
}
