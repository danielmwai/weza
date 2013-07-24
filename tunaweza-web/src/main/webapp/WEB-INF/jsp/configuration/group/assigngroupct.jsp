<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
<fieldset>
<legend>Assign Course Template To Group</legend>
<br/>
<form method="get" id="coursTemplateBean" >
	<div id="addcoursetemplate_errors" class="error"></div>
	
	<select id="select"
			class="forminput"
			onchange="JJTEACH.ajaxLoadGroupCT('/group/groupctlistboxes',this.value)">
			<option value="0">Select Group</option>
			<c:forEach items="${GROUPLIST}" var="group">
				<option value="${group.id}">
					${group.name} 
				</option>
			</c:forEach>					
	</select>
</form>
<br/>
<br/>
    
<div id="groupctbox">
</div>

</fieldset>
</div>
<iframe name="addcoursetemplateframe" 
		id="addcoursetemplateframe" 
		style="display: none">
</iframe>


  