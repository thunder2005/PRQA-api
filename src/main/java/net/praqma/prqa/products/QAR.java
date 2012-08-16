/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.prqa.PRQAContext.QARReportType;
import net.praqma.prqa.logging.Config;
import net.praqma.util.execute.CmdResult;

/**
 * Reporting class.
 *
 * @author jes
 */
public class QAR extends PRQA {

	private String reportOutputPath;
	private String projectFile;
	private String product;
	private PRQACommandBuilder builder;
	private QARReportType type;
	public static final String QAW_WRAPPER = "qaw";
	private static final Logger logger;

	/**
	 * QAR is invoked using QAW where this is taken as parameter in the QAW command.
	 *
	 */
	static {
		logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
	}

	public QAR() {
		logger.finest(String.format("Constructor called for class QAR()"));

		builder = new PRQACommandBuilder(QAR.QAW_WRAPPER);

		logger.finest(String.format("Ending execution of constructor - QAR()"));
	}

	public QAR(String product, String projectFile, QARReportType type) {
		logger.finest(String.format("Constructor called for class QAR(String product, String projectFile, QARReportType type)"));
		logger.finest(String.format("Input parameter product type: %s; value: %s", product.getClass(), product));
		logger.finest(String.format("Input parameter projectFile type: %s; value: %s", projectFile.getClass(), projectFile));
		logger.finest(String.format("Input parameter type type: %s; value: %s", type.getClass(), type));

		this.builder = new PRQACommandBuilder(QAR.QAW_WRAPPER);
		this.product = product;
		this.projectFile = projectFile;
		this.type = type;

		logger.finest(String.format("Ending execution of constructor - QAR(String product, String projectFile, QARReportType type)"));
	}

	public PRQACommandBuilder getBuilder() {
		logger.finest(String.format("Starting execution of method - getBuilder"));
		logger.finest(String.format("Returning value: %s", builder));

		return builder;
	}

	@Override
	public CmdResult execute() {
		logger.finest(String.format("Starting execution of method - execute()"));

		CmdResult output = PRQACommandLineUtility.run(getBuilder().getCommand(), new File(commandBase));
		
		logger.finest(String.format("Returning value: %s", output));
		
		return output;
	}

	public void setReportOutputPath(String reportOutputPath) {
		logger.finest(String.format("Starting execution of method - setReportOutputPath"));
		logger.finest(String.format("Input parameter reportOutputPath type: %s; value: %s", reportOutputPath.getClass(), reportOutputPath));

		this.reportOutputPath = reportOutputPath;

		logger.finest(String.format("Ending execution of method - setReportOutputPath"));
	}

	public String getReportOutputPath() {
		logger.finest(String.format("Starting execution of method - getReportOutputPath"));
		logger.finest(String.format("Returning value: %s", this.reportOutputPath));

		return this.reportOutputPath;
	}

	public String getProduct() {
		logger.finest(String.format("Starting execution of method - getProduct"));
		logger.finest(String.format("Returning value: %s", this.product));

		return this.product;
	}

	public void setProduct(String product) {
		logger.finest(String.format("Starting execution of method - setProduct"));
		logger.finest(String.format("Input parameter product type: %s; value: %s", product.getClass(), product));

		this.product = product;

		logger.finest(String.format("Ending execution of method - setProduct"));
	}

	public String getProjectFile() {
		logger.finest(String.format("Starting execution of method - getProjectFile"));
		logger.finest(String.format("Returning value: %s", this.projectFile));

		return this.projectFile;
	}

	public void setProjectFile(String projectFile) {
		logger.finest(String.format("Starting execution of method - setProjectFile"));
		logger.finest(String.format("Input parameter projectFile type: %s; value: %s", projectFile.getClass(), projectFile));

		this.projectFile = projectFile;

		logger.finest(String.format("Ending execution of method - setProjectFile"));
	}

	@Override
	public String toString() {
		String out = "";
		out += "QAR selected project file:\t" + this.projectFile + System.getProperty("line.separator");
		out += "QAR selected product:\t\t" + this.product + System.getProperty("line.separator");
		out += "QAR selected report type:\t" + this.type + System.getProperty("line.separator");

		return out;
	}

	/**
	 * @return the type
	 */
	public QARReportType getType() {
		logger.finest(String.format("Starting execution of method - getType"));
		logger.finest(String.format("Returning value: %s", type));

		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(QARReportType type) {
		logger.finest(String.format("Starting execution of method - setType"));
		logger.finest(String.format("Input parameter type type: %s; value: %s", type.getClass(), type));

		this.type = type;

		logger.finest(String.format("Ending execution of method - setType"));
	}
}
