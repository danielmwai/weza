<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
	$j(function() {
		$j("#sidebar div,#sidebar").css("display", "none");
		/* $j("#contents,#content").css("width", "100%"); */
	});
</script>
<div id="modules">
	<div class="fieldModules">
		<fieldset>
			<legend>Module List</legend>
			<ul id="studentmodules">
				<c:choose>
					<c:when test="${MODULESIZE!=0}">

						<c:forEach var="course" items="${MODULEMAP}">
							<c:choose>
								<c:when
									test="${course.key.status=='COURSE_AWAITING_EVALUATION' }">
									<li><c:out value="${course.key.name}" /> <span
										style="font-style: italic; margin-left: 60px; color: blue; font-size: 0.8em;"
										onclick="JJTEACH.courseEvaluation('/studenttest/course/start','${course.key.id}');"
										onmouseover="$j(this).css('text-decoration','underline');"
										onmouseout="$j(this).css('text-decoration','none');">
											Start Course Evaluation</span></li>

								</c:when>
								<c:otherwise>
									<li><c:out value="${course.key.name}" /></li>
								</c:otherwise>
							</c:choose>
							<c:forEach var="coursemodules" items="${course.value}">

								<c:choose>
									<c:when
										test="${coursemodules.status=='STATUS_MODULE_AWAITING_STUDENT'}">
										<li><span style="color: orange;"
											onclick="JJTEACH.setCurrentModule('/user/setcurrentmodule_onstudentclick','/topic/list/by/modules','${coursemodules.id}','${coursemodules.name}')">
												${coursemodules.name}</span></li>
									</c:when>
									<c:when
										test="${coursemodules.status=='MODULE_EVALUATION_DISABLED'}">
										<li><span style="color: blue;"
											onclick="JJTEACH.setCurrentModule('/user/setcurrentmodule_onstudentclick','/topic/list/by/modules','${coursemodules.id}','${coursemodules.name}')">
												${coursemodules.name}</span></li>
									</c:when>

									<c:when
										test="${coursemodules.status=='MODULE_STARTED_AWAITING_EVALUATION'}">
										<li><span style="color: blue;"
											onclick="JJTEACH.loadTopicTree('/topic/list/by/modules','${coursemodules.id}','${coursemodules.name}');">
												${coursemodules.name} </span> <span
											style="font-style: italic; margin-left: 60px; color: blue; font-size: 0.8em;"
											onclick="JJTEACH.moduleEvaluation('/studenttest/module/start','${coursemodules.id}');"
											onmouseover="$j(this).css('text-decoration','underline');"
											onmouseout="$j(this).css('text-decoration','none');">
												Start Module Evaluation</span></li>
									</c:when>

									<c:when
										test="${coursemodules.status=='MODULE_STARTED_EVALUATION_PASSED'}">
										<li><span style="color: blue;"
											onclick="JJTEACH.loadTopicTree('/topic/list/by/modules','${coursemodules.id}','${coursemodules.name}');">
												${coursemodules.name} </span> <span
											style="font-style: italic; margin-left: 60px; color: green; font-size: 0.8em;"
											onclick="JJTEACH.moduleEvaluation('/studenttest/module/start','${coursemodules.id}');">
												Module Evaluation Passed</span></li>
									</c:when>

									<c:when
										test="${coursemodules.status=='MODULE_COMPLETED_AWAITING_EVALUATION'}">
										<li><span style="color: green;"
											onclick="JJTEACH.loadTopicTree('/topic/list/by/modules','${coursemodules.id}','${coursemodules.name}');">
												${coursemodules.name} </span> <span
											style="font-style: italic; margin-left: 60px; color: blue; font-size: 0.8em;"
											onclick="JJTEACH.moduleEvaluation('/studenttest/module/start','${coursemodules.id}');"
											onmouseover="$j(this).css('text-decoration','underline');"
											onmouseout="$j(this).css('text-decoration','none');">
												Start Module Evaluation</span></li>
									</c:when>

									<c:when test="${coursemodules.status=='MODULE_NOT_STARTED'}">
										<li><span style="color: red;"
											onclick="alert('This module is not active. Complete enabled modules first!')">
												${coursemodules.name}</span></li>
									</c:when>

									<c:when test="${coursemodules.status=='STATUS_MODULE_CLOSED'}">
										<li><span style="color: red;"
											onclick="alert('complete enabled modules first')">
												${coursemodules.name}</span></li>
									</c:when>

									<c:when
										test="${coursemodules.status=='MODULE_COMPLETED_EVALUATION_PASSED'}">
										<li><span style="color: green;"
											onclick="JJTEACH.loadTopicTree('/topic/list/by/modules','${coursemodules.id}','${coursemodules.name}');">
												${coursemodules.name}</span></li>
									</c:when>
								</c:choose>
							</c:forEach>
						</c:forEach>
					</c:when>
					
					<c:otherwise>
						<li><c:out value="${MESSAGE}" /></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</fieldset>
	</div>
</div>