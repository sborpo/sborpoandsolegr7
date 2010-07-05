$(document).ready(function() {
	// validate signup form on keyup and submit
	var validator = $("#signupform").validate({
		rules: {
			name: "required",
			username: {
				required: true,
				minlength: 2
			},
			password: {
				required: true,
				minlength: 5
			},
			c_password: {
				required: true,
				minlength: 5,
				equalTo: "#password"
			},
			email: {
				required: true,
				email: true
			},
			address: {
				required: true,
				minlength: 5
			},
			phonenumber : {
				required: true,
				minlength: 9,
				number: true
			},
			group : {
				required: true
			}
		},
		messages: {
			name: "Enter your name",
			username: {
				required: "Enter a username",
				minlength: jQuery.format("Enter at least {0} characters")
			//	remote: jQuery.format("{0} is already in use")
			},
			password: {
				required: "Provide a password",
				rangelength: jQuery.format("Enter at least {0} characters")
			},
			c_password: {
				required: "Repeat your password",
				minlength: jQuery.format("Enter at least {0} characters"),
				equalTo: "Enter the same password as above"
			},
			address: {
				required: "Enter an address",
				minlength: jQuery.format("Enter at least {0} characters")
			},
			phonenumber : {
				required: "Enter your phone number",
				minlength: jQuery.format("Enter at least {0} characters"),
				number: "Enter digits only"
			},
			group : {
				required: "Enter your group"
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
	
 
});