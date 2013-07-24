<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript" src="${PREFIX}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${PREFIX}/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript">
	var $j = jQuery.noConflict();
</script>
<script type="text/javascript" src="${PREFIX}/js/JJTEACH.js"></script>
<script type="text/javascript">
	$j('#postsolution_errors', window.parent.document).html('${Message}');	
	<c:if test="${Topic != null}">
		alert('${Message}');
		$j("#treeDiv li#${Topic}", window.parent.document).attr('rel','STATUS_EXERCISE_SUBMITTED_BUT_AWAITING_FEEDBACK');
		$j("#prevbutton,#nextbutton",window.parent.document).css('display','block');
		$j('#coursecontent', window.parent.document).load("${PREFIX}/topictext/view" + JJTEACH.extension+"?topicId="+${Topic});
	</c:if>
</script>
