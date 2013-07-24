<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadowMT">
<fieldset><legend>Company Setting info</legend> <c:set var="company"
	value="${companySettingBean}"></c:set>
<table id="table-form-input">
	<colgroup>
		<col class="oce-first" />
	</colgroup>
	<tbody class="information">

		<tr>
			<td width="30%" valign="top">Company Logo:</td>
			<td width="69%" valign="top" style="background-color:${company.header_color_code}" ><img alt="logo" 
			src="${PREFIX}/companysetting/imagedisplay.htm"
			width="150" height="100"
			>
			</td>
		</tr>

		<tr>
			<td width="30%" valign="top">Company Background:</td>
			<td width="30px" valign="top"  style="background-color:${company.bg_color_code};width:30px">
			</td>
		</tr>
		<tr>
			<td width="30%" valign="top">Company Header Color </td>
			<td width="69%" valign="top" style="background-color:${company.header_color_code}"></td>
		</tr>
		<tr>
			<td width="30%" valign="top">Company Menu Color </td>
			<td width="69%" valign="top" style="background-color:${company.menu_color_code}"></td>
		</tr>
		<tr>
			<td width="30%" valign="top">Company Menu Font Color </td>
			<td width="69%" valign="top" style="background-color:${company.font_color_code}"></td>
		</tr>
		<tr>
			<td width="30%" valign="top">Header links color </td>
			<td width="69%" valign="top" style="background-color:${company.header_links_color_code}"></td>
		</tr>
		<tr>
			<td width="20%" valign="top">Mentoring:</td>
			<c:if test="${company.mentoring == true}">
			<td width="79%" valign="top">Enabled</td>
			</c:if>
			<c:if test="${company.mentoring == false}">
			<td width="79%" valign="top">Disabled</td>
			</c:if>
		</tr>
		<tr>
			<td width="30%">&nbsp;</td>
			<td width="69%" align="left">
			<button class="button"
			onClick="JJTEACH.ajaxLoad('/companysetting/editcompanysetting');">Edit</button> &nbsp; &nbsp; &nbsp; <input type ="button" value="Apply Changes"  onclick="window.location='${PREFIX}/home/index.htm'">
			</td>
		</tr>

	</tbody>
</table>
</fieldset>
</div>