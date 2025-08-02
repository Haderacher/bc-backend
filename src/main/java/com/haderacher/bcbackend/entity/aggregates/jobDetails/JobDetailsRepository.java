package com.haderacher.bcbackend.entity.aggregates.jobDetails;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobDetailsRepository extends MongoRepository<JobDetails, String> {
}