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

package com.tunaweza.core.business.dao.topic;

import java.math.BigInteger;
import java.util.List;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface TopicDao extends GenericDao<Topic> {

	/**
	 * @param uid
	 * @return
	 * @throws TopicDoesNotExistException
	 */
	public Topic findTopicById(long uid) throws TopicDoesNotExistException;

	/**
	 * @param name
	 * @return
	 * @throws TopicDoesNotExistException
	 */
	public Topic findTopicByName(String name) throws TopicDoesNotExistException;

	/**
	 * @param topic
	 * @return
	 * @throws TopicDoesNotExistException
	 */
	public Topic findTopic(Topic topic) throws TopicDoesNotExistException;

	/**
	 * @param uid
	 * @throws TopicDoesNotExistException
	 */
	public void deleteTopic(long uid) throws TopicDoesNotExistException;

	/**
	 * @param topic
	 * @throws TopicDoesNotExistException
	 */
	public void deleteTopic(Topic topic) throws TopicDoesNotExistException;

	/**
	 * @param topic
	 * @return
	 * @throws TopicExistsException
	 */
	public Topic addTopic(Topic topic) throws TopicExistsException;

	/**
	 * @return
	 */
	public List<Topic> getAllTopics();

	/**
	 * @param topic
	 */
	public void saveTopic(Topic topic);

	/**
	 * @param ModuleId
	 * @return
	 */
	public List<Topic> getAllTopicsByModule(Long moduleId);

	/**
	 * @return
	 */
	public int getMaxLevel();

	/**
	 * 
	 * @param moduleId
	 */
	public int getNumberOfTopicsByModule(long moduleId);

	/**
	 * @param id
	 * @return
	 */
	public List<Topic> findAllTopicsByModule(long id);

	/**
	 * @param id
	 * @return
	 */
	public List<Topic> findAllParentTopicsByModule(long id);

	/**
	 * Gets all topic that are direct children of a module
	 * 
	 * @param ModuleId
	 * @return
	 */
	public List<Topic> getAllTopicsDirectChildOfModule(Long moduleId);

	/**
	 * Gets all topics that are children of a topic
	 * 
	 * @param ModuleId
	 * @return
	 */
	public List<Topic> getAllChildrenTopicsOfTopic(Long topicId);

	/**
	 * @param moduleId
	 * @param topicId
	 * @return
	 */
	public List<Topic> getAllChildrenTopicsOfTopic(Long moduleId, Long topicId);

	/**
	 * @param id
	 * @return
	 */
	public int getMaxLevelByModule(long id);

	/**
	 * @param currentLevel
	 * @param futureLevel
	 * @param moduleId
	 * @param topicId
	 */
	public void arrangeLevels(long currentLevel, long futureLevel,
			long moduleId, long topicId);

	/**
	 * @param currentLevel
	 * @param futureLevel
	 * @param moduleId
	 * @param topicId
	 * @param size
	 */
	public void arrangeLevels(long currentLevel, long futureLevel,
			long moduleId, long topicId, int size);

	/**
	 * @param moduleId
	 * @return
	 */
	public List<Double> getPercentageVariables(long moduleId);

	/**
	 * @param startIndex
	 * @param pageSize
	 * @param moduleId
	 * @return
	 */
	public List<Topic> getPaginatedTopicsList(int startIndex, int pageSize,
			long moduleId,String searchString);

	/**
	 * @param moduleId
	 * @return
	 */
	public BigInteger getNumberOfEnabledTopics(long moduleId);

	/**
	 * @return
	 */
	public int getNumberOfAllowedQuestions(long topicId);

	/**
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<Topic> getAllExercisesByModule(Long moduleId);

	/**
	 * <p>
	 * Finds the list of topics with part or all of the given name and having
	 * the given moduleId
	 * </p>
	 * 
	 * @param name
	 *            the name of the topic to find
	 * @param moduleId
	 *            the moduleId of the topic to find
	 * @return a {@link List} of topics matching the search criteria
	 */
	public List<Topic> findTopicByNameAndModuleId(String name, Long moduleId);
	/**
	 * <p>
	 * 
	 * </p>
	 * @param name
	 * @param moduleId
	 * @return
	 */
	public int getNumberOfTopicByNameAndModule(String name,Long moduleId);
	
	
	public BigInteger getNumberOfEnabledTopicsNoSession(long moduleId, String companyDbName);
	
	public List<Topic> getAllTopicsByModuleNoSession(String companyDbName, String moduleId);
	
	public String getTopicTextById(String companyDbName, String topicId);

}
