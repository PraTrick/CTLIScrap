package com.centurylink.TaskReport.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.centurylink.TaskReport.dto.DetailedSummaryReport;
import com.centurylink.TaskReport.dto.ProjectEffortReport;
import com.centurylink.TaskReport.service.IReadXlWorkbook;

/**
 * Name: ReadHeaderRow
 * 
 * Purpose: This Class has function ReadHeader which when called reads the
 * Header Row from the ProjectTaskReport Excel file. ProjectTaskReport Excel is
 * opened. All the Headers(i.e the Main Headings of each Column in the Excel)
 * are stored.
 * 
 * Parameters for Function: NA
 * 
 * Return Value: HeaderRow
 **/

public class ReadXlWorkbook implements IReadXlWorkbook {

    private Map<String, DetailedSummaryReport> data_table = null;
    private ArrayList<ProjectEffortReport> data_table2 = null;

    private void ReadPERSheet(ArrayList<String> header_row2,
            XSSFSheet sheetname2, int header_row_pos2) {
        int b = sheetname2.getLastRowNum();
        for (int i = header_row_pos2 + 1; i <= b; i++) {
            ProjectEffortReport tempProjectEffortReport = new ProjectEffortReport();
            Row row1 = sheetname2.getRow(i);

            tempProjectEffortReport.setResource_Name(String.valueOf(row1
                    .getCell((int) header_row2.indexOf("Resource Name"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectEffortReport.setProject_Name(String.valueOf(row1
                    .getCell((int) header_row2.indexOf("Project Name"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectEffortReport.setProject_Type(String.valueOf(row1
                    .getCell((int) header_row2.indexOf("Project Type"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectEffortReport.setMilestone(String.valueOf(row1.getCell(
                    (int) header_row2.indexOf("Milestone"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectEffortReport.setTask_Type(String.valueOf(row1.getCell(
                    (int) header_row2.indexOf("Task Type"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectEffortReport.setTask_Identifier(String.valueOf(row1
                    .getCell((int) header_row2.indexOf("Task Identifier"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectEffortReport.setActual_Effort(String.valueOf(row1
                    .getCell((int) header_row2.indexOf("Actual Effort"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectEffortReport.setEffort_Date(String.valueOf(row1.getCell(
                    (int) header_row2.indexOf("Effort Date"),
                    Row.RETURN_BLANK_AS_NULL)));

            data_table2.add(tempProjectEffortReport);
        }
    }

    private void ReadDSRSheet(ArrayList<String> header_row1,
            XSSFSheet sheetname1, int header_row_pos1) {
        // To get the Last Row number in the sheet
        int b = sheetname1.getLastRowNum();
        // Loop through each row and store the data of each column in the
        // tempPtempProjectTaskReport object
        for (int i = header_row_pos1 + 1; i <= b; i++) {
            DetailedSummaryReport tempProjectTaskReport = new DetailedSummaryReport();
            Row row1 = sheetname1.getRow(i);

            tempProjectTaskReport.setProject_name(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Project Name"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport
                    .setLead(String.valueOf(row1.getCell(
                            (int) header_row1.indexOf("Lead"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setManager(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Manager"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setTask_item_no(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Task Item No"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport
                    .setType(String.valueOf(row1.getCell(
                            (int) header_row1.indexOf("Type"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setTicket_category(String.valueOf(row1
                    .getCell((int) header_row1.indexOf("Ticket_Category"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setTask_type(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Task Type"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setSeverity(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Severity"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setDate_range_effort(String.valueOf(row1
                    .getCell((int) header_row1.indexOf("Date Range Effort"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setApplication(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Application"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setComments(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Comments"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setEnhanced_fp(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Enhanced FP"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setEffort_till_date(String.valueOf(row1
                    .getCell((int) header_row1.indexOf("Effort Till Date"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setSubmit_name(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Submit Name"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setStatus(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Status"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setWorked_by(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Worked By"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setPlanned_start_date(String.valueOf(row1
                    .getCell((int) header_row1.indexOf("Planned_Start_Date"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setPlanned_end_date(String.valueOf(row1
                    .getCell((int) header_row1.indexOf("Planned_End_Date"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setActual_start_date(String.valueOf(row1
                    .getCell((int) header_row1.indexOf("Actual_Start_Date"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setActual_end_date(String.valueOf(row1
                    .getCell((int) header_row1.indexOf("Actual_End_Date"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setTask_create_date(String.valueOf(row1
                    .getCell((int) header_row1.indexOf("Task_Create_Date"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setTask_closed_date(String.valueOf(row1
                    .getCell((int) header_row1.indexOf("Task_Closed_Date"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setPlanned_effort(String.valueOf(row1
                    .getCell((int) header_row1.indexOf("Planned_Effort"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setPercentage_completion(String.valueOf(row1
                    .getCell(
                            (int) header_row1.indexOf("Percentage_Completion"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setIteration(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Iteration"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport
                    .setPoc(String.valueOf(row1.getCell(
                            (int) header_row1.indexOf("POC"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setNormalizeequivalent(String.valueOf(row1
                    .getCell((int) header_row1.indexOf("Normalize Equivalent"),
                            Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setComplexity(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("Complexity"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setfP_Size(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("FP Size"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setsP_Size(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("SP Size"),
                    Row.RETURN_BLANK_AS_NULL)));
            tempProjectTaskReport.setvP_Size(String.valueOf(row1.getCell(
                    (int) header_row1.indexOf("VP Size"),
                    Row.RETURN_BLANK_AS_NULL)));

            data_table.put(
                    String.valueOf(tempProjectTaskReport.getTask_item_no()),
                    tempProjectTaskReport);
        }
    }

    public Map<String, DetailedSummaryReport> ReadDetailedSumReport(File file,
            String sheetname1, int header_row_pos1) {
        java.util.Date date = new java.util.Date();
        System.out.println("Inside ReadHeader class: "
                + new Timestamp(date.getTime()));
        ArrayList<String> HeaderRow = new ArrayList<String>();
        XSSFSheet sheet = null;
        Row row = null;
        FileInputStream inputFile = null;
        XSSFWorkbook workbook = null;
        try {
            inputFile = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputFile);
            sheet = workbook.getSheet(sheetname1);
            row = sheet.getRow(header_row_pos1);

            // For each row, iterate through all the columns
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++)
                HeaderRow.add(String.valueOf(row.getCell(i)));

            data_table = new TreeMap<String, DetailedSummaryReport>();
            ReadDSRSheet(HeaderRow, sheet, header_row_pos1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Done: " + new Timestamp(date.getTime()));
            if (file != null) {
                workbook = null;
                try {
                    inputFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data_table;
    }

    @Override
    public ArrayList<ProjectEffortReport> ReadProjectEffortReport(File file,
            String sheetname1, int header_row_pos2) {
        java.util.Date date = new java.util.Date();
        System.out.println("Inside ReadHeader class: "
                + new Timestamp(date.getTime()));
        ArrayList<String> HeaderRow = new ArrayList<String>();
        XSSFSheet sheet = null;
        Row row = null;
        FileInputStream inputFile = null;
        XSSFWorkbook workbook = null;
        try {
            inputFile = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputFile);
            sheet = workbook.getSheet(sheetname1);
            row = sheet.getRow(header_row_pos2);

            // For each row, iterate through all the columns
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++)
                HeaderRow.add(String.valueOf(row.getCell(i)));

            data_table2 = new ArrayList<ProjectEffortReport>();
            ReadPERSheet(HeaderRow, sheet, header_row_pos2);
        } catch (FileNotFoundException e1) {
            System.out.println("File not found!");
            e1.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Execption occured!");
            e.printStackTrace();
        } finally {
            try {
                inputFile.close();
            } catch (IOException e) {
                System.out.println("Unable to close the file!");
                e.printStackTrace();
            }
        }
        return data_table2;
    }
}
