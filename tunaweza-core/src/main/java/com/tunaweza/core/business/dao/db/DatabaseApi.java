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

package com.tunaweza.core.business.dao.db;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface DatabaseApi {

	/**
	 * Creates a database.
	 * 
	 * @param database
	 *            The name of the database to create.
	 * @throws DatabaseException
	 */
	public void createDatabase(String database) throws DatabaseException;

	/**
	 * Drops a database.
	 * 
	 * @param database
	 *            The name of the database to drop.
	 * @throws DatabaseException
	 */
	public void dropDatabase(String database) throws DatabaseException;

	/**
	 * Checks to see if a database exists.
	 * 
	 * @param database
	 *            The name of the database to be checked if exists.
	 * @return <code>true</code> if it exists otherwise <code>false</code>.
	 * @throws DatabaseException
	 */
	public boolean databaseExists(String database) throws DatabaseException;

	/**
	 * Renames a database.
	 * 
	 * @param databaseName
	 *            The name of the database to be renamed.
	 * @param newDatabaseName
	 *            The new name of the database.
	 * @throws DatabaseException
	 */
	public void renameDatabase(String databaseName, String newDatabaseName)
			throws DatabaseException;

	/**
	 * Populates a database with tables.
	 * 
	 * @param databaseURL
	 *            The URL of the database to populate with tables
	 * @param user
	 *            The user name to use to log into the database.
	 * @param password
	 *            The password to use to log into the database.
	 * @throws DatabaseException
	 */
	public void populateDatabase(String databaseURL, String user,
			String password) throws DatabaseException;

	/**
	 * Creates a database and populates it with the desired tables.
	 * 
	 * @param database
	 *            The name of the database to create and populate with tables.
	 * @param user
	 *            The user name to use to log into the database.
	 * @param password
	 *            The password to use to log into the database.
	 * @throws DatabaseException
	 */
	public void createPopulateDatabase(String database, String user,
			String password) throws DatabaseException;

	/**
	 * Creates a database user.
	 * 
	 * @param host
	 *            The host of the user that is being created.
	 * @param user
	 *            The name of the user to create.
	 * @param password
	 *            The password of the user being created.
	 * @throws DatabaseException
	 */
	public void createDatabaseUser(String host, String user, String password)
			throws DatabaseException;

	/**
	 * Deletes a database user.
	 * 
	 * @param user
	 *            The name of the user to delete.
	 * @param host
	 *            The host of the user to be deleted.
	 * @throws DatabaseException
	 */
	public void deleteDatabaseUser(String user, String host)
			throws DatabaseException;

	/**
	 * Grants all privileges on a given database to a given user.
	 * 
	 * @param database
	 *            The name of the database to grant all privileges on.
	 * @param user
	 *            The name of the user to grant all privileges to.
	 * @param host
	 *            The host of the user to grant all privileges to.
	 * @throws DatabaseException
	 */
	public void grantAllPermissionsToDBUser(String database, String user,
			String host) throws DatabaseException;


}
