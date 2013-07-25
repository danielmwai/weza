package com.tunaweza.web.controller;

import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.settings.CompanySettingsDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.course.CourseNotFoundException;
import java.io.IOException;
/*import java.sql.Connection;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;*/
import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import net.sourceforge.cardme.engine.VCardEngine;
import net.sourceforge.cardme.vcard.VCard;
import net.sourceforge.cardme.vcard.features.EmailFeature;

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
import org.springframework.web.multipart.MultipartFile;

import com.tunaweza.core.business.dao.exceptions.group.GroupDoesNotExistsException;
import com.tunaweza.core.business.dao.exceptions.message.MessageExistsException;
import com.tunaweza.core.business.dao.exceptions.role.RoleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentCourseEvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.course.EmbeddableCourse;
import com.tunaweza.core.business.model.group.EmbeddableUser;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.message.Message;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.Location;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.company.CompanyService;
import com.tunaweza.core.business.model.company.CompanySettings;
import com.tunaweza.core.business.service.course.CourseService;
import com.tunaweza.core.business.service.dbswitcher.DbSwitcherHelperImpl;
import com.tunaweza.core.business.service.evaluation.student.StudentEvaluationService;
import com.tunaweza.core.business.service.exercise.ExerciseBean;
import com.tunaweza.core.business.service.exercise.ExerciseService;
import com.tunaweza.core.business.service.group.GroupService;
import com.tunaweza.core.business.service.license.CompanyLicenseService;
import com.tunaweza.core.business.service.location.LocationService;
import com.tunaweza.core.business.service.mail.MailService;
import com.tunaweza.core.business.service.mentor.MentorService;
import com.tunaweza.core.business.service.messageboard.MessageBoardService;
import com.tunaweza.core.business.service.module.ModuleService;
import com.tunaweza.core.business.service.role.RoleService;
import com.tunaweza.core.business.service.student.MonitorCourseBean;
import com.tunaweza.core.business.service.student.StudentEvaluationTransactionBean;
import com.tunaweza.core.business.service.student.StudentService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.core.business.service.user.UserService;
import com.tunaweza.core.business.utils.CredentialsEncoder;
import com.tunaweza.core.business.utils.DateUtils;
import static com.tunaweza.core.business.utils.SessionHelper.evalSessionAttribRole;
import com.tunaweza.core.business.utils.StringUtils;
import com.tunaweza.web.spring.configuration.cloud.instance.CloudUserBean;
import com.tunaweza.web.spring.configuration.messaging.bean.SendMessageBean;
import com.tunaweza.core.business.service.module.MonitorModuleBean;
import com.tunaweza.web.spring.configuration.user.bean.AddUserBean;
import com.tunaweza.web.spring.configuration.user.bean.ChangePasswordBean;
import com.tunaweza.web.spring.configuration.user.bean.EditEmailBean;
import com.tunaweza.web.spring.configuration.user.bean.EditUserBean;
import com.tunaweza.web.spring.configuration.user.bean.EmailValidatorBean;
import com.tunaweza.web.spring.configuration.user.bean.ListUserBean;
import com.tunaweza.web.spring.configuration.user.bean.SearchUserBean;
import com.tunaweza.web.spring.configuration.user.bean.UserBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.ADD_USER_TO_GROUP;
import static com.tunaweza.web.views.Views.CANCEL_SEARCH;
import static com.tunaweza.web.views.Views.CHANGE_CLOUD_USER_STATUS;
import static com.tunaweza.web.views.Views.CHANGE_STATUS_USER;
import static com.tunaweza.web.views.Views.DISABLE_USER;
import static com.tunaweza.web.views.Views.EDIT_EMAIL;
import static com.tunaweza.web.views.Views.EDIT_EMAIL_FORM;
import static com.tunaweza.web.views.Views.EDIT_USER;
import static com.tunaweza.web.views.Views.EDIT_USER_FORM;
import static com.tunaweza.web.views.Views.EDIT_USER_PASSWORD;
import static com.tunaweza.web.views.Views.EDIT_USER_PASSWORD_FORM;
import static com.tunaweza.web.views.Views.ENABLE_USER;
import static com.tunaweza.web.views.Views.JJCLOUD_LIST_USER;
import static com.tunaweza.web.views.Views.JJCLOUD_USER;
import static com.tunaweza.web.views.Views.LIST_SEARCH;
import static com.tunaweza.web.views.Views.LIST_USERS;
import static com.tunaweza.web.views.Views.NEW_USER;
import static com.tunaweza.web.views.Views.PAGE_SIZE;
import static com.tunaweza.web.views.Views.SEARCH_USER;
import static com.tunaweza.web.views.Views.SEND_MESSAGE;
import static com.tunaweza.web.views.Views.SET_CURRENT_MODULE_ON_MODULE_COMPLETION;
import static com.tunaweza.web.views.Views.SET_CURRENT_MODULE_ON_STUDENT_CLICK;
import static com.tunaweza.web.views.Views.STUDENT_COURSE_TEST_STATISTICS;
import static com.tunaweza.web.views.Views.STUDENT_INFO;
import static com.tunaweza.web.views.Views.STUDENT_MODULE_TEST_STATISTICS;
import static com.tunaweza.web.views.Views.STUDENT_TEST_STATISTICS;
import static com.tunaweza.web.views.Views.TOPIC_COMPLETE;
import static com.tunaweza.web.views.Views.UPLOAD_USER_VCARD;
import static com.tunaweza.web.views.Views.USER_ENABLE_DISABLE_MODULE;
import static com.tunaweza.web.views.Views.USER_INFO;
import static com.tunaweza.web.views.Views.USER_LIST_EXERCISES_BY_MODULE;
import static com.tunaweza.web.views.Views.USER_PROFILE;
import static com.tunaweza.web.views.Views.USER_TO_GROUP;
import org.json.JSONException;
import org.json.JSONObject;

@Controller
@RequestMapping(Views.USER)
public class UserController implements Views {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private StudentEvaluationService studentEvaluationService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private UserCastService userCastService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CourseService courseTemplateService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private Validator validator;

    @Autowired
    private MentorService mentorService;

    @Autowired
    private StudentService studentService;

    @Resource
    private MailService mailService;

    @Autowired
    private CompanyLicenseService companyLicenseService;

    @Autowired
    private DbSwitcherHelperImpl dbSwitcherHelper;

    @Autowired
    private MessageBoardService messageBoardService;

    @Autowired
    private GroupService groupService;

    @RequestMapping(method = RequestMethod.GET, value = NEW_USER)
    public String newUserForm(Model model)
            throws CompanySettingsDoesNotExistException {

        // List<Role> roleList = roleService.getAllRoles();

        /*
         * James to test it------------------------------------------
         * ---------------------------------------------------------
         */

        /* User user = userService.getUserById(1l); */
        Company userCompany = null;
        try {
            /* userCompany = companyService.findCompanyById(1l); */
            userCompany = companyService.findCompanyById(userCastService
                    .getUser().getId());
        } catch (CompanyDoesNotExistException e2) {
            e2.printStackTrace();
        }
        String dbname = userCompany.getDbaseName();
        List<Role> roleList = null;
        if (dbname.equals("jjteach_")) {

            roleList = roleService.getAllRoles();
        } else {
            try {
                roleList = roleService.getRolesNotOnCloudLevel();
            } catch (RoleDoesNotExistException e1) {
                e1.printStackTrace();
            }
        }

        // CompanySettings companySettings = companySettingsService
        // .getAllCompaniesSettings().get(0);
        //
        // List<Role> newRolelist = new ArrayList();
        // if (companySettings.getMentoring()) {
        // newRolelist = roleList;
        // } else {
        // for (Role role : roleList) {
        // if (!role.getRoleName().equalsIgnoreCase("ROLE_MENTOR")) {
        // newRolelist.add(role);
        // }
        // }
        // }
        CompanySettings companySettings = null;
        try {
            /* Company company = companyService.findCompanyById(1l); */
            Company company = companyService.findCompanyById(userCastService
                    .getUser().getId());
            /*
             * .findCompanyByEmail(userCastService .getUser().getEmail());
             */
            companySettings = company.getCompanySettings();
        } catch (CompanyDoesNotExistException e) {
            e.printStackTrace();
        }

        List<Role> newRolelist = new ArrayList<Role>();
        if (companySettings != null) {

            if (companySettings.getMentoring()) {
                newRolelist = roleList;
            } else {
                for (Role rol : roleList) {
                    if (!rol.getRoleName().equalsIgnoreCase("ROLE_MENTOR")) {
                        newRolelist.add(rol);
                    }
                }
            }
            model.addAttribute("ROLELIST", newRolelist);
        } else {
            model.addAttribute("ROLELIST", roleList);
        }

        List<Location> locationList = locationService.getAllLocations();
        // model.addAttribute("ROLELIST", newRolelist);
        model.addAttribute("LOCATIONLIST", locationList);
        model.addAttribute("addUserBean", new AddUserBean());

        return NEW_USER;
    }

    @RequestMapping(method = RequestMethod.GET, value = EDIT_USER_FORM)
    public String editUserForm(
            @RequestParam(value = "userId", required = false) String id,
            Model model, HttpServletRequest request) throws Exception {

        User user = userService.getUserById(Long.valueOf(id));
        User loggedIn = userCastService.getUser();

        EditUserBean editUserBean = new EditUserBean();

        List<Role> roleList = roleService.getAllRoles();

        CompanySettings companySettings = null;
        try {
            /* Company company = companyService.findCompanyById(1l); */
            /*
             * .findCompanyByEmail(userCastService .getUser().getEmail());
             */
            Company company = companyService.findCompanyById(userCastService
                    .getUser().getId());
            companySettings = company.getCompanySettings();
        } catch (CompanyDoesNotExistException e) {
            e.printStackTrace();
        }

        List<Role> newRolelist = new ArrayList<Role>();
        if (companySettings != null) {

            if (companySettings.getMentoring()) {
                newRolelist = roleList;
            } else {
                for (Role rol : roleList) {
                    if (!rol.getRoleName().equalsIgnoreCase("ROLE_MENTOR")) {
                        newRolelist.add(rol);
                    }
                }
            }
            model.addAttribute("ROLELIST", newRolelist);
        } else {
            model.addAttribute("ROLELIST", roleList);
        }

        List<Location> locationList = locationService.getAllLocations();
        // model.addAttribute("ROLELIST", newRolelist);
        model.addAttribute("LOCATIONLIST", locationList);
        model.addAttribute("USERID", id);

        editUserBean.setId(String.valueOf(user.getId()));
        editUserBean.setUsername(user.getUsername());
        editUserBean.setEmail(user.getEmail());
        editUserBean.setPassword(user.getPassword());
        editUserBean.setFirstName(user.getFirstName());
        editUserBean.setLastName(user.getLastName());
        editUserBean.setOldEmail(user.getEmail());

        Role rle = null;
        if (user.getRoles().size() > 0) {
            rle = user.getRoles().get(0);
            editUserBean.setRole(rle.getRoleName());
            editUserBean.setOldRole(rle.getRoleName());
        } else {
            model.addAttribute("NOROLE", "no role");
        }
        /*if (user.getLocation() != null) {
         editUserBean.setLocation(user.getLocation().getLocationName());
         } else {
         model.addAttribute("NOLOCATION", "no location");
         }*/
        model.addAttribute("editUserBean", editUserBean);
        model.addAttribute("loggedIn", loggedIn);

        for (Role role : user.getRoles()) {
            if (role.getRoleName().equals("ROLE_STUDENT")
                    || (role.getRoleName().equals("ROLE_EVALUATOR"))) {
                evalSessionAttribRole(request, "STUDENT");
                // request.getSession(true).setAttribute(EVAL_CURRENTLY_TESTING,
                // "ADMIN");
                model.addAttribute("STUDENT", "exists");
            }
        }

        return EDIT_USER;
    }

    @RequestMapping(method = RequestMethod.GET, value = EDIT_USER_PASSWORD_FORM)
    public String getUserPassword(
            @RequestParam(value = "userId", required = false) String id,
            Model model) {
        ChangePasswordBean changePasswordBean = new ChangePasswordBean();
        changePasswordBean.setId(String.valueOf(id));
        model.addAttribute("changePasswordBean", changePasswordBean);
        return EDIT_USER_PASSWORD_FORM;
    }

    // NB:look here for current course
    @RequestMapping(method = RequestMethod.GET, value = USER_PROFILE)
    public String getUserProfile(Model model, HttpServletRequest request) {
        User objUser = userCastService.getUser();

        if (objUser instanceof User) {
            User user = (User) objUser;
            UserBean profile = new UserBean();
            profile.setId(user.getId());
            profile.setEmail(user.getEmail());
            profile.setFirstName(user.getFirstName());
            profile.setLastName(user.getLastName());

            // Get company profile details
            if (userService.getUserAuthority(user.getUsername())
                    .equalsIgnoreCase("ROLE_EVALUATOR")) {
                try {

                    /*
                     * Company
                     * company=companyService.findCompanyByEmail(user.getEmail
                     * ());
                     */
                    Company company = companyService.findCompanyById(user
                            .getId());

                    /*
                     * Company company = companyService.findCompanyByEmail(user
                     * .getEmail());
                     */
                    profile.setEmail(company.getEmail());
                    profile.setLocation(company.getLocation());
                    profile.setWebsite(company.getWebsite());
                    profile.setDescription(company.getDescription());

                } catch (CompanyDoesNotExistException e) {
                }

            }

            if (userService.getUserAuthority(user.getUsername())
                    .equalsIgnoreCase("ROLE_STUDENT")
                    || userService.getUserAuthority(user.getUsername())
                    .equalsIgnoreCase("ROLE_EVALUATOR")) {
                evalSessionAttribRole(request, "STUDENT");
                try {
                    Student student = studentService.getStudentByUser(user);
                    profile.setRegNo(student.getRegNo());
                    profile.setStartDate(student.getStartDate());
                    List<EmbeddableCourse> courseTemplateList = student
                            .getCourseList();
                    Collections.sort(courseTemplateList);
                    logger.info("----------------------------------------------"
                            + "\n\n\n\n\n\n\n\n\n\n\n\nCOURSE TEMPLATE LIST SIZE:"
                            + "----------------------------------------------------"
                            + "\n\n\n\n\n\n\n\n\n\n\n\n");
                    if (courseTemplateList != null
                            && courseTemplateList.size() > 0) {
                        boolean doingCourse = false;
                        Course courseTemplate;
                        try {
                            for (EmbeddableCourse ect : courseTemplateList) {
                                if (!ect.isComplete()) {
                                    courseTemplate = courseTemplateService
                                            .findCourseById(ect
                                            .getCourseId());
                                    // this is where the current course is set
                                    profile.setCurrentCourse(courseTemplate
                                            .getName());
                                    doingCourse = true;
                                    break;
                                }
                            }
                            if (!doingCourse) {
                                profile.setCurrentCourse("The student has completed all Course Templates");
                            }
                        } catch (CourseNotFoundException e) {
                            e.printStackTrace();
                        }

                    } else {
                        profile.setCurrentCourse("NOT ASSIGNED");
                    }
                } catch (StudentDoesNotExistException e) {
                }
            }

            profile.setCreationDate(DateUtils.aStringToDate(user
                    .getCreationDate().getTime()));
            profile.setRole(userService.getUserAuthority(user.getUsername())
                    .replace("ROLE_", ""));
            profile.setStudent("STUDENT");

            model.addAttribute("userBean", profile);
        }
        return USER_PROFILE;
    }

    @RequestMapping(method = RequestMethod.GET, value = EDIT_EMAIL_FORM)
    public String getUserProfileEdit(
            @RequestParam(value = "userId", required = false) String id,
            Model model) throws Exception {
        User user = userService.getUserById(Long.valueOf(id));

        EditEmailBean editEmailBean = new EditEmailBean();
        model.addAttribute("USERID", id);
        editEmailBean.setId(id);
        editEmailBean.setEmail(user.getEmail());
        Role role = null;
        if (user.getRoles().size() > 0) {
            role = user.getRoles().get(0);
            editEmailBean.setRole(role.getRoleName());
        }

        model.addAttribute("editEmailBean", editEmailBean);

        return EDIT_EMAIL;
    }

    @RequestMapping(method = RequestMethod.POST, value = EDIT_EMAIL)
    public @ResponseBody
    Map<String, ? extends Object> editEmail(
            @RequestBody EditEmailBean editEmailBean) throws Exception {
        Set<ConstraintViolation<EditEmailBean>> failures = validator
                .validate(editEmailBean);

        EmailValidatorBean emailValidatorBean = new EmailValidatorBean();
        if (!failures.isEmpty()) {
            editEmailMessages(failures);
            return Collections.singletonMap("u", "Fill in required fields");
        } else if (!editEmailBean.getNewEmail().equals(
                editEmailBean.getConfirmEmail())) {
            return Collections.singletonMap("u", "Emails do not match");
        } else if (!emailValidatorBean.validate(editEmailBean.getNewEmail())) {
            return Collections.singletonMap("u",
                    "The entered E-mail is incorrect!");
        } else {
            String userId = editEmailBean.getId();
            User user = userService.getUserById(Long.valueOf(userId));

            user.setEmail(editEmailBean.getNewEmail());
            user.setUsername(editEmailBean.getNewEmail());
            List<Role> roles = new ArrayList<Role>();
            try {
                Role role = roleService.getRoleByName(editEmailBean.getRole());

                roles.add(role);
            } catch (Exception e) {
            }

            user.setRoles(roles);
            try {
                userService.updateUser(user);
                // This method call deletes the previously set username in the
                // authorities table
                userService.deletePreviousUsername(editEmailBean.getEmail(),
                        editEmailBean.getRole());
            } catch (UserDoesNotExistException e) {
            }
            Connection conn = dbSwitcherHelper.dbSwitcher("jjteach_",
                    "jjteach_", "jjteach_");
            try {
                logger.info("Now executing this>>>>>>>>>>>>>>>>");
                Statement stm = conn.createStatement();

                int row = stm
                        .executeUpdate("update jjteach_.users set email = '" + editEmailBean.getNewEmail() + "',username='" + editEmailBean.getNewEmail() + "' where username='"
                        + editEmailBean.getEmail() + "'");
                int row2 = stm
                        .executeUpdate("update jjteach_.authorities set username = '" + editEmailBean.getNewEmail() + "' where username='"
                        + editEmailBean.getEmail() + "'");

                /*logger.info("this is the user>>>>>>>>>>>>"+user.getEmail());
                 logger.info("this is the user>>>>>>>>>>>>"+user.getPassword());*/
                System.out.print("These rows have been affected in user table = " + row);
                System.out.print("These rows have been affected in authorities table = " + row2);

                conn.close();
                stm.close();

                //return Collections.singletonMap("u", "Saved");
            } catch (SQLException se) {
                System.out.print("sql exception is " + se);
            }

            return Collections.singletonMap("u", "Saved");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = EDIT_USER_PASSWORD)
    public @ResponseBody
    Map<String, ? extends Object> editPassword(
            @RequestBody ChangePasswordBean changePasswordBean)
            throws Exception {
        Set<ConstraintViolation<ChangePasswordBean>> failures = validator
                .validate(changePasswordBean);
        if (!failures.isEmpty()) {
            changePasswordValidationMessages(failures);
            return Collections.singletonMap("u", "Fill in required fields");
        } else if (!changePasswordBean.getNewPassword().equals(
                changePasswordBean.getNewPasswordConfirm())) {
            return Collections.singletonMap("u", "Passwords do not match");
        } else {

            UserBean userBean = new UserBean();
            String userId = changePasswordBean.getId();
            //String userEmail=userBean.getEmail();
            User user = userService.getUserById(Long.valueOf(userId));
            user.setPassword(CredentialsEncoder.generateMD5(changePasswordBean
                    .getNewPassword()));

            userService.updateUser(user);

            Connection conn = dbSwitcherHelper.dbSwitcher("jjteach_",
                    "jjteach_", "jjteach_");
            try {
                logger.info("Now executing this>>>>>>>>>>>>>>>>");
                Statement stm = conn.createStatement();
                int row = stm
                        .executeUpdate("update jjteach_.users set password = '" + user.getPassword() + "' where email='"
                        + user.getEmail() + "'");

                logger.info("this is the user>>>>>>>>>>>>" + user.getEmail());
                logger.info("this is the user>>>>>>>>>>>>" + user.getPassword());
                System.out.print("These rows have been affected = " + row);

                conn.close();
                stm.close();

                //return Collections.singletonMap("u", "Saved");
            } catch (SQLException se) {
                System.out.print("sql exception is " + se);
            }
            StringBuilder builder = new StringBuilder();
            builder.append("Dear " + user.getFirstName() + ",<br/>");
            builder.append("<p></p>");
            builder.append("<p> Your password has been changed. Below are your new login details:</p> ");
            builder.append("<strong>Email: </strong>" + user.getEmail() + "<br/>");
            builder.append("<strong>password: </strong>" + changePasswordBean.getNewPassword());
            builder.append("</ul></p>");
            builder.append("<p></p>");
            builder.append("<p>Kind Regards,</p>");
            builder.append("<p></p>");
            builder.append("<p>JJ People Team</p>");

            String message = builder.toString();
            logger.info("================");
            try {
                logger.info("sending mail");
                mailService.sendMail(message, user.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
                return Collections.singletonMap("u", "Email was not sent");
            }
        }
        return Collections.singletonMap("u", "Saved");
    }

    private Map<String, String> changePasswordValidationMessages(
            Set<ConstraintViolation<ChangePasswordBean>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<ChangePasswordBean> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(),
                    failure.getMessage());
        }
        return failureMessages;
    }

    @RequestMapping(method = RequestMethod.POST, value = NEW_USER)
    public @ResponseBody
    Map<String, ? extends Object> addUser(@RequestBody AddUserBean addUserBean,
            HttpServletRequest request) throws Exception {
        Set<ConstraintViolation<AddUserBean>> failures = validator
                .validate(addUserBean);

        List<User> users = userService.getAllUsers();
        List<User> enuser = new ArrayList<User>();
        for (User userss : users) {
            if (userss.getIs_enabled() == 1) {
                enuser.add(userss);

            }
        }
        Company instanceUserCompany = companyService
                .findCompany(userCastService.getUser().getUserCompany());
        String email = instanceUserCompany.getEmail();

        long max_users = companyLicenseService
                .maxNumberOfActiveUsersForLicence(email);

        if (!failures.isEmpty()) {

            Collections.emptyList();
            return Collections.singletonMap("u", "Fill in required fields");
        }

        EmailValidatorBean emailValidatorBean = new EmailValidatorBean();

        if (!emailValidatorBean.validate(addUserBean.getEmail())) {

            Collections.emptyList();
            return Collections.singletonMap("u",
                    "The entered E-mail is incorrect!");
        }

        /*if (addUserBean.getLocation() == "") {

         Collections.emptyList();
         return Collections.singletonMap("u", "Select the user's location");
         }*/
        if (addUserBean.getRole() == "") {

            Collections.emptyList();
            return Collections.singletonMap("u", "Select the user's role");
        }

        try {
            User temp = userService.getUserByUsername(addUserBean.getEmail());

            Collections.emptyList();
            return Collections
                    .singletonMap("u",
                    "The user entered already exists! Enter another E-mail address.");

        } catch (Exception e) {
        }

        User user = new User();

        user.setUsername(addUserBean.getEmail());
        user.setEmail(addUserBean.getEmail());

        // String newPassword = userService.generatePassword();
        String newPassword = addUserBean.getPassword();

        String encryptPassword = CredentialsEncoder.generateMD5(newPassword);

        user.setPassword(encryptPassword);

        user.setFirstName(addUserBean.getFirstName());
        user.setLastName(addUserBean.getLastName());
        int enabled = 0;
        if (addUserBean.getEnabled() != null) {
            if (enuser.size() >= max_users) {
                Collections.emptyList();
                return Collections
                        .singletonMap(
                        "u",
                        "You have reached maximum number of active users<br>"
                        + "allowed by your license plan; Please disable this user and continue.");

            } else {
                user.setIs_enabled(1);
                enabled = 1;

            }
        } else {
            user.setIs_enabled(0);
            enabled = 0;
        }
        /*
         * Company company = companyService.findCompanyByEmail(
         * userCastService.getUser().getEmail());
         */
        Company company = companyService.findCompanyById(userCastService
                .getUser().getId());
        /* Company company = companyService.findCompanyById(1l); */
        user.setUserCompany(company);

        List<Role> roles = new ArrayList<Role>();
        try {
            Role role = roleService.getRoleByName(addUserBean.getRole());
            roles.add(role);
        } catch (Exception e) {
            logger.info("The role could not be found in the system.");
            e.printStackTrace();
        }

        user.setRoles(roles);
        logger.info("======================The user's set roles are========================: "
                + user.getRoles());
        Role roleuser = roleService.getRoleByName(addUserBean.getRole());
        String userrole = roleuser.getRoleName();
        /*Location location = locationService.getLocationByName(addUserBean
         .getLocation());
         String locationname = location.getLocationName();
         user.setLocation(location);*/

        User savedUser = userService.addUser(user);
        logger.info("#########################The Saved User's Name is: "
                + savedUser.getFirstName() + " " + savedUser.getLastName()
                + "###########################");
        if (company != null) {
            logger.info("The companies database name is: "
                    + company.getDbaseName());
        }
        for (Role role : roles) {
            if (role.getRoleName().equals("ROLE_MENTOR")) {
                evalSessionAttribRole(request, "MENTOR");
                Mentor mentor = new Mentor();
                mentor.setUser(savedUser);
                mentorService.saveMentor(mentor);
                logger.info("The mentors name  is: "
                        + mentor.getUser().getFirstName() + " "
                        + mentor.getUser().getLastName());
            } else if (role.getRoleName().equals("ROLE_STUDENT")) {
                evalSessionAttribRole(request, "STUDENT");
                Student student = new Student();
                student.setUser(savedUser);
                student.setRegNo(userService.getNextRegistrationNumber());
                studentService.saveStudent(student);
                logger.info("The student's name is: "
                        + student.getUser().getFirstName() + " "
                        + student.getUser().getLastName());
            } else if (role.getRoleName().equals("ROLE_EVALUATOR")) {
                evalSessionAttribRole(request, "ADMIN");
                Mentor mentor = new Mentor();
                mentor.setUser(savedUser);
                mentorService.saveMentor(mentor);
                Student student = new Student();
                student.setUser(savedUser);
                student.setRegNo(userService.getNextRegistrationNumber());
                studentService.saveStudent(student);
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Dear " + user.getFirstName() + ",<br/>");
        builder.append("<p></p>");
        builder.append("<p> Thank you for your interest in JJPeople and in our software developer training program."
                + "<br> I am sorry about the delay in providing the login information, however, we have completely re-written the software that drives our training portal and there was a short delay in deploying this"
                + ".<br>It is now available and you can gain access using the login information set out below."
                + "<br>Login when you are ready and get started on this. We will be in touch as soon as you start to make some progress." + "</p>"
                );
        builder.append("<p> To log into the training portal, click on the link below and log in using the user name and password provided </p> " + "<br/>");
        String url = StringUtils.applicationUrl(request);
        builder.append(url + "<br/>");
        builder.append("<strong>Email: </strong>" + user.getEmail() + "<br/>");
        builder.append("<strong>Password: </strong>" + newPassword);
        builder.append("</ul></p>");
        builder.append("<p> If you have any questions or concerns  on the training please contact me on +44 0207 199 4968 or email bernard.mararo@jjpeople.com.We are pleased to have you as part of your team.</p>");
        builder.append("<p></p>");
        builder.append("<p>Regards,<br/>Bernard<br/>Training Facilitator</p>");
        builder.append("<p></p>");

        String message = builder.toString();
        mailService.sendMail(message, addUserBean.getEmail());
        String email1 = company.getEmail();

        Connection conn = dbSwitcherHelper.dbSwitcher("jjteach_", "jjteach_",
                "jjteach_");

        int id = 0;
        int locationid = 0;

        try {

            Statement stm = conn.createStatement();
            ResultSet rs = stm
                    .executeQuery("select id from company_details c where c.email = '"
                    + email1 + "'");

            while (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();

            logger.info("Inserting user record into database");
            Statement st3 = conn.createStatement();
            st3.executeUpdate("INSERT INTO users"
                    + "(VERSION,email,enabled,first_name,last_name,password,superuser,username,id_company)"
                    + " VALUES("
                    + 0
                    + ","
                    + "'"
                    + addUserBean.getEmail()
                    + "'"
                    + ","
                    + enabled
                    + ","
                    + "'"
                    + addUserBean.getFirstName()
                    + "'"
                    + ","
                    + "'"
                    + addUserBean.getLastName()
                    + "'"
                    + ","
                    + "'"
                    + encryptPassword
                    + "'"
                    + ","
                    + ""
                    + 0
                    + ","
                    + "'"
                    + addUserBean.getEmail()
                    + "'"
                    + ","
                    + id + ")");

            st3.close();

            Statement st4 = conn.createStatement();
            st4.executeUpdate("INSERT INTO authorities(username,authority) VALUES("
                    + "'"
                    + addUserBean.getEmail()
                    + "'"
                    + ","
                    + "'"
                    + userrole
                    + "')");

            logger.info("User record inserted into database");

            st4.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return Collections.singletonMap("u", "Saved");
    }

    @RequestMapping(method = RequestMethod.GET, value = ADD_USER_TO_GROUP)
    public String CompanyUsers(
            @RequestParam(value = "startIndex", required = false) String startIndex,
            @RequestParam(value = "groupId", required = true) String id,
            Model model) throws GroupDoesNotExistsException {
        List<User> userList = new ArrayList<User>();

        int groupId = Integer.valueOf(id);
        Group group = groupService.findGroupById(groupId);

        List<User> groupUsers = groupService.getUsersInGroup(group);
        if (groupUsers.size() == 0 || groupUsers == null) {
        }

        userList = userService.getAllStudents();
        userList.removeAll(groupUsers);
        int countUsers = userList.size();
        int counter = groupUsers.size();

        if (countUsers <= PAGE_SIZE || counter <= PAGE_SIZE) {
            model.addAttribute("groupId", groupId);
            model.addAttribute("USERSLIST", userList);
            model.addAttribute("GROUPUSER", groupUsers);

        } else {
            int start = 1;
            int totalPages = countUsers / PAGE_SIZE;
            int total = counter / PAGE_SIZE;
            if (countUsers != totalPages * PAGE_SIZE
                    || counter != total * PAGE_SIZE) {
                ++totalPages;
            }
            ++total;
            if (startIndex != null) {
                start = Integer.valueOf(startIndex);
            }
            userList = userService.getPaginatedUsers((start - 1) * PAGE_SIZE,
                    PAGE_SIZE);
            userList.removeAll(groupUsers);
            model.addAttribute("USERSLIST", userList);
            model.addAttribute("GROUPUSER", groupUsers);
            model.addAttribute("PAGE_NUMBER", start);
            model.addAttribute("TOTAL_PAGES", totalPages);
            model.addAttribute("groupId", groupId);
        }

        if (userList.size() == 0 && groupUsers.size() == 0) {
            model.addAttribute("NOLIST", "No User");
        }

        return ADD_USER_TO_GROUP;
    }

    @RequestMapping(method = RequestMethod.POST, value = USER_TO_GROUP)
    public @ResponseBody
    Map<String, ? extends Object> addUserGroup(
            @RequestParam(value = "groupId", required = true) String groupid,
            @RequestParam(value = "userId", required = true) String userid)
            throws GroupDoesNotExistsException, UserDoesNotExistException {
        List<EmbeddableUser> userList = new ArrayList<EmbeddableUser>();
        Map<String, String> responseMap = new HashMap<String, String>();
        String userId = userid;
        String[] tokens = userId.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        for (String string : tokens) {
            EmbeddableUser embed = new EmbeddableUser();
            if (string.length() == 0) {
            } else {
                embed.setUserId(Long.valueOf(string));
                userList.add(embed);
            }

        }

        int groupId = Integer.valueOf(groupid);
        Group group = groupService.findGroupById(groupId);
        groupService.saveUserToGroup(group, userList);
        // userService.addUserToGroup(group, userList);
        responseMap.put("isSaved", "true");
        return responseMap;

    }

    @RequestMapping(method = RequestMethod.POST, value = EDIT_USER)
    public @ResponseBody
    Map<String, ? extends Object> editUser(
            @RequestBody EditUserBean editUserBean) throws Exception {
        Set<ConstraintViolation<EditUserBean>> failures = validator
                .validate(editUserBean);
        if (!failures.isEmpty()) {
            editValidationMessages(failures);
            return Collections.singletonMap("u", "Fill in required fields");
        }

        try {
            User temp = userService.getUserByUsername(editUserBean.getEmail());

            if (!(temp.getEmail().equals(editUserBean.getEmail()))) {

                Collections.emptyList();
                return Collections
                        .singletonMap("u",
                        "The user entered already exists! Enter another E-mail address.");

            }

        } catch (Exception e) {
        }

        EmailValidatorBean emailValidatorBean = new EmailValidatorBean();

        if (!emailValidatorBean.validate(editUserBean.getEmail())) {
            return Collections.singletonMap("u",
                    "The entered E-mail is incorrect!");
        } else {

            User user = userService.getUserById(Long.valueOf(editUserBean
                    .getId()));

            user.setId(Long.valueOf(editUserBean.getId()));
            user.setEmail(editUserBean.getEmail());
            user.setUsername(editUserBean.getEmail());
            user.setFirstName(editUserBean.getFirstName());
            user.setLastName(editUserBean.getLastName());
            List<Role> roles = new ArrayList<Role>();
            try {
                Role role = roleService.getRoleByName(editUserBean.getRole());

                roles.add(role);
            } catch (Exception e) {
            }

            user.setRoles(roles);

            /*Location location = locationService.getLocationByName(editUserBean
             .getLocation());
             user.setLocation(location);*/
            Role roleuser = roleService.getRoleByName(editUserBean.getRole());
            String userrole = roleuser.getRoleName();
            /*String locationname = location.getLocationName();*/
            /*user.setLocation(location);*/
            String newPassword = editUserBean.getPassword();

            String encryptPassword = CredentialsEncoder
                    .generateMD5(newPassword);

            try {
                userService.updateUser(user);
                // This method call deletes the previously set username in the
                // authorities table
                userService.deletePreviousUsername(editUserBean.getOldEmail(),
                        editUserBean.getOldRole());
            } catch (Exception e) {

                Collections.emptyList();
                return Collections
                        .singletonMap("u",
                        "The user entered already exists! Enter another E-mail address.");

            }

            int id = 0;
            int locationid = 0;

            Connection conn = dbSwitcherHelper.dbSwitcher("jjteach_",
                    "jjteach_", "jjteach_");
            try {

                Statement stm = conn.createStatement();
                ResultSet rs = stm
                        .executeQuery("select id_company from users u where u.email = '"
                        + editUserBean.getOldEmail() + "'");

                while (rs.next()) {
                    id = rs.getInt("id_company");
                    System.out.print("Company id++++++++++++++ = " + id);
                }
                /* id = rs.getInt("company_id"); */
                rs.close();

                /*Statement stm1 = conn.createStatement();
                 ResultSet rs1 = stm1
                 .executeQuery("select id from location l where l.location_name = '"
                 + locationname + "'");
                 while (rs1.next()) {
                 locationid = rs1.getInt("id");
                 System.out.print("Location id++++++++++++++ = "
                 + locationid);
                 }

                 logger.info("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
                 logger.info("JJJJJJJJJJJJJJJJJJJJ" + id);
                 logger.info("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");

                 rs1.close();*/
                Statement st3 = conn.createStatement();
                int row
                        = st3.executeUpdate("UPDATE  jjteach_.users set email='" + editUserBean.getEmail() + "',first_name='" + editUserBean.getFirstName() + "',last_name='" + editUserBean.getLastName() + "',username='" + editUserBean.getEmail() + "' where email='" + editUserBean.getOldEmail() + "'");

                Statement st4 = conn.createStatement();
                int row2
                        = st4.executeUpdate("UPDATE jjteach_.authorities set username='" + editUserBean.getEmail() + "',authority='" + userrole + "' where username='" + editUserBean.getOldEmail() + "'");

                logger.info(">>>>>>>>>>This number of rows affected in jjteach users" + row);
                logger.info("JJJJJJJJJJJJJJJJJJJJ" + id);
                logger.info(">>>>>>>>>>>>This number of rows affected in jjteach authorities" + row2);
                stm.close();
                //stm1.close();
                st3.close();
                st4.close();
            } catch (SQLException e) {
                e.printStackTrace();

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            StringBuilder builder = new StringBuilder();
            builder.append("Dear " + user.getFirstName() + ",<br/>");
            builder.append("<p></p>");
            builder.append("<p> Below are the details that have been changed.</p> ");
            builder.append("<strong>Email: </strong>" + editUserBean.getEmail()
                    + "<br/>");
            builder.append("<strong>FirstName: </strong>"
                    + editUserBean.getFirstName() + "<br/>");
            builder.append("<strong>LastName: </strong>"
                    + editUserBean.getLastName() + "<br/>");
            /*builder.append("<strong>Location: </strong>"
             + location.getLocationName());*/
            builder.append("</ul></p>");
            builder.append("<p>Once you login, you can change you password by clicking on the 'My profile' icon(it's on the top navigation panel). Then Click on the 'Edit password'.</p>");
            builder.append("<p></p>");
            builder.append("<p>Kind Regards,</p>");
            builder.append("<p></p>");
            builder.append("<p>JJ People Team</p>");

            String message = builder.toString();

            if (editUserBean.getSendIn() != null) {
                mailService.sendMail(message, user.getEmail());
            }
            logger.info("+MAIL SENT");
            return Collections.singletonMap("u", "Saved");
        }
    }

    private Map<String, String> editValidationMessages(
            Set<ConstraintViolation<EditUserBean>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<EditUserBean> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(),
                    failure.getMessage());
        }
        return failureMessages;
    }

    private Map<String, String> editEmailMessages(
            Set<ConstraintViolation<EditEmailBean>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<EditEmailBean> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(),
                    failure.getMessage());
        }
        return failureMessages;
    }

    @RequestMapping(method = RequestMethod.GET, value = LIST_USERS)
    public String licontrollerstUsers(
            @RequestParam(value = "startIndex", required = false) String startIndex,
            @RequestParam(value = "role", required = false) String role,
            Model model) throws CompanyDoesNotExistException {

        System.out.println("\n\n\n >>>> getting users for Role :" + role
                + "<<<<<<<<<< \n\n");

        List<User> userList = new ArrayList<User>();
        // List<Student> studenList = studentService.getAllStudents();
        // if (studenList.size() != 0) {
        // System.out.println(studenList.get(0).getRegNo());
        // }
		/* Long company_id = userCastService.getUser().getUserCompany().getId(); */

        if (role == null || role.equalsIgnoreCase("NONE")
                || role.equalsIgnoreCase("undefined")) {
            // List <User> users = userService.getUsersByCompanyId(company_id);
            System.out.println("\n\n\n >>>> Selected Role(Not selected) :"
                    + role + "<<<<< \n\n");

            userList = userService.getAllUsers();
            /* .getUsersByCompanyId(company_id); */
            int countUsers = userList.size();

            if (countUsers <= PAGE_SIZE) {

                model.addAttribute("USERSLIST", userList);
            } else {
                int start = 1;
                int totalPages = countUsers / PAGE_SIZE;
                if (countUsers != totalPages * PAGE_SIZE) {
                    ++totalPages;
                }
                if (startIndex != null) {
                    start = Integer.valueOf(startIndex);
                }
                userList = userService.getPaginatedUsers((start - 1)
                        * PAGE_SIZE, PAGE_SIZE);
                System.out.println("\n\n\n >>>> Selected Role in un :" + role
                        + "Userlist size :" + userList.size());
                model.addAttribute("USERSLIST", userList);
                model.addAttribute("PAGE_NUMBER", start);
                model.addAttribute("TOTAL_PAGES", totalPages);
            }
        } else {

            model.addAttribute("ROLE", role);
            userList = userService.getAllUsers(role);
            int countUsers = userList.size();
            /*
             * userList = userService.getUsersByRole_CompanyId(role,
             * company_id);
             */
            // int countUsers = userList.size();

            if (countUsers <= PAGE_SIZE) {
                // userList = userService.getAllUsers(role);
                // userList = userService.getUsersByRole_CompanyId(role,
                // company_id);
                model.addAttribute("USERSLIST", userList);
            } else {
                int start = 1;
                int totalPages = countUsers / PAGE_SIZE;
                if (countUsers != totalPages * PAGE_SIZE) {
                    ++totalPages;
                }
                if (startIndex != null) {
                    start = Integer.valueOf(startIndex);
                }
                userList = userService.getPaginatedUsers((start - 1)
                        * PAGE_SIZE, PAGE_SIZE, role);
                System.out.println("\n\n\n >>>> Selected Role in stu :" + role
                        + "Userlist size :" + userList.size());
                /*
                 * userList =
                 * userService.getPaginatedUsersByRole_CompanyId((start - 1)
                 * PAGE_SIZE, PAGE_SIZE, role, company_id);
                 */
                model.addAttribute("USERSLIST", userList);
                model.addAttribute("PAGE_NUMBER", start);
                model.addAttribute("TOTAL_PAGES", totalPages);
            }
        }

        List<Role> roleList = roleService.getAllRoles();
        // model.addAttribute("ROLELIST", roleList);

        List<Role> companyRolelist = new ArrayList<Role>();

        CompanySettings companySettings = null;
        try {
            /*
             * Company company =
             * companyService.findCompanyByEmail(userCastService
             * .getUser().getEmail());
             */
            Company company = companyService.findCompanyById(userCastService
                    .getUser().getId());
            /* Company company = companyService.findCompanyById(1l); */
            companySettings = company.getCompanySettings();
        } catch (CompanyDoesNotExistException e) {
            e.printStackTrace();
        }
        // //////////////
        if (companySettings != null) {

            if (companySettings.getMentoring()) {
                companyRolelist = roleList;
            } else {
                for (Role rol : roleList) {
                    if (!rol.getRoleName().equalsIgnoreCase("ROLE_MENTOR")) {
                        companyRolelist.add(rol);
                    }
                }
            }
            model.addAttribute("ROLELIST", companyRolelist);
        } else {
            model.addAttribute("ROLELIST", roleList);
        }
        // model.addAttribute("ROLELIST", newRolelist);

        model.addAttribute("listUserBean", new ListUserBean());

        model.addAttribute("HIDEROLES", "DONTHIDEROLES");

        if (userList.size() == 0) {
            model.addAttribute("NOLIST", "No User");
        }
        return LIST_USERS;
    }

    @RequestMapping(value = ENABLE_USER, method = RequestMethod.GET)
    public String enableUser(
            @RequestParam(value = "userId", required = false) String id,
            Model model) throws Exception {

        userService.enableUser(id);
        List<Role> roleList = roleService.getAllRoles();
        model.addAttribute("ROLELIST", roleList);
        model.addAttribute("listUserBean", new ListUserBean());
        model.addAttribute("HIDEROLES", "DONTHIDEROLES");
        return LIST_USERS;
    }

    @RequestMapping(value = DISABLE_USER, method = RequestMethod.GET)
    public String disableUser(
            @RequestParam(value = "userId", required = false) String id,
            Model model) throws Exception {

        userService.disableUser(id);
        List<Role> roleList = roleService.getAllRoles();
        model.addAttribute("ROLELIST", roleList);
        model.addAttribute("listUserBean", new ListUserBean());
        model.addAttribute("HIDEROLES", "DONTHIDEROLES");
        return LIST_USERS;
    }

    @RequestMapping(value = SEARCH_USER, method = RequestMethod.GET)
    public String searchUser(Model model) {
        model.addAttribute("searchUserBean", new SearchUserBean());
        List<Role> roleList = roleService.getAllRoles();

        CompanySettings companySettings = null;
        try {
            /*
             * Company company =
             * companyService.findCompanyByEmail(userCastService
             * .getUser().getEmail());
             */
            /* Company company = companyService.findCompanyById(1l); */
            Company company = companyService.findCompanyById(userCastService
                    .getUser().getId());
            companySettings = company.getCompanySettings();
        } catch (CompanyDoesNotExistException e) {
            e.printStackTrace();
        }

        /* List<Role> newRolelist = new ArrayList<Role>(); */

        /*
         * James to test it------------------------------------------
         * ---------------------------------------------------------
         */

        /*
         * User user = userCastService.getUser(); Company userCompany =
         * user.getUserCompany();
         */
        Company userCompany = null;
        try {
            /* userCompany = companyService.findCompanyById(1l); */
            userCompany = companyService.findCompanyById(userCastService
                    .getUser().getId());
        } catch (CompanyDoesNotExistException e) {
            e.printStackTrace();
        }
        String dbname = userCompany.getDbaseName();
        List<Role> newRolelist = null;
        if (dbname.equals("jjteach_")) {

            newRolelist = roleService.getAllRoles();
        } else {
            try {
                newRolelist = roleService.getRolesNotOnCloudLevel();
            } catch (RoleDoesNotExistException e1) {
                e1.printStackTrace();
            }
        }
        if (companySettings != null) {

            if (companySettings.getMentoring()) {
                newRolelist = roleList;
            } else {
                for (Role rol : roleList) {
                    if (!rol.getRoleName().equalsIgnoreCase("ROLE_MENTOR")) {
                        newRolelist.add(rol);
                    }
                }
            }
            model.addAttribute("ROLELIST", newRolelist);
        } else {
            model.addAttribute("ROLELIST", roleList);
        }

        return SEARCH_USER;
    }

    @RequestMapping(value = SEARCH_USER, method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> searchUser(
            @RequestBody SearchUserBean searchUserBean) throws Exception {
        if (searchUserBean.getFirstname() == ""
                && searchUserBean.getLastname() == "") {
            return Collections.singletonMap("u", "Empty");
        }
        String firstName = searchUserBean.getFirstname();
        String lastName = searchUserBean.getLastname();
        String role = searchUserBean.getRole();
        return Collections.singletonMap("u", "firstName=" + firstName
                + "&lastName=" + lastName + "&role=" + role);
    }

    @RequestMapping(value = CANCEL_SEARCH)
    public @ResponseBody
    String Cancel(Model model) throws Exception {

        return null;
    }

    @RequestMapping(value = LIST_SEARCH, method = RequestMethod.GET)
    public String listSearch(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "role", required = false) String role,
            Model model) {

        CompanySettings companySettings = null;
        Company company = null;
        try {
            /*
             * company = companyService.findCompanyByEmail(userCastService
             * .getUser().getEmail());
             */
            company = companyService.findCompanyById(userCastService.getUser().getUserCompany()
                    .getId());
            /* company = companyService.findCompanyById(1l); */
            companySettings = company.getCompanySettings();
        } catch (CompanyDoesNotExistException e) {
            e.printStackTrace();
        }

        List<User> userList = new ArrayList<User>();
        if (role.equals("NONE")) {
            if (!firstName.equals("") && lastName.equals("")) {
                try {
                    userList = userService.getUsersByFirstName(firstName,
                            company.getId());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } else if (!lastName.equals("") && firstName.equals("")) {
                try {
                    userList = userService.getUsersByLastName(lastName,
                            company.getId());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    userList = userService.searchUsers(firstName, lastName,
                            company.getId());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            model.addAttribute("HIDEROLES", "HIDEROLES");

        } else {
            if (!firstName.equals("") && lastName.equals("")) {
                try {
                    userList = userService.getUsersByFirstNameAndRole(
                            firstName, role, company.getId());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } else if (!lastName.equals("") && firstName.equals("")) {
                try {
                    userList = userService.getUsersByLastNameAndRole(lastName,
                            role, company.getId());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } else if (!lastName.equals("") && !firstName.equals("")) {
                try {
                    userList = userService.searchUsers(firstName, lastName,
                            role, company.getId());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    userList = userService.getUsersByRoleCompanyId(role,
                            company.getId());
                } catch (UserDoesNotExistException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            model.addAttribute("HIDEROLES", "HIDEROLES");
        }

        if (userList.size() > 0) {

            Collections.sort(userList);

            model.addAttribute("USERSLIST", userList);
        }

        List<Role> roleList = roleService.getAllRoles();

        List<Role> newRolelist = new ArrayList<Role>();
        if (companySettings != null) {

            if (companySettings.getMentoring()) {
                newRolelist = roleList;
            } else {
                for (Role rol : roleList) {
                    if (!rol.getRoleName().equalsIgnoreCase("ROLE_MENTOR")) {
                        newRolelist.add(rol);
                    }
                }
            }
            model.addAttribute("ROLELIST", newRolelist);
        } else {
            model.addAttribute("ROLELIST", roleList);
        }

        model.addAttribute("listUserBean", new ListUserBean());

        if (userList.size() == 0) {
            model.addAttribute("NOLIST", "No such User");
        }
        return LIST_USERS;
    }

    @RequestMapping(value = CHANGE_STATUS_USER, method = RequestMethod.GET)
    public @ResponseBody
    Map<String, ? extends Object> enableDisableUser(
            @RequestParam(value = "userId", required = false) String userId,
            Model model) throws Exception {

        List<User> users = userService.getAllUsers();
        List<User> enuser = new ArrayList<User>();
        for (User userss : users) {
            if (userss.getIs_enabled() == 1) {
                enuser.add(userss);

            }
        }
        Map<String, String> responseMap = new HashMap<String, String>();
        long uId = Long.valueOf(userId);
        User user = userService.getUserById(uId);

        Company instanceUserCompany = companyService
                .findCompany(userCastService.getUser().getUserCompany());
        String email = instanceUserCompany.getEmail();
        long max_users = companyLicenseService
                .maxNumberOfActiveUsersForLicence(email);
        int enabled = 0;
        if (user.getIs_enabled() == 0) {
            if (enuser.size() >= max_users) {

                responseMap.put("isMax", "true");
                return responseMap;

            } else {
                user.setIs_enabled(1);
                enabled = 1;
                responseMap.put("isMax", "false");
            }

        } else {
            user.setIs_enabled(0);
            enabled = 0;
            responseMap.put("isMax", "false");

        }

        userService.saveUser(user);
        Connection conn = dbSwitcherHelper.dbSwitcher("jjteach_", "jjteach_",
                "jjteach_");
        try {

            Statement upd = conn.createStatement();

            upd.executeUpdate("UPDATE users SET enabled = " + enabled
                    + " WHERE email =  '" + user.getEmail() + " '");
            upd.close();
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return responseMap;
    }

    @RequestMapping(value = TOPIC_COMPLETE, method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> completeTopic(
            @RequestParam(value = "topicId", required = true) String id)
            throws Exception {
        User user = userCastService.getUser();
        Student student = studentService.getStudentByUser(user);
        Long topicId;
        try {
            topicId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return Collections.singletonMap("m", "Error Occcured. Try Again");
        }
        Topic topic = topicService.getTopicById(topicId);
        List<Topic> topics = student.getCompletedTopics();
        topics.add(topic);
        student.setCompletedTopics(topics);
        studentService.saveStudent(student);
        return Collections.singletonMap("m", "Topic Completed.");

    }

    @RequestMapping(method = RequestMethod.GET, value = USER_INFO)
    public String userInfo(
            @RequestParam(value = "userId", required = true) String id,
            Model model, HttpServletRequest request) throws Exception {

        User user = userService.getUserById(Long.valueOf(id));
        for (Role role : user.getRoles()) {
            if (role.getRoleName().equals("ROLE_STUDENT")) {
                evalSessionAttribRole(request, "STUDENT");
                model.addAttribute("STUDENT", "exists");
            } else if (role.getRoleName().equals("ROLE_MENTOR")) {
                evalSessionAttribRole(request, "MENTOR");
                model.addAttribute("MENTOR", "exists");
            } else if (role.getRoleName().equals("ROLE_EVALUATOR")) {
                model.addAttribute("EVALUATOR", "exists");
            }
        }

        EditUserBean editUserBean = new EditUserBean();

        model.addAttribute("USERID", id);

        editUserBean.setId(String.valueOf(user.getId()).replaceAll(
                "<p>([^<]*)</p>", "$1"));
        editUserBean.setUsername(user.getUsername().replaceAll(
                "<p>([^<]*)</p>", "$1"));
        editUserBean.setEmail(user.getEmail()
                .replaceAll("<p>([^<]*)</p>", "$1"));
        editUserBean.setPassword(user.getPassword().replaceAll(
                "<p>([^<]*)</p>", "$1"));
        editUserBean.setFirstName(user.getFirstName().replaceAll(
                "<p>([^<]*)</p>", "$1"));
        editUserBean.setLastName(user.getLastName().replaceAll(
                "<p>([^<]*)</p>", "$1"));
        if (user.getRoles().size() > 0) {
            editUserBean.setRole(user.getRoles().get(0).getRoleName());
        } else {
            editUserBean.setRole("No Role");
        }
        /*if (user.getLocation() != null) {
         editUserBean.setLocation(user.getLocation().getLocationName());
         } else {
         editUserBean.setLocation("No Location");
         }*/
        model.addAttribute("editUserBean", editUserBean);
        return USER_INFO;
    }

    @RequestMapping(method = RequestMethod.GET, value = STUDENT_INFO)
    public String studentProgress(
            @RequestParam(value = "userId", required = true) String id,
            Model model, HttpServletRequest request) throws Exception {
        User user = userService.getUserById(Long.valueOf(id));
        /*
         * Check if the user is a student. If so, get statistics regarding the
         * modules assigned to him.
         */
        try {
            Student student = studentService.getStudentByUser(user);

            List<Role> roles = user.getRoles();
            for (Role role : roles) {
                /*
                 * User is a student if condition passes... get the modules and
                 * their statistics.
                 */

                if (role.getRoleName().equals("ROLE_STUDENT")
                        || role.getRoleName().equals("ROLE_EVALUATOR")) {
                    evalSessionAttribRole(request, "STUDENT");
                    List<EmbeddableCourse> embeddableTemplates = student
                            .getCourseList();
                    if (embeddableTemplates != null) {
                        Collections.sort(embeddableTemplates);
                        List<Course> courseTemplateList = new ArrayList<Course>();

                        for (EmbeddableCourse emb : embeddableTemplates) {
                            courseTemplateList.add(courseTemplateService
                                    .findCourseById(emb
                                    .getCourseId()));

                        }

                        if (courseTemplateList != null) {

                            Map<MonitorCourseBean, List<MonitorModuleBean>> moduleMap = studentService
                                    .getStudentModuleStatistics(student,
                                    courseTemplateList);
                            if (moduleMap != null) {
                                model.addAttribute("MODULEMAP", moduleMap);
                            } else {
                                model.addAttribute("NOMODULES",
                                        "The student has no modules assigned as yet!");
                            }
                            model.addAttribute("USERID", id);
                        }
                    } else {
                        model.addAttribute("NOMODULES",
                                "The Student has no Course template assigned to yet.");
                    }
                }
            }

            String lastLogin = null;
            try {
                lastLogin = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                        DateFormat.SHORT).format(
                        studentService.getLastLoginDate(student));
            } catch (Exception e) {
            }
            if (lastLogin != null) {

                model.addAttribute("LASTLOGIN", lastLogin);
            } else {
                model.addAttribute("LASTLOGIN", "Student has never logged in");
            }
        } catch (StudentDoesNotExistException e) {
            e.printStackTrace();
        }
        return STUDENT_INFO;

    }

    /**
     * Get the list of exercises in a given student's module
     *
     * @param userId
     * @param moduleId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = USER_LIST_EXERCISES_BY_MODULE)
    public String listExercises(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "moduleId", required = false) String moduleId,
            Model model) throws Exception {
        List<ExerciseBean> exerciseList = new ArrayList<ExerciseBean>();
        exerciseList = exerciseService.getStudentExerciseByModule(userId,
                moduleId);
        model.addAttribute("EXERCISE_LIST", exerciseList);
        if (exerciseList == null) {
            model.addAttribute("NOLIST", "No Exercises");
        }
        return USER_LIST_EXERCISES_BY_MODULE;
    }

    @RequestMapping(method = RequestMethod.POST, value = USER_ENABLE_DISABLE_MODULE)
    public @ResponseBody
    Map<String, ? extends Object> enableStudentModule(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "moduleId", required = true) String moduleId) {
        try {
            Module module = moduleService.getModuleById(Long.valueOf(moduleId));
            User user = userService.getUserById(Long.valueOf(userId));
            studentService.enableStudentModule(module, user);
            return Collections.singletonMap("m",
                    "Student Module Status Changed");
        } catch (Exception ex) {
            return Collections.singletonMap("m", "Error Occcured. Try Again");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = SET_CURRENT_MODULE_ON_STUDENT_CLICK)
    public @ResponseBody
    Map<String, ? extends Object> setCurrentModuleOnStudentClick(
            @RequestParam(value = "moduleId", required = true) String moduleId) {
        /*
         * call the student service and set the current module for the student.
         */
        try {
            Module module = moduleService.getModuleById(Long.valueOf(moduleId));
            User user = userCastService.getUser();
            Student student = user.getStudent();
            studentService.setCurrentModule(module, student);
            return Collections.singletonMap("m",
                    "Student Module " + module.getName() + "started");
        } catch (Exception e) {
            return Collections.singletonMap("m", "Error Occcured. Try Again");
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = SET_CURRENT_MODULE_ON_MODULE_COMPLETION)
    public @ResponseBody
    Map<String, ? extends Object> setCurrentModuleOnModuleCompletion(
            @RequestParam(value = "moduleId", required = true) String moduleId)
            throws Exception {
        /*
         * Get the module id passed and obtain the next module in-line and set
         * it as the current one
         */
        try {
            User user = userCastService.getUser();
            Student student = user.getStudent();
            studentService.activateNextModule(Long.valueOf(moduleId), student);

            return Collections.singletonMap("m", "Student Module "
                    + moduleService.getModuleById(Long.valueOf(moduleId))
                    .getName() + "started");
        } catch (Exception e) {
            System.out
                    .println("\n\n\n\n\n-----------------------------------------------"
                    + "ERROR OCCURED SETTING NEXT MODULE"
                    + e.getMessage()
                    + "\n\n\n\n\n\n\n----------------------------------------------------------");

            return Collections.singletonMap("m", "Error Occcured. Try Again");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = STUDENT_MODULE_TEST_STATISTICS)
    public String studentEvalStats(
            @RequestParam(value = "studentId", required = false) String studentId,
            @RequestParam(value = "startIndex", required = false) String startIndex,
            Model model) throws StudentCourseEvaluationDoesNotExistException,
            NumberFormatException, StudentDoesNotExistException {
        int start = 1;
        if (startIndex != null) {
            start = Integer.valueOf(startIndex);
        }

        Student student;
        if (studentId == null) {
            student = userCastService.getUser().getStudent();
        } else {
            student = studentService.getStudentById(Long.valueOf(studentId));
        }
        int countStudentEvaluations = studentEvaluationService
                .countStudentCourseEvaluations(student.getId());

        List<StudentEvaluationTransactionBean> studentEvaluationList = new ArrayList<StudentEvaluationTransactionBean>();

        if (countStudentEvaluations <= PAGE_SIZE) {
            studentEvaluationList = studentEvaluationService
                    .getStudentEvaluationTransactionBeans(student.getId(),
                    start - 1, PAGE_SIZE);
            model.addAttribute("EVALUATIONS", studentEvaluationList);
        } else {

            int totalPages = countStudentEvaluations / PAGE_SIZE;
            if (countStudentEvaluations != totalPages * PAGE_SIZE) {
                ++totalPages;
            }
            if (startIndex != null) {
                start = Integer.valueOf(startIndex);
            }
            studentEvaluationList = studentEvaluationService
                    .getStudentEvaluationTransactionBeans(student.getId(),
                    start - 1 * PAGE_SIZE, PAGE_SIZE);
            model.addAttribute("EMPTY", false);
            model.addAttribute("EVALUATIONS", studentEvaluationList);
            model.addAttribute("PAGE_NUMBER", start);
            model.addAttribute("TOTAL_PAGES", totalPages);
        }

        if (studentEvaluationList == null || studentEvaluationList.size() == 0) {
            model.addAttribute("NOLIST", "No Evaluations");
        }
        return STUDENT_MODULE_TEST_STATISTICS;
    }

    @RequestMapping(method = RequestMethod.GET, value = STUDENT_COURSE_TEST_STATISTICS)
    public String studentCourseEvalStats(
            @RequestParam(value = "studentId", required = false) String studentId,
            @RequestParam(value = "startIndex", required = false) String startIndex,
            Model model) throws StudentCourseEvaluationDoesNotExistException,
            NumberFormatException, StudentDoesNotExistException {
        int start = 1;
        if (startIndex != null) {
            start = Integer.valueOf(startIndex);
        }

        Student student;
        if (studentId == null) {
            student = userCastService.getUser().getStudent();
        } else {
            student = studentService.getStudentById(Long.valueOf(studentId));
        }
        int countStudentEvaluations = studentEvaluationService
                .countStudentCourseEvaluations(student.getId());

        List<StudentEvaluationTransactionBean> studentEvaluationList = new ArrayList<StudentEvaluationTransactionBean>();

        if (countStudentEvaluations <= PAGE_SIZE) {
            studentEvaluationList = studentEvaluationService
                    .getStudentEvaluationTransactionBeans(student.getId(),
                    start - 1, PAGE_SIZE);
            model.addAttribute("EVALUATIONS", studentEvaluationList);
        } else {

            int totalPages = countStudentEvaluations / PAGE_SIZE;
            if (countStudentEvaluations != totalPages * PAGE_SIZE) {
                ++totalPages;
            }
            if (startIndex != null) {
                start = Integer.valueOf(startIndex);
            }
            studentEvaluationList = studentEvaluationService
                    .getStudentEvaluationTransactionBeans(student.getId(),
                    start - 1 * PAGE_SIZE, PAGE_SIZE);
            model.addAttribute("EMPTY", false);
            model.addAttribute("EVALUATIONS", studentEvaluationList);
            model.addAttribute("PAGE_NUMBER", start);
            model.addAttribute("TOTAL_PAGES", totalPages);
        }

        if (studentEvaluationList == null || studentEvaluationList.isEmpty()) {
            model.addAttribute("NOLIST", "No Evaluations");
        }
        return STUDENT_COURSE_TEST_STATISTICS;
    }

    @RequestMapping(method = RequestMethod.GET, value = STUDENT_TEST_STATISTICS)
    public String studentTestStats() {
        return STUDENT_TEST_STATISTICS;
    }

    @RequestMapping(method = RequestMethod.GET, value = JJCLOUD_USER)
    public String newCloudUserForm(Model model)
            throws CompanySettingsDoesNotExistException {
        List<Location> locationList = locationService.getAllLocations();
        model.addAttribute("LOCATIONLIST", locationList);
        model.addAttribute("addUserBean", new AddUserBean());

        return JJCLOUD_USER;
    }

    @RequestMapping(method = RequestMethod.POST, value = JJCLOUD_USER)
    public @ResponseBody
    Map<String, ? extends Object> addCloudUser(
            @RequestBody CloudUserBean addUserBean, HttpServletRequest request)
            throws Exception {
        Set<ConstraintViolation<CloudUserBean>> failures = validator
                .validate(addUserBean);
        if (!failures.isEmpty()) {

            Collections.emptyList();
            return Collections.singletonMap("u", "Fill in required fields");
        }

        EmailValidatorBean emailValidatorBean = new EmailValidatorBean();

        if (!emailValidatorBean.validate(addUserBean.getEmail())) {

            Collections.emptyList();
            return Collections.singletonMap("u",
                    "The entered E-mail is incorrect!");
        }

        if (addUserBean.getLocation() == "") {

            Collections.emptyList();
            return Collections.singletonMap("u", "Select the user's location");
        }

        try {
            User temp = userService.getUserByUsername(addUserBean.getEmail());

            Collections.emptyList();
            return Collections
                    .singletonMap("u",
                    "The user entered already exists! Enter another E-mail address.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = new User();

        user.setUsername(addUserBean.getEmail());
        user.setEmail(addUserBean.getEmail());

        user.setFirstName(addUserBean.getFirstName());
        user.setLastName(addUserBean.getLastName());

        String newPassword = addUserBean.getPassword();

        String encryptPassword = CredentialsEncoder.generateMD5(newPassword);

        user.setPassword(encryptPassword);

        List<Role> roles = new ArrayList<Role>();
        try {
            Role role = roleService.getRoleByName("ROLE_CLOUDADMIN");

            roles.add(role);
        } catch (Exception e) {
        }

        user.setRoles(roles);

        Location location = locationService.getLocationByName(addUserBean
                .getLocation());
        user.setLocation(location);

        userService.addUser(user);

        StringBuilder builder = new StringBuilder();
        builder.append("Dear " + user.getFirstName() + ",<br/>");
        builder.append("<p></p>");
        builder.append("<p> Welcome to JJpeople e-learning program.</p> ");
        builder.append("<p> Your details are as follows:</p> ");
        builder.append("<strong>Email: </strong>" + user.getEmail() + "<br/>");
        builder.append("<strong>Password: </strong>" + newPassword);
        builder.append("</ul></p>");
        builder.append("<p>Once you login, you can change you password by clicking on the 'My profile' icon(it's on the top navigation panel). Then Click on the 'Edit password'.</p>");
        builder.append("<p></p>");
        builder.append("<p>Kind Regards,</p>");
        builder.append("<p></p>");
        builder.append("<p>JJ People Team</p>");

        String message = builder.toString();

        mailService.sendMail(message, addUserBean.getEmail());

        return Collections.singletonMap("u", "Saved");
    }

    @RequestMapping(method = RequestMethod.GET, value = JJCLOUD_LIST_USER)
    public String listCloudUsers(
            @RequestParam(value = "startIndex", required = false) String startIndex,
            Model model) throws CompanyDoesNotExistException {
        String role = "ROLE_CLOUDADMIN";
        List<User> userList = new ArrayList<User>();
        userList = userService.getAllUsers(role);
        int countUsers = userList.size();

        if (countUsers <= PAGE_SIZE) {

            model.addAttribute("USERSLIST", userList);
        } else {
            int start = 1;
            int totalPages = countUsers / PAGE_SIZE;
            if (countUsers != totalPages * PAGE_SIZE) {
                ++totalPages;
            }
            if (startIndex != null) {
                start = Integer.valueOf(startIndex);
            }
            userList = userService.getPaginatedUsersByRole((start - 1)
                    * PAGE_SIZE, PAGE_SIZE, role);
            model.addAttribute("USERSLIST", userList);
            model.addAttribute("PAGE_NUMBER", start);
            model.addAttribute("TOTAL_PAGES", totalPages);
        }
        return JJCLOUD_LIST_USER;
    }

    @RequestMapping(value = CHANGE_CLOUD_USER_STATUS, method = RequestMethod.GET)
    public String enableDisableCloudUser(
            @RequestParam(value = "userId", required = false) String userId,
            Model model) throws Exception {

        long uId = Long.valueOf(userId);
        User user = userService.getUserById(uId);

        if (user.getIs_enabled() == 1) {
            user.setIs_enabled(0);
        } else {
            user.setIs_enabled(1);
        }

        userService.saveUser(user);

        return JJCLOUD_LIST_USER;
    }

    @SuppressWarnings("unused")
    private Map<String, String> addCloudUserValidationMessages(
            Set<ConstraintViolation<CloudUserBean>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<CloudUserBean> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(),
                    failure.getMessage());
        }
        return failureMessages;
    }

    @RequestMapping(method = RequestMethod.GET, value = SEND_MESSAGE)
    public String newMessage(@RequestParam("userId") String id, Model model) {
        SendMessageBean sendMessageBean = new SendMessageBean();
        Long userId = Long.parseLong(id);
        User user = null;
        try {
            user = userService.getUserById(userId);
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();

        }

        Student student = null;
        try {
            student = studentService.getStudentByUser(user);
        } catch (StudentDoesNotExistException e) {
            e.printStackTrace();
        }
        sendMessageBean.setStudentId(student.getId());

        model.addAttribute("sendMessageBean", sendMessageBean);

        return SEND_MESSAGE;
    }

    @RequestMapping(method = RequestMethod.POST, value = SEND_MESSAGE)
    public @ResponseBody
    Map<String, ? extends Object> sendMessage(
            @RequestBody SendMessageBean sendMessageBean) {

        String messageContent = sendMessageBean.getMessage();
        Long studentId = sendMessageBean.getStudentId();
        Message message = new Message();
        message.setMessage(messageContent);
        message.setStudentId(studentId);
        try {
            messageBoardService.saveMessage(message);

        } catch (MessageExistsException e) {
            e.printStackTrace();
        }

        return Collections.singletonMap("u", "Saved");
    }

    /**
     * Processes the uploaded vcard file
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = UPLOAD_USER_VCARD)
    public @ResponseBody
    String readVcard(@RequestParam("myfile") MultipartFile file) {

        // get the uploaded vcard file and create a string of bytes
        String strVcard;
        String details = "";
        try {
            strVcard = new String(file.getBytes());

            // process the Vcard
            VCardEngine vcardEngine = new VCardEngine();
            VCard vcard = vcardEngine.parse(strVcard);

            // put the necessary details in a JSONObject
            JSONObject userDetails = new JSONObject();
            userDetails.put("firstName", vcard.getName().getGivenName());
            userDetails.put("lastName", vcard.getName().getFamilyName());

            String email = "";
            Iterator<EmailFeature> itr = (Iterator<EmailFeature>) vcard.getEmails();
            
            while (itr.hasNext()) {
                email = itr.next().getEmail();
            }
            userDetails.put("email", email);

            details = userDetails.toString();
            System.out.println("\n\n\n >>>> Json vcard:" + details);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;
    }
}
