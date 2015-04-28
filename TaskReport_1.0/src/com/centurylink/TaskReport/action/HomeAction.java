package com.centurylink.TaskReport.action;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.centurylink.TaskReport.dto.SensitivityReportData;
import com.opensymphony.xwork2.ActionContext;

public class HomeAction {

    private Collection<SensitivityReportData> row;
    private List<File> fileUpload;
    private List<String> fileUploadContentType;
    private List<String> fileUploadFileName;
    private String fromDate;
    private String toDate;
    private Map<String, Object> session = ActionContext.getContext()
            .getSession();

    public Collection<SensitivityReportData> getRow() {
        return row;
    }

    public void setRow(Collection<SensitivityReportData> row) {
        this.row = row;
    }

    public List<File> getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(List<File> fileUpload) {
        this.fileUpload = fileUpload;
    }

    public List<String> getFileUploadContentType() {
        return fileUploadContentType;
    }

    public void setFileUploadContentType(List<String> fileUploadContentType) {
        this.fileUploadContentType = fileUploadContentType;
    }

    public List<String> getFileUploadFileName() {
        return fileUploadFileName;
    }

    public void setFileUploadFileName(List<String> fileUploadFileName) {
        this.fileUploadFileName = fileUploadFileName;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
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

    public String execute() {
        SummaryReportAction detailedSummaryReportMain = new SummaryReportAction();
        detailedSummaryReportMain.generateReport(fileUpload, row, toDate, fromDate);

        return "success";
    }
}
