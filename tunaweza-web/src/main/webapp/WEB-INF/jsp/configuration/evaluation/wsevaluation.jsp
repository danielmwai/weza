<!DOCTYPE html> 
<html>
<head>
<title> Eval Test
</title>
<link type="text/css" rel="stylesheet" href="${PREFIX}/css/table.css">
<script type="text/javascript" src="${PREFIX}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${PREFIX}/js/json.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.1.0/jquery.mobile-1.1.0.min.css" />
<script src="http://code.jquery.com/mobile/1.1.0/jquery.mobile-1.1.0.min.js"></script>
<script type="text/javascript">
function start(testId, username) {
	var url = "${PREFIX}/rest/student/get/module/new.htm?testId=" + testId + "&username=" + username;
	$j('#main').hide();
	$j('#content').load(url);
	return false;
}
</script>
<script type="text/javascript">
	var $j = jQuery.noConflict();
</script>
</head>
<body>
<div id="start" data-role="page">
<header data-role="header" data-theme='b'><h1>Module Evaluation</h1>
<a  data-backbtn="true" onClick="history.go(-1);return false;" >Home</a>
</header>
<div id="mainCntent" style="padding: 20px;">
<div id="main">
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow" style="margin-top:25px">
	<fieldset>
<legend></legend>
		<span class="legend"></span>
		<table class="table-form-input">
		<tbody>
					<tr>
						<td width="20%" valign="top"><b>Module:</b></td>
						<td width="79%" valign="top">
							${EVALUATIONTEMPLATE.module.name}
						</td>
					</tr>
					
					<tr>
						<td width="20%" valign="top"><b>Duration:</b></td>
						<td width="79%" valign="top">
							${EVALUATIONTEMPLATE.duration} minutes
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top"><b>Instructions:</b>
						</td>
						<td width="90%" valign="top">
							<div>	${EVALUATIONTEMPLATE.description}</div>
						
			
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top"></td>
						<td width="79%" valign="top">
							<input type="button" data-inline="true" value="Start Test" onclick="start('${EVALUATIONTEMPLATE.id}', '${USERNAME}');">
						</td>
					</tr>
		</table>
		
		</fieldset>
</div></div>
<div id="content"></div>
</div></div>
</body>
</html>