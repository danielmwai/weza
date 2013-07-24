/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 *
 * @author naistech
 */
public class PropertiesUtil extends PropertyPlaceholderConfigurer{
	
	  Log logger = LogFactory.getLog(getClass());
	  private static Map<String,Set<String>> allowedMimeTypes;

	  @Override
	  protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
	      Properties props) throws BeansException {
	      super.processProperties(beanFactoryToProcess, props);
	      allowedMimeTypes = new HashMap<String,Set<String>>();
	      for (Object key : props.keySet()) {
	    	  if(key.toString().startsWith(".")){
		          String keyStr = key.toString();
		          String keyValue = props.getProperty(keyStr);
		          keyStr = keyStr.toString().substring(1);
		          Set<String> keyMime= new HashSet<String>();
		          if(keyValue.contains(",")){
		        	  String[] mimeTypes= keyValue.split(",");
		        	  for(String mimeType : mimeTypes ){
		        		  keyMime.add(mimeType);
		        	  }
		          }
		          else
		        	  keyMime.add(keyValue);
		          allowedMimeTypes.put(keyStr,keyMime);
		          logger.info("\nPropertiesUtil: Supported MimeType property found for  "+key);
		          logger.info("\nPropertiesUtil:  "+keyStr+" files allowed mimetypes set to: "+keyValue);
	    	  }		        	
	      }
	  }

	  public static boolean isfileSupported(String fileType, String mimeType) {
		  Set<String> mimetypes= allowedMimeTypes.get(fileType);
		  if(mimetypes != null)
			  return mimetypes.contains(mimeType);
		  else return false;
	  }


}

