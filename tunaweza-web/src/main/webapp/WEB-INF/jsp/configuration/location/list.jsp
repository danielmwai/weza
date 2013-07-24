<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<c:if test="${not empty ERROR}">
	<div id="deleteError" style="display: none;">${ERROR}</div>
</c:if>

<table id="list" border="0" cellspacing="0">
	<thead>
		<tr rowspan='2'>
			<th valign="top" width="20%">LOCATION ID</th>
			<th valign="top" width="25%">LOCATION NAME</th>
			<th valign="top" width="45%">DESCRIPTION</th>
			<th valign="top" width="10%">EDIT</th>
			<th valign="top" width="10%">DELETE</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="location" items="${LOCATIONLIST}">
			<tr>
				<td valign="top">${location.id}</td>
				<td valign="top">${location.locationName}</td>
				<td valign="top">${location.description}</td>
				<td valign="top">
					<button class="button"
						onClick="JJTEACH.ajaxLocationEdit('/location/editlocationform',${location.id})">Edit</button>
				</td>
				<td valign="top">
					<button class="button"
						onClick="JJTEACH.ajaxLocationDelete('/location/deleteLocation',${location.id})">Delete</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
$j()
.ready(
		function() {
			if($j("#deleteError").html()) {
				alert($j("#deleteError").html());
				$j("#deleteError").html("");
			}
		});
</script>