<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="cs236369.hw5.ErrorInfoBean"%>
<%String login= request.getParameter("username");
	if (login==null)
	{ ErrorInfoBean err = new ErrorInfoBean();
	err.setLinkStr("Main Page");
	err.setLink("javascript:history.back(1)");
	err.setErrorString("Parameter Error"); 
	request.setAttribute("ErrorInfoBean", err);
%>
	<jsp:forward page="/errorPages/errorPage.jsp"></jsp:forward>
	<%} %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.users.UserManager"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Password Recovery Page</title>
<link rel="stylesheet" type="text/css" media="screen" href="addUser.css" />   
<link rel="stylesheet" type="text/css" media="screen" href="defualtCss.css" />   

<link rel="stylesheet" type="text/css" media="screen" href="http://jquery.bassistance.de/validate/demo/css/chili.css" /> 
 
<script src="http://jquery.bassistance.de/validate/lib/jquery.js" type="text/javascript"></script> 
<script src="http://jquery.bassistance.de/validate/jquery.validate.js" type="text/javascript"></script> 
 
<style type="text/css"> 
	pre { text-align: left; }
</style> 
 
<script src="/sborpoandsolegr7/js/dynamicTestForAddUser.js" type="text/javascript"></script> 
</head>
<body>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<h2>Reset Password</h2>
<br/>
<br/>
<div class="content"> 
    <div id="signupbox"> 
       <div id="signuptab"> 
        <ul> 
          <li id="signupcurrent">Reset You Password</li> 
        </ul> 
      </div> 
      <div id="signupwrap"> 
      <p>Now you can reset your password<br/> Please enter your username:</p>
      		<form id="signupform" action="ResetPassword"  method="post" > 
<table> 
				
			 <tr> 
	  			<td class="label"><label id="lusername" for="username">Username</label></td> 
	  			<td class="field"><input id="username" type="text"   maxlength="50"  readonly="readonly" value="<%=login%>" name="<%=UserManager.Usern%>" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
			 <tr> 
	  			<td class="label"><label id="lpassword" for="password">New Password</label></td> 
	  			<td class="field"><input id="password" type="password" maxlength="50" value="" name="<%=UserManager.Password%>"/></td> 
	  			<td class="status"></td> 
	  		  </tr> 
	  		  <tr> 
	  			<td class="label"><label id="lpassword_confirm" for="password_confirm">Confirm New Password</label></td> 
	  			<td class="field"><input id="password_confirm" type="password" maxlength="50" value="" name="<%=UserManager.PassConfirm%>"/></td> 
	  			<td class="status"></td> 
	  		  </tr> 
</table>
<br/>
<input value="Reset Password" type="submit"/>
</form>
</div>
</div>
</div>
</body>
</html>