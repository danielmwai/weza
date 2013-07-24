<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
	$j(function() {
		$j("#courseTemplate").tabs();
	});
</script>

<div id="courseTemplate">

	<!-- li><a href="#templates">CourseTemplates</a></li>
	<li><a href="#templatemodules" id="contentTitle"-->


	<div id="templates">
		<!-- <div class="fieldShadow">
			<fieldset>
				<legend>Add Course Template</legend> -->
		<form:form method="post" modelAttribute="addCourseTemplateBean"
			class="add_form">
			<div id="addcoursetemplate_errors" class="error"></div>

			<div class="container course">
				<div class="container_inner">
					<label class="title">Template Name</label>
					<p>Letters and numbers only, 5 to 15 characters</p>

					<form:input path="name" cssClass="forminput" />
				</div>
			</div>

			<div class="container description">
				<div class="container_inner">
					<label class="title">Template Description</label>
					<p>Letters and numbers only, 5 to 15 characters</p>

					<form:textarea cols="30" rows="5" path="description" id="description"
						cssClass="forminput" />
				</div>
			</div>

			<%-- <div class="container factoring">
				<div class="container_inner">
					<label class="title">Evaluation Factoring</label>
					<form:checkbox path="evaluated" value="true" cssClass="forminput" />
				</div>
			</div> --%>

			<%-- preRequisites for the course --%>
			<div class="container preRequisites">
				<div class="container_inner">
					<label class="title">Prerequisites</label>
					<p>
						<form:select path="preRequisites" id="preRequsites"
							cssClass="forminput" multiple="multiple">
							<%--Default is non selected --%>
							<form:option exclusive="true" selected="selected" value="None">None</form:option>
							<c:forEach items="${allCourseTemplates}" var="courseTemplate">
								<form:option value="${courseTemplate.name}">${courseTemplate.name}</form:option>
							</c:forEach>
						</form:select>
					</p>
				</div>
			</div>

			<button type="submit" class="submit_button">Add course
				Template</button>
			<input type="button" value="Cancel"
				onClick="JJTEACH.ajaxLoad('/coursetemplate/listcoursetemplates'); return false;">
			<%-- <table id="table-form-input">
		<colgroup>
			<col class="oce-first" />
		</colgroup>
		<tbody>
			<tr>
				<td width="20%" valign="top">CourseTemplate name:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:input path="name" cssClass="forminput" /></td>
			</tr>


			<tr>
				<td width="20%" valign="top">CourseTemplate Description:</td>
				<td width="79%" valign="top"><span style="color: red;">*</span>
				<form:input path="description" id="description" cssClass="forminput" />
				</td>
			</tr>
			
				<tr>
				<td width="20%" valign="top">Evaluation Factoring:</td>
				<td width="79%" valign="top"><form:checkbox path="evaluated"
					value="true" cssClass="forminput" /></td>
				</tr>
			<tr>


				<td width="20%">&nbsp;</td>
				<td width="70%" align="left"><input type="submit" value="Add" />
				&nbsp; &nbsp; <a
					onClick="JJTEACH.ajaxLoad('/coursetemplate/listcoursetemplates'); return false;">Cancel</a>
				</td>
			</tr>

		</tbody>
	</table> --%>
		</form:form>
	</div>
</div>
<div id="templatemodules"></div>



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
				$j("#addCourseTemplateBean").submit(
						function() {
							//jAlert("preRequsites>>"+$j("#preRequsites").val());
							var account = $j(this).serializeObject();

							$j('#errors').html("Processing..........");
							$j.postJSON($j('#addCourseTemplateBean').attr(
									'action'), account, function(data) {
								doaction(data);
							});
							return false;
						});
				/* $j('#description').tinymce({
					script_url : '${PREFIX}/js/tiny_mce/tiny_mce.js',
					theme : "simple"
				}); */
			});

	function doaction(data) {
		if (data.t == null) {
			var message = data.m;
			$j('#addcoursetemplate_errors').html(message);
		} else {
			var message = "Saved";
			var courseTemplateId = data.t;
			$j('#addcoursetemplate_errors').html(message);
			JJTEACH.ajaxEditCT('/coursetemplate/ctinfo', courseTemplateId);
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
