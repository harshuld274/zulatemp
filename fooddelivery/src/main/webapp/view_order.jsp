<%@page import="java.util.Date"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.phantombeast.fooddelivery.bean.CartBean"%>
<%@page import="com.phantombeast.fooddelivery.bean.OrderBean"%>
<%@page import="java.util.List"%>
<%@page import="com.phantombeast.fooddelivery.dao.ConnectionProvider"%>
<%@page import="com.phantombeast.fooddelivery.dao.OrderDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Order</title>
<%@include file="components/all_css.jsp"%>
</head>
<body>
	<%@include file="components/navbar.jsp"%>


	<div class="container">
		<%
		int orderId = Integer.parseInt(request.getParameter("id"));
		int sno = 1;

		OrderDAO orderDAO = new OrderDAO(ConnectionProvider.getConnection());
		OrderBean order = orderDAO.selectOrderById(orderId);
		CartBean cb = order.getCart();
		%>
		<table class="table table-striped">
			<thead>
				<tr>
					<th scope="col">S.No</th>
					<th scope="col">Food</th>
					<th scope="col">Quantity</th>
				</tr>
			</thead>
			<tbody>
				<%
				for (Entry<String, Integer> entry : cb.getQuantities().entrySet()) {
				%>
				<tr class="align-middle">
					<th scope="row"><%=sno++%></th>
					<td><%=entry.getKey()%></td>
					<td><%=entry.getValue()%></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>

		<p>
			Address -
			<%=order.getAddress()%>
		</p>
		<%
		Date date = new Date(order.getTime().getTime());
		%>
		<p>
			Order Time -
			<%=date%>
		</p>
		<h3>
			Amount -
			<%=order.getAmount()%>
		</h3>
		<h3>
			Status -
			<%=order.getStatus()%>
		</h3>

	</div>
</body>
</html>