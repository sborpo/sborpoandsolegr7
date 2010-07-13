var lastBrother;

function init()
{
	lastBrother=document.getElementById('pls');
}

function searchfunc()
{
	var check;
	var checkBoxColl=document.getElementsByName('selection');
	var keys=document.getElementById('instkeys');
	var k=document.getElementById('kinput');
	for (var i=0; i<checkBoxColl.length; i++)
	{
		if (checkBoxColl[i].checked==true)
		{
		var request=getRequestObject();
		var URL = "SearchLab?labname="+encodeURIComponent(checkBoxColl[i].value)+"&keywords="+encodeURIComponent(keys.value)+"&slots="+encodeURIComponent(k.value);
		var handlerFunction = new function(){
//			var slots=request.responseXML.getElementsByTagName('slotElem');
		    var table = document.createElement('table');
		    var headerRow = document.createElement('tr');
		    var nameH= document.createElement('th').appendChild(document.createTextNode('Lab Name'));
		    var instH= document.createElement('th').appendChild(document.createTextNode('Instrument ID'));
		    var slotH= document.createElement('th').appendChild(document.createTextNode('Slot'));
//		    headerRow.appendChild(mameH);
//		    headerRow.appendChild(instH);
//		    headerRow.appendChild(slotH);
//		    table.appendChild(headerRow);
//		    for (var i=0;i<slots.length;i++)
//			{
//		    	var newRow = document.createElement('tr');
//		        newRow.appendChild(document.createElement('td').appendChild(document.createTextNode(getNodeValue(books[i],'labName'))));
//		    	newRow.appendChild(document.createElement('td').appendChild(document.createTextNode(getNodeValue(books[i],'instid'))));
//		    	newRow.appendChild(document.createElement('td').appendChild(document.createTextNode(getNodeValue(books[i],'timeSlot'))));
//		    	table.appendChild(newRow);
//			}
//		    var body=document.getElementsByTagName('body');
//		    body[0].appendChild(table);
//		    body[0].appendChild( document.createElement('br'));		
		};   // Set the handler function to receive callback notifications from the request object  
		req.onreadystatechange = handlerFunction;        
		request.open("GET",URL,true);               
		request.send(null);	
		}
	}
}

function handleSearchReponse(req)
{
    var slots=request.responseXML.getElementsByTagName('slotElem');
    var table = document.createElement('table');
    var headerRow = document.createElement('tr');
    var nameH= document.createElement('th').appendChild(document.createTextNode('Lab Name'));
    var instH= document.createElement('th').appendChild(document.createTextNode('Instrument ID'));
    var slotH= document.createElement('th').appendChild(document.createTextNode('Slot'));
    headerRow.appendChild(mameH);
    headerRow.appendChild(instH);
    headerRow.appendChild(slotH);
    table.appendChild(headerRow);
    for (var i=0;i<slots.length;i++)
	{
    	var newRow = document.createElement('tr');
        newRow.appendChild(document.createElement('td').appendChild(document.createTextNode(getNodeValue(books[i],'labName'))));
    	newRow.appendChild(document.createElement('td').appendChild(document.createTextNode(getNodeValue(books[i],'instid'))));
    	newRow.appendChild(document.createElement('td').appendChild(document.createTextNode(getNodeValue(books[i],'timeSlot'))));
    	table.appendChild(newRow);
	}
    var body=document.getElementsByTagName('body');
    body[0].appendChild(table);
    body[0].appendChild( document.createElement('br'));
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
            }else{  
                // An HTTP problem has occurred  
                //alert("HTTP error: "+req.status);  
                $('lblError').innerHTML += "HTTP error: "+req.status;  
            }  
        }  
    }  
}       


function getRequestObject(){
	if(window.XMLHttpRequest)
		{return(new XMLHttpRequest());} 
	else if (window.ActiveXObject) 
		{return(new ActiveXObject("Microsoft.XMLHTTP"));} 
	else{
		return(null);}
}





