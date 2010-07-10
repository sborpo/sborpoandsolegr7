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