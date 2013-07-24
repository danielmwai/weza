package com.tunaweza.web.spring.configuration.company.bean;

import javax.validation.constraints.Size;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 
 * @author Jimmy Ndung'u
 * @author Allan Odera
 * @author David Gitonga
 * 
 */
public class AddCompanyBean {

	private Long id;
	private String productName;
	@NotEmpty
	private String name;
	@Email
	private String email;
	@NotEmpty
	@Size(min = 6, max = 20)
	private String password;
	@NotEmpty
	@Size(min = 6, max = 20)
	private String repassword;
	// @NotEmpty
	private String location;
	// @NotEmpty
	private String otherLocation;
	// @NotEmpty
	private String website;

	// @NotEmpty
	private String firstline;
	// @NotEmpty
	private String secondline;
	// @NotEmpty
	private String address;

	private CommonsMultipartFile logo_image;
	private String bg_color_code;
	private String header_color_code = "1f48c2";
	private String menu_color_code = "2083d9";
	private String font_color_code = "ffd6ff";
	private String header_links_color_code;
	private String navigation_hover_color;
	// private static boolean mentoring;

	// stores the type of url that the company will use
	@NotEmpty
	private String urlType;

	// stores the url that the company will use if urlType selected is private
	private String url;

	private String licenseType;

	public CommonsMultipartFile getLogo_image() {
		return logo_image;
	}

	public void setLogo_image(CommonsMultipartFile logo_image) {
		this.logo_image = logo_image;
	}

	public String getBg_color_code() {
		return bg_color_code;
	}

	public void setBg_color_code(String bg_color_code) {
		this.bg_color_code = bg_color_code;
	}

	public String getHeader_color_code() {
		return header_color_code;
	}

	public void setHeader_color_code(String header_color_code) {
		this.header_color_code = header_color_code;
	}

	public String getMenu_color_code() {
		return menu_color_code;
	}

	public void setMenu_color_code(String menu_color_code) {
		this.menu_color_code = menu_color_code;
	}

	public String getFont_color_code() {
		return font_color_code;
	}

	public void setFont_color_code(String font_color_code) {
		this.font_color_code = font_color_code;
	}

	public String getHeader_links_color_code() {
		return header_links_color_code;
	}

	public void setHeader_links_color_code(String header_links_color_code) {
		this.header_links_color_code = header_links_color_code;
	}

	public String getNavigation_hover_color() {
		return navigation_hover_color;
	}

	public void setNavigation_hover_color(String navigation_hover_color) {
		this.navigation_hover_color = navigation_hover_color;
	}

	/*
	 * public boolean getMentoring() { return mentoring; }
	 * 
	 * public void setMentoring(boolean mentoring) { this.mentoring = mentoring;
	 * }
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstline() {
		return firstline;
	}

	public void setFirstline(String firstline) {
		this.firstline = firstline;
	}

	public String getSecondline() {
		return secondline;
	}

	public void setSecondline(String secondline) {
		this.secondline = secondline;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the otherLocation
	 */
	public String getOtherLocation() {
		return otherLocation;
	}

	/**
	 * @param otherLocation
	 *            the otherLocation to set
	 */
	public void setOtherLocation(String otherLocation) {
		this.otherLocation = otherLocation;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

}
