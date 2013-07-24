<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div id="listEvaluationQuestionsDiv">
	<table id='list' cellspacing='0' border='0'>
		<thead>
			<tr colspan=3>
				<td><button id='newquestion'
						onclick="JJTEACH.ajaxQuestionAdd('listEvaluationQuestionsDiv',${TEMPLATEID})">ADD
						QUESTION</button>
					<button onClick="viewTemplate()">Back</button></td>
			</tr>
			<tr>
				<th valign='top' width='25%'>QUESTION</th>
				<th valign='top' width='25%'>LEVEL</th>
				<th valign='top' width='25%'>EDIT</th>
				<th valign='top' width='25%'>DELETE</th>
			</tr>
		</thead>
		<c:choose>
			<c:when test="${empty NOQUESTIONS}">

				<tbody>

					<c:forEach var="question" items="${QUESTIONLIST}">
						<tr>
							<td valign='top'>${question.text}</td>
							<td valign='top'>${question.level}</td>
							<td valign='top'><button
									onclick="JJTEACH.ajaxQuestionEdit('listEvaluationQuestionsDiv','${question.id}')">Edit
									Question</button></td>
							<td valign='top'><button
									onclick="JJTEACH.ajaxQuestionDelete('${question.id}','${TEMPLATEID}')">Delete
									Question</button></td>
						</tr>

					</c:forEach>
					<tr class="pagin">
						<td colspan="5" align="center">
							<div class="pages" id="pageLink" align="center">
								<c:if test="${not empty TOTAL_PAGES}">
									<c:if test="${PAGE_NUMBER > 1}">
										<button
											onclick="JJTEACH.ajaxPaginateEvaluationQuestions(1, ${TEMPLATEID})">First</button>
										<button class="pagenumb"
											onclick="JJTEACH.ajaxPaginateEvaluationQuestions(${PAGE_NUMBER-1}, ${TEMPLATEID})">
											<c:out value="<< Previous" />
										</button>

									</c:if>
									<c:if test="${PAGE_NUMBER >2}">
										<button class="pagenumb"
											onclick="JJTEACH.ajaxPaginateEvaluationQuestions(${PAGE_NUMBER-2},${TEMPLATEID})">
											<c:out value="${PAGE_NUMBER-2}" />
										</button>
									</c:if>
									<c:if test="${PAGE_NUMBER >1}">
										<button class="pagenumb"
											onclick="JJTEACH.ajaxPaginateEvaluationQuestions(${PAGE_NUMBER-1}, ${TEMPLATEID})">
											<c:out value="${PAGE_NUMBER-1}" />
										</button>
									</c:if>
									<c:out value="${PAGE_NUMBER}" />
									<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
										<button class="pagenumb"
											onclick="JJTEACH.ajaxPaginateEvaluationQuestions(${PAGE_NUMBER+1}, ${TEMPLATEID})">
											<c:out value="${PAGE_NUMBER+1}" />
										</button>
									</c:if>
									<c:if test="${PAGE_NUMBER+1 < TOTAL_PAGES}">
										<button class="pagenumb"
											onclick="JJTEACH.ajaxPaginateEvaluationQuestions(${PAGE_NUMBER+2}, ${TEMPLATEID})">
											<c:out value="${PAGE_NUMBER+2}" />
										</button>
									</c:if>
									<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
										<button class="next"
											onclick="JJTEACH.ajaxPaginateEvaluationQuestions(${PAGE_NUMBER+1}, ${TEMPLATEID})">
											<c:out value="Next >>" />
										</button>
										<button
											onclick="JJTEACH.ajaxPaginateEvaluationQuestions(${TOTAL_PAGES}, ${TEMPLATEID})">Last</button>
									</c:if>
								</c:if>
							</div>
						</td>
					</tr>
				</tbody>
			</c:when>
			<c:otherwise>
				<tr>
					<td valign='top'>${NOQUESTIONS}</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</div>
<script type="text/javascript">
$j().ready(
        function() 
        {
        	window.tabparent = document.getElementById('listEvaluationQuestionsDiv').parentNode.id;
        });
        
        
        function viewTemplate(){
        	JJTEACH.loader('#' + window.tabparent);
            $j('#' + window.tabparent).load(
                    prefix + "/evaluation/listevaluationtemplates" + JJTEACH.extension + "?moduleId="
                            + ${MODULEID});
        }
</script>