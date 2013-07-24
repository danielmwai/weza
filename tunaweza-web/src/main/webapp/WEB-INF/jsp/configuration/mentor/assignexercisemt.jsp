<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
<fieldset>
<legend>Assign Exercise To Mentor Template </legend>
<br/>
<form method="get" >

	<div id="mentortemplate_errors" class="error"></div>
	
	<select id="select" 
			class="forminput"
			onchange="JJTEACH.ajaxLoadMTExercise('/mentor/mtlistboxes',this.value)">
			<option value="" label="Select TEMPLATE" />
			<c:forEach items="${MENTORTEMPLATESLIST}" var="mentortemplate">
				<option value="${mentortemplate.id}">
					${mentortemplate.name} 
				</option>
			</c:forEach>					
	</select>
</form>
<br/>
<br/>
    
<div id="mtemplateboxes">
</div>

</fieldset>
</div>
<iframe name="addexercisemtframe" 
		id="addexercisemtframe" 
		style="display: none">
</iframe>


  
