
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.answer;

import com.tunaweza.core.business.dao.exceptions.answer.AnswerDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.answer.AnswerExistsException;
import com.tunaweza.core.business.dao.generic.GenericDao;

import com.tunaweza.core.business.model.answer.Answer;
import java.util.List;

/**
 *
 * @author Daniel Mwai
 */
public interface AnswerDao extends GenericDao<Answer> {

	/**
	 * 
	 * @param uid
	 * @return
	 * @throws AnswerDoesNotExistException
	 */
	public Answer findAnswerById(long uid) throws AnswerDoesNotExistException;
	
	/**
	 * 
	 * @param answer
	 * @return
	 * @throws AnswerDoesNotExistException
	 */
	public Answer findAnswer(Answer answer) throws AnswerDoesNotExistException;
	
	/**
	 * 
	 * @param text
	 * @return
	 * @throws AnswerDoesNotExistException
	 */
	public Answer findAnswerByText(String text) throws AnswerDoesNotExistException;

	/**
	 * 
	 * @param answer
	 * @return
	 * @throws AnswerExistsException
	 */
	public Answer addAnswer(Answer answer) throws AnswerExistsException;

	/**
	 * 
	 * @return
	 */
	public List<Answer> getAllAnswers();

	/**
	 * 
	 * @param questionId
	 * @return
	 */
	public List<Answer> getAllAnswersByQuestion(long questionId);
	
	/**
	 * 
	 * @param answer
	 * @return
	 */
	public void updateAnswer(Answer answer);

	

}
