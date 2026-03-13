package com.example.ems.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ems.dto.EmployeeRequest;
import com.example.ems.dto.EmployeeResponse;
import com.example.ems.exception.ResourceNotFoundException;
import com.example.ems.model.Address;
import com.example.ems.model.Department;
import com.example.ems.model.Employee;
import com.example.ems.model.Project;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.repository.ProjectRepository;

@Service
@Transactional(readOnly = true) // default: read-only for queries
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               DepartmentRepository departmentRepository,
                               ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
    }

    // -----------------------------
    // CRUD + Queries (return DTOs)
    // -----------------------------

    @Override
    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        Employee e = new Employee();
        apply(e, request);            // map DTO -> entity (your existing logic)
        e = employeeRepository.save(e);
        return toResponse(e);
    }

    @Override
    @Transactional
    public EmployeeResponse update(Integer empId, EmployeeRequest request) {
        Employee e = employeeRepository.findById(empId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + empId));

        // Normalize/lock the ID to the path variable (your original policy)
        if (request.getEmpId() != null && !request.getEmpId().equals(e.getEmpId())) {
            request.setEmpId(e.getEmpId());
        }

        apply(e, request);
        e = employeeRepository.save(e);
        return toResponse(e);
    }

    @Override
    public EmployeeResponse get(Integer empId) {
        Employee e = employeeRepository.findById(empId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + empId));
        return toResponse(e);
    }

    @Override
    public List<EmployeeResponse> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Integer empId) {
        Employee e = employeeRepository.findById(empId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + empId));
        employeeRepository.delete(e);
        // Note: Address is removed too due to cascade + orphanRemoval on Employee.address
        // (from your entity) 
    }

    @Override
    public List<EmployeeResponse> findByDesg(String desg) {
        return employeeRepository.findByEmpDesg(desg)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponse> findBySalaryRange(Integer min, Integer max) {
        return employeeRepository.findByEmpSalBetween(min, max)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // -----------------------------
    // Mapping helpers
    // -----------------------------

    /** Map flattened EmployeeRequest -> Employee entity (uses your existing logic) */
    private void apply(Employee e, EmployeeRequest req) {
        // If empId is auto-generated, remove this line in future.
        e.setEmpId(req.getEmpId());
        e.setEmpName(req.getEmpName());
        e.setEmpSal(req.getEmpSal());
        e.setEmpDesg(req.getEmpDesg());

        // Address (flattened in DTO)
        if (req.getCity() != null
            || req.getState() != null
            || req.getPincode() != null
            || req.getAddressId() != null) {

            Address a = e.getAddress();
            if (a == null) a = new Address();

            // If Address IDs are auto-generated, do not set addressId from request.
            if (req.getAddressId() != null) {
                a.setAddrId(req.getAddressId());
            }
            a.setCity(req.getCity());
            a.setState(req.getState());
            a.setPincode(req.getPincode());
            e.setAddress(a);
        }

        // Department by ID
        if (req.getDeptId() != null) {
            Department d = departmentRepository.findById(req.getDeptId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found: " + req.getDeptId()));
            e.setDepartment(d);
        } else {
            // policy: keep as-is when null (do not clear)
        }

        // Projects by IDs
        if (req.getProjectIds() != null) {
            Set<Project> ps = new HashSet<>();
            for (Integer pid : req.getProjectIds()) {
                Project p = projectRepository.findById(pid)
                        .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + pid));
                ps.add(p);
            }
            e.setProjects(ps); // replaces existing set when provided
        } else {
            // policy: keep existing projects if not provided
        }
    }

    /** Map Employee entity -> EmployeeResponse DTO (prevents recursion in JSON) */
    private EmployeeResponse toResponse(Employee e) {
        EmployeeResponse r = new EmployeeResponse();
        r.setEmpId(e.getEmpId());
        r.setEmpName(e.getEmpName());
        r.setEmpSal(e.getEmpSal());
        r.setEmpDesg(e.getEmpDesg());

        if (e.getAddress() != null) {
            r.setAddressId(e.getAddress().getAddrId());
            r.setCity(e.getAddress().getCity());
            r.setState(e.getAddress().getState());
            r.setPincode(e.getAddress().getPincode());
        }
        if (e.getDepartment() != null) {
            r.setDeptId(e.getDepartment().getDeptId());
            r.setDeptName(e.getDepartment().getDeptName());
        }
        if (e.getProjects() != null) {
            Set<EmployeeResponse.ProjectSummary> ps = new HashSet<>();
            for (Project p : e.getProjects()) {
                ps.add(new EmployeeResponse.ProjectSummary(p.getProjectId(), p.getProjectName()));
            }
            r.setProjects(ps);
        }
        return r;
    }
}