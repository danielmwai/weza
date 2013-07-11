///*
// * The MIT License
// *
// * Copyright 2013 Daniel Mwai <naistech.gmail.com>.
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in
// * all copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// * THE SOFTWARE.
// */
//
//package com.tunaweza.web.datasource;
//
//import com.tunaweza.core.business.dao.datasource.DataSourceSwitcherApi;
//import com.tunaweza.core.business.dao.datasource.DataSourceSwitcherApiImpl;
//import static com.tunaweza.core.business.utils.SessionHelper.setSessionAttribDbConfig;
//import com.tunaweza.web.user.DbConfigBean;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
///**
// * @version $Revision: 1.1.1.1 $
// * @since Build {3.0.0.SNAPSHOT} (06 2013)
// * @author Daniel mwai
// */
//public class DataSourceImpl implements DataSource{
//
//	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
//	
//	
//	public void jjteachDatasource(HttpServletRequest req){
//	String emfName = "&entityManagerFactory";
//
//	HttpSession httpSession = ((HttpServletRequest) req)
//			.getSession(true);
//	WebApplicationContext wac = WebApplicationContextUtils
//			.getRequiredWebApplicationContext(httpSession
//					.getServletContext());
//	this.entityManagerFactoryBean = wac.getBean(emfName,
//			LocalContainerEntityManagerFactoryBean.class);
//
//	DataSourceSwitcherApi dataSourceSwitcherApi = new DataSourceSwitcherApiImpl();
//	httpSession.invalidate();
//	DbConfigBean dbConfig = new DbConfigBean("localhost","jjteach_", "jjteach_","jjteach_");
//	 dataSourceSwitcherApi.newEntityManagerFactory(
//	  entityManagerFactoryBean,"jdbc:mysql://" + "localhost" +
//	  ":3306/" + "jjteach_", "jjteach_","jjteach_");
//	  //httpSession.setAttribute("DBCONFIG", dbConfig);
//	  setSessionAttribDbConfig(req,dbConfig);
//	}
//
//}
//
