/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.evaluation.student;

import com.tunaweza.core.business.dao.evaluation.EvaluationTransactionDao;
import com.tunaweza.core.business.dao.evaluation.course.CourseEvaluationTransactionDao;
import com.tunaweza.core.business.dao.evaluation.student.StudentCourseEvaluationDao;
import com.tunaweza.core.business.dao.evaluation.student.StudentEvaluationDao;
import com.tunaweza.core.business.dao.exceptions.student.StudentCourseEvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentCourseEvaluationExistsException;
import com.tunaweza.core.business.dao.exceptions.student.StudentEvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentEvaluationExistsException;
import com.tunaweza.core.business.dao.student.StudentDao;
import com.tunaweza.core.business.model.evaluation.CourseEvaluationTransaction;
import com.tunaweza.core.business.model.evaluation.EvaluationTransaction;
import com.tunaweza.core.business.model.evaluation.StudentCourseEvaluation;
import com.tunaweza.core.business.model.evaluation.StudentEvaluation;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.service.student.StudentEvaluationTransactionBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author naistech
 */
@Service
public class StudentEvaluationServiceImpl implements StudentEvaluationService 
{
	@Autowired
	private StudentEvaluationDao studentEvaluationDao;
	
	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private EvaluationTransactionDao evaluationTransactionDao;
	
	@Autowired
	private StudentCourseEvaluationDao studentCourseEvaluationDao;
	
	@Autowired
	private CourseEvaluationTransactionDao courseEvaluationTransactionDao;
	
	@Override
	public StudentEvaluation addStudentEvaluation(StudentEvaluation studentEvaluation) 
			throws StudentEvaluationExistsException{
			
		return studentEvaluationDao.addStudentEvaluation(studentEvaluation);
	
	}
	
	@Override
	public StudentEvaluation getStudentEvaluationById(long id) 
			throws StudentEvaluationDoesNotExistException{
	
		return studentEvaluationDao.findStudentEvaluationById(id);
	
	}
	
	@Override
	public List<StudentEvaluation> listAllStudentEvaluations() throws StudentEvaluationDoesNotExistException{
	
		return studentEvaluationDao.getAllStudentEvaluation();
	
	}
	
	@Override
	public void updateStudentEvaluation(StudentEvaluation studentEvaluation) throws StudentEvaluationDoesNotExistException{
	
		studentEvaluationDao.updateStudentEvaluation(studentEvaluation);
	
	}

	@Override
	public List<StudentEvaluation> getStudentEvaluation(long evaluationId,
			long studentId) {
		return studentEvaluationDao.getStudentEvaluation(evaluationId,studentId);
	}
	@Override
	public StudentEvaluation getFirstStudentEvaluation(long evaluationId,
			long studentId) {
		return studentEvaluationDao.getFirstStudentEvaluation(evaluationId,studentId);
	}

	@Override
	public void deleteStudentEvaluation(StudentEvaluation evaluation) {
		studentEvaluationDao.deleteStudentEvaluation(evaluation);
		
	}
	
	@Override
	public EvaluationTransaction addEvaluationTransaction(EvaluationTransaction evaluationTransaction){
			
		return evaluationTransactionDao.saveEvaluationTransaction(evaluationTransaction);
	
	}
	
	@Override
	public StudentEvaluation getLastStudentEvaluation(long evaluationId,
			long studentId) throws StudentEvaluationDoesNotExistException {
		
		return studentEvaluationDao.getLastStudentEvaluation(evaluationId,studentId);
	}

	@Override
	public StudentCourseEvaluation addStudentCourseEvaluation(
			StudentCourseEvaluation studentEvaluation)
			throws StudentCourseEvaluationExistsException {
		
		return studentCourseEvaluationDao.addStudentCourseEvaluation(studentEvaluation);
		
	}

	@Override
	public StudentCourseEvaluation getStudentCourseEvaluationById(long id)
			throws StudentCourseEvaluationDoesNotExistException {
		return studentCourseEvaluationDao.findStudentCourseEvaluationById(id);
	}

	@Override
	public List<StudentCourseEvaluation> listAllStudentCourseEvaluations()
			throws StudentCourseEvaluationDoesNotExistException {
		return studentCourseEvaluationDao.getAllStudentCourseEvaluation();
	}

	@Override
	public void updateStudentCourseEvaluation(
			StudentCourseEvaluation studentEvaluation)
			throws StudentCourseEvaluationDoesNotExistException {
		studentCourseEvaluationDao.updateStudentCourseEvaluation(studentEvaluation);
	}

	@Override
	public List<StudentCourseEvaluation> getStudentCourseEvaluation(
			long evaluationId, long studentId) {
		return studentCourseEvaluationDao.getStudentCourseEvaluation(evaluationId,studentId);
	}

	@Override
	public StudentCourseEvaluation getFirstStudentCourseEvaluation(
			long evaluationId, long studentId) {
		return studentCourseEvaluationDao.getFirstStudentCourseEvaluation(evaluationId,studentId);
	}

	@Override
	public void deleteStudentCourseEvaluation(StudentCourseEvaluation evaluation) {
		studentCourseEvaluationDao.delete(evaluation);
		
	}

	@Override
	public CourseEvaluationTransaction addCourseEvaluationTransaction(
			CourseEvaluationTransaction evaluationTransaction) {
		
		return courseEvaluationTransactionDao.saveCourseEvaluationTransaction(evaluationTransaction);
		
	}

    @Override
	public List<StudentEvaluation> getStudentEvaluation(long studentId) {
		return studentEvaluationDao.getStudentEvaluation(studentId);
	}

    @Override
	public List<StudentCourseEvaluation> getStudentCourseEvaluation(long studentId,int startIndex,int pagesize) throws StudentCourseEvaluationDoesNotExistException {
		Student student = studentDao.findById(studentId);
    	return studentCourseEvaluationDao.getAllStudentCourseEvaluationByStudent(student,startIndex,pagesize);
	}
    
    @Override
    public List<StudentEvaluationTransactionBean> getStudentEvaluationTransactionBeans (long studentId,int startIndex,int pagesize) throws StudentCourseEvaluationDoesNotExistException
    {
        List<StudentCourseEvaluation> studentCourseEvals = this.getStudentCourseEvaluation(studentId,startIndex,pagesize);
    	List<StudentEvaluation> studentEvals = this.getStudentEvaluation(studentId);
        Map<String,StudentEvaluationTransactionBean> studentEvaluationTransactionBeanMap = new HashMap<String,StudentEvaluationTransactionBean>();
        List<StudentEvaluationTransactionBean> studentEvaluationTransactionBeanList = new ArrayList<StudentEvaluationTransactionBean>();
        if(studentEvals!=null){
        for(StudentEvaluation eval:studentEvals)
        {
            String moduleName;
            StudentEvaluationTransactionBean evalBean = new  StudentEvaluationTransactionBean();
            moduleName = eval.getEvaluation().getModule().getName();
            if(studentEvaluationTransactionBeanMap.containsKey(moduleName))
            {
            	evalBean = studentEvaluationTransactionBeanMap.get(moduleName);
            	evalBean.setSecondDate(eval.getDateTaken().toString());
                evalBean.setSecondScore(eval.getTestScore() +"%");
            }
            else
            {
            evalBean.setFirstDate(eval.getDateTaken().toString());
            evalBean.setModuleName(eval.getEvaluation().getModule().getName());
            evalBean.setFirstScore(eval.getTestScore() +"%");
            evalBean.setSecondDate("Not Taken");
            evalBean.setModuleId(eval.getEvaluation().getModule().getId().toString());
            evalBean.setIsCourse(false);
            }
            studentEvaluationTransactionBeanMap.put(moduleName, evalBean);
        }
        
        }
        for(Map.Entry<String,StudentEvaluationTransactionBean> entry : studentEvaluationTransactionBeanMap.entrySet())
        {
        	studentEvaluationTransactionBeanList.add(entry.getValue());
        } 
        return  studentEvaluationTransactionBeanList;
    }
    
    
    @Override
    public List<StudentEvaluationTransactionBean> getStudentCourseEvaluationTransactionBeans(long studentId,int startIndex) throws StudentCourseEvaluationDoesNotExistException
    {
        List<StudentCourseEvaluation> studentCourseEvals = this.getStudentCourseEvaluation(studentId,startIndex);
        Map<String,StudentEvaluationTransactionBean> studentEvaluationTransactionBeanMap = new HashMap<String,StudentEvaluationTransactionBean>();
        List<StudentEvaluationTransactionBean> studentCourseEvaluationTransactionBeanList = new ArrayList<StudentEvaluationTransactionBean>();
        if(studentCourseEvals!=null){
        for(StudentCourseEvaluation eval:studentCourseEvals)
        {
            String moduleName;
            StudentEvaluationTransactionBean evalBean = new  StudentEvaluationTransactionBean();
            moduleName = eval.getCourse().getName();
            if(studentEvaluationTransactionBeanMap.containsKey(moduleName))
            {
            	evalBean = studentEvaluationTransactionBeanMap.get(moduleName);
            	evalBean.setSecondDate(eval.getDateTaken().toString());
            	evalBean.setSecondScore(eval.getTestScore() +"%");
            }
            else
            {
            evalBean.setFirstDate(eval.getDateTaken().toString());
            evalBean.setModuleName(eval.getCourse().getName());
            evalBean.setFirstScore(eval.getTestScore() +"%");
            evalBean.setSecondDate("Not Taken");
            evalBean.setSecondScore("Not Taken");
            evalBean.setModuleId(eval.getCourse().getId().toString());
            evalBean.setIsCourse(true);
            }
            studentEvaluationTransactionBeanMap.put(moduleName, evalBean);
        }
        }
        for(Map.Entry<String,StudentEvaluationTransactionBean> entry : studentEvaluationTransactionBeanMap.entrySet())
        {
        	studentCourseEvaluationTransactionBeanList.add(entry.getValue());
        }  
     return  studentCourseEvaluationTransactionBeanList;
    }
    
	@Override
	public StudentCourseEvaluation getLastStudentCourseEvaluation(
			long evaluationId, long studentId) throws StudentCourseEvaluationDoesNotExistException {
		return studentCourseEvaluationDao.getLastStudentCourseEvaluation(evaluationId, studentId);
	}
	
	@Override
	public int countStudentCourseEvaluations(long studentId)
	{
		return studentCourseEvaluationDao.countStudentCourseEvaluations(studentId);
	}
	
	@Override
	public int countStudentEvaluations(long studentId)
	{
		return studentEvaluationDao.countStudentEvaluations(studentId);
	}

	@Override
	public StudentEvaluation getTemporaryStudentEvaluation(long evaluationId,
			long studentId) throws StudentEvaluationDoesNotExistException {
		// TODO Auto-generated method stub
		return studentEvaluationDao.getTemporaryStudentEvaluation(evaluationId, studentId);
	}

	@Override
	public StudentCourseEvaluation getTemporaryStudentCourseEvaluation(long evaluationId, long studentId) throws StudentCourseEvaluationDoesNotExistException{
	return studentCourseEvaluationDao.getTemporaryStudentCourseEvaluation(evaluationId, studentId);
	}
	
	
	@Override
	public StudentEvaluation getLastStudentEvaluationNoSession(long evaluationId,
			long studentId, String companyDbName) throws StudentEvaluationDoesNotExistException {
		
		return studentEvaluationDao.getLastStudentEvaluationNoSession(evaluationId,studentId, companyDbName);
	}
	
	
}
