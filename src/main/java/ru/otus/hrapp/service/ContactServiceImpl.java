package ru.otus.hrapp.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.dto.ContactDto;
import ru.otus.hrapp.model.entity.Contact;
import ru.otus.hrapp.repository.ContactRepository;
import ru.otus.hrapp.repository.mapper.UpdateMapper;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;
import ru.otus.hrapp.util.ModelConverter;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final UpdateMapper updateMapper;

    @Override
    public List<ContactDto> getEmployeeContactDtoList(long employeeId) {
        log.debug("GetEmployeeContactByEmployeeId method was called with employeeId: " + employeeId);

        return getEmployeeContactList(employeeId).stream().map(ModelConverter::toContactDto).toList();
    }

    @Override
    public ContactDto createEmployeeContact(ContactDto contactDto) {
        log.debug("CreateEmployeeContact method was called with contactDto: " + contactDto);

        Contact contact = new Contact();
        contact.setType(contactDto.getType());
        contact.setAccountName(contactDto.getAccountName());
        contact.setDescription(contactDto.getDescription());
        contact.setEmployeeId(contactDto.getEmployeeId());
        return ModelConverter.toContactDto(contactRepository.save(contact));
    }

    @Override
    @Transactional
    public ContactDto updateContact(ContactDto updateContactDto) {
        log.debug("UpdateContact method was called with ID: " + updateContactDto.getId());

        Optional<Contact> contactOptional = contactRepository.findById(updateContactDto.getId());
        if (contactOptional.isPresent()) {
            Contact contact = contactOptional.get();
            updateMapper.updateContact(updateContactDto, contact);
            return ModelConverter.toContactDto(contactRepository.save(contact));
        } else {
            throw new ResourceNotFoundException("No contact is found for the id: " + updateContactDto.getId());
        }
    }

    @Override
    public List<Contact> getEmployeeContactList(long employeeId) {
        return contactRepository.findByEmployeeId(employeeId);
    }

    @Override
    public void deleteContact(long contactId) {
        Contact contact = new Contact();
        contact.setId(contactId);
        contactRepository.delete(contact);
    }
}
