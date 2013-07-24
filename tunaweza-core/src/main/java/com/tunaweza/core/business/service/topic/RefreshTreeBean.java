package com.tunaweza.core.business.service.topic;

import com.tunaweza.core.business.model.status.Status;


public class RefreshTreeBean {

	public long topicId;
	
	private Status status;

	public long getTopicId() {
		return topicId;
	}

	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
}
