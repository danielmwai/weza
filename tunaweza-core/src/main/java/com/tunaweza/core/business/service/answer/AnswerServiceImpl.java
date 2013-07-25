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

package com.tunaweza.core.business.service.answer;

import com.tunaweza.core.business.dao.answer.AnswerDao;
import com.tunaweza.core.business.dao.exceptions.answer.AnswerDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.answer.AnswerExistsException;
import com.tunaweza.core.business.model.answer.Answer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel Mwai
 */
@Service("answerService")
@Transactional
public class AnswerServiceImpl implements AnswerService 
{
	@Autowired
	AnswerDao answerDao;
	
	@Override
	public Answer addAnswer(Answer answer) 
			throws AnswerExistsException{
		
		return answerDao.addAnswer(answer);
	
	}
	
	@Override
	public Answer findAnswerById(long id) 
			throws AnswerDoesNotExistException{
	
		return answerDao.findAnswerById(id);
	
	}
	
	@Override
	public Answer findAnswerByText(String text)
			throws AnswerDoesNotExistException{
	
		return answerDao.findAnswerByText(text);
	
	}
	
	@Override
	public Answer findAnswer(Answer answer)
			throws AnswerDoesNotExistException{
	
		return answerDao.findAnswer(answer);
	
	}
	
	@Override
	public List<Answer> listAllAnswers(){
	
		return answerDao.getAllAnswers();
	
	}
	
	
	@Override
	public List<Answer> listAnswersByQuestion(long questionId){
	
		return answerDao.getAllAnswersByQuestion(questionId);
	}
	
	@Override
	public void updateAnswer(Answer answer){
	
		answerDao.updateAnswer(answer);
	}

}
