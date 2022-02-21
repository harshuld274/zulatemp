<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="com.phantombeast.fooddelivery.dao.CartDAO, com.phantombeast.fooddelivery.bean.TempCartBean, com.phantombeast.fooddelivery.dao.ConnectionProvider, java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Cart</title>
<%@include file="components/all_css.jsp"%>
</head>
<body>
	<%@include file="components/navbar.jsp"%>
	<%
	if (user == null) {
		session.setAttribute("login-fail", "Login to access");
		response.sendRedirect("login.jsp");
	}
	%>
	<div class="container">
		<table class="table table-striped">
			<thead>
				<tr>
					<th scope="col">S.No</th>
					<th scope="col">Name</th>
					<th scope="col">Price</th>
					<th scope="col">Order Quantity</th>
					<th scope="col">Add To Cart</th>
				</tr>
			</thead>
			<tbody>
				<%
				CartDAO cartDAO = new CartDAO(ConnectionProvider.getConnection());
				List<TempCartBean> items = cartDAO.selectCartByUser(user);
				int sno = 1;
				for (TempCartBean tb : items) {
				%>
				<form method="post" action="addToCart">
					<div>
						<input type="hidden" value=<%=tb.getIdFromName(tb.getFood())%>
							name="id">
						<tr class="align-middle">
							<th scope="row"><%=sno++%></th>
							<td><%=tb.getFood()%></td>
							<td><%=tb.getPrice()%></td>
							<td><%=tb.getQuantity()%></td>
							<td><input type="number" name="quantity"
								placeholder="Quantity" min="1" required></td>
							<td>
								<button type="submit" class="btn btn-primary">Add To
									Cart</button>
							</td>
						</tr>
					</div>
				</form>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>