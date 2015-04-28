package com.centurylink.xprsr.dto;

import java.util.Comparator;

public class TicketData {

    private String type;
    private String caseID;
    private String individual;
    private String status;
    private String createTime;
    private String summary;
    private String item;
    private String severity;
    private String group;
    public static final Comparator<TicketData> BY_NAME = new ByNameComparator();
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(String caseID) {
        this.caseID = caseID;
    }

    public String getIndividual() {
        return individual;
    }

    public void setIndividual(String individual) {
        this.individual = individual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    private static class ByNameComparator implements Comparator<TicketData> {
        @Override
        public int compare(TicketData o1, TicketData o2) {
            if (o1.getIndividual().compareTo(o2.getIndividual()) == 0)
                return o1.getCaseID().compareTo(o2.getCaseID());
            else
                return o1.getIndividual().compareTo(o2.getIndividual());
        }
    }
}
