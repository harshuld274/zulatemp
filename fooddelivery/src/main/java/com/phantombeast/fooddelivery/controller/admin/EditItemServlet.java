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

public class EditItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public EditItemServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fid= request.getParameter("id");
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
				response.sendRedirect("edit_item.jsp?id="+fid);
			}
		} else {
			session.setAttribute("edit-item-fail", error);
			response.sendRedirect("edit_item.jsp?id="+fid);
		}
	}

}
