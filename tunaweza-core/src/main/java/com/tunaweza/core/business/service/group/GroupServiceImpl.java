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
import com.tunaweza.core.business.Dao.exceptions.group.GroupDoesNotExistsException;
import com.tunaweza.core.business.Dao.exceptions.group.GroupExistsException;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.user.User;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Service("groupService")
@Transactional
public class GroupServiceImpl extends Constants implements GroupService{

	@Autowired
	private GroupDao groupDao;
	
	@Override
	public Group addGroup(Group group) throws GroupExistsException {
		return groupDao.addGroup(group);
	}

	@Override
	public void saveGroup(Group group) throws GroupExistsException {
		groupDao.saveOrUpdateGroup(group);

	}

	@Override
	public Group findGroupById(long id) throws GroupDoesNotExistsException {
		return groupDao.findGroupById(id);
	}
	
	@Override
	public Group findGroupByName(String name) throws GroupDoesNotExistsException {
		return groupDao.findGroupByName(name);
	}

	@Override
	public Group findGroup(Group group)
			throws GroupDoesNotExistsException {
		return groupDao.findGroup(group);
	}

	@Override
	public void deleteGroup(long id) throws GroupDoesNotExistsException {
		groupDao.deleteGroup(id);

	}

	@Override
	public void deleteGroup(Group group)
			throws GroupDoesNotExistsException {
		groupDao.deleteGroup(group);

	}
	


	@Override
	public List<Group> getAllGroups() {
		return groupDao.getAllGoups();
	}
	
	@Override
	public Integer getCount() {
		return groupDao.getCount();
	}
	@Override
	public void saveUserToGroup(Group group,
			List<EmbeddableUser> user) {
		groupDao.saveUserToGroup(group, user);

	}
	
	@Override
	public void saveCoursesToGroup(Group group,
			List<EmbeddableCourse> courses) {
		groupDao.saveCoursesToGroup(group, courses);

	}
	

	@Override
	public List<User> getUsersInGroup(Group group) {
		return groupDao.getUsersInGroup(group
				.getId());
	}
	
	@Override
	public List<CourseTemplate> getCoursesInGroup(Group group) {
		return groupDao.getCoursesInGroup(group
				.getId());
	}

    @Override
    public Group addGroup(Group group) throws GroupExistsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveGroup(Group group) throws GroupExistsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveUserToGroup(Group group, List<EmbeddableUser> user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Group findGroup(Group group) throws GroupDoesNotExistsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteGroup(Group group) throws GroupDoesNotExistsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Group> getAllGroups() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getUsersInGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CourseTemplate> getCoursesInGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveCoursesToGroup(Group group, List<EmbeddableCourse> courses) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	
	
	
}
