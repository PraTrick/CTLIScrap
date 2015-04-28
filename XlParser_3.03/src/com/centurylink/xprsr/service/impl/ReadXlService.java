package com.centurylink.xprsr.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;

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
    private boolean rowIsEmpty = false;

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
    public TreeMap<Integer, ArrayList<String>> readSheet(File inputFile,
            String sheetName) throws IOException {

        logger.info("Reading from " + inputFile);

        TreeMap<Integer, ArrayList<String>> table = new TreeMap<Integer, ArrayList<String>>();

        try {
            XSSFWorkbook w = new XSSFWorkbook(new FileInputStream(inputFile));
            XSSFSheet sheet = w.getSheet(sheetName);
            int rows = sheet.getPhysicalNumberOfRows();

            for (int j = 0; j < rows; j++) {
                XSSFRow rowNo = sheet.getRow(j);
                ArrayList<String> row = new ArrayList<String>();

                int cellNo = rowNo.getLastCellNum() - rowNo.getFirstCellNum();

                for (int i = 0; i < cellNo; i++) {
                    XSSFCell cell = rowNo.getCell(i,
                            XSSFRow.CREATE_NULL_AS_BLANK);

                    if ((cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) && (i == 0)) {
                        rowIsEmpty = true;
                        break;
                    }

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
                if(rowIsEmpty == false)
                    table.put(j, row);
                else
                    rowIsEmpty = false;
            }
        }

        catch (IOException e) {
            System.out.println("File does not exist! " + e.getMessage());
        }
        return table;
    }
}