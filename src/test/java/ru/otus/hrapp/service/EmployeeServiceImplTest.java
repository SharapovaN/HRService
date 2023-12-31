package ru.otus.hrapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hrapp.model.dto.EmployeeDto;
import ru.otus.hrapp.model.dto.UpdateEmployeeDto;
import ru.otus.hrapp.model.entity.Contract;
import ru.otus.hrapp.model.entity.Employee;
import ru.otus.hrapp.model.enumeration.EmployeeStatus;
import ru.otus.hrapp.model.enumeration.RoleType;
import ru.otus.hrapp.repository.EmployeeRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void getAllActiveEmployees() {
        given(employeeRepository.findEmployeesByStatusActive()).willReturn(List.of(getPreparedEmployee()));

        List<EmployeeDto> employeeDtos = employeeService.getAllEmployees(true);
        Assertions.assertEquals(1, employeeDtos.size());
        Assertions.assertEquals(1, employeeDtos.get(0).getId());
        Assertions.assertEquals("Name", employeeDtos.get(0).getName());
    }

    @Test
    void getAllEmployees() {
        given(employeeRepository.findAll()).willReturn(List.of(getPreparedEmployee()));

        List<EmployeeDto> employeeDtos = employeeService.getAllEmployees(false);
        Assertions.assertEquals(1, employeeDtos.size());
        Assertions.assertEquals(1, employeeDtos.get(0).getId());
        Assertions.assertEquals("Name", employeeDtos.get(0).getName());
    }

    @Test
    void getEmployeeByIdIfOk() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(getPreparedEmployee()));

        Employee employee = employeeService.getEmployeeById(1);
        Assertions.assertNotNull(employee);
        Assertions.assertEquals(1, employee.getId());
        Assertions.assertEquals("Name", employee.getName());
        Assertions.assertEquals("Surname", employee.getSurname());
        Assertions.assertEquals(EmployeeStatus.PENDING, employee.getStatus());
        Assertions.assertEquals("email@email", employee.getEmail());
    }

    @Test
    void getEmployeeByIdIfNotOk() {
        given(employeeRepository.findById(1L)).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(1));
    }

    @Test
    void updateEmployeeIfNotFound() {
        given(employeeRepository.findById(1L)).willReturn(Optional.empty());
        UpdateEmployeeDto dto = getPreparedEmployeeDto();
        dto.setId(1L);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(dto));
    }

    @Test
    void updateEmployeeStatus() {
        given(employeeRepository.findById(1L)).willReturn(Optional.empty());
        UpdateEmployeeDto dto = getPreparedEmployeeDto();
        dto.setId(1L);
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> employeeService.updateEmployeeStatus(1, EmployeeStatus.ACTIVE));
    }

    private Employee getPreparedEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Name");
        employee.setSurname("Surname");
        employee.setStatus(EmployeeStatus.PENDING);
        employee.setEmail("email@email");
        employee.setHireDate(LocalDate.now().plusDays(1));
        employee.setManager(getEmployeeManager());
        employee.setContract(new Contract(null, 1000000, LocalDate.now().plusDays(1),
                null, null, null));

        return employee;
    }

    private Employee getEmployeeManager() {
        Employee employee = new Employee();
        employee.setId(2L);

        return employee;
    }

    private UpdateEmployeeDto getPreparedEmployeeDto() {
        UpdateEmployeeDto saveEmployeeDto = new UpdateEmployeeDto();
        saveEmployeeDto.setName("Name");
        saveEmployeeDto.setSurname("Surname");
        saveEmployeeDto.setHireDate(LocalDate.now().plusDays(1));
        saveEmployeeDto.setContractStartDate(LocalDate.now().plusDays(1));
        saveEmployeeDto.setManagerId(1L);
        saveEmployeeDto.setEmail("email@email");
        saveEmployeeDto.setLocationId(1L);
        saveEmployeeDto.setDepartmentId(1L);
        saveEmployeeDto.setJobId(1L);
        saveEmployeeDto.setSalary(1000000);
        saveEmployeeDto.setRole(RoleType.ROLE_USER);

        return saveEmployeeDto;
    }
}