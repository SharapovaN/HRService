package ru.otus.hrapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.hrapp.model.dto.EmployeeDto;
import ru.otus.hrapp.model.dto.SaveEmployeeDto;
import ru.otus.hrapp.service.EmployeeService;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employee")
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public EmployeeDto getEmployee(@PathVariable("id") long id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping("/employee/search")
    public List<EmployeeDto> getEmployeesBySearchPhrase(@RequestParam String searchPhrase,
                                                        @RequestParam(required = true, defaultValue = "true") boolean isActiveOnly) {
        return employeeService.getEmployeeBySearchPhrase(searchPhrase, isActiveOnly);
    }

    @GetMapping("/employee/location")
    public List<EmployeeDto> getEmployeesByLocationId(@RequestParam int locationId,
                                                      @RequestParam(required = true, defaultValue = "true") boolean isActiveOnly) {
        return employeeService.getEmployeesByLocationId(locationId, isActiveOnly);
    }

    @PostMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto createEmployee(@RequestBody @Valid SaveEmployeeDto createEmployeeDto) {
        return employeeService.createEmployee(createEmployeeDto);
    }


    @PutMapping("/employee")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto updateEmployee(@RequestBody @Valid SaveEmployeeDto updateEmployeeDto) {
        return employeeService.updateEmployee(updateEmployeeDto);
    }
}
