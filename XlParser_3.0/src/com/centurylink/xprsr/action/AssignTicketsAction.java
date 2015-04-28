package com.centurylink.xprsr.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Properties;
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
public class AssignTicketsAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    IAssignTickets countTickets;
    TreeMap<Integer, ArrayList<String>> tableData = new TreeMap<Integer, ArrayList<String>>();
    TreeMap<Integer, ArrayList<String>> escalatedTickets = new TreeMap<Integer, ArrayList<String>>();

    @SuppressWarnings("unchecked")
    TreeMap<Integer, ArrayList<String>> parsedSheet = (TreeMap<Integer, ArrayList<String>>) ServletActionContext
            .getRequest().getSession().getAttribute("parsedSheet");

    public static String inputFilePath = (String) ServletActionContext
            .getRequest().getSession().getAttribute("inputFilePath");
    IReadXlService readXml = new ReadXlService();
    TreeMap<String, Integer[]> currentCount;
    TreeMap<Integer, ArrayList<String>> table = new TreeMap<Integer, ArrayList<String>>();
    TreeMap<Integer, ArrayList<String>> assignedTable = new TreeMap<Integer, ArrayList<String>>();
    TreeMap<Integer, ArrayList<String>> restrictedTicketsList = new TreeMap<Integer, ArrayList<String>>();
    TreeMap<String, String> newTicketList = new TreeMap<String, String>();
    ArrayList<String> nameList = new ArrayList<String>();
    Integer totalTickets = 0;
    Integer totalAssignedTickets = 0;
    Integer totalRestrictedTickets = 0;
    Integer totalAssignedEscalatedTickets = 0;
    Integer totalPendingEscalatedTickets = 0;
    Integer totalPendingTickets = 0;

    String mailTo = null;
    String cc = null;
    String subject = null;
    String body = null;

    public TreeMap<Integer, ArrayList<String>> getEscalatedTickets() {
        return escalatedTickets;
    }

    public void setEscalatedTickets(
            TreeMap<Integer, ArrayList<String>> escalatedTickets) {
        this.escalatedTickets = escalatedTickets;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getTotalAssignedTickets() {
        return totalAssignedTickets;
    }

    public Integer getTotalAssignedEscalatedTickets() {
        return totalAssignedEscalatedTickets;
    }

    public Integer getTotalPendingEscalatedTickets() {
        return totalPendingEscalatedTickets;
    }

    public Integer getTotalRestrictedTickets() {
        return totalRestrictedTickets;
    }

    public Integer getTotalPendingTickets() {
        return totalPendingTickets;
    }

    public void setTotalAssignedEscalatedTickets(Integer totalAssignedEscalatedTickets) {
        this.totalAssignedEscalatedTickets = totalAssignedEscalatedTickets;
    }

    public void setTotalPendingEscalatedTickets(Integer totalPendingEscalatedTickets) {
        this.totalPendingEscalatedTickets = totalPendingEscalatedTickets;
    }

    public void setTotalAssignedTickets(Integer totalAssignedTickets) {
        this.totalAssignedTickets = totalAssignedTickets;
    }

    public void setTotalPendingTickets(Integer totalPendingTickets) {
        this.totalPendingTickets = totalPendingTickets;
    }

    public TreeMap<Integer, ArrayList<String>> getAssignedTable() {
        return assignedTable;
    }

    public void setAssignedTable(
            TreeMap<Integer, ArrayList<String>> assignedTable) {
        this.assignedTable = assignedTable;
    }

    public TreeMap<String, String> getNewTicketList() {
        return newTicketList;
    }

    public void setNewTicketList(TreeMap<String, String> newTicketList) {
        this.newTicketList = newTicketList;
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public void setNameList(ArrayList<String> nameList) {
        this.nameList = nameList;
    }

    public TreeMap<Integer, ArrayList<String>> getTable() {
        return table;
    }

    public void setTable(TreeMap<Integer, ArrayList<String>> table) {
        this.table = table;
    }

    public TreeMap<String, Integer[]> getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(TreeMap<String, Integer[]> currentCount) {
        this.currentCount = currentCount;
    }

    public TreeMap<Integer, ArrayList<String>> getParsedSheet() {
        return parsedSheet;
    }

    public void setParsedSheet(TreeMap<Integer, ArrayList<String>> parsedSheet) {
        this.parsedSheet = parsedSheet;
    }

    public String getMailTo() {
        return mailTo;
    }

    public String getCc() {
        return cc;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    /**
     * On "success", navigates to autoAssignedXl.jsp
     * 
     * @see AssignTickets#countTickets(TreeMap, TreeMap, TreeMap)
     * @see ReadXlService#read(String, Integer)
     * @see AssignTickets#getNames(TreeMap)
     */
    public String ticketCounter() {
        countTickets = new AssignTickets();
        IReadXlService readXl = new ReadXlService();
        String defaultPath = null;

        Properties prop = new Properties();
        FileInputStream fis = null;

        try {

            String propertyHome = System.getenv("CATALINA_HOME");
            if (null == propertyHome) {
                propertyHome = System.getProperty("PROPERTY_HOME");
            }

            String filePath = propertyHome + "\\properties\\config.properties";
            fis = new FileInputStream(filePath);
            prop.load(fis);
            defaultPath = prop.getProperty("defaultPath").toString();
            if (inputFilePath == null || inputFilePath.length() == 0
                    || !inputFilePath.contains(":"))
                inputFilePath = defaultPath;

            tableData = readXl.read(inputFilePath, "DATA");

        } catch (IOException ex) {
            System.out.println("Xcel file path not found! " + ex.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            table = countTickets.countTickets(parsedSheet,
                    readXml.read(inputFilePath, "THRESHOLD"),
                    readXml.read(inputFilePath, "CATEGORIES"),
                    readXml.read(inputFilePath, "TICKET_STATUS"),
                    readXml.read(inputFilePath, "RESTRICTED_KEYWORDS"));

            escalatedTickets = readXl.readEscalatedTickets(table);
            restrictedTicketsList = readXml.read(inputFilePath,
                    "RESTRICTED_TICKETS");
            nameList = countTickets.getNames(readXml.read(inputFilePath,
                    "THRESHOLD"));
            
            for (Entry<Integer, ArrayList<String>> escTktEntry : escalatedTickets
                    .entrySet()) {
                if (escTktEntry.getKey() != 0) {
                    if (!escTktEntry.getValue().get(2).equalsIgnoreCase(" ")) {
                        if (!escTktEntry.getValue().get(3)
                                .equalsIgnoreCase("Pending"))
                            totalAssignedEscalatedTickets++;
                        else
                            totalPendingEscalatedTickets++;
                    }
                }
            }

            totalRestrictedTickets = restrictedTicketsList.size();
            totalAssignedEscalatedTickets = (escalatedTickets.size() - 1);
            totalAssignedTickets = totalAssignedTickets + totalAssignedEscalatedTickets;
            totalPendingTickets = totalPendingTickets + totalPendingEscalatedTickets;

            for (Entry<Integer, ArrayList<String>> tableEntry : table
                    .entrySet()) {
                if (tableEntry.getKey() != 0) {
                    totalTickets++;
                    if (!tableEntry.getValue().get(2).equalsIgnoreCase(" ")) {
                        if (!tableEntry.getValue().get(7).equalsIgnoreCase("3")) {
                            if (!tableEntry.getValue().get(3)
                                    .equalsIgnoreCase("Pending")) {
                                assignedTable.put(tableEntry.getKey(),
                                        tableEntry.getValue());
                                totalAssignedTickets++;
                            } else
                                totalPendingTickets++;
                        }
                    }
                } else
                    assignedTable.put(tableEntry.getKey(),
                            tableEntry.getValue());
            }

            crushTreeMap(readXml.read(inputFilePath, "MAIL"));

            newTicketList = countTickets.newTicketList();
            return "success";
        }

        catch (IOException e) {
            System.out.println("Cannot find file!");
            return "error";
        }
    }

    public void crushTreeMap(TreeMap<Integer, ArrayList<String>> mail) {
        mailTo = mail.get(0).get(1);
        cc = mail.get(1).get(1);
        subject = mail.get(2).get(1);
        body = mail.get(3).get(1);
    }
}
