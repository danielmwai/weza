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

package com.tunaweza.core.business.service.solution;

import com.tunaweza.core.business.dao.exceptions.student.StudentExerciseExistsException;
import com.tunaweza.core.business.dao.exceptions.student.StudentExerciseNotFoundException;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.exercise.transaction.ExerciseTransactionType;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.exercise.ExerciseTransaction;
import com.tunaweza.core.business.service.exercise.GradeExerciseBean;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface SolutionService {

	/**
	 * 
	 * @param user
	 * @param solutionBean
	 */
	public void createStudentExercise(User user,
			PostSolutionBean solutionBean)throws StudentExerciseExistsException;
	/**
	 * 
	 * @param user
	 * @param solutionBean
	 */
	public void replaceLastExerciseTransaction(User user,
			PostSolutionBean solutionBean);
	/**
	 * 
	 * @param user
	 * @param solutionBean
	 */
	public void resubmitExerciseTransaction(User user,
			PostSolutionBean solutionBean);
	
	/**
	 * 
	 * @param user
	 * @param exercise
	 * @return
	 */
	public ExerciseTransaction getLastExerciseTransaction(StudentExercise studentExercise);
	
	/**
	 * 
	 * @param studentExercise
	 * @return
	 */
	public List<ExerciseTransaction> getExerciseTransactionByStudentExercise(
			StudentExercise studentExercise);
	
	/**
	 * 
	 * @param mentor
	 * @return
	 */
	public List<ExerciseTransaction> getExerciseTransactionByMentor(
			Mentor mentor);
	
	/**
	 * 
	 * @param transactionType
	 * @return
	 */
	public List<ExerciseTransaction> getExerciseTransactionByExerciseTransactionType(
			ExerciseTransactionType transactionType);
	
	/**
	 * 
	 * @param user
	 * @param exercise
	 * @return
	 */
	public StudentExercise getStudentExerciseByStudentAndTopic(User user,
			Topic exercise);
			
	
	/**
	 * 
	 * @param userid 
	 * @param topicid
	 */
	public StudentExercise getStudenExercise(String userid,
			String topicid);
	
	
	/**
	 * 
	 * @param studentExercise 
	 * 
	 */
	public ExerciseTransaction getExerciseLastTransaction(StudentExercise studentExercise);
	
	/**
	 * 
	 * @param exerciseTransaction
	 * 
	 */
	public String getMentorName(ExerciseTransaction exerciseTransaction);
	
	
	/**
	 * @param id
	 * @return
	 */
	public ExerciseTransaction getExerciseTransactionById(long id);
	
	/**
	 * Gets student Exercise by id
	 * 
	 * @param exerciseId
	 * @return 
	 * @throws StudentExerciseNotFoundException 
	 */
	public StudentExercise getStudentExerciseById(long exerciseId) throws StudentExerciseNotFoundException;
	
	/**
	 * saves an incomplete graded exercise
	 * @param mentor
	 * @param gradedExercise
	 * @throws StudentExerciseNotFoundException 
	 * @throws NumberFormatException 
	 */
	public void addMentorExerciseTransaction(Mentor mentor, GradeExerciseBean gradedExercise) throws NumberFormatException, StudentExerciseNotFoundException;
	
	/**
	 * Marks a student Exercise as complete
	 * @param gradedExercise
	 * @throws NumberFormatException
	 * @throws StudentExerciseNotFoundException
	 */
	public void completeStudentExercise(GradeExerciseBean gradedExercise) throws NumberFormatException, StudentExerciseNotFoundException;
	
	/**
	 * 
	 * @param mentorId
	 * @param moduleId
	 * @return List<ExerciseTransaction>
	 */
	public List<ExerciseTransaction> getModuleTransactionsInMentorTemplate(
			String mentorId, String moduleId);
	/**
	 * 
	 * @param exerciseTransaction
	 */
	public void saveExerciseTransaction(ExerciseTransaction exerciseTransaction);
	
	/**
	 * 
	 * @param mentorId
	 * @param moduleId
	 * @return
	 */
	public int numberOfExercisesByMentorTemplate(long mentorId,long moduleId);
	
	/**
	 * 
	 * @param moduleId
	 * @param mentorId
	 * @return
	 */
	
	
	public int countExerciseTransactions(long moduleId, long mentorId);
	
	
}

