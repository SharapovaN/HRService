package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.hrapp.model.dto.EmployeeDto;
import ru.otus.hrapp.model.dto.SaveEmployeeDto;
import ru.otus.hrapp.model.entity.Contract;
import ru.otus.hrapp.model.entity.Employee;
import ru.otus.hrapp.model.enumeration.EmployeeStatus;
import ru.otus.hrapp.repository.EmployeeRepository;
import ru.otus.hrapp.repository.mapper.UpdateMapper;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;
import ru.otus.hrapp.util.ModelConverter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ContractService contractService;
    private final LocationService locationService;
    private final DepartmentService departmentService;
    private final JobService jobService;
    private final UpdateMapper updateMapper;
    //private final RoleRepository roleRepository;
    // private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    @Override
    public List<EmployeeDto> getAllEmployees(boolean isActiveOnly) {
        log.debug("GetAllEmployees method was called with isActiveOnly: {}", isActiveOnly);

        if (isActiveOnly) {
            return employeeRepository.findEmployeesByStatusActive().stream()
                    .map(ModelConverter::toEmployeeDto).collect(Collectors.toList());
        } else {
            return employeeRepository.findAll().stream()
                    .map(ModelConverter::toEmployeeDto).collect(Collectors.toList());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeDtoById(long id) {
        log.debug("getEmployeeDtoById method was called with id: " + id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No employee is found for the id: " + id));
        /*if (isExtended) {

            return ModelConverter.toExtendedEmployeeDto(employee, contactList);
        } else {
            return ModelConverter.toEmployeeDto(employee);
        }*/
        return ModelConverter.toEmployeeDto(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        log.debug("getEmployeeById method was called with id: " + id);

        return employeeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No employee is found for the id: " + id));
    }

    public EmployeeDto createEmployee(SaveEmployeeDto saveEmployeeDto) {
        log.debug("CreateEmployee method was called with createEmployeeDto: " + saveEmployeeDto);

        StringBuilder errorMsgBuilder = new StringBuilder();
        if (LocalDate.now().isBefore(saveEmployeeDto.getHireDate()) && !saveEmployeeDto.getStatus().equals(EmployeeStatus.PENDING)) {
            errorMsgBuilder.append("Employee status must be PENDING.");
        }
        if (employeeRepository.findByEmail(saveEmployeeDto.getEmail()).isPresent()) {
            errorMsgBuilder.append("Employee with this email already exists: ").append(saveEmployeeDto.getEmail());
        }

        Optional<Employee> employeeOptional = employeeRepository.findById(saveEmployeeDto.getManagerId());
        if (!employeeOptional.isPresent()) {
            appendNotFoundMessage(errorMsgBuilder, "manager", saveEmployeeDto.getManagerId());
        }

       /* if (createEmployeeDto.getRole() != null && roleRepository.findRoleByName(createEmployeeDto.getRole().name()) == null) {
            errorMsgBuilder.append("Unknown role: ").append(createEmployeeDto.getRole());
        }
*/
        if (!errorMsgBuilder.toString().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMsgBuilder.toString());
        }

        Employee employee = new Employee(saveEmployeeDto.getName(), saveEmployeeDto.getSurname(), saveEmployeeDto.getMiddleName(),
                saveEmployeeDto.getEmail(), saveEmployeeDto.getBirthday(), saveEmployeeDto.getStatus(), saveEmployeeDto.getHireDate());
        employee.setLocation(locationService.getLocationById(saveEmployeeDto.getLocationId()));
        employee.setManager(employeeOptional.get());
        employee.setDepartment(departmentService.getDepartmentById(saveEmployeeDto.getDepartmentId()));
        employee.setJob(jobService.getJobById(saveEmployeeDto.getJobId()));

        Contract contract = new Contract();
        contract.setStartDate(saveEmployeeDto.getHireDate());
        contract.setSalary(saveEmployeeDto.getSalary());
        contractService.save(contract);
        employee.setContract(contract);

        Employee savedEmployee = employeeRepository.save(employee);

        /*User user = new User();

        user.setUsername(saveEmployeeDto.getUsername());
        user.setEmployeeId(savedEmployee.getId());

        Set<Role> roleSet = new HashSet<>();
        Optional<Role> role;
        if (saveEmployeeDto.getRole() == null) {
            role = roleRepository.findRoleByName("ROLE_USER");
        } else {
            role = roleRepository.findRoleByName(saveEmployeeDto.getRole().name());
        }
        role.ifPresent(roleSet::add);
        user.setRoles(roleSet);

        String userPassword = PasswordGenerator.generatePassword();
        EmployeeDto employeeDto = ModelConverter.toExtendedEmployeeDto(employee, null);
        employeeDto.setPassword(userPassword);
        employeeDto.setRoleName(role.get().getName().name());

        user.setPassword(passwordEncoder.encode(userPassword));

        userRepository.save(user);*/

        return ModelConverter.toEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateEmployee(SaveEmployeeDto saveEmployeeDto) {
        log.debug("UpdateEmployee method was called for employee id: " + saveEmployeeDto.getId());

        Optional<Employee> employeeOptional = employeeRepository.findById(saveEmployeeDto.getId());
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            updateMapper.updateEmployee(saveEmployeeDto, employee);
            return ModelConverter.toExtendedEmployeeDto(employeeRepository.save(employee), null);
        } else {
            throw new ResourceNotFoundException("No employee is found for the id: " + saveEmployeeDto.getId());
        }
    }

    @Override
    public void updateEmployeeStatus(long employeeId, EmployeeStatus status) {
        log.debug("UpdateEmployeeStatus method was called for employee id: " + employeeId);

        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            employeeRepository.updateEmployeeStatus(status.name(), employeeId);
        } else {
            throw new ResourceNotFoundException("No employee is found for the id: " + employeeId);
        }
    }

    private void appendNotFoundMessage(StringBuilder stringBuilder, String entityName, long id) {
        String template = " No %s is found for the id: %d";
        stringBuilder.append(String.format(template, entityName, id));
    }

}
