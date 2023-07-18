package ru.otus.hrapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hrapp.model.dto.EmployeeDto;
import ru.otus.hrapp.model.dto.SaveEmployeeDto;
import ru.otus.hrapp.model.entity.Contract;
import ru.otus.hrapp.model.entity.Employee;
import ru.otus.hrapp.model.enumeration.EmployeeStatus;
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

    @Mock
    JobServiceImpl jobService;

    @Mock
    DepartmentServiceImpl departmentService;

    @Mock
    LocationServiceImpl locationService;

    @Mock
    ContractServiceImpl contractService;

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
    void getEmployeeDtoByIdIfOk() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(getPreparedEmployee()));

        EmployeeDto employeeDto = employeeService.getEmployeeDtoById(1);
        Assertions.assertNotNull(employeeDto);
        Assertions.assertEquals(1, employeeDto.getId());
        Assertions.assertEquals("Name", employeeDto.getName());
        Assertions.assertEquals("Surname", employeeDto.getSurname());
        Assertions.assertEquals(EmployeeStatus.PENDING, employeeDto.getStatus());
        Assertions.assertEquals("email@email", employeeDto.getEmail());
    }

    @Test
    void getEmployeeDtoByIdIfNotOk() {
        given(employeeRepository.findById(1L)).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeDtoById(1));
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
    void createEmployee() {
        given(employeeRepository.findByEmail("email@email")).willReturn(Optional.empty());
        given(employeeRepository.findById(1L)).willReturn(Optional.of(getEmployeeManager()));
        given(locationService.getLocationById(1)).willReturn(null);
        given(departmentService.getDepartmentById(1)).willReturn(null);
        given(jobService.getJobById(1)).willReturn(null);
        given(contractService.save(new Contract(null, 1000000, LocalDate.now().plusDays(1),
                null, null, null))).willReturn(null);
        Employee employee = getPreparedEmployee();
        employee.setId(null);
        given(employeeRepository.save(employee)).willReturn(getPreparedEmployee());

        EmployeeDto employeeDto = employeeService.createEmployee(getPreparedEmployeeDto());

        Assertions.assertNotNull(employeeDto);
        Assertions.assertEquals(1, employeeDto.getId());
        Assertions.assertEquals("Name", employeeDto.getName());
        Assertions.assertEquals("Surname", employeeDto.getSurname());
        Assertions.assertEquals(EmployeeStatus.PENDING, employeeDto.getStatus());
        Assertions.assertEquals("email@email", employeeDto.getEmail());
    }

    @Test
    void updateEmployeeIfNotFound() {
        given(employeeRepository.findById(1L)).willReturn(Optional.empty());
        SaveEmployeeDto dto = getPreparedEmployeeDto();
        dto.setId(1L);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(dto));
    }

    @Test
    void updateEmployeeStatus() {
        given(employeeRepository.findById(1L)).willReturn(Optional.empty());
        SaveEmployeeDto dto = getPreparedEmployeeDto();
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

    private SaveEmployeeDto getPreparedEmployeeDto() {
        SaveEmployeeDto saveEmployeeDto = new SaveEmployeeDto();
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

        return saveEmployeeDto;
    }
}