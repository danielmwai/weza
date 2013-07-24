<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", -1);
	response.setHeader("Expires", "Tues, 01 Jan 1980 00:00:00 GMT");
	response.setHeader("Pragma", "no-cache");
%>
<script type="text/javascript" src="${PREFIX}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${PREFIX}/js/jquery.min.js"></script>

<script type="text/javascript" src="${PREFIX}/js/json.min.js"></script>
<script type="text/javascript" src="${PREFIX}/js/datepicker/date.js"></script>
<script type="text/javascript"
	src="${PREFIX}/js/datepicker/jquery.datePicker.min-2.1.2.js"></script>
<!-- <script type="text/javascript" src="${PREFIX}/js/ui.tabs.closable.js"></script>
	 <script type="text/javascript" src="${PREFIX}/js/ui.tabs.closable.min.js"></script> -->
<script type="text/javascript"
	src="${PREFIX}/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript"
	src="${PREFIX}/js/tiny_mce/jquery.tinymce.js"></script>
<script type="text/javascript" src="${PREFIX}/js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="${PREFIX}/js/jquery.jstree.js"></script>
<script type="text/javascript" src="${PREFIX}/js/jquery.MoverBoxes.js"></script>
<script type="text/javascript" src="${PREFIX}/js/ui.spinner.min.js"></script>
<script type="text/javascript">
	var $j = jQuery.noConflict();
</script>
<script type="text/javascript" src="${PREFIX}/js/prototype.js"></script>
<script type="text/javascript" src="${PREFIX}/js/dimbox.js"></script>
<script type="text/javascript" src="${PREFIX}/js/utils.js"></script>
<script type="text/javascript" src="${PREFIX}/js/JJTEACH.js"></script>
<script type="text/javascript" src="${PREFIX}/js/shadedborder.js"></script>
<script type="text/javascript" src="${PREFIX}/js/tempDateFormat.js"></script>
<script type="text/javascript"
	src="${PREFIX}/js/jquery.pstrength-min.1.2.js"></script>
<script type="text/javascript" src="${PREFIX}/js/jquery.alerts.js"></script>
<script type="text/javascript" src="${PREFIX}/js/jquery.validate.js"></script>
<script type="text/javascript"
	src="${PREFIX}/js/ui.dropdownchecklist.js"></script>
<script type="text/javascript" src="${PREFIX}/js/ajaxupload.3.6.js"></script>
<script type="text/javascript" src="${PREFIX}/js/jquery.form.js"></script>
<script type="text/javascript" src="${PREFIX}/js/jquery.simplemodal.js"></script>

<script type="text/javascript" src="${PREFIX}/js/jquery.address.js"></script>




<!--[if lt IE 9]>
<script type="text/javascript" src="${PREFIX}/js/PIE.js"></script>
<![endif]-->