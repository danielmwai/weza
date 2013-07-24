<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript" src="${PREFIX}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript">

	var tab = $('#moduletab', window.parent.document).tabs();
	
	var url = prefix + "/question/listquestions/bytopic" + JJTEACH.extension;
	
	var data = {"topicId":${TOPIC.id}, "topicName":"${TOPIC.name}", "moduleId":${TOPIC.module.id},
			"moduleName":"${TOPIC.module.name}"};
	
	$('#topicTabLabel', window.parent.document).attr('style', 'display:inline');
	$('#topics', window.parent.document).load(url, data);
		
	$('#topicTabLabel', window.parent.document).html('Topic Questions');
	
	tab.tabs('select', (tab.tabs("length") - 1));
			
</script>