
<%@page import="cs236369.hw5.instrument.InstrumentManager"%>
<%@page import="cs236369.hw5.instrument.Instrument"%><%
	System.out.println(request.getParameter("id"));
	if (!InstrumentManager.isInstrumentExists(Integer.parseInt(request.getParameter("id")))) {
		System.out.println(true);%>
	<%="true"%>
<%}
	else {System.out.println(false);%> 
		<%="false" %>
		
<%	}%>
