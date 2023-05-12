package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToErrorPage implements Command {

	private static final String ERROR_JSP = "WEB-INF/pages/tiles/error.jsp";
	private static final String PRESENTATION = "presentation";
	private static final String ERROR_VALIDATION_MESSAGE = "errorValidationMessage";
	private static final String ERROR_MESSAGE = "errorMessage";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		String message;
		String validationMessage;
		
		
		message = (String) request.getSession().getAttribute(ERROR_MESSAGE);
		
		validationMessage = (String) request.getSession().getAttribute(ERROR_VALIDATION_MESSAGE);
		
		if (message != null) {
			session.setAttribute(ERROR_MESSAGE, message);
		}
		if (validationMessage != null) {
			session.setAttribute(ERROR_VALIDATION_MESSAGE, validationMessage);
		}
		request.setAttribute(PRESENTATION, ERROR_MESSAGE);
		request.getRequestDispatcher(ERROR_JSP).forward(request, response);
	}
}
