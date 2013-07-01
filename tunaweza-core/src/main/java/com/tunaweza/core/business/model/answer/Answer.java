/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.model.answer;

/**
 *
 * @author Daniel Mwai
 */

import com.tunaweza.core.business.model.evaluation.EvaluationTransaction;
import com.tunaweza.core.business.model.persistence.AbstractPersistentEntity;
import com.tunaweza.core.business.model.question.Question;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = Answer.TABLE_NAME)
public class Answer extends AbstractPersistentEntity implements
		Comparable<Answer> {
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "answers";

	@Column(name = "enabled")
	private boolean enabled = true;

	@Column(name = "correct")
	private boolean correct = false;

	@Column(name = "text", nullable = false, columnDefinition = "LONGTEXT")
	private String text;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

	@ManyToMany(mappedBy = "answers", cascade = CascadeType.REMOVE)
	private List<EvaluationTransaction> evaluationTransaction;

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean getCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<EvaluationTransaction> getEvaluationTransaction() {
		return evaluationTransaction;
	}

	public void setEvaluationTransaction(
			List<EvaluationTransaction> evaluationTransaction) {
		this.evaluationTransaction = evaluationTransaction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Answer answer) {
		if (answer.getId() > getId()) {
			return -1;

		} else if (answer.getId() < getId()) {
			return 1;
		}
		return 0;
	}

}
