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
import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.user.Location;
import java.util.List;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface LocationDao  extends GenericDao<Location> {

	/**
	 * 
	 * @param uid
	 * @return
	 * @throws LocationDoesNotExistException
	 */
	public Location findLocationById(long lid) throws LocationDoesNotExistException;
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws LocationDoesNotExistException
	 */
	public Location findLocationByName(String name) throws LocationDoesNotExistException;

	/**
	 * 
	 * @param location
	 * @return
	 * @throws LocationDoesNotExistException
	 */
	public Location findLocation(Location location) throws LocationDoesNotExistException;

	/**
	 * 
	 * @param uid
	 * @throws LocationDoesNotExistException
	 */
	public void deleteLocation(long lid) throws LocationDoesNotExistException;

	/**
	 * 
	 * @param location
	 * @throws LocationDoesNotExistException
	 */
	public void deleteLocation(Location location) throws LocationDoesNotExistException;

	/**
	 * 
	 * @param location
	 * @return
	 * @throws LocationExistsException
	 */
	public LocationDao addLocation(Location location) throws LocationExistsException;

	/**
	 * 
	 * @return
	 */
	public List<Location> getAllLocations();

	/**
	 * 
	 * @param location
	 */
	public void saveLocation(Location location);
	
	/**
	 * 
	 * @return
	 */
	public Integer getBiggestNumber();

}
