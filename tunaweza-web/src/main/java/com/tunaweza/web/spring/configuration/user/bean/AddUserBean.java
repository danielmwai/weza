package com.tunaweza.web.spring.configuration.user.bean;

import javax.validation.constraints.NotNull;



/**
 * @author Jose Marcucci
 * @author Jacktone Mony
 */
public class AddUserBean {

	@NotNull
	private String email;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;
	
	@NotNull
	private String password;
	
	@NotNull
	private String repassword;

	@NotNull
	private String role;

	/*@NotNull
	private String location;*/
	
	private String enabled;
	
	private String _enabled;


	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param rolename
	 *            the rolename to set
	 */
	public void setRole(String roleName) {
		role = roleName;

	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param locationName
	 *            the location_name to set
	 */
	/*public void setLocation(String locationName) {
		location = locationName;

	}

	*//**
	 * @return the location_name
	 *//*
	public String getLocation() {
		return location;
	}*/

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the repassword
	 */
	public String getRepassword() {
		return repassword;
	}

	/**
	 * @param repassword the repassword to set
	 */
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	

	public String get_enabled() {
		return _enabled;
	}

	public void set_enabled(String _enabled) {
		this._enabled = _enabled;
	}
	
}