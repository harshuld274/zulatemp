<%@page import="java.util.Date"%>
<%@page import="com.phantombeast.fooddelivery.dao.ConnectionProvider"%>
<%@page import="com.phantombeast.fooddelivery.dao.OrderDAO"%>
<%@page import="com.phantombeast.fooddelivery.bean.OrderBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search Order</title>
<%@include file="../components/all_css.jsp"%>
</head>
<body>
	<%@include file="navbar_admin.jsp"%>
	<div class="container">
		<table class="table table-striped">
			<thead>
				<tr>
					<th scope="col">S.No</th>
					<th scope="col">Order Id</th>
					<th scope="col">Cart</th>
					<th scope="col">Amount</th>
					<th scope="col">Order Date</th>
					<th scope="col">Actions</th>
				</tr>
			</thead>
			<tbody>
				<%
				OrderDAO orderDAO = new OrderDAO(ConnectionProvider.getConnection());
				String user = request.getParameter("email");
				List<OrderBean> orders = orderDAO.selectOrdersByUser(user);

				int sno = 1;
				float total = 0;
				for (OrderBean order : orders) {
				%>
				<tr class="align-middle">
					<th scope="row"><%=sno++%></th>
					<td><%=order.getId()%></td>
					<td><a href="view_order.jsp?id=<%=order.getId()%>">items</a></td>
					<td><%=order.getAmount()%></td>
					<%Date date = new Date(order.getTime().getTime());%>
					<td><%=date%></td>
					<td><a href="view_order.jsp?id=<%=order.getId()%>"
						class="btn btn-primary">View Order</a></td>
				</tr>
				<%}%>
			</tbody>
		</table>
	</div>
</body>
</html>