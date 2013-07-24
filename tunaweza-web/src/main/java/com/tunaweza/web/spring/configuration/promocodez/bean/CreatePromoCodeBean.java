package com.tunaweza.web.spring.configuration.promocodez.bean;

import javax.validation.constraints.Size;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
*@author AOdera
*/

public class CreatePromoCodeBean{
	
	@NotEmpty
	private String promocodename;
	
	@NotEmpty
	private long numberofextrausers;
	
	@NotNull
	private long associatedlicenseid;
	
	private long promocodeid;
	
	private boolean use;
	
	private String _use;
	
	
	public String get_use() {
		return _use;
	}

	public void set_use(String _use) {
		this._use = _use;
	}

	public boolean isUse() {
		return use;
	}

	public void setUse(boolean use) {
		this.use = use;
	}

	public String getPromocodename() {
		return promocodename;
	}

	public void setPromocodename(String promocodename) {
		this.promocodename = promocodename;
	}

	public long getNumberofextrausers() {
		return numberofextrausers;
	}

	public void setNumberofextrausers(long numberofextrausers) {
		this.numberofextrausers = numberofextrausers;
	}

	public long getAssociatedlicenseid() {
		return associatedlicenseid;
	}

	public void setAssociatedlicenseid(long associatedlicenseid) {
		this.associatedlicenseid = associatedlicenseid;
	}
	
	public long getPromocodeid() {
		return promocodeid;
	}

	public void setPromocodeid(long promocodeid) {
		this.promocodeid = promocodeid;
	}
	
}