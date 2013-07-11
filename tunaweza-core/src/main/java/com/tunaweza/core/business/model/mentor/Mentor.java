/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.model.mentor;

/**
 *
 * @author Daniel Mwai
 */
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import com.tunaweza.core.business.model.user.User;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;




@Entity
@Table(name = Mentor.TABLE_NAME)
public class Mentor  {
    @Id
    private Long id;
	
	public static final String TABLE_NAME = "mentor";
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_user", unique=true)
	private User user;

	@OneToMany(mappedBy = "mentor",fetch = FetchType.LAZY)
	private List<ExerciseTransaction> exerciseTransactions;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "mentortemplate_mentor", joinColumns = @JoinColumn(name = "mentor"), inverseJoinColumns = @JoinColumn(name = "mentortemplate"))
	private List<MentorTemplate> mentorTemplates;
	
	public User getUser() {
		return user;
	}

	/**
	 * @param jJTeachUser
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the exerciseTransactions
	 */
	public List<ExerciseTransaction> getExerciseTransactions() {
		return exerciseTransactions;
	}

	/**
	 * @param exerciseTransactions
	 */
	public void setExerciseTransactions(
			List<ExerciseTransaction> exerciseTransactions) {
		this.exerciseTransactions = exerciseTransactions;
	}

	/**
	 * @return the mentorTemplates
	 */
	public List<MentorTemplate> getMentorTemplates() {
		return mentorTemplates;
	}

	/**
	 * @param mentorTemplates the mentorTemplates to set
	 */
	public void setMentorTemplates(List<MentorTemplate> mentorTemplates) {
		this.mentorTemplates = mentorTemplates;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
