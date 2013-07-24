package com.tunaweza.web.controller.group;


import com.tunaweza.core.business.dao.exceptions.group.GroupDoesNotExistsException;
import com.tunaweza.core.business.dao.exceptions.group.GroupExistsException;
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.course.EmbeddableCourse;
import com.tunaweza.core.business.model.group.Group;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.course.CourseService;
import com.tunaweza.core.business.service.group.GroupService;
import com.tunaweza.core.business.service.user.UserService;
import com.tunaweza.web.spring.configuration.group.bean.AddGroupBean;
import com.tunaweza.web.spring.configuration.group.bean.AssignGroupCourseTemplateBean;
import com.tunaweza.web.spring.configuration.group.bean.EditGroupBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.ASSIGN_GROUP_CT;
import static com.tunaweza.web.views.Views.DELETE_GROUP;
import static com.tunaweza.web.views.Views.EDIT_GROUP;
import static com.tunaweza.web.views.Views.EDIT_GROUP_FORM;
import static com.tunaweza.web.views.Views.GROUP_COURSES_FORM;
import static com.tunaweza.web.views.Views.GROUP_LIST;
import static com.tunaweza.web.views.Views.NEW_GROUP;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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



@Controller
@RequestMapping(Views.GROUP)
public class GroupController implements Views {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private GroupService groupService;

	@Autowired
	private Validator validator;

	@Autowired
	private CourseService courseTemplateService;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, value = NEW_GROUP)
	public String newGroupForm(Model model, HttpServletRequest request)
			throws GroupDoesNotExistsException {
		model.addAttribute("addGroupBean", new AddGroupBean());

		return NEW_GROUP;
	}

	@RequestMapping(value = EDIT_GROUP_FORM, method = RequestMethod.GET)
	public String editGroup(@RequestParam("groupId") String id, Model model)
			throws Exception {

		int groupId = Integer.valueOf(id);
		Group group = groupService.findGroupById(groupId);
		EditGroupBean editGroupBean = new EditGroupBean();
		editGroupBean.setId(group.getId());
		editGroupBean.setName(group.getName());
		editGroupBean.setDescription(group.getDescription());
		model.addAttribute("editGroupBean", editGroupBean);

		return EDIT_GROUP;
	}

	@RequestMapping(value = DELETE_GROUP, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> deleteGroup(@RequestParam("groupId") String id, Model model)
			throws Exception {
		int groupId = Integer.valueOf(id);
		Group group = groupService.findGroupById(groupId);
		Map<String, String> responseMap = new HashMap<String,String>();
		responseMap.put("empty", "true");
		List<User> users = groupService.getUsersInGroup(group);
		if(users.size()!=0){
			responseMap.put("empty", "false");
			return responseMap;
		}else{
			groupService.deleteGroup(groupId);
			responseMap.put("empty", "true");
		}
		
		return responseMap;

	}

	@RequestMapping(method = RequestMethod.GET, value = GROUP_LIST)
	public String listGroups(Model model) {
		List<Group> groupList = groupService.getAllGroups();
		Collections.sort(groupList);
		model.addAttribute("GROUPLIST", groupList);

		return GROUP_LIST;
	}

	@RequestMapping(method = RequestMethod.POST, value = NEW_GROUP)
	public @ResponseBody
	Map<String, ? extends Object> addGroup(
			@RequestBody AddGroupBean addGroupBean) {
		Set<ConstraintViolation<AddGroupBean>> failures = validator
				.validate(addGroupBean);
		if (!failures.isEmpty()) {
			addValidationMessages(failures);
			return Collections.singletonMap("u", "Fill in required fields");
		} else {
			try {
				Group group = groupService.findGroupByName(addGroupBean
						.getName());
			} catch (Exception e) {
				Group group = new Group();
				group.setName(addGroupBean.getName());
				if (!addGroupBean.getDescription().equals("")
						|| addGroupBean.getDescription() != null) {
					group.setDescription(addGroupBean.getDescription());
				}

				try {
					groupService.addGroup(group);
				} catch (GroupExistsException ex) {
					ex.printStackTrace();
				}
				return Collections.singletonMap("u", "Saved");
			}
		}
		return Collections.singletonMap("u",
				"A group with that name already exists");
	}

	@RequestMapping(method = RequestMethod.POST, value = EDIT_GROUP)
	public @ResponseBody
	Map<String, ? extends Object> editGroup(
			@RequestBody EditGroupBean editGroupBean) throws Exception {
		Set<ConstraintViolation<EditGroupBean>> failures = validator
				.validate(editGroupBean);
		if (!failures.isEmpty()) {
			editValidationMessages(failures);
			return Collections.singletonMap("u", "Fill in required fields");
		}

		else {
			int groupId = Integer
					.valueOf(String.valueOf(editGroupBean.getId()));
			Group group = groupService.findGroupById(groupId);
			group.setName(editGroupBean.getName());
			if (!editGroupBean.getDescription().equals("")
					|| editGroupBean.getDescription() != null) {
				group.setDescription(editGroupBean.getDescription());
			}

			groupService.saveGroup(group);

			return Collections.singletonMap("u", "Saved");
		}
	}

	@RequestMapping(value = ASSIGN_GROUP_CT, method = RequestMethod.GET)
	public String assignCourseModules(Model model) throws Exception {

		List<Group> groupList = groupService.getAllGroups();
		Collections.sort(groupList);

		model.addAttribute("GROUPLIST", groupList);
		return ASSIGN_GROUP_CT;
	}

	@RequestMapping(value = GROUP_COURSES_FORM, method = RequestMethod.GET)
	public String listGroupCT(
			@RequestParam(value = "groupId", required = false) String id,
			Model model) throws Exception {
	
		List<Course> courseTemplateList = groupService
				.getCoursesInGroup(groupService.findGroupById(Long.valueOf(id)));
		List<Course> allTemplatesList = courseTemplateService
				.listAllCourses();
		List<User> groupUsers = groupService.getUsersInGroup(groupService
				.findGroupById(Long.valueOf(id)));

		allTemplatesList.removeAll(courseTemplateList);
		Collections.sort(allTemplatesList);
		model.addAttribute("GROUPUSER", groupUsers);
		model.addAttribute("COURSELIST", courseTemplateList);
		model.addAttribute("ALLCOURSELIST", allTemplatesList);
		model.addAttribute("GROUPID", id);
		model.addAttribute("assignGroupCourseBean",
				new AssignGroupCourseTemplateBean());

		return GROUP_COURSES_FORM;

	}

	@RequestMapping(method = RequestMethod.POST, value = GROUP_COURSES_FORM)
	public @ResponseBody
	Map<String, ? extends Object> assignCourseModulesPost(
			@RequestBody AssignGroupCourseTemplateBean assignGroupCourseBean)
			throws Exception {

		Group group = groupService.findGroupById(Long
				.valueOf(assignGroupCourseBean.getId()));

		// all group courses before changes
		List<Course> allCT = groupService.getCoursesInGroup(group);

		List<EmbeddableCourse> courseList = new ArrayList<EmbeddableCourse>();

		try {
			if (!assignGroupCourseBean.getCourseList().startsWith("[")) {
				EmbeddableCourse embedd = new EmbeddableCourse();
				embedd.setCourseId(Long
						.valueOf(assignGroupCourseBean.getCourseList()));
				courseList.add(embedd);

			} else
				try {
					JSONArray coursesArray = new JSONArray(
							assignGroupCourseBean.getCourseList());
					for (int count = 0; count < coursesArray.length(); count++) {
						EmbeddableCourse embedd = new EmbeddableCourse();
						embedd.setCourseId(Long.valueOf(coursesArray
								.getLong(count)));
						courseList.add(embedd);
					}
				} catch (NumberFormatException ex) {
					return Collections.singletonMap("m",
							"Error converting string to long");
				}

		} catch (NullPointerException n) {
			System.out.println("Group has no assigned course Template!");

		} catch (NumberFormatException nfe) {
			System.out.println("NumberFormatException !");

		}

		// oldModID stores a list of all module IDs before changes
		// newModID stores a list of all module IDs after changes
		List<String> oldModID = new ArrayList<String>();
		List<String> newModID = new ArrayList<String>();

		for (EmbeddableCourse embeddableCourse : courseList) {
			newModID.add(embeddableCourse.getCourseId().toString());
		}

		for (Course courseTemplate : allCT) {
			oldModID.add(courseTemplate.getId().toString());

		}

		List<EmbeddableCourse> courseTemplateList = new ArrayList<EmbeddableCourse>();
		List<Course> groupTemp = groupService.getCoursesInGroup(group);

		System.out.println("Outside >>>>>>>>>>>>>>>>>>>" + courseTemplateList);

		try {
			for (EmbeddableCourse ct : courseList) {
				EmbeddableCourse embedd = new EmbeddableCourse();

				embedd.setCourseId(ct.getCourseId());
				embedd.setLevel(Long.valueOf(1));

				courseTemplateList.add(embedd);

			}

		} catch (NullPointerException n) {
			System.out.println("Student not assigned any course template !");

		}
		List<User> user = new ArrayList<User>();
		if (assignGroupCourseBean.getChecklist() == null) {
			return Collections.singletonMap("m", "select");
		}else{

		if (!assignGroupCourseBean.getChecklist().startsWith("[")) {

			User newUser = userService.getUserById(Long
					.valueOf(assignGroupCourseBean.getChecklist()));
			user.add(newUser);

		} else {
			JSONArray array = new JSONArray(
					assignGroupCourseBean.getChecklist());

			for (int i = 0; i < array.length(); i++) {
				User newUser = userService.getUserById(Long
						.parseLong((String) array.get(i)));
				user.add(newUser);
			}

		}
	}

		if (courseTemplateList != null && courseTemplateList.size() != 0) {
			if (user.size() == 0 || user == null) {
				return Collections.singletonMap("m", "noUser");
			}

			for (User usr : user) {

				userService.setUserCourse(usr.getId(),
						courseTemplateList);
			}
		} else {
			for (User usr : user) {

				userService.setUserCourse(usr.getId(),
						courseTemplateList);
			}
		}
		groupService.saveCoursesToGroup(group, courseList);

		return Collections.singletonMap("m", "Saved");

	}

	private Map<String, String> addValidationMessages(
			Set<ConstraintViolation<AddGroupBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<AddGroupBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	private Map<String, String> editValidationMessages(
			Set<ConstraintViolation<EditGroupBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<EditGroupBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}
}
