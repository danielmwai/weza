/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.location;

import com.tunaweza.core.business.dao.exceptions.location.LocationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.location.LocationExistsException;
import com.tunaweza.core.business.model.user.Location;
import java.util.List;

/**
 *
 * @author naistech
 */
public interface LocationService {
 
	/**
	 * 
	 * @param location
	 * @throws LocationExistsException
	 */
	public void addLocation(Location location) throws LocationExistsException;

	/**
	 * 
	 * @param location
	 */
	public void saveLocation(Location location);

	/**
	 * 
	 * @param name
	 * @return
	 * @throws LocationDoesNotExistException
	 */
	public Location getLocationByName(String name) throws LocationDoesNotExistException;

	/**
	 * 
	 * @param locationId
	 * @return
	 * @throws LocationDoesNotExistException
	 */
	public Location getLocationById(long locationId) throws LocationDoesNotExistException;

	/**
	 * 
	 * @return
	 */
	public List<Location> getAllLocations();

	/**
	 * 
	 * @param location
	 * @throws LocationDoesNotExistException
	 */
	public void deleteLocation(Location location) throws LocationDoesNotExistException;
	
	/**
	 * 
	 * @param locationId
	 * @throws LocationDoesNotExistException
	 */
	public void deleteLocation(long locationId) throws LocationDoesNotExistException;
	
}

