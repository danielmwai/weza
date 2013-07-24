//package com.tunaweza.web.salesforce;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import javax.xml.rpc.ServiceException;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.jjpeople.jjteach.dao.UserDAO;
//import com.jjpeople.jjteach.dao.exceptions.ModuleDoesNotExistException;
//import com.jjpeople.jjteach.dao.module.ModuleDAO;
//import com.jjpeople.jjteach.orm.entities.exercise.StudentExercise;
//import com.jjpeople.jjteach.orm.entities.user.User;
//
///**
// * This class is used as the JJTeach, Salesforce Logic. It invokes methods from
// * the JJTeachSalesForceLinker. The JJTeachSalesForceLinker has three methods
// * that can be considered as utility methods i.e.
// * 
// */
//@Service("jjteachSalesForceLogic")
//@Transactional
//public class JJteachSalesForceLogic {
//	public static Logger logger = Logger
//			.getLogger(JJteachSalesForceLogic.class);
//
//	@Autowired
//	private UserDAO userDao;
//	/*
//	 * @Autowired private UserModuleDAO userModuleDAO;
//	 */
//	@Autowired
//	private ModuleDAO moduleDAO;
//	
//	/*@Autowired
//	StudentExercise studentExercise;*/
//	/*
//	 * @Autowired private ExerciseTransactionDAO exerciseTransactionDAO;
//	 */
//	 JJTeachSalesForceLinker jjsforcelinker = new JJTeachSalesForceLinker();
//	private String[] modName = { "javafundamentals", "mysql", "javascript",
//			"j2ee", "Hibernate", "ejb3", "spring" };
//	
//	public void testLogin() throws ServiceException
//	{
//		JJTeachSalesForceLinker jjsforcelinker = new JJTeachSalesForceLinker();
//		
//		logger.info("we have logged in>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + jjsforcelinker.login());
//	}
//
//	/**
//	 * This method iterates through every JJTeach users who are students to find
//	 * out if they have reached the 20th exercise so that a new JJAssociate__c
//	 * object of the specific student is created in Salesforce.
//	 * 
//	 * @throws ServiceException
//	 */
//	
//	public void createStudent(){
//		
//	}
//	public void checkStudentExeTwenty() throws ServiceException {
//		
//		JJTeachSalesForceLinker jjsforcelinker = new JJTeachSalesForceLinker();
//		List<String> emails = null;
//		System.out.print("Before>>>>>>>>>>>>>>>>>>get Alll");
//		List<User> jjTeachUsers = userDao.getAllStudents();
//		System.out.print("After>>>>>>>>>>>>>>>>>>get Alll");
//		logger.info("Size of students>> " + jjTeachUsers.size());
//		User jjTeachUser = null;
//		if (jjTeachUsers.size() > 0) {
//
//			for (int i = 0; i < jjTeachUsers.size(); i++) {
//				jjTeachUser = jjTeachUsers.get(i);
//				if(jjTeachUser.getStudent()== null)
//					continue;
//				List<StudentExercise> studentExercises = jjTeachUser.getStudent().getStudentExercise();
//				if(studentExercises==null)
//					continue;
//				logger.info("Student exercise size is: "+studentExercises.size()+jjTeachUser.getStudent().getStudentExercise());
//				for(StudentExercise studentExercise : studentExercises){
//				logger.info("Student Exrcise size is: >>>> " + studentExercise.getExercise().getName());	
//				logger.info("Particular Student >> " + jjTeachUser.getUsername());
//				/*
//				 * List<Topics> userExercises = jjTeachUser .getUserExercise();
//				 * logger.info("The user exercise >> " +
//				 * jjTeachUser.getUserExercise());
//				 */
//				/*
//				 * for (int j = 0; j < userExercises.size(); j++) { if
//				 * (userExercises.get(j).isComplete() && (userExercises .get(j)
//				 * .getExercise() .getName() .equals(
//				 * "05_01_EXERCISE_objects_methods_variables_classes_interfaces"
//				 * ) || userExercises .get(j).getExercise().getName()
//				 * .equals("10_01_EXERCISE_workflow_engine")))
//				 */
//
//				if ((studentExercise.getExercise().getName().equals("Java (OO) features"))/*&&(studentExercise.isComplete()==true)*/) {
//					logger.info("Checking to find out if it's object:::::::: >> " + studentExercise.getExercise().getName());
//					/*
//					 * logger.info(
//					 * "To check and see if the use exercise is worklow >> " +
//					 * userExercises.get(j).getExercise().getName());
//					 */
//					//
//					// if (userExercises.get(j).){}exercs workfl marked
//					// right get that Date.class
//					if (jjTeachUser.getEnabled() == 1) {
//						if (jjsforcelinker.login()) {
//							//for(User userStudent:jjTeachUsers){
//								
////							String studentEmail = userStudent.getEmail();
////							emails = jjsforcelinker
////									.retrieveSforceStudentEmail();
//							//for(String email:emails){
////								try{
////									studentEmail=userDao
////								}
//							/*
//							 * if (!emails.contains(jjTeachUser.getUser()
//							 * .getUsername())) {
//							 */
//							Calendar firstExeCompleted=Calendar.getInstance();
//							//firstExeCompleted=studentExercise.getClosedDate();
//							//logger.info("Date of Exe completion >> " + firstExeCompleted);
//							Date firstExerciseCompleted=firstExeCompleted.getTime();
//							logger.info("Date of Exe completion >> " + firstExerciseCompleted);
//							Date workflowCompleted = new Date(12-11-2011);
//							 
//							// exerciseTransactionDAO.getFirstJFExerciseCompleted(jjTeachUser.getId(),
//							// 1L);
//							// userExercises.get(j).getExercise().getModule().getId());
//
//							Date estCompletionDate = new Date(12-11-2011);
//
//							/*
//							 * if (userExercises .get(j) .getExercise()
//							 * .getName()
//							 * .equals("10_01_EXERCISE_workflow_engine"))
//							 */
//							if ((studentExercise.getExercise().getName().equals("Java (OO) features"))/*&&(studentExercise.isComplete()==true)*/) {
//							/*	Calendar exeClosedDate = Calendar.getInstance();
//								//Date closedDate=exeClosedDate.getTime();
//								workflowCompleted = exeClosedDate.getTime();*/
//								/*
//								 * exerciseTransactionDAO .getWorkflowCompleted(
//								 * jjTeachUser.getId(), 1L);
//								 */
//
//								/*Calendar cal = Calendar.getInstance();
//								cal.setTime(workflowCompleted);
//								cal.add(Calendar.DAY_OF_YEAR, 180);
//								estCompletionDate = cal.getTime();*/
//							}
//
//							if (firstExerciseCompleted != null) {
//								jjsforcelinker.run(jjTeachUser.getFirstName(),
//										jjTeachUser.getLastName(),
//										firstExerciseCompleted,
//										workflowCompleted, estCompletionDate,
//										jjTeachUser.getUsername(),
//										jjTeachUser.getStudent()
//												.getLastLoggedIn(), jjTeachUser
//												.getLocation()
//												.getLocationName(), jjTeachUser
//												.getLocation()
//												.getLocationCode());
//							}
//							//}
//						}
//						}
//					}
//				}
//			}}
//			
//		}
//	
//
//	/**
//	 * This method finds an existing JJAsociates__c entity using the unique
//	 * email and updates the respective module completion date based on the
//	 * email passed as search criteria.
//	 * 
//	 * @throws ServiceException
//	 */
//
//	public void checkAndUpdateStudentModule() throws ServiceException {
//		JJTeachSalesForceLinker jjsforcelinker = new JJTeachSalesForceLinker();
//
//		List<String> emails = null;
//		String status = null;
//
//		if (jjsforcelinker.login()) {
//			emails = jjsforcelinker.retrieveSforceStudentEmail();
//			for (String email : emails) {
//				User jjTeachUser = null;
//				try {
//					jjTeachUser = userDao.findUser(email);
//				} catch (Exception e) {
//					//logger.error("User NOT FOUND >>>>>>>>>>>>>>>> " + e);
//				}
//				logger.info("jjsforcelinker.retrieveSforceStudentEmail() >> "
//						+ email);
//				if (jjTeachUser != null) {
//
//					if (jjTeachUser.getStudent().getLastLoggedIn() == null) {
//						try {
//							jjsforcelinker.setAssociateComment(email,
//									"User has never logged in to JJTeach");
//						} catch (ServiceException e) {
//							e.printStackTrace();
//						}
//						// } else if(hasCompletedEx20(jjTeachUser)) {
//					} else if (hasCompletedEx20(jjTeachUser)) {
//						/*
//						 * List<Module> allModules = userModuleDAO
//						 * .getModulesByUserId(String.valueOf(jjTeachUser
//						 * .getId()));
//						 * 
//						 * logger.info("jjTeachUser.getUser().getUsername() >>"
//						 * + jjTeachUser.getUser().getUsername());
//						 * logger.info("allModules Size >> " +
//						 * allModules.size()); // Module aModule = null; if
//						 * (allModules.size() > 0) { for (int i = 0; i <
//						 * allModules.size(); i++) { Module module =
//						 * allModules.get(i); logger.info("Module >> " +
//						 * module.getName()); int completed_mods = 0; // getting
//						 * all exercises in a specific module List<Exercise>
//						 * exeInModule = module .getExercises();
//						 * logger.info("Size of exercises in >> " +
//						 * module.getName() + " === " + exeInModule.size());
//						 * 
//						 * for (int j = 0; j < exeInModule.size(); j++) { //
//						 * getting a specific in a module Exercise exercise =
//						 * exeInModule.get(j);
//						 * 
//						 * List<UserExercise> userExe = exercise
//						 * .getUserExercises(); for (int k = 0; k <
//						 * userExe.size(); k++) { UserExercise ux =
//						 * userExe.get(k); logger.info("UserExercise ux >> " +
//						 * ux.getId());
//						 * logger.info("ux.getJJTeachUser() .getId() >> " +
//						 * ux.getJJTeachUser().getId());
//						 * logger.info("jjTeachUser.getId() >> " +
//						 * jjTeachUser.getId());
//						 * logger.info("UserExercise ux status>> " +
//						 * ux.isComplete()); if (ux.getJJTeachUser() .getId()
//						 * .equals(Long .valueOf(jjTeachUser .getId()))) { if
//						 * (ux.isComplete()) { completed_mods++;
//						 * 
//						 * logger.info("Completed user modules>>> " +
//						 * completed_mods); } } } }
//						 */
//
//						/*
//						 * UserExercise ux = null; // There just in case the
//						 * user exercises is more // than // the // number of
//						 * exercises in the module due to a // repost. if
//						 * (completed_mods >= exeInModule.size()) { Date
//						 * moduleCompDte = exerciseTransactionDAO
//						 * .getModuleCompDate( jjTeachUser.getId(),
//						 * allModules.get(i).getId()); try {
//						 * 
//						 * logger.info("jjTeachUser.getUser().getUsername() " +
//						 * jjTeachUser.getUser() .getUsername());
//						 * 
//						 * if (email.equals(jjTeachUser.getUser()
//						 * .getUsername()))
//						 */
//						// {
//						/*
//						 * jjsforcelinker.run2(email, moduleCompDte, allModules
//						 * .get(i).getName(), jjTeachUser .getLastLoggedIn());
//						 * /*} } catch (ServiceException e) {
//						 * e.printStackTrace(); } } } } }
//						 */
//
//						try {
//							jjsforcelinker.run2(
//									email,
//									new Date(4 - 12 - 2011),
//									moduleDAO.findModuleById(
//											jjTeachUser.getStudent()
//													.getCurrentModule())
//											.getName(), jjTeachUser
//											.getStudent().getLastLoggedIn());
//						} catch (ModuleDoesNotExistException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					} else {
//						try {
//							jjsforcelinker.setAssociateComment(email,
//									"User could not be found in JJTeach");
//						} catch (ServiceException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		}
//	}
//
//	// }
//
//	/**
//	 * Function to check whether a user has completed exercise WORKFLOW_ENGINE.
//	 * 
//	 * @param jjTeachUser
//	 * @return
//	 */
//	public boolean hasCompletedEx20(User jjTeachUser) {
//		// List<UserExercise> uxs = jjTeachUser.getUserExercise();
//		boolean hasCompleted = false;
//		/*
//		 * for (UserExercise userExercise : uxs) { if
//		 * (userExercise.getExercise().getName()
//		 * .equals("10_01_EXERCISE_workflow_engine") &&
//		 * userExercise.isComplete()) { hasCompleted = true;
//		 * 
//		 * } }
//		 */
//		return jjTeachUser.getStudent().getCompletedTopics().contains(20);
//	}
//
//	public void checkLastLoggedInAfterFiveDays() {
//		// userManager.mailStudentsNotLoggedLastFiveDays();
//	}
//
//}
