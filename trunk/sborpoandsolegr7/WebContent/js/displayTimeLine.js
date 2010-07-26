function poorman_close(id)
{
	var tr = document.getElementById(id);
	if (tr==null) { return; }
	tr.style.display =  'none' ;
}

function poorman_open(id)
{
	var tr = document.getElementById(id);
	if (tr==null) { return; }
	tr.style.display =  '' ;
}

function saveHourToServer(from,to) {
	var request = new XMLHttpRequest();
	request.open("GET", "ReservationTimesSaver?fromHour="+from+"&toHour="+to, false);
	request.send(null);
	
}

function setFrom(s,lastIndex)
{
	var val=parseInt(s);
	var ToHour = document.getElementById('toHour');
	var idx= ToHour.selectedIndex;
	var FromHour = document.getElementById('fromHour');
	var ToVal= parseInt(ToHour.options[idx].value);
	if (val >ToVal	)
	{
		FromHour.selectedIndex = parseInt(document.getElementById('fromHourPrev').value)-1;
		alert('You cannot set the From hour to be after the To hour');
		return;
	}
	document.getElementById('fromHourPrev').value=val;
	updateRows(val,ToVal,lastIndex);
	saveHourToServer(val,ToVal);
	
	
}

function setTo(s,lastIndex)
{
	var val=parseInt(s);
	var FromHour = document.getElementById('fromHour');
	var idx= FromHour.selectedIndex;
	var FromHour = document.getElementById('toHour');
	var FromVal= parseInt(FromHour.options[idx].value);
	if (val < FromVal	)
	{
		FromHour.selectedIndex = parseInt(document.getElementById('toHourPrev').value)-1;
		alert('You cannot set the To hour to be before the From hour');
		return;
	}
	document.getElementById('toHourPrev').value=val;
	updateRows(FromVal,val,lastIndex);
	saveHourToServer(FromVal,val);
}


function updateRows(from,to,lastIndex)
{
	var i;
	for (i=1;i<from;i++)
	{
		poorman_close(i);
	}
	for (i=from;i<=to;i++)
	{
		poorman_open(i);
	}
	for (i=to+1; i<lastIndex; i++)
	{
		poorman_close(i);
	}
	
}

function gotoOptionsScreen(i,j,year,month,day,instrumnetType,k) {
	document.location.href = "optionsPage.jsp?" + "i=" + i + "&j=" + j + "&year=" + year +"&month=" + month + "&day=" + day +"&type=" + instrumnetType + "&k=" + k;
}


