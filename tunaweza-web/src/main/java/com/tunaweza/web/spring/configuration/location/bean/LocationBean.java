package com.tunaweza.web.spring.configuration.location.bean;

/**
 * @author Joyce Echessa
 */
public class LocationBean {
	
	private Long id;	
	private int locationCode;
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
	 * @param locationCode 
	 * 			the locationCode to set
	 */
	public void setLocationCode(int locationCode)
	{
		this.locationCode=locationCode;
	}
	
	/**
	 * @return the locationCode
	 */
	public int getLocationCode()
	{
		return locationCode;
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
