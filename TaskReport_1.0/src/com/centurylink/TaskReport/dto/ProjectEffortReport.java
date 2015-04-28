package com.centurylink.TaskReport.dto;

public class ProjectEffortReport {

	String resource_Name;
	String project_Name;
	String project_Type;
	String milestone;
	String task_Type;
	String task_Identifier;
	String actual_Effort;
	String effort_Date;

	public String getResource_Name() {
		return resource_Name;
	}

	public void setResource_Name(String resource_Name) {
		this.resource_Name = resource_Name;
	}

	public String getProject_Name() {
		return project_Name;
	}

	public void setProject_Name(String project_Name) {
		this.project_Name = project_Name;
	}

	public String getProject_Type() {
		return project_Type;
	}

	public void setProject_Type(String project_Type) {
		this.project_Type = project_Type;
	}

	public String getMilestone() {
		return milestone;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	public String getTask_Type() {
		return task_Type;
	}

	public void setTask_Type(String task_Type) {
		this.task_Type = task_Type;
	}

	public String getTask_Identifier() {
		return task_Identifier;
	}

	public void setTask_Identifier(String task_Identifier) {
		this.task_Identifier = task_Identifier;
	}

	public String getActual_Effort() {
		return actual_Effort;
	}

	public void setActual_Effort(String actual_Effort) {
		this.actual_Effort = actual_Effort;
	}

	public String getEffort_Date() {
		return effort_Date;
	}

	public void setEffort_Date(String effort_Date) {
		this.effort_Date = effort_Date;
	}
}
