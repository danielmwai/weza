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

package com.tunaweza.core.business.service.module;

import com.tunaweza.core.business.dao.exceptions.level.LevelInuseException;
import com.tunaweza.core.business.dao.exceptions.module.ModuleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.module.ModuleExistsException;
import com.tunaweza.core.business.dao.module.ModuleDao;
import com.tunaweza.core.business.model.module.Module;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Service("moduleService")
@Transactional
public class ModuleServiceImpl implements ModuleService{

	@Autowired
	ModuleDao moduleDAO;

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.web.service.module.ModuleService#addModule(com.jjpeople.jjteach.orm.entities.module.Module)
	 */
	@Override
	public Module addModule(Module module) throws ModuleExistsException, LevelInuseException {
		return moduleDAO.addModule(module);
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.web.service.module.ModuleService#deleteModule(long)
	 */
	@Override
	public void deleteModule(long moduleId) throws ModuleDoesNotExistException {
		moduleDAO.deleteModule(moduleId);
		
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.web.service.module.ModuleService#deleteModule(com.jjpeople.jjteach.orm.entities.module.Module)
	 */
	@Override
	public void deleteModule(Module module) throws ModuleDoesNotExistException {
		moduleDAO.deleteModule(module);
		
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.web.service.module.ModuleService#getAllModules()
	 */
	@Override
	public List<Module> getAllModules() {
		return moduleDAO.getAllModules();
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.web.service.module.ModuleService#getModuleById(long)
	 */
	@Override
	public Module getModuleById(long moduleId)
			throws ModuleDoesNotExistException {
		return moduleDAO.findModuleById(moduleId);
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.web.service.module.ModuleService#getModuleByName(java.lang.String)
	 */
	@Override
	public Module getModuleByName(String name)
			throws ModuleDoesNotExistException {
		return moduleDAO.findModuleByName(name);
	}

	/* (non-Javadoc)
	 * @see com.jjpeople.jjteach.web.service.module.ModuleService#saveModule(com.jjpeople.jjteach.orm.entities.module.Module)
	 */
	@Override
	public void saveModule(Module module) throws LevelInuseException {
		moduleDAO.saveModule(module);
		
	}

	@Override
	public boolean enableModule(String moduleId) throws ModuleDoesNotExistException
	{
		Module module = moduleDAO.findById(Long.valueOf(moduleId));

		if (module.getEnabled() == 1)
			return true;
		else
		{
			module.setEnabled(1);

			module = moduleDAO.saveOrUpdate(module);

			if (module.getEnabled() == 1)
				return true;
			else
				return false;
		}
	}

	@Override
	public boolean disableModule(String moduleId) throws ModuleDoesNotExistException{
		Module module = moduleDAO.findById(Long.valueOf(moduleId));

		if (module.getEnabled() == 0)
			return true;
		else
		{
			module.setEnabled(0);

			module = moduleDAO.saveOrUpdate(module);

			if (module.getEnabled() == 0)
				return true;
			else
				return false;
		}
		
	}

	@Override
	public List<Module> getPaginatedModules(int startIndex, int pageSize) {
		return moduleDAO.getPaginatedModulesList(startIndex, pageSize);
	}

	@Override
	public int countModules() {
		return moduleDAO.count();
	}
	
	
	
	//////////
	@Override
	public Module getModuleByIdNoSession(long moduleId, String companyDbName)
			throws ModuleDoesNotExistException {
		return moduleDAO.findModuleByIdNoSession(moduleId, companyDbName);
	}
}

