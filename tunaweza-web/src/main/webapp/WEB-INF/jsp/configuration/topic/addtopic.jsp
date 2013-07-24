<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadowMT" id="newTopicFormDiv">
<fieldset style="margin: 0;"><legend>Add Topic</legend> <iframe
	name="newTopic" id="newTopic" style="display: none;"></iframe> <form:form
	method="post" modelAttribute="addTopicBean" target="newTopic"
	enctype="multipart/form-data">
	<div id="topic_errors" class="error">${MESSAGE}</div>
	<c:set var="module" value="${MODULE}" />
	<form:hidden path="module" />
	<table id="table-form-input">
		<colgroup>
			<col class="oce-first" />
		</colgroup>
		<tbody>
			<tr>
				<td width="10%" valign="top">Topic Name:</td>
				<td width="20%" valign="top"><span style="color: red;">*</span>
				<form:input path="name" cssClass="forminput" /></td>
				<td width="10%" valign="top">Module:</td>
				<td width="20%" valign="top">${module.name}</td>
			</tr>
			<tr>

				<td width="10%" valign="top">Parent:</td>
				<td width="20%" valign="top"><span style="color: red;">*</span>
				<form:select path="parent" cssClass="forminput">
					<form:option value="" label="Select Parent" />
					<form:option value="0" label="Module as Parent" />
					<form:options items="${TOPICS}" itemValue="id" itemLabel="name" />
				</form:select></td>
				<td width="10%" valign="top">Exercise:</td>
				<td width="20%" valign="top"><form:checkbox path="exercise"
					value="true" onclick="JJTEACH.ajaxShowHideEvalQuestions();" /></td>
			</tr>
			<tr id="evalQstnNum">
				<td>Number of evaluation questions:</td>
				<td><form:input path="evaluationQuestionsLimit" /></td>

			</tr>
			<tr>
				<td valign="top" colspan="4"><span style="color: red;">*</span>
				&nbsp; Topic Text: <form:textarea id="text" path="text"
					cssClass="forminput" /></td>
			</tr>

			<tr>
				<td width="20%" valign="top">Attach File:<span
					style="color: red;">*</span></td>
				<td width="79%" valign="top"><form:input path="attachedfile"
					cssClass="forminput" type="file" /></td>
			</tr>

			<tr>
				<td align="center" colspan="4"><input type="submit"
					value="Add Topic" /> &nbsp; &nbsp; <input type ="button" value="Cancel" 
					onClick="listTopics(); return false;">
			</tr>

		</tbody>
	</table>
</form:form></fieldset>
</div>
<script type="text/javascript">
  
    $j().ready(
            function() 
            {
            	window.tabparent = document.getElementById('newTopicFormDiv').parentNode.id;
                /*$j("#addTopicBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#addtopic_errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#addTopicBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                        	doaction(data);
                                    	});
                            return false;
                        });*/
            	$j('#text').tinymce({
            		script_url : prefix+'/js/tiny_mce/tiny_mce.js',
            		//media_external_list_url : prefix+'/js/tiny_mce/plugins/media/media.jsp',
            		    theme : "advanced",
                        plugins : "pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,internallinks",

                       // theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
                       // theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,image,cleanup,help,|,link,unlink",
                       // theme_advanced_buttons3 : "print,|,ltr,rtl,|,fullscreen,|,styleprops,|,attribs,|,visualchars,nonbreaking,template,pagebreak,|,insertdate,inserttime,preview,|,forecolor,backcolor,code,internallinks",
                        theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
                        theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor,code,internallinks",
                        theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
                        theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak",
                        theme_advanced_toolbar_location : "top",
                        theme_advanced_toolbar_align : "left",
                        theme_advanced_resizing : true
                        
                 });
                $j('#evaluationQuestionsLimit').spinner({min:0,max:99});
            });

    function doaction(data) 
    {
        var message = data.u;
        $j('#topic_errors').html(message);
        if (message == "Saved") 
        {
        	//JJTEACH.ajaxViewModuleTopics(${MODULEID});
        	listTopics();
        }
    }
    function listTopics(){
    	
        $j('#' + window.tabparent).load(
                prefix + "/topic/topiclist" + JJTEACH.extension + "?moduleId="
                        + ${MODULEID});
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