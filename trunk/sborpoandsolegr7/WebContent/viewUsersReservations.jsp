<%if (request.getParameter(UserManager.Usern)==null){ %><jsp:forward page="ParamErrorSetter"></jsp:forward><%} %>
<%boolean isAdmin=UserUtils.isAdmin(request);%>
<%if ((!request.getUserPrincipal().getName().equals(request.getParameter(UserManager.Usern)))&& (!isAdmin)) {%>
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
<link rel="stylesheet" type="text/css" media="screen" href="defualtCss.css" /> 
<link rel="stylesheet" type="text/css" media="screen" href="/sborpoandsolegr7/css/tableCss.css" /> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<br/>
<br/>
<br/>
<%ArrayList<ReservationManager.UserReservation> resv=(isAdmin)? ReservationManager.getAllReservations() : ReservationManager.getUserReservations(request.getUserPrincipal().getName()); %>
<%if (resv.size()==0){ %>
<div class="centeredBox">There are no reserved time slots for you!</div>
<%}else{ %>
<table>
<tr>
<th>Instrument Id</th><th>Date</th><th>Start Time</th><%if (isAdmin){ %><th>Username</th><%} %><th></th>
</tr>
<%for (int i=0; i<resv.size(); i++){ %>
<tr>
<td><%=resv.get(i).getIntsId() %></td><td><%=resv.get(i).getTimeslot().getDatePrint() %></td><td><%=TimeSlot.getSlotTime(resv.get(i).getTimeslot().getSlotNumber())%></td><%if (isAdmin){ %><td><%=resv.get(i).getUsername() %></td><%} %><td><a href="DeleteReservedTimeSlot?id=<%=resv.get(i).getIntsId()%>&year=<%=resv.get(i).getTimeslot().getYear() %>&slotNum=<%=resv.get(i).getTimeslot().getSlotNumber() %>">Remove</a></td>
</tr>
<%} %>
</table>
<%} %>
</body>
</html>