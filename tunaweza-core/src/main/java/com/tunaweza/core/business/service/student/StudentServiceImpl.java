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

package com.tunaweza.core.business.service.student;

import com.tunaweza.core.business.dao.course.CourseDao;
import com.tunaweza.core.business.dao.evaluation.EvaluationDao;
import com.tunaweza.core.business.dao.exceptions.course.CourseNotFoundException;
import com.tunaweza.core.business.dao.exceptions.evaluation.EvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.module.ModuleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentCourseEvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentEvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentExistsException;
import com.tunaweza.core.business.dao.student.StudentDao;
import com.tunaweza.core.business.dao.topic.TopicDao;
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.course.CoursePreRequisite;
import com.tunaweza.core.business.model.course.EmbeddableCourse;
import com.tunaweza.core.business.model.course.EmbeddableModule;
import com.tunaweza.core.business.model.evaluation.Evaluation;
import com.tunaweza.core.business.model.evaluation.StudentCourseEvaluation;
import com.tunaweza.core.business.model.evaluation.StudentEvaluation;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.status.Status;
import com.tunaweza.core.business.model.student.CompletedModule;
import com.tunaweza.core.business.model.student.EnabledModule;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.course.CourseService;
import com.tunaweza.core.business.service.evaluation.EvaluationService;
import com.tunaweza.core.business.service.evaluation.student.StudentCourseEvaluationService;
import com.tunaweza.core.business.service.evaluation.student.StudentEvaluationService;
import com.tunaweza.core.business.service.module.ModuleService;
import com.tunaweza.core.business.service.module.MonitorModuleBean;
import com.tunaweza.core.business.service.topic.TopicService;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Service
public class StudentServiceImpl implements StudentService {

	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	StudentDao studentDao;

	@Autowired
	ModuleService moduleService;

	@Autowired
	CourseService courseService;
	
	@Autowired
	CourseDao courseDao;

	@Autowired
	EvaluationService evaluationService;
	
	@Autowired
	EvaluationDao evaluationDao;

	@Autowired
	StudentEvaluationService studentEvaluationService;

	@Autowired
	StudentCourseEvaluationService studentCourseEvaluationService;

	@Autowired
	TopicService topicService;
	
	@Autowired
	TopicDao topicDAO;

	@Override
	public List<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	@Override
	public Student getStudentById(Long studentId)
			throws StudentDoesNotExistException {
		return studentDao.getStudentById(studentId);
	}
	
	@Override
	public Student getStudentByIdNoSession(String studentId, String companyDbName)
			throws StudentDoesNotExistException {
		return studentDao.getStudentByIdNoSession(studentId, companyDbName);
	}

	@Override
	public Student saveStudent(Student student) throws StudentExistsException {
		return studentDao.saveStudent(student);
	}

	@Override
	public Student getStudentByUser(User user)
			throws StudentDoesNotExistException {
		return studentDao.getStudentByUser(user);
	}
	
	@Override
	public Long getStudentIdByUserId(Long userId)
			throws StudentDoesNotExistException {
		return studentDao.getStudentIdByUserId(userId);
	}
	
	@Override
	public Student getStudentByUserNoSession(User user, String dbName, String companyId )
			throws StudentDoesNotExistException {
		return studentDao.getStudentByUserNoSession(user, dbName, companyId);
	}

	@Override
	public Map<MonitorCourseBean, List<MonitorModuleBean>> getStudentModuleStatistics(
			Student student, List<Course> courseList)
			throws Exception {
		User user = student.getUser();
		/*
		 * Map to be returned to the view having a CourseTemplate name as the
		 * key and a list of modules in that CourseTemplate as the value.
		 */
		Map<MonitorCourseBean, List<MonitorModuleBean>> moduleMap = new LinkedHashMap<MonitorCourseBean, List<MonitorModuleBean>>();

		/*
		 * *********************************************************************
		 * MODULE START AND END DATE CALCULATION
		 * *********************************************************************
		 */
		// variables used for dates
		Date startDate = new Date();
		Map<Long, String[]> dateMap = new HashMap<Long, String[]>();
		String[] dates = new String[2];

		/*
		 * Calculation of the start and expected completion dates of the modules
		 * thestudent is currently taking.
		 */

		// get all modules the student is currently taking.
		List<EnabledModule> currentModules = student.getEnabledModules();
		// loop through all modules to get start and end date values for each.
		if (currentModules.size() > 0) {
			for (EnabledModule em : currentModules) {
				Module currentModule = moduleService.getModuleById(em
						.getModuleId());

				// get the startDate and Start Date Time
				startDate = em.getModuleStartDate();
				if (startDate != null) {
					dates[0] = startDate.toString();

					dates[1] = "Module Incomplete";
					logger.info("End Date: " + dates[1]);
					logger.info("Current Module ID: " + currentModules);
					dateMap.put(currentModule.getId(), dates);
				}
			}
		}

		/*
		 * Calculation of the start and completion dates of the modules the
		 * student has completed.
		 */

		// get the completed modules

		List<CompletedModule> completedModules = student.getCompletedModules();
		// loop through all modules to get start and end date values for each.
		if (completedModules.size() > 0) {
			for (CompletedModule completedModule : completedModules) {

				String[] completedModuleDates = new String[2];

				completedModuleDates[0] = completedModule.getStartDate()
						.toString();
				completedModuleDates[1] = completedModule.getCompletedDate()
						.toString();

				dateMap.put(completedModule.getModuleId(), completedModuleDates);
			}
		}
		/*
		 * *********************************************************************
		 * END OF MODULE START AND END DATE CALCULATION
		 * *********************************************************************
		 */

		/*
		 * *********************************************************************
		 * LOOP THROUGH EACH STUDENT C.T. TO GET OTHER MODULE STATISTICS, topic
		 * % completed, exercise % completed and evaluation scores.
		 * *********************************************************************
		 */

		/*
		 * The courseTemplateList is ordered by levels. addedModules: List to
		 * contain added modules as we loop through the course templates to
		 * avoid duplication of modules across course templates.
		 */
		List<Module> addedModules = new ArrayList<Module>();
		if (courseList.size() == 0) {
			return null;
		} else {

			for (Course course : courseList) {
				logger.info("COURSE : " + course.getName());

				// List of Beans for our data
				List<MonitorModuleBean> modules = new ArrayList<MonitorModuleBean>();
				List<Module> repeatedModules = new ArrayList<Module>();

				List<Module> courseModules = courseService
						.getModulesInCourse(course);

				if (courseModules.size() > 0) {
					for (Module module : courseModules) {
						if (addedModules.contains(module)) {
							repeatedModules.add(module);
						} else {
							// add module statistic logic here
							addedModules.add(module);

							// data required to get our results
							double topicTotal = 0;
							double exerciseTotal = 0;
							double completedTopics = 0;
							double completedExercises = 0;

							// results of interest
							double topicPercentage = 0;
							double exercisePercentage = 0;
							int pendingExercises = 0;

							// Bean to contain results. Set results into this
							// bean
							MonitorModuleBean monitorModuleBean = new MonitorModuleBean();

							Long moduleId = module.getId();
							logger.info("Module Name: " + module.getName());
							logger.info("Module ID " + moduleId);

							// set the module name and id.
							monitorModuleBean.setModuleName(module.getName());
							monitorModuleBean.setId(module.getId().toString());

							monitorModuleBean.setModuleEnabled(this
									.getStudentModuleStatus(module, user));
							/*
							 * Find the number of pending exercises
							 */
							pendingExercises = studentDao
									.countStudentPendingExercises(moduleId,
											student.getId());
							monitorModuleBean
									.setPendingExercises(pendingExercises);
							/*
							 * Get the total number of Topics and Exercises in
							 * this module
							 */
							List<Double> percentagevariables = topicService
									.getPercentageVariables(moduleId);

							topicTotal = percentagevariables.get(0);
							logger.info("Total Topics for " + module.getName()
									+ " " + topicTotal);

							exerciseTotal = percentagevariables.get(1);
							logger.info("Total Exercises for "
									+ module.getName() + " " + exerciseTotal);
							/*
							 * Get the completed Topics and Exercises in this
							 * module
							 */
							List<Double> completedTopicsAndExercises = studentDao
									.getCompletedTopicsAndExercises(moduleId,
											student.getId());

							completedTopics = completedTopicsAndExercises
									.get(0);
							logger.info("Completed Topics for "
									+ module.getName() + " " + completedTopics);

							completedExercises = completedTopicsAndExercises
									.get(1);
							logger.info("Completed Exercises for "
									+ module.getName() + " "
									+ completedExercises);

							// calculate and set topicPercentage
							topicPercentage = (completedTopics / topicTotal) * 100;
							topicPercentage = roundOffNumber(topicPercentage, 2);
							logger.info("Topic % for " + module.getName() + " "
									+ topicPercentage);

							monitorModuleBean
									.setTopicPercentage(topicPercentage);

							// calculate and set exercisePercentage
							exercisePercentage = (completedExercises / exerciseTotal) * 100;
							exercisePercentage = roundOffNumber(
									exercisePercentage, 2);
							logger.info("Exercises % for " + module.getName()
									+ " " + exercisePercentage);

							monitorModuleBean
									.setExercisePercentage(exercisePercentage);

							StudentEvaluation studentEvaluation = null;
							Evaluation evaluation = null;
							try {
								evaluation = evaluationService
										.getEvaluationByModule(module
												.getId());
							} catch (EvaluationDoesNotExistException e) {

							} catch (NullPointerException ex) {

							}
							try {
								studentEvaluation = studentEvaluationService
										.getLastStudentEvaluation(
												evaluation.getId(),
												student.getId());
							} catch (StudentEvaluationDoesNotExistException e) {

							} catch (NullPointerException ex) {

							}
							if (studentEvaluation != null) {
								monitorModuleBean
										.setTestScore(((Integer) studentEvaluation
												.getTestScore()).toString());
							} else {
								monitorModuleBean.setTestScore("Not Taken");
							}

							/*
							 * Set the module start and end date in the bean if
							 * the module has been completed or started
							 */
							boolean isThere = dateMap.containsKey(moduleId);
							if (isThere) {
								String[] values = dateMap.get(moduleId);

								monitorModuleBean.setStartDate(values[0]);
								monitorModuleBean.setEndDate(values[1]);
							} else {
								monitorModuleBean.setStartDate("Not Started");

								monitorModuleBean.setEndDate("Not Started");
							}

							modules.add(monitorModuleBean);

						}
					}
					courseModules.removeAll(repeatedModules);
					if (courseModules.size() > 0) {

						StudentCourseEvaluation studentCourseEvaluation = null;
						MonitorCourseBean monitorCourseBean = new MonitorCourseBean();
						monitorCourseBean
								.setCourseTemplateName(course.getName());
						monitorCourseBean.setId(course.getId()
								.toString());

						try {
							studentCourseEvaluation = studentCourseEvaluationService
									.getLastStudentCourseEvaluation(
											course.getId(),
											student.getId());
						} catch (StudentCourseEvaluationDoesNotExistException e) {
							logger.error("Student Course Evaluation Does Not Exist !!");
							e.printStackTrace();
						} catch (NullPointerException ex) {
							ex.printStackTrace();
						}

						if (studentCourseEvaluation != null) {
							monitorCourseBean
									.setTestScore(((Integer) studentCourseEvaluation
											.getTestScore()).toString());
						} else {
							monitorCourseBean.setTestScore("Not Taken");
						}
						moduleMap.put(monitorCourseBean, modules);

					}
				}

			}

		}

		return moduleMap;

	}

	/**
	 * @param number
	 * @param decimalPlaces
	 * @return
	 */
	private double roundOffNumber(double number, int decimalPlaces) {
		// assume number = 2.555 and decimalPlaces = 2
		double p = Math.pow(10, decimalPlaces);// p=100
		number *= p;// new number = 255.5
		double tmp = Math.round(number);// tmp=256
		return tmp / p;// return 256/100=2.56
	}

	@Override
	public void enableStudentModule(Module module, User user)
			throws StudentDoesNotExistException {
		studentDao.enableStudentModule(module, user);
	}

	@Override
	public void disableStudentModule(Module module, User user)
			throws StudentDoesNotExistException {
		studentDao.disableStudentModule(module, user);
	}

	@Override
	public void enableFirstStudentModule(Module module, User user)
			throws StudentDoesNotExistException {
		studentDao.enableFirstStudentModule(module, user);
	}

	@Override
	public boolean getStudentModuleStatus(Module module, User user)
			throws StudentDoesNotExistException {
		return studentDao.getStudentModuleStatus(module, user);
	}

	@Override
	public void setCurrentModule(Module module, Student student) {
		studentDao.setCurrentModule(module, student);
	}

	@Override
	public BigInteger getCourseIdOfModule(Long moduleId, Long studentId) {

		return studentDao.getCourseIdOfModule(moduleId, studentId);
	}

	/**
	 * create test
	 */
	@Override
	public Map<Course, List<Module>> getStudentModules(Student student) {

		/*
		 * Map to be returned to the view having a CourseTemplate as the key and
		 * a list of modules in that CourseTemplate as the value.
		 */
		Map<Course, List<Module>> modules = new LinkedHashMap<Course, List<Module>>();
		try {
			/*
			 * Get the student's EmbeddableCourseTemplate List and sort it
			 * according to the levels of the individual course templates in the
			 * list.
			 */
			List<EmbeddableCourse> embeddableCourseList = student
					.getCourseList();

			Collections.sort(embeddableCourseList);

			/*
			 * Get the courseTemplateList from the embeddableCourseTemplateList
			 */
			List<Course> courseList = enabledCourse(
					student, embeddableCourseList);
			// -uncomment this section to go back without prerequisites and
			// remove the above courseTemplateList
			// List<CourseTemplate> courseTemplateList = new
			// ArrayList<CourseTemplate>();
			// for (EmbeddableCourseTemplate embeddableCourseTemplate :
			// embeddableCourseTemplateList) {
			//
			// courseTemplateList.add(courseTemplateService
			// .findCourseTemplateById(embeddableCourseTemplate
			// .getCourseTemplateId()));
			// }

			/*
			 * Get the student's completed modules
			 */
			List<CompletedModule> completedModules = student
					.getCompletedModules();
			List<Module> completedModuleList = new ArrayList<Module>();

			for (CompletedModule cm : completedModules) {
				completedModuleList.add(moduleService.getModuleById(cm
						.getModuleId()));
			}

			// set enabled modules for the students according to prerequisites.
			// enable modules for only the courses that have been enabled
			enableStudentModules(student, courseList);
			/*
			 * Get the student's enabled module(s)
			 */
			List<EnabledModule> enabledModules = student.getEnabledModules();
			List<Module> currentEnabledModulesList = new ArrayList<Module>();
			List<Module> currentDisabledModulesList = new ArrayList<Module>();

			for (EnabledModule em : enabledModules) {
				if (em.getEnabled() == true) {
					currentEnabledModulesList.add(moduleService
							.getModuleById(em.getModuleId()));
				} else {
					currentDisabledModulesList.add(moduleService
							.getModuleById(em.getModuleId()));
				}
			}

			/*
			 * Iterate through the courseTemplateList to fetch student modules
			 * and populating the Map.
			 */

			boolean moduleWaitingSet = false;
			/*
			 * List to contain added modules as we loop through the course
			 * templates to avoid duplication of modules across course
			 * templates.
			 */
			List<Module> addedModules = new ArrayList<Module>();

			for (Course course : courseList) {

				boolean startedTemplate = false;
				logger.info("COURSE : " + course.getName());
				List<Module> repeatedModules = new ArrayList<Module>();
				/*
				 * List the modules in order of their levels in the Course
				 * Template
				 */
				List<Module> courseModules = courseService
						.getModulesInCourse(course);

				int numberOfModules = courseModules.size();
				int completedModulesInCourse = 0;
				if (courseModules.size() > 0) {
					for (Module module : courseModules) {
						if (addedModules.contains(module)) {
							repeatedModules.add(module);
						} else {

							addedModules.add(module);
							// check if the module is complete
							if (currentEnabledModulesList.contains(module)) {
								int totalTopics = topicService
										.getNumberOfEnabledTopics(
												module.getId()).intValue();
								List<Double> studentComplatedTopics = this
										.getCompletedTopicsAndExercises(
												module.getId(), student.getId());
								int count = 0;
								for (Double d : studentComplatedTopics) {
									count += Double.valueOf(d);
								}

								StudentEvaluation attempt = null;
								try {
									attempt = studentEvaluationService
											.getLastStudentEvaluation(
													module.getId(),
													student.getId());
								} catch (StudentEvaluationDoesNotExistException e) {

								}
								if ((attempt == null || !attempt.isCompleted())
										&& (module.getEvaluation() != null && module
												.getEvaluation()
												.getNumberOfQuestions() != 0)) {
									if (count < totalTopics) {
										module.setStatus(Status.MODULE_STARTED_AWAITING_EVALUATION);
									} else if (count == totalTopics) {
										module.setStatus(Status.MODULE_COMPLETED_AWAITING_EVALUATION);
									}
								} else if (module.getEvaluation() != null
										&& module.getEvaluation()
												.getNumberOfQuestions() != 0) {
									if (count < totalTopics) {
										module.setStatus(Status.MODULE_STARTED_EVALUATION_PASSED);
									} else if (count == totalTopics) {
										module.setStatus(Status.MODULE_COMPLETED_EVALUATION_PASSED);
										completedModulesInCourse++;
									}
								} else {
									module.setStatus(Status.MODULE_EVALUATION_DISABLED);
									if (count == totalTopics)
										completedModulesInCourse++;
								}

								startedTemplate = true;
							} else if (currentDisabledModulesList
									.contains(module)) {
								module.setStatus(Status.STATUS_MODULE_CLOSED);
								startedTemplate = true;
							} else if ((!startedTemplate)
									&& (!moduleWaitingSet)
									&& (currentEnabledModulesList.size() == 0)) {
								module.setStatus(Status.STATUS_MODULE_AWAITING_STUDENT);
								moduleWaitingSet = true;
							} else {
								module.setStatus(Status.MODULE_NOT_STARTED);

							}
						}

					}
					if (numberOfModules == completedModulesInCourse) {
						StudentCourseEvaluation attempt = null;
						try {
							attempt = studentEvaluationService
									.getLastStudentCourseEvaluation(
											course.getId(),
											student.getId());
						} catch (StudentCourseEvaluationDoesNotExistException e) {
							logger.error("STUDENTSERVICE: ", e);
						}
						if (attempt == null || !attempt.isCompleted())
							course
									.setStatus(Status.COURSE_AWAITING_EVALUATION);
					}
					courseModules.removeAll(repeatedModules);
					if (courseModules.size() > 0) {
						modules.put(course, courseModules);
					}
				}
			}
		} catch (ModuleDoesNotExistException m) {
			m.printStackTrace();
		}
		return modules;
	}

	@Override
	public void activateNextModule(Long moduleId, Student student) {

		try {
			/*
			 * Get the course template the student is on
			 */

			BigInteger courseTemplateId = getCourseIdOfModule(
					Long.valueOf(moduleId), student.getId());

			List<EnabledModule> enabledModules = student.getEnabledModules();
			Date moduleStartDate = new Date();

			for (EnabledModule em : enabledModules) {
				if (em.getModuleId() == moduleId) {
					moduleStartDate = em.getModuleStartDate();
				}
			}
			Module completedModule = moduleService.getModuleById(moduleId);
			setCompletedModule(completedModule, student, moduleStartDate);

			/*
			 * Get the EmbeddableModule List of that course template
			 */

			Course course = courseService
					.findCourseById(courseTemplateId.longValue());

			List<EmbeddableModule> embeddableModules = course
					.getModules();

			Long newModuleLevel = 0L;
			for (EmbeddableModule e : embeddableModules) {
				Module m = moduleService.getModuleById(e.getModuleId());
				if (m.getId().equals(Long.valueOf(moduleId))) {
					Long level = e.getLevel();
					long levelValue = level.longValue();
					levelValue += 1;
					newModuleLevel = (Long) levelValue;
					break;
				}
			}
			for (EmbeddableModule e : embeddableModules) {
				if (e.getLevel().equals(newModuleLevel)) {

					setCurrentModule(
							moduleService.getModuleById(e.getModuleId()),
							student);
					break;
				}
			}

		} catch (CourseNotFoundException e) {
			e.printStackTrace();
		} catch (ModuleDoesNotExistException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public List<Double> getCompletedTopicsAndExercises(long moduleId,
			long userId) {
		return studentDao.getCompletedTopicsAndExercises(moduleId, userId);
	}

	@Override
	public void setCompletedModule(Module module, Student student,
			Date moduleStartDate) {
		studentDao.setCompletedModule(module, student, moduleStartDate);
	}

	public void setLastLoginDate(Student student) {
		studentDao.setLastLoginDate(student);
	}

	public Date getLastLoginDate(Student student) {
		return studentDao.getLastLoginDate(student);
	}

	/**
	 * enable student modules according to prerequisites
	 */
	@Override
	public void enableStudentModules(Student student,
			List<Course> courseList) {
		System.out
				.println("\n\n >>>>>>>>>>>>>>>>>>>>> Setting enabled modules for student:"
						+ student.getUser().getLastName());

		// all courses that are enabled for this student
		List<Course> courses = courseList;
		List<EnabledModule> enabledModules = new ArrayList<EnabledModule>();

		// all modules for the enabled courses
		List<Module> courseModules = new ArrayList<Module>();
		// all module ids for the enabled courses
		List<String> allModuleIds = new ArrayList<String>();

		for (Course course : courses) {
			List<EmbeddableModule> embeddableModules = course
					.getModules();
			for (EmbeddableModule em : embeddableModules) {
				try {
					courseModules.add(moduleService.getModuleById(em
							.getModuleId()));
					allModuleIds.add(String.valueOf(em.getModuleId()));
				} catch (ModuleDoesNotExistException e) {
					logger.error("No module was found matching Id:"
							+ em.getModuleId());
					e.printStackTrace();
				}
			}
		}

		for (Module module : courseModules) {

			// the list of prerequisite module Ids is stored as a string
			List<String> prerequisites = new ArrayList<String>();

			List<String> moduleprerequisites = module.getPreRequisites();

			// if a prerequisite is not contained in the modules that have been
			// assigned to a student then dont consider it
			for (String modPrerequisite : moduleprerequisites) {
				if (allModuleIds.contains(modPrerequisite)) {
					prerequisites.add(modPrerequisite);
				}
			}

			if (completedModulePrereq(student, prerequisites)) {
				// add the module to enabled modules if it was not already
				// there
				EnabledModule em = new EnabledModule();
				em.setEnabled(true);
				em.setModuleId(module.getId());
				enabledModules.add(em);
			}
		}

		student.setEnabledModules(enabledModules);
		studentDao.saveStudent(student);
	}

	/**
	 * Checks if prerequisites of a particular module are complete. Enable a
	 * module if it has no prerequisites, or if all prerequisites are complete
	 * 
	 * @param student
	 * @param prerequisites
	 * @return true or false based on whether all prerequisites are complete
	 */
	private boolean completedModulePrereq(Student student,
			List<String> prerequisites) {
		if (prerequisites.isEmpty()) {
			return true;
		} else {
			List<CompletedModule> completedModules = student
					.getCompletedModules();
			List<String> completedModulesIds = new ArrayList<String>();
			for (CompletedModule completedModule : completedModules) {
				completedModulesIds.add(String.valueOf(completedModule
						.getModuleId()));
			}
			return completedModulesIds.containsAll(prerequisites);
		}
	}

	/**
	 * Determines which course Templates will be enabled for the student based
	 * on the prerequisites
	 * 
	 * @param student
	 * @param embeddableCourseList
	 * @return
	 */
	private List<Course> enabledCourse(Student student,
			List<EmbeddableCourse> embeddableCourseList) {
		System.out.println("\n\n >>>> Setting Enabled course templates for : "
				+ student.getUser().getFirstName());
		List<Course> courseList = new ArrayList<Course>();
		for (EmbeddableCourse embeddableCourse : embeddableCourseList) {

			try {
				Course ct = courseService
						.findCourseById(embeddableCourse
								.getCourseId());

				List<CoursePreRequisite> coursePrerequisites = new ArrayList<CoursePreRequisite>();
				coursePrerequisites = ct.getCoursePreReQuisites();
				if (completedCoursePrereq(student, coursePrerequisites)) {
					courseList.add(ct);
				}
			} catch (CourseNotFoundException e) {
				logger.info("Course template does not exist");
				e.printStackTrace();
			}
		}
		return courseList;
	}

	/**
	 * Check if course prerequisites are complete
	 * 
	 * @param student
	 * @param coursePrerequisites
	 * @return
	 */
	private boolean completedCoursePrereq(Student student,
			List<CoursePreRequisite> coursePrerequisites) {
		boolean complete = false;
		List<EmbeddableCourse> studentCourses = student
				.getCourseList();
		List<Long> completedIds = new ArrayList<Long>();
		List<Long> prerequisiteIds = new ArrayList<Long>();

		List<Long> studentCoursesIds = new ArrayList<Long>();

		for (EmbeddableCourse em : studentCourses) {
			long id = em.getCourseId();
			studentCoursesIds.add(id);
			if (em.isComplete()) {
				completedIds.add(em.getCourseId());
			}
		}

		for (CoursePreRequisite prerequisite : coursePrerequisites) {
			long prerequisiteId = prerequisite.getCoursePreRequisiteId();
			// if a course has a prerequisite that does not exist in courses
			// assigned to the student then ignore the prerequisite
			if (studentCoursesIds.contains(prerequisiteId)) {
				prerequisiteIds.add(prerequisiteId);
			}
		}

		// set complete
		if (coursePrerequisites.isEmpty()) {
			complete = true;
		} else {
			// true if completed courses contains all prerequisites
			complete = completedIds.containsAll(prerequisiteIds);
		}
		return complete;
	}
	
	// Temp method until I find a better way to do this
	// Calls to the webservice aren't being switched to the company db
	// so db queries are to jjteach and not the student's company db
	
	/*@Override
	public Map<CourseTemplate, List<Module>> getStudentModulesNoSession(Student student, String companyDbName) {

	
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>01");
		
		 * Map to be returned to the view having a CourseTemplate as the key and
		 * a list of modules in that CourseTemplate as the value.
		 
		Map<CourseTemplate, List<Module>> modules = new LinkedHashMap<CourseTemplate, List<Module>>();
		try {
			
			 * Get the student's EmbeddableCourseTemplate List and sort it
			 * according to the levels of the individual course templates in the
			 * list.
			 
			// = studentDao.getStudentCourseTemplateList
			List<EmbeddableCourseTemplate> embeddableCourseTemplateList = studentDao.getStudentCourseTemplateList(student.getId(), companyDbName);

			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>02" + student.toString());
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>02" + embeddableCourseTemplateList);
			Collections.sort(embeddableCourseTemplateList);

			
			 * Get the courseTemplateList from the embeddableCourseTemplateList
			 
			List<CourseTemplate> courseTemplateList = enabledCourseTemplates(
					student, embeddableCourseTemplateList);
			// -uncomment this section to go back without prerequisites and
			// remove the above courseTemplateList
			// List<CourseTemplate> courseTemplateList = new
			// ArrayList<CourseTemplate>();
			// for (EmbeddableCourseTemplate embeddableCourseTemplate :
			// embeddableCourseTemplateList) {
			//
			// courseTemplateList.add(courseTemplateService
			// .findCourseTemplateById(embeddableCourseTemplate
			// .getCourseTemplateId()));
			// }

			
			 * Get the student's completed modules
			 
			List<CompletedModule> completedModules = student
					.getCompletedModules();
			List<Module> completedModuleList = new ArrayList<Module>();

			for (CompletedModule cm : completedModules) {
				completedModuleList.add(moduleService.getModuleByIdNoSession(cm
						.getModuleId(), companyDbName));
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>03");
			}

			// set enabled modules for the students according to prerequisites.
			// enable modules for only the courses that have been enabled
			enableStudentModules(student, courseTemplateList);
			
			 * Get the student's enabled module(s)
			 
			List<EnabledModule> enabledModules = student.getEnabledModules();
			List<Module> currentEnabledModulesList = new ArrayList<Module>();
			List<Module> currentDisabledModulesList = new ArrayList<Module>();

			for (EnabledModule em : enabledModules) {
				if (em.getEnabled() == true) {
					currentEnabledModulesList.add(moduleService
							.getModuleByIdNoSession(em.getModuleId(), companyDbName));
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>04");
				} else {
					currentDisabledModulesList.add(moduleService
							.getModuleByIdNoSession(em.getModuleId(), companyDbName));
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>05");
				}
			}

			
			 * Iterate through the courseTemplateList to fetch student modules
			 * and populating the Map.
			 

			boolean moduleWaitingSet = false;
			
			 * List to contain added modules as we loop through the course
			 * templates to avoid duplication of modules across course
			 * templates.
			 
			List<Module> addedModules = new ArrayList<Module>();

			for (CourseTemplate courseTemplate : courseTemplateList) {

				boolean startedTemplate = false;
				logger.info("COURSE TEMPLATE: " + courseTemplate.getName());
				List<Module> repeatedModules = new ArrayList<Module>();
				
				 * List the modules in order of their levels in the Course
				 * Template
				 
				List<Module> courseTemplateModules = courseTemplateService
						.getModulesInCourseTemplateNoSession(courseTemplate, companyDbName);
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>06");

				int numberOfModules = courseTemplateModules.size();
				int completedModulesInCourse = 0;
				if (courseTemplateModules.size() > 0) {
					for (Module module : courseTemplateModules) {
						if (addedModules.contains(module)) {
							repeatedModules.add(module);
						} else {

							addedModules.add(module);
							// check if the module is complete
							if (currentEnabledModulesList.contains(module)) {
								int totalTopics = topicService
										.getNumberOfEnabledTopicsNoSession(
												module.getId(), companyDbName).intValue();
								List<Double> studentComplatedTopics = this
										.getCompletedTopicsAndExercises(
												module.getId(), student.getId());
								int count = 0;
								for (Double d : studentComplatedTopics) {
									count += Double.valueOf(d);
								}

								StudentEvaluation attempt = null;
								try {
									attempt = studentEvaluationService
											.getLastStudentEvaluationNoSession(
													module.getId(),
													student.getId(), companyDbName);
								} catch (StudentEvaluationDoesNotExistException e) {

								}
								if ((attempt == null || !attempt.isCompleted())
										&& (module.getEvaluationTemplate() != null && module
												.getEvaluationTemplate()
												.getNumberOfQuestions() != 0)) {
									if (count < totalTopics) {
										module.setStatus(Status.MODULE_STARTED_AWAITING_EVALUATION);
									} else if (count == totalTopics) {
										module.setStatus(Status.MODULE_COMPLETED_AWAITING_EVALUATION);
									}
								} else if (module.getEvaluationTemplate() != null
										&& module.getEvaluationTemplate()
												.getNumberOfQuestions() != 0) {
									if (count < totalTopics) {
										module.setStatus(Status.MODULE_STARTED_EVALUATION_PASSED);
									} else if (count == totalTopics) {
										module.setStatus(Status.MODULE_COMPLETED_EVALUATION_PASSED);
										completedModulesInCourse++;
									}
								} else {
									module.setStatus(Status.MODULE_EVALUATION_DISABLED);
									if (count == totalTopics)
										completedModulesInCourse++;
								}

								startedTemplate = true;
							} else if (currentDisabledModulesList
									.contains(module)) {
								module.setStatus(Status.STATUS_MODULE_CLOSED);
								startedTemplate = true;
							} else if ((!startedTemplate)
									&& (!moduleWaitingSet)
									&& (currentEnabledModulesList.size() == 0)) {
								module.setStatus(Status.STATUS_MODULE_AWAITING_STUDENT);
								moduleWaitingSet = true;
							} else {
								module.setStatus(Status.MODULE_NOT_STARTED);

							}
						}

					}
					if (numberOfModules == completedModulesInCourse) {
						StudentCourseEvaluation attempt = null;
						try {
							attempt = studentEvaluationService
									.getLastStudentCourseEvaluationNoSession(
											courseTemplate.getId(),
											student.getId(), companyDbName);
						} catch (StudentCourseEvaluationDoesNotExistException e) {
							logger.error("STUDENTSERVICE: ", e);
						}
						if (attempt == null || !attempt.isCompleted())
							courseTemplate
									.setStatus(Status.COURSE_AWAITING_EVALUATION);
					}
					courseTemplateModules.removeAll(repeatedModules);
					if (courseTemplateModules.size() > 0) {
						modules.put(courseTemplate, courseTemplateModules);
					}
				}
			}
		} catch (ModuleDoesNotExistException m) {
			m.printStackTrace();
		}
		return modules;
	}
*/
	@Override
	public Map<Map<String, String>, List<Map<String, String>>> getStudentCoursesandModules(Student student, String companyDbName) {
		Long studentId = student.getId();
		Map<Map<String, String>, List<Map<String, String>>> studentCoursesandModules = new LinkedHashMap<Map<String, String>, List<Map<String, String>>>();
		List<BigInteger> studentCTIds = studentDao.getStudentCourseList(studentId, companyDbName);
		List<Course> studentCTs = courseDao.courseListById(studentCTIds, companyDbName);
		
		for(Course studentCT : studentCTs) {
			List<Module> modulesInCT = new ArrayList<Module>();
			modulesInCT = courseDao.getActiveModulesInCourseNoSession(studentCT.getId(), companyDbName);
			List<Map<String, String>> moduleInfoList = new ArrayList<Map<String, String>>();
			for (Module module: modulesInCT) {
				Map<String, String> moduleInfo = new LinkedHashMap<String, String>();
				Evaluation evaluation = null;
				try {
					evaluation = evaluationDao.getEvaluationByModuleNoSession(module.getId(), companyDbName);
				} catch (EvaluationDoesNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// This check if module has evaluation. For some reason module.isEvaluated() is always false
				String hasEvaluation  = "false";
				if(evaluation != null) {
					hasEvaluation = "true";
				}
				moduleInfo.put(module.getId().toString(), module.getName() +  "##" + hasEvaluation);
				moduleInfoList.add(moduleInfo);
			}
			Map<String, String> courseInfo = new LinkedHashMap<String, String>();
			courseInfo.put(studentCT.getId().toString(), studentCT.getName());
			studentCoursesandModules.put(courseInfo, moduleInfoList);
		}
		
		return studentCoursesandModules;
	}
	
	@Override
	public Map<Map<String, String>, String> getModuleTopics(String companyDbName, String moduleId) {
		Map<Map<String, String>, String> moduleTopics = new LinkedHashMap<Map<String, String>, String>();
		List<Topic> topics = new ArrayList<Topic>();
		topics = topicDAO.getAllTopicsByModuleNoSession(companyDbName, moduleId);
		for (Topic topic : topics) {
			String topicText = topicDAO.getTopicTextById(companyDbName, topic.getId().toString());
			Map<String, String> topicInfo = new LinkedHashMap<String, String>();
			topicInfo.put(topic.getId().toString(), topic.getName());
			moduleTopics.put(topicInfo, topicText);
		}
		return moduleTopics;
	}
}

