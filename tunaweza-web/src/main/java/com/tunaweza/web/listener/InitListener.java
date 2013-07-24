package com.tunaweza.web.listener;

import com.tunaweza.core.business.settings.Settings;
import static com.tunaweza.core.business.settings.Settings.PREFIX;
import com.tunaweza.core.business.utils.Directories;
import static com.tunaweza.core.business.utils.Directories.ADMINDIRECTORY;
import static com.tunaweza.core.business.utils.Directories.HOMEDIRECTORY;
import static com.tunaweza.core.business.utils.Directories.JJTEACHDIRECTORY;
import static com.tunaweza.core.business.utils.Directories.LOGSDIRECTORY;
import static com.tunaweza.core.business.utils.Directories.PASSWORDDIRECTORY;
import static com.tunaweza.core.business.utils.Directories.PASSWORDFILE;
import static com.tunaweza.core.business.utils.Directories.SYSTEMDIRECTORY;
import static com.tunaweza.core.business.utils.Directories.fileSeparator;
import com.tunaweza.core.business.utils.SystemFolder;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class InitListener implements ServletContextListener,
		ServletContextAttributeListener, Directories, Settings {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	SystemFolder systemFolder = new SystemFolder();

	@SuppressWarnings("static-access")
	public void contextInitialized(ServletContextEvent arg0) {
		String pr = arg0.getServletContext().getContextPath();
		ServletContext ctx = arg0.getServletContext();
		ctx.setAttribute(PREFIX, pr);
		

		String cctx = arg0.getServletContext()
				.getInitParameter(SYSTEMDIRECTORY);

		File xfile = new File(cctx);
		cctx = xfile.getAbsolutePath();

		ctx.setAttribute(SYSTEMDIRECTORY, cctx);

		String systemDirectory = cctx;
		String homeDirectory = systemDirectory + fileSeparator + HOMEDIRECTORY;
		String adminDirectory = homeDirectory + fileSeparator + ADMINDIRECTORY;
		String jjetsDirectory = adminDirectory + fileSeparator + JJTEACHDIRECTORY;
		String passwordDirectory = jjetsDirectory + fileSeparator
				+ PASSWORDDIRECTORY;
		String passwordFile = passwordDirectory + fileSeparator + PASSWORDFILE;

		String logsDirectory = homeDirectory + fileSeparator + LOGSDIRECTORY;
		
		 // System Directory
		 
		File systemDir = new File(systemDirectory);
		if (!systemDir.exists())
			systemDir.mkdirs();

		systemDir = new File(systemDirectory);
		if (!systemDir.exists())
			systemDir.mkdirs();

		
		 // Home Directory
		 
		File homeDir = new File(homeDirectory);
		if (!homeDir.exists())
			homeDir.mkdirs();

		homeDir = new File(homeDirectory);
		if (!homeDir.exists())
			homeDir.mkdirs();

		
		 // Admin Directory
		 
		File adminDir = new File(adminDirectory);
		if (!adminDir.exists())
			adminDir.mkdirs();

		adminDir = new File(adminDirectory);
		if (!adminDir.exists())
			adminDir.mkdirs();

		
		 // JJETS dir
		 

		File jjetsDir = new File(jjetsDirectory);
		if (!jjetsDir.exists())
			jjetsDir.mkdirs();

		jjetsDir = new File(jjetsDirectory);
		if (!jjetsDir.exists())
			jjetsDir.mkdirs();

		
		 // Password Dir
		 
		File passwordDir = new File(passwordDirectory);
		if (!passwordDir.exists())
			passwordDir.mkdirs();

		passwordDir = new File(passwordDirectory);
		if (!passwordDir.exists())
			passwordDir.mkdirs();

		
		 // Password file
		 
		File passwordF = new File(passwordFile);
		if (!passwordF.exists())
			try {
				passwordF.createNewFile();
				passwordF.setReadOnly();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		passwordF = new File(passwordFile);
		if (!passwordF.exists())
			try {
				passwordF.createNewFile();
				passwordF.setReadOnly();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		
		 // Logs Directory
		 
		File logsDir = new File(logsDirectory);
		if (!logsDir.exists())
			logsDir.mkdirs();

		logsDir = new File(logsDirectory);
		if (!logsDir.exists())
			logsDir.mkdirs();
		
		
		 
		systemFolder.setFSContextValues(cctx);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent arg0) {

	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent arg0) {

	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent arg0) {

	}
}