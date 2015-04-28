package com.centurylink.xprsr.service.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.poi.xssf.usermodel.*;
import org.apache.struts2.ServletActionContext;

import com.centurylink.xprsr.service.IWriteXlService;

/**
 * @author Anurag Chowdhury
 * @author Prateek Gupta
 * @version 1.0
 * @since JUNE 2013
 */
public class WriteXlService implements IWriteXlService {

    /**
     * Overwrites the assigned list back to the "DATA" sheet of the Excel
     * Workbook using "Apache POI - the Java API for Microsoft Documents"
     * 
     * @param browseFile
     *            Path of the incoming Excel Workbook
     * @param sheetNo
     *            Sheet of the Excel Workbook
     */
    @SuppressWarnings("unchecked")
    @Override
    public void write(String browseFile, String sheetName) {
        TreeMap<Integer, ArrayList<String>> table = null;
        String defaultPath = null;
        Properties prop = new Properties();
        FileInputStream fis = null;

        table = (TreeMap<Integer, ArrayList<String>>) ServletActionContext
                .getRequest().getSession().getAttribute("parsedSheet");

        try {
            if (browseFile == null || browseFile.equalsIgnoreCase("")) {
                String propertyHome = System.getenv("CATALINA_HOME");
                if (null == propertyHome) {

                    propertyHome = System.getProperty("PROPERTY_HOME");
                }

                String filePath = propertyHome
                        + "\\properties\\config.properties";
                fis = new FileInputStream(filePath);
                prop.load(fis);
                defaultPath = prop.getProperty("defaultPath").toString();
                browseFile = defaultPath;
            }

            XSSFWorkbook w = new XSSFWorkbook(new FileInputStream(browseFile));

            XSSFSheet sheetData = w.getSheet(sheetName);

            for (Entry<Integer, ArrayList<String>> dataSheetEntry : table
                    .entrySet()) {
                XSSFRow row = sheetData.getRow(dataSheetEntry.getKey());

                for (int j = 0; j < dataSheetEntry.getValue().size(); j++) {
                    XSSFCell cell = row
                            .getCell(j, XSSFRow.CREATE_NULL_AS_BLANK);
                    cell.setCellValue(dataSheetEntry.getValue().get(j));
                }
            }

            FileOutputStream stream = new FileOutputStream(browseFile);
            w.write(stream);
            stream.close();
        }

        catch (IOException e) {
            System.out.println("File does not exist!");
        }
    }

    /**
     * Creates (for the first instance)/Overwrites the assigned list back to the
     * "REPORT" sheet of the Excel Workbook using
     * "Apache POI - the Java API for Microsoft Documents"
     * 
     * @param browseFile
     *            Path of the incoming Excel Workbook
     */
    @SuppressWarnings("unchecked")
    @Override
    public void writeToNewSheet(String browseFile, String sheetName) {

        TreeMap<Integer, ArrayList<String>> table = null;

        table = (TreeMap<Integer, ArrayList<String>>) ServletActionContext
                .getRequest().getSession().getAttribute("parsedSheet");

        try {
            XSSFWorkbook w = new XSSFWorkbook(new FileInputStream(browseFile));
            XSSFSheet sheetData = null;
            if (w.getSheet(sheetName) == null) {
                sheetData = w.createSheet(sheetName);
            } else {
                sheetData = w.getSheet(sheetName);
            }

            for (Entry<Integer, ArrayList<String>> dataSheetEntry : table
                    .entrySet()) {
                XSSFRow row = sheetData.createRow(dataSheetEntry.getKey());

                for (int j = 0; j < dataSheetEntry.getValue().size(); j++) {
                    XSSFCell cell = row.createCell(j);
                    cell.setCellValue(dataSheetEntry.getValue().get(j));
                }
            }

            FileOutputStream stream = new FileOutputStream(browseFile);
            w.write(stream);
            stream.close();
        }

        catch (IOException e) {
            System.out.println("File does not exist!");
        }
    }

    @Override
    public void writeToNewSheet(String browseFile,
            TreeMap<Integer, ArrayList<String>> restrictedTickets,
            String sheetName) {
        try {
            XSSFWorkbook w = new XSSFWorkbook(new FileInputStream(browseFile));
            XSSFSheet sheetData = null;
            if (w.getSheet(sheetName) == null) {
                sheetData = w.createSheet(sheetName);
            } else {
                Integer sheetIndex = w.getSheetIndex(sheetName);
                w.removeSheetAt(sheetIndex);
                sheetData = w.createSheet(sheetName);
            }

            Integer rowCount = 0;
            for (Entry<Integer, ArrayList<String>> dataSheetEntry : restrictedTickets
                    .entrySet()) {
                XSSFRow row = sheetData.createRow(rowCount);

                for (int j = 0; j < dataSheetEntry.getValue().size(); j++) {
                    XSSFCell cell = row.createCell(j);
                    cell.setCellValue(dataSheetEntry.getValue().get(j));
                }
                rowCount++;
            }

            FileOutputStream stream = new FileOutputStream(browseFile);
            w.write(stream);
            stream.close();
        }

        catch (IOException e) {
            System.out.println("File does not exist!");
        }

    }
}
