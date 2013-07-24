<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadowMT">
<fieldset><legend>Edit Mentor Template </legend> <form:form
	method="post" modelAttribute="editMentorTemplateBean">
	<div id="editmentortemplate_errors2" class="error"></div>
	<form:hidden path="id" />
	<table id="table-form-input">
		<colgroup>
			<col class="oce-first" />
		</colgroup>
		<tbody>

			<tr>
				<td width="20%" valign="top">MentorTemplate name:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:input path="name" cssClass="forminput" /></td>
			</tr>


			<tr>
				<td width="20%" valign="top">MentorTemplate Description:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:textarea cols="30" rows="5" path="description" id="description" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td width="20%" valign="top">Module:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:select path="module" cssClass="forminput">
					<c:if test="${not empty NOMODULE}">
						<form:option value="" label="Select MODULE" />
					</c:if>
					<form:options items="${MODULELIST}" itemValue="id" itemLabel="name" />
				</form:select></td>
			</tr>


			<tr>
				<td width="20%">&nbsp;</td>
				<td width="70%" align="left"><input type="submit" value="Save" />&nbsp;
				&nbsp; <input type ="button" value="cancel"
					onClick="JJTEACH.ajaxLoad('/mentor/listmentortemplates'); return false;">
				</td>
			</tr>

		</tbody>
	</table>
</form:form></fieldset>
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j("#editMentorTemplateBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#editMentorTemplateBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                        	doaction(data);
                                    	});
                            return false;
                        });

                /* $j('#description').tinymce({
        			script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
        			theme : "simple"
        		}); */
            });

    function doaction(data) 
    {
        var message = data.m;
        $j('#editmentortemplate_errors2').html(message);
        if (message == "Saved") 
        {
        	JJTEACH.ajaxLoad('/mentor/listmentortemplates');
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