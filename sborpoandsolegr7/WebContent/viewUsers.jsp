<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.users.UserManager"%>
<%@page import="java.util.LinkedList"%>
<%@page import="cs236369.hw5.User.UserType"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View System's Users</title>
<link rel="stylesheet" type="text/css" href="defualtCss.css" />



<style type="text/css">
table
{
border-collapse:collapse;
width:90%;
}
th
{
height:50px;
}
td
{
padding:15px;
}
table,th, td
{
border: 1px solid black;
}
td.userTypesRow {background-color:yellow;}
</style>
</head>
<%@page import="cs236369.hw5.*" %>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<body>
<table>
<tr>
<th>#</th><th>Username</th><th>Name</th><th>Group Leader</th><th>Permissions</th>
</tr>
<tr class="userTypesRow">
<td colspan="5">Researchers</td>
</tr>
<%
	int i=1;
LinkedList<User> users= UserManager.getUsers();
for ( User user : users ){ if (!user.getRole().equals(User.UserType.REASEARCHER)){continue;}
%>
<tr>
<td><%=i %></td><td><a href=viewUser.jsp?username=<%=user.getLogin()%>><%=user.getLogin() %></a></td><td><%=user.getName() %></td><td><%=user.getGroup() %></td><td><%=user.getPremissions() %></td>
</tr>
<%i++;} %>
<tr class="userTypesRow">
<td colspan="5">Administrators</td>
</tr>
<%for ( User user : users ){ if (!user.getRole().equals(User.UserType.ADMIN)){continue;}%>
<tr>
<td><%=i %></td><td><a href=viewUser.jsp?username=<%=user.getLogin()%>><%=user.getLogin() %></a></td><td><%=user.getName() %></td><td><%=user.getGroup() %></td><td><%=user.getPremissions() %></td>
</tr>
<%i++;} %>
</table>
</body>
</html>