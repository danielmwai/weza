<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<table id="list" border="0" cellspacing="0">
	<thead>
	  <tr rowspan='2'>
		
		<th valign="top" width="25%">PROMO CODE NAME</th>
		<th valign="top" width="45%">ASSOCIATED LICENSE </th>
		<th valign="top" width="45%">NUMBER OF EXTRA USERS</th>
		<th valign="top" width="30%"></th> 
		<th valign="top" width="10%"></th>
	  </tr>
 	</thead>		
	<tbody>
	<c:forEach var="promocodelst" items="${PROMOCODELST}">
		<tr>
			
			<td valign="top">${promocodelst.promocodename}</td>
			<c:choose>
			<c:when test="${promocodelst.inuse==true}">
			<td valign="top">${promocodelst.associatedlicense}</td>
			</c:when>
			<c:otherwise>
			<td valign="top">N/A</td>
			 </c:otherwise>
            </c:choose>
			<td valign="top">${promocodelst.extrausers}</td>
			<td valign="top">
            <button class="button" onClick="JJTEACH.ajaxEdit('/promo_code/editpromocodepage',${promocodelst.id})">Edit</button>
            </td>
            <td valign="top">
            <button class="button" onClick="JJTEACH.ajaxEdit('/promo_code/dissociatepromocodepage',${promocodelst.id},${promocodelst.dissociatedid})">Dissociate</button>
            </td>
		</tr>
	</c:forEach>
	</tbody>
</table>