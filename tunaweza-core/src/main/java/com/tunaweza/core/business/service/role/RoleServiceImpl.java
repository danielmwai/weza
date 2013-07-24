/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.role;

import com.tunaweza.core.business.dao.exceptions.role.RoleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.role.RoleExistsException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.dao.role.RoleDao;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.user.User;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService
{
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private RoleDao roleDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.RoleService#getAllRoles()
	 */
	public List<Role> getAllRoles()
	{
		return roleDao.getallRoles();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.RoleService#getTotalRoles()
	 */
	public int getTotalRoles()
	{
		return roleDao.getallRoles().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.RoleService#getRoleByName(java.lang
	 * .String)
	 */
	public Role getRoleByName(String roleName) throws RoleDoesNotExistException
	{
		return roleDao.getRole(roleName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.RoleService#getRoleById(int)
	 */
	public Role getRoleById(int roleId) throws RoleDoesNotExistException
	{
		return roleDao.getRole(roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.RoleService#getUsersByRole(java.lang
	 * .String)
	 */
	public List<User> getUsersByRole(String roleName)
			throws RoleDoesNotExistException
	{
		Role role = roleDao.getRole(roleName);

		return role.getUsers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.RoleService#getTotalUsersByRole(java
	 * .lang.String)
	 */
	public int getTotalUsersByRole(String roleName)
			throws RoleDoesNotExistException
	{
		Role role = roleDao.getRole(roleName);

		return role.getUsers().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.RoleService#addRole(
	 * java.lang.String, java.lang.String)
	 */
	/*
	public void addRole(String roleName, String description)
			throws RoleExistsException
	{
		roleDao.addRole(roleName, description);
	}
*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.RoleService#addRole(com.jjpeople.jjteach
	 * .orm.entities.user.Role)
	 */
	public void addRole(Role role) throws RoleExistsException
	{
		roleDao.addRole(role);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.RoleService#deleteRole(com.jjpeople
	 * .jjteach.orm.entities.user.Role)
	 */
	public void deleteRole(Role roleToDelete)
			throws RoleDoesNotExistException
	{
		roleDao.delete(roleToDelete);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.RoleService#updateRole(com.jjpeople
	 * .jjteach.orm.entities.user.Role)
	 */
	public void updateRole(Role role) throws RoleDoesNotExistException
	{
		roleDao.saveOrUpdate(role);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.RoleService#getUserRoles(java.lang.
	 * String)
	 */
	public List<Role> getUserRoles(String username)
			throws UserDoesNotExistException
	{
		return roleDao.getUserRoles(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.RoleService#setUserRoles(java.lang.
	 * String, java.util.List)
	 */
	public void setUserRoles(String username, List<Role> roles)
			throws UserDoesNotExistException, RoleDoesNotExistException
	{
		roleDao.setUserRoles(username, roles);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.RoleService#setUserRoles(long,
	 * java.util.List)
	 */
	public void setUserRoles(long userId, List<Role> roles)
			throws UserDoesNotExistException, RoleDoesNotExistException
	{
		roleDao.setUserRoles(userId, roles);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.RoleService#getRolesNotOnCloudLevel()
	 */
	public List<Role> getRolesNotOnCloudLevel()
			throws RoleDoesNotExistException
	{
		return roleDao.getRoleNotInCloudLevel();
	}
}
