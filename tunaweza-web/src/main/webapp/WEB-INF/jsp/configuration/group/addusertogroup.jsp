<%--
 	@Author James M. Mungai 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<table id="list" border="0" cellspacing="0">
	<thead>
		<tr>
			<th valign="top">
			<pre><input type="checkbox" class="checkall" checked /> Select All</pre></th>
			<th valign="top">NAME</th>
			<th valign="top">EMAIL</th>

		</tr>
	</thead>
	<tbody>

		<c:choose>
			<c:when test="${not empty NOLIST}">
				<tr>
					<td align="center">${NOLIST}</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="user" items="${GROUPUSER}">
					<tr>
						<td valign="top" align="left"><input type="checkbox" id ="chk"
							value='<c:out value="${user.id}" />' checked /></td>
						<td valign="top"><PRE>${user.firstName}  ${user.lastName}</PRE></td>
						<td valign="top">${user.email}</td>
					</tr>
				</c:forEach>
				<c:forEach var="user" items="${USERSLIST}">
					<tr>
						<td valign="top" align="left"><input type="checkbox" id ="chk"
							value='<c:out value="${user.id}" />' /></td>
						<td valign="top"><PRE>${user.firstName}  ${user.lastName}</PRE></td>
						<td valign="top">${user.email}</td>
					</tr>
				</c:forEach>
			<tr><td colspan="3" align ="left"><input type="button" id="btnClick" name="btnClick" value="Save" /></td></tr>
				
			</c:otherwise>
		</c:choose>

	</tbody>
</table>



<!-- <script type="text/javascript">
	$j("#role").change(
			function() 
            {
				var role = $j("#role :selected").val();
				JJTEACH.ajaxList(role);
				return false;
            });
</script> -->
<script type="text/javascript">
  $(function(){
    	  $('#btnClick').click(function(){
    	    var val = [];
    	    var userIds ="";
    	    $('#chk:checked').each(function(i){
    	      val[i] = $(this).val();
    	      userIds += (val[i]?',':'') + $j(this).attr('value');
    	    });
    	    return JJTEACH.ajaxADDUserToAGroup('/user/addusergroup',userIds, ${groupId}); 
    	  
    	  });
    	  
    	});
  
  $(function () { // this line makes sure this code runs on page load
		$('.checkall').click(function () {
			$(this).parents('table:eq(0)').find(':checkbox').attr('checked', this.checked);
		});
	});
    </script>