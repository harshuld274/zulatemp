package com.phantombeast.fooddelivery.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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
		String action = request.getServletPath();
		switch (action) {

		case "/signup":
			signup(request, response);
			break;
		case "/editProfile":
			editProfile(request, response);
			break;
		case "/updatePassword":
			updatePassword(request, response);
			break;
		default:
			response.sendRedirect("welcome.jsp");
			break;
		}
	}

	private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("password");
		String newPassword2 = request.getParameter("password2");
		SignupBean sb = new SignupBean("", email, newPassword, "", "", "");

		UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());
		String error = SignupBean.isPasswordUpdateValid(email, oldPassword, newPassword, newPassword2);

		HttpSession session = request.getSession();

		if (error == null) {
			if (userDAO.updatePassword(sb)) {
				session.setAttribute("pass-succ", "Successfully changed password");
				response.sendRedirect("welcome.jsp");
			} else {
				session.setAttribute("pass-fail", "Error on server");
				response.sendRedirect("update_password.jsp");
			}
		}

		else {
			session.setAttribute("pass-fail", error);
			response.sendRedirect("update_password.jsp");
		}
		;
	}

	private void editProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");

		SignupBean sb = new SignupBean(name, email, "", "", address, mobile);
		UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());
		String error = sb.isUpdateValid();
		HttpSession session = request.getSession();

		if (error == null) {
			if (userDAO.updateUser(sb)) {
				session.setAttribute("edit-succ", "Successfully saved changes");
				response.sendRedirect("home.jsp");
			} else {
				session.setAttribute("edit-fail", "Error on server");
				response.sendRedirect("edit_profile.jsp");
			}
		}

		else {
			session.setAttribute("edit-fail", error);
			response.sendRedirect("edit_profile.jsp");
		}
	}

	public void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");

		SignupBean sb = new SignupBean(name, email, password, password2, address, mobile);

		UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());
		String error = sb.isSignupValid();
		HttpSession session = request.getSession();
		System.out.println(sb.toString());
		System.out.println(error + "error");

		if (error == null) {
			if (userDAO.insertUser(sb)) {
				session.setAttribute("email", email);
				session.setAttribute("signup-succ", "Succesfully registered");
				response.sendRedirect("welcome.jsp");
			} else {
				session.setAttribute("signup-fail", "Error on server");
				response.sendRedirect("signup.jsp");
			}
		}

		else {
			session.setAttribute("signup-fail", error);
			response.sendRedirect("signup.jsp");
		}
	}
}
