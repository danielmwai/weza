package com.tunaweza.web.controller.license;

import com.tunaweza.core.business.model.licence.License;
import com.tunaweza.core.business.service.license.LicenseService;
import com.tunaweza.web.spring.configuration.license.bean.AddLicenseBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.CREATE_LICENSE;
import static com.tunaweza.web.views.Views.LIST_LICENSES;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(Views.LICENSE)
public class LicenseController implements Views {
	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private LicenseService licenseService;

	@RequestMapping(method = RequestMethod.GET, value = CREATE_LICENSE)
	public String newLicense(Model model) {

		model.addAttribute("addLicenseBean", new AddLicenseBean());

		return CREATE_LICENSE;

	}

	@RequestMapping(method = RequestMethod.POST, value = CREATE_LICENSE)
	public @ResponseBody Map<String, ? extends Object>
	postLicense(@RequestBody AddLicenseBean addLicenseBean, Model model) {
		
		System.out.println("VALUES VALUES VALUES >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+addLicenseBean.getCost());

		License license = new License();
		license.setMax_users(Long.parseLong(addLicenseBean.getMax_users()));
		license.setCost(Float.parseFloat(addLicenseBean.getCost()));
		license.setName(addLicenseBean.getName());

		License newLicense = licenseService.addLicense(license);

		model.addAttribute("licenseId", license.getId());

		 return Collections.singletonMap("u", "Saved");

	}

	@RequestMapping(method = RequestMethod.GET, value = LIST_LICENSES)
	public String listLocations(Model model) {

		List<License> licenseList = licenseService.listAllLicenses();
		model.addAttribute("LICENSELIST", licenseList);
		return LIST_LICENSES;

	}
}
