package com.centurylink.xprsr.service;

import java.util.ArrayList;
import java.util.TreeMap;

public interface IGetSpecificIssues {
    void getGhostOrderissues(TreeMap<Integer, ArrayList<String>> dataSet); 
}
