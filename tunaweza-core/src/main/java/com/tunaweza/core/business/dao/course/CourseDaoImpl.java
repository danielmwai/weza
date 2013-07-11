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

package com.tunaweza.core.business.dao.course;

import com.tunaweza.core.business.dao.exceptions.course.CourseExistsException;
import com.tunaweza.core.business.dao.exceptions.course.CourseNotFoundException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;

import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.course.EmbeddableModule;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.user.User;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Repository(value = "courseTemplateDao")
@Transactional
public class CourseDaoImpl extends GenericDaoImpl<Course>
		implements  CourseDao {
@Autowired
SessionFactory sessionFactory ; 

	@Override
	public Course findCourseById(long uid)
			throws CourseNotFoundException 
	{
		Course course=(Course) findById(uid);
		if(course==null)
			throw new CourseNotFoundException();
		return course;
	}

	@Override
	public Course findCourseByName(String name)
			throws CourseNotFoundException 
	{
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+getDomainClass().getName()+" i WHERE i.name='"+name+"'");
		
		Course course = null;
		if(query.list().size() > 0){
			course = (Course)query.list().get(0);
		}
		else{
			throw new CourseNotFoundException("CourseTemplate with " +
					"name : "+name+" was not found");
		}		
		return course;
	}

	@Override
	public Course findCourse(Course course)
			throws CourseNotFoundException 
	{
		Course dbCourse=(Course) findById(course.getId());
		if(dbCourse==null){
			throw new CourseNotFoundException();			
		}
		return dbCourse;
	}

	@Override
	public List<Course> getAllCourse() 
	{
		return findAll();
	}

	@Override
	public void saveCourse(Course course) 
	throws CourseExistsException
	{
		Course duplicate=null;
		try{
			duplicate = findCourseByName(course.getName());
		}
		catch(CourseNotFoundException e){			
		}		 
		if(duplicate !=null){
			throw new CourseExistsException();
		}
		saveOrUpdate(course);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsersByCourse(Long courseId) 
	{
		List<User> users = null;
		Query query = sessionFactory.getCurrentSession().createQuery("FROM User u " +
				"WHERE u.student.courseTemplate.id = "+ courseId + " " +
				"ORDER BY u.student.courseTemplate.id ASC");
		
		if(query.list().size()>0){
			users = query.list();
		}
		return users;
	}

	@Override
	public void updateCourse(Course course) 
	{
		saveOrUpdate(course);
		
	}

	@Override
	public void saveModulesToCourse(Course course,List<EmbeddableModule> modules) 
	{		
		course.setModules(modules);			
		saveOrUpdate(course);
	}

	@Override
	public List<Module> getModulesInCourse(Long courseId) 
	{
		Course course = null;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM " +getDomainClass().getName()+
				" i WHERE i.id = "+ courseId);
		
		if(query.list().size()>0){
			course = (Course)query.list().get(0);
		}
		List<Module> modules=new ArrayList<Module>();
		List<EmbeddableModule> embeddedModuleList = course.getModules();
		if(embeddedModuleList!=null){
		for(EmbeddableModule embeddModule : embeddedModuleList )
		{
			Query moduleQuery=sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM module " +
					"WHERE id ="+embeddModule.getModuleId()).addEntity(Module.class);
			Module module = (Module)moduleQuery.list().get(0);
			modules.add(module);
		}
		}
		return modules;
	}

	@Override
	public List<Module> getActiveModulesInCourse(Long courseId) {
		Course course = null;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM " +getDomainClass().getName()+
				" i WHERE i.id = "+ courseId);
		
		if(query.list().size()>0){
			course = (Course)query.list().get(0);
		}
		List<Module> modules=new ArrayList<Module>();
		List<EmbeddableModule> embeddedModuleList = course.getModules();
		
		for(EmbeddableModule embeddModule : embeddedModuleList )
		{
			Query moduleQuery=sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM module m " +
					"WHERE m.id = "+embeddModule.getModuleId()+" AND m.enabled = 1")
					.addEntity(Module.class);
			Module module = (Module)moduleQuery.list().get(0);
			modules.add(module);
		}
		return modules;
	}
	
	
	//////////////////
	@Override
	public List<Module> getModulesInCourseNoSession(Long courseId, String companyDbName) 
	{
		Course course = null;
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM " + companyDbName + ".course_template"
				+ " WHERE id = " + courseId).addEntity(Course.class);
		
		if(query.list().size()>0){
			course = (Course)query.list().get(0);
		}
		List<Module> modules=new ArrayList<Module>();
		List<EmbeddableModule> embeddedModuleList = course.getModules();
		if(embeddedModuleList!=null){
		for(EmbeddableModule embeddModule : embeddedModuleList )
		{
			Query moduleQuery=sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM " + companyDbName + ".module " +
					"WHERE id ="+embeddModule.getModuleId()).addEntity(Module.class);
			Module module = (Module)moduleQuery.list().get(0);
			modules.add(module);
		}
		}
		return modules;
	}
	
	
	public List<Course> courseListById(List<BigInteger> ctIds, String companyDbName) {
		
		List<Course> cts=new ArrayList<Course>();
		
		System.out.println(">>>>>>>>>>>>01");
		for(BigInteger ctId : ctIds) {
			System.out.println(">>>>>>>>>>>>02");
			Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM " + companyDbName +
					".course_template i WHERE i.id = " + ctId).addEntity(Course.class);
			System.out.println(">>>>>>>>>>>>03");
			Course ct = (Course)query.list().get(0);
			System.out.println(">>>>>>>>>>>>04");
			cts.add(ct);
			System.out.println(">>>>>>>>>>>>05");
		}
		return cts;
	}
	
	@Override
	public List<Module> getActiveModulesInCourseNoSession(Long courseTemplateId, String companyDbName) {
		List<BigInteger> moduleIds=new ArrayList<BigInteger>();
		List<Module> modules=new ArrayList<Module>();
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT module FROM " + companyDbName +
				".course_modules i WHERE i.course = "+ courseTemplateId);
		
		if(query.list().size()>0){
			moduleIds = (List<BigInteger>)query.list();
			for(BigInteger moduleId : moduleIds) {
				query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM " + companyDbName +
					".module i WHERE i.id = "+ moduleId).addEntity(Module.class);
				if(query.list().size() > 0) {
					Module module = (Module)query.list().get(0);
					modules.add(module);
				}
			}
		}
		return modules;
	}

}
