package com.tunaweza.core.business.service.license;

import com.tunaweza.core.business.dao.company.CompanyDao;
import com.tunaweza.core.business.dao.company.licence.CompanyLicenseDao;
import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.license.CompanyLicenseDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.licence.LicenseDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.dao.licence.LicenseDao;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.dbswitcher.DbSwitcherHelper;
import com.tunaweza.core.business.service.dbswitcher.DbSwitcherHelperImpl;
import com.tunaweza.core.business.service.mail.MailService;
import com.tunaweza.core.business.service.user.UserService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service("companyLicenseService")
@Transactional
public class CompanyLicenseServiceImpl implements CompanyLicenseService {

	@Autowired
	private CompanyLicenseDao companyLicenseDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	public long DAYS = 0;

	@Autowired
	private LicenseDao licenseDao;
	@Autowired
	private DbSwitcherHelper dbSwitcherHelper;
	/**
	 * 
	 * @param company
	 * @return the number of days remaining for license key to expire
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public long getLicenseRemainingDays(String email)
			throws CompanyLicenseDoesNotExistException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			CompanyDoesNotExistException {
		
/*		DbSwitcherHelperImpl dbSwitcherHelperImpl = new DbSwitcherHelperImpl();
*/		Connection conn = dbSwitcherHelper.dbSwitcher("jjteach_",
				"jjteach_", "jjteach_");
		
		Statement company = conn.createStatement();
		ResultSet rs1 = company.executeQuery("select c.id from company_details c where c.email= '"+email+"'");
		long companyid =0;
		while(rs1.next()){
			companyid = rs1.getLong("id");
		}
		Statement st1 = conn.createStatement();
		ResultSet rs = st1
				.executeQuery("Select DATEDIFF(cl.expiry_date,NOW()) as dat from company_license cl where cl.company_id="+companyid);
		
		while (rs.next()) {
			DAYS = rs.getLong("dat");
		}
		conn.close();
		return DAYS;
	}

	public void licenseMessage(long companyid) throws Exception {
		Company company = companyDao.findCompanyById(companyid);

		StringBuilder builder = new StringBuilder();
		builder.append("Dear " + company.getEmail() + ",<br/>");
		builder.append("<p></p>");
		builder.append("<p> Welcome to JJpeople e-learning program.</p> ");
		builder.append("<strong>Your Email: </strong>" + company.getEmail()
				+ "<br/>");
		builder.append("</ul></p>");
		builder.append("<p style='color:red;'> This is to notify you that your License has expired!</p>");
		builder.append("<p>Please renew your license to continue using jjteach services by logging in to your" +
				"<a href='localhost:8087/jjteach/login.htm'> JJTeach Account</a></p>");
		builder.append("<p>You can also use the payment link below:</p>");
		builder.append("<p><a href='localhost:8087/jjteach/payment.htm'>localhost:8087/jjteach/payment.htm</a></p>");
		builder.append("<p></p>");
		builder.append("<p>Kind Regards,</p>");
		builder.append("<p></p>");
		builder.append("<p>JJ People Team</p>");
		mailService.sendMail(builder.toString(), company.getEmail());

	}

	public void senEmailToAllAdmin(String role, long companyId)
			throws CompanyDoesNotExistException, Exception {
		try {

			List<User> users = userService.getUsersByRoleCompanyId(role,
					companyId);
			for (User user : users) {

				StringBuilder builder = new StringBuilder();
				builder.append("Dear " + user.getEmail() + ",<br/>");
				builder.append("<p></p>");
				builder.append("<p> Welcome to JJpeople e-learning program.</p> ");
				builder.append("<strong>Your Email: </strong>"
						+ user.getEmail() + "<br/>");
				builder.append("</ul></p>");
				builder.append("<p style='color:red;'> This is to notify you that your License has expired!</p>");
				builder.append("<p>please re-new your license to continue using jjteach services By Login to your" +
						"<a href='localhost:8087/jjteach/login.htm'> JJTeach Account</a></p>");
				builder.append("<p>Adn click on Pay link</p>");
				builder.append("<p></p>");
				builder.append("<p>Kind Regards,</p>");
				builder.append("<p></p>");
				builder.append("<p>JJ People Team</p>");

				mailService.sendMail(builder.toString(), user.getEmail());

			}
		} catch (UserDoesNotExistException e) {
			e.printStackTrace();
		}

	}

	public long maxNumberOfActiveUsersForLicence(String email)
			throws CompanyLicenseDoesNotExistException,
			CompanyDoesNotExistException, LicenseDoesNotExistException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		DbSwitcherHelperImpl dbSwitcherHelperImpl = new DbSwitcherHelperImpl();
		Connection conn = dbSwitcherHelperImpl.dbSwitcher("jjteach_",
				"jjteach_", "jjteach_");
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select c.id from company_details c where c.email= '"+email+"'");
		long companyid =0;
		while(rs.next()){
			companyid = rs.getLong("id");
		}
		Statement st1 = conn.createStatement();

		ResultSet rs1 = st1
				.executeQuery("Select cl.license_id from company_license cl where cl.company_id="
						+ companyid);
		long license_id = 0;
		while (rs1.next()) {
			license_id = rs1.getLong("license_id");
		}

		Statement st2 = conn.createStatement();
		ResultSet rs2 = st2
				.executeQuery("Select lt.max_users from license_types lt where lt.id="
						+ license_id);
		long max_users = 0;
		while (rs2.next()) {
			max_users = rs2.getLong("max_users");
		}
conn.close();
		return max_users;
	}
}
