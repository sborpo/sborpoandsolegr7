<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Calendar"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Reservations</title>
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
<script src="/sborpoandsolegr7/js/common.js" type="text/javascript"></script> 
</head>
<style type="text/css">
div.centeredBox
{
width:60%;
text-align: left;
}
</style>
<body>
<jsp:include page="sessionDetailsHeader.jsp"></jsp:include>
<div class="centeredBox">
<form method="GET" action="/sborpoandsolegr7/displayResvTimeline.jsp" onsubmit="return validateSlotsInput('slotInput');">
<fieldset>
<legend id="timeTableLeg">View Timetable On:</legend>
<br/>
Instrument Type : <input type="text" size="15" maxlength="25" name="type"><br><br>
Day : <select  name="day"><%for (int i=1; i<=30; i++){ %><option value=<%=i %>><%=i %></option><%} %></select>&nbsp;&nbsp;&nbsp;
Month : <select name="month"><%for (int i=1; i<=12; i++){ %><option value=<%=i %>><%=i %></option><%} %></select>&nbsp;&nbsp;&nbsp;
Year : <select name="year"><%	Calendar cal = Calendar.getInstance(); int year=cal.get(Calendar.YEAR); for (int i=year; i<=year+3; i++){ %><option value=<%=i %>><%=i %></option><%} %></select>&nbsp;&nbsp;&nbsp;
<br/>
<br/>
<label>Sequence Slots Number : </label><input type="text" size="4" maxlength="4" id="slotInput" name="slotSequence" onchange="javascript:validateSlotsInput('slotInput')"><br><br>
<input value="Search For Slots" type="submit" >&nbsp;&nbsp;&nbsp;&nbsp;<input value="Clear" type="reset">
</fieldset>
</form>
</div>
</body>
</html>





