package com.tunaweza.web.spring.configuration.role.bean;


/**
 * @author Timothy Tuti
 */
public class RoleBean 
{
	private Long id;	
	private int number;
	private String name;
	private String description;
	
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
	 * @param number 
	 * 			the number to set
	 */
	public void setNumber(int number)
	{
		this.number=number;
	}
	
	/**
	 * @return the number
	 */
	public int getNumber()
	{
		return number;
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
