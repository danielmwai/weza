/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.licence;

import com.tunaweza.core.business.dao.exceptions.licence.LicenseDoesNotExistException;
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.licence.License;
import java.util.List;

/**
 *
 * @author naistech
 */
public interface LicenseDao extends GenericDao<License> {
	
	/**
	 * 
	 * @return a list of all licenses
	 */
	public List<License> getAllLicenses();
	
	/**
	 * 
	 * @return license by Id
	 */
	public License findLicenseById(long id) throws LicenseDoesNotExistException;
	
	/**
	 * 
	 * @return License
	 */
	public License saveLicense(License license);
	
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws LicenseDoesNotExistException 
	 */
	public License findLicenseByName(String name) throws LicenseDoesNotExistException;
	
	
}