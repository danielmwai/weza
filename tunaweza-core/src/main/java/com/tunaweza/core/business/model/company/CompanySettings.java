/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.model.company;

import com.tunaweza.core.business.model.company.Company;
import java.sql.Blob;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author naistech
 */

@Entity
@Table(name = CompanySettings.TABLE_NAME)
public class CompanySettings {
    @Id
    @GeneratedValue
    private Long id;

	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "company_settings";

	@Column(name = "bg_color_code")
	private String bg_color_code;
	
	@Column(name = "menu_color_code")
	private String menu_color_code;
	
	@Column(name = "font_color_code")
	private String font_color_code;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "logo_image", length = 16777215)
	private Blob logo_image;

	@Column(name = "header_color_code")
	private String header_color_code;
	
	@Column(name= "header_links_color_code")
	private String header_links_color_code;
	
	@Column(name="navigation_hover_color")
	private String navigation_hover_color;
	
	
	@Column(name = "mentoring", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean mentoring = true;

	/*
	 * @OneToOne(fetch=FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "company_id") private Registration company_id;
	 */

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_company", unique = true)
	private Company company;

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the bg_color_code
	 */
	public String getBg_color_code() {
		return bg_color_code;
	}

	/**
	 * @param bg_color_code
	 *            the bg_color_code to set
	 */
	public void setBg_color_code(String bg_color_code) {
		this.bg_color_code = bg_color_code;
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

	/**
	 * @return the logo_image
	 */
	public Blob getLogo_image() {
		return logo_image;
	}

	/**
	 * @param logo_image
	 *            the logo_image to set
	 */
	public void setLogo_image(Blob logo_image) {
		this.logo_image = logo_image;
	}

	/**
	 * @return the header_color_code
	 */
	public String getHeader_color_code() {
		return header_color_code;
	}

	/**
	 * @param header_color_code
	 *            the header_color_code to set
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
	 * 
	 * @return header_links_color_code
	 */
	public String getHeader_links_color_code() {
		return header_links_color_code;
	}

	/**
	 * sets the header links color code
	 * @param header_links_color_code
	 */
	public void setHeader_links_color_code(String header_links_color_code) {
		this.header_links_color_code = header_links_color_code;
	}
	
	/**
	 * 
	 * @return navigation_hover_color
	 */
	public String getNavigation_hover_color() {
		return navigation_hover_color;
	}
	
	/**
	 * set the navigation hover color
	 * @param navigation_hover_color
	 */
	public void setNavigation_hover_color(String navigation_hover_color) {
		this.navigation_hover_color = navigation_hover_color;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	

}

