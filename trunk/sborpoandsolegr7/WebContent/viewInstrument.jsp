<%if (request.getParameter("id")==null){ %><jsp:forward page="ParamErrorSetter"></jsp:forward><%} %>
<%if (!InstrumentManager.isInstrumentExists(Integer.parseInt(request.getParameter("id")))){ 		
		ErrorInfoBean err = new ErrorInfoBean();
		err.setErrorString("User Error");
		err.setReason("User not exists in the system!");
		err.setLink("javascript:history.back(1);");
		err.setLinkStr("Back");
		request.setAttribute("ErrorInfoBean", err);%><jsp:forward page="/errorPages/errorPage.jsp"></jsp:forward><%} %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="cs236369.hw5.users.UserManager"%>
<%@page import="cs236369.hw5.User"%>
<%@page import="cs236369.hw5.users.*"%>
<%@page import="cs236369.hw5.ErrorInfoBean"%>
<%@page import="cs236369.hw5.instrument.Instrument"%>
<%@page import="cs236369.hw5.instrument.InstrumentManager"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Instrument</title>
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
<link rel="stylesheet" type="text/css" href="/sborpoandsolegr7/css/viewInstrument.css" />
</head>

<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>

<body>
<%
	String id = request.getParameter("id");
	Instrument inst=InstrumentManager.getInstrumentDetails(Integer.parseInt(id));
%>
<h1>Instrument Details</h1><br/><br/>

<div class="table">
<div id=heading><div id="heading-text"><img src="/sborpoandsolegr7/images/lab_icon.png" />&nbsp;&nbsp;# <%=inst.getId()%></div></div>
<div id="details">
<div id="content">

<strong>Type: </strong><%=inst.getType() %><br/>

<strong>Premission: </strong><%=inst.getPremission() %><br/>

<strong>Location: </strong><%=(inst.getLocation()) %><br/>

<strong>Description: </strong><div id="descArea"><textarea id="textarea" rows="4" cols="20"><%=inst.getDescription() %></textarea></div>

</div>
</div>

<div class="options" ><div class="button-text">
<a href=updateInstrument.jsp?id=<%=id%>>Updated Instrument Details</a> || <a href="DeleteInstrument?id=<%=id%>"> remove Instrument</a>
</div></div>
</div>
</body>
</html>