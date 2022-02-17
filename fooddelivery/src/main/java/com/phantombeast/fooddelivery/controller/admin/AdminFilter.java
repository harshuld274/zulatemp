package com.phantombeast.fooddelivery.controller.admin;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	public AdminFilter() {
		super();
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
//		System.out.println("In filter");
		
		String user = (String) session.getAttribute("email");
		if (user != null && user.equals("admin")) {
			chain.doFilter(request, response);
		} else {
//			System.out.println("In filter redirect");
			((HttpServletResponse) response).sendRedirect("../login.jsp");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
