package com.centurylink.xprsr.action;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Map;
import java.util.TreeMap;

import com.centurylink.xprsr.service.IAssignTickets;
import com.centurylink.xprsr.service.impl.AssignTickets;
import com.centurylink.xprsr.service.impl.ReadXlService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Anurag Chowdhury
 * @author Prateek Gupta
 * @version 1.0
 * @since JUNE 2013
 */
@SuppressWarnings("serial")
public class AssignTicketsAction extends ActionSupport {

    IAssignTickets countTicketsSvc;

    private Map<String, Object> session = ActionContext.getContext()
            .getSession();

    TreeMap<Integer, ArrayList<String>> ticketsTable = new TreeMap<Integer, ArrayList<String>>();
    TreeMap<Integer, ArrayList<String>> assignedTicketsTable = new TreeMap<Integer, ArrayList<String>>();
    TreeMap<Integer, ArrayList<String>> restrictedTicketsList = new TreeMap<Integer, ArrayList<String>>();
    TreeMap<String, String> newTicketList = new TreeMap<String, String>();
    private String mailTo = null;
    private String cc = null;
    private String subject = null;
    private String body = null;

    Integer totalTickets = 0;
    Integer totalAssignedTickets = 0;
    Integer totalRestrictedTickets = 0;
    Integer totalPendingTickets = 0;

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getTotalAssignedTickets() {
        return totalAssignedTickets;
    }

    public Integer getTotalRestrictedTickets() {
        return totalRestrictedTickets;
    }

    public Integer getTotalPendingTickets() {
        return totalPendingTickets;
    }

    public void setTotalAssignedTickets(Integer totalAssignedTickets) {
        this.totalAssignedTickets = totalAssignedTickets;
    }

    public void setTotalPendingTickets(Integer totalPendingTickets) {
        this.totalPendingTickets = totalPendingTickets;
    }

    public TreeMap<Integer, ArrayList<String>> getAssignedTable() {
        return assignedTicketsTable;
    }

    public void setAssignedTable(
            TreeMap<Integer, ArrayList<String>> assignedTicketsTable) {
        this.assignedTicketsTable = assignedTicketsTable;
    }

    public TreeMap<String, String> getNewTicketList() {
        return newTicketList;
    }

    public void setNewTicketList(TreeMap<String, String> newTicketList) {
        this.newTicketList = newTicketList;
    }

    public TreeMap<Integer, ArrayList<String>> getTable() {
        return ticketsTable;
    }

    public void setTable(TreeMap<Integer, ArrayList<String>> ticketsTable) {
        this.ticketsTable = ticketsTable;
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

    private void crushTreeMap(TreeMap<Integer, ArrayList<String>> mail) {
        mailTo = new String();
        cc = new String();
        subject = new String();
        body = new String();

        mailTo = mail.get(0).get(1);
        cc = mail.get(1).get(1);
        subject = mail.get(2).get(1);
        body = mail.get(3).get(1);
    }

    /**
     * On "success", navigates to autoAssignedXl.jsp
     * 
     * @see AssignTickets#countTickets(TreeMap, TreeMap, TreeMap)
     * @see ReadXlService#read(String, Integer)
     * @see AssignTickets#getNames(TreeMap)
     */
    @SuppressWarnings("unchecked")
    public String execute() {
        countTicketsSvc = new AssignTickets();

        try {
            ticketsTable = countTicketsSvc.countTickets(
                    (TreeMap<Integer, ArrayList<String>>) session
                            .get("sheetDATA"),
                    (TreeMap<Integer, ArrayList<String>>) session
                            .get("sheetTHRESHOLD"),
                    (TreeMap<Integer, ArrayList<String>>) session
                            .get("sheetCATEGORIES"),
                    (TreeMap<Integer, ArrayList<String>>) session
                            .get("sheetTICKET_STATUS"),
                    (TreeMap<Integer, ArrayList<String>>) session
                            .get("sheetRESTRICTED_KEYWORDS"));

            newTicketList = countTicketsSvc.newTicketList();
            restrictedTicketsList = countTicketsSvc.restrictedTicketList();

            if (restrictedTicketsList != null)
                totalRestrictedTickets = restrictedTicketsList.size();

            for (Entry<Integer, ArrayList<String>> tableEntry : ticketsTable
                    .entrySet()) {
                if (tableEntry.getKey() != 0) {
                    totalTickets++;
                    if (!tableEntry.getValue().get(2).equalsIgnoreCase(" ")) {
                        if (!tableEntry.getValue().get(3)
                                .equalsIgnoreCase("Pending")) {
                            assignedTicketsTable.put(tableEntry.getKey(),
                                    tableEntry.getValue());
                            totalAssignedTickets++;
                        } else
                            totalPendingTickets++;
                    }
                } else
                    assignedTicketsTable.put(tableEntry.getKey(),
                            tableEntry.getValue());
            }
            crushTreeMap((TreeMap<Integer, ArrayList<String>>) session
                    .get("sheetMAIL"));
            return "success";
        }

        catch (ClassCastException e) {
            System.out.println("Cannot cast objects to desired Class!");
            return "error";
        }
    }
}
