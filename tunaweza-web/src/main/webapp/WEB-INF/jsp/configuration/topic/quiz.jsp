<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<form:form modelAttribute="quizBean"
	action="${PREFIX}/topictext/quiz.htm">
	<c:set value="${ERRORS}" var="errors" />
	<form:hidden path="id" />
	<div id="errors" style="font-size: 12px;"><c:if
		test="${not empty ERRORS}">
    			${errors}
    		</c:if></div>
	<c:set var="answers" value="${ANSWERS}" />

	<table id="topicQuiz">
		<tbody>
			<tr>
				<form:hidden path="textin" />
				<td colSpan="2" rowSpan="2" align="left" valign="top"
					style="padding: 15px 30px 10px;"><b>${quizBean.textin}</b></td>
			</tr>
			<tr></tr>
			<tr>
				<td colSpan="2" valign="top">
				<div class="quiz"><form:checkboxes path="answer"
					items="${answers}" itemValue="id" itemLabel="text" element="div" /></div>
				</td>
			</tr>

			<tr>
				<td width="50%">&nbsp;</td>
				<td width="50%" align="center" style="padding-bottom:10px;"><input type="submit"
					value="${COMMAND}"/></td>
			</tr>
		</tbody>
	</table>
</form:form>
<script type="text/javascript">
postJSON = function(url, data, callback) {
    return jQuery.ajax({
        'type': 'POST',
        'url': url,
        'contentType': 'application/json',
        'data': JSON.stringify(data),
        'success': callback
    });
}
    $j().ready(
            function() 
            {
                $j("#quizBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#errors').html(
                                    "Please wait ..........");
                            $j.postJSON($j('#quizBean').attr('action'),
                                        account, 
                            
                            function(response) 
                                { 
                                doaction(response);
                            	 });
                            return false;
                        });
            });

    function doaction(data) 
    {
        if(data.message != null){  
        	$j('#errors').html(data.message);
        }else if(data.u != null){
         JJTEACH.ajaxShowResult(${TOPICID});  
        }else if(data.n != null){
            JJTEACH.ajaxLoadNextQuiz(${TOPICID});
        }	
        	       	
    }

    function isJsonString(str) {
        try {
            JSON.parse(str);
        } catch (e) {
            return false;
        }
        return true;
    }
</script>

<style>
.ui-dialog-titlebar-close {
	display: none;
}
</style>

