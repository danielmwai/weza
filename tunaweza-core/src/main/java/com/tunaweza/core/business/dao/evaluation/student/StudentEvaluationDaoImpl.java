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

package com.tunaweza.core.business.dao.evaluation.student;




import com.tunaweza.core.business.dao.exceptions.student.StudentEvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentEvaluationExistsException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.evaluation.StudentEvaluation;
import com.tunaweza.core.business.model.student.Student;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "studentEvaluationDao")
@Transactional
public class StudentEvaluationDaoImpl extends GenericDaoImpl<StudentEvaluation>
		implements  StudentEvaluationDao {
@Autowired
SessionFactory sessionFactory;
	@Override
	public StudentEvaluation findStudentEvaluationById(Long id)
			throws StudentEvaluationDoesNotExistException {
		StudentEvaluation exercise = (StudentEvaluation) findById(id);
		if (exercise == null)
			throw new StudentEvaluationDoesNotExistException(
					"StudentEvaluation with id " + id + " does not exist");
		return exercise;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentEvaluation> getAllStudentEvaluationByStudent(Student student)
			throws StudentEvaluationDoesNotExistException {
		List<StudentEvaluation> studentEvaluationList = null;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i " + "WHERE i.student.id='"
				+ student.getId() + "'");
		if (query.list().size() > 0) {
			studentEvaluationList = query.list();
		}
		return studentEvaluationList;
	}

	@Override
	public List<StudentEvaluation> getAllStudentEvaluation()
			throws StudentEvaluationDoesNotExistException {
		return findAll();
	}

	@Override
	public StudentEvaluation addStudentEvaluation(StudentEvaluation studentEvaluation)
			throws StudentEvaluationExistsException {
		return saveOrUpdate(studentEvaluation);
	}

	@Override
	public void updateStudentEvaluation(StudentEvaluation studentEvaluation)
			throws StudentEvaluationDoesNotExistException {
		saveOrUpdate(studentEvaluation);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentEvaluation> getStudentEvaluation(long evaluationId,
			long studentId) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.student.id= ? "
				+ " AND i.evaluationTemplate.id = ?");
		query.setLong(0, studentId);
		query.setLong(1, evaluationId);
		return query.list();

	}

	@Override
	public StudentEvaluation getFirstStudentEvaluation(long evaluationId,
			long studentId) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.student.id= ? "
				+ " AND i.evaluationTemplate.id = ? AND i.temporary=0" +
				"ORDER BY i.dateTaken ASC");
		query.setLong(0, studentId);
		query.setLong(1, evaluationId);
		return (StudentEvaluation) query.list().get(0);
	}
	
	@Override
	public StudentEvaluation getLastStudentEvaluation(long evaluationId,
			long studentId) throws StudentEvaluationDoesNotExistException {
		StudentEvaluation studentEvaluation = null;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.student.id= ? "
				+ " AND i.evaluationTemplate.id = ? AND i.temporary=0" +
				"ORDER BY i.dateTaken DESC");
		query.setLong(0, studentId);
		query.setLong(1, evaluationId);
		if (query.list().size() > 0) {
			studentEvaluation = (StudentEvaluation) query.list().get(0);
		}
		return studentEvaluation;
	}
	
	@Override
	public StudentEvaluation getTemporaryStudentEvaluation(long evaluationId,
			long studentId) throws StudentEvaluationDoesNotExistException {
		StudentEvaluation studentEvaluation = null;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.student.id= ? "
				+ " AND i.evaluationTemplate.id = ? AND i.temporary=1" +
				"ORDER BY i.dateTaken DESC");
		query.setLong(0, studentId);
		query.setLong(1, evaluationId);
		if (query.list().size() > 0) {
			studentEvaluation = (StudentEvaluation) query.list().get(0);
		}
		return studentEvaluation;
	}

	@Override
	public void deleteStudentEvaluation(StudentEvaluation evaluation) {
		delete(evaluation);		
	}

    @SuppressWarnings("unchecked")
	@Override
	public List<StudentEvaluation> getStudentEvaluation(long studentId) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.student.id= ? ");
		query.setLong(0, studentId);
		return query.list();

	}

    @SuppressWarnings("unchecked")
	@Override
	public List<StudentEvaluation> getStudentEvaluation(long studentId,int startIndex) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.student.id= ? ");
        query.setLong(0, studentId);
		query.setFirstResult(startIndex);
        query.setMaxResults(10);
        return query.list();

	}
    
    public int countStudentEvaluations(long studentId)
    {
    	Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT COUNT(DISTINCT evaluationTemplate_id) FROM student_test WHERE student_id = ?");
    	query.setLong(0,studentId);
    	BigInteger count = (BigInteger)query.list().get(0);
    	return count.intValue();
    }
    
    @Override
	public StudentEvaluation getLastStudentEvaluationNoSession(long evaluationId,
			long studentId, String companyDbName) throws StudentEvaluationDoesNotExistException {
		StudentEvaluation studentEvaluation = null;
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM "
				+ companyDbName + ".student_test i WHERE i.student_id= ? "
				+ " AND i.evaluationTemplate_id = ? AND i.temporary=0" +
				"ORDER BY i.date_taken DESC").addEntity(StudentEvaluation.class);
		query.setLong(0, studentId);
		query.setLong(1, evaluationId);
		if (query.list().size() > 0) {
			studentEvaluation = (StudentEvaluation) query.list().get(0);
		}
		return studentEvaluation;
	}


}