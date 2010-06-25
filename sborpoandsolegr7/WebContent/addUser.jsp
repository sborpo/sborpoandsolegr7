<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.users.UserManager"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add User</title>
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
<style type="text/css">
table
{
border-collapse:collapse;
width:50%;
}
td
{
padding:0.7em;
text-align:left;
}
tr
{
border: 1px solid black;
}
</style>
</head>
<%@page import="cs236369.hw5.*" %>
<jsp:include page="sessionDetailsHeader.jsp"></jsp:include>
<body>
<h1>Please enter your details:</h1><br/><br/>

<form  action="AddNewUser" ENCTYPE="multipart/form-data"
      method="post">
<table>
<tr><td>Username : </td><td><input type="text" size="15" maxlength="25" name="<%=UserManager.Usern %>"></td><td>Name :</td><td> <input type="text" size="15" maxlength="25" name="<%=UserManager.Name %>"></td></tr>
<tr><td>Password : </td><td><input type="password" size="15" maxlength="25" name="<%=UserManager.Password %>"></td><td>Confirm Password : </td><td><input type="password" size="15" maxlength="25" name="<%=UserManager.PassConfirm %>"></td></tr>

<tr><td>Address :</td><td> <input type="text" size="15" maxlength="25" name="<%=UserManager.Address %>"></td><td>  Phone Number :</td><td><input type="text" size="15" maxlength="25" name="<%=UserManager.PhoneNumber %>"></td></tr>
<tr><td>Group:</td><td> <input type="text" size="15" maxlength="25" name="<%=UserManager.Group %>"></td>
<td>User Type : </td><td><select name="<%=UserManager.UserTypen %>">
<option value="Researcher" selected="selected">Researcher</option>
<option value="Admin">Administrator</option>
</select>
</td></tr>
<tr><td colspan="2">Picture (Max Size: 300 Kb) :</td><td colspan="2"><input type="hidden" name="MAX_FILE_SIZE" value="300" />
<input type="file" name="userpicture" />
</td></tr>
<tr><td colspan="4">Captcha</td></tr>
<tr><td colspan="4"><img src="jcaptcha.jpg" /></td></tr>
<tr><td colspan="4">Please enter the word which is presented in the picture: <input type="text" name="<%=UserManager.Captcha %>" value="" /></td></tr>

</table>
<br/>
<input value="AddUser" type="submit">&nbsp;&nbsp;&nbsp;&nbsp;<input value="Clear" type="reset">
</form>

</body>
</html>