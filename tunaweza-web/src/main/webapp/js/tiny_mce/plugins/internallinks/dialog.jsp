<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Add Internal JJTeach link</title>
	<script type="text/javascript" src="${PREFIX}/js/tiny_mce/tiny_mce_popup.js"></script>
	<script type="text/javascript" src="${PREFIX}/js/tiny_mce/plugins/internallinks/js/dialog.js"></script>
	<script type="text/javascript" src="${PREFIX}/js/jquery-1.6.2.min.js"></script>
	<style>
		label{
			float:left;
			width:120px;
		}
	</style>
</head>
<body>

<form onsubmit="InternallinksDialog.insert();return false;" action="#">
	<p>The following are the topics in ${MODULE} </p>
	<p><label>Selected Topic: </label>
	<select name="topic" id="topic">
	<c:forEach var="topic" items="${TOPICS_LIST}">
	 <option value="${topic.id}">${topic.name}</option>
	</c:forEach>
	</select>
	
	</p>
	<p><label>Custom Argument: </label><input id="somearg" name="somearg" type="text" class="text" /></p>
	<div class="mceActionPanel">
		<input type="button" id="insert" name="insert" value="{#insert}" onclick="InternallinksDialog.insert();" />
		<input type="button" id="cancel" name="cancel" value="{#cancel}" onclick="tinyMCEPopup.close();" />
	</div>
</form>

</body>
</html>
