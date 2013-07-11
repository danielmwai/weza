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
package com.tunaweza.core.business.dao.student;


import com.tunaweza.core.business.dao.exceptions.student.StudentDoesNotExistException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.student.CompletedModule;
import com.tunaweza.core.business.model.student.EnabledModule;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.user.User;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "studentDao")
@Transactional
public class StudentDaoImpl extends GenericDaoImpl<Student> implements
        StudentDao {
@Autowired
SessionFactory sessionFactory;
    public Logger logger = Logger.getLogger(StudentDaoImpl.class);

    @Override
    public Student saveStudent(Student student) {
        try {
            saveOrUpdate(student);
        } catch (ConstraintViolationException e) {
            logger.info("ConstraintViolationException occured :");
            e.printStackTrace();
        } catch (DataIntegrityViolationException e) {
            logger.info("DataIntegrityViolationException occured :");
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        return findAll();
    }

    @Override
    public Student getStudentById(Long studentId)
            throws StudentDoesNotExistException {


        Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
                + getDomainClass().getName() + " i WHERE i.id = " + studentId);

        if (query.list().size() == 0) {
            throw new StudentDoesNotExistException("Student with id "
                    + studentId + " does not exist");
        }

        return (Student) query.list().get(0);
    }

    @Override
    public Student getStudentByIdNoSession(String studentId, String companyDbName)
            throws StudentDoesNotExistException {


        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM " + companyDbName + ".student"
                + " WHERE id = " + studentId).addEntity(Student.class);

        if (query.list().size() == 0) {
            throw new StudentDoesNotExistException("Student with id "
                    + studentId + " does not exist");
        }

        return (Student) query.list().get(0);
    }

    @Override
    public List<Student> getAllStudentModule(Long studentId)
            throws StudentDoesNotExistException {

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
                + getDomainClass().getName() + " i WHERE i.id = " + studentId);
        if (query.list().size() == 0) {
            throw new StudentDoesNotExistException("Student with id "
                    + studentId + " does not exist");
        }

        return (List<Student>) query.list();
    }

    @Override
    public Student getStudentByUser(User user)
            throws StudentDoesNotExistException {

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
                + getDomainClass().getName() + " i WHERE i.id.user = "
                + user.getId());

        if (query.list().size() == 0) {
            throw new StudentDoesNotExistException("Student does not exist");
        }

        return (Student) query.list().get(0);

    }

    @Override
    public Student getStudentByUserNoSession(User user, String dbName, String companyId)
            throws StudentDoesNotExistException {

        Query myQuery = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM " + dbName + ".users"
                + " WHERE username = '" + user.getUsername() + "'").addEntity(User.class);

        User myUser = (User) myQuery.list().get(0);

        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM " + dbName + ".student"
                + " WHERE id_user = " + myUser.getId()).addEntity(Student.class);

        if (query.list().size() == 0) {
            throw new StudentDoesNotExistException("Student does not exist");
        }

        return (Student) query.list().get(0);

    }

    @Override
    public Long getStudentIdByUserId(Long userId)
            throws StudentDoesNotExistException {

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT id FROM student WHERE id_user = " + userId);

        if (query.list().size() == 0) {
            throw new StudentDoesNotExistException("Student does not exist");
        }

        return (Long) query.list().get(0);

    }

    @Override
    public int countStudentPendingExercises(long moduleId, long studentId) {
        int count = 0;

        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT COUNT(*) FROM "
                + "(SELECT * FROM exercise_transaction WHERE id IN "
                + "(SELECT MAX(id) FROM exercise_transaction GROUP BY exercise_id)) et"
                + " JOIN exercise_transaction_type ett ON ett.id =et.exercise_transactiontype_id "
                + "JOIN student_exercise se ON se.id = et.exercise_id JOIN topics t ON  t.id=se.topic_id "
                + "WHERE t.is_exercise=1 and ett.name =? AND se.user_id=? "
                + "and t.id IN (SELECT id FROM topics t WHERE id_module=?)");

        query.setString(0, "StudentToMentor");
        query.setLong(1, studentId);
        query.setLong(2, moduleId);

        if (query.list().size() > 0) {
            java.math.BigInteger countedEx = (java.math.BigInteger) query
                    .list().get(0);
            count += countedEx.intValue();
        }
        return count;

    }

    @Override
    public void enableStudentModule(Module module, User user)
            throws StudentDoesNotExistException {
        Student student = getStudentById(user.getStudent().getId());
        Boolean moduleExists = false;
        List<EnabledModule> enabledModules = student.getEnabledModules();
        List<EnabledModule> newEnabledModules = new ArrayList<EnabledModule>();
        for (EnabledModule enabledModule : enabledModules) {
            if (enabledModule.getModuleId() == module.getId()) {
                if (enabledModule.getEnabled() == true) {
                    enabledModule.setEnabled(false);
                } else {
                    enabledModule.setEnabled(true);
                }
                newEnabledModules.add(enabledModule);
                moduleExists = true;
            } else {
                newEnabledModules.add(enabledModule);
            }
        }
        if (!moduleExists) {
            EnabledModule enabledModule = new EnabledModule();
            enabledModule.setModuleId(module.getId());
            enabledModule.setEnabled(true);
            enabledModule.setModuleStartDate(new java.util.Date());
            newEnabledModules.add(enabledModule);
        }
        student.setEnabledModules(newEnabledModules);
        saveStudent(student);
    }

    @Override
    public void disableStudentModule(Module module, User user)
            throws StudentDoesNotExistException {

        Student student = getStudentById(user.getStudent().getId());
        Boolean moduleExists = false;
        List<EnabledModule> enabledModules = student.getEnabledModules();
        List<EnabledModule> newEnabledModules = new ArrayList<EnabledModule>();
        for (EnabledModule enabledModule : enabledModules) {
            if (enabledModule.getModuleId() == module.getId()) {
                if (enabledModule.getEnabled() == true) {
                    enabledModule.setEnabled(false);
                } else {
                    enabledModule.setEnabled(false);
                }
                newEnabledModules.add(enabledModule);
                moduleExists = true;
            } else {
                newEnabledModules.add(enabledModule);
            }
        }
        if (!moduleExists) {
            EnabledModule enabledModule = new EnabledModule();
            enabledModule.setModuleId(module.getId());
            enabledModule.setEnabled(false);
            enabledModule.setModuleStartDate(new java.util.Date());
            newEnabledModules.add(enabledModule);
        }
        student.setEnabledModules(newEnabledModules);
        saveStudent(student);
    }

    @Override
    public void enableFirstStudentModule(Module module, User user)
            throws StudentDoesNotExistException {

        Student student = getStudentById(user.getStudent().getId());
        Boolean moduleExists = false;
        List<EnabledModule> enabledModules = student.getEnabledModules();
        List<EnabledModule> newEnabledModules = new ArrayList<EnabledModule>();
        for (EnabledModule enabledModule : enabledModules) {
            if (enabledModule.getModuleId() == module.getId()) {
                if (enabledModule.getEnabled() == true
                        || enabledModule.getEnabled() == false) {
                    enabledModule.setEnabled(true);
                } else {
                    enabledModule.setEnabled(true);
                }
                newEnabledModules.add(enabledModule);
                moduleExists = true;
            } else {
                newEnabledModules.add(enabledModule);
            }
        }
        if (!moduleExists) {
            EnabledModule enabledModule = new EnabledModule();
            enabledModule.setModuleId(module.getId());
            enabledModule.setEnabled(true);
            enabledModule.setModuleStartDate(new java.util.Date());
            newEnabledModules.add(enabledModule);
        }
        student.setEnabledModules(newEnabledModules);
        saveStudent(student);
    }

    @Override
    public void setCurrentModule(Module module, Student student) {

        List<EnabledModule> studentEnabledModuleList = student
                .getEnabledModules();
        EnabledModule enabledModule = new EnabledModule();

        // set the new values
        enabledModule.setModuleId(module.getId());
        enabledModule.setEnabled(true);
        Date moduleStartDate = new Date();
        enabledModule.setModuleStartDate(moduleStartDate);
        // add the new enabled module to the student's list
        studentEnabledModuleList.add(enabledModule);
        student.setEnabledModules(studentEnabledModuleList);
        saveStudent(student);
    }

    @Override
    public boolean getStudentModuleStatus(Module module, User user)
            throws StudentDoesNotExistException {
        boolean enabled = false;
        Student student;
        try {
            student = getStudentById(user.getStudent().getId());

            List<EnabledModule> enabledModules = student.getEnabledModules();
            for (EnabledModule enabledModule : enabledModules) {
                if (enabledModule.getModuleId() == module.getId()) {
                    enabled = enabledModule.getEnabled();
                }
            }
        } catch (Exception e) {
            logger.info("Error Changing Student's Module Status");
            e.printStackTrace();
        }
        return enabled;
    }

    @Override
    public BigInteger getCourseIdOfModule(Long moduleId, Long studentId) {

        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT course FROM course_modules cm"
                + " JOIN student_template st ON cm.course = st.courseTemplate"
                + " WHERE cm.module=? AND st.student=? ORDER BY cm.level ASC");

        query.setLong(0, moduleId);
        query.setLong(1, studentId);

        if (query.list().size() == 0) {
            return null;
        }
        return (BigInteger) query.list().get(0);
    }

    @Override
    public List<Double> getCompletedTopicsAndExercises(long moduleId,
            long userId) {

        java.math.BigInteger countCompletedTopics = BigInteger.valueOf(0);
        java.math.BigInteger countCompletedExercises = BigInteger.valueOf(0);

        Query queryCompletedExercises = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT COUNT(*) FROM student_exercise WHERE is_complete = 1 "
                + "AND user_id =? AND topic_id IN (SELECT id FROM topics WHERE id_module=?)");
        queryCompletedExercises.setLong(0, userId);
        queryCompletedExercises.setLong(1, moduleId);
        countCompletedExercises = (java.math.BigInteger) queryCompletedExercises
                .list().get(0);

        Query queryCompletedTopics = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT COUNT(*) FROM student_completed_topics "
                + "WHERE student = ? AND topic IN (SELECT id FROM topics WHERE id_module= ?)");
        queryCompletedTopics.setLong(0, userId);
        queryCompletedTopics.setLong(1, moduleId);
        countCompletedTopics = (java.math.BigInteger) queryCompletedTopics
                .list().get(0);

        List<Double> completedTopicsAndExercises = new ArrayList<Double>();

        completedTopicsAndExercises.add(countCompletedTopics.doubleValue());
        completedTopicsAndExercises.add(countCompletedExercises.doubleValue());
        return completedTopicsAndExercises;
    }

    @Override
    public void setCompletedModule(Module module, Student student,
            Date moduleStartDate) {
        // TODO Auto-generated method stub
        List<CompletedModule> studentCompletedModuleList = student
                .getCompletedModules();
        CompletedModule completedModule = new CompletedModule();

        Date moduleCompletionDate = new Date();
        // set the new values
        completedModule.setModuleId(module.getId());
        completedModule.setStartDate(moduleStartDate);
        completedModule.setCompletedDate(moduleCompletionDate);
        // add the new enabled module to the student's list
        studentCompletedModuleList.add(completedModule);
        student.setCompletedModules(studentCompletedModuleList);
        saveStudent(student);
    }

    @Override
    public void setLastLoginDate(Student student) {

        java.sql.Date sqlDate = new java.sql.Date(
                new java.util.Date().getTime());
        student.setLastLoggedIn(sqlDate);
        saveStudent(student);
    }

    @Override
    public Date getLastLoginDate(Student student) {

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
                + getDomainClass().getName() + " i WHERE i.id = "
                + student.getId());

        return ((Student) query.list().get(0)).getLastLoggedIn();
    }

    /////////////////////////
    public List<BigInteger> getStudentCourseList(Long studentId, String companyDbName) {

        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT courseTemplate FROM " + companyDbName + ".student_template"
                + " WHERE student = " + studentId);

        return (query.list().size() > 0) ? (List<BigInteger>) query.list() : null;
    }
}
