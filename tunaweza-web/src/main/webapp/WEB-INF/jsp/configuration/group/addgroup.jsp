<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<form:form method="post" class="add_form" id="addGroupBean" modelAttribute="addGroupBean">
	
	<div id="addgroup_errors" class="error"></div>
	
	<div class="container country">
				<div class="container_inner">
					<label class="title">Group Name</label>
					<form:input path="name" cssClass="forminput" />
				</div>
			</div>
	<div class="container description">
				<div class="container_inner">
					<label class="title">Group Description</label>
					<p>A bit of a description of the group created</p>

					<form:textarea cols="30" rows="5" path="description" id="description" cssClass="inputtextarea" 
						/>
				</div>
			</div>
					
	<button type="submit" class="submit_button">Save</button>
			<input type ="button" value="Cancel"  onClick="JJTEACH.ajaxLoad('/group/listgroups'); return false;">	
	
	
</form:form>

<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j("#addGroupBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#addgroup_errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#addGroupBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                        	doaction(data);
                                    	});
                            return false;
                        });
		/* $j('#description').tinymce({
			script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
			theme : "simple"
		}); */
            });

    function doaction(data) 
    {
        var message = data.u;
        $j('#addgroup_errors').html(message);
        if (message == "Saved") 
        {
        	JJTEACH.ajaxLoad('/group/listgroups');
        }
    }    
</script>
<script type="text/javascript">
	$j(function() {
		$j("#name")
				.validate(
						{
							expression : "if (VAL) return true; else return false;",
							message : "Please enter the Required field",
							error_class : "errors",
							error_field_class : "errors"
						});
	});
</script>