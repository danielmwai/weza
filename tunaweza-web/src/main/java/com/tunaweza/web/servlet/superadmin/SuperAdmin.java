package com.tunaweza.web.servlet.superadmin;

import com.tunaweza.core.business.utils.AdminMD5;
import com.tunaweza.core.business.utils.FileUtils;
import com.tunaweza.core.business.utils.SystemFolder;
import com.tunaweza.web.servlet.superadmin.util.SuperAdminLoginUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




/**
 * 
 * @author Ephraim Muhia
 * 
 */
@SuppressWarnings("serial")
public class SuperAdmin extends HttpServlet {

	public static String FIELD_OLD_PASSWORD = "oldpassword";

	public static String FIELD_PASSWORD = "password";

	public static String FIELD_REPEAT_PASSWORD = "repeatpassword";

	public static String USER_KEY = "jjteachU";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		resp.setHeader("Expires", "Tues, 01 Jan 1980 00:00:00 GMT");
		String uri = req.getRequestURI();

		if (SuperAdminLoginUtil.checkIfSet()) {
			changepasword(out, uri, req);
		} else {
			login(out, uri, req);
		}
		return;

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		resp.setContentType("text/html");
		java.io.PrintWriter out = resp.getWriter();

		String oldpassword = req.getParameter(FIELD_OLD_PASSWORD);
		if (oldpassword == null) {
			String password = req.getParameter(FIELD_PASSWORD);
			String repeatpassword = req.getParameter(FIELD_REPEAT_PASSWORD);
			if ((password != null) && (password.trim().length() > 7)) {

				if (!validPassword(password, repeatpassword)) {
					out.println("<html>");
					out.println("<title>Invalid User</title>");
					out.println("<body><center><h2>"
							+ "Passwords do not match</h2><br>");
					out.println("Press the 'Back' button to try again");
					out.println("</center></body></html>");
					out.flush();
					return;
				} else {
					if (writeFile(password)) {
						success(out, req);
					} else {
						out.println("<html>");
						out.println("<title>File Write Error</title>");
						out.println("<body><center><h2>"
								+ "File Write Error!</h2><br>");
						out.println("Contact System admin or "
								+ "Press the 'Back' button to try again");
						out.println("</center></body></html>");
						out.flush();
						return;
					}
				}
			}
		} else {
			String password = req.getParameter(FIELD_PASSWORD);
			String repeatpassword = req.getParameter(FIELD_REPEAT_PASSWORD);
			if ((oldpassword != null) && (oldpassword.trim().length() > 7)) {
				if ((password != null) && (password.trim().length() > 7)) {
					if ((repeatpassword != null)
							&& (repeatpassword.trim().length() > 7)) {
						if (!validPassword(password, repeatpassword)) {
							out.println("<html>");
							out.println("<title>Invalid User</title>");
							out.println("<body><center><h2>"
									+ "Passwords do not match</h2><br>");
							out.println("Press the 'Back' button to try again");
							out.println("</center></body></html>");
							out.flush();
							return;
						} else {
							if (SuperAdminLoginUtil.comparePassword(oldpassword)) {

								if (writeFile(password)) {
									success(out, req);
								} else {
									out.println("<html>");
									out
											.println("<title>File Write Error</title>");
									out.println("<body><center><h2>"
											+ "File Write Error!</h2><br>");
									out
											.println("Contact System admin or "
													+ "Press the 'Back' button to try again");
									out.println("</center></body></html>");
									out.flush();
									return;
								}
							} else {
								out.println("<html>");
								out.println("<title>Password Error</title>");
								out.println("<body><center><h2>"
										+ "Passwords do not match!</h2><br>");
								out
										.println("Press the 'Back' button to try again");
								out.println("</center></body></html>");
								out.flush();
								return;
							}
						}
					}
				}
			}
		}

		resp.sendRedirect(req.getRequestURI());
	}

	protected void login(PrintWriter out, String uri, HttpServletRequest req)
			throws java.io.IOException {
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Super Admin settings</title>");
		out.println("<link rel='shortcut icon' href='" + req.getContextPath()
				+ "/images/icons/favicon.ico'/>");
		out.println("</head>");
		out.println("<center><h2>Enter super admin details</h2>");
		out.println("<br><form method=POST action=\"" + uri + "\">");
		out.println("<table>");
		out.println("<tr><td>Password:</td>");
		out.println("<td><input type=password name=" + FIELD_PASSWORD
				+ " size=30></td></tr>");
		out.println("<tr><td>Confirm Password:</td>");
		out.println("<td><input type=password name=" + FIELD_REPEAT_PASSWORD
				+ " size=30></td></tr>");
		out.println("</table><br>");
		out.println("<input type=submit value=\"SUBMIT\">");
		out.println("</form></center></body></html>");
	}

	protected void success(PrintWriter out, HttpServletRequest req)
			throws java.io.IOException {
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Saved</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center><h2>Settings saved. Proceed to login</h2>");
		out.println("<br><br>");
		out.println("<a href='" + req.getContextPath() + "'>LOGIN</a></center>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		return;
	}

	protected void changepasword(PrintWriter out, String uri,
			HttpServletRequest req) throws java.io.IOException {
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Super Admin settings</title>");
		out.println("<link rel='shortcut icon' href='" + req.getContextPath()
				+ "/images/icons/favicon.ico'/>");
		out.println("</head>");
		out.println("<center><h2>Change password</h2>");
		out.println("<br><form method=POST action=\"" + uri + "\">");
		out.println("<table>");
		out.println("<tr><td>Old Password:</td>");
		out.println("<td><input type=password name=" + FIELD_OLD_PASSWORD
				+ " size=30></td></tr>");
		out.println("<tr><td>New Password:</td>");
		out.println("<td><input type=password name=" + FIELD_PASSWORD
				+ " size=30></td></tr>");
		out.println("<tr><td>Confirm Password:</td>");
		out.println("<td><input type=password name=" + FIELD_REPEAT_PASSWORD
				+ " size=30></td></tr>");
		out.println("</table><br>");
		out.println("<input type=submit value=\"SUBMIT\">");
		out.println("</form></center></body></html>");
	}

	protected boolean validPassword(String password, String repeatpassword) {
		boolean valid = false;
		if ((password != null) && (password.length() > 7)
				&& (repeatpassword != null) && (repeatpassword.length() > 7)) {
			valid = password.equals(repeatpassword);
		}

		return valid;
	}

	protected boolean writeFile(String password) {
		boolean written = false;
		File f = new File(SystemFolder.getAdminPasswordFile());
		try {
			f.setWritable(true);
		} catch (Exception e) {

		}
		try {
			FileUtils.createFile(f, AdminMD5.generateMD5(password));
			written = true;
			try {
				f.setReadOnly();
			} catch (Exception e) {

			}
		} catch (IOException e) {
			written = false;
		}
		return written;
	}
}