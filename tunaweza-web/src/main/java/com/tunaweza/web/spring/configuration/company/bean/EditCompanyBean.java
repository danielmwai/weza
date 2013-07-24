package com.tunaweza.web.spring.configuration.company.bean;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class EditCompanyBean {

	private String companyId;
	@NotEmpty
	private String name;

	private String productName;

	@NotEmpty
	private String location;
	@NotEmpty
	private String website;
	@NotEmpty
	private String description;
	@NotEmpty
	private String firstline;
	private String secondline;
	@NotEmpty
	private String address;

	private String facebook;
	private String twiter;
	private String linkenin;

	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId
	 *            the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFirstline() {
		return firstline;
	}

	public void setFirstline(String firstline) {
		this.firstline = firstline;
	}

	public String getSecondline() {
		return secondline;
	}

	public void setSecondline(String secondline) {
		this.secondline = secondline;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 
	 * @return producName
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
	public String getFacebook() {
		return facebook;
	}

	/**
	 * 
	 * @param facebook
	 */
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	/**
	 * 
	 * @return
	 */
	public String getTwiter() {
		return twiter;
	}

	/**
	 * 
	 * 
	 * @param twiter
	 */
	public void setTwiter(String twiter) {
		this.twiter = twiter;
	}

	/**
	 * 
	 * @return
	 */
	public String getLinkenin() {
		return linkenin;
	}

	/**
	 * 
	 * @param linkenin
	 */

	public void setLinkenin(String linkenin) {
		this.linkenin = linkenin;
	}

}