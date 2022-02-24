<%@page import="com.phantombeast.fooddelivery.bean.SignupBean"%>
<%@page import="com.phantombeast.fooddelivery.dao.ConnectionProvider"%>
<%@page import="com.phantombeast.fooddelivery.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Profile</title>
<%@include file="components/all_css.jsp"%>
</head>
<body>
	<%@include file="components/navbar.jsp"%>
	<%
	String editFail = (String) session.getAttribute("edit-fail");
	if (editFail != null) {
	%>
	<p><%=editFail%></p>
	<%
	session.removeAttribute("edit-fail");
	}
	%>
	<div class="container">
		<%
		UserDAO userDAO = new UserDAO(ConnectionProvider.getConnection());
		SignupBean sb = userDAO.selectUser(user);
		%>

		<div
			class="row d-flex justify-content-center align-items-center h-150">
			<div class="col-lg-12 col-xl-11">
				<div class="card text-black my-3" style="border-radius: 25px;">
					<div class="card-body p-md-5">
						<div class="row justify-content-center">
							<div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
								<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Edit
									Profile</p>
								<form class="mx-1 mx-md-4" action="editProfile" method="post">

									<div class="d-flex flex-row align-items-center mb-4">
										<i class="fas fa-user fa-lg me-3 fa-fw"></i>
										<div class="form-outline flex-fill mb-0">
											<input type="text" id="formName" name="name"
												class="form-control" required="required"
												value="<%=sb.getName()%>" /> <label class="form-label"
												for="formName">Your Name</label>
										</div>
									</div>
									<input type="hidden" value="<%=sb.getEmail()%>" name="email" />

									<div class="d-flex flex-row align-items-center mb-4">
										<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
										<div class="form-outline flex-fill mb-0">
											<input type="text" id="formEmail" value="<%=sb.getEmail()%>"
												class="form-control" disabled /> <label class="form-label"
												for="formEmail">Your Email</label>
										</div>
									</div>

									<div class="d-flex flex-row align-items-center mb-4">
										<i class="fas fa-user fa-lg me-3 fa-fw"></i>
										<div class="form-outline flex-fill mb-0">
											<textarea rows="5" cols="60" id="formAddress" name="address"
												class="form-control" required="required"><%=sb.getAddress()%></textarea>
											<label class="form-label" for="formAddress">Your
												Address</label>
										</div>
									</div>

									<div class="d-flex flex-row align-items-center mb-4">
										<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
										<div class="form-outline flex-fill mb-0">
											<input type="text" value="<%=sb.getMobile()%>"
												id="formMobile" name="mobile" class="form-control"
												required="required" /> <label class="form-label"
												for="formMobile">Your Mobile Number</label>
										</div>
									</div>
									<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
										<button type="submit" class="btn btn-primary btn-lg">Save
											Changes</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>