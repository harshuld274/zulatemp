<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="com.phantombeast.fooddelivery.dao.FoodItemDAO, com.phantombeast.fooddelivery.bean.FoodItemBean,com.phantombeast.fooddelivery.dao.ConnectionProvider, java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="components/all_css.jsp"%>
</head>
<body>
	<%@include file="components/navbar.jsp"%>
	<%
	String fail = (String) session.getAttribute("signup-fail2");
	if (fail != null) {
	%>
	<p><%=fail%></p>
	<%
	session.removeAttribute("signup-fail2");
	}
	%>
	<%
	String fail2 = (String) session.getAttribute("quantity-exceed-fail");
	if (fail2 != null) {
	%>
	<p><%=fail2%></p>
	<%
	session.removeAttribute("quantity-exceed-fail");
	}
	%>
	<%
	String orderSuccess = (String) session.getAttribute("order-success");
	if (orderSuccess != null) {
	%>
	<p><%=orderSuccess%></p>
	<%
	session.removeAttribute("order-success");
	}
	%>
	
	<br>
	<div class="container">
		<div class="row">
			<div class="col-md-12 bg-light float-right">
				<form action="searchHome.jsp" method="post">
					<input type="text" name="search" placeholder="Search">
					<button type="submit">Search</button>
				</form>
			</div>
		</div>
	</div>

	<br>
	<div class="container">
		<table class="table table-striped">
			<thead>
				<tr>
					<th scope="col">S.No</th>
					<th scope="col">Name</th>
					<th scope="col">Price</th>
					<th scope="col">Description</th>
					<th scope="col">Quantity Available</th>
					<th scope="col">Order Quantity</th>
					<th scope="col">Add To Cart</th>
				</tr>
			</thead>
			<tbody>
				<%
				FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
				List<FoodItemBean> foods = foodDAO.selectAvailableFoods();
				int sno = 1;
				for (FoodItemBean f : foods) {
				%>
				<form method="post" action="addToCart">
					<div>
						<input type="hidden" value=<%=f.getId()%> name="id">
						<tr class="align-middle">
							<th scope="row"><%=sno++%></th>
							<td><%=f.getName()%></td>
							<td><%=f.getPrice()%></td>
							<td><%=f.getAbout()%></td>
							<td><%=f.getQuantity()%></td>
							<td><input type="number" name="quantity" placeholder="Quantity" min="1" required></td>

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