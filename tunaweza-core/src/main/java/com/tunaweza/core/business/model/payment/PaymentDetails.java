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

package com.tunaweza.core.business.model.payment;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Entity
@Table(name = PaymentDetails.TABLE_NAME)
public class PaymentDetails extends AbstractPersistentEntity {

	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "payment_details";
	
	@Column(name = "transaction_id", unique = true)
	private String transactionId;
	
	@Column(name = "payer_first_name", nullable = true)
	private String payerFirstName;
	
	@Column(name = "payer_last_name", nullable = true)
	private String payerLastName;
	
	@Column(name = "payer_business_name", nullable = true)
	private String payerBusinessName;
	
	@Column(name = "payer_email")
	private String payerEmail;
	
	@Column(name = "payer_id")
	private String payerId;
	
	@Column(name = "amount")
	private String amount;
	
	@Column(name = "license_name")
	private String licenseName;
	
	@Column(name = "license_id")
	private String licenseId;
	
	@Column(name = "payment_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date paymentDate;

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the payerFirstName
	 */
	public String getPayerFirstName() {
		return payerFirstName;
	}

	/**
	 * @param payerFirstName the payerFirstName to set
	 */
	public void setPayerFirstName(String payerFirstName) {
		this.payerFirstName = payerFirstName;
	}

	/**
	 * @return the payerLastName
	 */
	public String getPayerLastName() {
		return payerLastName;
	}

	/**
	 * @param payerLastName the payerLastName to set
	 */
	public void setPayerLastName(String payerLastName) {
		this.payerLastName = payerLastName;
	}

	/**
	 * @return the payerBusinessName
	 */
	public String getPayerBusinessName() {
		return payerBusinessName;
	}

	/**
	 * @param payerBusinessName the payerBusinessName to set
	 */
	public void setPayerBusinessName(String payerBusinessName) {
		this.payerBusinessName = payerBusinessName;
	}

	/**
	 * @return the payerEmail
	 */
	public String getPayerEmail() {
		return payerEmail;
	}

	/**
	 * @param payerEmail the payerEmail to set
	 */
	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	/**
	 * @return the payerId
	 */
	public String getPayerId() {
		return payerId;
	}

	/**
	 * @param payerId the payerId to set
	 */
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the licenseName
	 */
	public String getLicenseName() {
		return licenseName;
	}

	/**
	 * @param licenseName the licenseName to set
	 */
	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	/**
	 * @return the licenseId
	 */
	public String getLicenseId() {
		return licenseId;
	}

	/**
	 * @param licenseId the licenseId to set
	 */
	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}


	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	
}
