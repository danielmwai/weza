<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<legend>Edit email</legend>
		<form:form method="post" action="${PREFIX}/user/editemail.htm"
			modelAttribute="editEmailBean" target="editemailframe">
			<div id="errors" class="error"></div>
			<form:hidden path="id" />
			<form:hidden path="email" />
			<form:hidden path="role" />
			<table id="table-form-input">
				<colgroup>
					<col class="oce-first" />
				</colgroup>
				<tbody>
					<tr>
						<td width="20%" valign="top">Old Email :</td>
						<td width="79%" valign="top">${editEmailBean.email}</td>
					</tr>
					<tr>
						<td width="20%" valign="top">New Email :</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
							<form:input path="newEmail" cssClass="forminput" />
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top">Confirm New Email :</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
							<form:input path="confirmEmail" cssClass="forminput" />
						</td>
					</tr>
					<tr>
						<td width="20%"><input type="submit" value="Save"
							onClick="return confirmSubmit()" /> &nbsp; &nbsp;</td>
						<td width="70%" align="left"><a
							onClick="JJTEACH.ajaxLoad('/user/profile'); return false;">Cancel</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</fieldset>
</div>
<iframe name="editemailframe" id="editemailframe" style="display: none"></iframe>
<script type="text/javascript">
	$j().ready(
			function() {
				$j("#editEmailBean").submit(
						function() {
							var account = $j(this).serializeObject();
							$j('#errors').html("Processing..........");
							$j.postJSON($j('#editEmailBean').attr('action'),
									account, function(data) {
										doaction(data);
									});
							return false;
						});
			});

	function doaction(data) {
		var message = data.u;
		$j('#errors').html(message);
		if (message == "Saved") {
			window.location.href = "/jjteach/j_spring_security_logout";
		}
	}

	function confirmSubmit() {
		var agree = confirm("After changing your email you will need to login with the new email. Continue?");
		if (agree)
			return true;
		else
			return false;
	}
</script>
<script type="text/javascript">
	$j(function() {
		$j("#newEmail")
				.validate(
						{
							expression : "if (VAL.match(/^[^\\W][a-zA-Z0-9\\_\\-\\.]+([a-zA-Z0-9\\_\\-\\.]+)*\\@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*\\.[a-zA-Z]{2,4}$/)) return true; else return false;",
							message : "Please enter a valid Email ID",
							error_class : "errors",
							error_field_class : "errors"
						});
		$j("#confirmEmail")
		.validate(
				{
					expression : "if ((VAL == jQuery('#newEmail').val()) && VAL) return true; else return false;",
					message : "Emails do not match",
					error_class : "errors",
					error_field_class : "errors"
				});
	});
</script>