package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.ContactDto;
import ru.otus.hrapp.model.entity.Contact;

import java.util.List;

public interface ContactService {
    List<ContactDto> getEmployeeContactDtoList(long employeeId);

    ContactDto createEmployeeContact(ContactDto contactDto);

    ContactDto updateContact(ContactDto updateContactDto);

    List<Contact> getEmployeeContactList(long employeeId);

    void deleteContact(long contactId);

}
