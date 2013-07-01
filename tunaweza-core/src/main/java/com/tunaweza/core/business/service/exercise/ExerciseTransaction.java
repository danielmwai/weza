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

package com.tunaweza.core.business.service.exercise;

import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.exercise.transaction.ExerciseTransactionType;
import com.tunaweza.core.business.model.file.File;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.persistence.AbstractPersistentEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = ExerciseTransaction.TABLE_NAME)
public class ExerciseTransaction extends AbstractPersistentEntity implements
		Comparable<ExerciseTransaction> {

	public static final String TABLE_NAME = "exercise_transaction";

	private static final long serialVersionUID = 1L;

	@Column(name = "transaction_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date transanctionDate;

	@Column(name = "read_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date readDate;

	@Column(name = "comment", length = 1048576)
	private String comment;

	@Column(name = "subject", columnDefinition = "varchar(255) default 'Module Subject'")
	private String subject;

	@ManyToOne
	@JoinColumn(name = "mentor_id")
	Mentor mentor;

	@ManyToOne
	@JoinColumn(name = "exercise_id")
	private StudentExercise studentExercise;

	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;

	@ManyToOne
	@JoinColumn(name = "exercise_transactiontype_id")
	private ExerciseTransactionType exerciseTransactionType;

	/**
	 * @return trasanctionDate the transaction date
	 */
	public Date getTransanctionDate() {
		return transanctionDate;
	}

	/**
	 * @param trasanctionDate
	 *            the transaction date to set
	 */
	public void setTransactionDate(Date transanctionDate) {
		this.transanctionDate = transanctionDate;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the mentor
	 */
	public Mentor getMentor() {
		return mentor;
	}

	/**
	 * @param mentor
	 *            the mentor to set
	 */
	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}

	/**
	 * @return the studentExercise
	 */
	public StudentExercise getStudentExercise() {
		return studentExercise;
	}

	/**
	 * @param studentExercise
	 *            the studentExercise to set
	 */
	public void setStudentExercise(StudentExercise studentExercise) {
		this.studentExercise = studentExercise;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @param readDate
	 *            the readDate to set
	 */
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	/**
	 * @return the readDate
	 */
	public Date getReadDate() {
		return readDate;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @param exerciseTransactionType
	 *            the exerciseTransactionType to set
	 */
	public void setExerciseTransactionType(
			ExerciseTransactionType exerciseTransactionType) {
		this.exerciseTransactionType = exerciseTransactionType;
	}

	/**
	 * @return the exerciseTransactionType
	 */
	public ExerciseTransactionType getExerciseTransactionType() {
		return exerciseTransactionType;
	}

	@Override
	public int compareTo(ExerciseTransaction exerciseTransaction) {
		if (exerciseTransaction.getTransanctionDate().before(
				exerciseTransaction.getTransanctionDate()))
			return 1;
		if (exerciseTransaction.getTransanctionDate().after(
				exerciseTransaction.getTransanctionDate()))
			return -1;
		else
			return 0;
	}

}
