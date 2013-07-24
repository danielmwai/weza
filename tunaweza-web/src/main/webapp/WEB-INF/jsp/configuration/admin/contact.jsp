<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
	$j(function() {
		var tab = $j("#usertab").tabs({tabTemplate: "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-closethick'>close</span></li>"
			});
		$j( "#usertab span.ui-icon-closethick" ).live( "click", function() {
			var index = $j( "li", tab ).index( $j( this ).parent() );
			tab.tabs( "remove", index );
		});
	});
</script>


	<div id="infotab">
		<div class="fieldShadow">
			<fieldset>
				<legend>Company Contacts</legend>
				<!--<c:set var="user" value="${editUserBean }" />-->
				<table id="table-form-input">
					<colgroup>
						<col class="oce-first" />
					</colgroup>
					<tbody class="information">
						<tr>
							<td width="20%" valign="top">Name:</td>
							  <td width="79%" valign="top"><c:out value="${name}"></c:out>
						</tr>
						<tr>
							<td width="20%" valign="top">E-Mail:</td>
							  <td width="79%" valign="top"><a href="mailto:${email}" target="_self"><c:out
									value="${email}"></c:out></a></td>
						</tr>
						<tr>
							<td width="20%" valign="top">Tel Line 1:</td>
						  	<td width="79%" valign="top"><c:out value="${firsttel}"></c:out></td>
						</tr>
						<tr>
							<td width="20%" valign="top">Tel Line 2:</td>
							  <td width="79%" valign="top"><c:out value="${secondtel}"></c:out> 
							</td>
						</tr>
						<tr>
							<td width="20%" valign="top">Address:</td>
							  <td width="79%" valign="top"><c:out value="${add}"></c:out> 
							</td>
						</tr>
						<tr>
							<td width="20%" valign="top">Website:</td>
						  	<td width="79%" valign="top"><a href="${website}"><c:out value="${website}"></c:out></a> 
							</td>
						</tr>
						<tr>
							<td width="20%">&nbsp;</td>
							<td width="70%" align="left">
								<!--    <button
									onClick="${PREFIX}/home/index.htm">DONE</button> -->
								  	<a href="${PREFIX}/home/index.htm">DONE</a> 
							</td>
						</tr>
					</tbody>
				</table>
			</fieldset>

		</div>
	</div>





