<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
<fieldset>
<legend>Assign Modules To Course Template </legend>
<br/>
<form method="get" id="coursTemplateBean" >
	<div id="addcoursetemplate_errors" class="error"></div>
	
	<select id="select" 
			class="forminput"
			onchange="JJTEACH.ajaxLoadCTModules('/coursetemplate/ctlistboxes',this.value)">
			<option value="" label="Select TEMPLATE" />
			<c:forEach items="${COURSETEMPLATESLIST}" var="coursetemplate">
				<option value="${coursetemplate.id}">
					${coursetemplate.name} 
				</option>
			</c:forEach>					
	</select>
</form>
<br/>
<br/>
    
<div id="ctemplateboxes">
</div>

</fieldset>
</div>
<iframe name="addcoursetemplateframe" 
		id="addcoursetemplateframe" 
		style="display: none">
</iframe>


  