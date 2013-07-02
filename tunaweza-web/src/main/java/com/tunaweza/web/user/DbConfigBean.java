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
package com.tunaweza.web.user;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class DbConfigBean {

    private String dbHost;
    private String dbName;
    private String dbUsername;
    private String dbPass;

    public DbConfigBean(String host, String name, String username, String pass) {
        this.dbHost = host;
        this.dbName = name;
        this.dbUsername = username;
        this.dbPass = pass;
    }

    /**
     * @return the dbHost
     */
    public String getDbHost() {
        return dbHost;
    }

    /**
     * @param dbHost the dbHost to set
     */
    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the dbUsername
     */
    public String getDbUsername() {
        return dbUsername;
    }

    /**
     * @param dbUsername the dbUsername to set
     */
    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    /**
     * @return the dbPass
     */
    public String getDbPass() {
        return dbPass;
    }

    /**
     * @param dbPass the dbPass to set
     */
    public void setDbPass(String dbPass) {
        this.dbPass = dbPass;
    }

}
