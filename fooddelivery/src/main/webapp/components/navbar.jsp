<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark p-3">
		<div class="container-fluid">
			<a class="navbar-brand" href="welcome.jsp">Zula</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarScroll"
				aria-controls="navbarScroll" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarScroll">
				<ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll"
					style="-bs-scroll-height: 100px;">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="home.jsp">Home</a></li>
					<li class="nav-item active"><a class="nav-link active"
						href="my_cart.jsp">My Cart</a></li>
					<li class="nav-item active"><a class="nav-link active"
						href="my_orders.jsp">My Orders</a></li>
					<li class="nav-item active"><a class="nav-link active"
						href="update_password.jsp">Change Password</a></li>
					<li class="nav-item active"><a class="nav-link active"
						href="about.jsp">About</a></li>
					<li class="nav-item active"><a class="nav-link active"
						href="contact_us.jsp">Contact Us</a></li>
				</ul>
				<%
				String user = (String) session.getAttribute("email");
				if (user == null) {
				%>
				<div class="d-flex">
					<a class="btn btn-success" href="login.jsp">Login</a> <a
						class="btn btn-danger ms-3" href="signup.jsp">Sign Up</a>
				</div>
				<%
				} else {
				%>
				<div class="d-flex">
					<a class="btn btn-success" href="edit_profile.jsp">Hi, <%=user%></a> <a
						class="btn btn-danger ms-3" href="logout">Logout</a>
				</div>
				<%}%>
			</div>
		</div>
	</nav>
</body>
</html>