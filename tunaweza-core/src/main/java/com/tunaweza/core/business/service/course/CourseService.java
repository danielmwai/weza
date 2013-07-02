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

package com.tunaweza.core.business.service.course;


import com.tunaweza.core.business.dao.exceptions.course.CourseExistsException;
import com.tunaweza.core.business.dao.exceptions.course.CourseNotFoundException;
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.course.EmbeddableModule;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.user.User;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface CourseService {
/**
	 * 
	 * @param course
	 * @return 
	 * @throws CourseExistsException
	 */
	public Course addCourse(Course course) 
			throws CourseExistsException;
	
	/**
	 * 
	 * @param courseTemplate
	 * 
	 */	
	public void editCourse(Course course);
	
	/**
	 * 
	 * @param course
	 * @throws CourseNotFoundException
	 */
	public Course findCourseById(Long id) 
			throws CourseNotFoundException;
	
	/**
	 * 
	 * @param course
	 * @throws CourseTemplateNotFoundException
	 */
	public Course findCourseByName(String name)
			throws CourseNotFoundException;
	
	/**
	 * 
	 * @return <code>Course</code> list
	 * 
	 */
	public List<Course> listAllCourse();
	
	
	/**
	 * 
	 * @param course
	 * @return <code>User</code> list
	 * 
	 */
	public List<User> listUsersByCourse(Course course);
	
	/**
	 * 
	 * @param course
	 * @param module
	 * 
	 */
	public void saveModulesToCourse(Course course,List<EmbeddableModule> module);
	
	/**
	 * 
	 * @param course
	 * @return <code>Module</code> list
	 * 
	 */
	public List<Module> getModulesInCourse(Course course);
	
	/**
	 * 
	 * @param course
	 * @return <code>Module</code> list
	 * 
	 */
	public List<Module> getActiveModulesInCourse(Course course);

	

	//////////////////
	public List<Module> getModulesInCourseNoSession(Course course, String companyDbName);
	
}
