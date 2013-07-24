<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript" src="${PREFIX}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript">
	var message = '${Message}';
	if(message== "Success"){
		
		var src = "${SERVERADDRESS}${PREFIX}/js/tiny_mce/plugins/advimage/displayimage.htm?imageId=${IMAGE.id}"; 
		var handler="ts_insert_image('${SERVERADDRESS}${PREFIX}/js/tiny_mce/plugins/advimage/displayimage.htm?imageId=${IMAGE.id}', '${IMAGE.imageText}')";
	    var img="<img src=\""+src+"\" onclick= \""+handler+"\" />";
		$("#dynamic_select_panel fieldset", window.parent.document).append(img);
	}
	else if(message == '')
		alert('Upload Failed, try again');
	else
		alert('${Message}');
</script>
