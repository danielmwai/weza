/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.company.settings;

import com.tunaweza.core.business.dao.exceptions.company.settings.CompanySettingsDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.settings.CompanySettingsExistsException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.company.CompanySettings;
import java.util.List;
import static org.h2.util.IOUtils.delete;
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
@Repository(value="companySettingsDao")
@Transactional
public class CompanySettingsDaoImpl extends GenericDaoImpl <CompanySettings> 
       implements CompanySettingsDao{

	@Autowired
     SessionFactory sessionFactory;
	@Override
	public CompanySettings findCompanySettingsById(long cid) 
			throws CompanySettingsDoesNotExistException{
			
		
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName()
				+" i WHERE i.id = "+ cid);
		
		if(query.list().size()== 0){
			throw new CompanySettingsDoesNotExistException("Company with id = "
					+ cid + "doesn't exist");
		}
		return (CompanySettings) query.list().get(0);
	}
	
	@Override
	public CompanySettings findCompanySettingsByCompanyId(long companyid) 
			throws CompanySettingsDoesNotExistException{
			
		
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName()
				+" i WHERE i.id_company = "+ companyid);
		
		if(query.list().size()== 0){
			throw new CompanySettingsDoesNotExistException("Company with companyid = "
					+ companyid + "doesn't exist");
		}
		return (CompanySettings) query.list().get(0);
	}
	
	@Override
	public CompanySettings findCompanySettingsByName(String companyname)
	        throws CompanySettingsDoesNotExistException{
		
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName()
				+" i WHERE i.company_name = '"+ companyname +"'");
		
		if(query.list().size()== 0){
			throw new CompanySettingsDoesNotExistException("Company with name = "
					+ companyname + "doesn't exist");
		}
		return (CompanySettings) query.list().get(0);
	}
	
	@Override
	public CompanySettings findCompanySettings(CompanySettings companySettings) 
			throws CompanySettingsDoesNotExistException{
		CompanySettings companyS = findCompanySettingsById(companySettings.getId());
		if(companyS==null){
			throw new CompanySettingsDoesNotExistException();
		}
		return companyS;
	}
	
	@Override
	public CompanySettings addCompanySettings (CompanySettings companySettings) 
			throws CompanySettingsExistsException{
		try{
		CompanySettings companyS = saveOrUpdate(companySettings);
		return companyS;
		}
		catch(IllegalArgumentException ex){
			throw new CompanySettingsExistsException();
		}
		
		
	}
	
	@Override
	public void saveOrUpdateCompanySettings (CompanySettings companySettings){
		saveOrUpdate(companySettings);
	}
	
	@Override
	public void deleteCompanySettings (CompanySettings companySettings) 
			throws CompanySettingsDoesNotExistException{
		try{
			delete(companySettings);
		}
		catch(IllegalArgumentException ex){
			throw new CompanySettingsDoesNotExistException();
		}
	}
	
	@Override
	public void deleteCompanySetting (long cid) 
			throws CompanySettingsDoesNotExistException{
		CompanySettings companyS = findById(cid);
		if(companyS == null){
			throw new CompanySettingsDoesNotExistException();
		}
		deleteCompanySettings(companyS);
	}
	
	@Override
	public List<CompanySettings> getAllCompaniesSettings(){
		return findAll();
	}
	
	@Override
	public Integer getCount(){
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT COUNT(*) FROM company_settings");
		Integer count = query.list().size(); 
		return count;
	}
	
}
