
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<tiles:insertAttribute name="meta" />
<title><tiles:insertAttribute name="title" /></title>
<tiles:insertAttribute name="header" />
<tiles:insertAttribute name="common-scripts" />
<tiles:insertAttribute name="scripts" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
</head>
<body>
	<script type="text/javascript">
		var prefix = "${PREFIX}";
	</script>
	<div id="wrapper">

		<div id="header" class="header"
			style="background-color:${HEADER_COLOR}">
			<div id="logo">
				<img id="img" src="${PREFIX}/imagelogo.htm" title="jjteach"
					alt="company logo" />
			</div>
			<%--             <div id="product"> ${PRODUCTNAME}</div>
 --%>
			<div id="companynav">${USER}</div>
			<div id="product" style="color: red">${WARNING}</div>

			<c:choose>
				<c:when test="${DAYS>0}">



					<ul id="top_menu">
						<%-- 			  			<li>${PRODUCTNAME}</li>   --%>
						<%-- <ul><li>	<!--  <li id="user_menu">--><div id="companynav">${USER}</div><!-- </li>>--></li></ul></br> --%>
						<!-- 					<ul id="user_menu_contents"> -->
						<!-- <li>-->
						<ul>
							<li><a href="#"
								onClick="JJTEACH.ajaxLoad('/user/profile'); return false;"><font
									color='black'>My Profile</font></a></li>
						</ul>
						<%-- <ul>
							<li><a href="${PREFIX}/forums/list.page"><font
									color='black'>Forum</font></a></li>
						</ul>  --%>
						<ul>
							<li><a href="#"><font color='black'>Feedback</font></a></li>
						</ul>
						<ul>
							<li><a href="${PREFIX}/j_spring_security_logout"><font
									color='black'>  Logout</font></a>
							<!-- </li>--></li>
						</ul>


						<!-- 					</ul> -->
						<!-- 				</li> -->
						<ul>
							<li><a
								onClick="JJTEACH.ajaxLoad('/contact_details/contacts');">Contact
									Us</a></li>
						</ul>

						<ul>
							<li id="dateDiv"></li>
						</ul>
					</ul>
		</div>
		<div id="navigation">
			<div class="oe_wrapper">
				<ul id="oe_menu" class="oe_menu"
					style="background-color:${MENU_COLOR}" style="color:${FONT_COLOR}">
					<tiles:insertAttribute name="menu" />

					<c:if test="${ROLE!='ROLE_STUDENT' && EVAL_TESTING != 'STUDENT'}">
						<tiles:insertAttribute name="content-tree" />
					</c:if>
				</ul>
			</div>
		</div>
		<div id="container">


			<c:if
				test="${ROLE!='ROLE_ADMIN'  && EVAL_TESTING != 'ADMIN' && ROLE!='ROLE_SUPERCLOUDADMIN'&& ROLE!='ROLE_CLOUDADMIN'}">
				<div id="sidebar">
					<tiles:insertAttribute name="content-tree" />
				</div>
			</c:if>

			<div id="content">
				<div id="contents">
					<tiles:insertAttribute name="content" />
				</div>
				<div id="prevbutton"></div>
				<div id="nextbutton"></div>
			</div>
			</c:when>
			<c:otherwise>
				<div id="content">
					<div id="contents">


						<tiles:insertAttribute name="paymentAttribute" />
					</div>
				</div>

			</c:otherwise>
			</c:choose>

		</div>
		<tiles:insertAttribute name="footer" />
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {

			$j('#img').error(function() {

				$j("#img").attr("src", "${PREFIX}/images/common/jjteach.png");

			});

		});
	</script>

	<script type="text/javascript">
		$(function menu() {
			var $oe_menu = $('#oe_menu');
			var $oe_menu_items = $oe_menu.children('li');
			var $oe_overlay = $('#oe_overlay');

			$oe_menu_items.bind(
					'mouseenter',
					function() {
						var $this = $(this);
						$this.addClass('slided selected');
						$this.children('div').css('z-index', '9999').stop(true,
								true).slideDown(
								200,
								function() {
									$oe_menu_items.not('.slided').children(
											'div').hide();
									$this.removeClass('slided');
								});
					}).bind(
					'mouseleave',
					function() {
						var $this = $(this);
						$this.removeClass('selected').children('div').css(
								'z-index', '1');
					});

			$oe_menu_items.bind('mouseenter', function() {
				var $this = $(this);
				$oe_overlay.stop(true, true).fadeTo(200, 0.6);
				$this.addClass('hovered');
			}).bind('mouseleave', function() {
				var $this = $(this);
				$this.removeClass('hovered');
				$oe_overlay.stop(true, true).fadeTo(200, 0);
				$oe_menu_items.children('div').hide();
			})
		});
	</script>
</body>
</html>
