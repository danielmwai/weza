package com.tunaweza.web.controller.module;

import com.tunaweza.core.business.dao.exceptions.module.ModuleExistsException;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.model.company.CompanySettings;
import com.tunaweza.core.business.service.module.ModuleService;
import com.tunaweza.core.business.service.student.StudentService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.web.spring.configuration.module.bean.AddModuleBean;
import com.tunaweza.web.spring.configuration.module.bean.EditModuleBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.DISABLE_MODULE;
import static com.tunaweza.web.views.Views.EDIT_MODULE;
import static com.tunaweza.web.views.Views.EDIT_MODULE_FORM;
import static com.tunaweza.web.views.Views.ENABLE_MODULE;
import static com.tunaweza.web.views.Views.LIST_MODULES;
import static com.tunaweza.web.views.Views.MENTOR_LIST_MODULES;
import static com.tunaweza.web.views.Views.MODULE;
import static com.tunaweza.web.views.Views.MODULE_INFO;
import static com.tunaweza.web.views.Views.NEW_MODULE;
import static com.tunaweza.web.views.Views.PAGE_SIZE;
import static com.tunaweza.web.views.Views.STUDENT_LIST_MODULES;
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
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Module Controller
 * 
 * @author Jacktone Mony
 * @author David Gitonga
 * @author Allan Odera
 * @author Margaret Nduati
 * @author Martin Mathu
 * 
 */

@Controller
@RequestMapping(Views.MODULE)
public class ModuleController implements Views {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private TopicService topicService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private Validator validator;

	@Autowired
	private UserCastService userCastService;

	@Autowired
	private StudentService studentService;

	@RequestMapping(method = RequestMethod.GET, value = NEW_MODULE)
	public String newUserForm(Model model) {
		User user = userCastService.getUser();
		Company userCompany = user.getUserCompany();
		if (userCompany != null) {
			CompanySettings companySettings = userCompany.getCompanySettings();

			if (companySettings != null) {
				model.addAttribute("MENTORINGSTATE",
						companySettings.getMentoring());
			} else {
				model.addAttribute("MENTORINGSTATE", true);
			}
		}

		List<Module> moduleList = moduleService.getAllModules();
		model.addAttribute("MODULELIST", moduleList);
		model.addAttribute("addModuleBean", new AddModuleBean());

		return NEW_MODULE;
	}

	@RequestMapping(method = RequestMethod.POST, value = NEW_MODULE)
	public @ResponseBody
	Map<String, ? extends Object> addModule(
			@RequestBody AddModuleBean addModuleBean) throws Exception {
		Set<ConstraintViolation<AddModuleBean>> failures = validator
				.validate(addModuleBean);
		if (!failures.isEmpty()) {
			addValidationMessages(failures);
			return Collections.singletonMap("m", "Fill in required fields");
		}

		else if (!isWord(addModuleBean.getModuleName())) {
			return Collections
					.singletonMap("m",
							"Special characters(E.g *, -, +, ?, !, %) are not allowed in Module name.");
		}

		else {
			Module module = new Module();
			module.setDescription(addModuleBean.getModuleDescription());
			module.setPreRequisites(createModulePrerequisites(addModuleBean
					.getPreRequisites()));
			int enabled = 0;
			if (addModuleBean.isEnabled() == true) {
				enabled = 1;
			} else {
				enabled = 0;
			}

			if (addModuleBean.getEvaluated() != null) {
				module.setEvaluated(true);
			} else {
				module.setEvaluated(false);
			}
			module.setEnabled(enabled);
			module.setName(addModuleBean.getModuleName());
			module.setTimeToComplete(addModuleBean.getTimeToComplete() + " "
					+ addModuleBean.getDuration());

			if (addModuleBean.getMentoring() != null) {
				module.setMentoring(true);
			} else if (addModuleBean.getMentoring() == null) {
				module.setMentoring(false);
			}
			Module savedModule = new Module();
			try {
				savedModule = moduleService.addModule(module);
			} catch (ModuleExistsException ex) {
				return Collections.singletonMap("m",
						"Module " + module.getName() + " already exists!");
			}
			return Collections.singletonMap("u",
					String.valueOf(savedModule.getId()));
		}
	}

	@RequestMapping(value = EDIT_MODULE_FORM, method = RequestMethod.GET)
	public String editModule(@RequestParam("moduleId") Long id, Model model)
			throws Exception {
		User user = userCastService.getUser();
		Company userCompany = user.getUserCompany();

		if (userCompany != null) {
			CompanySettings companySettings = userCompany.getCompanySettings();

			if (companySettings != null) {
				model.addAttribute("MENTORINGSTATE",
						companySettings.getMentoring());
			} else {
				model.addAttribute("MENTORINGSTATE", true);
			}

		}

		Module module = moduleService.getModuleById(id);
		EditModuleBean editModuleBean = new EditModuleBean();

		editModuleBean.setModuleName(module.getName());
		editModuleBean.setModuleDescription(module.getDescription());
		String period = module.getTimeToComplete();
		String[] speriod = period.split(" ", 2);

		String time = speriod[0];
		String duration = speriod[1];

		editModuleBean.setTimeToComplete(time);
		editModuleBean.setDuration(duration);
		editModuleBean.setEvaluated(module.isEvaluated() + "");
		editModuleBean.setMentoring(module.isMentoring() + "");

		List<String> prerequisiteIds = module.getPreRequisites();
		List<Module> modulePrerequisites = new ArrayList<Module>();
		for (String prerequisite : prerequisiteIds) {
			modulePrerequisites.add(moduleService.getModuleById(Long
					.valueOf(prerequisite)));
		}

		List<Module> allModules = moduleService.getAllModules();
		// we dont want the module itself to be set as a prerequisite therefore
		// remove it.
		allModules.remove(module);

		// remove all previous set prerequisites
		allModules.removeAll(modulePrerequisites);

		// avoid a situation where X can be prerequisite for Y and Y a
		// prequisite for X
		List<Module> mods = new ArrayList<Module>();
		for (Module mod : allModules) {
			List<String> pre = mod.getPreRequisites();
			if (pre.contains(String.valueOf(module.getId()))) {
				mods.add(mod);
			}
		}
		allModules.removeAll(mods);
		
		model.addAttribute("allModules", allModules);
		model.addAttribute("modulePrerequisites", modulePrerequisites);
		model.addAttribute("MODULEID", String.valueOf(id));
		model.addAttribute("editModuleBean", editModuleBean);

		return EDIT_MODULE;
	}

	@RequestMapping(method = RequestMethod.POST, value = EDIT_MODULE_FORM)
	public @ResponseBody
	Map<String, ? extends Object> editModulePost(
			@RequestBody EditModuleBean editModuleBean,
			@RequestParam("moduleId") Long id) throws Exception {
		Set<ConstraintViolation<EditModuleBean>> failures = validator
				.validate(editModuleBean);
		if (!failures.isEmpty()) {
			editModuleValidationMessages(failures);
			return Collections.singletonMap("m", "Fill in required fields");
		}

		else if (!isWord(editModuleBean.getModuleName())) {
			return Collections
					.singletonMap("m",
							"Special characters(E.g *, -, +, ?, !, %) are not allowed in Module name.");
		}

		else {
			Module module = moduleService.getModuleById(id);
			module.setName(editModuleBean.getModuleName());
			module.setDescription(editModuleBean.getModuleDescription());
			module.setPreRequisites(createModulePrerequisites(editModuleBean
					.getPreRequisites()));
			module.setTimeToComplete(editModuleBean.getTimeToComplete() + " "
					+ editModuleBean.getDuration());

			List<Topic> list = topicService.getAllTopicsByModule(id);
			if (list.size() == 0)
				return null;
			if (editModuleBean.getMentoring() != null) {
				module.setMentoring(true);

				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).isExercise())
						list.get(i).setEnabled(1);
					topicService.saveTopic(list.get(i));
				}
			}

			else {
				module.setMentoring(false);
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).isExercise())
						list.get(i).setEnabled(0);
					topicService.saveTopic(list.get(i));
				}
			}

			if (editModuleBean.getEvaluated() != null) {
				module.setEvaluated(true);
			} else {
				module.setEvaluated(false);
			}
			moduleService.saveModule(module);

			return Collections.singletonMap("m", "Saved");
		}
	}

	private Map<String, String> addValidationMessages(
			Set<ConstraintViolation<AddModuleBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<AddModuleBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	private Map<String, String> editModuleValidationMessages(
			Set<ConstraintViolation<EditModuleBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<EditModuleBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	// private Map<String, String> editValidationMessages(
	// Set<ConstraintViolation<EditModuleBean>> failures) {
	// Map<String, String> failureMessages = new HashMap<String, String>();
	// for (ConstraintViolation<EditModuleBean> failure : failures) {
	// failureMessages.put(failure.getPropertyPath().toString(),
	// failure.getMessage());
	// }
	// return failureMessages;
	// }

	@RequestMapping(method = RequestMethod.GET, value = LIST_MODULES)
	public String listModules(
			@RequestParam(value = "startIndex", required = false) String startIndex,
			Model model) {

		List<Module> moduleList = new ArrayList<Module>();
		int countModules = moduleService.countModules();
		if (countModules <= PAGE_SIZE) {
			moduleList = moduleService.getAllModules();
			model.addAttribute("MODULESLIST", moduleList);
		} else {
			int start = 1;
			int totalPages = countModules / PAGE_SIZE;
			if (countModules != totalPages * PAGE_SIZE)
				++totalPages;
			if (startIndex != null) {
				start = Integer.valueOf(startIndex);
			}
			moduleList = moduleService.getPaginatedModules((start - 1)
					* PAGE_SIZE, PAGE_SIZE);
			model.addAttribute("MODULESLIST", moduleList);
			model.addAttribute("PAGE_NUMBER", start);
			model.addAttribute("TOTAL_PAGES", totalPages);
			// Collections.sort(modulesList);

			// model.addAttribute("MODULESLIST", moduleList);
		}
		return LIST_MODULES;
	}

	@RequestMapping(method = RequestMethod.GET, value = STUDENT_LIST_MODULES)
	public String listStudentModules(Model model) {

		User user = userCastService.getUser();
		model.addAttribute("MODULEMAP",
				studentService.getStudentModules(user.getStudent()));

		return MODULE + STUDENT_LIST_MODULES;
	}

	@RequestMapping(method = RequestMethod.GET, value = MENTOR_LIST_MODULES)
	public String listMentorModules(Model model) {

		List<Module> modules = new ArrayList<Module>();
		modules = moduleService.getAllModules();
		/*
		 * User user = userCastService.getUser();
		 * 
		 * Mentor mentor = null; try { mentor =
		 * mentorService.getMentorByUser(user); } catch
		 * (MentorDoesNotExistException e) { } List<MentorTemplate>
		 * mentorTemplates = mentor.getMentorTemplates();
		 * //List<MentorModulesBean> mentorModuleBeans = new
		 * ArrayList<MentorModulesBean>(); List<Module> modules = new
		 * ArrayList<Module>();
		 * 
		 * for (MentorTemplate mentorTemplate : mentorTemplates) { Module module
		 * = mentorTemplate.getModule(); if (!modules.contains(module)) {
		 * modules.add(module); } }
		 */

		/*
		 * for (Module module : modules) { MentorModulesBean mentorModulesBean =
		 * new MentorModulesBean();
		 * mentorModulesBean.setMentorId(mentor.getId().toString());
		 * mentorModulesBean.setModuleId(String.valueOf(module.getId()));
		 * mentorModulesBean.setModuleName(module.getName()); int
		 * pendingTransactions = mentorTemplateService
		 * .countExerciseTransactions(module.getId(), mentor.getId());
		 * LOGGER.info("pending 0: " + pendingTransactions);
		 * mentorModulesBean.setPendingTransactions(String
		 * .valueOf(pendingTransactions));
		 * mentorModuleBeans.add(mentorModulesBean);
		 * 
		 * }
		 */

		model.addAttribute("MODULELIST", modules);

		if (modules.size() == 0) {
			model.addAttribute("NOMODULES", "No Modules Assigned to Mentor");
		}

		return MODULE + MENTOR_LIST_MODULES;
	}

	@RequestMapping(value = ENABLE_MODULE, method = RequestMethod.GET)
	public String enableModule(
			@RequestParam("moduleId") String id,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			Model model) throws Exception {

		moduleService.enableModule(id);
		List<Module> moduleList = new ArrayList<Module>();
		int countModules = moduleService.countModules();
		if (countModules <= PAGE_SIZE) {
			moduleList = moduleService.getAllModules();
			model.addAttribute("MODULESLIST", moduleList);
		} else {
			int start = 1;
			int totalPages = countModules / PAGE_SIZE;
			if (countModules != totalPages * PAGE_SIZE)
				++totalPages;
			if (startIndex != null) {
				start = Integer.valueOf(startIndex);
			}
			moduleList = moduleService.getPaginatedModules((start - 1)
					* PAGE_SIZE, PAGE_SIZE);
			model.addAttribute("MODULESLIST", moduleList);
			model.addAttribute("PAGE_NUMBER", start);
			model.addAttribute("TOTAL_PAGES", totalPages);
		}
		return LIST_MODULES;
	}

	@RequestMapping(value = DISABLE_MODULE, method = RequestMethod.GET)
	public String disableUser(
			@RequestParam("moduleId") String id,
			@RequestParam(value = "startIndex", required = false) String startIndex,
			Model model) throws Exception {

		moduleService.disableModule(id);
		List<Module> moduleList = new ArrayList<Module>();
		int countModules = moduleService.countModules();
		if (countModules <= PAGE_SIZE) {
			moduleList = moduleService.getAllModules();
			model.addAttribute("MODULESLIST", moduleList);
		} else {
			int start = 1;
			int totalPages = countModules / PAGE_SIZE;
			if (countModules != totalPages * PAGE_SIZE)
				++totalPages;
			if (startIndex != null) {
				start = Integer.valueOf(startIndex);
			}

			moduleList = moduleService.getPaginatedModules((start - 1)
					* PAGE_SIZE, PAGE_SIZE);
			model.addAttribute("MODULESLIST", moduleList);
			model.addAttribute("PAGE_NUMBER", start);
			model.addAttribute("TOTAL_PAGES", totalPages);
		}
		return LIST_MODULES;
	}

	public static boolean isWord(String name) {
		return name.matches("[a-zA-Z][\\w\\s]*");
	}

	@RequestMapping(value = MODULE_INFO, method = RequestMethod.GET)
	public String moduleInfo(@RequestParam("moduleId") Long id, Model model)
			throws Exception {

		Module module = moduleService.getModuleById(id);
		EditModuleBean editModuleBean = new EditModuleBean();

		editModuleBean.setModuleId(String.valueOf(id));
		editModuleBean.setModuleName(module.getName());
		editModuleBean.setModuleDescription(module.getDescription());
		editModuleBean.setTimeToComplete(module.getTimeToComplete());

		model.addAttribute("editModuleBean", editModuleBean);

		return MODULE_INFO;
	}

	/**
	 * Creates a list of type string for the prerequisite module Ids
	 * 
	 * @param prerequisites
	 *            the string posted as the value of the select box from the form
	 * @return a list containing Ids of the prerequisite modules
	 */
	private List<String> createModulePrerequisites(String prerequisites) {

		List<String> prerequisiteList = new ArrayList<String>();
		if (prerequisites != null && !prerequisites.isEmpty()) {
			if ((!prerequisites.equalsIgnoreCase("None"))) {

				// when only one item is selected the form will post the item as
				// a string without [] , take this one item and put it in the
				// list
				if (!prerequisites.startsWith("[")) {
					prerequisiteList.add(prerequisites);
				} else {
					try {
						JSONArray preRequisitesArray = new JSONArray(
								prerequisites);

						for (int index = 0; index < preRequisitesArray.length(); index++) {
							prerequisiteList.add(preRequisitesArray.get(index)
									.toString());

						}
					} catch (JSONException e) {
						LOGGER.error("The string :" + prerequisites
								+ " is not a valid JSON string"
								+ e.getMessage());
					}
				}
			}
		}
		System.out.println("\n\n >> Module prerequisites list:"
				+ prerequisiteList);
		return prerequisiteList;
	}
}
