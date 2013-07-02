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

package com.tunaweza.core.business.model.exercise;

import com.tunaweza.core.business.Dao.exercise.transaction.ExerciseTransactionTypeDao;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.exercise.transaction.ExerciseTransactionType;
import com.tunaweza.core.business.model.mentor.Mentor;
import com.tunaweza.core.business.service.exercise.ExerciseTransaction;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "exerciseTransactionDao")
@Transactional
public class ExerciseTransactionDaoImpl extends
		GenericDaoImpl<ExerciseTransaction> implements ExerciseTransactionDao {




	@Autowired 
	private ExerciseTransactionTypeDao exerciseTransactionTypeDAO;
	
	@Override
	public List<ExerciseTransaction> getExerciseTransactions() {
		return findAll();
	}

	@Override
	public ExerciseTransaction getExerciseTransaction(Long id) {
		return findById(id);
	}

		public void saveExerciseTransaction(ExerciseTransaction exerciseTransaction) 
	{
		saveOrUpdate(exerciseTransaction);
	}

	@SuppressWarnings("unchecked")
	public List<ExerciseTransaction> getExerciseTransactionByMentor(
			Mentor mentor) 
	{
		List<ExerciseTransaction> exerciseTransactionList = null;
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+getDomainClass().getName()+" i WHERE i.mentor.id = "+mentor.getId());
		
		if(query.list().size() > 0){
			exerciseTransactionList = query.list();
		}
		
		return exerciseTransactionList;
	}

	@SuppressWarnings("unchecked")
	public List<ExerciseTransaction> 
		getExerciseTransactionByExerciseTransactionType(
			ExerciseTransactionType exerciseTransactionType) 
	{
		List<ExerciseTransaction> exerciseTransactionList = null;
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+getDomainClass().getName()+" i WHERE " +
				"i.exerciseTransactionType.id = "
				+exerciseTransactionType.getId());
		
		if(query.list().size() > 0){
			exerciseTransactionList = query.list();
		}
		
		return exerciseTransactionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExerciseTransaction> getExerciseTransactionByStudentExercise(
			StudentExercise studentExercise) 
	{
		List<ExerciseTransaction> exerciseTransactionList = null;
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+getDomainClass().getName()+" i WHERE " +
				"i.studentExercise.id = "
				+studentExercise.getId()+" ORDER BY i.transanctionDate Desc");
		
		if(query.list().size() > 0){
			exerciseTransactionList = query.list();
		}
		
		return exerciseTransactionList;
	}

	@Override
	public ExerciseTransaction getLastUserExerciseTransaction(
			StudentExercise studentExercise) 
	{
		ExerciseTransaction exerciseTransaction = null;
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+getDomainClass().getName()+" i WHERE " +
				"i.studentExercise.id = "
				+studentExercise.getId()
				+" ORDER by i.id DESC");
		query.setMaxResults(1);
		
		if(query.list().size() > 0){
			exerciseTransaction = (ExerciseTransaction)query.list().get(0);
		}
		return exerciseTransaction;
	}
	
	@Override
	public ExerciseTransaction getLastUserExerciseTransactionByType(
			StudentExercise studentExercise,String transactionType) 
	{
	
		ExerciseTransaction exerciseTransaction = null;
		ExerciseTransactionType exerciseTransactionType = exerciseTransactionTypeDAO.getExerciseTransactionTypeByName(transactionType);
		
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+getDomainClass().getName()+" i WHERE " +
				"i.studentExercise.id = "
				+studentExercise.getId()
				+" AND i.exerciseTransactionType.id = "+exerciseTransactionType.getId()+" ORDER by i.transanctionDate DESC");
		query.setMaxResults(1);
		
		if(query.list().size() > 0){
			exerciseTransaction = (ExerciseTransaction)query.list().get(0);
		}
		return exerciseTransaction;
	}	


	
}
