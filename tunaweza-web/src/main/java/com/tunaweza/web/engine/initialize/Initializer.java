///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tunaweza.web.engine.initialize;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRegistration;
//
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
///**
// *
// * @author naistech
// */
//public class Initializer implements WebApplicationInitializer {
//
//    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
//    private static final String DISPATCHER_SERVLET_MAPPING = "/";
//
//    public void onStartup(ServletContext servletContext)
//            throws ServletException {
//        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.register(WebAppConfig.class);
//        rootContext.refresh();
//
//        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(rootContext));
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);
//
//        servletContext.addListener(new ContextLoaderListener(rootContext));
//    }
//
//}
