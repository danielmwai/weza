/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.exceptions.company.license;

/**
 *
 * @author naistech
 */
public class CompanyLicenseDoesNotExistException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new
	 * <code>LicenseDoesNotExistException<code> exception with null as its detail
	 * message.
	 */
	public CompanyLicenseDoesNotExistException() {
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
	public CompanyLicenseDoesNotExistException(String message) {
		super("The License specified does not exist. \n" + message);
	}

	/**
	 * Constructs a new <code>LicenseDoesNotExistException<code> exception.
	 * 
	 * @param <code>Throwable</code> the cause for the exception.
	 */
	public CompanyLicenseDoesNotExistException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new <code>LicenseDoesNotExistException<code> exception
	 * with the specified cause and a detail message.
	 * 
	 * @param <code>String</code>
	 * @param <code>Throwable</code>
	 */
	public CompanyLicenseDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

}
