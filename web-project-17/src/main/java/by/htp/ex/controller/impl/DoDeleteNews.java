package by.htp.ex.controller.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

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

public class DoDeleteNews implements Command {
	private static final String ID_NEWS = "idNews";
	private static final String NEWS_DELATED_SUCCES = "local.globalMessage.deleted";
	private static final String GLOBAL_MESSAGE = "globalMessage";
	private static final String COMMAND_GO_TO_NEWS_LIST = "controller?command=go_to_news_list";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String ERRIR_MESS = "Empty checkbox for deleting news";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String ERROR_JSP = "WEB-INF/pages/tiles/error.jsp";
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		SessionValidationBuilder builder = new SessionValidator.SessionValidationBuilder();
		SessionValidator validator = builder.checkAccess(session).checkAll();
		String[] idNews;
		
		if (!validator.getErrors().isEmpty()) {
			session.setAttribute(ERROR_MESSAGE, validator.errorMessage());
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		} else {
			String[] reqIdNews= (String[]) request.getParameterValues(ID_NEWS);
				
			if ( reqIdNews!= null && reqIdNews.length>0) {
				idNews = ((String[]) request.getParameterValues(ID_NEWS));
				int[] arr = Stream.of(idNews).mapToInt(Integer::parseInt).toArray();
				try {
					newsService.deleteNews(arr);
					session.setAttribute(GLOBAL_MESSAGE, NEWS_DELATED_SUCCES);
					response.sendRedirect(COMMAND_GO_TO_NEWS_LIST);
				} catch (ServiceException e) {
					response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
				}
			} else {
				session.setAttribute(ERROR_MESSAGE, ERRIR_MESS);
				request.getRequestDispatcher(ERROR_JSP).forward(request, response);
	//			response.sendRedirect("controller?command=go_to_error_page");
			}
		}
	}
}
