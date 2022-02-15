<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	System.out.println(user + " home");
	if (user == null) {
		session.setAttribute("login-fail", "Login to access");
		response.sendRedirect("login.jsp");
	}
	%>
	<%
	String fail = (String) session.getAttribute("signup-fail2");
	System.out.println(fail + " fail - home");
	if (fail != null) {
	%>
	<p><%=fail%></p>
	<%
	session.removeAttribute("signup-fail2");
	}
	%>
	Home
</body>
</html>