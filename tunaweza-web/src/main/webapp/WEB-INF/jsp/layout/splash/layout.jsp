<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp" %>
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
	<div id="home_main">
	<tiles:insertAttribute name="top" />
	<div id="right_boxes">
		<tiles:insertAttribute name="splash" />
	</div>
	<div id="left_box">
		<div id="box_feeds">
			<div id="feed_title">Profile</div>
			<div id="feed_content">
				<table width="100%" border="0">
				  <tr>
				    <td width="49%">Name : </td>
				    <td width="51%">${JJETS_PEOPLE_NAME}</td>
				  </tr>
				  <tr>
				    <td>Email : </td>
				    <td>${JJETS_USER.email}</td>
				  </tr>
				  <tr>
				    <td>Location : </td>
				    <td>${JJETS_USER.peopleNo.location.location}</td>
				  </tr>
				  <tr>
				    <td>Role:</td>
				    <td>${myRole}</td>
				  </tr>
				  <tr>
				    <td>&nbsp;</td>
				    <td>&nbsp;</td>
				  </tr>
				  <tr>
				    <td>&nbsp;</td>
				    <td>&nbsp;</td>
				  </tr>
				</table>
			</div>
		</div>
	</div>
	</div>
	<tiles:insertAttribute name="footer" />
</body>
</html>