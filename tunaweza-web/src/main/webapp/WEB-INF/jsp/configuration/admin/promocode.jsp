<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow">
	<form:form id="createPromoCodeBean" class="add_form" method="post"
		modelAttribute="createPromoCodeBean">

		<div id="promocode_errors" class="error"></div>

		<div class="container username">
			<div class="container_inner">
				<label class="title">Promo Code Name</label>

				<p>Letters and numbers only, 5 to 15 characters</p>
				<form:input path="promocodename" cssClass="forminput" />
			</div>
		</div>

		<div class="container email">
			<div class="container_inner">
				<label class="title">Number of extra users</label>
				<p>Enter number of valid extra users</p>
				<form:input path="numberofextrausers" cssClass="forminput" />
			</div>
		</div>

		<div class="container email">
			<div class="container_inner">
			<label class="title">Select License To Associate </label>
			<p>Associates a promo code to an existing license</p>
				<form:select path="associatedlicenseid" cssClass="forminput">
					<c:forEach var="license" items="${LICENSELIST}">
						<form:option value="${license.id}">${license.name}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		
		<div class="container email">
			<div class="container_inner">
				<label class="title">Activate the promocode</label>
				<p>Activate or deactivate this Promo Code </p>
				<form:checkbox path="use" cssClass="forminput" id="use"/>
			</div>
		</div>
		

		<button type="submit" class="submit_button" style="margin: 51px;">Save
			Promocode</button>

	</form:form>
</div>

<script type="text/javascript">
	$j().ready(
			function() {
				$j("#createPromoCodeBean").submit(
						function() {
							var account = $j(this).serializeObject();
							$j('#promocode_errors')
									.html("Processing..........");
							$j.postJSON($j('#createPromoCodeBean').attr(
									'action'), account, function(data) {
								doaction(data);
							});
							return false;
						});
			});

	function doaction(data) {
		var message = data.u;
		$j('#promocode_errors').html(message);
		if (message == "Saved") {
			JJTEACH.ajaxLoad('/promo_code/newpromocodesuccess');
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
	});
</script>
