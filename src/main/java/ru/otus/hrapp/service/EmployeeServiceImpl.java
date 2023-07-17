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
import java.util.Arrays;
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
    public List<EmployeeDto> getAllEmployees() {
        log.debug("GetAllEmployees method was called");

        return employeeRepository.findAll().stream().map(ModelConverter::toEmployeeDto).toList();
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

    @Override
    public List<EmployeeDto> getEmployeesByLocationId(long locationId, boolean isActiveOnly) {
        log.debug("GetEmployeesByLocationId method was called with locationId: " + locationId);

        if (locationService.existsById(locationId)) {
            if (isActiveOnly) {
                return employeeRepository.findActiveEmployeeByLocationId(locationId).stream()
                        .map(ModelConverter::toEmployeeDto).collect(Collectors.toList());
            } else {
                return employeeRepository.findByLocationId(locationId).stream()
                        .map(ModelConverter::toEmployeeDto).collect(Collectors.toList());
            }
        } else {
            throw new ResourceNotFoundException("No location is found for the id: " + locationId);
        }
    }

    @Override
    public List<EmployeeDto> getEmployeeBySearchPhrase(String searchPhrase, boolean isActiveOnly) {
        log.debug("GetEmployeeBySearchPhrase method was called with searchPhrase: " + searchPhrase);

        if (isActiveOnly) {
            return employeeRepository.findActiveEmployeeBySearchPhrase(searchPhrase).stream()
                    .map(ModelConverter::toEmployeeDto)
                    .collect(Collectors.toList());
        } else {
            return employeeRepository.findAllEmployeeBySearchPhrase(searchPhrase).stream()
                    .map(ModelConverter::toEmployeeDto)
                    .collect(Collectors.toList());
        }
    }

    public EmployeeDto createEmployee(SaveEmployeeDto saveEmployeeDto) {
        log.debug("CreateEmployee method was called with createEmployeeDto: " + saveEmployeeDto);

        StringBuilder errorMsgBuilder = new StringBuilder();
        if (LocalDate.now().isBefore(saveEmployeeDto.getHireDate()) && !saveEmployeeDto.getStatus().equals(EmployeeStatus.PENDING)) {
            errorMsgBuilder.append("Employee status must be PENDING.");
        }
        if (employeeRepository.findByEmail(saveEmployeeDto.getEmail()) != null) {
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

        EmployeeDto employeeDto = ModelConverter.toExtendedEmployeeDto(savedEmployee, null);
        return employeeDto;
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
    public void updateEmployeeStatus(Employee employee, EmployeeStatus status) {
        log.debug("UpdateEmployeeStatus method was called for employee id: " + employee.getId());

        employeeRepository.updateEmployeeStatus(status.name(), employee.getId());
    }

    @Override
    public List<Employee> getEmployeeByDepartmentAndLocation(long departmentId, long locationId) {
        log.debug("GetEmployeeByDepartmentAndLocation method was called with departmentId: {} and locationId : {}",
                departmentId, locationId);

        return employeeRepository.findEmployeeByDepartmentAndLocation(departmentId, locationId);
    }

    @Override
    public List<Employee> getPendingEmployeeHireDateBefore(LocalDate date) {
        log.debug("GetPendingEmployeeHireDateBefore method was called with date: {}", date);

        return employeeRepository.findPendingEmployeeHireDateBefore(date);
    }

    @Override
    public List<Employee> getPendingEmployeeHireDateIs(LocalDate date, LocalDate checkDate) {
        log.debug("GetPendingEmployeeHireDateIs method was called with dates: " + date + " " + checkDate);

        return employeeRepository.findPendingEmployeeHireDateIs(Arrays.asList(date, checkDate));
    }

    public List<EmployeeDto> getEmployeesByProjectId(long projectId, boolean isActiveOnly) {
        log.debug("GetEmployeesByProjectId method was called with projectId: " + projectId);

        if (isActiveOnly) {
            return employeeRepository.findActiveEmployeesByProjectId(projectId).stream()
                    .map(ModelConverter::toEmployeeDto)
                    .collect(Collectors.toList());
        } else {
            return employeeRepository.findByProjectId(projectId).stream()
                    .map(ModelConverter::toEmployeeDto)
                    .collect(Collectors.toList());
        }
    }

    private void appendNotFoundMessage(StringBuilder stringBuilder, String entityName, long id) {
        String template = " No %s is found for the id: %d";
        stringBuilder.append(String.format(template, entityName, id));
    }

    @Override
    public boolean existsById(long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}
