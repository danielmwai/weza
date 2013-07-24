<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<%-- 
		<li><a href="#" style="color:${FONT_COLOR}">Roles </a>
			<div style="background-color:${MENU_COLOR}">
				<ul>
					<li class="oe_heading">Role Functions</li>
					<li><a href="#" onclick="JJTEACH.ajaxLoad('/role/addrole')" style="color:${FONT_COLOR}">Add
							Role</a>
					</li>
					<li><a href="#" onclick="JJTEACH.ajaxLoad('/role/list')" style="color:${FONT_COLOR}">List
							Roles</a>
					</li>
				</ul>

			</div>
		</li>
--%>
<li><a href="#" style="color:${FONT_COLOR}">Users </a>
	<div style="background-color:${MENU_COLOR}">
		<ul>
			<li class="oe_heading">User Functions</li>
			<li><a href="#" onclick="JJTEACH.ajaxLoad('/user/adduser')"
				style="color:${FONT_COLOR}">Add User</a></li>
			<li><a href="#" onclick="JJTEACH.ajaxLoad('/user/listusers')"
				style="color:${FONT_COLOR}">List Users</a></li>
			<li><a href="#" onclick="JJTEACH.ajaxLoad('/user/search')"
				style="color:${FONT_COLOR}">Search Users</a></li>
		</ul>
	</div></li>

<li><a href="#" style="color:${FONT_COLOR}">Modules</a>
	<div style="background-color:${MENU_COLOR}">
		<ul>
			<li class="oe_heading">Module Functions</li>
			<li><a href="#" onclick="JJTEACH.ajaxLoad('/module/addmodule')"
				style="color:${FONT_COLOR}">Add Module</a></li>
			<li><a href="#"
				onclick="JJTEACH.ajaxLoad('/module/listmodules')"
				style="color:${FONT_COLOR}">List Module</a></li>
		</ul>
	</div></li>

<li><a href="#" style="color:${FONT_COLOR}">Courses</a>
	<div style="background-color:${MENU_COLOR}">
		<ul>
			<li class="oe_heading">Course Functions</li>
			<li><a href="#"
				onclick="JJTEACH.ajaxLoad('/coursetemplate/addcoursetemplate')"
				style="color:${FONT_COLOR}">Add Template</a></li>
			<li><a href="#"
				onclick="JJTEACH.ajaxLoad('/coursetemplate/listcoursetemplates')"
				style="color:${FONT_COLOR}">List Templates</a></li>
			<li><a href="#"
				onclick="JJTEACH.ajaxLoad('/coursetemplate/assignctmodules')"
				style="color:${FONT_COLOR}">Assign Modules</a></li>
		</ul>
	</div></li>

<c:if test="${MENTORING }">
	<li><a href="#" style="color:${FONT_COLOR}">Mentoring</a>

		<div style="background-color:${MENU_COLOR}">
			<ul>
				<li class="oe_heading">Mentor Functions</li>
				<li><a href="#"
					onclick="JJTEACH.ajaxLoad('/mentor/addmentortemplate')"
					style="color:${FONT_COLOR}">Add Template</a></li>
				<li><a href="#"
					onclick="JJTEACH.ajaxLoad('/mentor/listmentortemplates')"
					style="color:${FONT_COLOR}">List Templates</a></li>
				<li><a href="#"
					onclick="JJTEACH.ajaxLoad('/mentor/assignmtexercise')"
					style="color:${FONT_COLOR}">Assign Exercises</a></li>
				<li><a href="#"
					onclick="JJTEACH.ajaxLoad('/mentor/assignmentormt')"
					style="color:${FONT_COLOR}">Assign Template</a></li>
			</ul>
		</div></li>
</c:if>

<li><a href="#" style="color:${FONT_COLOR}">Company</a>
	<div style="background-color:${MENU_COLOR}">
		<ul>
			<li class="oe_heading">Company Settings</li>
			<c:choose>
				<c:when test="${COMPANY_SETTINGS_EXIST }">
					<li><a href="#"
						onclick="JJTEACH.ajaxLoad('/companysetting/editcompanysetting')"
						style="color:${FONT_COLOR}">Edit Company Profile</a>
				</c:when>
				<c:otherwise>
					<li><a href="#"
						onclick="JJTEACH.ajaxLoad('/companysetting/postcompanysetting')"
						style="color:${FONT_COLOR}">Add Company Profile</a></li>
				</c:otherwise>
			</c:choose>
			<li><a href="#"
				onclick="JJTEACH.ajaxLoad('/company/editregister')"
				style="color:${FONT_COLOR}">Edit Company Details</a></li>
			<!-- li><a href="#"
				onclick="JJTEACH.ajaxLoad('/company/paymentplan')"
				style="color:${FONT_COLOR}">Payment Plan</a></li-->
			<!--<li onclick="JJTEACH.ajaxLoad('/mentor/listmentortemplates')"><span>List Mentor Templates</span></li>
                         <li onclick="JJTEACH.ajaxLoad('/mentor/assignmtexercise')"><span>Assign MT Exercises</span></li> -->
			<!-- <li onclick="JJTEACH.ajaxLoad('/mentor/assignmentormt')"><span>Assign Mentor MT</span></li> -->
		</ul>
	</div></li>


<%-- <li><a href="#" style="color:${FONT_COLOR}">Locations</a>
	<div style="background-color:${MENU_COLOR}">
		<ul>
			<li class="oe_heading">Location Functions</li>
			<li><a href="#"
				onclick="JJTEACH.ajaxLoad('/location/addlocation')"
				style="color:${FONT_COLOR}">Add Location</a></li>
			<li><a href="#" onclick="JJTEACH.ajaxLoad('/location/list')"
				style="color:${FONT_COLOR}">List Location</a></li>
		</ul>
	</div></li> --%>

<li><a href="#" style="color:${FONT_COLOR}">Groups</a>
	<div style="background-color:${MENU_COLOR}">
		<ul>
			<li class="oe_heading">Group Functions</li>
			<li><a href="#" onclick="JJTEACH.ajaxLoad('/group/addgroup')"
				style="color:${FONT_COLOR}">Add Group</a></li>
			<li><a href="#" onclick="JJTEACH.ajaxLoad('/group/listgroups')"
				style="color:${FONT_COLOR}">List Group</a></li>
			<li><a href="#"
				onclick="JJTEACH.ajaxLoad('/group/assigngroupct')"
				style="color:${FONT_COLOR}">Assign Course</a></li>
		</ul>
	</div></li>

<li><a href="#" style="color:${FONT_COLOR}">Reports</a>
	<div style="background-color:${MENU_COLOR}">
		<ul>
			<li class="oe_heading">Reports</li>
			<li><a href="#"
				onclick="JJTEACH.ajaxLoad('/report/report_list')"
				style="color:${FONT_COLOR}">List Reports</a></li>
			<li><a href="#"
				onclick="JJTEACH.ajaxLoad('/report/customreport')"
				style="color:${FONT_COLOR}">Custom Reports</a></li>
			<li><a href="#"
				onclick="JJTEACH.ajaxLoad('/report/sqlreport')"
				style="color:${FONT_COLOR}">Create Sql Report</a></li>
		</ul>
	</div></li>

<!-- 	<li><a href="#">Licensing</a> -->
<!-- 	<div> -->
<!-- 		<ul> -->
<!-- 			<li><a href="#" -->
<!-- 				onclick="JJTEACH.ajaxLoad('/license/addlicense')">Create -->
<!-- 					License</a></li> -->
<!-- 			<li><a href="#" onclick="JJTEACH.ajaxLoad('/license/listlicenses')">List -->
<!--                     Instances</a> -->
<!--             </li> -->
<!-- 		</ul> -->
<!-- 	</div> -->
<!-- 	</li>	 -->



<%-- <h3>
		<a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/roles.png"
			align="left" />&nbsp;&nbsp;&nbsp;<b>Roles</b> </a>
	</h3>
	<div>
		<ul>
			<li onclick="JJTEACH.ajaxLoad('/role/addrole')"><span>Add
					Role</span>
			</li>
			<li onclick="JJTEACH.ajaxLoad('/role/list')"><span>List
					Roles</span>
			</li>
		</ul>
	</div>
	<h3>
		<a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/users.png"
			align="left" />&nbsp;&nbsp;&nbsp;<b>Users</b> </a>
	</h3>
	<div>
		<ul>
			<li onclick="JJTEACH.ajaxLoad('/user/adduser')"><span>Add
					User</span>
			</li>
			<li onclick="JJTEACH.ajaxLoad('/user/listusers')"><span>List
					Users</span>
			</li>
			<li onclick="JJTEACH.ajaxLoad('/user/search')"><span>Search
					Users</span>
			</li>
		</ul>
	</div>
	<h3>
		<a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/module.png"
			align="left" />&nbsp;&nbsp;&nbsp;<b>Modules</b> </a>
	</h3>
	<div>
		<ul>
			<li onclick="JJTEACH.ajaxLoad('/module/addmodule')"><span>Add
					Module</span>
			</li>
			<li onclick="JJTEACH.ajaxLoad('/module/listmodules')"><span>List
					Modules</span>
			</li>
		</ul>
	</div>
	<h3>
		<a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/courses.png"
			align="left" />&nbsp;&nbsp;&nbsp;<b>Course Templates</b> </a>
	</h3>
	<div>
		<ul>
			<li onclick="JJTEACH.ajaxLoad('/coursetemplate/addcoursetemplate')"><span>Add
					Course Template</span>
			</li>
			<li onclick="JJTEACH.ajaxLoad('/coursetemplate/listcoursetemplates')"><span>List
					Course Templates</span>
			</li>
			<!-- <li onclick="JJTEACH.ajaxLoad('/coursetemplate/assignctmodules')"><span>Assign CT Modules</span></li> -->

		</ul>
	</div>

	<c:if test="${MENTORING }">
		<h3>
			<a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/mentors.png"
				align="left" />&nbsp;&nbsp;&nbsp;<b>Mentor Templates</b> </a>
		</h3>
		<div>
			<ul>
				<li onclick="JJTEACH.ajaxLoad('/mentor/addmentortemplate')"><span>Add
						Mentor Template</span>
				</li>
				<li onclick="JJTEACH.ajaxLoad('/mentor/listmentortemplates')"><span>List
						Mentor Templates</span>
				</li>
				<!--         <li onclick="JJTEACH.ajaxLoad('/mentor/assignmtexercise')"><span>Assign MT Exercises</span></li> -->
				<!-- <li onclick="JJTEACH.ajaxLoad('/mentor/assignmentormt')"><span>Assign Mentor MT</span></li> -->
			</ul>
		</div>
	</c:if>

	<h3>
		<a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/setup.png"
			align="left" />&nbsp;&nbsp;&nbsp;<b>Company Profile Setting</b> </a>
	</h3>
	<div>
		<ul>
			<c:choose>
				<c:when test="${COMPANY_SETTINGS_EXIST }">
					<li
						onclick="JJTEACH.ajaxLoad('/companysetting/editcompanysetting')"><span>Edit
							Company Profile</span>
					</li>
				</c:when>
				<c:otherwise>
					<li
						onclick="JJTEACH.ajaxLoad('/companysetting/postcompanysetting')"><span>Add
							Company Profile</span>
					</li>
				</c:otherwise>
			</c:choose>
			<li onclick="JJTEACH.ajaxLoad('/company/editregister')"><span>Edit
					Company Details</span>
			</li>
			<!--<li onclick="JJTEACH.ajaxLoad('/mentor/listmentortemplates')"><span>List Mentor Templates</span></li>
                         <li onclick="JJTEACH.ajaxLoad('/mentor/assignmtexercise')"><span>Assign MT Exercises</span></li> -->
			<!-- <li onclick="JJTEACH.ajaxLoad('/mentor/assignmentormt')"><span>Assign Mentor MT</span></li> -->
		</ul>
	</div>

	<h3>
		<a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/locations.png"
			align="left" />&nbsp;&nbsp;&nbsp;<b>Locations</b> </a>
	</h3>
	<div>
		<ul>
			<li onclick="JJTEACH.ajaxLoad('/location/addlocation')"><span>Add
					Location</span>
			</li>
			<li onclick="JJTEACH.ajaxLoad('/location/list')"><span>List
					Locations</span>
			</li>
		</ul>
	</div>

	<c:if test="${not empty MENTORID}">
		<h3>
			<a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/users.png" />&nbsp;&nbsp;&nbsp;<b>Solutions</b>
			</a>
		</h3>
		<div>
			<div id="solutions"></div>
		</div>
		<h3>
			<a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/roles.png" />&nbsp;&nbsp;&nbsp;<b>Modules</b>
			</a>
		</h3>
		<div>
			<div id="treeTitle"></div>
			<div id="treeDiv"></div>
		</div>

	</c:if> --%>
<!-- I am commenting out the monitor student section instead of removing it in case it is ever required. -->
<!-- <h3><a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/monitor.png"align="left"/>&nbsp;&nbsp;&nbsp;<b>Monitor Students</b></a></h3>
	<div>		
		<ul>
			<li onclick=""><span>List All Students</span></li>
			<li onclick=""><span>List Active Students</span></li>
			<li onclick=""><span>List Inactive Students</span></li>
			<li onclick=""><span>Search Student</span></li>
		</ul>
	</div> -->

<!--mentering statistics moved to user info-->
<!--
	<h3><a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/statistics.gif"align="left"/>&nbsp;&nbsp;&nbsp;<b>Mentoring Statistics</b></a></h3>
	<div>		
		<ul>
			<li onclick="JJTEACH.ajaxLoad('/mentoring/listmentors')"><span>Response Time Statistics</span></li>
			
		</ul>
	</div>
	-->
<!--<h3><a href="#" style="color:${FONT_COLOR}"><img src="${PREFIX}/images/common/mentors.png"align="left"/>&nbsp;&nbsp;&nbsp;<b>Evaluation Templates</b></a></h3>
	<div>		
		<ul>
			<li onclick="JJTEACH.ajaxLoad('/evaluation/addevaluationtemplate')"><span>Add Evaluation Template</span></li>
			<li onclick="JJTEACH.ajaxLoad('/evaluation/listevaluationtemplates')"><span>List Evaluation Templates</span></li>
			
		</ul>
	</div>
-->
<c:choose>
	<c:when test="${not empty MENTORID}">
		<script type="text/javascript">
    $j(function() {
        $j('.accordion').accordion();
       JJTEACH.ajaxLoadModules("/mentor/listmentormodules", ${MENTORID});
         setInterval(function(){
             JJTEACH.ajaxLoadModules("/mentor/listmentormodules", ${MENTORID})
             }, 20000);
    });
</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
    $j(function() {
        $j('.accordion').accordion();
    });
</script>

	</c:otherwise>
</c:choose>