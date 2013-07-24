<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<script type="text/javascript">
	$j(function() {
		var topic = ${TOPIC.id};
		/* var content_width=(($j('body').width()*0.95) - 280);
		$j("#contents,#content").css("width", content_width); */
		if($j("#coursecontent button"))
			$j("#coursecontent button").button();
		$j(".navigationButtons button, #coursecontent .head button,.topnavigationButtons button,").attr('val', ${TOPIC.id});
		if($j("#treeDiv > ul >li:first").attr('id')== topic){
			$j("button.prev").css('display','none');
		}
		else if($j("#treeDiv > ul >li:last").attr('id')== topic){
			$j("button.next").css('display','none');
		}
		$j("#prevbutton").html($j('div.navigationButtons .prev').get());
		$j("#nextbutton").html($j('div.navigationButtons .next').get());
		/* $j("#topprevbutton").html($j('div.topnavigationButtons .prev').get());
		$j("#topnextbutton").html($j('div.topnavigationButtons .next').get()); */
		if($j.browser.msie){
			$j("#coursecontent").css('overflow','auto');
		//	$j("#coursecontent").css('height',($j('body').height()-180 )+"px");
			$j("#content").css("overflow","hidden");
		//	$j("#coursecontent .navigationButtons").css("overflow","hidden");
			$j("#coursecontent .text").append("<br/><br/><br/>");		
		}
	});
	$j("#nextbutton button,#prevbutton button").click(function(){
		var node = $j("#treeDiv li#"+$j(this).attr('val')).get();
		var node_to_load;
		if($j(this).text()=="Next"){
			$j.jstree._reference(node).open_node();
			node_to_load = $j.jstree._reference ( node )._get_next(node);
		}
		else if ($j(this).text()=="Previous"){
			var previous_node = $j.jstree._reference ( node )._get_prev(node);
			if(previous_node)
				$j.jstree._reference(previous_node).open_node(previous_node);		
			node_to_load= $j.jstree._reference ( node )._get_prev(node);
		}
		$j.jstree._reference ( node_to_load ).select_node(node_to_load,true);
		var target = $j('#contents');
        if (target.length)
        {
            var top = target.offset().top;
            $j('html,body').animate({scrollTop: top}, 1000);
        }
	});
	$j("#coursecontent .head #complete").click(function(){
	   $j("#treeDiv li#"+$j(this).attr('val')).attr('rel','STATUS_TOPIC_COMPLETED');
	   $j.ajax({
		   		url:"${PREFIX}/user/completetopic.htm",
		        type:"POST",
		        data: { "topicId": $j(this).attr('val')},
		   		success: function(data){
		   			$j("#coursecontent .head div").html(data.m);
		   			$j("#coursecontent .head div").css("font-size","1.3em");
		   		}
		 });		
	});
</script>
<div class="head" id="top">
<!-- <div  class="topnavigationButtons">
<button class="prev">Previous</button>
<button class="next">Next</button>
</div> -->
<div style="clear: both; color: red; float: right"><c:if
	test="${TOPIC.exercise == false && TOPIC.status !='STATUS_TOPIC_COMPLETED'}">
	<c:choose>
		<c:when test="${TOPIC.status =='STATUS_TAKE_QUIZ'}">
			<button class="buttonright"
				onclick="JJTEACH.ajaxTakeQuiz(${TOPIC.id}); return false;">Complete</button>
		</c:when>
		<c:otherwise>
			<button class="buttonright" id="complete">Complete</button>
		</c:otherwise>
	</c:choose>
</c:if> <c:if test="${TOPIC.exercise == true}">
	<c:choose>
		<c:when test="${EXERCISE_STATUS == 'STATUS_EXERCISE_PASSED' }">
			<a
				onClick="JJTEACH.ajaxReadFeedback('/solution/readfeedback',${TOPIC.id});return false;">Read
			FeedBack</a>
		</c:when>
		<c:when
			test="${EXERCISE_STATUS == 'STATUS_EXERCISE_SUBMITTED_BUT_AWAITING_FEEDBACK' }">
			<a
				onClick="JJTEACH.ajaxSubmitExercise('/solution/replacesolution',${TOPIC.id});return false;">Replace
			Solution</a>
		</c:when>
		<c:when
			test="${EXERCISE_STATUS == 'STATUS_EXERCISE_MARKED_AND_REQUIRES_MORE_WORK' }">
			<a
				onClick="JJTEACH.ajaxReadFeedback('/solution/readfeedback',${TOPIC.id});return false;">Read
			FeedBack</a>
			<a
				onClick="JJTEACH.ajaxSubmitExercise('/solution/resubmitsolution',${TOPIC.id});return false;">Resubmit
			Solution</a>
		</c:when>
		<c:otherwise>
			<a
				onClick="JJTEACH.ajaxSubmitExercise('/solution/postsolution',${TOPIC.id});return false;">Post
			Solution</a>
		</c:otherwise>
	</c:choose>
</c:if></div>
</div>
<div class="text">${TOPICTEXT.text} 
<c:if test="${FILE_ATTACHED}">
	<span class="transAttachment"><a
		href="${PREFIX}/topic/downloadattachedtopicfile.htm?fileId=${FILE_ID}">Download
	Attached File ( ${FILE_NAME} )</a> </span>
</c:if>
</div>
<div  class="navigationButtons">
<button class="prev">Previous</button>
<button class="next">Next</button>
</div>
<div id="popupQuiz${TOPIC.id}"></div>