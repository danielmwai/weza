/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.company;

import com.sleepycat.je.DatabaseException;
import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.CompanyExistsException;
import com.tunaweza.core.business.dao.exceptions.company.license.CompanyLicenseDoesNotExistException;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.licence.CompanyLicense;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author naistech
 */
public interface CompanyService {
/**
	 * Adds a new Company
	 * 
	 * @param <code>Company</code>
	 */
	public Company addCompany(Company company) throws CompanyExistsException;
	
	/**
	 * 
	 * @param databaseUrl
	 * @param user
	 * @param password
	 * @throws DatabaseException
	 */
	public void createDabaseTables(String databaseUrl, String user,
			String password) throws DatabaseException;

	/**
	 * saves or updates a Company details
	 * 
	 * @param <code>Company</code>
	 */
	public void saveCompany(Company company);
	
	

	/**
	 * searches for the specified company
	 * 
	 * @param <code>long</code> the company Id.
	 */
	public Company findCompanyById(long id) throws CompanyDoesNotExistException;

	/**
	 * searches for the specified company
	 * 
	 * @param <code>Company</code>
	 */
	public Company findCompany(Company company)
			throws CompanyDoesNotExistException;

	/**
	 * searches for the specified company
	 * 
	 * @param <code>String</code> the company email
	 */
	public Company findCompanyByEmail(String email)
			throws CompanyDoesNotExistException;

	/**
	 * Deletes the specified company using the company Id
	 * 
	 * @param <code>long</code> the company Id.
	 */
	public void deleteCompany(long id) throws CompanyDoesNotExistException;

	/**
	 * Deletes the specified company
	 * 
	 * @param <code>Company</code>
	 */
	public void deleteCompany(Company company)
			throws CompanyDoesNotExistException;

	/**
	 * Lists all the registered companies
	 * 
	 */
	public List<Company> getAllCompanies();

	public void createCompanyUser(String email, String password,
			String location, String name, Company userCompany);
//	/**
//	 * 
//	 * @param db_id
//	 * @param dbName
//	 * @param dbPassword
//	 * @param host
//	 * @param dbUsername
//	 * @throws JJCloudInstanceExistsException
//	 */
	public void createDatabaseAndPopulate(Long db_id, String dbName, String dbPassword,
			String host, String dbUsername)
			throws Exception;
	/**
	 * 
	 * @param database
	 * @throws DatabaseException
	 */
	public void dropDatabase(String database) throws DatabaseException;
	
	/**
	 * Method to set the first time a company logged in, 
	 * and the last time a company was logged in. 
	 * @author Daniel
	 * @param userCompany
	 * @throws CompanyDoesNotExistException
	 */
	public void setFirstAndLastLogins(Company userCompany) throws CompanyDoesNotExistException;

	public String generatePassword();

	/* jaymmmmooooooooooo
	 * public void creatSuperUser(Company userCompany)
			throws JJCloudInstanceExistsException;*/
	
	/**
	 * 
	 * @param license
	 * @throws CompanyLicenseDoesNotExistException
	 */
	public void setCompanyRegistrationAndExpiryDate(CompanyLicense license) 
			throws CompanyLicenseDoesNotExistException,ParseException;
	
}
