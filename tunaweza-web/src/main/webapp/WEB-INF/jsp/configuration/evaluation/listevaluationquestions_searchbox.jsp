<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<table>
    <tr>
        <td><input type="text" id="qtn_srch_txt"/></td>
        <td>
        <button onclick="JJTEACH.ajaxPaginateEvaluationQuestions(${PAGE_NUMBER}, ${TEMPLATEID});">Search</button>
        </td>
    </tr>
</table>
<div style="clear:both;"></div>