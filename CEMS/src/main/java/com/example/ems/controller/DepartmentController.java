
package com.example.ems.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.dto.DepartmentRequest;
import com.example.ems.dto.DepartmentResponse;
import com.example.ems.model.Department;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

	private final DepartmentRepository departmentRepository;
	private final DepartmentService departmentService;

	public DepartmentController(DepartmentRepository departmentRepository, DepartmentService departmentService) {
		super();
		this.departmentRepository = departmentRepository;
		this.departmentService = departmentService;
	}

	   @GetMapping
	    public List<DepartmentResponse> all(){
	        return departmentRepository.findAll()
	                .stream()
	                .map(this::toResponse)
	                .collect(Collectors.toList());
	    }


	@PostMapping
	public ResponseEntity<Department> create(@Valid @RequestBody DepartmentRequest request) {
		return ResponseEntity.ok(departmentService.create(request));
	}

	@PostMapping("/bulk")
	public ResponseEntity<List<Department>> createBulk(@Valid @RequestBody List<DepartmentRequest> requests) {
		return ResponseEntity.ok(departmentService.createAll(requests));
	}

    private DepartmentResponse toResponse(Department d) {
        DepartmentResponse dto = new DepartmentResponse();
        dto.setDeptId(d.getDeptId());
        dto.setDeptName(d.getDeptName());
        return dto;
    }

}
