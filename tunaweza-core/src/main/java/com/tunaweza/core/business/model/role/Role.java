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

package com.tunaweza.core.business.model.role;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Entity
@Table(name = Role.TABLE_NAME)
public class Role extends AbstractPersistentEntity implements
Comparable<Role> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "roles";
	public static final String JOIN_USER = "authority";
	/*
	 * Important : this id is(must be) an prime Number.
	 */

	@Column(name = "number", nullable = false, unique = true)
	private int number;

	@Column(name = "role_name", nullable = false, unique = true)
	private String roleName;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private List<User> users;
	
	@Column(name = "role_description", length = 16000, nullable = true)
	private String description;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(Role role) {
		if (role.getNumber() > number) {
			return -1;

		} else if (role.getNumber() < number) {
			return 1;
		}
		return 0;
	}
}
