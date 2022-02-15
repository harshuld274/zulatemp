<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up</title>
<%@include file="components/all_css.jsp"%>
</head>
<body>
	<%@include file="components/navbar.jsp"%>
	<%
	System.out.println("signup - " + user);
	if (user != null) {
		session.setAttribute("signup-fail2", "logout change to Sign Up");
		response.sendRedirect("home.jsp");
	}
	%>
	<%
	String signupFail = (String) session.getAttribute("signup-fail");
	System.out.println(signupFail + " fail - signup");
	if (signupFail != null) {
	%>
	
	<p class="text-danger"><%=signupFail%></p>
	<%
	System.out.println("removed");
	session.removeAttribute("signup-fail");

	}
	%>
	<section class="vh-150" style="background-color: #c6bec7;">
		<div class="container h-150">
			<div
				class="row d-flex justify-content-center align-items-center h-150">
				<div class="col-lg-12 col-xl-11">
					<div class="card text-black my-3" style="border-radius: 25px;">
						<div class="card-body p-md-5">
							<div class="row justify-content-center">
								<div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

									<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign
										up</p>

									<form class="mx-1 mx-md-4" action="signup" method="post">

										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-user fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0">
												<input type="text" id="formName" name="name"
													class="form-control" required="required" /> <label
													class="form-label" for="formName">Your Name</label>
											</div>
										</div>

										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0">
												<input type="text" id="formEmail" name="email"
													class="form-control" required="required" /> <label
													class="form-label" for="formEmail">Your Email</label>
											</div>
										</div>

										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-lock fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0">
												<input type="password" id="formPassword" name="password"
													class="form-control" required="required" /> <label
													class="form-label" for="formPassword">Your Password</label>
											</div>
										</div>

										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-key fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0">
												<input type="password" id="formPassword2" name="password2"
													class="form-control" required="required" /> <label
													class="form-label" for="formPassword2">Repeat your
													password</label>
											</div>
										</div>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-user fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0">
												<textarea rows="5" cols="60" id="formAddress" name="address"
													class="form-control" required="required"></textarea>
												<label class="form-label" for="formAddress">Your
													Address</label>
											</div>
										</div>

										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0">
												<input type="text" id="formMobile" name="mobile"
													class="form-control" required="required" /> <label
													class="form-label" for="formMobile">Your Mobile
													Number</label>
											</div>
										</div>
										<div class="form-check d-flex justify-content-center mb-5">
											<input class="form-check-input me-2" type="checkbox" value=""
												id="formAgreeConditions" name="agreeConditions"
												required="required" /> <label class="form-check-label"
												for="formAgreeConditions"> I agree all statements in
												<a href="#">Terms of service</a>
											</label>
										</div>

										<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
											<button type="submit" class="btn btn-primary btn-lg">Register</button>
										</div>

									</form>

								</div>
								<div
									class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

									<img
										src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
										class="img-fluid" alt="Sample image">

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="components/footer.jsp"%>
	</section>
</body>
</html>