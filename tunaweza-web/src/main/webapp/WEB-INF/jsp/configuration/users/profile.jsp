<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<legend>My Profile</legend>
		<table id="profile">
			<c:if test="${userBean.role == 'EVALUATOR'}">
				<tr>
					<td width="40%" valign="top">Company Name:</td>
					<td width="60%" valign="top">${userBean.lastName}</td>
				</tr>
			</c:if>
			<c:if test="${userBean.role != 'EVALUATOR'}">
				<tr>
					<td width="40%" valign="top">First name:</td>
					<td width="60%" valign="top">${userBean.firstName}</td>
				</tr>
				<tr>
					<td width="40%" valign="top">Last name:</td>
					<td width="60%" valign="top">${userBean.lastName}</td>
				</tr>
			</c:if>
			<%--
			<tr>
				<td width="40%" valign="top">Registration number:</td>
				<td width="60%" valign="top">${userBean.regNo}</td>
			</tr>
			--%>

			<tr>
				<td width="40%" valign="top">User Role:</td>
				<td width="60%" valign="top">${userBean.role}</td>
			</tr>
			<c:set value="${userBean.role}" var="role" />
			<c:if test="${userBean.student == role}">
				<tr>
					<td width="40%" valign="top">Start date:</td>
					<td width="60%" valign="top">${userBean.startDate}</td>
				</tr>
				<tr>
					<td width="40%" valign="top">Current course:</td>
					<td width="60%" valign="top">${userBean.currentCourse}</td>
				</tr>
			</c:if>
			<tr>
				<td width="40%" valign="top">Creation Date:</td>
				<td width="60%" valign="top">${userBean.creationDate}</td>
			</tr>
			<tr>
				<td width="40%" valign="top">Email:</td>
				<td width="60%" valign="top">${userBean.email}</td>
			</tr>
			<c:if test="${userBean.role == 'EVALUATOR'}">
				<tr>
					<td width="40%" valign="top">Website:</td>
					<td width="60%" valign="top">${userBean.website}</td>
				</tr>
				<tr>
					<td width="40%" valign="top">Location:</td>
					<td width="60%" valign="top">${userBean.location}</td>
				</tr>
				<tr>
					<td width="40%" valign="top">Description:</td>
					<td width="60%" valign="top">${userBean.description}</td>
				</tr>
			</c:if>
			<tr>
				<td width="40%" valign="top">
					<button class="button"
						onClick="JJTEACH.ajaxUserEdit('/user/editemailform',${userBean.id}); return false;">
						Edit email</button>
				</td>
				<td width="60%" valign="top">
					<button class="button"
						onClick="JJTEACH.ajaxUserEdit('/user/editpassword',${userBean.id}); return false;">
						Edit password</button>
				</td>
			</tr>

		</table>
	</fieldset>
</div>