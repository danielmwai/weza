<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadowMT" id="editTopicDiv">
<fieldset style="margin: 0;"><legend>Edit Topic</legend> <iframe
	name="editTopicFrame" id="editTopicFrame" style="display: none;"></iframe>
<form:form method="post" modelAttribute="editTopicBean"
	enctype="multipart/form-data" action="${PREFIX}/topic/edittopic.htm"
	target="editTopicFrame">
	<div id="topic_errors" class="error">${MESSAGE}</div>
	<form:hidden path="id" />
	<table id="table-form-input">
		<colgroup>
			<col class="oce-first" />
		</colgroup>
		<tbody>
			<tr>
				<td width="13%" valign="top">Topic Name:</td>
				<td width="35%" valign="top"><span style="color: red;">*</span>
				<form:input path="name" cssClass="forminput" /></td>
				<td width="10%" valign="top">Level:</td>
				<td width="35%" valign="top"><span style="color: red;">*</span>
				<form:input path="level" cssClass="forminput" /></td>
				<td width="13%" valign="top">Allowed Level:</td>
				<td valign="top">
				<div id="allowedLev">${ALLOWED}</div>
				</td>
			</tr>
			<tr>
				<td width="13%" valign="top">Module:</td>
				<td width="35%" valign="top"><span style="color: red;"></span>
				<form:select path="module" cssClass="forminput"
					onchange="populateTopics(this.value)">
					<form:options items="${MODULELIST}" itemValue="id" itemLabel="name" />
				</form:select></td>
				<td width="10%" valign="top">Parent:</td>
				<td width="35%" valign="top"><span style="color: red;">*</span>
				<form:select id="parent" path="parent" cssClass="forminput">
					<form:option value="" label="Select Parent" />
					<form:option value="0" label="Module as Parent" />
					<form:options items="${PARENTLIST}" itemValue="id" itemLabel="name" />


				</form:select></td>
				<td width="13%" valign="top">Exercise:</td>
				<td valign="top"><form:checkbox path="exercise" value="true"
					onclick="JJTEACH.ajaxShowHideEvalQuestions();" /></td>
			</tr>
			<c:choose>
				<c:when test="${editTopicBean.exercise==false}">
					<tr id="evalQstnNum">
				</c:when>
				<c:otherwise>
					<tr id="evalQstnNum" style="visibility:hidden">
				</c:otherwise>
			</c:choose>

			<td><span id="sp_">Number of evaluation questions:</span></td>
			<td><form:input path="evaluationQuestionsLimit"/>
				</td>
			</tr>
			<tr>
				<td valign="top" colspan="6"><span style="color: red;">*</span>Topic
				Text: <form:textarea id="text" path="text" cssClass="forminput" />
				</td>
			</tr>
			<c:if test="${FILE_ATTACHED}">
				<tr id="fileRow">
					<td colspan=3>File Attached <a
						href="${PREFIX}/topic/downloadattachedtopicfile.htm?fileId=${FILE_ID}">(${FILE_NAME})</a><a
						onClick='JJTEACH.ajaxRemoveFile(${FILE_ID});'>-Remove File</a></td>
				</tr>
			</c:if>
			<tr>

				<td width="20%" valign="top">Attach New File:</td>
				<td width="79%" valign="top"><form:input path="attachedfile"
					cssClass="forminput" type="file" /></td>
			</tr>
			<tr>
				<td align="center" colspan="6"><input type="submit"
					value="Save" /> &nbsp; &nbsp; <input type ="button" value="Cancel"  id="return"
					onClick="listTopics(); return false;"></td>
			</tr>
		</tbody>
	</table>
</form:form></fieldset>
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
            	window.tabparent = document.getElementById('editTopicDiv').parentNode.id;
                /*$j("#editTopicBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#edittopic_errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#editTopicBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                        	doaction(data);
                                    	});
                            return false;
                        });*/
                $j('#text').tinymce({
            		script_url : prefix+'/js/tiny_mce/tiny_mce.js',
            		    theme : "advanced",
                        plugins : "pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,internallinks",

                        //theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
                        //theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,image,cleanup,help,|,link,unlink,",
                        //theme_advanced_buttons3 : "print,|,ltr,rtl,|,fullscreen,|,styleprops,|,attribs,|,visualchars,nonbreaking,template,pagebreak,|,insertdate,inserttime,preview,|,forecolor,backcolor,code,internallinks",
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