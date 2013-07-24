package com.tunaweza.web.controller;


import com.tunaweza.core.business.dao.datasource.DataSourceSwitcherApi;
import com.tunaweza.core.business.dao.datasource.DataSourceSwitcherApiImpl;
import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.license.CompanyLicenseDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorNotFoundException;
import com.tunaweza.core.business.dao.exceptions.student.StudentDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.company.CompanyService;
import com.tunaweza.core.business.service.company.settings.CompanySettings;
import com.tunaweza.core.business.service.license.CompanyLicenseService;
import com.tunaweza.core.business.service.mail.MailService;
import com.tunaweza.core.business.service.mentor.MentorService;
import com.tunaweza.core.business.service.module.ModuleService;
import com.tunaweza.core.business.service.student.StudentService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.core.business.service.user.UserService;
import com.tunaweza.core.business.settings.Settings;
import com.tunaweza.core.business.utils.Constants;
import static com.tunaweza.core.business.utils.Constants.EVAL_CURRENTLY_TESTING;
import static com.tunaweza.core.business.utils.Constants.LICENSE_WARNING_MESSAGE;
import com.tunaweza.core.business.utils.CredentialsEncoder;
import static com.tunaweza.core.business.utils.SessionHelper.evalCurrLoggedInAttribRole;
import static com.tunaweza.core.business.utils.SessionHelper.evalSessionAttribRole;
import static com.tunaweza.core.business.utils.SessionHelper.setSessionAttribDbConfig;
import com.tunaweza.web.datasource.JJteachDataSource;
import com.tunaweza.web.spring.configuration.user.bean.DbConfigBean;
import com.tunaweza.web.spring.configuration.user.bean.ForgotPasswordBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.CONTACT_COMPANY;
import static com.tunaweza.web.views.Views.EVAL;
import static com.tunaweza.web.views.Views.FORGOT_PASSWORD;
import static com.tunaweza.web.views.Views.FORGOT_PASSWORD_FORM;
import static com.tunaweza.web.views.Views.HOME;
import static com.tunaweza.web.views.Views.IMAGELOGO;
import static com.tunaweza.web.views.Views.INDEX;
import static com.tunaweza.web.views.Views.JJTEACH_DEMO;
import static com.tunaweza.web.views.Views.LOGIN;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author David Gitonga Home Controller
 * @author James Mungai
 */
@Controller
public class HomeController extends Constants implements Settings, Views {
	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Resource
	private ModuleService moduleService;

	@Autowired
	private UserService userService;

	@Resource
	private MailService mailService;

	@Autowired
	private Validator validator;

	@Autowired
	private UserCastService userCastService;

	@Autowired
	private MentorService mentorService;

	@Autowired
	private StudentService studentService;

	/*
	 * @Autowired private ValidateLicenseDao validateLicenseDao;
	 */
	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyLicenseService companyLicenseService;

	@Autowired
	private JJteachDataSource jjteachDataSource;

	public final String ENTITY_MANAGER_FACTORY_NAME = "entityManagerFactoryName";

	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;

	FilterConfig config1 = null;

	// ServletRequest request1;

	/* DataSourceSwitcherApi dataSourceSwitcherApi; */

	/**
	 * Show the home page
	 * 
	 * @return the home page view
	 */
	@RequestMapping(value = HOME, method = RequestMethod.GET)
	public String home(HttpServletRequest req) {
		jjteachDataSource.jjteachDatasource(req);
		return HOME;
	}

	/**
	 * Show the login page
	 * 
	 * @return the login page view
	 */
	@RequestMapping(value = LOGIN, method = RequestMethod.GET)
	public String login(HttpServletRequest req) {
		jjteachDataSource.jjteachDatasource(req);
		return LOGIN;
	}

	/**
	 * @author David Gitonga Read sql Blob image from database and convert it to
	 *         byte[] then write it using response.getOutputstream() method
	 * 
	 */
	@RequestMapping(value = IMAGELOGO, method = RequestMethod.GET)
	public void image(HttpServletResponse response) throws SQLException,
			IOException {

		byte[] imageData = null;
		Blob blobImageFromDB = null;

		User user = userCastService.getUser();
		Company userCompany = user.getUserCompany();
		if (userCompany != null) {
			CompanySettings companySettings = userCompany.getCompanySettings();

			if (companySettings != null) {

				blobImageFromDB = companySettings.getLogo_image();
				if (blobImageFromDB != null) {
					imageData = blobImageFromDB.getBytes(1,
							(int) blobImageFromDB.length());
					response.setContentType("image/png,image/jpeg,image/gif,image/ico,image/bmp");

					OutputStream out = response.getOutputStream();
					out.write(imageData);
					out.flush();
					out.close();
				} else {
					System.out.println("there is no image in DB yet");
				}

			} else
				System.out.println("there is no record");

		}

	}

	/**
	 * renders user specific home page depending on user's role on login
	 * 
	 * @return view for the specific user home page
	 * @throws Exception
	 */
	@RequestMapping(value = INDEX, method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request)
			throws Exception {
		LOGGER.info("INDEX METHOD START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		evalCurrLoggedInAttribRole(request, null);
		evalSessionAttribRole(request, null);
		setSessionAttribDbConfig(request, null);

		User user = userCastService.getUser();
		LOGGER.info("USER====== >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ user);
		Company userCompany = user.getUserCompany();

		LOGGER.info("USER'S COMPANY====== >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ user.getUserCompany());
		// Davii`
		// Company userCompany = companyService
		// .findCompany(userCastService.getUser()
		// .getUserCompany());
		/*
		 * long companyLicenceRemainingDays =0; long max_users =0;
		 * List<ValidateCompanyLicense> validatlicense =
		 * validateLicenseDao.getAllLicenses(); if(validatlicense==null ||
		 * validatlicense.size()==0){ companyLicenceRemainingDays =
		 * companyLicenseService.getLicenseRemainingDays( userCompany.getId());
		 * max_users =
		 * companyLicenseService.maxNumberOfActiveUsersForLicence(userCompany
		 * .getId());
		 * 
		 * }else{
		 * 
		 * companyLicenceRemainingDays =
		 * validatlicense.get(0).getRemaining_days(); max_users =
		 * validatlicense.get(0).getMax_users(); }
		 */
		// Davii
		if (userCompany != null) {

			String emfName = "&entityManagerFactory";
			LOGGER.info("CHECKING ENTITY MANAGER2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			LOGGER.debug("Using EntityManagerFactory '" + emfName
					+ "' for HibernateLongConversationFilter");
			HttpSession httpSession = ((HttpServletRequest) request)
					.getSession(true);
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(httpSession
							.getServletContext());
			this.entityManagerFactoryBean = wac.getBean(emfName,
					LocalContainerEntityManagerFactoryBean.class);

			DataSourceSwitcherApi dataSourceSwitcherApi = new DataSourceSwitcherApiImpl();

			LOGGER.info("BEFORE DATASOURCE SWITCH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

			String host = userCompany.getDbaseHost();
			String dbname = userCompany.getDbaseName();
			String username = userCompany.getDbUserName();
			String pass = userCompany.getDbPassword();

			DbConfigBean dbConfig = new DbConfigBean(host, dbname, username,
					pass);
			dataSourceSwitcherApi.newEntityManagerFactory(
					entityManagerFactoryBean, "jdbc:mysql://" + host + ":3306/"
							+ dbname, username, pass);

			// httpSession.setAttribute("DBCONFIG", dbConfig);
			setSessionAttribDbConfig(request, dbConfig);
			LOGGER.info("AFTER DATASOURCE SWITCH >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

			User instanceUser = userCastService.getUser();
			Company instanceUserCompany = companyService
					.findCompany(userCastService.getUser().getUserCompany());
			CompanySettings companySettings = instanceUserCompany
					.getCompanySettings();
			model.addAttribute("FACEBOOK", instanceUserCompany.getFacebook());
			model.addAttribute("TWITER", instanceUserCompany.getTwiter());
			model.addAttribute("LINKENIN", instanceUserCompany.getLinkenin());

		}

		User newuser = userCastService.getUser();
		LOGGER.info("USER====== >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ newuser);
		Company instanceuserCompany = companyService
				.findCompany(userCastService.getUser().getUserCompany());

		// httpSession.isNew();
		/*
		 * if (instanceuserCompany != null) { CompanySettings companySettings =
		 * instanceuserCompany.getCompanySettings();
		 */
		// =======
		// User instanceUser = userCastService.getUser();
		// Company instanceUserCompany = companyService
		// .findCompany(userCastService.getUser()
		// .getUserCompany());

		/*
		 * ValidateCompanyLicense validatecompanyLicense =
		 * validateLicenseDao.findLicenseById(1); >>>>>>> .r1483
		 * if(validatecompanyLicense==null){ ValidateCompanyLicense
		 * companyLicense = new ValidateCompanyLicense();
		 * companyLicense.setMax_users(max_users);
		 * companyLicense.setRemaining_days(companyLicenceRemainingDays);
		 * validateLicenseDao.saveLicense(companyLicense);
		 * 
		 * }else{ validatecompanyLicense.setMax_users(max_users);
		 * validatecompanyLicense
		 * .setRemaining_days(companyLicenceRemainingDays);
		 * validateLicenseDao.saveLicense(validatecompanyLicense); }
		 */

		if (instanceuserCompany != null) {
			CompanySettings companySettings = instanceuserCompany
					.getCompanySettings();
			if (companySettings != null) {

				setUserUI(model, newuser);

			} else {
				model.addAttribute("CONTENT_COLOR", "white");
				model.addAttribute("HEADER_COLOR", "#dddddd");
				model.addAttribute("MENU_COLOR", "black");
				model.addAttribute("FONT_COLOR", "orange");
				model.addAttribute("MENTORING", true);
			}

			Collection<? extends GrantedAuthority> authorities = SecurityContextHolder
					.getContext().getAuthentication().getAuthorities();
			for (GrantedAuthority x : authorities) {
				LOGGER.info("AUTHORITY:>>>>>>>>>>>>" + x.getAuthority());
			}

			for (GrantedAuthority auth : authorities) {
				String role = auth.getAuthority();

				List<Module> moduleList = moduleService.getAllModules();
				Collections.sort(moduleList);
				if (newuser.getFirstName().equalsIgnoreCase("Company:")) {
					model.addAttribute("USER", newuser.getUserCompany()
							.getName().toUpperCase());
				} else {
					model.addAttribute("USER", newuser.getFirstName() + " "
							+ newuser.getLastName());
				}
				model.addAttribute("ROLE", role);
				model.addAttribute("PRODUCTNAME",
						instanceuserCompany.getProductName());

				/**
				 * gets company email from db
				 */
				// List<Company> CompanyEmail =
				// companyService.getAllCompanies();
				// if ((CompanyEmail.size())!=0){
				// companymail= CompanyEmail.get(0);
				//
				// model.addAttribute("email", companymail.getEmail());
				// }
				// else{ public String getForgotPassword(Model model) {

				// model.addAttribute("email", "xyz.com");
				// }

				if (role.equals("ROLE_STUDENT")) {
					try {

						Company company1 = companyService
								.findCompany(userCastService.getUser()
										.getUserCompany());
						String email = company1.getEmail();

						long DAYS = companyLicenseService
								.getLicenseRemainingDays(email);

						if (DAYS <= 0) {
							model.addAttribute("DAYS", DAYS);
							return "secure/error/studentblocked";

						} else if (DAYS > 0 && DAYS <= 7) {

							model.addAttribute("DAYS", DAYS);

						} else {
							model.addAttribute("DAYS", DAYS);
						}

						Student student = studentService
								.getStudentByUser(newuser);

						int studentModuleSize = studentService
								.getStudentModules(student).size();
						studentService.setLastLoginDate(student);
						model.addAttribute("MODULEMAP",
								studentService.getStudentModules(student));
						model.addAttribute("MODULESIZE", studentModuleSize);
						model.addAttribute("PRODUCTNAME",
								instanceuserCompany.getProductName());
						model.addAttribute("MESSAGE",
								"You are not assigned any courseTemplate; please contact system Administrator");
						setUserUI(model, newuser);

					} catch (StudentDoesNotExistException e) {
						model.addAttribute("MODULESLIST", moduleList);
					}
					/*
					 * if(topic==null){ return "newStudentHome"; }
					 * model.addAttribute("topic", topic);
					 */
					return "studentHome";
				}

				else if (role.equals("ROLE_MENTOR")) {
					try {

						Company company1 = companyService
								.findCompany(userCastService.getUser()
										.getUserCompany());
						/*
						 * CompanySettings companySettings = company1
						 * .getCompanySettings(); companySettings
						 */
						boolean mentorRole = companySettings.getMentoring();
						String email = company1.getEmail();
						long DAYS = companyLicenseService
								.getLicenseRemainingDays(email);

						if (DAYS <= 0) {
							model.addAttribute("DAYS", DAYS);

							return "secure/error/studentblocked";

						} else if (DAYS > 0 && DAYS <= 7) {

							model.addAttribute("DAYS", DAYS);

						} else {
							model.addAttribute("DAYS", DAYS);
						}

						if (mentorRole == true) {
							Mentor mentor = mentorService
									.getMentorByUser(newuser);
							model.addAttribute("MENTORID", mentor.getId());
							model.addAttribute("PRODUCTNAME",
									instanceuserCompany.getProductName());
							setUserUI(model, newuser);
							return "mentorHome";
						} else {
							return "secure/error/mentordisabled";
						}
					} catch (MentorNotFoundException e) {
						return "secure/error/mentordisabled";
					}
					// return "mentorHome";

				} else if (role.equals("ROLE_ADMIN")) {
					Company company1 = companyService
							.findCompany(userCastService.getUser()
									.getUserCompany());
					long companyid = company1.getId();

					String email = company1.getEmail();
					long DAYS = companyLicenseService
							.getLicenseRemainingDays(email);
					if (DAYS <= 0) {
						model.addAttribute("DAYS", DAYS);
						companyLicenseService.senEmailToAllAdmin(role,
								companyid);

					} else if (DAYS > 0 && DAYS <= 7) {

						model.addAttribute("DAYS", DAYS);
						model.addAttribute("WARNING", LICENSE_WARNING_MESSAGE
								+ " " + DAYS + " Days time");
					} else {
						model.addAttribute("DAYS", DAYS);
					}
					if (instanceuserCompany != null) {
						companyService
								.setFirstAndLastLogins(instanceuserCompany);
					}

					return "adminHome";
				} else if (role.equals("ROLE_EVALUATOR")) {
					Company company1 = companyService
							.findCompany(userCastService.getUser()
									.getUserCompany());
					String email = company1.getEmail();

					long DAYS = companyLicenseService
							.getLicenseRemainingDays(email);
					if (DAYS <= 0) {
						model.addAttribute("DAYS", DAYS);
						companyLicenseService.senEmailToAllAdmin(role,
								company1.getId());

					} else if (DAYS > 0 && DAYS <= 7) {

						model.addAttribute("DAYS", DAYS);
						model.addAttribute("WARNING", LICENSE_WARNING_MESSAGE
								+ " " + DAYS + " Days time");
					} else {
						model.addAttribute("DAYS", DAYS);
					}
					evalSessionAttribRole(request, "ADMIN");
					// request.getSession(true).setAttribute("EVAL_FLG",
					// "TRUE");

					if (instanceuserCompany != null) {
						companyService
								.setFirstAndLastLogins(instanceuserCompany);
					}
					setUserUI(model, newuser);
					return "adminHome";
				} else if (role.equals("ROLE_CLOUDADMIN")) {
					long DAYS = 100;
					if (DAYS <= 0) {
						model.addAttribute("DAYS", DAYS);
					} else if (DAYS > 0 && DAYS <= 7) {

						model.addAttribute("DAYS", DAYS);
					} else {
						model.addAttribute("DAYS", DAYS);
					}
					return "cloudAdminHome";
				}
			}
		}
		return INDEX;
	}

	@RequestMapping(method = RequestMethod.GET, value = FORGOT_PASSWORD_FORM)
	public String getForgotPassword(Model model) {
		ForgotPasswordBean forgotPasswordBean = new ForgotPasswordBean();
		model.addAttribute("forgotPasswordBean", forgotPasswordBean);
		return FORGOT_PASSWORD_FORM;
	}

	@RequestMapping(value = EVAL, method = RequestMethod.GET)
	public String getAdminPage(Model model, HttpServletRequest request)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, CompanyDoesNotExistException,
			CompanyLicenseDoesNotExistException {

		User user = userCastService.getUser();
		Company userCompany = user.getUserCompany();
		model.addAttribute("FACEBOOK", userCompany.getFacebook());
		model.addAttribute("TWITER", userCompany.getTwiter());
		model.addAttribute("LINKENIN", userCompany.getLinkenin());

		String email = userCompany.getEmail();

		long DAYS = companyLicenseService.getLicenseRemainingDays(email);
		if (DAYS <= 0) {
			model.addAttribute("DAYS", DAYS);

		} else if (DAYS > 0 && DAYS <= 7) {

			model.addAttribute("DAYS", DAYS);
			model.addAttribute("WARNING", LICENSE_WARNING_MESSAGE + " " + DAYS
					+ " Days time");
		} else {
			model.addAttribute("DAYS", DAYS);
		}

		// setUserUI(model, user);

		model.addAttribute("USER",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("PRODUCTNAME", userCompany.getProductName());
		model.addAttribute("ROLE", "ROLE_EVALUATOR");

		if (userCompany != null) {
			CompanySettings companySettings = userCompany.getCompanySettings();
			if (companySettings != null) {
				model.addAttribute("CONTENT_COLOR",
						companySettings.getBg_color_code());
				model.addAttribute("HEADER_COLOR",
						companySettings.getHeader_color_code());
				model.addAttribute("MENU_COLOR",
						companySettings.getMenu_color_code());
				model.addAttribute("FONT_COLOR",
						companySettings.getFont_color_code());
				model.addAttribute("MENTORING", companySettings.getMentoring());
				model.addAttribute("COMPANY_SETTINGS_EXIST", true);
			} else {
				model.addAttribute("CONTENT_COLOR", "white");
				model.addAttribute("HEADER_COLOR", "#dddddd");
				model.addAttribute("MENU_COLOR", "black");
				model.addAttribute("FONT_COLOR", "orange");
				model.addAttribute("MENTORING", true);
			}
		}
		evalSessionAttribRole(request, "ADMIN");
		request.getSession(true).setAttribute(EVAL_CURRENTLY_TESTING, "ADMIN");

		return "adminHome";
	}

	@RequestMapping(value = "/eval/student", method = RequestMethod.GET)
	public String getStudentPage(Model model, HttpServletRequest request)
			throws CompanyDoesNotExistException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			CompanyLicenseDoesNotExistException {

		User user = userCastService.getUser();
		Company userCompany = user.getUserCompany();

		model.addAttribute("FACEBOOK", userCompany.getFacebook());
		model.addAttribute("TWITER", userCompany.getTwiter());
		model.addAttribute("LINKENIN", userCompany.getLinkenin());

		// setUserUI(model, user);

		Student student = null;
		try {

			String email = userCompany.getEmail();

			long DAYS = companyLicenseService.getLicenseRemainingDays(email);

			if (DAYS <= 0) {
				model.addAttribute("DAYS", DAYS);

			} else if (DAYS > 0 && DAYS <= 7) {

				model.addAttribute("DAYS", DAYS);

			} else {
				model.addAttribute("DAYS", DAYS);
			}
			student = studentService.getStudentByUser(user);

			// Enables student modules according to their prerequisites
			// studentService.enableStudentModules(student);

			int studentModuleSize = studentService.getStudentModules(student)
					.size();
			model.addAttribute("MODULEMAP",
					studentService.getStudentModules(student));
			model.addAttribute("MODULESIZE", studentModuleSize);
			model.addAttribute("PRODUCTNAME", userCompany.getProductName());
			if (userCompany != null) {
				CompanySettings companySettings = userCompany
						.getCompanySettings();
				if (companySettings != null) {
					model.addAttribute("CONTENT_COLOR",
							companySettings.getBg_color_code());
					model.addAttribute("HEADER_COLOR",
							companySettings.getHeader_color_code());
					model.addAttribute("MENU_COLOR",
							companySettings.getMenu_color_code());
					model.addAttribute("FONT_COLOR",
							companySettings.getFont_color_code());
					model.addAttribute("MENTORING",
							companySettings.getMentoring());
					model.addAttribute("COMPANY_SETTINGS_EXIST", true);
				} else {
					model.addAttribute("CONTENT_COLOR", "white");
					model.addAttribute("HEADER_COLOR", "#dddddd");
					model.addAttribute("MENU_COLOR", "black");
					model.addAttribute("FONT_COLOR", "orange");
					model.addAttribute("MENTORING", true);
				}
			}
			model.addAttribute("MESSAGE",
					"You are not assigned any courseTemplate; please contact system Administrator");

			studentService.setLastLoginDate(student);
			// evalSessionAttribRole(request, "STUDENT");
			request.getSession(true).setAttribute(EVAL_CURRENTLY_TESTING,
					"STUDENT");
		} catch (StudentDoesNotExistException e) {
			e.printStackTrace();
		}
		evalSessionAttribRole(request, "STUDENT");
		return "studentHome";
	}

	@RequestMapping(value = "/eval/mentor")
	public String getMentorPage(Model model, HttpServletRequest request)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, CompanyDoesNotExistException,
			CompanyLicenseDoesNotExistException {

		User user = userCastService.getUser();
		Company userCompany = user.getUserCompany();
		model.addAttribute("FACEBOOK", userCompany.getFacebook());
		model.addAttribute("TWITER", userCompany.getTwiter());
		model.addAttribute("LINKENIN", userCompany.getLinkenin());
		// setUserUI(model, user);

		Mentor mentor = null;
		try {
			String email = userCompany.getEmail();

			long DAYS = companyLicenseService.getLicenseRemainingDays(email);

			if (DAYS <= 0) {
				model.addAttribute("DAYS", DAYS);

			} else if (DAYS > 0 && DAYS <= 7) {

				model.addAttribute("DAYS", DAYS);

			} else {
				model.addAttribute("DAYS", DAYS);
			}
			mentor = mentorService.getMentorByUser(user);
		} catch (MentorNotFoundException me) {
			me.printStackTrace();
		}
		model.addAttribute("PRODUCTNAME", userCompany.getProductName());
		model.addAttribute("MENTORID", mentor.getId());

		setUserUI(model, user);

		Company company = user.getUserCompany();
		if (company != null) {
			CompanySettings companySettings = company.getCompanySettings();
			if (companySettings != null) {
				model.addAttribute("CONTENT_COLOR",
						companySettings.getBg_color_code());
				model.addAttribute("HEADER_COLOR",
						companySettings.getHeader_color_code());
				model.addAttribute("MENU_COLOR",
						companySettings.getMenu_color_code());
				model.addAttribute("FONT_COLOR",
						companySettings.getFont_color_code());
				model.addAttribute("MENTORING", companySettings.getMentoring());
				model.addAttribute("COMPANY_SETTINGS_EXIST", true);
			} else {
				model.addAttribute("CONTENT_COLOR", "white");
				model.addAttribute("HEADER_COLOR", "#dddddd");
				model.addAttribute("MENU_COLOR", "black");
				model.addAttribute("FONT_COLOR", "orange");
				model.addAttribute("MENTORING", true);
			}
		}

		request.getSession(true).setAttribute(EVAL_CURRENTLY_TESTING, "MENTOR");
		evalSessionAttribRole(request, "MENTOR");
		return "mentorHome";
	}

	@RequestMapping(method = RequestMethod.POST, value = FORGOT_PASSWORD)
	public @ResponseBody
	Map<String, ? extends Object> sendPassword(
			@RequestBody ForgotPasswordBean forgotPasswordBean) {
		Set<ConstraintViolation<ForgotPasswordBean>> failures = validator
				.validate(forgotPasswordBean);
		if (!failures.isEmpty()) {
			return Collections.singletonMap("u", "Fill in required fields");
		} else {
			String email = forgotPasswordBean.getEmail();

			User user;
			try {
				user = userService.getUserByEmail(email);
			} catch (UserDoesNotExistException e) {
				return Collections.singletonMap("u", email+" does not exist");
			}

			String newPassword = userService.generatePassword();
			String encryptPassword = CredentialsEncoder
					.generateMD5(newPassword);

			user.setPassword(encryptPassword);
			try {
				userService.updateUser(user);
			} catch (UserDoesNotExistException e) {
				LOGGER.debug(e);
			}
			StringBuilder builder = new StringBuilder();
			builder.append("Dear " + user.getFirstName() + ",<br/>");
			builder.append("<p></p>");
			builder.append("<p> A new password has been created for your JJ Teach Account. Below are your new login details:</p> ");
			builder.append("<strong>Email: </strong>" + email + "<br/>");
			builder.append("<strong>password: </strong>" + newPassword);
			builder.append("</ul></p>");
			builder.append("<p>Once you login, you can change you password by clicking on the 'My profile' icon(it's on the top navigation panel). Then Click on the 'Edit password'.</p>");
			builder.append("<p></p>");
			builder.append("<p>Kind Regards,</p>");
			builder.append("<p></p>");
			builder.append("<p>JJ People Team</p>");

			String message = builder.toString();
			LOGGER.info("================");
			try {
				LOGGER.info("sending mail");
				mailService.sendMail(message, email);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.info(e.getMessage());
				return Collections.singletonMap("u", "Email was not sent");
			}
			return Collections.singletonMap("u",
					"New password sent to your email");
		}

	}

	/**
	 * @author Daniel Nyanumba Method to set a users look and feel as set by the
	 *         administrator
	 * @param companySettings
	 *            Specific company setting for a company
	 * @param model
	 *            Model to hold the settings
	 */
	public void setUserUI(Model model, User user) {
		Company company = user.getUserCompany();
		if (company != null) {
			CompanySettings companySettings = company.getCompanySettings();
			if (companySettings != null) {
				model.addAttribute("CONTENT_COLOR",
						companySettings.getBg_color_code());
				model.addAttribute("HEADER_COLOR",
						companySettings.getHeader_color_code());
				model.addAttribute("MENU_COLOR",
						companySettings.getMenu_color_code());
				model.addAttribute("FONT_COLOR",
						companySettings.getFont_color_code());
				model.addAttribute("MENTORING", companySettings.getMentoring());
				model.addAttribute("COMPANY_SETTINGS_EXIST", true);
			} else {
				model.addAttribute("CONTENT_COLOR", "white");
				model.addAttribute("HEADER_COLOR", "#dddddd");
				model.addAttribute("MENU_COLOR", "black");
				model.addAttribute("FONT_COLOR", "orange");
				model.addAttribute("MENTORING", true);
			}
		}
	}

	/*
	 * @RequestMapping(value = PAYMENT_VIEW, method = RequestMethod.GET) public
	 * String pay(Model model,HttpServletRequest request) {
	 * evalCurrLoggedInAttribRole(request, null); evalSessionAttribRole(request,
	 * null); HttpSession httpSession = ((HttpServletRequest) request)
	 * .getSession();
	 * 
	 * if(httpSession!=null){ httpSession.invalidate(); } String remote =
	 * request.getRemoteUser(); JOptionPane jp = new JOptionPane();
	 * jp.showMessageDialog(null, remote);
	 * model.addAttribute("PAY","loged out"); return PAYMENT_VIEW; }
	 */

	/**
	 * Returns a page with a contact form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = CONTACT_COMPANY)
	public String contactCompany(Model model, HttpServletRequest request) {

		jjteachDataSource.jjteachDatasource(request);
		return CONTACT_COMPANY;
	}

	/**
	 * Returns a page with the jjteach demo video
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = JJTEACH_DEMO)
	public String jjteachDemo(Model model, HttpServletRequest request) {

		jjteachDataSource.jjteachDatasource(request);
		return JJTEACH_DEMO;
	}

}
