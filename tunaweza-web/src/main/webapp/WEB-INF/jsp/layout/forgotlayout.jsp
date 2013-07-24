<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<tiles:insertAttribute name="meta" />
<title><tiles:insertAttribute name="title" /></title>
<tiles:insertAttribute name="header" />
<tiles:insertAttribute name="common-scripts" />
<tiles:insertAttribute name="scripts" />
<script type="text/javascript" src="${PREFIX}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript">
var $j = jQuery.noConflict();
$j(function() {$j("#wrapper,#footer").attr('style', 'width: ' + $j('body').width() * 0.95 + 'px;');});
$j(window).resize(function() {
			if ($j('body').width() >= 1000)
				$j("#wrapper,#footer").attr('style','width: ' + $j('body').width() * 0.95 + 'px;');
			else
				$j("#wrapper,#footer").attr('style', 'width: 1000px;');
		});
		
</script>
</head>
<body>
	<script type="text/javascript">
		var prefix = "${PREFIX}";
	</script>
	<div id="wrapper">
		<div style="clear: both"></div>
		<div id="container">
		<div id="content">		
			<tiles:insertAttribute name="content" />
		</div>
		</div>
	</div>
	<div id="footer">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>