<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>


<script type="text/javascript">
	$j(function() {
		var tab = $j("#resultsTab").tabs({tabTemplate: "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-closethick'>close</span></li>"
			});
		$j( "#resultsTab span.ui-icon-closethick" ).live( "click", function() {
			var index = $j( "li", tab ).index( $j( this ).parent() );
			tab.tabs( "remove", index );
		});
	});
</script>

<div align="center">
<div class="tab" id="resultsTab">
<ul>
		<li><a href="#moduleTestResultsTab" id="contentTitle">Module Test Results</a></li>
		<!-- <li><a href="#courseTestResultsTab" id="contentTitle">Course Test Results</a></li> -->

</ul>

<div id="moduleTestResultsTab"></div>
<!-- <div id="courseTestResultsTab"></div> -->

<script type="text/javascript">
    $j().ready(function() {
        JJTEACH.ajaxListModuleEvalStats("/user/module_evaluation_stats",0); 
    	/* JJTEACH.ajaxListCourseEvalStats("/user/course_evaluation_stats",0); */
        });
</script>

</div>
</div>