<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadowMT">
<fieldset><legend>Mentor Template Profile </legend> <c:set
	var="mentortemplate" value="${editMentorTemplateBean}" />
<table id="table-form-input">
	<colgroup>
		<col class="oce-first" />
	</colgroup>
	<tbody class="information">

		<tr>
			<td width="20%" valign="top">MentorTemplate name:</td>
			<td width="79%" valign="top"><c:out
				value="${mentortemplate.name}"></c:out>
		</tr>


		<tr>
			<td width="20%" valign="top">MentorTemplate Description:</td>
			<td width="79%" valign="top">${mentortemplate.description}
		</tr>
		<c:if test="${empty NOMODULE}">
			<tr>
				<td width="20%" valign="top">Module:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<c:out value="${mentortemplate.module}"></c:out></td>
			</tr>
		</c:if>

		<tr>
			<td width="20%">&nbsp;</td>
			<td width="70%" align="left">
			<button class="button"
				onClick="JJTEACH.ajaxEditMT('/mentor/editmentortemplateform', ${mentortemplate.id})">Edit</button>
			</td>
		</tr>

	</tbody>
</table>
</fieldset>
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j("#editMentorTemplateBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#editMentorTemplateBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                        	doaction(data);
                                    	});
                            return false;
                        });
            });

    function doaction(data) 
    {
        var message = data.m;
        $j('#editmentortemplate_errors').html(message);
        if (message == "Saved") 
        {
        	JJTEACH.ajaxLoad('/mentor/listmentortemplates');
        }
    }    
</script>