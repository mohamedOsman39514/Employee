package com.example.CRUD.with.postgresql.service;

import com.example.CRUD.with.postgresql.model.Job;
import com.example.CRUD.with.postgresql.repositroy.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Optional<Job> getJobById(long id)
    {
        return jobRepository.findById(id);
    }

    public Job createJob(Job job) {
        jobRepository.save(job);
        return job;
    }

    public void delete(long id)
    {
        jobRepository.deleteById(id);
    }

    public void update(Job job)
    {
        jobRepository.save(job);
    }


}
