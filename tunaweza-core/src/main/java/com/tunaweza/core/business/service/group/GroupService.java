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

package com.tunaweza.core.business.service.group;

import com.tunaweza.core.business.dao.exceptions.group.GroupDoesNotExistsException;
import com.tunaweza.core.business.dao.exceptions.group.GroupExistsException;
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.course.EmbeddableCourse;
import com.tunaweza.core.business.model.group.EmbeddableUser;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.user.User;
import java.util.List;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface GroupService {

	/**
	 * 
	 * @param group
	 * @return
	 * @throws GroupExistsException
	 */
	Group addGroup(Group group) throws GroupExistsException;

	/**
	 * 
	 * @param group
	 * @throws GroupDoesNotExistsException
	 * @throws GroupExistsException 
	 */
	void saveGroup(Group group) throws GroupExistsException;
	
	/**
	 * 
	 * @param group
	 * @param user
	 */
	public void saveUserToGroup(Group group,
			List<EmbeddableUser> user);
	/**
	 * 
	 * @param id
	 * @return
	 * @throws GroupDoesNotExistsException
	 */

	Group findGroupById(long id) throws GroupDoesNotExistsException;
	
	/**
	 * 
	 * @param group
	 * @return
	 * @throws GroupDoesNotExistsException
	 */

	Group findGroup(Group group) throws GroupDoesNotExistsException;
	
	/**
	 * 
	 * @param id
	 * @throws GroupDoesNotExistsException
	 */

	void deleteGroup(long id) throws GroupDoesNotExistsException;
	
	/**
	 * 
	 * @param group
	 * @throws GroupDoesNotExistsException
	 */

	void deleteGroup(Group group) throws GroupDoesNotExistsException;
	
	/**
	 * 
	 * @return
	 */

	List<Group> getAllGroups();

	/**
	 * 
	 * @return
	 */
	Integer getCount();
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws GroupDoesNotExistsException
	 */

	Group findGroupByName(String name) throws GroupDoesNotExistsException;

	/**
	 * 
	 * @param group
	 * @return
	 */
	public List<User> getUsersInGroup(Group group);
	/**
	 * 
	 * @param group
	 * @return
	 */
	
	public List<Course> getCoursesInGroup(Group group);
	
	/**
	 * 
	 * @param group
	 * @param courses
	 */
	public void saveCoursesToGroup(Group group,
			List<EmbeddableCourse> courses) ;
}
