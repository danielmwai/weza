/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.company;

import com.sleepycat.je.DatabaseException;
import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.CompanyExistsException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.company.Company;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */

@Repository(value = "companyDao")
@Transactional
public class CompanyDaoImpl extends GenericDaoImpl<Company> implements
		CompanyDao {

	@Autowired
     SessionFactory sessionFactory;
//
//	@Autowired
//    private DatabaseAPI databaseApi;
	
	@Override
	public Company addCompany(Company company) throws CompanyExistsException {
		Company savedCompany = null;
		try {
			savedCompany = saveOrUpdate(company);
			return savedCompany;
		} catch (ConstraintViolationException e) {
			throw new CompanyExistsException();
		} catch (DataIntegrityViolationException e) {
			BatchUpdateException ee = (BatchUpdateException) e.getRootCause();

			SQLException eee = ee.getNextException();

			String servmsg = eee.getLocalizedMessage();

			throw new CompanyExistsException(servmsg);
		}
	}

	@Override
	public void saveCompany(Company company) {
		saveOrUpdate(company);
		//flush();
	}
	


	@Override
	public Company findCompanyById(long companyId)
			throws CompanyDoesNotExistException {
		Company company = findById(companyId);
		if (company == null) {
			throw new CompanyDoesNotExistException("Company with ID : "
					+ companyId + " does not exist");
		}
		return company;
	}

	@Override
	public Company findCompany(Company company)
			throws CompanyDoesNotExistException {
		return findCompanyById(company.getId());
	}

	@Override
	public Company findCompanyByEmail(String email)
			throws CompanyDoesNotExistException {
		

		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.email = '" + email
				+ "'");

		Company company = null;

		if (query.list().size() > 0) {
			company = (Company) query.list().get(0);
		} else {
			throw new CompanyDoesNotExistException("Company with email : "
					+ email + " does not exist");
		}
		return company;
	}

	@Override
	public void deleteCompany(long companyId)
			throws CompanyDoesNotExistException {
		Company company = (Company) findById(companyId);
		if (company == null) {
			throw new CompanyDoesNotExistException();
		}
		deleteCompany(company);
	}

	@Override
	public void deleteCompany(Company company)
			throws CompanyDoesNotExistException {
		try {
			delete(company);
		} catch (IllegalArgumentException e) {
			throw new CompanyDoesNotExistException();
		}

	}

	@Override
	public List<Company> getAllCompanies() {
		return findAll();
	}
	
//	
//	@Override
//	public void createDatabase(Long db_id, String dbName, String dbPassword,String host, String dbUsername)
//			throws JJCloudInstanceExistsException {
//		try{
//		databaseApi.createDatabase(dbName);
//		databaseApi.createDatabaseUser(host, dbPassword, dbUsername);
		
		/*Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("insert into jdbc:mysql://localhost:3306/"+dbName+".company_details(VERSION,address,database_username,database_password" +
				"database_host,database_name,description,email,firstlogin_date,firstline,company_enabled,lastlogin_date,location,name," +
				"productName,secondline,website) select * from jdbc:mysql://localhost:3306/jjteach_.company_details");
		query.executeUpdate();
		Query query3 = session.createQuery("insert into jdbc:mysql://localhost:3306/"+dbName+".authorities(username,authority" +
				") select * from jdbc:mysql://localhost:3306/jjteach_.authorities");
		query3.executeUpdate();
		Query query4 = session.createQuery("insert into jdbc:mysql://localhost:3306/"+dbName+".company_settings(VERSION,bg_color_code,header_color_code,logo_image" +
				"mentoring,id_company,header_links_color_code,navigation_hover_color,font_color_code,menu_color_code" +
				") select * from jdbc:mysql://localhost:3306/jjteach_.company_settings");
		query4.executeUpdate();
		Query query1 = session.createQuery("insert into jdbc:mysql://localhost:3306/"+dbName+".roles(VERSION,role_description,number,role_name" +
				") select * from jdbc:mysql://localhost:3306/jjteach_.roles");
		query1.executeUpdate();
		Query query2 = session.createQuery("insert into jdbc:mysql://localhost:3306/"+dbName+".user(VERSION,creationDate,email,enabled,first_name" +
				"last_name,password,superuser,username,id_location,id_company) select * from jdbc:mysql://localhost:3306/jjteach_.user");
		query2.executeUpdate();
		session.close();*/
		
//		}catch(DatabaseException dbe){
//		     dbe.printStackTrace();
//		}
//		
//	}

}
