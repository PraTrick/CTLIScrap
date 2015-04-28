package com.centurylink.TaskReport.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.centurylink.TaskReport.dto.DetailedSummaryReport;
import com.centurylink.TaskReport.service.IWriteXlWorkbook;

/**
 * Name: WriteOutputExcel
 * 
 * Purpose: This Class has function WriteOutExcel which when called Creates a
 * new Excel file in which all the Reuslts are saved. All the results after
 * running rules that are stored in data variable are stored in an Excel file.
 * 
 * Parameters for Function: NA
 * 
 * Return Value: NA
 **/

public class WriteXlWorkbook implements IWriteXlWorkbook {
    private InputStream input = null;
    private FileOutputStream out = null;

    public void WriteOutExcel(String taskReport_TaskType,
            String taskReport_Complexity, String taskReport_Status,
            Map<String, DetailedSummaryReport> result, int writeCounter) {
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        try {
            Set<String> keyset = result.keySet();
            if (writeCounter == 0) {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(taskReport_TaskType + "  "
                        + taskReport_Complexity);
            } else {
                workbook = new XSSFWorkbook(new FileInputStream(
                        "C:\\Workspace\\Sensitivity Report.xlsx"));
                sheet = workbook.getSheet(taskReport_TaskType + "  "
                        + taskReport_Complexity);
            }

            int rownum = 0;
            Row row;
            Cell cell;
            row = sheet.createRow(rownum++);
            int cellnum = 0;

            for (String key : keyset) {
                row = sheet.createRow(rownum++);
                DetailedSummaryReport objArr = result.get(key);
                cellnum = 0;
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getProject_name()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getLead()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getManager()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getTask_item_no()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getType()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getTicket_category()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getTask_type()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getSeverity()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getDate_range_effort()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getApplication()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getComments()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getEnhanced_fp()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getEffort_till_date()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getSubmit_name()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getStatus()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getWorked_by()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getPlanned_start_date()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getPlanned_end_date()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getActual_start_date()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getActual_end_date()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getTask_create_date()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getTask_closed_date()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getPlanned_effort()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr
                        .getPercentage_completion()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getIteration()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getPoc()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr
                        .getNormalizeequivalent()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getComplexity()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getfP_Size()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getsP_Size()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getvP_Size()));
                cell = row.createCell(cellnum++);
                cell.setCellValue(String.valueOf(objArr.getSubProcess()));

            }
            out = new FileOutputStream("C:\\Workspace\\Sensitivity Report.xlsx");
            workbook.write(out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
