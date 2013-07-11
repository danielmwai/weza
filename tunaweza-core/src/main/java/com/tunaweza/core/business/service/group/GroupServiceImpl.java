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
import com.tunaweza.core.business.dao.group.GroupDao;
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.course.EmbeddableCourse;
import com.tunaweza.core.business.model.group.EmbeddableUser;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.user.User;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Service("groupService")
@Transactional
public class GroupServiceImpl /*extends Constants*/ implements GroupService{
        @Autowired
	private GroupDao groupDAO;
        
  
//    public GroupServiceImpl(Class<?> clazz) {
//        super(clazz);
//    }
	
	@Override
	public Group addGroup(Group group) throws GroupExistsException {
		return groupDAO.addGroup(group);
	}

	@Override
	public void saveGroup(Group group) throws GroupExistsException {
		groupDAO.saveOrUpdateGroup(group);

	}

	@Override
	public Group findGroupById(long id) throws GroupDoesNotExistsException {
		return groupDAO.findGroupById(id);
	}
	
	@Override
	public Group findGroupByName(String name) throws GroupDoesNotExistsException {
		return groupDAO.findGroupByName(name);
	}

	@Override
	public Group findGroup(Group group)
			throws GroupDoesNotExistsException {
		return groupDAO.findGroup(group);
	}

	@Override
	public void deleteGroup(long id) throws GroupDoesNotExistsException {
		groupDAO.deleteGroup(id);

	}

	@Override
	public void deleteGroup(Group group)
			throws GroupDoesNotExistsException {
		groupDAO.deleteGroup(group);

	}
	


	@Override
	public List<Group> getAllGroups() {
		return groupDAO.getAllGoups();
	}
	
	@Override
	public Integer getCount() {
		return groupDAO.getCount();
	}
	@Override
	public void saveUserToGroup(Group group,
			List<EmbeddableUser> user) {
		groupDAO.saveUserToGroup(group, user);

	}
	
	@Override
	public void saveCoursesToGroup(Group group,
			List<EmbeddableCourse> courses) {
		groupDAO.saveCoursesToGroup(group, courses);

	}
	

	@Override
	public List<User> getUsersInGroup(Group group) {
		return groupDAO.getUsersInGroup(group
				.getId());
	}
	
	@Override
	public List<Course> getCoursesInGroup(Group group) {
		return groupDAO.getCoursesInGroup(group
				.getId());
	}
	
	
	
}
