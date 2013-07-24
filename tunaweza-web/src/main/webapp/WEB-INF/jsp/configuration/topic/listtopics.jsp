<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>


<table id='list' cellspacing='0' border='0'>

	<thead>
		<tr>
			<td colspan="5">
				<button id='newtopic' onclick='JJTEACH.ajaxTopicAdd(${MODULEID})'>ADD
					NEW TOPIC</button>
			</td>
		</tr>
		<tr>
			<th valign='top' width='35%'>TOPIC NAME</th>
			<th valign='top' width='10%'>TYPE</th>
			<th valign='top' width='35%'>PARENT</th>
			<th valign='top' width='10%'>LEVEL</th>
			<th valign="top" width="50%">VIEW QUESTIONS</th>
			<th valign='top' width='5%'>ENABLED</th>
			<th valign='top' width='15%'>EDIT</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${not empty COUNT}">
				<c:forEach var="topic" items="${TOPICLIST}">
					<tr>
						<td valign='top'>${topic.topicName}</td>
						<td valign='top'><c:choose>
								<c:when test="${topic.exercise }">Exercise</c:when>
								<c:otherwise>Topic</c:otherwise>
							</c:choose></td>
						<td valign='top'>${topic.parentName}</td>
						<td valign='top'>${topic.level}</td>
						<td valign='top'><button
								onClick="JJTEACH.ajaxViewEvaluationQuestionsByTopic(${topic.topicId}, '${topic.topicName}', ${MODULEID})">View
								Questions</button></td>
						<td valign='top' align='center'><c:choose>
								<c:when test="${topic.enabled ==1 }">
									<input type="checkbox" name="enabled" checked
										value="<c:out value="${topic.topicId}"/>"
										onClick="JJTEACH.changeStatusTopic(${topic.topicId}, ${MODULEID} <c:if
						test="${not empty TOTAL_PAGES}">, ${PAGE_NUMBER}</c:if>)" />
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="enabled"
										value="<c:out value="${topic.topicId}"/>"
										onClick="JJTEACH.changeStatusTopic(${topic.topicId}, ${MODULEID} <c:if
						test="${not empty TOTAL_PAGES}">, ${PAGE_NUMBER}</c:if>)" />
								</c:otherwise>
							</c:choose></td>
						<td valign='top' align='center'>
							<button class='button'
								onClick='JJTEACH.ajaxTopicEdit(${topic.topicId}, ${MODULEID})'>Edit</button>
						</td>
					</tr>
				</c:forEach>

				<tr class="pagin">
					<td colspan="7" align="center">
						<div class="pages" id="pageLink" align="center">
							<c:if test="${not empty TOTAL_PAGES}">
								<c:if test="${PAGE_NUMBER > 1}">
									<button onclick="JJTEACH.ajaxTopicList(1, ${MODULEID})">First</button>
									<button class="pagenumb"
										onclick="JJTEACH.ajaxTopicList(${PAGE_NUMBER-1}, ${MODULEID})">
										<c:out value="<< Previous" />
									</button>

								</c:if>
								<c:if test="${PAGE_NUMBER >2}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxTopicList(${PAGE_NUMBER-2}, ${MODULEID})">
										<c:out value="${PAGE_NUMBER-2}" />
									</button>
								</c:if>
								<c:if test="${PAGE_NUMBER >1}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxTopicList(${PAGE_NUMBER-1}, ${MODULEID})">
										<c:out value="${PAGE_NUMBER-1}" />
									</button>
								</c:if>
								<c:out value="${PAGE_NUMBER}" />
								<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxTopicList(${PAGE_NUMBER+1}, ${MODULEID})">
										<c:out value="${PAGE_NUMBER+1}" />
									</button>
								</c:if>
								<c:if test="${PAGE_NUMBER+1 < TOTAL_PAGES}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxTopicList(${PAGE_NUMBER+2}, ${MODULEID})">
										<c:out value="${PAGE_NUMBER+2}" />
									</button>
								</c:if>
								<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
									<button class="next"
										onclick="JJTEACH.ajaxTopicList(${PAGE_NUMBER+1}, ${MODULEID})">
										<c:out value="Next >>" />
									</button>
									<button
										onclick="JJTEACH.ajaxTopicList(${TOTAL_PAGES}, ${MODULEID})">Last</button>
								</c:if>
							</c:if>
						</div>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td valign='top'>No topics has been added in this module</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>