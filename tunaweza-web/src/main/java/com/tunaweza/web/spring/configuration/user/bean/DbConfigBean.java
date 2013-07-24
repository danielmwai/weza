package com.tunaweza.web.spring.configuration.user.bean;

public class DbConfigBean {

	private String dbHost;
	private String dbName;
	private String dbUsername;
	private String dbPass;
	
	public DbConfigBean(String host, String name, String username, String pass){
		this.dbHost = host;
		this.dbName = name;
		this.dbUsername = username;
		this.dbPass = pass;
	}
	/**
	 * @return the dbHost
	 */
	public String getDbHost() {
		return dbHost;
	}
	/**
	 * @param dbHost the dbHost to set
	 */
	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}
	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}
	/**
	 * @param dbName the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	/**
	 * @return the dbUsername
	 */
	public String getDbUsername() {
		return dbUsername;
	}
	/**
	 * @param dbUsername the dbUsername to set
	 */
	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}
	/**
	 * @return the dbPass
	 */
	public String getDbPass() {
		return dbPass;
	}
	/**
	 * @param dbPass the dbPass to set
	 */
	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}
	
	
}
