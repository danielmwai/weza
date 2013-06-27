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

package com.tunaweza.core.business.model.exercise;
import com.tunaweza.core.business.model.persistence.AbstractPersistentEntity;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Entity
@Table(name = StudentExercise.TABLE_NAME)
public class StudentExercise extends AbstractPersistentEntity {
	
	public static final String TABLE_NAME= "student_exercise";
	private static final long serialVersionUID = 1L;   	

	@Column(name = "closed_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Calendar closedDate;

	@Column(name = "is_complete",columnDefinition = "boolean default 1")
	private boolean isComplete;

	@Column(name = "score",columnDefinition = "integer default 1")
	private int score;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "topic_id")
	private Topic exercise;

	@OneToMany(mappedBy = "studentExercise",cascade = CascadeType.REMOVE)
	private List<ExerciseTransaction> exerciseTransaction;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Student student;
	
	@Column(name = "being_graded", columnDefinition = "boolean default FALSE")
	private boolean beingGraded;
	
	@Column(name = "mentor_grading")
	private Long mentorGrading;	

	/**
	 * @return the closedDate
	 */
	public Calendar getClosedDate() {
		return closedDate;
	}

	/**
	 * @param rightNow
	 *            the closedDate to set
	 */
	public void setClosedDate(Calendar rightNow) {
		this.closedDate = rightNow;
	}

	/**
	 * @return the isComplete
	 */
	public boolean isComplete() {
		return isComplete;
	}

	/**
	 * @param isComplete
	 *            the isComplete to set
	 */
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the exerciseTransaction
	 */
	public List<ExerciseTransaction> getExerciseTransaction() {
		return exerciseTransaction;
	}

	/**
	 * @param exerciseTransaction
	 *            the exerciseTransaction to set
	 */
	public void setExerciseTransaction(
			List<ExerciseTransaction> exerciseTransaction) {
		this.exerciseTransaction = exerciseTransaction;
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @param exercise
	 *            the exercise to set
	 */
	public void setExercise(Topic exercise) {
		this.exercise = exercise;
	}

	/**
	 * @return the exercise
	 */
	public Topic getExercise() {
		return exercise;
	}


	/**
	 * @return the beingGraded
	 */
	public boolean isBeingGraded() {
		return beingGraded;
	}

	/**
	 * @param beingGraded the beingGraded to set
	 */
	public void setBeingGraded(boolean beingGraded) {
		this.beingGraded = beingGraded;
	}

	/**
	 * @return the mentorGrading
	 */
	public Long getMentorGrading() {
		return mentorGrading;
	}

	/**
	 * @param mentorGrading the mentorGrading to set
	 */
	public void setMentorGrading(Long mentorGrading) {
		this.mentorGrading = mentorGrading;
	}
}

