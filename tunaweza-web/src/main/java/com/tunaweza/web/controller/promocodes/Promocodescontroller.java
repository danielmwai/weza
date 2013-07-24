package com.tunaweza.web.controller.promocodes;


import com.tunaweza.core.business.dao.exceptions.licence.LicenseDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.promocode.PromocodeDoesNotExistException;
import com.tunaweza.core.business.model.licence.License;
import com.tunaweza.core.business.model.promocodes.Promocode;
import com.tunaweza.core.business.service.license.LicenseService;
import com.tunaweza.core.business.service.promocodes.PromocodeService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.web.spring.configuration.license.bean.AddLicenseBean;
import com.tunaweza.web.spring.configuration.promocodez.bean.CreatePromoCodeBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.DISSOCIATE_PROMO_CODE;
import static com.tunaweza.web.views.Views.EDIT_PROMO_CODE;
import static com.tunaweza.web.views.Views.PROMO_CODE_SUCCESS;
import static com.tunaweza.web.views.Views.PROMO_CODE_VIEW;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;
import java.util.Collections;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author AOdera
 */

@Controller
//@RequestMapping(Views.PROMO_CODE)
public class Promocodescontroller implements Views {
	protected final Log LOGGER = LogFactory.getLog(getClass());
	
	@Autowired
	private Validator validator;

	@Autowired
	private PromocodeService promocodeService;
	
	@Autowired
    private UserCastService userCastService;
	
	@Autowired
	private LicenseService licenseService;
	
	@RequestMapping(method = RequestMethod.GET, value = PROMO_CODE_VIEW)
	public String newLicense(Model model,HttpServletRequest request ) {

		
		List<License> licenseList = licenseService.listAllLicenses();
		
		
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>THIS IS THE LICENSE"+ licenseList);
		if (licenseList== null){
			model.addAttribute("No license exists", licenseList);
		
		}
		else {
			model.addAttribute("LICENSELIST", licenseList);
		}
		model.addAttribute("createPromoCodeBean", new CreatePromoCodeBean());
	    model.addAttribute("addLicenseBean", new AddLicenseBean());
		
		return PROMO_CODE_VIEW;

	}
	
	@RequestMapping(method = RequestMethod.POST, value = PROMO_CODE_VIEW)
	public @ResponseBody Map<String, ? extends Object>
	  promotioncode( @RequestBody CreatePromoCodeBean createPromoCodeBean,AddLicenseBean addLicenseBean, Model model)throws PromocodeDoesNotExistException, LicenseDoesNotExistException {
	
		
		Promocode promcode= new Promocode();

		License license= licenseService.findLicenseById(createPromoCodeBean.getAssociatedlicenseid());
		
		if (license.isAssociated()==true){
			
			return Collections.singletonMap("u", "The Selected License Is Already In Use");
		}
		else{
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>THIS IS THE PROMO CODE NAME"+ createPromoCodeBean.getPromocodename() );
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>THIS IS THE ASSOCIATED LICENNSE ID"+createPromoCodeBean.getAssociatedlicenseid());
		
		promcode.setPromocodename(createPromoCodeBean.getPromocodename());
		promcode.setExtrausers(createPromoCodeBean.getNumberofextrausers());
		promcode.setAssociatedlicense(license.getName());
		promcode.setLicense(license);
		promcode.setDissociatedid(createPromoCodeBean.getAssociatedlicenseid());
		promcode.setInuse(createPromoCodeBean.isUse());
		license.setAssociated(createPromoCodeBean.isUse());
		
		if (promcode.isInuse()==true){
		Long maxusers =license.getMax_users();
		license.setMax_users(maxusers+createPromoCodeBean.getNumberofextrausers());
		}
		
	    Promocode trial= promocodeService.addPromocode(promcode);
	    License licen=licenseService.addLicense(license);
			
		return Collections.singletonMap("u", "Saved");
		}
		
		
	}
	@RequestMapping(method = RequestMethod.GET, value = PROMO_CODE_SUCCESS)
	public String listpromocodes(Model model) {

		List<Promocode> promocodelist = promocodeService.listAllPromocodes();
		model.addAttribute("PROMOCODELST", promocodelist);
		return PROMO_CODE_SUCCESS;

	}
	@RequestMapping(method = RequestMethod.GET, value = EDIT_PROMO_CODE)
	public String editpromocode(@RequestParam(value = "roleId", required = true) String roleId,  Model model) throws PromocodeDoesNotExistException, LicenseDoesNotExistException {
		
		Promocode trial= promocodeService.findPromocodeById(Long.parseLong(roleId));
		
		List<Promocode> promocodelist = promocodeService.listAllPromocodes();
		
        List<License> licenseList = licenseService.listAllLicenses();
		
		if (licenseList== null){
			model.addAttribute("No license exists", licenseList);
		
		}
		else {
			model.addAttribute("LICENSELIST", licenseList);
		}
		
		CreatePromoCodeBean createPromoCodeBean= new CreatePromoCodeBean();
		createPromoCodeBean.setPromocodeid(trial.getId());
		createPromoCodeBean.setPromocodename(trial.getPromocodename());
		createPromoCodeBean.setNumberofextrausers(trial.getExtrausers());
		createPromoCodeBean.setUse(trial.isInuse());
		
		model.addAttribute("createPromoCodeBean", createPromoCodeBean);
		
		return PROMO_CODE_VIEW;
		
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(method = RequestMethod.POST, value = EDIT_PROMO_CODE)
	public @ResponseBody Map<String, ? extends Object>
	  editpromotioncodeimplementation( @RequestBody CreatePromoCodeBean createPromoCodeBean,AddLicenseBean addLicenseBean, Model model,@RequestParam(value = "roleId", required = true) String roleId)throws PromocodeDoesNotExistException, LicenseDoesNotExistException {
	
		
		Promocode promcode= new Promocode();

		License license= licenseService.findLicenseById(createPromoCodeBean.getAssociatedlicenseid());
		promcode = promocodeService.findPromocodeById(Long.parseLong(roleId));
		
		
		if (license.isAssociated()==true){
			
			return Collections.singletonMap("u", "The Selected License Is Already In Use");
		}
		
		else if(promcode.isInuse()==true){
			return Collections.singletonMap("u", "The Promocode is in use,dissociate first");
		}
		else{
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>THIS IS THE PROMO CODE NAME"+ createPromoCodeBean.getPromocodename() );
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>THIS IS THE ASSOCIATED LICENNSE ID"+createPromoCodeBean.getAssociatedlicenseid());
		
		promcode.setPromocodename(createPromoCodeBean.getPromocodename());
		promcode.setExtrausers(createPromoCodeBean.getNumberofextrausers());
		promcode.setAssociatedlicense(license.getName());
		promcode.setLicense(license);
		promcode.setDissociatedid(createPromoCodeBean.getAssociatedlicenseid());
		promcode.setInuse(createPromoCodeBean.isUse());
		license.setAssociated(createPromoCodeBean.isUse());
		
		if (promcode.isInuse()==true){
		Long maxusers =license.getMax_users();
		license.setMax_users(maxusers+createPromoCodeBean.getNumberofextrausers());
		}
		
	    Promocode trial= promocodeService.addPromocode(promcode);
	    License licen=licenseService.addLicense(license);
			
		return Collections.singletonMap("u", "Saved");
		}
		
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = DISSOCIATE_PROMO_CODE)
	public String dissociatepromocode(@RequestParam(value = "roleId", required = true) String roleId,CreatePromoCodeBean createPromoCodeBean,  Model model) throws PromocodeDoesNotExistException, LicenseDoesNotExistException {
		
		Promocode prom= new Promocode();
		
		Promocode trial= promocodeService.findPromocodeById(Long.parseLong(roleId));
		Long disslicense=trial.getDissociatedid();
		License license= licenseService.findLicenseById(disslicense);
		
		Long dissociateusers = trial.getExtrausers();
		Long maxusers = license.getMax_users();
		
		license.setMax_users(maxusers-dissociateusers);
		license.setAssociated(false);
		trial.setInuse(false);
		
		License licene= licenseService.addLicense(license);
		Promocode pcode= promocodeService.addPromocode(trial);
		
		
		model.addAttribute("createPromoCodeBean", createPromoCodeBean);
		 
		return PROMO_CODE_SUCCESS;
	
	}
}