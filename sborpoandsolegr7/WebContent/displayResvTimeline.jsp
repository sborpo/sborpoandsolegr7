<% if (!ReservationManager.validateTimeLineParams(request.getParameter("day"),request.getParameter("month"),request.getParameter("year"),request.getParameter("slotSequence"),request.getParameter("type"))){ 
%>
<jsp:forward page="/ParamErrorSetter"></jsp:forward>
<%} %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>See Available Reservations</title>
<link rel="stylesheet" type="text/css" href="addUser.css" />
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
<script src="/sborpoandsolegr7/js/displayTimeLine.js" type="text/javascript"></script> 

<link rel="stylesheet" type="text/css" href="/sborpoandsolegr7/css/TimeLineCss.css" />

</head>
<jsp:include page="sessionDetailsHeader.jsp"></jsp:include>


<%@page import="cs236369.hw5.*"%>
<%
	int day = Integer.parseInt(request.getParameter("day"));
	int month = Integer.parseInt(request.getParameter("month"));
	int year = Integer.parseInt(request.getParameter("year"));
	int k = Integer.parseInt(request.getParameter("slotSequence"));
	String type = request.getParameter("type");
	String[][] arr = new ReservationManager.ReservationTable(year,
			month, day, k, type,request.getUserPrincipal().getName()).getReservationTable();
	TimeSlot weekEnd = new TimeSlot(year,
			new TimeSlot(year, month, day).getSlotNumber()
					+ TimeSlot.numOfSlotsInDay() * 6);
	TimeSlot nextWeek = new TimeSlot(year, new TimeSlot(year, month,
			day).getSlotNumber()
			+ TimeSlot.numOfSlotsInDay() * 7);
	TimeSlot prevWeek = new TimeSlot(year, new TimeSlot(year, month,
			day).getSlotNumber()
			- TimeSlot.numOfSlotsInDay() * 7);
	Integer startHourTime = (Integer) request.getSession(true)
			.getAttribute("fromHour");
	Integer endHourTime = (Integer) request.getSession(true)
			.getAttribute("toHour");
	Integer startTime = (startHourTime != null) ? startHourTime
			: TimeSlot.numOfSlotsInHour() * 8 + 1;
	Integer endTime = (startHourTime != null) ? endHourTime : TimeSlot
			.numOfSlotsInHour() * 12 + 1;
%>


<body
	onload="javascript: updateRows(<%=startTime%>,<%=endTime%>,<%=arr.length%>);">
<h3>You can see the reservations schedule on the week between: <%=day%>/<%=month%>/<%=year%>
to: <%=weekEnd.getDay()%>/<%=weekEnd.getMonth()%>/<%=weekEnd.getYear()%></h3>
<br></br>
<div class="hours">Display Hours: From:&nbsp;&nbsp;<input
	type="hidden" id="fromHourPrev" value="<%=startTime%>"><select
	id="fromHour" onchange="javascript:setFrom(value,<%=arr.length%>)">
	<%
		for (int i = 1; i < arr.length; i++) {
	%><option value="<%=i%>"
		<%if (i == startTime) {%> selected="selected" <%}%>><%=arr[i][0]%></option>
	<%
		}
	%>
</select>&nbsp;&nbsp;To:&nbsp;&nbsp;<input type="hidden" id="toHourPrev"
	value="<%=endTime%>"><select id="toHour"
	onchange="javascript:setTo(value,<%=arr.length%>)">
	<%
		for (int i = 1; i < arr.length; i++) {
	%>
	<option value="<%=i%>" <%if (i == endTime) {%> selected="selected"
		<%}%>><%=arr[i][0]%></option>
	<%
		}
	%>
</select></div>
<br></br>
<table>
	<tr style="border: none">
		<td colspan="4" style="text-align: left; border: none"><a href=/sborpoandsolegr7/displayResvTimeline.jsp?year=<%=prevWeek.getYear()%>&month=<%=prevWeek.getMonth()%>&day=<%=prevWeek.getDay()%>&type=<%=type%>&slotSequence=<%=k%>>&lt;&lt;</a>
		Previous Week</td>
		<td colspan="4" style="text-align: right; border: none">Next Week
		<a href=/sborpoandsolegr7/displayResvTimeline.jsp?year=<%=nextWeek.getYear()%>&month=<%=nextWeek.getMonth()%>&day=<%=nextWeek.getDay()%>&type=<%=type%>&slotSequence=<%=k%>>
		&gt;&gt;</a></td>
	</tr>
	<tr>
		<%
			for (int j = 0; j < 8; j++) {
		%>
		<th><%=arr[0][j]%></th>
		<%
			}
		%>
	</tr>
	<%
		for (int i = 1; i < arr.length; i++) {
	%>
	<tr id="<%=i%>">
		<%
			for (int j = 0; j < arr[i].length; j++) {
					if (j != 0) {
						TimeSlot time = new TimeSlot(year, month, day + j-1);
						String classcolor = null;
						int x = Integer.valueOf(arr[i][j].split(";")[0]);
						for (int t = 1; t < i; ++t) {
							time = time.nextSlot();
						}
						switch (x) {
						case (-1):
							classcolor = "notAvailible";
							break;
						case (0):
							classcolor = "taken";
							break;
						case (1):
							classcolor = "available";
						}
		%>
		<td class="<%=classcolor%>" id="<%=i%>"<% if (classcolor.equals("available")) { %> onclick="gotoOptionsScreen(<%=i %>,<%=j %>,<%=time.getYear()%>,<%=time.getMonth()%>,<%=time.getDay()%>,'<%=type %>',<%=k %>)" <%} %>>
		<%
			if (x == 1) {
							out.print(arr[i][j].split(";")[1]);
						}
		%>
		</td>
		<%
			} else {
		%>
		<td id=""><%=arr[i][j]%></td>
		<%
			}
				}
		%>
	</tr>
	<%
		}
	%>
</table>
</body>
</html>