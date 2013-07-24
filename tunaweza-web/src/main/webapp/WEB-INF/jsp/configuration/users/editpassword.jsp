<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
<fieldset>
	<legend>Edit password</legend>
<form:form method="post" action="${PREFIX}/user/editpass.htm" modelAttribute="changePasswordBean" target="changepassframe">
    <div id="errors" class="error"></div>
    	<form:hidden path="id"/>
    <table id="table-form-input">
	    <colgroup>
	    	<col class="oce-first" />
	    </colgroup>
    	<tbody>        	
	        <tr>
	             <td width="20%" valign="top">Old Password:</td>
	             <td width="79%" valign="top">
	             	<span style="color:red;">*</span>
	             	<form:password path="oldPassword" cssClass="forminput" />
	             </td>
	         </tr>
	         <tr>
	            <td width="20%" valign="top">New Password:</td>
	            <td width="79%" valign="top">
	            	<span style="color:red;">*</span>
	            	<form:password path="newPassword" cssClass="forminput password_sbar" />
	            </td>
		     </tr>
		     <tr>
	            <td width="20%" valign="top">Confirm New Password:</td>
	            <td width="79%" valign="top">
	            	<span style="color:red;">*</span>
	            	<form:password path="newPasswordConfirm" cssClass="forminput" />
	            </td>
		     </tr>
		  <tr>
				<td width="20%">
					<input type="submit" value="Save"/>
				</td>
				<td width="70%" align="left">
					<a onClick="JJTEACH.ajaxLoad('/user/profile'); return false;">Cancel</a>
				</td>
			</tr>
		</tbody>
	</table>
</form:form>
</fieldset>
</div>
<iframe name="changepassframe" id="changepassframe" style="display: none"></iframe>
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j("#changePasswordBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#changePasswordBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                        	doaction(data);
                                    	});
                            return false;
                        });
            });

    function doaction(data) 
    {
        var message = data.u;
        $j('#errors').html(message);
        if (message == "Saved") 
        {
        	JJTEACH.ajaxLoad('/user/profile');
        }
    }
</script>
<script type="text/javascript">
	$j(function() {
	$j('.password_sbar').pstrength();
	});
	
	$j('.password_sbar').focusout(function() {
		  $j('#newPassword_text').hide();
		  $j('#newPassword_bar').hide();
		});
	
	$j('.password_sbar').focusin(function() {
		  $j('#newPassword_text').show();
		  $j('#newPassword_bar').show();
		});
</script>
<script type="text/javascript">
	$j(function() {
		$j("#oldPassword")
		.validate(
				{
					expression : "if (VAL) return true; else return false;",
					message : "Please enter the Required field",
					error_class : "errors",
					error_field_class : "errors"
				});
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