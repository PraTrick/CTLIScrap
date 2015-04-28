package com.centurylink.xprsr.action;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;

import com.centurylink.xprsr.service.IReadXlService;
import com.centurylink.xprsr.service.impl.ReadXlService;

public class HomeAction {

    TreeMap<Integer, ArrayList<String>> tableData = new TreeMap<Integer, ArrayList<String>>();
    TreeMap<Integer, ArrayList<String>> escalatedTickets = new TreeMap<Integer, ArrayList<String>>();
    
    public TreeMap<Integer, ArrayList<String>> getEscalatedTickets() {
        return escalatedTickets;
    }

    public void setEscalatedTickets(TreeMap<Integer, ArrayList<String>> escalatedTickets) {
        this.escalatedTickets = escalatedTickets;
    }
    
    public String execute() {
        return "success";

    }

    public String getEscalatedTicketList() {
        IReadXlService readXl = new ReadXlService();
        String dafaultPath = null;
        {
            Properties prop = new Properties();
            InputStream input = null;

            try {

                /*String propertyHome = System.getenv("CATALINA_HOME");*/
                
                /*String propertyHome = "C:\\";*/
                
                /*if(null == propertyHome)
                    propertyHome  =  System.getProperty("PROPERTY_HOME");*/
                
                /*String filename = propertyHome+"\\webapps\\config.properties";*/
                String filename = "config.properties";
                
                input = HomeAction.class.getClassLoader().getResourceAsStream(
                        filename);
                if (input == null) {
                    System.out.println("Sorry, unable to find " + filename);
                    return null;
                }

                // load a properties file from class path, inside static method
                prop.load(input);

                // get the property value and print it out
                dafaultPath = prop.getProperty("defaultPath").toString();
                tableData = readXl.read(dafaultPath, "DATA");
                escalatedTickets = readXl.readEscalatedTickets(tableData);

            } catch (IOException ex) {
                System.out.println("Xcel file path not found! "
                        + ex.getMessage());
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
            return "success";
        }
    }
}
