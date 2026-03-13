
package com.example.ems.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "departments")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)

public class Department {

	@Id
	@Column(name = "dept_id")
	private Integer deptId;

	@Column(name = "dept_name", nullable = false, length = 50)
	private String deptName;

	@OneToMany(mappedBy = "department")
	//@JsonIgnore
	private List<Employee> employees = new ArrayList<>();

}
