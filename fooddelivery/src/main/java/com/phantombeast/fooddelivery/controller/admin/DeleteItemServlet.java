package com.phantombeast.fooddelivery.controller.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.phantombeast.fooddelivery.dao.ConnectionProvider;
import com.phantombeast.fooddelivery.dao.FoodItemDAO;

public class DeleteItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteItemServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int fid = Integer.parseInt(request.getParameter("id"));
		System.out.println("here");
		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		HttpSession session = request.getSession();

		if (foodDAO.deleteFood(fid)) {
			session.setAttribute("delete-item-succ", "Item successfully deleted");
		} else {
			session.setAttribute("delete-item-fail", "Sorry.. Can't delete item");
		}
		response.sendRedirect("all_items.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
