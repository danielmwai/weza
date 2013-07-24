<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<form:form method="post" class="add_form" id="addLicenseBean"
	modelAttribute="addLicenseBean">

	<div id="addlicense_errors" class="error"></div>

	<div class="container country">
		<div class="container_inner">
			<label class="title">License Cost(£)</label>
			<p>Cost of the license</p>

			<form:input path="cost" cssClass="forminput" />
		</div>
	</div>
	<div class="container description">
		<div class="container_inner">
			<label class="title">Maximum users</label>
			<p>The maximum number of users</p>

			<form:input path="max_users" cssClass="forminput" />

		</div>
	</div>
	
	<div class="container description">
		<div class="container_inner">
			<label class="title">License Name</label>
			<p>The name of the license</p> 

			<form:input path="name" cssClass="forminput" />

		</div>
	</div>

	<button type="submit" class="submit_button">Save</button>
	<input type="button" value="Cancel"
		onClick="JJTEACH.ajaxLoad('/license/listlicenses'); return false;">

</form:form>

<script type="text/javascript">
	$j().ready(
			function() {
				$j("#addLicenseBean").submit(
						function() {
							var account = $j(this).serializeObject();
							$j('#addlicense_errors').html(
									"Processing..........");
							$j.postJSON($j('#addLicenseBean').attr('action'),
									account, function(data) {
										doaction(data);
									});
							return false;
						});
			});

	function doaction(data) {
		var message = data.u;
		$j('#addlicense_errors').html(message);
		if (message == "Saved") {
			JJTEACH.ajaxLoad('/license/listlicenses');
		}
	}
</script>
<script type="text/javascript">
	$j(function() {
		$j("#name").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
	});
</script>
