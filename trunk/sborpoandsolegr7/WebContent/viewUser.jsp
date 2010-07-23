<%if (request.getParameter(UserManager.Usern)==null){ %><jsp:forward page="ParamErrorSetter"></jsp:forward><%} %>
<%if (!UserManager.isUserExists(request.getParameter(UserManager.Usern))){ 		
%><jsp:forward page="/ParamErrorSetter"></jsp:forward><%} %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="cs236369.hw5.users.UserManager"%>
<%@page import="cs236369.hw5.User"%>
<%@page import="cs236369.hw5.users.*"%>
<%@page import="cs236369.hw5.ErrorInfoBean"%>
<%@page import="cs236369.hw5.User.UserType"%>
<%@page import="cs236369.hw5.User.UserType"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
<link rel="stylesheet" type="text/css" href="/sborpoandsolegr7/css/viewUserCss.css" />
</head>

<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>


<body>
<% String login = request.getParameter("username"); User user=UserManager.getUserDetails(login); 
String userTypeImage;
if (user.getRole().equals(User.UserType.ADMIN))
{
	userTypeImage="/sborpoandsolegr7/images/Administrator2.png";
}
else
{
	userTypeImage="/sborpoandsolegr7/images/Usericon.png";
}%>
<h1>User Details</h1><br/><br/>
<div class="table">
<div id=heading><div id="heading-text"><img src="<%=userTypeImage%>" />&nbsp;&nbsp;<%=user.getLogin()%></div></div>
<div id="details">
<div id="content">
<strong>Name:</strong> <%=user.getName() %><br/>
<strong>Group:</strong> <%=user.getGroup() %><br/>
<%if (user.getRole().equals(UserType.REASEARCHER)){ %>
<strong>Permissions:</strong> <%=((user.getPremissions().equals(""))? "No Permission" :user.getPremissions()) %><br/>
<%} %>
<strong>Phone Number:</strong> <%=((user.getPhoneNumber().equals(""))? "not specified" : user.getPhoneNumber()) %><br/>
<strong>Address: </strong><%=((user.getAddress().equals(""))? "not specified" :  user.getAddress()) %><br/>
<br/>
<img src="/sborpoandsolegr7/images/email-icon.gif">&nbsp;&nbsp;<a href="mailto:<%=user.getEmail() %>">Send Message</a>
</div>
</div>
<div id="photo"> <div class="image"><img src="ImageGetter?username=<%=login%>" /></div></div>

<div class="options" ><div class="button-text"><% boolean isAdmin= UserUtils.isAdmin(request);
if (((request.getUserPrincipal()!=null) && (request.getUserPrincipal().getName().equals(request.getParameter("username")))) || isAdmin){%>
	<a href=updateUser.jsp?username=<%=login%>>Updated Profile Details</a><%}%><%if ((isAdmin)&&(user.getRole().equals(User.UserType.REASEARCHER))){ %> || <a href="DeleteUser?username=<%=login %>"> remove User</a><%} %>
</div>
</div>

</div>
<br/>
<br/>
</body>
</html>