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

import com.tunaweza.core.business.model.evaluation.EvaluationTransaction;
import com.tunaweza.core.business.model.evaluation.StudentEvaluation;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface EvaluationTransactionDao {

	/** 
	 *
	 * @return List of <code>EvaluationTransaction<code>
	 */
	public List<EvaluationTransaction> getEvaluationTransactions();
	
	/** 
	 *
	 * @param id
	 * @return <code>EvaluationTransaction<code>
	 */
	public EvaluationTransaction getEvaluationTransaction(Long id);
	
	/** 
	 *
	 * @param evaluationTransaction
	 * @return 
	 */
	public EvaluationTransaction saveEvaluationTransaction(EvaluationTransaction evaluationTransaction);
	
	
	
	/** 
	 *
	 * @param studentEvaluation
	 * @return List of <code>EvaluationTransaction<code>
	 */
	public List<EvaluationTransaction> getEvaluationTransactionByStudentEvaluation(
			StudentEvaluation studentEvaluation);
	
	/** 
	 *
	 * @param studentEvaluation
	 * @return <code>EvaluationTransaction<code>
	 */
	public EvaluationTransaction getLastUserEvaluationTransaction(
			StudentEvaluation studentEvaluation);
	
	
}
