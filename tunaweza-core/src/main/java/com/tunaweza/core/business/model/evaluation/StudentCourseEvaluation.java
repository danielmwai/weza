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
import com.tunaweza.core.business.model.course.Course;
import com.tunaweza.core.business.model.student.Student;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name=StudentCourseEvaluation.TABLE_NAME)
public class StudentCourseEvaluation  implements Comparable<StudentCourseEvaluation>{
   @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	public static final String TABLE_NAME = "student_course_test";
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "coursee_id")
	private Course courseTemplate;
	
	@Column(name = "date_taken")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dateTaken;
	
	@OneToMany(mappedBy = "studentCourseEvaluation", cascade = CascadeType.REMOVE)
	private List<CourseEvaluationTransaction> evaluationTransaction;
	
	@Column(name = "test_score",columnDefinition="integer default 1")
	private int testScore;
	
	@Column(columnDefinition="boolean default 0")
	private boolean completed=false;

	@Column(columnDefinition="boolean default 0")
	private boolean temporary=false;
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return courseTemplate;
	}

	public void setCourse(Course course) {
		this.courseTemplate = course;
	}

	public Date getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
	}

	public List<CourseEvaluationTransaction> getEvaluationTransaction() {
		return evaluationTransaction;
	}

	public void setEvaluationTransaction(
			List<CourseEvaluationTransaction> evaluationTransaction) {
		this.evaluationTransaction = evaluationTransaction;
	}

	public int getTestScore() {
		return testScore;
	}

	public void setTestScore(int testScore) {
		this.testScore = testScore;
	}

	@Override
	public int compareTo(StudentCourseEvaluation evaluation) {
		if (evaluation.getId() > getId()) {
			return -1;

		} else if (evaluation.getId() < getId()) {
			return 1;
		}
		return 0;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isTemporary() {
		return temporary;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}
	
	
}
