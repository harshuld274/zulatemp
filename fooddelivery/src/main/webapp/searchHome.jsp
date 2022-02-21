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
					<th scope="col">Actions</th>
				</tr>
			</thead>
			<tbody>
				<%
				String search = request.getParameter("search");
				FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
				List<FoodItemBean> foods = foodDAO.selectAvailableFoodsLike(search);
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
							<td><input type="number" name="quantity" placeholder="Quantity"></td>

							<td>
								<button type="submit" class="btn btn-primary">Add To
									Cart</button>
							</td>
						</tr>
					</div>
				</form>
				<%
				}
				if (sno == 1) { %>
					<p>No items available</p>
				<% }%>
			</tbody>
		</table>
	</div>
</body>
</html>