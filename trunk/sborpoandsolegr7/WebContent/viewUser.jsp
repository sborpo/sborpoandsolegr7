<%if (request.getParameter(UserManager.Usern)==null){ %><jsp:forward page="ParamErrorSetter"></jsp:forward><%} %>
<%if (!UserManager.isUserExists(request.getParameter(UserManager.Usern))){ 		
		ErrorInfoBean err = new ErrorInfoBean();
		err.setErrorString("User Error");
		err.setReason("User not exists in the system!");
		err.setLink("javascript:history.back(1);");
		err.setLinkStr("Back");
		request.setAttribute("ErrorInfoBean", err);%><jsp:forward page="/errorPages/errorPage.jsp"></jsp:forward><%} %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="cs236369.hw5.users.UserManager"%>
<%@page import="cs236369.hw5.User"%>
<%@page import="cs236369.hw5.users.*"%>
<%@page import="cs236369.hw5.ErrorInfoBean"%>
<%@page import="cs236369.hw5.User.UserType"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
<style type="text/css">
label.viewuser{ font-weight: bold;}
</style>
</head>

<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>


<body>
<% String login = request.getParameter("username"); User user=UserManager.getUserDetails(login); %>
<h1>User Details</h1><br/><br/>
<table>
<tr>
<td>
<span class="viewuser">
<label>Username:</label> <%=user.getLogin()%><br/>
<label>Name:</label> <%=user.getName() %><br/>
<label>Group:</label> <%=user.getGroup() %><br/>
<label>Permissions:</label> <%=((user.getPremissions()==null)? "No Permission" :user.getPremissions()) %><br/>
<label>Phone Number:</label> <%=user.getPhoneNumber() %><br/>
<label>Address: </label><%=user.getAddress() %><br/>
</span>
</td>
<td><img src="ImageGetter?username=<%=login%>" height="100%"/></td>
</tr>
<tr>
<td colspan="2"><% boolean isAdmin= UserUtils.isAdmin(request);
if (((request.getUserPrincipal()!=null) && (request.getUserPrincipal().getName().equals(request.getParameter("username")))) || isAdmin){%>
	<a href=updateUser.jsp?username=<%=login%>>Updated Profile Details</a><%}%><%if ((isAdmin)&&(user.getRole().equals(User.UserType.REASEARCHER))){ %> || <a href="DeleteUser?username=<%=login %>"> remove User</a><%} %></td>
</tr>
</table>
<br/>
<br/>

</body>
</html>