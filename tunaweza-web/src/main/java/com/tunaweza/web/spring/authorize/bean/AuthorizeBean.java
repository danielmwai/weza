package com.tunaweza.web.spring.authorize.bean;

import javax.validation.constraints.Size;
//import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 
 * @author Paul Kevin
 * 
 * AuthorizeBean.java
 *
 */
public class AuthorizeBean {
	@Size(min=1, max=50) 
	private String email;
	
	@Size(min=1, max=50) 
	private String password;
	

	/**
	 * @return the email
	 */
	public String getEmail() {
		
		//SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

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
}