<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<table id="list" border="0" cellspacing="0">
	<c:choose>
		<c:when test="${not empty NOMODULES}">
			<tr>
				<td colSpan="6" align="center">${NOMODULES}</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<td colSpan="3">Last Login: ${LASTLOGIN}</td>
			</tr>
			<tr>
				<th>Module</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Topic %</th>
				<th>Exercises %</th>
				<th>Pending Exercises</th>
				<th>Test %</th>
				<th>Enabled</th>
			</tr>
			<c:forEach var="course" items="${MODULEMAP}">
				<tr>
					<td colSpan="6"><c:out
							value="${course.key.courseTemplateName}" /></td>
					<td><c:out value="${course.key.testScore}" /></td>
				</tr>
				<c:forEach var="monitormodulebean" items="${course.value}">
					<tr>
						<td valign="top"><a
							onClick="JJTEACH.ajaxViewModuleExercises('/user/list/exercises/by/module',${USERID},${monitormodulebean.id},'${monitormodulebean.moduleName}')">${monitormodulebean.moduleName}</a>
						</td>
						<td valign="top">${monitormodulebean.startDate}</td>
						<td valign="top">${monitormodulebean.endDate}</td>
						<td valign="top">${monitormodulebean.topicPercentage}</td>
						<td valign="top">${monitormodulebean.exercisePercentage}</td>
						<td valign="top">${monitormodulebean.pendingExercises}</td>
						<td valign="top">${monitormodulebean.testScore}</td>
						<td valign="top" halign="center"><c:choose>
								<c:when test="${monitormodulebean.moduleEnabled==true}">
									<input id="check_${monitormodulebean.id}" type="checkbox"
										onClick="JJTEACH.ajaxToggleUserModuleStatus(${USERID},${monitormodulebean.id},'#check_${monitormodulebean.id}')"
										checked />
								</c:when>
								<c:otherwise>
									<input id="check_${monitormodulebean.id}" type="checkbox"
										onClick="JJTEACH.ajaxToggleUserModuleStatus(${USERID},${monitormodulebean.id},'#check_${monitormodulebean.id}')" />
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</c:forEach>
			<tr>
				<td><b>Download Report</b> |<a
					href="${PREFIX}/report/studentprogressreport.htm?reportType=pdf&userId=${USERID}"><img
						src="${PREFIX}/images/icons/docs/pdf.png" />PDF</a>&nbsp;|<a
					href="${PREFIX}/report/studentprogressreport.htm?reportType=xls&userId=${USERID}"><img
						src="${PREFIX}/images/icons/docs/xls.png" />EXCEL</a>&nbsp;|<a
					href="${PREFIX}/report/studentprogressreport.htm?reportType=csv&userId=${USERID}"><img
						src="${PREFIX}/images/icons/docs/csv.png" />CSV</a></td>
			</tr>
		</c:otherwise>

	</c:choose>
</table>
