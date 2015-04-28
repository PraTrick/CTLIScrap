package com.centurylink.TaskReport.action;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.centurylink.TaskReport.dto.ProjectEffortReport;
import com.centurylink.TaskReport.dto.SensitivityReportData;
import com.centurylink.TaskReport.dto.DetailedSummaryReport;
import com.centurylink.TaskReport.service.impl.ImplementRules;
import com.centurylink.TaskReport.service.impl.ReadXlWorkbook;

public class SummaryReportAction {
    private Map<String, DetailedSummaryReport> detailedSummaryReport = null;
    private ArrayList<ProjectEffortReport> projectEffortReport = null;
    private String sheetDSR = "Task Items";
    private String sheetPER = "Effort Details";
    private int headerRowDSR = 1, headerRowPER = 0;
    private ReadXlWorkbook readXlWorkbook = null;
    private ImplementRules implementRules = null;
    private List<Integer> nonZeroTasksList = new ArrayList<Integer>();

    public List<Integer> getNonZeroTasksList() {
        return nonZeroTasksList;
    }
    
    public void setNonZeroTasksList(List<Integer> nonZeroTasksList) {
        this.nonZeroTasksList = nonZeroTasksList;
    }

    public String generateReport(List<File> fileUpload,
            Collection<SensitivityReportData> row, String toDate, String fromDate) {
        detailedSummaryReport = new TreeMap<String, DetailedSummaryReport>();
        projectEffortReport = new ArrayList<ProjectEffortReport>();
        java.util.Date date = new java.util.Date();
        System.out.println("Start: " + new Timestamp(date.getTime()));

        readXlWorkbook = new ReadXlWorkbook();
        detailedSummaryReport = readXlWorkbook.ReadDetailedSumReport(fileUpload.get(1),
                sheetDSR, headerRowDSR);

        System.out.println("Got HEADER row of Effort Report: "
                + new Timestamp(date.getTime()));

        projectEffortReport = readXlWorkbook.ReadProjectEffortReport(fileUpload.get(0),
                sheetPER, headerRowPER);

        System.out.println("Got HEADER row of Detailed Sum Report: "
                + new Timestamp(date.getTime()));

        implementRules = new ImplementRules();
        nonZeroTasksList = implementRules.getResult(detailedSummaryReport, projectEffortReport, row);

        System.out.println("Implemented Rules : "
                + new Timestamp(date.getTime()));
        
        return "success";
    }
}