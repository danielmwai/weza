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

import static com.mysema.query.jpa.JPQLGrammar.type;
import com.tunaweza.core.business.Dao.exceptions.student.StudentDoesNotExistException;
import com.tunaweza.core.business.Dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.Dao.report.ReportDao;
import com.tunaweza.core.business.model.report.Report;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.user.User;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import javax.xml.transform.Templates;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Columns;
import org.hibernate.type.ImageType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDao reportDao;

	@Autowired
	private UserService userService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CourseTemplateService courseTemplateService;

	Logger log = Logger.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.dynamicreports.ReportService#buildReport
	 * (com.jjpeople.jjteach.web.service.dynamicreports.DataSource,
	 * java.lang.String,
	 * com.jjpeople.jjteach.web.service.dynamicreports.impl.Template)
	 */
	@Override
	public ByteArrayOutputStream buildReport(List<?> dataSourceList,
			String reportTitle, String reportType,
			List<ReportColumn> reportColumns) {
		JasperReportBuilder report = DynamicReports.report();
		// Create the columns
		addReportColumns(reportType, reportColumns, report);
		report.setTemplate(Templates.reportTemplate).setShowColumnTitle(false);
		// Prevent printing of page numbers in csv files
		if (!reportType.equalsIgnoreCase("csv")) {
			report.title(cmp.text(reportTitle)).pageFooter(cmp.pageXofY())
					.setShowColumnTitle(false);
		}
		// Set the data source
		report.setDataSource(dataSourceList);
		return printReport(report, reportType);
	}

	// builds a report with grouped data
	@Override
	public ByteArrayOutputStream createStudentProgressReport(String userId,
			String reportType) {
		Student student = null;
		JasperReportBuilder report = report();
		try {
			student = studentService.getStudentById(Long.valueOf(userId));

			TextColumnBuilder<String> itemColumn = col.column("Course",
					"course", type.stringType());

			ColumnGroupBuilder itemGroup = grp.group(itemColumn)
					.setTitleWidth(30).setHeaderLayout(GroupHeaderLayout.VALUE)
					.showColumnHeaderAndFooter();

			report.setTemplate(Templates.reportTemplate)
					.setShowColumnTitle(false)
					.columns(
							itemColumn,
							col.column("Module", "moduleName",
									type.stringType()),
							col.column("Start Date", "startDate",
									type.stringType()),
							col.column("End Date", "endDate", type.stringType()),
							col.column("Pending Exercises", "pendingExercises",
									type.stringType()),
							col.column("Test Score%", "testScore",
									type.stringType()))
					.groupBy(itemGroup)
					.title(cmp.text(student.getUser().getFirstName()
							+ "'s progress report"))
					.pageFooter(Templates.footerComponent)
					.setDataSource(studentProgressDataSource(student));
		} catch (StudentDoesNotExistException e) {
			log.info("Student with id:" + userId + "was not found!!");
			e.printStackTrace();
		}
		System.out.println("report" + report);
		return printReport(report, reportType);
	}

	private ByteArrayOutputStream printReport(JasperReportBuilder rb,
			String type) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			if (type.equalsIgnoreCase("csv")) {
				rb.setShowColumnTitle(false);
				rb.toCsv(baos);
				return baos;
			} else if (type.equalsIgnoreCase("xls")) {
				rb.toXls(baos);
				return baos;
			} else if (type.equalsIgnoreCase("docx")) {
				rb.toDocx(baos);
				return baos;
			} else if (type.equalsIgnoreCase("ppt")) {
				rb.toPptx(baos);
				return baos;
			} else if (type.equalsIgnoreCase("jpg")) {
				rb.toImage(baos, ImageType.JPG);
				return baos;
			} else if (type.equalsIgnoreCase("png")) {
				rb.toImage(baos, ImageType.PNG);
				return baos;
			} else if (type.equalsIgnoreCase("gif")) {
				rb.toImage(baos, ImageType.GIF);
				return baos;
			} else if (type.equalsIgnoreCase("rtf")) {
				rb.toRtf(baos);
				return baos;
			} else if (type.equalsIgnoreCase("odt")) {
				rb.toOdt(baos);
				return baos;
			} else if (type.equalsIgnoreCase("ods")) {
				rb.toOds(baos);
				return baos;
			} else if (type.equalsIgnoreCase("html")) {
				rb.toHtml(baos);
				return baos;
			} else {
				rb.toPdf(baos);
				return baos;
			}
		} catch (DRException e) {
			log.debug("Unable to create report in the format " + type);
			e.printStackTrace();
		}
		return baos;
	}

	/**
	 * creates datasource for student progress report
	 */
	private JRDataSource studentProgressDataSource(Student student) {

		DataSource dataSource = new DataSource("course", "moduleName",
				"startDate", "endDate", "pendingExercises", "testScore");
		List<CourseTemplate> courseTemplates = new ArrayList<CourseTemplate>();
		try {

			List<EmbeddableCourseTemplate> embeddableCourses = student
					.getCourseTemplateList();

			for (EmbeddableCourseTemplate embeddableCT : embeddableCourses) {
				Long courseId = embeddableCT.getCourseTemplateId();
				try {
					courseTemplates.add(courseTemplateService
							.findCourseTemplateById(courseId));

				} catch (CourseTemplateNotFoundException e) {
					log.info("CourseTemplate with id:" + courseId
							+ "was not found!!");
					e.printStackTrace();
				}
			}

			Map<MonitorCourseTemplateBean, List<MonitorModuleBean>> moduleMap = studentService
					.getStudentModuleStatistics(student, courseTemplates);
			if (moduleMap != null) {
			// loop through the moduleMap and populate the dataSource
			for (Map.Entry<MonitorCourseTemplateBean, List<MonitorModuleBean>> entry : moduleMap
					.entrySet()) {
				String courseName = entry.getKey().getCourseTemplateName();

				List<MonitorModuleBean> monitorModules = entry.getValue();
				for (MonitorModuleBean mb : monitorModules) {
					dataSource.add(courseName, mb.getModuleName(),
							mb.getStartDate(), mb.getEndDate(),
							String.valueOf(mb.getPendingExercises()),
							String.valueOf(mb.getTestScore()));
				}

				System.out.println(entry.getKey() + "/" + entry.getValue());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.dynamicreports.ReportService#buildReport
	 * (java.lang.String, java.sql.Connection, java.lang.String,
	 * java.lang.String, java.util.List)
	 */
	@Override
	public ByteArrayOutputStream buildReport(String sqlQuery,
			Connection connection, String reportTitle, String reportType,
			List<ReportColumn> reportColumns) throws DRException {

		JasperReportBuilder report = DynamicReports.report();
		// Create the columns
		addReportColumns(reportType, reportColumns, report);
		report.setTemplate(Templates.reportTemplate);
		// .setShowColumnTitle(false);
		// Prevent printing of page numbers in csv files
		if (!reportType.equalsIgnoreCase("csv")) {
			report.title(cmp.text(reportTitle)).pageFooter(cmp.pageXofY());
		}
		// Set the data source
		report.setDataSource(sqlQuery, connection);
		return printReport(report, reportType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jjpeople.jjteach.web.service.dynamicreports.ReportService#buildReport
	 * (java.sql.ResultSet, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public ByteArrayOutputStream buildReport(ResultSet rs, String reportTitle,
			String reportType, List<ReportColumn> reportColumns)
			throws DRException {
		JasperReportBuilder report = DynamicReports.report();
		if (reportColumns.size() < 1) {
			createReportColumns(rs, reportColumns);
		}
		addReportColumns(reportType, reportColumns, report);
		report.setTemplate(Templates.reportTemplate);
		//
		// Prevent printing of page numbers in csv files
		if (!reportType.equalsIgnoreCase("csv")) {
			report.title(cmp.text(reportTitle)).pageFooter(cmp.pageXofY());
		}
		// Set the data source
		report.setDataSource(rs);
		return printReport(report, reportType);
	}

	/**
	 * @author Chrisantus Khamasi Add columns to a {@link JasperReportBuilder}
	 *         object
	 * @param reportType
	 * @param reportColumns
	 * @param report
	 */
	private void addReportColumns(String reportType,
			List<ReportColumn> reportColumns, JasperReportBuilder report) {
		// Create the columns
		for (ReportColumn cl : reportColumns) {
			try {

				if (cl.isGroup()) {
					// Group By columns
					TextColumnBuilder<String> groupColumn = Columns.column(
							cl.getDataType(), cl.getSourceColumnName(),
							type.stringType());
					ColumnGroupBuilder itemGroup = grp.group(groupColumn);
					itemGroup.setTitleWidth(30)
							.setHeaderLayout(GroupHeaderLayout.VALUE)
							.showColumnHeaderAndFooter();
					report.addColumn(groupColumn);
					if (!reportType.equalsIgnoreCase("csv")) {
						report.groupBy(itemGroup);
					}
				} else {
					// Normal columns
					report.addColumn(Columns.column(cl.getReportColumnName(),
							cl.getSourceColumnName(),
							DataTypes.detectType(cl.getDataType())
									.getValueClass()));
				}
			} catch (DRException e) {
				log.error("An error occured while trying to add columns \n" + e);
			}
		}
	}

	/**
	 * @author Chrisantus Khamasi Create columns from an SQL resultset
	 * @param rs
	 *            The sql resultset
	 * @param rcolumns
	 *            A list to add columns to
	 */
	private void createReportColumns(ResultSet rs, List<ReportColumn> rcolumns) {
		ResultSetMetaData rsm;
		try {
			rsm = rs.getMetaData();
			int j = rsm.getColumnCount();
			for (int i = 1; i <= j; i++) {
				int pos = rsm.getColumnClassName(i).lastIndexOf('.');
				String dataType = rsm.getColumnClassName(i);
				dataType = dataType.substring(pos + 1);
				// Check for timestamp data type and change to date
				if (dataType.equalsIgnoreCase("Timestamp")) {
					dataType = "Date";
				}
				// Format column label
				String columnLabel = rsm.getColumnLabel(i);
				columnLabel = columnLabel.substring(0, 1).toUpperCase()
						+ columnLabel.substring(1);
				columnLabel = columnLabel.replaceAll("_", " ");
				ReportColumn column = new ReportColumn(columnLabel,
						rsm.getColumnName(i), dataType);
				rcolumns.add(column);
			}
		} catch (SQLException e) {
			log.error("An Error Occured while trying to create columns \n" + e);
		}
	}

	@Override
	public List<String> getTableColumns(Connection conn, String tableName)
			throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SHOW COLUMNS FROM " + tableName);
		List<String> columns = new ArrayList<String>();
		while (rs.next()) {
			columns.add(rs.getString("Field"));
		}
		return columns;
	}

	@Override
	public List<ReportTableBean> getTables(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SHOW TABLES");
		List<ReportTableBean> reportTableBeans = new ArrayList<ReportTableBean>();
		while (rs.next()) {
			ReportTableBean table = new ReportTableBean();
			table.setTableName(rs.getString(1));
			table.setTableColumns(getTableColumns(conn, table.getTableName()));
			reportTableBeans.add(table);
		}
		return reportTableBeans;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jjpeople.jjteach.web.service.dynamicreports.ReportService#
	 * createUserDetailsReport(java.lang.String)
	 */
	@Override
	public ByteArrayOutputStream createUserDetailsReport(String userId,
			String reportType) {
		JasperReportBuilder report = report();
		try {
			User user = userService.getUserById(Long.valueOf(userId));
			report.setTemplate(Templates.reportTemplate)
					.setShowColumnTitle(false)
					.columns(col.column("item", "item", type.stringType()),
							col.column("value", "value", type.stringType()))
					.title(cmp.text(user.getFirstName() + "'s details"))
					.pageFooter(Templates.footerComponent)
					.setDataSource(UserDetailsDataSource(user));
		} catch (NumberFormatException e) {
			log.error(userId + "Is not a valid number");
			e.printStackTrace();
		} catch (UserDoesNotExistException e) {
			log.error("User with id: " + userId + " Does Not Exist");
			e.printStackTrace();
		}

		System.out.println("report" + report);
		return printReport(report, reportType);
	}

	/**
	 * creates a datasource for user details report
	 */
	private JRDataSource UserDetailsDataSource(User user) {
		DataSource dataSource = new DataSource("item", "value");

		dataSource.add("First Name:", user.getFirstName());
		dataSource.add("Last Name:", user.getLastName());
		dataSource.add("Email:", user.getEmail());
		if (user.getLocation() != null) {
		dataSource.add("Location:", user.getLocation().getLocationName());
		}
		String strRoles = "";
		List<Role> userRoles = new ArrayList<Role>();
		userRoles = user.getRoles();
		if (userRoles.isEmpty()) {
			strRoles = "None";
		}
		else{
			for(Role role:userRoles){
				strRoles+=role.getRoleName()+" ";
			}
		}
		dataSource.add("Roles:", strRoles);
		if (user.getCreationDate() != null) {
		dataSource.add("User created:", user.getCreationDate().getTime()
					.toString());
		}

		return dataSource;
	}

	@Override
	public List<Report> getAllReports() {
		return reportDao.findAll();
	}

	@Override
	public void deleteReport(Long report_id) {
		Report report = reportDao.findById(report_id);
		reportDao.delete(report);
	}

	@Override
	public Report getReportById(Long report_id) {
		return reportDao.findById(report_id);
	}

	@Override
	public void saveReport(Report report) {
		reportDao.saveOrUpdate(report);
	}

}
