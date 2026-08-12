package com.learner.jobapplicationtracker.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class JobApplication {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String companyName;
    private String jobId;
    private String recruiterName;
    private String refererName;
    private String jobTitle;
    private String applicationDate;
    private String applicationLink;
    private String resume;
    private String coverLetter;
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        APPLIED,
        INTERVIEW_SCHEDULED,
        REJECTED,
        OFFER_RECEIVED
    }

}

