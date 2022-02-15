package com.phantombeast.fooddelivery.controller.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.phantombeast.fooddelivery.bean.FoodItemBean;
import com.phantombeast.fooddelivery.dao.ConnectionProvider;
import com.phantombeast.fooddelivery.dao.FoodItemDAO;

public class AddItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddItemServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fname = request.getParameter("name");
		String fprice = request.getParameter("price");
		String fabout = request.getParameter("about");
		String fquantity = request.getParameter("quantity");

		FoodItemBean fb = new FoodItemBean(fname, Float.valueOf(fprice).floatValue(), fabout,
				Integer.parseInt(fquantity));
		
		FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
		HttpSession session = request.getSession();
		String error = fb.isValid();
		
//		System.out.println(fb.toString());
//		System.out.println(error + " error");
		
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
