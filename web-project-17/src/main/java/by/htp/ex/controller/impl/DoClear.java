package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoClear implements Command{
	private static final String LAST_REQ = "lastReq";
	private static final String CONTROLLER = "controller?";
	private static final String GLOBAL_MESSAGE = "globalMessage";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		session.setAttribute(GLOBAL_MESSAGE, null);
		response.sendRedirect(CONTROLLER + session.getAttribute(LAST_REQ));
	}
}

