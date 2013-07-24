/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.exceptions.licence;

/**
 *
 * @author naistech
 */

public class LicenseDoesNotExistException extends Exception{
	
	/**
	 * Constructs a new
	 * <code>LicenseDoesNotExistException<code> exception with no detail message
	 */
	public LicenseDoesNotExistException(){
		
		super();
	}
	
	/**
	 * Constructs a new
	 * <code>LicenseDoesNotExistException<code> exception with <code>String</code>
	 * message as its detail message
	 * 
	 * @param <code>String</code> message to display when the exception is
	 *        thrown
	 */
	public LicenseDoesNotExistException (String message){
		
		super(message);
		
	}

}