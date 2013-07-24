package com.tunaweza.web.controller.mentortemplate;

import com.tunaweza.core.business.dao.exceptions.mentor.MentorNotFoundException;
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.exercise.transaction.ExerciseTransactionType;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.mentor.MentorTemplate;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.mentor.MentorService;
import com.tunaweza.core.business.service.mentor.MentorTemplateService;
import com.tunaweza.core.business.service.module.ModuleService;
import com.tunaweza.core.business.service.solution.SolutionService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.core.business.service.user.UserService;
import com.tunaweza.web.spring.configuration.mentortemplate.bean.AddMentorTemplateBean;
import com.tunaweza.web.spring.configuration.mentortemplate.bean.AssignMentorMentorTemplateBean;
import com.tunaweza.web.spring.configuration.mentortemplate.bean.AssignMentorTemplateExercisesBean;
import com.tunaweza.web.spring.configuration.mentortemplate.bean.EditMentorTemplateBean;
import com.tunaweza.web.spring.configuration.mentortemplate.bean.MentorModulesBean;
import com.tunaweza.web.spring.configuration.mentortemplate.bean.TransactionsModuleBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.ASSIGN_MENTORTEMPLATE_EXERCISE;
import static com.tunaweza.web.views.Views.ASSIGN_MENTOR_MENTORTEMPLATES;
import static com.tunaweza.web.views.Views.EDIT_MENTORTEMPLATE;
import static com.tunaweza.web.views.Views.EDIT_MENTORTEMPLATE_FORM;
import static com.tunaweza.web.views.Views.LIST_MENTORTEMPLATE;
import static com.tunaweza.web.views.Views.LIST_MENTOR_MODULES;
import static com.tunaweza.web.views.Views.LIST_TRANSACTIONS;
import static com.tunaweza.web.views.Views.MENTORTEMPLATE_EXERCISE_FORM;
import static com.tunaweza.web.views.Views.MENTORTEMPLATE_PROFILE;
import static com.tunaweza.web.views.Views.MENTORTEMPLATE_TABBED_EXFORM;
import static com.tunaweza.web.views.Views.MENTOR_MENTORTEMPLATE_FORM;
import static com.tunaweza.web.views.Views.MENTOR_MENTORTEMPLATE_TABBED;
import static com.tunaweza.web.views.Views.NEW_MENTORTEMPLATE;
import static com.tunaweza.web.views.Views.PAGE_SIZE;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collections.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * @author Jacktone Mony
 * 
 *         Module Controller
 * 
 */

@Controller
@RequestMapping(Views.MENTORTEMPLATE)
public class MentorTemplateController implements Views {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private MentorTemplateService mentorTemplateService;

	@Autowired
	private TopicService topicService;

	@Autowired
	MentorService mentorService;

	@Autowired
	ModuleService moduleService;

	@Autowired
	SolutionService solutionService;

	@Autowired
	private UserService userService;

	@Autowired
	private Validator validator;

	@RequestMapping(method = RequestMethod.GET, value = NEW_MENTORTEMPLATE)
	public String newCourseTemplateForm(Model model,
			HttpServletResponse response) {

		List<Module> moduleList = moduleService.getAllModules();

		model.addAttribute("MODULELIST", moduleList);
		// List<Location> locationList = locationService.getAllLocations();
		// model.addAttribute("LOCATIONLIST", locationList);
		model.addAttribute("addMentorTemplateBean", new AddMentorTemplateBean());

		return NEW_MENTORTEMPLATE;
	}

	@RequestMapping(method = RequestMethod.POST, value = NEW_MENTORTEMPLATE)
	public @ResponseBody
	Map<String, ? extends Object> addCourseTemplate(
			@RequestBody AddMentorTemplateBean addMentorTemplateBean)
			throws Exception {
		Set<ConstraintViolation<AddMentorTemplateBean>> failures = validator
				.validate(addMentorTemplateBean);
		if (!failures.isEmpty()) {
			addValidationMessages(failures);
			return Collections.singletonMap("m", "Fill in required fields");
		} else if (!isWord(addMentorTemplateBean.getName())) {
			return Collections
					.singletonMap(
							"m",
							"Special characters(E.g *, -, +, ?, !, %) are not allowed in MentorTemplate name");

		}

		else {
			MentorTemplate mentorTemplate = new MentorTemplate();

			mentorTemplate.setDescription(addMentorTemplateBean
					.getDescription());
			// courseTemplate.setModules(addCourseTemplateBean.getModules());
			// courseTemplate.setModules(moduleService.getAllModules());
			mentorTemplate.setName(addMentorTemplateBean.getName());
			Module module = moduleService.getModuleById(Long
					.valueOf(addMentorTemplateBean.getModule()));
			mentorTemplate.setModule(module);

			MentorTemplate mentorTemplate1 = mentorTemplateService
					.addMentorTemplate(mentorTemplate);

			return Collections.singletonMap("u",
					String.valueOf(mentorTemplate1.getId()));
		}
	}

	@RequestMapping(value = EDIT_MENTORTEMPLATE_FORM, method = RequestMethod.GET)
	public String editCourseTemplate(
			@RequestParam("mentorTemplateId") String id, Model model)
			throws Exception {

		MentorTemplate mentorTemplate = mentorTemplateService
				.findMentorTemplateById(Long.valueOf(id));
		EditMentorTemplateBean editMentorTemplateBean = new EditMentorTemplateBean();

		List<Module> moduleList = moduleService.getAllModules();

		model.addAttribute("MODULELIST", moduleList);

		// List<Location> locationList = locationService.getAllLocations();
		// model.addAttribute("LOCATIONLIST", locationList);
		// model.addAttribute("COURSETEMPLATE", courseTemplate);

		editMentorTemplateBean.setId(id);

		editMentorTemplateBean.setName(mentorTemplate.getName());
		editMentorTemplateBean.setDescription(mentorTemplate.getDescription());
		if (mentorTemplate.getModule() != null) {
			editMentorTemplateBean.setModule(String.valueOf(mentorTemplate
					.getModule().getId()));
		} else {
			model.addAttribute("NOMODULE", "No module");
		}

		model.addAttribute("editMentorTemplateBean", editMentorTemplateBean);

		return EDIT_MENTORTEMPLATE;

	}

	@RequestMapping(method = RequestMethod.POST, value = EDIT_MENTORTEMPLATE_FORM)
	public @ResponseBody
	Map<String, ? extends Object> editMentorTemplatePost(
			@RequestBody EditMentorTemplateBean editMentorTemplateBean)
			throws Exception {
		Set<ConstraintViolation<EditMentorTemplateBean>> failures = validator
				.validate(editMentorTemplateBean);
		if (!failures.isEmpty()) {
			editCourseTemplateValidationMessages(failures);
			return Collections.singletonMap("m", "Fill in required fields");
		} else if (!isWord(editMentorTemplateBean.getName())) {
			return Collections
					.singletonMap(
							"m",
							"Special characters(E.g *, -, +, ?, !, %) are not allowed in MentorTemplate name");

		}

		else {
			MentorTemplate mentorTemplate = mentorTemplateService
					.findMentorTemplateById(Long.valueOf(editMentorTemplateBean
							.getId()));

			mentorTemplate.setId(Long.valueOf(editMentorTemplateBean.getId()));
			mentorTemplate.setDescription(editMentorTemplateBean
					.getDescription());
			mentorTemplate.setName(editMentorTemplateBean.getName());

			Module module = moduleService.getModuleById(Long
					.valueOf(editMentorTemplateBean.getModule()));
			mentorTemplate.setModule(module);

			mentorTemplateService.editMentorTemplate(mentorTemplate);

			return Collections.singletonMap("m", "Saved");
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = LIST_MENTORTEMPLATE)
	public String listCourseTemplate(Model model) {
		List<MentorTemplate> mentorTemplatesList = mentorTemplateService
				.listAllMentorTemplates();

		Collections.sort(mentorTemplatesList);

		model.addAttribute("MENTORTEMPLATESLIST", mentorTemplatesList);

		return LIST_MENTORTEMPLATE;
	}

	private Map<String, String> addValidationMessages(
			Set<ConstraintViolation<AddMentorTemplateBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<AddMentorTemplateBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	private Map<String, String> editCourseTemplateValidationMessages(
			Set<ConstraintViolation<EditMentorTemplateBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<EditMentorTemplateBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	@RequestMapping(value = ASSIGN_MENTORTEMPLATE_EXERCISE, method = RequestMethod.GET)
	public String assignMentorTemplateExercises(Model model) throws Exception {

		List<MentorTemplate> mentorTemplatesList = mentorTemplateService
				.listAllMentorTemplates();
		Collections.sort(mentorTemplatesList);

		model.addAttribute("MENTORTEMPLATESLIST", mentorTemplatesList);

		return ASSIGN_MENTORTEMPLATE_EXERCISE;
	}

	@RequestMapping(value = MENTORTEMPLATE_EXERCISE_FORM, method = RequestMethod.GET)
	public String listMTExercises(
			@RequestParam(value = "mentorTemplateId", required = false) String id,
			Model model) throws Exception {

		List<Topic> topicList = mentorTemplateService
				.getExercisesInMentorTemplate(Long.valueOf(id));

		MentorTemplate mentorTemplate = mentorTemplateService
				.findMentorTemplateById(Long.valueOf(id));
		List<Topic> allTopicList = new ArrayList<Topic>();
		if (mentorTemplate != null) {
			allTopicList = topicService.listAllExercises(mentorTemplate
					.getModule());
		} else {
			allTopicList = topicService.listAllExercises();
		}
		allTopicList.removeAll(topicList);

		Collections.sort(allTopicList);

		model.addAttribute("TOPICLIST", topicList);
		model.addAttribute("ALLTOPICLIST", allTopicList);
		model.addAttribute("TEMPLATEID", id);
		model.addAttribute("assignMentorTemplateExercisesBean",
				new AssignMentorTemplateExercisesBean());

		return MENTORTEMPLATE_EXERCISE_FORM;

	}

	@RequestMapping(method = RequestMethod.POST, value = MENTORTEMPLATE_EXERCISE_FORM)
	public @ResponseBody
	Map<String, ? extends Object> assignMentorTemplateExercisesPost(
			@RequestBody AssignMentorTemplateExercisesBean assignMentorTemplateExercisesBean)
			throws Exception {

		MentorTemplate mentorTemplate = mentorTemplateService
				.findMentorTemplateById(Long
						.valueOf(assignMentorTemplateExercisesBean.getId()));

		List<Topic> exerciseList = new ArrayList<Topic>();
		if (!assignMentorTemplateExercisesBean.getTopicList().startsWith("[")) {

			try {
				Topic exercise = topicService.getTopicById(Long
						.valueOf(assignMentorTemplateExercisesBean
								.getTopicList()));
				exerciseList.add(exercise);

			} catch (Exception e) {

				mentorTemplateService.saveExercisesToMentorTemplate(
						mentorTemplate, exerciseList);
				return Collections.singletonMap("m", "Saved");

			}

		} else
			try {
				JSONArray exercisesArray = new JSONArray(
						assignMentorTemplateExercisesBean.getTopicList());
				for (int count = 0; count < exercisesArray.length(); count++) {
					Topic exercise = topicService.getTopicById(exercisesArray
							.getLong(count));
					exerciseList.add(exercise);
				}
			} catch (NumberFormatException ex) {
				return Collections.singletonMap("m",
						"Error converting string to long");
			}
		mentorTemplateService.saveExercisesToMentorTemplate(mentorTemplate,
				exerciseList);

		return Collections.singletonMap("m", "Saved");

	}

	public static boolean isWord(String name) {

		return name.matches("[a-zA-Z][\\w\\s]*");

	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = LIST_MENTOR_MODULES)
	public String listMentorsModules(Model model,
			@RequestParam(value = "mentorId", required = true) String id) {
		Mentor mentor = null;
		try {
			mentor = mentorService.getMentorById(Long.valueOf(id));
		} catch (MentorNotFoundException e) {
		}
		List<MentorTemplate> mentorTemplates = mentor.getMentorTemplates();
		List<MentorModulesBean> mentorModuleBeans = new ArrayList<MentorModulesBean>();
		List<Module> modules = new ArrayList<Module>();

		for (MentorTemplate mentorTemplate : mentorTemplates) {
			Module module = mentorTemplate.getModule();
			if (!modules.contains(module)) {
				modules.add(module);
			}
		}

		for (Module module : modules) {
			MentorModulesBean mentorModulesBean = new MentorModulesBean();
			mentorModulesBean.setMentorId(id);
			mentorModulesBean.setModuleId(String.valueOf(module.getId()));
			mentorModulesBean.setModuleName(module.getName());
			int pendingTransactions = mentorTemplateService
					.countExerciseTransactions(module.getId(), Long.valueOf(id));
			LOGGER.info("\n\n >>>>>> pending Transactions: "
					+ pendingTransactions);
			mentorModulesBean.setPendingTransactions(String
					.valueOf(pendingTransactions));
			mentorModuleBeans.add(mentorModulesBean);

		}
		LOGGER.info("\n\n >>>>>> MODULELIST size: " + mentorModuleBeans.size());

		model.addAttribute("MODULELIST", mentorModuleBeans);
		model.addAttribute("MENTORID", id);

		return LIST_MENTOR_MODULES;
	}

	@RequestMapping(value = ASSIGN_MENTOR_MENTORTEMPLATES, method = RequestMethod.GET)
	public String assignMentorMentorTemplates(Model model) throws Exception {

		List<Mentor> mentors = mentorService.getAllMentors();

		model.addAttribute("MENTORLIST", mentors);

		return ASSIGN_MENTOR_MENTORTEMPLATES;
	}

	@RequestMapping(value = MENTOR_MENTORTEMPLATE_FORM, method = RequestMethod.GET)
	public String listMentorMT(
			@RequestParam(value = "mentorId", required = false) String id,
			Model model) throws Exception {

		List<MentorTemplate> mentorTemplateList = mentorService
				.getMentorTemplatesByMentor(Long.valueOf(id));
		List<MentorTemplate> allMentorTemplateList = mentorTemplateService
				.listAllMentorTemplates();
		allMentorTemplateList.removeAll(mentorTemplateList);

		Collections.sort(allMentorTemplateList);

		model.addAttribute("MENTORTEMPLATELIST", mentorTemplateList);
		model.addAttribute("ALLMENTORTEMPLATELIST", allMentorTemplateList);
		model.addAttribute("MENTORID", id);
		model.addAttribute("assignMentorMentorTemplateBean",
				new AssignMentorMentorTemplateBean());

		return MENTOR_MENTORTEMPLATE_FORM;

	}

	@RequestMapping(method = RequestMethod.POST, value = MENTOR_MENTORTEMPLATE_FORM)
	public @ResponseBody
	Map<String, ? extends Object> assignMentorMentorTemplatePost(
			@RequestBody AssignMentorMentorTemplateBean assignMentorMentorTemplateBean)
			throws Exception {

		Mentor mentor = mentorService.getMentorById(Long
				.valueOf(assignMentorMentorTemplateBean.getId()));

		List<MentorTemplate> mentorTemplateList = new ArrayList<MentorTemplate>();
		if (!assignMentorMentorTemplateBean.getMentorTemplateList().startsWith(
				"[")) {

			try {
				MentorTemplate mentorTemplate = mentorTemplateService
						.findMentorTemplateById(Long
								.valueOf(assignMentorMentorTemplateBean
										.getMentorTemplateList()));
				mentorTemplateList.add(mentorTemplate);

			} catch (Exception e) {
			}

		} else
			try {
				JSONArray mentorTemplateArray = new JSONArray(
						assignMentorMentorTemplateBean.getMentorTemplateList());
				for (int count = 0; count < mentorTemplateArray.length(); count++) {
					MentorTemplate mentorTemplate = mentorTemplateService
							.findMentorTemplateById(mentorTemplateArray
									.getLong(count));
					mentorTemplateList.add(mentorTemplate);
				}
			} catch (NumberFormatException ex) {
				return Collections.singletonMap("m",
						"Error converting string to long");
			}
		mentorService.saveMentorTemplatesToMentor(mentor, mentorTemplateList);
		return Collections.singletonMap("m", "Saved");
	}

	@RequestMapping(value = MENTORTEMPLATE_PROFILE, method = RequestMethod.GET)
	public String mentorTemplateProfile(
			@RequestParam("mentorTemplateId") String id, Model model)
			throws Exception {

		MentorTemplate mentorTemplate = mentorTemplateService
				.findMentorTemplateById(Long.valueOf(id));
		EditMentorTemplateBean editMentorTemplateBean = new EditMentorTemplateBean();

		editMentorTemplateBean.setId(id);

		editMentorTemplateBean.setName(mentorTemplate.getName());
		editMentorTemplateBean.setDescription(mentorTemplate.getDescription());
		if (mentorTemplate.getModule() != null) {
			editMentorTemplateBean.setModule(mentorTemplate.getModule()
					.getName());
		} else {
			model.addAttribute("NOMODULE", "No module");
		}

		model.addAttribute("editMentorTemplateBean", editMentorTemplateBean);

		return MENTORTEMPLATE_PROFILE;

	}

	@RequestMapping(value = MENTORTEMPLATE_TABBED_EXFORM, method = RequestMethod.GET)
	public String listMTTabbedExercises(
			@RequestParam(value = "mentorTemplateId", required = false) String id,
			Model model) throws Exception {

		List<Topic> topicList = mentorTemplateService
				.getExercisesInMentorTemplate(Long.valueOf(id));

		MentorTemplate mentorTemplate = mentorTemplateService
				.findMentorTemplateById(Long.valueOf(id));
		List<Topic> allTopicList = new ArrayList<Topic>();
		if (mentorTemplate != null) {
			allTopicList = topicService.listAllExercises(mentorTemplate
					.getModule());
		} else {
			allTopicList = topicService.listAllExercises();
		}
		allTopicList.removeAll(topicList);

		Collections.sort(allTopicList);

		model.addAttribute("TOPICLIST", topicList);
		model.addAttribute("ALLTOPICLIST", allTopicList);
		model.addAttribute("TEMPLATEID", id);
		model.addAttribute("assignMentorTemplateExercisesBean",
				new AssignMentorTemplateExercisesBean());

		return MENTORTEMPLATE_TABBED_EXFORM;

	}

	@RequestMapping(method = RequestMethod.POST, value = MENTORTEMPLATE_TABBED_EXFORM)
	public @ResponseBody
	Map<String, ? extends Object> assignMentorTemplateExercisesTabbedPost(
			@RequestBody AssignMentorTemplateExercisesBean assignMentorTemplateExercisesBean)
			throws Exception {

		MentorTemplate mentorTemplate = mentorTemplateService
				.findMentorTemplateById(Long
						.valueOf(assignMentorTemplateExercisesBean.getId()));

		List<Topic> exerciseList = new ArrayList<Topic>();
		if (!assignMentorTemplateExercisesBean.getTopicList().startsWith("[")) {

			try {
				Topic exercise = topicService.getTopicById(Long
						.valueOf(assignMentorTemplateExercisesBean
								.getTopicList()));
				exerciseList.add(exercise);

			} catch (Exception e) {
			}

		} else
			try {
				JSONArray exercisesArray = new JSONArray(
						assignMentorTemplateExercisesBean.getTopicList());
				for (int count = 0; count < exercisesArray.length(); count++) {
					Topic exercise = topicService.getTopicById(exercisesArray
							.getLong(count));
					exerciseList.add(exercise);
				}
			} catch (NumberFormatException ex) {
				return Collections.singletonMap("m",
						"Error converting string to long");
			}
		mentorTemplateService.saveExercisesToMentorTemplate(mentorTemplate,
				exerciseList);

		return Collections.singletonMap("m", "Saved");

	}

	@RequestMapping(method = RequestMethod.GET, value = LIST_TRANSACTIONS)
	public String listExerciseTransactions(
			Model model,
			@RequestParam(value = "mentorId", required = true) String mentorId,
			@RequestParam(value = "moduleId", required = true) String moduleId,
			@RequestParam(value = "startIndex", required = false) String startIndex) {
		List<TransactionsModuleBean> transactionModuleBeansList = new ArrayList<TransactionsModuleBean>();
		// get the number of Transactions in the Mentor Template.
		int exerciseTransactions = solutionService.countExerciseTransactions(
				Long.valueOf(moduleId), Long.valueOf(mentorId));
		/*
		 * int exerciseTransactions = solutionService
		 * .numberOfExercisesByMentorTemplate(Long.valueOf(mentorId),
		 * Long.valueOf(moduleId));
		 */
		if (exerciseTransactions > 0) {
			model.addAttribute("COUNT", exerciseTransactions);
		}

		if (exerciseTransactions <= 10) {

			List<ExerciseTransaction> et = solutionService
					.getModuleTransactionsInMentorTemplate(mentorId, moduleId);

			transactionModuleBeansList = getTransactionsModuleBeanList(et);
		} else {
			List<ExerciseTransaction> etList = new ArrayList<ExerciseTransaction>();

			int start = 1;
			int totalPages = exerciseTransactions / PAGE_SIZE;
			if (exerciseTransactions != totalPages * PAGE_SIZE) {
				++totalPages;
			}
			if (startIndex != null) {
				start = Integer.valueOf(startIndex);
			}
			etList = mentorTemplateService.getPaginatedExerciseTransactions(
					(start - 1) * PAGE_SIZE, PAGE_SIZE, Long.valueOf(moduleId),
					Long.valueOf(mentorId));
			if (etList != null) {
				transactionModuleBeansList = getTransactionsModuleBeanList(etList);
			}

			model.addAttribute("PAGE_NUMBER", start);
			model.addAttribute("TOTAL_PAGES", totalPages);

		}
		model.addAttribute("MODULEID", moduleId);
		model.addAttribute("MENTORID", mentorId);
		model.addAttribute("TRANSACTIONLIST", transactionModuleBeansList);

		return LIST_TRANSACTIONS;
	}

	public List<TransactionsModuleBean> getTransactionsModuleBeanList(
			List<ExerciseTransaction> et) {
		List<TransactionsModuleBean> transactionModuleBeans = new ArrayList<TransactionsModuleBean>();

		// Result: TransactionsModuleBean
		/*
		 * Parameters: 1. List<ExerciseTransaction> 2.
		 */
		for (ExerciseTransaction temp : et) {
			TransactionsModuleBean tempBean = new TransactionsModuleBean();
			StudentExercise studentExercise = temp.getStudentExercise();
			ExerciseTransactionType exerciseType = temp
					.getExerciseTransactionType();
			User user = new User();

			Student student = studentExercise.getStudent();
			user = student.getUser();

			Topic topic = studentExercise.getExercise();
			tempBean.setExerciseId(studentExercise.getId().toString());
			tempBean.setAuthor(user.getFirstName() + " " + user.getLastName());
			tempBean.setExerciseName(StringEscapeUtils.unescapeHtml(topic
					.getName()));
			tempBean.setTransactionDate(temp.getTransanctionDate().toString());
			if (temp.getReadDate() != null)
				tempBean.setTransactionReadDate(temp.getReadDate().toString());
			tempBean.setTransactionType(exerciseType.getDescription());

			transactionModuleBeans.add(tempBean);
		}

		return transactionModuleBeans;
	}

	@RequestMapping(value = MENTOR_MENTORTEMPLATE_TABBED, method = RequestMethod.GET)
	public String listMentorMTTabbed(
			@RequestParam(value = "userId", required = true) String id,
			Model model) throws Exception {
		User user = userService.getUserById(Long.valueOf(id));
		Mentor mentor = null;
		try {
			mentor = mentorService.getMentorByUser(user);
		} catch (MentorNotFoundException e) {
		}
		List<MentorTemplate> mentorTemplateList = null;
		try {
			mentorTemplateList = mentorService
					.getMentorTemplatesByMentor(mentor.getId());
		}

		catch (NullPointerException e) {
			e.printStackTrace();
		}

		List<MentorTemplate> allMentorTemplateList = mentorTemplateService
				.listAllMentorTemplates();
		if (mentorTemplateList != null) {
			allMentorTemplateList.removeAll(mentorTemplateList);
		}
		Collections.sort(allMentorTemplateList);

		model.addAttribute("MENTORTEMPLATELIST", mentorTemplateList);
		model.addAttribute("ALLMENTORTEMPLATELIST", allMentorTemplateList);
		model.addAttribute("MENTORID", mentor.getId());
		model.addAttribute("assignMentorMentorTemplateBean",
				new AssignMentorMentorTemplateBean());

		return MENTOR_MENTORTEMPLATE_TABBED;

	}

	@RequestMapping(method = RequestMethod.POST, value = MENTOR_MENTORTEMPLATE_TABBED)
	public @ResponseBody
	Map<String, ? extends Object> assignMentorMentorTemplatePostTabbed(
			@RequestBody AssignMentorMentorTemplateBean assignMentorMentorTemplateBean)
			throws Exception {

		Mentor mentor = mentorService.getMentorById(Long
				.valueOf(assignMentorMentorTemplateBean.getId()));

		List<MentorTemplate> mentorTemplateList = new ArrayList<MentorTemplate>();
		if (!assignMentorMentorTemplateBean.getMentorTemplateList().startsWith(
				"[")) {

			try {
				MentorTemplate mentorTemplate = mentorTemplateService
						.findMentorTemplateById(Long
								.valueOf(assignMentorMentorTemplateBean
										.getMentorTemplateList()));
				mentorTemplateList.add(mentorTemplate);

			} catch (Exception e) {
			}

		} else
			try {
				JSONArray mentorTemplateArray = new JSONArray(
						assignMentorMentorTemplateBean.getMentorTemplateList());
				for (int count = 0; count < mentorTemplateArray.length(); count++) {
					MentorTemplate mentorTemplate = mentorTemplateService
							.findMentorTemplateById(mentorTemplateArray
									.getLong(count));
					mentorTemplateList.add(mentorTemplate);
				}
			} catch (NumberFormatException ex) {
				return Collections.singletonMap("m",
						"Error converting string to long");
			}
		mentorService.saveMentorTemplatesToMentor(mentor, mentorTemplateList);
		return Collections.singletonMap("m", "Saved");
	}

}
