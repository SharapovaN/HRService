package ru.otus.hrapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.otus.hrapp.model.dto.EmployeeDto;
import ru.otus.hrapp.model.dto.SaveEmployeeDto;
import ru.otus.hrapp.model.dto.UpdateEmployeeDto;
import ru.otus.hrapp.service.EmployeeService;

import java.util.List;

@Tag(name = "Employee controller")
@CrossOrigin
@AllArgsConstructor
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Get all employees")
    @GetMapping("/employee")
    public List<EmployeeDto> getAllEmployees(@RequestParam(defaultValue = "true")
                                             @Parameter(name = "isActiveOnly",
                                                     description = " Request parameter to search for ACTIVE employees only (default) or for all employees")
                                             boolean isActiveOnly) {
        return employeeService.getAllEmployees(isActiveOnly);
    }

    @Operation(summary = "Get employee by ID")
    @GetMapping("/employee/{id}")
    public EmployeeDto getEmployee(@PathVariable("id")
                                   @Parameter(name = "id", description = " Employee unique identifier")
                                   long id, Authentication authentication) {
        return employeeService.getEmployeeDtoById(id, authentication);
    }

    @Operation(summary = "Create employee")
    @PostMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto createEmployee(@Parameter(name = "saveEmployeeDto", description = " DTO for creating employee")
                                      @RequestBody @Valid SaveEmployeeDto saveEmployeeDto) {
        return employeeService.createEmployee(saveEmployeeDto);
    }

    @Operation(summary = "Update employee")
    @PutMapping("/employee")
    public EmployeeDto updateEmployee(@Parameter(name = "updateEmployeeDto", description = " DTO for updating employee")
                                      @RequestBody @Valid UpdateEmployeeDto updateEmployeeDto) {
        return employeeService.updateEmployee(updateEmployeeDto);
    }
}
