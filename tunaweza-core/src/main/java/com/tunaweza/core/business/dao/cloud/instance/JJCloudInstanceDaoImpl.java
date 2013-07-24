/*
 * The MIT License
 *
 * Copyright 2013 naistech.
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

package com.tunaweza.core.business.dao.cloud.instance;

import com.tunaweza.core.business.dao.exceptions.cloud.JJCloudInstanceExistsException;
import com.tunaweza.core.business.dao.exceptions.cloud.JJCoudInstanceDoesNotExistException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.cloud.JJCloudInstance;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel Mwai
 */

@Repository(value="jjCloudInstanceDao")
public class JJCloudInstanceDaoImpl extends GenericDaoImpl<JJCloudInstance>
		implements JJCloudInstanceDao {
@Autowired
SessionFactory sessionFactory ;

	@Override
	public JJCloudInstance findInstanceById(Long instanceId)
			throws JJCoudInstanceDoesNotExistException {
		
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.id = " + instanceId);

		if (query.list().size() == 0) {
			throw new JJCoudInstanceDoesNotExistException("Cloud Instance with id "
					+ instanceId + " does not exist");
		}

		return (JJCloudInstance) query.list().get(0);
		
	}

	@Override
	public JJCloudInstance saveInstance(JJCloudInstance cloudInstance)
			throws JJCloudInstanceExistsException {
      
		try {
			saveOrUpdate(cloudInstance);
		} catch (ConstraintViolationException e) {
			throw new JJCloudInstanceExistsException();
		} catch (DataIntegrityViolationException e) {

			throw new JJCloudInstanceExistsException();
		}
		return cloudInstance;
	}

	@Override
	public List<JJCloudInstance> getAllInstances() {
		// TODO Auto-generated method stub
		return findAll();
	}

	@Override
	public void enableInstance(JJCloudInstance cloudInstance) {
		// TODO Auto-generated method stub

	}

	@Override
	public JJCloudInstance findInstanceByName(String name)
			throws JJCoudInstanceDoesNotExistException {
	
     Query query=sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.company_name = " + name);

		if (query.list().size() == 0) {
			throw new JJCoudInstanceDoesNotExistException("Cloud Instance with name "
					+ name + " does not exist");
		}

		return (JJCloudInstance) query.list().get(0);
	}
	
	public void removeJJCloudInstance(Long id){
		delete(findById(id));
	}

	@Override
	public void createDatabase(Long db_id, String dbName, String dbPassword)
			throws JJCloudInstanceExistsException {
		
		
	}

}
