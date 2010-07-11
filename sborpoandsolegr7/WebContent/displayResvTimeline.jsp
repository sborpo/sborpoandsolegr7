<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>See Available Reservations</title>
<link rel="stylesheet" type="text/css" href="addUser.css" />

  <script type="text/javascript">

function poorman_close(id)
{
	var tr = document.getElementById(id);
	if (tr==null) { return; }
	tr.style.display =  'none' ;
}

function poorman_open(id)
{
	var tr = document.getElementById(id);
	if (tr==null) { return; }
	tr.style.display =  '' ;
}

function saveHourToServer(from,to) {
	var request = new XMLHttpRequest();
	request.open("GET", "ReservationTimesSaver?fromHour="+from+"&toHour="+to, false);
	request.send(null);
}

function setFrom(s,lastIndex)
{
	var val=parseInt(s);
	var ToHour = document.getElementById('toHour');
	var idx= ToHour.selectedIndex;
	var FromHour = document.getElementById('fromHour');
	var ToVal= parseInt(ToHour.options[idx].value);
	if (val >ToVal	)
	{
		FromHour.selectedIndex = parseInt(document.getElementById('fromHourPrev').value)-1;
		alert('You cannot set the From hour to be after the To hour');
		return;
	}
	document.getElementById('fromHourPrev').value=val;
	updateRows(val,ToVal,lastIndex);
	saveHourToServer(val,ToVal);
	
	
}

function setTo(s,lastIndex)
{
	var val=parseInt(s);
	var FromHour = document.getElementById('fromHour');
	var idx= FromHour.selectedIndex;
	var FromHour = document.getElementById('toHour');
	var FromVal= parseInt(FromHour.options[idx].value);
	if (val < FromVal	)
	{
		FromHour.selectedIndex = parseInt(document.getElementById('toHourPrev').value)-1;
		alert('You cannot set the To hour to be before the From hour');
		return;
	}
	document.getElementById('toHourPrev').value=val;
	updateRows(FromVal,val,lastIndex);
	saveHourToServer(FromVal,val);
}


function updateRows(from,to,lastIndex)
{
	var i;
	for (i=1;i<from;i++)
	{
		poorman_close(i);
	}
	for (i=from;i<=to;i++)
	{
		poorman_open(i);
	}
	for (i=to+1; i<lastIndex; i++)
	{
		poorman_close(i);
	}
	
}





</script>

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

div.hours
{
text-align:center;
}
</style>

</head>
<jsp:include page="sessionDetailsHeader.jsp"></jsp:include>


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
	Integer startHourTime= (Integer)request.getSession(true).getAttribute("fromHour");
	Integer endHourTime= (Integer)request.getSession(true).getAttribute("toHour");
	Integer startTime= (startHourTime!=null)? startHourTime : TimeSlot.numOfSlotsInHour()*8+1;
	Integer endTime= (startHourTime!=null)? endHourTime : TimeSlot.numOfSlotsInHour()* 12 +1;
%>
<body onload="javascript: updateRows(<%= startTime %>,<%= endTime%>,<%=arr.length %>);">
<h3>You can see the reservations scheduale on the week between: <%=day%>/<%=month%>/<%=year%> to: <%=weekEnd.getDay()%>/<%=weekEnd.getMonth() %>/<%=weekEnd.getYear() %></h3>
<br></br>
<div class="hours">
Display Hours: From:&nbsp;&nbsp;<input type="hidden" id="fromHourPrev" value="<%=startTime %>"><select id="fromHour"  onchange="javascript:setFrom(value,<%=arr.length %>)"><%for (int i=1; i<arr.length; i++) { %><option value="<%=i %>" <%if (i==startTime) { %>selected="selected"<%} %>><%=arr[i][0]%></option><%} %></select>&nbsp;&nbsp;To:&nbsp;&nbsp;<input type="hidden" id="toHourPrev" value="<%=endTime %>"><select id="toHour" onchange="javascript:setTo(value,<%=arr.length %>)">
<%for (int i=1; i<arr.length; i++) { %>
	<option value="<%=i %>" <%if (i==endTime) { %>selected="selected"<%} %>><%=arr[i][0]%></option>
<%} %>
</select>
</div>
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
<tr id="<%=i %>">
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