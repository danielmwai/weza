/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.model.company;

import com.tunaweza.core.business.model.licence.CompanyLicense;
import com.tunaweza.core.business.model.user.User;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Daniel Mwai
 */
@Entity
@Table(name = Company.TABLE_NAME)
public class Company  implements
		Comparable<Company> {
    @Id
    @GeneratedValue
    private Long id;
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    public boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "company_details";

	@Column(name = "productName", columnDefinition = "mediumtext", length = 16777215, nullable = true)
	private String productName;

	@Column(name = "name")
	private String name;

	@Column(name = "company_enabled")
	private boolean isEnabled = false;

	@Column(name = "description")
	private String description;

	@Column(name = "location")
	private String location;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "website")
	private String website;

	@Column(name = "firstline")
	private String firstline;

	@Column(name = "secondline")
	private String secondline;

	@Column(name = "address")
	private String address;

	@Column(name = "database_host", nullable = true)
	private String dbaseHost;

	@Column(name = "database_name", nullable = true)
	private String dbaseName;

	@Column(name = "database_username", nullable = true)
	private String dbUserName;

	@Column(name = "database_password", nullable = true)
	private String dbPassword;

	@Column(name = "facebook", nullable = true)
	private String facebook;

	@Column(name = "twiter", nullable = true)
	private String twiter;

	@Column(name = "linkenin", nullable = true)
	private String linkenin;

	@Column(name = "firstlogin_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date firstLogin;

	@Column(name = "lastlogin_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastLogin;

	@OneToOne(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private CompanySettings companySettings;

	@OneToMany(mappedBy = "userCompany", cascade = CascadeType.ALL)
	private List<User> users;

	@OneToOne(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private CompanyLicense license;

	@Column(name = "urlType", columnDefinition = "varchar(255) default 'public'")
	private String urlType;

	@Column(name = "url", columnDefinition = "varchar(255) default 'www.jjteach.com'")
	private String url;
	
	@Column(name = "licenseType", columnDefinition = "varchar(255) default 'none'")
	private String licenseType;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return the companySettings
	 */
	public CompanySettings getCompanySettings() {
		return companySettings;
	}

	/**
	 * @param companySettings
	 *            the companySettings to set
	 */
	public void setCompanySettings(CompanySettings companySettings) {
		this.companySettings = companySettings;
	}

	/**
	 * 
	 * @return firstline the phone number to get
	 */

	public String getFirstline() {
		return firstline;
	}

	/**
	 * 
	 * @param firstline
	 *            the phone number to set
	 */

	public void setFirstline(String firstline) {
		this.firstline = firstline;
	}

	/**
	 * 
	 * @return the second phone number to get
	 */

	public String getSecondline() {
		return secondline;
	}

	/**
	 * 
	 * @param secondline
	 *            the second phone line to set
	 */
	public void setSecondline(String secondline) {
		this.secondline = secondline;
	}

	/**
	 * 
	 * @return the address to get
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 
	 * @param address
	 *            the adress to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public int compareTo(Company company) {
		if (company.getId() > getId()) {
			return -1;

		} else if (company.getId() < getId()) {
			return 1;
		}
		return 0;
	}

	/**
	 * @return the dbaseHost
	 */
	public String getDbaseHost() {
		return dbaseHost;
	}

	/**
	 * @param dbaseHost
	 *            the dbaseHost to set
	 */
	public void setDbaseHost(String dbaseHost) {
		this.dbaseHost = dbaseHost;
	}

	/**
	 * @return the dbaseName
	 */
	public String getDbaseName() {
		return dbaseName;
	}

	/**
	 * @param dbaseName
	 *            the dbaseName to set
	 */
	public void setDbaseName(String dbaseName) {
		this.dbaseName = dbaseName;
	}

	/**
	 * @return the dbUserName
	 */
	public String getDbUserName() {
		return dbUserName;
	}

	/**
	 * @param dbUserName
	 *            the dbUserName to set
	 */
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	/**
	 * @return the dbPassword
	 */
	public String getDbPassword() {
		return dbPassword;
	}

	/**
	 * @param dbPassword
	 *            the dbPassword to set
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	/**
	 * @return the firstLogin
	 */
	public Date getFirstLogin() {
		return firstLogin;
	}

	/**
	 * @param firstLogin
	 *            the firstLogin to set
	 */
	public void setFirstLogin(Date firstLogin) {
		this.firstLogin = firstLogin;
	}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin
	 *            the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * 
	 * @return productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 
	 * @return
	 */
	public CompanyLicense getLicense() {
		return license;
	}

	/**
	 * 
	 * @param license
	 */
	public void setLicense(CompanyLicense license) {
		this.license = license;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwiter() {
		return twiter;
	}

	public void setTwiter(String twiter) {
		this.twiter = twiter;
	}

	public String getLinkenin() {
		return linkenin;
	}

	public void setLinkenin(String linkenin) {
		this.linkenin = linkenin;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

