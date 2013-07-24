<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<div id="mentorModules">

	<ul>
		<c:forEach var="module" items="${MODULELIST}">
			<li id="listM"
				onClick="JJTEACH.ajaxLoadTransactions('/mentor/listtransactions', 
			${module.mentorId},${module.moduleId})"
				title="click to open">${module.moduleName}&nbsp;<c:if
					test="${not empty module.pendingTransactions}">
					<span id="spanM">pending: ${module.pendingTransactions}</span>
				</c:if></li>
		</c:forEach>
	</ul>
</div>

