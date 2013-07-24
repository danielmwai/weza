<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript" src="${PREFIX}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${PREFIX}/js/jquery.min.js"></script>
<script type="text/javascript" src="${PREFIX}/js/json.min.js"></script>
<script type="text/javascript" src="${PREFIX}/js/JJTEACH.js"></script>
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
	<table style="visibility: hidden;">
		<tr id="default_row">
			<td><select class="field" name="custom_fields">
					<c:forEach var="field" items="${FIELD_LIST}">
						<option value="${field}">${TABLE_NAME}.${field}</option>
					</c:forEach>
			</select></td>
			<td><select class="operators" name="operators">
					<option value="=">></option>
					<option value="="><</option>
					<option value="=">=</option>
					<option value="<"><=</option>
					<option value=">">>=</option>
					<option value="!=">!=</option>
					<option value=" LIKE ">LIKE</option>
			</select></td>
			<td><input type="text" name="field_values"></td>
			<td><select class="field_concat" name="field_concat">
					<option value="AND">AND</option>
					<option value="OR">OR</option>
			</select></td>
		</tr>
		<tr id="table_list">
			<td><select class="join_tables" name="joinTables">
					<c:forEach var="table" items="${TABLE_LIST}">
						<option value="${table.tableName}">${table.tableName}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr id="default_field">
			<td><select class="display_fields" name="display_fields">
					<c:forEach var="field" items="${FIELD_LIST}">
						<option value="${field}">${TABLE_NAME}.${field}</option>
					</c:forEach>
			</select></td>
			<td><input type="text" name="field_aliases"></td>
		</tr>
	</table>
	<form  id="ajaxform" action='${PREFIX}/report/customreport.htm' method='POST'
		style="margin-top: -40px">

		<div id="field_conditions" width="100%">
			<input type="hidden" name="table" value="${TABLE_NAME}">
			<table id="custom_fields" style='padding: 2;'>
				<tr>
					<td colspan=4><b>2.Add conditions or criteria to be met in
							the report</b></td>
				</tr>
			</table>
			<input type="button" onClick="JJTEACH.ajaxAddFieldRow()"
				value="Add Condition" /> <input type="button"
				onClick="JJTEACH.ajaxRemoveLastFieldRow()" value="Remove Condition" />
		</div>
		<div id='display_fields' width='100%'>

			<table id='display_fields_table' style='padding: 2;'>
				<th colspan=2>3.Add Fields and the name to be displayed for the column in
					the report</th>
				<tr>
					<td><select class="display_fields" name="display_fields">
							<c:forEach var="field" items="${FIELD_LIST}">
								<option value="${field}">${TABLE_NAME}.${field}</option>
							</c:forEach>
					</select></td>
					<td><input type="text" name="field_aliases" value='field name'></td>
				</tr>
			</table>
			<input type="button" onClick="JJTEACH.ajaxAddDisplayField()"
				value="Add Field" /> <input type="button"
				onClick="JJTEACH.ajaxRemoveDisplayField()" value="Remove Field" />
		</div>

		<div id="join_tables">

			<table id='join_tables' style='padding: 2;'>
				<th>4.Add Related Tables That Contain Information Needed In the Report</th>
			</table>
			<input type="button" onClick="JJTEACH.ajaxAddJoinTable()"
				value="Add Table" /> <input type="button"
				onClick="JJTEACH.ajaxRemoveJoinTable()" value="Remove Table" />
		</div>

		<div width='100%'>
			<table>
				<tr>
					<input type="hidden" name="isJoin" value="false">
					<input type="hidden" name="isMainTable" value="true">
					<input type="hidden" name="mainTable" value="${TABLE_NAME}">
					<td><input type="button" id="ajaxsubmit" value="Next"></td>
				</tr>
			</table>
		</div>
	</form>