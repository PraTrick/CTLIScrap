package com.centurylink.xprsr.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.centurylink.xprsr.dto.GhostOrderData;
import com.centurylink.xprsr.service.IGetSpecificIssues;
import com.centurylink.xprsr.service.IUploadService;

@SuppressWarnings("unused")
public class GetSpecificIssues implements IGetSpecificIssues {

    @Override
    public void getGhostOrderissues(TreeMap<Integer, ArrayList<String>> dataSet) {
        /*
         * ArrayList<GhostOrderData> dataArray = new
         * ArrayList<GhostOrderData>(); GhostOrderData data = null; Pattern
         * pattern = Pattern.compile("[1][0-9]{9,10}");
         */
        try {
            File issuesDirectory = new File("C:/ghostOrderIssues/dataSet.txt");
            PrintWriter pw = new PrintWriter(issuesDirectory);

            for (Entry<Integer, ArrayList<String>> dataSetEntry : dataSet
                    .entrySet()) {
                if (dataSetEntry.getKey() != 0) {
                    String developerString = dataSetEntry.getValue().get(2);
                    if (developerString.equalsIgnoreCase(" "))
                        developerString = "Jain.Akash";

                    developerString = developerString.replaceAll(", ", ".");

                    String summaryString = dataSetEntry.getValue().get(5)
                            .replaceAll("(\\r|\\n|\\r\\n)", "");
                    /*
                     * Matcher matcher = pattern.matcher(summaryString);
                     * 
                     * while (matcher.find()) { data = new GhostOrderData();
                     * data.setCaseID(dataSetEntry.getValue().get(1));
                     * data.setIndividual(dataSetEntry.getValue().get(2));
                     * data.setOrderID(matcher.group());
                     * data.setType(dataSetEntry.getValue().get(0));
                     * dataArray.add(data); }
                     */
                    pw.println(dataSetEntry.getValue().get(1) + "\t"
                            + developerString + "\t" + developerString + "\t"
                            + summaryString);
                }
            }
            pw.close();
            IUploadService uploadScr = new UploadService();
            uploadScr.uploadScript("C:/ghostOrderIssues/dataSet.txt");

            /*
             * PrintWriter pw; try { pw = new PrintWriter(issuesDirectory); for
             * (GhostOrderData issueItem : dataArray) {
             * pw.println(issueItem.getCaseID() + "\t" +
             * issueItem.getIndividual() + "\t" + issueItem.getOrderID()); }
             * pw.close();
             */
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to file!" + e.getMessage());
        }
    }
}
