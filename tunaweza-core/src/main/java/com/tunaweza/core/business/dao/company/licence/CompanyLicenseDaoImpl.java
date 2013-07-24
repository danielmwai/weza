/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.company.licence;

import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.license.CompanyLicenseDoesNotExistException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.licence.CompanyLicense;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */

@Repository(value = "companyLicenseDao")
@Transactional
public class CompanyLicenseDaoImpl extends GenericDaoImpl<CompanyLicense> implements CompanyLicenseDao{

	@Autowired
     SessionFactory sessionFactory;

	public Logger logger = Logger.getLogger(CompanyLicenseDaoImpl.class);

	@Override
	public CompanyLicense findLicenseById(long id) throws CompanyLicenseDoesNotExistException {
		CompanyLicense license = findById(id);
		if (license == null) {
			throw new CompanyLicenseDoesNotExistException("User with ID : " + id
					+ " does not exist");
		}
		return license;
	}


	@Override
	public CompanyLicense findLicense(CompanyLicense license) throws CompanyLicenseDoesNotExistException {
		return findLicenseById(license.getId());
	}



	@Override
	@SuppressWarnings("unchecked")
	public CompanyLicense findLicenseByCompanyId(long companyid)
			throws CompanyDoesNotExistException {
		
		Query query = sessionFactory.getCurrentSession()
				.createQuery("SELECT i FROM Company i WHERE i.id = "
						+ companyid);

		if (query.list().size() == 0) {
			throw new CompanyDoesNotExistException("No Company with id "
					+ companyid);
		}

		Company company = (Company) query.list().get(0);
		return company.getLicense();

	}

	

	
	

	/*
	 * public void deleteAuthorisationConfirmation(UserWaitingAuthorisation u){
	 * this.getHibernateTemplate().delete(u); }
	 */
	@Override
	public void deleteLicense(CompanyLicense license) throws CompanyLicenseDoesNotExistException {
		try {
			delete(license);
		} catch (IllegalArgumentException e) {
			throw new CompanyLicenseDoesNotExistException();
		}
	}

	@Override
	public void deleteLicense(long id) throws CompanyLicenseDoesNotExistException {
		CompanyLicense license = (CompanyLicense) findById(id);
		if (license == null) {
			throw new CompanyLicenseDoesNotExistException();
		}
		deleteLicense(license);
	}

	


	@Override
	public void saveLicense(CompanyLicense license) {
		saveOrUpdate(license);

	}

	

	@Override
	public int count() {
		

		Query queryCount = sessionFactory.getCurrentSession().createSQLQuery("SELECT COUNT(*) FROM company_license");

		java.math.BigInteger count = (java.math.BigInteger) queryCount.list()
				.get(0);
		return count.intValue();
	}

	
}
