<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<c:if test = "${topic != null}">
	<script type="text/javascript">
		$j(function(){
					JJTEACH.loadLastAccessedTopic('${topic.id}', '${topic.name}', '${topic.module.id}', '${topic.module.name}');
				});
	</script>
</c:if>
<div id="coursecontent">
</div>



