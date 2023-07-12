package ru.otus.hrapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hrapp.model.entity.Job;
import ru.otus.hrapp.repository.JobRepository;
import ru.otus.hrapp.service.exception.ResourceNotFoundException;

@Slf4j
@AllArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public Job getJobById(long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("No job is found for the id: " + jobId));
    }
}
