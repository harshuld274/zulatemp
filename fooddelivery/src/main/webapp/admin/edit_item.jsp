<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="com.phantombeast.fooddelivery.dao.FoodItemDAO, com.phantombeast.fooddelivery.bean.FoodItemBean,com.phantombeast.fooddelivery.dao.ConnectionProvider, java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit</title>
<%@include file="../components/all_css.jsp"%>
</head>
<body>
	<%@include file="navbar_admin.jsp"%>

	<div class="container-fluid py-5 h-100"
		style="background-color: #c6bec7;">
		<div class="row d-flex justify-content-center align-items-center">
			<div class="col-xl-8">
				<div class="card" style="border-radius: 1rem;">
					<div class="col-md-6 col-lg-7 d-flex align-items-center">
						<div class="card-body p-4 p-lg-5 text-black">
							<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Edit
								Food Item</p>
							<%
							String editItemFail = (String) session.getAttribute("edit-item-fail");
							if (editItemFail != null) {
							%>
							<p class="text-danger"><%=editItemFail%></p>

							<%
							session.removeAttribute("edit-item-fail");
							}
							%>

							<%
							int id = Integer.parseInt((String) request.getParameter("id"));
							FoodItemDAO foodDAO = new FoodItemDAO(ConnectionProvider.getConnection());
							FoodItemBean food = foodDAO.selectFoodById(id);
							%>
							<form action="editItem" method="post">
								<input type="hidden" name="id" value=<%=id%>>
								<div class="mb-3">
									<label for="formName" class="form-label">Food Name </label> <input
										type="text" class="form-control" id="formName" name="name"
										required="required" value=<%=food.getName()%>>
								</div>
								<div class="mb-3">
									<label for="formPrice" class="form-label">Food Price </label> <input
										type="number" step="0.01" class="form-control" id="formPrice"
										name="price" required="required" value=<%=food.getPrice()%>>
								</div>
								<div class="mb-3">
									<label for="formAbout" class="form-label">Food
										Description </label>
									<textarea rows="5" cols="60" id="formAbout" name="about"
										class="form-control" required="required"><%=food.getAbout()%></textarea>
								</div>
								<div class="mb-3">
									<label for="formQuantity" class="form-label">Food
										Quantity </label> <input type="number" class="form-control"
										id="formQuantity" name="quantity" required="required"
										value=<%=food.getQuantity()%>>
								</div>
								<button type="submit" class="btn btn-primary">Submit</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>