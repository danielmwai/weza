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

package com.tunaweza.core.business.Dao.evaluation;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "evaluationTransactionDao")
@Transactional
public class EvaluationTransactionDaoImpl extends
		GenericDaoImpl<EvaluationTransaction> implements EvaluationTransactionDao {

	@Override
	public Evaluation findEvaluationById(long uid) throws EvaluationDoesNotExistException {
		Evaluation evaluation = findById(uid);
		if (evaluation == null) {
			/*throw new EvaluationTemplateDoesNotExistException("EvaluationTemplate with ID : " + uid
					+ " does not exist");*/
			return null;
		}

		return evaluation;
	}

	@Override
	public Evaluation findEvaluationByName(String name) throws EvaluationDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.name = '" + name
				+ "'");

		Evaluation evaluation = null;

		if (query.list().size() > 0) {
			evaluation = (Evaluation) query.list().get(0);
		} else {
			throw new EvaluationDoesNotExistException("EvaluationTemplate with name : " + name
					+ " does not exist");
		}

		return evaluation;

	}

	@Override
	public Evaluation findEvaluation(Evaluation evaluation) throws EvaluationDoesNotExistException {

		Evaluation evaluation = findById(evaluation.getId());
		if (evaluation == null) {
			throw new EvaluationDoesNotExistException();
		}
		return evaluation;
	}

	@Override
	public Evaluation addEvaluation(Evaluation evaluation) throws EvaluationExistsException {

		Evaluation evaluation=null;
		try {
			evaluation = findEvaluationByName(evaluation.getName());
		} catch (EvaluationDoesNotExistException e) 
		{			
			//e.printStackTrace();
		}
		
		if(evaluation != null){
			throw new EvaluationExistsException();
		}

		Evaluation savedEvaluation = saveOrUpdate(evaluation);
		logger.info("Saved EvaluationTemplate Id" + savedEvaluation.getId());
		return savedEvaluation;
	}

	@Override
	public List<Evaluation> getAllEvaluation() {
		return findAll();
	}

	@Override
	public void updateEvaluation(Evaluation evaluationTemplate){
	
		saveOrUpdate(evaluation);
	
	}

	@Override
	public Evaluation getEvaluationByModule(long moduleId) throws EvaluationDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.module.id = '" + moduleId
				+ "'");

		Evaluation evaluation = null;

		if (query.list().size() > 0) {
			evaluation = (Evaluation) query.list().get(0);
		} else {
			throw new EvaluationDoesNotExistException("EvaluationTemplate does not exist");
		}

		return evaluation;
	}

	@Override
	public Evaluation getEvaluationByModuleNoSession(long moduleId, String companyDBName) throws EvaluationDoesNotExistException {
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createSQLQuery("SELECT * FROM " + companyDBName +
				".evaluation_template i WHERE i.module_id = '" + moduleId
				+ "'").addEntity(Evaluation.class);

		Evaluation evaluationTemplate = null;

		if (query.list().size() > 0) {
			evaluationTemplate = (Evaluation) query.list().get(0);
		} else {
			throw new EvaluationDoesNotExistException("EvaluationTemplate does not exist");
		}

		return evaluation;
	}

	

	@Override
	public List<EvaluationTransaction> getEvaluationTransactions() {
		return findAll();
	}

	@Override
	public EvaluationTransaction getEvaluationTransaction(Long id) {
		return findById(id);
	}

	@Override
	public EvaluationTransaction saveEvaluationTransaction(EvaluationTransaction evaluationTransaction) 
	{
		return saveOrUpdate(evaluationTransaction);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EvaluationTransaction> getEvaluationTransactionByStudentEvaluation(
			StudentEvaluation studentEvaluation) 
	{
		List<EvaluationTransaction> evaluationTransactionList = null;
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+getDomainClass().getName()+" i WHERE " +
				"i.studentEvaluation.id = "
				+studentEvaluation.getId()+" ORDER BY i.transanctionDate Desc");
		
		if(query.list().size() > 0){
			evaluationTransactionList = query.list();
		}
		
		return evaluationTransactionList;
	}

	@Override
	public EvaluationTransaction getLastUserEvaluationTransaction(
			StudentEvaluation studentEvaluation) 
	{
		EvaluationTransaction evaluationTransaction = null;
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+getDomainClass().getName()+" i WHERE " +
				"i.studentEvaluation.id = "
				+studentEvaluation.getId()
				+" ORDER by i.id DESC");
		query.setMaxResults(1);
		
		if(query.list().size() > 0){
			evaluationTransaction = (EvaluationTransaction)query.list().get(0);
		}
		return evaluationTransaction;
	}
	
	
	
}
