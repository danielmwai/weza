package com.tunaweza.web.spring.configuration.evaluation.bean;

import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

public class EvaluationTestBean {
	
	@NotEmpty
	private String question;
	
	private String questionText;
	
	private String module;
	
	private int correctAnswers;
		
	private Map<String,String> choises;
	//@NotEmpty(message="You must select Atleast One Answer")
	private String answer;
	
	private String _answer;
	
	private String timeRemaining;
	
	private String timeout="0";
	
	private long evalid;

	public long getEvalid() {
		return evalid;
	}

	public void setEvalid(long evalid) {
		this.evalid = evalid;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public Map<String, String> getChoises() {
		return choises;
	}

	public void setChoises(Map<String, String> choises) {
		this.choises = choises;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String get_answer() {
		return _answer;
	}

	public void set_answer(String _answer) {
		this._answer = _answer;
	}

	public String getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(String timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	
}
