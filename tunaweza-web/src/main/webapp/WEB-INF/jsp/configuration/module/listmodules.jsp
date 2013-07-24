<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
$j(function() {
    var tab = $j("#moduletab").tabs({tabTemplate: "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-closethick'>close</span></li>"
        });
    $j( "#moduletab span.ui-icon-closethick" ).live( "click", function() {
        var index = $j( "li", tab ).index( $j( this ).parent() );
        tab.tabs( "remove", index );
    });
});
</script>
<div id="moduletab" class="tab">
	<ul>
		<li><a href="#module">Modules</a></li>
		<li><a href="#topics" id="topicTabLabel" style="display: none;">Topic</a>
		</li>
	</ul>
	<div id="module">
		<table id="list" border="0" cellspacing="0">
			<thead>
				<tr>
					<th valign="top" width="20%">NAME</th>
					<th valign="top" width="28%">DESCRIPTION</th>
					<th valign="top" width="15%">TOPICS</th>
					<th valign="top" width="17%">MODULE EVALUATION</th>
					<th valign="top" width="10%">ENABLED</th>
					<th valign="top" width="10%">EDIT</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="module" items="${MODULESLIST}">
					<tr>
						<td valign="top">${module.name}</td>
						<td valign="top">${module.description}</td>
						<td valign="top"><button
								onClick="JJTEACH.populateTable(${module.id},'${module.name}');">View
								Topics</button></td>
						<td valign="top"><button
								onClick="JJTEACH.ajaxViewModuleEvaluation(${module.id},'${module.name}');">View
								Evaluation</button></td>
						<td valign="top" align="center"><c:choose>
								<c:when test='${module.enabled == 1}'>
									<input type="checkbox" name="enabled" checked
										value="<c:out value="${module.id}" />"
										onClick="JJTEACH.ajaxDisableModule('/module/disablemodule', ${module.id})" />
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="enabled"
										value="<c:out value="${module.id}"/>"
										onClick="JJTEACH.ajaxEnableModule('/module/enablemodule', ${module.id})" />
								</c:otherwise>
							</c:choose></td>
						<td valign="top">
							<button class="button"
								onClick="JJTEACH.ajaxEditModule('/module/editmoduleform', ${module.id})">Edit</button>
						</td>
					</tr>
				</c:forEach>
				<tr class="pagin">
					<td colspan="6" align="center">
						<div class="pages" id="pageLink" align="center">
							<c:if test="${not empty TOTAL_PAGES}">
								<c:if test="${PAGE_NUMBER > 1}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxListModules(${PAGE_NUMBER-1})">
										<c:out value="<< Previous" />
									</button>
								</c:if>
								<c:if test="${PAGE_NUMBER >2}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxListModules(${PAGE_NUMBER-2})">
										<c:out value="${PAGE_NUMBER-2}" />
									</button>
								</c:if>
								<c:if test="${PAGE_NUMBER >1}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxListModules(${PAGE_NUMBER-1})">
										<c:out value="${PAGE_NUMBER-1}" />
									</button>
								</c:if>
								<c:out value="${PAGE_NUMBER}" />
								<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxListModules(${PAGE_NUMBER+1})">
										<c:out value="${PAGE_NUMBER+1}" />
									</button>
								</c:if>
								<c:if test="${PAGE_NUMBER+1 < TOTAL_PAGES}">
									<button class="pagenumb"
										onclick="JJTEACH.ajaxListModules(${PAGE_NUMBER+2})">
										<c:out value="${PAGE_NUMBER+2}" />
									</button>
								</c:if>
								<c:if test="${PAGE_NUMBER < TOTAL_PAGES}">
									<button class="next"
										onclick="JJTEACH.ajaxListModules(${PAGE_NUMBER+1})">
										<c:out value="Next >>" />
									</button>
								</c:if>
							</c:if>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="topics"></div>
</div>

<script type="text/javascript">
    $j().ready(
            function() 
            {
            	var tabcontent_height=($j('#content').height()-84);
            	$j("#module").attr('style', 'height:'+ tabcontent_height+ 'px;');
            	$j("#topic").attr('style', 'height:'+ tabcontent_height+ 'px;');

           	 $j(window).resize(function() {
              	var tabcontent_height=($j('#content').height()-84);
              	$j("#module").attr('style', 'height:'+ tabcontent_height+ 'px;');
              	$j("#topic").attr('style', 'height:'+ tabcontent_height+ 'px;');
              });
            });      
</script>