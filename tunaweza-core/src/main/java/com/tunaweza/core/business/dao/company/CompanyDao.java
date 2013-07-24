/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.company;

import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.CompanyExistsException;
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.company.Company;
import java.util.List;

/**
 *
 * @author naistech
 */
public interface CompanyDao  extends GenericDao<Company> {

	/**
	 * Adds a new Company
	 * 
	 * @param <code>Company</code>
	 */
	public Company addCompany(Company company) throws CompanyExistsException;
	
	/**
	 * 
	 * @param db_id
	 * @param dbName
	 * @param dbPassword
	 * @param host
	 * @throws JJCloudInstanceExistsException
//	 */
//	public void createDatabase(Long db_id, String dbName, String dbPassword, String host, String dbUsername)
//			throws Exception;

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

}
