<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadowMT" id="newQuestionDiv">
<fieldset style="margin: 0;"><legend>Add Evaluation
Question</legend> <form:form method="post" modelAttribute="addQuestionBean">
	<div id="addQuestion_errors" class="error"></div>
	<c:set var="evaluationTemplateId" value="${EVALUATIONTEMPLATEID}" />
	<form:hidden path="evaluationTemplateId" />
	<table id="table-form-input">
		<colgroup>
			<col class="oce-first" />
		</colgroup>
		<tbody>
			<tr>
				<td width="10%" valign="top">Question:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>
				<form:textarea id="text" path="text" cssClass="forminput" /></td>
				<td><form:errors path="text" /></td>
			</tr>
			<tr>
				<td width="10%" valign="top">Question Level:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>

				<form:select path="level">
					<option>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
					<option>5</option>
				</form:select></td>
				<td><form:errors path="level" /></td>
			</tr>
			<tr>
				<td width="10%" valign="top">Answer:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>
				<form:input path="choiceOne" cssClass="forminput" /></td>
                <td><form:errors path="choiceOne" /></td>
			</tr>
			<tr>
				<td width="10%" valign="top">Choice Two:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>
				<form:input path="choiceTwo" cssClass="forminput" /></td>
				<td><form:errors path="choiceTwo" /></td>
				
				<td width="10%" valign="top">Correct:</td>
				<td><input type="checkbox" name="choiceTwoCorrect"
					value="correct" /></td>
                
			</tr>
			<tr>
				<td width="10%" valign="top">Choice Three:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>
				<form:input path="choiceThree" cssClass="forminput" /></td>
				<td><form:errors path="choiceThree" /></td>
				
				<td width="10%" valign="top">Correct:</td>
				<td><input type="checkbox" name="choiceThreeCorrect"
					value="correct" /></td>

			</tr>
			<tr>
				<td width="10%" valign="top">Choice Four:</td>
				<td width="10%" valign="top"><span style="color: red;">*</span>
				<form:input path="choiceFour" cssClass="forminput" /></td>
				<td><form:errors path="choiceFour" /></td>
				
				<td width="10%" valign="top">Correct:</td>
				<td><input type="checkbox" name="choiceFourCorrect"
					value="correct" /></td>

			</tr>
			<tr>
				<td align="center" colspan="4"><input type="submit"
					value="Add Question" /> &nbsp; &nbsp; <input type ="button" value="Cancel"  
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
            	window.tabparent = document.getElementById('newQuestionDiv').parentNode.id;
                $j("#addQuestionBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#addQuestion_errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#addQuestionBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                            doaction(data);
                                        });
                            return false;
                        });
                $j('#text').tinymce({
                    script_url : prefix+'/js/tiny_mce/tiny_mce.js',
                        theme : "simple",
                       
                 });
            });

    function doaction(data) 
    {
        var message = data.u;
        $j('#addQuestion_errors').html(message);
        if (message == "Saved") 
        {
        	viewQuestions();
        }
    }
    function viewQuestions(){
        
        $j('#' + window.tabparent).load(
        		prefix + "/question/listquestions" + JJTEACH.extension + "?evaluationId="
        				+ ${EVALUATIONTEMPLATEID});
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