<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<legend>Edit Instance</legend>
		<form:form method="post" action="${PREFIX}/cloud/editcloud.htm"
			modelAttribute="editCloudBean">
			<div id="assoc_saverole_errors" class="error"></div>
			<form:hidden path="id" />
			<table id="table-form-input">
				<colgroup>
					<col class="oce-first" />
				</colgroup>
				<tbody>
					<tr>
						<td width="20%" valign="top">Company Name:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
							<form:input path="name" cssClass="forminput" /></td>
					</tr>
					<tr>
						<td width="20%" valign="top">Database Name:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
							<form:input path="dbaseName" cssClass="forminput" /></td>
					</tr>
					<tr>
						<td width="20%" valign="top">Database Username:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
							<form:input path="dbUserName" cssClass="forminput" /></td>
					</tr>
					<tr>
						<td width="20%" valign="top">Database Password:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
							<form:input path="dbPassword" cssClass="forminput" /></td>
					</tr>
					<tr>
						<td width="20%" valign="top">Email:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
							<form:input path="email" cssClass="forminput" /></td>
					</tr>
					<tr>
						<td width="20%">&nbsp;</td>
						<td width="70%" align="left"><input type="submit"
							value="Save" /> &nbsp; &nbsp; <input type ="button" value="Cancel"  
							onClick="JJTEACH.ajaxLoad('/cloud/listinstances'); return false;">
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</fieldset>
</div>
<script type="text/javascript">
	$j().ready(
			function() {
				$j("#editCloudBean").submit(
						function() {
							var account = $j(this).serializeObject();
							$j('#assoc_saverole_errors').html(
									"Processing..........");
							$j.postJSON($j('#editCloudBean').attr('action'),
									account, function(data) {
										doaction(data);
									});
							return false;
						});

				$j('#description').tinymce({
					script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
					theme : "simple"
				});
			});

	function doaction(data) {
		var message = data.u;
		$j('#assoc_saverole_errors').html(message);
		if (message == "Saved") {
			JJTEACH.ajaxLoad('/cloud/listinstances');
		}
	}
</script>
<script type="text/javascript">
	$j(function() {
		$j("#email")
        .validate(
                {
                    expression : "if (VAL.match(/^[^\\W][a-zA-Z0-9\\_\\-\\.]+([a-zA-Z0-9\\_\\-\\.]+)*\\@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*\\.[a-zA-Z]{2,4}$/)) return true; else return false;",
                    message : "Please enter a valid Email ID",
                    error_class : "errors",
                    error_field_class : "errors"
                });
		$j("#dbaseName").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Database Name",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#dbUserName").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#dbPassword").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Database Username",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#name").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
	});
</script>