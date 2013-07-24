<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
	$j(function() {
		var tab = $j("#usertab").tabs({tabTemplate: "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-closethick'>close</span></li>"
			});
		$j( "#usertab span.ui-icon-closethick" ).live( "click", function() {
			var index = $j( "li", tab ).index( $j( this ).parent() );
			tab.tabs( "remove", index );
		});
	});
</script>

<div class="tab" id="usertab">
	<ul>
		<li><a href="#infotab">${editUserBean.firstName} ${editUserBean.lastName}</a></li>

		<c:if test="${not empty STUDENT}">
			<li><a href="#progresstab" id="contentTitle">Student
					Progress</a></li>
			<li><a href="#coursetemplatetab" id="contentTitle">Assign
					CourseTemplate</a></li>
					
			<!-- <li><a href="#sendmessagetab" id="contentTitle">Send Message
					</a></li> -->		
		</c:if>

		<c:if test="${not empty MENTOR}">
			<li><a href="#statistics" id="contentTitle">Mentoring
					Statistics</a></li>
			<li><a href="#assigntab" id="contentTitle">Assign MT</a></li>
		</c:if>
		<c:if test="${not empty EVALUATOR}">

            <li><a href="#progresstab" id="contentTitle">Student
                    Progress</a></li>
            <li><a href="#coursetemplatetab" id="contentTitle">Assign
                    CourseTemplate</a></li>
              <li><a href="#statistics" id="contentTitle">Mentoring
                    Statistics</a></li>
              <li><a href="#assigntab" id="contentTitle">Assign MT</a></li>  
              
              <!-- <li><a href="#sendmessagetab" id="contentTitle">Send Message
					</a></li> -->	    
        </c:if>
	</ul>
	<div id="infotab">
		<div class="fieldShadow">
			<fieldset>
				<legend>User Info</legend>
				<c:set var="user" value="${editUserBean }" />
				<table id="table-form-input">
					<colgroup>
						<col class="oce-first" />
					</colgroup>
					<tbody class="information">
						<tr>
							<td width="20%" valign="top">E-mail:</td>
							<td width="79%" valign="top"><c:out value="${user.email}"></c:out>
						</tr>
						<tr>
							<td width="20%" valign="top">First Name:</td>
							<td width="79%" valign="top"><c:out
									value="${user.firstName}"></c:out></td>
						</tr>
						<tr>
							<td width="20%" valign="top">Last Name:</td>
							<td width="79%" valign="top"><c:out value="${user.lastName}"></c:out></td>
						</tr>
						<%-- <tr>
							<td width="20%" valign="top">Location:</td>
							<td width="79%" valign="top"><c:out value="${user.location}"></c:out>
							</td>
						</tr> --%>
						<tr>
							<td width="20%" valign="top">Role:</td>
							<td width="79%" valign="top"><c:out value="${user.role}"></c:out>
							</td>
						</tr>
						<tr>
							<td width="20%">&nbsp;</td>
							<td width="70%" align="left">
								<button
									onClick="JJTEACH.ajaxUserEdit('/user/edituserform', ${user.id})">Edit</button>
							</td>
						</tr>
						<tr>
							<td><b>Download user details</b></td>
							<td>|<a
								href="${PREFIX}/report/userinforeport.htm?reportType=pdf&userId=${user.id}"><img
									src="${PREFIX}/images/icons/docs/pdf.png" />PDF</a>&nbsp;|<a
								href="${PREFIX}/report/userinforeport.htm?reportType=xls&userId=${user.id}"><img
									src="${PREFIX}/images/icons/docs/xls.png" />EXCEL</a>&nbsp;|<a
								href="${PREFIX}/report/userinforeport.htm?reportType=csv&userId=${user.id}"><img
									src="${PREFIX}/images/icons/docs/csv.png" />CSV</a></td>
						</tr>
					</tbody>
				</table>
			</fieldset>

		</div>
	</div>

	<c:if test="${not empty STUDENT}">
		<div id="progresstab"></div>
		<div id="coursetemplatetab"></div>
<!-- 		<div id="sendmessagetab"></div>
 -->		

		<script type="text/javascript">
    $j().ready(function() {
        JJTEACH.ajaxLoadStudentInfo(${USERID});
    	JJTEACH.ajaxLoadStudentCT('/coursetemplate/studentcoursetemplateform',${USERID});
    	JJTEACH.ajaxLoadStudentCT('/coursetemplate/studentcoursetemplateform',${USERID});

        });
</script>
	</c:if>
	<c:if test="${not empty MENTOR}">
		<div id="statistics"></div>
		<div id="assigntab"></div>
		<script type="text/javascript">
    $j().ready(function() {
    	JJTEACH.ajaxLoadMentorMt(${USERID});
    	JJTEACH.ajaxLoadStatistics('/mentoring/mentorstatistics',${USERID});
             });
    
</script>
</c:if>
<c:if test="${not empty EVALUATOR}">
        <div id="progresstab"></div>
        <div id="coursetemplatetab"></div>
        <div id="statistics"></div>
        <div id="assigntab"></div>
<!--         <div id="sendmessagetab"></div>
 -->        
        <script type="text/javascript">
    $j().ready(function() {
        JJTEACH.ajaxLoadStudentInfo(${USERID});
        JJTEACH.ajaxLoadStudentCT('/coursetemplate/studentcoursetemplateform',${USERID});
        JJTEACH.ajaxLoadMentorMt(${USERID});
        JJTEACH.ajaxLoadStatistics('/mentoring/mentorstatistics',${USERID});
/*     	JJTEACH.ajaxLoadSendStudentMsg('/user/sendmessage',${USERID});
 */
        });
</script>
	</c:if>


</div>


