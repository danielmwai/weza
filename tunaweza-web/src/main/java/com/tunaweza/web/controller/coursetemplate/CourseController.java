package com.tunaweza.web.controller.coursetemplate;

import com.tunaweza.core.business.dao.course.CourseDao;
import com.tunaweza.core.business.dao.exceptions.course.CourseExistsException;
import com.tunaweza.core.business.dao.exceptions.course.CourseNotFoundException;
import com.tunaweza.core.business.dao.exceptions.module.ModuleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentDoesNotExistException;
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.course.CoursePreRequisite;
import com.tunaweza.core.business.model.course.EmbeddableCourse;
import com.tunaweza.core.business.model.course.EmbeddableModule;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.student.CompletedModule;
import com.tunaweza.core.business.model.student.EnabledModule;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.course.CourseService;
import com.tunaweza.core.business.service.module.ModuleService;
import com.tunaweza.core.business.service.student.StudentService;
import com.tunaweza.core.business.service.user.UserService;
import com.tunaweza.web.spring.configuration.coursetemplate.bean.AddCourseBean;
import com.tunaweza.web.spring.configuration.coursetemplate.bean.AssignCourseModulesBean;
import com.tunaweza.web.spring.configuration.coursetemplate.bean.AssignUserCourseBean;
import com.tunaweza.web.spring.configuration.coursetemplate.bean.EditCourseBean;


import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.ASSIGN_COURSETEMPLATE_MODULES;
import static com.tunaweza.web.views.Views.ASSIGN_STUDENT_COURSETEMPLATE;
import static com.tunaweza.web.views.Views.COURSETEMPLATE_MODULES_FORM;
import static com.tunaweza.web.views.Views.CT_INFO;
import static com.tunaweza.web.views.Views.EDIT_COURSETEMPLATE;
import static com.tunaweza.web.views.Views.EDIT_COURSETEMPLATE_FORM;
import static com.tunaweza.web.views.Views.EDIT_MODULE;
import static com.tunaweza.web.views.Views.LIST_COURSETEMPLATE;
import static com.tunaweza.web.views.Views.NEW_COURSETEMPLATE;
import static com.tunaweza.web.views.Views.STUDENT_COURSETEMPLATE_FORM;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
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
 * @author Jacktone Mony Course Controller
 * @author Martin Mathu
 * @author David Gitonga
 * @author Peter Kiragu
 * 
 */

@Controller
@RequestMapping(Views.COURSETEMPLATE)
public class CourseController implements Views {
	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private CourseService courseTemplateService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private UserService userService;

	@Autowired
	private Validator validator;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CourseDao courseTemplateDao;

	@RequestMapping(method = RequestMethod.GET, value = NEW_COURSETEMPLATE)
	public String newCourseForm(Model model,
			HttpServletResponse response) {

		// /List<Module> moduleList = moduleService.getAllModules();

		// List<Location> locationList = locationService.getAllLocations();
		// model.addAttribute("MODULELIST", moduleList);
		// model.addAttribute("LOCATIONLIST", locationList);
		List<Course> allCourses = courseTemplateService
				.listAllCourses();
		model.addAttribute("allCourses", allCourses);
		model.addAttribute("addCourseBean", new AddCourseBean());

		return NEW_COURSETEMPLATE;
	}

	@RequestMapping(method = RequestMethod.POST, value = NEW_COURSETEMPLATE)
	public @ResponseBody
	Map<String, ? extends Object> addCourse(
			@RequestBody AddCourseBean addCourseBean) {
		Set<ConstraintViolation<AddCourseBean>> failures = validator
				.validate(addCourseBean);
		if (!failures.isEmpty()) {
			addValidationMessages(failures);
			return Collections.singletonMap("m", "Fill in required fields");
		}

		else if (!isWord(addCourseBean.getName())) {
			return Collections
					.singletonMap(
							"m",
							"Special characters(E.g *, -, +, ?, !, %) are not allowed in Course name");

		}

		else {
			Course courseTemplate = new Course();

			courseTemplate.setDescription(addCourseBean
					.getDescription());
			if (addCourseBean.getEvaluated() != null) {
				courseTemplate.setEvaluated(true);
			} else {
				courseTemplate.setEvaluated(false);
			}

			LOGGER.info("\n\n >>> preRequisites for:"
					+ addCourseBean.getName() + " : "
					+ addCourseBean.getPreRequisites());
			String strCoursePreReq = addCourseBean.getPreRequisites();

			List<CoursePreRequisite> coursePreRequisites = createCoursePreRequisites(strCoursePreReq);
			courseTemplate.setCoursePreReQuisites(coursePreRequisites);
			courseTemplate.setName(addCourseBean.getName());

			try {
				Course savedTemplate = courseTemplateService
						.addCourse(courseTemplate);

				return Collections.singletonMap("t",
						String.valueOf(savedTemplate.getId()));
			}

			catch (CourseExistsException e) {
				e.printStackTrace();

				return Collections.singletonMap("u",
						"course template already exists");
			}

		}
	}

	/**
	 * creates list of CoursePreRequisite which are embeddable tables containing
	 * IDs for the PreRequisite Courses
	 * 
	 * @param strCoursePreReq
	 * @return list of CoursePreRequisite
	 * 
	 */
	private List<CoursePreRequisite> createCoursePreRequisites(
			String strCoursePreReq) {
		// Stores the prerequisite Courses
		List<CoursePreRequisite> coursePreRequisites = new ArrayList<CoursePreRequisite>();
		if (strCoursePreReq != null && !strCoursePreReq.isEmpty()) {
			if ((!strCoursePreReq.equalsIgnoreCase("None"))) {
				LOGGER.info("\n\n >>> preRequisites : " + strCoursePreReq);

				if (!strCoursePreReq.startsWith("[")) {
					CoursePreRequisite coursePreRequisite = new CoursePreRequisite();
					try {
						coursePreRequisite
								.setCoursePreRequisiteId(courseTemplateService
										.findCourseByName(
												strCoursePreReq).getId());
					} catch (CourseNotFoundException e) {
						LOGGER.error("Course with name:" + strCoursePreReq
								+ " was not found" + e.getMessage());

					}
					coursePreRequisites.add(coursePreRequisite);

				} else {
					JSONArray preRequisitesArray;
					try {
						preRequisitesArray = new JSONArray(strCoursePreReq);

						System.out.println("\n\n >>> preRequisitesArray:"
								+ strCoursePreReq);
						int count = 0;

						for (count = 0; count < preRequisitesArray.length(); count++) {
							CoursePreRequisite coursePreRequisite = new CoursePreRequisite();
							try {
								coursePreRequisite
										.setCoursePreRequisiteId(courseTemplateService
												.findCourseByName(
														preRequisitesArray.get(
																count)
																.toString())
												.getId());
							} catch (CourseNotFoundException e) {
								LOGGER.error("Course with name:"
										+ strCoursePreReq + " was not found"
										+ e.getMessage());
							}
							coursePreRequisites.add(coursePreRequisite);

						}
					} catch (JSONException e) {
						LOGGER.error("The string :" + strCoursePreReq
								+ " is not a valid JSON string"
								+ e.getMessage());
					}
				}

			}
		}
		System.out
				.println("\n\n >>> coursePreRequisites" + coursePreRequisites);
		return coursePreRequisites;
	}

	@RequestMapping(value = EDIT_COURSETEMPLATE_FORM, method = RequestMethod.GET)
	public String editCourse(
			@RequestParam("courseTemplateId") String id, Model model) {

		Course courseTemplate = null;

		try {
			courseTemplate = courseTemplateService.findCourseById(Long
					.valueOf(id));
		}

		catch (CourseNotFoundException e) {
			e.printStackTrace();
		}
		EditCourseBean editCourseBean = new EditCourseBean();

		editCourseBean.setId(id);

		editCourseBean.setName(courseTemplate.getName());
		editCourseBean.setDescription(courseTemplate.getDescription());
		System.out.println("\n\n\n >>>>> EDIT ct:"
				+ courseTemplate.getDescription());
		editCourseBean.setEvaluated(courseTemplate.isEvaluated() + "");
		model.addAttribute("TEMPLATEID", String.valueOf(id));
		model.addAttribute("editCourseBean", editCourseBean);

		List<Course> allCourses = courseTemplateService
				.listAllCourses();

		// all course preRequisite. this contains only the embbeded table that
		// only contains IDs of the courses
		List<CoursePreRequisite> coursePreRequisites = courseTemplate
				.getCoursePreReQuisites();

		// actual course prerequisites contains the Course entities
		List<Course> preRequisites = new ArrayList<Course>();
		if (!coursePreRequisites.isEmpty()) {

			for (CoursePreRequisite coursePreRequisite : coursePreRequisites) {
				try {
					preRequisites.add(courseTemplateService
							.findCourseById(coursePreRequisite
									.getCoursePreRequisiteId()));
				} catch (CourseNotFoundException e) {
					e.printStackTrace();

				}
			}

		}
		// We dont want a course to contain itself as a pre requisite
		allCourses.remove(courseTemplate);
		allCourses.removeAll(preRequisites);

		// ---------------------------------------------------------
		// avoid a situation where X can be prerequisite for Y and Y a
		// prequisite for X
		List<Course> cts = new ArrayList<Course>();
		for (Course ct : allCourses) {
			List<CoursePreRequisite> ctPreRequisites = ct
					.getCoursePreReQuisites();
			List<Course> prereq = new ArrayList<Course>();
			for (CoursePreRequisite c : ctPreRequisites) {
				try {
					prereq.add(courseTemplateService.findCourseById(c
							.getCoursePreRequisiteId()));
				} catch (CourseNotFoundException e) {
					e.printStackTrace();
				}
			}

			if (prereq.contains(courseTemplate)) {
				cts.add(ct);
			}
		}

		allCourses.removeAll(cts);
		// -----------------------------------------------------------

		model.addAttribute("allCourses", allCourses);
		model.addAttribute("preRequisites", preRequisites);

		return EDIT_COURSETEMPLATE;
	}

	@RequestMapping(method = RequestMethod.POST, value = EDIT_COURSETEMPLATE_FORM)
	public @ResponseBody
	Map<String, ? extends Object> editCoursePost(
			@RequestBody EditCourseBean editCourseBean) {
		Set<ConstraintViolation<EditCourseBean>> failures = validator
				.validate(editCourseBean);
		if (!failures.isEmpty()) {
			editCourseValidationMessages(failures);
			return Collections.singletonMap("m", "Fill in required fields");
		}

		else if (!isWord(editCourseBean.getName())) {
			return Collections
					.singletonMap(
							"m",
							"Special characters(E.g *, -, +, ?, !, %) are not allowed in Course name");
		}

		else {
			Course courseTemplate = null;
			try {
				courseTemplate = courseTemplateService
						.findCourseById(Long
								.valueOf(editCourseBean.getId()));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (/*CourseNotFound*/Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			courseTemplate.setId(Long.valueOf(editCourseBean.getId()));
			courseTemplate.setDescription(editCourseBean
					.getDescription());
			courseTemplate.setName(editCourseBean.getName());
			if (editCourseBean.getEvaluated() != null) {
				courseTemplate.setEvaluated(true);
			} else {
				courseTemplate.setEvaluated(false);
			}
			// ====================================================
			// Stores the prerequisite Courses
			String strCoursePreReq = editCourseBean.getPreRequisites();
			List<CoursePreRequisite> coursePreRequisites = createCoursePreRequisites(strCoursePreReq);
			courseTemplate.setCoursePreReQuisites(coursePreRequisites);

			courseTemplateService.editCourse(courseTemplate);

			return Collections.singletonMap("m", "Saved");
		}
	}

	private Map<String, String> addValidationMessages(
			Set<ConstraintViolation<AddCourseBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<AddCourseBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	private Map<String, String> editCourseValidationMessages(
			Set<ConstraintViolation<EditCourseBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<EditCourseBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	@RequestMapping(method = RequestMethod.POST, value = EDIT_MODULE)
	public @ResponseBody
	Map<String, ? extends Object> editModule(
			@RequestBody EditCourseBean editCourseBean) {
		Set<ConstraintViolation<EditCourseBean>> failures = validator
				.validate(editCourseBean);
		if (!failures.isEmpty()) {
			editCourseValidationMessages(failures);
			return Collections.singletonMap("m", "Fill in required fields");
		}

		else {
			Course courseTemplate = null;
			try {
				courseTemplate = courseTemplateService
						.findCourseById(Long
								.valueOf(editCourseBean.getId()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (/*CourseNotFound*/Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			courseTemplate.setId(Long.valueOf(editCourseBean.getId()));
			courseTemplate.setDescription(editCourseBean
					.getDescription());
			courseTemplate.setName(editCourseBean.getName());

			try {
				courseTemplateService.addCourse(courseTemplate);
			} catch (CourseExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Collections.singletonMap("m", "Saved");
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = LIST_COURSETEMPLATE)
	public String listCourse(Model model) {
		List<Course> courseTemplatesList = courseTemplateService
				.listAllCourses();

		Collections.sort(courseTemplatesList);

		model.addAttribute("COURSETEMPLATESLIST", courseTemplatesList);

		return LIST_COURSETEMPLATE;
	}

	@RequestMapping(value = ASSIGN_COURSETEMPLATE_MODULES, method = RequestMethod.GET)
	public String assignCourseModules(Model model) throws Exception {

		List<Course> courseTemplatesList = courseTemplateService
				.listAllCourses();
		Collections.sort(courseTemplatesList);

		model.addAttribute("COURSETEMPLATESLIST", courseTemplatesList);
		return ASSIGN_COURSETEMPLATE_MODULES;
	}

	@RequestMapping(value = ASSIGN_STUDENT_COURSETEMPLATE, method = RequestMethod.GET)
	public String assignStudentCourse(Model model) throws Exception {

		List<User> studentList = userService.getAllStudents();
		Collections.sort(studentList);
		model.addAttribute("STUDENTLIST", studentList);
		return ASSIGN_STUDENT_COURSETEMPLATE;
	}

	@RequestMapping(value = COURSETEMPLATE_MODULES_FORM, method = RequestMethod.GET)
	public String listCTModules(
			@RequestParam(value = "courseTemplateId", required = false) String id,
			Model model) {

		List<Module> moduleList = null;
		try {
			moduleList = courseTemplateService
					.getModulesInCourse(courseTemplateService
							.findCourseById(Long.valueOf(id)));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Module> allModuleList = moduleService.getAllModules();
		allModuleList.removeAll(moduleList);
		Collections.sort(allModuleList);

		model.addAttribute("MODULELIST", moduleList);
		model.addAttribute("ALLMODULELIST", allModuleList);
		model.addAttribute("TEMPLATEID", id);
		model.addAttribute("assignCourseModulesBean",
				new AssignCourseModulesBean());

		return COURSETEMPLATE_MODULES_FORM;

	}

	/*
	 * enabled modules will be determined by the prerequisites. See
	 * documentation inside method for changes made.
	 */
	@RequestMapping(method = RequestMethod.POST, value = COURSETEMPLATE_MODULES_FORM)
	public @ResponseBody
	Map<String, ? extends Object> assignCourseModulesPost(
			@RequestBody AssignCourseModulesBean assignCourseBean) {

		Course courseTemplate = null;
		try {
			courseTemplate = courseTemplateService.findCourseById(Long
					.valueOf(assignCourseBean.getId()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (/*CourseNotFound*/Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// all course template modules before changes
		List<Module> allCTModules = courseTemplateService
				.getModulesInCourse(courseTemplate);

		List<EmbeddableModule> moduleList = new ArrayList<EmbeddableModule>();
		try {
			if (!assignCourseBean.getModuleList().startsWith("[")) {
				EmbeddableModule embedd = new EmbeddableModule();
				embedd.setModuleId(Long.valueOf(assignCourseBean
						.getModuleList()));
				embedd.setLevel(Long.valueOf(1));
				moduleList.add(embedd);

			} else
				try {
					JSONArray modulesArray = null;
					try {
						modulesArray = new JSONArray(
								assignCourseBean.getModuleList());
					} catch (/*JSON*/Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (int count = 0; count < modulesArray.length(); count++) {
						EmbeddableModule embedd = new EmbeddableModule();
						try {
							embedd.setModuleId(Long.valueOf(modulesArray
									.getLong(count)));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						embedd.setLevel(Long.valueOf(count + 1));
						moduleList.add(embedd);
					}
				} catch (NumberFormatException ex) {
					return Collections.singletonMap("m",
							"Error converting string to long");
				}

		} catch (NullPointerException n) {
			System.out.println("Course template has no assigned module !");

		} catch (NumberFormatException nfe) {
			System.out.println("NumberFormatException !");

		}

		// oldModID stores a list of all module IDs before changes
		// newModID stores a list of all module IDs after changes
		List<String> oldModID = new ArrayList<String>();
		List<String> newModID = new ArrayList<String>();

		for (EmbeddableModule embeddableModule : moduleList) {
			newModID.add(embeddableModule.getModuleId().toString());
		}

		for (Module module : allCTModules) {
			oldModID.add(module.getId().toString());

		}

		List<Student> allStudents = studentService.getAllStudents();
		/*
		 * Module newFirstModule = null; try { newFirstModule =
		 * moduleService.getModuleById(moduleList.get(0) .getModuleId()); }
		 * catch (ModuleDoesNotExistException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */

		List<Module> newModuleList = moduleService.getAllModules();

		// if new list of modules does not contain all the modules check if
		// those that were removed are currently being done by any student

		for (Student student : allStudents) {

			/*
			 * try { studentService.enableFirstStudentModule(newFirstModule,
			 * student.getUser()); } catch (StudentDoesNotExistException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */
			if (newModuleList.size() != 0 && moduleList.size() != 0) {
				for (Module module : newModuleList) {
					if (module.getId() != moduleList.get(0).getModuleId()) {

						try {
							studentService.disableStudentModule(module,
									student.getUser());
						} catch (StudentDoesNotExistException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			if (!newModID.containsAll(oldModID)) {

				Date startDate = new Date();
				List<EnabledModule> currentEnabledModules = student
						.getEnabledModules();
				if (currentEnabledModules.size() > 0) {

					List<CompletedModule> completedModules = student
							.getCompletedModules();

					List<String> completedModID = new ArrayList<String>();

					for (CompletedModule completedModule : completedModules) {
						completedModID.add(String.valueOf(completedModule
								.getModuleId()));

					}

					// currentEnabledModules
					for (EnabledModule em : currentEnabledModules) {

						startDate = em.getModuleStartDate();
						if (startDate != null) {
							String modID;
							modID = String.valueOf(em.getModuleId());
							if ((!newModID.contains(modID))
									&& (!completedModID.contains(modID))) {

								try {
									return Collections
											.singletonMap(
													"m",
													"Module :<b>"
															+ moduleService
																	.getModuleById(
																			Long.valueOf(modID))
																	.getName()
															+ "</b></br> is currently being done by a student and cannot be removed.");
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ModuleDoesNotExistException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						}

					}
				}
			}
		}

		courseTemplateService.saveModulesToCourse(courseTemplate,
				moduleList);

		/*
		 * for (Student student : allStudents) {
		 * 
		 * 
		 * for (EmbeddableCourse eCTemplate : student
		 * .getCourseList()) {
		 * 
		 * Course cTemplate = courseTemplateDao
		 * .findById(eCTemplate.getCourseId());
		 * 
		 * List<EnabledModule> en = student.getEnabledModules();
		 * List<EmbeddableModule> Mlevel = new ArrayList<EmbeddableModule>();
		 * 
		 * List<EmbeddableModule> eModules = cTemplate.getModules();
		 * 
		 * 
		 * 
		 * for (EmbeddableModule eModule : eModules) {
		 * 
		 * Mlevel.add(eModule);
		 * 
		 * }
		 * 
		 * Module module = moduleService.getModuleById(Mlevel.get(0)
		 * .getModuleId()); studentService.enableFirstStudentModule(module,
		 * student.getUser());
		 * 
		 * List<Module> newCTModules = courseTemplateService
		 * .getModulesInCourse(courseTemplate);
		 * 
		 * for(Module mod:newCTModules){
		 * 
		 * if(module.getId()!=mod.getId()){
		 * 
		 * studentService.disableStudentModule(mod, student.getUser());
		 * 
		 * } } }
		 * 
		 * 
		 * }
		 */

		return Collections.singletonMap("m", "Saved");

	}

	@RequestMapping(value = STUDENT_COURSETEMPLATE_FORM, method = RequestMethod.GET)
	public String listCTStudents(
			@RequestParam(value = "userId", required = false) String id,
			Model model) {

		List<Course> allTemplates = courseTemplateService
				.listAllCourses();

		try {

			// Gets all assigned course template. Doc not written by author
			// therefore not sure
			List<EmbeddableCourse> studentTemplateE = userService
					.getUserCourse(Long.valueOf(id));

			List<Course> studentTemplate = new ArrayList<Course>();

			for (EmbeddableCourse embeddT : studentTemplateE) {

				try {
					studentTemplate.add(courseTemplateService
							.findCourseById(embeddT
									.getCourseId()));
				} catch (CourseNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			allTemplates.removeAll(studentTemplate);
			model.addAttribute("STUDENTTEMPLATE", studentTemplate);
		} catch (NullPointerException ex) {
			// /model.addAttribute("STUDENTTEMPLATE","none");
		}

		model.addAttribute("ALLTEMPLATELIST", allTemplates);
		model.addAttribute("USERID", id);
		model.addAttribute("assignUserCourseBean",
				new AssignUserCourseBean());

		return STUDENT_COURSETEMPLATE_FORM;
	}

	@RequestMapping(method = RequestMethod.POST, value = STUDENT_COURSETEMPLATE_FORM)
	public @ResponseBody
	Map<String, ? extends Object> assignStudentCoursePost(
			@RequestBody AssignUserCourseBean assignUserCourseBean)
			throws Exception {
		// courseTemplate=null;
		List<EmbeddableCourse> courseTemplateList = new ArrayList<EmbeddableCourse>();

		System.out.println("Outside >>>>>>>>>>>>>>>>>>>" + courseTemplateList);

		try {
			if (!assignUserCourseBean.getTemplateList().startsWith("[")) {

				EmbeddableCourse embedd = new EmbeddableCourse();

				embedd.setCourseId(Long
						.valueOf(assignUserCourseBean.getTemplateList()));
				embedd.setLevel(Long.valueOf(1));

				courseTemplateList.add(embedd);
				/*
				 * courseTemplateList.add(courseTemplateService
				 * .findCourseById
				 * (Long.valueOf(assignUserCourseBean
				 * .getTemplateList())));
				 */

			} else {
				JSONArray templatesArray = new JSONArray(
						assignUserCourseBean.getTemplateList());

				for (int i = 0; i < templatesArray.length(); i++) {

					EmbeddableCourse embedd = new EmbeddableCourse();
					embedd.setCourseId(templatesArray.getLong(i));
					embedd.setLevel(Long.valueOf(i + 1));
					System.out.println("In array >>>>>>>>>>>>>>>>>>>"
							+ courseTemplateList);

					/*
					 * courseTemplateList.add(courseTemplateService
					 * .findCourseById(Long.valueOf(templatesArray
					 * .getLong(i))));
					 */

					courseTemplateList.add(embedd);
				}

			}
			System.out.print(assignUserCourseBean.getUserId());

		} catch (NullPointerException n) {
			System.out.println("Student not assigned any course template !");

		}
		User user = null;

		try {
			user = userService.getUserById(Long
					.valueOf(assignUserCourseBean.getUserId()));
		}

		catch (/*UserDoesNotExist*/Exception e) {
			e.printStackTrace();
		}

		if (courseTemplateList != null && courseTemplateList.size() != 0) {

			// studentDao.getStudentById(user.getStudent().getId());
			Course temp = courseTemplateDao.findById(courseTemplateList
					.get(0).getCourseId());

			List<EmbeddableModule> ambed = temp.getModules();
			List<EmbeddableModule> Mlevel = new ArrayList<EmbeddableModule>();

			for (EmbeddableModule em : ambed) {
				Mlevel.add(em);
			}
			if (Mlevel.size() != 0) {

				// courseTemplateList was already checked for null above. this
				// seems unnecessary
				if (courseTemplateList != null) {

					for (int i = 0; i < courseTemplateList.size(); i++) {
						try {
							if (courseTemplateList.get(i).isComplete()) {

							} else {

								// user = userService.getUserById(Long
								// .valueOf(assignUserCourseBean
								// .getUserId()));

								Student student = user.getStudent();
								try {
									List<EnabledModule> enabledModules = student
											.getEnabledModules();
									Module module;
									if (enabledModules != null) {
										for (int j = 0; j <= enabledModules
												.size(); j++) {
											try {

												module = moduleService
														.getModuleById(enabledModules
																.get(j)
																.getModuleId());
												studentService
														.disableStudentModule(
																module, user);
											}

											catch (ModuleDoesNotExistException e) {

												e.printStackTrace();
											} catch (IndexOutOfBoundsException e) {
												e.printStackTrace();
											}

											catch (StudentDoesNotExistException e) {
												e.printStackTrace();
											}
										}

									}
								} catch (NullPointerException e) {
									e.printStackTrace();
								}

							}
						} catch (IndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					}

					try {
						user = userService.getUserById(Long
								.valueOf(assignUserCourseBean
										.getUserId()));
					} catch (/*UserDoesNotExist*/Exception e) {

						e.printStackTrace();
					}

					Course template = courseTemplateDao
							.findById(courseTemplateList.get(0)
									.getCourseId());

					List<EmbeddableModule> courseModules = template
							.getModules();

					List<EmbeddableModule> level = new ArrayList<EmbeddableModule>();

					for (EmbeddableModule em : courseModules) {
						level.add(em);
					}
					/*
					 * set modules to use prerequisites instead of the ordering
					 * level. Uncomment this section if we need to use the old
					 * way
					 */
					// Module module = moduleService.getModuleById(level.get(0)
					// .getModuleId());
					//
					// // This is where the first module is set as enabled
					// studentService.enableFirstStudentModule(module, user);

					userService.setUserCourse(
							assignUserCourseBean.getUserId(),
							courseTemplateList);
				}
			} else {

				return Collections
						.singletonMap(
								"m",
								"You have assigned student an empty courseTemplate: please add module to the template");
			}
			userService.setUserCourse(
					assignUserCourseBean.getUserId(),
					courseTemplateList);
		}
		userService.setUserCourse(
				assignUserCourseBean.getUserId(), courseTemplateList);
		return Collections.singletonMap("m", "Saved");

	}

	public static boolean isWord(String name) {

		return name.matches("[a-zA-Z][\\w\\s]*");

	}

	@RequestMapping(value = CT_INFO, method = RequestMethod.GET)
	public String moduleInfo(@RequestParam("courseTemplateId") Long id,
			Model model) {

		Course template = null;
		try {
			template = courseTemplateService.findCourseById(id);
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EditCourseBean editCourseBean = new EditCourseBean();

		editCourseBean.setId(String.valueOf(id));
		editCourseBean.setName(template.getName());
		editCourseBean.setDescription(template.getDescription());

		model.addAttribute("editCourseBean", editCourseBean);

		return CT_INFO;
	}
}
