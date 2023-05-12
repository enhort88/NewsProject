package by.htp.ex.controller.impl;

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

import java.io.IOException;

public class GoToEditNews implements Command {

	private static final String ERROR_MESS = "Problems with parsing ID news";
	private static final String WEB_INF_PAGES_LAYOUTS_BASE_LAYOUT_JSP = "WEB-INF/pages/layouts/baseLayout.jsp";
	private static final String EDIT_NEWS = "editNews";
	private static final String PRESENTATION = "presentation";
	private static final String NEWS = "news";
	private static final String ID = "id";
	private static final String NEWS_ID = "news_id";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String ERROR_MESSAGE = "errorMessage";
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		SessionValidationBuilder builder = new SessionValidator.SessionValidationBuilder();
		SessionValidator validator = builder.checkAccess(session).checkAll();
		
		if (!validator.getErrors().isEmpty()) {
			session.setAttribute(ERROR_MESSAGE, validator.errorMessage());
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		} else {
			
			News news;

			try {
				int newsId = Integer.parseInt(request.getParameter(ID));
				session.setAttribute(NEWS_ID, newsId);
				news = newsService.findById(newsId);
				request.setAttribute(NEWS, news);
				request.setAttribute(PRESENTATION, EDIT_NEWS);
				request.getRequestDispatcher(WEB_INF_PAGES_LAYOUTS_BASE_LAYOUT_JSP).forward(request, response);
			} catch (ServiceException e) {
				session.setAttribute(ERROR_MESSAGE, ERROR_MESS);
				response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
			}

		}
	}
}