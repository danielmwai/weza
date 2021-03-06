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

package com.tunaweza.core.business.Dao.exceptions.promocode;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class PromocodeExistsException extends Exception{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new
	 * <code>PromocodeExistsException<code> exception with null as its detail
	 * message.
	 */
	public PromocodeExistsException(){
		super();
	}
	
	/**
	 * Constructs a new
	 * <code>PromocodeExistsException<code> exception with <code>String</code>
	 * message as its detail message
	 * 
	 * @param <code>String</code> message to display when the exception is
	 *        thrown
	 */
	public PromocodeExistsException(String message){
		super("The promo code specified alread exists. \n"+message);
	}
	
	/**
	 * Constructs a new <code>PromocodeExistsException<code> exception
	 * with the specified cause and a detail message.
	 * 
	 * @param <code>String</code>
	 * @param <code>Throwable</code>
	 */
	public PromocodeExistsException(String message,Throwable cause){
		super(message, cause);
	}
	
	/**
	 * Constructs a new <code>PromocodeExistsException<code> exception.
	 * 
	 * @param <code>Throwable</code> the cause for the exception.
	 */	
	public PromocodeExistsException(Throwable cause){
		super(cause);
	}
}