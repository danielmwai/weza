/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.company.settings;

import com.tunaweza.core.business.dao.exceptions.company.settings.CompanySettingsDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.settings.CompanySettingsExistsException;
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.company.CompanySettings;
import java.util.List;

/**
 *
 * @author naistech
 */
public interface CompanySettingsDao extends GenericDao<CompanySettings> {

	/*
	 * @return a company's settings
	 * @throws CompanySettingsDoesNotExistException
	 * @param cid
	 */
	CompanySettings findCompanySettingsById(long cid)
			throws CompanySettingsDoesNotExistException;
	
	/**
	 * 
	 * @param companyid
	 * @return
	 * @throws CompanySettingsDoesNotExistException
	 */
	public CompanySettings findCompanySettingsByCompanyId(long companyid) 
			throws CompanySettingsDoesNotExistException;

	/*
	 * @return a company's settings
	 * @throws CompanySettingsDoesNotExistException
	 * @param companyname
	 */
	CompanySettings findCompanySettingsByName(String companyname)
			throws CompanySettingsDoesNotExistException;

	/*
	 * @return a company's settings
	 * @throws CompanySettingsDoesNotExistException
	 * @param companyname
	 */
	CompanySettings findCompanySettings(CompanySettings companySettings)
			throws CompanySettingsDoesNotExistException;

	/*
	 * @return a company settings
	 * @throws CompanySettingsDoesNotExistException
	 * @param companySettings
	 */
	CompanySettings addCompanySettings(CompanySettings companySettings) 
			throws CompanySettingsExistsException;

	/*
	 * save a company settings
	 * @param companySettings
	 */
	void saveOrUpdateCompanySettings(CompanySettings companySettings);

	/*
	 * deletes a company settings
	 * @param companySettings
	 */
	void deleteCompanySettings(CompanySettings companySettings)
			throws CompanySettingsDoesNotExistException;

	/*
	 * deletes a company settings
	 * @param cid
	 */
	void deleteCompanySetting(long cid) throws CompanySettingsDoesNotExistException;

	/*
	 * @return a list of company settings
	 */
	List<CompanySettings> getAllCompaniesSettings();

	/*
	 * @return count of company settings
	 */
	Integer getCount();
}

