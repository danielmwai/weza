/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.mentor;

/**
 *
 * @author Daniel Mwai
 */


import com.tunaweza.core.business.dao.exceptions.mentor.MentorExistsException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorNotFoundException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorTemplateExistsException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorTemplateNotFoundException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import com.tunaweza.core.business.model.mentor.MentorTemplate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository(value = "mentorTemplateDao")
@Transactional
public class MentorTemplateDaoImpl extends GenericDaoImpl<MentorTemplate> implements MentorTemplateDao {

	public Logger logger = Logger.getLogger(MentorDaoImpl.class);
@Autowired
SessionFactory sessionFactory;
        
	@Override
	public MentorTemplate findMentorTemplateById(long id)
			throws MentorTemplateNotFoundException {
		MentorTemplate mentor = findById(id);
		if (mentor == null)
			throw new MentorTemplateNotFoundException();
		return mentor;
	}

	@Override
	public MentorTemplate findMentorTemplateByName(String name)
			throws MentorTemplateNotFoundException {
		Query query = sessionFactory.getCurrentSession()
				.createQuery("SELECT i FROM " + getDomainClass().getName()
						+ " i WHERE i.name='" + name + "'");

		MentorTemplate mentorTemplate = null;
		if (query.list().size() > 0) {
			mentorTemplate = (MentorTemplate) query.list().get(0);
		} else {
			throw new MentorTemplateNotFoundException("Mentor with "
					+ "name : " + name + " was not found");
		}
		return mentorTemplate;
	}

	@Override
	public MentorTemplate findMentorTemplate(MentorTemplate mentorTemplate)
			throws MentorTemplateNotFoundException {
		MentorTemplate dbMentorTemplate = findById(mentorTemplate.getId());
		if (dbMentorTemplate == null) {
			throw new MentorTemplateNotFoundException();
		}
		return dbMentorTemplate;
	}

	@Override
	public List<MentorTemplate> getAllMentorTemplates() {
		return findAll();
	}

	@Override
	public MentorTemplate saveMentorTemplate(MentorTemplate mentor)
			throws MentorTemplateExistsException {
		MentorTemplate duplicate = null;
		MentorTemplate savedMentor = null;
		try {
			duplicate = findMentorTemplateByName(mentor.getName());
		} catch (MentorTemplateNotFoundException e) {
		}
		if (duplicate != null) {
			throw new MentorTemplateExistsException();
		}
		savedMentor = saveOrUpdate(mentor);
		return savedMentor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mentor> getAllMentorsByMentorTemplate(long mentorId) {
		List<Mentor> mentors = null;
		try {
			MentorTemplate mentor = findMentorTemplateById(mentorId);
			mentors = mentor.getMentors();
		} catch (MentorTemplateNotFoundException e) {
			return (new ArrayList<Mentor>());
		}
		return mentors;
	}

	@Override
	public void saveExercisesToMentorTemplate(MentorTemplate mentor,
			List<Topic> exercises) {
		mentor.setExercises(exercises);
		saveOrUpdate(mentor);
	}

	@Override
	public List<Topic> getExercisesInMentorTemplate(long mentorId) {
		MentorTemplate mentorTemplate = null;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.id = "
				+ mentorId);

		if (query.list().size() > 0) {
			mentorTemplate = (MentorTemplate) query.list().get(0);
		}
		List<Topic> exercises = new ArrayList<Topic>();
		exercises = mentorTemplate.getExercises();
		return exercises;
	}

	@Override
	public List<Topic> getActiveExercisesInMentorTemplate(long mentorId) {
		MentorTemplate mentor = null;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.id = "
				+ mentorId);

		if (query.list().size() > 0) {
			mentor = (MentorTemplate) query.list().get(0);
		}
		List<Topic> exercises = new ArrayList<Topic>();
		exercises = mentor.getExercises();
		for (Topic exercise : exercises) {
			if (exercise.getEnabled() != 1) {
				exercises.remove(exercise);
			}
		}
		return exercises;
	}

	@Override
	public void updateMentorTemplate(MentorTemplate mentor) {
		saveOrUpdate(mentor);

	}

	@Override
	public int countExerciseTransactions(long moduleId, long mentorId) {
		int count = 0;


		Query query = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT COUNT(*) FROM (SELECT * FROM exercise_transaction WHERE id IN"
						+ " (SELECT MAX(id) FROM exercise_transaction GROUP BY exercise_id)) et JOIN"
						+ " exercise_transaction_type ett ON ett.id = et.exercise_transactiontype_id JOIN"
						+ " student_exercise se ON se.id = et.exercise_id JOIN topics t ON t.id = se.topic_id"
						+ " WHERE t.is_exercise=1 AND ett.name =? AND t.id IN (SELECT DISTINCT(em.exercise)"
						+ " FROM exercise_mentortemplate em JOIN mentor_template mt ON mt.id = em.mentortemplate"
						+ " WHERE mt.id_module=? AND mt.id IN (SELECT mtm.mentortemplate FROM "
						+ " mentortemplate_mentor mtm WHERE mtm.mentor=?))");

		query.setString(0, "StudentToMentor");
		query.setLong(1, moduleId);
		query.setLong(2, mentorId);
		if (query.list().size() > 0) {
			java.math.BigInteger countedEx = (java.math.BigInteger) query
					.list().get(0);
			count += countedEx.intValue();
		}
		return count;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExerciseTransaction> getExerciseTransactions(long moduleId,
			long mentorId) {


		Query query = sessionFactory.getCurrentSession()
				.createSQLQuery(
						"SELECT * FROM (SELECT * FROM exercise_transaction WHERE id IN"
								+ " (SELECT MAX(id) FROM exercise_transaction GROUP BY exercise_id)) et JOIN"
								+ " exercise_transaction_type ett ON ett.id = et.exercise_transactiontype_id JOIN"
								+ " student_exercise se ON se.id = et.exercise_id JOIN topics t ON t.id = se.topic_id"
								+ " WHERE t.is_exercise=1 AND ett.name =? AND t.id IN (SELECT DISTINCT(em.exercise)"
								+ " FROM exercise_mentortemplate em JOIN mentor_template mt ON mt.id = em.mentortemplate"
								+ " WHERE mt.id_module=? AND mt.id IN (SELECT mtm.mentortemplate FROM "
								+ " mentortemplate_mentor mtm WHERE mtm.mentor=?))")
				.addEntity(ExerciseTransaction.class);

		query.setString(0, "StudentToMentor");
		query.setLong(1, moduleId);
		query.setLong(2, mentorId);
		return query.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ExerciseTransaction> getPaginatedExerciseTransactionsList(int startIndex, int pageSize, long moduleId, long mentorId) {

		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT * FROM (SELECT * FROM exercise_transaction WHERE id IN"
						+ " (SELECT MAX(id) FROM exercise_transaction GROUP BY exercise_id)) et JOIN"
						+ " exercise_transaction_type ett ON ett.id = et.exercise_transactiontype_id JOIN"
						+ " student_exercise se ON se.id = et.exercise_id JOIN topics t ON t.id = se.topic_id"
						+ " WHERE t.is_exercise=1 AND ett.name =? AND t.id IN (SELECT DISTINCT(em.exercise)"
						+ " FROM exercise_mentortemplate em JOIN mentor_template mt ON mt.id = em.mentortemplate"
						+ " WHERE mt.id_module=? AND mt.id IN (SELECT mtm.mentortemplate FROM "
						+ " mentortemplate_mentor mtm WHERE mtm.mentor=?))")
				.addEntity(ExerciseTransaction.class);
		
		query.setString(0, "StudentToMentor");
		query.setLong(1, moduleId);
		query.setLong(2, mentorId);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
	@Override
	public int numberOfExercisesByMentorTemplate(long mentorId, long moduleId) {

		Query query = sessionFactory.getCurrentSession()
				.createSQLQuery(
						"SELECT COUNT(*) FROM (SELECT * FROM exercise_transaction WHERE id IN"
								+ " (SELECT MAX(id) FROM exercise_transaction GROUP BY exercise_id)) et JOIN"
								+ " exercise_transaction_type ett ON ett.id = et.exercise_transactiontype_id JOIN"
								+ " student_exercise se ON se.id = et.exercise_id JOIN topics t ON t.id = se.topic_id"
								+ " WHERE t.is_exercise=1 AND ett.name =? AND t.id IN (SELECT DISTINCT(em.exercise)"
								+ " FROM exercise_mentortemplate em JOIN mentor_template mt ON mt.id = em.mentortemplate"
								+ " WHERE mt.id_module=? AND mt.id IN (SELECT mtm.mentortemplate FROM "
								+ " mentortemplate_mentor mtm WHERE mtm.mentor=?))")
				.addEntity(ExerciseTransaction.class);

		query.setString(0, "StudentToMentor");
		query.setLong(1, moduleId);
		query.setLong(2, mentorId);
		java.math.BigInteger count = (java.math.BigInteger) query.list().get(0);
		return count.intValue();

	}

}
