package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.hrapp.model.dto.EmployeeDto;
import ru.otus.hrapp.model.dto.SaveEmployeeDto;
import ru.otus.hrapp.model.dto.UpdateEmployeeDto;
import ru.otus.hrapp.model.entity.Contract;
import ru.otus.hrapp.model.entity.Employee;
import ru.otus.hrapp.model.entity.Role;
import ru.otus.hrapp.model.entity.User;
import ru.otus.hrapp.model.enumeration.EmployeeStatus;
import ru.otus.hrapp.repository.EmployeeRepository;
import ru.otus.hrapp.repository.RoleRepository;
import ru.otus.hrapp.repository.UserRepository;
import ru.otus.hrapp.repository.mapper.UpdateMapper;
import ru.otus.hrapp.security.UserDetailsImpl;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;
import ru.otus.hrapp.util.ModelConverter;
import ru.otus.hrapp.util.PasswordGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasAnyRole('USER', 'HR_MANAGER')")
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
    @PreAuthorize("hasAnyRole('USER', 'HR_MANAGER')")
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeDtoById(long id, Authentication authentication) {
        log.debug("getEmployeeDtoById method was called with id: " + id);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No employee is found for the id: " + id));

        if (id == userDetails.getEmployeeId() || userDetails.getAuthorities().contains("ROLE_HR_MANAGER")) {
            return ModelConverter.toExtendedEmployeeDto(employee);
        }

        return ModelConverter.toEmployeeDto(employee);
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'HR_MANAGER')")
    public Employee getEmployeeById(long id) {
        log.debug("getEmployeeById method was called with id: " + id);

        return employeeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No employee is found for the id: " + id));
    }

    @Override
    @PreAuthorize("hasRole('HR_MANAGER')")
    public EmployeeDto createEmployee(SaveEmployeeDto saveEmployeeDto) {
        log.debug("CreateEmployee method was called with createEmployeeDto: " + saveEmployeeDto);

        StringBuilder errorMsgBuilder = new StringBuilder();

        if (employeeRepository.findByEmail(saveEmployeeDto.getEmail()).isPresent()) {
            errorMsgBuilder.append("Employee with this email already exists: ").append(saveEmployeeDto.getEmail());
        }

        Optional<Employee> employeeOptional = employeeRepository.findById(saveEmployeeDto.getManagerId());
        if (!employeeOptional.isPresent()) {
            String template = " No manager is found for the id: %d";
            errorMsgBuilder.append(String.format(template, saveEmployeeDto.getManagerId()));
        }

        if (!errorMsgBuilder.toString().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMsgBuilder.toString());
        }

        Employee employee = new Employee(saveEmployeeDto.getName(), saveEmployeeDto.getSurname(), saveEmployeeDto.getMiddleName(),
                saveEmployeeDto.getEmail(), saveEmployeeDto.getBirthday(), EmployeeStatus.PENDING, saveEmployeeDto.getHireDate());
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

        User user = new User();

        user.setUsername(saveEmployeeDto.getUsername());
        user.setEmployeeId(savedEmployee.getId());

        Set<Role> roleSet = new HashSet<>();

        Optional<Role> role = roleRepository.findRoleByName(saveEmployeeDto.getRole().name());

        role.ifPresent(roleSet::add);
        user.setRoles(roleSet);

        String userPassword = PasswordGenerator.generatePassword();
        EmployeeDto employeeDto = ModelConverter.toExtendedEmployeeDto(employee);
        employeeDto.setPassword(userPassword);
        employeeDto.setRoleName(role.get().getName().name());

        user.setPassword(passwordEncoder.encode(userPassword));

        userRepository.save(user);

        return employeeDto;
    }

    @Override
    @PreAuthorize("hasRole('HR_MANAGER')")
    public EmployeeDto updateEmployee(UpdateEmployeeDto updateEmployeeDto) {
        log.debug("UpdateEmployee method was called for employee id: " + updateEmployeeDto.getId());

        Optional<Employee> employeeOptional = employeeRepository.findById(updateEmployeeDto.getId());
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            updateMapper.updateEmployee(updateEmployeeDto, employee);
            return ModelConverter.toExtendedEmployeeDto(employeeRepository.save(employee));
        } else {
            throw new ResourceNotFoundException("No employee is found for the id: " + updateEmployeeDto.getId());
        }
    }

    @Override
    @PreAuthorize("hasRole('HR_MANAGER')")
    public void updateEmployeeStatus(long employeeId, EmployeeStatus status) {
        log.debug("UpdateEmployeeStatus method was called for employee id: " + employeeId);

        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            employeeRepository.updateEmployeeStatus(status.name(), employeeId);
        } else {
            throw new ResourceNotFoundException("No employee is found for the id: " + employeeId);
        }
    }
}
