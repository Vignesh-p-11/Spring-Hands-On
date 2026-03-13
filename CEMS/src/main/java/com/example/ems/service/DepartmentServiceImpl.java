package com.example.ems.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ems.dto.DepartmentRequest;
import com.example.ems.model.Department;
import com.example.ems.repository.DepartmentRepository;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public Department create(DepartmentRequest request) {
        Department d = new Department();
        d.setDeptId(request.getDeptId());
        d.setDeptName(request.getDeptName());
        return departmentRepository.save(d);
    }

    @Override
    @Transactional
    public List<Department> createAll(List<DepartmentRequest> requests) {
        List<Department> toSave = new ArrayList<>();
        for (DepartmentRequest r : requests) {
            Department d = new Department();
            d.setDeptId(r.getDeptId());
            d.setDeptName(r.getDeptName());
            toSave.add(d);
        }
        return departmentRepository.saveAll(toSave);
    }
}