<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<link href="${PREFIX}/css/tooltip.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function() {
	 
    //Select all anchor tag with rel set to tooltip
    $('a[rel=tooltip]').mouseover(function(e) {
         
        //Grab the title attribute's value and assign it to a variable
        var rid = $(this).attr('name');   
         
        //Remove the title attribute's to avoid the native tooltip from the browser
        $(this).attr('title','');
         
        //Append the tooltip template and its value
        $(this).append('<div id="tooltip"><div class="tipHeader"></div><div class="tipBody" id="tipBody"></div><div class="tipFooter"></div></div>');
        JJTEACH.ajaxReportToolTipPreview(rid,'#tipBody');
        //Set the X and Y axis of the tooltip
        $('#tooltip').css('top', e.pageY + 10 );
        $('#tooltip').css('left', e.pageX + 20 );
         
        //Show the tooltip with faceIn effect
        $('#tooltip').fadeIn('500');
        $('#tooltip').fadeTo('10',0.8);
         
    }).mousemove(function(e) {
     
        //Keep changing the X and Y axis for the tooltip, thus, the tooltip move along with the mouse
        $('#tooltip').css('top', e.pageY + 10 );
        $('#tooltip').css('left', e.pageX + 20 );
         
    }).mouseout(function() {
     
        //Put back the title attribute's value
        $(this).attr('title',$('.tipBody').html());
     
        //Remove the appended tooltip template
        $(this).children('div#tooltip').remove();
     
    });
 
});
</script>
<div class="from_container">
	<table id='list' style="padding: 5px 5px; width: 600px">
		<tr>
			<td colspan=3><b>Saved Reports<b></td>
		</tr>
		<c:choose>
			<c:when test="${empty EMPTY}">
				<th>Report Title</th>
				<th>Download Options</th>
				<th>Delete</th>
				<c:forEach var="report" items="${REPORT_LIST}" varStatus="status">
					<tr>
						<td>${report.reportTitle}</td>
						<td><a name="${report.id}" rel="tooltip"
							onClick="JJTEACH.ajaxSavedReportDownload('/report/saved_report.htm?reportType=pdf',${report.id})">Pdf<img
								src="${PREFIX}/images/icons/docs/pdf.png" alt="PDF" /></a>|<a
							name="${report.id}" rel="tooltip"
							onClick="JJTEACH.ajaxSavedReportDownload('/report/saved_report.htm?reportType=xls',${report.id})">Excel<img
								src="${PREFIX}/images/icons/docs/xls.png" alt="EXCEL" /></a>|<a
							name="${report.id}" rel="tooltip"
							onClick="JJTEACH.ajaxSavedReportDownload('/report/saved_report.htm?reportType=csv',${report.id})">Csv<img
								alt="CSV" src="${PREFIX}/images/icons/docs/csv.png" /></a></td>
						<td align='center'><button
								onClick="JJTEACH.ajaxDeleteReport('/report/delete_report','${report.id}')">Delete</button></td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan=3>${EMPTY}</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</div>