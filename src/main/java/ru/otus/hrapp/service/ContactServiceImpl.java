package ru.otus.hrapp.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.dto.ContactDto;
import ru.otus.hrapp.model.dto.SaveContactDto;
import ru.otus.hrapp.model.entity.Contact;
import ru.otus.hrapp.repository.ContactRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;
import ru.otus.hrapp.util.ModelConverter;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final EmployeeService employeeService;

    @Override
    @PreAuthorize("hasAnyRole('USER', 'HR_MANAGER')")
    public List<ContactDto> getEmployeeContactDtoList(long employeeId) {
        log.debug("GetEmployeeContactByEmployeeId method was called with employeeId: " + employeeId);

        return contactRepository.findByEmployeeId(employeeId).stream()
                .map(ModelConverter::toContactDto)
                .toList();
    }

    @Override
    @PreAuthorize("checkCreateEmployeeContactPermissions(#contactDto)")
    public ContactDto createEmployeeContact(SaveContactDto contactDto) {
        log.debug("CreateEmployeeContact method was called with contactDto: " + contactDto);

        Contact contact = new Contact();
        contact.setType(contactDto.getType());
        contact.setAccountName(contactDto.getAccountName());
        contact.setDescription(contactDto.getDescription());
        contact.setEmployee(employeeService.getEmployeeById(contactDto.getEmployeeId()));

        return ModelConverter.toContactDto(contactRepository.save(contact));
    }

    @Override
    @PreAuthorize("checkCreateEmployeeContactPermissions(#updateContactDto)")
    public ContactDto updateContact(ContactDto updateContactDto) {
        log.debug("UpdateContact method was called with ID: " + updateContactDto.getId());

        Optional<Contact> contactOptional = contactRepository.findById(updateContactDto.getId());
        if (contactOptional.isPresent()) {
            Contact contact = contactOptional.get();
            setContactFields(contact, updateContactDto);
            return ModelConverter.toContactDto(contactRepository.save(contact));
        } else {
            throw new ResourceNotFoundException("No contact is found for the id: " + updateContactDto.getId());
        }
    }

    @Override
    @PreAuthorize("hasRole('HR_MANAGER')")
    public void deleteContact(long contactId) {
        Contact contact = new Contact();
        contact.setId(contactId);
        contactRepository.delete(contact);
    }

    private void setContactFields(Contact contact, ContactDto updateContactDto) {
        contact.setAccountName(updateContactDto.getAccountName());
        contact.setDescription(updateContactDto.getDescription());
        contact.setEmployee(employeeService.getEmployeeById(updateContactDto.getEmployeeId()));
        contact.setType(updateContactDto.getType());
    }
}
