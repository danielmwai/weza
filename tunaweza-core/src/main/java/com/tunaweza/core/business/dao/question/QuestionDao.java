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

package com.tunaweza.core.business.dao.question;


import com.tunaweza.core.business.dao.exceptions.question.QuestionDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.question.QuestionExistsException;
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.question.Question;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface QuestionDao extends GenericDao<Question> {

	/**
	 * 
	 * @param uid
	 * @return
	 * @throws QuestionDoesNotExistException
	 */
	public Question findQuestionById(long uid)
			throws QuestionDoesNotExistException;

	/**
	 * 
	 * @param text
	 * @return
	 * @throws QuestionDoesNotExistException
	 */
	public Question findQuestionByText(String text)
			throws QuestionDoesNotExistException;

	/**
	 * 
	 * @param text
	 * @param evaluationId
	 * @return
	 * @throws QuestionDoesNotExistException
	 */
	public Question findQuestionByTextAndEvaluation(String text,
			String evaluationId) throws QuestionDoesNotExistException;

	/**
	 * 
	 * @param question
	 * @return
	 * @throws QuestionDoesNotExistException
	 */
	public Question findQuestion(Question question)
			throws QuestionDoesNotExistException;

	/**
	 * 
	 * @param question
	 * @return
	 * @throws QuestionExistsException
	 */
	public Question addQuestion(Question question)
			throws QuestionExistsException;

	/**
	 * 
	 * @return
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
	 * @param evaluationTemplate
	 * @return
	 */
	public List<Question> getAllQuestionsByTemplate(Long evaluationTemplateId);

	/**
	 * 
	 * @param topicId
	 * @return
	 */
	public List<Question> getQuestionsByTopic(long topicId);


	/**
	 * @param topicId
	 * @return
	 */
	public int getNumberOfQuestionsByTopic(long topicId,String text);

	/**
	 * @param startIndex
	 * @param pageSize
	 * @param topicId
	 * @return
	 */
	public List<Question> getPaginatedQuestionsByTopic(int startIndex,
			int pageSize, long topicId,String text);
	
	/**
	 * @param templateId
	 * @return
	 */
	public List<Question> getQuestionsInNotAssociatedWithTopic(Long templateId,String text);
	
	/**
	 * @param templateId
	 * @return
	 */
	public int getNoOfQuestionsInNotAssociatedWithTopic(Long templateId,String text);
	
	/**
	 * @param startIndex
	 * @param pageSize
	 * @param templateId
	 * @return
	 */
	public List<Question> getPaginatedQuestionsInNotAssociatedWithTopic(int startIndex, int pageSize, Long Id,String text);
	/**
	 * <p>
	 * Finds the questions belonging to the given topicId and that have part of
	 * or all of the given text
	 * </p>
	 * 
	 * @param topicId
	 *            the id of the topic to which the questions that match the
	 *            search criteria should belong to
	 * @param text
	 *            the text that questions that match the search creteria should
	 *            contain
	 * @return a {@link List} of questions that match the search creteria
	 */
	public List<Question> getQuestionsByAndText(long evaluationId, String text);

	/**
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param topicId
	 * @param startIndex
	 * @param pageSize
	 * @param searchString
	 * @return
	 */
	public List<Question> getPaginatedQuestionsByTemplateAndText(long evaluationTemplateId, int startIndex,
			int pageSize, String searchString);
	/**
	 * 
	 * @param evaluationTemplateId
	 * @param text
	 * @return
	 */
	public int getNumberOfQuestionsByTemplateAndText(long evaluationTemplateId,String text);
	public List<Question> getQuestionsByTopicAndText(long topicId,String text);

}
