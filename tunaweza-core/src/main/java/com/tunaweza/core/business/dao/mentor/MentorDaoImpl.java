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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository(value = "mentorDAO")
@Transactional
public class MentorDaoImpl extends GenericDaoImpl<Mentor> implements MentorDAO {

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
			throws MentorDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM " 
				+ getDomainClass().getName() + " i WHERE i.id = " + mentorId);

		if (query.list().size() == 0) {
			throw new MentorDoesNotExistException("Mentor with id " + mentorId
					+ " does not exist");
		}

		return (Mentor) query.list().get(0);
	}

	@Override
	public Mentor getMentorByUser(User user) throws MentorDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.user.id = "
				+ user.getId());

		if (query.list().size() == 0) {
			throw new MentorDoesNotExistException("Mentor does not exist");
		}

		return (Mentor) query.list().get(0);

	}

	@Override
	public List<Mentor> getMentorByMentor(long mentorId) {
		Mentor mentor = null;
		try {
			mentor = getMentorById(mentorId);
		} catch (MentorDoesNotExistException e) {
			return new ArrayList<Mentor>();
		}

		List<Mentor> mentor = mentor.getMentor();
		return mentor;
	}

	@Override
	public void saveMentorToMentor(Mentor mentor,
			List<Mentor> mentor) {
		mentor.setMentor(mentor);
		saveOrUpdate(mentor);

	}
        ///refactor this code as it was duplicated on jjteach
        @Override
	public Mentor findMentorById(long id)
			throws MentorNotFoundException {
		Mentor mentor = findById(id);
		if (mentor == null)
			throw new MentorNotFoundException();
		return mentor;
	}

	@Override
	public MentorTemplate findMentorByName(String name)
			throws MentorTemplateNotFoundException {
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session
				.createQuery("SELECT i FROM " + getDomainClass().getName()
						+ " i WHERE i.name='" + name + "'");

		Mentor mentor = null;
		if (query.list().size() > 0) {
			mentor = (Mentor) query.list().get(0);
		} else {
			throw new MentorNotFoundException("MentorTemplate with "
					+ "name : " + name + " was not found");
		}
		return mentor;
	}

	@Override
	public Mentor findMentor(Mentor mentor)
			throws MentorNotFoundException {
		Mentor dbMentor = findById(mentor.getId());
		if (dbMentor == null) {
			throw new MentorNotFoundException();
		}
		return dbMentor;
	}

	@Override
	public List<Mentor> getAllMentorTemplates() {
		return findAll();
	}

	@Override
	public Mentor saveMentor(Mentor mentor)
			throws MentorExistsException {
		Mentor duplicate = null;
		Mentor savedMentorTemplate = null;
		try {
			duplicate = findMentorTemplateByName(mentor.getName());
		} catch (MentorTemplateNotFoundException e) {
		}
		if (duplicate != null) {
			throw new MentorTemplateExistsException();
		}
		savedMentor= saveOrUpdate(mentor);
		return savedMentor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mentor> getAllMentorsByMentor(long mentorId) {
		List<Mentor> mentors = null;
		try {
			Mentor mentor = findMentorById(mentorId);
			mentors = mentor.getMentors();
		} catch (MentorNotFoundException e) {
			return (new ArrayList<Mentor>());
		}
		return mentors;
	}

	@Override
	public void saveExercisesToMentor(Mentor mentor,
			List<Topic> exercises) {
		mentor.setExercises(exercises);
		saveOrUpdate(mentor);
	}

	@Override
	public List<Topic> getExercisesInMentor(long mentorTemplateId) {
		Mentor mentor = null;
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.id = "
				+ mentorTemplateId);

		if (query.list().size() > 0) {
			mentor = (Mentor) query.list().get(0);
		}
		List<Topic> exercises = new ArrayList<Topic>();
		exercises = mentor.getExercises();
		return exercises;
	}

	@Override
	public List<Topic> getActiveExercisesInMentor(long mentorTemplateId) {
		Mentor mentor = null;
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.id = "
				+ mentorTemplateId);

		if (query.list().size() > 0) {
			mentor = (Mentor) query.list().get(0);
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
	public void updateMentor(Mentor mentor) {
		saveOrUpdate(mentor);

	}

	@Override
	public int countExerciseTransactions(long moduleId, long mentorId) {
		int count = 0;

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
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

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
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
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery(
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
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session
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
