<%--
 	@Author James mungai 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<title>JJTEACH - Error</title>
<link href="${PREFIX}/css/main.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${PREFIX}/images/icons/favicon.ico" />
</head>

<body id="login_body">
<div id="home_container">
	<div id="logo"></div>
	<div style="clear:both"></div>
	<div id="login">
		<div class="login-content">
			<center>
				<h2>
					Access Denied:-Mentoring disabled<br/>
					<a href="javascript:history.back();" target="_self">Return</a>
				</h2>
			</center>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/layout/pieces/footer.jsp"%>
<script type="text/javascript" src="${PREFIX}/js/shadedborder.js"></script>
<script type="text/javascript">
var logo = RUZEE.ShadedBorder.create({corner:12, shadow:24 });
//logo.render('logo');
var login = RUZEE.ShadedBorder.create({corner:12, shadow:24, edges:'blr'});
login.render('login');
</script>
</body>
</html>