package com.centurylink.xprsr.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.centurylink.xprsr.dto.BotRepData;
import com.centurylink.xprsr.dto.ValidationData;
import com.centurylink.xprsr.dto.WeeklyRepData;
import com.centurylink.xprsr.service.IReportService;

public class ReportService implements IReportService {

    DecimalFormat dF = new DecimalFormat("###.##");

    @Override
    public ArrayList<WeeklyRepData> getReport(
            TreeMap<Integer, ArrayList<String>> read,
            TreeMap<Integer, ArrayList<String>> nameList,
            TreeMap<Integer, ArrayList<String>> issueStatusList,
            String fromDate, String toDate) {

        WeeklyRepData grandTotal = new WeeklyRepData();
        grandTotal.setName("Grand Total");
        grandTotal.setUnderWork(0);
        grandTotal.setWithdrawn(0);
        grandTotal.setClosed(0);
        grandTotal.setGrandTotalResolved(0);
        grandTotal.setTAT(0.0);
        grandTotal.setActualTime(0.0);
        grandTotal.setTATPerTicket(null);

        ArrayList<String> columnNameList = read.firstEntry().getValue();
        ArrayList<WeeklyRepData> weeklyReportList = new ArrayList<WeeklyRepData>();
        for (Entry<Integer, ArrayList<String>> tempNameList : nameList
                .entrySet()) {
            if (tempNameList.getKey() != 0) {
                WeeklyRepData weeklyRepData = new WeeklyRepData();
                weeklyRepData.setName(tempNameList.getValue().get(0));
                weeklyRepData.setUnderWork(0);
                weeklyRepData.setWithdrawn(0);
                weeklyRepData.setClosed(0);
                weeklyRepData.setGrandTotalResolved(0);
                weeklyRepData.setTAT(0.0);
                weeklyRepData.setActualTime(0.0);
                weeklyRepData.setTATPerTicket(null);

                for (Entry<Integer, ArrayList<String>> tempReadList : read
                        .entrySet()) {
                    if (tempReadList.getKey() != 0) {

                        if (tempReadList
                                .getValue()
                                .get(columnNameList.indexOf("Assigned To"))
                                .equalsIgnoreCase(
                                        tempNameList.getValue().get(0))) {
                            for (Entry<Integer, ArrayList<String>> tempStatusList : issueStatusList
                                    .entrySet()) {
                                if (tempStatusList.getKey() != 0) {
                                    if (tempReadList
                                            .getValue()
                                            .get(columnNameList
                                                    .indexOf("Issue SubStatus"))
                                            .equalsIgnoreCase(
                                                    tempStatusList.getValue()
                                                            .get(1))) {
                                        if (tempStatusList.getValue().get(1)
                                                .contains("Withdrawn")
                                                && tempReadList
                                                        .getValue()
                                                        .get(columnNameList
                                                                .indexOf("Resolution Date"))
                                                        .compareTo(toDate) < 0
                                                && tempReadList
                                                        .getValue()
                                                        .get(columnNameList
                                                                .indexOf("Resolution Date"))
                                                        .compareTo(fromDate) > 0) {
                                            weeklyRepData
                                                    .setWithdrawn(weeklyRepData
                                                            .getWithdrawn() + 1);
                                            weeklyRepData
                                                    .setTAT(weeklyRepData
                                                            .getTAT()
                                                            + Double.valueOf(tempReadList
                                                                    .getValue()
                                                                    .get(columnNameList
                                                                            .indexOf("Turnaround Time(In Hours)"))));
                                            weeklyRepData
                                                    .setActualTime(weeklyRepData
                                                            .getActualTime()
                                                            + Double.valueOf(tempReadList
                                                                    .getValue()
                                                                    .get(columnNameList
                                                                            .indexOf("Actual Hours"))));

                                            grandTotal.setWithdrawn(grandTotal
                                                    .getWithdrawn() + 1);
                                            grandTotal
                                                    .setTAT(grandTotal.getTAT()
                                                            + Double.valueOf(tempReadList
                                                                    .getValue()
                                                                    .get(columnNameList
                                                                            .indexOf("Turnaround Time(In Hours)"))));
                                            grandTotal
                                                    .setActualTime(grandTotal
                                                            .getActualTime()
                                                            + Double.valueOf(tempReadList
                                                                    .getValue()
                                                                    .get(columnNameList
                                                                            .indexOf("Actual Hours"))));

                                            break;

                                        } else if (tempStatusList.getValue()
                                                .get(0)
                                                .equalsIgnoreCase("Active")) {
                                            weeklyRepData
                                                    .setUnderWork(weeklyRepData
                                                            .getUnderWork() + 1);
                                            grandTotal.setUnderWork(grandTotal
                                                    .getUnderWork() + 1);
                                            break;
                                        } else if (tempStatusList.getValue()
                                                .get(0)
                                                .equalsIgnoreCase("Resolved")
                                                && tempReadList
                                                        .getValue()
                                                        .get(columnNameList
                                                                .indexOf("Resolution Date"))
                                                        .compareTo(toDate) < 0
                                                && tempReadList
                                                        .getValue()
                                                        .get(columnNameList
                                                                .indexOf("Resolution Date"))
                                                        .compareTo(fromDate) > 0) {
                                            weeklyRepData
                                                    .setClosed(weeklyRepData
                                                            .getClosed() + 1);
                                            weeklyRepData
                                                    .setTAT(weeklyRepData
                                                            .getTAT()
                                                            + Double.valueOf(tempReadList
                                                                    .getValue()
                                                                    .get(columnNameList
                                                                            .indexOf("Turnaround Time(In Hours)"))));
                                            weeklyRepData
                                                    .setActualTime(weeklyRepData
                                                            .getActualTime()
                                                            + Double.valueOf(tempReadList
                                                                    .getValue()
                                                                    .get(columnNameList
                                                                            .indexOf("Actual Hours"))));
                                            grandTotal.setClosed(grandTotal
                                                    .getClosed() + 1);
                                            grandTotal
                                                    .setTAT(grandTotal.getTAT()
                                                            + Double.valueOf(tempReadList
                                                                    .getValue()
                                                                    .get(columnNameList
                                                                            .indexOf("Turnaround Time(In Hours)"))));
                                            grandTotal
                                                    .setActualTime(grandTotal
                                                            .getActualTime()
                                                            + Double.valueOf(tempReadList
                                                                    .getValue()
                                                                    .get(columnNameList
                                                                            .indexOf("Actual Hours"))));
                                            break;
                                        }
                                    }
                                }
                            }

                        }
                    }

                }
                weeklyRepData.setGrandTotalResolved(weeklyRepData.getClosed()
                        + weeklyRepData.getWithdrawn());
                grandTotal.setGrandTotalResolved(grandTotal
                        .getGrandTotalResolved()
                        + weeklyRepData.getGrandTotalResolved());
                if (!Double.isNaN(weeklyRepData.getTAT()
                        / weeklyRepData.getGrandTotalResolved())) {
                    weeklyRepData.setTATPerTicket(dF.format(weeklyRepData
                            .getTAT() / weeklyRepData.getGrandTotalResolved()));
                }
                weeklyReportList.add(weeklyRepData);

            }
        }
        grandTotal.setTATPerTicket(dF.format(grandTotal.getTAT()
                / grandTotal.getGrandTotalResolved()));
        weeklyReportList.add(grandTotal);
        if (weeklyReportList.isEmpty())
            return null;
        else
            return weeklyReportList;
    }

    @Override
    public ArrayList<ValidationData> getStatusMismatch(
            TreeMap<Integer, ArrayList<String>> read,
            TreeMap<Integer, ArrayList<String>> issueStatusList) {

        ArrayList<String> columnNameList = read.firstEntry().getValue();
        ArrayList<ValidationData> validationList = new ArrayList<ValidationData>();
        ValidationData validationData = null;
        boolean notValid = false;

        for (Entry<Integer, ArrayList<String>> tempReadList : read.entrySet()) {
            if (tempReadList.getKey() != 0) {
                for (Entry<Integer, ArrayList<String>> tempStatusList : issueStatusList
                        .entrySet()) {
                    if (tempStatusList.getKey() != 0) {
                        if (tempReadList
                                .getValue()
                                .get(columnNameList.indexOf("Issue SubStatus"))
                                .equalsIgnoreCase(
                                        tempStatusList.getValue().get(1))) {
                            if (!tempReadList
                                    .getValue()
                                    .get(columnNameList.indexOf("Issue Status"))
                                    .equalsIgnoreCase(
                                            tempStatusList.getValue().get(0))) {
                                notValid = true;
                                validationData = new ValidationData();
                                validationData.setIR(tempReadList.getValue()
                                        .get(columnNameList.indexOf("IR#")));
                                validationData.setAssignedTo(tempReadList
                                        .getValue()
                                        .get(columnNameList
                                                .indexOf("Assigned To")));
                                validationData.setAssignedDate(tempReadList
                                        .getValue()
                                        .get(columnNameList
                                                .indexOf("Assigned Date")));
                                validationData.setIssueStatus(tempReadList
                                        .getValue()
                                        .get(columnNameList
                                                .indexOf("Issue Status")));
                                validationData.setIssueSubStatus(tempReadList
                                        .getValue()
                                        .get(columnNameList
                                                .indexOf("Issue SubStatus")));
                                break;
                            }
                        }
                    }
                }
            }
            if (notValid == true) {
                validationList.add(validationData);
                notValid = false;
            }
        }
        if (validationList.isEmpty())
            return null;
        else
            return validationList;
    }

    @Override
    public ArrayList<ValidationData> getMissingResolutionDate(
            TreeMap<Integer, ArrayList<String>> read,
            TreeMap<Integer, ArrayList<String>> issueStatusList) {

        ArrayList<String> columnNameList = read.firstEntry().getValue();
        ArrayList<ValidationData> validationList = new ArrayList<ValidationData>();
        ValidationData validationData = null;
        boolean notValid = false;

        for (Entry<Integer, ArrayList<String>> tempReadList : read.entrySet()) {
            if (tempReadList.getKey() != 0) {
                for (Entry<Integer, ArrayList<String>> tempStatusList : issueStatusList
                        .entrySet()) {
                    if (tempStatusList.getKey() != 0) {
                        if (tempReadList.getValue()
                                .get(columnNameList.indexOf("Issue Status"))
                                .equalsIgnoreCase("Resolved")) {
                            if (tempReadList
                                    .getValue()
                                    .get(columnNameList
                                            .indexOf("Resolution Date"))
                                    .equals(" ")) {
                                notValid = true;
                                validationData = new ValidationData();
                                validationData.setIR(tempReadList.getValue()
                                        .get(columnNameList.indexOf("IR#")));
                                validationData.setAssignedTo(tempReadList
                                        .getValue()
                                        .get(columnNameList
                                                .indexOf("Assigned To")));
                                validationData.setAssignedDate(tempReadList
                                        .getValue()
                                        .get(columnNameList
                                                .indexOf("Assigned Date")));
                                validationData.setIssueStatus(tempReadList
                                        .getValue()
                                        .get(columnNameList
                                                .indexOf("Issue Status")));
                                validationData.setIssueSubStatus(tempReadList
                                        .getValue()
                                        .get(columnNameList
                                                .indexOf("Issue SubStatus")));
                                validationData.setResolutionDate(tempReadList
                                        .getValue()
                                        .get(columnNameList
                                                .indexOf("Resolution Date")));
                                break;
                            }
                        }

                    }
                }
            }
            if (notValid == true) {
                validationList.add(validationData);
                notValid = false;
            }
        }
        if (validationList.isEmpty())
            return null;
        else
            return validationList;
    }

    @Override
    public ArrayList<ValidationData> getMissingTAT(
            TreeMap<Integer, ArrayList<String>> read,
            TreeMap<Integer, ArrayList<String>> issueStatusList) {

        ArrayList<String> columnNameList = read.firstEntry().getValue();
        ArrayList<ValidationData> validationList = new ArrayList<ValidationData>();
        ValidationData validationData = null;
        boolean notValid = false;

        for (Entry<Integer, ArrayList<String>> tempReadList : read.entrySet()) {
            if (tempReadList.getKey() != 0) {
                for (Entry<Integer, ArrayList<String>> tempStatusList : issueStatusList
                        .entrySet()) {
                    if (tempStatusList.getKey() != 0) {
                        if (tempReadList.getValue()
                                .get(columnNameList.indexOf("Issue Status"))
                                .equalsIgnoreCase("Resolved")) {
                            if (!tempReadList
                                    .getValue()
                                    .get(columnNameList
                                            .indexOf("Turnaround Time(In Hours)"))
                                    .equalsIgnoreCase(" ")
                                    && !tempReadList
                                            .getValue()
                                            .get(columnNameList
                                                    .indexOf("Actual Hours"))
                                            .equalsIgnoreCase(" ")) {
                                if ((Double
                                        .parseDouble(tempReadList
                                                .getValue()
                                                .get(columnNameList
                                                        .indexOf("Turnaround Time(In Hours)"))) < Double
                                        .parseDouble(tempReadList
                                                .getValue()
                                                .get(columnNameList
                                                        .indexOf("Actual Hours"))))
                                        || (Double
                                                .parseDouble(tempReadList
                                                        .getValue()
                                                        .get(columnNameList
                                                                .indexOf("Turnaround Time(In Hours)"))) < 0.1)) {
                                    notValid = true;
                                    validationData = new ValidationData();
                                    validationData.setIR(tempReadList
                                            .getValue().get(
                                                    columnNameList
                                                            .indexOf("IR#")));
                                    validationData.setAssignedTo(tempReadList
                                            .getValue()
                                            .get(columnNameList
                                                    .indexOf("Assigned To")));
                                    validationData.setAssignedDate(tempReadList
                                            .getValue()
                                            .get(columnNameList
                                                    .indexOf("Assigned Date")));
                                    validationData.setIssueStatus(tempReadList
                                            .getValue()
                                            .get(columnNameList
                                                    .indexOf("Issue Status")));
                                    validationData
                                            .setIssueSubStatus(tempReadList
                                                    .getValue()
                                                    .get(columnNameList
                                                            .indexOf("Issue SubStatus")));
                                    validationData
                                            .setTAT(Double.valueOf(tempReadList
                                                    .getValue()
                                                    .get(columnNameList
                                                            .indexOf("Turnaround Time(In Hours)"))));
                                    validationData
                                            .setActualTime(Double.valueOf(tempReadList
                                                    .getValue()
                                                    .get(columnNameList
                                                            .indexOf("Actual Hours"))));
                                    break;
                                }
                            }
                        }

                    }
                }
            }
            if (notValid == true) {
                validationList.add(validationData);
                notValid = false;
            }
        }
        if (validationList.isEmpty())
            return null;
        else
            return validationList;
    }

    @Override
    public ArrayList<ValidationData> getZeroWRNo(
            TreeMap<Integer, ArrayList<String>> read) {

        ArrayList<String> columnNameList = read.firstEntry().getValue();
        ArrayList<ValidationData> validationList = new ArrayList<ValidationData>();
        ValidationData validationData = null;
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        boolean notValid = false;

        for (Entry<Integer, ArrayList<String>> tempReadList : read.entrySet()) {
            if (tempReadList.getKey() != 0) {
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = formatter.parse(tempReadList.getValue().get(
                            columnNameList.indexOf("Assigned Date")));
                    date2 = formatter.parse("07/01/2013");
                } catch (ParseException e) {
                    System.out.println("Date Parsing Failed!");
                    e.printStackTrace();
                }

                if (!tempReadList.getValue().get(columnNameList.indexOf("WR#"))
                        .equalsIgnoreCase(" ")
                        && tempReadList.getValue()
                                .get(columnNameList.indexOf("WR#")).length() < 8) {
                    if (date1.compareTo(date2) >= 0
                            && (Integer.valueOf(tempReadList.getValue().get(
                                    columnNameList.indexOf("WR#"))) == 0 || tempReadList
                                    .getValue()
                                    .get(columnNameList.indexOf("WR#"))
                                    .length() < 7)) {
                        notValid = true;
                        validationData = new ValidationData();
                        validationData.setIR(tempReadList.getValue().get(
                                columnNameList.indexOf("IR#")));
                        validationData.setAssignedTo(tempReadList.getValue()
                                .get(columnNameList.indexOf("Assigned To")));
                        validationData.setAssignedDate(tempReadList.getValue()
                                .get(columnNameList.indexOf("Assigned Date")));
                        validationData.setIssueStatus(tempReadList.getValue()
                                .get(columnNameList.indexOf("Issue Status")));
                        validationData.setIssueSubStatus(tempReadList
                                .getValue().get(
                                        columnNameList
                                                .indexOf("Issue SubStatus")));
                        validationData
                                .setWRNo(Integer.valueOf(tempReadList
                                        .getValue().get(
                                                columnNameList.indexOf("WR#"))));
                    }

                }
            }
            if (notValid == true) {
                validationList.add(validationData);
                notValid = false;
            }
        }
        if (validationList.isEmpty())
            return null;
        else
            return validationList;
    }

    @Override
    public ArrayList<ValidationData> getRemedyCreatedResolved(
            TreeMap<Integer, ArrayList<String>> read) {
        ArrayList<String> columnNameList = read.firstEntry().getValue();
        ArrayList<ValidationData> validationList = new ArrayList<ValidationData>();
        ValidationData validationData = null;
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        boolean notValid = false;

        for (Entry<Integer, ArrayList<String>> tempReadList : read.entrySet()) {
            if (tempReadList.getKey() != 0) {

                Date date1 = null;
                Date date2 = null;
                try {
                    if (!tempReadList.getValue()
                            .get(columnNameList.indexOf("Resolution Date"))
                            .equalsIgnoreCase(" ")) {
                        date1 = formatter.parse(tempReadList.getValue().get(
                                columnNameList.indexOf("Resolution Date")));
                        date2 = new Date(new Date().getTime()
                                - (2 * 24 * 3600 * 1000));
                    }
                } catch (ParseException e) {
                    System.out.println("Date Parsing Failed!");
                    e.printStackTrace();
                }

                if (tempReadList.getValue()
                        .get(columnNameList.indexOf("Issue Status"))
                        .equalsIgnoreCase("Resolved")
                        && tempReadList.getValue()
                                .get(columnNameList.indexOf("Issue SubStatus"))
                                .equalsIgnoreCase("Remedy Created")) {
                    if (date1 != null && date2 != null) {
                        if (date1.compareTo(date2) <= 0) {
                            notValid = true;
                            validationData = new ValidationData();
                            validationData.setIR(tempReadList.getValue().get(
                                    columnNameList.indexOf("IR#")));
                            validationData.setAssignedTo(tempReadList
                                    .getValue().get(
                                            columnNameList
                                                    .indexOf("Assigned To")));
                            validationData.setAssignedDate(tempReadList
                                    .getValue().get(
                                            columnNameList
                                                    .indexOf("Assigned Date")));
                            validationData.setIssueStatus(tempReadList
                                    .getValue().get(
                                            columnNameList
                                                    .indexOf("Issue Status")));
                            validationData.setIssueSubStatus(tempReadList
                                    .getValue()
                                    .get(columnNameList
                                            .indexOf("Issue SubStatus")));
                            validationData.setResolutionDate(tempReadList
                                    .getValue()
                                    .get(columnNameList
                                            .indexOf("Resolution Date")));
                        }
                    }
                }
            }
            if (notValid == true) {
                validationList.add(validationData);
                notValid = false;
            }
        }
        if (validationList.isEmpty())
            return null;
        else
            return validationList;
    }

    @Override
    public ArrayList<BotRepData> getBotReport(
            TreeMap<Integer, ArrayList<String>> read,
            TreeMap<Integer, ArrayList<String>> read2, String fromDate,
            String toDate) {
        // TODO Auto-generated method stub
        return null;
    }

}
