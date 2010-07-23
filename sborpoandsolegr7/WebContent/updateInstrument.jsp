<%if (request.getParameter(InstrumentManager.ID)==null){ %><jsp:forward page="ParamErrorSetter"></jsp:forward><%} %>
<%if (!InstrumentManager.isInstrumentExists(Integer.parseInt(request.getParameter(InstrumentManager.ID)))){ 		
		ErrorInfoBean err= new ErrorInfoBean();
		err.setErrorString("Instrument Error");
		err.setReason("Instrument does not exists in the system!");
		err.setLink("javascript:history.back(1);");
		err.setLinkStr("Back");
		request.setAttribute("ErrorInfoBean", err);%><jsp:forward page="/errorPages/errorPage.jsp"></jsp:forward><%} %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 

<%@page import="cs236369.hw5.instrument.InstrumentManager"%>
<%@page import="cs236369.hw5.ErrorInfoBean"%>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<%String decision="update";%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> 

<title>Update Instrument</title> 
 

<link rel="stylesheet" type="text/css" media="screen" href="addUser.css" /> 
<link rel="stylesheet" type="text/css" media="screen" href="defualtCss.css" />  
<link rel="stylesheet" type="text/css" media="screen" href="http://jquery.bassistance.de/validate/demo/css/chili.css" /> 

<script src="http://jquery.bassistance.de/validate/lib/jquery.js" type="text/javascript"></script> 
<script src="http://jquery.bassistance.de/validate/jquery.validate.js" type="text/javascript"></script> 
<script src="js/common.js" type="text/javascript"></script> 
<style type="text/css"> 
	pre { text-align: left; }
</style> 
 
<script src="dynamicTestForUpdateInstrument.js" type="text/javascript"></script> 

</head>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<body> 
<div id="main"> 
 
<div id="content"> 

<div style="clear: both;"><div></div></div> 
 
 
<div class="content"> 
    <div id="signupbox"> 
       <div id="signuptab"> 
        <ul> 
          <li id="signupcurrent">Update Instrument Details</li> 
        </ul> 
      </div> 
      <div id="signupwrap"> 
      		 <form id="signupform" action="UpdateInstrument" enctype="multipart/form-data" method="post"> 
	  		 <jsp:include page="instrumentInputForms.jsp">
	  		 	<jsp:param value="<%=decision %>" name="decision"/>
	  		 	<jsp:param value="<%=request.getParameter("id") %>" name="id"/>
	  		 </jsp:include>
<br/>
			<input value="Update Instrument" type="submit"/>&nbsp;&nbsp;&nbsp;&nbsp;<input value="Clear" type="reset"/>
</form>
      </div> 
    </div> 
</div> 
 
</div> 
<br/>
 
</div> 
 
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript"> 
</script> 
<script type="text/javascript"> 
_uacct = "UA-2623402-1";
urchinTracker();
</script> 
 
<script src="http://jquery.bassistance.de/validate/demo/js/chili-1.7.pack.js" type="text/javascript"></script> 
 
</body> 
</html>