package com.example.ems.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DepartmentRequest {
    @NotNull(message = "Department ID is required")
    private Integer deptId;

    @NotBlank(message = "Department name is required")
    @Size(max = 50, message = "Department name must be at most 50 characters")
    private String deptName;

    // getters & setters
    public Integer getDeptId() { return deptId; }
    public void setDeptId(Integer deptId) { this.deptId = deptId; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
}