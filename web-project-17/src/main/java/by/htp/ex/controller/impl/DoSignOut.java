package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import by.htp.ex.util.validation.impl.SessionValidator;
import by.htp.ex.util.validation.impl.SessionValidator.SessionValidationBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoSignOut implements Command {

	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String USER = "user";
	private static final String NOT_ACTIVE = "not active";
	private static final String AUTHENTICATION_MESSAGE = "AuthenticationMessage";
	private static final String USER_INFO_MESSAGE = "user_info_message";
	private static final String COMMAND_GO_TO_BASE_PAGE = "controller?command=go_to_base_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		SessionValidationBuilder builder = new SessionValidator.SessionValidationBuilder();
		SessionValidator validator = builder.checkAuthorization(session).checkAll();

		if (!validator.getErrors().isEmpty()) {
			request.getSession().setAttribute(ERROR_MESSAGE, validator.errorMessage());
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		} else {
			request.getSession(true).setAttribute(USER, NOT_ACTIVE);
			session.setAttribute(AUTHENTICATION_MESSAGE, null);
			request.setAttribute(USER_INFO_MESSAGE, null);
			response.sendRedirect(COMMAND_GO_TO_BASE_PAGE);
		}
	}

}
