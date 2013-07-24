<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

	<li><a href="#">Users </a>
	<div>
		<ul>
			<li><a href="#" onclick="JJTEACH.ajaxLoad('/user/addclouduser')">Ad-d
					User</a>
			</li>
			<li><a href="#" onclick="JJTEACH.ajaxLoad('/user/listcloudusers')">List
					Users</a>
			</li>
			<li><a href="#" onclick="JJTEACH.ajaxLoad('/cloud/listcompanyadmins')">List
					Company Admins</a>
			</li>
		</ul>
	</div>
	</li>
   <li><a href="#">JJCloud</a>
    <div>
        <ul>
            <li> <a href="#" onclick="JJTEACH.ajaxLoad('/cloud/createcloudinstance')">Create
                    Instance</a>
            </li>
            <li><a href="#" onclick="JJTEACH.ajaxLoad('/cloud/listinstances')">List
                    Instances</a>
            </li>
            <li> <a href="#" onclick="JJTEACH.ajaxLoad('/promo_code/promocodeviews')">Create
                    Promo Code</a>
            </li>
        </ul>
    </div>
    

</li>
    <li><a href="#">License</a>
    <div>
        <ul>
        	<li> <a href="#" onclick="JJTEACH.ajaxLoad('/license/addlicense')">Create
                    License</a>
            </li>
            
            <li><a href="#" onclick="JJTEACH.ajaxLoad('/license/listlicenses')">List
                    License</a>
            </li>
            <li> <a href="#" onclick="JJTEACH.ajaxLoad('/promo_code/promocodeviews')">Create
                    Promo Code</a>
            </li>
            <li> <a href="#" onclick="JJTEACH.ajaxLoad('/promo_code/newpromocodesuccess')">List
                    Promo Codes</a>
            </li>
            
            
        </ul>
    </div>
    

</li>
    

