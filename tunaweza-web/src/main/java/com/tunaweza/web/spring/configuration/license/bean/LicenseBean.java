package com.tunaweza.web.spring.configuration.license.bean;


/**
 * 
 * @author Peter Kiragu
 *
 */
public class LicenseBean {
	
	
	private Long max_users;	
	private Float cost;
	private Long id;
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
	 *sets the name of the license
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * gets the id of the license
	 * 
	 * @return the id of the license
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @param id 
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return the maximum number of users	
	 */
	public Long getMax_users() {
		return max_users;
	}

	/**
	 * 
	 * @param max_users the maximum number of users that the license holds
	 */
	public void setMax_users(Long max_users) {
		this.max_users = max_users;
	}

	/**
	 * 
	 * @return the cost of the license
	 */
	public Float getCost() {
		return cost;
	}
	/**
	 * 
	 * @param cost
	 */
	public void setCost(Float cost) {
		this.cost = cost;
	} 

}