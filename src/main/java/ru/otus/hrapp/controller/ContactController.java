package ru.otus.hrapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hrapp.model.dto.ContactDto;
import ru.otus.hrapp.service.ContactService;

import java.util.List;

@AllArgsConstructor
@RestController
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/contact/{employeeId}")
    public List<ContactDto> getContactByEmployeeId(@PathVariable int employeeId) {
        return contactService.getEmployeeContactByEmployeeId(employeeId);
    }

    @PostMapping("/contact")
    public ContactDto createEmployeeContact(@RequestBody @Valid ContactDto contactDto) {
        return contactService.createEmployeeContact(contactDto);
    }

    @PutMapping("/contact")
    public ContactDto updateContact(@RequestBody @Valid ContactDto updateContactDto) {
        return contactService.updateContact(updateContactDto);
    }

    @DeleteMapping("/contact/{id}")
    public ContactDto deleteContact(@PathVariable int id) {
        return contactService.deleteContact(id);
    }
}
