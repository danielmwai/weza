<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<table id="list" border="0" cellspacing="0">
	<thead>
	  <tr rowspan='2'>
		<th valign="top" width="20%">ROLE NUMBER</th>
		<th valign="top" width="25%">ROLE NAME</th>
		<th valign="top" width="45%">DESCRIPTION</th>
		<th valign="top" width="10%">EDIT</th>
	  </tr>
 	</thead>		
	<tbody>
	<c:forEach var="role" items="${ROLELIST}">
		<tr>
			<td valign="top">${role.number}</td>
			<td valign="top">${role.roleName}</td>
			<td valign="top">${role.description}</td>
			<td valign="top">
			<button class="button" onClick="JJTEACH.ajaxEdit('/role/editroleform',${role.id})">Edit</button>
			<!-- 
				<form method="post" action="${PREFIX}/role/editroleform.htm" target="editRoles" 
				onSubmit="$j('#list').html('');"> 
					<input type="hidden" name="roleId" value="${role.id}" />
					<input type="submit" value="Edit" class="button" />
				</form>
			--></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
