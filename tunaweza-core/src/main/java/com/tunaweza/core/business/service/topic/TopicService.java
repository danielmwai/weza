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

package com.tunaweza.core.business.service.topic;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface TopicService {

	/**
	 * @param topic
	 * @return
	 * @throws TopicExistsException
	 */
	public Topic addTopic(Topic topic) throws TopicExistsException;

	/**
	 * 
	 * @param topic
	 */
	public void saveTopic(Topic topic);

	/**
	 * 
	 * @param name
	 * @return
	 * @throws TopicDoesNotExistException
	 */
	public Topic getTopicByName(String name) throws TopicDoesNotExistException;

	/**
	 * 
	 * @param topicId
	 * @return
	 * @throws TopicDoesNotExistException
	 */
	public Topic getTopicById(long topicId) throws TopicDoesNotExistException;

	/**
	 * Enables or disables a topic.
	 * 
	 * @param topicId
	 *            The topic id of the <code>Topic</code> to enable.
	 * @return <code>true</code> if the <code>Topic</code> has been enabled or
	 *         was already enabled or <code>false</code> if the topic could not
	 *         be enabled.
	 * @throws TopicDoesNotExistException
	 *             Thrown if a <code>Topic</code> with the given id does not
	 *             exist.
	 */
	public boolean changeStatusTopic(String topicId)
			throws TopicDoesNotExistException;

	/**
	 * 
	 * @return
	 */
	public List<Topic> getAllTopics();

	/**
	 * 
	 * @param topic
	 * @throws TopicDoesNotExistException
	 */
	public void deleteTopic(Topic topic) throws TopicDoesNotExistException;

	/**
	 * 
	 * @param topicId
	 * @throws TopicDoesNotExistException
	 */
	public void deleteTopic(long topicId) throws TopicDoesNotExistException;

	/**
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<Topic> getAllTopicsByModule(Long moduleId);

	/**
	 * 
	 * @return
	 */
	public int getMaxLevel();

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<ListTopicBean> listTopicsByModule(long id);

	public List<ListOfTopicBean> listTopicsBy_Module(long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<ListTopicBean> listParentTopicsByModule(long id);

	/**
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<TopicBean> getAllTopicsByModuleJson(Long moduleId, HttpServletRequest request);

	/**
	 * 
	 * @param addTopicBean
	 * @param module
	 * @return
	 * @throws TopicExistsException
	 */
	public void savingTopic(AddTopicBean addTopicBean, Module module)
			throws TopicExistsException;

	/**
	 * 
	 * @param editTopicBean
	 * @param module
	 * @return
	 * @throws TopicExistsException
	 */
	public String savingTopic(EditTopicBean editTopicBean, Module module,
			String id) throws TopicExistsException;

	/**
	 * 
	 * @param id
	 * @return
	 */
	public int getMaxLevelByModule(long id);

	/**
	 * 
	 * @return
	 */
	public List<Topic> getAllParentTopics(long id);

	/**
	 * Gets the status of an exercise
	 * 
	 * @param user
	 *            , exercise
	 * @return Status of exercise
	 */
	public Status getStatusOfExercise(User user, Topic exercise);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Topic> listAllExercises();

	/**
	 * 
	 * @param module
	 * @return
	 */
	public List<Topic> listAllExercises(Module module);

	/**
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<Double> getPercentageVariables(long moduleId);

	/**
	 * get the status of exercises in a module
	 * 
	 * @param moduleId
	 * @return
	 */
	List<RefreshTreeBean> getStatusOfExercisesByModule(Long moduleId);
	
	/**
	 * 
	 * @param startIndex
	 *            The start index
	 * @param pageSize
	 *            The number of <code>Topic</code>s to return.
	 * @param moduleId
	 * 			  The module id of <code>Topic</code>s to return
	 * @return A <code>List</code> with <code>Topic Object</code>s.
	 */
	public List<Topic> getPaginatedTopics(int startIndex, int pageSize, long moduleId,String searchString);
	
	/**
	 * 
	 * @param moduleId
	 * @return
	 */
	public BigInteger getNumberOfEnabledTopics(long moduleId) ;

	/**
	 * 
	 * @param moduleId
	 * @return
	 */
	public int getNumberOfTopicsByModule(long moduleId);
	

	/**
	 * 
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
	 * 
	 * </p>
	 * @param name
	 * @param moduleId
	 * @return
	 */
	public int getNumberOfTopicByNameAndModule(String name,Long moduleId);
	
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
	public List<ListTopicBean> findTopicByNameAndModuleId(String name, Long moduleId);
	
	
	///////////
	
	public BigInteger getNumberOfEnabledTopicsNoSession(long moduleId, String companyDbName);
}

