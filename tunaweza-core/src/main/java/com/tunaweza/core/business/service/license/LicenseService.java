package com.tunaweza.core.business.service.license;

import com.tunaweza.core.business.dao.exceptions.licence.LicenseDoesNotExistException;
import com.tunaweza.core.business.model.licence.License;
import java.util.List;


/**
 * 
 * @author Peter Kiragu
 *
 */
public interface LicenseService {
	
	/**
	 * 
	 * @param license
	 * @return License
	 */
	
	public License addLicense(License license);
	
	/**
	 * 
	 * @param id
	 * @return License
	 * @throws LicenseDoesNotExistException
	 */
	public License findLicenseById(long id) throws LicenseDoesNotExistException;
	
	/**
	 * 
	 * @return List<License>
	 */
	public List<License> listAllLicenses();
	
	/**
	 * 
	 * @param license
	 */
	public void saveLicense(License license);
	
	/**
	 * 
	 * @param license
	 */
	public void deleteLicense(License license);
	
	/**
	 * 
	 * @param licenseId
	 */
	public void deleteLicense(long licenseId);
}