<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<link href="${PREFIX}/css/layout.css" rel="stylesheet" type="text/css" />
<link href="${PREFIX}/css/colorpicker.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${PREFIX}/js/jquery.js"></script>
<script type="text/javascript" src="${PREFIX}/js/colorpicker.js"></script>
<script type="text/javascript" src="${PREFIX}/js/eye.js"></script>
<script type="text/javascript" src="${PREFIX}/js/iutils.js"></script>
<script type="text/javascript" src="${PREFIX}/js/layout.js?ver=1.0.2"></script>
<div id=comanysetting>
	<!-- <div class="fieldShadow">
		<fieldset>
			<legend>Company Profile</legend>-->
			<iframe name="postcompanysetting" id="postcompanysetting"
				style="display: none;"></iframe> 


	<form:form method="post" modelAttribute="companySettingBean"
		enctype="multipart/form-data" target="postcompanysetting"
		action="${PREFIX}/companysetting/postcompanysetting.htm"
		class="add_form">
		<div id="companySettingBean_errors" class="error">
			<span><c:out value="${Message}" /> </span>
		</div>

		<div class="container">

			<div class="container_inner">
				<label class="title">Company Logo</label>

				<form:input path="logo_image" cssClass="forminput" type="file" />
			</div>
		</div>


		<div class="container">

			<div class="container_inner">
				<label class="title">Background Color</label> <span
					style="color: red;">*</span>
				<form:input path="bg_color_code" cssClass="forminput"
					id="colorpickerField1" />
			</div>
		</div>
		<div class="container">

			<div class="container_inner">
				<label class="title">Company Color</label> <span style="color: red;">*</span>
				<form:input path="header_color_code" cssClass="forminput"
					id="colorpickerField2" />
			</div>
		</div>
		
		<div class="container">

		<div class="container_inner">
			<label class="title">Menu Color</label> <span
				style="color: red;">*</span>
			<form:input path="menu_color_code" cssClass="forminput"
				id="colorpickerField1" />
		</div>
	</div>
	
	<div class="container">

		<div class="container_inner">
			<label class="title">Menu Font Color</label> <span
				style="color: red;">*</span>
			<form:input path="font_color_code" cssClass="forminput"
				id="colorpickerField1" />
		</div>
	</div>

		<div class="container username">
			
			<div class="container_inner">
			<label class="title">Navigation Links Color:</label>
				<p>Letters and numbers only, 5 to 15 characters</p>
				<form:input path="navigation_hover_color" cssClass="forminput"
					id="colorpickerField4" />
			</div>
		</div>

		<div class="container">
			<div class="container_inner">
			<label class="title">Enable Mentoring</label>
			<p>Enable/Disable</p>
				<form:checkbox path="mentoring" cssClass="forminput" id="mentoring" />
			</div>
		</div>
			
		
		<button type="submit" class="submit_button">Add
				Settings</button>
			

		<%-- <table id="table-form-input">

					<colgroup>
						<col class="oce-first" />
					</colgroup>
					<tbody>

						<tr>
							<td width="20%" valign="top">Company Logo</td>
							<td width="79%" valign="top"><span style="color: red;">*</span>
								<form:input path="logo_image" cssClass="forminput" type="file" />
							</td>
						</tr>
						<tr>
							<td width="20%" valign="top">Background Color:</td>
							<td width="79%" valign="top"><span style="color: red;">*</span>
								<form:input path="bg_color_code" cssClass="forminput"
									id="colorpickerField1" />
							</td>
						</tr>
						<tr>
							<td width="20%" valign="top">Company Color:</td>
							<td width="79%" valign="top"><span style="color: red;">*</span>
								<form:input path="header_color_code" cssClass="forminput"
									id="colorpickerField2" />
							</td>
						</tr>

						<tr>
							<td width="20%" valign="top">Navigation Links Color:</td>
							<td width="79%" valign="top"><span style="color: red;">*</span>
								<form:input path="header_links_color_code" cssClass="forminput"
									id="colorpickerField3" />
							</td>
						</tr>

						<tr>
							<td width="20%" valign="top">Navigation Links Hover Color:</td>
							<td width="79%" valign="top"><span style="color: red;">*</span>
								<form:input path="navigation_hover_color" cssClass="forminput"
									id="colorpickerField4" />
							</td>
						</tr>

						<tr>
							<td width="20%" valign="top">Enable Mentoring:</td>
							<td width="79%" valign="top"><span style="color: red;">*</span>
								<form:checkbox path="mentoring" cssClass="forminput"
									id="mentoring" />
							</td>
						</tr>



						<tr>
							<td width="20%">&nbsp;</td>
							<td width="70%" align="left"><input type="submit"
								value="Add" />&nbsp; &nbsp;</td>
						</tr>

					</tbody>
				</table> --%>
	</form:form>
</div>
<script type="text/javascript">
	$j().ready($('#colorpickerField1, #colorpickerField2').ColorPicker({
		onSubmit : function(hsb, hex, rgb, el) {
			$j(el).val(hex);
			$(el).ColorPickerHide();
		},
		onBeforeShow : function() {
			$(this).ColorPickerSetColor(this.value);
		},
		onChange : function(hsb, hex, rgb) {
			$('#colorpickerField1 div').css('backgroundColor', '#' + hex);
		}

	}).bind('keyup', function() {
		$(this).ColorPickerSetColor(this.value);
	})

	);
</script>
<script type="text/javascript">
	$j(function() {
		$j("#logo_image").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#colorpickerField1").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#colorpickerField2").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
	});
</script>