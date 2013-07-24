package com.tunaweza.web.spring.configuration.messaging.bean;

import javax.validation.constraints.NotNull;

public class SendMessageBean {
	
	@NotNull
	private String message;
	
	@NotNull
	private long studentId;
	
	/**
	 * 
	 * @return the student id
	 */
	public long getStudentId() {
		return studentId;
	}
	
	/**
	 * 
	 * @param studentId
	 */
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	/**
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
