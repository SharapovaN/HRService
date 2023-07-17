package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.dto.JobDto;
import ru.otus.hrapp.model.entity.Job;
import ru.otus.hrapp.repository.JobRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;
import ru.otus.hrapp.util.ModelConverter;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public List<JobDto> getAllJobs() {
        log.info("getAllJobs method was called");

        return jobRepository.findAll().stream()
                .map(ModelConverter::toJobDto)
                .toList();
    }

    @Override
    public Job getJobById(long jobId) {
        log.info("getJobById method was called with jobId: {}", jobId);

        return jobRepository.findById(jobId).orElseThrow(() ->
                new ResourceNotFoundException("No job is found for the id: " + jobId));
    }
}
