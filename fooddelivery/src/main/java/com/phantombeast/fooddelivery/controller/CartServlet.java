package com.phantombeast.fooddelivery.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.phantombeast.fooddelivery.bean.FoodItemBean;
import com.phantombeast.fooddelivery.bean.TempCartBean;
import com.phantombeast.fooddelivery.dao.CartDAO;
import com.phantombeast.fooddelivery.dao.ConnectionProvider;
import com.phantombeast.fooddelivery.dao.FoodItemDAO;

public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CartServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");

		int id = Integer.parseInt(request.getParameter("id"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		FoodItemBean food = foodDAO.selectFoodById(id);

		String name = food.getName();
		float price = food.getPrice();
		float total = price * quantity;

		CartDAO cartDAO = new CartDAO(ConnectionProvider.getConnection());
		TempCartBean cart = cartDAO.selectCart(name, email);

		if (cart == null) {
			TempCartBean tb = new TempCartBean(email, name, quantity, price, total);
			System.out.println(tb.toString());
			if (!tb.isQuantityAvailable()) {
				session.setAttribute("quantity-exceed-fail", "Requested Quantity not available");
				System.out.println("Quantity exceeded error");
				response.sendRedirect("home.jsp");
			} else if (cartDAO.insertIntoCart(tb)) {
				System.out.println("Cart Inserted");
				response.sendRedirect("home.jsp");
			} else {
				System.out.println("Error Occured in server 1");
				response.sendRedirect("home.jsp");
			}
		} else {
			int newQuantity = cart.getQuantity() + quantity;
			float newTotal = cart.getTotal() + total;
			TempCartBean tb = new TempCartBean(email, name, newQuantity, price, newTotal);

			if (!tb.isQuantityAvailable()) {
				session.setAttribute("quantity-exceed-fail", "Requested Quantity not available");
				response.sendRedirect("home.jsp");
			} else if (cartDAO.updateCart(tb)) {
				System.out.println("Cart Updated");
				response.sendRedirect("home.jsp");
			} else {
				System.out.println("Error Occured in server");
				response.sendRedirect("home.jsp");
			}
		}

	}

}
