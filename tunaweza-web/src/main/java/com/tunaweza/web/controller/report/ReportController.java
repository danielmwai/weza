package com.tunaweza.web.controller.report;

import com.tunaweza.core.business.dao.exceptions.student.StudentCourseEvaluationDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.student.StudentDoesNotExistException;
import com.tunaweza.core.business.dao.exceptions.user.UserDoesNotExistException;
import com.tunaweza.core.business.model.report.Report;
import com.tunaweza.core.business.model.role.Role;
import com.tunaweza.core.business.model.student.Student;
import com.tunaweza.core.business.model.user.User;
import com.tunaweza.core.business.service.evaluation.student.StudentEvaluationService;
import com.tunaweza.core.business.service.reports.dynamicreports.ReportColumn;
import com.tunaweza.core.business.service.reports.ReportService;
import com.tunaweza.core.business.service.student.StudentEvaluationTransactionBean;
import com.tunaweza.core.business.service.student.StudentService;
import com.tunaweza.core.business.service.user.UserCastService;
import com.tunaweza.core.business.service.user.UserService;
import com.tunaweza.web.views.Views;
import static com.tunaweza.web.views.Views.CUSTOM_REPORT;
import static com.tunaweza.web.views.Views.CUSTOM_REPORT_PREVIEW;
import static com.tunaweza.web.views.Views.DELETE_REPORT;
import static com.tunaweza.web.views.Views.DOWNLOAD_CUSTOM_REPORT;
import static com.tunaweza.web.views.Views.DOWNLOAD_STUDENT_EVALUATION_REPORT;
import static com.tunaweza.web.views.Views.GET_TABLE_FIELDS;
import static com.tunaweza.web.views.Views.JOIN_TABLE;
import static com.tunaweza.web.views.Views.LIST_REPORTS;
import static com.tunaweza.web.views.Views.REPORT_TABLE;
import static com.tunaweza.web.views.Views.SAVED_REPORT;
import static com.tunaweza.web.views.Views.SQL_REPORT;
import static com.tunaweza.web.views.Views.STUDENT_PROGRESS_REPORT;
import static com.tunaweza.web.views.Views.USER_INFO_REPORT;
import static com.tunaweza.web.views.Views.USER_REPORT;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.dynamicreports.report.exception.DRException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Reporting Functionality
 * 
 * @author Chrisantus Khamasi
 * @author Martin Mathu
 */

@Controller
@RequestMapping(Views.REPORT)
public class ReportController implements Views {

	@Autowired
	UserCastService userCastService;

	@Autowired
	ReportService reportService;

	@Autowired
	StudentService studentService;

	@Autowired
	StudentEvaluationService studentEvaluationService;

	@Autowired
	UserService userService;

	Logger log = Logger.getLogger(getClass());

	private ResultSet getSqlResultSet(String query) {
		// Get the current user company database credentials
		ResultSet rs = null;
		Connection conn = getSQLConnection();
		Statement st;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

	private Connection getSQLConnection() {

		String dbHost = userCastService.getUser().getUserCompany()
				.getDbaseHost();
		String dbName = userCastService.getUser().getUserCompany()
				.getDbaseName();
		String dbUser = userCastService.getUser().getUserCompany()
				.getDbUserName();
		String dbPass = userCastService.getUser().getUserCompany()
				.getDbPassword();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/"
					+ dbName, dbUser, dbPass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	@RequestMapping(value = CUSTOM_REPORT, method = RequestMethod.GET)
	public String customReport(Model model) throws SQLException,
			ClassNotFoundException {
		model.addAttribute("TABLE_LIST",
				reportService.getTables(getSQLConnection()));
		model.addAttribute("ISJOIN", false);
		return CUSTOM_REPORT;
	}

	@RequestMapping(method = RequestMethod.GET, value = GET_TABLE_FIELDS)
	public @ResponseBody
	Map<String, ? extends Object> getTableFields(
			@RequestParam(value = "table", required = true) String table) {
		List<String> fieldList = new ArrayList<String>();
		try {
			fieldList = reportService
					.getTableColumns(getSQLConnection(), table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Collections.singletonMap("fields", fieldList);
	}

	@RequestMapping(value = REPORT_TABLE, method = RequestMethod.GET)
	public String loadTable(
			@RequestParam(value = "table", required = true) String table,
			Model model) throws SQLException, ClassNotFoundException {
		model.addAttribute("TABLE_NAME", table);
		model.addAttribute("FIELD_LIST",
				reportService.getTableColumns(getSQLConnection(), table));
		model.addAttribute("TABLE_LIST",
				reportService.getTables(getSQLConnection()));
		return REPORT_TABLE;
	}

	@RequestMapping(value = DOWNLOAD_CUSTOM_REPORT, method = RequestMethod.GET)
	public String downloadReport() throws SQLException, ClassNotFoundException {
		return REPORT_TABLE;
	}

	@RequestMapping(method = RequestMethod.GET, value = CUSTOM_REPORT_PREVIEW)
	public void customReportPreview(
			@RequestParam(value = "customquery", required = false) String customquery,
			@RequestParam(value = "reportTitle", required = false) String reportTitle,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "reportId", required = false) String reportId,
			HttpServletResponse response) throws IOException, DRException {
		if (customquery != null) {
			// Remove the last semicolon
			if (customquery.endsWith(";")) {
				customquery = customquery
						.substring(0, customquery.length() - 1);
			}
		} else if (reportId != null && reportId != "undefined") {
			Report report = reportService.getReportById(Long.valueOf(reportId));
			customquery = report.getReportQuery();
			reportTitle = report.getReportTitle();
		}
		// For preview limit the results to 20
		customquery += " LIMIT 0,20";
		ResultSet rs;
		try {
			if (reportTitle == null) {
				reportTitle = "Report";
			}
			rs = getSqlResultSet(customquery);
			List<ReportColumn> reportColumns = new ArrayList<ReportColumn>();
			reportService.buildReport(rs, reportTitle, "html", reportColumns)
					.writeTo(response.getOutputStream());
			response.setContentType("application/" + "htm");
		} catch (Exception e) {
			log.error("SQL Syntax Error");
			response.getWriter()
					.write("<div class='message err'>System Error!<br>Make sure Display fields are not empty,your tables are correctly joined and your SQL syntax is formed correctly</div>");
		}
		response.flushBuffer();

	}

	@RequestMapping(method = RequestMethod.GET, value = SQL_REPORT)
	public String sqlReport() {
		return SQL_REPORT;
	}

	@RequestMapping(method = RequestMethod.POST, value = SQL_REPORT)
	public void sqlReport(
			@RequestParam(value = "customquery", required = true) String customquery,
			@RequestParam(value = "reportType", required = true) String reportType,
			@RequestParam(value = "reportTitle", required = false) String reportTitle,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "save", required = false) String save,
			HttpServletResponse response) throws IOException,
			ClassNotFoundException, DRException {

		// Remove the last semicolon
		if (customquery.endsWith(";")) {
			customquery = customquery.substring(0, customquery.length() - 1);
		}

		ResultSet rs;
		try {
			if (reportTitle == null) {
				reportTitle = "Report";
			}
			rs = getSqlResultSet(customquery);
			List<ReportColumn> reportColumns = new ArrayList<ReportColumn>();
			reportService.buildReport(rs, reportTitle, reportType,
					reportColumns).writeTo(response.getOutputStream());
			response.setContentType("application/" + reportType);
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ reportTitle + "." + reportType + "\"");
			// Save the report
			if (save == null)
				save = "false";
			if (save.equals("true")) {
				Report report = new Report();
				report.setReportQuery(customquery);
				report.setReportTitle(reportTitle);
				reportService.saveReport(report);
			}

		} catch (Exception e) {
			log.error("SQL Syntax Error");
			log.error(e.getMessage());
			response.getWriter()
					.write("<div class='message err'>System Error!<br>Make sure Display fields are not empty,your tables are correctly joined and your SQL syntax is formed correctly</div>");
		}
		response.flushBuffer();
	}

	@RequestMapping(method = RequestMethod.POST, value = CUSTOM_REPORT)
	public String customReport(
			@RequestParam(value = "custom_fields", required = false) String[] custom_fields,
			@RequestParam(value = "field_aliases", required = false) String[] field_aliases,
			@RequestParam(value = "display_fields", required = false) String[] display_fields,
			@RequestParam(value = "field_values", required = false) String[] field_values,
			@RequestParam(value = "field_concat", required = false) String[] field_concat,
			@RequestParam(value = "operators", required = false) String[] operators,
			@RequestParam(value = "reportType", required = false) String reportType,
			@RequestParam(value = "reportTitle", required = false) String reportTitle,
			@RequestParam(value = "table", required = true) String tableName,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "save", required = false) String save,
			@RequestParam(value = "joinTables", required = false) String[] joinTables,
			@RequestParam(value = "mainJoinColumn", required = false) String mainJoinColumn,
			@RequestParam(value = "mainTable", required = false) String mainTable,
			@RequestParam(value = "joinColumn", required = false) String joinColumn,
			@RequestParam(value = "sqlString", required = false) String sqlString,
			@RequestParam(value = "isJoin", required = false) String isJoin,
			@RequestParam(value = "isMainTable", required = false) String isMainTable,
			@RequestParam(value = "joinTable", required = false) String joinTable,
			@RequestParam(value = "reportTable", required = false) String[] reportTables,
			@RequestParam(value = "step", required = false) String step,
			Model model) throws IOException, ClassNotFoundException,
			DRException, SQLException {
		StringBuilder sb = new StringBuilder();
		List<String> reportTableList = new ArrayList<String>();
		int step_no;
		if (step != null) {
			step_no = Integer.parseInt(step);
		} else {
			step_no = 1;
		}
		if (reportTables != null) {
			// Check if the number of join tables is greater than 1
			if (reportTables.length > 0) {
				for (int i = 0; i < reportTables.length; i++) {
					reportTableList.add(reportTables[i]);
				}
			}
		}

		if (isMainTable.equalsIgnoreCase("true")) {

			sb.append(buildQuery(custom_fields, field_aliases, display_fields,
					field_values, field_concat, operators, tableName, false,
					new StringBuilder(), "", "", step_no, reportTableList));
			reportTableList.add(tableName);
			if (joinTables != null) {
				// Check if the number of join tables is greater than 1
				if (joinTables.length > 1) {
					for (int i = 0; i < joinTables.length; i++) {
						reportTableList.add(joinTables[i]);
					}
				}
			}
			model.addAttribute("STEP_NO", step_no);
		} else {
			model.addAttribute("STEP_NO", step_no + 1);
			sb.append(buildQuery(custom_fields, field_aliases, display_fields,
					field_values, field_concat, operators, tableName, true,
					new StringBuilder(sqlString), joinColumn, joinTable + "_."
							+ mainJoinColumn, step_no, reportTableList));
		}
		model.addAttribute("SQL_STRING", sb.toString());
		if (joinTables != null) {

			// Check if the number of join tables is greater than 1
			if (joinTables.length > 1) {
				List<String> joinTablesList = new ArrayList<String>();
				for (int i = 1; i < joinTables.length; i++) {
					joinTablesList.add(joinTables[i]);
				}
				model.addAttribute("JOIN_TABLES", joinTablesList);
			}
			model.addAttribute("ISJOIN", true);
			model.addAttribute("MAIN_TABLE", mainTable);
			model.addAttribute("MAIN_TABLE_FIELDS", reportService
					.getTableColumns(getSQLConnection(), mainTable));
			model.addAttribute("TABLE_LIST",
					reportService.getTables(getSQLConnection()));
			model.addAttribute("TABLE_NAME", joinTables[0]);
			model.addAttribute("FIELD_LIST", reportService.getTableColumns(
					getSQLConnection(), joinTables[0]));
			model.addAttribute("REPORT_TABLES", reportTableList);
			return JOIN_TABLE;
		} else {
			return DOWNLOAD_CUSTOM_REPORT;
		}
	}

	private StringBuilder buildQuery(String[] custom_fields,
			String[] field_aliases, String[] display_fields,
			String[] field_values, String[] field_concat, String[] operators,
			String tableName, Boolean join, StringBuilder sqlQuery,
			String joinColumn, String mainTableColumn, int step,
			List<String> reportTables) {
		StringBuilder sb = new StringBuilder();
		StringBuilder select = new StringBuilder();
		StringBuilder where = new StringBuilder();
		if (!join) {
			sb.append("SELECT ");
		}
		String tableAlias = tableName + "_";
		// Construct String Query for the fields to be displayed
		if (display_fields != null) {
			for (int i = 0; i < display_fields.length; i++) {
				if (i > 0) {
					select.append(",");
				}
				select.append(tableAlias).append(".").append(display_fields[i])
						.append(" AS ");
				if (field_aliases != null && field_aliases.length > i) {
					select.append("'").append(field_aliases[i]).append("'");
				} else {
					select.append("'").append(field_aliases[i]).append("'");
				}
			}
		} else {
			// select.append(tableAlias + ".id");
		}

		// Construct condition statement
		if (custom_fields != null) {
			if (!join) {
				where.append(" WHERE ");
			}
			for (int i = 0; i < custom_fields.length; i++) {
				where.append(tableAlias).append(".").append(custom_fields[i]);
				where.append(" ").append(operators[i]).append(" ");
				where.append("'").append(field_values[i]).append("'");
				if (i != custom_fields.length - 1) {
					where.append(" ").append(field_concat[i]);
				}
				where.append(" ");
			}
		}

		if (join) {
			if (select.length() > 0) {
				select.append(" ");
				int selectInsertPoint = sqlQuery.indexOf("FROM") - 1;
				sqlQuery.insert(selectInsertPoint, "," + select);
			}
			if (sqlQuery.indexOf("WHERE") >= 0) {
				// if (reportTables.contains(tableName)) {
				// tableAlias = tableName + step;
				// }
				int joinInsertPoint = sqlQuery.indexOf("WHERE") - 1;
				sqlQuery.insert(joinInsertPoint, " JOIN " + tableName + " "
						+ tableAlias + " ON " + tableAlias + "." + joinColumn
						+ " = " + mainTableColumn + " ");
				int whereInsertPoint = sqlQuery.indexOf("WHERE") + 6;
				if (where.length() > 1) {
					sqlQuery.insert(whereInsertPoint, where + " AND ");
				}
			} else {
				sqlQuery.append(" JOIN " + tableName + " " + tableAlias
						+ " ON " + mainTableColumn + " = " + tableAlias + "."
						+ joinColumn + " ");

				if (where.length() > 1) {
					sqlQuery.append(" WHERE " + where);
				}
			}
			sb.append(sqlQuery);
		} else {
			sb.append(select);
			sb.append(" FROM ");
			sb.append(tableName);
			sb.append(" ");
			sb.append(tableAlias);
			sb.append(" ");
			sb.append(where);
		}
		return sb;
	}

	@RequestMapping(method = RequestMethod.GET, value = DELETE_REPORT)
	public String deleteReport(
			@RequestParam(value = "id", required = true) String reportId,
			Model model) {
		Role role = userCastService.getUser().getRoles().get(0);
		String rn = role.getRoleName();
		// Check if the user is allowed to delete the report
		if (rn.equals("ROLE_ADMIN") || rn.equals("ROLE_EVALUATOR")) {
			reportService.deleteReport(Long.valueOf(reportId));
		}
		List<Report> reports = new ArrayList<Report>();
		reports = reportService.getAllReports();
		if (reports.isEmpty()) {
			model.addAttribute("EMPTY", "No saved reports.");
		} else {
			model.addAttribute("REPORT_LIST", reports);
		}
		return LIST_REPORTS;
	}

	@RequestMapping(method = RequestMethod.GET, value = LIST_REPORTS)
	public String listReports(Model model) {
		List<Report> reports = new ArrayList<Report>();
		reports = reportService.getAllReports();
		if (reports.isEmpty()) {
			model.addAttribute("EMPTY", "No saved reports.");
		} else {
			model.addAttribute("REPORT_LIST", reports);
		}
		return LIST_REPORTS;
	}

	@RequestMapping(value = USER_REPORT, method = RequestMethod.GET)
	public void downloadUserReport(
			@RequestParam(value = "reportType", required = true) String reportType,
			@RequestParam(value = "role", required = false) String role,
			HttpServletResponse response) throws IOException,
			UserDoesNotExistException {
		List<User> users = new ArrayList<User>();
		if (role == null || role.equalsIgnoreCase("NONE")) {
			users = userService.getAllUsers();
		} else {

			users = userService.getUsersByRole(role);

		}
		List<ReportColumn> reportColumns = new ArrayList<ReportColumn>();
		ReportColumn column = new ReportColumn("First name", "firstName",
				"string");
		reportColumns.add(column);
		column = new ReportColumn("Last name", "lastName", "string");
		reportColumns.add(column);
		column = new ReportColumn("Email", "email", "string");
		reportColumns.add(column);
		column = new ReportColumn("Location", "location.locationName", "string");
		column.setGroup(false);
		reportColumns.add(column);

		try {
			response.setContentType("application/" + reportType);
			response.setHeader("Content-Disposition",
					"attachment; filename=\"user_report." + reportType + "\"");
			reportService.buildReport(users,
					new java.util.Date() + " User Report", reportType,
					reportColumns).writeTo(response.getOutputStream());
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.flushBuffer();
	}

	@RequestMapping(value = SAVED_REPORT, method = RequestMethod.GET)
	public void downloadSavedReport(
			@RequestParam(value = "reportType", required = true) String reportType,
			@RequestParam(value = "reportId", required = true) String reportId,
			HttpServletResponse response) throws IOException {

		Report report = reportService.getReportById(Long.valueOf(reportId));
		try {
			response.setContentType("application/" + reportType);
			response.setHeader("Content-Disposition",
					"attachment; filename=\"user_report." + reportType + "\"");
			ResultSet rs;
			rs = getSqlResultSet(report.reportQuery);
			List<ReportColumn> reportColumns = new ArrayList<ReportColumn>();
			reportService.buildReport(rs, report.getReportTitle(), reportType,
					reportColumns).writeTo(response.getOutputStream());
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.flushBuffer();
	}

	/**
	 * Download the Student Evaluation Report
	 * 
	 * @param reportType
	 * @param userId
	 * @param response
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws StudentDoesNotExistException
	 * @throws StudentCourseEvaluationDoesNotExistException
	 */
	@RequestMapping(value = DOWNLOAD_STUDENT_EVALUATION_REPORT, method = RequestMethod.GET)
	public void downloadEvaluationReport(
			@RequestParam(value = "reportType", required = true) String reportType,
			@RequestParam(value = "userId", required = false) String userId,
			HttpServletResponse response) throws IOException,
			NumberFormatException, StudentDoesNotExistException,
			StudentCourseEvaluationDoesNotExistException {
		Student student;
		if (userId == null) {
			student = userCastService.getUser().getStudent();
		} else {
			student = studentService.getStudentById(Long.valueOf(userId));
		}
		List<StudentEvaluationTransactionBean> studentEvaluationList = studentEvaluationService
				.getStudentEvaluationTransactionBeans(student.getId(), 0, 100);
		// TODO
		// change pagesize to fit all records

		List<ReportColumn> reportColumns = new ArrayList<ReportColumn>();
		ReportColumn column = new ReportColumn("Module Name", "moduleName",
				"string");
		column.setGroup(true);
		reportColumns.add(column);
		column = new ReportColumn("First Test Date", "firstDate", "string");
		reportColumns.add(column);
		column = new ReportColumn("Second Test Date", "secondDate", "string");
		reportColumns.add(column);
		column = new ReportColumn("First Score", "firstScore", "string");
		reportColumns.add(column);
		column = new ReportColumn("Second Score", "secondScore", "string");
		reportColumns.add(column);
		try {
			response.setContentType("application/" + reportType);
			response.setHeader("Content-Disposition",
					"attachment; filename=\"evaluation_report." + reportType
							+ "\"");
			reportService.buildReport(
					studentEvaluationList,
					new java.util.Date() + "\n"
							+ student.getUser().getFirstName() + " "
							+ student.getUser().getLastName()
							+ " Evaluation Report", reportType, reportColumns)
					.writeTo(response.getOutputStream());
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.flushBuffer();
	}

	/**
	 * Creates a report for the student progress
	 * 
	 * @param reportType
	 * @param userId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = STUDENT_PROGRESS_REPORT, method = RequestMethod.GET)
	public void studentProgressReport(
			@RequestParam(value = "reportType", required = true) String reportType,
			@RequestParam(value = "userId", required = true) String userId,
			HttpServletResponse response) throws Exception {

		response.setContentType("application/" + reportType);
		response.setHeader("Content-Disposition",
				"attachment; filename=\"user_report." + reportType + "\"");
		reportService.createStudentProgressReport(userId, reportType).writeTo(
				response.getOutputStream());

		response.flushBuffer();
	}

	@RequestMapping(value = USER_INFO_REPORT, method = RequestMethod.GET)
	public void userInfoReport(
			@RequestParam(value = "reportType", required = true) String reportType,
			@RequestParam(value = "userId", required = true) String userId,
			HttpServletResponse response) throws Exception {

		response.setContentType("application/" + reportType);
		response.setHeader("Content-Disposition",
				"attachment; filename=\"user_report." + reportType + "\"");
		reportService.createUserDetailsReport(userId, reportType).writeTo(
				response.getOutputStream());

		response.flushBuffer();
	}

}
