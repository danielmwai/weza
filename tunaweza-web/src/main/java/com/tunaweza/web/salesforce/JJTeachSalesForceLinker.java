//package com.tunaweza.web.salesforce;
//
//The sample client application begins by importing the necessary packages and objects.
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Locale;
//import java.util.MissingResourceException;
//import java.util.ResourceBundle;
//import java.rmi.RemoteException;
//import java.util.Date;
//import org.apache.log4j.Logger;
//
//
//import com.jjpeople.jjteach.orm.entities.user.User;
//
//import javax.xml.rpc.ServiceException;
//
//import com.sforce.soap.enterprise.*;
//import com.sforce.soap.enterprise.Error;
//import com.sforce.soap.enterprise.fault.ApiFault;
//import com.sforce.soap.enterprise.fault.ExceptionCode;
//import com.sforce.soap.enterprise.fault.LoginFault;
//import com.sforce.soap.enterprise.sobject.Account;
//import com.sforce.soap.enterprise.sobject.Contact;
//import com.sforce.soap.enterprise.sobject.JJAssociates__c;
//import com.sforce.soap.enterprise.sobject.SObject;
//
//public class JJTeachSalesForceLinker {
//	private SoapBindingStub binding;
//	User jjteachuser = new User();
//	public static Logger logger = Logger
//			.getLogger(JJTeachSalesForceLinker.class);
//
//	public JJTeachSalesForceLinker() {
//	}
//
//	/**
//	 * The login call is used to obtain a token from Salesforce. This token must
//	 * be passed to all other calls to provide authentication and is valid for 2
//	 * hours.
//	 */
//
//	public boolean login() throws ServiceException {
//		String userName ="a.kubai@jjpeople.com.sandbox1";
//		String password = "mine18ooioEfDFeYw0QlLWqyMmwSXagr";
//		
//		try {
//			Locale engLocale = new Locale("en", "EN");
//			//ResourceBundle rb = ResourceBundle.getBundle("salesforce",
//					//engLocale);
//
//			//userName = rb.getString("salesforce.username");
//            logger.info("This is the User Name ::::::::" + userName);
//            
//			//password = rb.getString("salesforce.password");
//
//		} catch (MissingResourceException mre) {
//			mre.printStackTrace();
//		}
//		// String userName = rb.getString("");//
//		// getUserInput("Enter username: ");
//		// String password = rb.getString("");//
//		// getUserInput("Enter password: ");
//		/**
//		 * Next, the client application initializes the binding stub. This is
//		 * our main interface to the API through which all calls are made. The
//		 * getSoap method takes an optional parameter, (a java.net.URL) which is
//		 * the endpoint. For the login call, the parameter always starts with
//		 * http(s)://www.salesforce.com. After logging in, the client
//		 * application changes the endpoint to the one specified in the returned
//		 * loginResult object.
//		 */
//		binding = (SoapBindingStub) new SforceServiceLocator().getSoap();
//
//		// Time out after an hour
//		binding.setTimeout(3600000);
//		// Test operation
//		LoginResult loginResult;
//		try {
//			System.out.println("LOGGING IN NOW....");
//			loginResult = binding.login(userName, password);
//			logger.info("Successfully Logged in>>>>>>" + loginResult);
//		} catch (LoginFault ex) {
//			// The LoginFault derives from AxisFault
//			ExceptionCode exCode = ex.getExceptionCode();
//			if (exCode == ExceptionCode.FUNCTIONALITY_NOT_ENABLED
//					|| exCode == ExceptionCode.INVALID_CLIENT
//					|| exCode == ExceptionCode.INVALID_LOGIN
//					|| exCode == ExceptionCode.LOGIN_DURING_RESTRICTED_DOMAIN
//					|| exCode == ExceptionCode.LOGIN_DURING_RESTRICTED_TIME
//					|| exCode == ExceptionCode.ORG_LOCKED
//					|| exCode == ExceptionCode.PASSWORD_LOCKOUT
//					|| exCode == ExceptionCode.SERVER_UNAVAILABLE
//					|| exCode == ExceptionCode.TRIAL_EXPIRED
//					|| exCode == ExceptionCode.UNSUPPORTED_CLIENT) {
//				System.out
//						.println("Please be sure that you have a valid username and password.");
//			} else {
//				// Write the fault code to the console
//				System.out.println(ex.getExceptionCode());
//				// Write the fault message to the console
//				System.out.println("An unexpected error has occurred 1.");
//				// ex.printStackTrace();
//			}
//			return false;
//		} catch (Exception ex) {
//			System.out.println("An unexpected error has occurred 2 ");
//			ex.printStackTrace();
//			return false;
//		}
//		// Check if the password has expired
//		if (loginResult.isPasswordExpired()) {
//			System.out
//					.println("An error has occurred. Your password has expired.");
//			return false;
//		}
//		/**
//		 * Once the client application has logged in successfully, it will use
//		 * the results of the login call to reset the endpoint of the service to
//		 * the virtual server instance that is servicing your organization. To
//		 * do this, the client application sets the ENDPOINT_ADDRESS_PROPERTY of
//		 * the binding object using the URL returned from the LoginResult.
//		 */
//		binding._setProperty(SoapBindingStub.ENDPOINT_ADDRESS_PROPERTY,
//				loginResult.getServerUrl());
//		/**
//		 * The application now has an instance of the SoapBindingStub that is
//		 * pointing to the correct endpoint. Next, the application sets a
//		 * persistent SOAP header (to be included on all subsequent calls that
//		 * are made with the SoapBindingStub) that contains the valid sessionId
//		 * for our login credentials. To do this, the application creates a new
//		 * SessionHeader object and set its sessionId property to the sessionId
//		 * property from the LoginResult object.
//		 */
//		// Create a new session header object and add the session id
//		// from the login return object
//		SessionHeader sh = new SessionHeader();
//		sh.setSessionId(loginResult.getSessionId());
//		/**
//		 * Next, the application calls the setHeader method of the
//		 * SoapBindingStub to add the header to all subsequent method calls.
//		 * This header will persist until the SoapBindingStub is destroyed until
//		 * the header is explicitly removed. The "SessionHeader" parameter is
//		 * the name of the header to be added.
//		 */
//		// set the session header for subsequent call authentication
//		binding.setHeader(new SforceServiceLocator().getServiceName()
//				.getNamespaceURI(), "SessionHeader", sh);
//		// return true to indicate that we are logged in, pointed
//		// at the right url and have our security token in place.
//		return true;
//	}
//
//	/**
//	 * used to create a new JJAssociate in Salesforce if and only if the
//	 * associate has reached the twentieth
//	 * exercise("10_01_EXERCISE_workflow_engine") using the parameters passed in
//	 * the method.
//	 * 
//	 * @param firstName
//	 *            - the first name of the JJAssociates__c object created in
//	 *            Salesforce
//	 * @param lastName
//	 *            - the last name of the JJAssociates__c object created in
//	 *            Salesforce
//	 * @param accntCreated
//	 *            - the date the JJAssociates__c object was created in JJTeach
//	 *            after the
//	 * @param estFinishDate
//	 * @param email
//	 * @param lastLoggedIn
//	 */
//
//	private void createNewJJAssociate(String firstName, String lastName,
//			Date accntCreated, Date doneExeTwenty, Date estFinishDate, String email,
//			Date lastLoggedIn, String country, String locationCode) {
//		QueryOptions qo = new QueryOptions();
//		qo.setBatchSize(200);
//		binding.setHeader(new SforceServiceLocator().getServiceName()
//				.getNamespaceURI(), "QueryOptions", qo);
//		// System.out.println("Update the 1 new test JJAssociates custom object...");
//		JJAssociates__c jjassoc = null;
//		JJAssociates__c[] jjpeople = new JJAssociates__c[1];
//		boolean exists = false;
//		
//		try {
//			QueryResult qr = binding
//			.query("SELECT id, Email__c FROM JJAssociates__c"
//					+ " WHERE Email__c = '" + email + "'");
//			if (qr.getSize() > 0) {
//				// String email = null;
//				for (int i = 0; i < qr.getRecords().length; i++) {
//					jjassoc = (JJAssociates__c) qr.getRecords(i);
//					exists = true;
//				}
//			}else{
//				jjassoc = new JJAssociates__c();
//				exists = false;
//			}
//		} catch (ApiFault ex) {
//			ex.printStackTrace();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			jjassoc.setName(firstName + " " + lastName);
//			jjassoc.setFirstName__c(firstName);
//			jjassoc.setLastName__c(lastName);
//			jjassoc.setAccount_Created_Dte__c(accntCreated);
//			jjassoc.setDone_Ex20__c(doneExeTwenty);
//			jjassoc.setEst_Fnsh_Dte__c(estFinishDate);
//			jjassoc.setEmail__c(email);
//			jjassoc.setLast_Logged_In__c(lastLoggedIn);
//			jjassoc.setCountry__c(country);
//			jjassoc.setLocation__c(locationCode);
//			// Log.INFO("Calendar before setting time > " + c1.getTime());
//			// c1.setTime(jjteachuser.getStartDate());
//			// Log.INFO("Calendar after setting time > " + c1.getTime());
//			// c1.add(Calendar.DAY_OF_YEAR, 180);
//			// Log.INFO("Calendar after adding 180 days > " + c1.getTime());
//			// jjassoc.setEst_Fnsh_Dte__c(c1.getTime());
//			// jj.setAccount_Created_Dte__c(new Date());
//			System.out.println("...Object udpated1...");
//			// jj.setEst_Fnsh_Dte__c(new Date());
//			System.out.println("...Object udpated2...");
//			// jjpeople[0] = jj;
//			System.out.println("...Object udpated3...");
//			jjpeople[0] = jjassoc;
//			/*
//			 * } }else{ System.out.println("Query.size is 0"); }
//			 */
//
//			// update the records in Salesforce.com
//			// System.out.println("jjpeople.length is " + jjassoc.length);
//			SaveResult[] saveResults = null;
//			if(!exists){
//				saveResults = binding.create(jjpeople);
//			}else{
//				saveResults = binding.update(jjpeople);
//			}
//			System.out.println("saveResults.length is " + saveResults.length);
//
//			// check the returned results for any errors
//			if (saveResults.length > 0) {
//				for (int i = 0; i < saveResults.length; i++) {
//					if (saveResults[i].isSuccess()) {
//						System.out.println(i
//								+ ". Successfully updated record - Id: "
//								+ saveResults[i].getId());
//					} else {
//						Error[] errors = saveResults[i].getErrors();
//						for (int j = 0; j < errors.length; j++) {
//							System.out.println("ERROR updating record: "
//									+ errors[j].getMessage());
//						}
//					}
//				}
//			} else {
//				System.out.println("saveResults.length is 0");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * This method is used to retrieve the emails of all JJAssociates from
//	 * Salesforce and returns a List of the mails which are used for querying
//	 * JJTeach users who have the roles of Student with marching emails as
//	 * returned from retrieveSforceStudentEmail() method, in the
//	 * updateExistingJJAssociate(String email, Date moduleCompDte, String
//	 * moduleName,Date lastLoggedIn)
//	 * 
//	 * @return
//	 */
//
//	public List<String> retrieveSforceStudentEmail() {
//		String email = null;
//		List<String> emails = new ArrayList<String>();
//
//		QueryOptions qo = new QueryOptions();
//		qo.setBatchSize(200);
//		binding.setHeader(new SforceServiceLocator().getServiceName()
//				.getNamespaceURI(), "QueryOptions", qo);
//
//		try {
//			QueryResult qr = binding
//					.query("select Email__c from JJAssociates__c");
//			if (qr.getSize() > 0) {
//				// String email = null;
//				for (int i = 0; i < qr.getRecords().length; i++) {
//					JJAssociates__c jj = (JJAssociates__c) qr.getRecords(i);
//					email = jj.getEmail__c();
//					emails.add(email);
//				}
//			}
//		} catch (ApiFault ex) {
//			ex.printStackTrace();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//
//		return emails;
//	}
//
//	/**
//	 * This method updates the specific JJAssociates__c object module completion
//	 * date based on the email which is used as the Search criteria.
//	 * 
//	 * @param email
//	 *            - the email address of a specific JJAssociate
//	 * @param moduleCompDte
//	 *            - this is the specific module completion date of a specific
//	 *            student
//	 * @param moduleName
//	 *            - the name of the module that a JJAssociate has completed.
//	 * @param lastLoggedIn
//	 *            - The last time a JJAssociate logged into JJTeach
//	 */
//
//	private void updateExistingJJAssociate(String email, Date moduleCompDte,
//			String moduleName, Date lastLoggedIn) {
//		String[] modName = { "javafundamentals", "mysql","XML","CSS", "javascript", "j2ee","Tiles",
//				"Hibernate","webservices" ,"ejb3","spring" };
//		QueryOptions qo = new QueryOptions();
//		qo.setBatchSize(200);
//		binding.setHeader(new SforceServiceLocator().getServiceName()
//				.getNamespaceURI(), "QueryOptions", qo);
//
//		try {
//			// JJAssociates_c jjassoc = new JJAssociates_c();
//			JJAssociates__c jjassoc = null;
//			SaveResult[] upsertResults = null;
//			QueryResult queryResults = binding
//					.query("SELECT id, Email__c FROM JJAssociates__c"
//							+ " WHERE Email__c = '" + email + "'");
//			JJAssociates__c jjassocs[] = new JJAssociates__c[1];
//			// QueryResult qr = binding.query("SELECT * FROM JJAssociates__c ");
//
//			if (queryResults.getSize() > 0) {
//				for (int i = 0; i < queryResults.getRecords().length; i++) {
//					jjassoc = (JJAssociates__c) queryResults.getRecords(i);
//					if (moduleName.equalsIgnoreCase(modName[0])) {
//						jjassoc.setJF_Completed_Dte_del__c(moduleCompDte);
//					} else if (moduleName.equalsIgnoreCase(modName[1])) {
//						jjassoc.setMySQL_Completed_Dte_del__c(moduleCompDte);
//					} else if (moduleName.equalsIgnoreCase(modName[2])) {
//						jjassoc.setXML_Complete_Dte__c(moduleCompDte);
//					} else if (moduleName.equalsIgnoreCase(modName[3])) {
//						jjassoc.setCSS_Complete_Dte__c(moduleCompDte);
//					} else if (moduleName.equalsIgnoreCase(modName[4])) {
//						jjassoc.setJavaScript_Complete_Dte_del__c(moduleCompDte);
//					} else if (moduleName.equalsIgnoreCase(modName[5])) {
//						jjassoc.setJ2EE_Complete_Dte_del__c(moduleCompDte);
//					} else if (moduleName.equalsIgnoreCase(modName[6])) {
//						jjassoc.setTiles_Complete_Dte__c(moduleCompDte);
//					} else if (moduleName.equalsIgnoreCase(modName[7])) {
//						jjassoc.setHibernate_Complete_Dte__c(moduleCompDte);
//					} else if (moduleName.equalsIgnoreCase(modName[8])) {
//						jjassoc.setEJB_Complete_Dte_del__c(moduleCompDte);
//					} else if (moduleName.equalsIgnoreCase(modName[9])) {
//						jjassoc.setWebservices_Complete_Dte__c(moduleCompDte);
//					} else if (moduleName.equalsIgnoreCase(modName[10])) {
//						jjassoc.setSpring_Complete_Dte__c(moduleCompDte);
//					} else {
//						System.out.println("Module Not Specified!");
//					}
//					jjassoc.setLast_Logged_In__c(lastLoggedIn);
//					jjassocs[i] = jjassoc;
//
//					upsertResults = binding.update(jjassocs);// ("email__c",
//																// jjassoc);
//				}
//			}
//			// try {
//			// Invoke the upsert call and save the results.
//			// Use External_Id custom field for matching records
//			// UpsertResult[] upsertResults = binding.upsert("email__c",
//			// jjassoc);
//			for (SaveResult result : upsertResults) {
//				if (result.isSuccess()) {
//					System.out.println("\nUpsert succeeded.");
//					/*
//					 * System.out.println((((Object) result).isCreated() ?
//					 * "Insert" : "Update") + " was performed.");
//					 */
//				} else {
//					System.out.println("The Upsert failed because: "
//							+ result.getErrors(0).getMessage());
//				}
//			}
//			// }
//		} catch (RemoteException ex) {
//			System.out.println("An unexpected error has occurred 3.");
//			ex.printStackTrace();
//		}
//
//	}
//
//	private void setJJAssociateComment(String email, String message) {
//
//		QueryOptions qo = new QueryOptions();
//		qo.setBatchSize(200);
//		binding.setHeader(new SforceServiceLocator().getServiceName()
//				.getNamespaceURI(), "QueryOptions", qo);
//
//		try {
//			// JJAssociates_c jjassoc = new JJAssociates_c();
//			JJAssociates__c jjassoc = null;
//			SaveResult[] upsertResults = null;
//			QueryResult queryResults = binding
//					.query("SELECT id, Email__c FROM JJAssociates__c"
//							+ " WHERE Email__c = '" + email + "'");
//			JJAssociates__c jjassocs[] = new JJAssociates__c[1];
//			// QueryResult qr = binding.query("SELECT * FROM JJAssociates__c ");
//
//			if (queryResults.getSize() > 0) {
//				for (int i = 0; i < queryResults.getRecords().length; i++) {
//					jjassoc = (JJAssociates__c) queryResults.getRecords(i);
//
//					if(jjassoc.getComment__c() == null || jjassoc.getComment__c().equals("")){
//						jjassoc.setComment__c(message);
//					}else{
//						jjassoc.setComment__c(jjassoc.getComment__c() + ". "
//								+ message);
//					}
//					
//					jjassocs[i] = jjassoc;
//
//					upsertResults = binding.update(jjassocs);// ("email__c",
//																// jjassoc);
//				}
//			}
//			// try {
//			// Invoke the upsert call and save the results.
//			// Use External_Id custom field for matching records
//			// UpsertResult[] upsertResults = binding.upsert("email__c",
//			// jjassoc);
//			for (SaveResult result : upsertResults) {
//				if (result.isSuccess()) {
//					System.out.println("\nUpsert succeeded.");
//					/*
//					 * System.out.println((((Object) result).isCreated() ?
//					 * "Insert" : "Update") + " was performed.");
//					 */
//				} else {
//					System.out.println("The Upsert failed because: "
//							+ result.getErrors(0).getMessage());
//				}
//			}
//			// }
//		} catch (RemoteException ex) {
//			System.out.println("An unexpected error has occurred 3.");
//			ex.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * This method is used for invoking the createNewJJAssociate method when the
//	 * application is run so that a JJAssociates__c object can be created. This
//	 * method is called from the JJTeachSalesForceLogic
//	 * 
//	 * @param firstName
//	 *            - the first name of a newly created JJAssociates__c object
//	 * @param lastName
//	 *            - the last name of a newly created JJAssociates__c object
//	 * @param accntCreated
//	 *            - the date when the JJAssociates__c account was created i.e.
//	 *            Workflow Engine was marked right
//	 * @param estFinishDate
//	 *            - this is the accntCreated date + 180 days
//	 * @param email
//	 *            - the email address of a specific JJAssociate
//	 * @param lastLoggedIn
//	 *            - this is the date the student last logged in.
//	 * @throws ServiceException
//	 */
//
//	public void run(String firstName, String lastName, Date accntCreated, Date doneExeTwenty,
//			Date estFinishDate, String email, Date lastLoggedIn, String country, String locationCode)
//			throws ServiceException {
//		// if (login()) {
//		// getUserInput("SUCESSFUL LOGIN! Hit the enter key to continue.");
//		// describeGlobalSample();
//		// describeSample();
//		// createAccounts();
//		createNewJJAssociate(firstName, lastName, accntCreated, doneExeTwenty, estFinishDate,
//				email, lastLoggedIn, country, locationCode);
//		// }
//	}
//
//	/**
//	 * This method is used for invoking the updateExistingJJAssociate method
//	 * when the application is run so that a JJAssociates__c object which is
//	 * existing in Salesforce can be updated. This method is called from the
//	 * JJTeachSalesForceLogic
//	 * 
//	 * @param email
//	 *            - the email field of a JJAssociates__c object
//	 * @param moduleCompDte
//	 *            - this is the specific module completion date of a specific
//	 *            student
//	 * @param moduleName
//	 *            - the name of the module that a JJAssociate has completed.
//	 * @param lastLoggedIn
//	 *            - The last time a JJAssociate logged into JJTeach
//	 * @throws ServiceException
//	 */
//	public void run2(String email, Date moduleCompDte, String moduleName,
//			Date lastLoggedIn) throws ServiceException {
//		// if(login()){
//		updateExistingJJAssociate(email, moduleCompDte, moduleName,
//				lastLoggedIn);
//		// }
//	}
//
//	public void setAssociateComment(String email, String message)
//			throws ServiceException {
//		setJJAssociateComment(email, message);
//	}
//}
