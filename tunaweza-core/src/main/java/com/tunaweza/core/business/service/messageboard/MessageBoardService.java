/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.messageboard;

import com.tunaweza.core.business.dao.exceptions.message.MessageDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.message.MessageExistsException;
import com.tunaweza.core.business.model.message.Message;
import java.util.List;

/**
 *
 * @author naistech
 */
public interface MessageBoardService {

	/**
	 * 
	 * @return a list of all messages
	 */
	public List<Message> getAllMessages();
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws MessageDoesNotExistException
	 */
	public Message findMessageById(long id) throws MessageDoesNotExistException;
	
	/**
	 * 
	 * @param message
	 * @return
	 * @throws MessageExistsException
	 */
	public Message saveMessage(Message message)throws MessageExistsException;
	
	/**
	 * 
	 * @param message
	 * @return
	 * @throws MessageDoesNotExistException
	 */
	public Message findMessage(Message message) throws MessageDoesNotExistException;
	
	/**
	 * 
	 * @param message
	 */
	public void updateMessage(Message message);
	
	
	
	

}
