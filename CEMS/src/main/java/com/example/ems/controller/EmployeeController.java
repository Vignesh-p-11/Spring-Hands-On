package com.example.ems.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.dto.EmployeeRequest;
import com.example.ems.dto.EmployeeResponse;
import com.example.ems.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping
	public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest request) {
		return ResponseEntity.ok(employeeService.create(request));
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponse> get(@PathVariable Integer id) {
		return ResponseEntity.ok(employeeService.get(id));
	}

	@GetMapping
	public ResponseEntity<List<EmployeeResponse>> all() {
		return ResponseEntity.ok(employeeService.getAll());
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeResponse> update(@PathVariable Integer id,
			@Valid @RequestBody EmployeeRequest request) {
		return ResponseEntity.ok(employeeService.update(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		employeeService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search/by-desg")
	public ResponseEntity<List<EmployeeResponse>> byDesg(@RequestParam String desg) {
		return ResponseEntity.ok(employeeService.findByDesg(desg));
	}

	@GetMapping("/search/by-salary")
	public ResponseEntity<List<EmployeeResponse>> bySalary(@RequestParam Integer min, @RequestParam Integer max) {
		return ResponseEntity.ok(employeeService.findBySalaryRange(min, max));
	}
}