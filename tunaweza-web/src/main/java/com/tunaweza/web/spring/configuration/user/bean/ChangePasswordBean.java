package com.tunaweza.web.spring.configuration.user.bean;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

/**
 * @author Timothy Tuti
 */
public class ChangePasswordBean
{
	private String id;
	
	@NotNull
	@Size(min=6,max=20)
	private String oldPassword;
	
	
	
	@NotNull
	@Size(min=6,max=20)
	private String newPassword;

	@NotNull
	@Size(min=6,max=20)
	private String newPasswordConfirm;
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
	 * @param oldPassword 
	 * 				the old password to set
	 */
	public void setOldPassword(String oldPassword)
	{
		this.oldPassword=oldPassword;
	}
	
	/**
	 * @return the old password
	 */
	public String getOldPassword()
	{
		return oldPassword;
	}
	
	/**
	 * @param newPassword 
	 * 				the new password to set
	 */
	public void setNewPassword(String newPassword)
	{
		this.newPassword=newPassword;
	}
	
	/**
	 * @return the newPassword
	 */
	public String getNewPassword()
	{
		return newPassword;
	}
	/**
	 * @param newPasswordConfirm 
	 * 				the new password to confirm with
	 */
	public void setNewPasswordConfirm(String newPasswordConfirm)
	{
		this.newPasswordConfirm=newPasswordConfirm;
	}
	
	/**
	 * @return the newPasswordConfirm
	 */
	public String getNewPasswordConfirm()
	{
		return newPasswordConfirm;
	}

}
