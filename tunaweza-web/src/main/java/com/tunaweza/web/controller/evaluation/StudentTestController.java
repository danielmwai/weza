package com.tunaweza.web.controller.evaluation;

import com.tunaweza.core.business.model.answer.Answer;
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.evaluation.CourseEvaluationTransaction;
import com.tunaweza.core.business.model.evaluation.Evaluation;
import com.tunaweza.core.business.model.evaluation.EvaluationTransaction;
import com.tunaweza.core.business.model.evaluation.StudentCourseEvaluation;
import com.tunaweza.core.business.model.evaluation.StudentEvaluation;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.question.Question;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.service.answer.AnswerService;
import com.tunaweza.core.business.service.course.CourseService;
import com.tunaweza.core.business.service.evaluation.EvaluationService;
import com.tunaweza.core.business.service.evaluation.student.StudentEvaluationService;
import com.tunaweza.core.business.service.question.QuestionService;
import com.tunaweza.core.business.service.student.StudentService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.web.spring.configuration.evaluation.bean.CourseEvaluationTestBean;
import com.tunaweza.web.spring.configuration.evaluation.bean.EvaluationTestBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.STUDENT_TEST_COURSE;
import static com.tunaweza.web.views.Views.STUDENT_TEST_COURSE_NEW;
import static com.tunaweza.web.views.Views.STUDENT_TEST_COURSE_RESULTS;
import static com.tunaweza.web.views.Views.STUDENT_TEST_MODULE;
import static com.tunaweza.web.views.Views.STUDENT_TEST_MODULE_NEW;
import static com.tunaweza.web.views.Views.STUDENT_TEST_MODULE_RESULTS;
import static com.tunaweza.web.views.Views.TEST_COURSE_START;
import static com.tunaweza.web.views.Views.TEST_MODULE_START;
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
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author samuel Waweru.
 * @author Martin Mathu
 */
@Controller
@RequestMapping(Views.STUDENT_TEST)
public class StudentTestController implements Views {

	@Autowired
	private Validator validator;

	@Autowired
	private EvaluationService evaluationTemplateService;

	@Autowired
	private CourseService courseTemplateService;

	@Autowired
	private StudentEvaluationService studentEvaluationService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserCastService userCastService;

	@Autowired
	TopicService topicService;

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = TEST_MODULE_START, method = RequestMethod.GET)
	public String evaluationDescription(
			@RequestParam(value = "moduleId", required = true) String id,
			Model model) throws Exception {
		Evaluation evaluationTemplate = evaluationTemplateService
				.getEvaluationByModule(Long.valueOf(id));
		model.addAttribute("EVALUATIONTEMPLATE", evaluationTemplate);
		return TEST_MODULE_START;
	}

	@RequestMapping(value = STUDENT_TEST_MODULE_NEW, method = RequestMethod.GET)
	public String startmoduleEvaluation(
			@RequestParam(value = "testId", required = true) String id,
			Model model, HttpServletRequest request) throws Exception {
		Evaluation evaluationTemplate = evaluationTemplateService
				.findEvaluationById(Long.valueOf(id));
		Student student = userCastService.getUser().getStudent();
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

		return STUDENT_TEST_MODULE_NEW;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = STUDENT_TEST_MODULE, method = RequestMethod.POST)
	public String moduleEvaluation(
			@RequestBody EvaluationTestBean evaluationTestBean,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
        
		Set<ConstraintViolation<EvaluationTestBean>> failures = validator
				.validate(evaluationTestBean);
		int timeout = Integer.valueOf(evaluationTestBean.getTimeout());
		if (timeout == 0 && !failures.isEmpty()) {
			MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
			MediaType jsonMimeType = MediaType.APPLICATION_JSON;
			jsonConverter.write(Collections.singletonMap("message",
					submitValidationMessages(failures)), jsonMimeType,
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
				model = gradeModuleTest(model, studentEvaluation);
				return STUDENT_TEST_MODULE_RESULTS;
			}
			List<Answer> answers = new ArrayList<Answer>();
			if(evaluationTestBean.getAnswer()!=null){
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

			System.out.println("\n\n\n >>> evaluation Transactions size : "
					+ evalTransactions.size());

			// System.out
			// .println("\n\n\n >>> Module eval, Setting evaluation transaction, studentEvalId : "
			// + evaluationTransaction.getStudentEvaluation()
			// .getId());
			// StudentEvaluation st= studentEvaluationService
			// .getStudentEvaluationById(evaluation);
			// System.out
			// .println("\n\n\n >>> studentEvaluation transactions b4 grading id : "+st.getEvaluationTransaction());
			//
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
				return STUDENT_TEST_MODULE;
				
			} else {
				model = gradeModuleTest(model, studentEvaluation);
				return STUDENT_TEST_MODULE_RESULTS;
			}
		}
			 
		}
        
        
        
                     
	//}

	@RequestMapping(value = TEST_COURSE_START, method = RequestMethod.GET)
	public String courseEvaluationDescription(
			@RequestParam(value = "courseId", required = true) String id,
			Model model, HttpServletRequest request) throws Exception {
		Course courseTemplate = courseTemplateService
				.findCourseById(Long.valueOf(id));
		Student student = userCastService.getUser().getStudent();
		Map<Course, List<Module>> studentModules = studentService
				.getStudentModules(student);
		List<Module> courseModules = studentModules.get(courseTemplate);
		double duration = 0;
		for (Module module : courseModules) {
			duration += module.getEvaluation().getDuration();
		}
		model.addAttribute("COURSETEMPLATE", courseTemplate);
		model.addAttribute("DURATION", duration);
		// convert to seconds
		duration = duration * 60;
		HttpSession session = request.getSession(true);
		session.setAttribute("TESTDURATION", duration);
		return TEST_COURSE_START;
	}

	@RequestMapping(value = STUDENT_TEST_COURSE_NEW, method = RequestMethod.GET)
	public String startCourseEvaluation(
			@RequestParam(value = "courseId", required = true) String id,
			Model model, HttpServletRequest request) throws Exception {
		Course courseTemplate = courseTemplateService
				.findCourseById(Long.valueOf(id));
		Student student = userCastService.getUser().getStudent();
		Map<Course, List<Module>> studentModules = studentService
				.getStudentModules(student);
		List<Module> courseModules = studentModules.get(courseTemplate);
		HttpSession session = request.getSession(true);
		Double duration = (Double) session.getAttribute("TESTDURATION");
		List<StudentCourseEvaluation> pastevaluations = studentEvaluationService
				.getStudentCourseEvaluation(courseTemplate.getId(),
						student.getId());

		StudentCourseEvaluation lastCourseEvaluation = studentEvaluationService
				.getLastStudentCourseEvaluation(courseTemplate.getId(),
						student.getId());
		Boolean passed = lastCourseEvaluation.isCompleted();
		// student has attempted the test more 2 or more times delete the
		// earliest attempt
		StudentCourseEvaluation evaluation = null;

		if (pastevaluations.size() >= 2) {

			for (StudentCourseEvaluation pastevaluation : pastevaluations) {
				if (pastevaluation.isCompleted()) {
					passed = true;
				}
			}

			if (passed) {
				evaluation = studentEvaluationService
						.getTemporaryStudentCourseEvaluation(
								courseTemplate.getId(), student.getId());
			} else {
				evaluation = studentEvaluationService
						.getFirstStudentCourseEvaluation(
								courseTemplate.getId(), student.getId());
			}
			if (evaluation != null) {
				studentEvaluationService
						.deleteStudentCourseEvaluation(evaluation);
			}
		}

		evaluation = new StudentCourseEvaluation();
		evaluation.setCourse(courseTemplate);
		evaluation.setStudent(student);
		evaluation.setDateTaken(new Date());

		if (passed) {
			evaluation.setTemporary(true);
		}
		evaluation = studentEvaluationService
				.addStudentCourseEvaluation(evaluation);
		List<Long> evaluationQuestions = new ArrayList<Long>();
		double passmark = 0;
		for (Module module : courseModules) {
			List<Long> moduleQuestions = evaluationTemplateService
					.getRandomModuleEvaluationQuestions(module
							.getEvaluation());
			evaluationQuestions.addAll(moduleQuestions);
			passmark += module.getEvaluation().getPassmark();
		}
		Question question = questionService
				.findQuestionById(evaluationQuestions.get(0));
		session.setAttribute("CourseTestQuestions", evaluationQuestions);
		session.setAttribute("CourseEvaluation", evaluation.getId());
		session.setAttribute("CourseQuestionsAdministered", 1);
		session.setAttribute("CoursePassmark", (double) passmark
				/ courseModules.size());
		if (passed) {
			session.setAttribute("PreviousPass", "true");
		} else {
			session.setAttribute("PreviousPass", "false");
		}
		Map<String, String> choices = new HashMap<String, String>();
		int correct = 0;
		for (Answer ans : question.getAnswers()) {
			if (ans.getCorrect())
				correct++;
			choices.put(ans.getId().toString(), ans.getText());
		}
		CourseEvaluationTestBean testBean = new CourseEvaluationTestBean();
		testBean.setQuestion(question.getId().toString());
		testBean.setQuestionText(question.getText());
		testBean.setChoises(choices);
		testBean.setCourse(courseTemplate.getName());
		testBean.setCorrectAnswers(correct);
		testBean.setTimeRemaining(duration.toString());
		model.addAttribute("evaluationTestBean", testBean);

		return STUDENT_TEST_COURSE_NEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = STUDENT_TEST_COURSE, method = RequestMethod.POST)
	public String courseEvaluation(
			@RequestBody CourseEvaluationTestBean evaluationTestBean,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		Set<ConstraintViolation<CourseEvaluationTestBean>> failures = validator
				.validate(evaluationTestBean);
		int timeout = Integer.valueOf(evaluationTestBean.getTimeout());
		if (timeout == 0 && !failures.isEmpty()) {
			MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
			MediaType jsonMimeType = MediaType.APPLICATION_JSON;
			jsonConverter.write(Collections.singletonMap("message",
					courseValidationMessages(failures)), jsonMimeType,
					new ServletServerHttpResponse(response));

			return null;
		} else {
			HttpSession session = request.getSession(true);
			Double duration = (Double) session.getAttribute("TESTDURATION");
			long evaluation = (Long) session.getAttribute("CourseEvaluation");
			int questionsAdministered = (Integer) session
					.getAttribute("CourseQuestionsAdministered");
			List<Long> evaluationQuestions = (List<Long>) session
					.getAttribute("CourseTestQuestions");
			StudentCourseEvaluation studentEvaluation = studentEvaluationService
					.getStudentCourseEvaluationById(evaluation);
			if (timeout == 1) {
				model = gradeCourseTest(model, studentEvaluation,
						evaluationQuestions, session);
				return STUDENT_TEST_COURSE_RESULTS;
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
			CourseEvaluationTransaction evaluationTransaction = new CourseEvaluationTransaction();
			long questionId = Long.valueOf(evaluationTestBean.getQuestion());

			evaluationTransaction.setQuestion(questionService
					.findQuestionById(questionId));
			evaluationTransaction.setStudentCourseEvaluation(studentEvaluation);
			evaluationTransaction.setAnswers(answers);
			studentEvaluationService
					.addCourseEvaluationTransaction(evaluationTransaction);
			Date currentTime = new Date();
			long timeDiff = (currentTime.getTime() - studentEvaluation
					.getDateTaken().getTime()) / 1000;
			Double timeremaining = duration - timeDiff;
			// test still underway
			if (questionsAdministered < evaluationQuestions.size()
					&& timeremaining > 0) {

				Question question = questionService
						.findQuestionById(evaluationQuestions
								.get(questionsAdministered));
				session.setAttribute("CourseQuestionsAdministered",
						++questionsAdministered);
				Map<String, String> choices = new HashMap<String, String>();
				int correct = 0;
				for (Answer ans : question.getAnswers()) {
					if (ans.getCorrect())
						correct++;
					choices.put(ans.getId().toString(), ans.getText());
				}
				CourseEvaluationTestBean testBean = new CourseEvaluationTestBean();
				testBean.setQuestion(question.getId().toString());
				testBean.setQuestionText(question.getText());
				testBean.setChoises(choices);
				testBean.setCourse(studentEvaluation.getCourse()
						.getName());
				testBean.setCorrectAnswers(correct);
				testBean.setTimeRemaining(timeremaining.toString());
				model.addAttribute("evaluationTestBean", testBean);
				return STUDENT_TEST_COURSE;
			} else {
				model = gradeCourseTest(model, studentEvaluation,
						evaluationQuestions, session);
				return STUDENT_TEST_COURSE_RESULTS;
			}

		}
	}

	private Model gradeModuleTest(Model model,
			StudentEvaluation studentEvaluation) throws Exception {

		List<EvaluationTransaction> evaluationTransactions = studentEvaluation
				.getEvaluationTransaction();

		System.out.println("\n\n\n studentEvaluation id:"
				+ studentEvaluation.getId());
		System.out.println("\n\n\n evaluationTransactions:"
				+ evaluationTransactions);

		double totalScore = 0;
		// store all incorrectly answered questions in a list
		List<Question> incorrectQuestions = new ArrayList<Question>();

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
			Student student = userCastService.getUser().getStudent();
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

	private Model gradeCourseTest(Model model,
			StudentCourseEvaluation studentEvaluation,
			List<Long> evaluationQuestions, HttpSession session)
			throws Exception {
		List<CourseEvaluationTransaction> evaluationTransactions = studentEvaluation
				.getEvaluationTransaction();
		// store all incorrectly answered questions in a list
		List<Question> incorrectQuestions = new ArrayList<Question>();
		double totalScore = 0;
		for (CourseEvaluationTransaction transaction : evaluationTransactions) {
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
			if (transaction.getQuestion().getEvaluation().getModule()
					.isEvaluated()) {
				double factor = transaction.getQuestion()
						.getEvaluation().getFactoring();
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
		Double percentageScore = totalScore / evaluationQuestions.size() * 100;

		double passmark = (Double) session.getAttribute("CoursePassmark");

		// If the student had not done the evaluation or done it but not passed
		// it
		if (!studentEvaluation.isCompleted()) {
			studentEvaluation.setTestScore(percentageScore.intValue());
			studentEvaluationService
					.updateStudentCourseEvaluation(studentEvaluation);
			if (percentageScore >= passmark) {
				Student student = userCastService.getUser().getStudent();
				Course passedCourse = studentEvaluation
						.getCourse();
				Course nextCourse = null;
				Map<Course, List<Module>> studentModules = studentService
						.getStudentModules(student);
				int count = 0, index = 0;
				for (Course course : studentModules.keySet()) {
					if (course.equals(passedCourse)) {
						index = count + 1;
						if (index < studentModules.size())
							continue;
					}
					if (index > 0 && index < studentModules.size()) {
						nextCourse = course;
					}
					count++;
				}
				// student has a next course template
				if (nextCourse != null) {
					List<Module> courseModules = studentModules.get(nextCourse);
					// enable the first module in the new course template
					if (courseModules.size() > 0) {
						studentService.enableStudentModule(
								courseModules.get(0), student.getUser());
					}

				}
				studentEvaluation.setCompleted(true);
				studentEvaluationService
						.updateStudentCourseEvaluation(studentEvaluation);
			}
		}
		// grade exercise
		model.addAttribute("IncorrectQuestions", incorrectQuestions);
		model.addAttribute("StudentEvaluation", studentEvaluation);
		model.addAttribute("COURSE", studentEvaluation.getCourse());
		model.addAttribute("COMMENTS", (percentageScore >= passmark) ? "passed"
				: "failed");
		return model;
	}

	public String submitValidationMessages(
			Set<ConstraintViolation<EvaluationTestBean>> failures) {
		String failureMessages = "";
		for (ConstraintViolation<EvaluationTestBean> failure : failures) {
			failureMessages += failure.getMessage() + " ,";
		}
		return failureMessages;
	}

	private String courseValidationMessages(
			Set<ConstraintViolation<CourseEvaluationTestBean>> failures) {
		String failureMessages = "";
		for (ConstraintViolation<CourseEvaluationTestBean> failure : failures) {
			failureMessages += failure.getMessage() + " ,";
		}
		return failureMessages;
	}

}
