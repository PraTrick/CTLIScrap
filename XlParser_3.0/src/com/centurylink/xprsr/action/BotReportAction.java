package com.centurylink.xprsr.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.struts2.ServletActionContext;

import com.centurylink.xprsr.dto.BotRepData;
import com.centurylink.xprsr.dto.ValidationData;
import com.centurylink.xprsr.service.IReadXlService;
import com.centurylink.xprsr.service.IReportService;
import com.centurylink.xprsr.service.impl.ReadXlService;
import com.centurylink.xprsr.service.impl.ReportService;
import com.opensymphony.xwork2.ActionSupport;

public class BotReportAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private String browseFile = null;
    private String fromDate = null;
    private String toDate = null;
    private String mailTo = null;
    private String cc = null;
    private String subject = null;
    private String body = null;
    String error;
    String flag = null;
    ArrayList<BotRepData> portalDataList = new ArrayList<BotRepData>();
    ArrayList<ValidationData> portalStatusMismatchList = new ArrayList<ValidationData>();
    ArrayList<ValidationData> portalMissingResolutionDateList = new ArrayList<ValidationData>();
    ArrayList<ValidationData> portalMissingTATList = new ArrayList<ValidationData>();
    ArrayList<ValidationData> portalZeroWRNo = new ArrayList<ValidationData>();
    ArrayList<ValidationData> portalRemedyCreatedResolved = new ArrayList<ValidationData>();   
    
    
    public ArrayList<BotRepData> getPortalDataList() {
        return portalDataList;
    }

    public void setPortalDataList(ArrayList<BotRepData> portalDataList) {
        this.portalDataList = portalDataList;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getBrowseFile() {
        return browseFile;

    }

    public void setBrowseFile(String browseFile) {
        this.browseFile = browseFile;
    }


  

    public ArrayList<ValidationData> getPortalStatusMismatchList() {
        return portalStatusMismatchList;
    }

    public void setPortalStatusMismatchList(
            ArrayList<ValidationData> portalStatusMismatchList) {
        this.portalStatusMismatchList = portalStatusMismatchList;
    }

    public ArrayList<ValidationData> getPortalMissingResolutionDateList() {
        return portalMissingResolutionDateList;
    }

    public void setPortalMissingResolutionDateList(
            ArrayList<ValidationData> portalMissingResolutionDateList) {
        this.portalMissingResolutionDateList = portalMissingResolutionDateList;
    }

    public ArrayList<ValidationData> getPortalMissingTATList() {
        return portalMissingTATList;
    }

    public void setPortalMissingTATList(
            ArrayList<ValidationData> portalMissingTATList) {
        this.portalMissingTATList = portalMissingTATList;
    }

    public ArrayList<ValidationData> getPortalZeroWRNo() {
        return portalZeroWRNo;
    }

    public void setPortalZeroWRNo(ArrayList<ValidationData> portalZeroWRNo) {
        this.portalZeroWRNo = portalZeroWRNo;
    }

    public ArrayList<ValidationData> getPortalRemedyCreatedResolved() {
        return portalRemedyCreatedResolved;
    }

    public void setPortalRemedyCreatedResolved(
            ArrayList<ValidationData> portalRemedyCreatedResolved) {
        this.portalRemedyCreatedResolved = portalRemedyCreatedResolved;
    }
        
    public String isFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String validatePortal() {
        IReadXlService readXl = new ReadXlService();
        ServletActionContext.getRequest().getSession()
                .setAttribute("inputFilePath", browseFile);

        try {

            if (browseFile.length() <= 4) {
                throw new NullPointerException("No Location Provided");
            } else if (!browseFile.substring(browseFile.length() - 4,
                    browseFile.length()).equalsIgnoreCase("xlsx")) {
                throw new InvalidFormatException(
                        "Invalid File Format (Only .xlsx is supported)");
            }

            IReportService weeklyReport = new ReportService();
            portalStatusMismatchList = weeklyReport.getStatusMismatch(
                    readXl.read(browseFile, "owssvr"),
                    readXl.read(browseFile, "IssueStatus"));
            portalMissingResolutionDateList = weeklyReport
                    .getMissingResolutionDate(
                            readXl.read(browseFile, "owssvr"),
                            readXl.read(browseFile, "IssueStatus"));
            portalMissingTATList = weeklyReport.getMissingTAT(
                    readXl.read(browseFile, "owssvr"),
                    readXl.read(browseFile, "IssueStatus"));
            portalZeroWRNo = weeklyReport.getZeroWRNo(readXl.read(browseFile,
                    "owssvr"));
            portalRemedyCreatedResolved = weeklyReport
                    .getRemedyCreatedResolved(readXl.read(browseFile, "owssvr"));
            
            if (!portalStatusMismatchList.isEmpty()
                    || !portalMissingResolutionDateList.isEmpty()
                    || !portalMissingTATList.isEmpty()
                    || !portalZeroWRNo.isEmpty()
                    || !portalRemedyCreatedResolved.isEmpty())
                flag = "Something is coming!!";
            crushTreeMap(readXl.read(browseFile, "Mail"));
            
        } catch (NullPointerException e) {
            System.out.println(portalRemedyCreatedResolved.toString());
            error = "Excel Parsing Failed!! -- Null Pointer Exception occurred ";
            return "error";
        }

        catch (InvalidFormatException e) {
            error = "Invalid FileFormat";
            return "error";
        }

        catch (IOException e) {
            error = "File does not exist or Invalid File";
            return "error";
        }

        return "success";

    }

    public String botReport() {
        IReadXlService readXl = new ReadXlService();
        ServletActionContext.getRequest().getSession()
                .setAttribute("inputFilePath", browseFile);

        try {

            if (browseFile.length() <= 4) {
                throw new NullPointerException("No Location Provided");
            } else if (!browseFile.substring(browseFile.length() - 4,
                    browseFile.length()).equalsIgnoreCase("xlsx")) {
                throw new InvalidFormatException(
                        "Invalid File Format (Only .xlsx is supported)");
            }

            IReportService botReport = new ReportService();
            portalDataList = botReport.getBotReport(
                    readXl.read(browseFile, "owssvr"),
                    readXl.read(browseFile, "Name"), fromDate, toDate);
            crushTreeMap(readXl.read(browseFile, "MailReport"));

        } catch (NullPointerException e) {
            error = "Excel Parsing Failed!! -- Null Pointer Exception occurred ";
            return "error";
        }

        catch (InvalidFormatException e) {
            error = "Invalid FileFormat";
            return "error";
        }

        catch (IOException e) {
            error = "File does not exist or Invalid File";
            return "error";
        }

        return "success";
    }

    public void crushTreeMap(TreeMap<Integer, ArrayList<String>> mail) {
        mailTo = mail.get(0).get(1);
        cc = mail.get(1).get(1);
        subject = mail.get(2).get(1);
        body = mail.get(3).get(1);
    }

}
