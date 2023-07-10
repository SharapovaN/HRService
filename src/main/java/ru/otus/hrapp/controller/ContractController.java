package ru.otus.hrapp.controller;

import com.practice.hrapp.model.dto.ContractDto;
import com.practice.hrapp.service.ContractService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Contract Controller")
@RequestMapping("contract")
@RestController
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @ApiOperation(
            value = "Get contract by ID",
            nickname = "getContract")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public ContractDto getContractById(@ApiParam(name = "contractId", value = "Contract unique identifier")
                                       @RequestParam int contractId) {
        return contractService.getContractById(contractId);
    }

    @ApiOperation(
            value = "Update contract",
            nickname = "updateContract")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public ContractDto updateContract(@ApiParam(name = "updateContractDto", value = "DTO for updating contract")
                                      @RequestBody @Valid ContractDto contractDto) {
        return contractService.updateContract(contractDto);
    }
}
