/*
 * The MIT License
 *
 * Copyright 2013 Daniel Mwai <naistech.gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tunaweza.core.business.model.evaluation;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.persistence.AbstractPersistentEntity;
import com.tunaweza.core.business.model.question.Question;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Entity
@Table(name = Evaluation.TABLE_NAME)
public class Evaluation extends AbstractPersistentEntity implements
		Comparable<Evaluation> {

	public static final String TABLE_NAME = "evaluation_template";
	private static final long serialVersionUID = 1L;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column
	private double factoring = 0;

	@Column(name = "number_of_questions")
	private double numberOfQuestions = 10;

	@Column(name = "passmark")
	private int passmark = 70;

	@Column(name = "duration")
	private double duration = 15;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy="evaluationTemplate")
	private List<Question> questions;

	@OneToOne
	@JoinColumn(name = "module_id")
	private Module module;

	@OneToMany(mappedBy = "evaluationTemplate", fetch = FetchType.LAZY)
	private List<StudentEvaluation> studentEvaluations;
	

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


	public int getPassmark() {
		return passmark;
	}


	public void setPassmark(int passmark) {
		this.passmark = passmark;
	}


	public double getDuration() {
		return duration;
	}


	public void setDuration(double duration) {
		this.duration = duration;
	}


	public List<Question> getQuestions() {
		return questions;
	}


	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}


	public Module getModule() {
		return module;
	}


	public void setModule(Module module) {
		this.module = module;
	}


	public List<StudentEvaluation> getStudentEvaluations() {
		return studentEvaluations;
	}


	public void setStudentEvaluations(List<StudentEvaluation> studentEvaluations) {
		this.studentEvaluations = studentEvaluations;
	}


	@Override
	public int compareTo(Evaluation evaluation) {
		if (evaluation.getId() > getId()) {
			return -1;

		} else if (evaluation.getId() < getId()) {
			return 1;
		}
		return 0;
	}

}