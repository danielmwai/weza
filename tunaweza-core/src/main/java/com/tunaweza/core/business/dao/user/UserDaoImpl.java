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

package com.tunaweza.core.business.dao.user;

import antlr.Utils;
import com.tunaweza.core.business.dao.exceptions.email.EmailExistsException;
import com.tunaweza.core.business.dao.exceptions.role.RoleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.role.RoleExistsException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserExistsException;

import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.persistence.PersistentEntity;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.model.user.UserType;
import com.tunaweza.core.business.settings.Settings;
import java.security.NoSuchAlgorithmException;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateCallback;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao,
		Settings {

	public Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public User findUserById(long uid) throws UserDoesNotExistException {
		User user = (User) findById(uid);
		if (user == null) {
			throw new UserDoesNotExistException("User with ID : " + uid
					+ " does not exist");
		}
		return user;
	}

	@Override
	public void addUserToGroup(Group group, User user)
			throws UserDoesNotExistException {
		// User user1 = user;
		// Group group1 = group;

		/*
		 * List <Group> groups = user.getGroups(); List <User> users =
		 * group.getUsers(); try{ groups.add(group); //users.add(user);
		 * }catch(NullPointerException ex){ groups.add(group);
		 * //users.add(user); }
		 * 
		 * logger.info("hshdhdhhehfjhfhfhjfjhfjhf==========================="+groups
		 * );
		 * logger.info("hshdhdhhehfjhfhfhjfjhfjhf==========================="+
		 * users); Session session = (Session) getEntityManager().getDelegate();
		 * Transaction transaction = session.beginTransaction(); //Group group1
		 * = new Group(); User user1= new User(); //group1.setUsers(users);
		 * user1.setGroups(groups);
		 * //logger.info("hshdhdhhehfjhfhfhjfjhfjhf==========================="
		 * +group1);
		 * logger.info("hshdhdhhehfjhfhfhjfjhfjhf==========================="
		 * +user1); //session.saveOrUpdate(user1); session.saveOrUpdate(user1);
		 * transaction.commit(); //session.close();
		 */}

	/*
	 * @Override public void saveUserConfirmationCode(long uid,String code)
	 * throws Exception{ Session ss =this.getSession(); try{
	 * UserWaitingAuthorisation u= new UserWaitingAuthorisation();
	 * u.setUserId(uid); u.setConfirmationKey(code); ss.save(u); ss.flush();
	 * }catch(Exception e){ Log.LOG(e); }finally{ ss.close(); } }
	 * 
	 * public UserWaitingAuthorisation findUserConfirmationCode(long uid) throws
	 * UserDoesNotExistException { Session ss =this.getSession(); try{
	 * UserWaitingAuthorisation u= ((UserWaitingAuthorisation)
	 * ss.get(UserWaitingAuthorisation.class, uid)); if(u == null) throw new
	 * UserDoesNotExistException(); // u.setSession(ss); return u;
	 * }catch(Exception e){
	 * 
	 * return null; }finally{ ss.close(); } }
	 */

	@Override
	public User findUser(User u) throws UserDoesNotExistException {
		return findUserById(u.getId());
	}

	@Override
	public User findUser(String username) throws UserDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.username = '"
				+ username + "'");

		User user = null;

		if (query.list().size() > 0) {
			user = (User) query.list().get(0);
		} else {
			throw new UserDoesNotExistException("User with username : "
					+ username + " does not exist");
		}

		return user;
	}
	
	
	@Override
	public User findUserById(String userId) throws UserDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.id = '"
				+ userId + "'");

		User user = null;

		if (query.list().size() > 0) {
			user = (User) query.list().get(0);
		} else {
			throw new UserDoesNotExistException("User with id : "
					+ userId + " does not exist");
		}

		return user;
	}

	public void jdbcUpdateUser(Connection connection, String fieldname,
			int value, String usrId) throws Exception {
		try {

			Statement statement = connection.createStatement();

			statement.executeUpdate("UPDATE " + User.TABLE_NAME + " SET "
					+ fieldname + " = " + value + " where id = " + usrId);
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public User findUserByEmail(String email) throws UserDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.email = '" + email
				+ "'");

		User user = null;

		if (query.list().size() > 0) {
			user = (User) query.list().get(0);
		} else {
			throw new UserDoesNotExistException("User with email : " + email
					+ " does not exist");
		}

		return user;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findUsersByRole(String roleName)
			throws UserDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
				.createQuery("SELECT i FROM Role i WHERE i.roleName = '"
						+ roleName + "'");

		if (query.list().size() == 0) {
			throw new UserDoesNotExistException("No user with Role " + roleName);
		}

		Role role = (Role) query.list().get(0);
		return role.getUsers();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findUsersByRoleCompanyId(String roleName, long companyId)
			throws UserDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery(
				"SELECT * FROM users u LEFT JOIN  authorities a ON u.username=a.username "
						+ "WHERE a.authority='" + roleName
						+ "' AND u.id_company = ? "
						+ "ORDER BY u.first_name, u.last_name ASC").addEntity(
				User.class);
		query.setLong(0, companyId);
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findUsersByRole(long roleid)
			throws UserDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM Role i WHERE i.id = "
				+ roleid);

		if (query.list().size() == 0) {
			throw new UserDoesNotExistException("No Role with id " + roleid);
		}

		Role role = (Role) query.list().get(0);
		return role.getUsers();

	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public List<User> findUsersByCompanyId(long companyid)
//			throws CompanyDoesNotExistException {
//		Session session = (Session) getEntityManager().getDelegate();
//
//		Query query = session
//				.createQuery("SELECT i FROM Company i WHERE i.id = "
//						+ companyid);
//
//		if (query.list().size() == 0) {
//			throw new CompanyDoesNotExistException("No Company with id "
//					+ companyid);
//		}
//
//		Company company = (Company) query.list().get(0);
//		return company.getUsers();
//
//	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public List<User> findUsersByCompanyName(String companyName)
//			throws CompanyDoesNotExistException {
//		Session session = (Session) getEntityManager().getDelegate();
//
//		Query query = session
//				.createQuery("SELECT i FROM Company i WHERE i.name = "
//						+ companyName);
//
//		if (query.list().size() == 0) {
//			throw new CompanyDoesNotExistException("No Company with name "
//					+ companyName);
//		}
//
//		Company company = (Company) query.list().get(0);
//		return company.getUsers();
//
//	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Role> getUserRoles(long uid) throws UserDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.id = " + uid);

		if (query.list().size() == 0) {
			throw new UserDoesNotExistException("User with uid " + uid
					+ " does not exist");
		}

		User user = (User) query.list().get(0);
		return user.getRoles();
	}
	
	@Override
	public String getUserRole(User user) {
		String role = "";
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createSQLQuery(
				"SELECT authority FROM authorities WHERE username='" + user.getEmail() + "'").addEntity(
				User.class);

		if (query.list().size() > 0) {
			role = (String) query.list().get(0);
		}
		return role;
	}

	@Override
	@SuppressWarnings("unchecked")
	public UserDao setUserRoles(User user, List<Role> roles) {
		user.setRoles(roles);
		saveOrUpdate(user);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findUsersByRole(Role role)
			throws UserDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM Role i "
				+ "WHERE i.number = " + role.getNumber());

		if (query.list().size() == 0) {
			throw new UserDoesNotExistException("User with roleName "
					+ role.getNumber() + " does not exist");
		}

		Role role1 = (Role) query.list().get(0);
		return role1.getUsers();
	}

	@Override
	public User addUser(User user) throws RoleDoesNotExistException,
			UserExistsException, EmailExistsException {
		User savedUser = null;
		try {
			if (user.getUsername() == null)
				// && user.getUsername().equals(SUPERUSER))
				throw new UserExistsException();
			savedUser = saveOrUpdate(user);
			return savedUser;
		} catch (ConstraintViolationException e) {
			throw new UserExistsException();
		} catch (DataIntegrityViolationException e) {
			BatchUpdateException ee = (BatchUpdateException) e.getRootCause();

			SQLException eee = ee.getNextException();

			String servmsg = eee.getLocalizedMessage();

			if (servmsg.contains("user_")) {
				throw new UserExistsException();
			} else
				throw new EmailExistsException();
		}
	}

	/*
	 * public void deleteAuthorisationConfirmation(UserWaitingAuthorisation u){
	 * this.getHibernateTemplate().delete(u); }
	 */
	@Override
	public void deleteUser(User user) throws UserDoesNotExistException {
		try {
			delete(user);
		} catch (IllegalArgumentException e) {
			throw new UserDoesNotExistException();
		}
	}

	@Override
	public void deleteUser(long uid) throws UserDoesNotExistException {
		User user = (User) findById(uid);
		if (user == null) {
			throw new UserDoesNotExistException();
		}
		deleteUser(user);
	}

	@Override
	public void addRole(String uname, String rname)
			throws UserDoesNotExistException, RoleDoesNotExistException,
			RoleExistsException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.username = '"
				+ uname + "'");
		User user = null;
		if (query.list().size() > 0) {
			user = (User) query.list().get(0);
		} else {
			throw new UserDoesNotExistException(" User with username " + uname
					+ " does not exist");
		}

		Query query1 = session.createSQLQuery(
				"SELECT * FROM roles r WHERE r.role_name = '" + rname + "'")
				.addEntity(Role.class);

		Role role = null;
		if (query1.list().size() > 0) {
			role = (Role) query1.list().get(0);
		} else {
			throw new RoleDoesNotExistException(" Role with rolename " + rname
					+ " does not exist");
		}

		if (user.getRoles().contains(role))
			throw new RoleExistsException();
		user.getRoles().add(role);

		saveOrUpdate(user);
	}

	@Override
	public void removeRole(String uname, String rname)
			throws UserDoesNotExistException, RoleDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.username = '"
				+ uname + "'");
		User user = null;
		if (query.list().size() > 0) {
			user = (User) query.list().get(0);
		} else {
			throw new UserDoesNotExistException(" User with username " + uname
					+ " does not exist");
		}

		Query query1 = session.createSQLQuery(
				"SELECT * FROM roles r WHERE r.role_name = '" + rname + "'")
				.addEntity(Role.class);

		Role role = null;
		if (query1.list().size() > 0) {
			role = (Role) query1.list().get(0);
		} else {
			throw new RoleDoesNotExistException(" Role with rolename " + rname
					+ " does not exist");
		}

		if (user.getRoles().contains(role)) {
			user.getRoles().remove(role);
		} else {
			throw new RoleDoesNotExistException("in user");
		}
		saveOrUpdate(user);

	}

	@Override
	public void changePassword(long uid, String newp)
			throws NoSuchAlgorithmException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery(
				"SELECT * FROM users u WHERE u.id = " + uid).addEntity(
				User.class);
		User user = null;

		user = (User) query.list().get(0);
		user.setPassword(Utils.generateMD5StringNumberNoMinuses(newp));

		logger.info("Name =" + user.getFirstName());
		logger.info("\nPassword =" + user.getPassword());
		logger.info("User ID = " + user.getId());
		saveOrUpdate(user);
	}

	@Override
	public List findUsersByCriteria(int criterium, long offsetStart,
			long offsetEnd, String regex, String sql) {
		// Session s = this.getSession();
		// @TODO should be switch - we will be back here very soon
		if (criterium == CRITERIUM_OFFSETS) {
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public List<User> getUserByMentor(Mentor mentor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUserByUserType(UserType userType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUserByEnabled(boolean enabled) {
		int userenabled = 0;
		if (enabled) {
			userenabled = 1;
		}
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery(
				"SELECT * FROM users u WHERE u.enabled = " + userenabled)
				.addEntity(User.class);

		return query.list();
	}

	/**
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getEnabledUserByCompanyId(boolean enabled, long companyid) {
		int userenabled = 0;
		if (enabled) {
			userenabled = 1;
		}
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery(
				"SELECT * FROM users u WHERE u.enabled = " + userenabled
						+ " AND u.id_company = " + companyid).addEntity(
				User.class);

		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUsers(String firstName, String lastName, long companyId) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
				.createSQLQuery(
						"SELECT * FROM users u "
								+ "WHERE u.first_name LIKE '%"
								+ firstName
								+ "%' AND u.last_name LIKE '%"
								+ lastName
								+ "%' AND u.id_company = ? ORDER BY u.first_name, u.last_name ASC")
				.addEntity(User.class);
		query.setLong(0, companyId);
		return query.list();
	}

	@Override
	public List<User> getUsers(String firstName, String lastName, String role,
			long companyId) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
				.createSQLQuery(
						"SELECT * FROM users u LEFT JOIN  authorities a ON u.username=a.username "
								+ "WHERE a.authority='"
								+ role
								+ "'"
								+ " AND u.first_name LIKE '%"
								+ firstName
								+ "%' AND u.last_name LIKE '%"
								+ lastName
								+ "%' AND u.id_company = ? ORDER BY u.first_name, u.last_name ASC")
				.addEntity(User.class);
		query.setLong(0, companyId);
		return query.list();

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUsersByFirstName(String firstName, long companyId) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
				.createSQLQuery(
						"SELECT * FROM users u "
								+ "WHERE u.first_name LIKE '%"
								+ firstName
								+ "%' AND u.id_company = ? ORDER BY u.first_name, u.last_name ASC")
				.addEntity(User.class);
		query.setLong(0, companyId);
		return query.list();
	}

	@Override
	@SuppressWarnings({ "unchecked", "null" })
	public List<User> getUsersByFirstNameAndRole(String firstName, String role,
			long companyId) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
				.createSQLQuery(
						"SELECT * FROM users u LEFT JOIN  authorities a ON u.username=a.username "
								+ "WHERE a.authority='"
								+ role
								+ "'"
								+ " AND u.first_name LIKE '%"
								+ firstName
								+ "%' AND u.id_company = ? ORDER BY u.first_name, u.last_name ASC")
				.addEntity(User.class);
		query.setLong(0, companyId);
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUsersByLastName(String lastName, long companyId) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
				.createSQLQuery(
						"SELECT * FROM users u "
								+ "WHERE u.last_name LIKE '%"
								+ lastName
								+ "%' AND u.id_company = ? ORDER BY u.first_name, u.last_name ASC")
				.addEntity(User.class);
		query.setLong(0, companyId);
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUsersByLastNameAndRole(String lastName, String role,
			long companyId) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
				.createSQLQuery(
						"SELECT * FROM users u LEFT JOIN  authorities a ON u.username=a.username "
								+ "WHERE a.authority='"
								+ role
								+ "'"
								+ " AND u.last_name LIKE '%"
								+ lastName
								+ "%' AND u.id_company = ? ORDER BY u.first_name, u.last_name ASC")
				.addEntity(User.class);
		query.setLong(0, companyId);
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getPaginatedUsersList(int startIndex, int pageSize) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery(
				"SELECT * FROM users u ORDER BY u.id DESC").addEntity(
				User.class);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		return (List<User>) query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getPaginatedUsersList(int startIndex, int pageSize,
			String role) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery(
				"SELECT * FROM users u LEFT JOIN  authorities a ON u.username="
						+ "a.username WHERE a.authority=? ORDER BY u.id DESC")
				.addEntity(User.class);
		query.setString(0, role);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		return (List<User>) query.list();
	}

	@Override
	public List<User> getAllStudents() {

		Session session = (Session) getEntityManager().getDelegate();
		Query query = session
				.createSQLQuery(
						"SELECT * FROM users u LEFT JOIN  authorities a ON u.username=a.username WHERE a.authority='ROLE_STUDENT'")
				.addEntity(User.class);
		return query.list();

	}

	@Override
	public List<User> getActiveStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
 * 
 */
	@Override
	public void saveUser(User user) {
		saveOrUpdate(user);

	}

	@Override
	public String findUserAuthority(String username) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery("SELECT a.authority "
				+ "FROM authorities a WHERE a.username = '" + username + "'");

		String authority = (String) query.list().get(0);

		return authority;
	}

	@Override
	public int count() {
		Session session = (Session) getEntityManager().getDelegate();

		Query queryCount = session.createSQLQuery("SELECT COUNT(*) FROM users");

		java.math.BigInteger count = (java.math.BigInteger) queryCount.list()
				.get(0);
		return count.intValue();
	}

	@Override
	public int count(String role) {
		Session session = (Session) getEntityManager().getDelegate();

		Query queryCount = session
				.createSQLQuery("SELECT COUNT(*) FROM users u LEFT JOIN authorities "
						+ "a ON u.username=a.username WHERE a.authority='"
						+ role + "'");

		java.math.BigInteger count = (java.math.BigInteger) queryCount.list()
				.get(0);
		return count.intValue();
	}

	@Override
	public int getNewRegistrationNumber() {
		int count = 0;
		Session session = (Session) getEntityManager().getDelegate();
		Query queryCount = session
				.createSQLQuery("SELECT MAX(registration_no) FROM student");
		if (queryCount.list().get(0) != null) {
			/*
			 * int studentNo=1; Query defaultReg = queryCount.setInteger(0,
			 * studentNo); count = (Integer) defaultReg.list().get(0);
			 */
			count = (Integer) queryCount.list().get(0);
		}
		return count + 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll(String role) {
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session
				.createSQLQuery(
						"SELECT * FROM users u LEFT JOIN  authorities a ON u.username="
								+ "a.username WHERE a.authority = ? ORDER BY u.id DESC")
				.addEntity(User.class);
		query.setString(0, role);
		return (List<User>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersByRole_CompanyId(String role, long company_id) {
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createSQLQuery(
				"SELECT * FROM users u LEFT JOIN  authorities a "
						+ "ON u.username=a.username WHERE a.authority = ? "
						+ "AND u.id_company = ? ORDER BY u.id DESC").addEntity(
				User.class);
		query.setString(0, role);
		query.setLong(1, company_id);
		return (List<User>) query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getPaginatedUsersByRole_CompanyId(int startIndex,
			int pageSize, String role, long company_id) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery(
				"SELECT * FROM users u LEFT JOIN  authorities a "
						+ "ON u.username=a.username WHERE a.authority = ? "
						+ "AND u.id_company = ? ORDER BY u.id DESC").addEntity(
				User.class);
		query.setString(0, role);
		query.setLong(1, company_id);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		return (List<User>) query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getPaginatedUsersByRole(int startIndex, int pageSize,
			String role) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery(
				"SELECT * FROM users u LEFT JOIN  authorities a "
						+ "ON u.username=a.username WHERE a.authority = ? "
						+ " ORDER BY u.id DESC").addEntity(User.class);
		query.setString(0, role);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		return (List<User>) query.list();
	}

	public void deletePreviousUsername(String oldUsername, String role) {
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createSQLQuery(
				"DELETE FROM authorities WHERE username = ? AND authority = ?")
				.addEntity(User.class);
		query.setString(0, oldUsername);
		query.setString(1, role);
		query.executeUpdate();
	}

	@Override
	public User getSuperUser(String username, String firstName, String lastName) {
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createSQLQuery(
				"SELECT * FROM users u WHERE u.username=? "
						+ " AND u.first_name=? AND u.last_name=?").addEntity(
				User.class);
		query.setString(0, username);
		query.setString(1, firstName);
		query.setString(2, lastName);

		User user = null;
		if (query.list().size() > 0) {
			user = (User) query.list().get(0);
		}
		return user;
	}

	public List<User> findUsersByLocation(long locationId)
			throws UserDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.location = '"
				+ locationId + "'");

		return (List<User>) query.list();
	}
	
	public void print(){
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>In the user Dao impl");
	}

}
