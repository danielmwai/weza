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

package com.tunaweza.web.system;

import com.tunaweza.core.business.settings.Settings;
import com.tunaweza.web.directories.Directories;
import static com.tunaweza.web.directories.Directories.ADMINDIRECTORY;
import static com.tunaweza.web.directories.Directories.HOMEDIRECTORY;
import static com.tunaweza.web.directories.Directories.JJTEACHDIRECTORY;
import static com.tunaweza.web.directories.Directories.LOGSDIRECTORY;
import static com.tunaweza.web.directories.Directories.PASSWORDDIRECTORY;
import static com.tunaweza.web.directories.Directories.PASSWORDFILE;
import static com.tunaweza.web.directories.Directories.SYSTEMDIRECTORY;
import static com.tunaweza.web.directories.Directories.fileSeparator;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class SystemFolder implements Directories, Settings{
	private static ApplicationContext ctx;

	private static WebApplicationContext webApplicationContex;

	private static ServletContext webctx;

	private static Properties mime;

	private static String systemDirectory = null;
	private static String rootDirectory = null;
	private static String adminDirectory = null;
	private static String jjetsDirectory = null;
	private static String passwordDirectory = null;
	private static String adminPasswordFile = null;
	private static String logsDirectory = null;
	private static String invoiceDirectory = null;

	/**
	 * 
	 * @param cctx
	 */
	public static void setFSContextValues(String cctx) {
		systemDirectory = cctx;
		rootDirectory = systemDirectory + fileSeparator + HOMEDIRECTORY;
		adminDirectory = rootDirectory + fileSeparator + ADMINDIRECTORY;
		jjetsDirectory = adminDirectory + fileSeparator + JJTEACHDIRECTORY;
		passwordDirectory = jjetsDirectory + fileSeparator + PASSWORDDIRECTORY;
		adminPasswordFile = passwordDirectory + fileSeparator + PASSWORDFILE;
		logsDirectory = rootDirectory + fileSeparator + LOGSDIRECTORY;
	}

	/**
	 * 
	 * @return webctx
	 */
	public static String getSystemDirectoryFromWebContext() {
		return ((String) webctx.getAttribute(SYSTEMDIRECTORY));
	}

	/**
	 * 
	 * @return ctx
	 */
	public static ApplicationContext getApplicationContext() {
		if (ctx != null)
			return ctx;
		throw new RuntimeException("\n--------------------------------\n"
				+ "\n\nContext is not set. \nFrom:"
				+ SystemFolder.class.getCanonicalName());
	}

	/**
	 * @return the ctx
	 */
	public static ApplicationContext getCtx() {
		return ctx;
	}

	/**
	 * @param ctx the ctx to set
	 */
	public static void setCtx(ApplicationContext ctx) {
		SystemFolder.ctx = ctx;
	}

	/**
	 * @return the webApplicationContex
	 */
	public static WebApplicationContext getWebApplicationContex() {
		return webApplicationContex;
	}

	/**
	 * @param webApplicationContex the webApplicationContex to set
	 */
	public static void setWebApplicationContex(
			WebApplicationContext webApplicationContex) {
		SystemFolder.webApplicationContex = webApplicationContex;
	}

	/**
	 * @return the webctx
	 */
	public static ServletContext getWebctx() {
		return webctx;
	}

	/**
	 * @param webctx the webctx to set
	 */
	public static void setWebctx(ServletContext webctx) {
		SystemFolder.webctx = webctx;
	}

	/**
	 * @return the mime
	 */
	public static Properties getMime() {
		return mime;
	}

	/**
	 * @param mime the mime to set
	 */
	public static void setMime(Properties mime) {
		SystemFolder.mime = mime;
	}

	/**
	 * @return the systemDirectory
	 */
	public static String getSystemDirectory() {
		return systemDirectory;
	}

	/**
	 * @param systemDirectory the systemDirectory to set
	 */
	public static void setSystemDirectory(String systemDirectory) {
		SystemFolder.systemDirectory = systemDirectory;
	}

	/**
	 * @return the rootDirectory
	 */
	public static String getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * @param rootDirectory the rootDirectory to set
	 */
	public static void setRootDirectory(String rootDirectory) {
		SystemFolder.rootDirectory = rootDirectory;
	}

	/**
	 * @return the adminDirectory
	 */
	public static String getAdminDirectory() {
		return adminDirectory;
	}

	/**
	 * @param adminDirectory the adminDirectory to set
	 */
	public static void setAdminDirectory(String adminDirectory) {
		SystemFolder.adminDirectory = adminDirectory;
	}

	/**
	 * @return the jjetsDirectory
	 */
	public static String getJjetsDirectory() {
		return jjetsDirectory;
	}

	/**
	 * @param jjetsDirectory the jjetsDirectory to set
	 */
	public static void setJjetsDirectory(String jjetsDirectory) {
		SystemFolder.jjetsDirectory = jjetsDirectory;
	}

	/**
	 * @return the passwordDirectory
	 */
	public static String getPasswordDirectory() {
		return passwordDirectory;
	}

	/**
	 * @param passwordDirectory the passwordDirectory to set
	 */
	public static void setPasswordDirectory(String passwordDirectory) {
		SystemFolder.passwordDirectory = passwordDirectory;
	}

	/**
	 * @return the adminPasswordFile
	 */
	public static String getAdminPasswordFile() {
		return adminPasswordFile;
	}

	/**
	 * @param adminPasswordFile the adminPasswordFile to set
	 */
	public static void setAdminPasswordFile(String adminPasswordFile) {
		SystemFolder.adminPasswordFile = adminPasswordFile;
	}

	/**
	 * @return the logsDirectory
	 */
	public static String getLogsDirectory() {
		return logsDirectory;
	}

	/**
	 * @param logsDirectory the logsDirectory to set
	 */
	public static void setLogsDirectory(String logsDirectory) {
		SystemFolder.logsDirectory = logsDirectory;
	}

	/**
	 * @return the invoiceDirectory
	 */
	public static String getInvoiceDirectory() {
		return invoiceDirectory;
	}

	/**
	 * @param invoiceDirectory the invoiceDirectory to set
	 */
	public static void setInvoiceDirectory(String invoiceDirectory) {
		SystemFolder.invoiceDirectory = invoiceDirectory;
	}
}
