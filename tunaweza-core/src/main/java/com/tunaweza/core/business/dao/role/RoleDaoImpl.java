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

package com.tunaweza.core.business.dao.role;

import com.tunaweza.core.business.Dao.exceptions.role.RoleDoesNotExistException;
import com.tunaweza.core.business.Dao.exceptions.role.RoleExistsException;
import com.tunaweza.core.business.Dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.Dao.user.UserDao;
import com.tunaweza.core.business.Dao.user.UserDaoImpl;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.user.User;
import java.util.List;
import org.apache.log4j.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "roleDao")
@Transactional
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {

	public Logger logger = Logger.getLogger(RoleDaoImpl.class);
	
	@Autowired
	private UserDao userDao;

	@Override
	public Role addRole(Role role) throws RoleExistsException {
		try {
			role.setNumber(getBiggestNumber()+1);
			if (role.getDescription() == null)
				role.setDescription("Description was not set.");
			saveOrUpdate(role);
			return role;
		} catch (    ConstraintViolationException | DataIntegrityViolationException e) {
			
			throw new RoleExistsException();
		}
	}

	public Role addRole(String rolename, String description)
			throws RoleExistsException {
		int id = 0;
		try {
			Role role = new Role();
			role.setRoleName(rolename);
			if (description == null)
				description = "Description was not set.";
			role.setDescription(description);
			
			role.setNumber(getBiggestNumber()+1);
			saveOrUpdate(role);
			return role;
		} catch (    ConstraintViolationException | DataIntegrityViolationException e) {
			
			throw new RoleExistsException();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Role> getUserRoles(long uid) throws UserDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM User i "
				+ "WHERE i.id = " + uid);

		if (query.list().size() == 0) {
			throw new UserDoesNotExistException("User with id " + uid
					+ " does not exist");
		}

		User user = (User) query.list().get(0);
		return user.getRoles();
	}

	@Override
	public List<Role> getUserRoles(String uname)
			throws UserDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM User i "
				+ "WHERE i.username = '" + uname +"'");

		if (query.list().size() == 0) {
			throw new UserDoesNotExistException("User with username " + uname
					+ " does not exist");
		}

		User user = (User) query.list().get(0);
		return user.getRoles();

	}

	
	@Override
	public RoleDao setUserRoles(long uid, List<Role> roles)
			throws UserDoesNotExistException, RoleDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM User i "
				+ "WHERE i.id = " + uid);

		if (query.list().size() == 0) {
			throw new UserDoesNotExistException("User with id " + uid
					+ " does not exist");
		}

		User user = (User) query.list().get(0);

		try{
		user.setRoles(roles);
		}catch(Exception e){
			throw new RoleDoesNotExistException();
		}
		UserDao userDao = new UserDaoImpl();

		userDao.saveOrUpdate(user);
		return this;
	}

	@Override
	public RoleDao setUserRoles(String uname, List<Role> roles)
			throws RoleDoesNotExistException, UserDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM User i "
				+ "WHERE i.username = '" + uname + "'");

		if (query.list().size() == 0) {
			throw new UserDoesNotExistException("User with username " + uname
					+ " does not exist");
		}

		User user = (User) query.list().get(0);

		try{
		user.setRoles(roles);
		}catch(Exception e){
			throw new RoleDoesNotExistException();
		}
		// UserDao userDao = new UserDaoImpl();

		userDao.saveOrUpdate(user);
		return this;
	}

	@Override
	public RoleDao removeRole(Role role) throws RoleDoesNotExistException {
		try {
			delete(role);
			
			return this;
		} catch (Exception e) {
			throw new RoleDoesNotExistException(e);
		}

	}

	@Override
	public RoleDao removeRole(int roleid) throws RoleDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery("SELECT * FROM roles r "
				+ "WHERE r.id = " + roleid);

		if (query.list().size() == 0) {
			throw new RoleDoesNotExistException("Role with id " + roleid
					+ " does not exist");
		}

		Role role = (Role) query.list().get(0);
		removeRole(role);
		return this;
	}

	@Override
	public RoleDao removeRole(String roleName) throws RoleDoesNotExistException {
		try {
			Role role = this.getRole(roleName);
			removeRole(role);
			
			return this;
		} catch (Exception e) {
			throw new RoleDoesNotExistException();
		}
	}

	@Override
	public Role getRole(int roleid) throws RoleDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.id = " + roleid);

		if (query.list().size() == 0) {
			throw new RoleDoesNotExistException("Role with id " + roleid
					+ " does not exist");
		}

		Role role = (Role) query.list().get(0);
		return role;
	}

	@Override
	public Role getRole(String rname) throws RoleDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
				.createQuery("SELECT i FROM " + getDomainClass().getName()
						+ " i WHERE i.roleName = '" + rname + "'");

		if (query.list().size() == 0) {
			throw new RoleDoesNotExistException("Role with name " + rname
					+ " does not exist");
		}

		Role role = (Role) query.list().get(0);
		return role;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoleNotInCloudLevel()
			throws RoleDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();
		String roleadmin = "ROLE_CLOUDADMIN";
		String rolecloud = "ROLE_SUPERCLOUDADMIN";
		
		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.roleName != '"
				+ roleadmin + "' and i.roleName != '"+ rolecloud + "'");

		if (query.list().size() == 0) {
			throw new RoleDoesNotExistException("Role with name " + rolecloud
					+ " and with name " + roleadmin
					+ " does not exist");
		}

		return query.list();
	}

	@Override
	public Role getRole(int roleid, boolean wired)
			throws RoleDoesNotExistException {

		if (!wired)
			return getRole(roleid);

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.id = " + roleid);

		if (query.list().size() == 0) {
			throw new RoleDoesNotExistException("Role with id " + roleid
					+ " does not exist");
		}

		Role role = (Role) query.list().get(0);
		return role;
	}

	@Override
	public List<Role> getallRoles() {
		return findAll();
	}

	@Override
	public int getBiggestNumber() {

		Session session = (Session) getEntityManager().getDelegate();
		
		Query queryCount = session.createSQLQuery("SELECT COUNT(*) FROM roles");
		Integer max = 0;
		java.math.BigInteger count = (java.math.BigInteger)queryCount.list().get(0);
		if(count.intValue() > 0)
		{
			Query query = session.createSQLQuery("SELECT max(number) FROM roles");
			max = (Integer) query.list().get(0);
		}
		return max.intValue();
	}
}
