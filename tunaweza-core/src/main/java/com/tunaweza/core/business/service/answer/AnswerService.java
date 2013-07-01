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

package com.tunaweza.core.business.service.answer;

import com.tunaweza.core.business.Dao.exceptions.answer.AnswerDoesNotExistException;
import com.tunaweza.core.business.Dao.exceptions.answer.AnswerExistsException;
import com.tunaweza.core.business.model.answer.Answer;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface AnswerService 
{
	/**
	 * 
	 * @param answer
	 * @throws AnswerExistsException
	 */
	public Answer addAnswer(Answer answer) 
			throws AnswerExistsException;
	
	/**
	 * 
	 * @param id
	 * @throws AnswerDoesNotExistException
	 */
	public Answer findAnswer(Answer answer) 
			throws AnswerDoesNotExistException;
	
	/**
	 * 
	 * @param id
	 * @throws AnswerDoesNotExistException
	 */
	public Answer findAnswerById(long id) 
			throws AnswerDoesNotExistException;
	
	/**
	 * 
	 * @param answer
	 * @throws AnswerDoesNotExistException
	 */
	public Answer findAnswerByText(String text)
			throws AnswerDoesNotExistException;
	
	/**
	 * 
	 * @return <code>Answer</code> list
	 * 
	 */
	public List<Answer> listAllAnswers();
	
	
	/**
	 * 
	 * @param answer
	 * @return <code>User</code> list
	 * 
	 */
	public List<Answer> listAnswersByQuestion(long questionId);
	
	/**
	 * 
	 * @param answer
	 * @return <code>User</code> list
	 * 
	 */
	public void updateAnswer(Answer answer);
	
	
	
}
