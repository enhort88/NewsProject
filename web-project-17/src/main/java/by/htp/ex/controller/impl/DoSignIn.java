package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.bean.User;
import by.htp.ex.controller.Command;
import by.htp.ex.service.IUserService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.util.validation.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoSignIn implements Command {

	private static final String ERROR_VALIDATION_MESSAGE = "errorValidationMessage";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String ERROR_MESS = "Error while \"Sign in\", sorry try later";
	private static final String LOGIN_ERROR = "local.header.loginerror";
	private static final String AUTHENTICATION_MESSAGE = "AuthenticationMessage";
	private static final String NOT_ACTIVE = "not active";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String COMMAND_GO_TO_NEWS_LIST = "controller?command=go_to_news_list";
	private static final String USER_ID = "userId";
	private static final String ROLE = "role";
	private static final String ACTIVE = "active";
	private static final String USER = "user";
	private static final String GUEST = "guest";
	private final IUserService service = ServiceProvider.getInstance().getUserService();
	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String login;
		String password;
		String role = GUEST;

		login = request.getParameter(JSP_LOGIN_PARAM);
		password = request.getParameter(JSP_PASSWORD_PARAM);

		try {
			role = service.signIn(login, password);

			if (!role.equals(GUEST)) {
				int id = service.getIdByLogin(login);
				request.getSession(true).setAttribute(USER, ACTIVE);
				request.getSession().setAttribute(ROLE, role);
				request.getSession().setAttribute(USER_ID, id);
				response.sendRedirect(COMMAND_GO_TO_NEWS_LIST);
			} else {
				session.setAttribute(USER, NOT_ACTIVE);
				session.setAttribute(AUTHENTICATION_MESSAGE, LOGIN_ERROR);
				response.sendRedirect(COMMAND_GO_TO_NEWS_LIST);
			}
		} catch (ServiceException e) {
			session.setAttribute(ERROR_MESSAGE, ERROR_MESS);
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		} catch (ValidationException e) {
			String errors = e.getMessage();
			session.setAttribute(ERROR_VALIDATION_MESSAGE, errors);
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		}
	}
}