package com.example.ems.dto;

import java.util.Set;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

	@NotNull(message = "Employee ID is required")
	private Integer empId;

	@NotBlank(message = "Employee name is required")
	@Size(max = 50, message = "Employee name must be at most 50 characters")
	private String empName;

	@Min(value = 0, message = "Salary cannot be negative")
	private Integer empSal;

	@Size(max = 30, message = "Designation must be at most 30 characters")
	private String empDesg;

	// Address fields 
	private Integer addressId; 
	private String city;
	private String state;
	private String pincode;

	// Relationship references by IDs
	private Integer deptId; // Department FK
	private Set<Integer> projectIds; // Many-to-many projects

}
