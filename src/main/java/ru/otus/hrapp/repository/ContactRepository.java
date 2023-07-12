package ru.otus.hrapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hrapp.model.entity.Contact;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByEmployeeId(long id);
}
