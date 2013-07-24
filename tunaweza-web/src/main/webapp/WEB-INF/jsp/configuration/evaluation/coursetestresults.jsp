<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script language="javascript">
function printresults()
{ 
  var disp_setting="toolbar=no,location=no,directories=no,menubar=no,"; 
      disp_setting+="scrollbars=no,width=650, height=600, left=100, top=25"; 
  var content_vlue = document.getElementById("results").innerHTML; 
  
  var docprint=window.open("","",disp_setting); 
   docprint.document.open(); 
   docprint.document.write('<html><head><title></title>'); 
   docprint.document.write('</head><body onLoad="self.print()"><center>');          
   docprint.document.write(content_vlue);          
   docprint.document.write('</center></body></html>'); 
   docprint.document.close(); 
   docprint.focus(); 
}
</script>
<div align="left"><input type="button" onclick="javascript:printresults()" value="print results"></div>
<div class="fieldShadow"  id="results"
	style="margin-top: 25px; 
	position: absolute; 
	left: 100px; 
	top: 100px; 
	width: 86%; 
	height: 78%; 
	overflow: auto;">
	<fieldset>
		<legend>Course Evaluation Test Results</legend>
		<span class="legend"></span>
		<table class="table-form-input">
			<tbody>
				<tr>
					<td width="39%" valign="top"><b>Course:</b></td>
					<td width="60%" valign="top">${COURSE.name}</td>
				</tr>

				<tr>
					<td width="39%" valign="top"><b>Score:</b></td>
					<td width="60%" valign="top">${StudentEvaluation.testScore}%</td>
				</tr>
				<tr>
					<td width="39%" valign="top"><b>Comments:</b></td>
					<td width="60%" valign="top"><c:if
							test="${COMMENTS=='passed' }">
							Well Done . You have passed the test. You may now continue with the next module
							</c:if> <c:if test="${COMMENTS=='failed' }">
							You have failed the test. You will have to attempt the test again.
							</c:if></td>
				</tr>


				<c:if test="${not empty IncorrectQuestions}">
					<tr>
						<td><br />
						<br />
							<h2>Questions Answered Incorrectly</h2></td>
					</tr>


					<c:forEach var="incorrectquestion" items="${IncorrectQuestions}">
						<tr>
							<td width="39%" valign="top"></br><b>Q.&nbsp;${incorrectquestion.text}</b></br></td>
							<ul>
								<td width="60%" valign="top"></br><b>A.</b>&nbsp; <c:forEach
										var="answer" items="${incorrectquestion.answers}">
										<c:if test="${answer.correct=='true' }">
											<li>${answer.text}</li>
										</c:if>
										</br>
									</c:forEach>
							</ul>
							</td>
						</tr>
					</c:forEach>
				</c:if>


				<tr>
					<td width="39%" valign="top"></td>
					<td width="60%" valign="top">
						<button onclick="JJTEACH.ajaxLoadFullPage('/module/list/by/student')">Close
							Results</button>
					</td>
				</tr>
		</table>

	</fieldset>
</div>
