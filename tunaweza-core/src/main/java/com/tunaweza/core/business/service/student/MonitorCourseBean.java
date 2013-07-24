package com.tunaweza.core.business.service.student;

/**
 * 
 * @author Joyce Echessa
 * 
 */

public class MonitorCourseBean {

	private String id;
	private String courseTemplateName;
	private String testScore;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCourseTemplateName() {
		return courseTemplateName;
	}
	public void setCourseTemplateName(String courseTemplateName) {
		this.courseTemplateName = courseTemplateName;
	}
	public String getTestScore() {
		return testScore;
	}
	public void setTestScore(String testScore) {
		this.testScore = testScore;
	}
}
