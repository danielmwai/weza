package com.tunaweza.core.business.service.license;

import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.license.CompanyLicenseDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.licence.LicenseDoesNotExistException;
import java.sql.SQLException;


public interface CompanyLicenseService {

	/**
	 * 
	 * @param companyid
	 * @return
	 * @throws CompanyLicenseDoesNotExistException
	 */
	public long getLicenseRemainingDays(String email)throws CompanyLicenseDoesNotExistException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, CompanyDoesNotExistException;	
	/**
	 * 
	 * @param companyid
	 * @return
	 * @throws CompanyDoesNotExistException
	 */
	public void licenseMessage(long companyid) throws Exception;
	
	/**
	 * 
	 * @param role
	 * @param companyId
	 * @throws CompanyDoesNotExistException
	 * @throws Exception
	 */
	public void senEmailToAllAdmin(String role, long companyId) throws CompanyDoesNotExistException, Exception;
	/**
	 * 
	 * @param compnayid
	 * @return
	 * @throws CompanyLicenseDoesNotExistException
	 * @throws CompanyDoesNotExistException
	 * @throws LicenseDoesNotExistException 
	 */
	


	public long maxNumberOfActiveUsersForLicence(String email)throws CompanyLicenseDoesNotExistException, CompanyDoesNotExistException, LicenseDoesNotExistException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;	
}
