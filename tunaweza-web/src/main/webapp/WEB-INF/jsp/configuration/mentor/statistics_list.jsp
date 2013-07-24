<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div id="mentoringTab" class="tab" >
<div id="transactions">
<table id='list' cellspacing='0' border='0'>
	<thead>
		<tr>
			<th valign='top' align='center' width='20%'>MONTH</th>
			<th valign='top' align='center' width='15%'>TOTAL TRANSACTIONS</th>
			<th valign='top' align='center' width='25%'>AVERAGE RESPONSE TIME</th>
			<th valign='top' align='center' width='25%'>AVERAGE READ TIME</th>
		</tr>
	</thead>
	<tbody>
		
				<c:forEach var="data" items="${DATALIST}">
					<tr>
						<td valign='top' align='center'>${data.month}</td>
						<td valign='top' align='center'>${data.totalTransactions}</td>
						<td valign='top' align='center'>${data.averageTimeToResponse}</td>
						<td valign='top' align='center'>${data.averageTimeToRead}</td>
					</tr>
				</c:forEach>
			
	</tbody>
</table>
</div>
</div>

