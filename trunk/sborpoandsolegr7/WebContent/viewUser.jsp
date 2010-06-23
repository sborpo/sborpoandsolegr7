<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.User"%>
<%@page import="cs236369.hw5.users.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
<% String login = request.getParameter("username"); User user=UserManager.getUserDetails(login); %>
<h1>User Details</h1><br/><br/>
<table>
<tr>
<td>Username: <%=user.getLogin()%></td><td>Name: <%=user.getName() %></td>
</tr>
<tr>
<td>Group: <%=user.getGroup() %></td><td>Permissions:<%=((user.getPremissions()==null)? "No Permission" :user.getPremissions()) %></td>
</tr>
<tr>
<td>Phone Number: <%=user.getPhoneNumber() %></td><td>Address: <%=user.getAddress() %></td>
</tr>

</table>
<img src="ImageGetter?username=<%=login%>" />
</body>
</html>