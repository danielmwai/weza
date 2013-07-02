/*
 * The MIT License
 *
 * Copyright 2013 Daniel Mwai <naistech.gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.tunaweza.web.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@SuppressWarnings("serial")
public class Admin extends HttpServlet {

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

        if (AdminLoginUtil.checkIfSet()) {
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
                            if (AdminLoginUtil.comparePassword(oldpassword)) {

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
