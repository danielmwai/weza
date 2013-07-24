<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>

<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.5.2.min.js"></script>
<div id="main" align="center">
	<div id="social" align="right" width="100%">
		<table>
			<tr>
				<td align="right"><a
					href="http://www.facebook.com/pages/JJPeople/100975103277799"
					target="_blank"><img src="${PREFIX}/images/common/facebook.png"
						align="right" />
				</a><a href="http://www.twitter.com/jjpeople" target="_blank"><img
						src="${PREFIX}/images/common/twitter.png" align="right" />
				</a><a href="http://www.linkedin.com/company/jj-people" target="_blank"><img
						src="${PREFIX}/images/common/linkedin.png" align="right" />
				</a>
				</td>
			</tr>
			<tr>
				<td align="right"><a href="http://www.jjpeople.com/?page_id=20"
					target="_blank" style="font-size: 0.9em; text-decoration: none;">
						Contact us</a>
				</td>
			</tr>
		</table>
	</div>


	<%-- <div id="cloudlogo" align="center" width="100%">
		<img src="${PREFIX}/images/splash/p33_logo.jpg" title="jjteach"
			width="30%" height="30%" styto get correct passwordsle="margin: 20px;" /> 
	</div> --%>
	<!--

	<div id="contents">
			<h3>Welcome to JJCloud, the online learning platform...</h3>
			<p>JJCloud is an e-learning platform that enables
				students to learn remotely and be mentored online.</p>
		</div>
		
	-->
	<div id="loginForm" align="center">
		<form name='f' action='/jjteach/j_spring_security_check' method='POST'>


			<div>
				<c:if test="${not empty param.authfailed}">
					<c:if test="${((fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'User is disabled', 'Wrong Username and or Password.')) != '') && ( (fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Bad credentials', 'Wrong Username and or Password.')) == 'User is disabled' )}">

						<script type="text/javascript" src="${PREFIX}/js/popup.js"></script>
					</c:if>
					<c:if test="${((fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Bad credentials', 'Wrong Username and or Password.')) != '') && ( (fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Bad credentials', 'Wrong Username and or Password.')) != 'User is disabled' ) }">
						<span id="infomessage" class="errormessage">
							${fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Bad credentials', 'Wrong Username and or Password.')}</span>
						<br />	
					</c:if>
				</c:if>

				<c:if test="${login}">
					<span id="infomessage" class="errormessage"> ${msg}</span>
				</c:if>

				<table class="list" style="padding: 0.5px;">

					<tr>
						<td colspan=2>Email</td>
					</tr>
					<tr>
						<td colspan=2><input type='text' name='j_username'
							class="logininput" id="j_username" />
						</td>
					</tr>
					<tr>

						<td colspan=2>Password</td>
					</tr>

					<tr>
						<td colspan=2><input type='password' name='j_password'
							class="logininput" />
						</td>

					</tr>
					<tr>
						<td colspan="2"><input name="submit" type="submit"
							value="Login" /><a
							onclick="JJTEACH.ajaxChangePassword('/forgotpassword'); return false;"
							href="#"
							style="font-size: 0.9em; color: grey; text-decoration: none;">
								Forgot password? </a>
						</td>
					</tr>
					<tr>
					<td colspan="2">
						<a href="home.htm">Home page</a>
					</td>
					</tr>	
					<!-- 
					<tr align="center">
						<td align="center" style="font-size:0.9em;color:grey">Don't have an account?</td><td><a href="#"
							onclick="JJTEACH.ajaxCompany('/register'); return false;" style="font-size:0.9em;text-decoration:none;">
								Sign up now!</a></td>
					</tr>
					 -->
				</table>
				</div>
		</form>
	</div>
	<div id ="popupForgot"></div>
</div>

<div id="popupForgot" style="display:none"></div>

<script type="text/javascript" src="${PREFIX}/js/shadedborder.js"></script>
<script type="text/javascript">
	$j(function() {
		$j("#loginForm form").submit(
				function() {
					if ($j("#j_username").val() == "SCA") {
						//alert($j("#j_username").val());
						$j("#loginForm form").attr("action",
								"${PREFIX}/root/login.htm");
					}
				});
		/* var drift = ($j('.info:first').position().top - $j('#loginForm')
				.position().top)
		$j("#loginForm").css('top', drift); */
	});

	// WARNING POPUP MANAGEMENT -----------------------------

	//SETTING UP OUR POPUP
	//0 means disabled; 1 means enabled;
	var popupWarningStatus = 0;

	//loading popup with jQuery magic!
	function loadWarningPopup() {
		//loads popup only if it is disabled
		if (popupWarningStatus == 0) {
			$j("#backgroundPopup").css({
				"opacity" : "0.7"
			});
			$j("#backgroundPopup").fadeIn("slow");
			$j("#popupWarning").fadeIn("slow");
			popupWarningStatus = 1;
		}
	}

	//disabling popup with jQuery magic!
	function disableWarningPopup() {
		//disables popup only if it is enabled
		if (popupWarningStatus == 1) {
			$j("#backgroundPopup").fadeOut("slow");
			$j("#popupWarning").fadeOut("slow");
			popupWarningStatus = 0;
		}
	}

	//centering popup
	function centerWarningPopup() {
		//request data for centering
		var windowWidth = document.documentElement.clientWidth;
		var windowHeight = document.documentElement.clientHeight;
		var popupHeight = $j("#popupWarning").height();
		var popupWidth = $j("#popupWarning").width();
		//centering
		$j("#popupWarning").css({
			"position" : "absolute",
			"top" : ((windowHeight / 2 - popupHeight / 2) - 30),
			"left" : ((windowWidth / 2 - popupWidth / 2) - 20)
		});
		//only need force for IE6

		$j("#backgroundPopup").css({
			"height" : windowHeight
		});

	}

	// BLOCKED POPUP MANAGEMENT -----------------------------

	//SETTING UP OUR POPUP
	//0 means disabled; 1 means enabled;
	var popupBlockedStatus = 1;

	//loading popup with jQuery magic!
	function loadBlockedPopup() {
		//loads popup only if it is disabled
		if (popupBlockedStatus == 0) {
			$j("#backgroundPopup").css({
				"opacity" : "0.7"
			});
			$j("#backgroundPopup").fadeIn("slow");
			$j("#popupBlocked").fadeIn("slow");
			popupBlockedStatus = 1;
		}
	}

	//disabling popup with jQuery magic!
	function disableBlockedPopup() {
		//disables popup only if it is enabled
		if (popupBlockedStatus == 1) {
			$j("#backgroundPopup").fadeOut("slow");
			$j("#popupBlocked").fadeOut("slow");
			popupBlockedStatus = 0;
		}
	}

	//centering popup
	function centerBlockedPopup() {
		//request data for centering
		var windowWidth = document.documentElement.clientWidth;
		var windowHeight = document.documentElement.clientHeight;
		var popupHeight = $j("#popupBlocked").height();
		var popupWidth = $j("#popupBlocked").width();
		//centering
		$j("#popupBlocked").css({
			"position" : "absolute",
			"top" : ((windowHeight / 2 - popupHeight / 2) - 30),
			"left" : ((windowWidth / 2 - popupWidth / 2) - 20)
		});
		//only need force for IE6

		$j("#backgroundPopup").css({
			"height" : windowHeight
		});

	}

	//CONTROLLING EVENTS IN jQuery
	$j(document).ready(function() {

		//LOADING BLOCKED POPUP
		//Click the button event!
		$j("#clickBlocked").click(function() {
			//centering with css
			centerBlockedPopup();
			//load popup
			loadBlockedPopup();
		});

		//CLOSING BLOCKED POPUP
		//Click the x event!
		$j("#popupBlockedClose").click(function() {
			disableBlockedPopup();
		});
		//Click out event!
		$j("#backgroundPopup").click(function() {
			disableBlockedPopup();
		});
		//Press Escape event!
		$j(document).keypress(function(e) {
			if (e.keyCode == 27 && popupBlockedStatus == 1) {
				disableBlockedPopup();
			}
		});

		//----------------------------------------------

		//LOADING WARNING POPUP
		//Click the button event!
		$j("#clickWarning").click(function() {
			//centering with css
			centerWarningPopup();
			//load popup
			loadWarningPopup();
		});

		//CLOSING WARNING POPUP
		//Click the x event!
		$j("#popupWarningClose").click(function() {
			disableWarningPopup();
		});
		//Click out event!
		$j("#backgroundPopup").click(function() {
			disableWarningPopup();
		});
		//Press Escape event!
		$j(document).keypress(function(e) {
			if (e.keyCode == 27 && popupWarningStatus == 1) {
				disableWarningPopup();
			}
		});
	});
</script>

