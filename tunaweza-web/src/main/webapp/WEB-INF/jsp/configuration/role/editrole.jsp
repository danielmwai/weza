<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<!-- <div class="fieldShadow">
<fieldset>
<legend>Edit Role</legend> -->
<form:form method="post" action="${PREFIX}/role/editrole.htm" modelAttribute="editRoleBean" class="add_form">
    <div id="assoc_saverole_errors" class="error"></div>
    	<form:hidden path="id"/>
    	
    	<div class="container role">
				<div class="container_inner">
					<label class="title">Role Name</label>
					<p>Name of the role starting with(preferably with with ROLE_*)</p>

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
	<c:if test="${editRoleBean.id!=0}">
	  <button type="submit" class="submit_button">Save</button>
	</c:if>	
	<c:if test="${editRoleBean.id==0}">			
	   <button type="submit" class="submit_button">Add
				Role</button>
	</c:if>			
			<input type ="button" value="Cancel"  onClick="JJTEACH.ajaxLoad('/role/list'); return false;">	
    <%-- <table id="table-form-input">
	    <colgroup>
	    	<col class="oce-first" />
	    </colgroup>
    	<tbody>        	
	        <tr>
	             <td width="20%" valign="top">Role name:</td>
	             <td width="79%" valign="top">
	             	<span style="color:red;">*</span>
	             	<form:input path="name" cssClass="forminput" />
	             </td>
	         </tr>
	         <tr>
	            <td width="20%" valign="top">Role description:</td>
	            <td width="79%" valign="top">
	            	<span style="color:red;">*</span>
	            	<form:input path="description" id="description" cssClass="forminput" />
	            </td>
		     </tr>
		  <tr>
				<td width="20%">
				&nbsp;
				</td>
				<td width="70%" align="left">
				<input type="submit" value="Save"/> &nbsp; &nbsp;    	
				<a onClick="JJTEACH.ajaxLoad('/role/list'); return false;">Cancel</a>
				</td>
		</tbody>
	</table> --%>
</form:form>
<!-- </fieldset></div> -->
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j("#editRoleBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#assoc_saverole_errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#editRoleBean').attr('action'),
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
        $j('#assoc_saverole_errors').html(message);
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