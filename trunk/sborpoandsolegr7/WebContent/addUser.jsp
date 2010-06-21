<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add User</title>
</head>
<body>
<h1>Please Your Details:</h1><br/><br/>
<form  action="AddNewUser" ENCTYPE="multipart/form-data"
      method="post">
Username : <input type="text" size="15" maxlength="25" name="username"><br/><br/>
Password : <input type="password" size="15" maxlength="25" name="password"><br/><br/>
Confirm Password : <input type="password" size="15" maxlength="25" name="c_password"><br/><br/>
Name : <input type="text" size="15" maxlength="25" name="name"><br/><br/>
Permission : <input type="text" size="15" maxlength="25" name="permission"><br/><br/>
Group: <input type="text" size="15" maxlength="25" name="group"><br/><br/>
Address : <input type="text" size="15" maxlength="25" name="address"><br/><br/>
Phone Number : <input type="text" size="15" maxlength="25" name="phonenumber"><br/><br/>
User Type : <select name="usertype">
<option value="Researcher" selected="selected">Researcher</option>
<option value="Admin">Administrator</option>
</select>
<br/><br/>
Picture (Max Size: 300 Kb) :<input type="hidden" name="MAX_FILE_SIZE" value="300" />
<input type="file" name="userpicture" />
<br/><br/>
<img src="jcaptcha.jpg" /> <input type="text" name="jcaptcha" value="" />
<br/><br/>



<input value="AddUser" type="submit">&nbsp;&nbsp;&nbsp;&nbsp;<input value="Clear" type="reset">
</form>
</body>
</html>