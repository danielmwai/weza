<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<!-- ul id="c-panel"-->



<li style="background-color:${MENU_COLOR}"><a
	href="${PREFIX}/home/index.htm" style="color:${FONT_COLOR}">Home
		${JJTEACH_PEOPLE_NAME}</a></li>


<c:if test="${JJTEACH_PEOPLE_NAME != 'root'}">
	<!-- <li><a href="#">Forum</a></li> -->
	<c:if test="${ROLE == 'ROLE_STUDENT'}">
		<li><a
			onClick="JJTEACH.ajaxLoad('/module/list/by/student'); return false;">Modules</a></li>
	</c:if>
	<c:if test="${ROLE == 'ROLE_MENTOR'}">
		<li style="background-color:${MENU_COLOR}"><a
			onClick="JJTEACH.ajaxLoad('/module/list/by/mentor'); return false;"
			style="color:${FONT_COLOR}">Modules</a></li>
	</c:if>
	<c:if test="${ROLE == 'ROLE_STUDENT'}">
		<li><a
			onClick="JJTEACH.ajaxLoad('/user/evaluation_stats'); return false;">Eval
				Stats</a></li>
	</c:if>
	<!-- <li><a onClick="JJTEACH.ajaxLoad('/user/profile'); return false;">My
				Profile</a></li> -->
</c:if>
<c:if test="${not empty EVAL_TESTING}">

	<c:if test="${EVAL_TESTING == 'ADMIN'}">
		<li><a href="#" style="color:${FONT_COLOR}">Change</a>
			<div style="background-color:${MENU_COLOR}">
				<ul>
					<li class="oe_heading">Change Roles</li>

					<li><a id="eval" href="javascript:void(0)"
						style="color:${FONT_COLOR}">Admin</a></li>
					<li><a href="/jjteach/eval/mentor.htm"
						style="color:${FONT_COLOR}">Mentor</a></li>
					<li><a href="/jjteach/eval/student.htm"
						style="color:${FONT_COLOR}">Student</a></li>
				</ul>
			</div></li>
	</c:if>
	<c:if test="${EVAL_TESTING == 'STUDENT'}">
		<li><a href="#" style="color:${FONT_COLOR}">Change</a>
			<div style="background-color:${MENU_COLOR}">
				<ul>
					<li class="oe_heading">Change Roles</li>
					<li><a href="/jjteach/eval/admin.htm"
						style="color:${FONT_COLOR}">Admin</a></li>
					<li><a href="/jjteach/eval/mentor.htm"
						style="color:${FONT_COLOR}">Mentor</a></li>
					<li><a id="eval" href="javascript:void(0)"
						style="color:${FONT_COLOR}">Student</a></li>
				</ul>
			</div></li>
	</c:if>
	<c:if test="${EVAL_TESTING == 'MENTOR'}">
		<li><a href="#" style="color:${FONT_COLOR}">Change</a>
			<div style="background-color:${MENU_COLOR}">
				<ul>
					<li class="oe_heading">Change Roles</li>
					<li><a href="/jjteach/eval/admin.htm"
						style="color:${FONT_COLOR}">Admin</a></li>
					<li><a id="eval" href="javascript:void(0)"
						style="color:${FONT_COLOR}">Mentor</a></li>
					<li><a href="/jjteach/eval/student.htm"
						style="color:${FONT_COLOR}">Student</a></li>
				</ul>
			</div></li>
	</c:if>

</c:if>
<!-- div id="searchDiv">
	<table width="100%" border="0">
	  <tr>
		<td width="75%" align="right" valign="top"><input class="forminput" type="text" /></td>
		<td width="25%" valign="top"><button  class="button">SEARCH</button></td>
		<li><a id="eval" style="color:grey;" href="javascript:void(0)">Student</a></li>
	  </tr>
  </table>
</div -->
<div id="logo_home"></div>