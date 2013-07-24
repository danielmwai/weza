/**
 * 
 */
package com.tunaweza.web.spring.configuration.companysettings.bean;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author james mbugua
 *
 */
public class CompanySettingBean {

	private CommonsMultipartFile logo_image;
	private String bg_color_code;
	private String header_color_code;
	private String menu_color_code;
	private String font_color_code;
	private boolean mentoring;
	private String header_links_color_code;
	private String navigation_hover_color;
	
	private String id;
	
	/**
	 * @return the logo_image
	 */
	public CommonsMultipartFile getLogo_image() {
		return logo_image;
	}
	/**
	 * @param logo_image the logo_image to set
	 */
	public void setLogo_image(CommonsMultipartFile logo_image) {
		this.logo_image = logo_image;
	}
	/**
	 * @return the bg_color_code
	 */
	public String getBg_color_code() {
		return bg_color_code;
	}
	/**
	 * @param bg_color_code the bg_color_code to set
	 */
	public void setBg_color_code(String bg_color_code) {
		this.bg_color_code = bg_color_code;
	}
	/**
	 * @return the header_color_code
	 */
	public String getHeader_color_code() {
		return header_color_code;
	}
	/**
	 * @param header_color_code the header_color_code to set
	 */
	public void setHeader_color_code(String header_color_code) {
		this.header_color_code = header_color_code;
	}
	/**
	 * @return the mentoring
	 */
	public boolean getMentoring() {
		return mentoring;
	}
	/**
	 * @param mentoring the mentoring to set
	 */
	public void setMentoring(boolean mentoring) {
		this.mentoring = mentoring;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return header_links_color_code
	 */
	public String getHeader_links_color_code() {
		return header_links_color_code;
	}
	
	/**
	 * set the navigation links color
	 * @param header_links_color_code the color code for the links
	 */
	public void setHeader_links_color_code(String header_links_color_code) {
		this.header_links_color_code = header_links_color_code;
	}
	
	/**
	 * 
	 * @return the navigation hover_color
	 */
	public String getNavigation_hover_color() {
		return navigation_hover_color;
	}
	
	/**
	 * set hover color for navigation
	 * @param navigation_hover_color
	 */
	public void setNavigation_hover_color(String navigation_hover_color) {
		this.navigation_hover_color = navigation_hover_color;
	}
	/**
	 * @return the menu_color_code
	 */
	public String getMenu_color_code() {
		return menu_color_code;
	}
	/**
	 * @param menu_color_code the menu_color_code to set
	 */
	public void setMenu_color_code(String menu_color_code) {
		this.menu_color_code = menu_color_code;
	}
	/**
	 * @return the font_color_code
	 */
	public String getFont_color_code() {
		return font_color_code;
	}
	/**
	 * @param font_color_code the font_color_code to set
	 */
	public void setFont_color_code(String font_color_code) {
		this.font_color_code = font_color_code;
	}
	
	
	
	
	
}
	