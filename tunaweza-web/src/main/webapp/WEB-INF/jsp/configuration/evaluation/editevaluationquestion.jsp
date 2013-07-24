<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadowMT" id="editEvaluationQuestionDiv">
<fieldset style="margin: 0;"><legend>Edit Evaluation
Question</legend> <form:form method="post" modelAttribute="editQuestionBean">
	<div id="editQuestion_errors" class="error"></div>

	<table id="table-form-input">
		<colgroup>
			<col class="oce-first" />
		</colgroup>
		<tbody>
			<tr>
				<td width="10%" valign="top">Question:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>
				<form:textarea id="text" path="text" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td width="10%" valign="top">Question Level:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>
				Current Level: ${editQuestionBean.level} <form:select path="level">
					<option>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
					<option>5</option>
				</form:select></td>
			</tr>
			<tr>
				<td width="10%" valign="top">Answer:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>
				<form:input path="choiceOne" cssClass="forminput" value="" /></td>

			</tr>
			<tr>
				<td width="10%" valign="top">Choice Two:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>
				<form:input path="choiceTwo" cssClass="forminput"
					value="${editQuestionBean.choiceTwo}" /></td>
				<td width="10%" valign="top">Correct:</td>
				<c:choose>
					<c:when test="${editQuestionBean.choiceTwoCorrect==true}">
						<td><input type="checkbox" name="choiceTwoCorrect" value=true
							checked="yes" /></td>
					</c:when>
					<c:otherwise>
						<td><input type="checkbox" name="choiceTwoCorrect" value=true /></td>
					</c:otherwise>
				</c:choose>

			</tr>
			<tr>
				<td width="10%" valign="top">Choice Three:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>
				<form:input path="choiceThree" cssClass="forminput"
					value="${editQuestionBean.choiceThree}" /></td>
				<td width="10%" valign="top">Correct:</td>
				<c:choose>
					<c:when test="${editQuestionBean.choiceThreeCorrect==true}">
						<td><input type="checkbox" name="choiceThreeCorrect"
							value=true checked="yes" /></td>
					</c:when>
					<c:otherwise>
						<td><input type="checkbox" name="choiceThreeCorrect"
							value=true /></td>
					</c:otherwise>
				</c:choose>

			</tr>
			<tr>
				<td width="10%" valign="top">Choice Four:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>
				<form:input path="choiceFour" cssClass="forminput"
					value="${editQuestionBean.choiceFour}" /></td>
				<td width="10%" valign="top">Correct:</td>
				<c:choose>
					<c:when test="${editQuestionBean.choiceFourCorrect==true}">
						<td><input type="checkbox" name="choiceFourCorrect"
							value=true checked="yes" /></td>
					</c:when>
					<c:otherwise>
						<td><input type="checkbox" name="choiceFourCorrect"
							value=true /></td>
					</c:otherwise>
				</c:choose>

			</tr>
			<tr>
				<td align="center" colspan="4"><input type="submit"
					value="Edit Question" />&nbsp; &nbsp;<input type ="button" value="Cancel"  
					onClick="viewQuestions(); return false;"></td>
			</tr>
		</tbody>


	</table>
</form:form></fieldset>
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
            	window.tabparent = document.getElementById('editEvaluationQuestionDiv').parentNode.id;
                $j("#editQuestionBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#editQuestion_errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#editQuestionBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                            doaction(data);
                                        });
                            return false;
                        });
                $j('#text').tinymce({
                    script_url : prefix+'/js/tiny_mce/tiny_mce.js',
                        theme : "simple"
                 });
            });

    function doaction(data) 
    {
        var message = data.u;
        $j('#editQuestion_errors').html(message);
        if (message == "Saved") 
        {
        	viewQuestions();
        }
    }  
    function viewQuestions(){
        
        $j('#' + window.tabparent).load(
                prefix + "/question/listquestions" + JJTEACH.extension
                + "?evaluationId=" + ${TEMPLATEID});
    }
</script>
<script type="text/javascript">
	$j(function() {
		$j("#choiceOne").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#choiceTwo").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#choiceThree").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#choiceFour").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
	});
</script>