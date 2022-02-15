package com.phantombeast.fooddelivery.controller;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.phantombeast.fooddelivery.bean.SignupBean;
import com.phantombeast.fooddelivery.dao.ConnectionProvider;
import com.phantombeast.fooddelivery.dao.UserDAO;

public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SignupServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");

		SignupBean sb = new SignupBean(name, email, password, password2, address, mobile);

		UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());
		String error = sb.isValid();
		HttpSession session = request.getSession();
		System.out.println(sb.toString());
		System.out.println(error + "error");

		if (error == null) {
			if (userDAO.insertUser(sb)) {
				session.setAttribute("email", email);
				response.sendRedirect("home.jsp");
			} else {
				session.setAttribute("signup-fail", "Error on server");
				response.sendRedirect("signup.jsp");
			}
		}

		else {
			System.out.println("here");
			session.setAttribute("signup-fail", error);
			response.sendRedirect("signup.jsp");
		}

	}
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
//		rd.forward(request, response);
//	}
}
