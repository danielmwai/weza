/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.evaluation;

import com.tunaweza.core.business.dao.exceptions.evaluation.EvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.evaluation.Evaluation;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */
@Repository(value = "evaluationdao")
@Transactional
public class EvaluationDaoImpl extends GenericDaoImpl<Evaluation> implements EvaluationDao {
//    @Id
//    @GeneratedValue
//    private Long id;
    @Autowired
    SessionFactory sessionFactory ; 
    
	@Override
	public Evaluation findEvaluationById(long uid) throws EvaluationDoesNotExistException {
		Evaluation evaluation = findById(uid);
		if (evaluation == null) {
			/*throw new EvaluationDoesNotExistException("Evaluation with ID : " + uid
					+ " does not exist");*/
			return null;
		}

		return evaluation;
	}

	@Override
	public Evaluation findEvaluationByName(String name) throws EvaluationDoesNotExistException {

		

		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.name = '" + name
				+ "'");

		Evaluation evaluation = null;

		if (query.list().size() > 0) {
			evaluation = (Evaluation) query.list().get(0);
		} else {
			throw new EvaluationDoesNotExistException("Evaluation with name : " + name
					+ " does not exist");
		}

		return evaluation;

	}

	@Override
	public Evaluation findEvaluation(Evaluation evaluation) throws EvaluationDoesNotExistException {

		Evaluation evaluation1 = findById(evaluation.getId());
		if (evaluation1 == null) {
			throw new EvaluationDoesNotExistException();
		}
		return evaluation1;
	}

	@Override
	public Evaluation addEvaluation(Evaluation evaluation) throws EvaluationDoesNotExistException {

		Evaluation evaluation1=null;
		try {
			evaluation1 = findEvaluationByName(evaluation.getName());
		} catch (EvaluationDoesNotExistException e) 
		{			
			//e.printStackTrace();
		}
                /**
                 * what were they doing here
                 */
		
//		if(evaluation1 != null){
//			throw new EvaluationExistsException();
//		}

		Evaluation savedEvaluation = saveOrUpdate(evaluation);
		logger.info("Saved Evaluation Id" + savedEvaluation.getId());
		return savedEvaluation;
	}

	

	@Override
	public void updateEvaluation(Evaluation evaluation){
	
		saveOrUpdate(evaluation);
	
	}

	@Override
	public Evaluation getEvaluationByModule(long moduleId) throws EvaluationDoesNotExistException {

		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.module.id = '" + moduleId
				+ "'");

		Evaluation evaluation = null;

		if (query.list().size() > 0) {
			evaluation = (Evaluation) query.list().get(0);
		} else {
			throw new EvaluationDoesNotExistException("Evaluation does not exist");
		}

		return evaluation;
	}

	@Override
	public Evaluation getEvaluationByModuleNoSession(long moduleId, String companyDBName) throws EvaluationDoesNotExistException {

		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM " + companyDBName +
				".evaluation i WHERE i.module_id = '" + moduleId
				+ "'").addEntity(Evaluation.class);

		Evaluation evaluation = null;

		if (query.list().size() > 0) {
			evaluation = (Evaluation) query.list().get(0);
		} else {
			throw new EvaluationDoesNotExistException("Evaluation does not exist");
		}

		return evaluation;
	}


	@Override
	public List<Evaluation> getAllEvaluations() {
		return findAll();
	}
}
