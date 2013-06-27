/*
 * The MIT License
 *
 * Copyright 2013 Daniel Mwai <naistech.gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tunaweza.core.business.dao.module;
import com.tunaweza.core.business.dao.exceptions.level.LevelInuseException;
import com.tunaweza.core.business.dao.exceptions.module.ModuleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.module.ModuleExistsException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.module.Module;
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "moduleDao")
@Transactional
public class ModuleDaoImpl  extends GenericDaoImpl<Module> implements ModuleDao{

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.dao.module.ModuleDAO#addModule(com.jjpeople.jjteach.orm.entities.module.Module)
	 */
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public Module addModule(Module module) throws ModuleExistsException,LevelInuseException {
		Module module1=null;
		try {
			module1 = findModuleByName(module.getName());
		} catch (ModuleDoesNotExistException e) 
		{			
			//e.printStackTrace();
		}
		
		if(module1 != null){
			throw new ModuleExistsException();
		}

		Module savedModule = saveOrUpdate(module);
		logger.info("Saved Module Id" + savedModule.getId());
		return savedModule;
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.dao.module.ModuleDAO#deleteModule(long)
	 */
	@Override
	public void deleteModule(long moduleId) throws ModuleDoesNotExistException {
		Module module = (Module) findById(moduleId);
		if(module ==null){
			throw new ModuleDoesNotExistException();
		}
			deleteModule(module);
		
		
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.dao.module.ModuleDAO#deleteModule(com.jjpeople.jjteach.orm.entities.module.Module)
	 */
	@Override
	public void deleteModule(Module module) throws ModuleDoesNotExistException {
		try {
			delete(module);
		} catch (IllegalArgumentException e) {
			throw new ModuleDoesNotExistException();
		}
		
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.dao.module.ModuleDAO#findModule(com.jjpeople.jjteach.orm.entities.module.Module)
	 */
	@Override
	public Module findModule(Module module) throws ModuleDoesNotExistException {
		Module module1 = findById(module.getId());
		if(module1 == null){
			throw new ModuleDoesNotExistException();
		}
		return module1;
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.dao.module.ModuleDAO#findModuleById(long)
	 */
	@Override
	public Module findModuleById(long moduleId)
			throws ModuleDoesNotExistException {
		Module module = findById(moduleId);
		if(module == null){
			throw new ModuleDoesNotExistException();
		}
		
		return module;
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.dao.module.ModuleDAO#findModuleByName(java.lang.String)
	 */
	@Override
	public Module findModuleByName(String name)
			throws ModuleDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.name = '"
				+ name + "'");

		Module module = null;

		if (query.list().size() > 0) {
			module = (Module) query.list().get(0);
		} else {
			throw new ModuleDoesNotExistException("Module with name : "
					+ name + " does not exist");
		}

		return module;
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.dao.module.ModuleDAO#getAllModules()
	 */
	@Override
	public List<Module> getAllModules() {
		return findAll();
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.dao.module.ModuleDAO#saveModule(com.jjpeople.jjteach.orm.entities.module.Module)
	 */
	@Override
	public void saveModule(Module module){
		saveOrUpdate(module);		
	}

	@Override
	public int count() {
		Session session = (Session) getEntityManager().getDelegate();

		Query queryCount = session.createSQLQuery("SELECT COUNT(*) FROM module");

		java.math.BigInteger count = (java.math.BigInteger) queryCount.list()
				.get(0);
		return count.intValue();
	}

	@Override
	public List<Module> getPaginatedModulesList(int startIndex, int pageSize) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery(
				"SELECT * FROM module")
				.addEntity(Module.class);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		return (List<Module>) query.list();
	}

	
	// No Session methods
	
	@Override
	public Module findModuleByIdNoSession(long moduleId, String companyDbName)
			throws ModuleDoesNotExistException {
		
		Session session = (Session) getEntityManager().getDelegate();
		
		Query query = session.createSQLQuery("SELECT * FROM " + companyDbName + ".module"
				+ " WHERE id = " + moduleId).addEntity(Module.class);
		
		return query.list().size() > 0 ? (Module) query.list().get(0) : null;
		
	}
}
