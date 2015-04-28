package com.centurylink.xprsr.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.centurylink.xprsr.action.ReadAction;
import com.centurylink.xprsr.service.IReadXlService;

/**
 * @author Anurag Chowdhury
 * @author Prateek Gupta
 * @version 1.0
 * @since JUNE 2013
 */
public class ReadXlService implements IReadXlService {

    private static Log logger = LogFactory.getLog(ReadAction.class);

    /**
     * Parses the input Excel Workbook using
     * "Apache POI - the Java API for Microsoft Documents"
     * 
     * @param inputFile
     *            Path of the incoming Excel Workbook
     * @param sheetNo
     *            Sheet of the Excel Workbook
     */
    @Override
    public TreeMap<Integer, ArrayList<String>> read(String inputFile,
            String sheetName) throws IOException {

        logger.info("reading from " + inputFile + " in ReadXmlService");

        TreeMap<Integer, ArrayList<String>> table = new TreeMap<Integer, ArrayList<String>>();

        try {
            XSSFWorkbook w = new XSSFWorkbook(new FileInputStream(new File(
                    inputFile)));
            XSSFSheet sheet = w.getSheet(sheetName);
            int rows = sheet.getPhysicalNumberOfRows();

            for (int j = 0; j < rows; j++) {
                XSSFRow rowNo = sheet.getRow(j);
                ArrayList<String> row = new ArrayList<String>();

                int cellNo = rowNo.getLastCellNum() - rowNo.getFirstCellNum();

                for (int i = 0; i < cellNo; i++) {
                    XSSFCell cell = rowNo.getCell(i,
                            XSSFRow.CREATE_NULL_AS_BLANK);

                    switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
                        row.add(cell.getRichStringCellValue().getString());
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
                        row.add(" ");
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            DateFormat parser = new SimpleDateFormat(
                                    "dd-MMM-yyyy");
                            DateFormat formatter = new SimpleDateFormat(
                                    "MM/dd/yyyy");
                            String initialDeliveryDate = cell.toString();
                            String finalDeliveryDate = new String();
                            try {
                                finalDeliveryDate = formatter.format(parser
                                        .parse(initialDeliveryDate));

                            } catch (ParseException e) {
                                System.out.println("DATE got FUCKED UP!!");
                                ;
                            }
                            row.add(finalDeliveryDate);
                        } else {
                            row.add(Double.toString(cell.getNumericCellValue()));
                        }
                        break;

                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        row.add(Boolean.toString(cell.getBooleanCellValue()));
                        break;
                    case XSSFCell.CELL_TYPE_FORMULA:
                        row.add(cell.getCellFormula());
                        break;
                    default:
                        System.out.println("Cell type different");
                    }

                }
                table.put(j, row);
            }
        }

        catch (IOException e) {
            System.out.println("File does not exist! " + e.getMessage());
        }

        return table;
    }

    /**
     * Compares the "DATA" sheet and the "REPORT" sheet of the Excel Workbook
     * 
     * @param readPrev
     *            TreeMap of the assigned list in the "REPORT" sheet
     * @param readCurrent
     *            TreeMap of the assigned list in the "DATA" sheet
     */
    @Override
    public TreeMap<Integer, ArrayList<String>> compareSheets(
            TreeMap<Integer, ArrayList<String>> readPrev,
            TreeMap<Integer, ArrayList<String>> readCurrent) {

        TreeMap<Integer, ArrayList<String>> modifiedSheet = new TreeMap<Integer, ArrayList<String>>();

        for (Entry<Integer, ArrayList<String>> readCurrentRow : readCurrent
                .entrySet()) {
            boolean flag = false;
            for (Entry<Integer, ArrayList<String>> readPrevRow : readPrev
                    .entrySet()) {
                if (readCurrentRow.getValue().get(1)
                        .equalsIgnoreCase(readPrevRow.getValue().get(1))) {
                    modifiedSheet.put(readCurrentRow.getKey(),
                            readPrevRow.getValue());
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                readCurrentRow.getValue().set(0, " ");
                modifiedSheet.put(readCurrentRow.getKey(),
                        readCurrentRow.getValue());
            }
        }

        return modifiedSheet;
    }

    /**
     * Modifies the "DATA" sheet of the Excel Workbook for to include ticket
     * status as the new column for "REPORT" sheet
     * 
     * @param table
     *            TreeMap of the assigned list in the "DATA" sheet
     */
    @Override
    public TreeMap<Integer, ArrayList<String>> modifyTable(
            TreeMap<Integer, ArrayList<String>> table) {

        @SuppressWarnings("unchecked")
        TreeMap<Integer, ArrayList<String>> refTable = (TreeMap<Integer, ArrayList<String>>) table
                .clone();
        for (Entry<Integer, ArrayList<String>> userThresholdEntry : refTable
                .entrySet()) {
            if (userThresholdEntry.getKey() == 0) {
                userThresholdEntry.getValue().set(0, "WORK STATUS");
            } else
                userThresholdEntry.getValue().set(0, " ");
        }

        return refTable;
    }

    @Override
    public TreeMap<Integer, ArrayList<String>> readEscalatedTickets(
            TreeMap<Integer, ArrayList<String>> tableData) {

        TreeMap<Integer, ArrayList<String>> escalatedTickets = new TreeMap<Integer, ArrayList<String>>();
        Integer i = 0;

        for (Entry<Integer, ArrayList<String>> sheetEntry : tableData
                .entrySet()) {
            if (sheetEntry.getKey() != 0) {
                if (!sheetEntry.getValue().get(2).equalsIgnoreCase(" ")
                        && !sheetEntry.getValue().get(7).equalsIgnoreCase("4")
                        && !escalatedTickets.get(i - 1).get(1)
                                .equalsIgnoreCase(sheetEntry.getValue().get(1)))
                    escalatedTickets.put(i++, sheetEntry.getValue());
            } else
                escalatedTickets.put(i++, sheetEntry.getValue());
        }

        return escalatedTickets;
    }
}