package ru.otus.hrapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hrapp.model.dto.ContactDto;
import ru.otus.hrapp.model.dto.SaveContactDto;
import ru.otus.hrapp.service.ContactServiceImpl;

import java.util.List;

@Tag(name = "Contact controller")
@RequiredArgsConstructor
@RestController
public class ContactController {

    private final ContactServiceImpl contactService;

    @Operation(summary = "Get all employee's contacts")
    @GetMapping("/contact/{employeeId}")
    public List<ContactDto> getContactByEmployeeId(@PathVariable("employeeId")
                                                   @Parameter(name = "employeeId", description = " Employee unique identifier")
                                                   long employeeId) {
        return contactService.getEmployeeContactDtoList(employeeId);
    }

    @Operation(summary = "Create employee's contact")
    @PostMapping("/contact")
    public ContactDto createEmployeeContact(@Parameter(name = "contactDto", description = " DTO for creating employee's contact")
                                            @RequestBody @Valid SaveContactDto contactDto) {
        return contactService.createEmployeeContact(contactDto);
    }

    @Operation(summary = "Update employee's contact")
    @PutMapping("/contact")
    public ContactDto updateContact(@Parameter(name = "contactDto", description = " DTO for updating employee's contact")
                                    @RequestBody @Valid ContactDto contactDto) {
        return contactService.updateContact(contactDto);
    }

    @Operation(summary = "Delete employee's contact")
    @DeleteMapping("/contact/{id}")
    public void deleteContact(@Parameter(name = "id", description = " Contact unique identifier")
                              @PathVariable long id) {
        contactService.deleteContact(id);
    }
}
