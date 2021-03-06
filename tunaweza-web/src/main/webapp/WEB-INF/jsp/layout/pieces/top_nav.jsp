<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div id="top_nav">
	
	<c:if test="${not empty DEALS_MENU}">
		<div id="top_nav_links">
			<c:forEach var="menus" items="${DEALS_MENU}">
				<a id="${PREFIX}/${menus.url}" href="#" onclick="JJETS.loadpage(this,'content_loader')">${menus.perm}</a><br/>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${not empty USERS_MENU}">
		<div id="top_nav_links">
			<c:forEach var="menus" items="${USERS_MENU}">
				<a id="${PREFIX}/${menus.url}" href="#" onclick="JJETS.loadpage(this,'content_loader')">${menus.perm}</a><br/>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${not empty EMPLOYEE_MENU}">
		<div id="top_nav_links">
			<c:forEach var="menus" items="${EMPLOYEE_MENU}">
				<a id="${PREFIX}/${menus.url}" href="#" onclick="JJETS.loadpage(this,'content_loader')">${menus.perm}</a><br/>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${not empty TIMESHEET_MENU}">
		<div id="top_nav_links">
			<c:forEach var="menus" items="${TIMESHEET_MENU}">
				<a id="${PREFIX}/${menus.url}" href="#" onclick="JJETS.loadpage(this,'content_loader')">${menus.perm}</a><br/>
			</c:forEach>
		</div>
	</c:if>
	<c:forEach var="menus" items="${OTHER_MENU}">
		<div id="top_nav_links"><a id="${PREFIX}/${menus.url}" href="#" onclick="JJETS.loadpage(this,'content_loader')">${menus.perm}</a></div>
	</c:forEach>
	<c:if test="${not empty EMPLOYEE_STATUS}">
		<div id="top_nav_links">
			<a id="${PREFIX}/${EMPLOYEE_STATUS.url}" href="#" onclick="JJETS.loadpage(this,'content_loader')">${EMPLOYEE_STATUS.perm}</a><br/>
		</div>
	</c:if>
</div>