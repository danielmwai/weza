 <%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
 <script type="text/javascript">
	$j("#assignMentorTemplateExercisesBean").submit(
			function() {
				$j('#toSelectBox option').each(function(){$j(this).attr("selected","selected");});
				var account = $j(this).serializeObject();
				$j('#errors').html("Processing..........");
				$j.postJSON($j('#assignMentorTemplateExercisesBean').attr(
						'action'), account, function(data) {
					doaction(data);
				});
				return false;
			});

	function doaction(data) {
		var message = data.m;
		$j('#<div id="mentortemplate_errors" class="error"></div>').html(message);
		if (message == "Saved") {
			JJTEACH.ajaxLoad('/mentor/listmentortemplates');
		}
	} 
</script>
<div class="fieldShadowMT">
<fieldset>
<legend>Assign Exercises</legend>

 <form:form method="post" 
 			modelAttribute="assignMentorTemplateExercisesBean" 
 			target="assignmtframe">
 			
	<form:hidden path="id" value="${TEMPLATEID}"/>					
	<form:hidden path="topicList" id="weqt"/>

<iframe name="assignmtframe" id="aassignmtframe" style="display: none"></iframe>
<!-- Container -->
<div id="multiselectcontainer">
    <!-- From Container -->
    <div class="from_container">
     <span class="title"> Unassigned Exercises </span><br />	
      <select id="fromSelectBox" multiple="multiple">
         <c:forEach var="exercise" items="${ALLTOPICLIST}" varStatus="status">
            	<option value="${exercise.id}"> ${exercise.name}</option>
        </c:forEach>
      </select><br />
      <input type="image" src="${PREFIX}/images/multiselect/selectall.jpg" class="selectall" onclick="return selectAll('fromSelectBox')" />
      <input type="image" src="${PREFIX}/images/multiselect/deselectall.jpg" class="deselectall" onclick="return clearAll('fromSelectBox')" />
    </div>
    <!-- From Container [Close] -->
    <!-- Buttons Container -->
    <div class="buttons_container leftright">
    	<br/>
        <input type="image" src="${PREFIX}/images/multiselect/moveright.jpg" id="moveright" /><br />
        <input type="image" src="${PREFIX}/images/multiselect/moveleft.jpg" id="moveleft" /><br />
    </div>
    <!-- Buttons Container [Close] -->
    <!-- To Container -->
    <div class="to_container">
    	<span class="title"> Assigned Exercises</span> <br />	
	  <select id="toSelectBox" multiple="multiple" name="topicList" >
	   <c:forEach var="topic" items="${TOPICLIST}" varStatus="status">
            	<option value="${topic.id}"> ${topic.name}</option>
        </c:forEach>
	  </select><br />
      <input type="image" src="${PREFIX}/images/multiselect/selectall.jpg" class="selectall" onclick="return selectAll('toSelectBox')" />
      <input type="image" src="${PREFIX}/images/multiselect/deselectall.jpg" class="deselectall" onclick="return clearAll('toSelectBox')" />
    </div>
    <!-- Buttons Container -->
    <div class="buttons_container topbottom">
    	<br/>
    	<input type="image" src="${PREFIX}/images/multiselect/topmost.jpg" id="topmost" /><br />
        <input type="image" src="${PREFIX}/images/multiselect/moveup.jpg" id="moveup" /><br />
        <input type="image" src="${PREFIX}/images/multiselect/movedown.jpg" id="movedown" /><br />
        <input type="image" src="${PREFIX}/images/multiselect/bottommost.jpg" id="bottommost" /><br />
    </div>
    <!-- Buttons Container [Close] -->
    <!-- To Container [Close] -->
    <div style="clear:both"></div>
    <div class="savecontainer"><input type="submit" id="submitform" value="Save" /></div>
</div>
</form:form>    
</fieldset>
</div>
<!-- Container [Close] -->  
<script type="text/javascript">
$j(function() {
	//Moving selected item(s) to right select box provided
	$j('#moveright').click(function() { 
		//If none of the items are selected, inform the user using an alert
		if(!isSelected("#fromSelectBox")){return false;} 
		//If atleast one of the item is selected, initially the selected option would be 'removed' and then it is appended to 'toSelectBox' (select box)
		$j('#fromSelectBox option:selected').remove().appendTo('#toSelectBox'); 
		return false;
	});
	
	//Moving selected item(s) to left select box provided
	$j('#moveleft').click(function() {
		//If no items are present in 'toSelectBox' (or) if none of the items are selected inform the user using an alert
		if(!noOptions("#toSelectBox") || !isSelected("#toSelectBox")){return false;} 
		//If atleast one of the item is selected, initially the selected option would be 'removed' and then it is appended to 'fromSelectBox' (select box)
		$j('#toSelectBox option:selected').remove().appendTo('#fromSelectBox');
		return false;
	});
	
	//Moving selected item(s) to upwards
	$j('#moveup').click(function(){
		//If no items are present in 'toSelectBox' (or) if none of the items are selected inform the user using an alert
		if(!noOptions("#toSelectBox") || !isSelected("#toSelectBox")){return false;}
		//If atleast one of the item is selected, through loop - the selected option would be moved upwards
		$j('#toSelectBox option:selected').each(function(){$j(this).insertBefore($j(this).prev());});
		return false;
	});
	
	//Moving selected item(s) to upwards
	$j('#movedown').click(function(){
		//If no items are present in 'toSelectBox' (or) if none of the items are selected inform the user using an alert
		if(!noOptions("#toSelectBox") || !isSelected("#toSelectBox")){return false;}
		//If atleast one of the item is selected, through loop - the selected option would be moved downwards
		var eleValue = $j('#toSelectBox option:selected:last').next();
		$j("#toSelectBox option:selected").each(function(){
			$j(this).insertAfter(eleValue);
			eleValue = $j(eleValue).next();
		});
		return false;
	});
	
	//Moving selected item(s) to topmost
	$j('#topmost').click(function(){
		//If no items are present in 'toSelectBox' (or) if none of the items are selected inform the user using an alert
		if(!noOptions("#toSelectBox") || !isSelected("#toSelectBox")){return false;}
		//If the selected item(s) is not the first item (option) index then move that item to the first position
		if($j('#toSelectBox option:selected').attr('value') != $j('#toSelectBox option:first').attr('value')){
			$j('#toSelectBox option:selected').insertBefore($j('#toSelectBox option:first'));
		}
		return false;
	});
	
	$j('#bottommost').click(function(){
		//If no items are present in 'toSelectBox' (or) if none of the items are selected inform the user using an alert	
		if(!noOptions("#toSelectBox") || !isSelected("#toSelectBox")){return false;}
		//If the selected item(s) index is not the last item (option) index then move that item to the last position
		if($j('#toSelectBox option:selected').attr('value') != $j('#toSelectBox option:last').attr('value')){
			$j('#toSelectBox option:selected').insertAfter($j('#toSelectBox option:last'));
		}
		return false;
	});
});

//Below function is to validate the select box, if none of the item(s) is selected then it alerts saying 'Please select atleast one option' if user selects an item then it returns true
function isSelected(thisObj){
	if (!$j(thisObj+" option:selected").length){
		alert("Please select atleast one option");
		return 0;
	}
	return 1;
}

//Below function is to validate the select box, if none of the item(s) where present in the select box provided then it alerts saying 'There are no options to select/move' if select box has more than one item it returns true
function noOptions(thisObj){
	if(!$j(thisObj+" option").length){
		alert("There are no options to select/move");
		return 0;
	}
	return 1;
}

//Below function is to de-select all items if any of the item(s) are selected
function clearAll(thisObj){
	$j('#'+thisObj).each(function(){$j(this).find('option:selected').removeAttr("selected");});
	return false;
}//function close

//Below function is to select all items
function selectAll(thisObj){
	if(!noOptions("#"+thisObj)){return false;}
	$j('#'+thisObj+' option').each(function(){$j(this).attr("selected","selected");});
	return false;
}//function close

</script>
    
    





 
