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

package com.tunaweza.core.business.service.user;


import com.tunaweza.core.business.dao.exceptions.email.EmailExistsException;
import com.tunaweza.core.business.dao.exceptions.role.RoleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserExistsException;
import com.tunaweza.core.business.dao.group.GroupDao;
import com.tunaweza.core.business.dao.user.UserDao;
import com.tunaweza.core.business.model.course.EmbeddableCourse;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.course.CourseService;
import com.tunaweza.core.business.service.dbswitcher.DbSwitcherHelper;
import com.tunaweza.core.business.service.mail.MailService;
import com.tunaweza.core.business.service.student.StudentService;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GroupDao groupDao;

	@Autowired
	private MailService mailService;

	@Autowired
	StudentService studentService;

	@Autowired
	CourseService courseTemplateService;
	
	@Autowired
	private DbSwitcherHelper dbSwitcherHelper;
	
//	@Autowired
//	private CompanyService companyService;

	private Random rgen = new Random();
	private byte decision, numValue;
	private char charValue;

	// private SimpleMailSender simpleMailSender = new SimpleMailSenderImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#addUser(long,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addUser(long id, String firstName, String lastName,
			String email, String password, String modStartDate,
			String locationId, String currentModId) throws UserExistsException {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#addUser(com.jjpeople
	 * .jjteach.orm.entities.user.User)
	 */
        @Override
	public User addUser(User user) throws UserExistsException,
			EmailExistsException, RoleDoesNotExistException {
		return userDao.addUser(user);
	}
	/**
	 * 
	 */
	
	@Override
	public void saveUser(User user){
		userDao.saveUser(user);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#updateUser(long,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
        @Override
	public User updateUser(long id, String firstName, String lastName,
			String email, String password, String modStartDate,
			String locationId, String currentModId)
			throws UserDoesNotExistException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#updateUser(com.jjpeople
	 * . jjteach.orm.entities.user.User)
	 */
        @Override
	public User updateUser(User user) throws UserDoesNotExistException {
		return userDao.saveOrUpdate(user);
	}
	
	@Override
	public void addUserToGroup(Group group, User user)
			throws UserDoesNotExistException {
		userDao.addUserToGroup(group, user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#enableUser(java.lang
	 * .String)
	 */
        @Override
	public boolean enableUser(String userId) throws UserDoesNotExistException {
		long uId = -1;

		try {
			uId = Long.valueOf(userId);
		} catch (NumberFormatException nfe) {
			throw new UserDoesNotExistException("User does not exist.");
		}

		User user = userDao.findById(uId);

		if (user.getEnabled() == 1)
			return true;
		else {
			user.setEnabled(1);

			user = userDao.saveOrUpdate(user);

			if (user.getEnabled() == 1)
				return true;
			else
				return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#disableUser(java.
	 * lang.String )
	 */
        @Override
	public boolean disableUser(String userId) throws UserDoesNotExistException {
		long uId = -1;

		try {
			uId = Long.valueOf(userId);
		} catch (NumberFormatException nfe) {
			throw new UserDoesNotExistException("User does not exist.");
		}

		User user = userDao.findById(uId);

		if (user.getEnabled() == 0)
			return true;
		else {
			user.setEnabled(0);

			user = userDao.saveOrUpdate(user);

			if (user.getEnabled() == 0)
				return true;
			else
				return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUserByUsername
	 * (java.lang .String)
	 */
        @Override
	public User getUserByUsername(String username)
			throws UserDoesNotExistException {
		return userDao.findUser(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUserByEmail(java
	 * .lang .String)
	 */
        @Override
	public User getUserByEmail(String email) throws UserDoesNotExistException {
		User user = userDao.findUserByEmail(email);

		if (user == null)
			throw new UserDoesNotExistException(
					"User with given email does not exist");

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUserById(java.
	 * lang.String )
	 */
	public User getUserById(long userId) throws UserDoesNotExistException {
		User user = userDao.findById(userId);

		if (user == null)
			throw new UserDoesNotExistException("User does not exist.");
		else
			return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUserByRegNo(java
	 * .lang .String)
	 */
	public User getUserByRegNo(String regNo) throws UserDoesNotExistException {
		List<User> userList = userDao.findAll();

		Iterator<User> iterator = userList.iterator();

		User user = null;

		while (iterator.hasNext()) {
			user = iterator.next();

			if (user.getStudent().getRegNo() == Integer.valueOf(regNo))
				break;

			user = null;
		}

		if (user == null)
			throw new UserDoesNotExistException(
					"User with given reg no. does not exist.");
		else
			return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUserByPassword
	 * (java.lang .String)
	 */
	public List<User> getUserByPassword(String password) {
		List<User> userList = userDao.findAll();

		List<User> userReturnList = new ArrayList<User>();

		Iterator<User> iterator = userList.iterator();

		while (iterator.hasNext()) {
			User user = iterator.next();

			if (user.getPassword().equals(password))
				userReturnList.add(user);
		}

		return userReturnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUsersByFirstName
	 * (java .lang.String)
	 */
	public List<User> getUsersByFirstName(String firstName, long companyId) {
		return userDao.getUsersByFirstName(firstName, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.UserService#getUsersByFirstNameAndRole
	 * (java .lang.String)
	 */
	public List<User> getUsersByFirstNameAndRole(String firstName, String role, long companyId) {
		return userDao.getUsersByFirstNameAndRole(firstName, role, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUsersByLastName
	 * (java. lang.String)
	 */
	public List<User> getUsersByLastName(String lastName, long companyId) {
		return userDao.getUsersByLastName(lastName, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.UserService#getUsersByLastNameAndRole
	 * (java. lang.String)
	 */
	public List<User> getUsersByLastNameAndRole(String lastName, String role, long companyId) {
		return userDao.getUsersByLastNameAndRole(lastName, role, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUsersByName(java
	 * .lang .String, java.lang.String)
	 */
	public List<User> getUsersByName(String firstName, String lastName, long companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUsersByLocation
	 * (java. lang.String)
	 */
	public List<User> getUsersByLocation(long locationId) throws UserDoesNotExistException {
		return userDao.findUsersByLocation(locationId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUsersByRole(java
	 * .lang .String)
	 */
	public List<User> getUsersByRole(String role)
			throws UserDoesNotExistException {
		return userDao.findUsersByRole(role);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUsersByRole(java
	 * .lang .String)
	 */
	public List<User> getUsersByRoleCompanyId(String role, long companyId)
			throws UserDoesNotExistException {
		return userDao.findUsersByRoleCompanyId(role, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUsersByModule(
	 * java.lang .Long)
	 */
	public List<User> getUsersByModule(Long moduleId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getAllUsers()
	 */
	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getAllUsers(String
	 * role)
	 */
	public List<User> getAllUsers(String role) {
		return userDao.findAll(role);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService
	 * #getUsersByRole_CompanyId(String role, long company_id)
	 */
	public List<User> getUsersByRole_CompanyId(String role, long company_id){
		return userDao.findUsersByRole_CompanyId(role, company_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#countUser()
	 */
	public int countUsers() {
		return userDao.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#countUser(String role)
	 */
	public int countUsers(String role) {
		return userDao.count(role);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getTotalUsers()
	 */
	public int getTotalUsers() {
		return userDao.findAll().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getAllStudents()
	 */
	public List<User> getAllStudents() {
		return userDao.getAllStudents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#
	 * getUserExcerciseByUsername (java.lang.String)
	 */
	public List<StudentExercise> getUserExcerciseByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUserExcerciseByEmail
	 * ( java.lang.String)
	 */
	public List<StudentExercise> getUserExcerciseByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUserExerciseByRegNo
	 * (java .lang.String)
	 */
	public List<StudentExercise> getUserExerciseByRegNo(String regNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getEnabledUsers()
	 */
	public List<User> getEnabledUsers() {
		return userDao.getUserByEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getDisabledUsers()
	 */
	public List<User> getDisabledUsers() {
		return userDao.getUserByEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUsersByLastLogin
	 * (java .util.Date)
	 */
	public List<User> getUsersByLastLogin(Date lastLoginDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUsersByCreationDate
	 * (java .util.Date, java.util.Date)
	 */
	public List<User> getUsersByCreationDate(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#isSuperuser(java.
	 * lang.String )
	 */
	public boolean isSuperuser(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#isStudent(java.lang
	 * .String)
	 */
	public boolean isStudent(String username) throws UserDoesNotExistException {
		try {
			User user = userDao.findUser(username);

			return isStudent(user);

		} catch (Exception e) {

		}

		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#isStudent(com.jjpeople
	 * .jjteach .orm.entities.user.User)
	 */
	public boolean isStudent(User user) throws UserDoesNotExistException {

		try {

			userDao.findUser(user); // Verify user exists in the system.
			
			if(userDao.findUserAuthority(user.getUsername()).equals("ROLE_STUDENT")) {
				return true;
			}
		} catch (Exception e) {

		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#isEnabled(java.lang
	 * .String)
	 */
	public boolean isEnabled(String username) throws UserDoesNotExistException {
		User user = userDao.findUser(username);

		return user.getEnabled() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getCurrentModuleId
	 * (java. lang.String)
	 */
	public Long getCurrentModuleId(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getModuleStartDate
	 * (java. lang.String)
	 */
	public Date getModuleStartDate(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#searchUsers(java.
	 * lang.String , java.lang.String, java.lang.String)
	 */
	public List<User> searchUsers(String firstName, String lastName, long companyId) {
		return userDao.getUsers(firstName, lastName, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#searchUsers(java.
	 * lang.String , java.lang.String, java.lang.String)
	 */
	public List<User> searchUsers(String firstName, String lastName, String role, long companyId) {
		return userDao.getUsers(firstName, lastName, role, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.UserService#setUserModuleAndStartDate
	 * (long, java.lang.String, java.util.Date)
	 */
	public User setUserModuleAndStartDate(long userId, String currentModule,
			Date moduleStartDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getPaginatedStudents
	 * (int, int)
	 */
	public List<User> getPaginatedUsers(int startIndex, int pageSize) {
		return userDao.getPaginatedUsersList(startIndex, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getPaginatedStudents
	 * (int, int, String)
	 */
	public List<User> getPaginatedUsers(int startIndex, int pageSize,
			String role) {
		return userDao.getPaginatedUsersList(startIndex, pageSize, role);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService
	 * #getPaginatedUsersByRole_CompanyId(int, int, String, long)
	 */
	public List<User> getPaginatedUsersByRole_CompanyId(int startIndex, int pageSize,
			String role, long company_id){
		return userDao.getPaginatedUsersByRole_CompanyId(startIndex, pageSize, role, company_id);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService
	 * #getPaginatedUsersByRole(int, int, String)
	 */
	public List<User> getPaginatedUsersByRole(int startIndex, int pageSize,
			String role){
		return userDao.getPaginatedUsersByRole(startIndex, pageSize, role);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#
	 * mailStudentsNotLoggedLastFiveDays()
	 */
	public void mailStudentsNotLoggedLastFiveDays() {
		// TODO Auto-generated method stub

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUsersByCompanyId
	 * (companyId)
	 */
	
//	public List<User> getUsersByCompanyId(long companyId) throws CompanyDoesNotExistException {
//		return userDao.findUsersByCompanyId(companyId);
//	}
//	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#getUsersByCompanyName
	 * (companyName)
	 */
//	
//	public List<User> getUsersByCompanyName(String companyName) throws CompanyDoesNotExistException {
//		return userDao.findUsersByCompanyName(companyName);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#
	 * isEnabledAndHasCourseTemplate
	 * (com.jjpeople.jjteach.orm.entities.user.User)
	 */
	public boolean isEnabledAndHasCourse(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.UserService#logInUser(java.lang
	 * .String, java.lang.String)
	 */
	public User logInUser(String username, String password)
			throws UserDoesNotExistException {
		User user = userDao.findUser(username);

		String userPassword = user.getPassword();

		if (userPassword.equals(password))
			return user;
		else
			throw new UserDoesNotExistException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.user.UserService#getAutoPass(java.lang
	 * .String)
	 */
	public void getAutoPass(User user, String newPassword)
			throws UserDoesNotExistException, Exception {

		mailService.sendMail(user, newPassword);

	}

	@Override
	public void sendMail(User user, String password)
			throws UserDoesNotExistException, Exception {
		mailService.sendMail(user, password);

	}

	@Override
	public String getUserAuthority(String username) {
		return userDao.findUserAuthority(username);
	}

	@Override
	public String generatePassword() {
		int length = 8;
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length) {
			decision = (byte) rgen.nextInt(2);
			numValue = (byte) rgen.nextInt(10);
			charValue = (char) (rgen.nextInt(25) + 65);
			sb.append((decision % 2 == 0) ? (charValue + "") : (numValue + ""));
		}
		return sb.toString();
	}

	@Override
	public void enableDisableUser(String userId)
			throws UserDoesNotExistException, Exception {
		long uId = Long.valueOf(userId);
		User user = userDao.findById(uId);

		if (user.getEnabled() == 1)
			user.setEnabled(0);
		else
			user.setEnabled(1);

	userDao.saveOrUpdate(user);
		/*int enabled = 0;
		Connection con = dbSwitcherHelper.dbSwitcher("jjteach_", "jjteach_", "jjteach_");
		try{
		Statement st = con.createStatement();
		
		if (user.getEnabled() == 1)
			enabled =0;
		else
			enabled =1;
			
		st.executeUpdate("update users set enabled = "+enabled+" where id = "+userId);
			} catch (SQLException e) {
				e.printStackTrace();

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}*/
	}
	
	public void enableDisableCloudUser(String userId)throws UserDoesNotExistException, Exception {
		long uId = Long.valueOf(userId);
		User user = userDao.findById(uId);

		
		int enabled = 0;
		Connection con = dbSwitcherHelper.dbSwitcher("jjteach_", "jjteach_", "jjteach_");
		
		
		if (user.getEnabled() == 1){
			enabled =0;
		}
		else{
			enabled =1;
		}
		userDao.jdbcUpdateUser(con,"enabled",enabled, userId);
		
			
		
	}
	
	public void enableDisable(String userId)throws UserDoesNotExistException, Exception {
		long uId = Long.valueOf(userId);
		User user = userDao.findById(uId);
//		Company instanceUserCompany = companyService.findCompanyById(user.getUserCompany().getId());
//		
//		int enabled = 0;
//		Connection con = dbSwitcherHelper.dbSwitcher(instanceUserCompany.getDbaseName(), 
//				instanceUserCompany.getDbUserName(), instanceUserCompany.getDbPassword());
//		
//		
//		if (user.getEnabled() == 1){
//			enabled =0;
//		}
//		else{
//			enabled =1;
//		}
//		userDao.jdbcUpdateUser(con,"enabled",enabled, userId);
//		
			
		
	}
	

	@Override
	public List<EmbeddableCourse> getUserCourse(long userId) {
		User user = userDao.findById(userId);
		List<EmbeddableCourse> courseList = user.getStudent()
				.getCourseList();
		return courseList;
	}

	
	public User setUserCourse(long userId, List<EmbeddableCourse> courseList) {
		User user = userDao.findById(userId);
		
		System.out.print(user.getStudent());
		//courseTemplate = courseTemplateService.findCourseTemplateById(coursetemplateId);	
		Student student = null;
		try{
		student = studentService.getStudentByUser(user);
		student.setCourseList(courseList);
		}catch(StudentDoesNotExistException se){
			se.printStackTrace();
		}
	
		user = userDao.saveOrUpdate(user);
		return user;
	}
	


	@Override
	public int getNextRegistrationNumber() {
		return userDao.getNewRegistrationNumber();
	}

	@Override
	public void deletePreviousUsername(String oldUsername, String role) {
		userDao.deletePreviousUsername(oldUsername, role);
	}
	
	@Override
	public User getSuperUser(String username, String firstName, String lastName){
		return userDao.getSuperUser(username, firstName, lastName);
	}
}
