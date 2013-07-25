package com.tunaweza.web.datasource;


import static com.tunaweza.core.business.utils.SessionHelper.setSessionAttribDbConfig;
import com.tunaweza.web.spring.configuration.user.bean.DbConfigBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 
 * @author David Gitonga
 *
 */
@Service
public class JJteachDataSourceImpl implements JJteachDataSource {
	
	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
	
	
	public void jjteachDatasource(HttpServletRequest req){
	String emfName = "&entityManagerFactory";

	HttpSession httpSession = ((HttpServletRequest) req)
			.getSession(true);
	WebApplicationContext wac = WebApplicationContextUtils
			.getRequiredWebApplicationContext(httpSession
					.getServletContext());
	this.entityManagerFactoryBean = wac.getBean(emfName,
			LocalContainerEntityManagerFactoryBean.class);

	
	}

}
