//package com.tunaweza.web.salesforce;
//
///*
// *  ============================================================================
// *  jjteach is web based e-learning software built on top of the jdesk platform.
// *  
// *  Copyright (C) 2009 javaspeak
// *
// *  jjteach is free software: you can redistribute it and/or modify
// *  it under the terms of the GNU Lesser General Public License as published by
// *  the Free Software Foundation, either version 3 of the License, or
// *  (at your option) any later version.
// *
// *  This program is distributed in the hope that it will be useful,
// *  but WITHOUT ANY WARRANTY; without even the implied warranty of
// *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *  GNU Lesser General Public License for more details.
// *
// *  You should have received a copy of the GNU Lesser General Public License
// *  along with jjteach.  If not, see <http://www.gnu.org/licenses/>.
// *  ============================================================================
// *  Author : David Lumbasi
// *  ============================================================================
// */
//
//import javax.xml.rpc.ServiceException;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.log4j.Logger;
//
//import org.springframework.scheduling.quartz.QuartzJobBean;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
///**
// * 
// * @author Dave
// * This class contains the job that gets triggered to create a new JJAssociate.
// * It calls the JJteachSalesForceLogic checkStudentExeTwenty function to create new 
// * JJAssociates.
// */
//@Service
//public class SalesforceCreateAssociateJob {
//	
//	protected final Log logger = LogFactory.getLog(getClass());
//	@Autowired
//	private  JJteachSalesForceLogic jjteachSalesForceLogic;
//	
//	
//	 public JJteachSalesForceLogic getJjteachSalesForceLogic() {
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
//	@Scheduled(cron="0 36 16 * * ?")
//	 public void doSchedule() {
//		
//		try {
//			
//			logger.info(">>>>> Getting into the job.");
//			jjteachSalesForceLogic.checkStudentExeTwenty();
//			logger.info("Updated student module");
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
//
//
//
//	
//}
