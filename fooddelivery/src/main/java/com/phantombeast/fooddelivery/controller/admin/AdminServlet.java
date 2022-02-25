package com.phantombeast.fooddelivery.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.phantombeast.fooddelivery.bean.FoodItemBean;
import com.phantombeast.fooddelivery.dao.CartDAO;
import com.phantombeast.fooddelivery.dao.ConnectionProvider;
import com.phantombeast.fooddelivery.dao.FoodItemDAO;
import com.phantombeast.fooddelivery.dao.OrderDAO;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action + " action");
		switch (action) {
		case "/admin/addItem":
			addItem(request, response);
			break;
		case "/admin/editItem":
			editItem(request, response);
			break;
		case "/admin/deleteItem":
			deleteItem(request, response);
			break;
		case "/admin/searchOrders":
			searchOrders(request, response);
			break;
		case "/admin/settleOrder":
			settleOrder(request, response);
			break;
		default:
			homePage(request, response);
			break;
		}
	}

	private void searchOrders(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String user = request.getParameter("email");
		System.out.println(user);
		response.sendRedirect("search_orders.jsp?email=" + user);
	}

	private void settleOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");
		OrderDAO orderDAO = new OrderDAO(ConnectionProvider.getConnection());

		HttpSession session = request.getSession();

		if (orderDAO.settleOrderById(id, status)) {
			session.setAttribute("settle-order", "Order successfully settled");
		} else {
			session.setAttribute("settle-order", "Sorry.. Can't settle order");
		}
		response.sendRedirect("all_orders.jsp");
	}

	private void homePage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.sendRedirect("home.jsp");
	}

	private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int fid = Integer.parseInt(request.getParameter("id"));
		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		FoodItemBean fb = foodDAO.selectFoodById(fid);
		String name = fb.getName();
		CartDAO cartDAO = new CartDAO(ConnectionProvider.getConnection());

		HttpSession session = request.getSession();

		if (foodDAO.deleteFood(fid)) {
			if (cartDAO.removeCart(name)) {
				session.setAttribute("delete-item-succ", "Item successfully deleted");
			} else {
				System.out.println("Carts weren't removed. Nobody add this item in cart");
			}
		} else {
			session.setAttribute("delete-item-fail", "Sorry.. Can't delete item");
		}
		response.sendRedirect("all_items.jsp");
	}

	private void editItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fid = request.getParameter("id");
		String fname = request.getParameter("name");
		String fprice = request.getParameter("price");
		String fabout = request.getParameter("about");
		String fquantity = request.getParameter("quantity");

		FoodItemBean fb = new FoodItemBean(fname, Float.valueOf(fprice).floatValue(), fabout,
				Integer.parseInt(fquantity));
		fb.setId(Integer.parseInt(fid));

		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		HttpSession session = request.getSession();
		String error = fb.updateCheck();

		System.out.println(fb.toString());
		System.out.println(error + " error");

		if (error == null) {
			if (foodDAO.updateFood(fb)) {
				session.setAttribute("edit-item-succ", "Item successfully update");
				response.sendRedirect("all_items.jsp");
			} else {
				session.setAttribute("edit-item-fail", "Sorry.. Can't update item");
				response.sendRedirect("edit_item.jsp?id=" + fid);
			}
		} else {
			session.setAttribute("edit-item-fail", error);
			response.sendRedirect("edit_item.jsp?id=" + fid);
		}
	}

	private void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fname = request.getParameter("name");
		String fprice = request.getParameter("price");
		String fabout = request.getParameter("about");
		String fquantity = request.getParameter("quantity");

		FoodItemBean fb = new FoodItemBean(fname, Float.valueOf(fprice).floatValue(), fabout,
				Integer.parseInt(fquantity));

		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		HttpSession session = request.getSession();
		String error = fb.isValid();

		if (error == null) {
			if (foodDAO.insertFood(fb)) {
				session.setAttribute("add-item-succ", "Item successfully added");
			} else {
				session.setAttribute("add-item-fail", "Sorry.. Can't add item");
			}
		} else {
			session.setAttribute("add-item-fail", error);
		}
		response.sendRedirect("add_item.jsp");
	}

}
