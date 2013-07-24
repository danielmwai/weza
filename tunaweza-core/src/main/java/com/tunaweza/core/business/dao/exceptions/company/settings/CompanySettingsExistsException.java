/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.exceptions.company.settings;

/**
 *
 * @author naistech
 */
public class CompanySettingsExistsException extends Exception {

	
	public CompanySettingsExistsException(){
		super();
	}
	
	public CompanySettingsExistsException(String message){
		super(message);
	}
	
	public CompanySettingsExistsException(String message,Throwable cause){
		super(message,cause);
	}
	
	public CompanySettingsExistsException(Exception ex){
		super(""+ex.getStackTrace());
	}
	
	public CompanySettingsExistsException(Throwable cause){
		super(cause);
	}

}
