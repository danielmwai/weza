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

package com.tunaweza.core.business.service.topic.lastaccessed;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Service("lastAccessedTopicService")
@Transactional
public class LastAccessedTopicServiceImpl implements LastAccessedTopicService
{

	@Autowired
	private LastAccessedTopicDao lastAccessedTopicDao;

	@Autowired
	private UserService userService;

	@Autowired
	private TopicService topicService;

	@Autowired
	ModuleService moduleService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.topic.LastAccessedTopicService#
	 * saveLastAccessedTopicInModule
	 * (com.jjpeople.jjteach.orm.entities.user.User,
	 * com.jjpeople.jjteach.orm.entities.topic.Topic)
	 */
	public void saveOrUpdateLastAccessedTopicInModule(User user, Topic topic)
			throws LastAccessedTopicException
	{
		if (user == null)
			throw new LastAccessedTopicException("User cannot be null.");

		if (topic == null)
			throw new LastAccessedTopicException("Topic cannot be null.");

		Date lastAccessedDate = Calendar.getInstance().getTime();
		
		Module module = topic.getModule();

		LastAccessedTopicInModule lastAccessedTopicInModule = lastAccessedTopicDao
				.getLastAccessedTopicInModuleEntity(user, module);

		if (lastAccessedTopicInModule != null)
		{
			lastAccessedTopicInModule.setTopic(topic);
			lastAccessedTopicInModule.setLastAccessedDate(lastAccessedDate);

			lastAccessedTopicDao
					.updateLastAccessedTopicInModule(lastAccessedTopicInModule);
		}
		else
		{
			LastAccessedTopicPK lastAccessedTopicPK = new LastAccessedTopicPK();
			lastAccessedTopicPK.setModule(module);
			lastAccessedTopicPK.setUser(user);

			lastAccessedTopicInModule = new LastAccessedTopicInModule();
			lastAccessedTopicInModule
					.setLastAccessedTopicPK(lastAccessedTopicPK);
			lastAccessedTopicInModule.setTopic(topic);
			lastAccessedTopicInModule.setLastAccessedDate(lastAccessedDate);

			lastAccessedTopicDao
					.saveLastAccessedTopicInModule(lastAccessedTopicInModule);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.topic.LastAccessedTopicService#
	 * saveLastAccessedTopicInModule(long, long)
	 */
	public void saveOrUpdateLastAccessedTopicInModule(long userId, long topicId)
			throws LastAccessedTopicException
	{
		try
		{
			User user = userService.getUserById(userId);

			Topic topic = topicService.getTopicById(topicId);

			saveOrUpdateLastAccessedTopicInModule(user, topic);

		}
		catch (UserDoesNotExistException e)
		{
			throw new LastAccessedTopicException(
					"Could not save last accessed topic as User with id "
							+ userId + " does not exist.");
		}
		catch (TopicDoesNotExistException e)
		{
			throw new LastAccessedTopicException(
					"Could not save last accessed topic as Topic with id "
							+ topicId + " does not exist.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.topic.LastAccessedTopicService#
	 * getLastAccessedTopicInModule(com.jjpeople.jjteach.orm.entities.user.User,
	 * com.jjpeople.jjteach.orm.entities.module.Module)
	 */
	public Topic getLastAccessedTopicInModule(User user, Module module)
			throws LastAccessedTopicException
	{
		if (user == null)
			throw new LastAccessedTopicException("User cannot be null.");

		if (module == null)
			throw new LastAccessedTopicException("Module cannot be null.");

		Topic topic = lastAccessedTopicDao
				.getLastAccessedTopicByUserIdModuleId(user, module);

		return topic;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.topic.LastAccessedTopicService#
	 * getLastAccessedTopicInModule(long, long)
	 */
	public Topic getLastAccessedTopicInModule(long userId, long moduleId)
			throws LastAccessedTopicException
	{
		try
		{
			User user = userService.getUserById(userId);

			Module module = moduleService.getModuleById(moduleId);

			return getLastAccessedTopicInModule(user, module);

		}
		catch (UserDoesNotExistException e)
		{
			throw new LastAccessedTopicException(
					"Could not get last accessed topic as User with id "
							+ userId + " does not exist.");
		}
		catch (ModuleDoesNotExistException e)
		{
			throw new LastAccessedTopicException(
					"Could not get last accessed topic as Module with id "
							+ moduleId + " does not exist.");
		}
	}

}
