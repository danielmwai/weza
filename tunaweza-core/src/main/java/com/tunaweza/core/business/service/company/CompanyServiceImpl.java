/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.company;

import com.sleepycat.je.DatabaseException;
import com.tunaweza.core.business.dao.company.CompanyDao;
import com.tunaweza.core.business.dao.company.licence.CompanyLicenseDao;
import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.CompanyExistsException;
import com.tunaweza.core.business.dao.exceptions.company.license.CompanyLicenseDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.email.EmailExistsException;
import com.tunaweza.core.business.dao.exceptions.location.LocationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.role.RoleDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserExistsException;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.licence.CompanyLicense;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.user.Location;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.location.LocationService;
import com.tunaweza.core.business.service.role.RoleService;
import com.tunaweza.core.business.service.user.UserService;
import com.tunaweza.core.business.utils.Constants;
import static com.tunaweza.core.business.utils.Constants.LICENSE_PERIOD;
import com.tunaweza.core.business.utils.CredentialsEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */

@Service("companyService")
@Transactional
public class CompanyServiceImpl extends Constants implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private CompanyLicenseDao companyLicenseDao;
	

	@Autowired
	private RoleService roleService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private UserService userService;

	private Random rgen = new Random();
	private byte decision, numValue;
	private char charValue;

	@Override
	public Company addCompany(Company company) throws CompanyExistsException {
		return companyDao.addCompany(company);
	}

	@Override
	public void saveCompany(Company company) {
		companyDao.saveCompany(company);

	}


	@Override
	public Company findCompanyById(long id) throws CompanyDoesNotExistException {
		return companyDao.findCompanyById(id);
	}

	@Override
	public Company findCompany(Company company)
			throws CompanyDoesNotExistException {
		return companyDao.findCompany(company);
	}

	@Override
	public void deleteCompany(long id) throws CompanyDoesNotExistException {
		companyDao.deleteCompany(id);

	}

	@Override
	public void deleteCompany(Company company)
			throws CompanyDoesNotExistException {
		companyDao.deleteCompany(company);

	}

	@Override
	public List<Company> getAllCompanies() {
		return companyDao.getAllCompanies();
	}

	@Override
	public void createCompanyUser(String email, String password,
			String location, String name, Company userCompany) {
		// TODO Auto-generated method stub
		User user = new User();

		user.setUsername(email);
		user.setEmail(email);
		user.setPassword(CredentialsEncoder.generateMD5(password));
		user.setFirstName("Company:");
		user.setLastName(name);
		user.setUserCompany(userCompany);
		Mentor mentor = new Mentor();
		Student student = new Student();
		student.setRegNo(userService.getNextRegistrationNumber());
		mentor.setUser(user);
		student.setUser(user);
		user.setMentor(mentor);
		user.setStudent(student);

		List<Role> roles = new ArrayList<Role>();
		try {
			Role role = roleService.getRoleByName("ROLE_EVALUATOR");
			// Role role2 = roleService.getRoleByName("ROLE_CLOUDADMIN");
			roles.add(role);
			// roles.add(role2);
		} catch (Exception e) {

		}

		user.setRoles(roles);

		try {
			Location companyLocation;
			companyLocation = locationService.getLocationByName(location);
			user.setLocation(companyLocation);
		} catch (LocationDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			User savedUser = userService.addUser(user);
		} catch (UserExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RoleDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Company findCompanyByEmail(String email)
			throws CompanyDoesNotExistException {
		return companyDao.findCompanyByEmail(email);
	}

	
	/*jaymmmmoooooo  PROBLEM @Override
	public void creatSuperUser(Company userCompany) throws JJCloudInstanceExistsException{
		long id = userCompany.getId();
		String dbName = userCompany.getDbaseName();
		String dbUsername = userCompany.getDbUserName();
		String password = userCompany.getDbPassword();
		String email = userCompany.getEmail();
		String name = userCompany.getDbUserName();
		try {
			databaseApi.saveDatabaseSuperUser(id, name, dbUsername, password, email, dbName);
		} catch (DatabaseException e) {
			 TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	

	

	@Override
	public void setFirstAndLastLogins(Company userCompany)
			throws CompanyDoesNotExistException {
		if (userCompany.getFirstLogin() == null) {
			userCompany.setFirstLogin(new Date());
		}
		userCompany.setLastLogin(new Date());

		companyDao.saveCompany(userCompany);
	}

	@Override
	public void setCompanyRegistrationAndExpiryDate(CompanyLicense license)
			throws CompanyLicenseDoesNotExistException, ParseException {

		Calendar now = Calendar.getInstance();

		/**
		 * 
		 * get today's date inform of a string
		 */
		String today = (now.get(Calendar.MONTH) + 1) + "-"
				+ now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR);

		/**
		 * Get the date formatter to convert String to Date
		 */
		DateFormat formatter;
		Date dateToday;
		formatter = new SimpleDateFormat("MM-dd-yyyy");
		/**
		 * Convert today to date
		 */
		dateToday = (Date) formatter.parse(today);

		if (license.getRegistrationDate() == null) {
			license.setRegistrationDate(dateToday);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formatter.parse(today));
			/**
			 * add number of days to today's date to get expiry date
			 */
			calendar.add(Calendar.DATE, LICENSE_PERIOD);

			String expiry = formatter.format(calendar.getTime());
			Date expirydate = formatter.parse(expiry);
			license.setExpiryDate(expirydate);

		}
		/*long diff = d.getTime() - date.getTime();

        System.out.println("The 21st century (up to " + date + ") is "
            + (diff / (1000 * 60 * 60 * 24)) + " days old.");*/
		companyLicenseDao.saveLicense(license);

	}

	@Override
	public String generatePassword() {
		int length = 8;
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length) {
			decision = (byte) rgen.nextInt(2);
			numValue = (byte) rgen.nextInt(10);
			charValue = (char) (rgen.nextInt(25) + 65);
			sb.append((decision % 2 == 0) ? (charValue + "") : (numValue + ""));
		}
		return sb.toString();
	}

}

