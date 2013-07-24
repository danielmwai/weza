<%--
 	@Author Gideon Kakai 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<table id="list" border="0" cellspacing="0">
	<thead>

		<tr>
			<th valign="top" width="20%">FIRST NAME</th>
			<th valign="top" width="20%">LAST NAME</th>
			<th valign="top" width="25%">EMAIL</th>
			<th valign="top" width="5%">ENABLED</th>
			<th valign="top" width="10%">DETAILS</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${not empty NOLIST}">
				<tr>
					<td colSpan="6" align="center">${NOLIST}</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="user" items="${USERSLIST}">
					<tr>
						<td valign="top">${user.firstName}</td>
						<td valign="top">${user.lastName}</td>
						<td valign="top">${user.email}</td>
						<td valign="top" align="center"><c:choose>
								<c:when test='${user.enabled == 1}'>
									<input type="checkbox" name="enabled" checked
										value="<c:out value="${user.id}" />"
										onClick="JJTEACH.ajaxChangeCloudUserStatus('/user/changecloudUserstatus', ${user.id})" />
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="enabled"
										value="<c:out value="${user.id}"/>"
										onClick="JJTEACH.ajaxChangeCloudUserStatus('/user/changecloudUserstatus', ${user.id})" />
								</c:otherwise>
							</c:choose></td>

						<td valign="top">
							<button class="button" onClick="JJTEACH.ajaxUserInfo(${user.id})">Details</button>
						</td>
					</tr>
				</c:forEach>

				<tr class="pagin">
					<td colspan="6" align="center">
						<div class="pages" id="pageLink" align="center">
							<c:if test="${not empty TOTAL_PAGES}">
								<c:if test="${PAGE_NUMBER > 1}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER-1}, $j('#role :selected').val())">
										<c:out value="<< Previous" />
									</button>
								</c:if>
								<c:if test="${PAGE_NUMBER >2}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER-2}, $j('#role :selected').val())">
										<c:out value="${PAGE_NUMBER-2}" />
									</button>
								</c:if>
								<c:if test="${PAGE_NUMBER >1}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER-1}, $j('#role :selected').val())">
										<c:out value="${PAGE_NUMBER-1}" />
									</button>
								</c:if>
								<c:out value="${PAGE_NUMBER}" />
								<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER+1}, $j('#role :selected').val())">
										<c:out value="${PAGE_NUMBER+1}" />
									</button>
								</c:if>
								<c:if test="${PAGE_NUMBER+1 < TOTAL_PAGES}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER+2}, $j('#role :selected').val())">
										<c:out value="${PAGE_NUMBER+2}" />
									</button>
								</c:if>
								<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
									<button class="next"
										onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER+1}, $j('#role :selected').val())">
										<c:out value="Next >>" />
									</button>
								</c:if>
							</c:if>
						</div>
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>

<script type="text/javascript">
	$j("#role").change(
			function() 
            {
				var role = $j("#role :selected").val();
				JJTEACH.ajaxList(role);
				return false;
            });
</script>