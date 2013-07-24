package com.tunaweza.web.controller.company;

import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.company.CompanyService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.CONTACTS;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 
 * @author A Odera
 *
 */
@Controller
@RequestMapping(Views.CONTACT_DETAILS)
public class ContactController implements Views {
	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private Validator validator;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
    private UserCastService userCastService;

	@RequestMapping(method = RequestMethod.GET, value = CONTACTS)
	public String CompanyContact(Model model, HttpServletRequest request)throws CompanyDoesNotExistException {
	
	User user = userCastService.getUser();
	Company companydetails = user.getUserCompany();
	
		
			if (companydetails != null){
			 model.addAttribute("email", companydetails.getEmail());
			 model.addAttribute("website", companydetails.getWebsite());
			 model.addAttribute("firsttel", companydetails.getFirstline());
			 model.addAttribute("secondtel", companydetails.getSecondline());
			 model.addAttribute("name", companydetails.getName());
			 model.addAttribute("add", companydetails.getAddress());
			 }
			 else{ 

			 model.addAttribute("email", "N/A");
			 model.addAttribute("website", "N/A");
			 model.addAttribute("firsttel", "N/A");
			 model.addAttribute("secondtel", "N/A");
			 model.addAttribute("name", "N/A");
			 model.addAttribute("add", "N/A");
			 }
	
			
	
			 return CONTACTS;
	}	
}

