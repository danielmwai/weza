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

package com.tunaweza.core.business.settings;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */

public interface Settings {
	
	public static final String SUPERUSER = "SCA";

	public static String PREFIX = "PREFIX";
	public static String CONTENT_COLOR="CONTENT_COLOR";
	public static String RANDNUM = "RANDNUM";
	public static String ROOT = "root";
	public static String ERRORS = "ERRORS";
	public static String USER_ERRORS = "USER_ERRORS";

	public static String JJETS_PEOPLE = "JJETS_PEOPLE";
	public static String JJETS_PEOPLE_CLIENT = "JJETS_PEOPLE_CLIENT";
	public static String JJETS_MENU = "JJETS_MENU";
	public static String JJETS_PEOPLE_NAME = "JJETS_PEOPLE_NAME";
	public static String JJETS_ROLE = "JJETS_ROLE";
	
	public static String NORMAL_USER = "person";
	public static String CLIENT_USER = "client";

	public static String JJETS_VIEW = "JJETS_VIEW";

	public static String JJETS_SPLASH_VIEW = "JJETS_SPLASH_VIEW";

	public static String WHITEBOARD_LIST = "WHITEBOARD_LIST";
	public static String EMPLOYEE_WHITEBOARD_LIST = "EMPLOYEE_WHITEBOARD_LIST";
	public static String ASSOCIATE_DEALS = "ASSOCIATE_DEALS";
	public static String TIMESHEET_LIST = "TIMESHEET_LIST";
	public static String EMPLOYEE_TIMESHEET_LIST = "EMPLOYEE_TIMESHEET_LIST";
	public static String TIMESHEET_ITEM = "TIMESHEET_ITEM";
	public static String CLIENT_LIST = "CLIENT_LIST";
	public static String INVOICE_LIST = "INVOICE_LIST";

	public static String JJETS_SALESMAN = "salesman";
	public static String JJETS_CLIENT = "client";
	public static String JJETS_MANAGER = "jjmanager";
	
	public static String JJETS_BILL_CLERK = "billingclerk";
	public static String JJETS_B_PAY_CLERK = "bonuspayrollclerk";
	public static String JJETS_COMM_CLERK = "commissionpayrollclerk";
	
	public static Integer JJETS_DEFAULT_INVOICE_NUMBER = 136;

	/**
	 * Currency
	 */
	public static String CURR_USD = "USD";
	public static String CURR_EUR = "EUR";
	public static String CURR_KESH = "KSH";

	/**
	 * The string to look for in the User-Agent header to identify the
	 * GoogleBot.
	 */
	public static final String GOOGLEBOT_AGENT_STRING = "googlebot";

	/**
	 * The request header with the User-Agent information in it.
	 */
	public static final String USER_AGENT_HEADER_NAME = "User-Agent";

	/**
	 * No access page
	 * 
	 */
	public static final String HOME_PAGE = "home.htm";

	public static String JJETS_USER = "JJETS_USER";
	
	public static String ASSOC_DEAL_TYPE = "assoc_deal";
	
	public static String EMPLOYEE_DEAL_TYPE = "employee_deal";
}