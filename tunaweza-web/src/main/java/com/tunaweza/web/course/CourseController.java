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
package com.tunaweza.web.course;

import com.tunaweza.core.business.dao.course.CourseDao;
import com.tunaweza.core.business.dao.exceptions.course.CourseExistsException;
import com.tunaweza.core.business.dao.exceptions.course.CourseNotFoundException;
import com.tunaweza.core.business.dao.exceptions.module.ModuleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
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
import static com.tunaweza.web.views.Views.ASSIGN_COURSE_MODULES;
import static com.tunaweza.web.views.Views.ASSIGN_STUDENT_COURSE;
import static com.tunaweza.web.views.Views.COURSE_MODULES_FORM;
import static com.tunaweza.web.views.Views.CT_INFO;
import static com.tunaweza.web.views.Views.EDIT_COURSE;
import static com.tunaweza.web.views.Views.EDIT_COURSE_FORM;
import static com.tunaweza.web.views.Views.EDIT_MODULE;
import static com.tunaweza.web.views.Views.LIST_COURSE;
import static com.tunaweza.web.views.Views.NEW_COURSE;
import static com.tunaweza.web.views.Views.STUDENT_COURSE_FORM;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class CourseController {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private CourseService courseService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseDao courseDao;

    @RequestMapping(method = RequestMethod.GET, value = NEW_COURSE)
    public String newCourseForm(Model model,
            HttpServletResponse response) {

        // /List<Module> moduleList = moduleService.getAllModules();
        // List<Location> locationList = locationService.getAllLocations();
        // model.addAttribute("MODULELIST", moduleList);
        // model.addAttribute("LOCATIONLIST", locationList);
        List<Course> allCourse = courseService
                .listAllCourse();
        model.addAttribute("allCourse", allCourse);
        model.addAttribute("addCourseBean", new AddCourseBean());

        return NEW_COURSE;
    }

    @RequestMapping(method = RequestMethod.POST, value = NEW_COURSE)
    public @ResponseBody
    Map<String, ? extends Object> addCourse(@RequestBody AddCourseBean addCourseBean) {
        Set<ConstraintViolation<AddCourseBean>> failures = validator
                .validate(addCourseBean);
        if (!failures.isEmpty()) {
            addValidationMessages(failures);
            return Collections.singletonMap("m", "Fill in required fields");
        } else if (!isWord(addCourseBean.getName())) {
            return Collections
                    .singletonMap(
                    "m",
                    "Special characters(E.g *, -, +, ?, !, %) are not allowed in CourseTemplate name");

        } else {
            Course course = new Course();

            course.setDescription(addCourseBean
                    .getDescription());
            if (addCourseBean.getEvaluated() != null) {
                course.setEvaluated(true);
            } else {
                course.setEvaluated(false);
            }

            LOGGER.info("\n\n >>> preRequisites for:"
                    + addCourseBean.getName() + " : "
                    + addCourseBean.getPreRequisites());
            String strCoursePreReq = addCourseBean.getPreRequisites();

            List<CoursePreRequisite> coursePreRequisites = createCoursePreRequisites(strCoursePreReq);
            course.setCoursePreReQuisites(coursePreRequisites);
            course.setName(addCourseBean.getName());

            try {
                Course savedTemplate = courseService
                        .addCourse(course);

                return Collections.singletonMap("t",
                        String.valueOf(savedTemplate.getId()));
            } catch (CourseExistsException e) {
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
    private List<CoursePreRequisite> createCoursePreRequisites(String strCoursePreReq) {
        // Stores the prerequisite Courses
        List<CoursePreRequisite> coursePreRequisites = new ArrayList<CoursePreRequisite>();
        if (strCoursePreReq != null && !strCoursePreReq.isEmpty()) {
            if ((!strCoursePreReq.equalsIgnoreCase("None"))) {
                LOGGER.info("\n\n >>> preRequisites : " + strCoursePreReq);

                if (!strCoursePreReq.startsWith("[")) {
                    CoursePreRequisite coursePreRequisite = new CoursePreRequisite();
                    try {
                        coursePreRequisite
                                .setCoursePreRequisiteId(courseService
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
                                        .setCoursePreRequisiteId(courseService
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

    @RequestMapping(value = EDIT_COURSE_FORM, method = RequestMethod.GET)
    public String editCourse(@RequestParam("courseTemplateId") String id, Model model) {

        Course course = null;

        try {
            course = courseService.findCourseById(Long
                    .valueOf(id));
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
        }
        EditCourseBean editCourseBean = new EditCourseBean();

        editCourseBean.setId(id);

        editCourseBean.setName(course.getName());
        editCourseBean.setDescription(course.getDescription());
        System.out.println("\n\n\n >>>>> EDIT ct:"
                + course.getDescription());
        editCourseBean.setEvaluated(course.isEvaluated() + "");
        model.addAttribute("TEMPLATEID", String.valueOf(id));
        model.addAttribute("editCourseTemplateBean", editCourseBean);

        List<Course> allCourse = courseService
                .listAllCourse();

        // all course preRequisite. this contains only the embbeded table that
        // only contains IDs of the courses
        List<CoursePreRequisite> coursePreRequisites = course
                .getCoursePreReQuisites();

        // actual course prerequisites contains the CourseTemplate entities
        List<Course> preRequisites = new ArrayList<Course>();
        if (!coursePreRequisites.isEmpty()) {

            for (CoursePreRequisite coursePreRequisite : coursePreRequisites) {
                try {
                    preRequisites.add(courseService
                            .findCourseById(coursePreRequisite
                            .getCoursePreRequisiteId()));
                } catch (CourseNotFoundException e) {
                    e.printStackTrace();

                }
            }

        }
        // We dont want a course to contain itself as a pre requisite
        allCourse.remove(course);
        allCourse.removeAll(preRequisites);

        // ---------------------------------------------------------
        // avoid a situation where X can be prerequisite for Y and Y a
        // prequisite for X
        List<Course> cts = new ArrayList<Course>();
        for (Course ct : allCourse) {
            List<CoursePreRequisite> ctPreRequisites = ct
                    .getCoursePreReQuisites();
            List<Course> prereq = new ArrayList<Course>();
            for (CoursePreRequisite c : ctPreRequisites) {
                try {
                    prereq.add(courseService.findCourseById(c
                            .getCoursePreRequisiteId()));
                } catch (CourseNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if (prereq.contains(course)) {
                cts.add(ct);
            }
        }

        allCourse.removeAll(cts);
        // -----------------------------------------------------------

        model.addAttribute("allCourseTemplates", allCourse);
        model.addAttribute("preRequisites", preRequisites);

        return EDIT_COURSE;
    }

    @RequestMapping(method = RequestMethod.POST, value = EDIT_COURSE_FORM)
    public @ResponseBody
    Map<String, ? extends Object> editCoursePost(@RequestBody EditCourseBean editCourseBean) {
        Set<ConstraintViolation<EditCourseBean>> failures = validator
                .validate(editCourseBean);
        if (!failures.isEmpty()) {
            editCourseValidationMessages(failures);
            return Collections.singletonMap("m", "Fill in required fields");
        } else if (!isWord(editCourseBean.getName())) {
            return Collections
                    .singletonMap(
                    "m",
                    "Special characters(E.g *, -, +, ?, !, %) are not allowed in CourseTemplate name");
        } else {
            Course course = null;
            try {
                course = courseService
                        .findCourseById(Long
                        .valueOf(editCourseBean.getId()));
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (CourseNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            course.setId(Long.valueOf(editCourseBean.getId()));
            course.setDescription(editCourseBean
                    .getDescription());
            course.setName(editCourseBean.getName());
            if (editCourseBean.getEvaluated() != null) {
                course.setEvaluated(true);
            } else {
                course.setEvaluated(false);
            }
            // ====================================================
            // Stores the prerequisite Courses
            String strCoursePreReq = editCourseBean.getPreRequisites();
            List<CoursePreRequisite> coursePreRequisites = createCoursePreRequisites(strCoursePreReq);
            course.setCoursePreReQuisites(coursePreRequisites);

            courseService.editCourse(course);

            return Collections.singletonMap("m", "Saved");
        }
    }

    private Map<String, String> addValidationMessages(Set<ConstraintViolation<AddCourseBean>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<AddCourseBean> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(),
                    failure.getMessage());
        }
        return failureMessages;
    }

    private Map<String, String> editCourseValidationMessages(Set<ConstraintViolation<EditCourseBean>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<EditCourseBean> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(),
                    failure.getMessage());
        }
        return failureMessages;
    }

    @RequestMapping(method = RequestMethod.POST, value = EDIT_MODULE)
    public @ResponseBody
    Map<String, ? extends Object> editModule(@RequestBody EditCourseBean editCourseBean) {
        Set<ConstraintViolation<EditCourseBean>> failures = validator
                .validate(editCourseBean);
        if (!failures.isEmpty()) {
            editCourseValidationMessages(failures);
            return Collections.singletonMap("m", "Fill in required fields");
        } else {
            Course course = null;
            try {
                course = courseService
                        .findCourseById(Long
                        .valueOf(editCourseBean.getId()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (CourseNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            course.setId(Long.valueOf(editCourseBean.getId()));
            course.setDescription(editCourseBean
                    .getDescription());
            course.setName(editCourseBean.getName());

            try {
                courseService.addCourse(course);
            } catch (CourseExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return Collections.singletonMap("m", "Saved");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = LIST_COURSE)
    public String listCourse(Model model) {
        List<Course> courseList = courseService
                .listAllCourse();

        Collections.sort(courseList);

        model.addAttribute("COURSELIST", courseList);

        return LIST_COURSE;
    }

    @RequestMapping(value = ASSIGN_COURSE_MODULES, method = RequestMethod.GET)
    public String assignCourseModules(Model model) throws Exception {

        List<Course> courseList = courseService
                .listAllCourse();
        Collections.sort(courseList);

        model.addAttribute("COURSETEMPLATESLIST", courseList);
        return ASSIGN_COURSE_MODULES;
    }

    @RequestMapping(value = ASSIGN_STUDENT_COURSE, method = RequestMethod.GET)
    public String assignStudentCourse(Model model) throws Exception {

        List<User> studentList = userService.getAllStudents();
        Collections.sort(studentList);
        model.addAttribute("STUDENTLIST", studentList);
        return ASSIGN_STUDENT_COURSE;
    }

    @RequestMapping(value = COURSE_MODULES_FORM, method = RequestMethod.GET)
    public String listCTModules(@RequestParam(value = "courseId", required = false) String id,
            Model model) {

        List<Module> moduleList = null;
        try {
            moduleList = courseService
                    .getModulesInCourse(courseService
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
        model.addAttribute("assignCourseTemplateModulesBean",
                new AssignCourseModulesBean());

        return COURSE_MODULES_FORM;

    }

    /*
     * enabled modules will be determined by the prerequisites. See
     * documentation inside method for changes made.
     */
    @RequestMapping(method = RequestMethod.POST, value = COURSE_MODULES_FORM)
    public @ResponseBody
    Map<String, ? extends Object> assignCourseModulesPost(@RequestBody AssignCourseModulesBean assignCourseBean) {

        Course course = null;
        try {
            course = courseService.findCourseById(Long
                    .valueOf(assignCourseBean.getId()));
        } catch (NumberFormatException | CourseNotFoundException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        
        }

        // all course template modules before changes
        List<Module> allCTModules = courseService
                .getModulesInCourse(course);

        List<EmbeddableModule> moduleList = new ArrayList<EmbeddableModule>();
        try {
            if (!assignCourseBean.getModuleList().startsWith("[")) {
                EmbeddableModule embedd = new EmbeddableModule();
                embedd.setModuleId(Long.valueOf(assignCourseBean
                        .getModuleList()));
                embedd.setLevel(Long.valueOf(1));
                moduleList.add(embedd);

            } else {
                try {
                    JSONArray modulesArray = null;
                    try {
                        modulesArray = new JSONArray(
                                assignCourseBean.getModuleList());
                    } catch (JSONException e) {
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

        courseService.saveModulesToCourse(course,
                moduleList);

        /*
         * for (Student student : allStudents) {
         * 
         * 
         * for (EmbeddableCourseTemplate eCTemplate : student
         * .getCourseTemplateList()) {
         * 
         * CourseTemplate cTemplate = courseTemplateDAO
         * .findById(eCTemplate.getCourseTemplateId());
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
         * .getModulesInCourseTemplate(courseTemplate);
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

    @RequestMapping(value = STUDENT_COURSE_FORM, method = RequestMethod.GET)
    public String listCTStudents(@RequestParam(value = "userId", required = false) String id,
            Model model) {

        List<Course> all = courseService
                .listAllCourse();

        try {

            // Gets all assigned course template. Doc not written by author
            // therefore not sure
            List<EmbeddableCourse> studentE = userService
                    .getUserCourse(Long.valueOf(id));

            List<Course> student = new ArrayList<Course>();

            for (EmbeddableCourse embeddT : studentE) {

                try {
                    student.add(courseService
                            .findCourseById(embeddT
                            .getCourseId()));
                } catch (CourseNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            all.removeAll(student);
            model.addAttribute("STUDENT", student);
        } catch (NullPointerException ex) {
            // /model.addAttribute("STUDENTTEMPLATE","none");
        }

        model.addAttribute("ALLTEMPLATELIST", all);
        model.addAttribute("USERID", id);
        model.addAttribute("assignUserCourseBean",
                new AssignUserCourseBean());

        return STUDENT_COURSE_FORM;
    }

    @RequestMapping(method = RequestMethod.POST, value = STUDENT_COURSE_FORM)
    public @ResponseBody
    Map<String, ? extends Object> assignStudentCoursePost(@RequestBody AssignUserCourseBean assignUserCourseBean)
            throws Exception {
        // courseTemplate=null;
        List<EmbeddableCourse> courseList = new ArrayList<EmbeddableCourse>();

        System.out.println("Outside >>>>>>>>>>>>>>>>>>>" + courseList);

        try {
            if (!assignUserCourseBean.getList().startsWith("[")) {

                EmbeddableCourse embedd = new EmbeddableCourse();

                embedd.setCourseId(Long
                        .valueOf(assignUserCourseBean.getList()));
                embedd.setLevel(Long.valueOf(1));

                courseList.add(embedd);
                /*
                 * courseTemplateList.add(courseTemplateService
                 * .findCourseTemplateById
                 * (Long.valueOf(assignUserCourseTemplateBean
                 * .getTemplateList())));
                 */

            } else {
                JSONArray templatesArray = new JSONArray(
                        assignUserCourseBean.getList());

                for (int i = 0; i < templatesArray.length(); i++) {

                    EmbeddableCourse embedd = new EmbeddableCourse();
                    embedd.setCourseId(templatesArray.getLong(i));
                    embedd.setLevel(Long.valueOf(i + 1));
                    System.out.println("In array >>>>>>>>>>>>>>>>>>>"
                            + courseList);

                    /*
                     * courseTemplateList.add(courseTemplateService
                     * .findCourseTemplateById(Long.valueOf(templatesArray
                     * .getLong(i))));
                     */
                    courseList.add(embedd);
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
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }

        if (courseList != null && courseList.size() != 0) {

            // studentDao.getStudentById(user.getStudent().getId());
            Course temp = courseDao.findById(courseList
                    .get(0).getCourseId());

            List<EmbeddableModule> ambed = temp.getModules();
            List<EmbeddableModule> Mlevel = new ArrayList<EmbeddableModule>();

            for (EmbeddableModule em : ambed) {
                Mlevel.add(em);
            }
            if (Mlevel.size() != 0) {

                // courseTemplateList was already checked for null above. this
                // seems unnecessary
                if (courseList != null) {

                    for (int i = 0; i < courseList.size(); i++) {
                        try {
                            if (courseList.get(i).isComplete()) {
                            } else {

                                // user = userService.getUserById(Long
                                // .valueOf(assignUserCourseTemplateBean
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
                                            } catch (ModuleDoesNotExistException e) {

                                                e.printStackTrace();
                                            } catch (IndexOutOfBoundsException e) {
                                                e.printStackTrace();
                                            } catch (StudentDoesNotExistException e) {
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
                    } catch (UserDoesNotExistException e) {

                        e.printStackTrace();
                    }

                    Course template = courseDao
                            .findById(courseList.get(0)
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
                            courseList);
                }
            } else {

                return Collections
                        .singletonMap(
                        "m",
                        "You have assigned student an empty courseTemplate: please add module to the template");
            }
            userService.setUserCourse(
                    assignUserCourseBean.getUserId(),
                    courseList);
        }
        userService.setUserCourse(
                assignUserCourseBean.getUserId(), courseList);
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
            template = courseService.findCourseById(id);
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
