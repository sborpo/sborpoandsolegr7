<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.Utils"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
<title>Lab Managment System Index</title>
</head>
<body>
<jsp:include page="sessionDetailsHeader.jsp"></jsp:include>
<h1>Welcome!</h1>
<p>You are now logged into the system. You can manage your details using the<br/> status 
bar in the top of the screen. 
<br/> You may navigate the site easily using the navigation bar which appear below the status bar. 
</p>
<h3>Enjoy!</h3>
<h4> If you have any problem you can contact the support by sending an <a href="mailto:<%=Utils.supportMail %>">email.</a> </h4> 
</body>
</html>