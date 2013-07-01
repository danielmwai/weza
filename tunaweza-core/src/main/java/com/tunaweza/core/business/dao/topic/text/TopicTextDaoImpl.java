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

package com.tunaweza.core.business.Dao.topic.text;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "topicTextTextTextDao")
@Transactional
public class TopicTextDaoImpl extends GenericDaoImpl<TopicText> implements TopicTextDao{


	@Override
	public TopicText findTopicTextById(long tid) throws TopicTextDoesNotExistException {
		TopicText topicTextText = findById(tid);
		if(topicTextText == null){
			throw new TopicTextDoesNotExistException();
		}
		return topicTextText;
	}

	@Override
	public TopicText findTopicTextByTopicId(long topicId) throws TopicTextDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.topic.id = "
				+ topicId + "");

		TopicText topicTextText = null;

		if (query.list().size() > 0) {
			topicTextText = (TopicText) query.list().get(0);
		} else {
			throw new TopicTextDoesNotExistException("TopicText does not exist");
		}

		return topicTextText;

	}

	@Override
	public TopicText findTopicText(TopicText topicTextText) throws TopicTextDoesNotExistException {
		
		TopicText topicTextText1 = findById(topicTextText.getId());
		if(topicTextText1 == null){
			throw new TopicTextDoesNotExistException();
		}
		return topicTextText1;
	}

	@Override
	public void deleteTopicText(long uid) throws TopicTextDoesNotExistException {
		TopicText topicTextText = (TopicText) findById(uid);
		if(topicTextText ==null){
			throw new TopicTextDoesNotExistException();
		}
			deleteTopicText(topicTextText);
		
	}

	@Override
	public void deleteTopicText(TopicText topicTextText) throws TopicTextDoesNotExistException {
		try {
			delete(topicTextText);
		} catch (IllegalArgumentException e) {
			throw new TopicTextDoesNotExistException();
		}
		
	}

	@Override
	public TopicTextDao addTopicText(TopicText topicTextText) throws TopicTextExistsException {
		TopicText topicTextText1 = findById(topicTextText.getId());
		
		if(topicTextText1 != null){
			throw new TopicTextExistsException();
		}

		saveOrUpdate(topicTextText);
		return this;
	}

	@Override
	public List<TopicText> getAllTopicTexts() {
		return findAll();
	}

	@Override
	public void saveTopicText(TopicText topicTextText) {
		saveOrUpdate(topicTextText);
		
	}
	
}
