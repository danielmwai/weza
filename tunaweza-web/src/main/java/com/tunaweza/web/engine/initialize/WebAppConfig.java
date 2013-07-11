///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tunaweza.web.engine.initialize;
//import java.util.Properties;
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.hibernate4.HibernateTransactionManager;
//import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.view.JstlView;
//import org.springframework.web.servlet.view.UrlBasedViewResolver;
//
///***
// * 
// * @author naistech
// */
//@Configuration
//@ComponentScan("com.tunaweza.*")
//@EnableWebMvc
//@EnableTransactionManagement
//@PropertySource("classpath:application.properties")
//public class WebAppConfig {
//	
//    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
//    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
//    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
//    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
//	
//    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
//    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
//    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.tunaweza.core.business.model";
//    
//	@Resource
//	private Environment env;
//	
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		
//		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
//		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
//		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
//		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
//		
//		return dataSource;
//	}
//	
//	@Bean
//	public LocalSessionFactoryBean localsessionFactoryBean() {
//		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//		sessionFactoryBean.setDataSource(dataSource());
//		sessionFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
//		sessionFactoryBean.setHibernateProperties(hibProperties());
//		return sessionFactoryBean;
//	}
//	
//	private Properties hibProperties() {
//		Properties properties = new Properties();
//		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
//		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
//		return properties;	
//	}
//	
//	@Bean
//	public HibernateTransactionManager transactionManager() {
//		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//		transactionManager.setSessionFactory(localsessionFactoryBean().getObject());
//		return transactionManager;
//	}
//	
//	@Bean
//	public UrlBasedViewResolver setupViewResolver() {
//		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
//		resolver.setPrefix("/WEB-INF/pages/");
//		resolver.setSuffix(".xhtml");
//		resolver.setViewClass(JstlView.class);
//		return resolver;
//	}
//
//}
