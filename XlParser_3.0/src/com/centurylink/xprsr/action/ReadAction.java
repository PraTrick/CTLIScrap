package com.centurylink.xprsr.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.centurylink.xprsr.service.IAssignTickets;
import com.centurylink.xprsr.service.IReadXlService;
import com.centurylink.xprsr.service.impl.AssignTickets;
import com.centurylink.xprsr.service.impl.ReadXlService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Anurag Chowdhury
 * @author Prateek Gupta
 * @version 1.0
 * @since JUNE 2013
 */
public class ReadAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private String browseFile = null;
    String error;
    String assignType;
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<String> statusList = new ArrayList<String>();
    TreeMap<Integer, ArrayList<String>> table = new TreeMap<Integer, ArrayList<String>>();
    TreeMap<Integer, ArrayList<String>> modifiedTable = new TreeMap<Integer, ArrayList<String>>();

    public ArrayList<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(ArrayList<String> statusList) {
        this.statusList = statusList;
    }

    public TreeMap<Integer, ArrayList<String>> getModifiedTable() {
        return modifiedTable;
    }

    public void setModifiedTable(
            TreeMap<Integer, ArrayList<String>> modifiedTable) {
        this.modifiedTable = modifiedTable;
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public void setNameList(ArrayList<String> nameList) {
        this.nameList = nameList;
    }

    public String getAssignType() {
        return assignType;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getBrowseFile() {
        return browseFile;
    }

    public void setBrowseFile(String browseFile) {
        this.browseFile = browseFile;
    }

    public TreeMap<Integer, ArrayList<String>> getTable() {
        return table;
    }

    public void setTable(TreeMap<Integer, ArrayList<String>> table) {
        this.table = table;
    }

    private static Log logger = LogFactory.getLog(ReadAction.class);

    /**
     * On "success", navigates to manualAssignedXl.jsp
     * 
     * @see AssignTickets#getNames(TreeMap)
     * @see AssignTickets#getStati(TreeMap)
     * @see ReadXlService#read(String, Integer)
     * @see ReadXlService#compareSheets(TreeMap, TreeMap)
     * @see ReadXlService#modifyTable(TreeMap)
     */
    public String execute() {
        IReadXlService readXl = new ReadXlService();
        ServletActionContext.getRequest().getSession()
                .setAttribute("inputFilePath", browseFile);
        logger.info("Reading from : "
                + (String) ServletActionContext.getRequest().getSession()
                        .getAttribute("inputFilePath"));
        Properties prop = new Properties();
        try {

            String propertyHome = System.getenv("CATALINA_HOME");
            if (null == propertyHome) {
                // This is a system property that is passed
                // using the -D option in the Tomcat startup script
                propertyHome = System.getProperty("PROPERTY_HOME");
            }

            String filePath = propertyHome + "\\properties\\config.properties";
            FileInputStream fis = new FileInputStream(filePath);
            prop.load(fis);

            // get the property value and print it out

            if (browseFile == null || browseFile.length() == 0
                    || !browseFile.contains(":")) {
                browseFile = prop.getProperty("defaultPath").toString();
                ;
            }

            else if (browseFile.length() >= 4
                    && !browseFile.substring(browseFile.length() - 4,
                            browseFile.length()).equalsIgnoreCase("xlsx")) {
                throw new InvalidFormatException(
                        "Invalid File Format (Only .xlsx is supported)");
            }

            IAssignTickets countTickets = new AssignTickets();
            nameList = countTickets.getNames(readXl.read(browseFile,
                    "THRESHOLD"));
            statusList = countTickets.getStati(readXl.read(browseFile,
                    "WORK_STATUS"));
            table = readXl.read(browseFile, "DATA");
            if (assignType.equalsIgnoreCase("manual")) {

                XSSFWorkbook w = new XSSFWorkbook(new FileInputStream(
                        browseFile));
                if (w.getSheet("REPORT") != null) {
                    modifiedTable = readXl.compareSheets(
                            readXl.read(browseFile, "REPORT"),
                            readXl.read(browseFile, "DATA"));
                }

                else
                    modifiedTable = readXl.modifyTable(table);
                    
                return "success";
            }

            else {
                return "doubleSuccess";
            }

        }

        catch (NullPointerException e) {
            error = "Excel Parsing Failed!! -- Null Pointer Exception occurred ";
            return "error";
        }

        catch (InvalidFormatException e) {
            error = "Invalid FileFormat";
            return "error";
        }

        catch (IOException e) {
            error = "File does not exist or Invalid File";
            return "error";
        }
    }

}
