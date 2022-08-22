package com.example.CRUD.with.postgresql.rest.mapper;

import com.example.CRUD.with.postgresql.rest.dtos.JobDTO;
import com.example.CRUD.with.postgresql.model.Job;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface JobMapper {

    List<JobDTO> toJobDTOs(List<Job> jobs);
    Job toJob(JobDTO jobDTO);
    JobDTO toJobDTO(Job job);
}
