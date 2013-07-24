package com.tunaweza.web.filter;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class HibernateLongConversationUtils {
    private static final Log logger = LogFactory.getLog(HibernateLongConversationUtils.class);
    public static void registerResources(String entityManagerFactoryString, EntityManagerHolder newEmHolder)
      {
          if (logger.isDebugEnabled())
          {
              logger.debug(">> Register EntityManagerHolder" + newEmHolder + " for this EntityManagerFactory"
                      + entityManagerFactoryString + " to " + Thread.currentThread().toString());
          }
       
          TransactionSynchronizationManager.bindResource(entityManagerFactoryString, newEmHolder);
      }
       
      public static void unregisterPreviousResources(String entityManagerFactoryString)
      {
          EntityManagerHolder oldEmHolder = (EntityManagerHolder) TransactionSynchronizationManager.unbindResourceIfPossible(entityManagerFactoryString);
       
          String oldEmHolderString = "null";
       
          if (oldEmHolder != null)
          {
              oldEmHolderString = oldEmHolder.toString();
          }
          if (logger.isDebugEnabled())
          {
              logger.debug(">> Unregister old EntityManagerHolder " + oldEmHolderString + " for this EntityManagerFactory "
                      + entityManagerFactoryString.toString() + " from " + Thread.currentThread().toString());
          }
      }
}
