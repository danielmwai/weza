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
import com.tunaweza.core.business.dao.generic.GenericDao;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface StudentEvaluationDao extends GenericDao<StudentEvaluation> 
{
	/**
	 * 
	 * @param id
	 * @return <code>StudentEvaluation</code>
	 * @throws StudentEvaluationNotFoundException
	 */
	public StudentEvaluation findStudentEvaluationById(Long id) 
			throws StudentEvaluationDoesNotExistException;
	
	/**
	 * 
	 * @param user
	 * @return List of <code>StudentEvaluation</code>
	 * @throws StudentEvaluationNotFoundException
	 */
	public List<StudentEvaluation> getAllStudentEvaluationByStudent(Student student) 
			throws StudentEvaluationDoesNotExistException;
	
	/**
	 * 
	 * @return List of <code>StudentEvaluation</code>
	 * @throws StudentEvaluationNotFoundException
	 */
	public List<StudentEvaluation> getAllStudentEvaluation() 
			throws StudentEvaluationDoesNotExistException;
	
	
	/**
	 * 
	 * @param studentEvaluation
	 * @return 
	 * @throws StudentEvaluationExistsException
	 * 
	 */
	public StudentEvaluation addStudentEvaluation(StudentEvaluation studentEvaluation) 
			throws StudentEvaluationExistsException;
	
	/**
	 * 
	 * @param studentEvaluation
	 * @throws StudentEvaluationNotFoundException
	 * 
	 */
	public void updateStudentEvaluation(StudentEvaluation studentEvaluation) 
			throws StudentEvaluationDoesNotExistException;

	/**
	 * 
	 * @param evaluationId
	 * @param studentId
	 * @return
	 */
	public List<StudentEvaluation> getStudentEvaluation(long evaluationId,
			long studentId);

    public List<StudentEvaluation> getStudentEvaluation(long studentId);

    public List<StudentEvaluation> getStudentEvaluation(long studentId,int startIndex);

	public StudentEvaluation getFirstStudentEvaluation(long evaluationId, long studentId);

	public void deleteStudentEvaluation(StudentEvaluation evaluation);

	public StudentEvaluation getLastStudentEvaluation(long evaluationId,
			long studentId) throws StudentEvaluationDoesNotExistException;

	public int countStudentEvaluations(long studentId);

	public StudentEvaluation getTemporaryStudentEvaluation(long evaluationId,
			long studentId) throws StudentEvaluationDoesNotExistException;
	
	public StudentEvaluation getLastStudentEvaluationNoSession(long evaluationId,
			long studentId, String companyDbName) throws StudentEvaluationDoesNotExistException;

    @Override
    public StudentEvaluation findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean exists(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<StudentEvaluation> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<StudentEvaluation> findByExample(StudentEvaluation exampleInstance, String[] excludeProperty) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StudentEvaluation saveOrUpdate(StudentEvaluation entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(StudentEvaluation entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object execute(HibernateCallback callback) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List executeFind(HibernateCallback callback) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	
}
