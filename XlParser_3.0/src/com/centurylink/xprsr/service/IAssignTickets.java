package com.centurylink.xprsr.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import com.centurylink.xprsr.service.impl.SortAssignment;

public interface IAssignTickets {

    TreeMap<Integer, ArrayList<String>> countTickets(
            TreeMap<Integer, ArrayList<String>> dataSheet,
            TreeMap<Integer, ArrayList<String>> userThreshold,
            TreeMap<Integer, ArrayList<String>> typeOfTickets,
            TreeMap<Integer, ArrayList<String>> ticketStatus,
            TreeMap<Integer, ArrayList<String>> restrictedKeywords) throws IOException;

    TreeMap<Integer, ArrayList<String>> assignTo(
            TreeMap<Integer, ArrayList<String>> inputFile,
            SortAssignment[] sortAssignmentList, ArrayList<String> tempTTArr,
            ArrayList<String> resKeywordList);

    TreeMap<String, String> newTicketList();
    
    ArrayList<String> getNames(
            TreeMap<Integer, ArrayList<String>> assignedTickets);

    ArrayList<String> getStati(
            TreeMap<Integer, ArrayList<String>> assignedTickets);

    TreeMap<Integer, ArrayList<String>> reAssignTo(
            TreeMap<Integer, ArrayList<String>> theParsedSheet,
            String theElement, String button);

    TreeMap<Integer, ArrayList<String>> changeStatus(
            TreeMap<Integer, ArrayList<String>> theParsedSheet,
            String theElement, String button);

    boolean containsResKeywordList(String titleList,
            ArrayList<String> restrictedKeywords);

    boolean ticketsAvailableToAssign(
            TreeMap<Integer, ArrayList<String>> dataSheet,
            ArrayList<String> restrictedKeywords);

}
