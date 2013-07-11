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

package com.tunaweza.core.business.model.exercise;

import com.tunaweza.core.business.model.exercise.transaction.ExerciseTransactionType;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface ExerciseTransactionDao {

	/** 
	 *
	 * @return List of <code>ExerciseTransaction<code>
	 */
	public List<ExerciseTransaction> getExerciseTransactions();
	
	/** 
	 *
	 * @param id
	 * @return <code>ExerciseTransaction<code>
	 */
	public ExerciseTransaction getExerciseTransaction(Long id);
	
	/** 
	 *
	 * @param exerciseTransaction
	 */
	public void saveExerciseTransaction(ExerciseTransaction exerciseTransaction);
	
	/** 
	 *
	 * @param mentor
	 * @return List of <code>ExerciseTransaction<code>
	 */
	public List<ExerciseTransaction> getExerciseTransactionByMentor(
			Mentor mentor);
	
	/** 
	 *
	 * @param exerciseTransactionType
	 * @return List of <code>ExerciseTransaction<code>
	 */
	public List<ExerciseTransaction>
		getExerciseTransactionByExerciseTransactionType(
			ExerciseTransactionType exerciseTransactionType);
	
	/** 
	 *
	 * @param studentExercise
	 * @return List of <code>ExerciseTransaction<code>
	 */
	public List<ExerciseTransaction> getExerciseTransactionByStudentExercise(
			StudentExercise studentExercise);
	
	/** 
	 *
	 * @param studentExercise
	 * @return <code>ExerciseTransaction<code>
	 */
	public ExerciseTransaction getLastUserExerciseTransaction(
			StudentExercise studentExercise);
	
	/** 
	 *
	 * @param studentExercise
	 * @return <code>ExerciseTransaction<code>
	 */
	public ExerciseTransaction getLastUserExerciseTransactionByType(
			StudentExercise studentExercise,String transactionType);
	
}

