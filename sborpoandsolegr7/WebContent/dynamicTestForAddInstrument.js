
	// validate signup form on keyup and submit
	var validator = $("#signupform").validate({
		rules: {
			type: {
				required: true,
				minlength: 2
			},
			permission: {
				required: true,
				number: true
			},
			location : {
				required: true,
				minlength: 5
			},
			timeslot: {
				number: true
			},
			description : {
				required: false
			}
		},
		messages: {
			type: {
				required: "Specify a type",
				minlength: jQuery.format("Enter at least {0} characters")
			//	remote: jQuery.format("{0} is already in use")
			},
			permission : {
				required: "Provide a permission",
				number: "Enter digits only"
			},
			timeslot: {
				number: "Enter digits only"
			},
			location: {
				required: "Enter a location",
				minlength: jQuery.format("Enter at least {0} characters")
			}
		},
		// the errorPlacement has to take the table layout into account
		errorPlacement: function(error, element) {
			if ( element.is(":radio") )
				error.appendTo( element.parent().next().next() );
			else if ( element.is(":checkbox") )
				error.appendTo ( element.next() );
			else
				error.appendTo( element.parent().next() );
		},
		// set this class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
		}
	});
	
 

