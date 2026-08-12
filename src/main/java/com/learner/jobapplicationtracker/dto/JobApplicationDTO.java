package com.learner.jobapplicationtracker.dto;

import com.learner.jobapplicationtracker.entity.JobApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDTO {
    private String companyName;
    private String jobTitle;
    private String recruiterName;
    private String refererName;
    private String applicationDate;
    private String applicationLink;
    private JobApplication.Status status;

    public enum Status {
        APPLIED,
        INTERVIEW_SCHEDULED,
        REJECTED,
        OFFER_RECEIVED
    }
}
