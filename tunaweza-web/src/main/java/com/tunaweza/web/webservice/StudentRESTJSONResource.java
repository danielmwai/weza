/**
 * 
 */
package com.tunaweza.web.webservice;


import com.tunaweza.core.business.dao.exceptions.evaluation.EvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.dao.user.UserDao;
import com.tunaweza.core.business.model.answer.Answer;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.evaluation.Evaluation;
import com.tunaweza.core.business.model.evaluation.EvaluationTransaction;
import com.tunaweza.core.business.model.evaluation.StudentEvaluation;
import com.tunaweza.core.business.model.question.Question;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.answer.AnswerService;
import com.tunaweza.core.business.service.evaluation.EvaluationService;
import com.tunaweza.core.business.service.evaluation.student.StudentEvaluationService;
import com.tunaweza.core.business.service.question.QuestionService;
import com.tunaweza.core.business.service.student.StudentService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.core.business.service.user.UserService;
import com.tunaweza.core.business.utils.CredentialsEncoder;
import static com.tunaweza.core.business.utils.SessionHelper.setSessionAttribDbConfig;
import com.tunaweza.web.controller.evaluation.StudentTestController;
import com.tunaweza.web.spring.configuration.evaluation.bean.EvaluationTestBean;
import com.tunaweza.web.spring.configuration.user.bean.DbConfigBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.WS_AUTHENTICATE;
import static com.tunaweza.web.views.Views.WS_GET_COURSES_AND_MODULES;
import static com.tunaweza.web.views.Views.WS_GET_EVALUATION_TEMPLATE_QUESTIONS;
import static com.tunaweza.web.views.Views.WS_GET_MODULE_EVALUATION_TEMPLATE;
import static com.tunaweza.web.views.Views.WS_GET_MODULE_TOPICS;
import static com.tunaweza.web.views.Views.WS_STUDENT_TEST_MODULE;
import static com.tunaweza.web.views.Views.WS_STUDENT_TEST_MODULE_NEW;
import static com.tunaweza.web.views.Views.WS_STUDENT_TEST_MODULE_RESULTS;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.validation.Validator;


/**
 * @author Joyce Echessa
 * 
 *         REST API which makes a student's course material available to other
 *         applications
 */

@Controller
@RequestMapping("/student")
public class StudentRESTJSONResource implements Views {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private EvaluationService evaluationTemplateService;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private StudentEvaluationService studentEvaluationService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	TopicService topicService;
	
	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
	
	private StudentTestController studentTestController = new StudentTestController();

	@RequestMapping(value = WS_AUTHENTICATE, method = RequestMethod.POST)
	public @ResponseBody
	Map<String, String> authenticate(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {

		User user = null;
		Map<String, String> studentInfo = new HashMap<String, String>();
		try {
			user = userDao.findUser(username);
			String pass = CredentialsEncoder.generateMD5(password);
			if (!pass.equals(user.getPassword())) {
				return null;
			}
			
			if (!userService.isStudent(user)) {
				return null;
			}
			Company company = user.getUserCompany();
			String companyDbName = company.getDbaseName();
			String companyId = company.getId().toString();
			String studentName = user.getFirstName() + " " + user.getLastName();
			String studentId = "";
			String studentUserId = user.getId().toString();
			String userName = user.getUsername();
			String currentModule = "";
			Student student = null;
			try {
				student = studentService.getStudentByUserNoSession(user,
						companyDbName, companyId);
				studentId = student.getId().toString();
				if(student.getCurrentModule() != null) {
					currentModule = student.getCurrentModule().toString();
				}
			} catch (StudentDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			studentInfo.put("studentName", studentName);
			studentInfo.put("studentUserId", studentUserId);
			studentInfo.put("userName", userName);
			studentInfo.put("companyDbName", companyDbName);
			studentInfo.put("companyId", companyId);
			studentInfo.put("studentId", studentId);
			studentInfo.put("currentModule", currentModule);

			return studentInfo;

		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = WS_GET_COURSES_AND_MODULES, method = RequestMethod.GET)
	public @ResponseBody
	Map<Map<String, String>, List<Map<String, String>>> getStudentModules(@PathVariable String companyDbName,
			@PathVariable String studentId) {


		Student student = null;

		try {
			student = studentService.getStudentByIdNoSession(studentId, companyDbName);
			//Map<CourseTemplate, List<Module>> studentCoursesAndModules = studentService.getStudentModulesNoSession(student, companyDbName);
			Map<Map<String, String>, List<Map<String, String>>> studentCoursesAndModules =  studentService.getStudentCoursesandModules(student, companyDbName);
			
			return studentCoursesAndModules;
		} catch (StudentDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping(value = WS_GET_MODULE_TOPICS, method = RequestMethod.GET)
	public @ResponseBody
	Map<Map<String, String>, String> getModuleTopics(@PathVariable String companyDbName,
			@PathVariable String moduleId) {

		Map<Map<String, String>, String> moduleTopics = studentService.getModuleTopics(companyDbName, moduleId);
		return moduleTopics;
	}
	
	@RequestMapping(value = WS_GET_MODULE_EVALUATION_TEMPLATE, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String>  getModuleEvaluation(@PathVariable String username, @PathVariable String moduleId, HttpServletRequest request) {

		User user = null;
		Evaluation evaluationTemplate = null;
		try {
			user = userDao.findUser(username);
//			switchDb(user, request);
			try {
				evaluationTemplate = evaluationTemplateService
						.getEvaluationByModule(Long.valueOf(moduleId));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EvaluationDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UserDoesNotExistException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Map<String, String> evaluationTemplateInfo = new HashMap<String, String>();
		
		if(evaluationTemplate != null) {
			String moduleName = evaluationTemplate.getModule().getName();
			String duration = Double.toString(evaluationTemplate.getDuration());
			String description = evaluationTemplate.getDescription();
			String id = evaluationTemplate.getId().toString();
			
			evaluationTemplateInfo.put("moduleName", moduleName);
			evaluationTemplateInfo.put("duration", duration);
			evaluationTemplateInfo.put("description", description);
			evaluationTemplateInfo.put("testId", id);
			return evaluationTemplateInfo;
		}
		else {
			return null;
		}
	}
	
	//  A call to this method will re
	@RequestMapping(value = WS_GET_EVALUATION_TEMPLATE_QUESTIONS, method = RequestMethod.GET)
	public @ResponseBody
	EvaluationTestBean getEvaluationQuestions(@PathVariable String username, @PathVariable String testId, HttpServletRequest request) throws Exception {
		Evaluation evaluationTemplate = evaluationTemplateService
				.findEvaluationById(Long.valueOf(testId));
		
		User user = null;
		try {
			user = userDao.findUser(username);
//			switchDb(user, request);
			
			Student student = null;
			student = studentService.getStudentByUser(user);
			List<StudentEvaluation> pastevaluations = studentEvaluationService
					.getStudentEvaluation(evaluationTemplate.getId(),
							student.getId());
			Boolean passed = false;
			// student has attempted the test more 2 or more times delete the
			// earliest attempt

			StudentEvaluation evaluation = null;
			if (pastevaluations.size() >= 2) {
				for (StudentEvaluation pastevaluation : pastevaluations) {
					if (pastevaluation.isCompleted()) {
						passed = true;
					}
				}

				if (passed) {
					evaluation = studentEvaluationService
							.getTemporaryStudentEvaluation(
									evaluationTemplate.getId(), student.getId());
				} else {
					evaluation = studentEvaluationService
							.getFirstStudentEvaluation(evaluationTemplate.getId(),
									student.getId());
				}
				if (evaluation != null) {
					studentEvaluationService.deleteStudentEvaluation(evaluation);
				}
			}

			evaluation = new StudentEvaluation();
			if (passed) {
				evaluation.setTemporary(true);
			}

			evaluation.setEvaluation(evaluationTemplate);
			evaluation.setStudent(student);
			evaluation.setDateTaken(new Date());

			evaluation = studentEvaluationService.addStudentEvaluation(evaluation);
			List<Long> evaluationQuestions = evaluationTemplateService
					.getRandomModuleEvaluationQuestions(evaluationTemplate);
			Question question = questionService
					.findQuestionById(evaluationQuestions.get(0));
			HttpSession session = request.getSession(true);
			session.setAttribute("TestQuestions", evaluationQuestions);
			session.setAttribute("ModuleEvaluation", evaluation.getId());
			session.setAttribute("QuestionsAdministered", 1);
			Map<String, String> choices = new HashMap<String, String>();
			int correct = 0;
			for (Answer ans : question.getAnswers()) {
				if (ans.getCorrect())
					correct++;
				choices.put(ans.getId().toString(), ans.getText());
			}
			EvaluationTestBean testBean = new EvaluationTestBean();
			testBean.setQuestion(question.getId().toString());
			testBean.setQuestionText(question.getText());
			testBean.setChoises(choices);
			testBean.setModule(evaluationTemplate.getModule().getName());
			testBean.setCorrectAnswers(correct);
			testBean.setTimeRemaining((evaluationTemplate.getDuration() * 60) + "");

			return testBean;
			
		} catch (UserDoesNotExistException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}

	// Test method
	@RequestMapping(value = "/test/{username}/{password}", method = RequestMethod.GET)
	public @ResponseBody
	String myTest(@PathVariable String username,
			@PathVariable String password, HttpServletRequest request) {

		User user = null;
		try {
			user = userDao.findUser(username);
			LOGGER.info("USERFOUND >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		switchDb(user, request);
		Student student = null;
		try {
			student = studentService.getStudentByUser(user);
			LOGGER.info(">>>>>>>>>>>>>>>>>studentId >>>>>>>>>>>>>>>>>>>FOUND" + student.getId());
		} catch (StudentDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(student != null) {
		return student.getId().toString();
		}
		else return "not working";
	}
	
	
	
	
	// Plan B. Grading the evaluation quiz through the webservice might be a bit tricky so this is plan B.
	// Haven't complied with D.R.Y but might change it later
	
	@RequestMapping(value = WS_STUDENT_TEST_MODULE_NEW, method = RequestMethod.GET)
	public String startmoduleEvaluation(
			@RequestParam(value = "testId", required = true) String id,
			@RequestParam(value = "username", required = true) String username,
			Model model, HttpServletRequest request) throws Exception {
		Evaluation evaluationTemplate = evaluationTemplateService
				.findEvaluationById(Long.valueOf(id));
		
		User user = null;
		try {
			user = userDao.findUser(username);
			
			Student student = null;
			try {
				student = studentService.getStudentByUser(user);
				
				List<StudentEvaluation> pastevaluations = studentEvaluationService
						.getStudentEvaluation(evaluationTemplate.getId(),
								student.getId());
				Boolean passed = false;
				// student has attempted the test more 2 or more times delete the
				// earliest attempt

				StudentEvaluation evaluation = null;
				if (pastevaluations.size() >= 2) {
					for (StudentEvaluation pastevaluation : pastevaluations) {
						if (pastevaluation.isCompleted()) {
							passed = true;
						}
					}

					if (passed) {
						evaluation = studentEvaluationService
								.getTemporaryStudentEvaluation(
										evaluationTemplate.getId(), student.getId());
					} else {
						evaluation = studentEvaluationService
								.getFirstStudentEvaluation(evaluationTemplate.getId(),
										student.getId());
					}
					if (evaluation != null) {
						studentEvaluationService.deleteStudentEvaluation(evaluation);
					}
				}

				evaluation = new StudentEvaluation();
				if (passed) {
					evaluation.setTemporary(true);
				}

				evaluation.setEvaluation(evaluationTemplate);
				evaluation.setStudent(student);
				evaluation.setDateTaken(new Date());

				evaluation = studentEvaluationService.addStudentEvaluation(evaluation);
				List<Long> evaluationQuestions = evaluationTemplateService
						.getRandomModuleEvaluationQuestions(evaluationTemplate);
				Question question = questionService
						.findQuestionById(evaluationQuestions.get(0));
				HttpSession session = request.getSession(true);
				session.setAttribute("TestQuestions", evaluationQuestions);
				session.setAttribute("ModuleEvaluation", evaluation.getId());
				session.setAttribute("QuestionsAdministered", 1);
				Map<String, String> choices = new HashMap<String, String>();
				int correct = 0;
				for (Answer ans : question.getAnswers()) {
					if (ans.getCorrect())
						correct++;
					choices.put(ans.getId().toString(), ans.getText());
				}
				EvaluationTestBean testBean = new EvaluationTestBean();
				testBean.setQuestion(question.getId().toString());
				testBean.setQuestionText(question.getText());
				testBean.setChoises(choices);
				testBean.setModule(evaluationTemplate.getModule().getName());
				testBean.setCorrectAnswers(correct);
				testBean.setTimeRemaining((evaluationTemplate.getDuration() * 60) + "");
				model.addAttribute("evaluationTestBean", testBean);
				model.addAttribute("USERNAME", username);
			} catch (StudentDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return WS_STUDENT_TEST_MODULE_NEW;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = WS_STUDENT_TEST_MODULE + "/{username}", method = RequestMethod.POST)
	public String moduleEvaluation(
			@RequestBody EvaluationTestBean evaluationTestBean,
			@PathVariable String username,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		User user = null;
		Student student = null;
		try {
			user = userDao.findUser(username);
//			switchDb(user, request);
			try {
				student = studentService.getStudentByUser(user);
			}catch (StudentDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Set<ConstraintViolation<EvaluationTestBean>> failures = validator
				.validate(evaluationTestBean);
		int timeout = Integer.valueOf(evaluationTestBean.getTimeout());
		if (timeout == 0 && !failures.isEmpty()) {
			MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
			MediaType jsonMimeType = MediaType.APPLICATION_JSON;
			jsonConverter.write(Collections.singletonMap("message",
					studentTestController.submitValidationMessages(failures)), jsonMimeType,
					new ServletServerHttpResponse(response));
			return null;
		} else {
			HttpSession session = request.getSession(true);
			long evaluation = (Long) session.getAttribute("ModuleEvaluation");
			int questionsAdministered = (Integer) session
					.getAttribute("QuestionsAdministered");
			StudentEvaluation studentEvaluation = studentEvaluationService
					.getStudentEvaluationById(evaluation);
			if (timeout == 1) {
				model = gradeModuleTest(model, studentEvaluation, student);
				return WS_STUDENT_TEST_MODULE_RESULTS;
			}
			List<Answer> answers = new ArrayList<Answer>();
			if (!evaluationTestBean.getAnswer().startsWith("[")) {
				answers.add(answerService.findAnswerById(Long
						.valueOf(evaluationTestBean.getAnswer())));
			} else {
				JSONArray answersArray = new JSONArray(
						evaluationTestBean.getAnswer());
				for (int count = 0; count < answersArray.length(); count++) {
					answers.add(answerService.findAnswerById(Long
							.valueOf(answersArray.getLong(count))));
				}
			}
			EvaluationTransaction evaluationTransaction = new EvaluationTransaction();
			long questionId = Long.valueOf(evaluationTestBean.getQuestion());
			evaluationTransaction.setQuestion(questionService
					.findQuestionById(questionId));
			evaluationTransaction.setStudentEvaluation(studentEvaluation);
			evaluationTransaction.setAnswers(answers);
			// studentEvaluationService
			// .addEvaluationTransaction(evaluationTransaction);

			List<EvaluationTransaction> evalTransactions = new ArrayList<EvaluationTransaction>();
			List<EvaluationTransaction> savedTransactions = studentEvaluation
					.getEvaluationTransaction();
			if (savedTransactions != null) {
				evalTransactions = savedTransactions;
			}

			evalTransactions.add(evaluationTransaction);

			studentEvaluation.setEvaluationTransaction(evalTransactions);

			studentEvaluationService.updateStudentEvaluation(studentEvaluation);

			Date currentTime = new Date();
			long timeDiff = (currentTime.getTime() - studentEvaluation
					.getDateTaken().getTime()) / 1000;
			Double timeremaining = (studentEvaluation.getEvaluation()
					.getDuration() * 60) - timeDiff;
			// test still underway
			if (questionsAdministered < studentEvaluation
					.getEvaluation().getNumberOfQuestions()
					&& timeremaining > 0) {
				List<Long> evaluationQuestions = (List<Long>) session
						.getAttribute("TestQuestions");
				Question question = questionService
						.findQuestionById(evaluationQuestions
								.get(questionsAdministered));
				session.setAttribute("QuestionsAdministered",
						++questionsAdministered);
				Map<String, String> choices = new HashMap<String, String>();
				int correct = 0;
				try{
				for (Answer ans : question.getAnswers()) {
					if (ans.getCorrect())
						correct++;
					choices.put(ans.getId().toString(), ans.getText());
				}
				}
				catch(Exception e){
					System.out.print("caught"+e.getMessage()+"exception");
				}

				EvaluationTestBean testBean = new EvaluationTestBean();
				testBean.setQuestion(question.getId().toString());
				testBean.setQuestionText(question.getText());
				testBean.setChoises(choices);
				testBean.setModule(studentEvaluation.getEvaluation()
						.getModule().getName());
				testBean.setCorrectAnswers(correct);
				testBean.setTimeRemaining(timeremaining.toString());
				model.addAttribute("evaluationTestBean", testBean);
				model.addAttribute("USERNAME", username);
				return WS_STUDENT_TEST_MODULE;
				
			} else {
				model = gradeModuleTest(model, studentEvaluation, student);
				return WS_STUDENT_TEST_MODULE_RESULTS;
			}
		}
			 
		}

	
//	@RequestMapping(value = "switchdb/{username}", method = RequestMethod.GET)
//	public void switchDb(@PathVariable String username, HttpServletRequest request){
//		User user = null;
//		try {
//			user = userDao.findUser(username);
//			switchDb(user, request);
//		} catch (UserDoesNotExistException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
	
	@RequestMapping(value = "wsevaluation/{username}/{moduleId}", method = RequestMethod.GET)
	public String startmoduleEvaluation(@PathVariable String username, @PathVariable String moduleId, HttpServletRequest request, Model model){
		
		User user = null;
		Evaluation evaluationTemplate = null;
		try {
			user = userDao.findUser(username);
			
			try {
				evaluationTemplate = evaluationTemplateService
						.getEvaluationByModule(Long.valueOf(moduleId));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EvaluationDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UserDoesNotExistException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.addAttribute("EVALUATIONTEMPLATE", evaluationTemplate);
		model.addAttribute("USERNAME", username);
		return "wsevaluation";

	}
	
	private Model gradeModuleTest(Model model,
			StudentEvaluation studentEvaluation, Student student) throws Exception {

		List<EvaluationTransaction> evaluationTransactions = studentEvaluation
				.getEvaluationTransaction();

		System.out.println("\n\n\n studentEvaluation id:"
				+ studentEvaluation.getId());
		System.out.println("\n\n\n evaluationTransactions:"
				+ evaluationTransactions);

		double totalScore = 0;
		// store all incorrectly answered questions in a list
		List<Question> incorrectQuestions = new ArrayList<Question>();
		try{
		for (EvaluationTransaction transaction : evaluationTransactions) {
		
			List<Answer> possibleAnswers = transaction.getQuestion()
					.getAnswers();
			List<Answer> correctAnswers = new ArrayList<Answer>();
			List<Answer> answered = transaction.getAnswers();
		
			int correct = 0;
			for (Answer ans : possibleAnswers) {
				if (ans.getCorrect()) {
					correct++;
					correctAnswers.add(ans);
				}

			}
		
			// get all incorrectly answered questions
			for (Answer ans : answered) {
				if ((!ans.getCorrect())
						|| (answered.size() < correctAnswers.size())) {
					Question question = ans.getQuestion();
					incorrectQuestions.add(question);
				}
			}
		
			// factoring is enabled
			if (studentEvaluation.getEvaluation().getModule()
					.isEvaluated()) {
				double factor = studentEvaluation.getEvaluation()
						.getFactoring();
				// question has only 1 answer
				if (correct == 1) {
					if (answered.get(0).equals(correctAnswers.get(0)))
						totalScore++;
					else
						totalScore -= factor;
				} // question has more than 1 answer
				else {
					int correctAns = 0;
					int wrongAns = 0;
					for (Answer ans : answered) {
						if (correctAnswers.contains(ans))
							correctAns++;
						else
							wrongAns++;
					}
					totalScore += (correctAns - (wrongAns * factor))
							/ correctAnswers.size();
				}

			}
			// factoring is disabled
			else {
				// question has only 1 answer
				if (correct == 1) {
					if (answered.get(0).equals(correctAnswers.get(0)))
						totalScore++;
				} // question has more than 1 answer
				else {
					int correctAns = 0;
					int wrongAns = 0;
					for (Answer ans : answered) {
						if (correctAnswers.contains(ans))
							correctAns++;
						else
							wrongAns++;
					}
					totalScore += (correctAns - wrongAns)
							/ possibleAnswers.size();
				}

			}

		}
		}
		catch(Exception e){
			System.out.print("exception"+e.getMessage());
		}
		Double percentageScore = totalScore
				/ studentEvaluation.getEvaluation()
						.getNumberOfQuestions() * 100;

		studentEvaluation.setTestScore(percentageScore.intValue());
		studentEvaluationService.updateStudentEvaluation(studentEvaluation);
		// student has passed the evaluation test and completed the topics
		// and exercises
		if (percentageScore >= studentEvaluation.getEvaluation()
				.getPassmark()) {
			// mark module as complete
			studentEvaluation.setCompleted(true);
			studentEvaluationService.updateStudentEvaluation(studentEvaluation);
			if (!studentEvaluation.isTemporary()) {
				int totalTopics = topicService.getNumberOfEnabledTopics(
						studentEvaluation.getEvaluation().getModule()
								.getId()).intValue();
				List<Double> studentCompletedTopics = studentService
						.getCompletedTopicsAndExercises(studentEvaluation
								.getEvaluation().getModule().getId(),
								student.getId());
				int count = (int) (studentCompletedTopics.get(0) + studentCompletedTopics
						.get(1));
				/*
				 * for (Double d : studentCompletedTopics) { count +=
				 * Double.valueOf(d); }
				 */

				if (count == totalTopics) {
					studentService.activateNextModule(studentEvaluation
							.getEvaluation().getModule().getId(),
							student);
				}
			}
		}
		
		// grade exercise
		model.addAttribute("StudentEvaluation", studentEvaluation);
		model.addAttribute("IncorrectQuestions", incorrectQuestions);
		model.addAttribute("EVALUATIONTEMPLATE",
				studentEvaluation.getEvaluation());
		model.addAttribute("COMMENTS", (percentageScore >= studentEvaluation
				.getEvaluation().getPassmark()) ? "passed" : "failed");

		return model;

	}

}

