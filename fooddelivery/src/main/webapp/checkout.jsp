<%@page import="com.phantombeast.fooddelivery.bean.CartBean"%>
<%@page import="com.phantombeast.fooddelivery.bean.SignupBean"%>
<%@page import="com.phantombeast.fooddelivery.dao.UserDAO"%>
<%@page import="java.util.HashMap"%>
<%@page
	import="com.phantombeast.fooddelivery.dao.CartDAO, com.phantombeast.fooddelivery.bean.TempCartBean, com.phantombeast.fooddelivery.dao.ConnectionProvider, java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checkout</title>
</head>

<script type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
</script>
<%@include file="components/all_css.jsp"%>
</head>
<body>
	<%@include file="components/navbar.jsp"%>
	<div class="container">
		<form method="post" action="placeOrder">
			<table class="table table-striped">
				<thead>
					<tr>
						<th scope="col">S.No</th>
						<th scope="col">Name</th>
						<th scope="col">Price</th>
						<th scope="col">Order Quantity</th>
						<th scope="col">Sub total</th>
					</tr>
				</thead>
				<tbody>
					<%
					CartDAO cartDAO = new CartDAO(ConnectionProvider.getConnection());
					List<TempCartBean> items = cartDAO.selectCartByUser(user);
					CartBean cb = new CartBean(new HashMap<String, Integer>());
					int sno = 1;
					float total = 0;
					for (TempCartBean tb : items) {
						cb.addItem(tb.getFood(), tb.getQuantity());
						total += tb.getTotal();
					%>

					<div>
					<tr class="align-middle">
						<th scope="row"><%=sno++%></th>
						<td><%=tb.getFood()%></td>
						<td><%=tb.getPrice()%></td>
						<td><%=tb.getQuantity()%></td>
						<td><%=tb.getTotal()%></td>
					</tr>
					</div>
					<%
					}
					%>
					<input type="hidden" name="amount" value=<%=total%>>
				</tbody>
			</table>

			<div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

				<%
				UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());
				SignupBean sb = userDAO.selectUser(user);
				session.setAttribute("cart", cb);
				System.out.println(cb.toString());
				%>
				<div class="d-flex flex-row align-items-center mb-4">
					<i class="fas fa-user fa-lg me-3 fa-fw"></i>
					<div class="form-outline flex-fill mb-0">
						<textarea rows="5" cols="60" id="formAddress" name="address"
							class="form-control" required="required"><%=sb.getAddress()%></textarea>
						<label class="form-label" for="formAddress">Your Address</label>
					</div>
				</div>

				<div class="d-flex flex-row align-items-center mb-4">
					<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
					<div class="form-outline flex-fill mb-0">
						<input type="text" id="formMobile" name="mobile"
							class="form-control" required="required"
							value=<%=sb.getMobile()%>><label class="form-label"
							for="formMobile">Your Mobile Number</label>
					</div>
				</div>
				<select name="mode">
					<option value="CASH_ON_DELIVERY">Cash on Delivery</option>
					<option value="ONLINE_PAYMENT">Online Payment</option>
				</select>

				<p>
					Total
					<%=total%></p>
				<button type="submit" class="btn btn-success">Place Order</button>

			</div>
		</form>
	</div>
</body>
</html>