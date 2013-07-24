package com.tunaweza.core.business.service.license.impl;

import com.tunaweza.core.business.dao.exceptions.licence.LicenseDoesNotExistException;
import com.tunaweza.core.business.dao.licence.LicenseDao;
import com.tunaweza.core.business.model.licence.License;
import com.tunaweza.core.business.service.license.LicenseService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





@Service("licenseService")
@Transactional
public class LicenseServiceImpl implements LicenseService{
	
@Autowired
LicenseDao licenseDao;

	@Override
	public License addLicense(License license) {
		
		return licenseDao.saveLicense(license);
	}

	@Override
	public License findLicenseById(long id) throws LicenseDoesNotExistException {
		return licenseDao.findLicenseById(id);
	}

	@Override
	public List<License> listAllLicenses() {
		return licenseDao.getAllLicenses();
	}

	@Override
	public void saveLicense(License license) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteLicense(License license) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteLicense(long licenseId) {
		// TODO Auto-generated method stub
		
	}

}