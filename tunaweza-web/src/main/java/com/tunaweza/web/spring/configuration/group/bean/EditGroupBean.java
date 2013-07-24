package com.tunaweza.web.spring.configuration.group.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author James M. Mungai
 */
public class EditGroupBean {
	@NotNull
	@Size(min=2,max=20)
	private String name;
	
	private String description;
	
	private Long id;
	
	
	/**
	 * @param id 
	 * 			the id to set
	 */
	public void setId(Long id)
	{
		this.id=id;
	}
	
	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}
	
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
