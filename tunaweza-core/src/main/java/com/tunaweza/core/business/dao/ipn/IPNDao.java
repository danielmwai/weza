/*
 * The MIT License
 *
 * Copyright 2013 naistech.
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

package com.tunaweza.core.business.dao.ipn;

import com.tunaweza.core.business.dao.generic.GenericDao;
import com.tunaweza.core.business.model.payment.PaymentDetails;

/**
 *
 * @author Daniel Mwai
 */
public interface IPNDao  extends GenericDao <PaymentDetails> {
	
	/**
	 * 
	 * @param paymentDetails
	 * @return
	 */
	public void savePaymentDetails(PaymentDetails paymentDetails);
	
	/**
	 * 
	 * @param paymentDetails
	 * @return
	 */
	public String getTransactionId(PaymentDetails paymentDetails);

}
