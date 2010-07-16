<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.users.UserManager"%><html>
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
 
<script src="dynamicTestForAddUser.js" type="text/javascript"></script> 
</head>
<body>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<h2>Password Recovery</h2>
<br/>
<br/>
<div class="content"> 
    <div id="signupbox"> 
       <div id="signuptab"> 
        <ul> 
          <li id="signupcurrent">Recover your password</li> 
        </ul> 
      </div> 
      <div id="signupwrap"> 
      <p>A new password will be sent to the email that you have entered when you have registered to the site.<br/>The email will be sent to you within 15 minutes, so please be patient.<br/> Please enter your username:</p>
      		<form id="signupform" action="PasswordRecovery"  method="get" > 
<table> 
			
			 <tr> 
	  			<td class="label"><label id="lusername" for="username">Username</label></td> 
	  			<td class="field"><input id="username" type="text"   maxlength="50" value="" name="<%=UserManager.Usern%>" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
</table>
<br/>
<input value="Recover Password" type="submit"/>
</form>
</div>
</div>
</div>
</body>
</html>