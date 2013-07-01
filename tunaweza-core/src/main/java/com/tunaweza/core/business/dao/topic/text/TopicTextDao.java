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

package com.tunaweza.core.business.dao.topic.text;

import com.tunaweza.core.business.Dao.exceptions.topic.TopicTextDoesNotExistException;
import com.tunaweza.core.business.Dao.exceptions.topic.TopicTextExistsException;
import com.tunaweza.core.business.Dao.generic.GenericDao;
import com.tunaweza.core.business.model.topic.TopicText;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface TopicTextDao extends GenericDao<TopicText> {

	/**
	 * 
	 * @param uid
	 * @return
	 * @throws TopicTextDoesNotExistException
	 */
	public TopicText findTopicTextById(long uid) throws TopicTextDoesNotExistException;
	
	/**
	 * 
	 * @param namet
	 * @return
	 * @throws TopicTextDoesNotExistException
	 */
	public TopicText findTopicTextByTopicId(long topicId) throws TopicTextDoesNotExistException;

	/**
	 * 
	 * @param topicText
	 * @return
	 * @throws TopicTextDoesNotExistException
	 */
	public TopicText findTopicText(TopicText topicText) throws TopicTextDoesNotExistException;

	/**
	 * 
	 * @param uid
	 * @throws TopicTextDoesNotExistException
	 */
	public void deleteTopicText(long uid) throws TopicTextDoesNotExistException;

	/**
	 * 
	 * @param topicText
	 * @throws TopicTextDoesNotExistException
	 */
	public void deleteTopicText(TopicText topicText) throws TopicTextDoesNotExistException;

	/**
	 * 
	 * @param topicText
	 * @return
	 * @throws TopicTextExistsException
	 */
	public TopicTextDao addTopicText(TopicText topicText) throws TopicTextExistsException;

	/**
	 * 
	 * @return
	 */
	public List<TopicText> getAllTopicTexts();

	/**
	 * 
	 * @param topicText
	 */
	public void saveTopicText(TopicText topicText);
		

}
