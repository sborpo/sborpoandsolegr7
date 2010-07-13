var lastBrother;

function init()
{
	lastBrother=document.getElementById('pls');
}

function searchfunc()
{
	var check;
	var checkBoxColl=getElementsByName("selection");
	for (check in checkBoxColl)
	{
		var request=getRequestObject();
		var URL = "SearchLab?labname=?keywords=?slots=?";
		var handlerFunction = getReadyStateHandler(req, handleSearchReponse);    // Set the handler function to receive callback notifications from the request object  
		 req.onreadystatechange = handlerFunction;        
		request.open("GET",URL,true);               
		request.send(null);	
	}
}

function handleSearchReponse(req)
{
    request.responseXML.getElementsByTagName("name");

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





