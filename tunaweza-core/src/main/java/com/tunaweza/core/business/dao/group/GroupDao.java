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

<<<<<<< HEAD
package com.tunaweza.core.business.dao.group;
import com.tunaweza.core.business.dao.exceptions.group.GroupDoesNotExistsException;
import com.tunaweza.core.business.dao.exceptions.group.GroupExistsException;
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.user.User;
=======
package com.tunaweza.core.business.Dao.group;
import com.tunaweza.core.business.Dao.generic.GenericDao;
>>>>>>> b96906ca9fbfa7acaa718f3782e6069e07baf027
import java.util.List;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface GroupDao extends GenericDao<Group>{
/**
 * 
 * @param cid
 * @return
 * @throws GroupDoesNotExistsException
 */
	public Group findGroupById(long cid) 
			throws GroupDoesNotExistsException;
/**
 * 
 * @param groupname
 * @return
 * @throws GroupDoesNotExistsException
 */
	public Group findGroupByName(String groupname)
	        throws GroupDoesNotExistsException;
	/**
	 * 
	 * @param group
	 * @param modules
	 */
	
	public void saveUserToGroup(Group group,List<EmbeddableUser> modules);

	/**
	 * 
	 * @param group
	 * @param course
	 */
	public void saveCoursesToGroup(Group group,List<EmbeddableCourse> course) ;
/**
 * 
 * @param group
 * @return
 * @throws GroupDoesNotExistsException
 */
	public Group findGroup(Group group) 
			throws GroupDoesNotExistsException;
/**
 * 
 * @param group
 * @return
 * @throws GroupExistsException
 */
	public Group addGroup (Group group) 
			throws GroupExistsException;
/**
 * 
 * @param group
 */
	public void saveOrUpdateGroup (Group group);
/**
 * 
 * @param group
 * @throws GroupDoesNotExistsException
 */
	public void deleteGroup (Group group) 
			throws GroupDoesNotExistsException;

/**
 * 
 * @param gid
 * @throws GroupDoesNotExistsException
 */
	public void deleteGroup (long gid) 
			throws GroupDoesNotExistsException;
/**
 * 
 * @return
 */
	public List<Group> getAllGoups();
/**
 * 
 * @return
 */
	public Integer getCount();
	
	/**
	 * 
	 * @param groupId
	 * @return
	 */
	public List<User> getUsersInGroup(Long groupId) ;
	
	/**
	 * 
	 * @param groupId
	 * @return
	 */
	
	public List<Course> getCoursesInGroup(Long groupId) ;
}
