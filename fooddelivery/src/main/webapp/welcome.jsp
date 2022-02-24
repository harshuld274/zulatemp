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
	String signupSuccess = (String) session.getAttribute("signup-succ");
	if (signupSuccess != null) {
	%>
	<p><%=signupSuccess%></p>
	<%
	session.removeAttribute("signup-succ");
	}
	%>
	<%
	String passSuccess = (String) session.getAttribute("pass-succ");
	if (passSuccess != null) {
	%>
	<p><%=passSuccess%></p>
	<%
	session.removeAttribute("pass-succ");
	}
	%>
	Welcome
</body>
</html>