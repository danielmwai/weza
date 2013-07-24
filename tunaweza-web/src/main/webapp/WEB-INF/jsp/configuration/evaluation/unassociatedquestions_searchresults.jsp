<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<form:form
    modelAttribute="associateQuestionWithTopicBean"
    action="${PREFIX}/question/associatequestionwithtopic.htm">
    <form:hidden path="topicId" />
    <table id='list' cellspacing='0' border='0'>
        <thead>
            <tr>
                <td colspan="2"><input type="submit" value="Save Changes" />&nbsp;&nbsp;
                <input type="button" value =" to Topic Questions"
                    onClick="JJTEACH.ajaxViewEvaluationQuestionsByTopic(${TOPICID}, '${TOPICNAME}', ${MODULEID});">Back
               </td>
            </tr>
            <tr>
                <th valign='top' align='center' width='100%' colspan="2">${TOPICNAME}</th>
            </tr>
        </thead>
        <c:choose>
            <c:when test="${empty NOQUESTIONS}">
                <tbody>
                    <c:forEach var="question" items="${QUESTIONLIST}">
                        <tr>
                            <td valign='top' width='95%'>${question.text}</td>
                            <td id="#chkbx${question.id}" width='5%'><form:checkbox
                                path="questionIds" value="${question.id}" /></td>
                        </tr>
                    </c:forEach>
        
                </tbody>
            </c:when>
            <c:otherwise>
                <tr>
                    <td valign='top'>${NOQUESTIONS}</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</form:form>
<div class="pages pagin" id="pageLink" align="center"
    style="width: 89%; border-left: 1px solid black; -moz-border-radius: 5px; box-shadow: 1px 1px 3px grey; -moz-box-shadow: 1px 1px 3px grey; -webkit-box-shadow: 1px 1px 3px grey; margin: -23px 45px 20px; font-size: 12px; border-left: medium ridge;"><c:if
    test="${not empty TOTAL_PAGES}">
    <c:if test="${PAGE_NUMBER > 1}">
       <button class="pagenumb" onclick="JJTEACH.ajaxPaginateUnAssociated(${MODULEID}, ${TOPICID}, '${TOPICNAME}', 1);">First</button>
        <button class="pagenumb"
            onclick="JJTEACH.ajaxPaginateUnAssociated(${MODULEID}, ${TOPICID}, '${TOPICNAME}', ${PAGE_NUMBER-1});"><c:out
            value="<< Previous" /></button>
    </c:if>
    <c:if test="${PAGE_NUMBER >2}">
        <button class="pagenumb"
            onclick="JJTEACH.ajaxPaginateUnAssociated(${MODULEID}, ${TOPICID}, '${TOPICNAME}', ${PAGE_NUMBER-2});"><c:out
            value="${PAGE_NUMBER-2}" /></button>
    </c:if>
    <c:if test="${PAGE_NUMBER >1}">
        <button class="pagenumb"
            onclick="JJTEACH.ajaxPaginateUnAssociated(${MODULEID}, ${TOPICID}, '${TOPICNAME}', ${PAGE_NUMBER-1});"><c:out
            value="${PAGE_NUMBER-1}" /></button>
    </c:if>
    <c:out value="${PAGE_NUMBER}" />
    <c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
        <button class="pagenumb"
            onclick="JJTEACH.ajaxPaginateUnAssociated(${MODULEID}, ${TOPICID}, '${TOPICNAME}', ${PAGE_NUMBER+1});"><c:out
            value="${PAGE_NUMBER+1}" /></button>
    </c:if>
    <c:if test="${PAGE_NUMBER+1 < TOTAL_PAGES}">
        <button class="pagenumb"
            onclick="JJTEACH.ajaxPaginateUnAssociated(${MODULEID}, ${TOPICID}, '${TOPICNAME}', ${PAGE_NUMBER+2});"><c:out
            value="${PAGE_NUMBER+2}" /></button>
    </c:if>
    <c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
        <button class="next"
            onclick="JJTEACH.ajaxPaginateUnAssociated(${MODULEID}, ${TOPICID}, '${TOPICNAME}', ${PAGE_NUMBER+1});"><c:out
            value="Next >>" /></button>
            <button class="pagenumb" onclick="JJTEACH.ajaxPaginateUnAssociated(${MODULEID}, ${TOPICID}, '${TOPICNAME}', ${TOTAL_PAGES});">Last</button>
    </c:if>
</c:if>