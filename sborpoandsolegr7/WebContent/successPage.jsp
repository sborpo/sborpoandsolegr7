<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.users.UserManager"%>
<%@page import="cs236369.hw5.Utils"%><html>
<head>
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