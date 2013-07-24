/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.company.settings;

import com.tunaweza.core.business.dao.company.settings.CompanySettingsDao;
import com.tunaweza.core.business.dao.exceptions.company.settings.CompanySettingsDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.settings.CompanySettingsExistsException;
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
	private CompanySettingsDao companySettingsDAO;

	@Override
	public CompanySettings addCompanySettings(CompanySettings companySettings)
			throws CompanySettingsExistsException {
			return companySettingsDAO.addCompanySettings(companySettings);
	}

	@Override
	public void deleteCompanySetting(long cid)
			throws CompanySettingsDoesNotExistException {
		companySettingsDAO.deleteCompanySetting(cid);
		
	}

	@Override
	public void deleteCompanySettings(CompanySettings companySettings)
			throws CompanySettingsDoesNotExistException {
		companySettingsDAO.deleteCompanySettings(companySettings);
		
	}

	@Override
	public CompanySettings findCompanySettings(CompanySettings companySettings)
			throws CompanySettingsDoesNotExistException {
		return companySettingsDAO.findCompanySettings(companySettings);
	}

	@Override
	public CompanySettings findCompanySettingsByName(String companyname)
			throws CompanySettingsDoesNotExistException {
		return companySettingsDAO.findCompanySettingsByName(companyname);
	}

	@Override
	public List<CompanySettings> getAllCompaniesSettings() {
		return companySettingsDAO.getAllCompaniesSettings();
	}

	@Override
	public Integer getCount() {
		return companySettingsDAO.getCount();
	}

	@Override
	public void saveOrUpdateCompanySettings(CompanySettings companySettings) {
		companySettingsDAO.saveOrUpdateCompanySettings(companySettings);
		
	}

	@Override
	public CompanySettings findCompanySettingsById(long cid)
			throws CompanySettingsDoesNotExistException {
		return companySettingsDAO.findCompanySettingsById(cid);
	}
	
	@Override
	public CompanySettings findCompanySettingsByCompanyId(long companyid)
			throws CompanySettingsDoesNotExistException {
		return companySettingsDAO.findCompanySettingsByCompanyId(companyid);
	}
}
