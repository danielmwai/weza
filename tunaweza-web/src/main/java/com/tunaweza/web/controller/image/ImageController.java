package com.tunaweza.web.controller.image;

import com.tunaweza.core.business.model.image.Image;
import com.tunaweza.core.business.service.image.ImageService;
import com.tunaweza.core.business.utils.PropertiesUtil;
import com.tunaweza.web.spring.configuration.image.bean.ImageUploadBean;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.IMAGE;
import static com.tunaweza.web.views.Views.IMAGE_DISPLAY;
import static com.tunaweza.web.views.Views.IMAGE_UPLOAD_REPLY;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.engine.jdbc.NonContextualLobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * @author Ephraim Muhia
 * @author Samuel Waweru
 * 
 *         Module Controller
 * 
 */

@Controller
@RequestMapping(Views.ADV_IMAGE)
public class ImageController implements Views {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private ImageService imageService;
	
	@RequestMapping(method = RequestMethod.GET, value = IMAGE)
	public String showImageForm(Model model,HttpServletRequest request) {
		List<Image> images= imageService.getAllImages(); 
		model.addAttribute("IMAGES",images);
		int index=request.getProtocol().indexOf("/");
		String protocol=request.getProtocol().substring(0, index).toLowerCase();
		int port=request.getLocalPort();
		String serverAddress= protocol +"://"+request.getServerName()+":"+port;
		model.addAttribute("SERVERADDRESS", serverAddress);
		return IMAGE;
	}
		
	@RequestMapping(value = IMAGE, method = RequestMethod.POST)
	public String uploadImage(ImageUploadBean imageUploadBean, Model model,HttpServletRequest request)
			throws Exception
	{

		if (imageUploadBean.getImage().getSize() == 0)
		{
			model.addAttribute("Message", "You must upload a solution");
			return IMAGE_UPLOAD_REPLY;
		}

		LOGGER.info("\n IMAGECONTROLLER: uploaded image mimetype -----"+imageUploadBean.getImage().getContentType());
		if (!PropertiesUtil.isfileSupported("image",imageUploadBean.getImage().getContentType())) {
			model.addAttribute("Message", "Upload Only Supported Files ");

			return IMAGE_UPLOAD_REPLY;
		}
		Image image = new Image();
		NonContextualLobCreator.INSTANCE.createBlob(imageUploadBean.getImage().getBytes());
		image.setImage(NonContextualLobCreator.INSTANCE.createBlob(imageUploadBean.getImage().getBytes()));
		image.setContentType(imageUploadBean.getImage().getContentType());
		image.setImageText(imageUploadBean.getImage().getOriginalFilename());
		Image savedImage=imageService.saveImage(image);
		
		int index=request.getProtocol().indexOf("/");
		String protocol=request.getProtocol().substring(0, index).toLowerCase();
		int port=request.getLocalPort();
		String serverAddress= protocol +"://"+request.getServerName()+":"+port;
		model.addAttribute("SERVERADDRESS", serverAddress);
		model.addAttribute("IMAGE",savedImage);
		model.addAttribute("Message", "Success");
		return IMAGE_UPLOAD_REPLY;	

	}
	@RequestMapping(method = RequestMethod.GET, value = IMAGE_DISPLAY)
	public void displayImage(Model model,
			HttpServletResponse response, @RequestParam(value="imageId", required=true) String imageId) {
		Image image= imageService.getImage(Long.valueOf(imageId)); 

		try
		{
			int fileSize = (int)image.getImage().length();
			
			response.setBufferSize(fileSize);
			response.setContentType(image.getContentType());	
			response.setContentLength(fileSize);
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(image.getImage().getBinaryStream(), out);
			out.flush();
			out.close();
			
		}
		catch (IOException e)
		{
			LOGGER.error(e);
		}
		catch (SQLException e)
		{
			LOGGER.error(e);
		}
		
	}
	
	

}
