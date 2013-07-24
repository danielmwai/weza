<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>


<form:form method="post"
	action="${PREFIX}/cloud/changeadminpassword.htm"
	modelAttribute="changeAdminPasswordBean" id="changeAdminPasswordBean"
	class="add_form">

	<div id="assoc_savelocation_errors" class="error"></div>
	<form:hidden path="company_id" />

	<div class="container country">
		<div class="container_inner">
			<label class="title">New password</label>
			<form:password path="newPassword" cssClass="forminput" />
		</div>
	</div>
	<div class="container description">
		<div class="container_inner">
			<label class="title">Confirm Password</label>

			<form:password path="newPasswordConfirm" cssClass="forminput" />

		</div>
	</div>

	<button type="submit" class="submit_button">Save</button>
	<input type="button" value="Cancel"
		onClick="JJTEACH.ajaxLoad('/cloud/listcompanyadmins'); return false;">

</form:form>

<script type="text/javascript">
	$j().ready(
			function() {
				$j("#changeAdminPasswordBean").submit(
						function() {
							var account = $j(this).serializeObject();
							$j('#errors').html("Processing..........");
							$j.postJSON($j('#changeAdminPasswordBean').attr(
									'action'), account, function(data) {
								doaction(data);
							});
							return false;
						});
				/* 
				 $j('#description').tinymce({
				 script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
				 theme : "simple"
				 }); */
			});

	function doaction(data) {
		var message = data.u;
		$j('#assoc_savelocation_errors').html(message);
		if (message == "Saved") {
			JJTEACH.ajaxLoad('/cloud/listcompanyadmins');
		}
	}
</script>
<script type="text/javascript">
	$j(function() {
		$j("#newPassword")
				.validate(
						{
							expression : "if (VAL.length > 6 && VAL.length <= 20 && VAL) return true; else return false;",
							message : "Enter a password 6 to 20 characters long",
							error_class : "errors",
							error_field_class : "errors"
						});
		$j("#newPasswordConfirm")
				.validate(
						{
							expression : "if ((VAL == jQuery('#newPassword').val()) && VAL) return true; else return false;",
							message : "Passwords do not match",
							error_class : "errors",
							error_field_class : "errors"
						});
	});
</script>