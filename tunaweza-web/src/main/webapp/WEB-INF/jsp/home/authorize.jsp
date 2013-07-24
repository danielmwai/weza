<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<title>JJTEACH - Login</title>
<link href="${PREFIX}/css/main.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${PREFIX}/images/icons/favicon.ico" />
</head>

<body id="login_body">
<div id="home_container">
	<div id="logo"></div>
	<div style="clear:both"></div>
	<div id="login">
		<div class="login-content">
			<form:form method="post" modelAttribute="authorizeBean">
				<c:if test="${not empty ERRORS}">
					<div id="login_errors">
						<form:errors path="*" />
					</div>
				</c:if> 
				<c:if test="${not empty USER_ERRORS}">
					<div id="login_errors">
						<c:out value="${USER_ERRORS}" />
					</div>
				</c:if> 
				<table width="100%" border="0" align="center">
				 <tr>
					<td colspan="2" valign="top"><div align="center"><span class="login_label">LOGIN</span></div></td>
				  </tr>
				  <tr>
					<td width="28%" valign="top">Email:</td>
					<td width="72%" valign="top"><form:input path="email" cssClass="forminput"/></td>
				  </tr>
				  <tr>
					<td valign="top">Password:</td>
					<td valign="top"><form:password path="password" cssClass="forminput"/></td>
				  </tr>
				  <tr>
					<td valign="top">&nbsp;</td>
					<td valign="top"><button class="button">LOGIN</button></td>
				  </tr>
			  </table> 
			 </form:form>
		</div>
	</div>
</div>
<br/><br/><br/><br/>
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