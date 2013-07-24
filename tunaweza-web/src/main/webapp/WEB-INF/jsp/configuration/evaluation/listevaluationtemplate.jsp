<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<div id="evaluationTemplate">


	<table id="list" border="0" cellspacing="0">
		<c:choose>
			<c:when test="${not empty EVALUATIONTEMPLATE}">
				<thead>
					<tr>
						<th valign="top" width="20%">NAME</th>
						<th valign="top" width="30%">DESCRIPTION</th>
						<th valign="top" width="20%">MODULE</th>
						<th valign="top" width="20%">VIEW QUESTIONS</th>
						<th valign="top" width="10%">EDIT TEMPLATE</th>

					</tr>
				</thead>
				<tbody>

					<tr>
						<td valign="top">${EVALUATIONTEMPLATE.name}</td>
						<td valign="top">${EVALUATIONTEMPLATE.description}</td>
						<td valign="top">${EVALUATIONTEMPLATE.module.name}</td>
						<td valign="top"><button
								onClick="JJTEACH.ajaxViewEvaluationQuestions('evaluationTemplate',${EVALUATIONTEMPLATE.id})">View
								Questions</button></td>
						<td valign="top">
							<button class="button"
								onClick="JJTEACH.ajaxEditET('/evaluation/editevaluationtemplateform', ${EVALUATIONTEMPLATE.id})">Edit</button>
						</td>
					</tr>

				</tbody>
			</c:when>
			<c:otherwise>
				<tr>
					<td>This Module of ID ${MODULEID} does not have an Evaluation
						Template</td>
					<td>
						<button
							onClick="JJTEACH.ajaxAddEvaluation('evaluationTemplate',${MODULEID});">Add
							Template</button>
					</td>
				</tr>

			</c:otherwise>
		</c:choose>
	</table>


</div>
