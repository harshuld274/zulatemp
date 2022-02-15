<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Zula</title>
<%@include file="components/all_css.jsp"%>
</head>

<body>
	<%@include file="components/navbar.jsp"%>
	<%
	System.out.println(user + " login");
	if (user != null) {
		response.sendRedirect("home.jsp");
	}
	%>
	<section class="vh-100" style="background-color: #c6bec7;">
		<div class="container py-5 h-100">
			<div
				class="row d-flex justify-content-center align-items-center h-100">
				<div class="col col-xl-10">
					<div class="card" style="border-radius: 1rem;">
						<div class="row g-0">
							<div class="col-md-6 col-lg-5 d-none d-md-block">
								<img
									src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/img1.webp"
									alt="login form" class="img-fluid"
									style="border-radius: 1rem 0 0 1rem;" />
							</div>
							<div class="col-md-6 col-lg-7 d-flex align-items-center">
								<div class="card-body p-4 p-lg-5 text-black">

									<form action="login" method="post">

										<div class="d-flex align-items-center mb-3 pb-1">
											<i class="fas fa-cubes fa-2x me-3" style="color: #ff6219;"></i>
											<span class="h1 fw-bold mb-0">Zula</span>
										</div>

										<h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Sign
											into your account</h5>

										<div class="form-outline mb-4">
											<input type="email" id="formEmail" name="email"
												class="form-control form-control-lg" required="required"/> <label
												class="form-label" for="formEmail">Email</label>
										</div>

										<div class="form-outline mb-4">
											<input type="password" id="formPassword" name="password"
												class="form-control form-control-lg" required="required"/> <label
												class="form-label" for="formPassword">Password</label>
										</div>
										<%
										String fail = (String) session.getAttribute("login-fail");
										%>
										<%
										if (fail != null) {
										%>
										<p class="text-danger"><%=fail%></p>

										<%
										session.removeAttribute("login-fail");
										}
										%>
										<div class="pt-1 mb-4">
											<button class="btn btn-dark btn-lg btn-block" type="submit">Login</button>
										</div>

										<a class="small text-muted" href="#!">Forgot password?</a>
										<p class="mb-5 pb-lg-2" style="color: #393f81;">
											Don't have an account? <a href="signup.jsp"
												style="color: #393f81;">Register here</a>
										</p>
										<a href="#" class="small text-muted">Terms of use.</a> <a
											href="#" class="small text-muted">Privacy policy</a>
									</form>

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