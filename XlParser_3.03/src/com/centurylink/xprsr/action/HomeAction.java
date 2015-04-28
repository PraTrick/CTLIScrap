package com.centurylink.xprsr.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HomeAction {

    private InputStream fileInputStream;
    private String downloadTemp;

    public String execute() {
        return "success";
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public String getDownloadTemp() {
        return downloadTemp;
    }

    public void setDownloadTemp(String downloadTemp) {
        this.downloadTemp = downloadTemp;
    }

    public String downloadFile() {
        try {
            Properties prop = new Properties();
            String propertyHome = System.getenv("CATALINA_HOME");
            if (null == propertyHome) {
                propertyHome = System.getProperty("PROPERTY_HOME");
            }

            String filePath = propertyHome + "\\properties\\config.properties";
            FileInputStream fis = new FileInputStream(filePath);
            prop.load(fis);

            downloadTemp = prop.getProperty("defaultPath").toString();
            fileInputStream = new FileInputStream(new File(downloadTemp));

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "success";
    }
}
