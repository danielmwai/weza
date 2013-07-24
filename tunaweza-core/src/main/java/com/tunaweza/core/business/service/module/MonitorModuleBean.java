package com.tunaweza.core.business.service.module;

import java.util.Date;

/**
 * 
 * @author Jimmy Ndung'u
 * 
 */
public class MonitorModuleBean {
	private String id;
	private String moduleName;
	private String startDate;
	private String endDate;
	private String testScore;
	private double topicPercentage;
	private double exercisePercentage;
	private int pendingExercises;
	private boolean moduleEnabled;
	
	
	
	public String getTestScore() {
		return testScore;
	}

	public void setTestScore(String testScore) {
		this.testScore = testScore;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public double getTopicPercentage() {
		return topicPercentage;
	}

	public void setTopicPercentage(double topicPercentage) {
		this.topicPercentage = topicPercentage;
	}

	public double getExercisePercentage() {
		return exercisePercentage;
	}

	public void setExercisePercentage(double exercisePercentage) {
		this.exercisePercentage = exercisePercentage;
	}

	public int getPendingExercises() {
		return pendingExercises;
	}

	public void setPendingExercises(int pendingExercises) {
		this.pendingExercises = pendingExercises;
	}

	public boolean getModuleEnabled() {
		return moduleEnabled;
	}

	public void setModuleEnabled(boolean moduleEnabled) {
		this.moduleEnabled = moduleEnabled;
	}

}
