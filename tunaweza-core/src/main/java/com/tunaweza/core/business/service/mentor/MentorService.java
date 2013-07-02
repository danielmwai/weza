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

package com.tunaweza.core.business.service.mentor;

import com.tunaweza.core.business.Dao.exceptions.mentor.MentorNotFoundException;
import com.tunaweza.core.business.Dao.exceptions.mentor.MentorExistsException;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.exercise.ExerciseTransaction;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface MentorService {

	/**
	 * returns a list of all mentors
	 * @return all the mentors
	 */
	public List<Mentor> getAllMentors();
	
	/**
	 * returns a single Mentor
	 * @param mentorId
	 * @return a mentor
	 * @throws MentorNotFoundException
	 */
	public Mentor getMentorById(Long mentorId) throws MentorNotFoundException;
	
	/**
	 * saves or updates a mentor
	 * @param mentor
	 * @return a mentor
	 */
	public Mentor saveMentor(Mentor mentor) throws MentorExistsException;
	
	/**
	 * returns a single Mentor
	 * @param user
	 * @return
	 */
	public Mentor getMentorByUser(User user) throws MentorNotFoundException;
	
	/**
	 * 
	 * @param mentorId
	 * @return
	 */
	public List<Mentor> getMentorsByMentor(long mentorId);
	
	/**
	 * 
	 * @param mentor
	 * @param Mentors
	 */
	public void saveMentorsToMentor(Mentor mentor, List<Mentor> Mentors);
	
	/**
	 * 
	 * @return List of the total transactions of the last three months
	 */
	public List<Integer> getTransactionLastThreeMonths(String mentorId);
	
	
	/**
	 * 
	 * @return List names of the last three months
	 */
	public List<String> getNameLastMonths();
	
	
	/**
	 * 
	 * @return List of the total transactions of the last three months
	 */
	public List<String> getAvergeTimeToRead(String mentorId);
	
	
	/**
	 * 
	 * @return List of the total transactions of the last three months
	 */
	public List<String> getAvergeTimeToResponse(String mentorId);
	
	
	/**
	 * 
	 * @return List of the total transactions of the last three months
	 */
	public String getAvergeofList(List<Long> listofDiff);
	
 public Mentor addMentor(Mentor Mentor) 
			throws MentorExistsException;
	
	/**
	 * 
	 * @param Mentor
	 * @throws MentorNotFoundException
	 */
	public Mentor findMentorById(long id) 
			throws MentorNotFoundException;
	
	/**
	 * 
	 * @param Mentor
	 * @throws MentorNotFoundException
	 */
	public Mentor findMentorByName(String name)
			throws MentorNotFoundException;
	
	/**
	 * 
	 * @return <code>Mentor</code> list
	 * 
	 */
	public List<Mentor> listAllMentors();
	
	
	/**
	 * 
	 * @param Mentor
	 * @return <code>User</code> list
	 * 
	 */
	public List<Mentor> listMentorsByMentor(long MentorId);
	
	/**
	 * 
	 * @param Mentor
	 * @param module
	 * 
	 */
	public void saveExercisesToMentor(Mentor Mentor, List<Topic> exercises);
	
	/**
	 * 
	 * @param Mentor
	 * @return <code>Module</code> list
	 * 
	 */
	public List<Topic> getExercisesInMentor(long MentorId);
	
	/**
	 * 
	 * @param Mentor
	 * @return <code>Module</code> list
	 * 
	 */
	public List<Topic> getActiveExercisesInMentor(long MentorId);

	/**
	 * 
	 * @param Mentor
	 * @throws MentorExistsException
	 */
	public void editMentor(Mentor Mentor) throws MentorExistsException;

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
	public int numberOfExercisesByMentor(long mentorId,long moduleId);

}

