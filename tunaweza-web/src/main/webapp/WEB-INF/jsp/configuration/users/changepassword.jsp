<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<form:form method="post" action="${PREFIX}/role/editrole.htm" modelAttribute="changePasswordBean" target="changepassframe">
    <div id="assoc_saverole_errors"></div>
    	<form:hidden path="id"/>
    <table id="table-form-input">
	    <colgroup>
	    	<col class="oce-first" />
	    </colgroup>
	    <thead>
	    	<tr>
	        	<th scope="col">Fields</th>
	            <th scope="col">Input</th>
	        </tr>
	    </thead>
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
	            	<form:input path="description" cssClass="forminput" />
	            	<form:password path="newPassword" cssClass="forminput" />
	            </td>
		     </tr>
		     <tr>
	            <td width="20%" valign="top">Confirm New Password:</td>
	            <td width="79%" valign="top">
	            	<span style="color:red;">*</span>
	            	<form:input path="description" cssClass="forminput" />
	            	<form:password path="newPasswordConfirm" cssClass="forminput" />
	            </td>
		     </tr>
		  <tr>
				<td width="20%">
				&nbsp;
				</td>
				<td width="70%" align="left">
				<button class="button" >Save</button> &nbsp; &nbsp;    	
				<button class="button" onClick="JJTEACH.ajaxLoad('/role/list'); return false;">Cancel</button>
				</td>
			</tr>
		</tbody>
	</table>
</form:form>
<iframe name="changepassframe" id="changepassframe" style="display: none"></iframe>
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j("#changePasswordBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#assoc_saverole_errors').html(
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
        $j('#assoc_saverole_errors').html(message);
        if (message == "Saved") 
        {
        	JJTEACH.ajaxLoad('/user/profile');
        }
    }
</script>