<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow" style="margin-top: 25px">
	<fieldset>
		<form:form modelAttribute="evaluationTestBean"
			action="${PREFIX}/rest/student/get/module/administer/test/${USERNAME}">
			<div id="evaluationTest_errors" class="error"></div>
			<legend></legend>
			<div id="timer"></div>
			<form:hidden path="timeRemaining" />
			<form:hidden path="timeout" />
			<input type="hidden" id="interval" />
			<table class="table-form-input">
				<tbody>
					<form:hidden path="question" />
					<tr>
						<td width="20%" valign="top"><b>Module:</b>
						</td>
						<td width="79%" valign="top">${evaluationTestBean.module}</td>
					</tr>
					<tr>
						<td width="20%" valign="top"><b>Question:</b>
						</td>
						<td width="90%" valign="top">
							<div>
								${evaluationTestBean.questionText}
							</div></td>
					</tr>
					<tr>
						<td width="20%" valign="top"><b>Choices:</b>
						</td>
                    <td>
                         <div id="james">
                         <form:checkboxes path="answer"
  										items="${evaluationTestBean.choises}" element="div" />  
  										</div>
                    </td>
                    </tr>
					<tr>
						<td width="20%" valign="top"></td>
						<td width="79%" valign="top"><input type="submit"
							value="Submit" /></td>
					</tr>
			</table>
		</form:form>
	</fieldset>
</div>
<script type="text/javascript">
	postJSON = function(url, data, callback) {
		return jQuery.ajax({
			'type' : 'POST',
			'url' : url,
			'contentType' : 'application/json',
			'data' : JSON.stringify(data),
			'success' : callback
		});
	}
	$j("#evaluationTestBean").submit(
			function() {
				var account = $j(this).serializeObject();
				$j('#addrole_errors').html("Processing..........");
				postJSON($j('#evaluationTestBean').attr('action'), account,
						function(response) {
							//JJTEACH.ajaxLoadPage(response);
							try {
								response.substr(0, 1);
								clearInterval($j("#interval").val());
								$j('#content').html(response);
							} catch (e) {
								$j('#evaluationTest_errors').html(
										response.message);
							}

						}); 
				return false;
			});
	$j(function() {
		$j("#evaluationTestBean :checkbox,#evaluationTestBean :radio")
				.removeAttr("checked");
		var id = setInterval(function() {
			var time = $j("#timeRemaining").val();
			var seconds = time % 60;
			var minutes = Math.floor(time / 60);
			$j("#timer").html("Time Remaining  " + minutes + ": " + seconds);
			$j("#timer").css("color","red");
			$j("#timeRemaining").val(parseFloat(time - 1));
			$j("#interval").val(id);
			if (time <= 0) {
				$j("#timeout").val(1);
				$j("#evaluationTestBean").submit();
			}

		}, '1000');
	});
	function isJsonString(str) {
		try {
			JSON.parse(str);
		} catch (e) {
			return false;
		}
		return true;
	}
</script>
