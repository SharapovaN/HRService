package ru.otus.hrapp.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hrapp.model.dto.ContractDto;
import ru.otus.hrapp.service.ContractServiceImpl;

@AllArgsConstructor
@RestController
public class ContractController {

    private final ContractServiceImpl contractServiceImpl;

    @GetMapping("/contract")
    public ContractDto getContractById(@RequestParam int contractId) {
        return contractServiceImpl.getContractById(contractId);
    }

    @PutMapping("/contract")
    public ContractDto updateContract(@RequestBody @Valid ContractDto contractDto) {
        return contractServiceImpl.updateContract(contractDto);
    }
}
