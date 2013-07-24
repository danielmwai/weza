 <%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
 <script type="text/javascript">
	$j("#assignMentorMentorTemplateBean").submit(
			function() {
				$j('#toSelectBox_m option').each(function(){$j(this).attr("selected","selected");});
				var account = $j(this).serializeObject();
				$j('#errors').html("Processing..........");
				$j.postJSON($j('#assignMentorMentorTemplateBean').attr(
						'action'), account, function(data) {
					doAction(data);
				});
				return false;
			});
	function doAction(data) {
		var message = data.m;
		var spanMsg=document.getElementById("mentorAssignedTempMsg");
		$j('#mentortemplate_errors').html(message);
		if (message == "Saved") {
			//spanMsg.innerHtml("Saved successfully");
			$j('#mentorAssignedTempMsg').html("Saved Mentor\'s Course Template successfully");
			$j('#mentorAssignedTempMsg').fadeIn(1000);
			//JJTEACH.ajaxLoad('/user/listusers');
		}
		$j('#mentorAssignedTempMsg').delay(3500).fadeOut(1000);
	}
</script>
  <!-- <span id="mentorAssignedTempMsg"></span> -->
<div class="fieldShadowMT">
<div><h2 id="mentorAssignedTempMsg"></h2></div>
<fieldset>
<legend>Assign MT</legend>
<!--<div id="mentortemplate_errors" class="error"></div>-->
 <form:form method="post" 
 			modelAttribute="assignMentorMentorTemplateBean" 
 			target="assignmtframe">
 			
	<form:hidden path="id" value="${MENTORID}"/>					
	<form:hidden path="mentorTemplateList" id="weqt"/>

<iframe name="assignmtframe" id="aassignmtframe" style="display: none"></iframe>
<!-- Container -->
<div id="multiselectcontainer" style="width:670px;">
    <!-- From Container -->
    <div class="from_container">
     <span class="title"> Unassigned MentorTemplates </span><br />	
      <select id="fromSelectBox_m" multiple="multiple">
         <c:forEach var="mentortemplate" items="${ALLMENTORTEMPLATELIST}" varStatus="status">
            	<option value="${mentortemplate.id}"> ${mentortemplate.name}</option>
        </c:forEach>
      </select><br />
      <input type="image" src="${PREFIX}/images/multiselect/selectall.jpg" class="selectall" onclick="return selectAll('fromSelectBox_m')" />
      <input type="image" src="${PREFIX}/images/multiselect/deselectall.jpg" class="deselectall" onclick="return clearAll('fromSelectBox_m')" />
    </div>
    <!-- From Container [Close] -->
    <!-- Buttons Container -->
    <div class="buttons_container leftright">
    	<br/>
        <input type="image" src="${PREFIX}/images/multiselect/moveright.jpg" id="moveright_m" /><br />
        <input type="image" src="${PREFIX}/images/multiselect/moveleft.jpg" id="moveleft_m" /><br />
    </div>
    <!-- Buttons Container [Close] -->
    <!-- To Container -->
    <div class="to_container">
    	<span class="title"> Assigned MentorTemplates</span> <br />	
	  <select id="toSelectBox_m" multiple="multiple" name="mentorTemplateList" >
	   <c:forEach var="mentortemplate1" items="${MENTORTEMPLATELIST}" varStatus="status">
            	<option value="${mentortemplate1.id}"> ${mentortemplate1.name}</option>
        </c:forEach>
	  </select><br />
      <input type="image" src="${PREFIX}/images/multiselect/selectall.jpg" class="selectall" onclick="return selectAll('toSelectBox_m')" />
      <input type="image" src="${PREFIX}/images/multiselect/deselectall.jpg" class="deselectall" onclick="return clearAll('toSelectBox_m')" />
    </div>
    <!-- Buttons Container -->
    <div class="buttons_container topbottom">
    	<br/>
    	<input type="image" src="${PREFIX}/images/multiselect/topmost.jpg" id="topmost_m" /><br />
        <input type="image" src="${PREFIX}/images/multiselect/moveup.jpg" id="moveup_m" /><br />
        <input type="image" src="${PREFIX}/images/multiselect/movedown.jpg" id="movedown_m" /><br />
        <input type="image" src="${PREFIX}/images/multiselect/bottommost.jpg" id="bottommost_m" /><br />
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
	$j('#moveright_m').click(function() { 
		//If none of the items are selected, inform the user using an alert
		if(!isSelected("#fromSelectBox_m")){return false;} 
		//If atleast one of the item is selected, initially the selected option would be 'removed' and then it is appended to 'toSelectBox_m' (select box)
		$j('#fromSelectBox_m option:selected').remove().appendTo('#toSelectBox_m'); 
		return false;
	});
	
	//Moving selected item(s) to left select box provided
	$j('#moveleft_m').click(function() {
		//If no items are present in 'toSelectBox_m' (or) if none of the items are selected inform the user using an alert
		if(!noOptions("#toSelectBox_m") || !isSelected("#toSelectBox_m")){return false;} 
		//If atleast one of the item is selected, initially the selected option would be 'removed' and then it is appended to 'fromSelectBox_m' (select box)
		$j('#toSelectBox_m option:selected').remove().appendTo('#fromSelectBox_m');
		return false;
	});
	
	//Moving selected item(s) to upwards
	$j('#moveup_m').click(function(){
		//If no items are present in 'toSelectBox_m' (or) if none of the items are selected inform the user using an alert
		if(!noOptions("#toSelectBox_m") || !isSelected("#toSelectBox_m")){return false;}
		//If atleast one of the item is selected, through loop - the selected option would be moved upwards
		$j('#toSelectBox_m option:selected').each(function(){$j(this).insertBefore($j(this).prev());});
		return false;
	});
	
	//Moving selected item(s) to upwards
	$j('#movedown_m').click(function(){
		//If no items are present in 'toSelectBox_m' (or) if none of the items are selected inform the user using an alert
		if(!noOptions("#toSelectBox_m") || !isSelected("#toSelectBox_m")){return false;}
		//If atleast one of the item is selected, through loop - the selected option would be moved downwards
		var eleValue = $j('#toSelectBox_m option:selected:last').next();
		$j("#toSelectBox_m option:selected").each(function(){
			$j(this).insertAfter(eleValue);
			eleValue = $j(eleValue).next();
		});
		return false;
	});
	
	//Moving selected item(s) to topmost
	$j('#topmost_m').click(function(){
		//If no items are present in 'toSelectBox_m' (or) if none of the items are selected inform the user using an alert
		if(!noOptions("#toSelectBox_m") || !isSelected("#toSelectBox_m")){return false;}
		//If the selected item(s) is not the first item (option) index then move that item to the first position
		if($j('#toSelectBox_m option:selected').attr('value') != $j('#toSelectBox_m option:first').attr('value')){
			$j('#toSelectBox_m option:selected').insertBefore($j('#toSelectBox_m option:first'));
		}
		return false;
	});
	
	$j('#bottommost_m').click(function(){
		//If no items are present in 'toSelectBox_m' (or) if none of the items are selected inform the user using an alert	
		if(!noOptions("#toSelectBox_m") || !isSelected("#toSelectBox_m")){return false;}
		//If the selected item(s) index is not the last item (option) index then move that item to the last position
		if($j('#toSelectBox_m option:selected').attr('value') != $j('#toSelectBox_m option:last').attr('value')){
			$j('#toSelectBox_m option:selected').insertAfter($j('#toSelectBox_m option:last'));
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
    
    





 
