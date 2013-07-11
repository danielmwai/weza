/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.mentor;

import com.tunaweza.core.business.dao.exceptions.mentor.MentorTemplateExistsException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorTemplateNotFoundException;
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.mentor.MentorTemplate;
import com.tunaweza.core.business.model.topic.Topic;
import java.util.List;

/**
 *
 * @author naistech
 */
public interface MentorTemplateService {
 
	/**
	 * 
	 * @param mentorTemplate
     * @return 
	 * @throws MentorTemplateExistsException
	 */
	public MentorTemplate addMentorTemplate(MentorTemplate mentorTemplate) 
			throws MentorTemplateExistsException;
	
	/**
	 * 
	 * @param mentorTemplate
	 * @throws MentorTemplateNotFoundException
	 */
	public MentorTemplate findMentorTemplateById(long id) 
			throws MentorTemplateNotFoundException;
	
	/**
	 * 
	 * @param mentorTemplate
	 * @throws MentorTemplateNotFoundException
	 */
	public MentorTemplate findMentorTemplateByName(String name)
			throws MentorTemplateNotFoundException;
	
	/**
	 * 
	 * @return <code>MentorTemplate</code> list
	 * 
	 */
	public List<MentorTemplate> listAllMentorTemplates();
	
	
	/**
	 * 
	 * @param mentorTemplate
	 * @return <code>User</code> list
	 * 
	 */
	public List<Mentor> listMentorsByMentorTemplate(long mentorTemplateId);
	
	/**
	 * 
	 * @param mentorTemplate
	 * @param module
	 * 
	 */
	public void saveExercisesToMentorTemplate(MentorTemplate mentorTemplate, List<Topic> exercises);
	
	/**
	 * 
	 * @param mentorTemplate
	 * @return <code>Module</code> list
	 * 
	 */
	public List<Topic> getExercisesInMentorTemplate(long mentorTemplateId);
	
	/**
	 * 
	 * @param mentorTemplate
	 * @return <code>Module</code> list
	 * 
	 */
	public List<Topic> getActiveExercisesInMentorTemplate(long mentorTemplateId);

	/**
	 * 
	 * @param mentorTemplate
	 * @throws MentorTemplateExistsException
	 */
	public void editMentorTemplate(MentorTemplate mentorTemplate) throws MentorTemplateExistsException;

	/**
	 * 
	 * @param mentorTemplateId
	 * @return
	 */
	public int countExerciseTransactions(long moduleId, long mentorId);
	
	/**
	 * 
	 * @param mentorTemplateId
	 * @return
	 */
	public List<ExerciseTransaction> getExerciseTransactions(long moduleId, long mentorId);

	
	/**
	 * 
	 * @param startIndex
	 *            The start index
	 * @param pageSize
	 *            The number of <code>ExerciseTransaction</code>s to return.
	 * @param moduleId
	 * 			  The module id of <code>ExerciseTransaction</code>s to return
	 * @param mentorId
	 * 			  The mentor id
	 * @return A <code>List</code> with <code>ExerciseTransaction Object</code>s.
	 */
	public List<ExerciseTransaction> getPaginatedExerciseTransactions(int startIndex, int pageSize, long moduleId, long mentorId);
	
	/**
	 * 
	 * @param mentorId
	 * @param moduleId
	 * @return
	 */
	public int numberOfExercisesByMentorTemplate(long mentorId,long moduleId);

}
