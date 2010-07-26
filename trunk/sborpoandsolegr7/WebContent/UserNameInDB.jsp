<%@page import="cs236369.hw5.users.UserManager"%><%
	if (!UserManager.isUserExists(request.getParameter("username"))) {
		System.out.println(true);%>
	<%="true"%>
<%}
	else {System.out.println(false);%> 
		<%="false" %>
		
<%	}%>
