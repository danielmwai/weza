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

package com.tunaweza.core.business.model.student;

import com.tunaweza.core.business.model.course.EmbeddableCourse;
import com.tunaweza.core.business.model.evaluation.StudentCourseEvaluation;
import com.tunaweza.core.business.model.evaluation.StudentEvaluation;
import com.tunaweza.core.business.model.exercise.OverrideStudentExercise;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.persistence.AbstractPersistentEntity;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = Student.TABLE_NAME)
public class Student extends AbstractPersistentEntity {

	public static final String TABLE_NAME = "student";
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_user", unique = true)
	private User user;
	
	@Column(name = "registration_no", nullable = false, unique = true, columnDefinition = "integer default '1'")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int regNo;

	@Column(name = "start_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name = "last_logged_in")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastLoggedIn;

	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
	private List<StudentExercise> studentExercise;

	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
	private List<StudentEvaluation> studentEvaluations;

	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
	private List<StudentCourseEvaluation> courseEvaluations;

	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
	private List<OverrideStudentExercise> overridenStudentExercise;

	@ElementCollection
	@CollectionTable(name = "student_template", joinColumns = @JoinColumn(name = "student"))
	private List<EmbeddableCourse> courseList;

	@Column(name = "module_startdate", nullable = true)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date moduleStartDate;

	@Column(name = "current_module", nullable = true)
	private Long currentModule;
	
	@ElementCollection
	@CollectionTable(name = "student_completed_modules", joinColumns = @JoinColumn(name = "student_id"))
	private List<CompletedModule> completedModules;

	@ElementCollection
	@CollectionTable(name = "student_enabled_modules", joinColumns = @JoinColumn(name = "student_id"))
	private List<EnabledModule> enabledModules;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "student_completed_topics", joinColumns = @JoinColumn(name = "student"), inverseJoinColumns = @JoinColumn(name = "topic"))
	private List<Topic> completedTopics;

	// @OneToOne
	// @JoinTable(name = "last_accessed_topic",
	// joinColumns = { @JoinColumn(name = "user_id", referencedColumnName =
	// "id") }, inverseJoinColumns = { @JoinColumn(name =
	// "last_accessed_topic_id", referencedColumnName = "id") })
	private Topic topic;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the lastLoggedIn
	 */

	public Date getLastLoggedIn() {
		return lastLoggedIn;
	}

	/**
	 * @param lastLoggedIn
	 *            the lastLoggedIn to set
	 */

	public void setLastLoggedIn(Date lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	/**
	 * @return the studentExercise
	 */
	public List<StudentExercise> getStudentExercise() {
		return studentExercise;
	}

	/**
	 * @param studentExercise
	 *            the studentExercise to set
	 */
	public void setStudentExercise(List<StudentExercise> studentExercise) {
		this.studentExercise = studentExercise;
	}

	/**
	 * @return the overridenStudentExercise
	 */
	public List<OverrideStudentExercise> getOverridenStudentExercise() {
		return overridenStudentExercise;
	}

	/**
	 * @param overridenStudentExercise
	 *            the overridenStudentExercise to set
	 */
	public void setOverridenStudentExercise(
			List<OverrideStudentExercise> overridenStudentExercise) {
		this.overridenStudentExercise = overridenStudentExercise;
	}

	/**
	 * @param courseTemplate
	 *            the courseTemplate to set
	 * 
	 *            public void setCourseTemplate(CourseTemplate courseTemplate) {
	 *            this.courseTemplate = courseTemplate; }
	 * 
	 *            /**
	 * @return the courseTemplate
	 * 
	 *         public CourseTemplate getCourseTemplate() { return
	 *         courseTemplate; } /* /**
	 * @param regNo
	 *            the regNo to set
	 */
	public void setRegNo(int regNo) {
		this.regNo = regNo;
	}

	public List<EmbeddableCourse> getCourseList() {
		return courseList;
	}

	public void setCourseTemplateList(
			List<EmbeddableCourse> courseList) {
		this.courseList = courseList;
	}

	/**
	 * @return the regNo
	 */
	public int getRegNo() {
		return regNo;
	}

	/**
	 * @return the moduleStartDate
	 */
	public Date getModuleStartDate() {
		return moduleStartDate;
	}

	/**
	 * @param moduleStartDate
	 *            the moduleStartDate to set
	 */
	public void setModuleStartDate(Date moduleStartDate) {
		this.moduleStartDate = moduleStartDate;
	}

	/**
	 * @return the currentModule
	 */
	public Long getCurrentModule() {
		return currentModule;
	}

	/**
	 * @param currentModule
	 *            the currentModule to set
	 */
	public void setCurrentModule(Long currentModule) {
		this.currentModule = currentModule;
	}

	public List<CompletedModule> getCompletedModules() {
		return completedModules;
	}

	public void setCompletedModules(List<CompletedModule> completedModules) {
		this.completedModules = completedModules;
	}

	/**
	 * 
	 * @return user completed topics
	 */
	public List<Topic> getCompletedTopics() {
		return completedTopics;
	}

	/**
	 * 
	 * @param completedTopics
	 *            the completed topics
	 */
	public void setCompletedTopics(List<Topic> completedTopics) {
		this.completedTopics = completedTopics;
	}

	/**
	 * @return the topic
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
	 * @param topicText
	 *            the topicText to set
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public List<EnabledModule> getEnabledModules() {
		return enabledModules;
	}

	public void setEnabledModules(List<EnabledModule> enabledModules) {
		this.enabledModules = enabledModules;
	}

	public List<StudentEvaluation> getStudentEvaluations() {
		return studentEvaluations;
	}

	public void setStudentEvaluations(List<StudentEvaluation> studentEvaluations) {
		this.studentEvaluations = studentEvaluations;
	}

	public List<StudentCourseEvaluation> getCourseEvaluations() {
		return courseEvaluations;
	}

	public void setCourseEvaluations(
			List<StudentCourseEvaluation> courseEvaluations) {
		this.courseEvaluations = courseEvaluations;
	}
}
