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

package com.tunaweza.core.business.service.evaluation;

import com.tunaweza.core.business.dao.exceptions.evaluation.EvaluationDoesNotExistException;
import com.tunaweza.core.business.model.evaluation.Evaluation;
import com.tunaweza.core.business.model.question.Question;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface EvaluationService {
/**
	 * 
	 * @param evaluation
	 * @throws EvaluationExistsException
	 */
	public Evaluation addEvaluation(
			Evaluation evaluation)
			throws EvaluationDoesNotExistException;

	/**
	 * 
     * @param evaluation
	 * @param id
     * @return 
	 * @throws EvaluationDoesNotExistException
	 */
	public Evaluation findEvaluation(
			Evaluation evaluation)
			throws EvaluationDoesNotExistException;

	/**
	 * 
	 * @param id
	 * @throws EvaluationDoesNotExistException
	 */
	public Evaluation findEvaluationById(long id)
			throws EvaluationDoesNotExistException;

	/**
	 * 
	 * @param evaluation
	 * @throws EvaluationDoesNotExistException
	 */
	public Evaluation findEvaluationByName(String name)
			throws EvaluationDoesNotExistException;

	/**
	 * 
	 * @return <code>EvaluationTemplate</code> list
	 * 
	 */
	public List<Evaluation> listAllEvaluations();

	/**
	 * 
	 * @param evaluation
	 * @return
	 */
	public void updateEvaluation(Evaluation evaluation);

	/**
	 * 
	 * @param moduleId
	 * @return
	 * @throws EvaluationDoesNotExistException
	 */

	public Evaluation getEvaluationByModule(long moduleId)
			throws EvaluationDoesNotExistException;

	/**
	 * 
	 * @param evaluation
	 * @return
	 */
	public List<Long> getRandomModuleEvaluationQuestions(
			Evaluation evaluation);

	
	/**
	 * @param moduleId
	 * @return
	 */
	public List<Question> getEvaluationQuestionsInModuleNotAssociatedWithTopic(
			Long moduleId) throws EvaluationDoesNotExistException ;

}
