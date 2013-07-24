//package com.tunaweza.web.salesforce;
//
//import javax.xml.rpc.ServiceException;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class PassiveStudentNotifier {
//	
//	public static Logger logger = Logger
//	.getLogger(PassiveStudentNotifier.class);
//	
//	
//	@Autowired
//	private JJteachSalesForceLogic jjteachSalesForceLogic;
//	
//	
//	@Scheduled(cron="0 05 19 * * ?")
//	public void doSchedule()
//	{
//		
//		//try {
//			
//			logger.info(">>>>> Getting into the job.");
//			jjteachSalesForceLogic.checkLastLoggedInAfterFiveDays();
//		/*	try {
//				jjteachSalesForceLogic.testLogin();
//			} catch (ServiceException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		/*} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}*/
//		
//		
//	}
//
//
//	public JJteachSalesForceLogic getJjteachSalesForceLogic() {
//		return jjteachSalesForceLogic;
//	}
//
//
//	public void setJjteachSalesForceLogic(
//			JJteachSalesForceLogic jjteachSalesForceLogic) {
//		this.jjteachSalesForceLogic = jjteachSalesForceLogic;
//	}
//
//
//	
//	
//}
