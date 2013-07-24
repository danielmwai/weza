/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.model.licence;

import com.tunaweza.core.business.model.company.Company;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author naistech
 */
@Entity
@Table(name = CompanyLicense.TABLE_NAME)
public class CompanyLicense   implements Comparable<CompanyLicense>
{
     @Id
     @GeneratedValue
     public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "company_license";


	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(name = "registration_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date registrationDate;

	@Column(name = "expiry_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date expiryDate;
	
	
	@Column(name = "license_id",nullable=false)
	private long license_id;
	
	


	public long getLicense_id() {
		return license_id;
	}



	public void setLicense_id(long license_id) {
		this.license_id = license_id;
	}



	/**
	 * 
	 * @return
	 */
	
	
	public Company getCompany() {
		return company;
	}



	/**
	 * 
	 * @param company
	 */



	public void setCompany(Company company) {
		this.company = company;
	}




	/**
	 * 
	 * @return
	 */


	public Date getRegistrationDate() {
		return registrationDate;
	}



	/**
	 * 
	 * @param registrationDate
	 */



	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}


	/**
	 * 
	 * @return
	 */




	public Date getExpiryDate() {
		return expiryDate;
	}



	/**
	 * 
	 * @param expiryDate
	 */



	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */




	public int compareTo(CompanyLicense license)
	{
		if (license.getId() > getId())
		{
			return -1;

		}
		else if (license.getId() < getId())
		{
			return 1;
		}
		return 0;
	}





}
