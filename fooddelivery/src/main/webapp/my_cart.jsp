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
	String fail2 = (String) session.getAttribute("quantity-exceed-fail");
	System.out.println(fail2 + " fail2 - home");
	if (fail2 != null) {
	%>
	<p><%=fail2%></p>
	<%
	session.removeAttribute("quantity-exceed-fail");
	}
	%>
	<%
	String cartMsg = (String) session.getAttribute("cart-msg");
	if (cartMsg != null) {
	%>
	<p><%=cartMsg%></p>
	<%
	session.removeAttribute("cart-msg");
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
					<th scope="col">Sub total</th>
					<th scope="col">Actions</th>
				</tr>
			</thead>
			<tbody>
				<%
				CartDAO cartDAO = new CartDAO(ConnectionProvider.getConnection());
				List<TempCartBean> items = cartDAO.selectCartByUser(user);
				int sno = 1;
				float total = 0;
				for (TempCartBean tb : items) {
					total += tb.getTotal();
				%>
				<form method="post" action="deleteFromCart">
					<div>
						<% int id = tb.getIdFromName(tb.getFood()); %>
						<input type="hidden" value=<%=id%> name="id">
						<tr class="align-middle">
							<th scope="row"><%=sno++%></th>
							<td><%=tb.getFood()%></td>
							<td><%=tb.getPrice()%></td>
							<td><a href="incQuan?id=<%= id %>" class="btn"> <i
									class="fa fa-plus"></i>
							</a> <%=tb.getQuantity()%><a href="decQuan?id=<%= id %>" class="btn"><i
									class="fa fa-minus"></i> </a></td>
							<td><%=tb.getTotal()%></td>
							<!-- 							<td><input type="number" name="quantity" -->
							<!-- 								placeholder="Quantity" min="1" required></td> -->
							<td>
								<button type="submit" class="btn btn-primary">Remove</button>
							</td>
						</tr>
					</div>
				</form>
				<%
				}
				%>
			</tbody>
		</table>
		<p>
			Total
			<%=total%></p>

	</div>
</body>
</html>