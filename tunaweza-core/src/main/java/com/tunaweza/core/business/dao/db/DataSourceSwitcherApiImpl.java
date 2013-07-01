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

package com.tunaweza.core.business.Dao.db;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
@Repository(value = "dataSourceSwitcherApi")
public class DataSourceSwitcherApiImpl implements DataSourceSwitcherApi,
        ApplicationContextAware {
    private final Log logger = LogFactory.getLog(DataSourceSwitcherApiImpl.class);
    public final String ENTITY_MANAGER_FACTORY_NAME = "entityManagerFactoryName";
    private final String ENTITY_MANAGER_FACTORY = "entityManagerFactory";
    private ApplicationContext appContext;
    private HttpSession httpSession;

    public void setDatasourceProperties(ComboPooledDataSource dataSource,
                                        String url, String username,
                                        String password) {
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        logger.debug(">>> " + dataSource.getJdbcUrl());
        logger.debug(">>> " + dataSource.getUser());
    }

    /**
     * @param httpSession the httpSession to set
     */
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void setHttpSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        this.httpSession = requestAttributes.getRequest().getSession();
    }

    @Override
    public void newEntityManagerFactory(String url, String username,
                                        String password) {
        String emfName = "&entityManagerFactory";
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = appContext.getBean(
                emfName, LocalContainerEntityManagerFactoryBean.class);
        newEntityManagerFactory(entityManagerFactoryBean, url, username,
                password);
    }

    @Override
    public void newEntityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean,
                                        String url, String username,
                                        String password) {

        if (httpSession == null)
            setHttpSession();

        ComboPooledDataSource dataSource = (ComboPooledDataSource) entityManagerFactoryBean.getDataSource();
        setDatasourceProperties(dataSource, url, username, password);
        EntityManagerFactory entityManagerFactory = entityManagerFactoryBean.getObject();

        EntityManager currentEm = entityManagerFactory.createEntityManager();
        Session currentSession = (Session) currentEm.getDelegate();
        currentSession.setFlushMode(FlushMode.MANUAL);

        EntityManagerHolder currentEmHolder = new EntityManagerHolder(currentEm);

        httpSession.setAttribute(this.ENTITY_MANAGER_FACTORY, currentEmHolder);
        httpSession.setAttribute(this.ENTITY_MANAGER_FACTORY_NAME,
                entityManagerFactory);
        unregisterPreviousResources(ENTITY_MANAGER_FACTORY);
        registerResources(ENTITY_MANAGER_FACTORY, currentEmHolder);
    }

    @Override
    public void newEntityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean,
                                        HttpSession session, String url,
                                        String username, String password) {

        this.httpSession = session;

        ComboPooledDataSource dataSource = (ComboPooledDataSource) entityManagerFactoryBean.getDataSource();
        setDatasourceProperties(dataSource, url, username, password);
        EntityManagerFactory entityManagerFactory = entityManagerFactoryBean.getObject();

        EntityManager currentEm = entityManagerFactory.createEntityManager();
        Session currentSession = (Session) currentEm.getDelegate();
        currentSession.setFlushMode(FlushMode.MANUAL);

        EntityManagerHolder currentEmHolder = new EntityManagerHolder(currentEm);

        httpSession.setAttribute(this.ENTITY_MANAGER_FACTORY, currentEmHolder);
        httpSession.setAttribute(this.ENTITY_MANAGER_FACTORY_NAME,
                entityManagerFactory);
        unregisterPreviousResources(ENTITY_MANAGER_FACTORY);
        registerResources(ENTITY_MANAGER_FACTORY, currentEmHolder);
    }

    public void registerResources(String entityManagerFactoryString,
                                  EntityManagerHolder newEmHolder) {
        if (logger.isDebugEnabled()) {
            logger.debug(">> Register EntityManagerHolder" + newEmHolder
                    + " for this EntityManagerFactory"
                    + entityManagerFactoryString + " to "
                    + Thread.currentThread().toString());
        }

        TransactionSynchronizationManager.bindResource(
                entityManagerFactoryString, newEmHolder);
    }

    public void unregisterPreviousResources(String entityManagerFactoryString) {
        EntityManagerHolder oldEmHolder = (EntityManagerHolder) TransactionSynchronizationManager.unbindResourceIfPossible(entityManagerFactoryString);

        String oldEmHolderString = "null";

        if (oldEmHolder != null) {
            oldEmHolderString = oldEmHolder.toString();
        }
        if (logger.isDebugEnabled()) {
            logger.debug(">> Unregister old EntityManagerHolder "
                    + oldEmHolderString + " for this EntityManagerFactory "
                    + entityManagerFactoryString.toString() + " from "
                    + Thread.currentThread().toString());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext appContext)
            throws BeansException {
        this.appContext = appContext;
    }
    
    
    
    
}
