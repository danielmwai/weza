<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow" style="margin-top:25px">
	<!-- <fieldset style="margin-bottom: 20px; margin-top: 20px"> -->
		<span class="legend">Post Solution</span>
		<iframe name="postSolution" id="postSolution"
			style="display:none;"></iframe>
		<form:form method="post" modelAttribute="postSolutionBean"
			enctype="multipart/form-data" target="postSolution"
			action="${PREFIX}/solution/postsolution.htm">
			<div id="postsolution_errors" class="error"></div>
			<form:hidden path="topicId" />
			<table id="table-form-input">
				<tbody>
					<tr>
						<td width="20%" valign="top">Subject:<span style="color: red;">*</span></td>
						<td width="79%" valign="top">
							<form:input path="subject" cssClass="forminput" />
						</td>
					</tr>
					<tr>
						<td width="20%" valign="top">Message:<span
							style="color: red;">*</span>
						</td>
						<td width="90%" valign="top"><form:textarea path="message"
								id="message" cssClass="inputtextarea"
								cssStyle="clear:left; width: 80%; height: 220px;" /></td>
					</tr>
					<tr>
						<td width="20%" valign="top">Solution:<span style="color: red;">*</span></td>
						<td width="79%" valign="top">
							<form:input path="solution" cssClass="forminput" type="file" />
						</td>
					</tr>
					<tr>
						<td width="20%">&nbsp;</td>
						<td width="70%" align="left"><input type="submit"
							value="Submit" />&nbsp; &nbsp;</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	<!-- </fieldset> -->
</div>
<script type="text/javascript">
	$j().ready(function() {
		$j('#message').tinymce({
			script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
			theme : "simple"
		});
	});
</script>