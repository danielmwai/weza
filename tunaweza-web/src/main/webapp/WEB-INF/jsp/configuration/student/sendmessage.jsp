<%@include file="/WEB-INF/jsp/includes/includes.jsp"%>

<form:form method="post" id="sendMessageBean"
	action="${PREFIX}/user/sendmessage.htm" class="add_form"
	modelAttribute="sendMessageBean">

	<div id="assoc_savelocation_errors" class="error"></div>
	<div class="container description">
		<div class="container_inner">
			<label class="title">Message</label>
			<form:textarea path="message" id="message" cssClass="inputtextarea"
				cssStyle="clear:left; width:200px;" />
		</div>
	</div>

	<button type="submit" class="submit_button">Save</button>
	<input type="button" value="Cancel"
		onClick="JJTEACH.ajaxLoad('/location/list'); return false;">

</form:form>
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j("#sendMessageBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#assoc_savelocation_errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#sendMessageBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                        	doaction(data);
                                    	});
                            return false;
                        });
		/* $j('#message').tinymce({
			script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
			theme : "simple"
		});
            }); */

    function doaction(data) 
    {
        var message = data.u;
        $j('#assoc_savelocation_errors').html(message);
        if (message == "Saved") 
        {
        	JJTEACH.ajaxLoad('/location/list');
        }
    }    
</script>
