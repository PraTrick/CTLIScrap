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

        File oldYesterdayTxt = new File("inputFiles/" + type
                + "/input/yesterday.txt");
        File todayTxt = new File("inputFiles/" + type + "/input/today.txt");
        File ignoredIssuesTxt = new File("inputFiles/" + type
                + "/input/ignoredIssues.txt");
        File source = new File("inputFiles/" + type + "/new/results.txt");
        File automatedIssues = new File("flatFiles/" + type
                + "/automatedIssues.txt");
        File resultsInputFile = new File(resultsFile);
        File commonData = new File("inputFiles/" + type
                + "/output/commonData.txt");
        File filteredData = new File("inputFiles/" + type
                + "/output/filteredData.txt");

        try {

            System.out
                    .println("*****************DC Order Script Generator Tool*****************");

            String sCurrentLineOld = null;
            String sCurrentLineNew = null;
            String sIgnoredIssueLine = null;

            try {
                copyFile(resultsInputFile, source);
                moveFile(todayTxt, oldYesterdayTxt);
                copyFile(source, todayTxt);

                brOld = new BufferedReader(new FileReader("inputFiles/" + type
                        + "/input/yesterday.txt"));
                brNew = new BufferedReader(new FileReader("inputFiles/" + type
                        + "/input/today.txt"));
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
                    commonFileDataArrayList.add(oldFileRow);
                    newFileDataArrayList.remove(oldFileRow);
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
            System.out
                    .println("----------------------------------------------------------------");

            commonDataBuffer.close();
            filteredDataBuffer.close();
            assignIssues();
            copyFile(filteredData, automatedIssues);

            System.out.println("----File(s) Generated----");
            System.out.println("automatedIssues.txt");
            System.out.println("commonData.txt");
            System.out
                    .println("----------------------------------------------------------------");
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

    public ArrayList<Issue> assignIssues() {
        ArrayList<Issue> issueList = new ArrayList<Issue>();
        ArrayList<Developer> devList = new ArrayList<Developer>();

        File prevAssignment = new File(
                "inputFiles/DC_ORDER/output/commonDataPrev.txt");
        File issuesDirectory = new File(
                "inputFiles/DC_ORDER/output/commonData.txt");
        File devDirectory = new File("referenceData/DeveloperList.txt");

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
                    Developer dev = new Developer();
                    dev.setName(currentLine.split(",")[0]);
                    dev.setThreshold(Integer.parseInt(currentLine.split(",")[1]));
                    devList.add(dev);
                }
            }

            issueBuffer = new BufferedReader(new FileReader(issuesDirectory));
            while ((currentLine = issueBuffer.readLine()) != null) {
                Issue issue = new Issue();
                issue.setStatus("Not Assigned");
                issue.setTitle(currentLine);
                issueList.add(issue);
            }

            int thresholdDev = issueList.size() / devList.size();

            for (Developer devitem : devList) {
                devitem.setThreshold(thresholdDev);
            }
            Iterator<Developer> itr = devList.iterator();
            Developer nextdevItem = itr.next();

            id = 0;
            boolean allThresholdUsed = false;
            boolean prevIssueAssigned = false;

            try {
                if (thresholdDev > 0) {
                    for (Issue issueItem : issueList) {
                        if (issueItem.getStatus().equalsIgnoreCase(
                                "Not Assigned")
                                && allThresholdUsed == false) {
                            for (String oldFileRow : oldAssignDataArrayList) {
                                if (oldFileRow.contains(issueItem.getTitle())) {
                                    issueItem.setDeveloperName(oldFileRow
                                            .split(" ")[1]);
                                    issueItem.setStatus("Assigned");

                                    setThreshold(oldFileRow.split(" ")[1],
                                            devList);

                                    prevIssueAssigned = true;
                                    break;
                                }
                            }

                            if (prevIssueAssigned == false) {
                                if (nextdevItem.getThreshold() > 0) {
                                    issueItem.setDeveloperName(nextdevItem
                                            .getName());
                                    issueItem.setStatus("Assigned");
                                    nextdevItem.setThreshold((nextdevItem
                                            .getThreshold() - 1));
                                } else {
                                    nextdevItem = itr.next();
                                    nextdevItem.setThreshold(thresholdDev);
                                    issueItem.setDeveloperName(nextdevItem
                                            .getName());
                                    issueItem.setStatus("Assigned");
                                    nextdevItem.setThreshold((nextdevItem
                                            .getThreshold() - 1));
                                }
                            } else {
                                prevIssueAssigned = false;
                            }
                        }
                    }
                }

            } catch (NoSuchElementException e) {
                allThresholdUsed = true;
                System.out.println("Reached at the end of iteration!");
            }

            for (Issue issueItem : issueList) {
                if (issueItem.getStatus().equalsIgnoreCase("Not Assigned")
                        && (allThresholdUsed == true || thresholdDev == 0)) {
                    issueItem.setDeveloperName(devList.get(id).getName());
                    issueItem.setStatus("Assigned");
                    id++;
                }
                if (id == devList.size()) {
                    id = 0;
                }
            }

            PrintWriter pw = new PrintWriter(issuesDirectory);
            for (Issue issueItem : issueList) {
                pw.println(issueItem.getTitle() + " "
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
        copyFile(issuesDirectory, prevAssignment);
        return issueList;
    }

    public void copyFile(File source, File dest) {
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

    public void moveFile(File todayTxt, File oldYesterdayTxt) {
        copyFile(todayTxt, oldYesterdayTxt);
        deleteFile(todayTxt);
    }

    public void deleteFile(File todayTxt) {
        if (todayTxt.delete()) {
            System.out.println("Deleted File : " + todayTxt.getAbsolutePath());
        } else {
            System.out.println("Delete operation failed.");
        }
    }

    public void setThreshold(String devName, ArrayList<Developer> devList) {
        for (Developer tempDev : devList) {
            if (tempDev.getName().equalsIgnoreCase(devName)) {
                tempDev.setThreshold((tempDev.getThreshold() - 1));
            }
        }
    }

    public boolean ifNotIgnoredIssue(String fileRow,
            ArrayList<String> ignoredDataArrayList) {
        for (String ignoredData : ignoredDataArrayList)
            if (fileRow.contains(ignoredData))
                return false;
        return true;
    }
}
