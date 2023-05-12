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

public class GoToViewNews implements Command {

	private static final String BASE_LAYOUT_JSP = "WEB-INF/pages/layouts/baseLayout.jsp";
	private static final String ID = "id";
	private static final String VIEW_NEWS = "viewNews";
	private static final String PRESENTATION = "presentation";
	private static final String NEWS = "news";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String ERROR_MESSAGE = "errorMessage";
	
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		News news;

		int newsId = Integer.parseInt(request.getParameter(ID));
		try {
			
			news = newsService.findById(newsId);
			request.setAttribute(NEWS, news);
			request.setAttribute(PRESENTATION, VIEW_NEWS);

			request.getRequestDispatcher(BASE_LAYOUT_JSP).forward(request, response);
		} catch (ServiceException e) {
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		}

	}

}

