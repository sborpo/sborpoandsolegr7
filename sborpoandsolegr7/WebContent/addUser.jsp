<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@page import="cs236369.hw5.users.UserManager"%>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> 

<title>Add User</title> 
 
<link rel="stylesheet" type="text/css" media="screen" href="addUser.css" /> 
<link rel="stylesheet" type="text/css" media="screen" href="http://jquery.bassistance.de/validate/demo/css/chili.css" /> 
 
<script src="http://jquery.bassistance.de/validate/lib/jquery.js" type="text/javascript"></script> 
<script src="http://jquery.bassistance.de/validate/jquery.validate.js" type="text/javascript"></script> 
 
<style type="text/css"> 
	pre { text-align: left; }
</style> 
 
<script src="dynamicTestForAddUser.js" type="text/javascript"></script> 
 
</head>
<%@page import="cs236369.hw5.*" %>
<jsp:include page="sessionDetailsHeader.jsp"></jsp:include>
<body> 
 
<h1 id="banner">Add User</h1> 
<div id="main"> 
 
<div id="content"> 

<div style="clear: both;"><div></div></div> 
 
 
<div class="content"> 
    <div id="signupbox"> 
       <div id="signuptab"> 
        <ul> 
          <li id="signupcurrent"><a href=" ">Signup</a></li> 
        </ul> 
      </div> 
      <div id="signupwrap"> 
      		<form id="signupform" action="AddNewUser" enctype="multipart/form-data" method="post" action=""> 
      		<!-- form  action="AddNewUser" ENCTYPE="multipart/form-data" method="post"/> -->
	  		  <table> 
	  		  <tr> 
	  		  	<td class="label"><label id="lname" for="name">Name</label></td> 
	  		  	<td class="field"><input id="name"  type="text" value="" maxlength="100" name="<%=UserManager.Name %>" /></td> 
	  		  	<td class="status"></td> 
	  		  </tr> 
	  		  <tr> 
	  			<td class="label"><label id="lusername" for="username">Username</label></td> 
	  			<td class="field"><input id="username" type="text" value="" maxlength="50" name="<%=UserManager.Usern %>" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
	  		  <tr> 
	  			<td class="label"><label id="lpassword" for="password">Password</label></td> 
	  			<td class="field"><input id="password" type="password" maxlength="50" value="" name="<%=UserManager.Password %>"/></td> 
	  			<td class="status"></td> 
	  		  </tr> 
	  		  <tr> 
	  			<td class="label"><label id="lpassword_confirm" for="password_confirm">Confirm Password</label></td> 
	  			<td class="field"><input id="password_confirm" type="password" maxlength="50" value="" name="<%=UserManager.PassConfirm %>"/></td> 
	  			<td class="status"></td> 
	  		  </tr> 
	  		  <tr> 
	  			<td class="label"><label id="laddress" for="address">Address</label></td> 
	  			<td class="field"><input id="address" name="<%=UserManager.Address %>" type="text" value="" maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
			    <tr> 
	  			<td class="label"><label id="lphoneno" for="phoneno">Phone Number</label></td> 
	  			<td class="field"><input id="phoneno" name="<%=UserManager.PhoneNumber %>" type="text" value="" maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
			  <tr> 
	  			<td class="label"><label id="lgroup" for="group">Group</label></td> 
	  			<td class="field"><input id="group" name="<%=UserManager.Group %>" type="text" value="" maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
			  <tr> 
	  			<td class="label"><label id="lusertype" for="usertype">User Type</label></td> 
	  			<td class="field"><select id="usertype" name="<%=UserManager.UserTypen %>">
						<option value="Researcher" selected="selected">Researcher</option>
						<option value="Admin">Administrator</option>
					</select>
				</td>
	  			<td class="status"></td> 
	  		  </tr> 
 			  <tr>
 			  	<td class="label"><label id="lpicture" for="picture">Picture (Max Size: 300 Kb):</label></td>
 			  	<td class="field"><input id="picture" type="hidden" name="MAX_FILE_SIZE" value="<%=UserManager.FileSizeInBytes/1000%>" />
								  <input type="file" name="userpicture" />
				</td>
			  </tr>
			  <tr>
			  	<td></td><td ><img src="jcaptcha.jpg" /></td>
			  </tr>
			  <tr>
			  	<td class="label"><label id="lcaptcha" for="captcha">Enter the letters above:</label></td>
 			  	<td class="field"><input id="captcha" type="text" name="<%=UserManager.Captcha %>" value="" />	</td>
			  </tr>

</table>
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