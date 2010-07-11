
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
