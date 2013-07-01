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

package com.tunaweza.core.business.service.question;

import com.tunaweza.core.business.Dao.exceptions.question.QuestionDoesNotExistException;
import com.tunaweza.core.business.Dao.exceptions.question.QuestionExistsException;
import com.tunaweza.core.business.model.answer.Answer;
import com.tunaweza.core.business.model.question.Question;
import java.util.ArrayList;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface QuestionService {
	/**
	 * 
	 * @param question
	 * @throws QuestionExistsException
	 */
	public Question addQuestion(Question question, ArrayList<Answer> choices)
			throws QuestionExistsException;

	/**
	 * 
	 * @param id
	 * @throws QuestionDoesNotExistException
	 */
	public Question findQuestion(Question question)
			throws QuestionDoesNotExistException;

	/**
	 * 
	 * @param id
	 * @throws QuestionDoesNotExistException
	 */
	public Question findQuestionById(long id)
			throws QuestionDoesNotExistException;

	/**
	 * 
	 * @param question
	 * @throws QuestionDoesNotExistException
	 */
	public Question findQuestionByText(String text)
			throws QuestionDoesNotExistException;

	/**
	 * 
	 * @return <code>Question</code> list
	 * 
	 */
	public List<Question> getAllQuestions();

	/**
	 * 
	 * @param question
	 * @return
	 */
	public void updateQuestion(Question question);

	/**
	 * 
	 * @param evaluation
	 * @return
	 */
	public List<Question> getAllQuestionsBy(Long evaluationTemplateId);

	/**
	 * 
	 * @param topicId
	 * @return
	 */
	public List<Question> getQuestionsByTopic(long topicId);

	/**
	 * @param startIndex
	 * @param pageSize
	 * @param topicId
	 * @return
	 */
	public List<Question> getPaginatedQuestionsByTopic(int startIndex,
			int pageSize, long topicId, String text);

	/**
	 * @param topicId
	 * @return
	 */
	public int getNumberOfQuestionsByTopic(long topicId, String text);

	/**
	 * @param Id
	 * @return
	 */
	public List<Question> getQuestionsInNotAssociatedWithTopic(
			Long templateId, String text);

	/**
	 * @param Id
	 * @return
	 */
	public int getNoOfQuestionsInNotAssociatedWithTopic(
			Long Id, String text);

	/**
	 * @param startIndex
	 * @param pageSize
	 * @param Id
	 * @return
	 */
	public List<Question> getPaginatedQuestionsInNotAssociatedWithTopic(
			int startIndex, int pageSize, Long Id, String text);

	/**
	 * 
	 * @param evaluationId
	 * @param text
	 * @return
	 */
	public int getNumberOfQuestionsByAndText(long evaluationTemplateId,
			String text);

	/**
	 * 
	 * @param evaluationId
	 * @param text
	 * @return
	 */
	public List<Question> getQuestionsByAndText(
			long evaluationId, String text);

	/**
	 * 
	 * @param evaluationId
	 * @param startIndex
	 * @param pageSize
	 * @param searchString
	 * @return
	 */
	public List<Question> getPaginatedQuestionsByAndText(
			long evaluationId, int startIndex, int pageSize,
			String searchString);

	public List<Question> getQuestionsByTopicAndText(long topicId, String text);

	/**
	 * Deletes a question
	 * 
	 * @param question
	 */
	public void deleteQuestion(Question question);

}