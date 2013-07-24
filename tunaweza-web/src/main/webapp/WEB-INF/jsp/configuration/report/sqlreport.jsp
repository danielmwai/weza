<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script>
	$(document).ready(function() {
		$('#ajaxsubmit').bind('click', function(event) {
			$('#ajaxform').ajaxSubmit({
				target : $('#reportDiv')
			});
		})
	});
</script>
<div class="fieldShadow" style="margin-top: 25px;width:100%">
	<form id="ajaxform" action="${PREFIX}/report/sqlreport.htm" target="#preview" method="POST">
		<span class="legend">Report Title <input type="text" name="reportTitle" style="padding:5px;" id="title"></span>
		<br><span class="legend">Enter SQL Query To Generate Report</span>
		<textarea id="customquery" name="customquery"
			style="width: 980px; height: 47px;"></textarea>
		 <input type="button"
			onClick="JJTEACH.ajaxReportPreview($j('#customquery').val(),$j('#title').val())"
			name="preview_btn" value="Preview"/>
		Download Format 
		<select name="reportType" id="reportType">
			<option value="pdf">Pdf</option>
			<option value="xls">Excel</option>
			<option value="csv">Csv</option>
			<option value="odt">Odt</option>
			<option value="docx">Docx</option>
		</select>
		Save?
		<input id="save" type="checkbox" value="true" name="save"/>
		<input type="button" value="Download Report" onClick="JJTEACH.ajaxSqlReportDownload()"/>
	</form>
</div>
<div id="preview" style="width:100%;overflow:auto;" align="center"></div>