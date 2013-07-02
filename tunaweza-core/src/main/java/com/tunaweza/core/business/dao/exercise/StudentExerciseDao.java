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

package com.tunaweza.core.business.dao.exercise;

import com.tunaweza.core.business.dao.exceptions.student.StudentExerciseExistsException;
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface StudentExerciseDao extends GenericDao<StudentExercise> 
{
	/**
	 * 
	 * @param id
	 * @return <code>StudentExercise</code>
	 * @throws StudentExerciseExistsException
	 */
	public StudentExercise findStudentExerciseById(Long id) 
			throws StudentExerciseExistsException;
	
	/**
	 * 
	 * @param user
	 * @return List of <code>StudentExercise</code>
	 * @throws StudentExerciseExistsException
	 */
	public List<StudentExercise> getAllStudentExerciseByStudent(User user) 
			throws StudentExerciseExistsException;
	
	/**
	 * 
	 * @return List of <code>StudentExercise</code>
	 * @throws StudentExerciseExistsException
	 */
	public List<StudentExercise> getAllStudentExercise() 
			throws StudentExerciseExistsException;
	
	/**
	 * 
	 * @param topic
	 * @return List of <code>StudentExercise</code>
	 * @throws StudentExerciseExistsException
	 */
	public List<StudentExercise> findStudentExerciseByTopic(Topic topic) 
			throws StudentExerciseExistsException;
	
	/**
	 * 
	 * @param studentExercise
	 * @throws StudentExerciseExistsException
	 * 
	 */
	public void addStudentExercise(StudentExercise studentExercise) 
			throws StudentExerciseExistsException;
	
	/**
	 * 
	 * @param studentExercise
	 * @throws StudentExerciseExistsException
	 * 
	 */
	public void updateStudentExercise(StudentExercise studentExercise) 
			throws StudentExerciseExistsException;
	
	/**
	 * 
	 * @param isComplete
	 * @return List of <code>StudentExercise</code>
	 * 
	 */
	public List<StudentExercise> getStudentExerciseByIsComplete(
			boolean isComplete);
	
	/**
	 * 
	 * @param user
	 * @param exercise
	 * @return <code>StudentExercise</code>
	 * 
	 */
	public StudentExercise getStudentExerciseByExercise(User user,
				Topic exercise);
	
	public List<List> getAllStudentExerciseByModule(User user, Module module)
			throws StudentExerciseExistsException;
	
}
