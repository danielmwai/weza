/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.mentor;

import com.tunaweza.core.business.dao.exceptions.mentor.MentorExistsException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorNotFoundException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.mentor.MentorTemplate;
import com.tunaweza.core.business.model.user.User;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */

@Repository(value = "mentorDao")
@Transactional
public class MentorDaoImpl extends GenericDaoImpl<Mentor> implements MentorDao {
@Autowired
SessionFactory sessionFactory;
        
	public Logger logger = Logger.getLogger(MentorDaoImpl.class);

	@Override
	public Mentor saveMentor(Mentor mentor) throws MentorExistsException {
		Mentor savedMentor = null;
		try {
			savedMentor = saveOrUpdate(mentor);
		} catch (ConstraintViolationException e) {
			throw new MentorExistsException();
		} catch (DataIntegrityViolationException e) {

			throw new MentorExistsException();
		}
		return savedMentor;
	}

	@Override
	public List<Mentor> getAllMentors() {
		return findAll();
	}

	@Override
	public Mentor getMentorById(Long mentorId)
			throws MentorNotFoundException {
		

		Query query =  sessionFactory.getCurrentSession().createQuery("SELECT i FROM " 
				+ getDomainClass().getName() + " i WHERE i.id = " + mentorId);

		if (query.list().size() == 0) {
			throw new MentorNotFoundException("Mentor with id " + mentorId
					+ " does not exist");
		}

		return (Mentor) query.list().get(0);
	}

	@Override
	public Mentor getMentorByUser(User user) throws MentorNotFoundException {
		

		Query query =  sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.user.id = "
				+ user.getId());

		if (query.list().size() == 0) {
			throw new MentorNotFoundException("Mentor does not exist");
		}

		return (Mentor) query.list().get(0);

	}

	@Override
	public List<MentorTemplate> getMentorTemplatesByMentor(long mentorId) {
		Mentor mentor = null;
		try {
			mentor = getMentorById(mentorId);
		} catch (MentorNotFoundException e) {
			return new ArrayList<MentorTemplate>();
		}

		List<MentorTemplate> mentorTemplates = mentor.getMentorTemplates();
		return mentorTemplates;
	}

	@Override
	public void saveMentorTemplatesToMentor(Mentor mentor,
			List<MentorTemplate> mentorTemplates) {
		mentor.setMentorTemplates(mentorTemplates);
		saveOrUpdate(mentor);

	}

}

