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
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.topic.Topic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Entity
@Table(name=OverrideStudentExercise.TABLE_NAME)
public class OverrideStudentExercise extends AbstractPersistentEntity 
{
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "override_student_exercise";
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "overriden_exercise_id")
	private Topic overridenExercise;
	
	@ManyToOne
	@JoinColumn(name = "replacing_exercise_id")
	private Topic replacingExercise;
	
	@Column(name = "overrideType")
	private OverrideTypes overrideType;
	
	public void setStudent(Student user)
	{
		this.student=user;
	}
	
	public Student getStudent()
	{
		return student;
	}
	
	/**
	 * @return the overridenExercise
	 */
	public Topic getOverridenExercise() {
		return overridenExercise;
	}

	/**
	 * @param overridenExercise
	 *            the overridenExercise to set
	 */
	public void setOverridenExercise(Topic overriddenExercise) {
		this.overridenExercise = overriddenExercise;
	}

	/**
	 * @return the replacingExercise
	 */
	public Topic getReplacingExercise() {
		return replacingExercise;
	}

	/**
	 * @param replacingExercise
	 *            the replacingExercise to set
	 */
	public void setReplacingExercise(Topic replacingExercise) {
		this.replacingExercise = replacingExercise;
	}
	
	/**
	 * @param overrideType
	 *            the overrideType to set
	 */
	public void setOverrideType(OverrideTypes overrideType) {
		this.overrideType = overrideType;
	}

	/**
	 * @return the overrideType
	 */
	public OverrideTypes getOverrideType() {
		return overrideType;
	}
}
