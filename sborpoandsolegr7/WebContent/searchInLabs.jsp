<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.wsClients.YellowPagesRegistrator"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" media="screen" href="/sborpoandsolegr7/defualtCss.css" />   
<link rel="stylesheet" type="text/css" media="screen" href="/sborpoandsolegr7/css/tableCss.css" />  
<script src="/sborpoandsolegr7/js/common.js" type="text/javascript"></script> 
<script src="/sborpoandsolegr7/js/searchInLabs.js" type="text/javascript"></script> 
<style type="text/css">
.searchPageText
{
margin-left: 15%;
}
td.selection{padding: 1px; width: 10%;}
</style>
<%String [] services=YellowPagesRegistrator.getServicesFromYellow(); %>
<title>Search For Available Slots In Other Labs</title>

</head>
<body>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
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
<div id="searchStuff">
<label class="searchPageText" id="instLabel">Instrument Keywords </label><input type="text" id="instkeys" value=""></input>&nbsp;&nbsp;<label id="slotsLabel">Number Of Consicutive Slots: </label><input type="text" id="kinput" value="" maxlength="4" onchange="javascript:data_change('kinput')"  ></input>
<br/>
<br/>
<table id="searchTable">
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
<button class="searchPageText" type="button" onclick="javascript:searchfunc()">Search</button>
<%} %>
</div>
<br/>
<br/>
</body>
</html>