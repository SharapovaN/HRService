package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.entity.Contract;
import ru.otus.hrapp.repository.ContractRepository;

@Slf4j
@AllArgsConstructor
@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    @Override
    public Contract save(Contract contract) {
        return contractRepository.save(contract);
    }
}
