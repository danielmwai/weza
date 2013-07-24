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
import com.tunaweza.core.business.dao.exceptions.service.ServiceException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserExistsException;
import com.tunaweza.core.business.model.course.EmbeddableCourse;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.user.User;
import java.util.Date;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface UserService {

	/**
	 * Logs in a user
	 * 
	 * @param username
	 *            The user name
	 * @param password
	 *            The password
	 * @return <code>User</code> with the given user name and password.
	 * @throws UserDoesNotExistException
	 *             Thrown if a <code>User</code> with the given user name and
	 *             password does not exist.
	 */
	public User logInUser(String username, String password)
			throws UserDoesNotExistException;

	/**
	 * Adds a user in the system
	 * 
	 * @param id
	 *            The user id.
	 * @param firstName
	 *            The first name.
	 * @param lastName
	 *            The last name.
	 * @param email
	 *            The email address.
	 * @param password
	 *            The password.
	 * @param modStartDate
	 *            The start date of a module.
	 * @param locationId
	 *            The location id of the user.
	 * @param currentModId
	 *            The current module id.
	 * @throws UserExistsException
	 *             Thrown if a <code>User</code> with the same id already
	 *             exists.
	 */
	public void addUser(long id, String firstName, String lastName,
			String email, String password, String modStartDate,
			String locationId, String currentModId) throws UserExistsException;

	/**
	 * Adds a user in the system.
	 * 
	 * @param user
	 *            The <code>User</code> to add.
	 * @return 
	 * @throws UserExistsException
	 *             Thrown if a similar <code>User</code> already exists i.e. has
	 *             either the same user name, id or registration number.
	 * @throws EmailExistsException
	 *             Thrown if the <code>User</code> has an email that already
	 *             exists in the system.
	 * @throws RoleDoesNotExistException
	 *             Thrown if the <code>User</code> has a <code>Role</code> that
	 *             does not exist.
	 */
	public User addUser(User user) throws UserExistsException,
			EmailExistsException, RoleDoesNotExistException;
	
	/**
	 * 
	 * @param userId
	 * @throws UserDoesNotExistException
	 * @throws Exception
	 */
	
	public void enableDisableCloudUser(String userId)throws UserDoesNotExistException, Exception;
	
	public void enableDisable(String userId)throws UserDoesNotExistException, Exception;
	/**
	 * Updates a <code>User</code>.
	 * 
	 * @param id
	 *            The id of the <code>User</code>
	 * @param firstName
	 *            The first name.
	 * @param lastName
	 *            The last name.
	 * @param email
	 *            The email.
	 * @param password
	 *            The password.
	 * @param modStartDate
	 *            The module start date.
	 * @param locationId
	 *            The <code>Location</code> id
	 * @param currentModId
	 *            The current module id.
	 * @return The updated <code>User</code>.
	 * @throws UserDoesNotExistException
	 *             Thrown if a similar <code>User</code> already exists i.e. has
	 *             either the same user name, id or registration number.
	 */
	public User updateUser(long id, String firstName, String lastName,
			String email, String password, String modStartDate,
			String locationId, String currentModId)
			throws UserDoesNotExistException;

	/**
	 * Updates a <code>User</code>.
	 * 
	 * @param user
	 *            The <code>User</code> to update
	 * @return The updated <code>User</code>.
	 * @throws UserDoesNotExistException
	 *             Thrown if the <code>User</code> to update does not already
	 *             exist in the system.
	 */
	public User updateUser(User user) throws UserDoesNotExistException;

	/**
	 * Enables a user.
	 * 
	 * @param userId
	 *            The user id of the <code>User</code> to enable.
	 * @return <code>true</code> if the <code>User</code> has been enabled or
	 *         was already enabled or <code>false</code> if the user could not
	 *         be enabled.
	 * @throws UserDoesNotExistException
	 *             Thrown if a <code>User</code> with the given id does not
	 *             exist.
	 */
	public boolean enableUser(String userId) throws UserDoesNotExistException;

	/**
	 * 
	 * @param userId
	 *            The user id of the <code>User</code> to disable.
	 * @return <code>true</code> if the <code>User</code> has been disabled or
	 *         was already disabled or <code>false</code> if the user could not
	 *         be disabled.
	 * @throws UserDoesNotExistException
	 *             Thrown if a <code>User</code> with the given id does not
	 *             exist.
	 */
	public boolean disableUser(String userId) throws UserDoesNotExistException;

	/**
	 * Gets a <code>User</code> with the given user name.
	 * 
	 * @param username
	 *            The user name.
	 * @return <code>User</code> with the given user name.
	 * @throws UserDoesNotExistException
	 *             Thrown if a <code>User</code> with the given user name does
	 *             not exist.
	 */
	public User getUserByUsername(String username)
			throws UserDoesNotExistException;

	/**
	 * Gets a <code>User</code> with the given email.
	 * 
	 * @param email
	 *            The email.
	 * @return <code>User</code> with the given email.
	 * @throws UserDoesNotExistException
	 *             Thrown if a <code>User</code> with the given email does not
	 *             exist.
	 */
	public User getUserByEmail(String email) throws UserDoesNotExistException;
	
	
	/**
	 * 
	 * @param user
	 */
	public void saveUser(User user);
	/**
	 * Gets a <code>User</code> with the given id.
	 * 
	 * @param userId
	 *            The user id.
	 * @return <code>User</code> with the given user id.
	 * @throws UserDoesNotExistException
	 *             Thrown if a <code>User</code> with the given user id does not
	 *             exist.
	 */
	public User getUserById(long userId) throws UserDoesNotExistException;

	/**
	 * Gets a <code>User</code> with the given registration number.
	 * 
	 * @param regNo
	 *            The registration number.
	 * @return <code>User</code> with the given registration number.
	 * @throws UserDoesNotExistException
	 *             Thrown if a <code>User</code> with the given registration
	 *             number does not exist.
	 */
	public User getUserByRegNo(String regNo) throws UserDoesNotExistException;

	/**
	 * Gets all <code>User</code>s with the given password.
	 * 
	 * @param password
	 *            The password.
	 * @return A <code>List</code> with <code>User Object</code>s that have the
	 *         same password as that given as a parameter.
	 */
	public List<User> getUserByPassword(String password);

	/**
	 * Gets all <code>User</code>s with a first name similar to that given in
	 * the parameter.
	 * 
	 * @param firstName
	 *            The first name.
	 * @return A <code>List</code> with <code>User Object</code>s that have the
	 *         first name as that given as a parameter.
	 */
	public List<User> getUsersByFirstName(String firstName, long companyId);
	
	/**
	 * Gets all <code>User</code>s with a first name similar to that given in
	 * the parameter and also the role given.
	 * 
	 * @param firstName
	 *            The first name.
	 * @param role
	 * 			  The role
	 * @return A <code>List</code> with <code>User Object</code>s that have the
	 *         first name and role as that given the parameters.
	 */
	public List<User> getUsersByFirstNameAndRole(String firstName, String role, long companyId);

	/**
	 * Gets all <code>User</code>s with a last name similar to that given in the
	 * parameter.
	 * 
	 * @param lastName
	 *            The last name.
	 * @return A <code>List</code> with <code>User Object</code>s that have the
	 *         last name as that given as a parameter.
	 */
	public List<User> getUsersByLastName(String lastName, long companyId);
	
	/**
	 * Gets all <code>User</code>s with a last name and role similar to those given in the
	 * parameter.
	 * 
	 * @param lastName
	 *            The last name.
	 * @param role
	 * 			  The role
	 * @return A <code>List</code> with <code>User Object</code>s that have the
	 *         last name and role as that given as the parameters.
	 */
	public List<User> getUsersByLastNameAndRole(String lastName, String role, long companyId);

	/**
	 * Gets <code>User</code>s who have the first and last name as those passed
	 * in as parameters.
	 * 
	 * @param firstName
	 *            The first name.
	 * @param lastName
	 *            The last name.
	 * @return A <code>List</code> with <code>User Object</code>s that have the
	 *         first and last name as that given as a parameter.
	 */
	public List<User> getUsersByName(String firstName, String lastName, long companyId);

	/**
	 * Gets <code>User</code>s by location.
	 * 
	 * @param location
	 *            The location.
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getUsersByLocation(long locationId) throws UserDoesNotExistException;

	/**
	 * Gets all <code>User</code>s with the given role.
	 * 
	 * @param roleName
	 *            The name of the role.
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getUsersByRole(String roleName)
			throws UserDoesNotExistException;
	
	/**
	 * Gets all <code>User</code>s with the given role.
	 * 
	 * @param roleName
	 *            The name of the role.
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getUsersByRoleCompanyId(String role, long companyId)
	        throws UserDoesNotExistException;

	/**
	 * Gets all <code>User</code>s with the given module id.
	 * 
	 * @param moduleId
	 *            The module id.
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getUsersByModule(Long moduleId);

	/**
	 * Gets all <code>User</code>s in the system.
	 * 
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getAllUsers();
	
	/**
	 * Gets all <code>User</code>s in the system with the specified role.
	 * 
	 * @param role
	 * 			  The user's role
	 * 
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getAllUsers(String role);
	
	/**
	 * Gets all <code>User</code>s in the system with the specified role
	 * belonging to the given company.
	 * 
	 * @param role
	 * 			  The user's role
	 * @param company_id The company id
	 * 
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getUsersByRole_CompanyId(String role, long company_id);

	/**
	 * Gets the total number of users in the system.
	 * 
	 * @return The total number of <code>User</code>s as an <code>int</code>.
	 */
	public int getTotalUsers();

	/**
	 * Gets all <code>User</code>s who are students.
	 * 
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getAllStudents();

	/**
	 * Gets all the exercises of the user with the given user name.
	 * 
	 * @param username
	 *            The <code>User</code>'s user name.
	 * @return A <code>List</code> with <code>UserExercise</code>s.
	 */
	public List<StudentExercise> getUserExcerciseByUsername(String username);

	/**
	 * Gets all the exercises of the user with the given email.
	 * 
	 * @param email
	 *            The <code>User</code>'s email.
	 * @return A <code>List</code> with <code>UserExercise</code>s.
	 */
	public List<StudentExercise> getUserExcerciseByEmail(String email);

	/**
	 * Gets all the exercises of the user with the given registration number.
	 * 
	 * @param regNo
	 *            The <code>User</code>'s registration number.
	 * @return A <code>List</code> with <code>UserExercise</code>s.
	 */
	public List<StudentExercise> getUserExerciseByRegNo(String regNo);

	/**
	 * Gets all enabled <code>User</code>s.
	 * 
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getEnabledUsers();

	/**
	 * Gets all disabled <code>User</code>s.
	 * 
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getDisabledUsers();

	/**
	 * Gets <code>User</code>s by the last log in date.
	 * 
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getUsersByLastLogin(Date lastLoginDate);

	/**
	 * Gets <code>User</code>s who were created between a certain defined
	 * period.
	 * 
	 * @param startDate
	 *            The start date.
	 * @param endDate
	 *            The end date.
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getUsersByCreationDate(Date startDate, Date endDate);

	/**
	 * Checks if a <code>User</code> is a super user.
	 * 
	 * @param username
	 *            The user name of the <code>User</code> to check.
	 * @return <code>true</code> if the <code>User</code> is a super user
	 *         otherwise returns <code>false</code>.
	 */
	public boolean isSuperuser(String username);

	/**
	 * Checks if a <code>User</code> is a student.
	 * 
	 * @param username
	 *            The user name of the <code>User</code> to check.
	 * @return <code>true</code> if the <code>User</code> is a student otherwise
	 *         returns <code>false</code>.
	 * @throws UserDoesNotExistException
	 *             Thrown if a <code>User</code> with the given user name does
	 *             not exist.
	 */
	public boolean isStudent(String username) throws UserDoesNotExistException;

	/**
	 * Checks if a <code>User</code> is a student.
	 * 
	 * @param user
	 *            The <code>User</code> to check.
	 * @return <code>true</code> if the <code>User</code> is a student otherwise
	 *         returns <code>false</code>.
	 * @throws UserDoesNotExistException
	 *             Thrown if the given <code>User</code> does not exist.
	 */
	public boolean isStudent(User user) throws UserDoesNotExistException;

	/**
	 * Checks if a <code>User</code> is enabled.
	 * 
	 * @param username
	 *            The user name of the <code>User</code> to check.
	 * @return <code>true</code> if the <code>User</code> is enabled otherwise
	 *         returns <code>false</code>.
	 * @throws UserDoesNotExistException
	 *             Thrown if a <code>User</code> with the given user name does
	 *             not exist.
	 */
	public boolean isEnabled(String username) throws UserDoesNotExistException;

	/**
	 * Gets the current module id of the user.
	 * 
	 * @param username
	 *            The user name of the <code>User</code> whose current module id
	 *            you want to get.
	 * @return The current module id as a <code>Long</code>.
	 */
	public Long getCurrentModuleId(String username);

	/**
	 * Gets the module start date of a user.
	 * 
	 * @param username
	 *            The user name of the <code>User</code>.
	 * @return A <code>Date</code> representing the module start date.
	 */
	public Date getModuleStartDate(String username);

	/**
	 * Searches <code>User</code>s using their first name and last name
	 * 
	 * @param firstName
	 *            The first name.
	 * @param lastName
	 *            The last name.
	 * @return A <code>List</code> containing <code>User Object</code>s.
	 */
	public List<User> searchUsers(String firstName, String lastName, long companyId);
	
	/**
	 * Searches <code>User</code>s using their first name, last name and their
	 * role.
	 * 
	 * @param firstName
	 *            The first name.
	 * @param lastName
	 *            The last name.
	 * @param role
	 *            The role.
	 * @return A <code>List</code> containing <code>User Object</code>s.
	 */
	public List<User> searchUsers(String firstName, String lastName, String role, long companyId);

	/**
	 * Sets a <code>User</code>s current module and the module start date.
	 * 
	 * @param userId
	 *            The user id of the <code>User</code>.
	 * @param currentModule
	 *            The current module.
	 * @param moduleStartDate
	 *            The module start date.
	 * @return A <code>User</code> with whose current module and module start
	 *         date you have set.
	 */
	public User setUserModuleAndStartDate(long userId, String currentModule,
			Date moduleStartDate);

	/**
	 * 
	 * @param startIndex
	 *            The start index
	 * @param pageSize
	 *            The number of <code>User</code>s to return.
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getPaginatedUsers(int startIndex, int pageSize);
	
	/**
	 * 
	 * @param startIndex
	 *            The start index
	 * @param pageSize
	 *            The number of <code>User</code>s to return.
	 * @param role
	 * 			  The role of users to get a paginated list of
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getPaginatedUsers(int startIndex, int pageSize, String role);
	
	/**
	 * 
	 * @param startIndex
	 *            The start index
	 * @param pageSize
	 *            The number of <code>User</code>s to return.
	 * @param role
	 * 			  The role of users to get a paginated list of
	 * @param company_id The company id
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getPaginatedUsersByRole_CompanyId(int startIndex, int pageSize,
			String role, long company_id);

	/**
	 * 
	 * @param startIndex
	 *            The start index
	 * @param pageSize
	 *            The number of <code>User</code>s to return.
	 * @param role
	 * 			  The role of users to get a paginated list of
	 * @return A <code>List</code> with <code>User Object</code>s.
	 */
	public List<User> getPaginatedUsersByRole(int startIndex, int pageSize,
			String roled);
	
	/**
	 * Sends email to students who have not logged into the system in the last
	 * five days.
	 */
	public void mailStudentsNotLoggedLastFiveDays();

	/**
	 * Checks if a user is enabled and has a course template.
	 * 
	 * @param user
	 *            The <code>User</code> we are checking.
	 * @return <code>true</code> if the <code>User</code> is enabled and has a
	 *         course template. Otherwise returns <code>false</code>.
	 */
	public boolean isEnabledAndHasCourse(User user);

	/**
	 * Checks if the email exists in the user database
	 * 
	 * @param email
	 *            The email we are checking.
	 * @return <code>int</code> password if the <code>User</code> is enabled and
	 *         has a course template.
	 */
	public void getAutoPass(User user,String newPassword) throws UserDoesNotExistException,
			Exception;
	
	public String generatePassword();
	
	public String getUserAuthority(String username);

	public void sendMail(User user, String password)
			throws UserDoesNotExistException, Exception;

	public int countUsers();
	
	public int countUsers(String role);
	
	
	/**
	 * @param userId
	 * @return
	 * @throws UserDoesNotExistException
	 * @throws Exception 
	 */
	public void enableDisableUser(String userId) throws UserDoesNotExistException, Exception;
	
	
	
	public List<EmbeddableCourse> getUserCourse(long userId);
	
	public int getNextRegistrationNumber();

	User setUserCourse(long userId,
			List<EmbeddableCourse> courseTemplateList);
	
	public void deletePreviousUsername(String oldUsername, String role);
	
	public User getSuperUser(String username, String firstName, String lastName);
	
	
//	/**
//	 * 
//	 * @param companyId
//	 * @throws CompanyDoesNotExistException
//	 */
//	public List<User> getUsersByCompanyId(long companyId) throws CompanyDoesNotExistException;
//	
//	/**
//	 * 
//	 * @param companyName
//	 * @throws CompanyDoesNotExistException
//	 */
//	public List<User> getUsersByCompanyName(String companyName) throws CompanyDoesNotExistException;

	/**
	 * 
	 * @param group
	 * @param user
	 * @throws UserDoesNotExistException
	 */
	void addUserToGroup(Group group, User user)
			throws UserDoesNotExistException; 
//	List<User> listUser() throws ServiceException;
}
