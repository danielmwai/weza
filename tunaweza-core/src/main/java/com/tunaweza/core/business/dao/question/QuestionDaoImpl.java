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
package com.tunaweza.core.business.dao.question;

import com.tunaweza.core.business.dao.exceptions.question.QuestionDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.question.QuestionExistsException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.persistence.PersistentEntity;
import com.tunaweza.core.business.model.question.Question;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class QuestionDaoImpl extends GenericDaoImpl<Question> implements
        QuestionDao {

    @Override
    public Question findQuestionById(long uid)
            throws QuestionDoesNotExistException {
        Question question = (Question) findById(uid);
        if (question != null) {
        } else {
            throw new QuestionDoesNotExistException();
        }

        return question;
    }

    @Override
    public Question findQuestionByText(String text)
            throws QuestionDoesNotExistException {

        Session session = (Session) getEntityManager().getDelegate();

        Query query = session.createQuery("SELECT i FROM "
                + getDomainClass().getName() + " i WHERE i.text = '" + text
                + "'");

        Question question = null;

        if (query.list().size() > 0) {
            question = (Question) query.list().get(0);
        } else {
            throw new QuestionDoesNotExistException("Question with text : "
                    + text + " does not exist");
        }

        return question;

    }

    @Override
    public Question findQuestion(Question question)
            throws QuestionDoesNotExistException {

        Question question1 = (Question) findById(question.getId());
        if (question1 == null) {
            throw new QuestionDoesNotExistException();
        }
        return question1;
    }

    @Override
    public Question addQuestion(Question question)
            throws QuestionExistsException {

        Question question1 = null;
        try {
            question1 = findQuestionByTextAndEvaluation(question.getText(),
                    question.getEvaluation().getId().toString());
        } catch (QuestionDoesNotExistException e) {
            // e.printStackTrace();
        }

        if (question1 != null) {
            throw new QuestionExistsException();
        }

        Question savedQuestion = saveOrUpdate(question);
        logger.info("Saved Question Id" + savedQuestion.getId());
        return savedQuestion;
    }

    @Override
    public List<Question> getAllQuestions() {
        return findAll();
    }

    @Override
    public void updateQuestion(Question question) {

        saveOrUpdate(question);

    }

    
    public List<Question> getAllQuestionsBy(Long evaluationTemplateId) {
        Session session = (Session) getEntityManager().getDelegate();

        Query query = session.createQuery("SELECT i FROM "
                + getDomainClass().getName()
                + " i WHERE i.evaluationTemplate.id = '" + evaluationTemplateId
                + "' ORDER BY i.id ASC");

        List<Question> quetions = new ArrayList<Question>();

        if (query.list().size() > 0) {
            quetions = query.list();
        } else {
        }

        return quetions;
    }

    @Override
    public Question findQuestionByTextAndEvaluation(String text,
            String evaluationId) throws QuestionDoesNotExistException {
        Session session = (Session) getEntityManager().getDelegate();

        Query query = session.createQuery("SELECT i FROM "
                + getDomainClass().getName()
                + " i WHERE i.text = ? AND i.evaluationTemplate.id = ?");

        query.setString(0, text);
        query.setString(1, evaluationId);

        Question question = null;

        if (query.list().size() > 0) {
            question = (Question) query.list().get(0);
        } else {
            throw new QuestionDoesNotExistException("Question with text : "
                    + text + " does not exist");
        }

        return question;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Question> getQuestionsByTopic(long topicId) {
        Session session = (Session) getEntityManager().getDelegate();

        Query query = session.createQuery("SELECT i FROM "
                + getDomainClass().getName() + " i WHERE i.topic.id = '"
                + topicId + "' ORDER BY i.id ASC");

        List<Question> quetions = new ArrayList<Question>();

        if (query.list().size() > 0) {
            quetions = query.list();
        }
        return quetions;
    }

    public int getNumberOfQuestionsByTopic(long topicId, String text) {
        Session session = (Session) getEntityManager().getDelegate();
        String sql = null;
        if (text != null) {

            sql = "SELECT COUNT(*) FROM questions q WHERE q.associatedTopic_id = ?"
                    + " AND q.text LIKE '%" + text + "%'";
        } else {
            sql = "SELECT COUNT(*) FROM questions q WHERE q.associatedTopic_id = ?";
        }
        Query query = session.createSQLQuery(sql);

        query.setLong(0, topicId);

        java.math.BigInteger count = (java.math.BigInteger) query.list().get(0);

        return count.intValue();
    }

    @SuppressWarnings("unchecked")
    public List<Question> getPaginatedQuestionsByTopic(int startIndex,
            int pageSize, long topicId, String text) {
        Session session = (Session) getEntityManager().getDelegate();
        String sql = null;
        if (text != null) {
            sql = "SELECT * FROM questions q WHERE q.associatedTopic_id = ?"
                    + " AND  q.text LIKE '%" + text + "%'ORDER BY q.id ASC";
        } else {
            sql = "SELECT * FROM questions q WHERE q.associatedTopic_id = ?"
                    + " ORDER BY q.id ASC";
        }
        Query query = session.createSQLQuery(sql).addEntity(Question.class);

        /*
         * Query query = session.createQuery("SELECT i FROM " +
         * getDomainClass().getName() + " i WHERE i.topic.id = '" + topicId +
         * "' ORDER BY i.id ASC");
         */

        /*
         * Query query = session.createQuery("SELECT i FROM " +
         * getDomainClass().getName() +
         * " i WHERE i.topic.id = ? ORDER BY i.id ASC");
         */

        query.setLong(0, topicId);

        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);

        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Question> getQuestionsNotAssociatedWithTopic(
            Long templateId, String text) {
        Session session = (Session) getEntityManager().getDelegate();
        String sql = null;
        if (text != null) {
            sql = "SELECT * FROM questions q WHERE q.evaluationTemplate_id = ?"
                    + " AND associatedTopic_id IS NULL AND q.text LIKE '%"
                    + text + "%'";
        } else {
            sql = "SELECT * FROM questions q WHERE q.evaluationTemplate_id = ?"
                    + " AND associatedTopic_id IS NULL";
        }
        System.out.println("QQQUEEEEEEEERY>>>>>>>>>>>>>>>>>>" + sql);
        Query query = session.createSQLQuery(sql).addEntity(Question.class);

        query.setLong(0, templateId);

        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Question> getPaginatedQuestionsInNotAssociatedWithTopic(
            int startIndex, int pageSize, Long templateId, String text) {
        Session session = (Session) getEntityManager().getDelegate();
        String sql = null;
        if (text != null) {
            sql = "SELECT * FROM questions q WHERE q.evaluationTemplate_id = ?"
                    + " AND associatedTopic_id IS NULL AND q.text LIKE '%"
                    + text + "%'";
        } else {
            sql = "SELECT * FROM questions q WHERE q.evaluationTemplate_id = ?"
                    + " AND associatedTopic_id IS NULL";

        }

        Query query = session.createSQLQuery(sql).addEntity(Question.class);

        query.setLong(0, templateId);

        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);

        return query.list();
    }

    public int getNoOfQuestionsInTemplateNotAssociatedWithTopic(
            Long templateId, String text) {
        Session session = (Session) getEntityManager().getDelegate();
        String sql = null;
        if (text != null) {
            sql = "SELECT COUNT(*) FROM questions q WHERE q.evaluationTemplate_id = ?"
                    + " AND associatedTopic_id IS NULL AND q.text LIKE '%"
                    + text + "%'";
        } else {
            sql = "SELECT COUNT(*) FROM questions q WHERE q.evaluationTemplate_id = ?"
                    + " AND associatedTopic_id IS NULL";
        }
        Query query = session.createSQLQuery(sql);

        query.setLong(0, templateId);

        java.math.BigInteger count = (java.math.BigInteger) query.list().get(0);

        return count.intValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jjpeople.jjteach.Dao.QuestionDao#getPaginatedQuestions(long,
     * int, int, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    
    public List<Question> getPaginatedQuestionsByAndText(
            long evaluationTemplateId, int startIndex, int pageSize,
            String searchString) {
        Session session = (Session) getEntityManager().getDelegate();
        String sql = null;
        if (searchString != null) {
            sql = "SELECT * FROM questions q WHERE q.evaluationTemplate_id = ?"
                    + " AND  q.text LIKE '%" + searchString
                    + "%' ORDER BY q.id ASC";
        } else {
            sql = "SELECT * FROM questions q WHERE q.evaluationTemplate_id = ? ORDER BY q.id ASC";

        }
        Query query = session.createSQLQuery(sql).addEntity(Question.class);
        query.setLong(0, evaluationTemplateId);
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);
        return (List<Question>) query.list();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jjpeople.jjteach.Dao.QuestionDao#getQuestionsByTopicAndText(long,
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Question> getQuestionsByAndText(
            long evaluationTemplateId, String text) {
        Session session = (Session) getEntityManager().getDelegate();
        String sql = null;
        if (text != null) {
            sql = "SELECT * FROM questions q WHERE q.evaluationTemplate_id = ?"
                    + " AND  q.text LIKE '%" + text + "%' ORDER BY q.id ASC";
        } else {
            sql = "SELECT * FROM questions q WHERE q.evaluationTemplate_id = ? ORDER BY q.id ASC";

        }
        Query query = session.createSQLQuery(sql).addEntity(Question.class);
        query.setLong(0, evaluationTemplateId);
        return (List<Question>) query.list();
    }

    
    public int getNumberOfQuestionsByAndText(long evaluationTemplateId,
            String text) {
        Session session = (Session) getEntityManager().getDelegate();
        String sql = null;
        if (text != null) {
            sql = "SELECT * FROM questions q WHERE q.evaluationTemplate_id = ?"
                    + " AND  q.text LIKE '%" + text + "%' ORDER BY q.id ASC";
        } else {
            sql = "SELECT * FROM questions q WHERE q.evaluationTemplate_id = ? ORDER BY q.id ASC";

        }
        Query query = session.createSQLQuery(sql).addEntity(Question.class);
        query.setLong(0, evaluationTemplateId);

        return query.list().size();

    }

    @Override
    public List<Question> getQuestionsByTopicAndText(long topicId, String text) {
        Session session = (Session) getEntityManager().getDelegate();
        String sql = null;
        if (text != null) {
            sql = "SELECT * FROM questions q WHERE q.associatedTopic_id=?  AND  q.text LIKE '%" + text + "%' ORDER BY q.id ASC";
        } else {
            sql = "SELECT * FROM questions q WHERE q.associatedTopic_id=? ORDER BY q.id ASC";
        }
        Query query = session.createSQLQuery(sql).addEntity(Question.class);
        query.setLong(0, topicId);
        List<Question> questions = new ArrayList<Question>();

        if (query.list().size() > 0) {
            questions = query.list();
        }
        return questions;
    }

    @Override
    public List<Question> getAllQuestionsByTemplate(Long evaluationTemplateId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Question> getQuestionsInNotAssociatedWithTopic(Long templateId, String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNoOfQuestionsInNotAssociatedWithTopic(Long templateId, String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Question> getPaginatedQuestionsByTemplateAndText(long evaluationTemplateId, int startIndex, int pageSize, String searchString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfQuestionsByTemplateAndText(long evaluationTemplateId, String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}