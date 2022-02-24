<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="com.phantombeast.fooddelivery.dao.FoodItemDAO, com.phantombeast.fooddelivery.bean.FoodItemBean,com.phantombeast.fooddelivery.dao.ConnectionProvider, java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="../components/all_css.jsp"%>
</head>
<body>
	<%@include file="navbar_admin.jsp"%>
	<div class="container">
		<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">All Food
			Items</p>

		<%
		String editItemSucc = (String) session.getAttribute("edit-item-succ");
		if (editItemSucc != null) {
		%>
		<p class="text-success"><%=editItemSucc%></p>

		<%
		session.removeAttribute("edit-item-succ");
		}
		%>
		
		<%
		String deleteItemSucc = (String) session.getAttribute("delete-item-succ");
		if (deleteItemSucc != null) {
		%>
		<p class="text-success"><%=deleteItemSucc%></p>

		<%
		session.removeAttribute("delete-item-succ");
		}
		%>
		
		<%
		String deleteItemFail= (String) session.getAttribute("delete-item-fail");
		if (deleteItemFail != null) {
		%>
		<p class="text-success"><%=deleteItemFail%></p>

		<%
		session.removeAttribute("delete-item-fail");
		}
		%>

		<table class="table table-striped">
			<thead>
				<tr>
					<th scope="col">S.No</th>
					<th scope="col">Name</th>
					<th scope="col">Price</th>
					<th scope="col">Description</th>
					<th scope="col">Quantity Available</th>
					<th scope="col">Actions</th>
				</tr>
			</thead>
			<tbody>
				<%
				FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
				List<FoodItemBean> foods = foodDAO.selectAllFoods();
				int sno = 1;
				for (FoodItemBean f : foods) {
				%>
				<tr class="align-middle">
					<th scope="row"><%=sno++%></th>
					<td><%=f.getName()%></td>
					<td><%=f.getPrice()%></td>
					<td><%=f.getAbout()%></td>
					<td><%=f.getQuantity()%></td>
					<td><a href="edit_item.jsp?id=<%=f.getId()%>"
						class="btn btn-small btn-success">Edit</a><a
						href="deleteItem?id=<%=f.getId()%>"
						class="btn btn-small btn-danger ms-3">Delete</a></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>