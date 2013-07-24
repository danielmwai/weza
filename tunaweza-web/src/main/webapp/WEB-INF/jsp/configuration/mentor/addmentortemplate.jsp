<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
	$j(function() {
		$j("#mentortemplatetab").tabs();
	});
</script>
<div id="mentortemplatetab">
	<ul>
		<!--li><a href="#mentorTemplate">MentorTemplate</a></li>
		<li><a href="#assignExtoMt">Assign Exercises</a></li-->
	</ul>
	<div id="mentorTemplate">
		<!-- <div class="fieldShadowMT">
<fieldset>
<legend>Add Mentor Template</legend> -->
		<form:form method="post" modelAttribute="addMentorTemplateBean"
			class="add_form">
			<div id="addmentortemplate_errors" class="error"></div>

			<div class="container template name">
				<div class="container_inner">
					<label class="title">Template Name</label>
					<p>Letters and numbers only, 5 to 15 characters</p>

					<form:textarea cols="30" rows="5" path="name" cssClass="forminput" />
				</div>
			</div>

			<div class="container description">
				<div class="container_inner">
					<label class="title">Template Description</label>
					<p>Letters and numbers only, 5 to 15 characters</p>

					<form:input path="description" id="description"
						cssClass="forminput" />
				</div>
			</div>

			<div class="container username">
				<div class="container_inner">
					<label class="title">Module</label>
					<p>Letters and numbers only, 5 to 15 characters</p>

					<form:select path="module" cssClass="forminput">
						<form:option value="" label="Select MODULE" />
						<form:options items="${MODULELIST}" itemValue="id"
							itemLabel="name" />
					</form:select>
				</div>
			</div>



			<button type="submit" class="submit_button">Add
				Template</button>
			<input type ="button" value ="Cancel"
				onClick="JJTEACH.ajaxLoad('/coursetemplate/listcoursetemplates'); return false;">
			<%-- <table id="table-form-input">
	    <colgroup>
	    	<col class="oce-first" />
	    </colgroup>
    	<tbody>        	
	        <tr>
	             <td width="20%" valign="top">MentorTemplate name:</td>
	             <td width="79%" valign="top">
	             	<span style="color:red;">*</span>
	             	<form:input path="name" cssClass="forminput" />
	             </td>
	         </tr>
	         
			 
			 <tr>
	            <td width="20%" valign="top">MentorTemplate Description:</td>
	            <td width="79%" valign="top">
	            	<span style="color:red;">*</span>
	            	<form:input path="description" id="description" cssClass="forminput" />
	            </td>
		     </tr>
		     
		      <tr>
	            <td width="20%" valign="top">Module:</td>
	            <td width="79%" valign="top">
	            	<span style="color:red;">*</span>
	            	<form:select path="module" cssClass="forminput" >
					<form:option value="" label="Select MODULE" />		
				    <form:options items="${MODULELIST}" itemValue="id"
                                        itemLabel="name" />
					</form:select>
	            </td>
		     </tr>
		   
		   <tr>
				<td width="20%">
				&nbsp;
				</td>
				<td width="70%" align="left">
				<input type="submit" value="Add"/> &nbsp; &nbsp;
				<a onClick="JJTEACH.ajaxLoad('/mentortemplate/listmentortemplates'); return false;">Cancel</a>
				</td>
			</tr>
		   
		</tbody>
	</table> --%>
		</form:form>
		<!-- </fieldset> -->
	</div>
</div>
<div id="assignExtoMt"></div>
</div>
<script type="text/javascript">
	$j().ready(
			function() {
				var tabcontent_height = ($j('#contents').height() - 65);
				//$j("#mentorTemplate").attr('style', 'height:'+ tabcontent_height+ 'px;');
				//$j("#assignExtoMt").attr('style', 'height:'+ tabcontent_height+ 'px;');
				$j("#addMentorTemplateBean").submit(
						function() {
							var account = $j(this).serializeObject();
							$j('#errors').html("Processing..........");
							$j.postJSON($j('#addMentorTemplateBean').attr(
									'action'), account, function(data) {
								doaction(data);
							});
							return false;
						});
				$j(window).resize(function() {
					var tabcontent_height = ($j('#contents').height() - 65);
					//$j("#mentorTemplate").attr('style', 'height:'+ tabcontent_height+ 'px;');
					//$j("#assignExtoMt").attr('style', 'height:'+ tabcontent_height+ 'px;');
				});
				/* $j('#description').tinymce({
					script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
					theme : "simple"
				}); */
			});

	function doaction(data) {
		if (data.u == null) {
			var message = data.m;
			$j('#addmentortemplate_errors').html(message);
		} else {
			var message = "Saved";
			var mentorTemplateId = data.u;
			$j('#addmentortemplate_errors').html(message);
			JJTEACH.ajaxTabbedTemplate(mentorTemplateId);
		}
	}
</script>

<script type="text/javascript">
	$j(function() {
		$j("#name").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
	});
</script>