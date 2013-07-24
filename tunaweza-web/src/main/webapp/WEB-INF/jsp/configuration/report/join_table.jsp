<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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

	<tr id="default_field">
		<td><select class="display_fields" name="display_fields">
				<c:forEach var="field" items="${FIELD_LIST}">
					<option value="${field}">${TABLE_NAME}.${field}</option>
				</c:forEach>
		</select></td>
		<td><input type="text" name="field_aliases"></td>
	</tr>
</table>
<form id="ajaxform" action='${PREFIX}/report/customreport.htm'
	method='POST' style="margin-top: -40px">
	<input type="hidden" name="step" value='${STEP_NO}'>
	<input type="hidden" name="isJoin" value="true"> <input
		type="hidden" name="isMainTable" value="false"> <input
		type="hidden" name="sqlString" value="${SQL_STRING}"> <input
		type="hidden" name="mainTable" value="${MAIN_TABLE}">
	<c:choose>
		<c:when test="${not empty JOIN_TABLES}">
			<c:forEach var="table" items="${JOIN_TABLES}">
				<input type="hidden" name="joinTables" value="${table}">
			</c:forEach>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${not empty REPORT_TABLES}">
			<c:forEach var="table" items="${REPORT_TABLES}">
				<input type="hidden" name="reportTable" value="${table}">
			</c:forEach>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	<h3>${STEP_NO}.Select the table to be joined to ${TABLE_NAME} table</h3>
	<select id="tables" name="joinTable">
		<c:forEach var="table" items="${REPORT_TABLES}">
			<option value="${table}">${table}</option>
		</c:forEach>
	</select>
	<div id="join_condition_div">
		<table>
			<tr>
				<th colspan=3 align="left">Define the Join Condition</th>
			<tr>
				<td>Table 1 Field</td>
				<td> = </td>
				<td>Table 2 Field</td>
			</tr>
			<tr>
				<td><select name="mainJoinColumn" id="mainJoinColumn">
						<c:forEach var="field" items="${MAIN_TABLE_FIELDS}">
							<option value="${field}">${MAIN_TABLE}.${field}</option>
						</c:forEach>

				</select></td>
				<td>=</td>
				<td><select name="joinColumn">
						<c:forEach var="field" items="${FIELD_LIST}">
							<option value="${field}">${TABLE_NAME}.${field}</option>
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<br>
	</div>
	<div id="field_conditions" width="100%">
		<input type="hidden" name="table" value="${TABLE_NAME}">
		<table id="custom_fields" style='padding: 2;'>
			<th colspan=4 align="left">Add conditions or criteria to be
				met in the report</th>
		</table>
		<input type="button" onClick="JJTEACH.ajaxAddFieldRow()"
			value="Add Condition" /> <input type="button"
			onClick="JJTEACH.ajaxRemoveLastFieldRow()" value="Remove Condition" />
	</div>
	<div id='display_fields' width='100%'>

		<table id='display_fields_table' style='padding: 2;'>
			<th colspan=2 align="left">Add fields and the name to be
				displayed in the report</th>
			<tr>
				<td><select class="display_fields" name="display_fields">
						<c:forEach var="field" items="${FIELD_LIST}">
							<option value="${field}">${field}</option>
						</c:forEach>
				</select></td>
				<td><input type="text" name="field_aliases" value='field name'></td>
			</tr>
		</table>
		<input type="button" onClick="JJTEACH.ajaxAddDisplayField()"
			value="Add Field" /> <input type="button"
			onClick="JJTEACH.ajaxRemoveDisplayField()" value="Remove Field" />
	</div>
	<div width='100%'>
		<table>
			<tr>
				<td><input type="button" id="ajaxsubmit" value="Next"></td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript">
	$j("#tables").change(function() {
		var table = $j("select#tables").val();
		JJTEACH.ajaxLoadTableFields(table);
		return false;
	});
</script>