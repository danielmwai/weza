package com.tunaweza.core.business.service.solution;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * @author Samuel Waweru
 */
public class PostSolutionBean 
{
	@NotEmpty(message = "Message must be entered")
	private String message;
	
	@NotEmpty (message = "Subject must be entered")
	private String subject;
	
	private CommonsMultipartFile solution;
	
	@NotEmpty
	private String topicId;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public CommonsMultipartFile getSolution() {
		return solution;
	}

	public void setSolution(CommonsMultipartFile solution) {
		this.solution = solution;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

		
}
