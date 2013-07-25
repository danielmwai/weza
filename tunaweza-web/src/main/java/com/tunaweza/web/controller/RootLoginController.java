package com.tunaweza.web.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.tunaweza.core.business.dao.exceptions.role.RoleDoesNotExistException;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.role.RoleService;
import com.tunaweza.core.business.service.user.UserService;
import com.tunaweza.core.business.settings.Settings;
import com.tunaweza.core.business.utils.CredentialsEncoder;
import static com.tunaweza.core.business.utils.SessionHelper.setSessionAttribDbConfig;
import com.tunaweza.web.servlet.superadmin.util.SuperAdminLoginUtil;
import com.tunaweza.web.spring.configuration.user.bean.DbConfigBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.LOGIN;
import static com.tunaweza.web.views.Views.ROOTPT;

/**
 * 
 * @author Jacktone Mony
 * @author David Gitonga
 * 
 */

@Controller
public class RootLoginController implements Settings, Views {

	protected final Log LOGGER = LogFactory.getLog(getClass());


	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	public final String ENTITY_MANAGER_FACTORY_NAME = "entityManagerFactoryName";

	private final String ENTITY_MANAGER_FACTORY = "entityManagerFactory";

	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;


	private static AuthenticationManager am = new RootAuthenticationManager();

	@RequestMapping(value = ROOTPT, method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("DAYS",100);

		return "superCloudAdminHome";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = ROOTPT)
	public String authaunticateRoot(HttpServletResponse res,
			HttpServletRequest req, Model model) {
		String emfName = "&entityManagerFactory";
		LOGGER.info("CHECKING ENTITY MANAGER2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LOGGER.debug("Using EntityManagerFactory '" + emfName
				+ "' for HibernateLongConversationFilter");
		HttpSession httpSession = ((HttpServletRequest) req)
				.getSession(true);
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(httpSession
						.getServletContext());
		this.entityManagerFactoryBean = wac.getBean(emfName,
				LocalContainerEntityManagerFactoryBean.class);

//		DataSourceSwitcherApi dataSourceSwitcherApi = new DataSourceSwitcherApiImpl();
		httpSession.invalidate();
//		DbConfigBean dbConfig = new DbConfigBean("localhost","jjteach_", "jjteach_","jjteach_");
//		 dataSourceSwitcherApi.newEntityManagerFactory(
//		  entityManagerFactoryBean,"jdbc:mysql://" + "localhost" +
//		  ":3306/" + "jjteach_", "jjteach_","jjteach_");
		  //httpSession.setAttribute("DBCONFIG", dbConfig);
//		  setSessionAttribDbConfig(req,dbConfig);
		String username = req.getParameter("j_username");
		String password = req.getParameter("j_password");

		System.out.print("username=" + username);
		System.out.print("password=" + password);
		try {
			
			Authentication request = new UsernamePasswordAuthenticationToken(
					username, password);
			Authentication result = am.authenticate(request);
			
			SecurityContextHolder.getContext().setAuthentication(result);
			
			User user = userService.getSuperUser("SCA", "Root", "SuperAdmin");
			//model.addAttribute("MENTORING", true);
			if (user == null) {
				User user1 = new User();
				user1.setFirstName("Root");
				user1.setLastName("SuperAdmin");
				user1.setSuperuser(true);
				user1.setUsername("SCA");
				user1.setPassword(CredentialsEncoder.generateMD5("password"));
	
				try {
						System.out.println("<<<<<<<<<<check role>>>>>>>>>>>>>> ");
						roleService.getRoleByName("ROLE_SUPERCLOUDADMIN");
						
				}catch (RoleDoesNotExistException ex) {
						Role role=new Role();
						role.setRoleName("ROLE_SUPERCLOUDADMIN");
						role.setDescription("Administrator role");
						System.out.println(">>>>>>>>>>>>>save role<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						roleService.addRole(role);
						ex.printStackTrace();
					}
				
				
				Role role = roleService.getRoleByName("ROLE_SUPERCLOUDADMIN");
				List<Role> roles = new ArrayList<Role>();
				roles.add(role);
				user1.setRoles(roles);

				user = userService.addUser(user1);


			}
			model.addAttribute("DAYS",100);
			model.addAttribute("USER","Root  SuperAdmin");
			model.addAttribute("ROLE", "ROLE_SUPERCLOUDADMIN");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<Authe");
			return "superCloudAdminHome";
			
		} catch (RoleDoesNotExistException re) {
			re.printStackTrace();
		} catch (AuthenticationException e) {
			System.out.println("Authentication failed: " + e.getMessage());
			model.addAttribute("login",true);
			model.addAttribute("msg","Authentication failed: " + e.getMessage());
		} catch (Exception ee) {
                ee.printStackTrace();
		}

		return LOGIN;
	}

}

class RootAuthenticationManager implements AuthenticationManager {
	List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

	List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);

	List<GrantedAuthority> getList() {
		authList.add(new GrantedAuthorityImpl("ROLE_SUPERCLOUDADMIN"));
		return authList;

	}

	// AUTHORITIES.add(new GrantedAuthorityImpl("ROLE_ADMIN"));

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {

		System.out.println("auth.getCredentials().toString()="
				+ auth.getCredentials().toString());

		if (SuperAdminLoginUtil.comparePassword(auth.getCredentials()
				.toString())) {
			return new UsernamePasswordAuthenticationToken(auth.getName(),
					auth.getCredentials(), getList());
		}
		throw new BadCredentialsException("Bad Credentials");
	}

}
