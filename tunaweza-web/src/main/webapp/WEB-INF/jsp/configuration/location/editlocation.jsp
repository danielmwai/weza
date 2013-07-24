<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>


		<form:form method="post" action="${PREFIX}/location/editlocation.htm"
			modelAttribute="editLocationBean" class="add_form">
			
			<div id="assoc_savelocation_errors" class="error"></div>
			<form:hidden path="id" />

			<div class="container country">
				<div class="container_inner">
					<label class="title">Location Name</label>
					<p>Name of the location(country,city,place)</p>

					<form:input path="name" cssClass="forminput" />
				</div>
			</div>
			<div class="container description">
				<div class="container_inner">
					<label class="title">Location Description</label>
					<p>A bit of a description of the location created</p>

					<form:textarea path="description" id="description"
						cssClass="inputtextarea" cssStyle="clear:left; width:200px;" />
				</div>
			</div>

			<button type="submit" class="submit_button">Save</button>
			<input type ="button" value="Cancel"  onClick="JJTEACH.ajaxLoad('/location/list'); return false;">
			
		</form:form>

<script type="text/javascript">
 						    $j().ready(
					            function() 
					            {
					                $j("#editLocationBean").submit(
					                        function() 
					                        {
					                            var account = $j(this).serializeObject();
					                            $j('#errors').html(
					                                    "Processing..........");
					                            $j.postJSON($j('#editLocationBean').attr('action'),
					                                        account, 
					                                        function(data) 
					                                        {
					                                        	doaction(data);
					                                    	});
					                            return false;
					                        });
/* 
					                $j('#description').tinymce({
					        			script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
					        			theme : "simple"
					        		}); */
					            }); 

	function doaction(data) {
		var message = data.u;
		$j('#assoc_savelocation_errors').html(message);
		if (message == "Saved") {
			JJTEACH.ajaxLoad('/location/list');
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