
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<table id="list" border="0" cellspacing="0">
	<c:choose>
		<c:when test="${not empty NOLIST}">
			<tr>
				<td align="center"><c:out value="${NOLIST}"></c:out></td>
			</tr>
		</c:when>
		<c:otherwise>
			<th>Title</th>
			<th>Date Last Submitted</th>
			<th>Date of Current Transaction</th>
			<th>Status</th>
			<c:forEach var="exercise" items="${EXERCISE_LIST}">
				<tr>
					<td>${exercise.exerciseTitle}</td>
					<td>${exercise.lastSubmitDate}</td>
					<td>${exercise.lastTransactionDate}</td>
					<td><c:choose>
							<c:when
								test="${exercise.exerciseStatus == 'STATUS_EXERCISE_PASSED' }">
				    Exercise Passed
				</c:when>
							<c:when
								test="${exercise.exerciseStatus == 'STATUS_EXERCISE_SUBMITTED_BUT_AWAITING_FEEDBACK' }">
					Awaiting Grading
				</c:when>
							<c:when
								test="${exercise.exerciseStatus == 'STATUS_EXERCISE_MARKED_AND_REQUIRES_MORE_WORK' }">
					Needs More Work
				</c:when>
							<c:otherwise>
					Not Submitted
				</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>