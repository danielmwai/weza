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

package com.tunaweza.core.business.model.module;
import com.tunaweza.core.business.model.persistence.AbstractPersistentEntity;
import com.tunaweza.core.business.model.status.Status;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Entity
@Table(name = Module.TABLE_NAME)
public class Module extends AbstractPersistentEntity implements
		Comparable<Module> {
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "module";

	@Column(name = "enabled")
	private int enabled = 1;

	@Column(name = "description",columnDefinition = "varchar(255) default 'Module Description'")
	private String description;

	@Column(name = "name", nullable = false, unique = true,columnDefinition = "varchar(255) default 'Module Name'")
	private String name;

	@Transient
	private Status status;

	@Column(name = "time_to_complete",columnDefinition = "varchar(255) default 'Time To Complete'")
	private String timeToComplete;

	@Column(columnDefinition = "boolean default 1")
	private boolean evaluated = true;

	@Column(name = "mentoring", columnDefinition = "boolean default 1", nullable = false)
	private boolean mentoring = true;

	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
	List<Topic> topics;

	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
	List<Mentor> Mentors;

	@OneToOne(mappedBy = "module")
	private EvaluationTemplate evaluationTemplate;

	//Store the IDs for the prerequisite modules
	@ElementCollection
	@CollectionTable(name = "module_prerequisites", joinColumns = @JoinColumn(name = "module_id"))
	@Column(name = "prerequisites")
	List<String> preRequisites;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the timeToComplete
	 */
	public String getTimeToComplete() {
		return timeToComplete;
	}

	/**
	 * @param timeToComplete
	 *            the timeToComplete to set
	 */
	public void setTimeToComplete(String timeToComplete) {
		this.timeToComplete = timeToComplete;
	}

	public boolean isEvaluated() {
		return evaluated;
	}

	public void setEvaluated(boolean evaluated) {
		this.evaluated = evaluated;
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

	/**
	 * @return
	 */
	public List<Topic> getTopics() {
		return topics;
	}

	/**
	 * @param topics
	 */
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	/**
	 * @param enabled
	 */
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return
	 */
	public int getEnabled() {
		return enabled;
	}

	/**
	 * @return the Mentors
	 */
	public List<Mentor> getMentors() {
		return Mentors;
	}

	/**
	 * @param Mentors
	 *            the Mentors to set
	 */
	public void setMentors(List<Mentor> Mentors) {
		this.Mentors = Mentors;
	}

	public EvaluationTemplate getEvaluationTemplate() {
		return evaluationTemplate;
	}

	public void setEvaluationTemplate(EvaluationTemplate evaluationTemplate) {
		this.evaluationTemplate = evaluationTemplate;
	}

	@Override
	public int compareTo(Module module) {
		if (module.getId() > getId()) {
			return -1;

		} else if (module.getId() < getId()) {
			return 1;
		}
		return 0;
	}

	/**
	 * @return the mentoring
	 */
	public boolean isMentoring() {
		return mentoring;
	}

	/**
	 * @param mentoring
	 *            the mentoring to set
	 */
	public void setMentoring(boolean mentoring) {
		this.mentoring = mentoring;
	}

	public List<String> getPreRequisites() {
		return preRequisites;
	}

	public void setPreRequisites(List<String> preRequisites) {
		this.preRequisites = preRequisites;
	}
}