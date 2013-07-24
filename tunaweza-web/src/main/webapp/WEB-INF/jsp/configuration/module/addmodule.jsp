<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
	$j(function() {
		$j("#moduletab").tabs();
	});
</script>

<form:form method="post" modelAttribute="addModuleBean" class="add_form">
	<div id="addmodule_errors" class="error"></div>

	<div class="container module">
		<div class="container_inner">
			<label class="title">Module Name</label>
			<p>Name of the module you want to create</p>

			<form:input path="moduleName" cssClass="forminput" />
		</div>
	</div>

	<div class="container description">
		<div class="container_inner">
			<label class="title">Module Description</label>
			<p>A bit of a description of what the module entails</p>

			<form:textarea cols="30" rows="5" path="moduleDescription" cssClass="forminput" />
		</div>
	</div>

	<div class="container preRequisites">
		<div class="container_inner">
			<label class="title">Prerequisites</label>
			<p>
				<form:select path="preRequisites" id="preRequsites"
					cssClass="forminput" multiple="multiple">
					<%--Default is non selected --%>
					<form:option exclusive="true" selected="selected" value="None">None</form:option>
					<c:forEach items="${MODULELIST}" var="module">
						<form:option value="${module.id}">${module.name}</form:option>
					</c:forEach>
				</form:select>
			</p>
		</div>
	</div>

	<div class="container time">
		<div class="container_inner">
			<label class="title">Time to
				Complete(days/hours/weeks/months)</label>
			<p>How many days/hours/weeks/months should it take to complete
				the module</p>
			<form:input path="timeToComplete" cssClass="forminput" />
			<form:select path="duration" cssClass="forminput">
				<form:option value="hours" label="hours"></form:option>
				<form:option value="days" label="days"></form:option>
				<form:option value="weeks" label="weeks"></form:option>
				<form:option value="months" label="months"></form:option>
			</form:select>
		</div>
	</div>

	<div class="container enabled">
		<div class="container_inner">
			<label class="title">Enabled</label>
			<p>Should the module be enabled</p>

			<form:checkbox path="enabled" value="true" cssClass="forminput" />
		</div>
	</div>

	<%-- <div class="container factoring">
		<div class="container_inner">
			<label class="title">Evaluation Factoring</label>
			<p>Difficulty level(Fibonacci sequence)</p>

			<form:checkbox path="evaluated" value="true" cssClass="forminput" />
		</div>
	</div> --%>

	<div class="container mentoring">
		<div class="container_inner">
			<c:choose>
				<c:when test="${MENTORINGSTATE}">
					<tr>

						<label class="title">Enable Mentoring</label>
						<form:checkbox path="mentoring" cssClass="forminput"
							checked="${mentoring}" value="true" />

					</tr>
				</c:when>
			</c:choose>
		</div>
	</div>

	<button type="submit" class="submit_button">Add Module</button>
	<input type="button" value="Cancel"
		onClick="JJTEACH.ajaxLoad('/module/listmodules'); return false;">


	<%-- <table id="table-form-input">
						<colgroup>
							<col class="oce-first" />
						</colgroup>
						<tbody>
							<tr>
								<td width="20%" valign="top">Module name:</td>
								<td width="79%" valign="top"><span style="color: red;">*</span>
									<form:input path="moduleName" cssClass="forminput" /></td>
							</tr>


							<tr>
								<td width="20%" valign="top">Module Description:</td>
								<td width="79%" valign="top"><span style="color: red;">*</span>
									<form:input path="moduleDescription" id="moduleDescription"
										cssClass="forminput" /></td>
							</tr>
							<tr>
								<td width="20%" valign="top">Time to Complete:</td>
								<td width="79%" valign="top">&nbsp; <form:input
										path="timeToComplete" cssClass="forminput" /></td>
							</tr>
							<tr>
								<td width="20%" valign="top">Enabled:</td>
								<td width="79%" valign="top"><form:checkbox path="enabled"
										value="true" cssClass="forminput" /></td>
							</tr>

							<tr>
								<td width="20%" valign="top">Evaluation Factoring:</td>
								<td width="79%" valign="top"><form:checkbox
										path="evaluated" value="true" cssClass="forminput" /></td>
							</tr>
							<c:choose>
								<c:when test="${MENTORINGSTATE}">
									<tr>

										<td width="20%" valign="top">Enable Mentoring:</td>
										<td width="79%" valign="top"><form:checkbox
												path="mentoring" cssClass="forminput" checked="${mentoring}"
												value="true" /></td>

									</tr>
								</c:when>
							</c:choose>
							<tr>
								<td width="20%">&nbsp;</td>
								<td width="70%" align="left"><input type="submit"
									value="Add Module" />&nbsp; &nbsp; <a
									onClick="JJTEACH.ajaxLoad('/module/listmodules'); return false;">Cancel</a>
								</td>
							</tr>

						</tbody>
					</table> --%>
</form:form>
<div id="topic"></div>
<script type="text/javascript">
	$j().ready(
			function() {
				$j('#preRequsites').dropdownchecklist({
					customTextFn : function(opts) {
						var count = 0;
						var first = null;
						for ( var i = 0, len = opts.length; i < len; ++i) {
							if (opts[i].selected) {
								if (!first) {
									first = opts[i].text;
								}
								++count;
							}
						}
						switch (count) {
						case 0:
							return "None";
						case 1:
							return first;
						default:
							return first + ", " + (count - 1) + " More"
						}
					},
					maxDropHeight : 100,
					width : 120

				});
				var tabcontent_height = ($j('#content').height() - 84);
				$j("#module").attr('style',
						'height:' + tabcontent_height + 'px;');
				$j("#topic").attr('style',
						'height:' + tabcontent_height + 'px;');
				$j("#addModuleBean").submit(
						function() {
							var account = $j(this).serializeObject();
							$j('#errors').html("Processing..........");
							$j.postJSON($j('#addModuleBean').attr('action'),
									account, function(data) {
										doaction(data);
									});
							return false;
						});
				$j(window)
						.resize(
								function() {
									var tabcontent_height = ($j('#content')
											.height() - 84);
									$j("#module").attr(
											'style',
											'height:' + tabcontent_height
													+ 'px;');
									$j("#topic").attr(
											'style',
											'height:' + tabcontent_height
													+ 'px;');
								});

				/* $j('#moduleDescription').tinymce({
					script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
					theme : "simple"
				}); */

			});

	function doaction(data) {
		if (data.u == null) {
			var message = data.m;
			//$j('#addmodule_errors').html(message);
			alert(message);
		} else {
			var message = "Saved";
			var moduleId = data.u;
			//$j('#addmodule_errors').html(message);
			alert(message);
			JJTEACH.ajaxLoad('/module/listmodules');
		}
	}
</script>
<script type="text/javascript">
	$j(function() {
		$j("#moduleName").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
		$j("#timeToComplete").validate({
			expression : "if (VAL) return true; else return false;",
			message : "Please enter the Required field",
			error_class : "errors",
			error_field_class : "errors"
		});
	});
</script>