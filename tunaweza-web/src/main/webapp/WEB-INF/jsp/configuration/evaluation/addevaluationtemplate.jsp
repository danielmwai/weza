<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
	$j(function() {

		$j("#evaluationTemplate").tabs();
	});
</script>
<div id="evaluationTemplate"><c:choose>
	<c:when test="${MODULELIST=='NOMODULES'}">No modules in system yet
Please Add some modules first!
</c:when>
	<c:otherwise>


		<div id="templates">
		<div class="fieldShadow">
		<fieldset><legend>Add Evaluation Template</legend> <form:form
			method="post" modelAttribute="addEvaluationTemplateBean">
			<div id=addevaluationtemplate_errors class="error"></div>
			<table id="table-form-input">
				<colgroup>
					<col class="oce-first" />
				</colgroup>
				<tbody>



					<tr>
						<td width="20%" valign="top">EvaluationTemplate name:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
						<form:input path="name" cssClass="forminput" /></td>
					</tr>

					<tr>
						<td width="20%" valign="top">EvaluationTemplate Description:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
						<form:input path="description" id="description"
							cssClass="forminput" /></td>
					</tr>


					<tr>
						<td width="20%" valign="top">Factoring:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
						<form:input path="factoring" cssClass="forminput" /></td>
					</tr>

					<tr>
						<td width="20%" valign="top">Duration:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
						<form:input path="duration" cssClass="forminput" />minutes</td>
					</tr>

					<tr>
						<td width="20%" valign="top">Number of questions:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
						<form:input path="numberOfQuestions" cssClass="forminput" /></td>
					</tr>
					<tr>
						<td width="20%" valign="top">Pass mark%:</td>
						<td width="79%" valign="top"><span style="color: red;">*</span>
						<form:input path="passMark" cssClass="forminput" /></td>
					</tr>
					<tr>


						<td width="20%">&nbsp;</td>
						<td width="70%" align="left"><input type="submit" value="Add" />
						&nbsp; &nbsp;<input type ="button" value="Cancel"  
							onClick="JJTEACH.ajaxLoad('/evaluationtemplate/listevaluationtemplates'); return false;">
						</td>
					</tr>

				</tbody>
			</table>
		</form:form></fieldset>
		</div>
		</div>

	</c:otherwise>
</c:choose></div>

<script type="text/javascript">
	$j().ready(
			function() {
				window.tabparent = document.getElementById('evaluationTemplate').parentNode.id;
				$j("#addEvaluationTemplateBean").submit(
						function() {
							var account = $j(this).serializeObject();
							$j('#errors').html("Processing..........");
							$j.postJSON($j('#addEvaluationTemplateBean').attr(
									'action'), account, function(data) {
								doaction(data);
							});
							return false;
						});
				$j('#description').tinymce({
					script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
					theme : "simple"
				});
			});

	function doaction(data) {
		var message = data.m;
		$j('#addevaluationtemplate_errors').html(message);
		if (message == "Saved") {
			alert("Added Evaluation successfully");
			viewTemplate();
		}
	}
	
	function viewTemplate(){
   
		$j('#' + window.tabparent).load(
                prefix + "/evaluation/listevaluationtemplates" + JJTEACH.extension + "?moduleId="
                        + ${MODULEID});
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
		$j("#factoring").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#duration").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#numberOfQuestions").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#passMark").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
	});
</script>
