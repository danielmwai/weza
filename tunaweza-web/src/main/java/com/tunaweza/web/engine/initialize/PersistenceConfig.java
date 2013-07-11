///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tunaweza.web.engine.initialize;
//
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.orm.hibernate4.HibernateTransactionManager;
//import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import com.google.common.base.Preconditions;
///**
// *
// * @author naistech
// */
//@Configuration
//@EnableTransactionManagement
//@PropertySource({ "classpath:persistence-mysql.properties" })
//@ComponentScan({ "com.tunaweza" })
//public class PersistenceConfig {
//  
//    @Autowired
//    private Environment env;
//
//    public PersistenceConfig() {
//        super();
//    }
//
//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(restDataSource());
//        sessionFactory.setPackagesToScan(new String[] { "com.tunaweza.core.business.model" });
//        sessionFactory.setHibernateProperties(hibernateProperties());
//
//        return sessionFactory;
//    }
//
//    @Bean
//    public DataSource restDataSource() {
//        final BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
//        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
//        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.user")));
//        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.pass")));
//
//        return dataSource;
//    }
//
//    @Bean
//    public HibernateTransactionManager transactionManager() {
//        final HibernateTransactionManager txManager = new HibernateTransactionManager();
//        txManager.setSessionFactory(sessionFactory().getObject());
//
//        return txManager;
//    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//    final Properties hibernateProperties() {
//        return new Properties() {
//            {
//                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
//
//                // setProperty("hibernate.globally_quoted_identifiers", "true");
//                // note: necessary in launchpad-storage, but causing problems here
//            }
//        };
//    }
//}