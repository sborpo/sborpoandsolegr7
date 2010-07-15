var lastBrother;
var numOfReq;
function init()
{
	lastBrother=document.getElementById('pls');
}

function searchfunc()
{
	var k=document.getElementById('kinput');
	if (!validateK(k.value))
	{
		return;
	}
	var check;
	var checkBoxColl=document.getElementsByName('selection');
	var searchTableI=document.getElementById('searchTable');
	var keys=document.getElementById('instkeys');
	var tut=document.getElementById('pls');
	var instLabel=document.getElementById('instLabel');
	var slotsLabel=document.getElementById('slotsLabel');
	var saerchDiv=document.getElementById('searchStuff');
	saerchDiv.style.display =  'none' ;
	tut.innerHTML='Searching...';
	crateResultTable();
	numOfReq=0;
	numOfReq=numOfRequests(checkBoxColl);
	for (var i=0; i<checkBoxColl.length; i++)
	{
		if (checkBoxColl[i].checked==true)
		{
		var request=getRequestObject();
		var URL = "SearchLab?labname="+encodeURIComponent(checkBoxColl[i].value)+"&keywords="+encodeURIComponent(keys.value)+"&slots="+encodeURIComponent(k.value);
		var handlerFunction = getReadyStateHandler(request, handleSearchReponse);
		request.onreadystatechange = handlerFunction;        
		request.open("GET",URL,true);               
		request.send(null);	
		}
	}
}

function numOfRequests(checkBoxColl)
{
	var counter=0;
	for (var i=0; i<checkBoxColl.length; i++)
	{
		if (checkBoxColl[i].checked==true)
		{
			counter++;
		}
	}
	return counter;
}
function crateResultTable()
{
	 var table = document.createElement('table');
	 table.id="resultTable";
	 table.style.display =  'none' ;
	 var headerRow = document.createElement('tr');
	 var nameH= document.createElement('th');
	 nameH.appendChild(document.createTextNode('Lab Name'));
	 var instH= document.createElement('th');
	 instH.appendChild(document.createTextNode('Instrument ID'));
	 var slotH= document.createElement('th');
	 slotH.appendChild(document.createTextNode('Slot'));
	 headerRow.appendChild(nameH);
	 headerRow.appendChild(instH);
	 headerRow.appendChild(slotH);
	 table.appendChild(headerRow);
	 var body=document.getElementsByTagName('body');
	 var saerchDiv=document.getElementById('searchStuff');
	 body[0].insertBefore( document.createElement('br'),saerchDiv);
	 body[0].insertBefore(table,saerchDiv);
	 body[0].insertBefore( document.createElement('br'),saerchDiv);
}

function handleSearchReponse(req)
{
    var slots=req.responseXML.getElementsByTagName('slotElem');
	var table=document.getElementById('resultTable');
    for (var i=0;i<slots.length;i++)
	{
    	var newRow = document.createElement('tr');
        var name=document.createElement('td');
        var instId=document.createElement('td');
        var timeSlot=document.createElement('td');
        name.appendChild(document.createTextNode(getNodeValue(slots[i],'labName')));
        instId.appendChild(document.createTextNode(getNodeValue(slots[i],'instid')));
    	timeSlot.appendChild(document.createTextNode(getNodeValue(slots[i],'timeSlot')));
    	newRow.appendChild(name);
    	newRow.appendChild(instId);
    	newRow.appendChild(timeSlot);
    	table.appendChild(newRow);
    	
    
	}
    table.style.display = '';
	 numOfReq--;
	sleep(1);
	 if (numOfReq==0)
	 {
		 searchCompleted();
	 }
}

function searchCompleted()
{
	 var body=document.getElementsByTagName('body');
	 var saerchDiv=document.getElementById('searchStuff');
	 body[0].insertBefore( document.createElement('br'),saerchDiv);
	 var heading=document.createElement('h3');
	 heading.className='searchPageText';
	 heading.innerHTML='Search Completed!';
	 body[0].insertBefore(heading,saerchDiv);
	var tut=document.getElementById('pls');
	 tut.style.display = 'none';
}


function sleep (naptime){
    naptime = naptime * 1000;
    var sleeping = true;
    var now = new Date();
    var alarm;
    var startingMSeconds = now.getTime();
    while(sleeping){
        alarm = new Date();
        alarmMSeconds = alarm.getTime();
        if(alarmMSeconds - startingMSeconds > naptime){ sleeping = false; }
    }        
}

function getNodeValue(obj,tag)
{
	return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
}

function getReadyStateHandler(req, responseXmlHandler) {  
    // Return an anonymous function that listens to the   
    // XMLHttpRequest instance  
    return function () {  
    // If the request's status is "complete"  
        if (req.readyState == 4) {  
            // Check that a successful server response was received  
   
            if (req.status == 200) {                  //todo: catch 404 error.  
                // Pass the XML payload of the response to the   
                // handler function  
                responseXmlHandler(req);  
            }
            else
            {
            	numOfReq--;
            	if (numOfReq==0)
            	{
            		searchCompleted();
            	}
            }
    }  
}
}


function data_change(id)
{
	validateSlotsInput(id);
}

function getRequestObject(){
	if(window.XMLHttpRequest)
		{return(new XMLHttpRequest());} 
	else if (window.ActiveXObject) 
		{return(new ActiveXObject("Microsoft.XMLHTTP"));} 
	else{
		return(null);}
}





