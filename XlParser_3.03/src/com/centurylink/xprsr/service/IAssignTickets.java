package com.centurylink.xprsr.service;

import java.util.ArrayList;
import java.util.TreeMap;

public interface IAssignTickets {

    TreeMap<Integer, ArrayList<String>> countTickets(
            TreeMap<Integer, ArrayList<String>> dataSheet,
            TreeMap<Integer, ArrayList<String>> userThreshold,
            TreeMap<Integer, ArrayList<String>> typeOfTickets,
            TreeMap<Integer, ArrayList<String>> ticketStatus,
            TreeMap<Integer, ArrayList<String>> restrictedKeywords);

    TreeMap<String, String> newTicketList();

    TreeMap<Integer, ArrayList<String>> restrictedTicketList();
}
