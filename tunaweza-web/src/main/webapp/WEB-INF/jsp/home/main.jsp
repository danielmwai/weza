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
						align="right" /> </a> <a href="http://www.twitter.com/jjpeople"
					target="_blank"><img src="${PREFIX}/images/common/twitter.png"
						align="right" /> </a> <a
					href="http://www.linkedin.com/company/jj-people" target="_blank"><img
						src="${PREFIX}/images/common/linkedin.png" align="right" /> </a>
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
	<div id="contents">
		<table style="width: 100%">
			<tr>
				<td width="50%" valign="top">
					<table>
						<tr>
							<td><img src="${PREFIX}/images/splash/jjcloud.png"
								title="jjteach" style="margin: 20px; height: 200px" />
							</td>
						</tr>
						<tr>
							<td>
								<h3>Welcome to JJTeach, the online learning platform...</h3>
								<p>JJTeach is an e-learning platform that enables students
									to learn remotely and be mentored online.</p></td>
						</tr>
						<tr>
							<td>
								<div class="register_link">
									<a href="company/register.htm"
										style="font-size: 1.5em; color: white; text-decoration: none;">Register
										Now !</a>

								</div>
								
							</td>
						</tr>
						<tr>
						<td>
						...if registered <a href="login.htm">Log In</a>
						</td>
						</tr>

					</table>
				</td>
				<td>
					<!-- img src="${PREFIX}/images/common/jjteach_home.png"
					style="margin: 20px; top: 0; left: 0; width: 80%; height: 100%" /-->
					<p>JJTeach&reg; is a learning and evaluation platform, it
						enables associates to be evaluated remotely. This current release
						of JJTeach&reg; has the following features :</p> <br />
					<div class="info1">
						<img src="${PREFIX}/images/common/admin.png" alt="admin section"
							align="left" /> <b>Admin Section</b><br /> JJTeach&reg; offers
						the functionality for administrators to configure and manage the
						system.
					</div>


					<div class="info1">
						<img src="${PREFIX}/images/common/mentor.png"
							alt="online mentoring" align="left" /> <b>Online Evaluation</b><br />
						JJTeach&reg; provides a facility for associates to be tested on
						their skills in .....
					</div>

					<div class="info1">
						<img src="${PREFIX}/images/common/user.png" alt="profile"
							align="left" /> <b>Personalized profile</b><br /> JJTeach&reg;
						enables associates to update and maintain their personal
						information and log in credentials.
					</div>

					<div class="info1">
						<img src="${PREFIX}/images/common/user.png" alt="profile"
							align="left" /> <b>Other ...</b><br /> JJTeach&reg; ..to add
						more
					</div></td>
			</tr>
		</table>
	</div>

</div>