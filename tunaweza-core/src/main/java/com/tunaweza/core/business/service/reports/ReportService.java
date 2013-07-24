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

package com.tunaweza.core.business.service.reports;

import com.tunaweza.core.business.service.reports.dynamicreports.ReportColumn;
import com.tunaweza.core.business.service.reports.dynamicreports.ReportTableBean;
import com.tunaweza.core.business.model.report.Report;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public interface ReportService {

	/**
	 * Build a report with the DataSource provided
	 * 
	 * @param datasource
	 *            The data to be displayed in the report
	 * @param reportTitle
	 *            TODO
	 * @param reportType
	 *            The output type of the report e.g pdf,excel or csv
	 * @param reportColumns
	 *            The columns for data selection and output;
	 * @return
	 * @throws DRException
	 */
	public ByteArrayOutputStream buildReport(List<?> datasource,
			String reportTitle, String reportType,
			List<ReportColumn> reportColumns) throws DRException;

	/**
	 * Builds a report containing the progress of a student
	 * 
	 * @param userid
	 *            Id of the user
	 * @param reportType
	 *            The file format e.g pdf, csv excel
	 * @return ByteArrayOutputStream
	 */
	public ByteArrayOutputStream createStudentProgressReport(String userid,
			String reportType);

	/**
	 * Build a report using a mysql query
	 * 
	 * @param sqlQuery
	 *            The query for the data source
	 * @param connection
	 *            An SQl connection
	 * @param reportTitle
	 *            The Title of the report
	 * @param reportType
	 *            The type of report to generate e.g html or pdf
	 * @param reportColumns
	 *            The columns to display
	 * @return
	 * @throws DRException
	 */
	public ByteArrayOutputStream buildReport(String sqlQuery,
			Connection connection, String reportTitle, String reportType,
			List<ReportColumn> reportColumns) throws DRException;

	/**
	 * Build a report using a database query resultset
	 * 
	 * @param rs
	 *            The query resultset for the data source
	 * @param reportTitle
	 *            The Title of the report
	 * @param reportType
	 *            The type of report to generate e.g html or pdf
	 * @param reportColumns
	 *            The columns to display
	 * @return
	 * @throws DRException
	 */
	public ByteArrayOutputStream buildReport(ResultSet rs, String reportTitle,
			String reportType, List<ReportColumn> reportColumns)
			throws DRException;

	/**
	 * Get a list of the table column names
	 * 
	 * @param tableName
	 *            The table to be queried
	 * @param rs
	 *            ResultSet to use
	 * @return
	 * @throws SQLException
	 */
	public List<String> getTableColumns(Connection conn, String tableName)
			throws SQLException;

	/**
	 * Get a list of all the available tables from db
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<ReportTableBean> getTables(Connection conn) throws SQLException;
	
	public ByteArrayOutputStream createUserDetailsReport(String userId, String reportType);

	public Report getReportById(Long report_id);

	public List<Report> getAllReports();

	public void saveReport(Report report);

	public void deleteReport(Long report_id);

}
