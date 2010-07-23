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
<link rel="stylesheet" type="text/css" href="/sborpoandsolegr7/css/viewUserCss.css" />
</head>

<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>

<body>
<%
	String id = request.getParameter("id");
	Instrument inst=InstrumentManager.getInstrumentDetails(Integer.parseInt(id));
%>
<h1>Instrument Details</h1><br/><br/>
<table>
<tr>
<td>ID: <%=inst.getId()%></td>
</tr>
<tr>
<td>Type: <%=inst.getType() %></td>
</tr>
<tr>
<td>Premission: <%=inst.getPremission() %></td>
</tr>
<tr>
<td>Location:<%=(inst.getLocation()) %></td>
</tr>
<tr>
<td>Description: <textarea rows="4" cols="20"><%=inst.getDescription() %></textarea></td>
</tr>

</table>
<br/>
<br/>
<a href=updateInstrument.jsp?id=<%=id%>>Updated Instrument Details</a> <a href="DeleteInstrument?id=<%=id%>"> remove Instrument</a>
</body>
</html>