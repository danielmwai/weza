package com.tunaweza.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.validation.Validator;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.validation.ConstraintViolation;

import org.apache.commons.logging.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.tunaweza.core.business.dao.exceptions.location.LocationExistsException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.model.user.Location;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.location.LocationService;
import com.tunaweza.core.business.service.user.UserService;
import com.tunaweza.web.spring.configuration.location.bean.AddLocationBean;
import com.tunaweza.web.spring.configuration.location.bean.EditLocationBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.EDIT_LOCATION;
import static com.tunaweza.web.views.Views.EDIT_LOCATION_FORM;
import static com.tunaweza.web.views.Views.LOCATIONS_LIST;
import static com.tunaweza.web.views.Views.LOCATION_LIST;
import static com.tunaweza.web.views.Views.NEW_LOCATION;

/**
 * 
 * @author Joyce Echessa
 * 
 *         Location Controller
 * 
 */

@Controller
@RequestMapping(Views.LOCATION)
public class LocationController implements Views {
	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private LocationService locationService;

	@Autowired
	private Validator validator;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, value = NEW_LOCATION)
	public String newLocationForm(Model model) {
		model.addAttribute("addLocationBean", new AddLocationBean());

		return NEW_LOCATION;
	}

	@RequestMapping(value = EDIT_LOCATION_FORM, method = RequestMethod.GET)
	public String editLocation(@RequestParam("locationId") String id,
			Model model) throws Exception {
		int locationId = Integer.valueOf(id);
		Location location = locationService.getLocationById(locationId);
		EditLocationBean editLocationBean = new EditLocationBean();
		editLocationBean.setId(location.getId());
		editLocationBean.setName(location.getLocationName());
		editLocationBean.setDescription(location.getDescription());
		model.addAttribute("editLocationBean", editLocationBean);

		return EDIT_LOCATION;
	}

	@RequestMapping(value = "/deleteLocation", method = RequestMethod.GET)
	public String deleteLocation(@RequestParam("locationId") String id,
			Model model) throws Exception {
		int locationId = Integer.valueOf(id);

		List<User> users = new ArrayList<User>();
		try {
			users = userService.getUsersByLocation(locationId);
			for (User present : users){
			System.out.println("PRESENT USERS =+++++++>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+present);
			}
		} catch (UserDoesNotExistException e) {
			e.printStackTrace();
		}
		if (users.size() == 0) {
			locationService.deleteLocation(locationId);
		} else {
			model.addAttribute("ERROR",
					"Could not delete location. Users allocated to location");
		}
		List<Location> locationList = locationService.getAllLocations();
		Collections.sort(locationList);
		model.addAttribute("LOCATIONLIST", locationList);

		return LOCATION_LIST;

	}

	@RequestMapping(method = RequestMethod.GET, value = LOCATIONS_LIST)
	public String listLocations(Model model) {
		List<Location> locationList = locationService.getAllLocations();
		Collections.sort(locationList);
		model.addAttribute("LOCATIONLIST", locationList);

		return LOCATION_LIST;
	}

	@RequestMapping(method = RequestMethod.POST, value = NEW_LOCATION)
	public @ResponseBody
	Map<String, ? extends Object> addLocation(
			@RequestBody AddLocationBean addLocationBean) {
		Set<ConstraintViolation<AddLocationBean>> failures = validator
				.validate(addLocationBean);
		if (!failures.isEmpty()) {
			addValidationMessages(failures);
			return Collections.singletonMap("u", "Fill in required fields");
		}
		if (failures.isEmpty()) {
			// This will be used to set the location.
			// Its value depends on whether the user used the drop down country
			// list
			// or chose 'Other' and entered a location
			String chosenLocation;
			if (addLocationBean.getLocation() == "Other") {
				chosenLocation = addLocationBean.getName();
			} else {
				chosenLocation = addLocationBean.getLocation();
			}

			Location location = new Location();
			try {
				location = locationService.getLocationByName(chosenLocation);
				if (location != null) {
					return Collections.singletonMap("u",
							"A location with that name already exists");
				}
			} catch (Exception e) {
				location.setLocationName(chosenLocation);
				if (!addLocationBean.getDescription().equals("")
						|| addLocationBean.getDescription() != null) {
					location.setDescription(addLocationBean.getDescription());
				}

				try {
					locationService.addLocation(location);
				} catch (LocationExistsException ex) {
					ex.printStackTrace();
				}
				return Collections.singletonMap("u", "Saved");
			}
		}
		return Collections.singletonMap("u",
				"Error: Location could not be saved");
	}

	@RequestMapping(method = RequestMethod.POST, value = EDIT_LOCATION)
	public @ResponseBody
	Map<String, ? extends Object> editLocation(
			@RequestBody EditLocationBean editLocationBean) throws Exception {
		Set<ConstraintViolation<EditLocationBean>> failures = validator
				.validate(editLocationBean);
		if (!failures.isEmpty()) {
			editValidationMessages(failures);
			return Collections.singletonMap("u", "Fill in required fields");
		}

		else {
			int locationId = Integer.valueOf(String.valueOf(editLocationBean
					.getId()));
			Location location = locationService.getLocationById(locationId);
			location.setLocationName(editLocationBean.getName());
			if (!editLocationBean.getDescription().equals("")
					|| editLocationBean.getDescription() != null) {
				location.setDescription(editLocationBean.getDescription());
			}

			locationService.saveLocation(location);

			return Collections.singletonMap("u", "Saved");
		}
	}

	private Map<String, String> addValidationMessages(
			Set<ConstraintViolation<AddLocationBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<AddLocationBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	private Map<String, String> editValidationMessages(
			Set<ConstraintViolation<EditLocationBean>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<EditLocationBean> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}
}
