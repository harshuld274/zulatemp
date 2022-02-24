package com.phantombeast.fooddelivery.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.phantombeast.fooddelivery.bean.CartBean;
import com.phantombeast.fooddelivery.bean.OrderBean;
import com.phantombeast.fooddelivery.dao.CartDAO;
import com.phantombeast.fooddelivery.dao.ConnectionProvider;
import com.phantombeast.fooddelivery.dao.OrderDAO;

public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OrderServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");

		CartBean cb = (CartBean) session.getAttribute("cart");
		float amount = Float.valueOf((String) request.getParameter("amount")).floatValue();
		String address = (String) request.getParameter("address");
		String mobile = (String) request.getParameter("mobile");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		String mode = (String) request.getParameter("mode");
		String status = "PENDING";

		OrderBean ob = new OrderBean(email, cb, amount, address, mobile, time, OrderBean.toMode(mode),
				OrderBean.toStatus(status));

		OrderDAO orderDAO = new OrderDAO(ConnectionProvider.getConnection());
		CartDAO cartDAO = new CartDAO(ConnectionProvider.getConnection());

		if (orderDAO.placeOrder(ob)) {
			if (cartDAO.emptyCart(email)) {
				System.out.println("Order successful");
				session.setAttribute("order-success", "Order placed Successfully");
				response.sendRedirect("home.jsp");
			} else {
				session.setAttribute("order-fail", "Order failed");
				System.out.println("Error in server side");
				response.sendRedirect("my_cart.jsp");
			}
		} else {
			session.setAttribute("order-success", "Order failed");
			System.out.println("Error in server side");
			response.sendRedirect("my_cart.jsp");
		}
	}

}
