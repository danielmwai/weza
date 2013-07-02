/*
 * The MIT License
 *
 * Copyright 2013 Daniel Mwai <naistech.gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tunaweza.core.business.model.promotioncodes;

import com.tunaweza.core.business.model.persistence.AbstractPersistentEntity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Entity
@Table(name=Promocode.TABLE_NAME)
public class Promocode extends AbstractPersistentEntity {

	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME="promo_codes";
			
	

	
	@Column(name = "Associated_license")
	private String associatedlicense;
	
	@Column(name="promo_code_name",nullable=false)
	private String promocodename;
	
	@Column(name="in_use")
	private boolean inuse = false;
	
	@Column(name="Extra_users",nullable=false)
	private long extrausers;
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="License_id")
	
	
	@Column(name="dissociate_id",nullable=false)
	private long dissociatedid;
	
	
	
	
	public long getDissociatedid() {
		return dissociatedid;
	}

	public void setDissociatedid(long dissociatedid) {
		this.dissociatedid = dissociatedid;
	}

//	public License getLicense() {
//		return license;
//	}

//	public void setLicense(License license) {
//		this.license = license;
//	}

	public String getPromocodename() {
		return promocodename;
	}

	public long getExtrausers() {
		return extrausers;
	}

	public void setExtrausers(long extrausers) {
		this.extrausers = extrausers;
	}

	public String getAssociatedlicense() {
		return associatedlicense;
	}

	public void setAssociatedlicense(String associatedlicense) {
		this.associatedlicense = associatedlicense;
	}

	public void setPromocodename(String promocodename) {
		this.promocodename = promocodename;
	}

	public boolean isInuse() {
		return inuse;
	}

	public void setInuse(boolean inuse) {
		this.inuse = inuse;
	}

	public void getDissociatedid(long associatedlicenseid) {
		// TODO Auto-generated method stub
		
	}

//	public CompanyLicense getCompanyLicense() {
//		return companyLicense;
//	}
//
//	public void setCompanyLicense(CompanyLicense companyLicense) {
//		this.companyLicense = companyLicense;
//	}
}
	
	