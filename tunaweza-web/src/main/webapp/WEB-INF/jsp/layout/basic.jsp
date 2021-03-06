<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<tiles:insertAttribute name="meta" />
	<title><tiles:insertAttribute name="title" /></title>
	<tiles:insertAttribute name="header" />
</head>
<body>
	<script type="text/javascript">
		var prefix = "${PREFIX}";
	</script>
	<tiles:insertAttribute name="common-scripts" />
	<div id="home">
		<div id="top-nav">
			<tiles:insertAttribute name="top-nav" />
			<tiles:insertAttribute name="menu" />
		</div>
		<div style="clear:both"></div>
		<div id="home-right">
			<div id="home-right-inner">
				<tiles:insertAttribute name="content" />
			</div>
		</div>
	</div>
	
	<tiles:insertAttribute name="scripts" />
	<tiles:insertAttribute name="sort-scripts" />
	<tiles:insertAttribute name="footer" />
</body>
</html>