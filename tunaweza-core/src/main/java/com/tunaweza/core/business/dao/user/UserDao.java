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

import com.tunaweza.core.business.dao.exceptions.email.EmailExistsException;
import com.tunaweza.core.business.dao.exceptions.role.RoleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.role.RoleExistsException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserExistsException;
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.model.user.UserType;
import java.sql.Connection;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface UserDao extends GenericDao<User> {

    int CRITERIUM_OFFSETS = 1;
    int CRITERIUM_REGEX = 2;
    int CRITERIUM_ORDER = 4;
    int CRITERIUM_SQL = 8;

    /**
     *
     * @param uname
     * @param rname
     * @throws UserDoesNotExistException
     * @throws RoleDoesNotExistException
     * @throws RoleExistsException
     */
    public void addRole(String uname, String rname)
            throws UserDoesNotExistException, RoleDoesNotExistException,
            RoleExistsException;

    // public void deleteAuthorisationConfirmation(UserWaitingAuthorisation
    // uid);
    /**
     *
     * @param uid
     * @return
     * @throws UserDoesNotExistException
     */
    public List<Role> getUserRoles(long uid) throws UserDoesNotExistException;

    /**
     *
     * @param user
     * @return
     */
    public String getUserRole(User user);

    /**
     *
     * @param u
     * @param roles
     * @return
     */
    public UserDao setUserRoles(User u, List<Role> roles);

    /**
     *
     * @param uid
     * @return
     * @throws UserDoesNotExistException
     */
    public User findUserById(long uid) throws UserDoesNotExistException;

    public User findUserById(String userId) throws UserDoesNotExistException;

    /**
     *
     * @param email
     * @return
     * @throws UserDoesNotExistException
     */
    public User findUserByEmail(String email) throws UserDoesNotExistException;

    /**
     *
     * @param u
     * @return
     * @throws UserDoesNotExistException
     */
    public User findUser(User u) throws UserDoesNotExistException;

    /**
     *
     * @param dbname
     * @param username
     * @param password
     * @param fieldname
     * @param value
     * @param usrId
     * @throws Exception
     */
    public void jdbcUpdateUser(Connection connection, String fieldname, int value, String usrId) throws Exception;

    /**
     *
     * @param username
     * @return
     */
    public String findUserAuthority(String username);

    /**
     *
     * @param username
     * @return
     * @throws UserDoesNotExistException
     */
    public User findUser(String username) throws UserDoesNotExistException;

    /**
     *
     * @param roleName
     * @return
     * @throws UserDoesNotExistException
     */
    public List<User> findUsersByRole(String roleName)
            throws UserDoesNotExistException;

    /**
     *
     * @param roleName
     * @return
     * @throws UserDoesNotExistException
     */
    public List<User> findUsersByRoleCompanyId(String roleName, long companyId)
            throws UserDoesNotExistException;

    /**
     *
     * @param role
     * @return
     * @throws UserDoesNotExistException
     */
    public List<User> findUsersByRole(Role role)
            throws UserDoesNotExistException;

    /**
     *
     * @param roleid
     * @return
     * @throws UserDoesNotExistException
     */
    public List<User> findUsersByRole(long roleid)
            throws UserDoesNotExistException;

    /**
     *
     * @param enabled
     * @param companyid
     * @return
     */
    public List<User> getEnabledUserByCompanyId(boolean enabled, long companyid);

    /**
     *
     * @param criterium
     * @param offsetStart
     * @param offsetEnd
     * @param regex
     * @param sql
     * @return
     */
    public List findUsersByCriteria(int criterium, long offsetStart,
            long offsetEnd, String regex, String sql);

    /**
     *
     * @param uid
     * @throws UserDoesNotExistException
     */
    public void deleteUser(long uid) throws UserDoesNotExistException;

    /**
     *
     * @param u
     * @throws UserDoesNotExistException
     */
    public void deleteUser(User u) throws UserDoesNotExistException;

    /**
     *
     * @param user
     * @return
     * @throws RoleDoesNotExistException
     * @throws UserExistsException
     * @throws EmailExistsException
     */
    public User addUser(User user) throws RoleDoesNotExistException,
            UserExistsException, EmailExistsException;

    /**
     *
     * @param uname
     * @param rname
     * @throws UserDoesNotExistException
     * @throws RoleDoesNotExistException
     */
    public void removeRole(String uname, String rname)
            throws UserDoesNotExistException, RoleDoesNotExistException;

    /**
     *
     * @param uid
     * @param newp
     * @throws Exception
     */
    public void changePassword(long uid, String newp) throws Exception;

    /**
     *
     * @param mentor
     * @return
     */
    public List<User> getUserByMentor(Mentor mentor);

    /**
     *
     * @param userType
     * @return
     */
    public List<User> getUserByUserType(UserType userType);

    /**
     *
     * @param enabled
     * @return
     */
    public List<User> getUserByEnabled(boolean enabled);

    /**
     *
     * @param firstName
     * @param lastName
     * @return
     */
    public List<User> getUsers(String firstName, String lastName, long companyId);

    /**
     *
     * @param firstName
     * @param lastName
     * @param role
     * @return
     */
    public List<User> getUsers(String firstName, String lastName, String role, long companyId);

    /**
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    public List<User> getPaginatedUsersList(int startIndex, int pageSize);

    /**
     *
     * @param startIndex
     * @param pageSize
     * @param role
     * @return
     */
    public List<User> getPaginatedUsersList(int startIndex, int pageSize, String role);

    /**
     *
     * @return
     */
    public List<User> getAllStudents();

    /**
     *
     * @return
     */
    public List<User> getActiveStudents();

    /**
     *
     * @param user
     */
    public void saveUser(User user);

    /**
     *
     * @param firstName
     * @return
     */
    public List<User> getUsersByFirstName(String firstName, long companyId);

    /**
     *
     * @param lastName
     * @return
     */
    public List<User> getUsersByLastName(String lastName, long companyId);

    /**
     *
     * @param firstName
     * @param role
     * @return
     */
    public List<User> getUsersByFirstNameAndRole(String firstName, String role, long companyId);

    /**
     *
     * @param lastName
     * @param role
     * @return
     */
    public List<User> getUsersByLastNameAndRole(String lastName, String role, long companyId);

    /**
     *
     * @return
     */
    public int count();

    /**
     *
     * @param role
     * @return
     */
    public int count(String role);

    /**
     *
     * @return
     */
    public int getNewRegistrationNumber();

    /**
     *
     * @param role
     * @return
     */
    public List<User> findAll(String role);

    /**
     *
     * @param role
     * @param company_id
     * @return
     */
    public List<User> findUsersByRole_CompanyId(String role, long company_id);

    /**
     *
     * @param role
     * @param company_id
     * @return
     */
    public List<User> getPaginatedUsersByRole_CompanyId(int startIndex, int pageSize,
            String role, long company_id);

    /**
     * @param role
     * @param company_id
     * @return
     */
    public List<User> getPaginatedUsersByRole(int startIndex, int pageSize,
            String role);

    /**
     *
     * @param oldUsername
     * @param role
     */
    public void deletePreviousUsername(String oldUsername, String role);

    /**
     *
     * @param username
     * @param firstName
     * @param lastName
     * @return
     */
    public User getSuperUser(String username, String firstName, String lastName);

    /**
     *
     * @param companyid
     * @throws CompanyDoesNotExistException
     */
//	List<User> findUsersByCompanyId(long companyid)
//			throws CompanyDoesNotExistException;
    /**
     *
     * @param companyName
     * @throws CompanyDoesNotExistException
     */
//	List<User> findUsersByCompanyName(String companyName)
//			throws CompanyDoesNotExistException;
//	
    /**
     *
     * @param locationId
     * @return
     * @throws UserDoesNotExistException
     */
    public List<User> findUsersByLocation(long locationId) throws UserDoesNotExistException;

    /**
     *
     * @param group
     * @param user
     * @throws UserDoesNotExistException
     */
    void addUserToGroup(Group group, User user)
            throws UserDoesNotExistException;

    public void print();

//    List<User> listUser();

}
