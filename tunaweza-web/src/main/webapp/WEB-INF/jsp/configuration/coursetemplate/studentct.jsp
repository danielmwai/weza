<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
<fieldset>
<legend>ASSIGN STUDENT COURSE TEMPLATE</legend>



<form method="get" id="coursTemplateBean" >
	<div id="addcoursetemplate_errors" class="error"></div>
	
<select class="forminput"
					onchange="JJTEACH.ajaxLoadStudentCT('/coursetemplate/studentcoursetemplateform',this.value)">
					<option value="" label="Select TEMPLATE" />
					<c:forEach items="${STUDENTLIST}" var="student">
					
					<option value="${student.id}">${student.firstName}  ${student.lastName}</option></c:forEach>
					
				</select>
				
				
				
				
				</form>
    
    
    <div id="ctemplateboxes">
    </div>

</fieldset>
</div>
<iframe name="addcoursetemplateframe" id="addcoursetemplateframe" style="display: none"></iframe>