package ru.otus.hrapp.controller;

import com.practice.hrapp.model.dto.CreateEmployeeDto;
import com.practice.hrapp.model.dto.EmployeeDto;
import com.practice.hrapp.model.dto.UpdateEmployeeDto;
import com.practice.hrapp.service.EmployeeService;
import com.practice.hrapp.service.RoleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@Api(tags = "Employee Controller")
@RequestMapping("employee")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService, RoleService roleService) {
        this.employeeService = employeeService;
    }

    @ApiOperation(
            value = "Get employee by ID",
            nickname = "getEmployee")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public EmployeeDto getEmployee(@ApiParam(name = "id", value = "Employee unique identifier")
                                   @RequestParam int id,
                                   @ApiParam(name = "isExtended", value = "Parameter defines amount of information collected from employee")
                                   @RequestParam(required = false, defaultValue = "false") boolean isExtended) {
        return employeeService.getEmployee(id, isExtended);
    }

    @ApiOperation(
            value = "Get employees by search phrase",
            nickname = "getEmployeesBySearchPhrase")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("search")
    public List<EmployeeDto> getEmployeesBySearchPhrase(@ApiParam(name = "searchPhrase", value = "Search phrase")
                                                        @RequestParam String searchPhrase,
                                                        @ApiParam(name = "isActiveOnly", value = " Request parameter to search for ACTIVE employees only (default) or for all employees")
                                                        @RequestParam(required = true, defaultValue = "true") boolean isActiveOnly) {
        return employeeService.getEmployeeBySearchPhrase(searchPhrase, isActiveOnly);
    }

    @ApiOperation(
            value = "Get employees from location by location ID",
            nickname = "getEmployeesByLocationId")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("location")
    public List<EmployeeDto> getEmployeesByLocationId(@ApiParam(name = "locationId", value = "Location unique identifier")
                                                      @RequestParam int locationId,
                                                      @ApiParam(name = "isActiveOnly", value = " Request parameter to search for ACTIVE employees only (default) or for all employees")
                                                      @RequestParam(required = true, defaultValue = "true") boolean isActiveOnly) {
        return employeeService.getEmployeesByLocationId(locationId, isActiveOnly);
    }

    @ApiOperation(
            value = "Create employee",
            nickname = "createEmployee")
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRequiredRole('HR_MANAGER')")
    public EmployeeDto createEmployee(@ApiParam(name = "createEmployeeDto", value = "DTO for creating employee")
                                      @RequestBody @Valid CreateEmployeeDto createEmployeeDto) {
        return employeeService.createEmployee(createEmployeeDto);
    }


    @ApiOperation(
            value = "Update employee",
            nickname = "updateEmployee")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("update")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('HR_MANAGER')")
    public EmployeeDto updateEmployee(@ApiParam(name = "updateEmployeeDto", value = "DTO for updating employee")
                                      @RequestBody @Valid UpdateEmployeeDto updateEmployeeDto) {
        return employeeService.updateEmployee(updateEmployeeDto);
    }
}
