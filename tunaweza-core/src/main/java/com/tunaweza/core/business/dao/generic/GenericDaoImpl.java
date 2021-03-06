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

package com.tunaweza.core.business.dao.generic;

import com.tunaweza.core.business.model.user.EndOfConversation;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

public class GenericDaoImpl<E> implements
		GenericDao<E> {
		
        @Autowired
	SessionFactory sessionFactory;
	public Logger logger = Logger.getLogger(GenericDaoImpl.class);


	// Domain implementation class.
	private Class<E> domainClass;

	private String restrictions;

	

	/**
	 * This method should be called on initialization of the Dao to set the
	 * persistent Class from the generic E parameter, if the class was not set
	 * explicitly.
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void setUpDao() {

		if (domainClass == null) {

			domainClass = (Class<E>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
	}

	@SuppressWarnings("unchecked")
	public final E findById(final Long id) {

		StringBuffer sb = new StringBuffer();
		sb.append("Attempting to find HibernateEntity ");
		sb.append(domainClass.getSimpleName());
		sb.append("by id ");
		sb.append(id);

		/*
		 * logger.debug(sb.toString()); return (E)
		 * entityManager.find(domainClass, id);
		 */

		
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ domainClass.getName() + " i WHERE i.id = " + id);
		return query.list().size() > 0 ? (E) query.list().get(0) : null;

	}

	public final boolean exists(final Long id) {

		return findById(id) != null;
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		
		 Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ domainClass.getName() + " i ORDER BY i.id DESC");
		return query.list();
	}

	/**
	 * We just return the same instance as passed in here, so no need to
	 * reassign to the return value after call.
	 */
	public E saveOrUpdate(final E entity) {

		// entityManager.persist(entity);
		// use Hibernate Session #saveOrPersist as the JPA
		// persist method does not work with detached objects
		// and client code is expecting the Hibernate saveOrUpdate behaviour

		
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
		
		return entity;
	}
	
	/**
	 * Saves the entity and reattaches it to the session
	 * @param entity 
	 * @return
	 */
	public E saveAndReattach(final E entity){
		
		
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
		
		
		return entity;
	}

	/**
	 * Delete the entity passed in from the database.
	 * 
	 * @param entity
	 */
	public void delete(E entity) {

		
		sessionFactory.getCurrentSession().delete(entity);
		
	}

	
	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}

	/**
	 * @see org.hibernate.Session.doWork(org.hibernate.jdbc.Work)
	 * @param work
	 *            Work instance
	 */
	protected void doWork(final Work work) {

		sessionFactory.getCurrentSession().doWork(work);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	protected List<E> findByCriteria(final Criterion... criterion) {

		return findByCriteria(false, criterion);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<E> findByCriteria(final boolean cacheQuery,
			final Criterion... criterion) {
		clear();
		

				Criteria crit = sessionFactory.getCurrentSession().createCriteria(domainClass);

				for (Criterion c : criterion) {
					crit.add(c);
				}

				crit.setCacheable(cacheQuery);
				return crit.list();
			}
		
	

	@SuppressWarnings("unchecked")
	public List<E> findByExample(final E exampleInstance,
			final String[] excludeProperty) {
		clear();
		

				Criteria criteria = sessionFactory.getCurrentSession().createCriteria(domainClass);
				Example example = Example.create(exampleInstance);

				for (String exclude : excludeProperty) {

					example.excludeProperty(exclude);
				}

				criteria.add(example);

				return criteria.list();
			}
	

	/**
	 * @return the domainClass
	 */
	public Class<E> getDomainClass() {

		return domainClass;
	}

	/**
	 * @param domainClass
	 *            the domainClass to set
	 */
	public void setDomainClass(Class<E> domainClass) {

		this.domainClass = domainClass;
	}

//
//	@SuppressWarnings("unchecked")
//	public List executeFind(final HibernateCallback callback) {
//		clear();
//		Object result = execute(callback);
//
//		if (result != null && !(result instanceof List)) {
//
//			throw new InvalidDataAccessApiUsageException(
//					"Result object returned from HibernateCallback "
//							+ "isn't a List: [" + result + "]");
//		}
//
//		return (List) result;
//	}

	/**
	 * Convert the given SQLException to an appropriate exception from the
	 * <code>org.springframework.Dao</code> hierarchy. Can be overridden in
	 * subclasses.
	 * <p>
	 * Note that a direct SQLException can just occur when callback code
	 * performs direct JDBC access via <code>Session.connection()</code>.
	 * 
	 * @param ex
	 *            the SQLException
	 * @return the corresponding DataAccessException instance
	 * @see #setJdbcExceptionTranslator
	 * @see org.hibernate.Session#connection()
	 */
	
	/**
	 * Set the JDBC exception translator for this instance.
	 * <p>
	 * Applied to any SQLException root cause of a Hibernate JDBCException,
	 * overriding Hibernate's default SQLException translation ( which is based
	 * on Hibernate's Dialect for a specific target database ).
	 * 
	 * @param jdbcExceptionTranslator
	 *            the exception translator
	 * @see java.sql.SQLException
	 * @see org.hibernate.JDBCException
	 * @see org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator
	 * @see org.springframework.jdbc.support.SQLStateSQLExceptionTranslator
	 */
	

	

}
