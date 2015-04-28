package com.centurylink.xprsr.dto;

public class ValidationData {

    private String IR;
    private String assignedTo;
    private String issueStatus;
    private String assignedDate;
    private String issueSubStatus;
    private String resolutionDate;
    private Double TAT;
    private Double actualTime;
    private Integer WRNo;
    
    public String getIR() {
        return IR;
    }
    public void setIR(String iR) {
        IR = iR;
    }
    public String getAssignedTo() {
        return assignedTo;
    }
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
    public String getIssueStatus() {
        return issueStatus;
    }
    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }
    public String getAssignedDate() {
        return assignedDate;
    }
    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }
    public String getIssueSubStatus() {
        return issueSubStatus;
    }
    public void setIssueSubStatus(String issueSubStatus) {
        this.issueSubStatus = issueSubStatus;
    }
    public String getResolutionDate() {
        return resolutionDate;
    }
    public void setResolutionDate(String resolutionDate) {
        this.resolutionDate = resolutionDate;
    }
    public Double getTAT() {
        return TAT;
    }
    public void setTAT(Double tAT) {
        TAT = tAT;
    }

    public Double getActualTime() {
        return actualTime;
    }
    public void setActualTime(Double actualTime) {
        this.actualTime = actualTime;
    }
    public Integer getWRNo() {
        return WRNo;
    }
    public void setWRNo(Integer wRNo) {
        WRNo = wRNo;
    } 
}
