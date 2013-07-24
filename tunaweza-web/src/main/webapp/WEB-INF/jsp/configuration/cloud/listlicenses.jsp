<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<table id="list" border="0" cellspacing="0">
	<thead>
	  <tr rowspan='2'>
		<th valign="top" width="20%">LICENSE ID</th>
		<th valign="top" width="25%">LICENSE NAME</th>
		<th valign="top" width="45%">COST</th>
		<th valign="top" width="10%">NUMBER OF USERS</th>
	  </tr>
 	</thead>		
	<tbody>
	<c:forEach var="license" items="${LICENSELIST}">
		<tr>
			<td valign="top">${license.id}</td>
			<td valign="top">${license.name}</td>
			<td valign="top">${license.cost}</td>
			<td valign="top">${license.max_users}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
