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

import com.tunaweza.core.business.Dao.exceptions.module.ModuleDoesNotExistException;
import com.tunaweza.core.business.Dao.exceptions.topic.TopicNotFoundExistException;
import com.tunaweza.core.business.Dao.exceptions.topic.TopicExistsException;
import com.tunaweza.core.business.Dao.file.FileDao;
import com.tunaweza.core.business.Dao.topic.TopicDao;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.file.File;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.status.Status;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.topic.TopicText;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.exercise.ExerciseTransaction;
import java.math.BigInteger;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.engine.jdbc.NonContextualLobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Service("topicService")
@Transactional
public class TopicServiceImpl implements TopicService {

	@Autowired
	private FileDao fileDao;

	@Autowired
	TopicDao topicDao;

	@Autowired
	SolutionService solutionService;

	@Autowired
	TopicTextService topicTextService;

	@Autowired
	UserCastService userCastService;

	@Autowired
	CompanyService companyService;

	@Autowired
	ModuleService moduleService;

	protected final Log logger = LogFactory.getLog(getClass());

	// public Logger logger = Logger.getLogger(TopicServiceImpl.class);

	@Override
	public Topic addTopic(Topic topic) throws TopicExistsException {
		Topic savedTopic = topicDao.addTopic(topic);
		return savedTopic;
	}

	@Override
	public void saveTopic(Topic topic) {
		topicDao.saveTopic(topic);
	}

	@Override
	public Topic getTopicByName(String name) throws TopicNotFoundExistException {
		return topicDao.findTopicByName(name);
	}

	@Override
	public Topic getTopicById(long topicId) throws TopicNotFoundExistException {
		return topicDao.findTopicById(topicId);
	}

	@Override
	public List<Topic> getAllTopics() {
		return topicDao.getAllTopics();
	}

	@Override
	public void deleteTopic(Topic topic) throws TopicNotFoundExistException {
		topicDao.deleteTopic(topic);

	}

	@Override
	public void deleteTopic(long topicId) throws TopicNotFoundExistException {
		topicDao.deleteTopic(topicId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.topic.TopicService#getAllTopicsByModule
	 * (java.lang.Long)
	 */
	@Override
	public List<Topic> getAllTopicsByModule(Long moduleId) {
		return topicDao.getAllTopicsByModule(moduleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.TopicService#enableUser(java.lang
	 * .String)
	 */
	public boolean changeStatusTopic(String topicId)
			throws TopicNotFoundExistException {
		long uId = -1;

		try {
			uId = Long.valueOf(topicId);
		} catch (NumberFormatException nfe) {
			throw new TopicNotFoundExistException("User does not exist.");
		}

		Topic topic = topicDao.findById(uId);

		if (topic.getEnabled() == 1) {

			topic.setEnabled(0);

			topic = topicDao.saveOrUpdate(topic);

			if (topic.getEnabled() == 0) {

				return true;

			} else {

				return false;

			}

		} else {
			topic.setEnabled(1);

			topic = topicDao.saveOrUpdate(topic);

			if (topic.getEnabled() == 1) {

				return true;

			} else {

				return false;

			}

		}
	}

	@Override
	public int getMaxLevel() {
		return topicDao.getMaxLevel();
	}

	public List<ListTopicBean> listTopicsByModule(long id) {
		List<Topic> topics = topicDao.findAllTopicsByModule(id);
		return getListTopicBeans(topics, id);

	}

	@Override
	public List<ListOfTopicBean> listTopicsBy_Module(long id) {
		List<ListOfTopicBean> list = new ArrayList<ListOfTopicBean>();
		List<Topic> topics = topicDao.findAllTopicsByModule(id);
		int maxLevel = topicDao.getMaxLevelByModule(id);
		ListOfTopicBean listOfTopicBean = new ListOfTopicBean();
		listOfTopicBean.setId(String.valueOf(0));
		listOfTopicBean.setName("Module as Parent");
		listOfTopicBean.setMaxLevel(String.valueOf(maxLevel));
		listOfTopicBean.setParentId("0");
		listOfTopicBean.setLevel("0");
		list.add(listOfTopicBean);

		if (topics != null) {
			for (Topic topic : topics) {
				ListOfTopicBean topicBean = new ListOfTopicBean();
				topicBean.setId(topic.getId().toString());
				topicBean.setName(StringEscapeUtils.unescapeHtml(topic
						.getName()));
				topicBean.setParentId(topic.getParentId() + "");
				topicBean.setLevel(topic.getLevel() + "");
				topicBean.setEnabled(topic.getEnabled());
				list.add(topicBean);
			}
		}

		return list;
	}

	public List<ListTopicBean> listParentTopicsByModule(long id) {
		List<ListTopicBean> list = new ArrayList<ListTopicBean>();
		List<Topic> topics = topicDao.findAllParentTopicsByModule(id);
		int maxLevel = topicDao.getMaxLevelByModule(id);
		ListTopicBean listTopicBean = new ListTopicBean();
		listTopicBean.setTopicId(String.valueOf(0));
		listTopicBean.setTopicName("Module as Parent");
		listTopicBean.setMaxLevel(String.valueOf(maxLevel));
		listTopicBean.setParentId("0");
		listTopicBean.setLevel("0");
		list.add(listTopicBean);

		if (topics != null) {
			for (Topic topic : topics) {
				ListTopicBean topicBean = new ListTopicBean();
				topicBean.setTopicId(topic.getId().toString());
				topicBean.setTopicName(StringEscapeUtils.unescapeHtml(topic
						.getName()));
				topicBean.setParentId(topic.getParentId() + "");
				topicBean.setLevel(topic.getLevel() + "");
				topicBean.setEnabled(topic.getEnabled());
				list.add(topicBean);
			}
		}

		return list;
	}

	@Override
	public List<TopicBean> getAllTopicsByModuleJson(Long moduleId,
			HttpServletRequest request) {
		User user = userCastService.getUser();
		List<Topic> topLeveltopics = topicDao
				.getAllTopicsDirectChildOfModule(moduleId);
		if (topLeveltopics != null) {
			List<Role> roles = user.getRoles();
			List<TopicBean> topics = new ArrayList<TopicBean>();
			for (Role role : roles) {
				if (role.getRoleName().equals("ROLE_STUDENT")) {
					topics = this.populate(topLeveltopics, user.getStudent()
							.getCompletedTopics(), user);
				} else if (role.getRoleName().equals("ROLE_MENTOR")) {
					topics = this.populate(topLeveltopics, user);
				} else if (role.getRoleName().equals("ROLE_EVALUATOR")) {
					if (getSessionAttribRole(request).equalsIgnoreCase(
							"STUDENT")) {
						topics = this.populate(topLeveltopics, user
								.getStudent().getCompletedTopics(), user);
					} else {
						topics = this.populate(topLeveltopics, user);
					}
				}
			}
			return topics;
		} else
			return null;

	}

	@Override
	public List<RefreshTreeBean> getStatusOfExercisesByModule(Long moduleId) {
		User user = userCastService.getUser();
		List<Topic> topics = topicDao.getAllExercisesByModule(moduleId);
		if (topics != null) {
			List<RefreshTreeBean> exercises = new ArrayList<RefreshTreeBean>();
			for (Topic topic : topics) {

				RefreshTreeBean exercise = new RefreshTreeBean();
				exercise.setTopicId(topic.getId());
				exercise.setStatus(getStatusOfExercise(user, topic));
				exercises.add(exercise);

			}
			return exercises;
		} else
			return null;

	}

	/**
	 * 
	 * @param topics
	 * @param user
	 * @return
	 */
	public List<TopicBean> populate(List<Topic> topics, User user) {
		List<TopicBean> topicBeans = new ArrayList<TopicBean>();
		for (Topic topic : topics) {
			TopicBean topicBean = new TopicBean();
			topicBean.setData(StringEscapeUtils.unescapeHtml(topic.getName()));
			Map<String, Object> metadata = new HashMap<String, Object>();
			metadata.put("id", topic.getId());
			if (topic.isExercise()) {
				metadata.put("rel", Status.STATUS_EXERCISE_NOT_READ_YET);

			}
			topicBean.setAttr(metadata);
			List<Topic> childTopics = topicDao
					.getAllChildrenTopicsOfTopic(topic.getId());
			if (childTopics != null) {
				topicBean.setChildren(this.populate(childTopics, user));
			}
			topicBeans.add(topicBean);
		}
		return topicBeans;
	}

	/**
	 * get a list of topic beans used to build json module tree from a list of
	 * topic objects
	 * 
	 * @param topics
	 *            the list of topics
	 * 
	 * @return List of topicbeans used to build json object
	 */
	public List<TopicBean> populate(List<Topic> topics,
			List<Topic> completedTopics, User user) {
		List<TopicBean> topicBeans = new ArrayList<TopicBean>();
		for (Topic topic : topics) {
			TopicBean topicBean = new TopicBean();
			topicBean.setData(StringEscapeUtils.unescapeHtml(topic.getName()));
			Map<String, Object> metadata = new HashMap<String, Object>();
			metadata.put("id", topic.getId());
			if (topic.isExercise()) {
				metadata.put("rel", getStatusOfExercise(user, topic));
			} else {
				if (completedTopics.contains(topic))
					metadata.put("rel", Status.STATUS_TOPIC_COMPLETED);
			}
			topicBean.setAttr(metadata);
			List<Topic> childTopics = topicDao
					.getAllChildrenTopicsOfTopic(topic.getId());
			if (childTopics != null) {
				topicBean.setChildren(this.populate(childTopics,
						completedTopics, user));
			}
			topicBeans.add(topicBean);
		}
		return topicBeans;
	}

	@Override
	public Status getStatusOfExercise(User user, Topic exercise) {
		StudentExercise studentExercise = solutionService
				.getStudentExerciseByStudentAndTopic(user, exercise);
		if (studentExercise != null) {
			ExerciseTransaction lastTransaction = solutionService
					.getLastExerciseTransaction(studentExercise);
			System.out.println("\n\n >>>>>> studentExercise:"
					+ studentExercise.getId());

			System.out.println("\n\n >>>>>> Exercise lastTransaction:"
					+ lastTransaction);
			System.out.println("\n\n >>>>>> Exercise lastTransaction comment:"
					+ lastTransaction.getComment());

			System.out
					.println("\n\n >>>>>> Exercise lastTransaction transaction type:"
							+ lastTransaction.getExerciseTransactionType());

			if (studentExercise.isComplete())
				return Status.STATUS_EXERCISE_PASSED;
			else if (lastTransaction.getExerciseTransactionType().getName()
					.equals("StudentToMentor"))
				return Status.STATUS_EXERCISE_SUBMITTED_BUT_AWAITING_FEEDBACK;
			else
				return Status.STATUS_EXERCISE_MARKED_AND_REQUIRES_MORE_WORK;
		} else
			return Status.STATUS_EXERCISE_NOT_READ_YET;

	}

	@Override
	@Transactional
	public void savingTopic(AddTopicBean addTopicBean, Module module)
			throws TopicExistsException {
		Topic topic = new Topic();
		int maxLevel = getMaxLevelByModule(module.getId());
		topic.setLevel(++maxLevel);
		topic.setName(StringEscapeUtils.escapeHtml(addTopicBean.getName()));
		topic.setParentId(Long.valueOf(addTopicBean.getParent()));

		topic.setModule(module);
		try {
			CommonsMultipartFile multipartFile = addTopicBean.getAttachedfile();
			File file = new File();
			if (!multipartFile.isEmpty()) {
				file.setFile(convertFileToBytes(multipartFile));
				file.setContentType(multipartFile.getContentType());
				file.setFileName(multipartFile.getOriginalFilename());
				fileDao.saveFile(file);
				topic.setFile(file);
			}

		} catch (Exception ex) {
			logger.info("Error Trying To save file.Details below \n");
			ex.printStackTrace();
		}
		if (addTopicBean.getExercise() != null) {
			topic.setExercise(true);
			topic.setEvaluationQuestionsLimit(0);
		} else {
			topic.setExercise(false);
			topic.setEvaluationQuestionsLimit(Integer.valueOf(addTopicBean
					.getEvaluationQuestionsLimit()));
		}

		boolean mentoring = module.isMentoring();
		if (mentoring)
			topic.setEnabled(1);
		else
			topic.setEnabled(0);

		Topic topic1 = topicDao.addTopic(topic);
		topicTextService.savingTopicText(topic1, addTopicBean.getText());

	}

	@Override
	@Transactional
	public String savingTopic(EditTopicBean editTopicBean, Module module,
			String id) throws TopicExistsException {
		long currentLevel = 0;
		try {
			Topic topic = getTopicById(Long.valueOf(editTopicBean.getId()));
			currentLevel = topic.getLevel();
			long parentId = Long.valueOf(editTopicBean.getParent());
			if (parentId != 0) {
				Topic parent = getTopicById(parentId);
				if (Long.valueOf(editTopicBean.getLevel()) <= parent.getLevel()) {
					return "Please enter the allowed level";
				}
			}
			long futureLevel = Long.valueOf(editTopicBean.getLevel());

			List<Topic> childTopics = topicDao.getAllChildrenTopicsOfTopic(
					module.getId(), Long.valueOf(id));
			if (childTopics != null) {
				long currentLevel1 = childTopics.get(0).getLevel();
				boolean increment = false;
				if (currentLevel < futureLevel) {
					increment = true;
				}
				int i = 1;
				for (Topic child : childTopics) {
					if (increment) {
						topicDao.arrangeLevels(currentLevel1, futureLevel + i,
								module.getId(), Long.valueOf(id));
					} else {
						topicDao.arrangeLevels(currentLevel1, futureLevel + i,
								module.getId(), Long.valueOf(id));
						currentLevel1 = child.getLevel();
					}
					i++;
				}

				if (!increment) {
					topicDao.arrangeLevels(currentLevel, futureLevel,
							module.getId(), Long.valueOf(id),
							childTopics.size());
				} else {
					topicDao.arrangeLevels(currentLevel, futureLevel,
							module.getId(), Long.valueOf(id));
				}
			} else {
				topicDao.arrangeLevels(currentLevel, futureLevel,
						module.getId(), Long.valueOf(id));
			}

			if (childTopics != null) {
				Long childFutureLevel = futureLevel + 1;
				for (Topic child : childTopics) {
					child.setLevel(childFutureLevel);
					childFutureLevel++;
				}
			}
			topic.setLevel(futureLevel);
			topic.setName(StringEscapeUtils.escapeHtml(editTopicBean.getName()));
			topic.setParentId(Long.valueOf(editTopicBean.getParent()));
			topic.setModule(module);
			topic.setExercise(editTopicBean.getExercise());

			if (editTopicBean.getExercise()) {
				topic.setEvaluationQuestionsLimit(0);
			} else {
				topic.setEvaluationQuestionsLimit(Integer.valueOf(editTopicBean
						.getEvaluationQuestionsLimit()));
			}
			try {
				CommonsMultipartFile multipartFile = editTopicBean
						.getAttachedfile();
				File file = new File();
				if (multipartFile != null) {
					file.setFile(convertFileToBytes(multipartFile));
					file.setContentType(multipartFile.getContentType());
					file.setFileName(multipartFile.getOriginalFilename());
					if (topic.getFile() != null) {
						Long prevFileId = topic.getFile().getId();
						fileDao.saveFile(file);
						topic.setFile(file);
						fileDao.removeFile(prevFileId);
					}

					else {
						fileDao.saveFile(file);
						topic.setFile(file);
					}
				}
			} catch (Exception ex) {
				logger.info("Error Trying To save file.Details below \n");
				ex.printStackTrace();
			}

			boolean mentoring = module.isMentoring();
			if (mentoring == true && editTopicBean.getExercise() == true)
				topic.setEnabled(1);

			else if (mentoring == false && editTopicBean.getExercise() == true)
				topic.setEnabled(0);

			topicDao.saveTopic(topic);
			TopicText topicText = topicTextService.getTopicTextByTopic(Long
					.valueOf(editTopicBean.getId()));
			topicText.setText(editTopicBean.getText().trim());
			topicTextService.saveTopicText(topicText);
			return "Saved";
		} catch (Exception e) {
			return "Error trying to save the changes!";
		}
	}

	@Override
	public int getMaxLevelByModule(long id) {
		return topicDao.getMaxLevelByModule(id);
	}

	@Override
	public List<Topic> getAllParentTopics(long id) {
		return topicDao.findAllParentTopicsByModule(id);
	}

	@Override
	public List<Topic> listAllExercises() {
		List<Topic> list = new ArrayList<Topic>();
		List<Topic> topics = topicDao.getAllTopics();

		if (topics != null) {
			for (Topic topic : topics) {

				if (topic.isExercise()) {

					list.add(topic);
				}
			}
		}

		return list;
	}

	@Override
	public List<Topic> listAllExercises(Module module) {
		List<Topic> list = new ArrayList<Topic>();
		List<Topic> topics = topicDao.getAllTopics();

		if (topics != null) {
			for (Topic topic : topics) {

				if (topic.isExercise() && topic.getModule().equals(module)) {

					list.add(topic);
				}
			}
		}

		return list;
	}

	@Override
	public List<Double> getPercentageVariables(long moduleId) {
		return topicDao.getPercentageVariables(moduleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.TopicService#getPaginatedTopics
	 * (int, int, long)
	 */
	public List<Topic> getPaginatedTopics(int startIndex, int pageSize,
			long moduleId, String searchString) {
		return topicDao.getPaginatedTopicsList(startIndex, pageSize, moduleId,
				searchString);
	}

	@Override
	public BigInteger getNumberOfEnabledTopics(long moduleId) {
		return topicDao.getNumberOfEnabledTopics(moduleId);
	}

	private Blob convertFileToBytes(CommonsMultipartFile file) {
		return NonContextualLobCreator.INSTANCE.createBlob(file.getBytes());
	}

	@Override
	public int getNumberOfTopicsByModule(long moduleId) {
		// TODO Auto-generated method stub
		return topicDao.getNumberOfTopicsByModule(moduleId);
	}

	@Override
	public int getNumberOfAllowedQuestions(long topicId) {
		return topicDao.getNumberOfAllowedQuestions(topicId);
	}

	@Override
	public List<Topic> getAllExercisesByModule(Long moduleId) {
		// TODO Auto-generated method stub
		return topicDao.getAllExercisesByModule(moduleId);
	}

	@Override
	public int getNumberOfTopicByNameAndModule(String name, Long moduleId) {
		return topicDao.getNumberOfTopicByNameAndModule(name, moduleId);
	}

	@Override
	public List<ListTopicBean> findTopicByNameAndModuleId(String name,
			Long moduleId) {

		List<Topic> topics = topicDao
				.findTopicByNameAndModuleId(name, moduleId);
		return getListTopicBeans(topics, moduleId);
	}

	private List<ListTopicBean> getListTopicBeans(List<Topic> topics,
			Long moduleId) {
		int maxLevel = topicDao.getMaxLevelByModule(moduleId);
		List<ListTopicBean> list = new ArrayList<ListTopicBean>();

		CompanySettings companySettings = null;
		boolean mentoring_c = true;
		boolean mentoring_m = true;
		try {
			Company company = companyService.findCompanyByEmail(userCastService
					.getUser().getEmail());

			companySettings = company.getCompanySettings();
			if (companySettings != null) {
				mentoring_c = companySettings.getMentoring();
			}
			Module module = moduleService.getModuleById(moduleId);
			mentoring_m = module.isMentoring();

		} catch (CompanyDoesNotExistException e1) {
			e1.printStackTrace();
		} catch (ModuleDoesNotExistException e1) {
			e1.printStackTrace();
		}

		// ListTopicBean listTopicBean = new ListTopicBean();
		// listTopicBean.setTopicId(String.valueOf(0));
		// listTopicBean.setTopicName("Module as Parent");
		// listTopicBean.setMaxLevel(String.valueOf(maxLevel));
		// listTopicBean.setParentId("0");
		// listTopicBean.setLevel("0");
		// list.add(listTopicBean);

		if (topics != null) {
			for (Topic topic : topics) {
				ListTopicBean topicBean = new ListTopicBean();
				topicBean.setTopicId(topic.getId().toString());
				topicBean.setTopicName(StringEscapeUtils.unescapeHtml(topic
						.getName()));
				// topicBean.setParentId(topic.getParentId() + "");
				try {
					if (topic.getParentId() == 0) {
						topicBean.setParentName("Module as Parent");
					} else {
						topicBean.setParentName(getTopicById(
								topic.getParentId()).getName());
					}
				} catch (TopicNotFoundExistException e) {
					e.printStackTrace();
				}
				topicBean.setLevel(topic.getLevel() + "");
				topicBean.setEnabled(topic.getEnabled());
				topicBean.setExercise(topic.isExercise());

				if (mentoring_c && mentoring_m) {
					list.add(topicBean);
				} else if (mentoring_c && !mentoring_m) {
					if (!topic.isExercise()) {
						list.add(topicBean);
					}
				} else {
					if (!topic.isExercise()) {
						list.add(topicBean);
					}
				}

			}
		}

		return list;
	}
	
	@Override
	public BigInteger getNumberOfEnabledTopicsNoSession(long moduleId, String companyDbName) {
		return topicDao.getNumberOfEnabledTopicsNoSession(moduleId, companyDbName);
	}

}
