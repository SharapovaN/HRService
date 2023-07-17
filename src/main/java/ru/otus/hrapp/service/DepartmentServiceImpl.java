package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.dto.DepartmentDto;
import ru.otus.hrapp.model.entity.Department;
import ru.otus.hrapp.repository.DepartmentRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;
import ru.otus.hrapp.util.ModelConverter;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> getAllDepartments() {
        log.info("getAllDepartments method was called");

        return departmentRepository.findAll().stream()
                .map(ModelConverter::toDepartmentDto)
                .toList();
    }

    @Override
    public Department getDepartmentById(long departmentId) {
        log.info("getDepartmentById method was called with id: {}", departmentId);

        return departmentRepository.findById(departmentId).orElseThrow(() ->
                new ResourceNotFoundException("No department is found for the id: " + departmentId));
    }
}
