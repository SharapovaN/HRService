package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.ContractDto;
import ru.otus.hrapp.model.entity.Contract;

public interface ContractService {
    ContractDto getContractById(long contractId);

    ContractDto updateContract(ContractDto contractDto);

    Contract save(Contract contract);
}
