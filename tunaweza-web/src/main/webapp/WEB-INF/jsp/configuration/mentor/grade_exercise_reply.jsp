<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<link href="${PREFIX}/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${PREFIX}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${PREFIX}/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript">
	var $j = jQuery.noConflict();
</script>
<script type="text/javascript" src="${PREFIX}/js/JJTEACH.js"></script>
<script type="text/javascript">
	$j('#gradeExercise_errors', window.parent.document).html('${Message}');
	<c:choose>
            <c:when test="${success == false}">
		            alert('${Message}');
            </c:when>
    <c:otherwise>
           alert('${Message}');
           $j(window.parent.document).append("<div id='contents'><div>");
           $j('#contents').load("${PREFIX}/mentor/listtransactions"+JJTEACH.extension+"?mentorId="+${mentorId}+"&moduleId="+${moduleId});
    </c:otherwise>
    </c:choose>

</script>
