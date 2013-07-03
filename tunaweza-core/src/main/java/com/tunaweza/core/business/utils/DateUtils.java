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

package com.tunaweza.core.business.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class DateUtils {
	/**
	 * Computates the weeks from dates
	 * 
	 * @param dateStart
	 * @param dateEnd
	 * @return Long weeks
	 */
	public static Long computeWeeksFromDates(Date dateStart, Date dateEnd) {
		long weeks = 0;
		long dates = 0;
		if (dateEnd != null && dateStart != null) {
			if (dateEnd.getTime() > dateStart.getTime()) {
				dates = ((dateEnd.getTime()) - (dateStart.getTime()));
			}
			if (dates > 0) {
				long days = dates / (1000 * 60 * 60 * 24);
				if (days % 7 >= 5) {
					weeks = (days / 7) + 1;
				} else {
					weeks = (days / 7);
				}
			}
		}
		return weeks;
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Long getWeekNumber(String date) throws ParseException {
		SimpleDateFormat sdf;
		Date dates = new Date();
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
		dates = sdf.parse(date);
		long weeknumber = 0;
		Calendar cal;
		cal = Calendar.getInstance();
		cal.setTime(dates);
		weeknumber = cal.get(Calendar.WEEK_OF_YEAR);
		return weeknumber;
	}

	/**
	 * Get the weeknumber from the date input
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Long getWeekNumber(Date date) {
		long weeknumber = 0;
		Calendar cal;
		cal = Calendar.getInstance();
		cal.setTime(date);
		weeknumber = cal.get(Calendar.WEEK_OF_YEAR);
		return weeknumber;
	}

	/**
	 * Gets the current week number
	 * 
	 * @return
	 */
	public static Long getCurrentWeekNumber() {
		Calendar cal = Calendar.getInstance();
		long weeknumber = cal.get(Calendar.WEEK_OF_YEAR);
		return weeknumber;
	}

	public static Long getCurrentWeekNumber(int weekofyear) {
		Calendar cal = Calendar.getInstance();
		long weeknumber = cal.get(weekofyear);
		return weeknumber;
	}

	public static Long getYearLastDigits() {
		Calendar cal = Calendar.getInstance();
		long year = 0;
		String fullYear = String.valueOf(cal.get(Calendar.YEAR));
		StringBuffer buffer = new StringBuffer();
		buffer.append(fullYear.charAt(2));
		buffer.append(fullYear.charAt(3));
		year = Long.parseLong(buffer.toString());
		return year;
	}

	public static Long getYearLastDigits(Date date) {
		Calendar cal;
		cal = Calendar.getInstance();
		cal.setTime(date);
		long year = 0;
		String fullYear = String.valueOf(cal.get(Calendar.YEAR));
		StringBuffer buffer = new StringBuffer();
		buffer.append(fullYear.charAt(2));
		if(fullYear.charAt(3) < 0)
			buffer.append(fullYear.charAt(3));
		else{
			buffer.append(fullYear.charAt(3));
		}
		year = Long.parseLong(buffer.toString());
		return year;
	}

	public static Long computeWeekNo() {
		long weeknumber = 0;
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getYearLastDigits());
		long wkno = getCurrentWeekNumber();
		System.out.println("Wkno " + wkno);
		if(wkno < 10){
			stringBuffer.append("0"+wkno);
		}else if(wkno >= 10){
			stringBuffer.append(wkno);
		}
		weeknumber = Long.parseLong(stringBuffer.toString());
		return weeknumber;
	}

	public static long computeWeekNo(Date date) {
		long weeknumber = 0;
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getYearLastDigits(date));
		long wkno = getWeekNumber(date);
		if(wkno < 10){
			stringBuffer.append("0"+wkno);
		}else if(wkno >= 10){
			stringBuffer.append(wkno);
		}
		weeknumber = Long.parseLong(stringBuffer.toString());
		return weeknumber;
	}

	/**
	 * Gets mondays date. If the date has passed, gets for the next monday
	 * 
	 * @return
	 */
	public static String getMondayDate() {
		Date mondayDate = null;
		Calendar cal = Calendar.getInstance();
		long dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek != Calendar.MONDAY) {
			// calculate how much to add
			// the 2 is the difference between Saturday and Monday
			int days = (int) ((Calendar.SATURDAY - dayofweek + 2) % 7);
			cal.add(Calendar.DAY_OF_YEAR, days);
		}

		mondayDate = cal.getTime();
		String format = new SimpleDateFormat("dd/MM/yyyy").format(mondayDate);
		return format;
	}

	public static String getWeekDate(int calendar) {
		Date mondayDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, calendar);
		mondayDate = cal.getTime();
		String format = new SimpleDateFormat("dd/MM/yyyy").format(mondayDate);
		return format;
	}

	public static String getSampleWeekDate(Date startDate, Date endDate) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(startDate);
		while (cal.getTime().before(endDate)) {
			System.out.println("Date : " + cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		return null;
	}

	public static String getTodayDate() {
		Date mondayDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		mondayDate = cal.getTime();
		String format = new SimpleDateFormat("yyyy.MM.dd").format(mondayDate);
		return format;
	}

	public static Date getDate() {
		Date todayDate = null;
		Calendar cal = Calendar.getInstance();
		todayDate = cal.getTime();
		return todayDate;
	}

	public static Date stringToDate(String date) throws Exception {
		Date newDate = null;
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
		newDate = sdf.parse(date);
		return newDate;
	}

	public static Date simpleStringToDate(String date) {
		Date newDate = null;
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			newDate = sdf.parse(date);
		} catch (ParseException e) {
			try {
				newDate = stringToDate(date);
			} catch (Exception e1) {
			}
		}
		return newDate;
	}

	public static Date getNextSimpleDate(String date) {
		Calendar calendar = Calendar.getInstance();
		Date newDate = null;
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			newDate = sdf.parse(date);
		} catch (ParseException e) {
			try {
				newDate = stringToDate(date);
			} catch (Exception e1) {
			}
		}
		calendar.setTime(newDate);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static String aStringToDate(Date date) {
		String newDate = null;
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		newDate = sdf.format(date);
		return newDate;
	}

	public static long dateDiff(Date startDate, Date endDate) {
		long datedif = 0;
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar2.setTime(endDate);

		// Get the represented date in milliseconds
		long milis1 = calendar.getTimeInMillis();
		long milis2 = calendar2.getTimeInMillis();

		long diff = milis2 - milis1;
		datedif = diff / (24 * 60 * 60 * 1000);
		return datedif;
	}

	public static Date getWeekDatesFromDate(int calendar, String date) {
		Date mondayDate = null;
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtils.simpleStringToDate(date));
		c.setFirstDayOfWeek(Calendar.MONDAY);	
		//mondayDate = c.getTime();
		c.set(Calendar.DAY_OF_WEEK, calendar);
		mondayDate = c.getTime();
		return mondayDate;
	}
	/**
	 * Sets the time to 00:00:00:00
	 * This is very important especially when using before and after methods 
	 * when the date are the same. 
	 * @param date
	 * @return
	 */
	public static Calendar getCalendarWithStaticTime(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static void main(String[] args) throws Exception {
		//System.out.println(getWeekDatesFromDate(Calendar.MONDAY, DateUtils
				//.aStringToDate(simpleStringToDate("01/11/2010"))));
		//System.out.println(aStringToDate(new Date()));
		
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		
//		System.out.println("Calendar 1: "+c.getTime() +" Milliseconds: "+c.getTimeInMillis());
//		System.out.println("Calendar 2: " +c1.getTime()+" Milliseconds: "+c1.getTimeInMillis());
//		System.out.println("is Calendar 1 before Calendar 2: "+c.getTime().before(c1.getTime()));
//				
		while (c.getTime().before(c1.getTime()) || c.getTime().equals(c1.getTime())) {
			System.out.println("am in");
			break;
		}
	}

}