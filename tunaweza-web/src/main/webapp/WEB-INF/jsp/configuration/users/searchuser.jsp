<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
<fieldset>
<legend>Search User</legend>
<form method="get" modelAttribute="searchUserBean" >
	<div id="assoc_saveuser_errors" class="error"></div>
	<table id="table-form-input">
		<colgroup>
			<col class="oce-first" />
		</colgroup>
		<tbody>
			<tr>
				<td width="20%" valign="top">First Name:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<input type="text" id="firstname" class="forminput" /></td>
			</tr>
			<tr>
				<td width="20%" valign="top">Last Name:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<input type="text" id="lastname" class="forminput" /></td>
			</tr>
			<tr>
				<td width="20%" valign="top">Role:</td>
				<td width="79%" valign="top">
				<form:select path="searchUserBean.role" id="role" cssClass="forminput">
					<form:option value="NONE" label="Select Role" />
					<form:options items="${ROLELIST}" itemValue="roleName"
						itemLabel="roleName" />
				</form:select></td>
			</tr>
			<tr>
				<td width="20%">&nbsp;</td>
				<td width="70%" align="left">
				<button class="button" onClick="return searchUser();">Search</button>
				&nbsp; &nbsp;
				<button class="button"
					onClick="JJTEACH.ajaxLoad('/user/cancel'); return false;">Cancel</button>
				</td>
			</tr>

		</tbody>
	</table>
</form>
</fieldset>
</div>
<script type="text/javascript">

	function searchUser() {
		var firstname = $j('#firstname').val();
		var lastname = $j('#lastname').val();
		var role = $j("#role :selected").val();
		JJTEACH.ajaxSearch('/user/listsearch', firstname, lastname, role);
		return false;
	}

   $j().ready(
            function() 
            {
                $j("#searchUserBean").submit(
                        function() 
                        {
                            return false;
                        });
            }); 

</script>