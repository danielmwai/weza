/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.topic;

import com.tunaweza.core.business.dao.exceptions.topic.TopicTextDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.topic.TopicTextExistsException;
import com.tunaweza.core.business.dao.topic.text.TopicTextDao;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.topic.TopicText;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */

@Service("topicTextService")
@Transactional
public class TopicTextServiceImpl implements TopicTextService{

	@Autowired
	TopicTextDao topicTextDAO;
	
	@Override
	public void addTopicText(TopicText topicText) throws TopicTextExistsException {
		topicTextDAO.addTopicText(topicText);		
	}

	@Override
	public void saveTopicText(TopicText topicText) {
		topicTextDAO.saveTopicText(topicText);
	}

	@Override
	public TopicText getTopicTextByTopic(long topicId) throws TopicTextDoesNotExistException {
		return topicTextDAO.findTopicTextByTopicId(topicId);
	}

	@Override
	public TopicText getTopicTextById(long topicTextId) throws TopicTextDoesNotExistException {
		return topicTextDAO.findTopicTextById(topicTextId);
	}

	@Override
	public List<TopicText> getAllTopicTexts() {
		return topicTextDAO.getAllTopicTexts();
	}

	@Override
	public void deleteTopicText(TopicText topicText) throws TopicTextDoesNotExistException {
		topicTextDAO.deleteTopicText(topicText);
		
	}

	@Override
	public void deleteTopicText(long topicTextId) throws TopicTextDoesNotExistException {
		topicTextDAO.deleteTopicText(topicTextId);
	}

	@Override
	public void savingTopicText(Topic topic, String text) {
		TopicText topicText = new TopicText();
		topicText.setText(text);
		topicText.setTopic(topic);
		topicTextDAO.saveTopicText(topicText);
	}

}

