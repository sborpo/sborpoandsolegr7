<%if ((request.getUserPrincipal()!=null)&&(request.getUserPrincipal().getName()!=null)){ %>
<p>
Logged in as: <b><%=request.getUserPrincipal().getName()%></b>&nbsp;&nbsp;&nbsp;&nbsp;<a href=viewUser.jsp?username=<%=request.getUserPrincipal().getName()%>>View Details</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="Logout">Log-Out</a>
</p>
<hr/>
<br/>
<br/>
<%} %>