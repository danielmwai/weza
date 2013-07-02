/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.mentor;

import com.tunaweza.core.business.dao.exceptions.mentor.MentorExistsException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorNotFoundException;
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.service.exercise.ExerciseTransaction;
import java.util.List;

/**
 *
 * @author Daniel Mwai
 */
public interface MentorDao extends GenericDao<Mentor> 
{
	/**
	 * 
	 * @param uid
	 * @return <code>Mentor</code>
	 * @throws MentorNotFoundException
	 */
	public Mentor findMentorById(long id) 
			throws MentorNotFoundException;
	
	/**
	 * 
	 * @param name
	 * @return <code>Mentor</code>
	 * @throws MentorNotFoundException
	 */
	public Mentor findMentorByName(String name) 
			throws MentorNotFoundException;

	/**
	 * 
	 * @param Mentor
	 * @return <code>Mentor</code>
	 * @throws MentorNotFoundException
	 */
	public Mentor findMentor(Mentor mentor) 
			throws MentorNotFoundException;

	/**
	 * 
	 * @return
	 */
	public List<Mentor> getAllMentor();

	/**
	 * 
	 * @param Mentor
	 */
	public Mentor saveMentor(Mentor mentor) 
			throws MentorExistsException;
	
	/**
	 * 
	 * @param Mentor, module
	 */
	public void saveExercisesToMentor(Mentor mentor,List<Topic> exercises);
	
		
	/**
	 * 
	 * @param MentorId
	 * @return 
	 */
	public List<Mentor> getAllMentorsByMentor(long MentorId);	
	
	/**
	 * 
	 * @param MentorId
	 * @return 
	 */
	public List<Topic> getExercisesInMentor(long MentorId);
	
	/**
	 * 
	 * @param MentorId
	 * @return 
	 */
	public List<Topic> getActiveExercisesInMentor(long mentorId);

	/**
	 * 
	 * @param Mentor
	 */
	public void updateMentor(Mentor mentor);
	
	/**
	 * 
	 * @param MentorId
	 * @return
	 */
	public int countExerciseTransactions(long moduleId, long mentorId);
	
	/**
	 * 
	 * @param MentorId
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
	public int numberOfExercisesByMentor(long mentorId, long moduleId);

}
