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

import com.tunaweza.core.business.dao.exceptions.student.StudentCourseEvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentCourseEvaluationExistsException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.evaluation.StudentCourseEvaluation;
import com.tunaweza.core.business.model.student.Student;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "studentCourseEvaluationDao")
@Transactional
public class StudentCourseEvaluationDaoImpl extends GenericDaoImpl<StudentCourseEvaluation>
		implements StudentCourseEvaluationDao {
@Autowired
     SessionFactory sessionFactory;
	@Override
	public StudentCourseEvaluation findStudentCourseEvaluationById(Long id)
			throws StudentCourseEvaluationDoesNotExistException {
		StudentCourseEvaluation exercise = findById(id);
		if (exercise == null)
			throw new StudentCourseEvaluationDoesNotExistException(
					"StudentCourseEvaluation with id " + id + " does not exist");
		return exercise;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentCourseEvaluation> getAllStudentCourseEvaluationByStudent(Student student)
			throws StudentCourseEvaluationDoesNotExistException {
		List<StudentCourseEvaluation> studentCourseEvaluationList = null;
		
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i " + "WHERE i.student.id='"
				+ student.getId() + "'");
		if (query.list().size() > 0) {
			studentCourseEvaluationList = query.list();
		}
		return studentCourseEvaluationList;
	}


    @SuppressWarnings("unchecked")
	@Override
	public List<StudentCourseEvaluation> getAllStudentCourseEvaluationByStudent(Student student,int startIndex,int pagesize)
			throws StudentCourseEvaluationDoesNotExistException {
		List<StudentCourseEvaluation> studentCourseEvaluationList = null;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i " + "WHERE i.student.id='"
				+ student.getId() + "'");
		query.setFirstResult(startIndex);
        query.setMaxResults(pagesize);
        if (query.list().size() > 0) {
			studentCourseEvaluationList = query.list();
		}
		return studentCourseEvaluationList;
	}


	@Override
	public List<StudentCourseEvaluation> getAllStudentCourseEvaluation()
			throws StudentCourseEvaluationDoesNotExistException {
		return findAll();
	}

	@Override
	public StudentCourseEvaluation addStudentCourseEvaluation(StudentCourseEvaluation studentCourseEvaluation)
			throws StudentCourseEvaluationExistsException {
		return saveOrUpdate(studentCourseEvaluation);
	}

	@Override
	public void updateStudentCourseEvaluation(StudentCourseEvaluation studentCourseEvaluation)
			throws StudentCourseEvaluationDoesNotExistException {
		saveOrUpdate(studentCourseEvaluation);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentCourseEvaluation> getStudentCourseEvaluation(
			long evaluationId, long studentId) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.student.id= ? "
				+ " AND i.courseTemplate.id = ?");
		query.setLong(0, studentId);
		query.setLong(1, evaluationId);
		return query.list();
	}

	@Override
	public StudentCourseEvaluation getFirstStudentCourseEvaluation(
			long evaluationId, long studentId) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.student.id= ? AND i.temporary=0"
				+ " AND i.courseTemplate.id = ?" +
				"ORDER BY i.dateTaken ASC");
		query.setLong(0, studentId);
		query.setLong(1, evaluationId);
		return (StudentCourseEvaluation) query.list().get(0);
	}
	
	@Override
	public StudentCourseEvaluation getLastStudentCourseEvaluation(
			long evaluationId, long studentId) throws StudentCourseEvaluationDoesNotExistException{
		StudentCourseEvaluation studentCourseEvaluation = null;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.student.id= ? AND i.temporary=0"
				+ " AND i.courseTemplate.id = ?" +
				"ORDER BY i.dateTaken DESC");
		query.setLong(0, studentId);
		query.setLong(1, evaluationId);
		if (query.list().size() > 0) {
			studentCourseEvaluation = (StudentCourseEvaluation) query.list().get(0);
		}
		return studentCourseEvaluation;
	}
	
	@Override
	public StudentCourseEvaluation getTemporaryStudentCourseEvaluation(
			long evaluationId, long studentId) throws StudentCourseEvaluationDoesNotExistException{
		StudentCourseEvaluation studentCourseEvaluation = null;
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.student.id= ? "
				+ " AND i.courseTemplate.id = ? AND i.temporary=1" +
				"ORDER BY i.dateTaken DESC");
		query.setLong(0, studentId);
		query.setLong(1, evaluationId);
		if (query.list().size() > 0) {
			studentCourseEvaluation = (StudentCourseEvaluation) query.list().get(0);
		}
		return studentCourseEvaluation;
	}
	
	@Override
	public int countStudentCourseEvaluations(long studentId)
	{
		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT COUNT(DISTINCT courseTemplate_id) FROM student_course_test WHERE student_id= ?");
		query.setLong(0,studentId);
		java.math.BigInteger count = (java.math.BigInteger) query.list()
				.get(0);
		return count.intValue();
	}
	
	@Override
	public void deleteStudentCourseEvaluation(StudentCourseEvaluation evaluation) {
		delete(evaluation);		
	}

	@Override
	public StudentCourseEvaluation getLastStudentCourseEvaluationNoSession(
			long evaluationId, long studentId, String companyDbName) throws StudentCourseEvaluationDoesNotExistException{
		StudentCourseEvaluation studentCourseEvaluation = null;
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM "
				+ companyDbName + ".student_test i WHERE i.student_id= ? AND i.temporary=0"
				+ " AND i.courseTemplate_id = ?" +
				"ORDER BY i.date_taken DESC");
		query.setLong(0, studentId);
		query.setLong(1, evaluationId);
		if (query.list().size() > 0) {
			studentCourseEvaluation = (StudentCourseEvaluation) query.list().get(0);
		}
		return studentCourseEvaluation;
	}

    

   
	
}
