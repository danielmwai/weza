<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadowMT">
<fieldset><legend>CT Info</legend> <c:set var="template"
	value="${editCourseTemplateBean}"></c:set>
<table id="table-form-input">
	<colgroup>
		<col class="oce-first" />
	</colgroup>
	<tbody class="information">

		<tr>
			<td width="30%" valign="top">Template name:</td>
			<td width="69%" valign="top"><c:out value="${template.name}"></c:out>
			</td>
		</tr>


		<tr>
			<td width="30%" valign="top">Template Description:</td>
			<td width="69%" valign="top">${template.description}</td>
		</tr>
		
		<tr>
			<td width="30%">&nbsp;</td>
			<td width="69%" align="left">
			<button class="button"
			onClick="JJTEACH.ajaxEditCT('/coursetemplate/editcoursetemplateform', ${template.id})">Edit</button>
			
			</td>
			
		
		</tr>

	</tbody>
</table>
</fieldset>
</div>