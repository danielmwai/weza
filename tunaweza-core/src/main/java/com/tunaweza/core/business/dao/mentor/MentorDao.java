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
import com.tunaweza.core.business.model.mentor.MentorTemplate;
import com.tunaweza.core.business.model.user.User;
import java.util.List;

/**
 *
 * @author Daniel Mwai
 */
public interface MentorDao extends  GenericDao<Mentor> {

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
		public void saveMentorTemplatesToMentor(Mentor mentor,
				List<MentorTemplate> mentorTemplates);
}
