package com.jjpeople.jjteach.web.spring.configuration.mentortemplate.bean;

public class MentorModulesBean {
	private String mentorId;
	private String moduleId;
	private String moduleName;
	private String pendingTransactions;
	
	
	/**
	 * @return the mentorId
	 */
	public String getMentorId() {
		return mentorId;
	}
	/**
	 * @param mentorId the mentorId to set
	 */
	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}
	/**
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}
	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}
	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/**
	 * @return the pendingTransactions
	 */
	public String getPendingTransactions() {
		return pendingTransactions;
	}
	/**
	 * @param pendingTransactions the pendingTransactions to set
	 */
	public void setPendingTransactions(String pendingTransactions) {
		this.pendingTransactions = pendingTransactions;
	}
	
	

}
