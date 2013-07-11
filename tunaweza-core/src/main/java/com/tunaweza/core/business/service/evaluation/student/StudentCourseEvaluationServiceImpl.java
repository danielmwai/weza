/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.evaluation.student;

import com.tunaweza.core.business.dao.evaluation.student.StudentCourseEvaluationDao;
import com.tunaweza.core.business.dao.exceptions.student.StudentCourseEvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentCourseEvaluationExistsException;
import com.tunaweza.core.business.dao.exceptions.student.StudentEvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentEvaluationExistsException;
import com.tunaweza.core.business.model.evaluation.CourseEvaluationTransaction;
import com.tunaweza.core.business.model.evaluation.EvaluationTransaction;
import com.tunaweza.core.business.model.evaluation.StudentCourseEvaluation;
import com.tunaweza.core.business.model.evaluation.StudentEvaluation;
import com.tunaweza.core.business.service.student.StudentEvaluationTransactionBean;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */
@Service("studentCourseEvaluationService")
@Transactional
public class StudentCourseEvaluationServiceImpl implements StudentCourseEvaluationService {
	
	@Autowired
	private StudentCourseEvaluationDao studentCourseEvaluationDao;

	public StudentCourseEvaluation getLastStudentCourseEvaluation(long evaluationId, long studentId)
			throws StudentCourseEvaluationDoesNotExistException {
		return studentCourseEvaluationDao.getLastStudentCourseEvaluation(evaluationId, studentId);
	}

	@Override
	public int countStudentCourseEvaluations(long studentId) {
		
		return studentCourseEvaluationDao.countStudentCourseEvaluations(studentId);
	}
}