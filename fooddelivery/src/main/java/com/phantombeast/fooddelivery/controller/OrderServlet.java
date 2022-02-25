package com.phantombeast.fooddelivery.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.phantombeast.fooddelivery.bean.CartBean;
import com.phantombeast.fooddelivery.bean.OrderBean;
import com.phantombeast.fooddelivery.bean.OrderBean.Status;
import com.phantombeast.fooddelivery.dao.CartDAO;
import com.phantombeast.fooddelivery.dao.ConnectionProvider;
import com.phantombeast.fooddelivery.dao.FoodItemDAO;
import com.phantombeast.fooddelivery.dao.OrderDAO;

public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OrderServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {

		case "/placeOrder":
			placeOrder(request, response);
			break;
		case "/cancelOrder":
			cancelOrder(request, response);
			break;
		default:
			response.sendRedirect("welcome.jsp");
			break;
		}

	}

	private void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));

		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		OrderDAO orderDAO = new OrderDAO(ConnectionProvider.getConnection());
		OrderBean ob = orderDAO.selectOrderById(id);
		ob.setStatus(Status.valueOf("CANCELLED"));

		if (orderDAO.settleOrderById(id, "CANCELLED")) {
			if (foodDAO.updateQuantities(ob)) {
				System.out.println("Order cancelled");
				session.setAttribute("cancel-msg", "Order cancelled Successfully");
				response.sendRedirect("my_orders.jsp");
			} else {
				session.setAttribute("cancel-msg", "Cancel failed");
				System.out.println("Error in server side");
				response.sendRedirect("my_cart.jsp");
			}
		} else {
			session.setAttribute("cancel-msg", "Cancel failed");
			System.out.println("Error in server side");
			response.sendRedirect("my_cart.jsp");
		}

	}

	public void placeOrder(HttpServletRequest request, HttpServletResponse response)
			throws JsonMappingException, JsonProcessingException, IOException {
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
		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());

		if (orderDAO.placeOrder(ob)) {
			if (cartDAO.emptyCart(email) && foodDAO.updateQuantities(ob)) {
				System.out.println("Order successful");
				session.setAttribute("order-success", "Order placed Successfully");
				response.sendRedirect("home.jsp");
			} else {
				session.setAttribute("order-fail", "Order failed");
				System.out.println("Error in server side");
				response.sendRedirect("my_cart.jsp");
			}
		} else {
			session.setAttribute("order-fail", "Order failed");
			System.out.println("Error in server side");
			response.sendRedirect("my_cart.jsp");
		}
	}

}
