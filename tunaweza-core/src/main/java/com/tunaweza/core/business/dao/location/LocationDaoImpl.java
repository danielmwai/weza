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
import com.tunaweza.core.business.model.user.Location;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Autowired
SessionFactory sessionFactory;
	@Override
	public Location findLocationById(long lid)
			throws LocationDoesNotExistException {
		Location location = findById(lid);
		if (location == null) {
			throw new LocationDoesNotExistException();
		}

		return location;
	}

	@Override
	public Location findLocationByName(String name)
			throws LocationDoesNotExistException {


		Query query = sessionFactory.getCurrentSession().createQuery("SELECT i FROM "
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

		Location location1 = findById(location.getId());
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
	public LocationDao addLocation(Location location)
			throws LocationExistsException {
		Location location1 = findById(location.getId());

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

		Query query = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT location_code FROM location");
		Integer i = new Integer((query.list().size()) + 1);

		return i;
	}

}
