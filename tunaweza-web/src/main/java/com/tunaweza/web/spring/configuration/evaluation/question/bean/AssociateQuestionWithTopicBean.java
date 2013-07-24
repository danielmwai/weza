/**
 * 
 */
package com.tunaweza.web.spring.configuration.evaluation.question.bean;

import java.util.List;


public class AssociateQuestionWithTopicBean {

	private Long topicId;

	private String questionIds;

	private String _questionIds;

	/**
	 * @return the topicId
	 */
	public Long getTopicId() {
		return topicId;
	}

	/**
	 * @param topicId the topicId to set
	 */
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	/**
	 * @return the questionIds
	 */
	public String getQuestionIds() {
		return questionIds;
	}

	/**
	 * @param questionIds the questionIds to set
	 */
	public void setQuestionIds(String questionIds) {
		this.questionIds = questionIds;
	}

	/**
	 * @return the _questionIds
	 */
	public String get_questionIds() {
		return _questionIds;
	}

	/**
	 * @param _questionIds
	 *            the _questionIds to set
	 */
	public void set_questionIds(String _questionIds) {
		this._questionIds = _questionIds;
	}
	
	

}
