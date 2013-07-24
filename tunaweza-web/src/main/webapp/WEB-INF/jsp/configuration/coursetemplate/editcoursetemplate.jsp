<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<legend>Edit CourseTemplate </legend>
		<form:form method="post" modelAttribute="editCourseTemplateBean">
			<div id="editcoursetemplate_errors" class="error"></div>
			<form:hidden path="id" />
			<table id="table-form-input">
				<colgroup>
					<col class="oce-first" />
				</colgroup>
				<tbody>

					<tr>
						<td width="20%" valign="top">CourseTemplate name:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
							<form:input path="name" cssClass="forminput" /></td>
					</tr>


					<tr>
						<td width="20%" valign="top">CourseTemplate Description:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
							<form:textarea cols="30" rows="5" path="description" id="description"
								cssClass="forminput" /></td>
					</tr>

					<tr>
						<td width="20%" valign="top">Evaluation Factoring:</td>
						<td width="79%" valign="top"><form:checkbox path="evaluated"
								cssClass="forminput" checked="${evaluated}" value="true" /></td>
					</tr>

					<tr>
						<td width="20%" valign="top">Prerequisites:</td>
						<td width="79%" valign="top"><form:select
								path="preRequisites" id="preRequsites" cssClass="forminput"
								multiple="multiple">

								<form:option exclusive="true" value="None">None</form:option>
								<c:forEach items="${preRequisites}" var="prerequisite">
									<form:option selected="selected" value="${prerequisite.name}">${prerequisite.name}</form:option>
								</c:forEach>
								<c:forEach items="${allCourseTemplates}" var="courseTemplate">
									<form:option value="${courseTemplate.name}">${courseTemplate.name}</form:option>
								</c:forEach>

							</form:select></td>
					</tr>

					<tr>
						<td width="20%">&nbsp;</td>
						<td width="70%" align="left"><input type="submit"
							value="Save" />&nbsp; &nbsp; <a
							onClick="JJTEACH.ajaxEditCT('/coursetemplate/ctinfo',${TEMPLATEID}); return false;">Cancel</a>
						</td>
					</tr>

				</tbody>
			</table>
		</form:form>
	</fieldset>
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
            	$j('#preRequsites').dropdownchecklist({
					customTextFn : function(opts) {
						var count = 0;
						var first = null;
						for ( var i = 0, len = opts.length; i < len; ++i) {
							if (opts[i].selected) {
								if (!first) {
									first = opts[i].text;
								}
								++count;
							}
						}
						switch (count) {
						case 0:
							return "None";
						case 1:
							return first;
						default:
							return first +", "+ (count-1) + " More"
						}
					},
					maxDropHeight : 100,
					width : 120
				});
                $j("#editCourseTemplateBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#editCourseTemplateBean').attr('action'),
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
        $j('#editcoursetemplate_errors').html(message);
        if (message == "Saved") 
        {
        	JJTEACH.ajaxEditCT('/coursetemplate/ctinfo',${TEMPLATEID});
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