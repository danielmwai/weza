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

package com.tunaweza.core.business.dao.answer;
import com.tunaweza.core.business.dao.exceptions.answer.AnswerDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.answer.AnswerExistsException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.answer.Answer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Repository(value = "answerDAO")
@Transactional
public class AnswerDaoImpl extends GenericDaoImpl<Answer> implements AnswerDao {

	@Override
	public Answer findAnswerById(long uid) throws AnswerDoesNotExistException {
		Answer answer = findById(uid);
		if (answer == null) {
			throw new AnswerDoesNotExistException();
		}

		return answer;
	}

	@Override
	public Answer findAnswerByText(String text) throws AnswerDoesNotExistException{
	
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.text = ?");// + text	+ "'");
		
		query.setString(0, text);

		Answer answer = null;

		if (query.list().size() > 0) {
			answer = (Answer) query.list().get(0);
		} else {
			throw new AnswerDoesNotExistException("Answer with text : " + text
					+ " does not exist");
		}

		return answer;
	
	}
	
	@Override
	public Answer findAnswer(Answer answer) throws AnswerDoesNotExistException {

		Answer answer1 = findById(answer.getId());
		if (answer1 == null) {
			throw new AnswerDoesNotExistException();
		}
		return answer1;
	}

	
	@Override
	public Answer addAnswer(Answer answer) throws AnswerExistsException {
		//Session session = (Session) getEntityManager().getDelegate();
		long questionId = answer.getQuestion().getId();
		
		Answer answer1=null;
		try {
			answer1 = findAnswerByText(answer.getText());
		} catch (AnswerDoesNotExistException e) 
		{			
			//e.printStackTrace();
		}
		
		if(answer1 != null){
			if (answer1.getQuestion().getId() == questionId )
			throw new AnswerExistsException();
		}
		
		Answer savedAnswer = saveOrUpdate(answer);
		//Answer savedAnswer = saveAndReattach(answer);

		return savedAnswer;
	}

	@Override
	public List<Answer> getAllAnswers() {
		return findAll();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Answer> getAllAnswersByQuestion(long questionId) {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.question.id = '"
				+ questionId + "' ORDER BY i.id ASC");

		List<Answer> answers = null;

		if (query.list().size() > 0) {
			answers = query.list();
		} else {

		}

		return answers;
	}

	@Override
	public void updateAnswer(Answer answer){
	
		saveOrUpdate(answer);
	
	}

	
}