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


import com.tunaweza.core.business.dao.exceptions.role.RoleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.role.RoleExistsException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.role.Role;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface RoleDao extends GenericDao<Role> {

	public List<Role>    getUserRoles(long uid) throws UserDoesNotExistException;
    public List<Role>    getallRoles();
    public List<Role>    getUserRoles(String username) throws UserDoesNotExistException;
    public RoleDao setUserRoles(long l,List<Role> roles) throws UserDoesNotExistException, RoleDoesNotExistException;
    public RoleDao setUserRoles(String s,List<Role> roles) throws RoleDoesNotExistException, UserDoesNotExistException;
    public Role    addRole(Role role) throws RoleExistsException;
    public Role    getRole(int roleid) throws RoleDoesNotExistException;
    public Role    getRole(int roleid,boolean wired) throws RoleDoesNotExistException;
    public Role    getRole(String rname) throws RoleDoesNotExistException;
    public RoleDao removeRole(Role role)throws RoleDoesNotExistException;
    public RoleDao removeRole(int roleid)throws RoleDoesNotExistException;
    public RoleDao removeRole(String roleName)throws RoleDoesNotExistException;
    public int    getBiggestNumber();
    public List<Role> getRoleNotInCloudLevel() throws RoleDoesNotExistException;
}