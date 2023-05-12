package by.htp.ex.controller.impl;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoLocale implements Command {

	private static final String GO_TO_NEWS_LIST = "command=go_to_news_list";
	private static final String CONTROLLER = "controller?";
	private static final String LOCAL = "local";
	private static final String LAST_REQ = "lastReq";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String adress;
		String last = (String) session.getAttribute(LAST_REQ);

		adress = CONTROLLER + last;

		request.getSession(false).setAttribute(LOCAL, request.getParameter(LOCAL));
		response.sendRedirect(adress);

//      request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
	}
}
