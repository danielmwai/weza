<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

   <li><a href="#">JJCloud</a>
    <div>
        <ul>
            <li> <a href="#" onclick="JJTEACH.ajaxLoad('/cloud/createcloudinstance')">Create
                    Instance</a>
            </li>
            <li><a href="#" onclick="JJTEACH.ajaxLoad('/cloud/listinstances')">List
                    Instances</a>
            </li>
        </ul>
    </div>

</li>


