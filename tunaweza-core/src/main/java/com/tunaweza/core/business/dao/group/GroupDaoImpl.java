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

package com.tunaweza.core.business.dao.group;

import com.tunaweza.core.business.dao.exceptions.group.GroupDoesNotExistsException;
import com.tunaweza.core.business.dao.exceptions.group.GroupExistsException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.course.EmbeddableCourse;
import com.tunaweza.core.business.model.group.EmbeddableUser;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.user.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Repository(value="groupDao")
@Transactional
public class GroupDaoImpl extends GenericDaoImpl <Group> 
implements GroupDao{
	

		@Override
		public Group findGroupById(long gid) 
				throws GroupDoesNotExistsException{
				Session session = (Session) getEntityManager().getDelegate();
			
			Query query = session.createQuery("SELECT i FROM "
					+ getDomainClass().getName()
					+" i WHERE i.id = "+ gid);
			
			if(query.list().size()== 0){
				throw new GroupDoesNotExistsException("Group with id = "
						+ gid + "doesn't exist");
			}
			return (Group) query.list().get(0);
		}
		
		@Override
		public Group findGroupByName(String groupname)
		        throws GroupDoesNotExistsException{
			Session session = (Session) getEntityManager().getDelegate();
			
			Query query = session.createQuery("SELECT i FROM "
					+ getDomainClass().getName()
					+" i WHERE i.name = '"+ groupname +"'");
			
			if(query.list().size()== 0){
				throw new GroupDoesNotExistsException("Group with name = "
						+ groupname + "doesn't exist");
			}
			return (Group) query.list().get(0);
		}
		
		@Override
		public void saveUserToGroup(Group group,List<EmbeddableUser> user) 
		{		
			group.setUser(user);			
			saveOrUpdate(group);
		}
		
		@Override
		public void saveCoursesToGroup(Group group,List<EmbeddableCourse> course) 
		{		
			group.setCourses(course);			
			saveOrUpdate(group);
		}
		
		@Override
		public Group findGroup(Group group) 
				throws GroupDoesNotExistsException{
			Group groupS = findGroupById(group.getId());
			if(groupS==null){
				throw new GroupDoesNotExistsException();
			}
			return groupS;
		}
		
		@Override
		public Group addGroup (Group group) 
				throws GroupExistsException{
			try{
			Group groupS = saveOrUpdate(group);
			return groupS;
			}
			catch(IllegalArgumentException ex){
				throw new GroupExistsException();
			}
			
			
		}
		
		@Override
		public void saveOrUpdateGroup (Group group){
			saveOrUpdate(group);
		}
		
		@Override
		public void deleteGroup (Group group) 
				throws GroupDoesNotExistsException{
			try{
				delete(group);
			}
			catch(IllegalArgumentException ex){
				throw new GroupDoesNotExistsException();
			}
		}
		
		@Override
		public void deleteGroup (long gid) 
				throws GroupDoesNotExistsException{
			Group groupS = findById(gid);
			if(groupS == null){
				throw new GroupDoesNotExistsException();
			}
			deleteGroup(groupS);
		}
		
		@Override
		public List<Group> getAllGoups(){
			return findAll();
		}
		
		@Override
		public Integer getCount(){
			Session session = (Session) getEntityManager().getDelegate();
			Query query = session.createSQLQuery("SELECT COUNT(*) FROM group");
			Integer count = query.list().size(); 
			return count;
		}
		
		
		@Override
		public List<User> getUsersInGroup(Long groupId) 
		{
			Group group = null;
			Session session = (Session) getEntityManager().getDelegate();
			Query query = session.createQuery("SELECT i FROM " +getDomainClass().getName()+
					" i WHERE i.id = "+ groupId);
			
			if(query.list().size()>0){
				group = (Group)query.list().get(0);
			}
			List<User> user=new ArrayList<User>();
			try{
			
			List<EmbeddableUser> embeddedUserList = group.getUser();
			
			
			for(EmbeddableUser embeddUser : embeddedUserList )
			{
				Query moduleQuery=session.createSQLQuery("SELECT * FROM users " +
						"WHERE id ="+embeddUser.getUserId()).addEntity(User.class);
				User users = (User)moduleQuery.list().get(0);
				user.add(users);
			}
			}catch (NullPointerException e){
				System.out.println("No Users in Group");
			}
			
			return user;
			
		}
		
		/**
		 * 
		 * @param groupId
		 * @return
		 */
		@Override
		public List<Course> getCoursesInGroup(Long groupId) 
		{
			Group group = null;
			Session session = (Session) getEntityManager().getDelegate();
			Query query = session.createQuery("SELECT i FROM " +getDomainClass().getName()+
					" i WHERE i.id = "+ groupId);
			
			if(query.list().size()>0){
				group = (Group)query.list().get(0);
			}
			List<Course> courses=new ArrayList<Course>();
			try{
			
			List<EmbeddableCourse> embeddedCoursesList = group.getCourses();
			
			
			for(EmbeddableCourse embeddCourses : embeddedCoursesList )
			{
				Query courseQuery=session.createSQLQuery("SELECT * FROM course_template " +
						"WHERE id ="+embeddCourses.getCourseId()).addEntity(Course.class);
				Course courseTemplates = (Course)courseQuery.list().get(0);
				courses.add(courseTemplates);
			}
			}catch (NullPointerException e){
				System.out.println("No Users in Group");
			}
			
			return courses;
			
		}
			
		
		
		
	

}


