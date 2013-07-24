<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<ul id="innermenu">
	<c:forEach var="menus" items="${JJTEACH_ROLE}">
		<c:if test="${menus.description != 'Home'}">
			<li id="${PREFIX}/${menus.viewurl}" onclick="JJTEACH.loadMenu(this)">${menus.description}</li>
		</c:if>
	</c:forEach>
</ul>