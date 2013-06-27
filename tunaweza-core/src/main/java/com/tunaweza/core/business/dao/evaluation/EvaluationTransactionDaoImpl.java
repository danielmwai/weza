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

package com.tunaweza.core.business.dao.evaluation;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "evaluationTransactionDAO")
@Transactional
public class EvaluationTransactionDaoImplextends
		GenericDaoImpl<EvaluationTransaction> implements EvaluationTransactionDAO {

	
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
