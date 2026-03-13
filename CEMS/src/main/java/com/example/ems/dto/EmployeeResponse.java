package com.example.ems.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

	private Integer empId;
	private String empName;
	private Integer empSal;
	private String empDesg;

	// Address info
	private Integer addressId;
	private String city;
	private String state;
	private String pincode;

	// Department info
	private Integer deptId;
	private String deptName;

	// Project info
	private Set<ProjectSummary> projects;

	public static class ProjectSummary {
		private Integer projectId;
		private String projectName;

		public ProjectSummary() {
		}

		public ProjectSummary(Integer projectId, String projectName) {
			this.projectId = projectId;
			this.projectName = projectName;
		}

		public Integer getProjectId() {
			return projectId;
		}

		public void setProjectId(Integer projectId) {
			this.projectId = projectId;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
	}

}