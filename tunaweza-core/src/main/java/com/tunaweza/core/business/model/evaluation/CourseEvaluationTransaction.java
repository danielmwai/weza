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

import com.tunaweza.core.business.model.answer.Answer;
import com.tunaweza.core.business.model.question.Question;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Entity
@Table(name = CourseEvaluationTransaction.TABLE_NAME)
public class CourseEvaluationTransaction  {
    @Id
    @GeneratedValue
    private Long id;
       
       
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "course_evaluation_transaction";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "student_course_evaluation_answer")
	private List<Answer> answers;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_evaluation_id")
	private StudentCourseEvaluation studentCourseEvaluation;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public StudentCourseEvaluation getStudentCourseEvaluation() {
		return studentCourseEvaluation;
	}

	public void setStudentCourseEvaluation(StudentCourseEvaluation studentCourseEvaluation) {
		this.studentCourseEvaluation = studentCourseEvaluation;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
