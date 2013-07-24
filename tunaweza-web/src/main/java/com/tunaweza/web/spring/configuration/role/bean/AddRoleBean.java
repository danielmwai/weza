package com.tunaweza.web.spring.configuration.role.bean;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

/**
 * @author Timothy Tuti
 */
public class AddRoleBean 
{
	@NotNull
	@Size(min=3,max=15)
	private String name;
	
	@NotNull
	private String description;
	
	/**
	 * @param name 
	 * 			the name to set
	 */
	public void setName(String name)
	{
		this.name=name;
	}
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @param description 
	 * 				the description to set
	 */
	public void setDescription(String description)
	{
		this.description=description;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
}
