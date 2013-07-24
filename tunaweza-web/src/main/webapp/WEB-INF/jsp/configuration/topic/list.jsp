<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<fieldset> <form:form method="post"
	modelAttribute="addTopicBean" target="addtopicframe">
	

	<div id="addtopic_errors" class="error"></div>


	<table id="table-form-input">
		<colgroup>
			<col class="oce-first" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col" align="center"> Select Module &nbsp;<form:select path="module" cssClass="forminput"
					onchange="JJTEACH.populateTable(this.value)">
					<form:option value="" label="" />
					<form:options items="${MODULELIST}" itemValue="id" itemLabel="name" />
				</form:select></th>
				
			</tr>
		</thead>
		<tbody id="tbod">
			

		</tbody>
	</table>

</form:form></fieldset>

	<table id='listtop' border='0' cellspacing='0'>

	</table>
<iframe name="adduserframe" id="adduserframe" style="display: none"></iframe>






