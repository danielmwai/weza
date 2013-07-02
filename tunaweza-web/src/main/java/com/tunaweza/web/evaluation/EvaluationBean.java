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

package com.tunaweza.web.evaluation;


import com.tunaweza.core.business.dao.exceptions.evaluation.EvaluationDoesNotExistException;
import com.tunaweza.core.business.model.evaluation.Evaluation;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.service.evaluation.EvaluationService;
import com.tunaweza.core.business.service.module.ModuleService;
import com.tunaweza.web.views.Views;
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
import org.infinispan.util.logging.Log;
import org.infinispan.util.logging.LogFactory;
import org.jboss.marshalling.river.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.webflow.engine.model.Model;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class EvaluationBean implements Views {

	@Autowired
	private EvaluationService evaluationService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private Validator validator;

	public String moduleId;

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@RequestMapping(method = RequestMethod.GET, value = NEW_EVALUATIONTEMPLATE)
	public String newEvaluationTemplateForm(
			@RequestParam("moduleId") String id, Model model,
			HttpServletResponse response) {
		AddEvaluationBean addEvaluationTemplateBean = new AddEvaluationBean();

		// set default values for addEvaluationTemplate
		double duration = 15;
		double numberOfQuestions = 10;
		int passMark = 70;
		moduleId = id;
		addEvaluationTemplateBean.setDuration(duration);
		addEvaluationTemplateBean.setNumberOfQuestions(numberOfQuestions);
		addEvaluationTemplateBean.setPassMark(passMark);

		List<Module> moduleList = moduleService.getAllModules();

		List<Module> remoduleList = new ArrayList<Module>();

		List<Evaluation> list = evaluationService
				.listAllEvaluation();

		if (list != null) {

			for (Module m : moduleList) {
				for (Evaluation eval : list) {
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
		model.addAttribute("addEvaluationTemplateBean",
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
			Evaluation evaluation = new Evaluation();

			// evaluationTemplate.setId(Long.valueOf(addEvaluationTemplateBean.getId()));
			evaluation.setDescription(addEvaluationBean
					.getDescription());
			evaluation.setName(addEvaluationBean.getName());
			evaluation.setFactoring(addEvaluationBean
					.getFactoring());
			evaluation.setNumberOfQuestions(addEvaluationBean
					.getNumberOfQuestions());
			evaluation.setDuration(addEvaluationBean
					.getDuration());
			evaluation.setPassmark(addEvaluationBean
					.getPassMark());
			evaluation.setModule(moduleService.getModuleById(Long
					.valueOf(moduleId)));

			evaluationTemplateService.addEvaluation(evaluation);

			return Collections.singletonMap("m", "Saved");
		}
	}

	@RequestMapping(value = EDIT_EVALUATION_FORM, method = RequestMethod.GET)
	public String editCourse(
			@RequestParam("evaluationId") String id, Model model)
			throws Exception {

		Evaluation evaluation = evaluationTemplateService
				.findEvaluationById(Long.valueOf(id));
		String moduleId = evaluation.getModule().getId().toString();
		EditEvaluationBean editEvaluationBean = new EditEvaluationBean();

		// List<Module> moduleList = moduleService.getAllModules();
		// List<Location> locationList = locationService.getAllLocations();
		// model.addAttribute("MODULELIST", moduleList);
		// model.addAttribute("LOCATIONLIST", locationList);

		// model.addAttribute("COURSETEMPLATE", courseTemplate);

		editEvaluationBean.setId(id);

		editEvaluationBean.setName(evaluation.getName());
		editEvaluationBean.setDescription(evaluation
				.getDescription());
		editEvaluationBean.setFactoring(evaluation
				.getFactoring());
		editEvaluationBean
				.setDuration(evaluation.getDuration());
		editEvaluationBean
				.setPassMark(evaluation.getPassmark());
		editEvaluationBean.setNumberOfQuestions(evaluation
				.getNumberOfQuestions());
		model.addAttribute("MODULEID", moduleId);
		model.addAttribute("TEMPLATEID", String.valueOf(id));
		model.addAttribute("editEvaluationTemplateBean",
				editEvaluationBean);

		return EDIT_EVALUATION;

	}

	@RequestMapping(method = RequestMethod.POST, value = EDIT_EVALUATION_FORM)
	public @ResponseBody
	Map<String, ? extends Object> editCoursePost(
			@RequestBody EditEvaluationBean editEvaluationTemplateBean)
			throws Exception {
		Set<ConstraintViolation<EditEvaluationBean>> failures = validator
				.validate(editEvaluationTemplateBean);
		if (!failures.isEmpty()) {
			editEvaluationValidationMessages(failures);
			return Collections.singletonMap("m", "Fill in required fields");
		} else if (!isWord(editEvaluationBean.getName())) {
			return Collections.singletonMap("m", "Enter a valid name");

		}

		else {
			Evaluation evaluation = evaluationService
					.findEvaluationById(Long
							.valueOf(editEvaluationBean.getId()));

			evaluation.setId(Long.valueOf(editEvaluationBean
					.getId()));
			evaluation.setDescription(editEvaluationBean
					.getDescription());
			evaluation.setName(editEvaluationBean.getName());
			evaluation.setFactoring(editEvaluationBean
					.getFactoring());
			evaluation.setNumberOfQuestions(editEvaluationBean
					.getNumberOfQuestions());
			evaluation.setDuration(editEvaluationBean
					.getDuration());
			evaluation.setPassmark(editEvaluationBean
					.getPassMark());
			System.out.println(evaluation.getNumberOfQuestions());
			evaluationTemplateService
					.updateEvaluation(evaluation);

			return Collections.singletonMap("m", "Saved");
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = LIST_EVALUATION)
	public String listEvaluation(@RequestParam("moduleId") Long id,
			Model model) {
		// EvaluationTemplate evaluationTemplatesList;
		try {
			model.addAttribute("MODULEID", id.toString());

			EvaluationTemplate evaluationTemplate = evaluationTemplateService
					.getEvaluationTemplateByModule(id);

			if (evaluationTemplate != null) {
				model.addAttribute("EVALUATION", evaluation);
			}
		} catch (EvaluationDoesNotExistException e) {
			e.printStackTrace();
		}

		return LIST_EVALUATION;
	}

	public static boolean isWord(String name) {

		return name.matches("[a-zA-Z][\\w\\s]*");

	}

	private Map<String, String> editEvaluationValidationMessages(
			Set<ConstraintViolation<EditEvaluationTemplateBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<EditEvaluationTemplateBean> failure : failures) {
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

