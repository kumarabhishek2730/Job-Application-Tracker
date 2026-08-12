# Job Application Tracker Backend

A simple backend application built with Java and Spring Boot to help users keep track of their job applications in one place.

The application stores job details such as company name, job title, recruiter, application date, status, and job link. It also allows users to upload their resume, which is stored securely in **Amazon S3**.

## What does this project do?

When you apply for different jobs, it can become difficult to remember:

* Which companies you applied to
* What position you applied for
* When you applied
* Who the recruiter is
* What your application status is
* Which resume you used

This backend provides APIs to store and manage this information.

For example, you can add an application like:

```text
Company: Google
Job Title: Software Engineer
Recruiter: John
Application Date: 13-Aug-2026
Status: Applied
Job Link: https://example.com/job
Resume: resume.pdf
```

The job information is stored in MySQL, while the uploaded resume is stored in Amazon S3.

---

## Main Features

* Add a new job application
* View all job applications
* Upload a resume with a job application
* Store resumes in Amazon S3
* Save resume URL with the job application
* Store job application data in MySQL
* Get database credentials from AWS Secrets Manager
* REST APIs built with Spring Boot

---

## How it works

The basic flow is:

```text
             User / Postman
                   |
                   v
          Spring Boot Backend
                   |
          +--------+--------+
          |                 |
          v                 v
       MySQL            Amazon S3
   Job application      Resume file
       details
          |
          |
          v
   AWS Secrets Manager
   Database credentials
```

### When adding a job

```text
1. Send job details + resume
            ↓
2. Spring Boot receives the request
            ↓
3. Resume is uploaded to Amazon S3
            ↓
4. S3 resume URL is generated
            ↓
5. Job details + resume URL are saved in MySQL
```

---

## Technologies Used

* Java 17
* Spring Boot 3.2.5
* Spring Web
* Spring Data JPA
* Hibernate
* MySQL
* Amazon S3
* AWS Secrets Manager
* AWS SDK
* Maven
* Lombok
* Gson

---

## Project Structure

```text
src/main/java/com/learner/jobapplicationtracker/

├── config/
│   ├── AwsSecretManagerConfig.java
│   ├── S3Config.java
│   └── SecretValue.java
│
├── controller/
│   └── JobApplicationController.java
│
├── dto/
│   └── JobApplicationDTO.java
│
├── entity/
│   └── JobApplication.java
│
├── repo/
│   └── JobApplicationRepo.java
│
└── service/
    └── JobApplicationService.java
```

The project follows a simple Spring Boot structure:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL
```

The controller handles API requests, the service contains the business logic, and the repository handles database operations.

---

# API Endpoints

## 1. Get all job applications

```http
GET /api/job-application
```

This returns all job applications stored in the database.

Example:

```bash
curl http://localhost:8080/api/job-application
```

---

## 2. Add a job application

```http
POST /api/job-application/add
```

This API accepts:

* Job application details
* Resume file

It uses `multipart/form-data`.

Example:

```bash
curl -X POST http://localhost:8080/api/job-application/add \
  -F 'json={"companyName":"Google","jobTitle":"Software Engineer","recruiterName":"John","refererName":"Jane","applicationDate":"2026-08-13","applicationLink":"https://example.com/job","status":"APPLIED"};type=application/json' \
  -F "file=@resume.pdf"
```

If the application is successfully added, the API returns the newly created application ID.

---

# AWS Integration

This project uses two AWS services.

### Amazon S3

Amazon S3 is used to store uploaded resumes.

Instead of storing the resume directly inside the database, the application uploads it to S3 and stores the resume URL in the job application record.

### AWS Secrets Manager

AWS Secrets Manager is used to store database credentials.

The application gets the database username and password from Secrets Manager when it starts.

This means database passwords don't need to be written directly inside the source code.

---

# Configuration

The application uses environment variables for AWS configuration.

```text
AWS_ACCESS_KEY_ID
AWS_SECRET_ACCESS_KEY
RDS_SECRET_NAME
BUCKET_NAME
```

Example:

```yaml
aws:
  accessKey: ${AWS_ACCESS_KEY_ID}
  secretKey: ${AWS_SECRET_ACCESS_KEY}
  region: ap-south-1
  rdsSecretName: ${RDS_SECRET_NAME}
  bucketName: ${BUCKET_NAME}
```

Never put your real AWS access key, secret key, database password, or other credentials directly into GitHub.

---

# Running the Project

## Requirements

Before running the project, install:

* Java 17
* MySQL
* AWS account
* AWS S3 bucket
* AWS Secrets Manager secret

You can use the Maven Wrapper included in the project, so installing Maven separately isn't required.

## Clone the project

```bash
git clone https://github.com/kumarabhishek2730/Job-Application-Tracker.git
```

Go into the project:

```bash
cd Job-Application-Tracker
```

## Set AWS environment variables

For Git Bash:

```bash
export AWS_ACCESS_KEY_ID="your-access-key"
export AWS_SECRET_ACCESS_KEY="your-secret-key"
export RDS_SECRET_NAME="your-secret-name"
export BUCKET_NAME="your-s3-bucket"
```

Then start the application:

```bash
./mvnw spring-boot:run
```

On Windows Command Prompt:

```cmd
mvnw.cmd spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

# Building the Project

To build the application:

```bash
./mvnw clean package
```

The generated JAR file will be available inside:

```text
target/
```

---

# Example Use Case

Imagine you apply to 20 different companies.

Instead of maintaining everything in an Excel sheet, you can use this backend to store:

```text
Company       → Amazon
Position      → Software Engineer
Recruiter     → John
Date Applied  → 13-Aug-2026
Status        → Interview
Job Link      → Company job URL
Resume        → Stored in Amazon S3
```

You can then retrieve your applications through the API.

---

# Future Improvements

Some features that can be added later:

* Update an application
* Delete an application
* Search applications
* Filter by application status
* Pagination
* Interview tracking
* Follow-up reminders
* Login and authentication
* Email notifications
* Resume download API
* Docker support
* CI/CD pipeline
* More automated tests

---

## Author

Abhishek Kumar

Backend Software Engineer | Java | Spring Boot | AWS | Microservices

---

## Note

This project is a backend-focused project created to demonstrate practical experience with Java, Spring Boot, REST APIs, database integration, file storage, and AWS services.
