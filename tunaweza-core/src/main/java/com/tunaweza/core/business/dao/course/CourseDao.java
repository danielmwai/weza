/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.course;

import com.tunaweza.core.business.dao.exceptions.course.CourseNotFoundException;
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.course.Course;

/**
 *
 * @author Daniel Mwai
 */
public interface CourseDao  extends GenericDao<Course> 
{
	/**
	 * 
	 * @param uid
	 * @return <code>Course</code>
	 * @throws CourseNotFoundException
	 */
	public Course findCourseById(long uid) 
			throws CourseNotFoundException;
	
	/**
	 * 
	 * @param name
	 * @return <code>Course</code>
	 * @throws CourseNotFoundException
	 */
	public Course findCourseByName(String name) 
			throws CourseNotFoundException;

	/**
	 * 
	 * @param course
	 * @return <code>Course</code>
	 * @throws CourseNotFoundException
	 */
	public Course findCourse(Course course) 
			throws CourseNotFoundException;

	/**
	 * 
	 * @return
	 */
	public List<Course> getAllCourse();

	/**
	 * 
	 * @param course
	 */
	public void saveCourse(Course course) 
			throws CourseExistsException;
	
	/**
	 * 
	 * @param course
	 */
	public void updateCourse(Course course);
	
	/**
	 * 
	 * @param courseTemplate, module
	 */
	public void saveModulesToCourse(Course course,List<EmbeddableModule> mdoule);
	
		
	/**
	 * 
	 * @param courseTemplateId
	 * @return 
	 */
	public List<User> getAllUsersByCourse(Long courseTemplateId);	
	
	/**
	 * 
	 * @param courseId
	 * @return 
	 */
	public List<Module> getModulesInCourse(Long courseTemplateId);
	
	/**
	 * 
	 * @param courseId
	 * @return 
	 */
	public List<Module> getActiveModulesInCourse(Long courseTemplateId);
	
	
	///////////////
	/**
	 * 
	 * @param courseId
	 * @param companyDbName
	 * @return
	 */
	public List<Module> getModulesInCourseNoSession(Long courseId, String companyDbName);
	
	public List<Course> courseListById(List<BigInteger> ctIds, String companyDbName);
	
	public List<Module> getActiveModulesInCourseNoSession(Long courseId, String companyDbName);
}
