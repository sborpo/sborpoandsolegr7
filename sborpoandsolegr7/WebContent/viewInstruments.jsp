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

<script type="text/javascript">
function handleClick(rowname,isshown,imagen)  {
	var val=document.getElementById(isshown);
	if (val.value=='shown')
	{
		 document.getElementById(imagen).src= '/sborpoandsolegr7/images/plus.gif';
		 toggleDisplay(rowname);
		 val.value='notshown';
	}
	else
	{
		document.getElementById(imagen).src='/sborpoandsolegr7/images/minus.gif';
		 toggleDisplay(rowname);
		 val.value='shown';
	}
}
function toggleDisplay(rowname) {
	   var tblRows=document.getElementById('instruments_table').rows;
	   for (i = 0; i < tblRows.length; i++) {
	      if (tblRows[i].title == rowname) {
		      if ( tblRows[i].style.display =="none")
		      {
		    	  tblRows[i].style.display="";
		      }
		      else
		      {
		    	  tblRows[i].style.display="none";
		      }
	      }
	   }
}
</script>


<style type="text/css">
table
{
border-collapse:collapse;
width:90%;
}
th
{
height:40 px;
}
td
{
padding:15 px;
}
table,th, td
{
border: 1px solid black;
}
tr.groupLeader {background-color:#e4f3b0;} 
tr.userTypesRow {background-color:yellow;}
td.groupMemberUsername {padding-left: 18px;}
</style>
</head>
<%@page import="cs236369.hw5.*" %>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<body>
<table id="instruments_table">
<tr>
<th>ID</th><th>Type</th><th>Permission</th><th>Location</th><th>Description</th>
</tr>

<%
LinkedList<Instrument> instruments = InstrumentManager.getInstruments();
for ( Instrument instrument : instruments ){
%>
<tr class="instruments" id="instruments">
<td><input id="in_<%=instrument.getId() %>" type="hidden" value="shown" ></input><img src="/sborpoandsolegr7/images/minus.gif" id="im_<%=instrument.getId()%>" onclick="handleClick('<%=instrument.getId() %>','in_<%=instrument.getId() %>','im_<%=instrument.getId()%>')" align="left">&nbsp;&nbsp;<a href=viewInstrument.jsp?id=<%=instrument.getId()%>><%=instrument.getId() %></a></td><td><%=instrument.getType() %></td><td><%=instrument.getPremission() %></td><td><%=instrument.getLocation() %></td><td><%=instrument.getDescription() %></td>
</tr>
<% }%>
</table>
</body>
</html>