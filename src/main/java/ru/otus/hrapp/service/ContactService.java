package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.ContactDto;
import ru.otus.hrapp.model.dto.SaveContactDto;

import java.util.List;

public interface ContactService {
    List<ContactDto> getEmployeeContactDtoList(long employeeId);

    ContactDto createEmployeeContact(SaveContactDto contactDto);

    ContactDto updateContact(ContactDto updateContactDto);

    void deleteContact(long contactId);
}
