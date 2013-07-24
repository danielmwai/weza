package com.tunaweza.web.controller.company;

import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.dao.licence.LicenseDao;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.licence.CompanyLicense;
import com.tunaweza.core.business.model.licence.License;
import com.tunaweza.core.business.model.user.Location;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.company.CompanyService;
import com.tunaweza.core.business.service.company.settings.CompanySettings;
import com.tunaweza.core.business.service.dbswitcher.DbSwitcherHelper;
import com.tunaweza.core.business.service.ipn.IPNService;
import com.tunaweza.core.business.service.location.LocationService;
import com.tunaweza.core.business.service.mail.MailService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.core.business.service.user.UserService;
import com.tunaweza.core.business.utils.PropertiesUtil;
import com.tunaweza.web.spring.configuration.company.bean.AddCompanyBean;
import com.tunaweza.web.spring.configuration.company.bean.EditCompanyBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.CONTENT_TYPE;
import static com.tunaweza.web.views.Views.EDIT_REGISTER_COMPANY;
import static com.tunaweza.web.views.Views.EMAIL_CHECK;
import static com.tunaweza.web.views.Views.FREE_TRIAL;
import static com.tunaweza.web.views.Views.MIME_APP_URLENC;
import static com.tunaweza.web.views.Views.PARAM_NAME_CMD;
import static com.tunaweza.web.views.Views.PARAM_VAL_CMD;
import static com.tunaweza.web.views.Views.PAYMENT_PLAN;
import static com.tunaweza.web.views.Views.PAY_PAL_DEBUG;
import static com.tunaweza.web.views.Views.REGISTER_COMPANY;
import static com.tunaweza.web.views.Views.REGISTER_COMPANY_REPLY;
import static com.tunaweza.web.views.Views.RESP_VERIFIED;
import static com.tunaweza.web.views.Views.VERIFY_PAYMENT;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;

import org.apache.http.message.BasicNameValuePair;
import org.hibernate.engine.jdbc.NonContextualLobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
@RequestMapping(Views.COMPANY)
public class CompanyController implements Views {
	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private Validator validator;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserCastService userCastService;

	@Autowired
	private IPNService ipnService;

	@Resource
	private MailService mailService;

	@Autowired
	private LicenseDao licenseDao;

	@Autowired
	private DbSwitcherHelper dbSwitcherHelper;

	/**
	 * Method to render the Company Registration Form
	 */
	@RequestMapping(method = RequestMethod.GET, value = REGISTER_COMPANY)
	public String addCompanyForm(Model model, HttpServletRequest request) {
		List<Location> locationList = locationService.getAllLocations();
		model.addAttribute("LOCATIONLIST", locationList);
		model.addAttribute("addCompanyBean", new AddCompanyBean());
		LOGGER.info("REGISTER COMPANY");
		return REGISTER_COMPANY;
	}

	@RequestMapping(method = RequestMethod.GET, value = PAYMENT_PLAN)
	public String paymentPlan(Model model, HttpServletRequest request) {

		// model.addAttribute("paymentPlanBean", new PaymentPlanBean());
		return PAYMENT_PLAN;
	}

	@RequestMapping(method = RequestMethod.POST, value = VERIFY_PAYMENT)
	public String processIPN(HttpServletRequest request, Model model) {

		// This should be changed once jjcloud goes live
		String paypalUrl = PAY_PAL_DEBUG;

		LOGGER.debug("POST Confirm");

		// Create client for Http communication
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams clientParams = httpClient.getParams();
                
		HttpConnectionParams.setConnectionTimeout(clientParams, 40000);
		HttpConnectionParams.setSoTimeout(clientParams, 40000);

		HttpPost httppost = new HttpPost(paypalUrl);
		httppost.setHeader(CONTENT_TYPE, MIME_APP_URLENC);

		try {
			// Store Payment info for passing to processing service
			Map<String, String> params = new HashMap<String, String>();

			// Use name/value pair for building the encoded response string
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

			// Append the required command
			nameValuePairs.add(new BasicNameValuePair(PARAM_NAME_CMD,
					PARAM_VAL_CMD));

			// Process the parameters
			Enumeration<String> names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String param = names.nextElement();
				String value = request.getParameter(param);

				nameValuePairs.add(new BasicNameValuePair(param, value));
				params.put(param, value);
				LOGGER.debug(param + "=" + value);
			}

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			if (verifyResponse(httpClient.execute(httppost))) {
				String paymentStatus = ipnService
						.processPaymentNotification(params);

				if (paymentStatus == "SUCCESS") {
					return "paymentsuccess";
				}
			} else {
				return "paymentfailure";
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "paymentfailure";
	}

	private boolean verifyResponse(HttpResponse response)
			throws IllegalStateException, IOException {

		InputStream is = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String responseText = reader.readLine();
		is.close();

		LOGGER.debug("RESPONSE : " + responseText);

		return responseText.equals(RESP_VERIFIED);

	}

	@RequestMapping(method = RequestMethod.POST, value = REGISTER_COMPANY)
	public String /*
				 * @ResponseBody Map<String, ? extends Object>
				 */addCompany(AddCompanyBean addCompanyBean, Model model,
			HttpServletRequest request) throws Exception {

		Set<ConstraintViolation<AddCompanyBean>> failures = validator
				.validate(addCompanyBean);

		if (!failures.isEmpty()) {
			addValidationMessages(failures);
			model.addAttribute("Message", "Please fill in required fields.");
			return REGISTER_COMPANY_REPLY; 
		} else {

			// This will be used to set the location.
			// Its value depends on whether the user used the drop down country
			// list
			// or chose 'Other' and entered a location
			String chosenLocation;
			if (addCompanyBean.getOtherLocation() == "Other") {
				chosenLocation = addCompanyBean.getLocation();
			} else {
				chosenLocation = addCompanyBean.getOtherLocation();
			}
			// No errors encountered-add the company.
			if (addCompanyBean.getPassword().equals(
					addCompanyBean.getRepassword())) {

				try {
					User temp = userService.getUserByUsername(addCompanyBean
							.getEmail());

					Collections.emptyList();
					model.addAttribute("Message",
							"The user entered already exists! Enter another E-mail address.");
					return REGISTER_COMPANY_REPLY;

				} catch (Exception e) {

				}
				try {

					Company company = companyService
							.findCompanyById(addCompanyBean.getId());
					model.addAttribute("Message",
							"The instance entered already exists! Enter another Instance.");
					return REGISTER_COMPANY_REPLY;
				} catch (Exception e) {
				}

				if (!addCompanyBean.getLogo_image().isEmpty()) {
					if (addCompanyBean.getLogo_image().getSize() == 0) {
						model.addAttribute("Message",
								"You must upload an image");
						return REGISTER_COMPANY_REPLY;

					}

					LOGGER.info("\n SOLUTIONCONTROLLER: uploaded file mimetype -----"
							+ addCompanyBean.getLogo_image().getContentType());
					if (!PropertiesUtil.isfileSupported("image", addCompanyBean
							.getLogo_image().getContentType())) {
						model.addAttribute("Message",
								"Upload Only Supported Files ");
						return REGISTER_COMPANY_REPLY;

					}
				}

				// if (addCompanyBean.getOtherLocation() == "") {
				// model.addAttribute("Message", "Select a location");
				// return REGISTER_COMPANY_REPLY;
				// }
				Company company = new Company();
				/**
				 * validates the telephone number format
				 */

				if (addCompanyBean.getSecondline() != null) {
					company.setSecondline(addCompanyBean.getSecondline());
				} else {
					company.setSecondline("N/A");
				}
				company.setEmail(addCompanyBean.getEmail());
				company.setLocation(chosenLocation);

				String companyName = addCompanyBean.getName();
				company.setName(companyName);
				company.setProductName(addCompanyBean.getProductName());
				company.setWebsite(addCompanyBean.getWebsite());
				company.setAddress(addCompanyBean.getAddress());
				company.setFirstline(addCompanyBean.getFirstline());
				company.setDbaseHost("localhost");
				company.setDbPassword(companyService.generatePassword());
				company.setDbaseName(genUserDBName(addCompanyBean.getEmail()));
				company.setDbUserName(genUserDBName(addCompanyBean.getEmail()));

				String urlType = addCompanyBean.getUrlType();
				String companyURL = "";

				if (urlType.equalsIgnoreCase("private")) {
					companyURL = addCompanyBean.getUrl();

				}

				else {
					companyURL = createCompanyUrl(companyName);
				}

				company.setUrlType(urlType);
				company.setUrl(companyURL);
				company.setLicenseType(addCompanyBean.getLicenseType());

				CompanySettings companySettings = new CompanySettings();
				companySettings.setBg_color_code("#"
						+ addCompanyBean.getBg_color_code());
				companySettings.setHeader_color_code("#"
						+ addCompanyBean.getHeader_color_code());
				companySettings.setMenu_color_code("#"
						+ addCompanyBean.getMenu_color_code());
				companySettings.setFont_color_code("#"
						+ addCompanyBean.getFont_color_code());

				if (!addCompanyBean.getLogo_image().isEmpty()) {
					companySettings
							.setLogo_image(NonContextualLobCreator.INSTANCE
									.createBlob(addCompanyBean.getLogo_image()
											.getBytes()));
				} else {

					companySettings.setLogo_image(companySettings
							.getLogo_image());
				}
				companySettings.setCompany(company);
				company.setCompanySettings(companySettings);

				List<License> license = licenseDao.getAllLicenses();
				CompanyLicense companyLicense = new CompanyLicense();
				companyService.addCompany(company);

				companyLicense.setCompany(company);
				try {
					companyLicense.setLicense_id(license.get(0).getId());
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				companyService
						.setCompanyRegistrationAndExpiryDate(companyLicense);

				LOGGER.info("The cloud Instance is now creating the Company User>>>>>>>>>>>>>>>>>>>>>>>>>>::::1");
				companyService.createCompanyUser(addCompanyBean.getEmail(),
						addCompanyBean.getPassword(), chosenLocation,
						addCompanyBean.getName(), company);
				LOGGER.info("The cloud Instance is now creating the database>>>>>>>>>>>>>>>>>>>>>>>>>>:::::::2");
				companyService.createDatabaseAndPopulate(
						addCompanyBean.getId(), company.getDbaseName(),
						company.getDbPassword(), company.getDbaseHost(),
						company.getDbUserName());

				LOGGER.info("The cloud Instance has now created the database>>>>>>>>>>>>>>>>>>>>>>>>>>:::::::3");

				// generates the email to be sent to the user
				StringBuilder builder = new StringBuilder();
				builder.append("Dear " + addCompanyBean.getEmail() + ",<br/>");
				builder.append("<p></p>");
				builder.append("<p> Welcome to JJpeople e-learning program.</p> ");
				builder.append("<p> Your details are as follows:</p> ");
				builder.append("<strong>Email: </strong>"
						+ addCompanyBean.getEmail() + "<br/>");
				builder.append("<strong>Password: </strong>"
						+ addCompanyBean.getPassword());// encryptCompPassword);
				builder.append("</ul></p>");
				builder.append("<p>Once you login, you can change you password by clicking on the 'My profile' icon(it's on the top navigation panel). Then Click on the 'Edit password'.</p>");
				builder.append("<p></p>");
				builder.append("<p>Kind Regards,</p>");
				builder.append("<p></p>");
				builder.append("<p>JJ People Team</p>");

				String message = builder.toString();

				mailService.sendMail(message, addCompanyBean.getEmail());

				Company comp = companyService.getAllCompanies().get(0);
				String passw = comp.getDbPassword();
				int version = comp.getVersion();

				mailService.sendMail(message, addCompanyBean.getEmail());

				Connection conn = null;
				String url = "jdbc:mysql://localhost:3306/";
				String dbName = company.getDbaseName();
				String driver = "com.mysql.jdbc.Driver";
				String userName = company.getDbUserName();
				String password = company.getDbPassword();
				String localhost = "localhost";
				String description = "company db";
				String first_name = "Company:";
				int flag = 0;
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				int mentoring;
				/*
				 * if (addCompanyBean.getMentoring() == true) { mentoring = 1; }
				 * else { mentoring = 0; }
				 */
				if (companySettings.getMentoring() == true) {
					mentoring = 1;
				} else {
					mentoring = 0;
				}
				String firstlogin = formatter.format(currentDate.getTime());
				try {
					Class.forName(driver).newInstance();
					conn = DriverManager.getConnection(url + dbName, userName,
							password);

					Statement st = conn.createStatement();
					int rows = st
							.executeUpdate("INSERT INTO company_details(VERSION,address,database_username,database_password,"
									+ "database_host,database_name,description,email,firstlogin_date,firstline,company_enabled,location,name,"
									+ "productName,secondline,website) VALUES("
									+ version
									+ ",'"
									+ addCompanyBean.getAddress()
									+ "','"
									+ userName
									+ "','"
									+ password
									+ "'"
									+ ",'"
									+ localhost
									+ "','"
									+ dbName
									+ "','"
									+ description
									+ "','"
									+ addCompanyBean.getEmail()
									+ "','"
									+ firstlogin
									+ "'"
									+ ",'"
									+ addCompanyBean.getFirstline()
									+ "',"
									+ flag
									+ ","
									+ "'"
									+ chosenLocation
									+ "','"
									+ addCompanyBean.getName()
									+ "'"
									+ ",'"
									+ addCompanyBean.getProductName()
									+ "','"
									+ addCompanyBean.getSecondline()
									+ "'"
									+ ",'" + addCompanyBean.getWebsite() + "')");
					st.close();

					PreparedStatement pstmt = conn
							.prepareStatement("INSERT INTO company_settings VALUES (?,?,?,?,?,?,?,?,?,?,?)");
					pstmt.setInt(1, 1);
					pstmt.setInt(2, version);
					pstmt.setString(3, "#" + addCompanyBean.getBg_color_code());
					pstmt.setString(4,
							"#" + addCompanyBean.getFont_color_code());
					pstmt.setString(5,
							"#" + addCompanyBean.getHeader_color_code());
					pstmt.setString(6,
							"#" + addCompanyBean.getHeader_links_color_code());
					pstmt.setBlob(7, NonContextualLobCreator.INSTANCE
							.createBlob(addCompanyBean.getLogo_image()
									.getBytes()));

					pstmt.setInt(8, mentoring);
					pstmt.setString(9,
							"#" + addCompanyBean.getMenu_color_code());
					pstmt.setString(10,
							"#" + addCompanyBean.getNavigation_hover_color());
					pstmt.setInt(11, 1);
					pstmt.executeUpdate();
					pstmt.close();

					Statement st2 = conn.createStatement();
					st2.execute("insert into roles(VERSION,role_description,number,role_name)"
							+ " VALUES("
							+ version
							+ ",'role admin',1,'ROLE_ADMIN'),("
							+ version
							+ ",'role mentor',2,'ROLE_MENTOR'),("
							+ version
							+ ",'role evaluator',3,'ROLE_EVALUATOR'),"
							+ "("
							+ version + ",'role student',4,'ROLE_STUDENT')");

					st2.close();

					Statement st6 = conn.createStatement();
					st6.executeUpdate("insert into location(VERSION,location_description,location_name)"
							+ " VALUES("
							+ version
							+ ",'company location','"
							+ chosenLocation + "')");

					st6.close();

					Statement st3 = conn.createStatement();
					st3.executeUpdate("INSERT INTO users"
							+ "(VERSION,email,enabled,first_name,last_name,password,superuser,username,id_location,id_company)"
							+ " VALUES("
							+ version
							+ ","
							+ "'"
							+ addCompanyBean.getEmail()
							+ "'"
							+ ","
							+ 1
							+ ","
							+ "'"
							+ first_name
							+ "'"
							+ ","
							+ "'"
							+ companyName(addCompanyBean.getEmail()
									.toLowerCase())
							+ "'"
							+ ""
							+ ","
							+ "'"
							+ password
							+ "'"
							+ ","
							+ ""
							+ 0
							+ ","
							+ "'"
							+ addCompanyBean.getEmail()
							+ "'"
							+ ","
							+ 1
							+ ","
							+ 1 + ")");

					st3.close();

					Statement st4 = conn.createStatement();
					st4.executeUpdate("INSERT INTO authorities(username,authority) VALUES("
							+ "'"
							+ addCompanyBean.getEmail()
							+ "'"
							+ ",'ROLE_EVALUATOR')");

					st4.close();

					Statement st5 = conn.createStatement();
					st5.executeUpdate("INSERT INTO mentor(VERSION,id_user) VALUES("
							+ version + ",1)");

					st5.close();

					Statement st7 = conn.createStatement();
					st7.executeUpdate("INSERT INTO student(VERSION,registration_no,id_user) VALUES("
							+ version + ",1,1)");

					st7.close();

					conn.close();

					if (rows == 0) {

						System.out.println("Don't add any row!");
					} else {
						System.out.println(rows + " row(s)affected.");
						conn.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				  /*URL jjteachurl = new URL(request.getScheme(),
				  request.getServerName(), request
				  .getServerPort(),"/jjteach"); String installUrl =
				  jjteachurl.toString().concat("/install.jsp"); final WebClient
				  browser = new WebClient(); final HtmlPage installForm =
				  browser.getPage(installUrl);
				  
				  while(installForm !=null){ HtmlSelect language =
				  (HtmlSelect)installForm.getElementByName("language");
				  HtmlOption en_US = language.getOptionByValue("en_US");
				  language.setSelectedAttribute(en_US, true);
				  
				  HtmlSelect database =
				  (HtmlSelect)installForm.getElementByName("database");
				  HtmlOption MySql = database.getOptionByValue("mysql");
				  database.setSelectedAttribute(MySql, true);
				  
				  HtmlSelect installType =
				  (HtmlSelect)installForm.getElementByName("install_type");
				  HtmlOption freshInstall = installType.getOptionByValue("0");
				  installType.setSelectedAttribute(freshInstall, true);
				  
				 HtmlSelect connectionType =
				 (HtmlSelect)installForm.getElementByName
				 ("db_connection_type"); HtmlOption jdbc =
				  connectionType.getOptionByValue("JDBC");
				  connectionType.setSelectedAttribute(jdbc, true);
				  
				  
				  HtmlElement dbport = installForm.getElementByName("dbport");
				  dbport.type("3306");
				  
				  HtmlElement dbdatasource =
				  installForm.getElementByName("dbdatasource");
				  dbdatasource.type("");
				  
				  HtmlElement dbname = installForm.getElementByName("dbname");
				  dbname.type(company.getDbaseName());
				  
				  HtmlElement dbuser = installForm.getElementByName("dbuser");
				  dbuser.type(company.getDbUserName());
				  
				  HtmlElement dbpasswd =
				  installForm.getElementByName("dbpasswd");
				  dbpasswd.type(company.getDbPassword());
				  
				  HtmlElement dbpasswd_confirm =
				  installForm.getElementByName("dbpasswd_confirm");
				  dbpasswd_confirm.type(company.getDbPassword());
				  
				  HtmlSelect dbencoding =
				  (HtmlSelect)installForm.getElementByName("dbencoding");
				  HtmlOption utf_8 = dbencoding.getOptionByValue("utf-8");
				  dbencoding.setSelectedAttribute(utf_8, true);
				  
				  HtmlSelect use_pool =
				  (HtmlSelect)installForm.getElementByName("use_pool");
				  HtmlOption useOptionYes = use_pool.getOptionByValue("yes");
				  use_pool.setSelectedAttribute(useOptionYes, true);
				 
				  HtmlElement forum_link =
				  installForm.getElementByName("forum_link"); String forumUrl =
				  jjteachurl.toString().concat("/forums/list.page");
				  forum_link.type(forumUrl);
				  
				 HtmlElement site_link =
				  installForm.getElementByName("site_link"); String
				  jjteachLoginUrl = jjteachurl.toString().concat("/login.htm");
				  site_link.type(jjteachLoginUrl);
				  
				  HtmlElement admin_pass1 =
				  installForm.getElementByName("admin_pass1");
				  admin_pass1.type(addCompanyBean.getEmail());
				  
				  HtmlElement admin_pass2 =
				  installForm.getElementByName("admin_pass2");
				  admin_pass2.type(addCompanyBean.getEmail());
				  
				  HtmlForm actualForm = installForm.getFormByName("install");
				  HtmlPage page2 =
				  actualForm.getInputByValue("Next Step").click();
				  
				  page2.getElementById("begininstall").click();
				  
				  }*/
				 
				model.addAttribute("SAVED", true);
				model.addAttribute("Message", "Saved");
				return REGISTER_COMPANY_REPLY;
			} else {
				model.addAttribute("Message", "Passwords do not match!");
				return REGISTER_COMPANY_REPLY;
			}
		}

	}

	

	@SuppressWarnings("unused")
	public String genUserDBName(String companyEmail)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		// Company company = companyService.
		Connection conn = dbSwitcherHelper.dbSwitcher("jjteach_", "jjteach_",
				"jjteach_");
		String dbname = null;
		try {
			Statement st1 = conn.createStatement();
			ResultSet rs = st1
					.executeQuery("SELECT database_name AS db FROM company_details where id = (SELECT MAX(id) FROM company_details)");

			while (rs.next()) {
				dbname = rs.getString("db");
			}
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
		int atIndex = companyEmail.indexOf("@");
		String gen1 = "";
		if (atIndex < 3) {
			gen1 = companyEmail.substring(0, atIndex);
		} else {
			gen1 = companyEmail.substring(0, 3);
		}

		if (gen1.length() < 3) {
			int x = 3 - gen1.length();
			for (int i = 0; i < x; i++) {
				gen1 = gen1 + "0";
			}

		}
		long suffix = 0;
		if (dbname != null) {
			String append = dbname.substring(3);

			suffix = isLong(append) + 1;

		} else {
			suffix = 2;
		}
		return gen1 + suffix;
	}

	/**
	 * 
	 * @param extNum
	 * @return
	 */
	public Long isLong(String extNum) {
		long a = 0;
		try {

			a = Long.parseLong(extNum);
			return a;
		} catch (Exception e) {
			return (long) 0;
		}
	}

	private String companyName(String email) {
		String name = email.replace("@", "");
		int index = name.indexOf(".");
		String newname = name.substring(0, index);
		return newname;
	}

	private Map<String, String> addValidationMessages(
			Set<ConstraintViolation<AddCompanyBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<AddCompanyBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	/**
	 * Method to render the Company Registration Form
	 */
	@RequestMapping(method = RequestMethod.GET, value = EDIT_REGISTER_COMPANY)
	public String editCompanyForm(Model model, HttpServletRequest request) {

		List<Location> locationList = locationService.getAllLocations();
		model.addAttribute("LOCATIONLIST", locationList);

		EditCompanyBean editCompanyBean = new EditCompanyBean();
		// User user = userCastService.getUser();
		Company company = null;
		try {
			company = companyService.findCompanyById(userCastService.getUser()
					.getId());
			/* company = companyService.findCompanyById(1l); */
		} catch (CompanyDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Company company = user.getUserCompany();
		;
		if (company != null) {
			// company
			editCompanyBean.setCompanyId(String.valueOf(company.getId()));
			editCompanyBean.setDescription(company.getDescription());
			editCompanyBean.setLocation(company.getLocation());
			editCompanyBean.setName(company.getName());
			editCompanyBean.setAddress(company.getAddress());
			editCompanyBean.setFirstline(company.getFirstline());
			if (company.getSecondline() != null
					|| company.getSecondline() != "") {
				editCompanyBean.setSecondline(company.getSecondline());
			}
			editCompanyBean.setProductName(company.getProductName());
			editCompanyBean.setFacebook(company.getFacebook());
			editCompanyBean.setLinkenin(company.getLinkenin());
			editCompanyBean.setTwiter(company.getTwiter());

			// editCompanyBean.setEmail(company.getEmail());
			// editCompanyBean.setPassword(password);
			editCompanyBean.setWebsite(company.getWebsite());
		} else {
			editCompanyBean.setCompanyId("");
			editCompanyBean.setName("N/A");
			editCompanyBean.setFirstline("N/A");
			editCompanyBean.setSecondline("N/A");
			editCompanyBean.setAddress("N/A");
			editCompanyBean.setDescription("N/A");
			editCompanyBean.setWebsite("N/A");
			editCompanyBean.setProductName("N/A");
			// TODO Auto-generated catch block

		}

		model.addAttribute("editCompanyBean", editCompanyBean);
		LOGGER.info("EDIT REGISTER COMPANY");
		return EDIT_REGISTER_COMPANY;
	}

	@RequestMapping(method = RequestMethod.POST, value = EDIT_REGISTER_COMPANY)
	public @ResponseBody
	Map<String, ? extends Object> editCompany(
			@RequestBody EditCompanyBean editCompanyBean) throws Exception {

		Set<ConstraintViolation<EditCompanyBean>> failures = validator
				.validate(editCompanyBean);
		if (!failures.isEmpty()) {
			editValidationMessages(failures);
			return Collections.singletonMap("u", "Fill in required fields");
		} else {
			// No errors encountered-add the company.

			Company company = new Company();
			company = companyService.findCompanyById(Long
					.valueOf(editCompanyBean.getCompanyId()));
			/**
			 * validates the phone number format input
			 */

			
			company.setId(Long.valueOf(editCompanyBean.getCompanyId()));
			company.setDescription(editCompanyBean.getDescription());
			company.setProductName(editCompanyBean.getProductName());
			company.setLocation(editCompanyBean.getLocation());
			company.setName(editCompanyBean.getName());
			company.setWebsite(editCompanyBean.getWebsite());
			company.setFirstline(editCompanyBean.getFirstline());
			if (editCompanyBean.getSecondline() != null
					|| editCompanyBean.getSecondline() != "") {
				company.setSecondline(editCompanyBean.getSecondline());
			}
			company.setAddress(editCompanyBean.getAddress());
			company.setFacebook(editCompanyBean.getFacebook());
			company.setTwiter(editCompanyBean.getTwiter());
			company.setLinkenin(editCompanyBean.getLinkenin());

			User user = userCastService.getUser();
			// user.setPassword(CredentialsEncoder.generateMD5(editCompanyBean
			// .getPassword()));
			user.setLastName(editCompanyBean.getName());

			userService.updateUser(user);

			// create a user account within the system
			/**/
			companyService.saveCompany(company);

			return Collections.singletonMap("u", "Saved");

		}

	}

	@RequestMapping(method = RequestMethod.POST, value = EMAIL_CHECK)
	public Map<String, ? extends Object> emailCheck(
			@RequestParam(value = "email", required = true) String email,
			HttpServletRequest request) {
		User user = null;
		try {
			user = userService.getUserByUsername(email);
		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user != null) {
			return Collections.singletonMap("u", "Email Already Registered");
		} else {
			return Collections.singletonMap("u", "Email Available");
		}
	}

	private Map<String, String> editValidationMessages(
			Set<ConstraintViolation<EditCompanyBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<EditCompanyBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	/**
	 * Method to render the Company Registration Form
	 */
	@RequestMapping(method = RequestMethod.GET, value = FREE_TRIAL)
	public String createTrialForm(Model model, HttpServletRequest request) {

		// List<Location> locationList = locationService.getAllLocations();
		// model.addAttribute("LOCATIONLIST", locationList);
		// model.addAttribute("addCompanyBean", new AddCompanyBean());
		// LOGGER.info("REGISTER COMPANY");
		return FREE_TRIAL;
	}

	/**
	 * creates a public url to access jjteach
	 * 
	 * @param companyName
	 * @return
	 */
	public String createCompanyUrl(String companyName) {
		StringBuilder sb = new StringBuilder();

		// split string into an array of substrings using spaces
		String[] name = companyName.split(" ");
		sb.append("www.");
		sb.append(name[0]);
		sb.append(".jjteach.com");

		String publicURL = sb.toString();
		System.out.println("\n\n\n >>>>> Public url for company" + publicURL);
		return publicURL;
	}

}
