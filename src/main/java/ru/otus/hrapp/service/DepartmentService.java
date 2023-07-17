package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.DepartmentDto;
import ru.otus.hrapp.model.entity.Department;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> getAllDepartments();

    Department getDepartmentById(long departmentId);
}
