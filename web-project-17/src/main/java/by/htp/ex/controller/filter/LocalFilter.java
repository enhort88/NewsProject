package by.htp.ex.controller.filter;

import java.io.IOException;
import java.util.Enumeration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LocalFilter implements Filter {

	private static final String LAST_REQ = "lastReq";
	private static final String GET = "GET";
	private ServletContext context;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		String result = "";
		if (req.getMethod().equals(GET)) {
			Enumeration<String> params = req.getParameterNames();

			while (params.hasMoreElements()) {
				String name = params.nextElement();
				String value = request.getParameter(name);
				String lastReq = name + "=" + value;
				
				result = result + lastReq + "&";
				
			}
			result = result.substring(0, (result.length() - 1));
			session.setAttribute(LAST_REQ, result);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {

		context = fConfig.getServletContext();
	}
}
