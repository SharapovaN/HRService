package ru.otus.hrapp.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.dto.ContactDto;
import ru.otus.hrapp.model.entity.Contact;
import ru.otus.hrapp.repository.ContactRepository;
import ru.otus.hrapp.repository.EmployeeRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;
import ru.otus.hrapp.util.ModelConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final EmployeeRepository employeeRepository;
    private final UpdateMapper updateMapper;

    public List<ContactDto> getEmployeeContactByEmployeeId(int employeeId) {
        log.debug("GetEmployeeContactByEmployeeId method was called with employeeId: " + employeeId);

        if (employeeRepository.findById(employeeId).isPresent()) {
            return contactRepository.findEmployeeContactByEmployeeId(employeeId).stream()
                    .map(ModelConverter::toContactDto).collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("No employee is found for the id: " + employeeId);
        }
    }

    public ContactDto createEmployeeContact(ContactDto contactDto) {
        log.debug("CreateEmployeeContact method was called with contactDto: " + contactDto);

        Contact contact = new Contact(contactDto.getType(), contactDto.getAccountName(), contactDto.getDescription(),
                contactDto.getEmployeeId());
        return ModelConverter.toContactDto(contactRepository.save(contact));
    }

    @Transactional
    public ContactDto updateContact(ContactDto updateContactDto) {
        log.debug("UpdateContact method was called with ID: " + updateContactDto.getId());

        Optional<Contact> contactOptional = contactRepository.findById(updateContactDto.getId());
        if (contactOptional.isPresent()) {
            Contact contact = contactOptional.get();
            updateMapper.updateContact(updateContactDto, contact);
            return ModelConverter.toContactDto(contactRepository.save(contact));
        } else {
            throw new BadRequestException("No contact is found for the id: " + updateContactDto.getId());
        }
    }
}
