package com.centurylink.xprsr.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.centurylink.xprsr.service.IReadXlService;
import com.centurylink.xprsr.service.impl.AssignTickets;
import com.centurylink.xprsr.service.impl.ReadXlService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Anurag Chowdhury
 * @author Prateek Gupta
 * @version 1.0
 * @since JUNE 2013
 */
@SuppressWarnings("serial")
public class ReadAction extends ActionSupport {
    
    private File fileUpload;
    private String fileUploadContentType;
    private String fileUploadFileName;
    private Map<String, Object> session = ActionContext.getContext()
            .getSession();
    private TreeMap<Integer, ArrayList<String>> parsedSheetDATA = null;
    private TreeMap<Integer, ArrayList<String>> parsedSheetTHRESHOLD = null;
    private TreeMap<Integer, ArrayList<String>> parsedSheetCATEGORIES = null;
    private TreeMap<Integer, ArrayList<String>> parsedSheetTICKET_STATUS = null;
    private TreeMap<Integer, ArrayList<String>> parsedSheetRESTRICTED_KEYWORDS = null;
    private TreeMap<Integer, ArrayList<String>> parsedSheetMAIL = null;
    private static Log logger = LogFactory.getLog(HomeAction.class);

    public String getFileUploadContentType() {
        return fileUploadContentType;
    }

    public void setFileUploadContentType(String fileUploadContentType) {
        this.fileUploadContentType = fileUploadContentType;
    }

    public String getFileUploadFileName() {
        return fileUploadFileName;
    }

    public void setFileUploadFileName(String fileUploadFileName) {
        this.fileUploadFileName = fileUploadFileName;
    }

    public File getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(File fileUpload) {
        this.fileUpload = fileUpload;
    }

    /**
     * On "success", navigates to manualAssignedXl.jsp
     * 
     * @see AssignTickets#getNames(TreeMap)
     * @see AssignTickets#getStati(TreeMap)
     * @see ReadXlService#read(String, Integer)
     * @see ReadXlService#compareSheets(TreeMap, TreeMap)
     * @see ReadXlService#modifyTable(TreeMap)
     */
    public String uploadFile() {
        IReadXlService readXl = new ReadXlService();
        try {
            parsedSheetDATA = new TreeMap<Integer, ArrayList<String>>();
            parsedSheetTHRESHOLD = new TreeMap<Integer, ArrayList<String>>();
            parsedSheetCATEGORIES = new TreeMap<Integer, ArrayList<String>>();
            parsedSheetTICKET_STATUS = new TreeMap<Integer, ArrayList<String>>();
            parsedSheetRESTRICTED_KEYWORDS = new TreeMap<Integer, ArrayList<String>>();
            parsedSheetMAIL = new TreeMap<Integer, ArrayList<String>>();
            
            parsedSheetDATA = readXl.readSheet(fileUpload, "DATA");
            logger.info("Read sheet 'DATA' from file " + fileUploadFileName);
            
            parsedSheetTHRESHOLD = readXl.readSheet(fileUpload, "THRESHOLD");
            logger.info("Read sheet 'THRESHOLD' from file " + fileUploadFileName);
            
            parsedSheetCATEGORIES = readXl.readSheet(fileUpload, "CATEGORIES");
            logger.info("Read sheet 'CATEGORIES' from file " + fileUploadFileName);
            
            parsedSheetTICKET_STATUS = readXl.readSheet(fileUpload, "TICKET_STATUS");
            logger.info("Read sheet 'TICKET_STATUS' from file " + fileUploadFileName);
            
            parsedSheetRESTRICTED_KEYWORDS = readXl.readSheet(fileUpload, "RESTRICTED_KEYWORDS");
            logger.info("Read sheet 'RESTRICTED_KEYWORDS' from file " + fileUploadFileName);
            
            parsedSheetMAIL = readXl.readSheet(fileUpload, "MAIL");
            logger.info("Read sheet 'MAIL' from file " + fileUploadFileName);
            
            session.put("sheetDATA", parsedSheetDATA);
            session.put("sheetTHRESHOLD", parsedSheetTHRESHOLD);
            session.put("sheetCATEGORIES", parsedSheetCATEGORIES);
            session.put("sheetTICKET_STATUS", parsedSheetTICKET_STATUS);
            session.put("sheetRESTRICTED_KEYWORDS", parsedSheetRESTRICTED_KEYWORDS);
            session.put("sheetMAIL", parsedSheetMAIL);
        } catch (FileNotFoundException e) {
            logger.error("File not found at path speciifed! " + e.getMessage());
        } catch (IOException e) {
            logger.error("I/O Exception occured. Bad file sectors found! "
                    + e.getMessage());
        }
        return "success";
    }
}
