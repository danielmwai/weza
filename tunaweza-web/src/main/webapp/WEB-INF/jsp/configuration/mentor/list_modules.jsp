<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<div id="modules">
	<div class="fieldModules">
		<fieldset>
			<legend>Module List</legend>
			<ul id="mentormodules">
				<c:choose>
					<c:when test="${not empty NOMODULES}">
						<li>${NOMODULES}</li>
					</c:when>
					<c:otherwise>
						<c:forEach var="module" items="${MODULELIST}">
							<li><span
								onclick="JJTEACH.loadTopicTree('/topic/list/by/modules','${module.id}','${module.name}');">
									${module.name}</span>
							</li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</ul>
		</fieldset>
	</div>
</div>


