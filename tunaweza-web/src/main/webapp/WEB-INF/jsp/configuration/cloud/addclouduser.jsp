<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
		<form:form method="post" class="add_form"
			enctype="multipart/form-data" modelAttribute="addUserBean">

			<div id="adduser_errors" class="error"></div>
			
			

			<div class="container email">
				<div class="container_inner">
					<label class="title">Email</label>
				<p>Valid email addresses please - we will send you an email to 
                     confirm your registration</p>
					<form:input path="email" cssClass="forminput" />
				</div>
			</div>

			
			 <div class="container username">
				<div class="container_inner">
	           <label class="title">Set password</label>
	            	<span style="color:red;">*</span>
	            	<form:password path="password" cssClass="forminput password_strength_bar" />
	            </div>
	            </div> 
	            <div class="container username">
				<div class="container_inner">
		         <label class="title">confirm password</label>
	            	<span style="color:red;">*</span>
	            	<form:password path="repassword" cssClass="forminput" />
	            </div>
	            </div>
	            
	            <div class="container username">
				<div class="container_inner">
					<label class="title">First Name</label>

					<p>Letters and numbers only, 5 to 15 characters</p>
					<form:input path="firstName" cssClass="forminput" />
				</div>
			</div>
			
			<div class="container username">
				<div class="container_inner">
					<label class="title">Second Name</label>

					<p>Letters and numbers only, 5 to 15 characters</p>
					<form:input path="lastName" cssClass="forminput" />
				</div>
			</div>
			<div class="container country">
				<div class="container_inner">
					<label class="title">Location</label>
					<p>Where in the world are you?</p>
					<form:select path="location" cssClass="forminput">
						<form:option value="" label="-------------" />
						<form:options items="${LOCATIONLIST}" itemValue="locationName"
							itemLabel="locationName" />
					</form:select>
				</div>
			</div>





			<button type="submit" class="submit_button" style="margin: 51px;">Add
				User</button>
			<input type ="button" value="Cancel" onClick="JJTEACH.ajaxLoad('/user/listcloudusers'); return false;">	



		</form:form>
</div>
<script type="text/javascript">
	$j().ready(
			function() {
				$j("#addUserBean").submit(
						function() {
							var account = $j(this).serializeObject();
							$j('#adduser_errors').html("Processing..........");
							$j.postJSON($j('#addUserBean').attr('action'),
									account, function(data) {
										doaction(data);
									});
							return false;
						});
			});

	function doaction(data) {
		var message = data.u;
		$j('#adduser_errors').html(message);
		if (message == "Saved") {
			JJTEACH.ajaxLoad('/user/listcloudusers');
		}
	}
</script>

<script type="text/javascript">
	$j(function() {
		$j('.password_strength_bar').pstrength();
	});

	$j('.password_strength_bar').focusout(function() {
		$j('#password_text').hide();
		$j('#password_bar').hide();
	});

	$j('.password_strength_bar').focusin(function() {
		$j('#password_text').show();
		$j('#password_bar').show();
	});
</script>
<script type="text/javascript">
	$j(function() {
		$j("#email").validate(
				{
					expression : "if (VAL.match(/^[^\\W][a-zA-Z0-9\\_\\-\\.]+([a-zA-Z0-9\\_\\-\\.]+)*\\@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*\\.[a-zA-Z]{2,4}$/)) return true; else return false;",
					message : "Please enter a valid Email ID",
					error_class : "errors",
					error_field_class : "errors"
				});
		$j("#password")
		.validate(
				{
					expression : "if (VAL.length > 6 && VAL.length <= 20 && VAL) return true; else return false;",
					message : "Enter a password 6 to 20 characters long",
					error_class : "errors",
					error_field_class : "errors"
				});
        $j("#repassword")
		.validate(
				{
					expression : "if ((VAL == jQuery('#password').val()) && VAL) return true; else return false;",
					message : "Passwords do not match",
					error_class : "errors",
					error_field_class : "errors"
				});
		$j("#firstName").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#lastName").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#role").validate({
            expression: "if (VAL != '') return true; else return false;",
            message: "Please make a selection",
            error_class : "errors",
			error_field_class : "errors"
        });
		$j("#location").validate({
            expression: "if (VAL != '') return true; else return false;",
            message: "Please make a selection",
            error_class : "errors",
			error_field_class : "errors"
        });
	});
</script>