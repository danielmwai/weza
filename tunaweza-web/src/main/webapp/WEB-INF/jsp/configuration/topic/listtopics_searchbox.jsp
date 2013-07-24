<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<table>
	<tr>
		<td><input type="text" id="tpc_srch_txt"  value="${SEARCH_STRING}"/></td>
		<td>
		<button
			onclick="JJTEACH.ajaxTopicList(${PAGE_NUMBER}, ${MODULEID});">Search</button>
		</td>
	</tr>
</table>
<div style="clear:both;"></div>