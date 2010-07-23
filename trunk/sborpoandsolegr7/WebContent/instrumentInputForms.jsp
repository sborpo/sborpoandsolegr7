<%@page import="cs236369.hw5.instrument.InstrumentManager" %>
<%@page import="cs236369.hw5.instrument.Instrument" %>
<%@page import="cs236369.hw5.instrument.AddNewInstrument" %>
<%@page import="cs236369.hw5.*" %>
<%
	Instrument instrument=null;
	String des=request.getParameter("decision");
	if (des == null) {
		des = "";
	}
	if (!des.equals("add")){
	String id=request.getParameter("id");
	if (id == null) {
		des = "";
	}
	
	if (request.getParameter(InstrumentManager.ID)==null){ %>
	
	<%@page import="java.lang.NumberFormatException;"%><jsp:forward page="ParamErrorSetter"></jsp:forward>
	<%} %>
	<%
	try {
	if (!InstrumentManager.isInstrumentExists(Integer.parseInt(request.getParameter(InstrumentManager.ID)))){ 		
			ErrorInfoBean err= new ErrorInfoBean();
			err.setErrorString("Instrument Error");
			err.setReason("Instrument does not exists in the system!");
			err.setLink("javascript:history.back(1);");
			err.setLinkStr("Back");
			request.setAttribute("ErrorInfoBean", err);%>
			<jsp:forward page="/errorPages/errorPage.jsp"></jsp:forward>
			<%}
	}
	catch (NumberFormatException e) {%>
			<jsp:forward page="/errorPages/errorPage.jsp"></jsp:forward>
<%	} %>
<% 
 instrument = InstrumentManager.getInstrumentDetails(Integer.parseInt(request.getParameter(InstrumentManager.ID)));

}%>
<table> 
			
			 <tr> 
	  			<td class="label"><label id="lid" for="id">ID</label></td> 
	  			<td class="field"><input id="id" type="text"   maxlength="50" <% if(des.equals("update")) { %> value="<%=instrument.getId()%>" readonly="readonly" <% } else { %>value="" <%} %> name="<%=InstrumentManager.ID%>" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
	  		  <tr> 
	  		  	<td class="label"><label id="ltype" for="type">Type</label></td> 
	  		  	<td class="field"><input id="type"  type="text" <% if (des.equals("update")) { %> value="<%=instrument.getType()%>"  <% } else { %>value="" <%} %>  maxlength="100" name="<%=InstrumentManager.Type%>" /></td> 
	  		  	<td class="status"></td> 
	  		  </tr>
	  		  <tr> 
	  			<td class="label"><label id="lpermission" for="permission">Permission</label></td> 
	  			<td class="field"><input id="permission" type="text" maxlength="50" <% if (des.equals("update")) { %> value="<%=instrument.getPremission()%>"  <% } else { %>value="" <%} %>  name="<%=InstrumentManager.Premission%>"/></td> 
	  			<td class="status"></td> 
	  		  </tr> 
 	  		  <tr> 
	  			<td class="label"><label id="ltimeslot" for="timeslot">Time Slot</label></td> 
	  			<td class="field"><input id="timeslot" name="<%=InstrumentManager.TimeSlot%>" type="text" value="15" readonly="readonly" maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr>
	  		  <tr> 
	  			<td class="label"><label id="llocation" for="location">Location</label></td> 
	  			<td class="field"><input id="location" name="<%=InstrumentManager.Location%>" type="text" <% if (des.equals("update")) { %> value="<%=instrument.getLocation()%>"  <% } else { %>value="" <%} %>  maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
			    <tr> 
			    <!-- 
	  			<td class="label"><label id="ldescription" for="description">Description</label></td> 
	  			<td class="field"><input id="description" name="<%=InstrumentManager.Description%>" type="text" <% if (des.equals("update")) { %> value="<%=instrument.getDescription()%>"  <% } else { %>value="" <%} %>  maxlength="150" /></td> 
	  			<td class="status"></td> 
	  			 -->
	  			<td class="label"><label id="ldescription" for="description">Description</label></td> 
	  			<td class="field"><textarea id="description" name="<%=InstrumentManager.Description%>" rows="4" cols="20" ><% if (des.equals("update")) { %><%=instrument.getDescription()%><%}%></textarea></td> 
	  			<td class="status"></td>
	  		  </tr> 
			  <tr>
			  <td class="label"><label id="lcaptcha" for="captcha">Enter the letters below:</label></td>
			  	<td class="field"><input id="captcha" type="text" name="<%=InstrumentManager.Captcha%>" value="" />	</td>

			  </tr>
			    <tr>
			  	<td></td><td ><img src="jcaptcha.jpg" /></td>
			  </tr>
		
		
			 
</table>	
			   

