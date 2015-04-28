package com.centurylink.xprsr.service;

import java.util.ArrayList;
import java.util.TreeMap;

import com.centurylink.xprsr.dto.BotRepData;
import com.centurylink.xprsr.dto.ValidationData;
import com.centurylink.xprsr.dto.WeeklyRepData;

public interface IReportService {

    ArrayList<WeeklyRepData> getReport(
            TreeMap<Integer, ArrayList<String>> read,
            TreeMap<Integer, ArrayList<String>> nameList,
            TreeMap<Integer, ArrayList<String>> issueStatusList,
            String fromDate, String toDate);

    ArrayList<ValidationData> getStatusMismatch(
            TreeMap<Integer, ArrayList<String>> read,
            TreeMap<Integer, ArrayList<String>> issueStatusList);
    
    ArrayList<ValidationData> getMissingResolutionDate(
            TreeMap<Integer, ArrayList<String>> read,
            TreeMap<Integer, ArrayList<String>> issueStatusList);
    
    ArrayList<ValidationData> getMissingTAT(
            TreeMap<Integer, ArrayList<String>> read,
            TreeMap<Integer, ArrayList<String>> issueStatusList);
    
    ArrayList<ValidationData> getZeroWRNo(
            TreeMap<Integer, ArrayList<String>> read);
    
    ArrayList<ValidationData> getRemedyCreatedResolved(
            TreeMap<Integer, ArrayList<String>> read);

    ArrayList<BotRepData> getBotReport(
            TreeMap<Integer, ArrayList<String>> read,
            TreeMap<Integer, ArrayList<String>> read2, String fromDate,
            String toDate);
}
