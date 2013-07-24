<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadowMT">
	<fieldset>
		<legend>Edit Module</legend>
		<form:form method="post" modelAttribute="editModuleBean">
			<div id="editmodule_errors" class="error"></div>
			<table id="table-form-input">
				<colgroup>
					<col class="oce-first" />
				</colgroup>
				<tbody>

					<tr>
						<td width="20%" valign="top">Module name:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
							<form:input path="moduleName" cssClass="forminput" /></td>
					</tr>


					<tr>
						<td width="20%" valign="top">Module Description:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
							<form:textarea cols="30" rows="5" path="moduleDescription" id="moduleDescription"
								cssClass="forminput" /></td>
					</tr>
					
					<tr>
						<td width="20%" valign="top">Prerequisites:</td>
						<td width="79%" valign="top"><form:select
								path="preRequisites" id="preRequsites" cssClass="forminput"
								multiple="multiple">

								<form:option exclusive="true" value="None">None</form:option>
								<c:forEach items="${modulePrerequisites}" var="modulePrerequisite">
									<form:option selected="selected" value="${modulePrerequisite.id}">${modulePrerequisite.name}</form:option>
								</c:forEach>
								<c:forEach items="${allModules}" var="module">
									<form:option value="${module.id}">${module.name}</form:option>
								</c:forEach>

							</form:select></td>
					</tr>
					
					<tr>
						<td width="20%" valign="top">Time to Complete:</td>
						<td width="79%" valign="top">&nbsp; <form:input
								path="timeToComplete" cssClass="forminput" />
								<form:select path="duration" cssClass="forminput">
								<form:option value="hours" label="hours"></form:option>
								<form:option value="days" label="days"></form:option>
								<form:option value="weeks" label="weeks"></form:option>
								<form:option value="months" label="months"></form:option>
								</form:select>
						</td>
					</tr>


					<tr>

						<td width="20%" valign="top">Evaluation Factoring:</td>
						<td width="79%" valign="top"><form:checkbox path="evaluated"
								cssClass="forminput" checked="${evaluated}" value="true" /></td>
					</tr>


					<c:choose>
						<c:when test="${MENTORINGSTATE}">
							<tr>

								<td width="20%" valign="top">Enable Mentoring:</td>
								<td width="79%" valign="top"><form:checkbox
										path="mentoring" cssClass="forminput" checked="${mentoring}"
										value="true" /></td>

							</tr>
						</c:when>
					</c:choose>


					<tr>
						<td width="20%">&nbsp;</td>
						<td width="70%" align="left"><input type="submit"
							value="Save" />&nbsp; &nbsp; <input type ="button" value="Cancel" 
							onClick="JJTEACH.ajaxEditModule('/module/moduleinfo', ${MODULEID}); return false;">
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
							return first + ", " + (count - 1) + " More"
						}
					},
					maxDropHeight : 100,
					width : 120
				});
				
				$j("#editModuleBean").submit(
						function() {
							var account = $j(this).serializeObject();
							$j('#errors').html("Processing..........");
							$j.postJSON($j('#editModuleBean').attr('action'),
									account, function(data) {
										doaction(data);
									});
							return false;
						});

				/* $j('#moduleDescription').tinymce({
	    			script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
	    			theme : "simple"
	    		}); */
			});

	function doaction(data) {
		var message = data.m;
		$j('#editmodule_errors').html(message);
		if (message == "Saved") {
			JJTEACH.ajaxEditModule('/module/moduleinfo', ${MODULEID});
		}
	}
</script>
<script type="text/javascript">
	$j(function() {
		$j("#moduleName").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#timeToComplete").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
	});
</script>