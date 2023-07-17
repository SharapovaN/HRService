package ru.otus.hrapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hrapp.model.dto.ContactDto;
import ru.otus.hrapp.service.ContactServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContactController {

    private final ContactServiceImpl contactService;

    @GetMapping("/contact/{employeeId}")
    public List<ContactDto> getContactByEmployeeId(@PathVariable int employeeId) {
        return contactService.getEmployeeContactDtoList(employeeId);
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
    public void deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
    }
}
