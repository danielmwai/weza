/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.model.course;

import com.tunaweza.core.business.model.persistence.AbstractPersistentEntity;
import com.tunaweza.core.business.model.status.Status;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Daniel Mwai
 */
@Entity
@Table(name = Course.TABLE_NAME)
public class Course extends AbstractPersistentEntity implements
		Comparable<Course> {

	public static final String TABLE_NAME = "course_template";

	private static final long serialVersionUID = 1L;

	@Column(columnDefinition="varchar(255) default 'Course Template Desc'")
	private String description;

	@Column(columnDefinition="varchar(255) default 'Course Template Name'")
	private String name;
	
	
	@Transient
	private Status status;
	
	@Column(columnDefinition="boolean default true")
	private boolean evaluated=true;

	@ElementCollection
	@CollectionTable(name = "course_modules", joinColumns = @JoinColumn(name = "course"))
	private List<EmbeddableModule> modules;
	
	@ElementCollection
	@CollectionTable(
	        name="course_pre_requisite",
	        joinColumns=@JoinColumn(name="course_template_id"))
	private List<CoursePreRequisite> CoursePreReQuisites;

	/**
	 * @return the modules
	 */

	public List<EmbeddableModule> getModules() {
		return modules;
	}

	/**
	 * @param modules
	 *            the modules to set
	 */

	public void setModules(List<EmbeddableModule> modules) {
		this.modules = modules;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isEvaluated() {
		return evaluated;
	}

	public void setEvaluated(boolean evaluated) {
		this.evaluated = evaluated;
	}

	
	public List<CoursePreRequisite> getCoursePreReQuisites() {
		return CoursePreReQuisites;
	}

	public void setCoursePreReQuisites(List<CoursePreRequisite> coursePreReQuisites) {
		CoursePreReQuisites = coursePreReQuisites;
	}

	@Override
	public int compareTo(CourseTemplate courseTemplate) {
		if (courseTemplate.getId() > getId()) {
			return -1;

		} else if (courseTemplate.getId() < getId()) {
			return 1;
		}
		return 0;
	}

    @Override
    public int compareTo(Course t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
