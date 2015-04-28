package com.ctli.dco.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import com.ctli.dco.dto.Developer;
import com.ctli.dco.dto.Issue;
import com.ctli.dco.service.ICompareIssueService;

public class CompareIssueService implements ICompareIssueService {

    public void compareIssues(String type, String resultsFile) {

        BufferedReader brOld = null;
        BufferedReader brNew = null;
        BufferedReader brIgnore = null;

        String repository = "C:/" + type + "/";
        File yesterdayTxt = new File(repository
                + "inputFiles/input/yesterday.txt");
        File todayTxt = new File(repository + "inputFiles/input/today.txt");
        File ignoredIssuesTxt = new File(repository
                + "inputFiles/input/ignoredIssues.txt");
        File source = new File(repository + "inputFiles/new/results.txt");

        File resultsInputFile = null;
        if (resultsFile.contains("C:"))
            resultsInputFile = new File(resultsFile);
        else
            resultsInputFile = new File(repository + resultsFile);

        File commonData = new File(repository
                + "inputFiles/output/commonData.txt");
        File prevAssignment = new File(repository
                + "inputFiles/output/commonDataPrev.txt");
        File filteredData = new File(repository
                + "inputFiles/output/filteredData.txt");

        /*
         * File yesterdayTxt = new File("inputFiles/" + type +
         * "/input/yesterday.txt"); File todayTxt = new File("inputFiles/" +
         * type + "/input/today.txt"); File ignoredIssuesTxt = new
         * File("inputFiles/" + type + "/input/ignoredIssues.txt"); File source
         * = new File("inputFiles/" + type + "/new/results.txt");
         */
        /*
         * File automatedIssues = new File("flatFiles/" + type +
         * "/automatedIssues.txt");
         */
        /*
         * File resultsInputFile = new File(resultsFile); File commonData = new
         * File("inputFiles/" + type + "/output/commonData.txt"); File
         * prevAssignment = new File("inputFiles/" + type +
         * "/output/commonDataPrev.txt"); File filteredData = new
         * File("inputFiles/" + type + "/output/filteredData.txt");
         */

        try {

            System.out
                    .println("*****************DC Order Script Generator Tool*****************");

            String sCurrentLineOld = null;
            String sCurrentLineNew = null;
            String sIgnoredIssueLine = null;

            try {
                copyFile(resultsInputFile, source);
                moveFile(todayTxt, yesterdayTxt);
                copyFile(source, todayTxt);
                copyFile(commonData, prevAssignment);

                brOld = new BufferedReader(new FileReader(yesterdayTxt));
                brNew = new BufferedReader(new FileReader(todayTxt));
                brIgnore = new BufferedReader(new FileReader(ignoredIssuesTxt));

            } catch (Exception e) {
                System.out.println("Input Files missing , Please read Note.");
                System.out.println("NOTE: Place results.txt in new directory");
                System.out
                        .println("NOTE: Place CSM_CUP1_DC_ORDERS_IR99999_part1.txt in   referenceData/DC_ORDER/");
                System.out
                        .println("----------------------------------------------------------------");
                System.exit(0);
            }

            ArrayList<String> oldFileDataArrayList = new ArrayList<String>();
            ArrayList<String> newFileDataArrayList = new ArrayList<String>();
            ArrayList<String> ignoredDataArrayList = new ArrayList<String>();
            ArrayList<String> commonFileDataArrayList = new ArrayList<String>();

            while ((sCurrentLineOld = brOld.readLine()) != null) {
                oldFileDataArrayList.add(sCurrentLineOld);
            }

            while ((sCurrentLineNew = brNew.readLine()) != null) {
                newFileDataArrayList.add(sCurrentLineNew);
            }

            while ((sIgnoredIssueLine = brIgnore.readLine()) != null) {
                ignoredDataArrayList.add(sIgnoredIssueLine);
            }

            for (String oldFileRow : oldFileDataArrayList) {
                if (newFileDataArrayList.contains(oldFileRow)
                        && ifNotIgnoredIssue(oldFileRow, ignoredDataArrayList)) {
                    if (!oldFileRow.contains("blbn_olnrc")) {
                        commonFileDataArrayList.add(oldFileRow);
                        newFileDataArrayList.remove(oldFileRow);
                    } else {
                        newFileDataArrayList.remove(oldFileRow);
                    }
                }
            }

            if (oldFileDataArrayList.isEmpty()) {
                for (String newFileRow : newFileDataArrayList) {
                    if (newFileRow.contains("unknown")
                            && ifNotIgnoredIssue(newFileRow,
                                    ignoredDataArrayList)) {
                        commonFileDataArrayList.add(newFileRow);
                    }
                }
            } else {
                if (!newFileDataArrayList.isEmpty()) {
                    Iterator<String> itr = newFileDataArrayList.iterator();
                    String tempVar = itr.next();
                    while (itr.hasNext()) {
                        if (tempVar.contains("unknown")
                                && ifNotIgnoredIssue(tempVar,
                                        ignoredDataArrayList)) {
                            commonFileDataArrayList.add(tempVar);
                            itr.next();
                            itr.remove();
                        } else {
                            itr.next();
                        }
                    }
                    if (newFileDataArrayList.get(0).contains("unknown")
                            && ifNotIgnoredIssue(newFileDataArrayList.get(0),
                                    ignoredDataArrayList)) {
                        commonFileDataArrayList
                                .add(newFileDataArrayList.get(0));
                        // change
                        newFileDataArrayList.clear();
                    }
                }
            }

            commonData.createNewFile();
            filteredData.createNewFile();

            FileWriter commonDataWriter = new FileWriter(
                    commonData.getAbsoluteFile());
            FileWriter filteredDataWriter = new FileWriter(
                    filteredData.getAbsoluteFile());
            BufferedWriter commonDataBuffer = new BufferedWriter(
                    commonDataWriter);
            BufferedWriter filteredDataBuffer = new BufferedWriter(
                    filteredDataWriter);

            for (String commonDataRow : commonFileDataArrayList) {
                commonDataBuffer.write(commonDataRow);
                commonDataBuffer.write('\n');
            }

            for (String filteredDataRow : newFileDataArrayList) {
                if (filteredDataRow.contains("unknown")
                        || !ifNotIgnoredIssue(filteredDataRow,
                                ignoredDataArrayList)) {
                    continue;
                }
                filteredDataBuffer.write(filteredDataRow);
                filteredDataBuffer.write('\n');
            }

            System.out.println("----File(s) Generated----");
            System.out.println("filteredData.txt");
            System.out.println("=============================");

            commonDataBuffer.close();
            filteredDataBuffer.close();
            assignIssues(type);

            System.out.println("----File(s) Generated----");
            System.out.println("commonData.txt");

            /*
             * copyFile(filteredData, automatedIssues);
             * 
             * System.out.println("----File(s) Generated----");
             * System.out.println("automatedIssues.txt");
             */

            System.out.println("=============================");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (brOld != null)
                    brOld.close();
                if (brNew != null)
                    brNew.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private ArrayList<Issue> assignIssues(String type) {
        ArrayList<Issue> issueList = new ArrayList<Issue>();
        // ArrayList<Developer> devList = new ArrayList<Developer>();
        ArrayList<String> devListArray = new ArrayList<String>();

        String repository = "C:/" + type + "/";
        File prevAssignment = new File(repository
                + "inputFiles/output/commonDataPrev.txt");
        File issuesDirectory = new File(repository
                + "inputFiles/output/commonData.txt");
        File devDirectory = new File(repository
                + "referenceData/DeveloperList.txt");

        /*
         * File prevAssignment = new File(
         * "inputFiles/DC_ORDER/output/commonDataPrev.txt"); File
         * issuesDirectory = new File(
         * "inputFiles/DC_ORDER/output/commonData.txt"); File devDirectory = new
         * File("referenceData/DeveloperList.txt");
         */

        BufferedReader issueBuffer = null;
        BufferedReader prevIssueBuffer = null;
        BufferedReader devBuffer = null;
        String currentLine = null;
        ArrayList<String> oldAssignDataArrayList = null;
        int id = 0;

        try {
            prevIssueBuffer = new BufferedReader(new FileReader(prevAssignment));
            oldAssignDataArrayList = new ArrayList<String>();
            while ((currentLine = prevIssueBuffer.readLine()) != null) {
                oldAssignDataArrayList.add(currentLine);
            }

            devBuffer = new BufferedReader(new FileReader(devDirectory));
            while ((currentLine = devBuffer.readLine()) != null) {
                if (!currentLine.contains("#")) {
                    devListArray.add(currentLine.split(",")[1]);
                    id++;
                }
            }

            int devListSize = id;

            issueBuffer = new BufferedReader(new FileReader(issuesDirectory));
            while ((currentLine = issueBuffer.readLine()) != null) {
                Issue issue = new Issue();
                issue.setStatus("Not Assigned");
                issue.setTitle(currentLine);
                issueList.add(issue);
            }

            int thresholdDev = (issueList.size() / devListSize);
            id = 0;
            Developer[] devList = new Developer[devListSize];

            for (id = 0; id < devListSize; id++) {
                devList[id] = new Developer(devListArray.get(id), thresholdDev);
            }

            for (Issue issueItem : issueList) {
                for (String oldFileRow : oldAssignDataArrayList) {
                    if (oldFileRow.contains(issueItem.getTitle())) {
                        issueItem.setDeveloperName(oldFileRow.split("\t")[1]);
                        issueItem.setStatus("Assigned");

                        setThreshold(oldFileRow.split("\t")[1], devList,
                                devListSize);
                        Developer.sort(devList,
                                Developer.BY_TOTALREQUIREDISSUES);
                        break;
                    }
                }
            }

            Developer.sort(devList, Developer.BY_TOTALREQUIREDISSUES);
            boolean allThresholdUsed = false;

            try {
                if (thresholdDev > 0) {
                    for (Issue issueItem : issueList) {
                        Developer nextdevItem = devList[0];
                        if (issueItem.getStatus().equalsIgnoreCase(
                                "Not Assigned")
                                && allThresholdUsed == false) {
                            if (nextdevItem.getThreshold() > 0) {
                                issueItem.setDeveloperName(nextdevItem
                                        .getName());
                                issueItem.setStatus("Assigned");
                                nextdevItem.setThreshold((nextdevItem
                                        .getThreshold() - 1));
                                Developer.sort(devList,
                                        Developer.BY_TOTALREQUIREDISSUES);
                            } else {
                                allThresholdUsed = true;
                            }
                        }
                    }
                }

            } catch (NoSuchElementException e) {
                System.out.println("Reached at the end of iteration!");
            }

            Developer.sort(devList, Developer.BY_TOTALREQUIREDISSUES);

            for (Issue issueItem : issueList) {
                Developer nextdevItem = devList[0];
                if (issueItem.getStatus().equalsIgnoreCase("Not Assigned")
                        && (allThresholdUsed == true || thresholdDev == 0)) {
                    issueItem.setDeveloperName(nextdevItem.getName());
                    issueItem.setStatus("Assigned");
                    nextdevItem.setThreshold((nextdevItem.getThreshold() - 1));
                    Developer.sort(devList, Developer.BY_TOTALREQUIREDISSUES);
                }
            }

            PrintWriter pw = new PrintWriter(issuesDirectory);
            for (Issue issueItem : issueList) {
                pw.println(issueItem.getTitle() + "\t"
                        + issueItem.getDeveloperName());
            }
            pw.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                prevIssueBuffer.close();
                devBuffer.close();
                issueBuffer.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return issueList;
    }

    private void copyFile(File source, File dest) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (IOException e) {
            System.out.println("Copy operation failed.");
            System.out.println(e.getMessage());
        } finally {
            try {
                System.out.println("Copied file : " + source.getAbsolutePath()
                        + " to " + dest.getAbsolutePath());
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void moveFile(File todayTxt, File yesterdayTxt) {
        copyFile(todayTxt, yesterdayTxt);
        deleteFile(todayTxt);
    }

    private void deleteFile(File todayTxt) {
        if (todayTxt.delete()) {
            System.out.println("Deleted File : " + todayTxt.getAbsolutePath());
        } else {
            System.out.println("Delete operation failed.");
        }
    }

    /*
     * private void setThreshold(String devName, ArrayList<Developer> devList) {
     * for (Developer tempDev : devList) { if
     * (tempDev.getName().equalsIgnoreCase(devName)) {
     * tempDev.setThreshold((tempDev.getThreshold() - 1)); } } }
     */

    private void setThreshold(String devName, Developer[] devList, int size) {
        for (int i = 0; i < size; i++) {
            if (devList[i].getName().equalsIgnoreCase(devName)) {
                devList[i].setThreshold((devList[i].getThreshold() - 1));
            }
        }
    }

    private boolean ifNotIgnoredIssue(String fileRow,
            ArrayList<String> ignoredDataArrayList) {
        for (String ignoredData : ignoredDataArrayList)
            if (fileRow.contains(ignoredData))
                return false;
        return true;
    }
}
