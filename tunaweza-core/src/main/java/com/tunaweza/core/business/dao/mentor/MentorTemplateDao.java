package com.tunaweza.core.business.dao.mentor;

import com.tunaweza.core.business.dao.exceptions.mentor.MentorExistsException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorTemplateExistsException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorTemplateNotFoundException;
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.mentor.MentorTemplate;
import com.tunaweza.core.business.model.topic.Topic;
import java.util.List;




public interface MentorTemplateDao extends GenericDao<MentorTemplate> 
{
	/**
	 * 
	 * @param uid
	 * @return <code>MentorTemplate</code>
	 * @throws MentorTemplateNotFoundException
	 */
	public MentorTemplate findMentorTemplateById(long id) 
			throws MentorTemplateNotFoundException;
	
	/**
	 * 
	 * @param name
	 * @return <code>MentorTemplate</code>
	 * @throws MentorTemplateNotFoundException
	 */
	public MentorTemplate findMentorTemplateByName(String name) 
			throws MentorTemplateNotFoundException;

	/**
	 * 
	 * @param MentorTemplate
	 * @return <code>MentorTemplate</code>
	 * @throws MentorTemplateNotFoundException
	 */
	public MentorTemplate findMentorTemplate(MentorTemplate mentorTemplate) 
			throws MentorTemplateNotFoundException;

	/**
	 * 
	 * @return
	 */
	public List<MentorTemplate> getAllMentorTemplates();

	/**
	 * 
	 * @param MentorTemplate
	 */
	public MentorTemplate saveMentorTemplate(MentorTemplate mentorTemplate) 
			throws MentorTemplateExistsException;
	
	/**
	 * 
	 * @param MentorTemplate, module
	 */
	public void saveExercisesToMentorTemplate(MentorTemplate mentorTemplate,List<Topic> exercises);
	
		
	/**
	 * 
	 * @param MentorTemplateId
	 * @return 
	 */
	public List<Mentor> getAllMentorsByMentorTemplate(long mentorTemplateId);	
	
	/**
	 * 
	 * @param MentorTemplateId
	 * @return 
	 */
	public List<Topic> getExercisesInMentorTemplate(long mentorTemplateId);
	
	/**
	 * 
	 * @param MentorTemplateId
	 * @return 
	 */
	public List<Topic> getActiveExercisesInMentorTemplate(long mentorTemplateId);

	/**
	 * 
	 * @param mentorTemplate
	 */
	public void updateMentorTemplate(MentorTemplate mentorTemplate);
	
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
	public List<ExerciseTransaction> getExerciseTransactions(
			long moduleId, long mentorId);
	
	/**
	 * 
	 * @param startIndex
	 * @param pageSize
	 * @param moduleId
	 * @param mentorId
	 * @return
	 */
	public List<ExerciseTransaction> getPaginatedExerciseTransactionsList(int startIndex, int pageSize, long moduleId, long mentorId);
	
	/**
	 * 
	 * @param mentorId
	 * @param moduleId
	 * @return
	 */
	public int numberOfExercisesByMentorTemplate(long mentorId, long moduleId);

}
