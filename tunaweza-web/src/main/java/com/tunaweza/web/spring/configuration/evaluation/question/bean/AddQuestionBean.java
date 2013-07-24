package com.tunaweza.web.spring.configuration.evaluation.question.bean;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author Jimmy Ndung'u
 * 
 */
public class AddQuestionBean {

	@NotEmpty
	private String level;
	@NotEmpty
	private String text;
	@NotEmpty
	private String evaluationTemplateId;
	@NotEmpty
	private String choiceOne;
	@NotEmpty
	private String choiceTwo;
	@NotEmpty
	private String choiceThree;
	@NotEmpty
	private String choiceFour;

	private String choiceTwoCorrect;

	private String choiceThreeCorrect;

	private String choiceFourCorrect;

	public String getChoiceThreeCorrect() {
		return choiceThreeCorrect;
	}

	public void setChoiceThreeCorrect(String choiceThreeCorrect) {
		this.choiceThreeCorrect = choiceThreeCorrect;
	}

	public String getChoiceFourCorrect() {
		return choiceFourCorrect;
	}

	public void setChoiceFourCorrect(String choiceFourCorrect) {
		this.choiceFourCorrect = choiceFourCorrect;
	}

	public String getChoiceThree() {
		return choiceThree;
	}

	public void setChoiceThree(String choiceThree) {
		this.choiceThree = choiceThree;
	}

	public String getChoiceFour() {
		return choiceFour;
	}

	public void setChoiceFour(String choiceFour) {
		this.choiceFour = choiceFour;
	}

	public String getChoiceTwoCorrect() {
		return choiceTwoCorrect;
	}

	public String isChoiceTwoCorrect() {
		return choiceTwoCorrect;
	}

	public void setChoiceTwoCorrect(String choiceTwoCorrect) {
		this.choiceTwoCorrect = choiceTwoCorrect;
	}

	public String getChoiceOne() {
		return choiceOne;
	}

	public void setChoiceOne(String choiceOne) {
		this.choiceOne = choiceOne;
	}

	public String getChoiceTwo() {
		return choiceTwo;
	}

	public void setChoiceTwo(String choiceTwo) {
		this.choiceTwo = choiceTwo;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getEvaluationTemplateId() {
		return evaluationTemplateId;
	}

	public void setEvaluationTemplateId(String evaluationTemplateId) {
		this.evaluationTemplateId = evaluationTemplateId;
	}

}
