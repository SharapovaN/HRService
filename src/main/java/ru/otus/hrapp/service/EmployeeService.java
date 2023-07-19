package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.EmployeeDto;
import ru.otus.hrapp.model.dto.SaveEmployeeDto;
import ru.otus.hrapp.model.dto.UpdateEmployeeDto;
import ru.otus.hrapp.model.entity.Employee;
import ru.otus.hrapp.model.enumeration.EmployeeStatus;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getAllEmployees(boolean isActiveOnly);

    EmployeeDto getEmployeeDtoById(long id);

    Employee getEmployeeById(long id);

    EmployeeDto createEmployee(SaveEmployeeDto saveEmployeeDto);

    EmployeeDto updateEmployee(UpdateEmployeeDto updateEmployeeDto);

    void updateEmployeeStatus(long employeeId, EmployeeStatus status);
}
