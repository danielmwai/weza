<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<link href="${PREFIX}/css/forgot.css" rel="stylesheet" type="text/css" />
<link href="${PREFIX}/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />
<link href="${PREFIX}/css/table.css" rel="stylesheet" type="text/css" />
<%--<link href="${PREFIX}/css/form.css" rel="stylesheet" type="text/css" />
 <link href="${PREFIX}/css/colorcodes.css" rel="stylesheet"
	type="text/css" />
<link href="${PREFIX}/css/datePicker.css" rel="stylesheet"
	type="text/css" /> --%>


<link rel="shortcut icon" href="${PREFIX}/images/icons/favicon.ico" />
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma","no-cache");
%>