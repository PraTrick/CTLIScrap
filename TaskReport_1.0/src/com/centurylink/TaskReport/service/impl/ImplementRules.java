package com.centurylink.TaskReport.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.ArrayList;

import com.centurylink.TaskReport.dto.ProjectEffortReport;
import com.centurylink.TaskReport.dto.SensitivityReportData;
import com.centurylink.TaskReport.dto.DetailedSummaryReport;

/**
 * Name: ReadDataExcel
 * 
 * Purpose: This Class has function getResult which when called Compares the
 * data depending upon which rule is called. Depending upon the rule, Rhs and
 * Lhs values are selected from the datatable and compared. Then the resuts are
 * stored in a variable result.
 * 
 * Parameters for Function: lhs,rhs,datatable,headerRow,total_rules,ruleno
 * 
 * Return Value: NA
 **/

public class ImplementRules {

    private String task_type;
    private String status;
    private String task_Complexity;
    private String task_item_no;
    private String taskReport_SubProcess;
    private String taskReport_TaskType;
    private String taskReport_Complexity;
    private String taskReport_Status;
    private Map<String, DetailedSummaryReport> result = new TreeMap<String, DetailedSummaryReport>();
    private WriteXlWorkbook writeOutputExcel = new WriteXlWorkbook();

    public List<Integer> getResult(Map<String, DetailedSummaryReport> data_table,
            ArrayList<ProjectEffortReport> data_table2, Collection<SensitivityReportData> row) {
        int nonZeroTasks;
        int writeCounter = 0;
        List<Integer> nonZeroTasksList = new ArrayList<Integer>();

        for (Iterator<SensitivityReportData> iter = row.iterator(); iter
                .hasNext();) {
            nonZeroTasks = 0;
            SensitivityReportData elem = iter.next();
            if (elem == null)
                continue;
            taskReport_TaskType = elem.getSelTaskRow();
            taskReport_Complexity = elem.getSelComplexityRow();
            taskReport_Status = elem.getSelStatusRow();
            taskReport_SubProcess = elem.getSelSubProcessRow();
            result.clear();
            Set<String> Data_table_keys = data_table.keySet();

            for (String key : Data_table_keys) {

                task_type = data_table.get(key).getTask_type();
                status = data_table.get(key).getStatus();
                task_Complexity = data_table.get(key).getComplexity();
                task_item_no = data_table.get(key).getTask_item_no();

                if (task_type.replaceAll("\\s+", "").equalsIgnoreCase(
                        taskReport_TaskType.replaceAll("\\s+", ""))
                        && task_Complexity.replaceAll("\\s+", "")
                                .equalsIgnoreCase(
                                        taskReport_Complexity.replaceAll(
                                                "\\s+", ""))
                        && status.replaceAll("\\s+", "").equalsIgnoreCase(
                                taskReport_Status.replaceAll("\\s+", ""))) {

                    double subProcessCharging = 0;

                    for (int i = 0; i < data_table2.size(); i++) {

                        if (task_item_no.replaceAll("\\s+", "")
                                .equalsIgnoreCase(
                                        String.valueOf(
                                                data_table2.get(i)
                                                        .getTask_Identifier())
                                                .replaceAll("\\s+", ""))
                                && taskReport_SubProcess
                                        .replaceAll("\\s+", "")
                                        .equalsIgnoreCase(
                                                String.valueOf(
                                                        data_table2.get(i)
                                                                .getTask_Type())
                                                        .replaceAll("\\s+", ""))) {

                            subProcessCharging = subProcessCharging
                                    + Double.parseDouble(String
                                            .valueOf(data_table2.get(i)
                                                    .getActual_Effort()));
                        }

                        (data_table.get(key)).setSubProcess(subProcessCharging);
                    }
                    if (subProcessCharging > 0.01)
                        nonZeroTasks++;

                    result.put(task_item_no, data_table.get(key));
                }
            }
            writeOutputExcel
                    .WriteOutExcel(taskReport_TaskType, taskReport_Complexity,
                            taskReport_Status, result, writeCounter);
            nonZeroTasksList.add(nonZeroTasks);
            writeCounter++;
        }
        return nonZeroTasksList;
    }
}
