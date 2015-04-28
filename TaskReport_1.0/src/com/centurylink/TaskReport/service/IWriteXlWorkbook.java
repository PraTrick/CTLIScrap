package com.centurylink.TaskReport.service;

import java.util.Map;

import com.centurylink.TaskReport.dto.DetailedSummaryReport;

public interface IWriteXlWorkbook {

	void WriteOutExcel(String taskReport_TaskType,
			String taskReport_Complexity, String taskReport_Status,
			Map<String, DetailedSummaryReport> result, int writeCounter);

}
