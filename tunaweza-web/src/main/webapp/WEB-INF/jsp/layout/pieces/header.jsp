<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<link href="${PREFIX}/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />
<link href="${PREFIX}/css/main.css" rel="stylesheet" type="text/css" />
<link href="${PREFIX}/css/table.css" rel="stylesheet" type="text/css" />
<link href="${PREFIX}/css/form.css" rel="stylesheet" type="text/css" />
<link href="${PREFIX}/css/ui.spinner.css" rel="stylesheet" type="text/css" />
<link href="${PREFIX}/css/jquery.alerts.css" rel="stylesheet" type="text/css" />
<link href="${PREFIX}/css/ui.dropdownchecklist.css" rel="stylesheet" type="text/css" />
<link href="${PREFIX}/css/jquery.validate.css" rel="stylesheet" type="text/css" />
<link href="${PREFIX}/css/simplemodal.css" rel="stylesheet" type="text/css" />



<%--<link href="${PREFIX}/css/form.css" rel="stylesheet" type="text/css" />
 <link href="${PREFIX}/css/colorcodes.css" rel="stylesheet"
	type="text/css" />
<link href="${PREFIX}/css/datePicker.css" rel="stylesheet"
	type="text/css" /> --%>


<link rel="shortcut icon" href="${PREFIX}/images/icons/favicon.ico" />
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", -1);
%>