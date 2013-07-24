<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow" style="margin-top:25px">
	<fieldset>
<legend>Course Evaluation Test</legend>
		<span class="legend"></span>
		<table class="table-form-input">
		<tbody>
					<tr>
						<td width="20%" valign="top"><b>Course:</b></td>
						<td width="79%" valign="top">
							${COURSETEMPLATE.name}
						</td>
					</tr>
					
					<tr>
						<td width="20%" valign="top"><b>Duration:</b></td>
						<td width="79%" valign="top">
							${DURATION} minutes
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top"><b>Instructions:</b>
						</td>
						<td width="90%" valign="top">
							<div>	${COURSETEMPLATE.description}</div>
						
			
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top"></td>
						<td width="79%" valign="top">
							 <button onclick="JJTEACH.courseEvaluation('/studenttest/course/new','${COURSETEMPLATE.id}')">Start Test</button>
						</td>
					</tr>
		</table>
		
		</fieldset>
</div>
