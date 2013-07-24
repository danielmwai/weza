package com.tunaweza.web.filter;

import com.tunaweza.core.business.dao.datasource.DataSourceSwitcherApi;
import com.tunaweza.core.business.dao.datasource.DataSourceSwitcherApiImpl;
import com.tunaweza.web.spring.configuration.user.bean.DbConfigBean;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * @author Ephraim Muhia
 * @author James Mungai
 */
public class HibernateLongConversationFilter implements Filter {
	private final Log logger = LogFactory
			.getLog(HibernateLongConversationFilter.class);
	public final String ENTITY_MANAGER_FACTORY_NAME = "entityManagerFactoryName";
	private final String ENTITY_MANAGER_FACTORY = "entityManagerFactory";
	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;

	// @Autowired
	DataSourceSwitcherApi dataSourceSwitcherApi;

	@Override
	public void init(FilterConfig config) throws ServletException {

		String emfName = "&entityManagerFactory";
		// config.getInitParameter(ENTITY_MANAGER_FACTORY_NAME);
		if (StringUtils.isEmpty(emfName)) {
			throw new ServletException(ENTITY_MANAGER_FACTORY_NAME
					+ " is not defined for the filter");
		} else {
			// this.entityManagerFactoryName = emfName;
			logger.debug("Using EntityManagerFactory '" + emfName
					+ "' for HibernateLongConversationFilter");
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(config
							.getServletContext());
			// AutowireCapableBeanFactory autowireCapableBeanFactory =
			// wac.getAutowireCapableBeanFactory();
			// autowireCapableBeanFactory.configureBean(dataSourceSwitcherApi,
			// "dataSourceSwitcherApi");
			dataSourceSwitcherApi = new DataSourceSwitcherApiImpl();

			this.entityManagerFactoryBean = wac.getBean(emfName,
					LocalContainerEntityManagerFactoryBean.class);
			if (this.entityManagerFactoryBean == null) {
				throw new ServletException(
						" Can not find any EntityManagerFactoryBean with name '"
								+ emfName + "' in the Spring context");
			}
		}
	}

	@Override
	public void destroy() {
		HibernateLongConversationUtils
				.unregisterPreviousResources(ENTITY_MANAGER_FACTORY);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession httpSession = ((HttpServletRequest) request)
				.getSession(true);

		EntityManagerHolder currentEmHolder;

		currentEmHolder = (EntityManagerHolder) httpSession
				.getAttribute(this.ENTITY_MANAGER_FACTORY);

		if (currentEmHolder == null) {
			logger.debug("> New conversation");

			try{
				DbConfigBean dbConfigBean = (DbConfigBean) httpSession
						.getAttribute("DBCONFIG");
				String host = dbConfigBean.getDbHost();
				String dbname = dbConfigBean.getDbName();
				String username = dbConfigBean.getDbUsername();
				String pass = dbConfigBean.getDbPass();
				//httpSession.invalidate();
				dataSourceSwitcherApi.newEntityManagerFactory(
						entityManagerFactoryBean,"jdbc:mysql://"
								+ host + ":3306/" + dbname, username, pass);
				logger.info("DataSource changed!!!!!!!!!");
				//httpSession.invalidate();
			}catch(NullPointerException e){
				logger.info("Could not change dataSource!!!!!");
			}
		} else {
			logger.debug("> Continuing conversation");
			HibernateLongConversationUtils
					.unregisterPreviousResources(ENTITY_MANAGER_FACTORY);
			HibernateLongConversationUtils.registerResources(
					ENTITY_MANAGER_FACTORY, currentEmHolder);
		}

		try {
			
			chain.doFilter(request, response);
//			httpSession.invalidate();
			
		} catch (Throwable throwable) {
			logger.error(throwable.getMessage(), throwable);
		} finally {
			HibernateLongConversationUtils
					.unregisterPreviousResources(ENTITY_MANAGER_FACTORY);
		}

	}
}
// }
