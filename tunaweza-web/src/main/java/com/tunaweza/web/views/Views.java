package com.tunaweza.web.views;

public interface Views {
	/** ----------------global------------------ **/
	public static String ADD = "add";
	public static String LIST = "list";
	public static String EDIT = "edit";
	public static String DELETE = "delete";
	public static int PAGE_SIZE = 10;

	public static String HOME = "home";
	public static String LOGIN = "login";

	public static String INDEX = HOME + "/index";
	public static String EVAL = "/eval/admin";
	public static String SECURE = "";
	public static String SECURE_HOME = SECURE + HOME;

	/** -----------------Roles--------------- **/
	public static String ROLE = "role";
	public static String NEW_ROLE = "addrole";
	public static String NEW_ROLE_FORM = "addrole";
	public static String EDIT_ROLE = "editrole";
	public static String EDIT_ROLE_FORM = "editroleform";
	public static String ROLE_LIST = "listroles";
	public static String ROLES_LIST = "list";

	/** -----------------Locations--------------- **/
	public static String LOCATION = "location";
	public static String NEW_LOCATION = "addlocation";
	public static String NEW_LOCATION_FORM = "addlocation";
	public static String EDIT_LOCATION = "editlocation";
	public static String EDIT_LOCATION_FORM = "editlocationform";
	public static String LOCATION_LIST = "listlocations";
	public static String LOCATIONS_LIST = "list";

	/** -----------------Users--------------- **/
	public static String USER = "user";
	public static String EDIT_USER_PASSWORD_FORM = "editpassword";
	public static String EDIT_USER_PASSWORD = "editpass";
	public static String FORGOT_PASSWORD_FORM = "forgotpassword";
	public static String FORGOT_PASSWORD = "resendpass";
	public static String NEW_USER = "adduser";
	public static String NEW_USER_FORM = "adduser";
	public static String EDIT_USER = "edituser";
	public static String EDIT_USER_FORM = "edituserform";
	public static String LIST_USERS = "listusers";
	public static String ENABLE_USER = "enableuser";
	public static String DISABLE_USER = "disableuser";
	public static String CHANGE_STATUS_USER = "changestatus";
	public static String CHANGE_CLOUD_USER_STATUS = "changecloudUserstatus";
	public static String USER_PROFILE = "profile";
	public static String EDIT_EMAIL_FORM = "editemailform";
	public static String EDIT_EMAIL = "editemail";
	public static String SEARCH_USER = "search";
	public static String LIST_SEARCH = "listsearch";
	public static String CANCEL_SEARCH = "cancel";
	public static String USER_INFO = "userinfo";
	public static String STUDENT_INFO = "studentinfo";
	public static String USER_ENABLE_DISABLE_MODULE = "enabledisablemodule";

	/** ----------------Group------------------------------- **/
	public static String GROUP = "group";
	public static String NEW_GROUP = "addgroup";
	public static String NEW_GROUP_FORM = "addgroup";
	public static String EDIT_GROUP = "editgroup";
	public static String EDIT_GROUP_FORM = "editgroupform";
	public static String DELETE_GROUP = "deleteGroup";
	public static String GROUP_LIST = "listgroups";
	public static String GROUPS_LIST = "list";
	public static String ADD_USER_TO_GROUP = "addusertogroup";
	public static String USER_TO_GROUP = "addusergroup";
	public static String ASSIGN_GROUP_CT = "assigngroupct";
	public static String GROUP_COURSES_FORM = "groupctlistboxes";

	/** -----------------Modules--------------- **/
	public static String MODULE = "module";
	// public static String MODULE_LIST = "listmodules";
	public static String NEW_MODULE = "addmodule";
	public static String NEW_MODULE_FORM = "addmodule";
	public static String EDIT_MODULE = "editmodule";
	public static String EDIT_MODULE_FORM = "editmoduleform";
	public static String LIST_MODULES = "listmodules";
	public static String ENABLE_MODULE = "enablemodule";
	public static String DISABLE_MODULE = "disablemodule";
	public static String STUDENT_LIST_MODULES = "list/by/student";
	public static String MENTOR_LIST_MODULES = "list/by/mentor";
	public static String MODULE_INFO = "moduleinfo";
	public static String SET_CURRENT_MODULE_ON_STUDENT_CLICK = "setcurrentmodule_onstudentclick";
	public static String SET_CURRENT_MODULE_ON_MODULE_COMPLETION = "setcurrentmodule_onmodulecompletion";

	/** -----------------Topics--------------- **/
	public static String TOPIC = "topic";
	public static String NEW_TOPIC = "addtopic";
	public static String TOPICS_LIST = "list";
	public static String TOPICS_LIST_BY_MODULE = "list/by/modules";
	public static String REFRESH_TOPICS_LIST_BY_MODULE = "refresh/tree/by/modules";
	public static String CHANGE_STATUS_TOPIC = "changestatustopic";
	public static String LIST_TOPICS = "listtopics";
	public static String LIST_PARENT_TOPICS = "listparenttopics";
	public static String EDIT_TOPIC_FORM = "edittopicform";
	public static String EDIT_TOPIC = "edittopic";
	public static String TOPIC_LIST = "topiclist";
	public static String TOPIC_COMPLETE = "completetopic";
	public static String LAST_ACCESSED_TOPIC_IN_MODULE = "lastaccessedtopicinmodule";
	public static String DOWNLOAD_ATTACHED_TOPIC_FILE = "downloadattachedtopicfile";
	public static String ADD_TOPIC_REPLY = "addtopicreply";
	public static String DELETE_TOPIC_FILE = "deletetopicfile";
	/** ------------------Exercises---------- **/
	public static String USER_LIST_EXERCISES_BY_MODULE = "list/exercises/by/module";

	/** ------------------Root---------------- **/
	public static String ROOTP = "tools/super/admiset";
	public static String ROOTPT = "/root/login";
	public static String ROOT_HOME = INDEX;

	/** -----------------TopicTexts--------------- **/
	public static String TOPICTEXT = "topictext";
	public static String NEW_TOPICTEXT = "addtopictext";
	public static String TOPICSTEXT_LIST = "list";
	public static String TOPICSTEXT_VIEW = "view";
	public static String QUIZ_FORM = "quiz";
	public static String QUIZ_RESULTS = "quizresults";
	public static String QUIZ_NEXT = "quiznext";

	/** -----------------CourseTemplates--------------- **/
	public static String COURSETEMPLATE = "coursetemplate";
	public static String CT_INFO = "ctinfo";
	public static String COURSETEMPLATE_LIST = "listcoursetemplates";
	public static String NEW_COURSETEMPLATE = "addcoursetemplate";
	public static String NEW_COURESTEMPLATE_FORM = "addcoursetemplate";
	public static String EDIT_COURSETEMPLATE = "editcoursetemplate";
	public static String EDIT_COURSETEMPLATE_FORM = "editcoursetemplateform";
	public static String LIST_COURSETEMPLATE = "listcoursetemplates";
	public static String ASSIGN_COURSETEMPLATE_MODULES = "assignctmodules";
	public static String ASSIGN_COURSETEMPLATE_MODULES_FORM = "assignctmoduleform";
	public static String COURSETEMPLATE_MODULES_FORM = "ctlistboxes";
	public static String STUDENT_COURSETEMPLATE_FORM = "studentcoursetemplateform";
	// public static String STUDENT_COURSETEMPLATE="studentcoursetemplate";
	public static String ASSIGN_STUDENT_COURSETEMPLATE = "assignstudentct";

	/*** -------solutions----------------------- */
	public static String SOLUTION = "solution";
	public static String POST_SOLUTION = "postsolution";
	public static String POST_SOLUTION_REPLY = "postsolutionreply";
	public static String REPLACE_SOLUTION = "replacesolution";
	public static String READ_FEEDBACK = "readfeedback";
	public static String RESUBMIT_SOLUTION = "resubmitsolution";
	public static String DOWNLOAD_SOLUTION = "downloadsolution";

	/** ---------Mentoring------------------- **/
	public static String MENTORING = "mentoring";
	public static String GRADE_EXERCISE = "gradeexercise";
	public static String GRADE_EXERCISE_REPLY = "gradeexercisereply";
	public static String MENTOR_VIEW_FEEDBACK = "viewfeedback";
	public static String LIST_ALL_MENTORS = "listmentors";
	public static String MENTOR_STATISTICS = "mentorstatistics";
	public static String CHECK_IS_BEING_GRADED = "checkexercisegradingstatus";

	/** -----------------MentorTemplates--------------- **/
	public static String MENTORTEMPLATE = "mentor";
	public static String MENTORTEMPLATE_LIST = "listmentortemplates";
	public static String NEW_MENTORTEMPLATE = "addmentortemplate";
	public static String NEW_MENTORTEMPLATE_FORM = "addmentortemplate";
	public static String EDIT_MENTORTEMPLATE = "editmentortemplate";
	public static String EDIT_MENTORTEMPLATE_FORM = "editmentortemplateform";
	public static String LIST_MENTORTEMPLATE = "listmentortemplates";
	public static String ASSIGN_MENTORTEMPLATE_EXERCISE = "assignmtexercise";
	public static String ASSIGN_MENTORTEMPLATE_EXERCISE_FORM = "assignmtexerciseform";
	public static String MENTORTEMPLATE_EXERCISE_FORM = "mtlistboxes";
	// public static String STUDENT_COURSETEMPLATE_FORM
	// ="studentcoursetemplateform";
	// public static String STUDENT_COURSETEMPLATE="studentcoursetemplate";
	public static String ASSIGN_MENTOR_COURSETEMPLATE = "assignstudentct";
	public static String LIST_MENTOR_MODULES = "listmentormodules";
	public static String ASSIGN_MENTOR_MENTORTEMPLATES = "assignmentormt";
	public static String MENTOR_MENTORTEMPLATE_FORM = "mentorlistboxes";
	public static String MENTORTEMPLATE_PROFILE = "mentortemplateprofile";
	public static String MENTORTEMPLATE_TABBED_EXFORM = "mttabbedlistboxes";
	public static String MENTOR_MENTORTEMPLATE_TABBED = "mtabbedlistboxes";
	public static String LIST_TRANSACTIONS = "listtransactions";

	// tinyMce image
	public static String ADV_IMAGE = "js/tiny_mce/plugins/advimage";
	public static String IMAGE = "image";
	public static String IMAGE_DISPLAY = "displayimage";
	public static String IMAGE_UPLOAD_REPLY = "imageuploadreply";

	// tinyMce internal link  
	public static String INTERNAL_LINK = "internallinks";
	public static String TINYMCE_ROOT = "js/tiny_mce";
	public static String PLUGIN_ROOT = "plugins/";
	public static String THEME_ROOT = "themes/advanced/";
	public static String EXTERNAL_LINK = "advlink/link";
	public static String ANCHOR = "anchor";
	// tinyMce media
	public static String MEDIA= "media/media";

	/** -----------------EvaluationTemplates--------------- **/
	public static String EVALUATIONTEMPLATE = "evaluation";
	public static String ET_INFO = "etinfo";
	// public static String EVALUATIONTEMPLATE_LIST = "listevaluationtemplates";
	public static String NEW_EVALUATIONTEMPLATE = "addevaluationtemplate";
	public static String NEW_EVALUATIONTEMPLATE_FORM = "addevaluationtemplate";

	public static String EDIT_EVALUATIONTEMPLATE = "editevaluationtemplate";
	public static String EDIT_EVALUATIONTEMPLATE_FORM = "editevaluationtemplateform";
	public static String LIST_EVALUATIONTEMPLATE = "listevaluationtemplates";

	/** --------------------Student Test-------------------------- **/
	public static String STUDENT_TEST = "studenttest";
	public static String STUDENT_TEST_MODULE_NEW = "module/new";
	public static String STUDENT_TEST_TEMPLATE = "course/new";
	public static String TEST_MODULE_START = "module/start";
	public static String TEST_COURSE_START = "course/start";
	public static String STUDENT_TEST_MODULE = "module/administer/test";
	public static String STUDENT_TEST_MODULE_RESULTS = "module/results";

	public static String STUDENT_TEST_COURSE_NEW = "course/new";
	public static String STUDENT_TEST_COURSE = "course/administer/test";
	public static String STUDENT_TEST_COURSE_RESULTS = "course/results";
	public static String STUDENT_TEST_STATISTICS = "evaluation_stats";

	/** ---------------Student Test Questions--------------------- **/
	public static String QUESTION = "question";
	public static String ASSOCIATE_QUESTION_WITH_TOPIC = "associatequestionwithtopic";
	public static String DISSOCIATE_QUESTION_FROM_TOPIC = "dissociatequestionfromtopic";
	public static String TEST_TEMPLATE_QUESTION_FORM = "addevaluationquestion";
	public static String QUESTION_LIST = "listquestions";
	public static String EDIT_TEST_TEMPLATE_QUESTION_FORM = "editquestionform";
	public static String STUDENT_COURSE_TEST_STATISTICS = "course_evaluation_stats";
	public static String STUDENT_MODULE_TEST_STATISTICS = "module_evaluation_stats";
	public static String QUESTIONS_BY_TOPIC = QUESTION_LIST + "/bytopic";
	public static String LIST_QUESTIONS_NOT_ASSOCIATED_WITH_TOPIC = QUESTION_LIST
			+ "/unassociatedwithtopic";
	public static String LIST_QUESTIONS_T0_DISSOCIATE_FROM_TOPIC = QUESTION_LIST
			+ "/todissociatefromtopic";
	public static String DELETE_QUESTION = "deleteQuestion";

	/** -----------------Company Views------------------------------ **/
	// Controller and Controller Methods RequestMappings
	public static String COMPANY = "company";
	public static String NEW_COMPANY = "new_company";
	public static String CONTACT_DETAILS = "contact_details";
	// WebPage URLs
	public static String REGISTER_COMPANY = "register";
	public static String REGISTER_COMPANY_REPLY = "registerreply";
	public static String REGISTER_COMPANY_SUCCESS = "registersuccess";
	public static String EDIT_REGISTER_COMPANY = "editregister";
	public static String REGISTER_COMPANY_ERROR = "registererror";
	public static String NEW_COMPANY_REGISTRATION = "newcompanyregistration";
	

	public static String COMPANY_SETTING = "companysetting";
	public static String POST_COMPANY_SETTING = "postcompanysetting";
	public static String POST_COMPANY_SETTING_REPLY = "postcompanysettingreply";
	public static String IMAGELOGO = "imagelogo";
	public static String IMAGEDISPLAY = "imagedisplay";
	public static String DISPLAY_COMPANY_SETTING = "displaycompanysetting";
	public static String EDIT_COMPANY_SETTING = "editcompanysetting";
	public static String COMPANY_LOGO = "logo";
	public static String CONTACTS = "contacts";

	public static String PAYMENT_PLAN = "paymentplan";
	public static String MAKE_PAYMENT = "makepayment";
	public static String VERIFY_PAYMENT = "verifypayment";
	public static String PAYMENT_FAILURE = "paymentfailure";
	public static String PAYMENT_SUCCESS = "paymentsuccess";

	/*------------------JJCloud Instance Views---------------------*/
	public static String JJCLOUD_INSTANCE = "cloud";
	public static String CREATE_INSTANCE = "createcloudinstance";
	public static String LIST_INSTANCES = "listinstances";
	public static String EDIT_CLOUD_INSTANCE_FORM = "editcloudform";
	public static String EDIT_CLOUD = "editcloud";
	public static String REVOKE_INSTANCE = "revokeinstance";
	public static String DELETE_INSTANCE = "deleteinstance";
	public static String ENABLE_INSTANCE = "enableinstance";
	public static String CREATE_LICENSE = "addlicense";
	public static String LIST_LICENSES = "listlicenses";
	public static String LISTCOMPANYADMINS = "listcompanyadmins";
	public static String CHANGEADMINPASS = "changeadminpassword";
	/*-------------------JJCLOUD SUPER CLOUD ADMIN VIEWS--------*/
	public static String JJCLOUD_USER = "addclouduser";
	public static String JJCLOUD_LIST_USER = "listcloudusers";
	public static String SUPERCLOUDHOME = "superClouldAdmin";
	public static String PROMO_CODE_VIEW = "/promo_code/promocodeviews";
	public static String PROMO_CODE_SUCCESS = "/promo_code/newpromocodesuccess";
	public static String EDIT_PROMO_CODE = "/promo_code/editpromocodepage";
	public static String DISSOCIATE_PROMO_CODE = "/promo_code/dissociatepromocodepage";

	/*------------------Dynamic Reports-------------------*/
	public static String REPORT ="report";
	public static String USER_REPORT ="user";
	public static String SAVE_REPORT = "report_save";
	public static String SAVED_REPORT = "saved_report";
	public static String DELETE_REPORT = "delete_report";
	public static String LIST_REPORTS = "report_list";
	public static String REPORT_TABLE = "report_table";
	public static String DOWNLOAD_STUDENT_REPORT ="student";
	public static String DOWNLOAD_STUDENT_EVALUATION_REPORT = "student/evaluation";
	public static String CUSTOM_REPORT = "customreport";
	public static String CUSTOM_REPORT_PREVIEW = "customreportpreview";
	public static String STUDENT_PROGRESS_REPORT = "studentprogressreport";
	public static String USER_INFO_REPORT = "userinforeport";
	public static String LICENSE = "license";
	public static String SQL_REPORT = "sqlreport";
	public static String JOIN_TABLE = "join_table";
	public static String DOWNLOAD_CUSTOM_REPORT = "downloadreport";
	public static String GET_TABLE_FIELDS = "gettablefields";
	/*------------------PayPal------------------------*/
	public static String CONTENT_TYPE = "Content-Type";
	public static String MIME_APP_URLENC = "application/x-www-form-urlencoded";
	public static String PAY_PAL_DEBUG = "https://www.sandbox.paypal.com/cgi-bin/webscr";
	public static String PAY_PAL_PROD = "https://www.paypal.com/cgi-bin/webscr";
	public static String PARAM_NAME_CMD = "cmd";
	public static String PARAM_VAL_CMD = "_notify-validate";
	public static String RESP_VERIFIED = "VERIFIED";

	/*------------------Messaging------------------------*/
	public static String SEND_MESSAGE = "sendmessage";

	public static String UPLOAD_USER_VCARD = "uploadUserVcard";
	
	public static String EMAIL_CHECK = "emailcheck";
	
	public static String JJTEACH_DEMO = "jjteachDemo";
	public static String CONTACT_COMPANY = "contactcompany";
	public static String FREE_TRIAL = "freetrial";
	
	
	/*------------------Webservice------------------------*/
	public static String WS_AUTHENTICATE = "authenticate";
	public static String WS_GET_COURSES_AND_MODULES = "/getcoursesandmodules/{companyDbName}/{studentId}";
	public static String WS_GET_MODULE_TOPICS = "/getmoduletopics/{companyDbName}/{moduleId}";
	public static String WS_GET_MODULE_EVALUATION_TEMPLATE = "/getmoduleevaluationtemplate/{username}/{moduleId}";
	public static String WS_GET_EVALUATION_TEMPLATE_QUESTIONS = "/getevaluationtemplatequestions/{username}/{testId}";
	public static String WS_STUDENT_TEST_MODULE_NEW = "get/module/new";
	public static String WS_STUDENT_TEST_MODULE_RESULTS = "get/module/results";
	public static String WS_STUDENT_TEST_MODULE = "get/module/administer/test";
	

}
