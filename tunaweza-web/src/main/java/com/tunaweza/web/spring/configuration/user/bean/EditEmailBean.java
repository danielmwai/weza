package com.tunaweza.web.spring.configuration.user.bean;

import javax.validation.constraints.NotNull;
/**
 * @author Timothy Tuti
 */
public class EditEmailBean 
{
	private String id;
	
	@NotNull
	private String email;
	
	@NotNull 
	private String newEmail;
	
	@NotNull 
	private String confirmEmail;
	
	private String role;

	
	/**
	 * @param id 
	 * 			the id to set
	 */
	public void setId(String id)
	{
		this.id=id;
	}
	
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * @param email 
	 * 			the email to set
	 */
	public String getEmail()
	{
		return email;
	}
	/**
	 * @return email 
	 * 
	 */
	public void setEmail(String email)
	{
		this.email=email;
	}
	/**
	 * @param newEmail 
	 * 			the email to set
	 */
	public String getNewEmail()
	{
		return newEmail;
	}
	/**
	 * @return newEmail 
	 * 
	 */
	public void setNewEmail(String newEmail)
	{
		this.newEmail=newEmail;
	}
	/**
	 * @param confirmEmail 
	 * 			the email to set
	 */
	public String getConfirmEmail()
	{
		return confirmEmail;
	}
	/**
	 * @return confirmEmail 
	 * 
	 */
	public void setConfirmEmail(String confirmEmail)
	{
		this.confirmEmail=confirmEmail;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
