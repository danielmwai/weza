<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<table class="quiz" id="table-form-input" style="width: 95%;">
	<c:if test="${not empty RESULTS}">
		<thead>
			<tr>
				<th colspan="2" align="center">
				<h2>Results</h2>
				</th>
			</tr>
		</thead>
		<tbody>


			<c:forEach var="q" items="${RESULTS}">
				<tr>
					<td width="39%" valign="top">&nbsp;${q.question}</td>
					<c:choose>
						<c:when test="${q.result == 'Correct'}">
							<td width="60%" valign="top" align="center"
								style="color: green; font-size: 10px; font-style: italic; padding-left: 15px;">${q.result}</td>
						</c:when>
						<c:otherwise>
							<td width="60%" valign="top" align="center"
								style="color: red; font-size: 10px; font-style: italic; padding-left: 15px;">${q.result}</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
			<tr></tr>
			<tr></tr>
			<tr>
				<td width="50%" valign="top" align="right"><b>Score:</b></td>
				<td width="50%" valign="top" align="center">${SCORE}</td>
			</tr>
		</tbody>
	</c:if>
</table>
<style>
.ui-dialog-titlebar-close {
	display: block;
}

#table-form-input,.table-form-input {
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 12px;
	width: 90%;
	text-align: left;
}

#table-form-input th,.table-form-input th {
	font-size: 14px;
	font-weight: normal;
	padding: 12px 15px;
	color: #000000;
}

#table-form-input .information td {
	padding: 20px 0 4px 20px;
}

#table-form-input td,.table-form-input td {
	padding: 4px 0 4px 10px;
	color: #000000;
	border-top: 1px solid #e8edff;
}
</style>

