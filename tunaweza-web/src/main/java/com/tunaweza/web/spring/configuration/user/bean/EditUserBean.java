package com.tunaweza.web.spring.configuration.user.bean;

import javax.validation.constraints.NotNull;



/**
 * @author Jose Marcucci
 */
public class EditUserBean {
	

	@NotNull
	private String email;

	private String username;
	
	private String password;
	
	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private String role;

	private String id;
	
	//@NotNull
	private String location;
	
	private String sendIn;
	
	private String _sendIn;
	
	@NotNull
	private String oldEmail;
	
	@NotNull
	private String oldRole;
	



	

	/**
	 * @return the oldRole
	 */
	public String getOldRole() {
		return oldRole;
	}

	/**
	 * @param oldRole the oldRole to set
	 */
	public void setOldRole(String oldRole) {
		this.oldRole = oldRole;
	}

	/**
	 * @return the oldEmail
	 */
	public String getOldEmail() {
		return oldEmail;
	}

	/**
	 * @param oldEmail the oldEmail to set
	 */
	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

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
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
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
	 * @return the roles
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param locationName 
	 * 			the location_name to set
	 */
	public void setLocation(String locationName)
	{
		location = locationName;
		
	}
	
	/**
	 * @return the location_name
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * @return the sendIn
	 */
	public String getSendIn() {
		return sendIn;
	}

	/**
	 * @param sendIn the sendIn to set
	 */
	public void setSendIn(String sendIn) {
		this.sendIn = sendIn;
	}

	/**
	 * @return the _sendIn
	 */
	public String get_sendIn() {
		return _sendIn;
	}

	/**
	 * @param _sendIn the _sendIn to set
	 */
	public void set_sendIn(String _sendIn) {
		this._sendIn = _sendIn;
	}

	
	
	
}
