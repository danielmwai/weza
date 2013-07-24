/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.service.location;

import com.tunaweza.core.business.dao.exceptions.location.LocationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.location.LocationExistsException;
import com.tunaweza.core.business.dao.location.LocationDao;
import com.tunaweza.core.business.model.user.Location;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naistech
 */

@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService{

	@Autowired
	LocationDao locationDao;
	
	@Override
	public void addLocation(Location location) throws LocationExistsException {
		locationDao.addLocation(location);		
	}

	@Override
	public void saveLocation(Location location) {
		locationDao.saveLocation(location);
	}

	@Override
	public Location getLocationByName(String name) throws LocationDoesNotExistException {
		return locationDao.findLocationByName(name);
	}

	@Override
	public Location getLocationById(long locationId) throws LocationDoesNotExistException {
		return locationDao.findLocationById(locationId);
	}

	@Override
	public List<Location> getAllLocations() {
		return locationDao.getAllLocations();
	}

	@Override
	public void deleteLocation(Location location) throws LocationDoesNotExistException {
		locationDao.deleteLocation(location);
		
	}

	@Override
	public void deleteLocation(long locationId) throws LocationDoesNotExistException {
		locationDao.deleteLocation(locationId);
	}

}
