package com.centurylink.xprsr.action;

import java.util.ArrayList;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.centurylink.xprsr.service.IWriteXlService;
import com.centurylink.xprsr.service.impl.WriteXlService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Anurag Chowdhury
 * @author Prateek Gupta
 * @version 1.0
 * @since JUNE 2013
 */
public class WriteAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private String browseFile = (String) ServletActionContext.getRequest()
            .getSession().getAttribute("inputFilePath");
    TreeMap<Integer, ArrayList<String>> table = new TreeMap<Integer, ArrayList<String>>();
    String error;
    String write;

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
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

    @SuppressWarnings("unused")
    private static Log logger = LogFactory.getLog(ReadAction.class);

    /**
     * On "success", navigates to thanks.jsp
     * 
     * @see WriteXlService#write(String, Integer)
     */
    public String execute() {
        IWriteXlService writeXL = new WriteXlService();
        writeXL.write(browseFile, "DATA");
        return "success";

    }

    /**
     * On "success", navigates to thanks.jsp
     * 
     * @see WriteXlService#writeToNewSheet(String)
     */
    public String writeManualExcel() {
        IWriteXlService writeXL = new WriteXlService();
        writeXL.writeToNewSheet(browseFile, "REPORT");
        return "success";
    }

}
