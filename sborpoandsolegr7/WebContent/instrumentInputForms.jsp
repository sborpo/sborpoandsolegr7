<%@page import="cs236369.hw5.instrument.InstrumentManager" %>
<%@page import="cs236369.hw5.instrument.Instrument" %>
<%@page import="cs236369.hw5.instrument.AddNewInstrument" %>
<%@page import="cs236369.hw5.*" %>
<%
	String des=request.getParameter("decision");
User user=null;
String login=null;
//boolean isAdmin= UserUtils.isAdmin(request);
//if (des.equals("update")){login = request.getParameter("username");  user=InstrumentManager.getUserDetails(login); }
%>
<table> 
			
			 <tr> 
	  			<td class="label"><label id="lid" for="id">ID</label></td> 
	  			<td class="field"><input id="id" type="text"   maxlength="50" value="" name="<%=InstrumentManager.ID%>" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
	  		  <tr> 
	  		  	<td class="label"><label id="ltype" for="type">Type</label></td> 
	  		  	<td class="field"><input id="type"  type="text" value="" maxlength="100" name="<%=InstrumentManager.Type%>" /></td> 
	  		  	<td class="status"></td> 
	  		  </tr>
	  		  <tr> 
	  			<td class="label"><label id="lpermission" for="permission">Permission</label></td> 
	  			<td class="field"><input id="permission" type="text" maxlength="50" value="" name="<%=InstrumentManager.Premission%>"/></td> 
	  			<td class="status"></td> 
	  		  </tr> 
 	  		  <tr> 
	  			<td class="label"><label id="ltimeslot" for="timeslot">Time Slot</label></td> 
	  			<td class="field"><input id="timeslot" name="<%=InstrumentManager.TimeSlot%>" type="text" value="15" readonly="readonly" maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr>
	  		  <tr> 
	  			<td class="label"><label id="llocation" for="location">Location</label></td> 
	  			<td class="field"><input id="location" name="<%=InstrumentManager.Location%>" type="text" value="" maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
			    <tr> 
	  			<td class="label"><label id="ldescription" for="description">Description</label></td> 
	  			<td class="field"><input id="description" name="<%=InstrumentManager.Description%>" type="text" value="" maxlength="150" /></td> 
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
			   

