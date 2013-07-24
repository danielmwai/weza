package com.tunaweza.web.controller;

import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.tunaweza.core.business.dao.exceptions.role.RoleExistsException;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.service.company.CompanyService;
import com.tunaweza.core.business.service.company.settings.CompanySettings;
import com.tunaweza.core.business.service.role.RoleService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.web.spring.configuration.role.bean.AddRoleBean;
import com.tunaweza.web.spring.configuration.role.bean.EditRoleBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.EDIT_ROLE;
import static com.tunaweza.web.views.Views.EDIT_ROLE_FORM;
import static com.tunaweza.web.views.Views.NEW_ROLE;
import static com.tunaweza.web.views.Views.ROLES_LIST;
import static com.tunaweza.web.views.Views.ROLE_LIST;

@Controller
@RequestMapping(Views.ROLE)
public class RoleController implements Views 
{
	protected final Log LOGGER = LogFactory.getLog(getClass());
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private UserCastService userCastService;
	
	@Autowired
	private Validator validator;
	
	@RequestMapping(method = RequestMethod.GET,value=NEW_ROLE)
	public String newRoleForm(Model model) 
	{
		model.addAttribute("addRoleBean",new AddRoleBean());
		
		return NEW_ROLE; 
	}
	
	@RequestMapping(value=EDIT_ROLE_FORM,method = RequestMethod.GET)
	public String editRole( @RequestParam("roleId") String id,
							Model model) throws Exception 
	{
		int roleId = Integer.valueOf(id);
		Role role = roleService.getRoleById(roleId);
		EditRoleBean editRoleBean=new EditRoleBean();
		editRoleBean.setId(role.getId());
		editRoleBean.setName(role.getRoleName());
		editRoleBean.setDescription(role.getDescription());
		model.addAttribute("editRoleBean",editRoleBean);
		
		return EDIT_ROLE; 
	}
	
	@RequestMapping(method = RequestMethod.GET, value=ROLES_LIST)
	public String listRoles(Model model) 
	{
		List<Role> roleList=roleService.getAllRoles();
		
		CompanySettings companySettings = null;
		try {
			Company company = companyService.findCompanyByEmail(userCastService
					.getUser().getEmail());
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
		}else{
			model.addAttribute("ROLELIST", roleList);
		}
		
		Collections.sort(roleList);
		//model.addAttribute("ROLELIST",newRolelist);
		
		return ROLE_LIST;
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST,value = NEW_ROLE)
	public @ResponseBody Map<String, ? extends Object> addRole(
		   @RequestBody AddRoleBean addRoleBean) 
	{
		Set<ConstraintViolation<AddRoleBean>> failures 
		= validator.validate(addRoleBean);
		if (!failures.isEmpty()) 
		{
			addValidationMessages(failures);
			return Collections.singletonMap("u", "Fill in required fields");
		} 
		if(failures.isEmpty())
		{
			try
			{
				Role role = roleService.getRoleByName(addRoleBean.getName());				
			}
			catch(Exception e)
			{
				Role role=new Role();
				role.setRoleName(addRoleBean.getName());
				role.setDescription(addRoleBean.getDescription());
				
				try 
				{
					roleService.addRole(role);
				} catch (RoleExistsException ex) 
				{
					ex.printStackTrace();
				}
				return Collections.singletonMap("u", "Saved");
			}			
		}
		return Collections.singletonMap("u", "A role with that name already exists");
	}
	
	@RequestMapping(method = RequestMethod.POST,value = EDIT_ROLE)
	public @ResponseBody Map<String, ? extends Object> editRole(
		   @RequestBody EditRoleBean editRoleBean) throws Exception
	{
		Set<ConstraintViolation<EditRoleBean>> failures 
		= validator.validate(editRoleBean);
		if (!failures.isEmpty()) 
		{
			editValidationMessages(failures);
			return Collections.singletonMap("u", "Fill in required fields");
		} 
	
		else 
		{
			int roleId=Integer.valueOf(String.valueOf(editRoleBean.getId()));
			Role role = roleService.getRoleById(roleId);
			role.setRoleName(editRoleBean.getName());
			role.setDescription(editRoleBean.getDescription());
			
			roleService.updateRole(role);

			return Collections.singletonMap("u", "Saved");
		}
	}
	
	private Map<String, String> addValidationMessages(
			Set<ConstraintViolation<AddRoleBean>> failures) 
	{
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<AddRoleBean> failure : failures) 
		{
			failureMessages.put(failure.getPropertyPath().toString(), 
								failure.getMessage());
		}
		return failureMessages;
	}
	
	private Map<String, String> editValidationMessages(
			Set<ConstraintViolation<EditRoleBean>> failures) 
	{
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<EditRoleBean> failure : failures) 
		{
			failureMessages.put(failure.getPropertyPath().toString(), 
								failure.getMessage());
		}
		return failureMessages;
	}	
}
