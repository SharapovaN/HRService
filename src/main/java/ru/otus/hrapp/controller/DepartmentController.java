package ru.otus.hrapp.controller;

import com.practice.hrapp.model.dto.DepartmentDto;
import com.practice.hrapp.model.enumeration.DepartmentName;
import com.practice.hrapp.service.DepartmentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Api(tags = "Department Controller")
@RequestMapping("department")
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ApiOperation(
            value = "Get department by department ID",
            nickname = "getDepartmentById")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public DepartmentDto getDepartmentById(@ApiParam(name = "departmentId", value = "Department unique identifier")
                                           @RequestParam int departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    @ApiOperation(
            value = "Get all departments",
            nickname = "getAllDepartments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("getAllDepartments")
    public List<DepartmentDto> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @ApiOperation(
            value = "Create department by department name",
            nickname = "createDepartment")
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 409, message = "Department already exists"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public DepartmentDto createDepartment(@ApiParam(name = "departmentName", value = "Name for department creation")
                                          @RequestParam DepartmentName departmentName) {
        return departmentService.createDepartment(departmentName);
    }
}
