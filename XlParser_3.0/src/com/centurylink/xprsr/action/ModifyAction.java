package com.centurylink.xprsr.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import org.apache.struts2.ServletActionContext;

import com.centurylink.xprsr.service.IAssignTickets;
import com.centurylink.xprsr.service.IReadXlService;
import com.centurylink.xprsr.service.impl.AssignTickets;
import com.centurylink.xprsr.service.impl.ReadXlService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Anurag Chowdhury
 * @author Prateek Gupta
 * @version 1.0
 * @since JUNE 2013
 */
public class ModifyAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    
    String button;
    String theElement;
    IAssignTickets countTickets;
    TreeMap<Integer, ArrayList<String>> modifiedTable;
    
    @SuppressWarnings("unchecked")
    TreeMap<Integer, ArrayList<String>> theParsedSheet = (TreeMap<Integer, ArrayList<String>>) ServletActionContext
            .getRequest().getSession().getAttribute("parsedSheet");
    
    public static String inputFilePath = (String) ServletActionContext
            .getRequest().getSession().getAttribute("inputFilePath");
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<String> statusList = new ArrayList<String>();
    IReadXlService readXml = new ReadXlService();

    public ArrayList<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(ArrayList<String> statusList) {
        this.statusList = statusList;
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public void setNameList(ArrayList<String> nameList) {
        this.nameList = nameList;
    }

    public IAssignTickets getCountTickets() {
        return countTickets;
    }

    public void setCountTickets(IAssignTickets countTickets) {
        this.countTickets = countTickets;
    }

    public TreeMap<Integer, ArrayList<String>> getModifiedTable() {
        return modifiedTable;
    }

    public void setModifiedTable(
            TreeMap<Integer, ArrayList<String>> modifiedTable) {
        this.modifiedTable = modifiedTable;
    }

    public String getTheElement() {
        return theElement;
    }

    public void setTheElement(String theElement) {
        this.theElement = theElement;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    /**
     * On "success", navigates to manualAssignedXl.jsp
     * 
     * @see AssignTickets#reAssignTo(TreeMap, String, String)
     * @see AssignTickets#getNames(TreeMap)
     * @see AssignTickets#getStati(TreeMap)
     */
    public String execute() {
        countTickets = new AssignTickets();
        IAssignTickets modifyAssignment = new AssignTickets();

        modifiedTable = modifyAssignment.reAssignTo(theParsedSheet, theElement,
                button);
        ServletActionContext.getRequest().getSession()
                .setAttribute("assignedTable", modifiedTable);
        try {
            nameList = countTickets.getNames(readXml.read(inputFilePath, "THRESHOLD"));
            statusList = countTickets.getStati(readXml.read(inputFilePath, "WORK_STATUS"));
            return "success";
        } catch (IOException e) {
            System.out.println("Cannot find file!");
            return "error";
        }

    }

    /**
     * On "success", navigates to manualAssignedXl.jsp
     * 
     * @see AssignTickets#changeStatus(TreeMap, String, String)
     * @see AssignTickets#getNames(TreeMap)
     * @see AssignTickets#getStati(TreeMap)
     */
    public String modifyStatus() {
        countTickets = new AssignTickets();
        IAssignTickets modifyAssignment = new AssignTickets();

        modifiedTable = modifyAssignment.changeStatus(theParsedSheet,
                theElement, button);
        ServletActionContext.getRequest().getSession()
                .setAttribute("assignedTable", modifiedTable);
        try {
            statusList = countTickets.getStati(readXml.read(inputFilePath, "WORK_STATUS"));
            nameList = countTickets.getNames(readXml.read(inputFilePath, "THRESHOLD"));
            return "success";
        } catch (IOException e) {
            System.out.println("Cannot find file!");
            return "error";
        }

    }

}
