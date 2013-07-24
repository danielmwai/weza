<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
<fieldset><legend>Edit User</legend> <form:form method="post"
	action="${PREFIX}/user/edituser.htm" modelAttribute="editUserBean">
	<div id="assoc_saveuser_errors" class="error"></div>
	<form:hidden path="id" />
	<form:hidden path="oldEmail" />
	<form:hidden path="oldRole" />
	<table id="table-form-input">
		<colgroup>
			<col class="oce-first" />
		</colgroup>
		<tbody>
			<c:if test="${editUserBean.email != loggedIn.email}">
			<tr>
				<td width="20%" valign="top">E-mail:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:input path="email" cssClass="forminput" /></td>
			</tr>
			</c:if>
			<tr>
				<td width="20%" valign="top">First Name:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:input path="firstName" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td width="20%" valign="top">Last Name:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:input path="lastName" cssClass="forminput" /></td>
			</tr>
			<%-- <tr>
				<td width="20%" valign="top">Location:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:select path="location" cssClass="forminput">
					<c:if test="${not empty NOLOCATION}">
						<form:option value="" label="Select LOCATION" />
					</c:if>
					<form:options items="${LOCATIONLIST}" itemValue="locationName"
						itemLabel="locationName" />
				</form:select></td>
			</tr> --%>
		
			<c:if test="${editUserBean.email != loggedIn.email}">
			<tr>
				<td width="20%" valign="top">Add Role:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:select path="role" cssClass="forminput">
					<c:if test="${not empty NOROLE}">
						<form:option value="" label="Select ROLE" />
					</c:if>
					<form:options items="${ROLELIST}" itemValue="roleName"
						itemLabel="roleName" />
				</form:select></td>
			</tr>
			</c:if>
			<tr>
				<td width="20%" valign="top">Mail</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:checkbox path="sendIn" value="true" label="Send?" /></td>
			</tr>
			<tr>
				<td width="20%">&nbsp;</td>
				<td width="70%" align="left"><input type="submit" value="Save" />
				&nbsp; &nbsp; <input type ="button" value="Cancel" 
					onClick="JJTEACH.ajaxUserInfo(${USERID}); return false;">
				</td>
			</tr>
		</tbody>
	</table>
</form:form></fieldset>

</div>

<script type="text/javascript">
	$j().ready(
			function() {
				$j("#editUserBean").submit(
						function() {
							var account = $j(this).serializeObject();
							$j('#assoc_saveuser_errors').html(
									"Processing..........");
							$j.postJSON($j('#editUserBean').attr('action'),
									account, function(data) {
										doaction(data);
									});
							return false;
						});
			});

		
	function doaction(data) {
		var message = data.u;
		$j('#assoc_saveuser_errors').html(message);
		if (message == "Saved") {
			JJTEACH.ajaxUserInfo(${USERID});
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
		$j("#firstName")
				.validate(
						{
							expression : "if (VAL) return true; else return false;",
							message : "Please enter the Required field",
							error_class : "errors",
							error_field_class : "errors"
						});
		$j("#lastName")
		.validate(
				{
					expression : "if (VAL) return true; else return false;",
					message : "Please enter the Required field",
					error_class : "errors",
					error_field_class : "errors"
				});
	});
</script>