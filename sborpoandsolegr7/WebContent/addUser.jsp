<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@page import="cs236369.hw5.users.UserManager"%>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<%String decision="add";%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> 

<title>Add User</title> 
 
<link type="text/css" href="css/south-street/jquery-ui-1.8.2.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script> 

<link rel="stylesheet" type="text/css" media="screen" href="addUser.css" />
<link rel="stylesheet" type="text/css" media="screen" href="defualtCss.css" />   
<link rel="stylesheet" type="text/css" media="screen" href="http://jquery.bassistance.de/validate/demo/css/chili.css" /> 
<script src="http://jquery.bassistance.de/validate/lib/jquery.js" type="text/javascript"></script> 
<script src="http://jquery.bassistance.de/validate/jquery.validate.js" type="text/javascript"></script> 
<script src="dynamicTestForAddUser.js" type="text/javascript"></script> 
 
<style type="text/css"> 
	pre { text-align: left; }
</style> 
 

	<script type="text/javascript">
	// increase the default animation speed to exaggerate the effect
	$.fx.speeds._default = 1000;
	$(function() {
		$('#dialog').dialog({
			autoOpen: false,
			show: 'blind',
			hide: 'explode'
		});

		$('#dialog2').dialog({
			autoOpen: false,
			show: 'blind',
			hide: 'explode'
		});
		
		$('#opener').click(function() {
			$('#dialog').dialog('open');
			return false;
		});

		$('#opener2').click(function() {
			$('#dialog2').dialog('open');
			return false;
		});
	});
	</script>

 <script type="text/javascript"">

 
function poorman_close(id)
{
	var tr = document.getElementById(id);
	if (tr==null) { return; }
	tr.style.display =  'none' ;
}


function toggleGroupBox()
{
	var userTypeSelector=document.getElementById('usertype');
	//if admin is selected
	var val= userTypeSelector.options[userTypeSelector.selectedIndex].value;
	if (val=="Admin")
	{
		return;
	}
	var userbox = document.getElementById('username');
	var initialeSize = parseInt(document.getElementById('initialeGroupsNum').value);
	var groupSelector=document.getElementById('group');
	var currentSize = parseInt(groupSelector.length);
	var wasSelected;
	if (groupSelector.selectedIndex==(currentSize-1))
	{
		wasSelected=true;
	}
	else
	{
		wasSelected=false;
	}
	if (userbox.value.length<=1)
	{
		if (currentSize>initialeSize)
		{
			groupSelector.remove(currentSize-1);
		}
		groupSelector.disabled=true;
		return;
	}
	groupSelector.disabled=false;
	if (currentSize>initialeSize)
	{
		groupSelector.remove(currentSize-1);
	}
	var newOpt=document.createElement('option');
	newOpt.text='Me: '+userbox.value;
	newOpt.value=userbox.value;
	try
	  {
		groupSelector.add(newOpt,null); // standards compliant
	  }
	catch(ex)
	  {
		groupSelector.add(newOpt); // IE only
	  }
	if (wasSelected==true)
	{
		groupSelector.selectedIndex=parseInt(groupSelector.length)-1;
	}

}

function init()
{
	
	var hiddenInput = document.getElementById('initialeGroupsNum');
	hiddenInput.value= document.getElementById('group').length;
	document.getElementById('group').disabled=true;
	
	
}


function poorman_open(id)
{
	var tr = document.getElementById(id);
	if (tr==null) { return; }
	tr.style.display =  '' ;
}
function toggeleAdminAuth()
{
	var initialeSize = parseInt(document.getElementById('initialeGroupsNum').value);
	var groupSelector=document.getElementById('group');
	var currentSize = parseInt(groupSelector.length);
	var selectedUserType = document.getElementById('usertype');
	var val= selectedUserType.options[selectedUserType.selectedIndex].value;
	if (val=="Admin")
	{
		document.getElementById('adminAuth').value="";
		poorman_open('authentication_row');
		groupSelector.disabled=true;
		if (currentSize>initialeSize)
		{
			groupSelector.remove(currentSize-1);
		}
		var newOpt=document.createElement('option');
		newOpt.text='Administrators';
		newOpt.value='Administrators';
		try
		  {
			groupSelector.add(newOpt,null); // standards compliant
		  }
		catch(ex)
		  {
			groupSelector.add(newOpt); // IE only
		  }
		groupSelector.selectedIndex=parseInt(groupSelector.length)-1;
		  
		
	}
	else
	{
		document.getElementById('adminAuth').value="------";
		poorman_close('authentication_row');
		if (currentSize>initialeSize)
		{
			groupSelector.remove(currentSize-1);
		}
		groupSelector.disabled=false;
		toggleGroupBox();	
		groupSelector.selectedIndex=0;
	}
}
</script>
 
</head>
<%@page import="cs236369.hw5.*" %>
<jsp:include page="/sessionDetailsHeader.jsp"></jsp:include>
<body onload="javascript:init(); toggeleAdminAuth(); "> 





<div id="userGroupDialog" title="Hint">
							<p>If you are a researcher , you should provide here the name of your research group leader. <br/>
							If you are an administrator , your group will be automatically the administrators group</p>
							</div>
				<button id="userGroupDialogLink">Hint</button>
 			  	  			<div id="authDialog" title="Hint">
							<p>You should provide here an authentication string that every administrator should know
							if you have a problem please contact the support.</p>
							</div>
				<button id="authDialogLink">Hint</button>
				

	






<div id="dialog" title="Basic dialog">
	<p>This is an animated dialog which is useful for displaying information. The dialog window can be moved, resized and closed with the 'x' icon.</p>
</div>

<div id="dialog2" title="stam">
	<p>asThe dialog windo</p>
</div>


<button id="opener">Open Dialog</button>

<button id="opener2">Open Dialog2</button>

<h1 id="banner">Add User</h1> 
<div id="main"> 
 
<div id="content"> 

<div style="clear: both;"><div></div></div> 
 
 
<div class="content"> 
    <div id="signupbox"> 
       <div id="signuptab"> 
        <ul> 
          <li id="signupcurrent">Signup</li> 
        </ul> 
      </div> 
      <div id="signupwrap"> 
      		<form id="signupform" action="AddNewUser" enctype="multipart/form-data" method="post" action=""> 
      		<!-- form  action="AddNewUser" ENCTYPE="multipart/form-data" method="post"/> -->
	  		 <jsp:include page="userInputForms.jsp">
	  		 	<jsp:param value="<%=decision %>" name="decision"/>
	  		 </jsp:include>
<br/>
			<input value="AddUser" type="submit"/>&nbsp;&nbsp;&nbsp;&nbsp;<input value="Clear" type="reset"/>
</form>
      </div> 
    </div> 
</div> 
 
</div> 
 
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