<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript" src="${PREFIX}/js/jquery.form.js"></script>
<script>
	$(document).ready(function() {
		$('#ajaxsubmit').bind('click', function(event) {
			$('#ajaxform').ajaxSubmit({
				target : $('#reportDiv')
			});
		})
	});
</script>
<div id="reportDiv">
<script>
	$(document).ready(function() {
		$('#ajaxsubmit').bind('click', function(event) {
			$('#ajaxform').ajaxSubmit({
				target : $('#reportDiv')
			});
		})
	});
</script>
<table style='padding: 2;'>
	<th>1.Choose The Main Table For The Report</th>
	<tr>
		<td><select id="table_select_box">
				<c:forEach var="table" items="${TABLE_LIST}" varStatus="status">
					<option value="${table.tableName}">${table.tableName}</option>
				</c:forEach>
		</select></td>
	</tr>
</table>
<div id='report_table_div'></div>
<script type="text/javascript">
	$j("#table_select_box").change(function() {
		JJTEACH.ajaxLoaderB('#report_table_div');
		var table = $j("select#table_select_box").val();
		JJTEACH.ajaxLoadReportTable(table);
		return false;
	});
</script>
</div>