package com.tunaweza.web.spring.configuration.image.bean;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * @author Samuel Waweru
 */
public class ImageUploadBean 
{
		
	private CommonsMultipartFile image;

	public CommonsMultipartFile getImage() {
		return image;
	}

	public void setImage(CommonsMultipartFile image) {
		this.image = image;
	}
	
		
}
