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
<title>My Orders</title>
<%@include file="components/all_css.jsp"%>
</head>
<body>
	<%@include file="components/navbar.jsp"%>
	<div class="container">
		<%
		String cancelMsg = (String) session.getAttribute("cancel-msg");
		if (cancelMsg != null) {
		%>
		<p><%=cancelMsg%></p>
		<%
		session.removeAttribute("cancel-msg");
		}
		%>
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
				System.out.print(user);
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
					<%
					Date date = new Date(order.getTime().getTime());
					%>
					<td><%=date%></td>
					<td><a href="view_order.jsp?id=<%=order.getId()%>"
						class="btn btn-primary">View Order</a> <%
 if (order.getStatus().toString().equals("PENDING")) {
 %> <a href="cancelOrder?id=<%=order.getId()%>" class="btn btn-danger">Cancel
							Order</a> <%} else {%> <a href="#" class="btn btn-secondary">Cancel
							Order</a></td>
					<%}%>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>