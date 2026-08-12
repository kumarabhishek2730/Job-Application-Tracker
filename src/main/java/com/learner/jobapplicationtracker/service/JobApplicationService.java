package com.learner.jobapplicationtracker.service;

import com.learner.jobapplicationtracker.dto.JobApplicationDTO;
import com.learner.jobapplicationtracker.entity.JobApplication;
import com.learner.jobapplicationtracker.repo.JobApplicationRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

/** @noinspection ClassCanBeRecord*/
@Service
@AllArgsConstructor
@Slf4j
public class JobApplicationService {
    private JobApplicationRepo repo;
    private S3Client s3Client;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.bucketName}")
    private String bucketName;
    @Autowired
    public JobApplicationService(JobApplicationRepo repo, S3Client s3Client) {
        this.repo = repo;
        this.s3Client = s3Client;
    }
    public List<JobApplication> getAllJobs(){
        return repo.findAll();
    }

    public JobApplication addJobApplication(JobApplicationDTO jobApplicationDTO, MultipartFile resume) throws IOException {
        JobApplication jobApplication = mapDTOToEntity(jobApplicationDTO);
        String resumeUrl = uploadResumeToS3Bucket(resume);
        log.info("**************** RESUME URL************************"+resumeUrl);
        jobApplication.setResume(resumeUrl);
        return repo.save(jobApplication);
    }

    private String uploadResumeToS3Bucket(MultipartFile resume) throws IOException {
        PutObjectRequest resumeRequest= PutObjectRequest.builder()
                .bucket(bucketName)
                .key(resume.getOriginalFilename())
                .contentType(resume.getContentType())
                .build();
        PutObjectResponse response = s3Client.putObject(resumeRequest, RequestBody.fromByteBuffer(ByteBuffer.wrap(resume.getBytes())));
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + resume.getOriginalFilename();
    }

    private JobApplication mapDTOToEntity(JobApplicationDTO jobApplicationDTO) {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setCompanyName(jobApplicationDTO.getCompanyName());
        jobApplication.setJobTitle(jobApplicationDTO.getJobTitle());
        jobApplication.setRecruiterName(jobApplicationDTO.getRecruiterName());
        jobApplication.setRefererName(jobApplicationDTO.getRefererName());
        jobApplication.setApplicationDate(jobApplicationDTO.getApplicationDate());
        jobApplication.setApplicationLink(jobApplicationDTO.getApplicationLink());
        jobApplication.setStatus(jobApplicationDTO.getStatus());
        return jobApplication;
    }
}
