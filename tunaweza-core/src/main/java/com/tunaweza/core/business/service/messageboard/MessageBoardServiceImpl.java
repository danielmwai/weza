/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.messageboard;

import com.tunaweza.core.business.dao.exceptions.message.MessageDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.message.MessageExistsException;
import com.tunaweza.core.business.dao.message.MessageBoardDao;
import com.tunaweza.core.business.model.message.Message;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */

@Service("messageBoardService")
@Transactional
public class MessageBoardServiceImpl implements MessageBoardService {

	@Autowired
	MessageBoardDao messageBoardDao;

	@Override
	public List<Message> getAllMessages() {
		List<Message> messages = messageBoardDao.getAllMessages();

		return messages;
	}

	@Override
	public Message findMessageById(long id) throws MessageDoesNotExistException {
		messageBoardDao.findMessageById(id);
		return null;
	}

	@Override
	public Message saveMessage(Message message) throws MessageExistsException {
		messageBoardDao.saveMessage(message);
		return null;
	}

	@Override
	public Message findMessage(Message message)
			throws MessageDoesNotExistException {		
		messageBoardDao.findMessage(message);
		return null;
	}

	@Override
	public void updateMessage(Message message) {
		messageBoardDao.updateMessage(message);

}
}
