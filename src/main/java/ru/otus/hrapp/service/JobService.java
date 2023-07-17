package ru.otus.hrapp.service;

import ru.otus.hrapp.model.dto.JobDto;
import ru.otus.hrapp.model.entity.Job;

import java.util.List;

public interface JobService {
    List<JobDto> getAllJobs();

    Job getJobById(long jobId);
}
