<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.wsClients.YellowPagesRegistrator"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" media="screen" href="/sborpoandsolegr7/defualtCss.css" />   

<style type="text/css">
.searchPageText
{
margin-left: 15%;
}
table
{
margin-left:15%;
margin-right:15%;
border-collapse:collapse;
width:70%;
}
th
{
text-align:center;
height:40 px;
}
table,th, td
{
border: 1px solid black;
}
td.selection{padding: 1px; width: 10%;}
</style>
<%String [] services=YellowPagesRegistrator.getServicesFromYellow(); %>
<title>Search For Available Slots In Other Labs</title>
<script src="searchInLabs.js" type="text/javascript"></script> 
</head>
<body>

<br/>
<h1>Available slots search</h1>
<br/>
<% if ((services==null) || (services.length==0) || (services[0]==null)){ %>
<div class="transbox">
<p >Unfortunately ,no web-services found in the labs yellow pages. <br/>Please try to search at your lab.</p>
</div>
<%}else{ %>
<br/>
<p id="pls" class="searchPageText"> Please select the labs that you want to search in from the following list: </p>
<br/>
<table>
<tr>
<th>Sel/Des</th><th>Lab's Search Web Service Location</th>
</tr>
<%for ( int i=0; i<services.length; i++){ %>
<tr>
<td class="selection"><input type="checkbox" id="<%=services[i] %>" name="selection" value="<%=services[i] %>" /></td><td><%=services[i] %></td>
</tr>
<%} %>
</table>
<br/>
<button class="searchPageText" type="button">Search</button>
<%} %>
</body>
</html>