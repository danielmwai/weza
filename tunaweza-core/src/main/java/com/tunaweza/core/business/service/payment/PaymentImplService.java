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

package com.tunaweza.core.business.service.payment;

import com.tunaweza.core.business.dao.payment.PaymentDao;
import com.tunaweza.core.business.model.payment.PaymentDetails;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.mail.internet.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

@Service("ipnService")
@Transactional
public class PaymentImplService  implements PaymentService {
	
	// This will change once the site goes live
	private final static String BUSINESS_EMAIL = "joyce.echessa@jjpeople.com";
	
	@Autowired
	PaymentDao PaymentDao;
	
//	@Autowired
//	LicenseDao licenseDao;
	
	PaymentDetails paymentDetails;
	
	public String processPaymentNotification(Map<String, String> params) {
		
		
		// check that paymentStatus=Completed
		// check that txnId has not been previously processed
		// check that receiverEmail is your Primary PayPal email
		// check that paymentAmount/paymentCurrency, license id, license name are correct
		// process payment
		
		if(params.get("payment_status") == "COMPLETED") {
			
			DateFormat formatter = new SimpleDateFormat();
			
			paymentDetails = new PaymentDetails();
			paymentDetails.setTransactionId(params.get("txn_id"));
			paymentDetails.setAmount(params.get("auth_amount"));
			paymentDetails.setLicenseId(params.get("item_numberx"));
			paymentDetails.setLicenseName(params.get("item_namex"));
			paymentDetails.setPayerBusinessName(params.get("payer_business_name"));
			paymentDetails.setPayerEmail(params.get("payer_email"));
			paymentDetails.setPayerFirstName(params.get("first_name"));
			paymentDetails.setPayerLastName(params.get("last_name"));
			paymentDetails.setPayerId(params.get("payer_id"));
//			try {
//				paymentDetails.setPaymentDate(formatter.parse(params.get("payment_date")));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			String txnId = PaymentDao.getTransactionId(paymentDetails);
			
			// If txnId has a value, it means it has previously been saved thus the
			// transaction with that id has been processed before
			if(txnId == null) {
				PaymentDao.savePaymentDetails(paymentDetails);
			}
			
////			License license = new License();
//			try {
//				license = licenseDao.findLicenseById(Long.parseLong(paymentDetails.getLicenseId()));
//			} catch (NumberFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (LicenseDoesNotExistException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			if (license.getCost().toString() == paymentDetails.getAmount() && params.get("business") == BUSINESS_EMAIL) {
//				return "SUCCESS";
//			}
//			else {
//				return "FAILED";
//			}
		//}
		//else {
			return "NOT_COMPLETED";
		}
            return "NOT_COMPLETED";

	}

}
