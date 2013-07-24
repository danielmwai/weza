<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<span>
<center>
	<img id ="img" src="${PREFIX}/imagelogo.htm" title="jjteach" height="120" width="300"/>
</center>
</span>
<br />
<div style="clear:both"></div>

<div id="contents">

<form:form method="post" 
           action="${PREFIX}/user/resendpass.htm" 
		   modelAttribute="forgotPasswordBean" >
		   <c:set value="${ERRORS}" var="errors"/>
    	<div id="assoc_saverole_errors">
    		<c:if test="${not empty ERRORS}">
    			${errors}
    		</c:if>
    	</div>
    	<table id="table-form-input">
			<colgroup>
	    		<col class="oce-first" />
		    </colgroup>	
			<tbody>
				<tr>
					<td width="20%" valign="top">Email:</td>
					<td width="79%" valign="top">
					<form:input path="email" cssClass="forminput" width="200" /></td>
				</tr>
				<tr>
					<td width="20%">
						&nbsp;
					</td>
					<td width="70%" align="left">
						<button class="button">Send</button>
					</td>
				</tr>
			</tbody>
		</table>
</form:form>
<script type="text/javascript">
$j(document).ready(function(){
	 
	$j('#img').error(function() {
		 
		$j("#img").attr("src","${PREFIX}/images/common/logo_placeholder.gif");
		
		});

});
</script>
</div>
