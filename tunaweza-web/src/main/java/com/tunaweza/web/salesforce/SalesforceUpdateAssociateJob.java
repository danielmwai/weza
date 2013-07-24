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
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SalesforceUpdateAssociateJob {
//	
//	public static Logger logger = Logger
//	.getLogger(SalesforceUpdateAssociateJob.class);
//	
//	@Autowired
//	private  JJteachSalesForceLogic jjteachSalesForceLogic;
//
//	@Scheduled(cron="0 36 18 * * ?")
//	 public void doSchedule() {
//		
//		try {
//			
//			logger.info(">>>>> Getting into the update job.");
//			jjteachSalesForceLogic.checkAndUpdateStudentModule();
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//
//	/**
//	 * @return the jjteachSalesForceLogic
//	 */
//	public JJteachSalesForceLogic getJjteachSalesForceLogic() {
//		return jjteachSalesForceLogic;
//	}
//
//
//	/**
//	 * @param jjteachSalesForceLogic the jjteachSalesForceLogic to set
//	 */
//	public void setJjteachSalesForceLogic(
//			JJteachSalesForceLogic jjteachSalesForceLogic) {
//		this.jjteachSalesForceLogic = jjteachSalesForceLogic;
//	}
//   
//	
//}
