package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.EmployeeDto;
import ru.otus.hrapp.model.dto.SaveEmployeeDto;
import ru.otus.hrapp.model.entity.Employee;
import ru.otus.hrapp.model.enumeration.EmployeeStatus;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployee(long id);

    List<EmployeeDto> getEmployeesByLocationId(long locationId, boolean isActiveOnly);

    List<EmployeeDto> getEmployeeBySearchPhrase(String searchPhrase, boolean isActiveOnly);

    EmployeeDto createEmployee(SaveEmployeeDto saveEmployeeDto);

    EmployeeDto updateEmployee(SaveEmployeeDto saveEmployeeDto);

    void updateEmployeeStatus(Employee employee, EmployeeStatus status);

    List<Employee> getEmployeeByDepartmentAndLocation(long departmentId, long locationId);

    List<Employee> getPendingEmployeeHireDateBefore(LocalDate date);

    List<Employee> getPendingEmployeeHireDateIs(LocalDate date, LocalDate checkDate);

    List<EmployeeDto> getEmployeesByProjectId(long projectId, boolean isActiveOnly);

    boolean existsById(long employeeId);
}
