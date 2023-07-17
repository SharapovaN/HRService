package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.ContactDto;

import java.util.List;

public interface ContactService {
    List<ContactDto> getEmployeeContactDtoList(long employeeId);

    ContactDto createEmployeeContact(ContactDto contactDto);

    ContactDto updateContact(ContactDto updateContactDto);

    void deleteContact(long contactId);
}
