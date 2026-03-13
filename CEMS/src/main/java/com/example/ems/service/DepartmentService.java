package com.example.ems.service;

import java.util.List;
import com.example.ems.dto.DepartmentRequest;
import com.example.ems.model.Department;

public interface DepartmentService {
    Department create(DepartmentRequest request);
    List<Department> createAll(List<DepartmentRequest> requests);
}