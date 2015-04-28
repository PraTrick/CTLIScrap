package com.centurylink.xprsr.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.centurylink.xprsr.dto.TicketData;
import com.centurylink.xprsr.service.IAssignTickets;

/**
 * @author Anurag Chowdhury
 * @author Prateek Gupta
 * @version 1.0
 * @since JUNE 2013
 */
public class AssignTickets implements IAssignTickets {

    /**
     * 1) Counts the number of tickets assigned to each member of the team.<br>
     * 2) Sorts the team members in decreasing order of tickets to be assigned.<br>
     * 3) Assigns the requisite tickets
     * 
     * @see SortAssignment#sort(Object[], Comparator)
     * 
     * @see AssignTickets#assignTo(TreeMap, SortAssignment[], ArrayList)
     * 
     * @param dataSheet
     *            The "DATA" sheet of the excel workbook
     * @param userThreshold
     *            The max. no. of tickets to be assigned to a particular team
     *            member
     * @param typeOfTickets
     *            The different categories of tickets defined
     */

    private TreeMap<Integer, ArrayList<String>> restrictedTickets = new TreeMap<Integer, ArrayList<String>>();
    private TreeMap<String, String> newTicketList = new TreeMap<String, String>();
    private ArrayList<TicketData> sortedListByDeveloper = new ArrayList<TicketData>();

    private boolean containsResKeywordList(String titleList,
            ArrayList<String> restrictedKeywords) {
        boolean flagKeyword = false;
        for (int l = 0; l < restrictedKeywords.size(); l++) {

            if (!restrictedKeywords.get(l).equalsIgnoreCase(" ")) {
                if (titleList.contains(restrictedKeywords.get(l))) {
                    flagKeyword = true;
                    break;
                }
            }
        }
        return flagKeyword;
    }

    private boolean ticketsAvailableToAssign(
            TreeMap<Integer, ArrayList<String>> dataSheet,
            ArrayList<String> restrictedKeywords) {

        for (Entry<Integer, ArrayList<String>> dataSheetEntry : dataSheet
                .entrySet()) {

            if (dataSheetEntry.getValue().get(2).equalsIgnoreCase(" ")
                    && !containsResKeywordList(
                            dataSheetEntry.getValue().get(5),
                            restrictedKeywords)) {
                return true;
            } else if (containsResKeywordList(dataSheetEntry.getValue().get(5),
                    restrictedKeywords)) {
                restrictedTickets.put(dataSheetEntry.getKey(),
                        dataSheetEntry.getValue());
            }
        }
        return false;
    }

    private void addToSortedList(ArrayList<String> dataSheetEntry) {
        TicketData tempTicketData = new TicketData();
        tempTicketData.setCaseID(dataSheetEntry.get(1));
        tempTicketData.setCreateTime(dataSheetEntry.get(4));
        tempTicketData.setGroup(dataSheetEntry.get(8));
        tempTicketData.setIndividual(dataSheetEntry.get(2));
        tempTicketData.setItem(dataSheetEntry.get(6));
        tempTicketData.setSeverity(dataSheetEntry.get(7));
        tempTicketData.setStatus(dataSheetEntry.get(3));
        tempTicketData.setSummary(dataSheetEntry.get(5));
        tempTicketData.setType(dataSheetEntry.get(0));

        sortedListByDeveloper.add(tempTicketData);
    }

    /**
     * Assigns the requisite tickets depending upon the category of the ticket
     * already assigned/to be assigned to a particular team member.
     * 
     * @param inputFile
     *            The "DATA" sheet of the excel workbook
     * @param sortAssignmentList
     *            No. of tickets, of each category, to be assigned to a team
     *            member sorted in decreasing order of total no. of tickets to
     *            be assigned
     * @param tempTTArr
     *            different categories of tickets
     * @param tickStatList
     */
    private TreeMap<Integer, ArrayList<String>> assignTo(
            TreeMap<Integer, ArrayList<String>> inputFile,
            SortAssignment[] sortAssignmentList, ArrayList<String> tempTTArr,
            ArrayList<String> resKeywordList) {

        String leastTickHolder = new String();

        while (sortAssignmentList[0].getCountArray()[0] > 0
                && ticketsAvailableToAssign(inputFile, resKeywordList)) {

            Integer status = new Integer(0);
            leastTickHolder = sortAssignmentList[0].getName();

            for (Entry<Integer, ArrayList<String>> dataSheetEntry : inputFile
                    .entrySet()) {

                if (sortAssignmentList[0].getCountArray()[0] <= 0)
                    break;

                if (dataSheetEntry.getValue().get(2).equalsIgnoreCase(" ")) {

                    if (containsResKeywordList(
                            dataSheetEntry.getValue().get(5), resKeywordList)) {
                        restrictedTickets.put(dataSheetEntry.getKey(),
                                dataSheetEntry.getValue());
                        continue;
                    }

                    boolean flag = false;

                    int tempVar = 0;

                    for (int k = 1; k < tempTTArr.size(); k++) {
                        if (inputFile.get(dataSheetEntry.getKey()).get(0)
                                .equalsIgnoreCase(tempTTArr.get(k))) {
                            tempVar = k;
                            break;
                        }
                    }

                    if (sortAssignmentList[0].getCountArray()[tempVar] == 0) {
                        inputFile.get(dataSheetEntry.getKey()).set(2,
                                sortAssignmentList[0].getName());
                        // change
                        if (!newTicketList.keySet().contains(
                                sortAssignmentList[0].getName()))
                            newTicketList.put(sortAssignmentList[0].getName(),
                                    '"' + inputFile
                                            .get(dataSheetEntry.getKey())
                                            .get(1) + '"');
                        else
                            newTicketList.put(
                                    sortAssignmentList[0].getName(),
                                    newTicketList.get(
                                            sortAssignmentList[0].getName())
                                            .concat(","
                                                    + '"'
                                                    + inputFile.get(
                                                            dataSheetEntry
                                                                    .getKey())
                                                            .get(1) + '"'));

                        sortAssignmentList[0].getCountArray()[tempVar]++;
                        sortAssignmentList[0].getCountArray()[0]--;
                    } else {
                        flag = true;
                    }

                    if (!flag) {
                        SortAssignment.sort(sortAssignmentList,
                                SortAssignment.BY_TOTALREQUIREDTICKETS);
                        if (!sortAssignmentList[0].getName().equalsIgnoreCase(
                                leastTickHolder)) {
                            status = 1;
                            break;
                        }
                    }
                }

            }

            while (status != 1 && sortAssignmentList[0].getCountArray()[0] > 0
                    && ticketsAvailableToAssign(inputFile, resKeywordList)) {
                leastTickHolder = sortAssignmentList[0].getName();
                for (Entry<Integer, ArrayList<String>> dataSheetEntry : inputFile
                        .entrySet()) {
                    int tempVar = 0;

                    if (sortAssignmentList[0].getCountArray()[0] <= 0)
                        break;

                    if (dataSheetEntry.getValue().get(2).equalsIgnoreCase(" ")) {

                        if (containsResKeywordList(dataSheetEntry.getValue()
                                .get(5), resKeywordList)) {
                            restrictedTickets.put(dataSheetEntry.getKey(),
                                    dataSheetEntry.getValue());
                            continue;
                        }

                        for (int k = 1; k < tempTTArr.size(); k++) {
                            if (inputFile.get(dataSheetEntry.getKey()).get(0)
                                    .equalsIgnoreCase(tempTTArr.get(k))) {
                                tempVar = k;
                                break;
                            }
                        }

                        inputFile.get(dataSheetEntry.getKey()).set(2,
                                sortAssignmentList[0].getName());

                        // change
                        addToSortedList(dataSheetEntry.getValue());

                        if (!newTicketList.keySet().contains(
                                sortAssignmentList[0].getName()))
                            newTicketList.put(sortAssignmentList[0].getName(),
                                    '"' + inputFile
                                            .get(dataSheetEntry.getKey())
                                            .get(1) + '"');
                        else
                            newTicketList.put(
                                    sortAssignmentList[0].getName(),
                                    newTicketList.get(
                                            sortAssignmentList[0].getName())
                                            .concat(","
                                                    + '"'
                                                    + inputFile.get(
                                                            dataSheetEntry
                                                                    .getKey())
                                                            .get(1) + '"'));

                        sortAssignmentList[0].getCountArray()[tempVar]++;
                        sortAssignmentList[0].getCountArray()[0]--;
                        SortAssignment.sort(sortAssignmentList,
                                SortAssignment.BY_TOTALREQUIREDTICKETS);
                        if (!sortAssignmentList[0].getName().equalsIgnoreCase(
                                leastTickHolder)) {
                            status = 1;
                            break;
                        }
                    } else {
                        if (dataSheetEntry.getKey() != 0)
                            addToSortedList(dataSheetEntry.getValue());
                    }
                }
            }
        }
        /*Collections.sort(sortedListByDeveloper, TicketData.BY_NAME);
        SendMail sendMailObj = new SendMail();
        sendMailObj.sendMail(sortedListByDeveloper, newTicketList,
                restrictedTickets);*/
        return inputFile;
    }

    @Override
    public TreeMap<Integer, ArrayList<String>> countTickets(
            TreeMap<Integer, ArrayList<String>> dataSheet,
            TreeMap<Integer, ArrayList<String>> userThreshold,
            TreeMap<Integer, ArrayList<String>> typeOfTickets,
            TreeMap<Integer, ArrayList<String>> ticketStatus,
            TreeMap<Integer, ArrayList<String>> restrictedKeywords) {

        TreeMap<String, Integer[]> currentAssignedCount = new TreeMap<String, Integer[]>();
        Integer totalcount = 0;
        Integer categoryCount = 0;
        Integer threshold = 0;
        Integer counter = 0;
        ArrayList<String> tickStatList = new ArrayList<String>();
        ArrayList<String> resKeywordList = new ArrayList<String>();
        ArrayList<String> tempTTArr = new ArrayList<String>();

        for (Entry<Integer, ArrayList<String>> tempTSL : ticketStatus
                .entrySet()) {
            tickStatList.add(tempTSL.getValue().get(0));
        }

        for (Entry<Integer, ArrayList<String>> tempRKL : restrictedKeywords
                .entrySet()) {
            resKeywordList.add(tempRKL.getValue().get(0));
        }

        SortAssignment[] sortAssignmentList = new SortAssignment[userThreshold
                .entrySet().size() - 1];

        for (Entry<Integer, ArrayList<String>> tempTT : typeOfTickets
                .entrySet()) {
            tempTTArr.add(tempTT.getValue().get(0));
        }

        for (Entry<Integer, ArrayList<String>> userThresholdEntry : userThreshold
                .entrySet()) {
            Integer countArray[] = new Integer[tempTTArr.size()];
            counter = 0;
            while (tempTTArr.size() > counter) {
                countArray[counter] = 0;
                counter++;
            }

            if (userThresholdEntry.getKey() != 0) {
                totalcount = 0;

                for (Entry<Integer, ArrayList<String>> dataSheetEntry : dataSheet
                        .entrySet()) {
                    if (dataSheetEntry.getKey() != 0) {

                        if (userThresholdEntry
                                .getValue()
                                .get(0)
                                .equalsIgnoreCase(
                                        dataSheetEntry.getValue().get(2))
                                && !tickStatList.contains(dataSheetEntry
                                        .getValue().get(3))) {
                            totalcount++;
                            categoryCount = 0;

                            while (tempTTArr.size() > categoryCount) {
                                if (dataSheetEntry
                                        .getValue()
                                        .get(0)
                                        .equalsIgnoreCase(
                                                tempTTArr.get(categoryCount))) {
                                    countArray[categoryCount]++;
                                }
                                categoryCount++;
                            }
                        }
                    }
                }
                Double temp = Double.parseDouble(userThresholdEntry.getValue()
                        .get(1));
                threshold = temp.intValue();
            }

            countArray[0] = threshold - totalcount;

            if (userThresholdEntry.getKey() != 0) {
                sortAssignmentList[(userThresholdEntry.getKey() - 1)] = new SortAssignment(
                        userThresholdEntry.getValue().get(0), countArray);
                currentAssignedCount.put(userThresholdEntry.getValue().get(0),
                        countArray);
            }
        }

        SortAssignment.setSortAssignmentList(sortAssignmentList);
        SortAssignment.sort(SortAssignment.getSortAssignmentList(),
                SortAssignment.BY_TOTALREQUIREDTICKETS);

        TreeMap<Integer, ArrayList<String>> newAssignedList = assignTo(
                dataSheet, SortAssignment.getSortAssignmentList(), tempTTArr,
                resKeywordList);

        return newAssignedList;
    }

    @Override
    public TreeMap<String, String> newTicketList() {
        if (newTicketList.isEmpty())
            return null;
        else {
            newTicketList.put("Individual+:", "Case ID+:");
            for (String key : newTicketList.keySet()) {
                if (key != "Individual+:") {
                    String tempValue = "'Case ID+:' = "
                            + newTicketList.get(key).replaceAll(",",
                                    "  OR 'Case ID+:' = ");
                    newTicketList.put(key, tempValue);
                }
            }
            return newTicketList;
        }
    }

    @Override
    public TreeMap<Integer, ArrayList<String>> restrictedTicketList() {
        if (restrictedTickets.isEmpty())
            return null;
        else
            return restrictedTickets;
    }
}
