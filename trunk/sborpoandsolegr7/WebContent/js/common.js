
function poorman_open(id)
{
	var tr = document.getElementById(id);
	if (tr==null) { return; }
	tr.style.display =  '' ;
}

function poorman_close(id)
{
	var tr = document.getElementById(id);
	if (tr==null) { return; }
	tr.style.display =  'none' ;
}

function isNumeric(val)
{
	var check = true;
    for(var i=0;i < val.length; ++i)
    {
         var new_key = val.charAt(i); //cycle through characters
         if(((new_key < "0") || (new_key > "9")))
         {
              check = false;
              break;
         }
    }
    return check;
}


function validateK(val)
{
	var check = true;
    for(var i=0;i < val.length; ++i)
    {
         var new_key = val.charAt(i); //cycle through characters
         if(((new_key < "0") || (new_key > "9")) && 
              !(new_key == ""))
         {
              check = false;
              break;
         }
    }
    return check;
}


function validateSlotsInput(id)
{
	  var check = true;
	 	var field=document.getElementById(id);
	     var value = field.value; //get characters
	     //check that all characters are digits, ., -, or ""
	     check=validateK(value);
	     //apply appropriate colour based on value
	     if(!check)
	     {
	          field.style.backgroundColor = "red";
	     }
	     else
	     {
	          field.style.backgroundColor = "white";
	     }
}

function validatePhone(val)
{
	
	if (val.length<9)
	{
		return false;
	}
	return isNumeric(val);
	
}
