<%--
 	@Author Gideon Kakai 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>


<p align="center">
	Filter Users by Role:
	<form:select path="listUserBean.role" id="role" cssClass="forminput">
		<form:option value="NONE" label="All" />
		<form:options items="${ROLELIST}" itemValue="roleName"
			itemLabel="roleName" />
	</form:select>
</p>

<div id="usersList">

	<table id="list" border="0" cellspacing="0">
		<thead>
			<%-- 
		<c:if test="${HIDEROLES == 'DONTHIDEROLES'}">
			<tr>
				<td><form:select path="listUserBean.role" id="role"
						cssClass="forminput">
						<form:option value="NONE" label="Select Role" />
						<form:options items="${ROLELIST}" itemValue="roleName"
							itemLabel="roleName" />
					</form:select></td>
				<c:choose>
					<c:when test="${not empty ROLE}">
						<td colSpan="4">Filtering Users by ${ROLE}</td>
					</c:when>
					<c:otherwise>
						<td>All Users</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:if>
				
		--%>

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
											onClick="JJTEACH.ajaxChangeStatusUser('/user/changestatus', ${user.id})" />
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="enabled"
											value="<c:out value="${user.id}"/>"
											onClick="JJTEACH.ajaxChangeStatusUser('/user/changestatus', ${user.id})" />
									</c:otherwise>
								</c:choose></td>

							<td valign="top">
								<button class="button"
									onClick="JJTEACH.ajaxUserInfo(${user.id})">Details</button>
							</td>
						</tr>
					</c:forEach>

					<tr class="pagin">
						<td colspan="6" align="center">
							<div class="pages" id="pageLink" align="center">
								<c:if test="${not empty TOTAL_PAGES}">
									<c:if test="${PAGE_NUMBER > 1}">
										<button class="pagenumb"
											onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER-1}, selectedRole)">
											<c:out value="<< Previous" />
										</button>
									</c:if>
									<c:if test="${PAGE_NUMBER >2}">
										<button class="pagenumb"
											onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER-2}, selectedRole)">
											<c:out value="${PAGE_NUMBER-2}" />
										</button>
									</c:if>
									<c:if test="${PAGE_NUMBER >1}">
										<button class="pagenumb"
											onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER-1}, selectedRole)">
											<c:out value="${PAGE_NUMBER-1}" />
										</button>
									</c:if>
									<c:out value="${PAGE_NUMBER}" />
									<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
										<button class="pagenumb"
											onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER+1}, selectedRole)">
											<c:out value="${PAGE_NUMBER+1}" />
										</button>
									</c:if>
									<c:if test="${PAGE_NUMBER+1 < TOTAL_PAGES}">
										<button class="pagenumb"
											onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER+2}, selectedRole)">
											<c:out value="${PAGE_NUMBER+2}" />
										</button>
									</c:if>
									<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
										<button class="next"
											onclick="JJTEACH.ajaxListUsers(${PAGE_NUMBER+1}, selectedRole)">
											<c:out value="Next >>" />
										</button>
									</c:if>
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan=5>Download|<a
							onClick="JJTEACH.ajaxReportDownload('/report/user.htm?reportType=pdf')">PDF<img
								src="${PREFIX}/images/icons/docs/pdf.png" /></a>|<a
							onClick="JJTEACH.ajaxReportDownload('/report/user.htm?reportType=xls')">EXCEL<img
								src="${PREFIX}/images/icons/docs/xls.png" /></a>|<a
							onClick="JJTEACH.ajaxReportDownload('/report/user.htm?reportType=csv')">CSV<img
								src="${PREFIX}/images/icons/docs/csv.png" /></a></td>
					</tr>
				</c:otherwise>
			</c:choose>

		</tbody>
	</table>
</div>

<script type="text/javascript">
var selectedRole;
	$j("#role").change(
			function() 
            {
				var role = $j("select#role").val();
				//alert(role);
				JJTEACH.ajaxList(role);
				selectedRole = role;
				return false;
            });
	
	
</script>