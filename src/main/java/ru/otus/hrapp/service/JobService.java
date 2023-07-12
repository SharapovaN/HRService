package ru.otus.hrapp.service;

import ru.otus.hrapp.model.entity.Job;

public interface JobService {
    Job getJobById(long jobId);
}
