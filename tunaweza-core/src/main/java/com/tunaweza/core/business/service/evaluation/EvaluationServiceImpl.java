/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.evaluation;

import com.tunaweza.core.business.dao.evaluation.EvaluationDao;
import com.tunaweza.core.business.dao.exceptions.evaluation.EvaluationDoesNotExistException;
import com.tunaweza.core.business.model.evaluation.Evaluation;
import com.tunaweza.core.business.model.question.Question;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author naistech
 */
@Service("evaluationService")
//@Transactional
public class EvaluationServiceImpl implements EvaluationService {
	@Autowired
	public  EvaluationDao evaluationDao;

	@Override
	public Evaluation addEvaluation(
			Evaluation evaluation)
			throws  EvaluationDoesNotExistException {

		return evaluationDao.addEvaluation(evaluation);

	}

	@Override
	public Evaluation findEvaluationById(long id)
			throws EvaluationDoesNotExistException {

		return evaluationDao.findEvaluationById(id);

	}

	
	public Evaluation findEvaluationByName(String name)
			throws EvaluationDoesNotExistException {

		return evaluationDao.findEvaluationByName(name);

	}

	@Override
	public Evaluation findEvaluation(
			Evaluation evaluation)
			throws EvaluationDoesNotExistException {

		return evaluationDao.findEvaluation(evaluation);

	}

		public List<Evaluation> listAllEvaluations() {

		return evaluationDao.getAllEvaluations();

	}

	@Override
	public void updateEvaluation(Evaluation evaluation) {

		evaluationDao.updateEvaluation(evaluation);

	}

	@Override
	public Evaluation getEvaluationByModule(long moduleId)
			throws EvaluationDoesNotExistException {

		return evaluationDao.getEvaluationByModule(moduleId);
	}

	@Override
	public List<Long> getRandomModuleEvaluationQuestions(
			Evaluation evaluation) {
		List<Question> questions = evaluation.getQuestions();
		List<Long> randomQuestions = new ArrayList<Long>();
		Random randomNumberGenerator = new Random(Calendar.getInstance()
				.getTimeInMillis());
		for (int count = 0; count < evaluation.getNumberOfQuestions(); count++) {
			while (true) {
				int index = randomNumberGenerator.nextInt((int) evaluation
						.getQuestions().size());
				long questionId = questions.get(index).getId();
				if (!randomQuestions.contains(questionId)) {
					randomQuestions.add(questionId);
					break;
				}
			}
		}
		return randomQuestions;
	}

	public List<Question> getEvaluationQuestionsInModuleNotAssociatedWithTopic(
			Long moduleId) throws EvaluationDoesNotExistException {
		
		Evaluation evaluation = evaluationDao
				.getEvaluationByModule(moduleId);
		
		List<Question> questionsList = evaluation.getQuestions();
		
		List<Question> unassociatedQuestionsList = new ArrayList<Question>();
		
		if(questionsList != null)
		{
			for(Question question : questionsList)
			{
				if(question.getTopic() == null)
					unassociatedQuestionsList.add(question);
			}
		}
		
		return unassociatedQuestionsList;
	}


    


}
