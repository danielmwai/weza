<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow" style="margin-top:25px">
	<fieldset>
<legend>Module Evaluation Test</legend>
		<span class="legend"></span>
		<table class="table-form-input">
		<tbody>
					<tr>
						<td width="20%" valign="top"><b>Module:</b></td>
						<td width="79%" valign="top">
							${EVALUATIONTEMPLATE.module.name}
						</td>
					</tr>
					
					<tr>
						<td width="20%" valign="top"><b>Duration:</b></td>
						<td width="79%" valign="top">
							${EVALUATIONTEMPLATE.duration} minutes
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top"><b>Instructions:</b>
						</td>
						<td width="90%" valign="top">
							<div>	${EVALUATIONTEMPLATE.description}</div>
						
			
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top"></td>
						<td width="79%" valign="top">
							 <button onclick="JJTEACH.administerEvaluation('/studenttest/module/new','${EVALUATIONTEMPLATE.id}')">Start Test</button>
						</td>
					</tr>
		</table>
		
		</fieldset>
</div>
