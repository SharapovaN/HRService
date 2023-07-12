package ru.otus.hrapp.service;

import ru.otus.hrapp.model.entity.Department;

public interface DepartmentService {
    Department getDepartmentById(long departmentId);
}
