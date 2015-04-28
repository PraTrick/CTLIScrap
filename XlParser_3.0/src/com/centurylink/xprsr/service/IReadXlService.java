package com.centurylink.xprsr.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public interface IReadXlService {
    public TreeMap<Integer, ArrayList<String>> read(String inputFile,
            String sheetName) throws IOException;

    public TreeMap<Integer, ArrayList<String>> readEscalatedTickets(
            TreeMap<Integer, ArrayList<String>> tableData);

    public TreeMap<Integer, ArrayList<String>> compareSheets(
            TreeMap<Integer, ArrayList<String>> read,
            TreeMap<Integer, ArrayList<String>> read2);

    public TreeMap<Integer, ArrayList<String>> modifyTable(
            TreeMap<Integer, ArrayList<String>> table);

}
