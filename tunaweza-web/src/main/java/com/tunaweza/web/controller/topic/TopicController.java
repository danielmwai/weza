package com.tunaweza.web.controller.topic;

import com.tunaweza.core.business.service.topic.RefreshTreeBean;
import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.module.ModuleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.topic.TopicNotFoundExistException;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.file.File;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.topic.AddTopicBean;
import com.tunaweza.core.business.model.topic.EditTopicBean;
import com.tunaweza.core.business.model.topic.ListTopicBean;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.company.CompanyService;
import com.tunaweza.core.business.service.company.settings.CompanySettings;
import com.tunaweza.core.business.service.file.FileService;
import com.tunaweza.core.business.service.module.ModuleService;
import com.tunaweza.core.business.service.topic.TopicService;
import com.tunaweza.core.business.service.topic.lastaccessed.LastAccessedTopicService;
import com.tunaweza.core.business.service.user.UserCastService;

import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.ADD_TOPIC_REPLY;
import static com.tunaweza.web.views.Views.CHANGE_STATUS_TOPIC;
import static com.tunaweza.web.views.Views.DELETE_TOPIC_FILE;
import static com.tunaweza.web.views.Views.DOWNLOAD_ATTACHED_TOPIC_FILE;
import static com.tunaweza.web.views.Views.EDIT_TOPIC;
import static com.tunaweza.web.views.Views.LAST_ACCESSED_TOPIC_IN_MODULE;
import static com.tunaweza.web.views.Views.LIST_PARENT_TOPICS;
import static com.tunaweza.web.views.Views.LIST_TOPICS;
import static com.tunaweza.web.views.Views.NEW_TOPIC;
import static com.tunaweza.web.views.Views.PAGE_SIZE;
import static com.tunaweza.web.views.Views.REFRESH_TOPICS_LIST_BY_MODULE;
import static com.tunaweza.web.views.Views.TOPICS_LIST;
import static com.tunaweza.web.views.Views.TOPICS_LIST_BY_MODULE;
import static com.tunaweza.web.views.Views.TOPIC_LIST;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author amosl
 *
 * @author David Gitonga
 */
@Controller
@RequestMapping(Views.TOPIC)
public class TopicController implements Views {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private TopicService topicService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private UserCastService userCastService;

    @Autowired
    CompanyService companyService;

    @Autowired
    private LastAccessedTopicService lastAccessedTopicService;

    @Autowired
    private Validator validator;

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = NEW_TOPIC)
    public String newTopicForm(
            @RequestParam(value = "moduleId", required = true) String moduleId,
            Model model, HttpServletRequest request) {
        long id = Long.valueOf(moduleId);
        List<Topic> topics = topicService.getAllTopicsByModule(id);
        List<Topic> filteredTopic = new ArrayList<Topic>();
        AddTopicBean addTopicBean = new AddTopicBean();

        try {
            Module module = moduleService.getModuleById(id);

            model.addAttribute("MODULE", module);
            if (module.isMentoring() == true) {
            }
        } catch (Exception e) {
        }
        addTopicBean.setModule(String.valueOf(moduleId));
        addTopicBean.setEvaluationQuestionsLimit(String.valueOf(0));

        model.addAttribute("addTopicBean", addTopicBean);
        for (int i = 0; i < topics.size(); i++) {
            if (!topics.get(i).isExercise()) {
                topics.get(i).setName(StringEscapeUtils.unescapeHtml(topics.get(i).getName()));
                filteredTopic.add(topics.get(i));
            }

        }
        model.addAttribute("TOPICS", filteredTopic);
        model.addAttribute("MODULEID", moduleId);
        HttpSession session = request.getSession(true);
        session.setAttribute("Module", moduleId);

        return NEW_TOPIC;
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = TOPICS_LIST)
    public String listTopics(Model model) {
        model.addAttribute("addTopicBean", new AddTopicBean());
        model.addAttribute("MODULELIST", moduleService.getAllModules());
        return TOPICS_LIST;
    }

    /**
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = LIST_TOPICS)
    public String listTopics(
            @RequestParam(value = "id", required = true) String id,
            HttpServletResponse response) throws Exception {
        List<com.tunaweza.core.business.model.topic.ListTopicBean> list = topicService.listTopicsByModule(Long
                .valueOf(id));
        MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;
        if (jsonConverter.canWrite(list.getClass(), jsonMimeType)) {
            try {
                jsonConverter.write(list, jsonMimeType,
                        new ServletServerHttpResponse(response));
            } catch (IOException m_Ioe) {
            } catch (HttpMessageNotWritableException p_Nwe) {
            }
        }
        return null;
    }

    /**
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = EDIT_TOPIC)
    public String editTopicForm(
            @RequestParam(value = "topicId", required = false) String id,
            @RequestParam(value = "moduleId", required = false) String moduleId,
            Model model, HttpServletRequest request) throws Exception {

        model.addAttribute("MODULEID", moduleId);
        Topic topic = topicService.getTopicById(Long.valueOf(id));
        EditTopicBean editTopicBean = new EditTopicBean();
        long parentId = topic.getParentId();
        String allowed = "";
        if (parentId != 0) {
            Topic parent = topicService.getTopicById(parentId);
            long level = parent.getLevel();
            allowed = "From " + String.valueOf(level++);
        } else {
            allowed = "Any Level";
        }

        model.addAttribute("ALLOWED", allowed);
        editTopicBean.setId(String.valueOf(topic.getId()));
        editTopicBean.setName(StringEscapeUtils.unescapeHtml(topic.getName()));
        editTopicBean.setLevel(topic.getLevel() + "");
        editTopicBean.setModule(topic.getModule().getName());
        editTopicBean.setParent(String.valueOf(topic.getParentId()));

        editTopicBean.setEvaluationQuestionsLimit(String.valueOf(topic
                .isExercise() ? 0 : topic.getEvaluationQuestionsLimit()));
        List<Module> listModule = new ArrayList<Module>();
        listModule.add(topic.getModule());
        model.addAttribute("MODULELIST", listModule);
        List<Topic> listTopics = topicService.getAllTopicsByModule(topic
                .getModule().getId());
        List<Topic> filteredTopic = new ArrayList<Topic>();
        for (int i = 0; i < listTopics.size(); i++) {
            if (!listTopics.get(i).isExercise()) {
                listTopics.get(i).setName(StringEscapeUtils.unescapeHtml(listTopics.get(i).getName()));
                filteredTopic.add(listTopics.get(i));
            }

        }

        model.addAttribute("PARENTLIST", filteredTopic);

        if (topic.isExercise()) {
            editTopicBean.setExercise(true);
            model.addAttribute("EXE", 1);
        } else {
            editTopicBean.setExercise(false);
            model.addAttribute("EXE", 0);
        }
        try {
            if (topic.getFile().getFile().length() != 0) {
                model.addAttribute("FILE_ATTACHED", true);
                model.addAttribute("FILE_NAME", topic.getFile().getFileName());
                model.addAttribute("FILE_ID", topic.getFile().getId());
            } else {
                model.addAttribute("FILE_ATTACHED", false);
            }
        } catch (NullPointerException e) {
            model.addAttribute("FILE_ATTACHED", false);
        }
        try {
            editTopicBean.setText(topic.getTopicText().getText());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        model.addAttribute("editTopicBean", editTopicBean);
        HttpSession session = request.getSession(true);
        session.setAttribute("Module", moduleId);
        return EDIT_TOPIC;
    }

    /**
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = LIST_PARENT_TOPICS)
    public String listParentTopics(
            @RequestParam(value = "id", required = true) String id,
            HttpServletResponse response) throws Exception {
        List<com.tunaweza.core.business.model.topic.ListTopicBean> list = topicService.listParentTopicsByModule(Long
                .valueOf(id));
        MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;
        if (jsonConverter.canWrite(list.getClass(), jsonMimeType)) {
            try {
                jsonConverter.write(list, jsonMimeType,
                        new ServletServerHttpResponse(response));
            } catch (IOException m_Ioe) {
            } catch (HttpMessageNotWritableException p_Nwe) {
            }
        }
        return null;
    }

    /**
     *
     * @param model
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET, value = TOPICS_LIST_BY_MODULE)
    public String listTopics(@RequestParam("moduleId") String mod_Id,
            Model model, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;
        Long moduleId = Long.valueOf(mod_Id);
        List<com.tunaweza.core.business.model.topic.TopicBean> topics = topicService.getAllTopicsByModuleJson(
                moduleId, request);
        if (topics != null
                && jsonConverter.canWrite(topics.getClass(), jsonMimeType)) {
            try {
                jsonConverter.write(topics, jsonMimeType,
                        new ServletServerHttpResponse(response));
            } catch (IOException m_Ioe) {
                LOGGER.info("Exce" + m_Ioe.getMessage());
            } catch (HttpMessageNotWritableException p_Nwe) {
                LOGGER.info("Exce" + p_Nwe.getMessage());
            }
        } else {
            LOGGER.info("JSON Exception");
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = REFRESH_TOPICS_LIST_BY_MODULE)
    public String refreshStudentTree(@RequestParam("moduleId") String mod_Id,
            Model model, HttpServletResponse response) throws IOException {

        MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;
        Long moduleId = Long.valueOf(mod_Id);
        List<RefreshTreeBean> topics = topicService
                .getStatusOfExercisesByModule(moduleId);
        if (topics != null
                && jsonConverter.canWrite(topics.getClass(), jsonMimeType)) {
            try {
                jsonConverter.write(topics, jsonMimeType,
                        new ServletServerHttpResponse(response));
            } catch (HttpMessageNotWritableException p_Nwe) {
                LOGGER.info("Exce" + p_Nwe.getMessage());

            } catch (Exception m_Ioe) {
                LOGGER.info("Exce" + m_Ioe.getMessage());
            }
        } else {
            LOGGER.info("JSON Exception");
        }
        return null;
    }

    /*
     * @RequestMapping(method = RequestMethod.POST, value = NEW_TOPIC) public
     * 
     * @ResponseBody Map<String, ? extends Object> editRole(
     * 
     * @RequestBody AddTopicBean addTopicBean) throws Exception {
     * Set<ConstraintViolation<AddTopicBean>> failures = validator
     * .validate(addTopicBean); if (!failures.isEmpty()) {
     * addValidationMessages(failures); return Collections.singletonMap("u",
     * "Fill in required fields"); } else { try {
     * Long.valueOf(addTopicBean.getParent());
     * 
     * } catch (NumberFormatException e) { return Collections.singletonMap("u",
     * "Please Select a Parent"); } Module module =
     * moduleService.getModuleById(Long .valueOf(addTopicBean.getModule())); try
     * { topicService.savingTopic(addTopicBean, module); } catch
     * (TopicExistsException ie) { return Collections.singletonMap("u",
     * "Topic Already exists"); } return Collections.singletonMap("u", "Saved");
     * } }
     */
    @RequestMapping(method = RequestMethod.POST, value = NEW_TOPIC)
    public String addTopic(AddTopicBean addTopicBean, Model model)
            throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>EXERCISE IS"
                + addTopicBean.getExercise());
        Set<ConstraintViolation<AddTopicBean>> failures = validator
                .validate(addTopicBean);
        if (!failures.isEmpty()) {
            addValidationMessages(failures);
            model.addAttribute("MESSAGE", "Fill in required fields");
            model.addAttribute("ERRORS", true);
            return ADD_TOPIC_REPLY;
        } //		else if (!isWord(addTopicBean.getName())) {
        //			model.addAttribute("MESSAGE",
        //					"Special characters(E.g *, -, +, ?, !, %) are not allowed in Topic name.");
        //			return ADD_TOPIC_REPLY;
        //		}
        else {
            if (addTopicBean.getExercise() == null
                    && !addTopicBean.getEvaluationQuestionsLimit().matches(
                    "\\d{1,2}")) {
                model.addAttribute("MESSAGE",
                        "The number of evaluation questions must be an integer value between 0-99");
                return ADD_TOPIC_REPLY;
            }
            try {
                Long.valueOf(addTopicBean.getParent());

            } catch (NumberFormatException e) {
                model.addAttribute("MESSAGE", "Please Select a Parent");
                model.addAttribute("ERRORS", true);
                return ADD_TOPIC_REPLY;
            }
            Module module = moduleService.getModuleById(Long
                    .valueOf(addTopicBean.getModule()));
            try {

                topicService.savingTopic(addTopicBean, module);
                model.addAttribute("ERRORS", false);
                model.addAttribute("MESSAGE", "Saved");
                model.addAttribute("MODULEID", module.getId());
            } catch (Exception ie) {

                model.addAttribute("MESSAGE", "Topic Already exists");
                model.addAttribute("ERRORS", true);
                return ADD_TOPIC_REPLY;
            }

            return ADD_TOPIC_REPLY;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = EDIT_TOPIC)
    public String editTopic(EditTopicBean editTopicBean, Model model)
            throws Exception {
        Set<ConstraintViolation<EditTopicBean>> failures = validator
                .validate(editTopicBean);

        if (editTopicBean.getText().equals("")) {
            model.addAttribute("ERRORS", true);
            model.addAttribute("MESSAGE", "Fill in required fields");
            return ADD_TOPIC_REPLY;
        }

        if (!failures.isEmpty()) {
            editValidationMessages(failures);
            model.addAttribute("ERRORS", true);
            model.addAttribute("MESSAGE", "Fill in required fields");
            return ADD_TOPIC_REPLY;
        } //		else if (!isWord(editTopicBean.getName())) {
        //			model.addAttribute("MESSAGE",
        //					"Special characters(E.g *, -, +, ?, !, %) are not allowed in Topic name.");
        //			return ADD_TOPIC_REPLY;
        //		}
        else {
            if (!editTopicBean.getEvaluationQuestionsLimit()
                    .matches("\\d{1,2}") && !editTopicBean.getExercise()) {
                model.addAttribute("MESSAGE",
                        "Number of evaluation questions must be an integer between 0-99");
                return ADD_TOPIC_REPLY;
            }
            try {
                Long.valueOf(editTopicBean.getLevel());
            } catch (NumberFormatException e) {
                model.addAttribute("ERRORS", true);
                model.addAttribute("MESSAGE", "Level must be an Integer");
                return ADD_TOPIC_REPLY;
            }
            Module module = moduleService.getModuleById(Long
                    .valueOf(editTopicBean.getModule()));

            try {

                String message = topicService.savingTopic(editTopicBean,
                        module, editTopicBean.getId());

                model.addAttribute("MESSAGE", message);
                if (message.equals("Saved")) {
                    model.addAttribute("ERRORS", false);
                    model.addAttribute("MODULEID", module.getId());
                } else {
                    model.addAttribute("ERRORS", true);
                }
                return ADD_TOPIC_REPLY;

            } catch (Exception ie) {
                model.addAttribute("MESSAGE", "Topic Already exists");
                model.addAttribute("ERRORS", true);
                return ADD_TOPIC_REPLY;
            }

        }
    }

    private Map<String, String> addValidationMessages(
            Set<ConstraintViolation<AddTopicBean>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<AddTopicBean> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(),
                    failure.getMessage());
        }
        return failureMessages;
    }

    private Map<String, String> editValidationMessages(
            Set<ConstraintViolation<EditTopicBean>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<EditTopicBean> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(),
                    failure.getMessage());
        }
        return failureMessages;
    }

    @RequestMapping(value = CHANGE_STATUS_TOPIC, method = RequestMethod.GET)
    public String enableTopic(
            @RequestParam(value = "topicId", required = false) String topicId,
            @RequestParam(value = "moduleId", required = true) String moduleId,
            @RequestParam(value = "startIndex", required = false) String startIndex,
            @RequestParam(value = "searchString", required = false) String searchString,
            Model model) throws Exception {
        searchString = searchString == null ? "" : searchString;
        topicService.changeStatusTopic(topicId);
        return topicList(moduleId, startIndex, URLDecoder.decode(searchString, "UTF-8"), null, model);
    }

    @RequestMapping(value = "listtopic", method = RequestMethod.GET)
    public String listTopics(@RequestParam(value = "moduleId", required = true) String id,
            @RequestParam(value = "startIndex", required = false) String startIndex,
            @RequestParam(value = "searchString", required = false) String searchString,
            @RequestParam(value = "view", required = false) String view,
            Model model) throws Exception {

        return topicList(id,
                startIndex,
                URLDecoder.decode(searchString, "UTF-8"), "listtopic", model);

    }

    @RequestMapping(value = TOPIC_LIST, method = RequestMethod.GET)
    public String topicList(
            @RequestParam(value = "moduleId", required = true) String id,
            @RequestParam(value = "startIndex", required = false) String startIndex,
            @RequestParam(value = "searchString", required = false) String searchString,
            @RequestParam(value = "view", required = false) String view,
            Model model) throws Exception {// get the total number of topics in
        // the module
		/*
         * int numberOfTopicsInModule = topicService
         * .getNumberOfTopicsByModule(Long.valueOf(id));
         */

        searchString = searchString == null ? "" : searchString;
        int numberOfTopicsInModule = topicService
                .getNumberOfTopicByNameAndModule(searchString, Long.valueOf(id));

        /*
         * if numberOfTopicsInModule<=10 return all topics else return a
         * paginated list
         */
        List<ListTopicBean> myTopicList = new ArrayList<ListTopicBean>();

        if (numberOfTopicsInModule <= 10) {
            /* myTopicList = topicService.listTopicsByModule(Long.valueOf(id)); */
            myTopicList = topicService.findTopicByNameAndModuleId(searchString,
                    Long.valueOf(id));
        } else {
            // get the paginatedTopicList of PAGE_SIZE topics per page
            List<Topic> topicList = new ArrayList<Topic>();
            List<ListTopicBean> lst = new ArrayList<ListTopicBean>();

            CompanySettings companySettings = null;
            boolean mentoring_c = true;
            boolean mentoring_m = true;
            try {
                Company company = companyService.findCompanyByEmail(
                        userCastService.getUser().getEmail());

                companySettings = company.getCompanySettings();
                if (companySettings != null) {
                    mentoring_c = companySettings.getMentoring();
                }
                Module module = moduleService.getModuleById(Long.valueOf(id));
                mentoring_m = module.isMentoring();
            } catch (CompanyDoesNotExistException e1) {
                e1.printStackTrace();
            } catch (ModuleDoesNotExistException e1) {
                e1.printStackTrace();
            }

            int start = 1;
            int totalPages = numberOfTopicsInModule / PAGE_SIZE;
            if (numberOfTopicsInModule != totalPages * PAGE_SIZE) {
                ++totalPages;
            }
            if (startIndex != null) {
                start = Integer.valueOf(startIndex);
            }
            topicList = topicService.getPaginatedTopics(
                    (start - 1) * PAGE_SIZE, PAGE_SIZE, Long.valueOf(id),
                    searchString);
            if (topicList != null) {
                for (Topic topic : topicList) {
                    ListTopicBean topicBean = new ListTopicBean();
                    topicBean.setTopicId(topic.getId().toString());
                    topicBean.setTopicName(StringEscapeUtils.unescapeHtml(topic.getName()));
                    // topicBean.setParentId(topic.getParentId() + "");
                    try {
                        if (topic.getParentId() == 0) {
                            topicBean.setParentName("Module as Parent");
                        } else {
                            topicBean.setParentName(topicService.getTopicById(topic.getParentId()).getName());
                        }
                    } catch (TopicNotFoundExistException e) {
                        e.printStackTrace();
                    }
                    topicBean.setLevel(topic.getLevel() + "");
                    topicBean.setEnabled(topic.getEnabled());
                    topicBean.setExercise(topic.isExercise());
                    //lst.add(topicBean);
                    if (mentoring_c && mentoring_m) {
                        lst.add(topicBean);
                    } else if (mentoring_c && !mentoring_m) {
                        if (!topic.isExercise()) {
                            lst.add(topicBean);
                        }
                    } else {
                        if (!topic.isExercise()) {
                            lst.add(topicBean);
                        }
                    }

                }
            }
            myTopicList = lst;
            model.addAttribute("PAGE_NUMBER", start);
            model.addAttribute("TOTAL_PAGES", totalPages);

        }

        model.addAttribute("TOPICLIST", myTopicList);
        model.addAttribute("COUNT", numberOfTopicsInModule);
        model.addAttribute("MODULEID", id);
        model.addAttribute("SEARCH_STRING", searchString);

        return view == null ? TOPIC_LIST : view;
    }

    @RequestMapping(method = RequestMethod.GET, value = LAST_ACCESSED_TOPIC_IN_MODULE)
    public @ResponseBody
    Map<String, ? extends Object> lastAccessedTopicIdInModule(
            @RequestParam(value = "moduleId", required = true) long moduleId)
            throws Exception {

        User user = userCastService.getUser();

        Topic lastAccessedTopic = lastAccessedTopicService
                .getLastAccessedTopicInModule(user.getId(), moduleId);

        if (lastAccessedTopic == null) {
            return Collections.singletonMap("topicId", -1);
        } else {
            return Collections.singletonMap("topicId",
                    lastAccessedTopic.getId());
        }
    }

    @RequestMapping(value = DOWNLOAD_ATTACHED_TOPIC_FILE, method = RequestMethod.GET)
    public void downloadTopicFile(
            @RequestParam(value = "fileId", required = true) String fileId,
            HttpServletResponse response) throws NumberFormatException,
            TopicNotFoundExistException {

        com.tunaweza.core.business.model.file.File file = fileService.getFileById(Long.valueOf(fileId));

        try {
            int fileSize = (int) file.getFile().length();

            response.setBufferSize(fileSize);
            response.setContentType(file.getContentType());
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + file.getFileName() + "\"");
            response.setContentLength(fileSize);
            OutputStream out = response.getOutputStream();
            FileCopyUtils.copy(file.getFile().getBinaryStream(), out);
            out.flush();
            out.close();

        } catch (IOException e) {
            LOGGER.error(e);
        } catch (SQLException e) {
            LOGGER.error(e);
        }

    }

    @RequestMapping(value = DELETE_TOPIC_FILE, method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> deleteTopicFile(
            @RequestParam(value = "fileId", required = true) String fileId)
            throws Exception {
        Map<String, String> dataMap = new HashMap<String, String>();
        File file = null;
        try {
            file = fileService.getFileById(Long.valueOf(fileId));
            file.getExercise().setFile(null);
            fileService.removeFile(Long.valueOf(fileId));
            dataMap.put("ERROR", "false");
            dataMap.put("MESSAGE", "Removed " + file.getFileName());
        } catch (Exception ex) {
            dataMap.put("ERROR", "true");
            dataMap.put("MESSAGE", "Error trying to remove file");
        }

        return dataMap;

    }

    public static boolean isWord(String name) {
        return name.matches("[a-zA-Z][\\w\\s]*");
    }
}
