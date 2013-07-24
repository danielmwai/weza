<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
	function doaction(data) {
		var message = data.m;
		$j('#addcoursetemplate_errors').html(message);
		if (message == "Saved") {
			$j('#saveAssignedTempMsg').html(
					"Saved Student Course Template successfully");
			$j('#saveAssignedTempMsg').fadeIn(1000);

		}
		$j('#saveAssignedTempMsg').delay(3500).fadeOut(1000);
	}
</script>
<%-- <c:if
	test="${EVAL_TESTING =='ADMIN'}">
	<div id="home">
	<p></p>
</div>
</c:if>
 --%>

