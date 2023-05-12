package by.htp.ex.controller.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

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

public class GoToAddNews implements Command {
	private static final String BASE_LAYOUT_JSP = "WEB-INF/pages/layouts/baseLayout.jsp";
	private static final String PRESENTATION = "presentation";
	private static final String ADD_NEWS = "addNews";
	private static final String COMMAND_GO_TO_ERROR_PAGE = "controller?command=go_to_error_page";
	private static final String ERROR_MESSAGE = "errorMessage";
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		HttpSession session = request.getSession();
		
		SessionValidationBuilder builder = new SessionValidator.SessionValidationBuilder();
		SessionValidator validator = builder.checkAccess(session).checkAll();
		
				
		if (!validator.getErrors().isEmpty()) {
			session.setAttribute(ERROR_MESSAGE, validator.errorMessage());
//			request.getRequestDispatcher("WEB-INF/pages/layouts/error.jsp").forward(request, response);
			response.sendRedirect(COMMAND_GO_TO_ERROR_PAGE);
			
		} else {
			request.setAttribute(PRESENTATION,ADD_NEWS);
			request.getRequestDispatcher(BASE_LAYOUT_JSP).forward(request, response);
		} 

}
}

