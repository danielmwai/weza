
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript" src="${PREFIX}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript"
	src="${PREFIX}/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript">
        var $j = jQuery.noConflict();
        var prefix="${PREFIX}";
	var prefix="${PREFIX}";
</script>
<script type="text/javascript" src="${PREFIX}/js/JJTEACH.js"></script>
<script type="text/javascript">
        $j('#companySettingBean_errors', window.parent.document).html('${Message}');
        
        <c:if test="${companyId != null}">
        $j('#contents', window.parent.document).load("${PREFIX}/companysetting/displaycompanysetting" + JJTEACH.extension);
        </c:if>
</script>