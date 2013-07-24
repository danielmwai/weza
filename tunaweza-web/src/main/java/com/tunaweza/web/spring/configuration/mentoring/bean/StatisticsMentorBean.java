package com.tunaweza.web.spring.configuration.mentoring.bean;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Jose Marcucci
 *
 */
public class StatisticsMentorBean {
	
	String month;
	String totalTransactions;
	String averageTimeToResponse;
	String averageTimeToRead;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTotalTransactions() {
		return totalTransactions;
	}
	public void setTotalTransactions(String totalTransactions) {
		this.totalTransactions = totalTransactions;
	}
	public String getAverageTimeToResponse() {
		return averageTimeToResponse;
	}
	public void setAverageTimeToResponse(String averageTimeToResponse) {
		this.averageTimeToResponse = averageTimeToResponse;
	}
	public String getAverageTimeToRead() {
		return averageTimeToRead;
	}
	public void setAverageTimeToRead(String averageTimeToRead) {
		this.averageTimeToRead = averageTimeToRead;
	}
	
	
}
