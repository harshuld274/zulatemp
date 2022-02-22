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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {

		case "/incQuan":
			System.out.println("action");
			incDecItem(request, response, true);
			break;
		case "/decQuan":
			incDecItem(request, response, false);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		switch (action) {
		case "/addToCart":
			addToCart(request, response);
			break;
		case "/deleteFromCart":
			deleteFromCart(request, response);
			break;
		}

	}

	private void incDecItem(HttpServletRequest request, HttpServletResponse response, boolean isIncrease)
			throws IOException {
		helper(request, response, isIncrease);
	}

	private void helper(HttpServletRequest request, HttpServletResponse response, boolean isIncrease)
			throws IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		int id = Integer.parseInt(request.getParameter("id"));

		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		FoodItemBean food = foodDAO.selectFoodById(id);

		String name = food.getName();
		float price = food.getPrice();

		CartDAO cartDAO = new CartDAO(ConnectionProvider.getConnection());
		TempCartBean cart = cartDAO.selectCart(name, email);

		int bit = 1;
		if (!isIncrease)
			bit = -1;

		int newQuantity = cart.getQuantity() + 1 * bit;
		float newTotal = cart.getTotal() + price * bit;

		cart.setQuantity(newQuantity);
		cart.setTotal(newTotal);

		updateCart(request, response, cart);
	}

	private void deleteFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");

		int id = Integer.parseInt(request.getParameter("id"));

		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		FoodItemBean food = foodDAO.selectFoodById(id);

		String name = food.getName();
		CartDAO cartDAO = new CartDAO(ConnectionProvider.getConnection());

		if (cartDAO.deleteCart(name, email)) {
			System.out.println("Cart Deleted");
			session.setAttribute("cart-msg", "Food deleted from cart");
			response.sendRedirect("my_cart.jsp");
		} else {
			System.out.println("Error Occured in server 1");
			response.sendRedirect("my_cart.jsp");
		}
	}

	public void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				session.setAttribute("cart-msg", "Food item added to cart");
				System.out.println("Cart Inserted");
				response.sendRedirect("my_cart.jsp");
			} else {
				System.out.println("Error Occured in server 1");
				response.sendRedirect("my_cart.jsp");
			}
		} else {
			int newQuantity = cart.getQuantity() + quantity;
			float newTotal = cart.getTotal() + total;

			cart.setQuantity(newQuantity);
			cart.setTotal(newTotal);

			updateCart(request, response, cart);
		}
	}

	public void updateCart(HttpServletRequest request, HttpServletResponse response, TempCartBean cart)
			throws IOException {
		HttpSession session = request.getSession();
		CartDAO cartDAO = new CartDAO(ConnectionProvider.getConnection());

		if (!cart.isQuantityAvailable()) {
			session.setAttribute("quantity-exceed-fail", "Requested Quantity not available");
			response.sendRedirect("my_cart.jsp");
		} else if (cart.getQuantity() == 0) {
			deleteFromCart(request, response);
		} else if (cartDAO.updateCart(cart)) {
			System.out.println("Cart Updated");
			session.setAttribute("cart-msg", "Food item updated");
			response.sendRedirect("my_cart.jsp");
		} else {
			System.out.println("Error Occured in server");
			response.sendRedirect("my_cart.jsp");
		}
	}

}
