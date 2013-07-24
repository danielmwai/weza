<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
	refreshTransaction = function(transaction){
			$j("div#subject").html($j("#list tr#tr"+transaction+" td.subject a").html());
			$j("div#author").html($j("#list tr#tr"+transaction+" td.author").html());
			$j("div#comment").html($j("#list tr#tr"+transaction+" span.comment").html());
			$j("div#attachment").html($j("#list tr#tr"+transaction+" span.transAttachment").html());
			$j("div#attachment a").attr("href", prefix + "/solution/downloadsolution" + JJTEACH.extension + "?transactionId=" + transaction);
			return false;
	}
	$j(function(){
		var transaction=$j("#list tr:nth-child(2)").attr("id").substr(2);
		refreshTransaction(transaction);  
	})
</script>
<div class="fieldShadow" style="margin-top:25px">
	
		<span class="legend">Read Feedback</span>
		<table class="table-form-input">
		<tbody>
					<tr>
						<td width="20%" valign="top"><b>Subject:</b></td>
						<td width="79%" valign="top">
							<div id ="subject"></div>
						</td>
					</tr>
					
					<tr>
						<td width="20%" valign="top"><b>Author:</b></td>
						<td width="79%" valign="top">
							<div id ="author"></div>
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top"><b>Comments:</b>
						</td>
						<td width="90%" valign="top">
							<div id ="comment" class="displayComments"></div>
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top">Attachment(s):</td>
						<td width="79%" valign="top">
							 <div id ="attachment" > </div>
						</td>
					</tr>
		</table>
		<table id="list" style="margin-top:0">
		<tr class="headerTransactionTable"><th>Subject</th><th>Exercise Status</th><th>Author</th><th>No. of Posts</th><th>Last Post Dates</th></tr>
		<c:forEach var="exerciseTransaction" items="${EXERCISETRANSACTIONS}">
		<tr id="tr${exerciseTransaction.id}">
		<td class="subject"><a  onClick="refreshTransaction(${exerciseTransaction.id})">${exerciseTransaction.subject}</a></td>
		<td><c:choose>
			<c:when test="${EXERCISESTATUS=='STATUS_EXERCISE_PASSED'}">Closed</c:when>
			<c:otherwise>Awaiting Submission</c:otherwise>
			</c:choose>
		</td>
		<td class="author"><c:choose>
			<c:when test="${exerciseTransaction.exerciseTransactionType.name=='StudentToMentor'}">
				${exerciseTransaction.studentExercise.student.user.firstName } ${exerciseTransaction.studentExercise.student.user.lastName}
			</c:when>
			<c:otherwise>
				${exerciseTransaction.mentor.user.firstName } ${exerciseTransaction.mentor.user.lastName}
			</c:otherwise>
			</c:choose>
		</td>
		<td>${POSTS}</td>
		<td>${exerciseTransaction.transanctionDate}
		<span style="display:none;" class="comment">${exerciseTransaction.comment}</span> 
		<span style="display:none;" class="transAttachment"><a>${exerciseTransaction.file.fileName}</a>
		</span>
		</td>
		</tr>
		</c:forEach>
		</table>
</div>
