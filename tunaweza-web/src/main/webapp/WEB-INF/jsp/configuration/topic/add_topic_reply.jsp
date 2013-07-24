<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript" src="${PREFIX}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${PREFIX}/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript">
	var $j = jQuery.noConflict();
</script>
<script type="text/javascript" src="${PREFIX}/js/JJTEACH.js"></script>
<script type="text/javascript">
	$j('#topic_errors', window.parent.document).html('${MESSAGE}');	
	<c:if test="${ERRORS == false}">
		alert('${MESSAGE}');
		$j('#topics', window.parent.document).load("${PREFIX}/topic/topiclist" + JJTEACH.extension+"?moduleId="+ ${MODULEID});
	</c:if>
</script>