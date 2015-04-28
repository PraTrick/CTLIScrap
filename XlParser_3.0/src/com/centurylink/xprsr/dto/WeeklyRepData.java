package com.centurylink.xprsr.dto;

public class WeeklyRepData {

    private String name;
    private Integer underWork;
    private Integer closed;
    private Integer withdrawn;
    private Integer grandTotalResolved;
    private Double TAT;
    private Double actualTime;
    private String TATPerTicket;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnderWork() {
        return underWork;
    }

    public void setUnderWork(Integer underWork) {
        this.underWork = underWork;
    }

    public Integer getClosed() {
        return closed;
    }

    public void setClosed(Integer closed) {
        this.closed = closed;
    }

    public Integer getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(Integer withdrawn) {
        this.withdrawn = withdrawn;
    }

    public Integer getGrandTotalResolved() {
        return grandTotalResolved;
    }

    public void setGrandTotalResolved(Integer grandTotalResolved) {
        this.grandTotalResolved = grandTotalResolved;
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

    public String getTATPerTicket() {
        return TATPerTicket;
    }

    public void setTATPerTicket(String tATPerTicket) {
        TATPerTicket = tATPerTicket;
    }
}
