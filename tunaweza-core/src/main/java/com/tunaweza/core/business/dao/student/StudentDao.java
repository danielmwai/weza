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

package com.tunaweza.core.business.Dao.student;

import com.tunaweza.core.business.Dao.exceptions.student.StudentDoesNotExistException;
import com.tunaweza.core.business.Dao.generic.GenericDao;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.user.User;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface StudentDao extends GenericDao<Student> {

	/**
	 * returns a list of all students
	 * 
	 * @return all the students
	 */
	public List<Student> getAllStudents();

	/**
	 * returns a single Student
	 * 
	 * @param studentId
	 * @return a student
	 * @throws StudentDoesNotExistException
	 */
	public Student getStudentById(Long studentId)
			throws StudentDoesNotExistException;
	
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws StudentDoesNotExistException
	 */
	public Student getStudentByIdNoSession(String studentId, String companyDbName)
			throws StudentDoesNotExistException;

	/**
	 * saves or updates a student
	 * 
	 * @param student
	 * @return a student
	 */
	public Student saveStudent(Student student);

	/**
	 * returns a single Student
	 * 
	 * @param user
	 * @return
	 */
	public Student getStudentByUser(User user)
			throws StudentDoesNotExistException;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws StudentDoesNotExistException
	 */
	public Long getStudentIdByUserId(Long userId)
			throws StudentDoesNotExistException;
	
	/**
	 * 
	 * @param user
	 * @param dbName
	 * @return
	 * @throws StudentDoesNotExistException
	 */
	public Student getStudentByUserNoSession(User user, String dbName, String companyId )
			throws StudentDoesNotExistException;

	/**
	 * 
	 * @param moduleId
	 * @param userId
	 * @return
	 */
	public int countStudentPendingExercises(long moduleId, long userId);

	/**
	 * 
	 * @param module
	 * @param user
	 */
	public void enableStudentModule(Module module, User user)
			throws StudentDoesNotExistException;

	/**
	 * 
	 * @param module
	 * @param user
	 * @return
	 * @throws StudentDoesNotExistException
	 */

	public boolean getStudentModuleStatus(Module module, User user)
			throws StudentDoesNotExistException;

	/**
	 * 
	 * @param module
	 * @param user
	 * @throws StudentDoesNotExistException
	 */
	public void setCurrentModule(Module module, Student student);

	/**
	 * 
	 * @param moduleId
	 * @return
	 */
	public BigInteger getCourseIdOfModule(Long moduleId, Long studentId);

	/**
	 * 
	 * @param moduleId
	 * @param userId
	 *            TODO
	 * @return
	 */
	List<Double> getCompletedTopicsAndExercises(long moduleId, long userId);

	/**
	 * 
	 * @param moduleId
	 * @param student
	 */
	public void setCompletedModule(Module module, Student student,
			Date moduleStartDate);

	/**
	 * 
	 * @param student
	 */
	public void setLastLoginDate(Student student);

	/**
	 * 
	 * @param student
	 * @return
	 */
	public Date getLastLoginDate(Student student);

	public void disableStudentModule(Module module, User user)
			throws StudentDoesNotExistException;

	public void enableFirstStudentModule(Module module, User user)
			throws StudentDoesNotExistException;

	public List<Student> getAllStudentModule(Long studentId)
			throws StudentDoesNotExistException;
	
	//////////////////
	public List<BigInteger> getStudentCourseTemplateList(Long studentId, String companyDbName);
}
