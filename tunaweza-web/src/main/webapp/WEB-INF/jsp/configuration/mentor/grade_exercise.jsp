<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
	$j("#gradeExerciseBean").submit(function(){
		console.log($j("#gradeExerciseBean select#status"));
		if($j("#gradeExerciseBean select#status").val()==0){
			alert("Select Exercise Status");
			return false;
		}
		if($j("#gradeExerciseBean select#status").val()==1 &&
			 $j("#gradeExerciseBean select#grade").val()==0){
			alert("Select Exercise Grade ")
			return false;
		}
		
	});
	$j("#status").click(function(){
		if($j(this).val()==1)
			$j("#grading").removeAttr("style");
		else
			$j("#grading").css("display","none");
	});
</script>
<div class="fieldShadow" style="margin-top:25px">
<fieldset>
<legend>Grade Student Exercise</legend>
		<iframe name="gradeExercise" id="postSolution"
			style="display:none;"></iframe>
		<form:form method="post" modelAttribute="gradeExerciseBean"
			enctype="multipart/form-data" target="gradeExercise"
			action="${PREFIX}/mentoring/gradeexercise.htm">
			<div id="gradeExercise_errors" class="errors"></div>
			<form:hidden path="exerciseId" />
			<table id="table-form-input">
				<tbody>
					<tr>
						<td width="20%" valign="top">Subject:<span style="color: red;">*</span></td>
						<td width="79%" valign="top">
							<form:input path="subject" cssClass="forminput" value="${LAST_TRANSACTION.subject }"/>
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top">Message:<span
							style="color: red;">*</span>
						</td>
						<td width="90%" valign="top"><form:textarea path="message"
								id="message" cssClass="inputtextarea"
								cssStyle="clear:left; width: 80%; height: 220px;" /></td>
					</tr>
					<tr>
						<td width="20%" valign="top">Status:<span style="color: red;">*</span></td>
						<td width="79%" valign="top">
						 <form:select  path="status"  cssClass="forminput" >
						    <form:option value="0">Select Status</form:option>			
				            <form:option value="1">Complete</form:option>
							<form:option value="2">Needs more work</form:option>
				            <form:option value="3">Needs Grading</form:option>
				            <form:option value="4">Not Started</form:option>
				            <form:option value="5">In Progress</form:option>
				            <form:option value="6">No Change</form:option>
				            </form:select>
						</td>
					</tr>
					<tr id="grading" style="display:none">
						<td width="20%" valign="top">Grade:<span style="color: red;">*</span></td>
						<td width="79%" valign="top">
							 <form:select  path="grade"  cssClass="forminput" >
								<form:option value="0">Select Grade</form:option>
					            <form:option value="5">Very Good</form:option>
								<form:option value="4">Good</form:option>
								<form:option value="3">Average</form:option>
								<form:option value="2">Below Average</form:option>
								<form:option value="1">Weak</form:option>
					          </form:select>
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top">Attach File:<span style="color: red;">*</span></td>
						<td width="79%" valign="top">
							<form:input path="attachedFile" cssClass="forminput" type="file" />
						</td>
					</tr>
					
					<tr>
						<td width="20%">&nbsp;</td>
						<td width="70%" align="left"><input type="submit"
							value="Submit" />&nbsp; &nbsp;</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</fieldset>
</div>
<script type="text/javascript">
	$j().ready(function() {
		$j('#message').tinymce({
			script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
			theme : "simple"
		});
		var tabcontent_height=($j('#content').height()-84);
      	$j("#mentoringTab div.ui-tabs-panel").css('height', tabcontent_height+ 'px');
      	$j(window).resize(function() {
      		var tabcontent_height=($j('#content').height()-84);
          	$j("#mentoringTab div.ui-tabs-panel").css('height', tabcontent_height+ 'px');
          });
	});
</script>