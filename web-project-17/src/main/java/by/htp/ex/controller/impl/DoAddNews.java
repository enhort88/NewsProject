package by.htp.ex.controller.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.util.validation.ValidationException;
import by.htp.ex.util.validation.impl.SessionValidator;
import by.htp.ex.util.validation.impl.SessionValidator.SessionValidationBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoAddNews implements Command {
	private static final String USER_ID = "userId";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String NEWS_ADDED_SUCCES = "local.globalMessage.added";
	private static final String GLOBAL_MESSAGE = "globalMessage";
	private static final String COMMAND_GO_TO_NEWS_LIST = "controller?command=go_to_news_list";
	private static final String ERROR_VALIDATION_MESSAGE = "errorValidationMessage";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private final String NEWS_TITLE = "news_title";
	private final String NEWS_BRIEF = "news_brief";
	private final String NEWS_CONTENT = "news_content";
	private final String NEWS_DATE = "news_date";

	private final INewsService service = ServiceProvider.getInstance().getNewsService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		SessionValidationBuilder builder = new SessionValidator.SessionValidationBuilder();
		SessionValidator validator = builder.checkAccess(session).checkAll();
		
		if (!validator.getErrors().isEmpty()) {
			session.setAttribute(ERROR_MESSAGE, validator.errorMessage());
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		} else {
			int userId = (int) session.getAttribute(USER_ID);
			try {
				service.addNews(new News(request.getParameter(NEWS_TITLE), request.getParameter(NEWS_BRIEF),
						request.getParameter(NEWS_CONTENT), request.getParameter(NEWS_DATE), userId));
				session.setAttribute(GLOBAL_MESSAGE, NEWS_ADDED_SUCCES);
				response.sendRedirect(COMMAND_GO_TO_NEWS_LIST);
			} catch (ServiceException e) {
				response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
			} catch (ValidationException e) {
				String errors = e.getMessage();
				session.setAttribute(ERROR_VALIDATION_MESSAGE, errors);
				response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
			}
		}
	}
}
