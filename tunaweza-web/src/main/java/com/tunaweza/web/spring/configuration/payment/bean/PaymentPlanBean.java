/**
 * 
 */
package com.tunaweza.web.spring.configuration.payment.bean;

/**
 * @author Joyce Echessa
 *
 */
public class PaymentPlanBean {

	private String planId;
//	private String planName;
//	private String planPrice;
	private String planTime;
	/**
	 * @return the planId
	 */
	public String getPlanId() {
		return planId;
	}
	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	/**
	 * @return the planTime
	 */
	public String getPlanTime() {
		return planTime;
	}
	/**
	 * @param planTime the planTime to set
	 */
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	
}
