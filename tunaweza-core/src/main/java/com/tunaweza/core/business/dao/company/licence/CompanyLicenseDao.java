/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.company.licence;

import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.license.CompanyLicenseDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.model.licence.CompanyLicense;

/**
 *
 * @author naistech
 */
public interface CompanyLicenseDao {
 
	/**
	 * 
	 * @param id
	 * @return
	 * @throws CompanyLicenseDoesNotExistException
	 */
	public CompanyLicense findLicenseById(long id) throws CompanyLicenseDoesNotExistException;
	
	/**
	 * 
	 * @param license
	 * @return
	 * @throws CompanyLicenseDoesNotExistException
	 */
	public CompanyLicense findLicense(CompanyLicense license) throws CompanyLicenseDoesNotExistException;
	
	/**
	 * 
	 * @param companyid
	 * @return
	 * @throws CompanyDoesNotExistException
	 */
	public CompanyLicense findLicenseByCompanyId(long companyid)
			throws CompanyDoesNotExistException ;

	
	
	/**
	 * 
	 * @param license
	 * @throws UserDoesNotExistException
	 */
	public void deleteLicense(CompanyLicense license) throws CompanyLicenseDoesNotExistException ;


	/**
	 * 
	 * @param uid
	 * @throws UserDoesNotExistException
	 */
	public void deleteLicense(long uid) throws CompanyLicenseDoesNotExistException;
	
	
	/**
	 * 
	 * @param license
	 */
	public void saveLicense(CompanyLicense license);
	
	/**
	 * 
	 * @return
	 */
	public int count();
}
