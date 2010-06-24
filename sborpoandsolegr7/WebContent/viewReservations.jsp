<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
</head>
<jsp:include page="sessionDetailsHeader.jsp"></jsp:include>
<body>
<form method="GET" action="/sborpoandsolegr7/displayResvTimeline.jsp">
Instrument Type : <input type="text" size="15" maxlength="25" name="type"><br><br>
Day : <input type="text" size="15" maxlength="25" name="day">&nbsp;&nbsp;&nbsp;
Month : <input type="text" size="15" maxlength="25" name="month">&nbsp;&nbsp;&nbsp;
Year : <input type="text" size="15" maxlength="25" name="year"><br><br>
Sequence Slots Number : <input type="text" size="15" maxlength="25" name="slotSequence"><br><br>
<input value="Login" type="submit">&nbsp;&nbsp;&nbsp;&nbsp;<input value="Clear" type="reset">
</form>
</body>
</html>





