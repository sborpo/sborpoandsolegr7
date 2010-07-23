<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="cs236369.hw5.users.UserManager"%>
<%@page import="java.util.LinkedList"%>
<%@page import="cs236369.hw5.User.UserType"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View System's Users</title>
<link rel="stylesheet" type="text/css" href="defualtCss.css" />
<link href="/sborpoandsolegr7/css/tableCss.css" rel="stylesheet" type="text/css">
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
	   var tblRows=document.getElementById('users_table').rows;
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
tr.groupLeader {background-color:#e4f3b0;} 
tr.userTypesRow {background-color:yellow;}
td.groupMemberUsername {padding-left: 18px;}
</style>
</head>
<%@page import="cs236369.hw5.*" %>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<body>
<table id="users_table">
<tr>
<th>Username</th><th>Name</th><th>Email</th><th>Permissions</th>
</tr>
<tr class="userTypesRow">
<td colspan="4">Researchers</td>
</tr>
<%
LinkedList<User> users= UserManager.getUsersByGroups();
for ( User user : users ){ if (!user.getRole().equals(User.UserType.REASEARCHER)){continue;}
%>
<%if (user.isGroupLeader()){ %>
<tr class="groupLeader" id="<%=user.getGroup() %>">
<td><input id="in_<%=user.getLogin() %>" type="hidden" value="shown" ></input><img src="/sborpoandsolegr7/images/minus.gif" id="im_<%=user.getLogin()%>" onclick="handleClick('<%=user.getLogin() %>','in_<%=user.getLogin() %>','im_<%=user.getLogin() %>')" align="left">&nbsp;&nbsp;<a href=viewUser.jsp?username=<%=user.getLogin()%>><%=user.getLogin() %></a></td><td><%=user.getName() %></td><td><%=user.getEmail() %></td><td><%=user.getPermissionsView()%></td>
</tr>
<%}else { %>
<tr class="groupMember" title="<%=user.getGroup() %>">
<td class="groupMemberUsername"><a href=viewUser.jsp?username=<%=user.getLogin()%>><%=user.getLogin() %></a></td><td><%=user.getName() %></td><td><%=user.getEmail() %></td><td><%=user.getPermissionsView() %></td>
</tr>
<%}} %>
<tr class="userTypesRow">
<td colspan="4">Administrators</td>
</tr>
<%for ( User user : users ){ if (!user.getRole().equals(User.UserType.ADMIN)){continue;}%>
<tr>
<td><a href=viewUser.jsp?username=<%=user.getLogin()%>><%=user.getLogin() %></a></td><td><%=user.getName() %></td><td><%=user.getGroup() %></td><td><%=user.getPermissionsView() %></td>
</tr>
<% } %>
</table>
</body>
</html>