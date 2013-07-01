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

import com.tunaweza.core.business.model.persistence.AbstractPersistentEntity;
import com.tunaweza.core.business.model.student.Student;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Entity
@Table(name=StudentEvaluation.TABLE_NAME)
public class StudentEvaluation extends AbstractPersistentEntity{

	public static final String TABLE_NAME = "student_test";
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "evaluationTemplate_id")
	private EvaluationTemplate evaluationTemplate;
	
	@Column(name = "date_taken")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dateTaken;
	
	@Column(name = "questions_tested")
	private int questionsTested=0;
		
	@OneToMany(mappedBy = "studentEvaluation", cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
	private List<EvaluationTransaction> evaluationTransaction;
	
	@Column(name = "test_score")
	private int testScore;
	
	@Column
	private boolean completed=false;
	
	@Column
	private boolean temporary=false;

	public boolean isTemporary() {
		return temporary;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public EvaluationTemplate getEvaluationTemplate() {
		return evaluationTemplate;
	}

	public void setEvaluationTemplate(EvaluationTemplate evaluationTemplate) {
		this.evaluationTemplate = evaluationTemplate;
	}

	public Date getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
	}

	public int getQuestionsTested() {
		return questionsTested;
	}

	public void setQuestionsTested(int questionsTested) {
		this.questionsTested = questionsTested;
	}

	public int getTestScore() {
		return testScore;
	}

	public void setTestScore(int testScore) {
		this.testScore = testScore;
	}

	public List<EvaluationTransaction> getEvaluationTransaction() {
		return evaluationTransaction;
	}

	public void setEvaluationTransaction(List<EvaluationTransaction> evaluationTransaction) {
		this.evaluationTransaction = evaluationTransaction;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
