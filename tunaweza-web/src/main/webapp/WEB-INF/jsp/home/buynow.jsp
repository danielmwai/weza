<%@ include file="/WEB-INF/jsp/home/homeHeader.jsp"%>


<script type="text/javascript">
	$j(document).ready(function() {
		$j("li.nav4").attr('id', 'menu_active')
	});
</script>

<style type="text/css">
table.formTable {
	border-collapse: separate;
	border-spacing: 5px;
}
</style>

<div id="container" class="ltr">

	<form:form id="addCompanyBean" method="post" class="addCompany"
		enctype="multipart/form-data" modelAttribute="addCompanyBean">


		<h3>Buy Now Details</h3>

		<div id="companyBean_errors" style="text-align: center; color: red">

			<span><c:out value="${Message}" /> </span>
		</div>

		<table width="100%" class="formTable">
			<tr>
				<td width="36%"><label class="desc"> Product Name: </label></td>
				<td width="64%"><form:input path="productName"
						cssClass="field text fn" /></td>
			</tr>

			<tr>
				<td><label class="desc"> Organization or company name.
						<span id="req_2" class="req">*</span>
				</label></td>
				<td><form:input path="name" cssClass="field text fn" /></td>
			</tr>

			<tr>
				<td><label class="desc"> Company logo:</label></td>
				<td><form:input path="logo_image" cssClass="forminput"
						type="file" /></td>
			</tr>
			<tr>
				<td><label class="desc"> Email address (Note: this is
						also your username): <span id="req_2" class="req">*</span>
				</label></td>
				<td><div>
						<span class="full addr1"> <form:input path="email"
								cssClass="field text fn" />
						</span>
					</div></td>
			</tr>
			<tr>
				<td><label class="desc" id="title2"> Enter Password: <span
						id="req_2" class="req">*</span>
				</label></td>
				<td><form:password path="password"
						cssClass="field text addr password_sbar" /></td>
			</tr>
			<tr>
				<td><label class="desc" id="title2"> Re-enter Password:<span
						id="req_2" class="req">*</span>
				</label></td>
				<td><form:password path="repassword" cssClass="field text addr" />
				</td>
			</tr>
			<tr>
				<td><label class="desc"> Phone number: </label></td>
				<td><form:input path="firstline" cssClass="field text medium" /></td>
			</tr>
			<tr>
				<td><label class="desc"> Select the type of access you
						want: </label></td>
				<td><form:select path="urlType" class="field select medium"
						tabindex="19">
						<form:option value="public">Public domain name (e.g.
							yourname.jjteach.com)</form:option>
						<form:option value="private">Private domain name (e.g.
							www.yourname.com)</form:option>
					</form:select></td>
			</tr>

			<!--
			to be displayed when a user wants a private url
			-->

			<tr id="urlInput">
				<td><label class="desc"> Enter a private domain name: </label></td>
				<td><form:input path="url" cssClass="field text medium" /></td>
			</tr>

			<tr>
				<td><label class="desc"> License Type: </label></td>
				<td><form:select path="licenseType" class="field select medium"
						tabindex="19">

						<form:option value="chui">Chui - 10 Users</form:option>
						<form:option value="simba">Simba - 25 Users</form:option>
						<form:option value="ndovu">Ndovu - 100 Users</form:option>
						<form:option value="kifaru">Kifaru - 250 Users</form:option>
						<form:option value="nyati">Nyati - 1000 Users</form:option>

					</form:select></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<div>

						<button type="submit" class="submit_button" id="saveForm">Submit
							details</button>
					</div>
				</td>
			</tr>
		</table>


	</form:form>

</div>
<!--container-->
</div>
</div>
&copy;
<a href="#" target="_blank"> JJPeople</a>
</body>

<div id="progress" style="display: none">
	<h3 align="center">please wait</h3>
	<p align="center">Registering company..</p>
	<p align="center">
		<img src="${PREFIX}/images/preloader/blue-006-loading.gif" alt=""
			width="50%" />
	</p>
</div>


<script type="text/javascript">
	var myWindow;
	var privateURL;
	var options = {
		target : '#companyBean_errors', // target element(s) to be updated with server response 
		beforeSubmit : showDialog, // pre-submit callback 
		success : closeDialog,
		error : errorDialog

	// other available options: 
	//url:       url         // override for form's 'action' attribute 
	//type:      type        // 'get' or 'post', override for form's 'method' attribute 
	//dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
	//clearForm: true        // clear all form fields after successful submit 
	//resetForm: true        // reset the form after successful submit 

	// $.ajax options can be used here too, for example: 
	//timeout:   3000 
	};

	$j(document).ready(
			function() {
				$(document).ajaxError(
						function(e, xhr, settings, exception) {
							alert('error in: ' + settings.url + ' \n'
									+ 'error:\n' + xhr.responseText);
						});

				privateURL = $j('#urlInput');
				privateURL.hide();

				$j('#urlType').change(function() {
					var domainType = $j(this).val();

					if (domainType == 'private') {
						privateURL.show();

					} else {
						privateURL.hide();
					}
				});

			});

	function showDialog(formData, jqForm, options) {
		var queryString = $j.param(formData);
		myWindow = $j('#progress').modal();
	}

	//function to close dialog
	function closeDialog(responseText, statusText) {
		myWindow.close();
	}

	function errorDialog(request, status, error) {
		myWindow.close();
		alert("An error has occured !!")
	}

	$j(function() {

		$j("#name").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});

		$j("#email")
				.validate(
						{
							expression : "if (VAL.match(/^[^\\W][a-zA-Z0-9\\_\\-\\.]+([a-zA-Z0-9\\_\\-\\.]+)*\\@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*\\.[a-zA-Z]{2,4}$/)) return true; else return false;",
							message : "Please enter a valid Email ID",
							error_class : "errors",
							error_field_class : "errors"
						});
		$j("#password")
				.validate(
						{
							expression : "if (VAL.length >= 6 && VAL.length <= 20 && VAL) return true; else return false;",
							message : "Enter a password 6 to 20 characters long",
							error_class : "errors",
							error_field_class : "errors"
						});
		$j("#repassword")
				.validate(
						{
							expression : "if ((VAL == jQuery('#password').val()) && VAL) return true; else return false;",
							message : "Passwords do not match",
							error_class : "errors",
							error_field_class : "errors"
						});

		// 		$j("#firstline").validate({
		// 			expression : "if (VAL) return true; else return false;",
		// 			message : "Please enter the Required field",
		// 			error_class : "errors",
		// 			error_field_class : "errors"
		// 		});

		$j("#logo_image").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});

		$j("#url")
				.validate(
						{
							expression : "if ((jQuery('#urlType').val() == 'private' && VAL) || (jQuery('#urlType').val() != 'private')) return true; else return false;",
							message : "Please enter a url",
							error_class : "errors",
							error_field_class : "errors"
						});

		$j('.addCompany').validated(function() {
			$j('.addCompany').ajaxSubmit(options);
		});

	});
</script>

<script type="text/javascript">
	$j(function() {
		$j('.password_sbar').pstrength();
	});

	$j('.password_sbar').focusout(function() {
		$j('#password_text').hide();
		$j('#password_bar').hide();
	});

	$j('.password_sbar').focusin(function() {
		$j('#password_text').show();
		$j('#password_bar').show();
	});
</script>

<script type="text/javascript">
	
</script>
<script>
	function checkvalue(val) {
		if (val === "Other") {
			$j("#location").css("display", "block");
			$j("#location").val("");
		} else {
			$j("#location").css("display", "none");
			$j("#location").val("Other");
		}
	}
</script>


