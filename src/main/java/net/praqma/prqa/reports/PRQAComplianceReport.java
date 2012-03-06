/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.reports;

import java.io.File;
import java.util.List;
import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.prqa.PRQAComplianceStatus;
import net.praqma.prqa.PRQAContext.QARReportType;
import net.praqma.prqa.PRQAReportingTask;
import net.praqma.prqa.parsers.ComplianceReportHtmlParser;
import net.praqma.prqa.parsers.ReportHtmlParser;
import net.praqma.prqa.products.PRQACommandBuilder;
import net.praqma.prqa.products.QAR;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLineException;

/**
 *
 * @author Praqma
 */
public class PRQAComplianceReport<T extends PRQAComplianceStatus, K extends String> extends PRQAReport implements PRQAReportingTask<T,K> {

    private QARReportType type = QARReportType.Compliance;
    private QAR qar;
    
    /**
     * A compliance report. Takes a command line wrapper for Programming Research QAR tool. Each report must implement their own parser.
     * 
     * @param qar 
     */
    public PRQAComplianceReport(QAR qar) {
        this.qar = qar;
        this.parser = new ComplianceReportHtmlParser();
    }
   
    public PRQAComplianceReport() {
        this.parser = new ComplianceReportHtmlParser();
    }

    public PRQAComplianceReport(ReportHtmlParser parser) {
        this.parser = parser;
    }
   
    /**
     * Completes the job of creating usable data given a path to a report generated by QAR. This is the Compliance report
     * @param reportpath
     * @return 
     */
    public T completeTask(K reportpath) throws PrqaException {
        CmdResult res = null;
        try {
            res = qar.execute();
        } catch (AbnormalProcessTerminationException ex) {
            //TODO: Remove the commented lines below when we go live.
            throw new PrqaException.PrqaCommandLineException(qar,ex);            
        } catch (CommandLineException cle) {      
            //TODO:Remove the commented lines below when we go live.
            throw new PrqaException.PrqaCommandLineException(qar,cle);            
        }
        
        //Parse it.
        PRQAComplianceStatus stat = new PRQAComplianceStatus();
        
        List<String> parseResult = parser.parse(getFullReportPath(), ComplianceReportHtmlParser.totalMessagesPattern);
        if(!parseResult.isEmpty()) {
            Integer tmp = Integer.parseInt(parseResult.get(0)); 
            stat.setMessages(tmp);
        }

        parseResult = parser.parse(getFullReportPath(), ComplianceReportHtmlParser.fileCompliancePattern);
        if(!parseResult.isEmpty()) {
            Double tmp = Double.parseDouble(parseResult.get(0));
            stat.setFileCompliance(tmp);
        }
        parseResult = parser.parse(getFullReportPath(), ComplianceReportHtmlParser.projectCompliancePattern);
        if(!parseResult.isEmpty()) {
            Double tmp = Double.parseDouble(parseResult.get(0));
            stat.setProjectCompliance(tmp);
        }
        
        return (T)stat;
    }
    
    /**
     * Completes the job of creating usable data given a path to a report generated by QAR. This is the Compliance report
     * @param reportpath
     * @return A result specified in the template. 
     */
    public T completeTask() throws PrqaException {
        return completeTask((K)getFullReportPath());
    }
    
    /**
     * 
     * @return A string representing the final path on which we have decided to place the report file generated by QAR.
     */
    public String getFullReportPath() {        
        return qar.getReportOutputPath()+File.separator+getNamingTemplate();
    }
    
    /**
     * QAR Reports seem to follow the naming convention of this kind.
     * @return A string representing the actual filename generated by the QAR reporting tool. 
     */
    public static String getNamingTemplate() {
        return QARReportType.Compliance.toString()+" "+PRQAReport.XHTML_REPORT_EXTENSION;
    }
    
    public static String getNamingTemplate(String extension) {
        return QARReportType.Compliance.toString()+" "+extension;
    }
}
