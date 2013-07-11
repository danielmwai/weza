/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.model.mentor;

import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.topic.Topic;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author naistech
 */

@Entity
@Table(name = MentorTemplate.TABLE_NAME)
public class MentorTemplate  implements
		Comparable<MentorTemplate> {
    @Id
    private Long id;

	public static final String TABLE_NAME = "mentor_template";
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "varchar(255) default 'Mentor Template Description'")
	private String description;

	@Column(columnDefinition = "varchar(255) default 'Mentor Template Name'")
	private String name;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "mentorTemplates")
	private List<Mentor> mentors;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "exercise_mentortemplate", joinColumns = @JoinColumn(name = "mentortemplate"), inverseJoinColumns = @JoinColumn(name = "exercise"))
	private List<Topic> exercises;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_module")
	private Module module;

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
	 * @param mentors
	 *            the mentors to set
	 */
	public void setMentors(List<Mentor> mentors) {
		this.mentors = mentors;
	}

	/**
	 * @return the mentors
	 * 
	 */
	public List<Mentor> getMentors() {
		return mentors;
	}

	/**
	 * @return the exercises
	 */

	public List<Topic> getExercises() {
		return exercises;
	}

	/**
	 * 
	 * @param exercises
	 *            the exercises to set
	 */

	public void setExercises(List<Topic> exercises) {
		this.exercises = exercises;
	}

	/**
	 * 
	 * @param exercise
	 *            the exercise to add
	 */

	public void addExercise(Topic exercise) {
		this.exercises.add(exercise);
	}

	/**
	 * 
	 * @param exercise
	 *            the exercise to remove
	 */

	public void removeExercise(Topic exercise) {
		this.exercises.remove(exercise);
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

	/**
	 * @return the module
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * @param module
	 *            the module to set
	 */
	public void setModule(Module module) {
		this.module = module;
	}

	@Override
	public int compareTo(MentorTemplate mentorTemplate) {
		if (mentorTemplate.getId() > getId()) {
			return -1;

		} else if (mentorTemplate.getId() < getId()) {
			return 1;
		}
		return 0;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

