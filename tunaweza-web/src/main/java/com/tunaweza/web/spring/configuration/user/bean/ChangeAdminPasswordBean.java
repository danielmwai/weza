package com.tunaweza.web.spring.configuration.user.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangeAdminPasswordBean {

	@NotNull
	private Long company_id;
	
	@NotNull
	@Size(min=6,max=20)
	private String newPassword;
	
	@NotNull
	@Size(min=6,max=20)
	private String newPasswordConfirm;
	
	/**
	 * returns the company id
	 * 
	 * @return company_id
	 */

	public Long getCompany_id() {
		return company_id;
	}

	/**
	 *sets the company_id
	 * 
	 * @param company_id
	 */
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}

	/**
	 * gets the new password
	 * 
	 * @return newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * sets the new password
	 * 
	 * @param newPassword
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * gets the new confirm password
	 * 
	 * @return newPasswordConfirm
	 */
	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}

	/**
	 * sets the confirm password
	 * 
	 * @param newPasswordConfirm
	 */
	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}

}