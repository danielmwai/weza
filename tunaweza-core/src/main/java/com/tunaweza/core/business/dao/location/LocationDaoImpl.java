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

package com.tunaweza.core.business.dao.location;

import com.tunaweza.core.business.dao.exceptions.location.LocationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.location.LocationExistsException;
import com.tunaweza.core.business.dao.generic.GenericDaoImpl;
import com.tunaweza.core.business.model.location.Location;
import com.tunaweza.core.business.model.persistence.PersistentEntity;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "locationDao")
@Transactional
public class LocationDaoImpl extends GenericDaoImpl<Location> implements
		LocationDao {

        @Override
	public Location findLocationById(long lid)
			throws LocationDoesNotExistException {
		Location location = (Location) findById(lid);
		if (location == null) {
			throw new LocationDoesNotExistException();
		}

		return location;
	}

	@Override
	public Location findLocationByName(String name)
			throws LocationDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.locationName = '"
				+ name + "'");

		Location location = null;

		if (query.list().size() > 0) {
			location = (Location) query.list().get(0);
		} else {
			throw new LocationDoesNotExistException("Location with name : "
					+ name + " does not exist");
		}

		return location;

	}

	@Override
	public Location findLocation(Location location)
			throws LocationDoesNotExistException {

		Location location1 = (Location) findById(location.getId());
		if (location1 == null) {
			throw new LocationDoesNotExistException();
		}
		return location1;
	}

	@Override
	public void deleteLocation(long lid) throws LocationDoesNotExistException {
		Location location = (Location) findById(lid);
		if (location == null) {
			throw new LocationDoesNotExistException();
		}
		deleteLocation(location);

	}

	@Override
	public void deleteLocation(Location location)
			throws LocationDoesNotExistException {
		try {
			delete(location);
		} catch (IllegalArgumentException e) {
			throw new LocationDoesNotExistException();
		}

	}

	@Override
	public LocationDAO addLocation(Location location)
			throws LocationExistsException {
		Location location1 = (Location) findById(location.getId());

		if (location1 != null) {
			throw new LocationExistsException();
		}

		location.setLocationCode(getBiggestNumber().toString());
		saveOrUpdate(location);
		return this;
	}

        @Override
		public List<Location> getAllLocations() {
		return findAll();
	}

	@Override
	public void saveLocation(Location location) {
		saveOrUpdate(location);

	}

	@Override
	public Integer getBiggestNumber() {

		Session session = (Session) getEntityManager().getDelegate();
		Query query = session
				.createSQLQuery("SELECT location_code FROM location");
		Integer i = new Integer((query.list().size()) + 1);

		return i;
	}

    @Override
    public com.tunaweza.core.business.model.user.Location findLocationById(long lid) throws LocationDoesNotExistException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public com.tunaweza.core.business.model.user.Location findLocationByName(String name) throws LocationDoesNotExistException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public com.tunaweza.core.business.model.user.Location findLocation(com.tunaweza.core.business.model.user.Location location) throws LocationDoesNotExistException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteLocation(com.tunaweza.core.business.model.user.Location location) throws LocationDoesNotExistException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LocationDao addLocation(com.tunaweza.core.business.model.user.Location location) throws LocationExistsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveLocation(com.tunaweza.core.business.model.user.Location location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersistentEntity findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean exists(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List findByExample(PersistentEntity exampleInstance, String[] excludeProperty) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersistentEntity saveOrUpdate(PersistentEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(PersistentEntity entity) {
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
