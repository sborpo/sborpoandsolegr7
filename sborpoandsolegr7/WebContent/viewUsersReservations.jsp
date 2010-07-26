<%if (request.getParameter(UserManager.Usern)==null){ %><jsp:forward page="ParamErrorSetter"></jsp:forward><%} %>
<%if ((!request.getUserPrincipal().getName().equals(request.getParameter(UserManager.Usern)))&& (!UserUtils.isAdmin(request))) {%>
<jsp:forward page="/sborpoandsolegr7/errorPages/unauthorized.html"></jsp:forward>
<%} %>
<%if (!UserManager.isUserExists(request.getParameter(UserManager.Usern))){ 		
%><jsp:forward page="/ParamErrorSetter"></jsp:forward><%} %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.users.UserManager"%>
<%@page import="cs236369.hw5.users.UserUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cs236369.hw5.ReservationManager.UserReservation"%>
<%@page import="cs236369.hw5.ReservationManager"%>
<%@page import="cs236369.hw5.TimeSlot"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<br/>
<br/>
<br/>
<%ArrayList<ReservationManager.UserReservation> resv= ReservationManager.getUserReservations(request.getUserPrincipal().getName()); %>
<%if (resv.size()==0){ %>
<div class="centeredBox">There are no reserved time slots for you!</div>
<%}else{ %>
<table>
<tr>
<th>Instrument Id</th><th>Date</th><th>Start Time</th><th>Remove</th>
</tr>
<%for (int i=0; i<resv.size(); i++){ %>
<tr>
<td><%=resv.get(i).getIntsId() %></td><td><%=resv.get(i).getTimeslot().getDatePrint() %></td><td><%=TimeSlot.getSlotTime(resv.get(i).getTimeslot().getSlotNumber())%></td><td><a href="DeleteReservedTimeSlot?id=<%=resv.get(i).getIntsId()%>&year=<%=resv.get(i).getTimeslot().getYear() %>&slotNum=<%=resv.get(i).getTimeslot().getSlotNumber() %>">Remove</a></td>
</tr>
<%} %>
</table>
<%} %>
</body>
</html>