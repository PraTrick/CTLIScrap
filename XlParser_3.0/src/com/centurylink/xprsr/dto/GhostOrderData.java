package com.centurylink.xprsr.dto;

public class GhostOrderData {
    private String type;
    private String caseID;
    private String individual;
    private String orderID;
    
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
    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }    
}
