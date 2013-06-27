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

package com.tunaweza.core.business.dao.payment;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "ipnDAO")
@Transactional
public class IPNDaoImpl extends GenericDaoImpl<PaymentDetails> implements
		IPNDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.dao.ipn.IPNDao#savePaymentDetails(com.jjpeople.jjteach
	 * .orm.entities.payment.PaymentDetails)
	 */
	@Override
	public void savePaymentDetails(PaymentDetails paymentDetails) {
		saveOrUpdate(paymentDetails);
		flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.dao.ipn.IPNDao#getTransactionId(com.jjpeople.jjteach
	 * .orm.entities.payment.PaymentDetails)
	 */
	@Override
	public String getTransactionId(PaymentDetails paymentDetails) {
		
		
		Session session = (Session) getEntityManager().getDelegate();

		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName() + " i WHERE i.transaction_id = '" + paymentDetails.getTransactionId()
				+ "'");

		PaymentDetails payment = null;

		if (query.list().size() > 0) {
			payment = (PaymentDetails) query.list().get(0);
			return payment.getTransactionId();
		}
		return null;
		
	}

}
