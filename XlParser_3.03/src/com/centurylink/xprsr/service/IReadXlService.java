package com.centurylink.xprsr.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public interface IReadXlService {
    public TreeMap<Integer, ArrayList<String>> readSheet(File inputFile,
            String sheetName) throws IOException;
}
