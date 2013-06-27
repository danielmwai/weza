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

package com.tunaweza.core.business.dao.evaluation;

import com.tunaweza.core.business.dao.generic.GenericDao;
import java.util.List;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class StudentCourseEvaluationDao extends GenericDao<StudentCourseEvaluation> 
{
	/**
	 * 
	 * @param id
	 * @return <code>StudentCourseEvaluation</code>
	 * @throws StudentCourseEvaluationNotFoundException
	 */
	public StudentCourseEvaluation findStudentCourseEvaluationById(Long id) 
			throws StudentCourseEvaluationDoesNotExistException;
	
	/**
	 * 
	 * @param user
	 * @return List of <code>StudentCourseEvaluation</code>
	 * @throws StudentCourseEvaluationNotFoundException
	 */
	public List<StudentCourseEvaluation> getAllStudentCourseEvaluationByStudent(Student student) 
			throws StudentCourseEvaluationDoesNotExistException;

    public List<StudentCourseEvaluation> getAllStudentCourseEvaluationByStudent(Student student,int startIndex, int pagesize)
			throws StudentCourseEvaluationDoesNotExistException;

	/**
	 * 
	 * @return List of <code>StudentCourseEvaluation</code>
	 * @throws StudentCourseEvaluationNotFoundException
	 */
	public List<StudentCourseEvaluation> getAllStudentCourseEvaluation()
			throws StudentCourseEvaluationDoesNotExistException;
	
	/**
	 * 
	 * @param studentCourseEvaluation
	 * @return 
	 * @throws StudentCourseEvaluationExistsException
	 * 
	 */
	public StudentCourseEvaluation addStudentCourseEvaluation(StudentCourseEvaluation studentCourseEvaluation) 
			throws StudentCourseEvaluationExistsException;
	
	/**
	 * 
	 * @param studentCourseEvaluation
	 * @throws StudentCourseEvaluationNotFoundException
	 * 
	 */
	public void updateStudentCourseEvaluation(StudentCourseEvaluation studentCourseEvaluation) 
			throws StudentCourseEvaluationDoesNotExistException;

	public List<StudentCourseEvaluation> getStudentCourseEvaluation(long evaluationId, long studentId);

	public StudentCourseEvaluation getFirstStudentCourseEvaluation(long evaluationId, long studentId);
	
	/**
	 * 
	 * @param evaluationId
	 * @param studentId
	 * @return
	 */
	public StudentCourseEvaluation getLastStudentCourseEvaluation(long evaluationId, long studentId)
			throws StudentCourseEvaluationDoesNotExistException;

	public void deleteStudentCourseEvaluation(StudentCourseEvaluation evaluation);

	public int countStudentCourseEvaluations(long studentId);

	public StudentCourseEvaluation getTemporaryStudentCourseEvaluation(
			long evaluationId, long studentId)
			throws StudentCourseEvaluationDoesNotExistException;
	
	public StudentCourseEvaluation getLastStudentCourseEvaluationNoSession(long evaluationId, long studentId, String companyDbName)
			throws StudentCourseEvaluationDoesNotExistException;

}
