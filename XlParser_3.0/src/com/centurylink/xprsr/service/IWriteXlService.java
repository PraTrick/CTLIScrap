package com.centurylink.xprsr.service;

import java.util.ArrayList;
import java.util.TreeMap;

public interface IWriteXlService {

    void write(String browseFile, String sheetName);

    void writeToNewSheet(String browseFile, String sheetName);

    void writeToNewSheet(String browseFile, TreeMap<Integer, ArrayList<String>> restrictedTickets,
            String sheetName);
}
