package by.htp.ex.controller.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class DoRegistration implements Command {

	private static final String REFISTRATION_SUCCES = "local.header.reg";

	private static final String COMMAND_GO_TO_NEWS_LIST = "controller?command=go_to_news_list";

	private static final String ERROR_NOT_UNIQ_USER = "local.header.not_new_user";

	private static final String AUTHENTICATION_MESSAGE = "AuthenticationMessage";

	private static final String COMMAND_GO_TO_REGISTRATION_PAGE = "controller?command=go_to_registration_page";

	private static final String ERROR_VALIDATION_MESSAGE = "errorValidationMessage";

	private static final String ERROR_REGISTRATION_FIELDS = "Different passwords, or password field empty";

	private static final String ERROR_MESSAGE = "errorMessage";

	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";

	private final IUserService service = ServiceProvider.getInstance().getUserService();

	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";
	private static final String JSP_REP_PASSWORD_PARAM = "repeatPassword";
	private static final String JSP_EMAIL_PARAM = "email";
	private static final String JSP_NAME_PARAM = "name";
	private static final String JSP_SURNAME_PARAM = "surname";
	private static final String JSP_DATEBIRTH_PARAM = "birthday";
	private static final String JSP_ADRESS_PARAM = "adress";
	private static final String JSP_TEL_PARAM = "tel";
	private static final String JSP_GENDER_PARAM = "gender";
	private static final SimpleDateFormat FORMATTER_BIRTHDAY = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		String password = request.getParameter(JSP_PASSWORD_PARAM);
		String repPassword = request.getParameter(JSP_REP_PASSWORD_PARAM);
		Date dateBirth = null;
		try {
			dateBirth = FORMATTER_BIRTHDAY.parse(request.getParameter(JSP_DATEBIRTH_PARAM));
		} catch (Exception e2) {
			throw new ServletException("Error! Problems with Date Birth parsing", e2);
		}

		if (password.equals(repPassword) && password != null) {
			User user = new User(request.getParameter(JSP_LOGIN_PARAM), password,
					request.getParameter(JSP_EMAIL_PARAM));
			user.setDateBirth(dateBirth);
			user.setName(request.getParameter(JSP_NAME_PARAM));
			user.setSurname(request.getParameter(JSP_SURNAME_PARAM));
			user.setGender(request.getParameter(JSP_GENDER_PARAM));
			user.setAdress(request.getParameter(JSP_ADRESS_PARAM));
			user.setTelnumber(request.getParameter(JSP_TEL_PARAM));
			try {
				if (service.registration(user)) {
					session.setAttribute(AUTHENTICATION_MESSAGE, REFISTRATION_SUCCES);
					response.sendRedirect(COMMAND_GO_TO_NEWS_LIST);	
				}else {
					session.setAttribute(AUTHENTICATION_MESSAGE, ERROR_NOT_UNIQ_USER);
					response.sendRedirect(COMMAND_GO_TO_REGISTRATION_PAGE);	
				}
			} catch (ServiceException e) {
				response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
			} catch (ValidationException e) {
				String errors = e.getMessage();
				session.setAttribute(ERROR_VALIDATION_MESSAGE, errors);
				response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
			}
		} else {
			session.setAttribute(ERROR_MESSAGE, ERROR_REGISTRATION_FIELDS);
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
		}
	}

}
