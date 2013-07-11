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

import com.tunaweza.core.business.dao.exceptions.topic.TopicExistsException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.topic.Topic;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository
@Transactional
public class TopicDaoImpl extends GenericDaoImpl<Topic> implements TopicDao {
@Autowired
SessionFactory sessionFactory;
	
        @Override
	public Topic findTopicById(long tid)  {
		Topic topic = (Topic) findById(tid);
		if (topic == null) {
                    try {
                        throw new TopicExistsException();
                    } catch (TopicExistsException ex) {
                        Logger.getLogger(TopicDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}

		return topic;
	}

	@Override
	public Topic findTopicByName(String name)  {


		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.name = '" + name
				+ "'");

		Topic topic = null;

		if (query.list().size() > 0) {
			topic = (Topic) query.list().get(0);
		} else {
//			throw new TopicExistsException("Topic with name : " + name
//					+ " does not exist");
		}

		return topic;

	}

	@Override
	public Topic findTopic(Topic topic)  {

		Topic topic1 = (Topic) findById(topic.getId());
		if (topic1 == null) {
                    try {
                        throw new TopicExistsException();
                    } catch (TopicExistsException ex) {
                        Logger.getLogger(TopicDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
		return topic1;
	}

	@Override
	public void deleteTopic(long uid)  {
		Topic topic = (Topic) findById(uid);
		if (topic == null) {
                    try {
                        throw new TopicExistsException();
                    } catch (TopicExistsException ex) {
                        Logger.getLogger(TopicDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
            try {
                deleteTopic(topic);
            } catch (/**TopicExists**/Exception ex) {
                Logger.getLogger(TopicDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

	}

	@Override
	public void deleteTopic(Topic topic)  {
		try {
			delete(topic);
		} catch (IllegalArgumentException e) {
                    try {
                        throw new TopicExistsException();
                    } catch (TopicExistsException ex) {
                        Logger.getLogger(TopicDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}

	}

	@Override
	public Topic addTopic(Topic topic) throws TopicExistsException {

		long level = topic.getLevel();
		long moduleId = topic.getModule().getId();

		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT * FROM topics t WHERE t.level = " + level
						+ " AND t.id_module = " + moduleId).addEntity(
				Topic.class);

		if (query.list().size() > 0) {
			throw new TopicExistsException();
		}

		saveOrUpdate(topic);

		Query query1 = sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT * FROM topics t WHERE t.level = " + level
						+ " AND t.id_module = " + moduleId).addEntity(
				Topic.class);

		return (Topic) query1.list().get(0);
	}

	@Override
	public List<Topic> getAllTopics() {
		return findAll();
	}

	@Override
	public void saveTopic(Topic topic) {
		saveOrUpdate(topic);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.Dao.TopicDao#getAllTopicsByModule(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Topic> getAllTopicsByModule(Long moduleId) {

		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName()
				+ " i WHERE i.module.id = ? ORDER BY i.id ASC");
		query.setLong(0, moduleId);

		List<Topic> topics = null;

		if (query.list().size() > 0) {
			topics = query.list();
		} else {
			topics=new ArrayList<Topic>();
		}

		return topics;
	}

	@Override
	public int getMaxLevel() {

		Query queryCount = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT COUNT(*) FROM topics");
		java.math.BigInteger max = BigInteger.valueOf(0);
		java.math.BigInteger count = (java.math.BigInteger) queryCount.list()
				.get(0);
		if (count.intValue() > 0) {
			Query query = sessionFactory.getCurrentSession()
					.createSQLQuery("SELECT MAX(level) FROM topics");
			max = (java.math.BigInteger) query.list().get(0);
		}
		return max.intValue() > 0 ? max.intValue() : 0;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Topic> findAllTopicsByModule(long id) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT * FROM topics t WHERE t.id_module = " + id
						+ " ORDER BY t.id ASC").addEntity(Topic.class);

		List<Topic> topics = null;

		if (query.list().size() > 0) {
			topics = query.list();
		}
		return topics;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Topic> findAllParentTopicsByModule(long id) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT * FROM topics t WHERE t.id_module = " + id
						+ " AND t.parent_id = 0 ORDER BY t.id ASC").addEntity(
				Topic.class);

		List<Topic> topics = null;

		if (query.list().size() > 0) {
			topics = query.list();
		}
		return topics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.Dao.TopicDao#getAllTopicsDirectChildOfModule(java
	 * .lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Topic> getAllTopicsDirectChildOfModule(Long moduleId) {


		Query query = sessionFactory.getCurrentSession()
				.createQuery("SELECT i FROM "
						+ getDomainClass().getName()
						+ " i WHERE i.parentId = 0 AND i.enabled = 1 AND i.module.id = '"
						+ moduleId + "' ORDER BY i.level ASC");

		List<Topic> topics = null;

		if (query.list().size() > 0) {
			topics = query.list();
		} else {

		}

		return topics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.Dao.TopicDao#getAllChildrenTopicsOfTopic(java.lang
	 * .Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Topic> getAllChildrenTopicsOfTopic(Long topicId) {


		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName()
				+ " i WHERE i.enabled = 1 AND i.parentId =  '" + topicId
				+ "' ORDER BY i.level ASC");

		List<Topic> topics = null;

		if (query.list().size() > 0) {
			topics = query.list();
		} else {

		}

		return topics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.Dao.TopicDao#getAllChildrenTopicsOfTopic(java.lang
	 * .Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Topic> getAllChildrenTopicsOfTopic(Long moduleId, Long topicId) {


		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT * FROM topics t" + " WHERE t.id_module = " + moduleId
						+ " AND t.parent_id =  '" + topicId
						+ "' ORDER BY t.level ASC").addEntity(Topic.class);

		List<Topic> topics = null;

		if (query.list().size() > 0) {
			topics = query.list();
		} else {

		}

		return topics;
	}

	@Override
	public int getMaxLevelByModule(long id) {

		Query queryCount = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT COUNT(*) FROM topics t "
						+ "WHERE t.id_module=" + id);
		java.math.BigInteger max = BigInteger.valueOf(0);
		java.math.BigInteger count = (java.math.BigInteger) queryCount.list()
				.get(0);
		if (count.intValue() > 0) {
			Query query = sessionFactory.getCurrentSession()
					.createSQLQuery("SELECT MAX(level) FROM topics t "
							+ "WHERE t.id_module=" + id);
			max = (java.math.BigInteger) query.list().get(0);
		}
		return max.intValue() > 0 ? max.intValue() : 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void arrangeLevels(long currentLevel, long futureLevel,
			long moduleId, long topicId) {

		Topic currentTopic = new Topic();
		try {
			currentTopic = findTopicById(topicId);
		} catch (Exception e) {
		}
		currentTopic.setLevel(getMaxLevel() + 1);
		saveOrUpdate(currentTopic);


		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT * FROM topics t WHERE t.id_module = " + moduleId
						+ " ORDER BY t.level ASC").addEntity(Topic.class);
		List<Topic> topics = new ArrayList<Topic>();

		if (query.list().size() > 0) {
			topics = query.list();
			for (Topic topic : topics) {
				if (currentLevel > futureLevel) {
					if (topic.getLevel() >= futureLevel
							&& topic.getLevel() < currentLevel) {
						topic.setLevel(topic.getLevel() + 1);
						saveOrUpdate(topic);
					}
				} else {
					if (topic.getLevel() <= futureLevel
							&& topic.getLevel() > currentLevel) {
						topic.setLevel(topic.getLevel() - 1);
						saveOrUpdate(topic);
					}
				}
			}
		}

	}

	public void arrangeLevels(long currentLevel, long futureLevel,
			long moduleId, long topicId, int size) {

		Topic currentTopic = new Topic();
		try {
			currentTopic = findTopicById(topicId);
		} catch (Exception e) {
		}
		currentTopic.setLevel(getMaxLevel() + 1);
		saveOrUpdate(currentTopic);


		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT * FROM topics t WHERE t.id_module = " + moduleId
						+ " ORDER BY t.level ASC").addEntity(Topic.class);
		List<Topic> topics = new ArrayList<Topic>();

		currentLevel = currentLevel + size;
		if (query.list().size() > 0) {
			topics = query.list();
			for (Topic topic : topics) {
				if (topic.getLevel() >= futureLevel
						&& topic.getLevel() < currentLevel) {
					topic.setLevel(topic.getLevel() + size + 1);
					saveOrUpdate(topic);
				}

			}

		}
	}

	@Override
	public List<Double> getPercentageVariables(long moduleId) {

		java.math.BigInteger countTopics = BigInteger.valueOf(0);
		java.math.BigInteger countExercises = BigInteger.valueOf(0);

		Query queryTopics = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT COUNT(*) FROM topics t WHERE"
						+ " t.id_module=? AND t.is_exercise=? AND t.enabled=?");
		queryTopics.setLong(0, moduleId);
		queryTopics.setBoolean(1, false);
		queryTopics.setBoolean(2, true);
		countTopics = (java.math.BigInteger) queryTopics.list().get(0);

		Query queryExercises = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT COUNT(*) FROM topics t WHERE"
						+ " t.id_module=? AND t.is_exercise=? AND t.enabled=?");
		queryExercises.setLong(0, moduleId);
		queryExercises.setBoolean(1, true);
		queryExercises.setBoolean(2, true);
		countExercises = (java.math.BigInteger) queryExercises.list().get(0);

		/*
		 * the Query object returned is a list in our case, a list of one in
		 * each case
		 */

		List<Double> percentageVariables = new ArrayList<Double>();

		percentageVariables.add(countTopics.doubleValue());
		percentageVariables.add(countExercises.doubleValue());

		return percentageVariables;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Topic> getPaginatedTopicsList(int startIndex, int pageSize,
			long moduleId, String searchString) {
		String sql = null;
		if (!searchString.isEmpty()) {
			sql = "SELECT * FROM topics t WHERE t.id_module = " + moduleId
					+ " AND t.topic_name LIKE '%" + searchString
					+ "%' ORDER BY t.id ASC";
		} else {
			sql = "SELECT * FROM topics t WHERE t.id_module = " + moduleId
					+ " ORDER BY t.id ASC";
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Topic.class);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		return query.list();
	}

	@Override
	public BigInteger getNumberOfEnabledTopics(long moduleId) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT count(*) FROM topics t "
				+ "WHERE t.id_module = " + moduleId + " AND t.enabled=1");
		return (java.math.BigInteger) query.list().get(0);
	}

	@Override
	public int getNumberOfAllowedQuestions(long topicId) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT t.eval_questions_limit "
				+ "FROM topics t WHERE t.id = " + topicId);
		return (Integer) query.list().get(0);

	}

	@Override
	public int getNumberOfTopicsByModule(long moduleId) {

		Query query = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT COUNT(*) FROM topics t WHERE t.id_module = ?");

		query.setLong(0, moduleId);
		java.math.BigInteger count = (java.math.BigInteger) query.list().get(0);
		return count.intValue();
	}

	@Override
	public List<Topic> getAllExercisesByModule(Long moduleId) {

		Query query = sessionFactory.getCurrentSession()
				.createQuery("SELECT i FROM "
						+ getDomainClass().getName()
						+ " i WHERE i.module.id = ? AND is_exercise =? ORDER BY i.id ASC");
		query.setLong(0, moduleId);
		query.setBoolean(1, true);

		List<Topic> topics = null;

		if (query.list().size() > 0) {
			topics = query.list();
		} else {

		}

		return topics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.Dao.TopicDao#findTopicByNameAndModuleId(java.lang
	 * .String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> findTopicByNameAndModuleId(String name, Long moduleId) {
		/*
		 * String queryStr = "SELECT i FROM " + getDomainClass().getName() +
		 * " WHERE i.module.id=? AND i.name LIKE '%?%' ORDER BY i.id ASC";
		 */
		String sql = null;
		if (!name.isEmpty()) {
			name = "%" + name + "%";
			sql = "SELECT * FROM topics t WHERE t.id_module=? AND t.topic_name LIKE '"
					+ name + "' ORDER BY t.id ASC";
		} else {
			sql = "SELECT * FROM topics t WHERE t.id_module=?  ORDER BY t.id ASC";
		}
		System.out.println("QUERY::::::::::::::::::::>>>" + sql);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Topic.class);
		query.setLong(0, moduleId);
		// query.setString(1, name);
		return query.list().size() > 0 ? (List<Topic>) query.list() : null;

	}

	@Override
	public int getNumberOfTopicByNameAndModule(String name, Long moduleId) {
		/*
		 * String queryStr = "SELECT i FROM " + getDomainClass().getName() +
		 * " WHERE i.module.id=? AND i.name LIKE '% ? %' ORDER BY i.id ASC";
		 */
		String sql = null;

		if (!name.isEmpty()) {
			name = "%" + name + "%";
			sql = "SELECT * FROM topics t WHERE t.id_module=? AND t.topic_name LIKE '"
					+ name + "' ORDER BY t.id ASC";
		} else {
			sql = "SELECT * FROM topics t WHERE t.id_module=? ORDER BY t.id ASC";
		}
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Topic.class);
		query.setLong(0, moduleId);

		// query.setString(1, name);
		return query.list().size() > 0 ? query.list().size() : 0;
	}
	
	
	/////////////
	
	@Override
	public BigInteger getNumberOfEnabledTopicsNoSession(long moduleId, String companyDbName) {
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT count(*) FROM " + companyDbName + ".topics t "
				+ "WHERE t.id_module = " + moduleId + " AND t.enabled=1");
		return (java.math.BigInteger) query.list().get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Topic> getAllTopicsByModuleNoSession(String companyDbName, String moduleId) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM " + companyDbName
				+ ".topics i WHERE i.id_module = ? ORDER BY i.id ASC").addEntity(Topic.class);
		query.setString(0, moduleId);

		List<Topic> topics = null;

		if (query.list().size() > 0) {
			topics = query.list();
		} else {
			topics=new ArrayList<Topic>();
		}

		return topics;
	}
	
	@Override
	public String getTopicTextById(String companyDbName, String topicId) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT text FROM " + companyDbName
				+ ".topic_text i WHERE i.id_topic = ?");
		query.setString(0, topicId);
		if (query.list().size() > 0) {
			return (String)query.list().get(0);
		}
		return null;
	}

}