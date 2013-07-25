/*
 * The MIT License
 *
 * Copyright 2013 naistech.
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

import com.tunaweza.core.business.dao.exceptions.question.QuestionDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.question.QuestionExistsException;
import com.tunaweza.core.business.dao.question.QuestionDao;
import com.tunaweza.core.business.model.answer.Answer;
import com.tunaweza.core.business.model.question.Question;
import com.tunaweza.core.business.service.answer.AnswerService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel Mwai
 */
@Service("questionService")
@Transactional
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	QuestionDao questionDao;

	@Autowired
	AnswerService answerService;

	@Override
	public Question addQuestion(Question question, ArrayList<Answer> choices)
			throws QuestionExistsException {
		Question savedQuestion;
		question.setAnswers(choices);
		savedQuestion = questionDao.addQuestion(question);
		
		for (Answer a : choices) {
			try {
				answerService.addAnswer(a);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				/*try {
					questionDao.delete(questionDao.findQuestion(question));
					return null;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return null;
				}*/
			}
		}
		return savedQuestion;

	}

	@Override
	public Question findQuestionById(long id)
			throws QuestionDoesNotExistException {

		return questionDao.findQuestionById(id);

	}

	@Override
	public Question findQuestionByText(String text)
			throws QuestionDoesNotExistException {

		return questionDao.findQuestionByText(text);

	}

	@Override
	public Question findQuestion(Question question)
			throws QuestionDoesNotExistException {

		return questionDao.findQuestion(question);

	}

	@Override
	public List<Question> getAllQuestions() {

		return questionDao.getAllQuestions();

	}

	@Override
	public void updateQuestion(Question question) {

		questionDao.updateQuestion(question);
	}

	@Override
	public List<Question> getAllQuestionsBy(Long evaluationTemplateId) {

		return questionDao.getAllQuestionsBy(evaluationTemplateId);
	}

	public List<Question> getQuestionsByTopic(long topicId) {
		return questionDao.getQuestionsByTopic(topicId);

	}

	public int getNumberOfQuestionsByTopic(long topicId, String text) {

		return questionDao.getNumberOfQuestionsByTopic(topicId, text);
	}

	public List<Question> getPaginatedQuestionsByTopic(int startIndex,
			int pageSize, long topicId, String text) {

		return questionDao.getPaginatedQuestionsByTopic(startIndex, pageSize,
				topicId, text);
	}

	public List<Question> getQuestionsInNotAssociatedWithTopic(
			Long templateId, String text) {
		return questionDao.getQuestionsInNotAssociatedWithTopic(
				templateId, text);
	}

	public int getNoOfQuestionsInNotAssociatedWithTopic(
			Long templateId, String text) {
		return questionDao.getNoOfQuestionsInNotAssociatedWithTopic(
				templateId, text);
	}

	public List<Question> getPaginatedQuestionsInNotAssociatedWithTopic(
			int startIndex, int pageSize, Long templateId, String text) {
		return questionDao
				.getPaginatedQuestionsInNotAssociatedWithTopic(
						startIndex, pageSize, templateId, text);
	}

	@Override
	public int getNumberOfQuestionsByAndText(long evaluationTemplateId,
			String text) {
		return questionDao.getNumberOfQuestionsByAndText(
				evaluationTemplateId, text);
	}

	@Override
	public List<Question> getQuestionsByAndText(
			long evaluationTemplateId, String text) {
		return questionDao.getQuestionsByAndText(evaluationTemplateId,
				text);
	}

	@Override
	public List<Question> getPaginatedQuestionsByAndText(
			long evaluationTemplateId, int startIndex, int pageSize,
			String searchString) {
		return questionDao.getPaginatedQuestionsByAndText(
				evaluationTemplateId, startIndex, pageSize, searchString);
	}

	@Override
	public List<Question> getQuestionsByTopicAndText(long topicId, String text) {

		return questionDao.getQuestionsByTopicAndText(topicId, text);
	}

	@Override
	public void deleteQuestion(Question question) {
		questionDao.delete(question);
	}

   
}
