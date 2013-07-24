<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
function getTodaysDate(){
	var now = new Date();
	var leo = dateFormat(now, "ddd, mmm dS, yyyy, h:MM:ss TT");
	$j('#dateDiv').html(leo);
}

setInterval('getTodaysDate()', '1000');
</script>