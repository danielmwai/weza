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

package com.tunaweza.core.business.model.topic;
import com.tunaweza.core.business.model.answer.GoodAnswer;
import com.tunaweza.core.business.model.exercise.OverrideStudentExercise;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.file.File;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.persistence.AbstractPersistentEntity;
import com.tunaweza.core.business.model.status.Status;
import com.tunaweza.core.business.model.student.Student;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Entity
@Table(name = Topic.TABLE_NAME)
@JsonIgnoreProperties({ "module", "topicText", "user" })
public class Topic extends AbstractPersistentEntity implements
		Comparable<Topic> {
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "topics";

	@Column(name = "enabled",columnDefinition = "integer default 1")
	private int enabled = 1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_module")
	@JsonIgnore
	private Module module;

	@Column(name = "parent_id")
	private long parentId;

	@Column(name = "level")
	private long level;

	@Column(name = "topic_name",columnDefinition = "varchar(255) default 'Topic Name'")
	private String name;

	@Column(name = "is_exercise",columnDefinition = "boolean default 0")
	private boolean exercise;

	@OneToOne(mappedBy = "topic", fetch = FetchType.LAZY)
	@JsonIgnore
	private TopicText topicText;

	@ManyToMany(mappedBy = "completedTopics", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Student> user;

	@Transient
	private Status status;

	@OneToMany(mappedBy = "replacingExercise")
	private List<OverrideStudentExercise> replacingExercises;

	@OneToMany(mappedBy = "overridenExercise")
	private List<OverrideStudentExercise> overridingExercises;

	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;

	@Column(name = "eval_questions_limit", columnDefinition = "integer default 0", nullable = false)
	private int evaluationQuestionsLimit;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "exercisetype_id") private ExerciseType exerciseType;
	 */

	@OneToMany(mappedBy = "exercise", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<StudentExercise> studentExercises;

	@OneToMany(mappedBy = "exercise", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<GoodAnswer> goodAnswers;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "exercises")
	private List<Mentor> mentor;

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
	 * @param exerciseType
	 *            the exerciseType to set
	 */

	/*
	 * public void setExerciseType(ExerciseType exerciseType) {
	 * this.exerciseType = exerciseType; }
	 */

	/**
	 * @return the exerciseType
	 */
	/*
	 * public ExerciseType getExerciseType() { return exerciseType; }
	 */

	/**
	 * @param replacingExercises
	 *            the replacingExercises to set
	 */
	public void setReplacingExercises(
			List<OverrideStudentExercise> replacingExercises) {
		this.replacingExercises = replacingExercises;
	}

	/**
	 * @return the replacingExercises
	 */
	public List<OverrideStudentExercise> getReplacingExercises() {
		return replacingExercises;
	}

	/**
	 * @param overridingExercises
	 *            the overridingExercises to set
	 */
	public void setOverridingExercises(
			List<OverrideStudentExercise> overridingExercises) {
		this.overridingExercises = overridingExercises;
	}

	/**
	 * @return the overridingExercises
	 */
	public List<OverrideStudentExercise> getOverridingExercises() {
		return overridingExercises;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getLevel() {
		return level;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public TopicText getTopicText() {
		return topicText;
	}

	public void setTopicText(TopicText topicText) {
		this.topicText = topicText;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isExercise() {
		return exercise;
	}

	public void setExercise(boolean exercise) {
		this.exercise = exercise;
	}

	public List<Student> getUser() {
		return user;
	}

	public void setUser(List<Student> user) {
		this.user = user;
	}

	public List<StudentExercise> getStudentExercises() {
		return studentExercises;
	}

	public void setStudentExercise(List<StudentExercise> studentExercises) {
		this.studentExercises = studentExercises;
	}

	/**
	 * @return the goodAnswers
	 */
	public List<GoodAnswer> getGoodAnswers() {
		return goodAnswers;
	}

	/**
	 * @param goodAnswers
	 *            the goodAnswers to set
	 */
	public void setGoodAnswers(List<GoodAnswer> goodAnswers) {
		this.goodAnswers = goodAnswers;
	}

	/**
	 * @return the Mentors
	 */
	public List<Mentor> getMentor() {
		return mentor;
	}

	/**
	 * @param mentor
	 *            the mentor to set
	 */
	public void setMentor(List<Mentor> mentor) {
		this.mentor = mentor;
	}

	/**
	 * @return the evaluationQuestionsLimit
	 */
	public int getEvaluationQuestionsLimit() {
		return evaluationQuestionsLimit;
	}

	/**
	 * @param evaluationQuestionsLimit
	 *            the evaluationQuestionsLimit to set
	 */
	public void setEvaluationQuestionsLimit(int evaluationQuestionsLimit) {
		this.evaluationQuestionsLimit = evaluationQuestionsLimit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Topic topic) {
		if (topic.getId() > getId()) {
			return -1;

		} else if (topic.getId() < getId()) {
			return 1;
		}
		return 0;
	}

}
