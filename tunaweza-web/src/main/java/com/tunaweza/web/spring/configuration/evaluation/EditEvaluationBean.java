package com.tunaweza.web.spring.configuration.evaluation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

public class EditEvaluationBean {

	private String id;

	public String getId() {
		return id;
	}

	@NotNull
	@Size(min = 3)
	private String name;

	@NotNull
	@Size(min = 3)
	private String description;

	@NotNull
	private double factoring;

	@NotNull
	private double numberOfQuestions;

	@NotNull
	private double duration;

	@NotNull
	@Min(1)
	@Max(100)	
	private int passMark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getFactoring() {
		return factoring;
	}

	public void setFactoring(double factoring) {
		this.factoring = factoring;
	}

	public double getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(double numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the passMark
	 */
	public int getPassMark() {
		return passMark;
	}

	/**
	 * @param passMark
	 *            the passMark to set
	 */
	public void setPassMark(int passMark) {
		this.passMark = passMark;
	}

}
