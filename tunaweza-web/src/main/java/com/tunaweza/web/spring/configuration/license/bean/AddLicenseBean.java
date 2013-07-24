package com.tunaweza.web.spring.configuration.license.bean;

/**
 * 
 * @author Peter Kiragu
 *
 */
public class AddLicenseBean {
	
	private String max_users;
	private String cost;
	private String name;
	
	/**
	 * gets the name of the license
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the license
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * returns the maximum number of users
	 * 
	 * @return max_users
	 */
	public String getMax_users() {
		return max_users;
	}
	
	/**
	 * 
	 * @param max_users
	 */
	public void setMax_users(String max_users) {
		this.max_users = max_users;
	}
	
	/**
	 * 
	 * @return cost of the license
	 */
	public String getCost() {
		return cost;
	}
	
	/**
	 * 
	 * @param cost
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}

}
