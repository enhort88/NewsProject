package by.htp.ex.controller.impl;

import java.io.IOException;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.util.validation.impl.SessionValidator;
import by.htp.ex.util.validation.impl.SessionValidator.SessionValidationBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToBasePage implements Command {

	private static final int COUNT_OF_VISIBLE_NEWS = 5;
	private static final String ERROR_JSP = "/WEB-INF/pages/tiles/error.jsp";
	private static final String ERROR_MESS = "Problems with presentation of News list";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String COMMAND_GO_TO_NEWS_LIST = "controller?command=go_to_news_list";
	private static final String NEWS = "news";
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		List<News> latestNews;

		try {
			latestNews = newsService.latestList(COUNT_OF_VISIBLE_NEWS);
			request.setAttribute(NEWS, latestNews);
			
			response.sendRedirect(COMMAND_GO_TO_NEWS_LIST);
//			request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
		} catch (ServiceException e) {
			session.setAttribute(ERROR_MESSAGE, ERROR_MESS);
			request.getRequestDispatcher(ERROR_JSP).forward(request, response);
		}

	}

}
