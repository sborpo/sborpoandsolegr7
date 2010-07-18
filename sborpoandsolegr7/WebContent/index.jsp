<%if ((request.getUserPrincipal()!=null) && (request.getUserPrincipal().getName()!=null)){ %>
<jsp:forward page="indexLoged.jsp"></jsp:forward>
<%}else{ %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="cs236369.hw5.Utils"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
<title>Lab Managment System Index</title>
</head>
<body>
<jsp:include page="sessionDetailsHeader.jsp"></jsp:include>
<h1>Welcome!</h1>
<p>This site gives you the opportunity to deal with these issues:
<br/></p>
<ul class="list">
<li class="list">Time slot reservations : we give the opportunity to search ,reserve and manage
<br/> time slots of lab's instruments for the research group you belong to. If you don't find any time slots which
<br/> satisfies you , you can search for this time slot in other labs with our web-service.
<br/> <img src="/sborpoandsolegr7/images/attention.png"></img>You may reserve time slots only for instruments that you have a permission
<br/>
<br/>
</li>
<li class="list">Lab users interaction : you can see all lab's users and their details , you can create your own <br/>
research group or joins others research groups. </li>
</ul>
<br/>
To get started with these opportunities , you first must create a username on the site.
<br/> In order to create one , press <a href="/sborpoandsolegr7/addUser.jsp">here</a>.
<br/>
<br/>
<div align="center"></div><img src="/sborpoandsolegr7/images/microscope.png"></img></div>
<br/>
<h4> If you have any problem you can contact the support by sending an <a href="mailto:<%=Utils.supportMail %>">email.</a> </h4> 
</body>
</html>
<%}%>