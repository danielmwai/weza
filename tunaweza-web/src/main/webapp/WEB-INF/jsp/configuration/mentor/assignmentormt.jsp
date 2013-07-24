<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
<fieldset>
<legend>Assign Mentor Templates To Mentor</legend>
<br/>
<form method="get" >

	<div id="mentortemplate_errors" class="error"></div>
	
	<select id="select" 
			class="forminput"
			onchange="JJTEACH.ajaxLoadMentorMT('/mentor/mentorlistboxes',this.value)">
			<option value="" label="Select MENTOR" />
			<c:forEach items="${MENTORLIST}" var="mentor">
				<option value="${mentor.id}">
					${mentor.user.firstName} ${mentor.user.lastName} 
				</option>
			</c:forEach>					
	</select>
</form>
<br/>
<br/>
    
<div id="mentorboxes">
</div>

</fieldset>
</div>
<iframe name="addexercisemtframe" 
		id="addexercisemtframe" 
		style="display: none">
</iframe>


  