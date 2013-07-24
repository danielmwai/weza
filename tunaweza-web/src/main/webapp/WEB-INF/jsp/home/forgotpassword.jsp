<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<form:form method="post" 
           action="${PREFIX}/resendpass.htm" 
		   modelAttribute="forgotPasswordBean">		   
		<c:set value="${ERRORS}" var="errors"/>
    	<div id="errors">
    		<c:if test="${not empty ERRORS}">
    			${errors}
    		</c:if>
    	</div>
    	<table id="forgot">
			<tbody>
				<tr>
					<td width="21%">
						<div style="float:right;">
							<label class="formlabel">
								Email:
							</label>
						</div>
					</td>
					<td width="79%" valign="top">
						<form:input path="email" value="" cssClass="forgotforminput" />
					</td>
				</tr>
				<tr>
					<td width="20%">
						&nbsp;
					</td>
					<td width="79%" align="left">
						<input type="submit" value="Send"/>
					</td>
				</tr>
			</tbody>
		</table>
</form:form>
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j("#forgotPasswordBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#errors').html(
                                    "Sending mail..........");
                            $j.postJSON($j('#forgotPasswordBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                        	doaction(data);
                                    	});
                            return false;
                        });
            });

    function doaction(data) 
    {
        var message = data.u;
        $j('#errors').html(message);
        if (message == "New password sent to your email") 
        {
        	setTimeout(function(){$j('#popupForgot').dialog('close');},2000);        	
        }
    }
</script>
