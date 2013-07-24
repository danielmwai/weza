<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
<!-- <fieldset>
<legend>Add Role</legend> -->
<form:form method="post" class="add_form" id="addRoleBean" modelAttribute="addRoleBean">
	
	<div id="addrole_errors" class="error"></div>
	
	<div class="container role">
				<div class="container_inner">
					<label class="title">Role Name</label>
					<p>Name of the role starting with(ROLE_*)</p>

					<form:input path="name" cssClass="forminput" />
				</div>
			</div>
	<div class="container description">
				<div class="container_inner">
					<label class="title">Role Description</label>
					<p>A bit of a description of the role created</p>

					<form:textarea path="description" id="description" cssClass="inputtextarea" 
						cssStyle="clear:left; width:200px;"/>
				</div>
			</div>
					
	<button type="submit" class="submit_button">Add
				Role</button>
		<input type ="button" value="Cancel"  onClick="JJTEACH.ajaxLoad('/role/list'); return false;">	
			
			
	<!-- table id="table-form-input">
		<colgroup>
			<col class="oce-first" />
		</colgroup>
		<tbody>
			<tr>
				<td width="20%" valign="top">Role name:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:input path="name" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td width="20%" valign="top">Role description:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:textarea path="description" id="description"
    				   cssClass="inputtextarea"
    				   cssStyle="clear:left; width: 200px;"/></td>
			</tr>
			<tr>
				<td width="20%">
				&nbsp;
				</td>
				<td width="70%" align="left">
				<input type="submit" value="Add role"/>&nbsp; &nbsp;
				<a onClick="JJTEACH.ajaxLoad('/role/list'); return false;">Cancel</a>
				</td>
			</tr>
		</tbody>
	</table-->
</form:form>
<!-- </fieldset> -->
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j("#addRoleBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#addrole_errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#addRoleBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                        	doaction(data);
                                    	});
                            return false;
                        });
		$j('#description').tinymce({
			script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
			theme : "simple"
		});
            });

    function doaction(data) 
    {
        var message = data.u;
        $j('#addrole_errors').html(message);
        if (message == "Saved") 
        {
        	JJTEACH.ajaxLoad('/role/list');
        }
    }    
</script>
<script type="text/javascript">
	$j(function() {
		$j("#name")
				.validate(
						{
							expression : "if (VAL) return true; else return false;",
							message : "Please enter the Required field",
							error_class : "errors",
							error_field_class : "errors"
						});
	});
</script>