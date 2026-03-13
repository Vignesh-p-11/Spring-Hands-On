package com.example.ems.service;

import java.util.List;

import com.example.ems.dto.EmployeeRequest;
import com.example.ems.dto.EmployeeResponse;

public interface EmployeeService {
    EmployeeResponse create(EmployeeRequest request);
    EmployeeResponse update(Integer empId, EmployeeRequest request);
    EmployeeResponse get(Integer empId);
    List<EmployeeResponse> getAll();
    void delete(Integer empId);
    List<EmployeeResponse> findByDesg(String desg);
    List<EmployeeResponse> findBySalaryRange(Integer min, Integer max);
}