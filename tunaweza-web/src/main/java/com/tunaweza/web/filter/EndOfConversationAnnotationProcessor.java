package com.tunaweza.web.filter;

import com.tunaweza.core.business.model.user.EndOfConversation;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * @author Ephraim Muhia
 */
@Aspect
public class EndOfConversationAnnotationProcessor implements
        ApplicationContextAware, Ordered, InitializingBean {
    @Autowired
    SessionFactory sessionFactory;

    private final Log logger = LogFactory.getLog(EndOfConversationAnnotationProcessor.class);
    private Integer order = 1;
    private ApplicationContext appContext;
    public final String ENTITY_MANAGER_FACTORY_NAME = "entityManagerFactoryName";
    private final String ENTITY_MANAGER_FACTORY = "entityManagerFactory";
    HttpSession httpSession = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.appContext = applicationContext;

    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.order == null) {
            throw new IllegalArgumentException(
                    " The Integer 'order' property should be set for the class "
                            + this.getClass().getName());
        }
    }

    @Around("execution(public * *(..)) && @annotation(endOfConversationAnn)")
    public Object triggerFlushAndClose(ProceedingJoinPoint jp,
                                       EndOfConversation endOfConversationAnn)
            throws Throwable {
        Object retValue = null;

        if (!StringUtils.isEmpty(endOfConversationAnn.entityManagerFactory())) {
            String emfName = endOfConversationAnn.entityManagerFactory();
            EntityManagerFactory emf = this.getEntityManagerFactoryReference();
            if (ENTITY_MANAGER_FACTORY != null) {
                if (TransactionSynchronizationManager.hasResource(ENTITY_MANAGER_FACTORY)) {

                    EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(ENTITY_MANAGER_FACTORY);
                    EntityManager em = emHolder.getEntityManager();
//                    SessionImpl currentSession = (SessionImpl) em.getDelegate();
                    
                    try {
                        if (endOfConversationAnn.flush()
                                && !endOfConversationAnn.forceFlush()) {
                            logger.debug("> Set Flush mode to FlushMode.COMMIT for the Session from EntityManagerHolder "
                                    + emHolder.toString());
                            sessionFactory.getCurrentSession().setFlushMode(FlushMode.COMMIT);
                        }

                        retValue = jp.proceed();

                        if (endOfConversationAnn.flush()
                                && endOfConversationAnn.forceFlush()) {
                            logger.debug("> Force flush called. Flush now the persistence context for the Session from EntityManagerHolder "
                                    + emHolder.toString());
                            if (sessionFactory.getCurrentSession().getTransaction() != null
                                    && sessionFactory.getCurrentSession().getTransaction()
                                                     .isActive()) {
                                sessionFactory.getCurrentSession().flush();
                                sessionFactory.getCurrentSession().getTransaction().commit();
                            } else {
                                logger.debug(">> No transaction detected, start a new one to perform flush and commit for EntityManagerHolder"
                                        + emHolder.toString());
                                sessionFactory.getCurrentSession().getTransaction().begin();
                                sessionFactory.getCurrentSession().flush();
                                sessionFactory.getCurrentSession().getTransaction().commit();
                            }
                        }
                        logger.debug("> Closing the Session from EntityManagerHolder "
                                + emHolder.toString());
                        sessionFactory.getCurrentSession().close();
                    } catch (Throwable throwable) {

                        try {
                            if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                                logger.debug("> Trying to rollback database transaction after exception");
                                sessionFactory.getCurrentSession().getTransaction().rollback();
                            }
                        } catch (Throwable rbEx) {
                            logger.error(
                                    "> Could not rollback transaction after exception!",
                                    rbEx);
                        } finally {
                            logger.error("> Cleanup after exception!");

                            HibernateLongConversationUtils.unregisterPreviousResources(ENTITY_MANAGER_FACTORY);

                            logger.debug("> Closing Session after exception");
                            sessionFactory.getCurrentSession().close();

                            logger.debug("> Removing Session from HttpSession");
                            httpSession.setAttribute(ENTITY_MANAGER_FACTORY,
                                    null);

                        }
                    } finally {
                        HibernateLongConversationUtils.unregisterPreviousResources(ENTITY_MANAGER_FACTORY);
                        EntityManager newEm = emf.createEntityManager();
                        EntityManagerHolder newEmHolder = new EntityManagerHolder(
                                newEm);

                        logger.debug("> Creating a new Session with EntityManagerHolder "
                                + newEmHolder.toString());

                        HibernateLongConversationUtils.registerResources(
                                ENTITY_MANAGER_FACTORY, newEmHolder);
                        httpSession.setAttribute(ENTITY_MANAGER_FACTORY,
                                newEmHolder);

                    }

                } else {
                    logger.debug(" No entityManager found with key "
                            + emf.toString());

                }
            } else {
                throw new IllegalAccessException(
                        "Can not find the entityManagerFactory with name '"
                                + emfName + "' in the Spring context !");
            }
        } else {
            logger.warn("'entityManagerFactory' property is not set for the annotation @EndOfConversation thus no advice is applied");
            retValue = jp.proceed();
        }
        return retValue;
    }

    private EntityManagerFactory getEntityManagerFactoryReference() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        httpSession = requestAttributes.getRequest().getSession();
        return (EntityManagerFactory) httpSession.getAttribute(ENTITY_MANAGER_FACTORY_NAME);
    }
}