
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
$j(function(){

	$j("#courseTemplate").tabs();
});
</script>
<div id="courseTemplate">
<ul>
<!-- li><a href="#templates">CourseTemplates</a></li>
<li><a href="#templatemodules" id="contentTitle">Assign CT Modules</a></li-->
</ul>
<div id="templates">
<table id="list" border="0" cellspacing="0">
	<thead>
		<tr>
			<th valign="top" width="30%">NAME</th>
			<th valign="top" width="50%">DESCRIPTION</th>
			<th></th>
			
		</tr>
	</thead>
	<tbody>
		<c:forEach var="template" items="${COURSETEMPLATESLIST}">
			<tr>
				<td valign="top">${template.name}</td>
				<td valign="top">${template.description}</td>
				
				<td valign="top">
					<button class="button"
						onClick="JJTEACH.ajaxEditCT('/coursetemplate/editcoursetemplateform', ${template.id})">Edit</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
<div id="templatemodules"></div>
</div>
