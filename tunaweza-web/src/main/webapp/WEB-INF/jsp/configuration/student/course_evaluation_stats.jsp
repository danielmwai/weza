<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
${EvalStats}
<table id='list' cellspacing='0' border='0'>
	<c:choose>
		<c:when test="${not empty NOLIST}">
			<tr>
				<td align="center">${NOLIST}</td>
			</tr>
		</c:when>
		<c:otherwise>
			<thead>
				<tr>
					<th valign='top'>Course</th>
					<th valign='top'>First Test Date</th>
					<th valign='top'>Second Test Date</th>
					<th valign='top'>First Test Score</th>
					<th valign='top'>Second Test Score</th>
					<th valign='top'>Re-take</th>
				</tr>
			</thead>
			<c:forEach var="evaluation" items="${EVALUATIONS}">
				<tr>
					<td>${evaluation.moduleName}</td>
					<td>${evaluation.firstDate}</td>
					<td>${evaluation.secondDate}</td>
					<td>${evaluation.firstScore}</td>
					<td>${evaluation.secondScore}</td>
					<td><input type='button' value="retake"
						onclick="JJTEACH.courseEvaluation('/studenttest/course/start','${evaluation.moduleId}');">
					</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>


	<tr class="pagin">
		<td colspan="6" align="center">
			<div class="pages" id="pageLink" align="center">
				<c:if test="${not empty TOTAL_PAGES}">
					<c:if test="${PAGE_NUMBER > 1}">
						<button class="pagenumb"
							onclick="JJTEACH.ajaxListCourseEvalStats('/user/course_evaluation_stats',${PAGE_NUMBER-1})">
							<c:out value="<< Previous" />
						</button>
					</c:if>
					<c:if test="${PAGE_NUMBER >2}">
						<button class="pagenumb"
							onclick="JJTEACH.ajaxListCourseEvalStats('/user/course_evaluation_stats',${PAGE_NUMBER-2})">
							<c:out value="${PAGE_NUMBER-2}" />
						</button>
					</c:if>
					<c:if test="${PAGE_NUMBER >1}">
						<button class="pagenumb"
							onclick="JJTEACH.ajaxListCourseEvalStats('/user/course_evaluation_stats',${PAGE_NUMBER-1})">
							<c:out value="${PAGE_NUMBER-1}" />
						</button>
					</c:if>
					<c:out value="${PAGE_NUMBER}" />
					<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
						<button class="pagenumb"
							onclick="JJTEACH.ajaxListCourseEvalStats('/user/course_evaluation_stats',${PAGE_NUMBER+1})">
							<c:out value="${PAGE_NUMBER+1}" />
						</button>
					</c:if>
					<c:if test="${PAGE_NUMBER+1 < TOTAL_PAGES}">
						<button class="pagenumb"
							onclick="JJTEACH.ajaxListCourseEvalStats('/user/course_evaluation_stats',${PAGE_NUMBER+2})">
							<c:out value="${PAGE_NUMBER+2}" />
						</button>
					</c:if>
					<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
						<button class="next"
							onclick="JJTEACH.ajaxListCourseEvalStats('/user/course_evaluation_stats',${PAGE_NUMBER+1})">
							<c:out value="Next >>" />
						</button>
					</c:if>
				</c:if>
			</div>
		</td>
	</tr>
<tr><td colspan=6>Download|<a href="${PREFIX}/report/student/evaluation.htm?reportType=pdf">PDF<img src="${PREFIX}/images/icons/docs/pdf.png"/></a>|<a href="${PREFIX}/user/report/student/evaluation.htm?reportType=xls">EXCEL<img src="${PREFIX}/images/icons/docs/xls.png"/></a>|<a href="${PREFIX}/user/report/student/evaluation.htm?reportType=csv">CSV<img src="${PREFIX}/images/icons/docs/csv.png"/></a></td></tr>
</table>