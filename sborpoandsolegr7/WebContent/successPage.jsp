<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.users.UserManager"%>
<%@page import="cs236369.hw5.Utils"%><html>
<head>
<style type="text/css">
	body {background: #f9fee8;margin: 0; padding: 20px; text-align:center; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#666666;}
		.error_page {width: 600px; padding: 50px; margin: auto;}
		.error_page h1 {margin: 20px 0 0;}
		.error_page p {margin: 10px 0; padding: 0;}		
		a {color: #9caa6d; text-decoration:none;}
		a:hover {color: #9caa6d; text-decoration:underline;}
	div.transbox
  {
  width:90%;
  height:90%;
  margin:2em 3em;
  background-color:#ffffff;
  border:1px dashed blue;
  /* for IE */
  filter:alpha(opacity=60);
  /* CSS3 standard */
  opacity:0.6;
  }
div.transbox p
  {
  margin:2em 3em;
  font-weight:bold;
  color:#000000;
  }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function delayer(){
    window.location = "<%=((String)request.getSession().getAttribute("successPage")) %>"
}
</script>
</head>
<body onLoad="setTimeout('delayer()', <%=Utils.successRedirectDelay*1000 %>)">
<div class="transbox">
<h2>Your Operation Was Completed Successfully!</h2>
<br/><br/>
<p>You are being redirected within <%=Utils.successRedirectDelay %> seconds...</p>
<br/>
<br/>
</div>
</body>
</html>