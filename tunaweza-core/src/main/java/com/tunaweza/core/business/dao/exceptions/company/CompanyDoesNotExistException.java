/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.exceptions.company;

/**
 *
 * @author naistech
 */
public class CompanyDoesNotExistException  extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new
	 * <code>CompanyDoesNotExistException<code> exception with null as its detail
	 * message.
	 */
	public CompanyDoesNotExistException() {
		super();
	}

	/**
	 * Constructs a new
	 * <code>CompanyDoesNotExistException<code> exception with <code>String</code>
	 * message as its detail message
	 * 
	 * @param <code>String</code> message to display when the exception is
	 *        thrown
	 */
	public CompanyDoesNotExistException(String message) {
		super("The company specified does not exist. \n" + message);
	}

	/**
	 * Constructs a new <code>CompanyDoesNotExistException<code> exception.
	 * 
	 * @param <code>Throwable</code> the cause for the exception.
	 */
	public CompanyDoesNotExistException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new <code>CompanyDoesNotExistException<code> exception
	 * with the specified cause and a detail message.
	 * 
	 * @param <code>String</code>
	 * @param <code>Throwable</code>
	 */
	public CompanyDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

}
