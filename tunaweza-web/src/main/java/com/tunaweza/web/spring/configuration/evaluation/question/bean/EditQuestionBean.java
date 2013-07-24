package com.tunaweza.web.spring.configuration.evaluation.question.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

public class EditQuestionBean {

	private String level;
	@NotEmpty
	private String text;
	@NotEmpty
	private String choiceOne;
	@NotEmpty
	private String choiceTwo;
	@NotEmpty
	private String choiceThree;
	@NotEmpty
	private String choiceFour;

	private boolean choiceTwoCorrect;

	private boolean choiceThreeCorrect;

	private boolean choiceFourCorrect;

	public boolean getChoiceThreeCorrect() {
		return choiceThreeCorrect;
	}

	public void setChoiceThreeCorrect(boolean choiceThreeCorrect) {
		this.choiceThreeCorrect = choiceThreeCorrect;
	}

	public boolean getChoiceFourCorrect() {
		return choiceFourCorrect;
	}

	public void setChoiceFourCorrect(boolean choiceFourCorrect) {
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

	public boolean getChoiceTwoCorrect() {
		return choiceTwoCorrect;
	}

	public boolean isChoiceTwoCorrect() {
		return choiceTwoCorrect;
	}

	public void setChoiceTwoCorrect(boolean choiceTwoCorrect) {
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

}
