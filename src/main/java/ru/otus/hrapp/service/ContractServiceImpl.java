package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.dto.ContractDto;
import ru.otus.hrapp.model.entity.Contract;
import ru.otus.hrapp.repository.ContractRepository;
import ru.otus.hrapp.repository.mapper.UpdateMapper;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;
import ru.otus.hrapp.util.ModelConverter;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final UpdateMapper updateMapper;

    @Override
    public ContractDto updateContract(ContractDto contractDto) {
        log.debug("UpdateContract method was called with ID: " + contractDto.getId());

        Optional<Contract> contractOptional = contractRepository.findById(contractDto.getId());
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            updateMapper.updateContract(contractDto, contract);
            return ModelConverter.toContractDto(contractRepository.save(contract));
        } else {
            throw new ResourceNotFoundException("No contract is found for the id: " + contractDto.getId());
        }
    }

    @Override
    public Contract save(Contract contract) {
        return contractRepository.save(contract);
    }
}
