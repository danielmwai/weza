<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<style type="text/css">
.ui-accordion .ui-accordion-header {
	height: 45px;
}
</style>

<div class="accordion">
	<h3>
		<a href="#"><img src="${PREFIX}/images/common/users.png" />&nbsp;&nbsp;&nbsp;<b>Solutions</b></a>
	</h3>
	<div>
		</br>
		<div class="solutions"></div>
		</br> </br> </br>
	</div>
	<h3>
		<a href="#"><img src="${PREFIX}/images/common/roles.png" />&nbsp;&nbsp;&nbsp;<b>Modules</b></a>
	</h3>
	<div>
		<div id="treeTitle"></div>
		<div id="treeDiv"></div>
	</div>
	<!-- I am commenting out the monitor student section instead of removing it in case it is ever required. -->
	<!-- <h3><a href="#"><img src="${PREFIX}/images/common/monitor.png"
	align="left" />&nbsp;&nbsp;&nbsp;<b>Monitor Students</b></a></h3>
<div>
<ul>
	<li onclick=""><span>List All Students</span></li>
	<li onclick=""><span>List Active Students</span></li>
	<li onclick=""><span>List Inactive Students</span></li>
	<li onclick=""><span>Search Student</span></li>
</ul>
</div> -->
</div>
<script type="text/javascript">
    $j().ready(
    		function(){ 
    			 $j('div.accordion').accordion({ 
    				    autoHeight: false 
    			 });
    			 //$j('.accordion').accordion();
    			 JJTEACH.ajaxLoadModules("/mentor/listmentormodules", ${MENTORID});
    			setInterval(function(){
    			 JJTEACH.ajaxLoadModules("/mentor/listmentormodules", ${MENTORID})
    				}, 20000);
    		});      
</script>
