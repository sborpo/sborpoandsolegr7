<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.instrument.InstrumentManager"%>
<%@page import="cs236369.hw5.instrument.Instrument"%>
<%@page import="java.util.LinkedList"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View System's Users</title>
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
<link href="/sborpoandsolegr7/css/tableCss.css" rel="stylesheet" type="text/css">

</head>
<%@page import="cs236369.hw5.*" %>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<body>
<table id="instruments_table">
<tr>
<th>ID</th><th>Type</th><th>Permission</th><th>Location</th>
</tr>

<%
LinkedList<Instrument> instruments = InstrumentManager.getInstruments();
for ( Instrument instrument : instruments ){
%>
<tr class="instruments" id="instruments">
<td><input id="in_<%=instrument.getId() %>" type="hidden" value="shown" ></input>&nbsp;&nbsp;<a href=viewInstrument.jsp?id=<%=instrument.getId()%>><%=instrument.getId() %></a></td><td><%=instrument.getType() %></td><td><%=instrument.getPremission() %></td><td><%=instrument.getLocation() %></td>
</tr>
<% }%>
</table>
</body>
</html>