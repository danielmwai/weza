package com.tunaweza.web.servlet.superadmin.util;

import com.tunaweza.core.business.utils.AdminMD5;
import com.tunaweza.core.business.utils.FileUtils;
import com.tunaweza.core.business.utils.SystemFolder;



public class SuperAdminLoginUtil {
	public static boolean checkIfSet() {
		boolean set = false;
		String contents = FileUtils.readTextFile(SystemFolder
				.getAdminPasswordFile());
		if (contents.trim().length() > 1) {
			set = true;
		}
		return set;
	}

	public static boolean comparePassword(String password) {
		boolean similar = false;
		String stored = FileUtils.readTextFile(SystemFolder
				.getAdminPasswordFile());
		if (stored.trim().length() > 1) {
			String compareTo = AdminMD5.generateMD5(password);
			if (compareTo.equalsIgnoreCase(stored)) {
				similar = true;
			}
		}
		return similar;
	}
}