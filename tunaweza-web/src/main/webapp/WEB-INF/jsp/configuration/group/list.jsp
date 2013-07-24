<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<table id="list" border="0" cellspacing="0">
	<thead>
	  <tr rowspan='2'>
		<th valign="top" width="25%">GROUP NAME</th>
		<th valign="top" width="45%">DESCRIPTION</th>
		<th valign="top" width="45%">ADD USERS</th>
		<th valign="top" width="10%">EDIT</th>
		<th valign="top" width="10%">DELETE</th>
	  </tr>
 	</thead>		
	<tbody>
	<c:forEach var="group" items="${GROUPLIST}">
		<tr>
			<td valign="top">${group.name}</td>
			<td valign="top">${group.description}</td>
			<td valign="top">
			<button class="button" onClick="JJTEACH.ajaxGroupAddUsers('/user/addusertogroup',${group.id})">Add Users</button>
			</td>
			<td valign="top">
			<button class="button" onClick="JJTEACH.ajaxGroupEdit('/group/editgroupform',${group.id})">Edit</button></td>
			<td valign="top">
			<button class="button" onClick="JJTEACH.ajaxGroupDelete('/group/deleteGroup',${group.id})">Delete</button>
			</td>
			
		</tr>
	</c:forEach>
	</tbody>
</table>