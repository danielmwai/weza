<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<table id="list" border="0" cellspacing="0">
    <thead>
      <tr rowspan='2'>
        <th valign="top" width="20%">COMPANY NAME</th>
        <th valign="top" width="30%">DATABASE USERNAME</th>
        <th valign="top" width="30%">DATABASE NAME</th>
        <th valign="top" width="20%">FIRST LOGIN</th>
        <th valign="top" width="20%">LAST LOGIN</th>
        <th valign="top" width="45%">INSTANCE ADMIN EMAIL</th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
      </tr>
    </thead>        
    <tbody>
     <c:forEach var="instance" items="${INSTANCELIST}">
        <tr>
            <td valign="top">${instance.name}</td>
            <td valign="top">${instance.dbaseName}</td>
            <td valign="top">${instance.dbUserName}</td>
            <td valign="top">${instance.firstLogin}</td>
            <td valign="top">${instance.lastLogin}</td>
            <td valign="top">${instance.email}</td>
            <td valign="top">
            <button class="button" onClick="JJTEACH.ajaxEdit('/cloud/editcloudform',${instance.id})">Edit</button>
             
            </td>
            <c:choose>
                <c:when test="${instance.enabled}">
                     <td><button onclick="JJTEACH.cloud.cloudRevokeInstance(${instance.id});">Revoke</button></td>
                </c:when>
                <c:otherwise>
                     <td><button onclick="JJTEACH.cloud.cloudEnableInstance(${instance.id});">Revoke</button></td>
                </c:otherwise>
            </c:choose>
           
            <td><button onclick="JJTEACH.cloud.cloudDeleteInstance(${instance.id});">Delete</button></td>
        </tr>
    </c:forEach> 
    </tbody>
</table>
