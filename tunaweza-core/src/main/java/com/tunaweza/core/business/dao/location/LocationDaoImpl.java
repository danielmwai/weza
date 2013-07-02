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
import com.tunaweza.core.business.model.location.Location2;
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
public class LocationDaoImpl extends GenericDaoImpl<Location2> implements
		LocationDao {

                
                @Override
        	public Location2 findLocationById(long lid)
			throws LocationDoesNotExistException {
		Location2 location = (Location2) findById(lid);
		if (location == null) {
			throw new LocationDoesNotExistException();
		}

		return location;
	}

	@Override
	public Location2 findLocationByName(String name)
			throws LocationDoesNotExistException {

		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.locationName = '"
				+ name + "'");

		Location2 location = null;

		if (query.list().size() > 0) {
			location = (Location2) query.list().get(0);
		} else {
			throw new LocationDoesNotExistException("Location with name : "
					+ name + " does not exist");
		}

		return location;

	}

	@Override
	public Location2 findLocation(Location2 location)
			throws LocationDoesNotExistException {

		Location2 location1 = (Location2) findById(location.getId());
		if (location1 == null) {
			throw new LocationDoesNotExistException();
		}
		return location1;
	}

	@Override
	public void deleteLocation(long lid) throws LocationDoesNotExistException {
		Location2 location = (Location2) findById(lid);
		if (location == null) {
			throw new LocationDoesNotExistException();
		}
		deleteLocation(location);

	}

	@Override
	public void deleteLocation(Location2 location)
			throws LocationDoesNotExistException {
		try {
			delete(location);
		} catch (IllegalArgumentException e) {
			throw new LocationDoesNotExistException();
		}

	}

	@Override
	public LocationDao addLocation(Location2 location)
			throws LocationExistsException {
		Location2 location1 = (Location2) findById(location.getId());

		if (location1 != null) {
			throw new LocationExistsException();
		}

		location.setLocationCode(getBiggestNumber().toString());
		saveOrUpdate(location);
		return this;
	}

        @Override
		public List<Location2> getAllLocations() {
		return findAll();
	}

	@Override
	public void saveLocation(Location2 location) {
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



}
