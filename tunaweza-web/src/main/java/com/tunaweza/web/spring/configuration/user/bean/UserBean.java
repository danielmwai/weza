package com.tunaweza.web.spring.configuration.user.bean;


import java.util.Date;

/**
 * @author Jose Marcucci
 */
public class UserBean 
{
	private Long id;	
	private int enabled;
	private String username;
	private String email;
	private String password;
	private boolean superuser;
	private String firstName;
	private String lastName;
	private int regNo;
	private Date startDate;
	private Date lastLoggedIn;
	private String currentCourse;
	private String creationDate;
	private String role;
	private String student;
	private String website;
	private String location;
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
	 * @param enabled 
	 * 			the enabled to set
	 */
	public void setEnabled(int enabled)
	{
		this.enabled=enabled;
	}
	
	/**
	 * @return the enabled
	 */
	public int getEnabled()
	{
		return enabled;
	}
	
	/**
	 * @param username 
	 * 			the username to set
	 */
	public void setUsername(String username)
	{
		this.username=username;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * @param email 
	 * 			the email to set
	 */
	public void setEmail(String email)
	{
		this.email=email;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}
	
	/**
	 * @param password 
	 * 			the password to set
	 */
	public void setPassword(String password)
	{
		this.password=password;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * @param firstName 
	 * 			the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}
	
	/**
	 * @param lastName 
	 * 			the lastName to set
	 */
	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}
	
	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lastName;
	}
	
	/**
	 * @param regNo 
	 * 			the regNo to set
	 */
	public void setRegNo(int regNo)
	{
		this.regNo=regNo;
	}
	
	/**
	 * @return the regNo
	 */
	public int getRegNo()
	{
		return regNo;
	}
	
	/**
	 * @param superuser 
	 * 			the superuser to set
	 */
	public void setSuperuser(boolean superuser)
	{
		this.superuser=superuser;
	}
	
	/**
	 * @return the superuser
	 */
	public boolean getSuperuser()
	{
		return superuser;
	}
	
	/**
	 * @param startDate 
	 * 			the startDate to set
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate=startDate;
	}
	
	/**
	 * @return the startDate
	 */
	public Date getStartDate()
	{
		return startDate;
	}
	
	/**
	 * @param lastLoggedIn 
	 * 			the lastLoggedIn to set
	 */
	public void setLastLoggedIn(Date lastLoggedIn)
	{
		this.lastLoggedIn=lastLoggedIn;
	}
	
	/**
	 * @return the lastLoggedIn
	 */
	public Date getLastLoggedIn()
	{
		return lastLoggedIn;
	}
	
	/**
	 * @param currentCourse 
	 * 			the currentCourse to set
	 */
	public void setCurrentCourse(String currentCourse)
	{
		this.currentCourse=currentCourse;
	}
	
	/**
	 * @return the currentCourse
	 */
	public String getCurrentCourse()
	{
		return currentCourse;
	}
	
	/**
	 * @param creationDate 
	 * 			the creationDate to set
	 */
	public void setCreationDate(String creationDate)
	{
		this.creationDate=creationDate;
	}
	
	/**
	 * @return the creationDate
	 */
	public String getCreationDate()
	{
		return creationDate;
	}

	public void setRole(String role) 
	{
		this.role=role;
		
	}
	public String getRole()
	{
		return role;
	}
	
	public void setStudent(String student) 
	{
		this.student=student;
		
	}
	public String getStudent()
	{
		return student;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
