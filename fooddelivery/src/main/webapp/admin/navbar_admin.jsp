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
			<a class="navbar-brand" href="home.jsp">Zula</a>
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
						aria-current="page" href="all_items.jsp">View All Items</a></li>
					<li class="nav-item active"><a class="nav-link active"
						href="add_item.jsp">Add Item</a></li>
					<!-- 					<li class="nav-item active"><a class="nav-link active" -->
					<!-- 						href="edit_item.jsp">Edit Item</a></li> -->
					<!-- 					<li class="nav-item active"><a class="nav-link active" -->
					<!-- 						href="delete_item.jsp">Delete Item</a></li> -->
				</ul>
				<%-- <%
				String user = (String) session.getAttribute("email");
				System.out.println("user -" + user);
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
					<a class="btn btn-success" href="home.jsp">Hi, <%=user%></a> <a
						class="btn btn-danger ms-3" href="logout">Logout</a>
				</div>
				<%}%> --%>


				<div class="d-flex">
					<a class="btn btn-success" href="home.jsp">Hi, <%=(String) session.getAttribute("email")%></a>
					<a class="btn btn-danger ms-3" href="../logout">Logout</a>
				</div>

			</div>
		</div>
	</nav>
</body>
</html>