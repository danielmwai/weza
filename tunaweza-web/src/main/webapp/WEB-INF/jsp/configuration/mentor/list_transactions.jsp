<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div id="mentoringTab" class="tab" >
<ul>
	<li><a href="#transactions">Posted Exercises</a><span class='ui-icon ui-icon-closethick'>close</span></li>
</ul>
<div id="transactions">
<table id='list' cellspacing='0' border='0'>
	<thead>
		<tr>
			<th valign='top' align='center' width='20%'>AUTHOR</th>
			<th valign='top' align='center' width='25%'>EXERCISE</th>
			<th valign='top' align='center' width='20%'>TRANSACTION DATE</th>
			<th valign='top' align='center' width='20%'>TRANSACTION READ DATE</th>
			<th valign='top' align='center' width='15%'>TRANSACTION TYPE</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${not empty COUNT}">
				<c:forEach var="transaction" items="${TRANSACTIONLIST}">
					<tr onclick="JJTEACH.ajaxLoadMentorTransactions('/mentoring/viewfeedback',${transaction.exerciseId},'${transaction.exerciseName}');">
						<td valign='top' align='center'>${transaction.author}</td>
						<td valign='top' align='center'>${transaction.exerciseName}</td>
						<td valign='top' align='center'>${transaction.transactionDate}</td>
						<c:choose>
						<c:when test="${not empty transaction.transactionReadDate }">
						<td valign='top' align='center'>${transaction.transactionReadDate}</td>
						</c:when>
						<c:otherwise>
						<td valign='top' align='center'>Not Read</td>
						</c:otherwise>
						</c:choose>
						<td valign='top' align='center'>${transaction.transactionType}</td>
						
					</tr>
				</c:forEach>
				
				<tr class="pagin">
					<td colspan="6" align="center">
					<div class="pages" id="pageLink" align="center"><c:if
						test="${not empty TOTAL_PAGES}">
						<c:if test="${PAGE_NUMBER > 1}">
							<button class="pagenumb"
								onclick="JJTEACH.ajaxListTransactions(${PAGE_NUMBER-1}, ${MODULEID}, ${MENTORID})"><c:out
								value="<< Previous" /></button>
						</c:if>
						<c:if test="${PAGE_NUMBER >2}">
							<button class="pagenumb"
								onclick="JJTEACH.ajaxListTransactions(${PAGE_NUMBER-2}, ${MODULEID}, ${MENTORID})"><c:out
								value="${PAGE_NUMBER-2}" /></button>
						</c:if>
						<c:if test="${PAGE_NUMBER >1}">
							<button class="pagenumb"
								onclick="JJTEACH.ajaxListTransactions(${PAGE_NUMBER-1}, ${MODULEID}, ${MENTORID})"><c:out
								value="${PAGE_NUMBER-1}" /></button>
						</c:if>
						<c:out value="${PAGE_NUMBER}" />
						<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
							<button class="pagenumb"
								onclick="JJTEACH.ajaxListTransactions(${PAGE_NUMBER+1}, ${MODULEID}, ${MENTORID})"><c:out
								value="${PAGE_NUMBER+1}" /></button>
						</c:if>
						<c:if test="${PAGE_NUMBER+1 < TOTAL_PAGES}">
							<button class="pagenumb"
								onclick="JJTEACH.ajaxListTransactions(${PAGE_NUMBER+2}, ${MODULEID}, ${MENTORID})"><c:out
								value="${PAGE_NUMBER+2}" /></button>
						</c:if>
						<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
							<button class="next"
								onclick="JJTEACH.ajaxListTransactions(${PAGE_NUMBER+1}, ${MODULEID}, ${MENTORID})"><c:out
								value="Next >>" /></button>
						</c:if>
					</c:if></div>
					</td>
				</tr>

			</c:when>
			<c:otherwise>
				<tr>
					<td valign='top'>No transactions in this module </td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
</div>
</div>
<script type="text/javascript">
	$j(function(){
		var tab= $j("#mentoringTab").tabs({
			tabTemplate: "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-closethick'>close</span></li>"
			});
		$j( "#mentoringTab span.ui-icon-closethick" ).live( "click", function() {
			var index = $j( "li", tab ).index( $j( this ).parent() );
			tab.tabs( "remove", index );
		}); 	
		
	
	});
	
</script>
