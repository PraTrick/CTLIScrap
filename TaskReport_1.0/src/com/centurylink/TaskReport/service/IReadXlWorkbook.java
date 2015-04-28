package com.centurylink.TaskReport.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.centurylink.TaskReport.dto.DetailedSummaryReport;
import com.centurylink.TaskReport.dto.ProjectEffortReport;

public interface IReadXlWorkbook {
	
	Map<String, DetailedSummaryReport> ReadDetailedSumReport(File file, String sheetname1,
			int header_row_pos1);
	ArrayList<ProjectEffortReport> ReadProjectEffortReport(File file, String sheetname1,
            int header_row_pos1);
}
