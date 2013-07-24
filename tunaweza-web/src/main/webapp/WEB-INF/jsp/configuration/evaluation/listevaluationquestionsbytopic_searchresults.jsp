<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<table id='list' cellspacing='0' border='0'>
    <thead>
    <tr><td>
        <button id='associatequestionwithtopic'
    onclick="JJTEACH.ajaxViewQuestionsNotAssociatedWithTopic(${MODULEID}, ${TOPICID}, '${TOPICNAME}');">Associate
        Question With Topic</button>&nbsp;&nbsp;
        <button id="dissociatequestionfromtopic" 
        onclick="JJTEACH.ajaxViewQuestionsToDissociateFromTopic(${MODULEID}, ${TOPICID}, '${TOPICNAME}');">
        Dissociate Question From Topic</button>&nbsp;&nbsp;
        <a onClick="JJTEACH.populateTable(${MODULEID},'${MODULENAME}');">Back to Topics</a></td></tr>
                <tr>
                    <th valign='top' align='center' width='100%'>${TOPICNAME}</th>
                </tr>
            </thead>
    <c:choose>
        <c:when test="${empty NOQUESTIONS}">            
            <tbody>
                <c:forEach var="question" items="${QUESTIONLIST}">
                    <tr>
                        <td valign='top'>${question.text}</td>
                    </tr>
                </c:forEach>
                <tr class="pagin">
                    <td colspan="2" align="center">
                    <div class="pages" id="pageLink" align="center"><c:if
                        test="${not empty TOTAL_PAGES}">
                        <c:if test="${PAGE_NUMBER > 1}">
                        <button onclick="JJTEACH.ajaxPaginateEvaluationQuestionsByTopic(${TOPICID}, '${TOPICNAME}', ${MODULEID},1)">First</button>
                            <button class="pagenumb"
                                onclick="JJTEACH.ajaxViewEvaluationQuestionsByTopic(${TOPICID}, '${TOPICNAME}', ${MODULEID}, ${PAGE_NUMBER-1})">
                                <c:out value="<< Previous" /></button>
                        </c:if>
                        <c:if test="${PAGE_NUMBER >2}">
                            <button class="pagenumb"
                                onclick="JJTEACH.ajaxViewEvaluationQuestionsByTopic(${TOPICID}, '${TOPICNAME}', ${MODULEID}, ${PAGE_NUMBER-2})"><c:out
                                value="${PAGE_NUMBER-2}" /></button>
                        </c:if>
                        <c:if test="${PAGE_NUMBER >1}">
                            <button class="pagenumb"
                                onclick="JJTEACH.ajaxViewEvaluationQuestionsByTopic(${TOPICID}, '${TOPICNAME}', ${MODULEID}, ${PAGE_NUMBER-1})"><c:out
                                value="${PAGE_NUMBER-1}" /></button>
                        </c:if>
                        <c:out value="${PAGE_NUMBER}" />
                        <c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
                            <button class="pagenumb"
                                onclick="JJTEACH.ajaxViewEvaluationQuestionsByTopic(${TOPICID}, '${TOPICNAME}', ${MODULEID}, ${PAGE_NUMBER+1})"><c:out
                                value="${PAGE_NUMBER+1}" /></button>
                        </c:if>
                        <c:if test="${PAGE_NUMBER+1 < TOTAL_PAGES}">
                            <button class="pagenumb"
                                onclick="JJTEACH.ajaxViewEvaluationQuestionsByTopic(${TOPICID}, '${TOPICNAME}', ${MODULEID}, ${PAGE_NUMBER+2})"><c:out
                                value="${PAGE_NUMBER+2}" /></button>
                        </c:if>
                        <c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
                            <button class="next"
                                onclick="JJTEACH.ajaxViewEvaluationQuestionsByTopic(${TOPICID}, '${TOPICNAME}', ${MODULEID}, ${PAGE_NUMBER+1})"><c:out
                                value="Next >>" /></button>
                                <button onclick="JJTEACH.ajaxPaginateEvaluationQuestionsByTopic(${TOPICID}, '${TOPICNAME}', ${MODULEID},${TOTAL_PAGES})">Last</button>
                        </c:if>
                    </c:if></div>
                    </td>
                </tr>
            </tbody>
        </c:when>
        <c:otherwise>
            <tr>
                <td valign='top'>${NOQUESTIONS}</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>

<script type="text/javascript">
    $j().ready(function(){
            if('${NOQUESTIONS}')
            {
                $j('#dissociatequestionfromtopic').attr('disabled', 'disabled');
            }
            }); 
</script>