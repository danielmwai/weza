<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page session="false" %>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<tiles:insertAttribute name="meta" />
<title><tiles:insertAttribute name="title" /></title>
<tiles:insertAttribute name="header" />
<tiles:insertAttribute name="common-scripts" />
<script type="text/javascript">

</script>
<script type="text/javascript">
$j(function() {
	var info_width = ($j('#contents').width()-40);
	$j("#wrapper,#footer").attr('style', 'width: ' + $j('body').width() * 0.95 + 'px;');
	$j(".info").attr('style', 'width:'+ info_width+ 'px;');
	});
	
$j(window).resize(function() {
	var info_width = ($j('#contents').width()-40);
	$j(".info").attr('style', 'width:'+ info_width+ 'px;');	
			if ($j('body').width() >= 1000)
				$j("#wrapper,#footer").attr('style','width: ' + $j('body').width() * 0.95 + 'px;');
			else
				$j("#wrapper,#footer").attr('style', 'width: 1000px;');
		});
		
	
</script>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", -1);
%>

</head>
<body>
<!-- Redirect the user to the home page if the user is already logged in -->
<c:if test="<%= request.getRemoteUser() != null%>">
   <% response.sendRedirect("home/index.htm"); %>
</c:if>
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
		<tiles:insertAttribute name="footer" />
	</div>
	
		
	
</body>
</html>