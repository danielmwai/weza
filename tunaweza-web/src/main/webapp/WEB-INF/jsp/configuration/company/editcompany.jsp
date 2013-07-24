<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<div class="fieldShadow">
<fieldset>
<legend>Edit Company Details</legend>
	<form:form id="editCompanyBean" method="post"
		modelAttribute="editCompanyBean">
		<form:hidden path="companyId" />
		<div id="register_errors" class="error"></div>
		 <table id="table-form-input">
	    <colgroup>
	    	<col class="oce-first" />
	    </colgroup>
    	<tbody> 
			<tr>
				<td>Product Name:</td>
				<td>
				<form:textarea cols="30" rows="5" path="productName" cssClass="forminput" />
				</td>
			</tr>
			<tr>
				<td>Company Name:</td>
				<td>
				<form:textarea cols="30" rows="5" path="name" cssClass="forminput" />
				</td>
			</tr>

			<tr>
				<td>Company Website:</td>
				<td><form:input path="website" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td>Tel Line 1:</td>
				<td><form:input path="firstline" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td>Tel Line 2:</td>
				<td><form:input path="secondline" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><form:input path="address" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td>Company Location:</td>
				<td><form:input path="location" cssClass="forminput" />
			</tr>
			
			<tr>
				<td>Company Description:</td>
				<td><form:textarea cols="30" rows="5" id="description" path="description" /></td>
			</tr>
			<tr>
				<td>Facebook: link<img src="${PREFIX}/images/common/facebook.png"
						align="right" width="20" height="20" /></td>
				<td><form:input path="facebook" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td>Twiter link:<img src="${PREFIX}/images/common/twitter.png"
						align="right"  width="20" height="20"/></td>
				<td><form:input path="twiter" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td>LinkedIn link: <img

						src="${PREFIX}/images/common/linkedin.png" align="right" width="20" height="20" /></td>
				<td><form:input path="linkenin" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td colspan=2><input type="submit" value="Submit changes" /></td>
			</tr>
			</tbody>
		</table>
	</form:form>
	</fieldset>
</div>

<script type="text/javascript">
	$j().ready(
			function() {
				$j("#editCompanyBean")
						.submit(
								function() {
									var account = $j(this).serializeObject();
									$j('#register_errors').html(
											"Processing..........");
									$j.postJSON($j('#editCompanyBean').attr(
											'action'), account, function(data) {
										doaction(data);
									});
									return false;
								});
				$j('#description').tinymce({
					script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
					theme : "simple"
				});
				
				/* $j('#name,#productName').tinymce({
            		script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
            		    theme : "advanced",
            		    mode : "textareas",
                        plugins : "pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,internallinks",
                        
                        theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
                        theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,image,cleanup,help,|,link,unlink",
                        theme_advanced_buttons3 : "print,|,ltr,rtl,|,fullscreen,|,styleprops,|,attribs,|,visualchars,nonbreaking,template,pagebreak,|,insertdate,inserttime,preview,|,forecolor,backcolor,code,internallinks",
                        theme_advanced_toolbar_location : "top",
                        theme_advanced_toolbar_align : "left",
                        theme_advanced_resizing : true
                 }); */
				
			});

	function doaction(data) {
		var message = data.u;
		$j('#register_errors').html(message);
		if (message == "Saved") {

			$j('#register_errors').html("Saved");
			window.location.href = prefix;
		}
	}
</script>

<script type="text/javascript">
	$j(function() {
		$j("#name").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#website").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#firstline").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#address").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#location").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
	});
</script>