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
package com.tunaweza.core.business.dao.exercise;

import com.tunaweza.core.business.dao.exceptions.student.StudentExerciseExistsException;
import com.tunaweza.core.business.dao.exceptions.student.StudentExerciseNotFoundException;
import com.tunaweza.core.business.dao.exercise.StudentExerciseDao;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.exercise.StudentExercise;
import com.tunaweza.core.business.model.module.Module;
import com.tunaweza.core.business.model.topic.Topic;
import com.tunaweza.core.business.model.user.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "studentExerciseDao")
@Transactional
public class StudentExerciseDaoImpl extends GenericDaoImpl<StudentExercise> implements StudentExerciseDao {
@Autowired
     SessionFactory sessionFactory;
    @Override
    public StudentExercise findStudentExerciseById(Long id)
            throws StudentExerciseNotFoundException {
        StudentExercise exercise = findById(id);
        if (exercise == null) {
            throw new StudentExerciseNotFoundException(
                    "StudentExercise with id " + id + " does not exist");
        }
        return exercise;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentExercise> getAllStudentExerciseByStudent(User user)
            throws StudentExerciseNotFoundException {
        List<StudentExercise> studentExerciseList = null;
      
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
                + getDomainClass().getName() + " i " + "WHERE i.student.id='"
                + user.getStudent().getId() + "'");
        if (query.list().size() > 0) {
            studentExerciseList = query.list();
        }
        return studentExerciseList;
    }

    @Override
    public List<StudentExercise> getAllStudentExercise()
            throws StudentExerciseNotFoundException {
        return findAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StudentExercise> findStudentExerciseByTopic(Topic topic)
            throws StudentExerciseNotFoundException {
        List<StudentExercise> studentExerciseList = null;
       
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
                + getDomainClass().getName() + " i " + "WHERE i.exercise.id='"
                + topic.getId() + "'");
        if (query.list().size() > 0) {
            studentExerciseList = query.list();
        }
        return studentExerciseList;
    }

    @Override
    public void addStudentExercise(StudentExercise studentExercise)
            throws StudentExerciseExistsException {
        saveOrUpdate(studentExercise);
    }

    @Override
    public void updateStudentExercise(StudentExercise studentExercise)
            throws StudentExerciseNotFoundException {
        saveOrUpdate(studentExercise);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StudentExercise> getStudentExerciseByIsComplete(
            boolean isComplete) {
        List<StudentExercise> studentExerciseList = null;
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
                + getDomainClass().getName() + " i " + "WHERE i.isComplete = "
                + isComplete);
        if (query.list().size() > 0) {
            studentExerciseList = query.list();
        }
        return studentExerciseList;
    }

    @Override
    public StudentExercise getStudentExerciseByExercise(User user,
            Topic exercise) {
        StudentExercise studentExercise = null;
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
                + getDomainClass().getName() + " i " + "WHERE i.exercise.id='"
                + exercise.getId() + "'" + "AND i.student.id='"
                + user.getStudent().getId() + "'");
        if (query.list().size() > 0) {
            studentExercise = (StudentExercise) query.list().get(0);
        }
        return studentExercise;
    }

    /**
     *
     * @param user
     * @param module
     * @return
     * @throws StudentExerciseNotFoundException
     */
    @Override
    public List<List> getAllStudentExerciseByModule(User user,
            Module module) throws StudentExerciseNotFoundException {
        List<List> studentExerciseList = new ArrayList();
        List<Topic> studentNotStartedExerciseList = new ArrayList<Topic>();
        List<StudentExercise> studentStartedExerciseList = new ArrayList<StudentExercise>();
        Query query1 = sessionFactory.getCurrentSession().createSQLQuery(
                "SELECT * FROM student_exercise s"
                + " JOIN topics t ON t.id = s.topic_id"
                + "  WHERE s.user_id = " + user.getStudent().getId() + " AND t.id_module = " + module.getId()
                + " AND is_exercise = 1 AND t.enabled = 1").addEntity(
                StudentExercise.class);

        Query query2 = sessionFactory.getCurrentSession()
                .createSQLQuery(
                "SELECT * FROM topics t WHERE t.id_module = " + module.getId()
                + " AND t.is_exercise = 1 AND t.enabled = 1 AND t.id NOT IN "
                + "(SELECT s.topic_id FROM student_exercise s WHERE s.user_id =" + user.getStudent().getId() + ")")
                .addEntity(Topic.class);

        if (query1.list().size() > 0) {
            studentStartedExerciseList = query1.list();
        }

        if (query2.list().size() > 0) {
            studentNotStartedExerciseList = query2.list();
        }
        studentExerciseList.add(studentStartedExerciseList);
        studentExerciseList.add(studentNotStartedExerciseList);
        return studentExerciseList;
    }

   
}
