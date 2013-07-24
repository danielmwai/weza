/**
 * 
 */
package com.tunaweza.web.controller;


import com.tunaweza.core.business.dao.exceptions.company.CompanyDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.company.settings.CompanySettingsExistsException;
import com.tunaweza.core.business.model.company.Company;
import com.tunaweza.core.business.service.company.CompanyService;
import com.tunaweza.core.business.service.company.settings.CompanySettings;
import com.tunaweza.core.business.service.company.settings.CompanySettingsService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.core.business.utils.PropertiesUtil;
import com.tunaweza.web.spring.configuration.companysettings.bean.CompanySettingBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.COMPANY_LOGO;
import static com.tunaweza.web.views.Views.DISPLAY_COMPANY_SETTING;
import static com.tunaweza.web.views.Views.EDIT_COMPANY_SETTING;
import static com.tunaweza.web.views.Views.IMAGEDISPLAY;
import static com.tunaweza.web.views.Views.POST_COMPANY_SETTING;
import static com.tunaweza.web.views.Views.POST_COMPANY_SETTING_REPLY;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.engine.jdbc.NonContextualLobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alfred Githinji
 * 
 * @author James Mungai
 */
@Controller
@RequestMapping(Views.COMPANY_SETTING)
public class CompanySettingController implements Views {

	@Autowired
	private Validator validator;

	@Autowired
	private CompanySettingsService companySettingsService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserCastService userCastService;


	protected final Log LOGGER = LogFactory.getLog(getClass());

	@RequestMapping(value = POST_COMPANY_SETTING, method = RequestMethod.GET)
	public String newCompanySettingForm(Model model) {

		model.addAttribute("companySettingBean", new CompanySettingBean());
		return POST_COMPANY_SETTING;
	}

	@RequestMapping(value = POST_COMPANY_SETTING, method = RequestMethod.POST)
	public String postCompanySetting(CompanySettingBean companySettingBean,
			Model model) throws CompanySettingsExistsException {
		Set<ConstraintViolation<CompanySettingBean>> failures = validator
				.validate(companySettingBean);
		if (!failures.isEmpty()) {
			model.addAttribute("Message", submitValidationMessages(failures));
			return POST_COMPANY_SETTING_REPLY;
		} else {
			if (!companySettingBean.getLogo_image().isEmpty()) {
				if (companySettingBean.getLogo_image().getSize() == 0) {
					model.addAttribute("Message", "You must upload an image");
					return POST_COMPANY_SETTING_REPLY;

				}

				LOGGER.info("\n SOLUTIONCONTROLLER: uploaded file mimetype -----"
						+ companySettingBean.getLogo_image().getContentType());
				if (!PropertiesUtil.isfileSupported("image", companySettingBean
						.getLogo_image().getContentType())) {
					model.addAttribute("Message",
							"Upload Only Supported Files ");
					return POST_COMPANY_SETTING_REPLY;

				}
			}

			Company company;
			CompanySettings companySettings = null;
			try {
				/*company = companyService.findCompanyById(1l);*/
				/*company = companyService.findCompanyByEmail(userCastService
						.getUser().getEmail());*/
				company = companyService.findCompanyById(userCastService
						.getUser().getId());
				companySettings = company.getCompanySettings();

				if (companySettings == null) {
					companySettings = new CompanySettings();
					companySettings.setCompany(company);
				}
			} catch (CompanyDoesNotExistException e) {
				model.addAttribute("Message", "Company not registered.");
				e.printStackTrace();
			}

			companySettings.setBg_color_code("#"
					+ companySettingBean.getBg_color_code());
			companySettings.setHeader_color_code("#"
					+ companySettingBean.getHeader_color_code());
			companySettings.setMenu_color_code("#"
					+ companySettingBean.getMenu_color_code());
			companySettings.setFont_color_code("#"
					+ companySettingBean.getFont_color_code());
			companySettings.setMentoring(companySettingBean.getMentoring());
			
			if (!companySettingBean.getLogo_image().isEmpty()) {
				companySettings.setLogo_image(NonContextualLobCreator.INSTANCE
						.createBlob(companySettingBean.getLogo_image()
								.getBytes()));
			} else {

				companySettings.setLogo_image(companySettings.getLogo_image());
			}
			companySettings = companySettingsService
					.addCompanySettings(companySettings);
			model.addAttribute("companyId", companySettings.getId());
			System.out.println("c>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+companySettings.getId());

			return POST_COMPANY_SETTING_REPLY;
		}
	}

	@RequestMapping(value = DISPLAY_COMPANY_SETTING, method = RequestMethod.GET)
	public String displayCompanySetting(Model model,
			HttpServletResponse response) throws Exception {
		/*Company company = companyService.findCompanyById(1l);*/
		/*company = companyService.findCompanyByEmail(userCastService
				.getUser().getEmail());*/
		Company company = companyService.findCompanyById(userCastService
				.getUser().getId());
		CompanySettings companySettings = company.getCompanySettings();

		CompanySettingBean companySettingBean = new CompanySettingBean();
		companySettingBean.setBg_color_code(companySettings.getBg_color_code());
		companySettingBean.setHeader_color_code(companySettings
				.getHeader_color_code());
		companySettingBean.setMenu_color_code(companySettings
				.getMenu_color_code());
		companySettingBean.setFont_color_code(companySettings
				.getFont_color_code());
		companySettingBean.setMentoring(companySettings.getMentoring());
		
		model.addAttribute("companySettingBean", companySettingBean);
		return DISPLAY_COMPANY_SETTING;
	}
	@RequestMapping(value = IMAGEDISPLAY, method = RequestMethod.GET)
	public HttpServletResponse image(HttpServletResponse response)
			throws SQLException, IOException,Exception {

		byte[] imageData = null;
		Blob blobImageFromDB = null;
		/*Company company = companyService.findCompanyById(1l);*/
		/*company = companyService.findCompanyByEmail(userCastService
				.getUser().getEmail());*/
		Company company = companyService.findCompanyById(userCastService
				.getUser().getId());
		CompanySettings companySettings = company.getCompanySettings();
		if (companySettings != null) {
			CompanySettings companySetting = company.getCompanySettings();

			if (companySetting != null) {

				blobImageFromDB = companySetting.getLogo_image();
				if(blobImageFromDB!=null){
				imageData = blobImageFromDB.getBytes(1,
						(int) blobImageFromDB.length());
				response.setContentType("image/png,image/jpeg,image/gif,image/ico,image/bmp");

				OutputStream out = response.getOutputStream();
				out.write(imageData);
				out.flush();
				out.close();
				}else{
					System.out.println("there is no image in DB yet");
				}

			} else
				return null;

		}
		return response;
	}

	@RequestMapping(value = EDIT_COMPANY_SETTING, method = RequestMethod.GET)
	public String editCompanySetting(Model model, HttpServletResponse response)
			throws Exception {

		/*Company company = companyService.findCompanyByEmail(userCastService
				.getUser().getEmail());*/
		/*Company company = companyService.findCompanyById(1l);*/
		Company company = companyService.findCompanyById(userCastService
				.getUser().getId());
		CompanySettings companySettings = company.getCompanySettings();

		CompanySettingBean companySettingBean = new CompanySettingBean();

		companySettingBean.setBg_color_code(companySettings.getBg_color_code()
				.replace("#", ""));
		companySettingBean.setHeader_color_code(companySettings
				.getHeader_color_code().replace("#", ""));
		companySettingBean.setMenu_color_code(companySettings
				.getMenu_color_code().replace("#", ""));
		companySettingBean.setFont_color_code(companySettings
				.getFont_color_code().replace("#", ""));
		
		companySettingBean.setMentoring(companySettings.getMentoring());

		model.addAttribute("companySettingBean", companySettingBean);
		model.addAttribute("BG_COLOR", companySettings.getBg_color_code());
		model.addAttribute("HEADER_COLOR",
				companySettings.getHeader_color_code());
		model.addAttribute("MENU_COLOR", companySettings.getMenu_color_code());
		model.addAttribute("FONT_COLOR",companySettings.getFont_color_code());
		return EDIT_COMPANY_SETTING;
	}

	private List<String> submitValidationMessages(
			Set<ConstraintViolation<CompanySettingBean>> failures) {
		List<String> failureMessages = new ArrayList<String>();
		for (ConstraintViolation<CompanySettingBean> failure : failures) {
			failureMessages.add(failure.getMessage());
		}
		return failureMessages;
	}

	/**
	 * Method to display the company logo
	 * 
	 * @author Daniel Nyanumba
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.GET, value = COMPANY_LOGO)
	public HttpServletResponse displayCompanyLogo(HttpServletResponse response)
			throws SQLException, IOException {

		byte[] imageData = null;
		Blob blobImageFromDB = null;

		Company company;
		CompanySettings companySettings = null;
		try {
			/*company = companyService.findCompanyById(1l);*/
			/*company = companyService.findCompanyByEmail(userCastService
					.getUser().getEmail());*/
			company = companyService.findCompanyById(userCastService
					.getUser().getId());
			companySettings = company.getCompanySettings();
		} catch (CompanyDoesNotExistException e1) {
			e1.printStackTrace();
		}

		if (companySettings == null) {

		} else {
			blobImageFromDB = companySettings.getLogo_image();
			imageData = blobImageFromDB.getBytes(1,
					(int) blobImageFromDB.length());
			response.setContentType("image/png,image/jpeg,image/gif,image/ico,image/bmp");

			OutputStream out = response.getOutputStream();
			out.write(imageData);
			out.flush();
			out.close();
		}

		return response;
	}

}
