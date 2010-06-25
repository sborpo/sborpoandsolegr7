<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>See Available Reservations</title>
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
<style type="text/css">
table
{
border-collapse:collapse;
width:90%;
border-top: none;
}
th
{
height:3em;
text-align:center;
}
td
{
padding:1em;
text-align:center;
}
table,th, td
{
border: 1px solid black;
}
td.notAvailible {background-color:red;}
td.taken {background-color:yellow;}
td.available {background-color:lightgreen;}
</style>

</head>
<jsp:include page="sessionDetailsHeader.jsp"></jsp:include>
<body>

<%@page import="cs236369.hw5.*" %>
<%
	int day= Integer.parseInt(request.getParameter("day"));
	int month= Integer.parseInt(request.getParameter("month"));
	int year= Integer.parseInt(request.getParameter("year"));
	int k =  Integer.parseInt(request.getParameter("slotSequence"));
	String type = request.getParameter("type");
	String[][] arr= new ReservationManager.ReservationTable(year,month,day,k,type).getReservationTable();
	TimeSlot weekEnd=new TimeSlot(year,new TimeSlot(year,month,day).getSlotNumber()+TimeSlot.numOfSlotsInDay()*6);
	TimeSlot nextWeek=new TimeSlot(year,new TimeSlot(year,month,day).getSlotNumber()+TimeSlot.numOfSlotsInDay()*7);
	TimeSlot prevWeek=new TimeSlot(year,new TimeSlot(year,month,day).getSlotNumber()-TimeSlot.numOfSlotsInDay()*7);
%>
<h3>You can see the reservations scheduale on the week between: <%=day%>/<%=month%>/<%=year%> to: <%=weekEnd.getDay()%>/<%=weekEnd.getMonth() %>/<%=weekEnd.getYear() %></h3>
<br></br>
<br></br>
<table>
<tr style="border: none">
<td colspan="4" style="text-align: left;border: none"><a href=/sborpoandsolegr7/displayResvTimeline.jsp?year=<%=prevWeek.getYear()%>&month=<%=prevWeek.getMonth()%>&day=<%=prevWeek.getDay()%>&type=<%=type%>&slotSequence=<%=k%>>&lt;&lt;</a> Previous Week</td><td colspan="4" style="text-align:right;border: none">Next Week  <a href=/sborpoandsolegr7/displayResvTimeline.jsp?year=<%=nextWeek.getYear()%>&month=<%=nextWeek.getMonth()%>&day=<%=nextWeek.getDay()%>&type=<%=type%>&slotSequence=<%=k%>> &gt;&gt;</a></td>
</tr>
<tr>
<%for (int j=0; j<8; j++){%>
 <th><%=arr[0][j] %></th>
 <%} %>
 </tr>
 <%for (int i=1; i<arr.length; i++){ %>
<tr>
<%for (int j=0; j<arr[i].length; j++) {
	if (j!=0){
	String classcolor=null;
	int x=Integer.valueOf( arr[i][j].split(";")[0]);
	switch (x)
	{ case (-1) : classcolor="notAvailible";
					break;
	  case(0) : classcolor="taken";
					break;
	  case(1): classcolor="available";
	}
		%>
		<td class="<%=classcolor %>"><%if (x==1){
			out.print( arr[i][j].split(";")[1]);
		}%></td>
	<%}
	else
	{%>
		<td><%=arr[i][j]%></td>
	<%}}%>
</tr>
<%} %>
</table>
</body>
</html>