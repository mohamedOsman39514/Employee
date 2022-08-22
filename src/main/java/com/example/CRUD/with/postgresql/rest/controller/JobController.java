package com.example.CRUD.with.postgresql.rest.controller;

import com.example.CRUD.with.postgresql.rest.exception.ResourceNotFound;
import com.example.CRUD.with.postgresql.rest.dtos.JobDTO;
import com.example.CRUD.with.postgresql.model.Job;
import com.example.CRUD.with.postgresql.rest.mapper.JobMapperImpl;
import com.example.CRUD.with.postgresql.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v3/jobs")
@Tag( name = "Jobs",description ="Rest Api For Jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobMapperImpl jobMapper;

    @GetMapping
    @Operation(summary = "This method is used to get all jobs")
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobDTOList = jobMapper.toJobDTOs(jobService.getAllJobs());
        return ResponseEntity.ok(jobDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "This method is used to get job")
    public ResponseEntity<JobDTO> getJobById(@PathVariable(value = "id") Long jobId)
            throws ResourceNotFound {
        Optional<Job> job = jobService.getJobById(jobId);
        JobDTO jobDTO = jobMapper.toJobDTO(job.get());
        return ResponseEntity.ok(jobDTO);
    }

    @PostMapping
    @Operation(summary = "This method is used to create job")
    public ResponseEntity<JobDTO> create(@RequestBody JobDTO jobDTO) {
        Job job = jobMapper.toJob(jobDTO);
        jobService.createJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "This method is used to update job")
    public ResponseEntity<JobDTO> update(@PathVariable Long id,	@RequestBody JobDTO jobDTO) throws ResourceNotFound {
        Job job = jobMapper.toJob(jobDTO);
        Job jobId = jobService.getJobById(id)
                .orElseThrow(()->new ResourceNotFound("Job Not Found"));
        job.setId(id);
        job.setName(job.getName()!=null ? jobId.getName() : jobId.getName());
        jobService.update(job);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jobDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "This method is used to delete job")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        jobService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
