<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
$j(function() {
		$j("#mentortemplatetab").tabs();
	});
</script>
<div id="mentortemplatetab">
<ul>
		<!-- li><a href="#mentorTemplate">MentorTemplate</a></li>
		<li><a href="#assignExtoMt">Assign Exercises</a></li-->
</ul>
<div id="mentorTemplate">
<table id="list" border="0" cellspacing="0">
	<thead>
		<tr>
			<th valign="top" width="30%">NAME</th>
			<th valign="top" width="50%">DESCRIPTION</th>
			<th valign="top" width="30%">EDIT</th>
			
		</tr>
	</thead>
	<tbody>
		<c:forEach var="template" items="${MENTORTEMPLATESLIST}">
			<tr>
				<td valign="top">${template.name}</td>
				<td valign="top">${template.description}</td>
				
				<td valign="top">
					<button class="button"
						onClick=" 
						JJTEACH.ajaxEditTabbedTemplate(${template.id});">Edit</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
<div id="assignExtoMt"></div>
</div>