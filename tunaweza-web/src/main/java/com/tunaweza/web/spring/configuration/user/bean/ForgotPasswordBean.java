package com.tunaweza.web.spring.configuration.user.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
/**
 * @author Timothy Tuti
 */
public class ForgotPasswordBean 
{
	@NotNull
	@Pattern(regexp="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[a-zA-Z]{2,4}$")  
	private String email;
	
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
}
