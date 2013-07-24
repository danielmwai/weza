<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<table id="list" border="0" cellspacing="0">
	<thead>
		<tr rowspan='2'>
			<th valign="top" width="20%">COMPANY ID</th>
			<th valign="top" width="25%">EMAIL</th>
			<th valign="top" width="45%">COMPANY</th>
			<th valign="top" width="45%">CHANGE PASSWORD</th>

		</tr>
	</thead>
	<tbody>
		<c:forEach var="company" items="${COMPANYLIST}">
			<tr>
				<td valign="top">${company.id}</td>
				<td valign="top">${company.email}</td>
				<td valign="top">${company.name}</td>
				<td valign="top">
					<button class="button"
						onClick="JJTEACH.ajaxAdminPasswordEdit('/cloud/changeadminpassword',${company.id})">change</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
