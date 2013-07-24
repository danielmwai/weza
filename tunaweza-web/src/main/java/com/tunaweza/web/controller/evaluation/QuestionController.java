package com.tunaweza.web.controller.evaluation;

import com.tunaweza.core.business.dao.exceptions.evaluation.EvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.question.QuestionDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.question.QuestionExistsException;
import com.tunaweza.core.business.model.answer.Answer;
import com.tunaweza.core.business.model.evaluation.Evaluation;
import com.tunaweza.core.business.model.question.Question;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.service.answer.AnswerService;
import com.tunaweza.core.business.service.evaluation.EvaluationService;
import com.tunaweza.core.business.service.question.QuestionService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.web.spring.configuration.evaluation.question.bean.AddQuestionBean;
import com.tunaweza.web.spring.configuration.evaluation.question.bean.AssociateQuestionWithTopicBean;
import com.tunaweza.web.spring.configuration.evaluation.question.bean.DissociateQuestionFromTopicBean;
import com.tunaweza.web.spring.configuration.evaluation.question.bean.EditQuestionBean;
import com.tunaweza.web.spring.configuration.evaluation.question.bean.ListQuestionBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.ASSOCIATE_QUESTION_WITH_TOPIC;
import static com.tunaweza.web.views.Views.DELETE_QUESTION;
import static com.tunaweza.web.views.Views.DISSOCIATE_QUESTION_FROM_TOPIC;
import static com.tunaweza.web.views.Views.EDIT_TEST_TEMPLATE_QUESTION_FORM;
import static com.tunaweza.web.views.Views.LIST_QUESTIONS_NOT_ASSOCIATED_WITH_TOPIC;
import static com.tunaweza.web.views.Views.LIST_QUESTIONS_T0_DISSOCIATE_FROM_TOPIC;
import static com.tunaweza.web.views.Views.PAGE_SIZE;
import static com.tunaweza.web.views.Views.QUESTIONS_BY_TOPIC;
import static com.tunaweza.web.views.Views.QUESTION_LIST;
import static com.tunaweza.web.views.Views.TEST_TEMPLATE_QUESTION_FORM;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(Views.QUESTION)
public class QuestionController implements Views {
	protected final Log LOGGER = LogFactory.getLog(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Autowired
	private Validator validator;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private TopicService topicService;

	@Autowired
	private EvaluationService evaluationTemplateService;

	@RequestMapping(method = RequestMethod.GET, value = TEST_TEMPLATE_QUESTION_FORM)
	public String newQuestionForm(
			@RequestParam(value = "evaluationId", required = true) String evaluationTemplateId,
			Model model) {

		/*
		 * pass the evaluation template id to the form so that question data
		 * filled in relates to that evaluation template
		 */

		AddQuestionBean addQuestionBean = new AddQuestionBean();
		addQuestionBean.setEvaluationTemplateId(String
				.valueOf(evaluationTemplateId));
		model.addAttribute("addQuestionBean", addQuestionBean);
		model.addAttribute("EVALUATIONTEMPLATEID", evaluationTemplateId);

		return TEST_TEMPLATE_QUESTION_FORM;
	}

	@RequestMapping(method = RequestMethod.GET, value = EDIT_TEST_TEMPLATE_QUESTION_FORM)
	public String editQuestionForm(
			@RequestParam(value = "questionId", required = true) String questionId,
			Model model) {

		/*
		 * pass the evaluation template id to the form so that question data
		 * filled in relates to that evaluation template
		 */
		try {
			Question question = questionService.findQuestionById(Long
					.valueOf(questionId));
			System.out.println("QUESTION >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+question.getText());

			System.out.println("QUESTION >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+question.getAnswers().get(0));
			model.addAttribute("TEMPLATEID", question.getEvaluation()
					.getId());
			List<Answer> questionChoices = question.getAnswers();

			EditQuestionBean editQuestionBean = new EditQuestionBean();
			editQuestionBean.setText(question.getText());
			editQuestionBean.setLevel(((Long) question.getLevel()).toString());

			editQuestionBean
					.setChoiceOne(questionChoices.size() > 0 ? questionChoices
							.get(0).getText() : "");

			editQuestionBean
					.setChoiceTwo(questionChoices.size() > 0 ? questionChoices
							.get(1).getText() : "");
			editQuestionBean.setChoiceTwoCorrect(questionChoices.get(1)
					.getCorrect());

			editQuestionBean
					.setChoiceThree(questionChoices.size() > 0 ? questionChoices
							.get(2).getText() : "");
			editQuestionBean.setChoiceThreeCorrect(questionChoices.get(2)
					.getCorrect());

			editQuestionBean
					.setChoiceFour(questionChoices.size() > 0 ? questionChoices
							.get(3).getText() : "");
			editQuestionBean.setChoiceFourCorrect(questionChoices.get(3)
					.getCorrect());

			model.addAttribute("editQuestionBean", editQuestionBean);
			model.addAttribute("QUESTIONID", questionId);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QuestionDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EDIT_TEST_TEMPLATE_QUESTION_FORM;
	}

	@RequestMapping(method = RequestMethod.POST, value = EDIT_TEST_TEMPLATE_QUESTION_FORM)
	public @ResponseBody
	Map<String, ? extends Object> editQuestionPost(
			@RequestBody EditQuestionBean editQuestionBean,
			@RequestParam("questionId") Long id) throws Exception {

		Set<ConstraintViolation<EditQuestionBean>> failures = validator
				.validate(editQuestionBean);
		if (!failures.isEmpty()) {
			editQuestionValidationMessages(failures);
			return Collections.singletonMap("u", "Fill in required fields");
		}

		else {
			Question question = questionService.findQuestionById(id);
			question.setText(editQuestionBean.getText());
			question.setLevel(Long.valueOf(editQuestionBean.getLevel()));
			List<Answer> questionChoices = question.getAnswers();

			Answer a = questionChoices.get(0);
			a.setText(editQuestionBean.getChoiceOne());
			answerService.updateAnswer(a);

			Answer b = questionChoices.get(1);
			b.setText(editQuestionBean.getChoiceTwo());
			b.setCorrect(editQuestionBean.isChoiceTwoCorrect());
			answerService.updateAnswer(b);

			Answer c = questionChoices.get(2);
			c.setText(editQuestionBean.getChoiceThree());
			c.setCorrect(editQuestionBean.getChoiceThreeCorrect());
			answerService.updateAnswer(c);

			Answer d = questionChoices.get(3);
			d.setText(editQuestionBean.getChoiceFour());
			d.setCorrect(editQuestionBean.getChoiceFourCorrect());
			answerService.updateAnswer(d);

			questionService.updateQuestion(question);

			return Collections.singletonMap("u", "Saved");
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = TEST_TEMPLATE_QUESTION_FORM)
	public @ResponseBody
	Map<String, ? extends Object> newQuestion(
			@RequestBody AddQuestionBean addQuestionBean) {

		Set<ConstraintViolation<AddQuestionBean>> failures = validator
				.validate(addQuestionBean);
		if (!failures.isEmpty()) {
			addValidationMessages(failures);
			return Collections.singletonMap("u", "Fill in required fields");
		} else {

			try {

				/*
				 * add the question and save it
				 */
				Question question = new Question();
				// List<Answer> questionChoices=new ArrayList<Answer>();
				LOGGER.debug("\n\n\n\n\n"
						+ "=======================================================\n"
						+ "addQuestionBean.getText().length() before trim()"
						+ "\n=======================================================\n"
						+ addQuestionBean.getText()
						+ ":"
						+ addQuestionBean.getText().length()
						+ "\n=======================================================\n"
						+ "addQuestionBean.getText().length() after trim()"
						+ "\n=======================================================\n"
						+ addQuestionBean.getText() + ":"
						+ addQuestionBean.getText().trim().length());

				question.setText(addQuestionBean.getText().trim());
				question.setLevel(Long.valueOf(addQuestionBean.getLevel()));
				try{
				question.setEvaluation(evaluationTemplateService
						.findEvaluationById(Long
								.valueOf(addQuestionBean
										.getEvaluationTemplateId())));
				}
				catch(EvaluationDoesNotExistException e){
					e.printStackTrace();
				}
				// question.setAnswers(questionChoices);

				/*
				 * save the answers and add them to the persisted question.
				 */
				Answer a = new Answer();

				a.setEnabled(true);
				a.setText(addQuestionBean.getChoiceOne().trim());
				a.setCorrect(true);
				a.setQuestion(question);

				// answerService.addAnswer(a);

				Answer b = new Answer();
				b.setEnabled(true);
				b.setText(addQuestionBean.getChoiceTwo().trim());
				if (addQuestionBean.isChoiceTwoCorrect() != null
						&& addQuestionBean.isChoiceTwoCorrect().equals(
								"correct")) {
					b.setCorrect(true);
				} else {
					b.setCorrect(false);
				}
				b.setQuestion(question);
				// answerService.addAnswer(b);

				Answer c = new Answer();
				c.setEnabled(true);
				c.setText(addQuestionBean.getChoiceThree().trim());
				if (addQuestionBean.getChoiceThreeCorrect() != null
						&& addQuestionBean.getChoiceThreeCorrect().equals(
								"correct")) {
					c.setCorrect(true);
				} else {
					c.setCorrect(false);
				}
				c.setQuestion(question);
				// answerService.addAnswer(c);

				Answer d = new Answer();
				d.setEnabled(true);
				d.setText(addQuestionBean.getChoiceFour().trim());
				if (addQuestionBean.getChoiceFourCorrect() != null
						&& addQuestionBean.getChoiceFourCorrect().equals(
								"correct")) {
					d.setCorrect(true);
				} else {
					d.setCorrect(false);
				}
				d.setQuestion(question);
				// answerService.addAnswer(d);
				ArrayList<Answer> choices =new ArrayList<Answer>();
				choices.add(a);
				choices.add(b);
				choices.add(c);
				choices.add(d);
				

				Question savedQuestion = questionService.addQuestion(question,
						choices);
				if (savedQuestion == null) {
					return Collections.singletonMap("u",
							"An unexpected Error Occured");
				}

			} catch (QuestionExistsException e) {
				e.printStackTrace();
				return Collections.singletonMap("u", "This Question Exists");
			}
			return Collections.singletonMap("u", "Saved");

		}

	}

	@RequestMapping(value = QUESTION_LIST, method = RequestMethod.GET)
	public String questionList(
			@RequestParam(value = "evaluationId", required = true) String templateId,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			@RequestParam(value = "searchString", required = false) String searchString,
			Model model) throws Exception {
		model.addAttribute("TEMPLATEID", templateId);
		Evaluation evaluationTemplate = evaluationTemplateService
				.findEvaluationById(Long.valueOf(templateId));
		model.addAttribute("MODULEID", evaluationTemplate.getModule().getId()
				.toString());
		return listEvaluationQuestions(Long.valueOf(templateId), startIndex,
				searchString, "questionslist", model);
	}

	@RequestMapping(value = "pgnteval", method = RequestMethod.GET)
	public String paginateEvalQuestions(
			@RequestParam(value = "evaluationId", required = true) String templateId,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			@RequestParam(value = "searchString", required = false) String searchString,
			Model model) throws Exception {
		model.addAttribute("TEMPLATEID", templateId);
		Evaluation evaluationTemplate = evaluationTemplateService
				.findEvaluationById(Long.valueOf(templateId));
		model.addAttribute("MODULEID", evaluationTemplate.getModule().getId()
				.toString());
		return listEvaluationQuestions(Long.valueOf(templateId), startIndex,
				searchString, QUESTION_LIST, model);
	}

	@RequestMapping(value = QUESTIONS_BY_TOPIC, method = RequestMethod.POST)
	public String viewQuestionsByTopic(
			@RequestParam("topicId") long topicId,
			@RequestParam("topicName") String topicName,
			@RequestParam("moduleId") Long moduleId,
			@RequestParam("moduleName") String moduleName,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			@RequestParam(value = "searchString", required = false) String searchString,
			Model model) {
		Long topicID = Long.valueOf(topicId);
		return getPaginatedQuestionsByTopic(topicID, startIndex, searchString,
				moduleId, topicName, moduleName, QUESTIONS_BY_TOPIC, model);

	}

	@RequestMapping(value = "listquestions/pgntbytopic", method = RequestMethod.GET)
	public String paginateQuestionsByTopic(
			@RequestParam("topicId") long topicId,
			@RequestParam("topicName") String topicName,
			@RequestParam("moduleId") Long moduleId,
			@RequestParam("moduleName") String moduleName,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			@RequestParam(value = "searchString", required = false) String searchString,
			Model model) {
		Long topicID = Long.valueOf(topicId);
		return getPaginatedQuestionsByTopic(topicID, startIndex, searchString,
				moduleId, topicName, moduleName, "listquestions/pgntbytopic",
				model);
	}

	@RequestMapping(method = RequestMethod.POST, value = LIST_QUESTIONS_NOT_ASSOCIATED_WITH_TOPIC)
	public String getEvaluationQuestionsInModuleNotAssociatedWithTopic(
			@RequestParam("moduleId") Long moduleId,
			@RequestParam("topicId") Long topicId,
			@RequestParam("topicName") String topicName,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			@RequestParam(value = "searchString", required = false) String searchString,
			Model model) {

		return getPaginatedUnassociatedQuestions(moduleId, topicId,
				searchString, topicName, startIndex,
				LIST_QUESTIONS_NOT_ASSOCIATED_WITH_TOPIC, model);
	}

	@RequestMapping(value = "listquestions/unassociatedsearchresults", method = RequestMethod.GET)
	public String paginateEvaluationQuestionsInModuleNotAssociatedWithTopic(
			@RequestParam("moduleId") Long moduleId,
			@RequestParam("topicId") Long topicId,
			@RequestParam("topicName") String topicName,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			@RequestParam(value = "searchString", required = false) String searchString,
			Model model) {
		return getPaginatedUnassociatedQuestions(moduleId, topicId,
				searchString, topicName, startIndex,
				"listquestions/unassociatedsearchresults", model);
	}

	private String listEvaluationQuestions(long templateId, String startIndex,
			String text, String view, Model model) {
		List<Question> questionList = getPaginatedQuestionsByTemplateAndText(
				Long.valueOf(templateId), text, startIndex, model);
		if (questionList.size() == 0) {
			model.addAttribute("NOQUESTIONS",
					"There are no questions in this template!");
		} else {
			List<ListQuestionBean> listQuestionBeans = new ArrayList<ListQuestionBean>();
			for (Question l : questionList) {
				ListQuestionBean listQuestionBean = new ListQuestionBean();
				listQuestionBean.setId(l.getId().toString());
				listQuestionBean
						.setLevel(Long.valueOf(l.getLevel()).toString());
				listQuestionBean.setText(l.getText());
				listQuestionBeans.add(listQuestionBean);
			}
			model.addAttribute("QUESTIONLIST", listQuestionBeans);
		}
		return view;
	}

	@RequestMapping(method = RequestMethod.POST, value = ASSOCIATE_QUESTION_WITH_TOPIC)
	public @ResponseBody
	Map<String, ? extends Object> associateQuestionsWithTopic(
			@RequestBody AssociateQuestionWithTopicBean associateQuestionWithTopicBean,
			Model model) throws Exception {

		long topicId = associateQuestionWithTopicBean.getTopicId();

		String questionIds = associateQuestionWithTopicBean.getQuestionIds();

		if (questionIds != null) {
			Question question = null;

			Topic topic = topicService.getTopicById(topicId);

			if (questionIds.startsWith("[")) {
				JSONArray idsArray = new JSONArray(questionIds);

				for (int index = 0; index < idsArray.length(); index++) {
					question = questionService.findQuestionById(idsArray
							.getLong(index));

					question.setTopic(topic);

					questionService.updateQuestion(question);
				}
			} else {
				question = questionService.findQuestionById(Long
						.valueOf(questionIds));

				question.setTopic(topic);

				questionService.updateQuestion(question);
			}

			return Collections.singletonMap("m", "success");
		} else {
			return Collections.singletonMap("m", "No Checkbox Selected");
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = DISSOCIATE_QUESTION_FROM_TOPIC)
	public @ResponseBody
	Map<String, ? extends Object> dissociateQuestionsFromTopic(
			@RequestBody DissociateQuestionFromTopicBean dissociateQuestionFromTopicBean,
			Model model) throws Exception {

		String questionIds = dissociateQuestionFromTopicBean.getQuestionIds();

		if (questionIds != null) {

			Question question = null;

			if (questionIds.startsWith("[")) {
				JSONArray idsArray = new JSONArray(questionIds);

				for (int index = 0; index < idsArray.length(); index++) {

					question = questionService.findQuestionById(idsArray
							.getLong(index));

					question.setTopic(null);

					questionService.updateQuestion(question);
				}
			} else {
				question = questionService.findQuestionById(Long
						.valueOf(questionIds));

				question.setTopic(null);

				questionService.updateQuestion(question);
			}
			return Collections.singletonMap("m", "success");
		} else {
			return Collections.singletonMap("m", "No Checkbox Selected");
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = LIST_QUESTIONS_T0_DISSOCIATE_FROM_TOPIC)
	public String listQuestionsToDissociateFromTopic(
			@RequestParam("moduleId") Long moduleId,
			@RequestParam("topicId") Long topicId,
			@RequestParam("topicName") String topicName,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			@RequestParam(value = "searchString", required = false) String searchString,
			Model model) throws EvaluationDoesNotExistException {

		return getPaginatedQuestionsByTopic(topicId, startIndex, searchString,
				moduleId, topicName, null,
				LIST_QUESTIONS_T0_DISSOCIATE_FROM_TOPIC, model);
	}

	@RequestMapping(method = RequestMethod.GET, value = "listquestions/pgnttodissociatefromtopic")
	public String paginateQuestionsToDissociateFromTopic(
			@RequestParam("moduleId") Long moduleId,
			@RequestParam("topicId") Long topicId,
			@RequestParam("topicName") String topicName,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			@RequestParam(value = "searchString", required = false) String searchString,
			Model model) throws EvaluationDoesNotExistException {

		return getPaginatedQuestionsByTopic(topicId, startIndex, searchString,
				moduleId, topicName, null,
				"listquestions/pgnttodissociatefromtopic", model);
	}

	private Map<String, String> addValidationMessages(
			Set<ConstraintViolation<AddQuestionBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<AddQuestionBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	private Map<String, String> editQuestionValidationMessages(
			Set<ConstraintViolation<EditQuestionBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<EditQuestionBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	private String getPaginatedQuestionsByTopic(Long topicId,
			String startIndex, String searchString, long moduleId,
			String topicName, String moduleName, String view, Model model) {
		try {
			topicName = topicName != null ? URLDecoder.decode(topicName,
					"UTF-8") : topicName;
			moduleName = moduleName != null ? URLDecoder.decode(moduleName,
					"UTF-8") : moduleName;
			searchString = searchString != null ? URLDecoder.decode(
					searchString, "UTF-8") : searchString;

		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e);
		}
		int noOfQuestions = questionService.getNumberOfQuestionsByTopic(
				topicId, searchString);

		List<Question> questionList = null;

		if (noOfQuestions <= 10) {
			questionList = questionService.getQuestionsByTopicAndText(topicId,
					searchString);
		} else {
			int start = 1;
			int totalPages = noOfQuestions / PAGE_SIZE;

			if (noOfQuestions != totalPages * PAGE_SIZE)
				++totalPages;

			if (startIndex != null)
				start = Integer.valueOf(startIndex);

			questionList = questionService.getPaginatedQuestionsByTopic(
					(start - 1) * PAGE_SIZE, PAGE_SIZE, topicId, searchString);

			model.addAttribute("PAGE_NUMBER", start);
			model.addAttribute("TOTAL_PAGES", totalPages);
		}
		model.addAttribute("QUESTIONLIST", questionList);
		model.addAttribute("MODULEID", moduleId);
		model.addAttribute("MODULENAME", moduleName);
		model.addAttribute("TOPICNAME", topicName);
		model.addAttribute("TOPICID", topicId);

		if (!view.equals(LIST_QUESTIONS_T0_DISSOCIATE_FROM_TOPIC)
				&& !view.equals("listquestions/pgnttodissociatefromtopic")) {
			AssociateQuestionWithTopicBean associateQuestionWithTopicBean = new AssociateQuestionWithTopicBean();
			associateQuestionWithTopicBean.setTopicId(topicId);
			model.addAttribute("associateQuestionWithTopicBean",
					associateQuestionWithTopicBean);
			if (questionList == null || questionList.size() == 0)
				model.addAttribute("NOQUESTIONS",
						"No Questions Linked to Topic");

		} else {
			DissociateQuestionFromTopicBean dissociateQuestionFromTopicBean = new DissociateQuestionFromTopicBean();
			LOGGER.info("WE SET THE dissociateQuestionFromTopicBean BEAN>>>>>>>>>>>>>>>>>>>");
			model.addAttribute("dissociateQuestionFromTopicBean",
					dissociateQuestionFromTopicBean);

			if (questionList == null || questionList.size() == 0)
				model.addAttribute("NOQUESTIONS",
						"No Questions to Dissociate From Topic");

		}

		return view;

	}

	public List<Question> getPaginatedQuestionsByTemplateAndText(
			long evaluationTemplateId, String text, String startIndex,
			Model model) {

		try {
			text = text == null ? text : URLDecoder.decode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int numberOfQuestions = questionService
				.getNumberOfQuestionsByAndText(evaluationTemplateId,
						text);
		List<Question> questionsList = null;
		if (numberOfQuestions <= 10) {
			questionsList = questionService.getQuestionsByAndText(
					evaluationTemplateId, text);
		} else {
			int start = 1;
			int totalPages = numberOfQuestions / PAGE_SIZE;

			if (numberOfQuestions != totalPages * PAGE_SIZE)
				++totalPages;

			if (startIndex != null)
				start = Integer.valueOf(startIndex);

			questionsList = questionService
					.getPaginatedQuestionsByAndText(
							evaluationTemplateId, (start - 1) * PAGE_SIZE,
							PAGE_SIZE, text);

			model.addAttribute("PAGE_NUMBER", start);
			model.addAttribute("TOTAL_PAGES", totalPages);
		}
		return questionsList;
	}

	private String getPaginatedUnassociatedQuestions(long moduleId,
			long topicId, String searchString, String topicName,
			String startIndex, String view, Model model) {

		AssociateQuestionWithTopicBean associateQuestionWithTopicBean = new AssociateQuestionWithTopicBean();
		associateQuestionWithTopicBean.setTopicId(topicId);
		List<Question> questionList = null;
		try {
			topicName = URLDecoder.decode(topicName, "UTF-8");
			searchString = searchString != null ? URLDecoder.decode(
					searchString, "UTF-8") : searchString;
			Evaluation evaluationTemplate = evaluationTemplateService
					.getEvaluationByModule(moduleId);

			Long templateId = evaluationTemplate.getId();

			int noOfQuestions = questionService
					.getNoOfQuestionsInNotAssociatedWithTopic(
							templateId, searchString);

			if (noOfQuestions <= 10) {
				questionList = questionService
						.getQuestionsInNotAssociatedWithTopic(
								templateId, searchString);
			} else {
				int start = 1;
				int totalPages = noOfQuestions / PAGE_SIZE;

				if (noOfQuestions != totalPages * PAGE_SIZE)
					++totalPages;

				if (startIndex != null)
					start = Integer.valueOf(startIndex);

				questionList = questionService
						.getPaginatedQuestionsInNotAssociatedWithTopic(
								(start - 1) * PAGE_SIZE, PAGE_SIZE, templateId,
								searchString);

				model.addAttribute("PAGE_NUMBER", start);
				model.addAttribute("TOTAL_PAGES", totalPages);
			}
		} catch (EvaluationDoesNotExistException etdne) {
			LOGGER.error(etdne);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e);
		}
		if (!view.equals(LIST_QUESTIONS_T0_DISSOCIATE_FROM_TOPIC)) {
			model.addAttribute("associateQuestionWithTopicBean",
					associateQuestionWithTopicBean);
			if (questionList == null || questionList.size() == 0)
				model.addAttribute("NOQUESTIONS",
						"No Questions to Associate With Topic");
		} else {
			DissociateQuestionFromTopicBean dissociateQuestionFromTopicBean = new DissociateQuestionFromTopicBean();

			model.addAttribute("dissociateQuestionFromTopicBean",
					dissociateQuestionFromTopicBean);

			if (questionList == null || questionList.size() == 0)
				model.addAttribute("NOQUESTIONS",
						"No Questions to Dissociate From Topic");
		}
		model.addAttribute("MODULEID", moduleId);
		model.addAttribute("TOPICNAME", topicName);
		model.addAttribute("QUESTIONLIST", questionList);
		model.addAttribute("TOPICID", topicId);

		return view;
	}

	@RequestMapping(method = RequestMethod.POST, value = DELETE_QUESTION)
	public @ResponseBody
	Map<String, ? extends Object> deleteQuestion(
			@RequestParam("questionId") Long id) {

		try {
			Question question = questionService.findQuestionById(id);
			questionService.deleteQuestion(question);
		} catch (QuestionDoesNotExistException e) {
			e.printStackTrace();
			return Collections.singletonMap("u", "deleteFailed");
		}
		return Collections.singletonMap("u", "deleted");

	}
}
