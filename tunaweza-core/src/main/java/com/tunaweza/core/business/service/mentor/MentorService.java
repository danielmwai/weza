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


import com.tunaweza.core.business.dao.exceptions.mentor.MentorExistsException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorNotFoundException;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.mentor.MentorTemplate;
import com.tunaweza.core.business.model.user.User;
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
	 * @throws MentorDoesNotExistException
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
	public List<MentorTemplate> getMentorTemplatesByMentor(long mentorId);
	
	/**
	 * 
	 * @param mentor
	 * @param mentorTemplates
	 */
	public void saveMentorTemplatesToMentor(Mentor mentor, List<MentorTemplate> mentorTemplates);
	
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
	
}
