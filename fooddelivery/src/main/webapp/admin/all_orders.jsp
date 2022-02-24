<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.phantombeast.fooddelivery.dao.ConnectionProvider"%>
<%@page import="com.phantombeast.fooddelivery.bean.OrderBean"%>
<%@page import="com.phantombeast.fooddelivery.dao.OrderDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Orders</title>
<%@include file="../components/all_css.jsp"%>
</head>
<body>
	<%@include file="navbar_admin.jsp"%>
	<%
	String settleOrder = (String) session.getAttribute("settle-order");
	if (settleOrder != null) {
	%>
	<p class="text-success"><%=settleOrder%></p>

	<%
	session.removeAttribute("settle-order");
	}
	%>
	<br>
	<div class="container">
		<div class="row">
			<div class="col-md-12 bg-light float-right">
				<form action="searchOrders" method="post">
					<input type="text" name="email" placeholder="Search by Email">
					<button type="submit">Search</button>
				</form>
			</div>
		</div>

		<br>
		<table class="table table-striped">
			<thead>
				<tr>
					<th scope="col">S.No</th>
					<th scope="col">Order Id</th>
					<th scope="col">Cart</th>
					<th scope="col">Amount</th>
					<th scope="col">Order Date</th>
					<th scope="col">Status</th>
					<th scope="col">Actions</th>
				</tr>
			</thead>
			<tbody>
				<%
				OrderDAO orderDAO = new OrderDAO(ConnectionProvider.getConnection());
				List<OrderBean> orders = orderDAO.selectAllOrders();

				int sno = 1;
				float total = 0;
				for (OrderBean order : orders) {
				%>
				<tr class="align-middle">
					<th scope="row"><%=sno++%></th>
					<td><%=order.getId()%></td>
					<td><a href="view_order.jsp?id=<%=order.getId()%>">items</a></td>
					<td><%=order.getAmount()%></td>
					<%
					Date date = new Date(order.getTime().getTime());
					%>
					<td><%=date%></td>
					<td><%=order.getStatus()%></td>
					<td><a href="view_order.jsp?id=<%=order.getId()%>"
						class="btn btn-primary">View Order</a></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>