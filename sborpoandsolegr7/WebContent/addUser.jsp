<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@page import="cs236369.hw5.users.UserManager"%>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<%String decision="add";%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> 

<title>Add User</title> 

<link rel="stylesheet" type="text/css" media="screen" href="addUser.css" />
<link rel="stylesheet" type="text/css" media="screen" href="defualtCss.css" />   
<link rel="stylesheet" type="text/css" media="screen" href="http://jquery.bassistance.de/validate/demo/css/chili.css" /> 
<script src="http://jquery.bassistance.de/validate/lib/jquery.js" type="text/javascript"></script> 
<script src="http://jquery.bassistance.de/validate/jquery.validate.js" type="text/javascript"></script> 
<script src="dynamicTestForAddUser.js" type="text/javascript"></script> 
<script src="js/common.js" type="text/javascript"></script> 
<script src="js/addUserValidate.js" type="text/javascript"></script> 

<style type="text/css"> 
	pre { text-align: left; }
</style> 
 
</head>
<%@page import="cs236369.hw5.*" %>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<body onload="javascript:init(); toggeleAdminAuth(); "> 
<br/>
<br/>
<div id="main"> 
 
<div id="content"> 

<div style="clear: both;"><div></div></div> 
 
 
<div class="content"> 
    <div id="signupbox"> 
       <div id="signuptab"> 
        <ul> 
          <li id="signupcurrent">Signup</li> 
        </ul> 
      </div> 
      <div id="signupwrap"> 
      		<form id="signupform" action="AddNewUser" enctype="multipart/form-data" method="post" action=""> 
      		<!-- form  action="AddNewUser" ENCTYPE="multipart/form-data" method="post"/> -->
	  		 <jsp:include page="userInputForms.jsp">
	  		 	<jsp:param value="<%=decision %>" name="decision"/>
	  		 </jsp:include>
<br/>
			<input value="AddUser" type="submit"/>&nbsp;&nbsp;&nbsp;&nbsp;<input value="Clear" type="reset"/>
</form>
      </div> 
    </div> 
</div> 
 
</div> 
 
</div> 
 
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript"> 
</script> 
<script type="text/javascript"> 
_uacct = "UA-2623402-1";
urchinTracker();
</script> 
 
<script src="http://jquery.bassistance.de/validate/demo/js/chili-1.7.pack.js" type="text/javascript"></script> 
 
</body> 
</html>