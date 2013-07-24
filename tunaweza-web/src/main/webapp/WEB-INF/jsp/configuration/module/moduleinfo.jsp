<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadowMT">
<fieldset><legend>Module Info</legend> <c:set var="module"
	value="${editModuleBean}"></c:set>
<table id="table-form-input">
	<colgroup>
		<col class="oce-first" />
	</colgroup>
	<tbody class="information">

		<tr>
			<td width="30%" valign="top">Module name:</td>
			<td width="69%" valign="top"><c:out value="${module.moduleName}"></c:out>
			</td>
		</tr>

		<tr>
			<td width="30%" valign="top">Module Description:</td>
			<td width="69%" valign="top">${module.moduleDescription}</td>
		</tr>
		<tr>
			<td width="30%" valign="top">Time to Complete:</td>
			<td width="69%" valign="top"><c:out
				value="${module.timeToComplete}"></c:out></td>
		</tr>
		<tr>
			<td width="30%">&nbsp;</td>
			<td width="69%" align="left">
			<button class="button"
			onClick="JJTEACH.ajaxEditModule('/module/editmoduleform',
			${module.moduleId});">Edit</button>
			</td>
		</tr>

	</tbody>
</table>
</fieldset>
</div>