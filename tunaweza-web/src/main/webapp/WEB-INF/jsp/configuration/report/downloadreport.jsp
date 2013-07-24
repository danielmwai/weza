<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript" src="${PREFIX}/js/jquery.form.js"></script>
<form  id="ajaxform" action="${PREFIX}/report/sqlreport.htm" method="POST" target="#preview">
	<input type="hidden" id="customquery" name="customquery" value="${SQL_STRING}"></textarea>
	<b>Report Title<input type="text" name="reportTitle" id="title" value="Report Title"/></b>
	<div width='100%'>
		<table>
			<tr><td>
			<input type="button"
			onClick="JJTEACH.ajaxReportPreview($j('#customquery').val(),$j('#title').val())"
			name="preview_btn" value="Preview" /></td><td>Show SQL<input id="showsql" type="checkbox" value="false" onclick="JJTEACH.ajaxShowReportSQL()"/></td>
				<td>Format<select name="reportType" id="reportType">
						<option value="pdf">Pdf</option>
						<option value="xls">Excel</option>
						<option value="csv">Csv</option>
						<option value="odt">Odt</option>
						<option value="docx">Docx</option>
				</select>
				</td>
				<td>Save <input type="checkbox" value="true" name="save" id="save"/></td>
				<td><input type="button" value="Download Report" onClick="JJTEACH.ajaxSqlReportDownload()"/></td>
			</tr>
		</table>
	</div>
	<div id="sqldiv" style="width: 980px; height: 47px;display:none">SQL STATEMENT [ ${SQL_STRING} ]</div>
<div id="preview" style="width:100%;overflow:auto;" align="center"></div>
</form>