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
//package com.tunaweza.core.business.dao.db;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import org.apache.log4j.Logger;
//import org.hibernate.HibernateException;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.cfg.AnnotationConfiguration;
//import org.hibernate.tool.hbm2ddl.SchemaExport;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * @version $Revision: 1.1.1.1 $
// * @since Build {3.0.0.SNAPSHOT} (06 2013)
// * @author Daniel mwai
// */
//@Repository(value = "databaseAPI")
//@Transactional
//public class DatabaseApiImpl implements DatabaseApi
//{
//
//	@PersistenceContext
//	private EntityManager entityManager;
//
//	@Autowired
//	LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;
//
//	private static Logger logger = Logger.getLogger(DatabaseApiImpl.class);
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.jjpeople.jjteach.Dao.db.DatabaseAPI#createDatabase(java.lang.String)
//	 */
//	public void createDatabase(String database) throws DatabaseException
//	{
//		logger.debug("Creating database: " + database);
//
//		if (!isValidString(database))
//			throw new DatabaseException(
//					"Database name cannot be empty or null: " + database);
//
//		database = database.trim();
//
//		Session session = getSession();
//
//		if (databaseExists(database))
//		{
//			logger.debug("Database " + database + " already exists");
//
//			throw new DatabaseException("Database " + database
//					+ " already exists!!!");
//		}
//
//		try
//		{
//			// TODO Sanitize database parameter to prevent SQLInjection.
//			Query query = session.createSQLQuery("CREATE DATABASE " + database);
//			query.executeUpdate();
//		}
//		catch (HibernateException he)
//		{
//			logger.error(he.getMessage(), he.getCause());
//
//			throw new DatabaseException("Problem Creating Database!!!",
//					he.getCause());
//		}
//
//		logger.debug("Finished creating database: " + database);
//	}
//	
//	
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.jjpeople.jjteach.Dao.db.DatabaseAPI#dropDatabase(java.lang.String)
//	 */
//	public void dropDatabase(String database) throws DatabaseException
//	{
//		logger.debug("Dropping database: " + database);
//
//		if (!isValidString(database))
//			throw new DatabaseException(
//					"Database name cannot be empty or null: " + database);
//
//		database = database.trim();
//
//		if (!databaseExists(database))
//		{
//			logger.debug("Database (" + database
//					+ ") to be dropped does not exist");
//
//			throw new DatabaseException("Database (" + database
//					+ ") to be dropped does not exist");
//		}
//
//		Session session = getSession();
//
//		try
//		{
//			// TODO Sanitize database parameter to prevent SQLInjection.
//			Query query = session.createSQLQuery("DROP DATABASE " + database);
//			query.executeUpdate();
//		}
//		catch (HibernateException he)
//		{
//			logger.error(he.getMessage(), he.getCause());
//
//			throw new DatabaseException("Problem Dropping Database!!!",
//					he.getCause());
//		}
//
//		logger.debug("Finished dropping database: " + database);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.jjpeople.jjteach.Dao.db.DatabaseAPI#databaseExists(java.lang.String)
//	 */
//	public boolean databaseExists(String database) throws DatabaseException
//	{
//		if (!isValidString(database))
//			throw new DatabaseException("Invalid database name");
//
//		database = database.trim();
//
//		Session session = getSession();
//
//		String queryString = "SHOW DATABASES LIKE ?";
//
//		try
//		{
//			Query query = session.createSQLQuery(queryString);
//			query.setString(0, database);
//
//			return query.list().size() == 1;
//		}
//		catch (HibernateException he)
//		{
//			logger.error(he.getMessage(), he.getCause());
//
//			throw new DatabaseException("Error when checking if database ("
//					+ database + ") exists", he.getCause());
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.jjpeople.jjteach.Dao.db.DatabaseAPI#renameDatabase(java.lang.String,
//	 * java.lang.String)
//	 */
//	public void renameDatabase(String oldDatabase, String newDatabase)
//			throws DatabaseException
//	{
//		// TODO Implement this method
//
//		throw new UnsupportedOperationException();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.jjpeople.jjteach.Dao.db.DatabaseAPI#populateDatabase(java.lang.String
//	 * , java.lang.String, java.lang.String)
//	 */
//	public void populateDatabase(String databaseUrl, String user,
//			String password) throws DatabaseException
//	{
//		logger.debug("Populating database");
//
//		// Validate input
//		if (!isValidString(databaseUrl))
//			throw new DatabaseException("Database name cannot be empty or null");
//
//		if (!isValidString(user))
//			throw new DatabaseException("User cannot be empty or null");
//
//		if (!isValidString(password))
//			throw new DatabaseException("Password cannot be empty or null");
//
//		databaseUrl = databaseUrl.trim();
//		user = user.trim();
//		password = password.trim();
//
//		Configuration configuration = getConfiguredAnnotationConfiguration();
//
//		Connection connection = getConnection(databaseUrl, user, password);
//
//		createSchema(configuration, connection);
//		
//		/*saveDatabaseSuperUser(password,user);*/
//
//		try
//		{
//			connection.close();
//		}
//		catch (SQLException sqle)
//		{
//			logger.warn(sqle.getMessage(), sqle.getCause());
//		}
//
//		logger.debug("Finished populating database");
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.jjpeople.jjteach.Dao.db.DatabaseAPI#createPopulateDatabase(java.lang
//	 * .String, java.lang.String, java.lang.String)
//	 */
//	public void createPopulateDatabase(String database, String user,
//			String password) throws DatabaseException
//	{
//		createDatabase(database);
//
//		database = database.trim();
//
//		String databaseUrl = "jdbc:mysql://localhost:3306/" + database;
//
//		populateDatabase(databaseUrl, user, password);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.jjpeople.jjteach.Dao.db.DatabaseAPI#createDatabaseUser(java.lang.
//	 * String, java.lang.String, java.lang.String)
//	 */
//	public void createDatabaseUser(String host, String user, String password)
//			throws DatabaseException
//	{
//		if (!isValidString(host))
//			throw new DatabaseException("Invalid host name: " + host);
//
//		if (!isValidString(user))
//			throw new DatabaseException("Invalid user name: " + user);
//
//		if (!isValidString(password))
//			throw new DatabaseException("Invalid password: " + password);
//
//		host = host.trim();
//		user = user.trim();
//		password = password.trim();
//
//		Session session = getSession();
//
//		try
//		{
//			Query query = session
//					.createSQLQuery("CREATE USER ?@? IDENTIFIED BY ?");
//			query.setString(0, user);
//			query.setString(1, host);
//			query.setString(2, password);
//			query.executeUpdate();
//		}
//		catch (HibernateException he)
//		{
//			logger.error(he.getMessage(), he.getCause());
//
//			throw new DatabaseException("Problem Creating user: " + user + "@"
//					+ host);
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.jjpeople.jjteach.Dao.db.DatabaseAPI#deleteDatabaseUser(java.lang.
//	 * String, java.lang.String)
//	 */
//	public void deleteDatabaseUser(String user, String host)
//			throws DatabaseException
//	{
//		if (!isValidString(user))
//			throw new DatabaseException("User cannot be empty or null");
//
//		if (!isValidString(host))
//			throw new DatabaseException("Host cannot be empty or null");
//
//		user = user.trim();
//		host = host.trim();
//
//		Session session = getSession();
//
//		try
//		{
//			Query query = session.createSQLQuery("DROP USER ?@?");
//			query.setString(0, user);
//			query.setString(1, host);
//			query.executeUpdate();
//		}
//		catch (HibernateException he)
//		{
//			logger.error(he.getMessage(), he.getCause());
//
//			throw new DatabaseException("Problem deleting database user: "
//					+ user, he.getCause());
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.jjpeople.jjteach.Dao.db.DatabaseAPI#grantAllPermissionsToDBUser(java
//	 * .lang.String, java.lang.String, java.lang.String)
//	 */
//	public void grantAllPermissionsToDBUser(String database, String user,
//			String host) throws DatabaseException
//	{
//		if (!isValidString(database))
//			throw new DatabaseException(
//					"Database name cannot be empty or null: " + database);
//
//		if (!isValidString(user))
//			throw new DatabaseException("User cannot be empty or null: " + user);
//
//		if (!isValidString(host))
//			throw new DatabaseException("Host cannot be empty or null: " + host);
//
//		database = database.trim();
//		user = user.trim();
//		host = host.trim();
//
//		Session session = getSession();
//
//		// TODO Sanitize database parameter to prevent SQLInjection
//		String queryString = "GRANT ALL PRIVILEGES ON " + database
//				+ ".* TO ?@?";
//
//		try
//		{
//			Query query = session.createSQLQuery(queryString);
//			query.setString(0, user);
//			query.setString(1, host);
//			query.executeUpdate();
//		}
//		catch (HibernateException he)
//		{
//			logger.error(he.getMessage(), he.getCause());
//
//			throw new DatabaseException("Problem granting privileges to user: "
//					+ user, he.getCause());
//		}
//	}
//
//	/*
//	 * Gets entity manager
//	 */
//	private EntityManager getEntityManager()
//	{
//		return entityManager;
//	}
//
//	/*
//	 * Gets session.
//	 */
//	private Session getSession()
//	{
//		return (Session) getEntityManager().getDelegate();
//	}
//
//	/*
//	 * Validates a String by checking if its empty or null and returning false
//	 * if it is. Otherwise it returns true.
//	 */
//	private boolean isValidString(String string)
//	{
//		return !(string == null || string.trim().equals(""));
//	}
//
//	/*
//	 * Creates a new AnnotationConfiguration, adds annotated classes to it and
//	 * returns it.
//	 */
//	private Configuration getConfiguredAnnotationConfiguration()
//			throws DatabaseException
//	{
//		AnnotationConfiguration annotationConfiguration = new AnnotationConfiguration();
//
//		List<String> classNames = getAnnotatedPersistentClasses();
//
//		for (String clazz : classNames)
//		{
//			try
//			{
//				annotationConfiguration.addAnnotatedClass(Class.forName(clazz));
//			}
//			catch (ClassNotFoundException cnfe)
//			{
//				logger.error(cnfe);
//
//				throw new DatabaseException("class " + clazz
//						+ " cannot be found");
//			}
//		}
//		// TODO Add a hibernate.dialect parameter to the method for flexibility.
//		annotationConfiguration.setProperty("hibernate.dialect",
//				"org.hibernate.dialect.MySQL5InnoDBDialect");
//
//		return (Configuration) annotationConfiguration;
//	}
//
//	/*
//	 * Gets annotated entity classes from the persistence.xml.
//	 */
//	private List<String> getAnnotatedPersistentClasses()
//	{
//		return localContainerEntityManagerFactoryBean.getPersistenceUnitInfo()
//				.getManagedClassNames();
//	}
//
//	/*
//	 * Gets a new connection to a database with the given url, username and
//	 * password.
//	 */
//	private Connection getConnection(String url, String user, String password)
//			throws DatabaseException
//	{
//		if (!isValidString(url))
//			throw new DatabaseException("Url cannot be empty or null");
//
//		if (!isValidString(user))
//			throw new DatabaseException("User cannot be empty or null");
//
//		if (!isValidString(password))
//			throw new DatabaseException("Password cannot be empty or null");
//
//		url = url.trim();
//		user = user.trim();
//		password = password.trim();
//
//		Connection connection = null;
//		try
//		{
//			connection = DriverManager.getConnection(url, user, password);
///*james work*/			
//			/*saveDatabaseSuperUser(1,"Company:",user,password,"Company@Company.com");*/
//		}
//		catch (SQLException sqle)
//		{
//			logger.error(sqle.getMessage(), sqle.getCause());
//
//			throw new DatabaseException("Problem accessing the database");
//		}
//
//		return connection;
//	}
//
//	/*
//	 * Adds tables to the database presented by the given connection.
//	 */
//	private void createSchema(Configuration configuration, Connection connection)
//			throws DatabaseException
//	{
//		if (configuration == null)
//			throw new DatabaseException("Configuration cannot be null");
//
//		if (connection == null)
//			throw new DatabaseException("Connection cannot be null");
//
//		try
//		{
//			SchemaExport schemaExport = new SchemaExport((org.hibernate.cfg.Configuration) configuration,
//					connection);
//			schemaExport.create(true, true);
//			/*saveDatabaseSuperUser();*/
//		}
//		catch (HibernateException he)
//		{
//			logger.error(he.getMessage(), he.getCause());
//
//			throw new DatabaseException("Problem preparing for schema export");
//		}
//	}
//}
//
