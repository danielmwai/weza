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

package com.tunaweza.core.business.Dao.evaluation.course;

import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface CourseEvaluationTransactionDao {

	/** 
	 *
	 * @return List of <code>CourseEvaluationTransaction<code>
	 */
	public List<CourseEvaluationTransaction> getCourseEvaluationTransactions();
	
	/** 
	 *
	 * @param id
	 * @return <code>CourseEvaluationTransaction<code>
	 */
	public CourseEvaluationTransaction getCourseEvaluationTransaction(Long id);
	
	/** 
	 *
	 * @param courseEvaluationTransaction
	 * @return 
	 */
	public CourseEvaluationTransaction saveCourseEvaluationTransaction(CourseEvaluationTransaction courseEvaluationTransaction);
	
	
	
	/** 
	 *
	 * @param studentEvaluation
	 * @return List of <code>CourseEvaluationTransaction<code>
	 */
	public List<CourseEvaluationTransaction> getCourseEvaluationTransactionByStudentEvaluation(
			StudentEvaluation studentEvaluation);
	
	/** 
	 *
	 * @param studentEvaluation
	 * @return <code>CourseEvaluationTransaction<code>
	 */
	public CourseEvaluationTransaction getLastUserCourseEvaluationTransaction(
			StudentEvaluation studentEvaluation);
	
	
}

