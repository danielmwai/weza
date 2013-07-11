/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.mentor;

import com.tunaweza.core.business.dao.exceptions.mentor.MentorTemplateExistsException;
import com.tunaweza.core.business.dao.exceptions.mentor.MentorTemplateNotFoundException;
import com.tunaweza.core.business.dao.mentor.MentorTemplateDao;
import com.tunaweza.core.business.model.exercise.ExerciseTransaction;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.model.mentor.MentorTemplate;
import com.tunaweza.core.business.model.topic.Topic;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */
@Service("mentorTemplateService")
@Transactional
public class MentorTemplateServiceImpl implements MentorTemplateService 
{
	@Autowired
	MentorTemplateDao mentorTemplateDAO;

	@Override
	public MentorTemplate addMentorTemplate(MentorTemplate mentorTemplate)
			throws MentorTemplateExistsException {
		return mentorTemplateDAO.saveMentorTemplate(mentorTemplate);

	}
	
	@Override
	public MentorTemplate findMentorTemplateById(long id)
			throws MentorTemplateNotFoundException {
		return mentorTemplateDAO.findById(id);
	}

	@Override
	public MentorTemplate findMentorTemplateByName(String name)
			throws MentorTemplateNotFoundException {
		return mentorTemplateDAO.findMentorTemplateByName(name);
	}

	@Override
	public List<MentorTemplate> listAllMentorTemplates() {
		return mentorTemplateDAO.findAll();
	}

	@Override
	public List<Mentor> listMentorsByMentorTemplate(long mentorTemplateId) {
		return mentorTemplateDAO.getAllMentorsByMentorTemplate(mentorTemplateId);
	}

	@Override
	public void saveExercisesToMentorTemplate(MentorTemplate mentorTemplate,
			List<Topic> exercises) {
		mentorTemplateDAO.saveExercisesToMentorTemplate(mentorTemplate, exercises);

	}

	@Override
	public List<Topic> getExercisesInMentorTemplate(long mentorTemplateId) {
		return mentorTemplateDAO.getExercisesInMentorTemplate(mentorTemplateId);
	}

	@Override
	public List<Topic> getActiveExercisesInMentorTemplate(
			long mentorTemplateId) {
		return mentorTemplateDAO.
				getActiveExercisesInMentorTemplate(mentorTemplateId);
	}

	@Override
	public void editMentorTemplate(MentorTemplate mentorTemplate) throws MentorTemplateExistsException {
		mentorTemplateDAO.updateMentorTemplate(mentorTemplate);
		
	}

	@Override
	public int countExerciseTransactions(long moduleId, long mentorId) {
		return mentorTemplateDAO.countExerciseTransactions(moduleId, mentorId);
	}

	@Override
	public List<ExerciseTransaction> getExerciseTransactions(
			long moduleId, long mentorId) {
		return mentorTemplateDAO.getExerciseTransactions(moduleId, mentorId);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.mentor.MentorTemplateService#getPaginatedExerciseTransactions
	 * (int, int, long, long)
	 */
	@Override
	public List<ExerciseTransaction> getPaginatedExerciseTransactions(int startIndex, int pageSize, long moduleId, long mentorId) {
		return mentorTemplateDAO.getPaginatedExerciseTransactionsList(startIndex, pageSize, moduleId, mentorId);
	}
	
	@Override
	public int numberOfExercisesByMentorTemplate(long mentorId, long moduleId) {
		// TODO Auto-generated method stub
		return mentorTemplateDAO.numberOfExercisesByMentorTemplate(mentorId, moduleId);
	}

}
