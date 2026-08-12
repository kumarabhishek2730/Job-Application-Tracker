package com.learner.jobapplicationtracker.config;

import lombok.Data;

@Data
public class SecretValue {
    private String username;
    private String password;
    private String engine;
    private String host;
    private String port;
    private String dbname;
    private String dbInstanceIdentifier;
}
