<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.xslt.XsltTransformer"%><html>
<head>
<link rel="stylesheet" type="text/css" media="screen" href="defualtCss.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
div.centeredBox
{
width:60%;
text-align: left;
}
</style>
</head>
<body>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<br/>
<br/>
<br/>
<div class="centeredBox">
<form action="XMLReportGenerator"  method="get">
Please Choose Your XSLT Type:<br/>
<select name="styleId">
<option value="1">Reserved Slots Of Instruments Per Group</option>
<option value="2">Reserved Slots Of Instruments Per Group</option>
<% if (XsltTransformer.xsltExists(request.getUserPrincipal().getName())){ %><option value="3">Your Uploaded XSLT Report</option><%} %>
</select>
<br/>
<input value="Generate Report" type="submit" onclick="this.form.target='_blank';return true;"/>
</form>
<br/>
<br/>
<a href="/sborpoandsolegr7/uploadXsltFile.jsp">You can upload a new XSLT stylesheet.</a>
</div>
</body>
</html>