package ru.otus.hrapp.controller;

import com.practice.hrapp.model.dto.CreateJobDto;
import com.practice.hrapp.model.dto.JobDto;
import com.practice.hrapp.service.JobService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@Api(tags = "Job Controller")
@RequestMapping("job")
@RestController
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @ApiOperation(
            value = "Get job by job ID",
            nickname = "getJobById")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public JobDto getJobById(@ApiParam(name = "jobId", value = "Job unique identifier")
                             @RequestParam int jobId) {
        return jobService.getJobById(jobId);
    }

    @ApiOperation(
            value = "Get all jobs",
            nickname = "getAllJobs")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("getAllJobs")
    public List<JobDto> getCountryAllCountries() {
        return jobService.getAllJobs();
    }

    @ApiOperation(
            value = "Create job",
            nickname = "createJob")
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 409, message = "Job already exists"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public JobDto createCountry(@ApiParam(name = "createJobDto", value = "DTO for country creation")
                                @RequestBody @Valid CreateJobDto createJobDto) {
        return jobService.createJob(createJobDto);
    }
}
