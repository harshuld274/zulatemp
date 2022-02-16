package com.phantombeast.fooddelivery.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.phantombeast.fooddelivery.bean.LoginBean;
import com.phantombeast.fooddelivery.dao.ConnectionProvider;
import com.phantombeast.fooddelivery.dao.UserDAO;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static String adminEmail = "admin@gmail.com";
	private static String adminPassword = "admin";

	public LoginServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();

		if (email.equals(adminEmail) && password.equals(adminPassword)) {
			session.setAttribute("email", "admin");
			System.out.println("home");
			response.sendRedirect("admin/home.jsp");
		} else {
			LoginBean lb = new LoginBean(email, password);
			UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());

			if (userDAO.validateLogin(lb)) {
				session.setAttribute("email", email);

				System.out.println("User Logged in!!!");
				response.sendRedirect("home.jsp");
			} else {
				session.setAttribute("login-fail", "Incorrect Email or Password");

				System.out.println("Log in failed!!!");
				response.sendRedirect("login.jsp");
			}
		}
	}

}
