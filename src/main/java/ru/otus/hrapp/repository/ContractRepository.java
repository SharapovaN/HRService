package ru.otus.hrapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hrapp.model.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

}
