

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
	groupSelector.disabled=false;
	var i=0;
	while (i<currentSize)
	{
		if (groupSelector.options[i].value == userbox.value)
		{
			return;
		}
		i=i+1;
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
		groupSelector.disabled=true;
		if (currentSize>initialeSize)
		{
			groupSelector.remove(currentSize-1);
		}
		var newOpt=document.createElement('option');
		newOpt.text='root';
		newOpt.value='root';
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