/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.licence;

import com.tunaweza.core.business.dao.exceptions.licence.LicenseDoesNotExistException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.licence.License;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */
@Repository("licenseDao")
@Transactional
public class LicenseDaoImpl extends GenericDaoImpl<License> implements LicenseDao{

	@Override
	public List<License> getAllLicenses() {
		return findAll();
	}

	@Override
	public License findLicenseById(long id) throws LicenseDoesNotExistException{
		
		License license = findById(id);
		if(license==null){
			throw new LicenseDoesNotExistException();
		}
		return license;
	}

	@Override
	public License saveLicense(License license) {
		License savedLicense = null;
		savedLicense = saveOrUpdate(license);
		return savedLicense;
	}

	@Override
	public License findLicenseByName(String name) throws LicenseDoesNotExistException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	
}