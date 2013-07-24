package com.tunaweza.web.controller.evaluation;

import com.tunaweza.core.business.dao.exceptions.evaluation.EvaluationDoesNotExistException;
import com.tunaweza.core.business.model.evaluation.Evaluation;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.service.evaluation.EvaluationService;
import com.tunaweza.core.business.service.module.ModuleService;
import com.tunaweza.web.spring.configuration.evaluation.AddEvaluationBean;
import com.tunaweza.web.spring.configuration.evaluation.EditEvaluationBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.EDIT_EVALUATIONTEMPLATE;
import static com.tunaweza.web.views.Views.EDIT_EVALUATIONTEMPLATE_FORM;
import static com.tunaweza.web.views.Views.LIST_EVALUATIONTEMPLATE;
import static com.tunaweza.web.views.Views.NEW_EVALUATIONTEMPLATE;
import static com.tunaweza.web.views.Views.NEW_EVALUATIONTEMPLATE_FORM;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jacktone Mony Evaluation Controller
 * 
 */

@Controller
@RequestMapping(Views.EVALUATIONTEMPLATE)
public class EvaluationController implements Views {

	@Autowired
	private EvaluationService evaluationTemplateService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private Validator validator;

	public String moduleId;

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@RequestMapping(method = RequestMethod.GET, value = NEW_EVALUATIONTEMPLATE)
	public String newEvaluationForm(
			@RequestParam("moduleId") String id, Model model,
			HttpServletResponse response) {
		AddEvaluationBean addEvaluationBean = new AddEvaluationBean();

		// set default values for addEvaluation
		double duration = 15;
		double numberOfQuestions = 10;
		int passMark = 70;
		moduleId = id;
		addEvaluationBean.setDuration(duration);
		addEvaluationBean.setNumberOfQuestions(numberOfQuestions);
		addEvaluationBean.setPassMark(passMark);

		List<Module> moduleList = moduleService.getAllModules();

		List<Module> remoduleList = new ArrayList<Module>();

		List<Evaluation> templateList = evaluationTemplateService
				.listAllEvaluations();

		if (templateList != null) {

			for (Module m : moduleList) {
				for (Evaluation eval : templateList) {
					if (eval.getModule().equals(m)) {
						remoduleList.add(m);

					}

				}

			}

			moduleList.removeAll(remoduleList);
		}

		if (moduleList == null) {
			model.addAttribute("MODULELIST", "NOMODULES");

		}

		// List<Location> locationList = locationService.getAllLocations();
		model.addAttribute("MODULELIST", moduleList);
		model.addAttribute("MODULEID", id);
		// model.addAttribute("LOCATIONLIST", locationList);
		model.addAttribute("addEvaluationBean",
				addEvaluationBean);

		return NEW_EVALUATIONTEMPLATE;
	}

	@RequestMapping(method = RequestMethod.POST, value = NEW_EVALUATIONTEMPLATE_FORM)
	public @ResponseBody
	Map<String, ? extends Object> addEvaluationPost(
			@RequestBody AddEvaluationBean addEvaluationBean)
			throws Exception {
		Set<ConstraintViolation<AddEvaluationBean>> failures = validator
				.validate(addEvaluationBean);
		if (!failures.isEmpty()) {
			addEvaluationValidationMessages(failures);
			return Collections.singletonMap("m", "Fill in required fields");
		} else if (!isWord(addEvaluationBean.getName())) {
			return Collections.singletonMap("m", "Enter a valid name");

		}

		else {
			Evaluation evaluationTemplate = new Evaluation();

			// evaluationTemplate.setId(Long.valueOf(addEvaluationBean.getId()));
			evaluationTemplate.setDescription(addEvaluationBean
					.getDescription());
			evaluationTemplate.setName(addEvaluationBean.getName());
			evaluationTemplate.setFactoring(addEvaluationBean
					.getFactoring());
			evaluationTemplate.setNumberOfQuestions(addEvaluationBean
					.getNumberOfQuestions());
			evaluationTemplate.setDuration(addEvaluationBean
					.getDuration());
			evaluationTemplate.setPassmark(addEvaluationBean
					.getPassMark());
			evaluationTemplate.setModule(moduleService.getModuleById(Long
					.valueOf(moduleId)));

			evaluationTemplateService.addEvaluation(evaluationTemplate);

			return Collections.singletonMap("m", "Saved");
		}
	}

	@RequestMapping(value = EDIT_EVALUATIONTEMPLATE_FORM, method = RequestMethod.GET)
	public String editCourseTemplate(
			@RequestParam("evaluationTemplateId") String id, Model model)
			throws Exception {

		Evaluation evaluationTemplate = evaluationTemplateService
				.findEvaluationById(Long.valueOf(id));
		String moduleId = evaluationTemplate.getModule().getId().toString();
		EditEvaluationBean editEvaluationBean = new EditEvaluationBean();

		// List<Module> moduleList = moduleService.getAllModules();
		// List<Location> locationList = locationService.getAllLocations();
		// model.addAttribute("MODULELIST", moduleList);
		// model.addAttribute("LOCATIONLIST", locationList);

		// model.addAttribute("COURSETEMPLATE", courseTemplate);

		editEvaluationBean.setId(id);

		editEvaluationBean.setName(evaluationTemplate.getName());
		editEvaluationBean.setDescription(evaluationTemplate
				.getDescription());
		editEvaluationBean.setFactoring(evaluationTemplate
				.getFactoring());
		editEvaluationBean
				.setDuration(evaluationTemplate.getDuration());
		editEvaluationBean
				.setPassMark(evaluationTemplate.getPassmark());
		editEvaluationBean.setNumberOfQuestions(evaluationTemplate
				.getNumberOfQuestions());
		model.addAttribute("MODULEID", moduleId);
		model.addAttribute("TEMPLATEID", String.valueOf(id));
		model.addAttribute("editEvaluationBean",
				editEvaluationBean);

		return EDIT_EVALUATIONTEMPLATE;

	}

	@RequestMapping(method = RequestMethod.POST, value = EDIT_EVALUATIONTEMPLATE_FORM)
	public @ResponseBody
	Map<String, ? extends Object> editCourseTemplatePost(
			@RequestBody EditEvaluationBean editEvaluationBean)
			throws Exception {
		Set<ConstraintViolation<EditEvaluationBean>> failures = validator
				.validate(editEvaluationBean);
		if (!failures.isEmpty()) {
			editEvaluationValidationMessages(failures);
			return Collections.singletonMap("m", "Fill in required fields");
		} else if (!isWord(editEvaluationBean.getName())) {
			return Collections.singletonMap("m", "Enter a valid name");

		}

		else {
			Evaluation evaluationTemplate = evaluationTemplateService
					.findEvaluationById(Long
							.valueOf(editEvaluationBean.getId()));

			evaluationTemplate.setId(Long.valueOf(editEvaluationBean
					.getId()));
			evaluationTemplate.setDescription(editEvaluationBean
					.getDescription());
			evaluationTemplate.setName(editEvaluationBean.getName());
			evaluationTemplate.setFactoring(editEvaluationBean
					.getFactoring());
			evaluationTemplate.setNumberOfQuestions(editEvaluationBean
					.getNumberOfQuestions());
			evaluationTemplate.setDuration(editEvaluationBean
					.getDuration());
			evaluationTemplate.setPassmark(editEvaluationBean
					.getPassMark());
			System.out.println(evaluationTemplate.getNumberOfQuestions());
			evaluationTemplateService
					.updateEvaluation(evaluationTemplate);

			return Collections.singletonMap("m", "Saved");
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = LIST_EVALUATIONTEMPLATE)
	public String listEvaluation(@RequestParam("moduleId") Long id,
			Model model) {
		// Evaluation evaluationTemplatesList;
		try {
			model.addAttribute("MODULEID", id.toString());

			Evaluation evaluationTemplate = evaluationTemplateService
					.getEvaluationByModule(id);

			if (evaluationTemplate != null) {
				model.addAttribute("EVALUATIONTEMPLATE", evaluationTemplate);
			}
		} catch (EvaluationDoesNotExistException e) {
			e.printStackTrace();
		}

		return LIST_EVALUATIONTEMPLATE;
	}

	public static boolean isWord(String name) {

		return name.matches("[a-zA-Z][\\w\\s]*");

	}

	private Map<String, String> editEvaluationValidationMessages(
			Set<ConstraintViolation<EditEvaluationBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<EditEvaluationBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	private Map<String, String> addEvaluationValidationMessages(
			Set<ConstraintViolation<AddEvaluationBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<AddEvaluationBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

}
