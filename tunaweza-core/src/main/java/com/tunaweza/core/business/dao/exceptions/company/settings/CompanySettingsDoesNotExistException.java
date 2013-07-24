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
public class CompanySettingsDoesNotExistException extends Exception{
	
	public CompanySettingsDoesNotExistException(){
		super();
	}
	
	public CompanySettingsDoesNotExistException(String message){
		super("Company settings matching this company name does not" +
				"exist"+ message);
	}
	
	public CompanySettingsDoesNotExistException(String message,Throwable cause){
		super("Company settings matching"+message+" doesn't exist caused by"+cause);
	}
	
	public CompanySettingsDoesNotExistException(Exception ex){
		super("Company settings matching this company name does not" +
				"exist"+ ex.getStackTrace());
	}
	
	public CompanySettingsDoesNotExistException(Throwable cause){
		super("Company settings matching this company name does not" +
				"exist caused by"+ cause);
	}

}
