/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.company.settings;

import com.tunaweza.core.business.dao.company.settings.CompanySettingsDao;
import com.tunaweza.core.business.dao.exceptions.company.settings.CompanySettingsDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.settings.CompanySettingsExistsException;
import com.tunaweza.core.business.model.company.CompanySettings;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */

@Service("companySettingsService")
@Transactional
public class CompanySettingsServiceImpl implements CompanySettingsService {
	
	@Autowired
	private CompanySettingsDao companySettingsDao;

	@Override
	public CompanySettings addCompanySettings(CompanySettings companySettings)
			throws CompanySettingsExistsException {
			return companySettingsDao.addCompanySettings(companySettings);
	}

	@Override
	public void deleteCompanySetting(long cid)
			throws CompanySettingsDoesNotExistException {
		companySettingsDao.deleteCompanySetting(cid);
		
	}

	@Override
	public void deleteCompanySettings(CompanySettings companySettings)
			throws CompanySettingsDoesNotExistException {
		companySettingsDao.deleteCompanySettings(companySettings);
		
	}

	@Override
	public CompanySettings findCompanySettings(CompanySettings companySettings)
			throws CompanySettingsDoesNotExistException {
		return companySettingsDao.findCompanySettings(companySettings);
	}

	@Override
	public CompanySettings findCompanySettingsByName(String companyname)
			throws CompanySettingsDoesNotExistException {
		return companySettingsDao.findCompanySettingsByName(companyname);
	}

	@Override
	public List<CompanySettings> getAllCompaniesSettings() {
		return companySettingsDao.getAllCompaniesSettings();
	}

	@Override
	public Integer getCount() {
		return companySettingsDao.getCount();
	}

	@Override
	public void saveOrUpdateCompanySettings(CompanySettings companySettings) {
		companySettingsDao.saveOrUpdateCompanySettings(companySettings);
		
	}

	@Override
	public CompanySettings findCompanySettingsById(long cid)
			throws CompanySettingsDoesNotExistException {
		return companySettingsDao.findCompanySettingsById(cid);
	}
	
	@Override
	public CompanySettings findCompanySettingsByCompanyId(long companyid)
			throws CompanySettingsDoesNotExistException {
		return companySettingsDao.findCompanySettingsByCompanyId(companyid);
	}
}
