/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.role;

import com.tunaweza.core.business.dao.exceptions.role.RoleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.role.RoleExistsException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.user.User;
import java.util.List;

/**
 *
 * @author naistech
 */
public interface RoleService {
   
	/**
	 * Gets all roles.
	 * 
	 * @return <code>List</code> containing <code>Role</code> objects.
	 */
	public List<Role> getAllRoles();

	/**
	 * Gets the total number of <code>Role</code>s.
	 * 
	 * @return <code>int</code> representing the number of roles.
	 */
	public int getTotalRoles();

	/**
	 * Gets a <code>Role</code> with the given role name.
	 * 
	 * @param roleName
	 *            The role name.
	 * @return <code>Role</code> object with the given role name.
	 * @throws RoleDoesNotExistException
	 *             Thrown if a <code>Role</code> with the given name does not
	 *             exist.
	 */
	public Role getRoleByName(String roleName) throws RoleDoesNotExistException;

	/**
	 * Gets a <code>Role</code> with the given id.
	 * 
	 * @param roleId
	 *            The role id of the <code>Role</code> we want to get.
	 * @return <code>Role</code> object with the given role id.
	 * @throws RoleDoesNotExistException
	 *             Thrown if a <code>Role</code> with the given id does not
	 *             exist.
	 */
	public Role getRoleById(int roleId) throws RoleDoesNotExistException;

	/**
	 * Gets <code>User</code>s with the given role.
	 * 
	 * @param roleName
	 *            The role of the users we want to get.
	 * @return <code>List</code> that contains <User> objects with the given
	 *         role.
	 * @throws RoleDoesNotExistException
	 *             Thrown if a <code>Role</code> with the given name does not
	 *             exist.
	 */
	public List<User> getUsersByRole(String roleName)
			throws RoleDoesNotExistException;

	/**
	 * Gets the total number of <code>User</code>s with a given
	 * <code>Role</code>.
	 * 
	 * @param roleName
	 *            The role of the <code>User</code>s we want to get.
	 * @return The total number of <code>User</code>s with the given role.
	 * @throws RoleDoesNotExistException
	 *             Thrown if the given role name does not exist.
	 */
	public int getTotalUsersByRole(String roleName)
			throws RoleDoesNotExistException;

	/**
	 * Adds a new <code>Role</code>.
	 * 
	 * @param roleName
	 *            The name of the role.
	 * @param description
	 *            The description of the role.
	 * @throws RoleExistsException
	 *             Thrown if a <code>Role</code> with given id and name already
	 *             exists.
	 */
//	public void addRole(String roleName, String description)
//			throws RoleExistsException;

	/**
	 * Adds a new <code>Role</code>.
	 * 
	 * @param role
	 *            The role to add.
	 * @throws RoleExistsException
	 *             Thrown if the given <code>Role</code> already exists.
	 */
	public void addRole(Role role) throws RoleExistsException;

	/**
	 * Deletes a <code>Role</code>.
	 * 
	 * @param roleToDelete
	 *            The <code>Role</code> to delete.
	 * @throws RoleDoesNotExistException
	 *             Thrown if the given <code>Role</code> does not exist.
	 */
	public void deleteRole(Role roleToDelete)
			throws RoleDoesNotExistException;

	/**
	 * Updates a given <code>Role</code>.
	 * 
	 * @param role
	 *            The <code>Role</code> to update.
	 * @throws RoleDoesNotExistException
	 *             Thrown if the given <code>Role</code> does not already exist.
	 */
	public void updateRole(Role role) throws RoleDoesNotExistException;

	/**
	 * Gets the <code>Role</code>s of a given <code>User</code>.
	 * 
	 * @param username
	 *            The user name.
	 * @return A <code>List Object</code> that contains <code>Role Object</code>
	 *         s.
	 */
	public List<Role> getUserRoles(String username)
			throws UserDoesNotExistException;

	/**
	 * Sets the <code>Role</code>s of the <code>User</code> with the given user
	 * name.
	 * 
	 * @param username
	 *            The user name.
	 * @param roles
	 *            A <code>List</code> that contains the <code>Role Object</code>
	 *            s.
	 * @throws UserDoesNotExistException
	 *             Thrown if a <code>User</code> with the given user name does
	 *             not exist.
	 * @throws RoleDoesNotExistException
	 *             Thrown if the <code>List</code> contains a <code>Role</code>
	 *             that does not exist.
	 */
	public void setUserRoles(String username, List<Role> roles)
			throws UserDoesNotExistException, RoleDoesNotExistException;

	/**
	 * Sets the <code>Role</code>s of the <code>User</code> with the given user
	 * id.
	 * 
	 * @param userId
	 *            The user id.
	 * @param roles
	 *            A <code>List</code> that contains the <code>Role Object</code>
	 *            s.
	 * @throws UserDoesNotExistException
	 *             Thrown if a <code>User</code> with the given user id does not
	 *             exist.
	 * @throws RoleDoesNotExistException
	 *             Thrown if the <code>List</code> contains a <code>Role</code>
	 *             that does not exist.
	 */
	public void setUserRoles(long userId, List<Role> roles)
			throws UserDoesNotExistException, RoleDoesNotExistException;

	/**
	 * Sets the <code>Role</code>s 
	 * @return 
	 * @throws RoleDoesNotExistException
	 *             Thrown if the <code>List</code> contains a <code>Role</code>
	 *             that does not exist.
	 */
	public List<Role> getRolesNotOnCloudLevel()
			throws RoleDoesNotExistException;

}
